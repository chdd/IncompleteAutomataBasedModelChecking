package it.polimi.checker.emptiness;

import it.polimi.automata.BA;
import it.polimi.automata.state.State;
import it.polimi.automata.transition.Transition;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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

	private Stack<State> firstStack;
	private Stack<State> secondStack;

	private Stack<Transition> transitionStack;

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
		this.firstStack = new Stack<State>();
		this.transitionStack = new Stack<Transition>();
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
			this.firstStack = new Stack<State>();
			if (firstDFS(init, this.firstStack)) {
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
	private boolean firstDFS(State currState, Stack<State> firstDFSStack) {
		Preconditions.checkNotNull(currState,
				"The current state cannot be null");
		Preconditions.checkNotNull(firstDFSStack, "The stack cannot be null");

		this.hashedStates.add(currState);
		firstDFSStack.push(currState);

		for (Transition t : automaton.getOutTransitions(currState)) {
			this.transitionStack.push(t);
			State next = automaton.getTransitionDestination(t);
			if (!this.hashedStates.contains(next)) {
				if (this.firstDFS(next, firstDFSStack))
					return true;
			}
			this.transitionStack.pop();

		}
		if (this.automaton.getAcceptStates().contains(currState)) {
			secondStack = new Stack<State>();
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
	private boolean secondDFS(State currState, Stack<State> firstDFSStack) {
		Preconditions.checkNotNull(currState,
				"The current state cannot be null");
		Preconditions.checkNotNull(firstDFSStack,
				"The first stack cannot be null");
		secondStack.push(currState);
		this.flaggedStates.add(currState);
		for (Transition t : automaton.getOutTransitions(currState)) {
			this.transitionStack.push(t);
			State next = automaton.getTransitionDestination(t);
			if (firstDFSStack.contains(next)) {
				secondStack.push(next);

				return true;
			} else {
				if (!this.flaggedStates.contains(next)) {
					if (this.secondDFS(next, firstDFSStack))
						return true;
				}
			}
			this.transitionStack.pop();
		}
		secondStack.pop();

		return false;
	}

	public Stack<State> getCounterExample() {
		if(!this.firstStack.isEmpty()){
			this.firstStack.pop();
		}
		if(this.secondStack!=null){
			List<State> list = new ArrayList<State>(this.secondStack);

			for (State x : list) {
				this.firstStack.push(x);
			}

		}
		
		return firstStack;
	}

	public Stack<Transition> getTransitionCounterExample() {
		return this.transitionStack;
	}
}
