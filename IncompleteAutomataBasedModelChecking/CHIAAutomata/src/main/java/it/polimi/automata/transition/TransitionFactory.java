package it.polimi.automata.transition;

import it.polimi.automata.state.State;

import java.util.Set;

import org.jgrapht.EdgeFactory;

import rwth.i2.ltl2ba4j.model.IGraphProposition;

/**
 * contains the factory that is used to create the transition of a Buchi
 * automaton or of an incomplete Buchi automaton
 * 
 * @author claudiomenghi
 * @param S
 *            is the type of the state of the automaton
 * @param T
 *            is the type of the transitions of the automaton
 */
public interface TransitionFactory<S extends State, T extends Transition>
		extends EdgeFactory<State, Transition> {

	/**
	 * creates a new transition with an auto-assigned id and an empty label set
	 * 
	 * @return a new transition with an auto-assigned id and an empty label set
	 */
	public T create();

	/**
	 * creates a new transition labeled with the specified condition
	 * 
	 * @param labels
	 *            are the labels of the transitions. If one of the labels is
	 *            satisfied the transition may be fired
	 * @return a new transition labeled with the specified condition
	 * @throws NullPointerException
	 *             if the label is null
	 */
	public T create(Set<IGraphProposition> labels);

	/**
	 * creates a new transition with the specified condition and id
	 * 
	 * @param labels
	 *            are the labels of the transitions. If one of the labels is
	 *            satisfied the transition may be fired
	 * @param id
	 *            is the id of the transition
	 * @return a new transition with the specified id and condition as label
	 * @throws NullPointerException
	 *             if the condition is null
	 * @throws IllegalArgumentException
	 *             if the id is not grater than or equal to zero
	 */
	public T create(int id, Set<IGraphProposition> labels);
}