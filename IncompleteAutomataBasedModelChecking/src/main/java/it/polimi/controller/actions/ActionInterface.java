package it.polimi.controller.actions;

import it.polimi.model.ModelInterface;

/**
 * describes the interface an action that changes the model must implement
 * @author claudiomenghi
 */
public interface ActionInterface {
	
	/**
	 * performs the action
	 * @param model is the model upon which the action must be performed
	 * @throws Exception is generated if there are problems in the action execution 
	 */
	public void perform(ModelInterface model) throws Exception;
}
