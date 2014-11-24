package it.polimi.model.impl.transitions;

import it.polimi.model.impl.states.State;
import it.polimi.model.interfaces.labeling.Formula;

/**
 * @author claudiomenghi contains an automata {@link Transition}. The
 *         {@link Transition} contains the character that labels the
 *         {@link Transition} and the destination {@link State}
 */
public class Transition {

	/**
	 * contains the id of the {@link Transition}
	 */
	private final int id;

	/**
	 * contains the {@link Formula} which labels the {@link Transition}
	 */
	private Formula condition;

	/**
	 * Constructs a new {@link Transition}.
	 * 
	 * @param condition
	 *            is the {@link Formula} that represent the condition of the
	 *            {@link Transition} to be fired
	 * @param id
	 *            is the id of the {@link Transition}
	 * @throws IllegalArgumentException
	 *             is generated is the id of the {@link Transition} is less than
	 *             zero
	 * @throws NullPointerException
	 *             is generated if the {@link Formula} that labels the
	 *             transition is null
	 */
	protected Transition(Formula condition, int id) {
		if (id < 0) {
			throw new IllegalArgumentException(
					"The value of the id cannot be less than zero");
		}
		this.id = id;
		if (condition == null) {
			throw new NullPointerException(
					"The character that labels the transition cannot be null");
		}
		this.condition = condition;
	}

	/**
	 * @return the {@link Formula} that is the condition that labels the
	 *         {@link Transition}
	 */
	public Formula getCondition() {
		return this.condition;
	}

	/**
	 * sets the {@link Formula} that labels the {@link Transition}
	 * 
	 * @param condition
	 *            the {@link Formula} to be set as a label of the
	 *            {@link Transition}
	 * @throws NullPointerException
	 *             if the {@link Formula} is null
	 */
	public void setCondition(Formula condition) {
		if (condition == null) {
			throw new NullPointerException("The DNFFormula cannot be null");
		}
		this.condition = condition;
	}

	/**
	 * returns the id of the {@link Transition}
	 * 
	 * @return the id of the {@link Transition}
	 */
	public int getId() {
		return id;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "{" + Integer.toString(this.id) + "} "
				+ this.condition.toString() + "";
	}
}
