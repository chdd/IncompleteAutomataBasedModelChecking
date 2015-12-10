package it.polimi.checker.intersection.acceptingpolicies;

import it.polimi.automata.BA;
import it.polimi.automata.state.State;

import com.google.common.base.Preconditions;

/**
 * Is the accepting policy that is used when all the states of the model must be
 * considered as accepting, i.e., the model must be considered as a Kripke
 * structure
 * 
 * @author Claudio Menghi
 *
 */
public class KripkeAcceptingPolicy extends AcceptingPolicy {

	/**
	 * creates a new Kripke accepting policy
	 * 
	 * @param model
	 *            is the model to be considered
	 * @param claim
	 *            is the claim to be considered
	 * @throws NullPointerException
	 *             if the model or the claim is null
	 */
	protected KripkeAcceptingPolicy(BA model, BA claim) {
		super(model, claim);
	}

	/**
	 * Computes the number of the next state of the intersection automaton
	 * depending on the number of the previous state and the state of the model
	 * and the claim
	 * 
	 * @param modelState
	 *            the state of the model to be considered
	 * @param claimState
	 *            the state of the claim to be considered
	 * @param prevNumber
	 *            is the number associated with the previous state of the
	 *            intersection automaton
	 * @return is the number to be associated to the new intersection state
	 */
	@Override
	public int comuteNumber(State modelState, State claimState, int prevNumber) {
		Preconditions.checkNotNull(modelState,
				"The state of the model to be considered cannot be null");
		Preconditions.checkNotNull(claimState,
				"The state of the claim to be considered cannot be null");
		Preconditions.checkArgument(
				this.model.getStates().contains(modelState), "The state "
						+ modelState + " mus be a state of the model");
		Preconditions.checkArgument(
				this.claim.getStates().contains(claimState), "The state "
						+ claimState + " mus be a state of the claim");
		Preconditions
				.checkArgument(
						1 <= prevNumber && prevNumber <= 2,
						"The number prevNumber of the intersection automaton must be 1<=prevNumber && prevNumber<=2");

		if (claim.getAcceptStates().contains(claimState)) {
			return 2;
		}
		return 1;
	}

	/**
	 * Returns the number of an initial state of the intersection automaton
	 * 
	 * @param modelState
	 *            the state of the model to be considered
	 * @param claimState
	 *            the state of the claim to be considered
	 * @return is the number to be associated to the new intersection state
	 */
	@Override
	public int comuteInitNumber(State modelState, State claimInitialState) {
		Preconditions.checkNotNull(modelState,
				"The state of the model to be considered cannot be null");
		Preconditions.checkNotNull(claimInitialState,
				"The state of the claim to be considered cannot be null");
		Preconditions.checkArgument(
				this.model.getStates().contains(modelState), "The state "
						+ modelState + " mus be a state of the model");
		Preconditions.checkArgument(
				this.claim.getStates().contains(claimInitialState), "The state "
						+ claimInitialState + " mus be a state of the claim");

		return 1;
	}
}
