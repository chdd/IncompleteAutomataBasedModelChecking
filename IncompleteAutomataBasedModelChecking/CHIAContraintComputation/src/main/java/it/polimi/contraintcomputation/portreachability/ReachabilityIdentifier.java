package it.polimi.contraintcomputation.portreachability;

import it.polimi.automata.IntersectionBA;
import it.polimi.automata.state.State;
import it.polimi.automata.transition.Transition;
import it.polimi.checker.abstractor.Abstractor;
import it.polimi.checker.intersection.IntersectionBuilder;
import it.polimi.constraints.transitions.ColoredPluggingTransition;
import it.polimi.contraintcomputation.subpropertyidentifier.SubPropertyIdentifier;

import java.util.AbstractMap;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.jgrapht.DirectedGraph;
import org.jgrapht.GraphPath;
import org.jgrapht.alg.FloydWarshallShortestPaths;

import com.google.common.base.Preconditions;

/**
 * The PortReachability class allows to compute the reachability between the
 * ports of the sub-properties and the corresponding colors, through the methods
 * computeInternalReachability and computeExternalReachability. <br/>
 * 
 * The computeInternalReachability method computes the internal reachability
 * between the ports (i.e., the reachability between the incoming and the
 * outcoming ports of a subproperty). It uses the ReachabilityChecker to compute
 * the reachability between the mixed states of the intersection automaton and
 * it connects the incoming and outcoming ports of the sub-properties
 * accordingly. <br/>
 * 
 * The computeExternalReachability method computes the external reachability
 * between the ports (i.e., the reachability between the incoming and the
 * outcoming ports of the different subpropertites). It uses the
 * ReachabilityChecker to compute the reachability between the regular states of
 * the intersection automaton and it connects the incoming and outcoming ports
 * of the sub-properties accordingly.
 * 
 * @author claudiomenghi
 *
 */
public class ReachabilityIdentifier {

	/**
	 * is the builder which is used to compute the intersection automaton
	 */
	private final IntersectionBuilder intersectionBuilder;

	/**
	 * is the identifier which has been used to generate the sub-properties
	 */
	private final SubPropertyIdentifier subPropertiesIdentifier;

	/**
	 * creates a new PortReachability component which is used to compute and
	 * update the reachability relation between the ports of the component
	 * 
	 * @param intersectionBuilder
	 *            is the builder which is used to compute the intersection
	 * @param subPropertiesIdentifier
	 *            is the component which has been used to compute the
	 *            sub-properties
	 * @throws NullPointerException
	 *             if one of the parameters is null
	 */
	public ReachabilityIdentifier(IntersectionBuilder intersectionBuilder,
			SubPropertyIdentifier subPropertiesIdentifier) {
		Preconditions.checkNotNull(intersectionBuilder,
				"The intersectionBuilder cannot be null");
		Preconditions.checkNotNull(subPropertiesIdentifier,
				"The subproperties cannot be null");

		this.intersectionBuilder = intersectionBuilder;
		this.subPropertiesIdentifier = subPropertiesIdentifier;
	}

	/**
	 * updates the reachability relation insider the constraint
	 */
	public void computeReachability() {
		this.computeLowerBoundRechabilityRelation();
		this.computeUpperBoundTransitionGraph();
	}

	private void computeLowerBoundRechabilityRelation() {

		IntersectionBA intersectionAutomaton = this.intersectionBuilder
				.getIntersectionAutomaton();

		IntersectionBA abstractedIntersection = new Abstractor<IntersectionBA>()
				.perform(intersectionAutomaton,
						intersectionAutomaton.getPurelyRegularStates());

		DirectedGraph<State, Transition> graph = abstractedIntersection
				.getGraph();

		Collection<GraphPath<State, Transition>> paths = new FloydWarshallShortestPaths<State, Transition>(
				graph).getShortestPaths();

		//AcceptingStatePathChecker acceptingPathChecker = new AcceptingStatePathChecker(
		//		this.intersectionBuilder, intersectionAutomaton,
		//		intersectionAutomaton.getPurelyRegularStates());

		AcceptingClaimStatePathChecker acceptingClaimStatePathChecker = new AcceptingClaimStatePathChecker(
				intersectionAutomaton,
				intersectionAutomaton.getPurelyRegularStates(),
				intersectionBuilder);

		AcceptingModelStatePathChecker acceptingModelStatePathChecker = new AcceptingModelStatePathChecker(
				intersectionAutomaton,
				intersectionAutomaton.getPurelyRegularStates(),
				intersectionBuilder);

		for (Entry<State, State> path : this.getReachabilityRelation(paths,
				intersectionAutomaton.getPurelyRegularStates())) {

			for (Transition outTransition : intersectionAutomaton
					.getInTransitions(path.getKey())) {

				for (Transition inTransition : intersectionAutomaton
						.getOutTransitions(path.getValue())) {

					if (subPropertiesIdentifier
							.getMapIntersectionTransitionOutgoingPorts()
							.keySet().contains(outTransition)
							&& subPropertiesIdentifier
									.getMapIntersectionTransitionIncomingPort()
									.keySet().contains(inTransition)) {
						ColoredPluggingTransition subPropertyOuttransition = this.subPropertiesIdentifier
								.getOutgoingPort(outTransition);
						ColoredPluggingTransition subPropertyInTransition = this.subPropertiesIdentifier
								.getIncomingTransition(inTransition);

						State source = intersectionAutomaton
								.getTransitionDestination(outTransition);
						State destination = intersectionAutomaton
								.getTransitionSource(inTransition);
						this.subPropertiesIdentifier.getSubProperty()
								.addReachabilityRelation(
										subPropertyOuttransition,
										subPropertyInTransition,
										acceptingModelStatePathChecker.perform(
												source, destination),
										acceptingClaimStatePathChecker.perform(
												source, destination));
					}
				}
			}

		}

	}

	private Set<Entry<State, State>> getReachabilityRelation(
			Collection<GraphPath<State, Transition>> paths, Set<State> states) {
		Preconditions.checkNotNull(paths, "The paths cannot be null");
		Preconditions.checkNotNull(states, "The states cannot be null");
		Set<Entry<State, State>> reachabilityMap = new HashSet<Map.Entry<State, State>>();
		for (GraphPath<State, Transition> path : paths) {
			reachabilityMap.add(new AbstractMap.SimpleEntry<State, State>(path
					.getStartVertex(), path.getEndVertex()));
		}
		for (State s : states) {
			reachabilityMap
					.add(new AbstractMap.SimpleEntry<State, State>(s, s));
		}
		return reachabilityMap;
	}

	/*
	 * private void computeLowerBoundTransitionGraph() {
	 * 
	 * IntersectionBA intersectionAutomaton = this.intersectionBuilder
	 * .getIntersectionAutomaton(); DirectedGraph<Transition, DefaultEdge> graph
	 * = new TransitionGraph( subPropertiesIdentifier
	 * .getMapIntersectionTransitionOutgoingPorts().keySet(),
	 * subPropertiesIdentifier
	 * .getMapIntersectionTransitionIncomingPort().keySet(),
	 * intersectionAutomaton.getPurelyRegularStates(),
	 * intersectionAutomaton).getTransitionGraph();
	 * 
	 * Collection<GraphPath<Transition, DefaultEdge>> paths = new
	 * FloydWarshallShortestPaths<Transition, DefaultEdge>(
	 * graph).getShortestPaths(); AcceptingStatePathChecker acceptingPathChecker
	 * = new AcceptingStatePathChecker( intersectionAutomaton,
	 * intersectionAutomaton.getPurelyRegularStates());
	 * 
	 * AcceptingClaimStatePathChecker acceptingClaimStatePathChecker = new
	 * AcceptingClaimStatePathChecker( intersectionAutomaton,
	 * intersectionAutomaton.getPurelyRegularStates(), intersectionBuilder);
	 * 
	 * AcceptingModelStatePathChecker acceptingModelStatePathChecker=new
	 * AcceptingModelStatePathChecker( intersectionAutomaton,
	 * intersectionAutomaton.getPurelyRegularStates(), intersectionBuilder); for
	 * (GraphPath<Transition, DefaultEdge> path : paths) {
	 * 
	 * Transition outTransition = path.getStartVertex(); Transition inTransition
	 * = path.getEndVertex(); if (subPropertiesIdentifier
	 * .getMapIntersectionTransitionOutgoingPorts().keySet()
	 * .contains(outTransition) && subPropertiesIdentifier
	 * .getMapIntersectionTransitionIncomingPort()
	 * .keySet().contains(inTransition)) { ColoredPluggingTransition
	 * subPropertyOuttransition = this.subPropertiesIdentifier
	 * .getOutgoingPort(outTransition); ColoredPluggingTransition
	 * subPropertyInTransition = this.subPropertiesIdentifier
	 * .getIncomingPort(inTransition);
	 * 
	 * State source = intersectionAutomaton
	 * .getTransitionDestination(outTransition); State destination =
	 * intersectionAutomaton .getTransitionSource(inTransition);
	 * this.subPropertiesIdentifier.getSubProperty() .addReachabilityRelation(
	 * subPropertyOuttransition, subPropertyInTransition,
	 * acceptingPathChecker.perform(source, destination),
	 * acceptingClaimStatePathChecker.perform(source, destination),
	 * acceptingModelStatePathChecker.perform(source, destination)); } } }
	 */

	private void computeUpperBoundTransitionGraph() {

		IntersectionBA intersectionAutomaton = this.intersectionBuilder
				.getIntersectionAutomaton();
		Set<State> states = new HashSet<State>(
				intersectionAutomaton.getStates());
		states.removeAll(this.subPropertiesIdentifier.getSubProperty()
				.getAutomaton().getStates());

		IntersectionBA abstractedIntersection = new Abstractor<IntersectionBA>()
				.perform(intersectionAutomaton, states);

		DirectedGraph<State, Transition> graph = abstractedIntersection
				.getGraph();

		Collection<GraphPath<State, Transition>> paths = new FloydWarshallShortestPaths<State, Transition>(
				graph).getShortestPaths();

		//AcceptingStatePathChecker acceptingPathChecker = new AcceptingStatePathChecker(
		//		this.intersectionBuilder, intersectionAutomaton, states);

		AcceptingClaimStatePathChecker acceptingClaimStatePathChecker = new AcceptingClaimStatePathChecker(
				intersectionAutomaton, states, intersectionBuilder);

		AcceptingModelStatePathChecker acceptingModelStatePathChecker = new AcceptingModelStatePathChecker(
				intersectionAutomaton, states, intersectionBuilder);

		for (Entry<State, State> path : this.getReachabilityRelation(paths,
				states)) {

			for (Transition outTransition : intersectionAutomaton
					.getInTransitions(path.getKey())) {

				for (Transition inTransition : intersectionAutomaton
						.getOutTransitions(path.getValue())) {

					if (subPropertiesIdentifier
							.getMapIntersectionTransitionOutgoingPorts()
							.keySet().contains(outTransition)
							&& subPropertiesIdentifier
									.getMapIntersectionTransitionIncomingPort()
									.keySet().contains(inTransition)) {
						ColoredPluggingTransition subPropertyOuttransition = this.subPropertiesIdentifier
								.getOutgoingPort(outTransition);
						ColoredPluggingTransition subPropertyInTransition = this.subPropertiesIdentifier
								.getIncomingTransition(inTransition);

						State source = intersectionAutomaton
								.getTransitionDestination(outTransition);
						State destination = intersectionAutomaton
								.getTransitionSource(inTransition);
						this.subPropertiesIdentifier.getSubProperty()
								.addPossibleReachabilityRelation(
										subPropertyOuttransition,
										subPropertyInTransition,
										acceptingModelStatePathChecker.perform(
												source, destination),
										acceptingClaimStatePathChecker.perform(
												source, destination));
					}
				}
			}

		}
	}

	/*
	 * private void computeUpperBoundTransitionGraph() {
	 * 
	 * IntersectionBA intersectionAutomaton = this.intersectionBuilder
	 * .getIntersectionAutomaton(); Set<State> states = new HashSet<State>(
	 * intersectionAutomaton.getStates());
	 * states.removeAll(this.subPropertiesIdentifier.getSubProperty()
	 * .getAutomaton().getStates());
	 * 
	 * DirectedGraph<Transition, DefaultEdge> graph = new TransitionGraph(
	 * subPropertiesIdentifier
	 * .getMapIntersectionTransitionOutgoingPorts().keySet(),
	 * subPropertiesIdentifier
	 * .getMapIntersectionTransitionIncomingPort().keySet(), states,
	 * intersectionAutomaton).getTransitionGraph();
	 * 
	 * Collection<GraphPath<Transition, DefaultEdge>> paths = new
	 * FloydWarshallShortestPaths<Transition, DefaultEdge>(
	 * graph).getShortestPaths(); AcceptingStatePathChecker acceptingPathChecker
	 * = new AcceptingStatePathChecker( intersectionAutomaton, states);
	 * AcceptingClaimStatePathChecker acceptingClaimStatePathChecker = new
	 * AcceptingClaimStatePathChecker( intersectionAutomaton, states,
	 * intersectionBuilder);
	 * 
	 * AcceptingModelStatePathChecker acceptingModelStatePathChecker = new
	 * AcceptingModelStatePathChecker( intersectionAutomaton, states,
	 * intersectionBuilder); for (GraphPath<Transition, DefaultEdge> path :
	 * paths) {
	 * 
	 * Transition outTransition = path.getStartVertex(); Transition inTransition
	 * = path.getEndVertex(); if (subPropertiesIdentifier
	 * .getMapIntersectionTransitionOutgoingPorts().keySet()
	 * .contains(outTransition) && subPropertiesIdentifier
	 * .getMapIntersectionTransitionIncomingPort()
	 * .keySet().contains(inTransition)) { ColoredPluggingTransition
	 * subPropertyOuttransition = this.subPropertiesIdentifier
	 * .getOutgoingPort(outTransition); ColoredPluggingTransition
	 * subPropertyInTransition = this.subPropertiesIdentifier
	 * .getIncomingPort(inTransition); State source = intersectionAutomaton
	 * .getTransitionDestination(outTransition); State destination =
	 * intersectionAutomaton .getTransitionSource(inTransition);
	 * this.subPropertiesIdentifier.getSubProperty()
	 * .addPossibleReachabilityRelation( subPropertyOuttransition,
	 * subPropertyInTransition, acceptingPathChecker.perform(source,
	 * destination), acceptingClaimStatePathChecker.perform(source,
	 * destination), acceptingModelStatePathChecker.perform(source,
	 * destination)); } } }
	 */

}
