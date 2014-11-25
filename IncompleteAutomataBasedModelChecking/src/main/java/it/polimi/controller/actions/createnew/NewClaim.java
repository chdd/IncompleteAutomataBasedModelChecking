package it.polimi.controller.actions.createnew;

import it.polimi.controller.actions.ActionInterface;
import it.polimi.model.ModelInterface;
import it.polimi.model.impl.states.IntersectionState;
import it.polimi.model.impl.states.State;
import it.polimi.model.impl.transitions.Transition;
import it.polimi.view.ViewInterface;

public class NewClaim<
CONSTRAINEDELEMENT extends State,
STATE extends State, 
TRANSITION extends Transition>
		implements
		ActionInterface<CONSTRAINEDELEMENT, STATE, TRANSITION> {

	public <INTERSECTIONSTATE extends IntersectionState<STATE>,
	INTERSECTIONTRANSITION extends Transition>  void perform(
			ModelInterface<CONSTRAINEDELEMENT, STATE, TRANSITION, INTERSECTIONSTATE, INTERSECTIONTRANSITION> model,
			ViewInterface<CONSTRAINEDELEMENT,  STATE, TRANSITION, INTERSECTIONSTATE, INTERSECTIONTRANSITION> view) throws Exception{
		model.newClaim();
		view.updateClaim(model.getSpecification());
	}
}
