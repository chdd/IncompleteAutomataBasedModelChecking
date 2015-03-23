package it.polimi.contraintcomputation;

import it.polimi.automata.IntersectionBA;
import it.polimi.checker.ModelCheckingResults;
import it.polimi.checker.intersection.IntersectionBuilder;
import it.polimi.constraints.Color;
import it.polimi.constraints.Constraint;
import it.polimi.constraints.Port;
import it.polimi.contraintcomputation.subpropertyidentifier.IntersectionCleaner;
import it.polimi.contraintcomputation.subpropertyidentifier.SubPropertiesIdentifier;

import java.util.HashMap;

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
public class ConstraintGenerator {

	/**
	 * is the logger of the ConstraintGenerator class
	 */
	private static final Logger logger = LoggerFactory
			.getLogger(ConstraintGenerator.class);

	/**
	 * contains the intersection automaton
	 */
	private final IntersectionBA intersectionAutomaton;

	/**
	 * is updated with the Model checking results, which stores the verification
	 * results and the time required from the different verification steps
	 */
	private final ModelCheckingResults mcResults;

	/**
	 * is the intersection Builder. The intersection Builder is used by the
	 * Constraint generation class to analyze the relation between the
	 * intersection state and the states of the model, i.e., given a specific
	 * state of the model to have the corresponding state of the intersection
	 * automaton and vice-versa
	 */
	private final IntersectionBuilder intersectionBuilder;

	/**
	 * creates a new ConstraintGenerator object which starting from the
	 * intersection automaton and the map between the states of the model and
	 * the corresponding states of the intersection automaton computes the
	 * constraints
	 * 
	 * @param intersectionBuilder
	 *            is the intersection automaton
	 * @param mcResults
	 *            is the model to be verified
	 * @throws NullPointerException
	 *             if one of the parameters is null
	 */
	public ConstraintGenerator(IntersectionBuilder intersectionBuilder,
			ModelCheckingResults mcResults) {

		Preconditions.checkNotNull(intersectionBuilder,
				"The intersection builder cannot be null");
		Preconditions.checkNotNull(mcResults,
				"The model checking result class cannot be null");
		this.intersectionAutomaton = intersectionBuilder
				.getPrecomputedIntersectionAutomaton();
		this.intersectionBuilder = intersectionBuilder;
		this.mcResults = mcResults;
	}

	/**
	 * returns the constraint of the automaton
	 * 
	 * @return the constraint of the automaton
	 */
	public Constraint generateConstraint() {

		logger.info("Computing the constraint");
		/*
		 * removes from the intersection automaton the states from which it is
		 * not possible to reach an accepting state since these states are not
		 * useful in the constraint computation
		 */
		IntersectionCleaner intersectionCleaner = new IntersectionCleaner(
				intersectionAutomaton, intersectionBuilder);
		intersectionCleaner.clean();

		SubPropertiesIdentifier subPropertiesIdentifier = new SubPropertiesIdentifier(
				intersectionBuilder, this.mcResults);
		Constraint constraint = subPropertiesIdentifier.getSubAutomata(
				new HashMap<Port, Color>(), new HashMap<Port, Color>());

		// PORT REACHABILITY
		/*
		 * if (mcResults.isPortReachability()) { Set<Port> visitedPorts = new
		 * HashSet<Port>();
		 * 
		 * long startPortReachabilityTime = System.nanoTime();
		 * ReachabilityChecker<IntersectionBA> reachabilityChecker = new
		 * ReachabilityChecker<IntersectionBA>( intersectionAutomaton,
		 * intersectionAutomaton.getRegularStates()); Map<State, Set<State>>
		 * forwardReachability = reachabilityChecker
		 * .forwardReachabilitycheck();
		 * 
		 * for (State init : intersectionAutomaton.getInitialStates()) {
		 * 
		 * if (forwardReachability.containsKey(init)) { Collection<State>
		 * reachableStates = forwardReachability .get(init); for (Port port :
		 * constraint.getIncomingPorts()) { if
		 * (reachableStates.contains(port.getSource())) {
		 * constraint.setPortValue(port, Color.GREEN); visitedPorts.add(port); }
		 * } } } Map<S, Collection<S>> backReachability = reachabilityChecker
		 * .backWardReachabilitycheck(); for (S accepting :
		 * intersectionAutomaton.getAcceptStates()) { if
		 * (backReachability.containsKey(accepting)) {
		 * 
		 * Collection<S> reachableStates = backReachability .get(accepting); for
		 * (Port<S, T> port : constraint.getOutcomingPorts()) { if
		 * (reachableStates.contains(port.getDestination())) {
		 * constraint.setPortValue(port, Color.RED); visitedPorts.add(port); } }
		 * } } for (Port<S, T> port : constraint.getPorts()) { if
		 * (!visitedPorts.contains(port) && constraint.getPortValue(port) !=
		 * Color.RED && constraint.getPortValue(port) != Color.GREEN) {
		 * constraint.setPortValue(port, Color.YELLOW); } } long
		 * stopPortReachabilityTime = System.nanoTime();
		 * 
		 * long portReachabilityTime = ((stopPortReachabilityTime -
		 * startPortReachabilityTime));
		 * 
		 * mcResults.setPortReachabilityTime(mcResults
		 * .getPortReachabilityTime() + portReachabilityTime); }
		 */

		logger.info("Constraint computed");
		return constraint;
	}
}
