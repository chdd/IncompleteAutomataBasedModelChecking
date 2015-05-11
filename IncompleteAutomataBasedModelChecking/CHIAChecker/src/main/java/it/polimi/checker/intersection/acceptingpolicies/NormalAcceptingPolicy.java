package it.polimi.checker.intersection.acceptingpolicies;

import it.polimi.automata.state.State;

public class NormalAcceptingPolicy extends AcceptingPolicy{

	
	public NormalAcceptingPolicy(){
		
	}
	
	@Override
	public int comuteNumber(State modelState, State claimState, int prevNumber) {
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

	@Override
	public int comuteInitNumber(State modelState, State claimState) {
		return 0;
	}

}
