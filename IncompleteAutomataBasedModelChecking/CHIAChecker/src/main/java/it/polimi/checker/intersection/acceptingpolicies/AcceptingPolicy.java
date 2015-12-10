package it.polimi.checker.intersection.acceptingpolicies;

import it.polimi.automata.BA;
import it.polimi.automata.state.State;

import com.google.common.base.Preconditions;

public abstract class AcceptingPolicy {

	public static enum AcceptingType {
		KRIPKE, BA
	}

	protected BA model;
	protected BA claim;

	/**
	 * creates a new Accepting policy
	 * 
	 * @param model
	 *            is the model to be considered
	 * @param claim
	 *            is the claim to be considered
	 * @throws NullPointerException
	 *             if the model or the claim is null
	 */
	public AcceptingPolicy(BA model, BA claim) {
		Preconditions.checkNotNull(model,
				"The model to be considered cannot be null");
		Preconditions.checkNotNull(claim,
				"The claim to be considered cannot be null");
		this.model = model;
		this.claim = claim;
	}

	public void setClaim(BA claim) {
		Preconditions.checkNotNull(claim,
				"The claim to be considered cannot be null");
		this.claim = claim;
	}

	public void setModel(BA model) {
		Preconditions.checkNotNull(model,
				"The claim to be considered cannot be null");
		this.model = model;
	}

	public BA getClaim() {
		return this.claim;
	}

	public BA getModel() {
		return this.model;
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
	public static AcceptingPolicy getAcceptingPolicy(AcceptingType acceptingType, BA model, BA claim) {
		Preconditions.checkNotNull(acceptingType,
				"The accepting policy to be considered cannot be null");
		if (acceptingType.equals(AcceptingType.KRIPKE)) {
			return new KripkeAcceptingPolicy(model, claim);
		}
		if (acceptingType.equals(AcceptingType.BA)) {
			return new BAAcceptingPolicy(model, claim);
		}
		throw new IllegalArgumentException("The accepting policy "
				+ acceptingType + " is not supported");
	}
}
