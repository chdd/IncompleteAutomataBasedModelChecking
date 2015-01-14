package it.polimi.automata.impl;

import it.polimi.automata.Transition;
import it.polimi.automata.labeling.Label;

/**
 * contains the implementation of a transition an Incomplete or a complete Buchi automaton.
 * It implements the interface {@link Transition}
 * 
 * @author claudiomenghi 
 * @see {@link Transition}
 */
public class TransitionImpl<LABEL extends Label> implements Transition<LABEL>{

	/**
	 * contains the id of the transition
	 */
	private final int id;

	/**
	 * contains the label of the transition
	 */
	private LABEL label;

	/**
	 * {@inheritDoc}
	 */
	public LABEL getLabel() {
		return this.label;
	}

	/**
	 * {@inheritDoc}
	 */
	public int getId() {
		return id;
	}
	
	/**
	 * Constructs a new transition
	 * 
	 * @param label
	 *            is the label of the transition
	 * @param id
	 *            is the id of the transition
	 * @throws IllegalArgumentException
	 *             is generated if the id is less than zero
	 * @throws NullPointerException
	 *             is generated if the label of the transition is null
	 */
	protected TransitionImpl(LABEL label, int id) {
		if (id < 0) {
			throw new IllegalArgumentException(
					"The value of the id cannot be less than zero");
		}
		this.id = id;
		if (label == null) {
			throw new NullPointerException(
					"The character that labels the transition cannot be null");
		}
		this.label = label;
	}

	
	/**
	 * sets the label of the transition
	 * 
	 * @param label
	 *            the label of the transition
	 * @throws NullPointerException
	 *             if the label of the transition is null
	 */
	public void setCondition(LABEL label) {
		if (label == null) {
			throw new NullPointerException("The DNFFormula cannot be null");
		}
		this.label = label;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return "{" + Integer.toString(this.id) + "} "
				+ this.label.toString() + "";
	}
}
