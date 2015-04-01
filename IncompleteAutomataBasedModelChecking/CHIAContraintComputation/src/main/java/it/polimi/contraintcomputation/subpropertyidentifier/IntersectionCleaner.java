package it.polimi.contraintcomputation.subpropertyidentifier;

import it.polimi.automata.IntersectionBA;
import it.polimi.automata.state.State;
import it.polimi.checker.emptiness.EmptinessChecker;
import it.polimi.checker.intersection.IntersectionBuilder;
import it.polimi.contraintcomputation.CHIAOperation;

import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Preconditions;

/**
 * The intersection cleaner removes from the intersection automaton the states
 * from which it it not possible to reach an accepting state of the intersection
 * automaton. Indeed, the states from which it is not possible to reach an
 * accepting state are not useful in the constraint computation.<br>
 * 
 * Note that the intersectionCleaner also updates the IntersectionBuilder which
 * keeps track of the relation between the intersection state and the original
 * states of the model
 * 
 * @author claudiomenghi
 * 
 */
public class IntersectionCleaner extends CHIAOperation{

	/**
	 * is the logger of the SubAutomataIdentifier class
	 */
	private static final Logger logger = LoggerFactory
			.getLogger(IntersectionCleaner.class);

	/**
	 * contains the automaton to be considered by the {@link EmptinessChecker}
	 */
	private final IntersectionBA intersectionAutomaton;

	/**
	 * contains the IntersectionBuilder to be updated
	 */
	private final IntersectionBuilder intersectionBuilder;

	/**
	 * creates a new IntersectionCleaner
	 * 
	 * @param intersectionBuilder
	 *            contains the intersection automaton, the relation between the
	 *            states of the intersection automaton and the states of the
	 *            model etc
	 * @throws NullPointerException
	 *             if one of the parameters is null
	 */
	public IntersectionCleaner(IntersectionBuilder intersectionBuilder) {
		super();
		Preconditions.checkNotNull(intersectionBuilder,
				"The intersection Builder cannot be null");

		this.intersectionAutomaton = intersectionBuilder
				.getPrecomputedIntersectionAutomaton();
		this.intersectionBuilder = intersectionBuilder;

	}

	/**
	 * Removes the states from which it is not possible to reach an accepting
	 * state from the intersection automaton
	 */
	public void clean() {

		logger.info("Starting the cleaning phase");

		/*
		 * contains the set of the states that has been encountered by
		 * <i>some<i> invocation of the first DFS
		 */
		Set<State> reachableStates = new HashSet<State>();

		/*
		 * contains the set of the visited states
		 */
		Set<State> visitedStates = new HashSet<State>();
		Set<State> toBeVisited = new HashSet<State>();
		toBeVisited.addAll(this.intersectionAutomaton.getAcceptStates());
		while (!toBeVisited.isEmpty()) {

			State currentState = toBeVisited.iterator().next();
			if (!visitedStates.contains(currentState)) {
				toBeVisited.addAll(this.intersectionAutomaton
						.getPredecessors(currentState));
				reachableStates.addAll(this.intersectionAutomaton
						.getPredecessors(currentState));

				visitedStates.add(currentState);
			}
			toBeVisited.remove(currentState);
		}

		Set<State> toBeRemoved = new HashSet<State>(
				this.intersectionAutomaton.getStates());
		toBeRemoved.removeAll(reachableStates);
		// removing the non reachable states
		for (State s : toBeRemoved) {
			State modelState = this.intersectionBuilder
					.getIntersectionStateModelStateMap().get(s);
			this.intersectionBuilder.getModelStateIntersectionStateMap()
					.get(modelState).remove(s);
			this.intersectionBuilder.getIntersectionStateModelStateMap()
					.remove(s);
			State claimState = this.intersectionBuilder
					.getIntersectionStateClaimStateMap().get(s);
			this.intersectionBuilder.getClaimStateIntersectionStateMap()
					.get(claimState).remove(s);
			this.intersectionBuilder.getIntersectionStateClaimStateMap()
					.remove(s);

			this.intersectionAutomaton.removeState(s);
		}

		logger.info("The cleaning phase has removed: " + toBeRemoved.size()
				+ " states");
		this.setPerformed(true);
	}
}
