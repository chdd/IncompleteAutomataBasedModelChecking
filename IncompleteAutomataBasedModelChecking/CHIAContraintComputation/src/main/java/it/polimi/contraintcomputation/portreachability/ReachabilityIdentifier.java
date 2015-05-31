package it.polimi.contraintcomputation.portreachability;

import it.polimi.automata.IntersectionBA;
import it.polimi.automata.state.State;
import it.polimi.automata.transition.Transition;
import it.polimi.checker.intersection.IntersectionBuilder;
import it.polimi.constraints.transitions.ColoredPluggingTransition;
import it.polimi.contraintcomputation.subpropertyidentifier.SubPropertyIdentifier;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.jgrapht.DirectedGraph;
import org.jgrapht.GraphPath;
import org.jgrapht.alg.FloydWarshallShortestPaths;
import org.jgrapht.graph.DefaultEdge;

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
		this.computeLowerBoundTransitionGraph();
		this.computeUpperBoundTransitionGraph();
	}

	private void computeLowerBoundTransitionGraph() {

		IntersectionBA intersectionAutomaton = this.intersectionBuilder
				.getIntersectionAutomaton();
		DirectedGraph<Transition, DefaultEdge> graph = new TransitionGraph(
				subPropertiesIdentifier
						.getMapIntersectionTransitionOutgoingPorts().keySet(),
				subPropertiesIdentifier
						.getMapIntersectionTransitionIncomingPort().keySet(),
				intersectionAutomaton.getPurelyRegularStates(),
				intersectionAutomaton).getTransitionGraph();

		Collection<GraphPath<Transition, DefaultEdge>> paths = new FloydWarshallShortestPaths<Transition, DefaultEdge>(
				graph).getShortestPaths();
		for (GraphPath<Transition, DefaultEdge> path : paths) {

			Transition outTransition = path.getStartVertex();
			Transition inTransition = path.getEndVertex();
			if (subPropertiesIdentifier
					.getMapIntersectionTransitionOutgoingPorts().keySet()
					.contains(outTransition)
					&& subPropertiesIdentifier
							.getMapIntersectionTransitionIncomingPort()
							.keySet().contains(inTransition)) {
				ColoredPluggingTransition subPropertyOuttransition = this.subPropertiesIdentifier
						.getOutgoingPort(outTransition);
				ColoredPluggingTransition subPropertyInTransition = this.subPropertiesIdentifier
						.getIncomingPort(inTransition);
				this.subPropertiesIdentifier.getSubProperty()
						.addReachabilityRelation(subPropertyOuttransition,
								subPropertyInTransition);
			}
		}
	}

	private void computeUpperBoundTransitionGraph() {

		IntersectionBA intersectionAutomaton = this.intersectionBuilder
				.getIntersectionAutomaton();
		Set<State> states = new HashSet<State>(
				intersectionAutomaton.getStates());
		states.removeAll(this.subPropertiesIdentifier.getSubProperty()
				.getAutomaton().getStates());
		
		DirectedGraph<Transition, DefaultEdge> graph = new TransitionGraph(
				subPropertiesIdentifier
						.getMapIntersectionTransitionOutgoingPorts().keySet(),
				subPropertiesIdentifier
						.getMapIntersectionTransitionIncomingPort().keySet(),
						states,
				intersectionAutomaton).getTransitionGraph();

		Collection<GraphPath<Transition, DefaultEdge>> paths = new FloydWarshallShortestPaths<Transition, DefaultEdge>(
				graph).getShortestPaths();
		for (GraphPath<Transition, DefaultEdge> path : paths) {

			Transition outTransition = path.getStartVertex();
			Transition inTransition = path.getEndVertex();
			if (subPropertiesIdentifier
					.getMapIntersectionTransitionOutgoingPorts().keySet()
					.contains(outTransition)
					&& subPropertiesIdentifier
							.getMapIntersectionTransitionIncomingPort()
							.keySet().contains(inTransition)) {
				ColoredPluggingTransition subPropertyOuttransition = this.subPropertiesIdentifier
						.getOutgoingPort(outTransition);
				ColoredPluggingTransition subPropertyInTransition = this.subPropertiesIdentifier
						.getIncomingPort(inTransition);
				this.subPropertiesIdentifier.getSubProperty()
						.addPossibleReachabilityRelation(subPropertyOuttransition,
								subPropertyInTransition);
			}
		}
	}

}
