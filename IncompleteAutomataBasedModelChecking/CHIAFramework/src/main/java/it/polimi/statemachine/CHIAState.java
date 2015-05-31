package it.polimi.statemachine;

import action.CHIAAction;

public interface CHIAState {


	public CHIAState perform(Class<? extends CHIAAction> chiaAction)
			throws CHIAException;
}
