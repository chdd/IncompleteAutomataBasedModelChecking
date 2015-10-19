package it.polimi.automata.transition;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.jgrapht.graph.DefaultEdge;

import com.google.common.base.Preconditions;

import rwth.i2.ltl2ba4j.model.IGraphProposition;

/**
 * Represents a transition of an automaton. <br>
 * A transition is identified by an id and is labeled by propositions </p>
 * 
 * @author claudio menghi
 * @see {@link Transition}
 */
@SuppressWarnings("serial")
public class Transition extends DefaultEdge {

	/**
	 * contains the id of the transition
	 */
	private final int id;

	/**
	 * contains the labels of the transition the transition can be fired if one
	 * of the label is satisfied
	 */
	private final Set<IGraphProposition> labels;

	/**
	 * contains the id of the last transition created
	 */
	protected static int transition_counter = 1;

	
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
	 * @throws NullPointerException
	 *             if one of the label to be added is null
	 * @throws IllegalArgumentException
	 *             if one of the label is not made by lower case character or
	 *             the especial proposition SIGMA
	 */
	protected Transition(Set<IGraphProposition> labels, int id) {
		Preconditions.checkNotNull(labels,
				"The character that labels the transition cannot be null");
		Preconditions.checkArgument(id >= 0,
				"The value of the id cannot be less than zero");
		this.id = id;
		this.labels = new HashSet<IGraphProposition>();
		for (IGraphProposition l : labels) {
			this.labels.add(l);
		}
	}

	/**
	 * <p>
	 * returns <b>the propositions</b> associated with the transition. Depending
	 * on whether the transition is associated with the model or a property it
	 * is associated with a set of propositions or a propositional logic
	 * formula, respectively.
	 * </p>
	 * 
	 * @return the <b>label</b> associated with the transition
	 */
	public Set<IGraphProposition> getPropositions() {
		return Collections.unmodifiableSet(this.labels);
	}

	/**
	 * <p>
	 * returns the <b>id</b> of the transition
	 * </p>
	 * 
	 * @return the <b>id</b> of the transition
	 */
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
			ret = ret + label.toString() + PropositionalLogicConstants.AND;
		}
		if (ret.endsWith(PropositionalLogicConstants.AND)) {
			ret = ret.substring(0, ret.length()
					- PropositionalLogicConstants.AND.length());
		}

		return "{" + Integer.toString(this.id) + "} " + ret + "";
	}

	/**
	 * returns the hashCode of the current transition
	 * 
	 * @return the hashCode of the current transition
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	/**
	 * returns true if the transition is equal to the otherTransition passed as
	 * parameter
	 * 
	 * @param otherTransition
	 *            is the transition with which the current transition must be
	 *            compare with
	 * @return true if the two transitions are equals, false otherwise
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Transition other = (Transition) obj;
		if (id != other.id)
			return false;
		return true;
	}
}
