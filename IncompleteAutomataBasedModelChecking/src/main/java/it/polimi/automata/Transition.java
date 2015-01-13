package it.polimi.automata;

import it.polimi.model.impl.transitions.TransitionImpl;

/**
 * <p>
 * Represents a transition of an automaton. <br>
 * A transition is identified by an id and is labeled by characters
 * </p>
 * 
 * @author claudiomenghi
 * @see TransitionImpl
 * @param <LABEL>
 *            is the label of the transition which must extend the interface 
 *            {@link Label}. Depending on whether the transition is associated 
 *            with the model or a property it is associated with a set of atomic
 *            propositions or a propositional logic formula, respectively.
 */
public interface Transition<LABEL extends Label> {

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
	 * associated with a set of atomic propositions or a propositional logic
	 * formula, respectively.
	 * </p>
	 * 
	 * @return the <b>label</b> associated with the transition
	 */
	public LABEL getLabel();
}
