package it.polimi.automata.transition.impl;

import it.polimi.automata.labeling.Label;
import it.polimi.automata.transition.Transition;

import java.util.Collections;
import java.util.Set;

/**
 * contains the implementation of a transition an Incomplete or a complete Buchi
 * automaton. It implements the interface {@link Transition}
 * 
 * @author claudiomenghi
 * @see {@link Transition}
 */
public class TransitionImpl<L extends Label> implements Transition<L> {

	/**
	 * contains the id of the transition
	 */
	private final int id;

	/**
	 * contains the labels of the transition the transition can be fired if one
	 * of the label is satisfied
	 */
	private Set<L> labels;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Set<L> getLabels() {
		return Collections.unmodifiableSet(this.labels);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getId() {
		return id;
	}

	/**
	 * {@inheritDoc}
	 */
	protected TransitionImpl(Set<L> label, int id) {
		if (id < 0)
			throw new IllegalArgumentException(
					"The value of the id cannot be less than zero");
		if (label == null)
			throw new NullPointerException(
					"The character that labels the transition cannot be null");

		this.id = id;
		this.labels = Collections.unmodifiableSet(label);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return "{" + Integer.toString(this.id) + "} " + this.labels.toString()
				+ "";
	}
}
