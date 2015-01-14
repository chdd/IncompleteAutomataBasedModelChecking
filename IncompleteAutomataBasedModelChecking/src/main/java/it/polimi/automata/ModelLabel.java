package it.polimi.automata;

import java.util.Set;

/**
 * <p>
 * Represents the label of a transition of the model of the automaton. It corresponds to the set of characters of the 
 * atomic propositions to be satisfied for the transition to be performed 
 * @see Label
 * </p>
 * 
 * @author claudiomenghi
 */
public interface ModelLabel extends Label {

	/**
	 * returns the set of atomic propositions that labels the transition
	 * @return the set of atomic propositions that labels the transition
	 */
	public Set<String> getAtomicPropositions();
}
