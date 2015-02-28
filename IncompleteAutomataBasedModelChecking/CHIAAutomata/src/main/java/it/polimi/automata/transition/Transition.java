package it.polimi.automata.transition;

import java.util.Set;

import rwth.i2.ltl2ba4j.model.IGraphProposition;
import it.polimi.automata.transition.impl.TransitionImpl;

/**
 * <p>
 * Represents a transition of an automaton. <br>
 * A transition is identified by an id and is labeled by characters
 * </p>
 * 
 * @author claudiomenghi
 * @see TransitionImpl
 */
public interface Transition {

	/**
	 * <p>
	 * returns the <b>id</b> of the transition
	 * </p>
	 * 
	 * @return the <b>id</b> of the transition
	 */
	public int getId();

	/**
	 * <p>
	 * returns <b>the label</b> associated with the transition. Depending on
	 * whether the transition is associated with the model or a property it is
	 * associated with a set of labels or a propositional logic formula,
	 * respectively. The semantic of the different labels is that the transition
	 * can be performed if one of the labels (conditions) is satisfied
	 * </p>
	 * 
	 * @return the <b>label</b> associated with the transition
	 */
	public Set<IGraphProposition> getPropositions();

	/**
	 * sets the label to the set of labels specified as parameter
	 * 
	 * @param labels
	 *            the labels to be set to the transition
	 * @throws NullPointerException
	 *             if the set of the labels is <code>null</code> or if a label
	 *             in the set is <code>null</code>
	 */
	public void setLabels(Set<IGraphProposition> labels);
	
	public boolean equals(Object obj); 

}
