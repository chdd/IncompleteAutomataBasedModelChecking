package it.polimi.contraintcomputation.subautomatafinder;

import it.polimi.automata.IntersectionBA;
import it.polimi.automata.state.State;
import it.polimi.automata.transition.Transition;
import it.polimi.checker.emptiness.EmptinessChecker;
import it.polimi.checker.intersection.IntersectionBuilder;

import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Preconditions;

/**
 * The intersection cleaner removes from the intersection automaton the states
 * from which it it not possible to reach an accepting state of the intersection
 * automaton. Indeed, the states from which it is not possible to reach an
 * accepting state are not useful in the constraint computation.
 * 
 * @author claudiomenghi
 * 
 * @param <S>
 *            is the type of the state of the Buchi Automaton. The type of the
 *            states of the automaton must implement the interface {@link State}
 * @param <T>
 *            is the type of the transition of the Buchi Automaton. The typer of
 *            the transitions of the automaton must implement the interface
 *            {@link Transition}
 */
public class IntersectionCleaner<S extends State, T extends Transition> {

	/**
	 * is the logger of the SubAutomataIdentifier class
	 */
	private static final Logger logger = LoggerFactory
			.getLogger(IntersectionCleaner.class);

	/**
	 * contains the automaton to be considered by the {@link EmptinessChecker}
	 */
	private final IntersectionBA<S, T> intersectionAutomaton;
	
	private final IntersectionBuilder<S, T> intersectionBuilder;

	/**
	 * contains the set of the states that has been encountered by <i>some<i>
	 * invocation of the first DFS
	 */
	private Set<S> reachableStates;

	/**
	 * contains the set of the visited states
	 */
	private Set<S> visitedStates;

	/**
	 * creates a new IntersectionCleaner
	 * 
	 * @param automaton
	 *            is the automaton to be considered
	 * @throws NullPointerException
	 *             if the automaton to be considered is null
	 */
	public IntersectionCleaner(IntersectionBA<S, T> automaton, IntersectionBuilder<S, T> intersectionBuilder) {
		Preconditions.checkNotNull(automaton,
				"The intersection automaton to be considered cannot be null");

		this.intersectionAutomaton = automaton;
		this.intersectionBuilder=intersectionBuilder;
		this.reachableStates = new HashSet<S>();
		visitedStates = new HashSet<S>();
	}

	/**
	 * returns true if the automaton is empty, i.e., when it does not exists an
	 * infinite path that contains an accepting run that can be accessed
	 * infinitely often, false otherwise
	 * 
	 * @return true if the automaton is empty, false otherwise
	 */
	public void clean() {

		logger.info("Sarting the cleaning phase");
		Set<S> toBeVisited = new HashSet<S>();
		toBeVisited.addAll(this.intersectionAutomaton.getAcceptStates());
		while (!toBeVisited.isEmpty()) {

			S currentState = toBeVisited.iterator().next();
			if (!visitedStates.contains(currentState)) {
				toBeVisited.addAll(this.intersectionAutomaton
						.getPredecessors(currentState));
				reachableStates.addAll(this.intersectionAutomaton
						.getPredecessors(currentState));

				visitedStates.add(currentState);
			}
			toBeVisited.remove(currentState);
		}
		
		
		Set<S> toBeRemoved = new HashSet<S>(
				this.intersectionAutomaton.getStates());
		toBeRemoved.removeAll(reachableStates);
		for (S s : toBeRemoved) {
			S modelState=this.intersectionBuilder.getIntersectionStateModelStateMap().get(s);
			this.intersectionBuilder.getModelStateIntersectionStateMap().get(modelState).remove(s);
			this.intersectionBuilder.getIntersectionStateModelStateMap().remove(s);
			S claimState=this.intersectionBuilder.getIntersectionStateClaimStateMap().get(s);
			this.intersectionBuilder.getClaimStateIntersectionStateMap().get(claimState).remove(s);
			this.intersectionBuilder.getIntersectionStateClaimStateMap().remove(s);
			
			this.intersectionAutomaton.removeState(s);
		}
		
		
		logger.info("The cleaning phase has removed: " + toBeRemoved.size()
				+ " states");
	}


}
