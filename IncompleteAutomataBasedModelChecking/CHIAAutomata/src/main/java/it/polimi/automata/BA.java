package it.polimi.automata;

import it.polimi.automata.labeling.Label;
import it.polimi.automata.state.State;
import it.polimi.automata.transition.Transition;

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
public interface BA<L extends Label, S extends State, T extends Transition<L>>
		extends Cloneable {

	/**
	 * returns the set of initial states of the Buchi automaton
	 * 
	 * @return the set of initial states of the Buchi automaton
	 */
	public Set<S> getInitialStates();

	/**
	 * returns the set of the states of the Buchi automaton
	 * 
	 * @return the set of the states of the Buchi automaton
	 */
	public Set<S> getStates();

	/**
	 * returns the set of accepting states of the Buchi automaton
	 * 
	 * @return set of the accepting states of the Buchi automaton
	 */
	public Set<S> getAcceptStates();

	/**
	 * returns the alphabet of the Buchi automaton
	 * 
	 * @return the alphabet of the Buchi automaton
	 */
	public Set<L> getAlphabet();

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
	public Set<T> getOutTransitions(S state);

	/**
	 * return the set of transitions that enters the state
	 * 
	 * @param state
	 *            is the state under analysis
	 * 
	 * @return the set of transitions that enter the state
	 * 
	 * @throws NullPointerException
	 *             if the state is null
	 * @throws IllegalArgumentException
	 *             if the state is not contained into the set of state of the
	 *             Buchi automaton
	 */
	public Set<T> getInTransitions(S state);

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
	public S getTransitionDestination(T transition);

	/**
	 * returns the state that is the source of the transition
	 * 
	 * @param transition
	 *            is the transition to be analyzed
	 * @return the state which is the source of the transition
	 * 
	 * @throws NullPointerException
	 *             if the transition is null
	 * @throws IllegalArgumentException
	 *             if the transition is not contained in the set of transitions
	 *             of the automaton
	 */
	public S getTransitionSource(T transition);

	/**
	 * returns the set of the transitions of the Buchi automaton
	 * 
	 * @return the set of the transitions of the Buchi automaton
	 */
	public Set<T> getTransitions();

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
	public void addInitialState(S state);

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
	public void addAcceptState(S state);

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
	public void addState(S state);

	/**
	 * adds the character to the characters of the Buchi automaton. If a
	 * character is already contained in the set of characters of the Buchi
	 * automaton no actions are performed
	 * 
	 * @param character
	 *            the character to be added to the Buchi automaton
	 * @throws NullPointerException
	 *             is generated if the character to be added is null
	 */
	public void addCharacter(L character);

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
	 *             a transition that connect source to the destination is
	 *             already present
	 */
	public void addTransition(S source, S destination,
			T transition);

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
	 * 
	 * <pre>
	 * state != null
	 * </pre>
	 */
	public void removeState(S state);

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
	public void removeTransition(T transition);

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
	public void removeAcceptingState(S state);

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
	public void removeInitialState(S state);

	/**
	 * returns the graph associated with the Buchi automaton
	 * 
	 * @return the graph associated with the Buchi automaton
	 */
	public DirectedSparseGraph<S, T> getGraph();

}
