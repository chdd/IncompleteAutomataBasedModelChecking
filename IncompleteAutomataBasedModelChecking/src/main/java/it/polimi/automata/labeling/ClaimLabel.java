package it.polimi.automata.labeling;

import java.util.Set;

import rwth.i2.ltl2ba4j.model.IGraphProposition;


/**
 * <p>
 * Represents the label of a transition of the claim automaton. It corresponds to the set of characters of the 
 * atomic propositions to be satisfied for the transition to be performed. The proposition may be negated or not
 * and are specifies through the interface IGraphProposition.
 * @see Label
 * </p>
 * 
 * @author claudiomenghi
 */
public interface ClaimLabel extends Label {

	/**
	 * returns the set of atomic propositions that labels the transition. In the claim the proposition may also be negated
	 * the IGraphProposition interface allows to describes propositions that are negated or not
	 * 
	 * @return the set of atomic propositions that labels the transition that refers to the claim
	 */
	public Set<IGraphProposition> getAtomicPropositions();
}
