package it.polimi.automata.labeling.impl;

import it.polimi.automata.labeling.ModelLabel;

import java.util.Collections;
import java.util.Set;

/**
 * <p>
 * Represents the label of a transition of the model of the automaton. It
 * corresponds to the set of characters of the atomic propositions to be
 * satisfied for the transition to be performed
 * 
 * @see ModelLabel </p>
 * 
 * @author claudiomenghi
 */
public class ModelLabelImple implements ModelLabel {

	/**
	 * contains the set of propositions that label the transitions
	 */
	public Set<String> propositions;

	/**
	 * creates a new label for the transition with the specified set of
	 * propositions
	 * 
	 * @param propositions
	 *            is the set of propositions that label the transitions
	 * @throws NullPointerException
	 *             if the set of propositions is null
	 */
	public ModelLabelImple(Set<String> propositions) {
		if(propositions==null){
			throw new NullPointerException("The set of propositions that label the transitions cannot be null");
		}
		this.propositions=Collections.unmodifiableSet(propositions);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Set<String> getAtomicPropositions() {
		return propositions;
	}
}
