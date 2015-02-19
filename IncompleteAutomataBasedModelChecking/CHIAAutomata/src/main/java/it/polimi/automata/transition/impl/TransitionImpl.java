package it.polimi.automata.transition.impl;

import it.polimi.automata.Constants;
import it.polimi.automata.transition.Transition;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import rwth.i2.ltl2ba4j.model.IGraphProposition;

/**
 * contains the implementation of a transition an Incomplete or a complete Buchi
 * automaton. It implements the interface {@link Transition}
 * 
 * @author claudiomenghi
 * @see {@link Transition}
 */
public class TransitionImpl implements Transition {

	/**
	 * contains the id of the transition
	 */
	private final int id;

	/**
	 * contains the labels of the transition the transition can be fired if one
	 * of the label is satisfied
	 */
	private Set<IGraphProposition> labels;

	/**
	 * Creates a new transition
	 * 
	 * @param labels
	 *            the <code>Set</code> of labels to be added to the transition
	 * @param id
	 *            : the identifier of the transition
	 * @throws IllegalArgumentException
	 *             if the id is less than zero
	 * @throws NullPointerException
	 *             if the set of labels or one of the label to be added is null
	 */
	protected TransitionImpl(Set<IGraphProposition> labels, int id) {
		if (id < 0)
			throw new IllegalArgumentException(
					"The value of the id cannot be less than zero");
		if (labels == null)
			throw new NullPointerException(
					"The character that labels the transition cannot be null");
		this.id = id;
		this.labels = new HashSet<IGraphProposition>();
		for (IGraphProposition l : labels) {
			if (l == null) {
				throw new NullPointerException(
						"No null labels can be added to the transition");
			}
			this.labels.add(l);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public void setLabels(Set<IGraphProposition> labels) {
		if (labels == null) {
			throw new NullPointerException(
					"It is not possible to set a null set of labels");
		}
		this.labels = new HashSet<IGraphProposition>();
		for (IGraphProposition l : labels) {
			if (l == null) {
				throw new NullPointerException(
						"The set of the labels cannot contain null labels");
			}
			this.labels.add(l);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Set<IGraphProposition> getLabels() {
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
		for (IGraphProposition label : labels) {
			ret = ret + "(" + label.toString() + ")" + Constants.AND;
		}
		if (ret.endsWith(Constants.AND)) {
			ret = ret.substring(0, ret.length() - Constants.AND.length());
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
		TransitionImpl other = (TransitionImpl) obj;
		if (id != other.id)
			return false;
		if (!labels.equals(other.labels))
			return false;
		return true;
	}
}
