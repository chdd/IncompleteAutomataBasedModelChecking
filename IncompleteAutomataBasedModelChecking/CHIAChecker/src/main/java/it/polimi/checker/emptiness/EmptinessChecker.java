package it.polimi.checker.emptiness;

import it.polimi.automata.BA;
import it.polimi.automata.state.State;
import it.polimi.automata.transition.Transition;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;
import java.util.Stack;

import com.google.common.base.Preconditions;

/**
 * Checks the emptiness of a BA automaton. An automaton is empty when it does
 * not exists an infinite run that contains an accepting state of the automaton
 * which is entered infinitely often.<br>
 * 
 * @see {@link BA}
 * 
 * @author Claudio Menghi
 */
public class EmptinessChecker {

	/**
	 * contains the automaton to be considered by the {@link EmptinessChecker}
	 */
	private final BA automaton;

	/**
	 * contains the set of the states that has been encountered by <i>some<i>
	 * invocation of the first DFS
	 */
	private final Set<State> hashedStates;

	/**
	 * contains the set of the states that has been encountered by <i>some<i>
	 * invocation of the second DFS
	 */
	private final Set<State> flaggedStates;

	/**
	 * contains the set of couple state, next transition that allow reaching an
	 * accepting states
	 */
	private Stack<Entry<State, Transition>> firstStack;

	/**
	 * The first stack used in the emptiness checking
	 */
	private Stack<State> stack1;

	/**
	 * The second stack used in the emptiness checking
	 */
	private Stack<State> stack2;

	/**
	 * contains the set of couple state, next transition that allow looping over
	 * an accepting states
	 */
	private Stack<Entry<State, Transition>> secondStack;

	/**
	 * contains a flag that indicates whether a counterexample has been founded
	 * or not
	 */
	private boolean counterexampleFounded;

	/**
	 * creates a new Emptiness checker
	 * 
	 * @param automaton
	 *            is the automaton to be considered
	 * @throws NullPointerException
	 *             if the automaton to be considered is null
	 */
	public EmptinessChecker(BA automaton) {
		Preconditions.checkNotNull(automaton,
				"The automaton to be considered cannot be null");

		this.automaton = automaton;
		this.hashedStates = new HashSet<State>();
		this.flaggedStates = new HashSet<State>();
		this.firstStack = new Stack<Entry<State, Transition>>();
		this.stack1 = new Stack<State>();
		this.stack2 = new Stack<State>();
		this.counterexampleFounded = false;
	}

	/**
	 * returns true if the automaton is empty, i.e., when it does not exists an
	 * infinite path that contains an accepting state that can be accessed
	 * infinitely often, false otherwise
	 * 
	 * @return true if the automaton is empty, false otherwise
	 */
	public boolean isEmpty() {

		for (State init : this.automaton.getInitialStates()) {
			this.firstStack = new Stack<Entry<State, Transition>>();
			if (!firstDFS(init, this.firstStack)) {
				this.counterexampleFounded = true;
				return false;
			}
		}
		return true;
	}

	/**
	 * returns true if an accepting path is found
	 * 
	 * @param currState
	 *            is the current states under analysis
	 * @param firstDFSStack
	 *            is the first DFS stack
	 * @return true if an accepting path is found, false otherwise
	 * @throws NullPointerException
	 *             if one of the parameters is null
	 */
	private boolean firstDFS(State currState,
			Stack<Entry<State, Transition>> firstDFSStack) {
		Preconditions.checkNotNull(currState,
				"The current state cannot be null");
		Preconditions.checkNotNull(firstDFSStack, "The stack cannot be null");

		this.hashedStates.add(currState);
		this.stack1.push(currState);

		for (Transition t : automaton.getOutTransitions(currState)) {
			firstDFSStack.push(new AbstractMap.SimpleEntry<State, Transition>(
					currState, t));
			State next = automaton.getTransitionDestination(t);
			if (!this.hashedStates.contains(next)) {
				if (!this.firstDFS(next, firstDFSStack))
					return false;
			}
			firstDFSStack.pop();

		}

		if (this.automaton.getAcceptStates().contains(currState)) {
			secondStack = new Stack<Entry<State, Transition>>();
			if (!this.secondDFS(currState, firstDFSStack)) {
				return false;
			}
		}
		this.stack1.pop();
		return true;
	}

	/**
	 * returns true if an accepting path is found
	 * 
	 * @param currState
	 *            is the current states under analysis
	 * @param firstDFSStack
	 *            is the first DFS stack
	 * @return true if an accepting path is found, false otherwise
	 * @throws NullPointerException
	 *             if the current state, the graph or the stack is null
	 */
	private boolean secondDFS(State currState,
			Stack<Entry<State, Transition>> firstDFSStack) {
		Preconditions.checkNotNull(currState,
				"The current state cannot be null");
		Preconditions.checkNotNull(firstDFSStack,
				"The first stack cannot be null");

		this.stack2.push(currState);

		this.flaggedStates.add(currState);

		for (Transition t : automaton.getOutTransitions(currState)) {

			State next = automaton.getTransitionDestination(t);

			if (this.stack1.contains(next)) {
				secondStack
						.push(new AbstractMap.SimpleEntry<State, Transition>(
								currState, t));
				return false;
			} else {
				secondStack
						.push(new AbstractMap.SimpleEntry<State, Transition>(
								currState, t));
				if (!this.flaggedStates.contains(next)
						&& !this.secondDFS(next, firstDFSStack)) {
					return false;

				}
				secondStack.pop();
			}
		}

		this.stack2.pop();

		return true;
	}

	/**
	 * returns a stack of states and transitions that contains the
	 * counterexample It must be used after the method is empty is run
	 * 
	 * @return a stack of states and transitions that contains the
	 *         counterexample
	 */
	public List<Entry<State, Transition>> getCounterExample() {

		if (!counterexampleFounded) {
			throw new InternalError(
					"A counterexample has not been founder or you must run the emptiness checker before getting the counterexample");
		}
		List<Entry<State, Transition>> counterexample = new ArrayList<Entry<State, Transition>>();
		counterexample.addAll(firstStack);
		counterexample.addAll(secondStack);
		return counterexample;
	}
}
