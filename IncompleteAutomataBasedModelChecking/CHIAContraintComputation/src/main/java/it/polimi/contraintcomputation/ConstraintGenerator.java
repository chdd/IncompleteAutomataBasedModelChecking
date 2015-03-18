package it.polimi.contraintcomputation;

import it.polimi.automata.BA;
import it.polimi.automata.IntersectionBA;
import it.polimi.automata.state.State;
import it.polimi.automata.transition.Transition;
import it.polimi.checker.ModelCheckingResults;
import it.polimi.checker.intersection.IntersectionBuilder;
import it.polimi.constraints.Color;
import it.polimi.constraints.Constraint;
import it.polimi.constraints.Port;
import it.polimi.constraints.impl.BAComponentFactory;
import it.polimi.contraintcomputation.subautomatafinder.IntersectionCleaner;
import it.polimi.contraintcomputation.subautomatafinder.ReachabilityChecker;
import it.polimi.contraintcomputation.subpropertyidentifier.SubPropertiesIdentifier;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Preconditions;

/**
 * The constraint generator computes a constraint. A constraint is a (set of)
 * sub-model(s) for the unspecified components is produce
 * 
 * @author claudiomenghi
 * 
 */
public class ConstraintGenerator<S extends State, T extends Transition> {

	/**
	 * is the logger of the ConstraintGenerator class
	 */
	private static final Logger logger = LoggerFactory
			.getLogger(ConstraintGenerator.class);

	/**
	 * contains the intersection automaton
	 */
	private IntersectionBA<S, T> intersectionAutomaton;

	private final ModelCheckingResults mcResults;
	private IntersectionBuilder<S, T> intersectionBuilder;

	/**
	 * creates a new ConstraintGenerator object which starting from the
	 * intersection automaton and the map between the states of the model and
	 * the corresponding states of the intersection automaton computes the
	 * constraints
	 * 
	 * @param intersectionAutomaton
	 *            is the intersection automaton
	 * @param model
	 *            is the model to be verified
	 * @param modelIntersectionStatesMap
	 *            is the map between the states of the model and the
	 *            corresponding states in the intersection automaton
	 * @param subpropertiestransitionFactory
	 *            is the factory to be used to create transitions
	 * @throws NullPointerException
	 *             if one of the parameters is null
	 */
	public ConstraintGenerator(IntersectionBuilder<S, T> intersectionBuilder,
			ModelCheckingResults mcResults) {

		Preconditions.checkNotNull(intersectionBuilder,
				"The intersection builder cannot be null");
		this.intersectionAutomaton = intersectionBuilder
				.getPrecomputedIntersectionAutomaton();
		this.intersectionBuilder = intersectionBuilder;
		this.mcResults = mcResults;
	}

	public IntersectionBA<S, T> getIntersectionAutomaton() {
		return this.intersectionAutomaton;
	}

	/**
	 * returns the constraint of the automaton
	 * 
	 * @return the constraint of the automaton
	 */
	public Constraint<S, T, BA<S, T>> generateConstraint() {

		logger.info("Computing the constraint");
		/*
		 * removes from the intersection automaton the states from which it is
		 * not possible to reach an accepting state since these states are not
		 * useful in the constraint computation
		 */
		IntersectionCleaner<S, T> intersectionCleaner = new IntersectionCleaner<S, T>(
				intersectionAutomaton, intersectionBuilder);
		intersectionCleaner.clean();

		SubPropertiesIdentifier<S, T, BA<S, T>> subPropertiesIdentifier = new SubPropertiesIdentifier<S, T, BA<S, T>>(
				intersectionBuilder, new BAComponentFactory<S, T>(),
				this.mcResults);
		Constraint<S, T, BA<S, T>> constraint = subPropertiesIdentifier
				.getSubAutomata(new HashMap<Port<S, T>, Color>(),
						new HashMap<Port<S, T>, Color>());

		
		if(mcResults.isPortReachability()){
			Set<Port<S, T>> visitedPorts = new HashSet<Port<S, T>>();

			long startPortReachabilityTime = System.nanoTime();
			ReachabilityChecker<S, T, IntersectionBA<S, T>> reachabilityChecker = new ReachabilityChecker<S, T, IntersectionBA<S, T>>(
					intersectionAutomaton, intersectionAutomaton.getRegularStates());
			Map<S, Collection<S>> forwardReachability = reachabilityChecker
					.forwardReachabilitycheck();

			for (S init : intersectionAutomaton.getInitialStates()) {

				if (forwardReachability.containsKey(init)) {
					Collection<S> reachableStates = forwardReachability.get(init);
					for (Port<S, T> port : constraint.getIncomingPorts()) {
						if (reachableStates.contains(port.getSource())) {
							constraint.setPortValue(port, Color.GREEN);
							visitedPorts.add(port);
						}
					}
				}
			}
			Map<S, Collection<S>> backReachability = reachabilityChecker
					.backWardReachabilitycheck();
			for (S accepting : intersectionAutomaton.getAcceptStates()) {
				if (backReachability.containsKey(accepting)) {

					Collection<S> reachableStates = backReachability.get(accepting);
					for (Port<S, T> port : constraint.getOutcomingPorts()) {
						if (reachableStates.contains(port.getDestination())) {
							constraint.setPortValue(port, Color.RED);
							visitedPorts.add(port);
						}
					}
				}
			}
			for (Port<S, T> port : constraint.getPorts()) {
				if (!visitedPorts.contains(port)
						&& constraint.getPortValue(port) != Color.RED
						&& constraint.getPortValue(port) != Color.GREEN) {
					constraint.setPortValue(port, Color.YELLOW);
				}
			}
			long stopPortReachabilityTime = System.nanoTime();

			long portReachabilityTime = ((stopPortReachabilityTime - startPortReachabilityTime));

			mcResults.setPortReachabilityTime(mcResults.getPortReachabilityTime()
					+ portReachabilityTime);
		}
		

		logger.info("Constraint computed");
		return constraint;
	}
}
