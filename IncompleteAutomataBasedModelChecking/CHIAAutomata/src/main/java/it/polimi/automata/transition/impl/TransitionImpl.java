package it.polimi.automata.transition.impl;

import it.polimi.automata.Constants;
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

	public void setLabels(Set<L> labels){
		this.labels=labels;
	}
	
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
	@Override
	public String toString() {
		String ret = "";
		for (L label : labels) {
			ret = ret + "("+label.toString() + ")"+Constants.OR;
		}
		if (ret.endsWith(Constants.OR)) {
			ret = ret.substring(0, ret.length() - Constants.OR.length());
		}

		return "{" + Integer.toString(this.id) + "} " + ret + "";
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	/** 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		@SuppressWarnings("unchecked")
		TransitionImpl<L> other = (TransitionImpl<L>) obj;
		if (id != other.id)
			return false;
		if (!labels.equals(other.labels))
			return false;
		return true;
	}	
}
