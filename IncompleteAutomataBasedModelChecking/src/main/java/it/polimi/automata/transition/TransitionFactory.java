package it.polimi.automata.transition;

import java.util.Set;

import it.polimi.automata.labeling.Label;

import org.apache.commons.collections15.Factory;

/**
 * contains the factory that is used to create the transition of a Buchi
 * automaton or of an incomplete Buchi automaton
 * 
 * @author claudiomenghi
 * @param LABEL
 *            is the label of a transition
 * @param TRANSITION
 *            is the type of the transition to be generated by the factory
 */
public interface TransitionFactory<LABEL extends Label, TRANSITION extends Transition<LABEL>>
		extends Factory<TRANSITION> {

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
	public TRANSITION create(Set<LABEL> labels);

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
	public TRANSITION create(int id, Set<LABEL> labels);
}