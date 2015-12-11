package it.polimi.automata.labeling.impl;

import it.polimi.automata.Constants;
import it.polimi.automata.labeling.Label;

import java.util.Collections;
import java.util.Set;

import rwth.i2.ltl2ba4j.model.IGraphProposition;

/**
 * <p>
 * Represents the label of a transition of the claim automaton. It corresponds
 * to the set of characters of the atomic propositions to be satisfied for the
 * transition to be performed. The proposition may be negated or not and are
 * specifies through the interface IGraphProposition.
 * 
 * @see Label </p>
 * 
 * @author claudiomenghi
 */
public class LabelImpl implements Label {
	/**
	 * contains the labels that label the transitions to be performed all the
	 * conditions of the Label must be satisfied
	 */
	private Set<IGraphProposition> labels;

	/**
	 * creates a new label for the transition with the specified set of
	 * propositions
	 * 
	 * @param labels
	 *            is the set of propositions that label the transitions
	 * @throws NullPointerException
	 *             if the set of propositions is null
	 */
	protected LabelImpl(Set<IGraphProposition> labels) {
		if (labels == null)
			throw new NullPointerException(
					"The set of propositions that label the transitions cannot be null");

		this.labels = Collections.unmodifiableSet(labels);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Set<IGraphProposition> getLabels() {
		return labels;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		String ret="";
		for(IGraphProposition label: labels){
			ret=ret+label.toString()+Constants.AND;
		}
		if(ret.endsWith(Constants.AND)){
			ret=ret.substring(0, ret.length()-Constants.AND.length());
		}
		return ret;
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + labels.hashCode();
		return result;
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		LabelImpl other = (LabelImpl) obj;
		if (!labels.equals(other.labels))
			return false;
		return true;
	}
}