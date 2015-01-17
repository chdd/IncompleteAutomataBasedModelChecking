package it.polimi.automata.labeling;

import it.polimi.automata.BA;
import it.polimi.automata.state.State;
import it.polimi.automata.transition.Transition;

import java.util.Set;

import rwth.i2.ltl2ba4j.model.IGraphProposition;

/**
 * <p>
 * Represents the label of a transition. It can be a set of characters of the 
 * alphabets or a propositional logic formula depending on whether the label
 * is associated with a transition of the model or the claim, respectively.
 * @see Transition
 * @see State
 * @see BA
 * </p>
 * 
 * @author claudiomenghi
 */
public interface Label {

	/**
	 * returns the set of propositions that labels the transition.  
	 * @return the set of atomic propositions that labels the transition that refers to the claim
	 */
	public Set<IGraphProposition> getAtomicPropositions();
}
