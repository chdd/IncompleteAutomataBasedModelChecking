package it.polimi.controller.actions.automata.states;

import it.polimi.controller.actions.ActionInterface;
import it.polimi.model.ModelInterface;
import it.polimi.model.impl.states.IntersectionState;
import it.polimi.model.impl.states.State;
import it.polimi.model.impl.transitions.Transition;
import it.polimi.view.ViewInterface;

import java.awt.event.ActionEvent;

@SuppressWarnings("serial")
public class RenameStateAction<
CONSTRAINEDELEMENT extends State,
STATE extends State, 
TRANSITION extends Transition
>		extends ActionEvent implements
ActionInterface<CONSTRAINEDELEMENT, STATE, TRANSITION> {

	protected STATE state;
	protected String name;

	public RenameStateAction(Object source, int id, String command, String name, STATE state) {
		super(source, id, command);
		this.state=state;
		this.name=name;
	}

	@Override
	public <INTERSECTIONSTATE extends IntersectionState<STATE>, 
	INTERSECTIONTRANSITION extends Transition> void perform(
			ModelInterface<CONSTRAINEDELEMENT, STATE, TRANSITION, INTERSECTIONSTATE, INTERSECTIONTRANSITION> model,
			ViewInterface<CONSTRAINEDELEMENT, STATE, TRANSITION, INTERSECTIONSTATE, INTERSECTIONTRANSITION> view)
			throws Exception {
		this.state.setName(name);
	}
}
