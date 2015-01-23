package it.polimi.checker.emptiness;

import it.polimi.automata.BA;
import it.polimi.automata.labeling.Label;
import it.polimi.automata.state.State;
import it.polimi.automata.transition.Transition;

import java.util.HashSet;
import java.util.Set;

import edu.uci.ics.jung.graph.DirectedSparseGraph;

/**
 * Checks the emptiness of an automaton. The automaton must extend the Buchi
 * Automaton Interface. An automaton is empty when it does not exists an
 * infinite path that enters an accepting state of the automaton infinitely
 * often.<br>
 * For an informal description of the algorithm see the book Model checking,
 * of Clark, Grumberg and Peled pag 130
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
 * @param <L>
 *            is the type of the label of the transitions depending on whether
 *            the automaton represents the model or the claim it is a set of
 *            proposition or a propositional logic formula {@link Label}
 */
public class EmptinessChecker<L extends Label, S extends State, T extends Transition<L>> {

	/**
	 * contains the automaton to be considered by the {@link EmptinessChecker}
	 */
	private BA<L, S, T> automaton;
	

	/**
	 * contains the set of the states that has been encountered by <i>some<i> invocation of the first DFS
	 */
	private Set<S> hashedStates;

	/**
	 * contains the set of the states that has been encountered by <i>some<i> invocation of the second DFS
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
	public EmptinessChecker(BA<L, S, T> automaton) {
		if (automaton == null) {
			throw new NullPointerException(
					"The automaton to be considered cannot be null");
		}
		this.automaton = automaton;
		this.hashedStates=new HashSet<S>();
		this.flaggedStates=new HashSet<S>();
	}

	/**
	 * returns true if the automaton is empty, i.e., when it does not exists an
	 * infinite path that contains an accepting run that can be accessed
	 * infinitely often, false otherwise
	 * 
	 * @return true if the automaton is empty, false otherwise
	 */
	public boolean isEmpty() {
		
		DirectedSparseGraph<S, T> graph=this.automaton.getGraph();
		boolean res = true;
		for (S init : this.automaton.getInitialStates()) {
			if (firstDFS(init,graph)) {
				return false;
			}
		}
		return res;
	}

	/**
	 * returns true if an accepting path is found
	 * 
	 * @param currState
	 *            is the current states under analysis
	 * @return true if an accepting path is found, false otherwise
	 */
	protected boolean firstDFS(S currState, DirectedSparseGraph<S, T> graph) {
		
			this.hashedStates.add(currState);
			for (S t : graph.getSuccessors(currState)) {
				if(!this.hashedStates.contains(t)){
					this.firstDFS(t, graph);
				}
			}
			if(this.automaton.getAcceptStates().contains(currState)){
				if(this.secondDFS(currState, graph)){
					return true;
				}
			}
			return false;
	}

	/**
	 * returns true if an accepting path is found
	 * 
	 * @param currState
	 *            is the current states under analysis
	 * @return true if an accepting path is found, false otherwise
	 */
	protected boolean secondDFS(S currState, DirectedSparseGraph<S, T> graph) {
		this.flaggedStates.add(currState);
		for (S t : graph.getSuccessors(currState)) {
			if(this.hashedStates.contains(t)){
				return true;
			}
			else{
				if(!this.flaggedStates.contains(currState)){
					this.secondDFS(t, graph);
				}
			}
		}
		return false;
	}
}
