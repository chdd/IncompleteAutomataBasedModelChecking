package it.polimi.contraintcomputation.abstraction;

import it.polimi.automata.IntersectionBA;
import it.polimi.automata.labeling.Label;
import it.polimi.automata.state.State;
import it.polimi.automata.transition.Transition;

import java.util.HashSet;
import java.util.Set;

/**
 * The abstractor allows to generate a more concise version of the intersection
 * automaton where the portions of the state space which are not useful in the
 * constraint computation are removed. The state space of the intersection is
 * explored from its initial to its final states and:<br>
 * a) all the transitions that connect a mixed state q1 to a purely regular
 * state (which is not accepting) q2 are removed and replaced with transitions
 * that connect q1 with the successors of q2.<br>
 * b) all the transitions that connect a purely regular state q1 to a purely
 * regular state (which is not accepting) q2 are removed and replaced with
 * transitions that connect q1 with the successors of q2. <br>
 * c) all the transitions that do not allow to reach an accepting state of I are
 * removed. <br>
 * d) all the transitions that connect an accepting state q1 to a purely regular
 * state (which is not accepting) q2 are removed and replaced with transitions
 * that connect q1 with the successors of q2.
 * 
 * @author claudiomenghi
 * @param <STATE>
 *            is the type of the state of the Intersection Buchi Automaton. The
 *            type of the states of the automaton must implement the interface
 *            {@link State}
 * @param <T>
 *            is the type of the transition of the Intersection Buchi Automaton.
 *            The typer of the transitions of the automaton must implement the
 *            interface {@link Transition}
 * @param <L>
 *            is the type of the label of the transitions depending on whether
 *            the automaton represents the model or the claim it is a set of
 *            proposition or a propositional logic formula {@link Label}
 */
public class Abstractor<L extends Label, STATE extends State, T extends Transition<L>> {

	/**
	 * contains the intersection automaton to be simplified
	 */
	private IntersectionBA<L, STATE, T> intBA;

	/**
	 * contains the set of already visited states
	 */
	private Set<STATE> abstractedStates;

	/**
	 * creates a new Abstractor
	 * 
	 * @param intBA
	 *            is the intersection automaton to be considered
	 * @throws NullPointerException
	 *             if the intersection automaton or the factory is null
	 */
	public Abstractor(IntersectionBA<L, STATE, T> intBA) {
		if (intBA == null) {
			throw new NullPointerException(
					"The intersection automaton cannot be null");
		}
		
		this.intBA = intBA.clone();
		this.abstractedStates = new HashSet<STATE>();
	}

	/**
	 * The abstractor allows to generate a more concise version of the
	 * intersection automaton where the portions of the state space which are
	 * not useful in the constraint computation are removed. The state space of
	 * the intersection is explored from its initial to its final states and: <br>
	 * a) all the transitions that connect a mixed state q1 to a purely regular
	 * state (which is not accepting) q2 are removed and replaced with
	 * transitions that connect q1 with the successors of q2.<br>
	 * b) all the transitions that connect a purely regular state q1 to a purely
	 * regular state (which is not accepting) q2 are removed and replaced with
	 * transitions that connect q1 with the successors of q2. <br>
	 * c) all the transitions that do not allow to reach an accepting state of I
	 * are removed. <br>
	 * d) all the transitions that connect an accepting state q1 to a purely
	 * regular state (which is not accepting) q2 are removed and replaced with
	 * transitions that connect q1 with the successors of q2.
	 * 
	 * @return the abstracted version of the state space
	 */
	public IntersectionBA<L, STATE, T> abstractIntersection() {

		for (STATE s : this.intBA.getInitialStates()) {
			this.abstractStateSpace(s);
		}
		return this.intBA;
	}

	/**
	 * abstract the state space of the automaton, by removing the non mixed
	 * states
	 * 
	 * @param currState
	 *            is the current state to be analyzed
	 */
	private void abstractStateSpace(STATE currState) {
		// if the currState has already been abstracted
		if (this.abstractedStates.contains(currState)) {
			return;
		}
		// get the out transitions
		for (T successorTransition : this.intBA
				.getOutTransitions(currState)) {
			// get the next state of the Buchi automaton
			STATE nextState = this.intBA
					.getTransitionDestination(successorTransition);
			// if the next state of the Buchi automaton is mixed
			if (this.intBA.getMixedStates().contains(nextState)) {
				// add the state to the set of the already visited states
				this.abstractedStates.add(currState);
				// calls the abstract state space method over the next state
				this.abstractStateSpace(nextState);
			} else {
				// contains the successors of the next state
				Set<STATE> nextStates = new HashSet<STATE>();
				// for each transition that follow the nextState
				for (T followingTransition : this.intBA
						.getOutTransitions(nextState)) {
					// contains the state that follow the nextState
					STATE nextNextState = this.intBA
							.getTransitionDestination(followingTransition);
					// add the nextNextState to the set of states to be visited
					// next
					nextStates.add(nextNextState);
					// add a transition from the currState to the nextNextState
					this.intBA.addTransition(currState, nextNextState,
							successorTransition);

				}
				// removes the successorTransition
				this.intBA.removeTransition(successorTransition);
				// if the nextState does not have any incoming transition and is
				// not initial
				if (this.intBA.getInTransitions(nextState).isEmpty()
						&& !this.intBA.getInitialStates().contains(nextState)) {
					// the state is removed
					this.intBA.removeState(nextState);
				}
				// for each state s in the nextStates
				for (STATE s : nextStates) {
					// procedes with the abstraction procedure
					this.abstractStateSpace(s);
				}
			}
		}
	}
}
