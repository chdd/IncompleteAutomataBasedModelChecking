package it.polimi.controller.actions.automata.states.accepting;

import it.polimi.model.ModelInterface;
import it.polimi.model.impl.states.IntersectionState;
import it.polimi.model.impl.states.State;
import it.polimi.model.impl.transitions.Transition;
import it.polimi.view.ViewInterface;

@SuppressWarnings("serial")
public class SetAcceptingClaim<
CONSTRAINEDELEMENT extends State,
STATE extends State, 
TRANSITION extends Transition>
		extends
		SetAccepting<CONSTRAINEDELEMENT, STATE, TRANSITION> {

	public SetAcceptingClaim(Object source, int id, String command, STATE state) {
		super(source, id, command, state);
	}

	@Override
	public <INTERSECTIONSTATE extends IntersectionState<STATE>, 
	INTERSECTIONTRANSITION extends Transition> void perform(
			ModelInterface<CONSTRAINEDELEMENT, STATE, TRANSITION, INTERSECTIONSTATE,  INTERSECTIONTRANSITION> model,
			ViewInterface<CONSTRAINEDELEMENT, STATE, TRANSITION, INTERSECTIONSTATE, INTERSECTIONTRANSITION> view)
			throws Exception {
		if (model.getSpecification().isAccept(state)) {
			model.getSpecification().getAcceptStates().remove(state);
		} else {
			model.getSpecification().addAcceptState(state);
		}
	}
}
