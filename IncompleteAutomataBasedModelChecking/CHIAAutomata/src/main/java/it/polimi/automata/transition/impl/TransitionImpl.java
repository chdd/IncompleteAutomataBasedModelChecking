package it.polimi.automata.transition.impl;

import it.polimi.automata.Constants;
import it.polimi.automata.transition.Transition;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.jgrapht.graph.DefaultEdge;

import com.google.common.base.Preconditions;

import rwth.i2.ltl2ba4j.model.IGraphProposition;

/**
 * contains the implementation of a transition an Incomplete or a complete Buchi
 * automaton. It implements the interface {@link Transition}
 * 
 * @author claudiomenghi
 * @see {@link Transition}
 */
@SuppressWarnings("serial")
public class TransitionImpl extends DefaultEdge implements Transition {

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
		Preconditions.checkNotNull(labels,
				"The character that labels the transition cannot be null");
		Preconditions.checkArgument(id >= 0,
				"The value of the id cannot be less than zero");
		this.id = id;
		this.labels = new HashSet<IGraphProposition>();
		for (IGraphProposition l : labels) {
			Preconditions.checkNotNull(l,
					"No null labels can be added to the transition");
			this.labels.add(l);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public void setLabels(Set<IGraphProposition> labels) {
		Preconditions.checkNotNull(labels,
				"It is not possible to set a null set of labels");

		this.labels = new HashSet<IGraphProposition>();
		for (IGraphProposition l : labels) {
			Preconditions.checkNotNull(l == null,
					"The set of the labels cannot contain null labels");

			this.labels.add(l);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Set<IGraphProposition> getPropositions() {
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
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
		return true;
	}
}
