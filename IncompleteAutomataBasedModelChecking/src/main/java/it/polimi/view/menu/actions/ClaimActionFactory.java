package it.polimi.view.menu.actions;

import it.polimi.controller.actions.automata.edges.ChangeClaimEdgeLabel;
import it.polimi.controller.actions.automata.edges.ChangeEdgeLabel;
import it.polimi.controller.actions.automata.edges.delete.DeleteEdgeAction;
import it.polimi.controller.actions.automata.edges.delete.DeleteEdgeClaimAction;
import it.polimi.controller.actions.automata.states.accepting.SetAccepting;
import it.polimi.controller.actions.automata.states.accepting.SetAcceptingClaim;
import it.polimi.controller.actions.automata.states.delete.DeleteClaimState;
import it.polimi.controller.actions.automata.states.delete.DeleteState;
import it.polimi.controller.actions.automata.states.initial.SetInitial;
import it.polimi.controller.actions.automata.states.initial.SetInitialClaim;
import it.polimi.model.impl.states.State;
import it.polimi.model.impl.states.StateFactory;
import it.polimi.model.impl.transitions.LabelledTransition;
import it.polimi.model.interfaces.transitions.LabelledTransitionFactory;

public class ClaimActionFactory<
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
		return new ChangeClaimEdgeLabel<STATE, STATEFACTORY, TRANSITION, TRANSITIONFACTORY>(source, id, command, edgeLabel, transition);
	}

	@Override
	public DeleteState<STATE, STATEFACTORY, TRANSITION, TRANSITIONFACTORY> getDeleteStateAction(
			Object source, int id, String command, STATE state) {
		return new DeleteClaimState<STATE, STATEFACTORY, TRANSITION, TRANSITIONFACTORY>(source, id, command, state);
	}

	@Override
	public SetAccepting<STATE, STATEFACTORY, TRANSITION, TRANSITIONFACTORY> setAccepting(
			Object source, int id, String command, STATE state) {
		return new SetAcceptingClaim<STATE, STATEFACTORY, TRANSITION, TRANSITIONFACTORY>(source, id, command, state);
	}

	@Override
	public DeleteEdgeAction<STATE, STATEFACTORY, TRANSITION, TRANSITIONFACTORY> deleteEdgeAction(
			Object source, int id, String command,
			TRANSITION transition) 
	{
		return new DeleteEdgeClaimAction<STATE, STATEFACTORY, TRANSITION, TRANSITIONFACTORY>(source, id, command, transition);
	}

	@Override
	public SetInitial<STATE, STATEFACTORY, TRANSITION, TRANSITIONFACTORY> setInitial(
			Object source, int id, String command, STATE state) {
		
		return new SetInitialClaim<STATE, STATEFACTORY, TRANSITION, TRANSITIONFACTORY>(source, id, command, state);
	}

}
