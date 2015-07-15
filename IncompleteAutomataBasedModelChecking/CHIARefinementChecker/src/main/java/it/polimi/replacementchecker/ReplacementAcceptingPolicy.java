package it.polimi.replacementchecker;

import com.google.common.base.Preconditions;

import it.polimi.automata.state.State;
import it.polimi.constraints.components.Replacement;
import it.polimi.constraints.components.SubProperty;

public class ReplacementAcceptingPolicy {

		protected SubProperty subproperty;
	protected Replacement replacement;

	
	public ReplacementAcceptingPolicy(SubProperty subproperty, Replacement replacement){
		Preconditions.checkNotNull(subproperty, "the sub-property cannot be null");
		Preconditions.checkNotNull(replacement, "the replacement cannot be null");
		this.subproperty=subproperty;
		this.replacement=replacement;
		
	}
	
	public int getNumber(boolean accepting, boolean propertyAccepting,
			boolean modelAccepting, int x, State subpropertyDestinationState,
			State replacementDestinationState) {

		// if there exists an accepting state between the incoming and the
		// outgoing transition the value 2 is returned
		//if (accepting) {
		//	return 2;
		//}
		// if x is accepting and there exists an accepting state for the
		// property the model is accepted the value 2 is returned
		if (propertyAccepting && modelAccepting) {
			return 2;
		}
			
		if (x == 1 && propertyAccepting) {
			return 2;
		}
		// if x is accepting and the destination sate is accepting for the
		// property the value 2 is returned
		if (x == 1
				&& this.subproperty.getAutomaton().getAcceptStates()
						.contains(subpropertyDestinationState)) {
			return 2;
		}
		// if x ==0 and there is a model accepting state on the reachability
		// relation and the destination state is accepting for the claim
		if (x == 0
				&& modelAccepting
				&& this.subproperty.getAutomaton().getAcceptStates()
						.contains(subpropertyDestinationState)) {
			return 2;
		}
		// if x== and an accepting state of the model is contained in the run
		if (x == 0 && modelAccepting) {
			return 1;
		}
		// if x==0 and the destination is an accepting state of the model
		if (x == 0
				&& this.replacement.getAutomaton().getAcceptStates()
						.contains(replacementDestinationState)) {
			return 1;
		}

		// return 0;
		return 0;

	}

	public int comuteInitNumber(State modelInit, State claimInit) {
		return 0;
	}
	public int comuteNumber(State modelState, State claimState, int prevNumber) {
		Preconditions.checkNotNull(modelState, "The state of the model cannot be null");
		Preconditions.checkNotNull(claimState, "The state of the claim cannot be null");
		Preconditions.checkArgument(prevNumber>=0 && prevNumber<=2, "the prevNumber must be included between 0 and 2");
		int num = prevNumber;

		if (prevNumber == 0 && replacement.getAutomaton().getAcceptStates().contains(modelState)) {
			num = 1;
		}
		if (prevNumber == 1 && subproperty.getAutomaton().getAcceptStates().contains(claimState)) {
			num = 2;
		}
		
		if (prevNumber == 2) {
			num = 0;
		}
		return num;
	}
}
