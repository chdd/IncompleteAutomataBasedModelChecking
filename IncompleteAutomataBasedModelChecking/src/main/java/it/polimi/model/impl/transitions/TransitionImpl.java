package it.polimi.model.impl.transitions;

import it.polimi.automata.Transition;
import it.polimi.model.impl.states.StateImpl;
import it.polimi.model.interfaces.labeling.DNFFormula;
import it.polimi.model.interfaces.labeling.Formula;

/**
 * @author claudiomenghi contains an automata {@link TransitionImpl}. The
 *         {@link TransitionImpl} contains the character that labels the
 *         {@link TransitionImpl} and the destination {@link StateImpl}
 */
public class TransitionImpl implements Transition{

	/**
	 * contains the id of the {@link TransitionImpl}
	 */
	private final int id;

	/**
	 * contains the {@link Formula} which labels the {@link TransitionImpl}
	 */
	private DNFFormula condition;

	/**
	 * Constructs a new {@link TransitionImpl}.
	 * 
	 * @param condition
	 *            is the {@link Formula} that represent the condition of the
	 *            {@link TransitionImpl} to be fired
	 * @param id
	 *            is the id of the {@link TransitionImpl}
	 * @throws IllegalArgumentException
	 *             is generated is the id of the {@link TransitionImpl} is less than
	 *             zero
	 * @throws NullPointerException
	 *             is generated if the {@link Formula} that labels the
	 *             transition is null
	 */
	protected TransitionImpl(DNFFormula condition, int id) {
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
	 *         {@link TransitionImpl}
	 */
	public DNFFormula getCondition() {
		return this.condition;
	}

	/**
	 * sets the {@link Formula} that labels the {@link TransitionImpl}
	 * 
	 * @param condition
	 *            the {@link Formula} to be set as a label of the
	 *            {@link TransitionImpl}
	 * @throws NullPointerException
	 *             if the {@link Formula} is null
	 */
	public void setCondition(DNFFormula condition) {
		if (condition == null) {
			throw new NullPointerException("The DNFFormula cannot be null");
		}
		this.condition = condition;
	}

	/**
	 * returns the id of the {@link TransitionImpl}
	 * 
	 * @return the id of the {@link TransitionImpl}
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
