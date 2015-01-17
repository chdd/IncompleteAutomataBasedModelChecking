package it.polimi.automata.transition.impl;

import it.polimi.automata.labeling.Label;
import it.polimi.automata.transition.Transition;

import java.util.Collections;
import java.util.Set;

/**
 * contains the implementation of a transition an Incomplete or a complete Buchi automaton.
 * It implements the interface {@link Transition}
 * 
 * @author claudiomenghi 
 * @see {@link Transition}
 */
public class TransitionImpl<LABEL extends Label> implements Transition<LABEL>{

	/**
	 * contains the id of the transition
	 */
	private final int id;

	/**
	 * contains the labels of the transition
	 * the transition can be fired if one of the label is satisfied
	 */
	private Set<LABEL> labels;

	/**
	 * {@inheritDoc}
	 */
	public Set<LABEL> getLabels() {
		return  Collections.unmodifiableSet(this.labels);
	}

	/**
	 * {@inheritDoc}
	 */
	public int getId() {
		return id;
	}
	
	/**
	 * Constructs a new transition
	 * 
	 * @param label
	 *            is the label of the transition
	 * @param id
	 *            is the id of the transition
	 * @throws IllegalArgumentException
	 *             is generated if the id is less than zero
	 * @throws NullPointerException
	 *             is generated if the label of the transition is null
	 */
	protected TransitionImpl(Set<LABEL> label, int id) {
		if (id < 0) {
			throw new IllegalArgumentException(
					"The value of the id cannot be less than zero");
		}
		this.id = id;
		if (label == null) {
			throw new NullPointerException(
					"The character that labels the transition cannot be null");
		}
		this.labels = Collections.unmodifiableSet(label);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return "{" + Integer.toString(this.id) + "} "
				+ this.labels.toString() + "";
	}
}
