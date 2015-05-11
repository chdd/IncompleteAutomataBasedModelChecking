package it.polimi.checker.intersection.acceptingpolicies;

import com.google.common.base.Preconditions;

import it.polimi.automata.BA;
import it.polimi.automata.IBA;
import it.polimi.automata.state.State;

public abstract class AcceptingPolicy {

	protected  IBA model;
	protected  BA claim;
	
	
	public void setClaim(BA claim){
		Preconditions.checkNotNull(claim, "The claim to be considered cannot be null");
		this.claim=claim;
	}
	public void setModel(IBA model){
		Preconditions.checkNotNull(model, "The claim to be considered cannot be null");
		this.model=model;
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
	public abstract int comuteNumber(State modelState, State claimState, int prevNumber);
	public abstract int comuteInitNumber(State modelState, State claimState);
	
}

