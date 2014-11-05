package it.polimi.controller.actions.automata.states.transparent;

import it.polimi.controller.actions.ActionInterface;
import it.polimi.model.ModelInterface;
import it.polimi.model.impl.states.IntersectionState;
import it.polimi.model.impl.states.IntersectionStateFactory;
import it.polimi.model.impl.states.State;
import it.polimi.model.impl.states.StateFactory;
import it.polimi.model.impl.transitions.LabelledTransition;
import it.polimi.model.interfaces.transitions.ConstrainedTransitionFactory;
import it.polimi.model.interfaces.transitions.LabelledTransitionFactory;
import it.polimi.view.ViewInterface;

import java.awt.event.ActionEvent;

@SuppressWarnings("serial")
public class SetTransparent<STATE extends State, STATEFACTORY extends StateFactory<STATE>, TRANSITION extends LabelledTransition, TRANSITIONFACTORY extends LabelledTransitionFactory<TRANSITION>>
		extends ActionEvent implements
		ActionInterface<STATE, STATEFACTORY, TRANSITION, TRANSITIONFACTORY> {
	protected STATE state;

	public SetTransparent(Object source, int id, String command, STATE state) {
		super(source, id, command);
		this.state = state;
	}

	@Override
	public <INTERSECTIONSTATE extends IntersectionState<STATE>, INTERSECTIONSTATEFACTORY extends 
	IntersectionStateFactory<STATE, INTERSECTIONSTATE>, 
	INTERSECTIONTRANSITION extends LabelledTransition, INTERSECTIONTRANSITIONFACTORY extends ConstrainedTransitionFactory<STATE, INTERSECTIONTRANSITION>> void perform(
			ModelInterface<STATE, STATEFACTORY, TRANSITION, TRANSITIONFACTORY, INTERSECTIONSTATE, INTERSECTIONSTATEFACTORY, INTERSECTIONTRANSITION, INTERSECTIONTRANSITIONFACTORY> model,
			ViewInterface<STATE, TRANSITION, INTERSECTIONSTATE, INTERSECTIONTRANSITION, TRANSITIONFACTORY, INTERSECTIONTRANSITIONFACTORY> view)
			throws Exception {
		if (model.getModel().isTransparent(state)) {
			model.getModel().getTransparentStates().remove(state);
		} else {
			model.getModel().addTransparentState(state);
		}
	}
}
