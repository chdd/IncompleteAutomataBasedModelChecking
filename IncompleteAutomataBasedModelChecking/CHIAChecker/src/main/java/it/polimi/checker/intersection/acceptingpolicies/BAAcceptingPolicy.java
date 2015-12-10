package it.polimi.checker.intersection.acceptingpolicies;

import it.polimi.automata.BA;
import it.polimi.automata.state.State;

import com.google.common.base.Preconditions;

/**
 * creates a BA accepting policy. The BA accepting policy given a state of the
 * model and a state of the claim and the number associated to the previous
 * state returns
 * 
 * <ol>
 * <li>1 if the previous number was 0 and the state of the model is accepting</li>
 * <li>2 if the previous number was 1 and the state of the claim is accepting</li>
 * <li>0 if the previous number was 2</li>
 * <li>the previous number in the other cases</li>
 * </ol>
 * 
 * @author Claudio Menghi
 *
 */
public class BAAcceptingPolicy extends AcceptingPolicy {

	/**
	 * creates a new {@link BAAcceptingPolicy}
	 * 
	 * @param model
	 *            the model to be considered
	 * @param claim
	 *            the claim to be considered
	 * @throws NullPointerException
	 *             if one of the parameters is null
	 */
	protected BAAcceptingPolicy(BA model, BA claim) {
		super(model, claim);

	}

	/**
	 * the compute number function returns
	 * <ol>
	 * <li>1 if the previous number was 0 and the state of the model is
	 * accepting</li>
	 * <li>2 if the previous number was 1 and the state of the claim is
	 * accepting</li>
	 * <li>0 if the previous number was 2</li>
	 * <li>the previous number in the other cases</li>
	 * </ol>
	 * 
	 * @param modelState
	 *            the state of the model to be considered
	 * @param claimState
	 *            the state of the claim to be considered
	 * @param prevNumber
	 *            the number of the previous state
	 * @return <ol>
	 *         <li>1 if the previous number was 0 and the state of the model is
	 *         accepting</li>
	 *         <li>2 if the previous number was 1 and the state of the claim is
	 *         accepting</li>
	 *         <li>0 if the previous number was 2</li>
	 *         <li>the previous number in the other cases</li>
	 *         </ol>
	 * @throws NullPointerException
	 *             if the state of the model or the of the claim is null
	 * @throws IllegalArgumentException
	 *             if the prevNumber is not between 0 and 2 included
	 * @throws IllegalArgumentException
	 *             if the modelState or the claimState is not included in the
	 *             set of the states of the model/claim, respectively
	 */
	@Override
	public int comuteNumber(State modelState, State claimState, int prevNumber) {

		Preconditions.checkNotNull(modelState,
				"The state of the model cannot be null");
		Preconditions.checkNotNull(claimState,
				"The state of the claim cannot be null");
		Preconditions
				.checkArgument(this.model.getStates().contains(modelState),
						"The model state must be contained into the set of the states of the model");
		Preconditions
				.checkArgument(this.claim.getStates().contains(claimState),
						"The claim state must be contained into the set of the states of the claim");
		Preconditions.checkArgument(0 <= prevNumber && prevNumber <= 2,
				"The previous number must be included between 0 and 2");
		int num = prevNumber;

		if (prevNumber == 0 && model.getAcceptStates().contains(modelState)) {
			num = 1;
		}
		if (prevNumber == 1 && claim.getAcceptStates().contains(claimState)) {
			num = 2;
		}

		if (prevNumber == 2) {
			num = 0;
		}
		return num;
	}

	/**
	 * returns the initial state to be used when the {@link BAAcceptingPolicy}
	 * is employed.
	 * 
	 * @return zero: the value to be used in the intersection as initial number
	 *         when the {@link BAAcceptingPolicy} is employed
	 * @throws NullPointerException
	 *             if the state of the model or the of the claim is null
	 * @throws IllegalArgumentException
	 *             if the modelState or the claimState is not included in the
	 *             set of the states of the model/claim, respectively
	 */
	@Override
	public int comuteInitNumber(State modelState, State claimState) {
		Preconditions.checkNotNull(modelState,
				"The state of the model cannot be null");
		Preconditions.checkNotNull(claimState,
				"The state of the claim cannot be null");
		Preconditions
				.checkArgument(this.model.getStates().contains(modelState),
						"The model state must be contained into the set of the states of the model");
		Preconditions
				.checkArgument(this.claim.getStates().contains(claimState),
						"The claim state must be contained into the set of the states of the claim");
		return 0;
	}

}
