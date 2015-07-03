package it.polimi.checker.intersection.acceptingpolicies;

import com.google.common.base.Preconditions;

import it.polimi.automata.BA;
import it.polimi.automata.IBA;
import it.polimi.automata.state.State;

public abstract class AcceptingPolicy {

	public static enum AcceptingType {
		KRIPKE, NORMAL
	}

	protected IBA model;
	protected BA claim;

	public void setClaim(BA claim) {
		Preconditions.checkNotNull(claim,
				"The claim to be considered cannot be null");
		this.claim = claim;
	}

	public void setModel(IBA model) {
		Preconditions.checkNotNull(model,
				"The claim to be considered cannot be null");
		this.model = model;
	}

	/**
	 * given the number of the previous state, the current model state, the
	 * claim state and the model and the claim returns the number to be
	 * associated to the state to be created
	 * 
	 * @param modelState
	 *            the model state under analysis
	 * @param claimState
	 *            the claim state under analysis
	 * @param prevNumber
	 *            the number of the previous state
	 * @return the number to be associated to the next state of the automaton
	 */
	public abstract int comuteNumber(State modelState, State claimState,
			int prevNumber);

	public abstract int comuteInitNumber(State modelState, State claimState);

	/**
	 * returns the correct accepting policy with respect to the specified
	 * acceptingType
	 * 
	 * @param acceptingType
	 *            is the type of accepting policy to be used
	 * @return a new accepting policy corresponding to the specified
	 *         acceptingType
	 * @throws NullPointerException
	 *             if the acceptingType is null
	 * @throws IllegalArgumentException
	 *             if the accepting type does not correspond to any accepting
	 *             policy
	 */
	public static AcceptingPolicy getAcceptingPolicy(AcceptingType acceptingType) {
		Preconditions.checkNotNull(acceptingType,
				"The accepting policy to be considered cannot be null");
		if (acceptingType.equals(AcceptingType.KRIPKE)) {
			return new KripkeAcceptingPolicy();
		}
		if (acceptingType.equals(AcceptingType.NORMAL.toString())) {
			return new NormalAcceptingPolicy();
		}
		throw new IllegalArgumentException("The accepting policy "
				+ acceptingType + " is not supported");
	}
}
