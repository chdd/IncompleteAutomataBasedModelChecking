package it.polimi.controller.actions.automata.states.delete;

import it.polimi.model.ModelInterface;
import it.polimi.model.impl.states.IntersectionState;
import it.polimi.model.impl.states.State;
import it.polimi.model.impl.transitions.Transition;
import it.polimi.view.ViewInterface;

@SuppressWarnings("serial")
public class DeleteClaimState
<
CONSTRAINEDELEMENT extends State,
STATE extends State, 
TRANSITION extends Transition
>	extends DeleteState<CONSTRAINEDELEMENT, STATE, TRANSITION> {


	public DeleteClaimState(Object source, int id, String command, STATE state) {
		super(source, id, command, state);
	}

	@Override
	public <INTERSECTIONSTATE extends IntersectionState<STATE>, 
	INTERSECTIONTRANSITION extends Transition> 
	void perform(
			ModelInterface<CONSTRAINEDELEMENT, STATE, TRANSITION,  INTERSECTIONSTATE,  INTERSECTIONTRANSITION> model,
			ViewInterface<CONSTRAINEDELEMENT, STATE, TRANSITION, INTERSECTIONSTATE, INTERSECTIONTRANSITION> view)
			throws Exception {
		model.getSpecification().removeState(state);
		
	}

}
