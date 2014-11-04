package it.polimi.view.menu.actions;

import it.polimi.controller.actions.automata.edges.ChangeEdgeLabel;
import it.polimi.controller.actions.automata.edges.ChangeModelEdgeLabel;
import it.polimi.controller.actions.automata.edges.delete.DeleteEdgeAction;
import it.polimi.controller.actions.automata.edges.delete.DeleteEdgeModel;
import it.polimi.controller.actions.automata.states.accepting.SetAccepting;
import it.polimi.controller.actions.automata.states.accepting.SetAcceptingModel;
import it.polimi.controller.actions.automata.states.delete.DeleteModelState;
import it.polimi.controller.actions.automata.states.delete.DeleteState;
import it.polimi.controller.actions.automata.states.initial.SetInitial;
import it.polimi.controller.actions.automata.states.initial.SetInitialModel;
import it.polimi.model.impl.states.State;
import it.polimi.model.impl.states.StateFactory;
import it.polimi.model.impl.transitions.LabelledTransition;
import it.polimi.model.interfaces.transitions.LabelledTransitionFactory;

public class ModelActionFactory<
	STATE extends State, 
	STATEFACTORY extends StateFactory<STATE>, 
	TRANSITION extends LabelledTransition, 
	TRANSITIONFACTORY extends LabelledTransitionFactory<TRANSITION>>
extends
		ActionTypesInterface<STATE, STATEFACTORY, TRANSITION, TRANSITIONFACTORY> {

	@Override
	public ChangeEdgeLabel<STATE, STATEFACTORY, TRANSITION, TRANSITIONFACTORY> getChangingLabelAction(
			Object source, int id, String command, String edgeLabel,
			TRANSITION transition) {
		return new ChangeModelEdgeLabel<STATE, STATEFACTORY, TRANSITION, TRANSITIONFACTORY>(source, id, command, edgeLabel, transition);
	}

	@Override
	public DeleteState<STATE, STATEFACTORY, TRANSITION, TRANSITIONFACTORY> getDeleteStateAction(
			Object source, int id, String command, STATE state) {
		return new DeleteModelState<STATE, STATEFACTORY, TRANSITION, TRANSITIONFACTORY>(source, id, command, state);
		
	}

	@Override
	public SetAccepting<STATE, STATEFACTORY, TRANSITION, TRANSITIONFACTORY> setAccepting(
			Object source, int id, String command, STATE state) {
		return new SetAcceptingModel<STATE, STATEFACTORY, TRANSITION, TRANSITIONFACTORY>(source, id, command, state);
	}

	@Override
	public DeleteEdgeAction<STATE, STATEFACTORY, TRANSITION, TRANSITIONFACTORY> deleteEdgeAction(
			Object source, int id, String command,
			TRANSITION transition) {
		
		return new DeleteEdgeModel<STATE, STATEFACTORY, TRANSITION, TRANSITIONFACTORY>(source, id, command, transition);
	}

	@Override
	public SetInitial<STATE, STATEFACTORY, TRANSITION, TRANSITIONFACTORY> setInitial(
			Object source, int id, String command, STATE state) {
		return new SetInitialModel<STATE, STATEFACTORY, TRANSITION, TRANSITIONFACTORY>(source, id, command, state);
	}

}
