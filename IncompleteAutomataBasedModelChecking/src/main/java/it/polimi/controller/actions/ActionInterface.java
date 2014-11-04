package it.polimi.controller.actions;

import it.polimi.model.ModelInterface;
import it.polimi.model.impl.states.IntersectionState;
import it.polimi.model.impl.states.IntersectionStateFactory;
import it.polimi.model.impl.states.State;
import it.polimi.model.impl.states.StateFactory;
import it.polimi.model.impl.transitions.ConstrainedTransition;
import it.polimi.model.impl.transitions.LabelledTransition;
import it.polimi.model.interfaces.transitions.ConstrainedTransitionFactory;
import it.polimi.model.interfaces.transitions.LabelledTransitionFactory;
import it.polimi.view.ViewInterface;

/**
 * describes the interface an action that changes the model must implement
 * @author claudiomenghi
 */
public interface ActionInterface<
	STATE extends State, 
	STATEFACTORY extends StateFactory<STATE>, 
	TRANSITION extends LabelledTransition, 
	TRANSITIONFACTORY extends LabelledTransitionFactory<TRANSITION>>
	 {
	
	/**
	 * performs the action
	 * @param model is the model upon which the action must be performed
	 * @throws Exception is generated if there are problems in the action execution 
	 */
	public <INTERSECTIONSTATE extends IntersectionState<STATE>,
	INTERSECTIONSTATEFACTORY extends IntersectionStateFactory<STATE, INTERSECTIONSTATE>,
	INTERSECTIONTRANSITION extends ConstrainedTransition<STATE>,
	INTERSECTIONTRANSITIONFACTORY extends ConstrainedTransitionFactory<STATE, INTERSECTIONTRANSITION>> 
	void perform(ModelInterface<STATE, STATEFACTORY, TRANSITION, TRANSITIONFACTORY, INTERSECTIONSTATE, INTERSECTIONSTATEFACTORY, INTERSECTIONTRANSITION, INTERSECTIONTRANSITIONFACTORY> model,
			ViewInterface<STATE, TRANSITION, INTERSECTIONSTATE, INTERSECTIONTRANSITION, 
			TRANSITIONFACTORY,
			INTERSECTIONTRANSITIONFACTORY> view) throws Exception;
}
