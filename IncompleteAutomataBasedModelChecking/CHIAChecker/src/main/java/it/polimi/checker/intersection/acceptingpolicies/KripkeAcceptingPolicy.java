package it.polimi.checker.intersection.acceptingpolicies;

import it.polimi.automata.state.State;

import com.google.common.base.Preconditions;

public class KripkeAcceptingPolicy extends AcceptingPolicy {

	
	protected KripkeAcceptingPolicy(){
		
	}
	
	
	@Override
	public int comuteNumber(State modelState, State claimState, int prevNumber) {
		Preconditions.checkNotNull(claim, "The claim to be considered cannot be null");
		
		if(claim.getAcceptStates().contains(claimState)){
			return 2;
		}
		return 1;
	}

	@Override
	public int comuteInitNumber(State modelState, State claimInitialState) {
		Preconditions.checkNotNull(claim, "The claim to be considered cannot be null");
		
		if(claim.getAcceptStates().contains(claimInitialState)){
			return 2;
		}
		else{
			return 1;
		}
		
	}

}
