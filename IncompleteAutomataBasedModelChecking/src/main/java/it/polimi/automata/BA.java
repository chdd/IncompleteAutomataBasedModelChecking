package it.polimi.automata;

import java.util.Set;

import edu.uci.ics.jung.graph.DirectedSparseGraph;

/**
 * <p>
 * Represents a Buchi Automaton <br>
 * The state of the automaton must must implement the {@link State} interface <br>
 * The transition of the automaton must must implement the {@link Transition}
 * interface <br>
 * The label of the transition must implement the label interface and depending
 * on whether the automaton represents the model or the claim it is a set of
 * proposition or a propositional logic formula
 * </p>
 * 
 * @author claudiomenghi
 * @param <STATE>
 *            is the type of the state of the Buchi Automaton. The type of the
 *            states of the automaton must implement the interface {@link State}
 * @param <TRANSITION>
 *            is the type of the transition of the Buchi Automaton. The typer of
 *            the transitions of the automaton must implement the interface
 *            {@link Transition}
 * @param <LABEL>
 *            is the type of the label of the transitions depending on whether
 *            the automaton represents the model or the claim it is a set of
 *            proposition or a propositional logic formula {@link Label}
 */
public interface BA<LABEL extends Label, STATE extends State, TRANSITION extends Transition<LABEL>>
		extends Cloneable {

	/**
	 * returns the set of initial states of the Buchi automaton
	 * 
	 * @return the set of initial states of the Buchi automaton
	 */
	public Set<STATE> getInitialStates();

	/**
	 * returns the set of the states of the Buchi automaton
	 * 
	 * @return the set of the states of the Buchi automaton
	 */
	public Set<STATE> getStates();

	/**
	 * returns the set of accepting states of the Buchi automaton
	 * 
	 * @return set of the accepting states of the Buchi automaton
	 */
	public Set<STATE> getAcceptStates();

	/**
	 * returns the alphabet of the Buchi automaton
	 * 
	 * @return the alphabet of the Buchi automaton
	 */
	public Set<LABEL> getAlphabet();

	/**
	 * return the set of transitions that exits the state
	 * 
	 * @param state
	 *            is the state under analysis
	 * 
	 * @return the set of transitions that exits the state
	 * 
	 * @throws NullPointerException
	 *             if the state is null
	 * @throws IllegalArgumentException
	 *             if the state is not contained into the set of state of the
	 *             Buchi automaton
	 */
	public Set<TRANSITION> getOutTransitions(STATE state);

	/**
	 * returns the state that is the destination of the transition
	 * 
	 * @param transition
	 *            is the transition to be analyzed
	 * @return the state which is destination of the transition
	 * 
	 * @throws NullPointerException
	 *             if the transition is null
	 * @throws IllegalArgumentException
	 *             if the transition is not contained in the set of transitions
	 *             of the automaton
	 */
	public STATE getTransitionDestination(TRANSITION transition);

	/**
	 * adds the initial state to the Buchi automaton. If the state is not
	 * contained into the states of the automaton it is also added to the states
	 * of the automaton
	 * 
	 * @param state
	 *            the state to be added as initial state
	 * @throws NullPointerException
	 *             if the state is null
	 */
	public void addInitialState(STATE state);

	/**
	 * adds the accepting state to the Buchi automaton. If the state is not
	 * contained into the state of the automaton it is also added to the states
	 * of the automaton
	 * 
	 * @param state
	 *            the state to be added as accepting state
	 * @throws NullPointerException
	 *             if the state is null
	 */
	public void addAcceptState(STATE state);

	/**
	 * adds the state to the set of the state of the Buchi automaton. If the
	 * state is already present into the set of the states of the automaton no
	 * actions are performed
	 * 
	 * @param state
	 *            is the state to be added to the states of the Buchi automaton
	 * @throws NullPointerException
	 *             if the state to be added is null
	 */
	public void addState(STATE state);

	/**
	 * adds the set of the characters to the characters of the Buchi automaton.
	 * If a character is already contained in the set of characters of the Buchi
	 * automaton no actions are performed
	 * 
	 * @param characters
	 *            the characters to be added to the Buchi automaton
	 * @throws NullPointerException
	 *             is generated if the set of the of the characters to be added
	 *             to the Buchi automaton is null or if there exists a character
	 *             in the set which is null
	 */
	public void addCharacter(Set<LABEL> characters);

	/**
	 * add the transition t which connects the source and the destination state
	 * 
	 * @param source
	 *            is the source of the transition
	 * @param destination
	 *            is the destination of the transition
	 * @param transition
	 *            is the transition to be added
	 * @throws NullPointerException
	 *             if the source state the destination state or the transition
	 *             is null
	 * 
	 * @throws IllegalArgumentException
	 *             is generated in one of the following cases <br/>
	 *             the label of the transition is not contained in the alphabet
	 *             of the automaton <br/>
	 *             the source is not contained into the set of the states of the
	 *             automaton <br/>
	 *             the destination of the transition is not contained into the
	 *             set of the states of the automaton <br/>
	 */
	public void addTransition(STATE source, STATE destination,
			TRANSITION transition);

	/**
	 * removes the specified state from the set of the states of the Buchi
	 * automaton
	 * 
	 * @param state
	 *            is the state to be removed from the states of the Buchi
	 *            automaton
	 * @throws NullPointerException
	 *             if the state to be removed is null
	 * @throws IllegalArgumentException
	 *             if the state to be removed is not contained into the set of
	 *             the states of the Buchi automaton
	 */
	public void removeState(STATE state);

	/**
	 * removes the specified transitions from the set of transitions of the
	 * Buchi automaton
	 * 
	 * @param transition
	 *            is the transition to be removed from the transitions of the
	 *            Buchi automaton
	 * @throws NullPointerException
	 *             if the transition to be removed is null
	 * @throws IllegalArgumentException
	 *             if the transition to be removed is not contained into the set
	 *             of transitions of the automaton
	 */
	public void removeTransition(TRANSITION transition);

	/**
	 * removes the specified state from the set of the accepting states of the
	 * Buchi automaton the method does not remove the states from the states of
	 * the automaton
	 * 
	 * @param state
	 *            is the accepting state to be removed from the accepting states
	 *            of the Buchi automaton
	 * @throws NullPointerException
	 *             if the state to be removed is null
	 * @throws IllegalArgumentException
	 *             if the state to be removed is not contained into the set of
	 *             the accepting states of the Buchi automaton
	 */
	public void removeAcceptingState(STATE state);

	/**
	 * removes the specified state from the set of the initial states of the
	 * Buchi automaton the method does not remove the states from the states of
	 * the automaton
	 * 
	 * @param state
	 *            is the initial state to be removed from the initial states of
	 *            the Buchi automaton
	 * @throws NullPointerException
	 *             if the state to be removed is null
	 * @throws IllegalArgumentException
	 *             if the state to be removed is not contained into the set of
	 *             the initial states of the Buchi automaton
	 */
	public void removeInitialState(STATE state);

	/**
	 * returns the graph associated with the Buchi automaton
	 * 
	 * @return the graph associated with the Buchi automaton
	 */
	public DirectedSparseGraph<STATE, TRANSITION> getGraph();

}
