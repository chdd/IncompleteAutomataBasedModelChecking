package it.polimi.checker.emptiness;

import it.polimi.automata.BA;
import it.polimi.automata.state.State;
import it.polimi.automata.transition.Transition;

import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

import com.google.common.base.Preconditions;


/**
 * Checks the emptiness of an automaton. The automaton must extend the Buchi
 * Automaton Interface. An automaton is empty when it does not exists an
 * infinite path that contains an accepting state of the automaton infinitely
 * often.<br>
 * For more information about the emptiness checker the reader can consult the
 * book Model checking, of Clark, Grumberg and Peled pag 130
 * 
 * @see {@link BA}
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
public class EmptinessChecker<S extends State, T extends Transition> {

	/**
	 * contains the automaton to be considered by the {@link EmptinessChecker}
	 */
	private BA<S, T> automaton;

	/**
	 * contains the set of the states that has been encountered by <i>some<i>
	 * invocation of the first DFS
	 */
	private Set<S> hashedStates;

	/**
	 * contains the set of the states that has been encountered by <i>some<i>
	 * invocation of the second DFS
	 */
	private Set<S> flaggedStates;

	/**
	 * creates a new Emptiness checker
	 * 
	 * @param automaton
	 *            is the automaton to be considered
	 * @throws NullPointerException
	 *             if the automaton to be considered is null
	 */
	public EmptinessChecker(BA<S, T> automaton) {
		Preconditions.checkNotNull(automaton, "The automaton to be considered cannot be null");

		this.automaton = automaton;
		this.hashedStates = new HashSet<S>();
		this.flaggedStates = new HashSet<S>();
	}

	/**
	 * returns true if the automaton is empty, i.e., when it does not exists an
	 * infinite path that contains an accepting run that can be accessed
	 * infinitely often, false otherwise
	 * 
	 * @return true if the automaton is empty, false otherwise
	 */
	public boolean isEmpty() {

		for (S init : this.automaton.getInitialStates()) {
			if (firstDFS(init,  new Stack<S>())) {
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
	 * @return true if an accepting path is found, false otherwise
	 * @throws NullPointerException
	 *             if one of the parameters is null
	 */
	private boolean firstDFS(S currState, 
			Stack<S> firstDFSStack) {
		Preconditions.checkNotNull(currState, "The current state cannot be null");
		Preconditions.checkNotNull(firstDFSStack, "The stack cannot be null");
		
		this.hashedStates.add(currState);
		firstDFSStack.push(currState);
		for (S t : automaton.getSuccessors(currState)) {
			if (!this.hashedStates.contains(t)) {
				if (this.firstDFS(t, firstDFSStack))
					return true;
			}
		}
		if (this.automaton.getAcceptStates().contains(currState)) {
			if (this.secondDFS(currState, firstDFSStack)) {
				return true;
			}
		}
		firstDFSStack.pop();
		return false;
	}

	/**
	 * returns true if an accepting path is found
	 * 
	 * @param currState
	 *            is the current states under analysis
	 * @return true if an accepting path is found, false otherwise
	 * @throws NullPointerException
	 *             if the current state, the graph or the stack is null
	 */
	private boolean secondDFS(S currState, 
			Stack<S> firstDFSStack) {
		Preconditions.checkNotNull(currState, "The current state cannot be null");
		Preconditions.checkNotNull(firstDFSStack, "The first stack cannot be null");
		
		this.flaggedStates.add(currState);
		for (S t : this.automaton.getSuccessors(currState)) {
			if (firstDFSStack.contains(t)) {
				return true;
			} else {
				if (!this.flaggedStates.contains(t)) {
					if (this.secondDFS(t, firstDFSStack))
						return true;
				}
			}
		}
		return false;
	}
}
