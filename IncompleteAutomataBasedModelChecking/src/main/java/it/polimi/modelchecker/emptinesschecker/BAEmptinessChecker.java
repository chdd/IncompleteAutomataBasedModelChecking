package it.polimi.modelchecker.emptinesschecker;

import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

import it.polimi.model.impl.states.State;
import it.polimi.model.impl.transitions.Transition;
import it.polimi.model.interfaces.automata.BA;

public class BAEmptinessChecker<STATE extends State, TRANSITION extends Transition, AUTOMATON extends BA<STATE, TRANSITION>> {

	/**
	 * contains the automaton to be considered by the {@link BAEmptinessChecker}
	 */
	protected AUTOMATON automaton;
	
	protected Stack<STATE> stack;
	protected Stack<TRANSITION> stacktransitions;


	/**
	 * creates a new {@link BAEmptinessChecker}
	 * @param automaton is the automaton to be considered
	 * @throws NullPointerException if the automaton to be considered is null
	 */
	public BAEmptinessChecker(AUTOMATON automaton) {
		if(automaton==null){
			throw new NullPointerException("The automaton to be considered cannot be null");
		}
		this.automaton=automaton;
	}
	
	/**
	 * returns true if the automaton is empty
	 * 
	 * @return true if the automaton is empty
	 */
	public boolean isEmpty() {
		boolean res = true;
		Set<STATE> visitedStates = new HashSet<STATE>();
		this.stacktransitions = new Stack<TRANSITION>();
		for (STATE init : this.automaton.getInitialStates()) {
			stack = new Stack<STATE>();
			if (firstDFS(visitedStates, init, stack)) {

				return false;
			}
		}
		// clear the set of the visited states
		visitedStates.clear();
		return res;
	}

	/**
	 * returns true if an accepting path is found
	 * 
	 * @param visitedStates
	 *            contains the set of the visited states by the algorithm
	 * @param currState
	 *            is the current states under analysis
	 * @return true if an accepting path is found, false otherwise
	 */
	protected boolean firstDFS(Set<STATE> visitedStates, STATE currState,
			Stack<STATE> statesOfThePath) {
		// if the current state have been already visited (and the second DFS
		// has not been started) it means that the path is not accepting
		if (visitedStates.contains(currState)) {
			return false;
		} else {
			// I add the state in the set of visited states
			visitedStates.add(currState);
			// I add the state in the state of the path
			statesOfThePath.push(currState);
			// if the state is accepting
			if (this.automaton.isAccept(currState)) {
				for (TRANSITION t : this.automaton.getOutTransitions(currState)) {

					this.stacktransitions.add(t);
					Stack<STATE> stackSecondDFS = new Stack<STATE>();
					// I start the second DFS if the answer of the second DFS is
					// true I return true
					if (this.secondDFS(new HashSet<STATE>(),
							this.automaton.getTransitionDestination(t), statesOfThePath,
							stackSecondDFS)) {
						statesOfThePath.addAll(stackSecondDFS);
						return true;
					}
					this.stacktransitions.remove(t);
				}
			}
			// otherwise, I check each transition that leaves the state
			// currState
			for (TRANSITION t : this.automaton.getOutTransitions(currState)) {
				this.stacktransitions.add(t);
				// I call the first DFS method, If the answer is true I return
				// true
				if (firstDFS(visitedStates, this.automaton.getTransitionDestination(t),
						statesOfThePath)) {
					return true;
				}
				this.stacktransitions.remove(t);

			}
			// I remove the state from the stack of the states of the current
			// path
			statesOfThePath.pop();
			return false;
		}
	}

	/**
	 * contains the second DFS procedure
	 * 
	 * @param visitedStates
	 *            contains the set of the states visited in the SECOND DFS
	 *            procedure
	 * @param currState
	 *            is the current state under analysis
	 * @param statesOfThePath
	 *            is the state of the path that is currently analyzed
	 * @return true if an accepting path is found (a path that contains a state
	 *         in the set of the states statesOfThePath), false otherwise
	 */
	// note that at the beginning the visited states do not contain the current
	// state
	protected boolean secondDFS(Set<STATE> visitedStates, STATE currState,
			Stack<STATE> statesOfThePath, Stack<STATE> stackSecondDFS) {
		// if the state is in the set of the states on the path the an accepting
		// path is found
		if (statesOfThePath.contains(currState)) {
			return true;
		} else {
			// if the state is in the set of the visited states of the second
			// DFS, the path is not accepting
			if (visitedStates.contains(currState)) {
				return false;
			} else {
				stackSecondDFS.push(currState);
				// add the state into the set of the visited states
				visitedStates.add(currState);
				// for each transition that leaves the current state
				for (TRANSITION t : this.automaton.getOutTransitions(currState)) {

					this.stacktransitions.add(t);
					// if the second DFS returns a true answer than the
					// accepting path has been found
					if (secondDFS(visitedStates, this.automaton.getTransitionDestination(t),
							statesOfThePath, stackSecondDFS)) {
						return true;
					}
					this.stacktransitions.remove(t);
				}
				stackSecondDFS.pop();
				// otherwise the path is not accepting
				return false;
			}
		}
	}
}
