package it.polimi.statemachine.states;

import action.CHIAAction;
import action.CHIAException;

/**
 * contains the current state of the state machine
 * 
 * @author Claudio Menghi
 *
 */
public interface CHIAStateInterface {

	/**
	 * returns true if the CHIAAction is performable, false otherwise
	 * 
	 * @param chiaAction
	 *            the type of the action to be performed
	 * @return true if the action is performable, false otherwise
	 */
	public boolean isPerformable(Class<? extends CHIAAction<?>> chiaAction);

	/**
	 * returns the next state of the machine based on the CHIAAction
	 * 
	 * @param chiaAction
	 *            the class of the action to be performed
	 * @return the state which is reached after the action is performed
	 * @throws CHIAException
	 *             if the action is not performable
	 */
	public CHIAStateInterface perform(Class<? extends CHIAAction<?>> chiaAction)
			throws CHIAException;
}
