package it.polimi.controller.actions;

import it.polimi.model.ModelInterface;
import it.polimi.model.impl.states.IntersectionState;
import it.polimi.model.impl.states.State;
import it.polimi.model.impl.transitions.Transition;
import it.polimi.view.ViewInterface;

/**
 * describes the interface an action that changes the model must implement
 * @author claudiomenghi
 */
public interface ActionInterface<
	CONSTRAINEDELEMENT extends State,
	STATE extends State, 
	TRANSITION extends Transition>
	 {
	
	/**
	 * performs the action
	 * @param model is the model upon which the action must be performed
	 * @throws Exception is generated if there are problems in the action execution 
	 */
	public <INTERSECTIONSTATE extends IntersectionState<STATE>,
	INTERSECTIONTRANSITION extends Transition>
	void perform(ModelInterface<CONSTRAINEDELEMENT, STATE, TRANSITION, INTERSECTIONSTATE, INTERSECTIONTRANSITION> model,
			ViewInterface<CONSTRAINEDELEMENT, STATE, TRANSITION, INTERSECTIONSTATE, INTERSECTIONTRANSITION> view) throws Exception;
}
