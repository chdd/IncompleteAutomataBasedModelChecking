package it.polimi.view.menu;

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
import it.polimi.model.impl.transitions.Transition;

public class ClaimActionFactory<
CONSTRAINEDELEMENT extends State,
STATE extends State, 
TRANSITION extends Transition>
		extends
		ActionTypesInterface<CONSTRAINEDELEMENT, STATE, TRANSITION> {

	@Override
	public ChangeEdgeLabel<CONSTRAINEDELEMENT, STATE, TRANSITION> getChangingLabelAction(
			Object source, int id, String command, String edgeLabel,
			TRANSITION transition) {
		return new ChangeClaimEdgeLabel<CONSTRAINEDELEMENT, STATE, TRANSITION>(source, id, command, edgeLabel, transition);
	}

	public DeleteState<CONSTRAINEDELEMENT, STATE, TRANSITION> getDeleteStateAction(
			Object source, int id, String command, STATE state) {
		return new DeleteClaimState<CONSTRAINEDELEMENT, STATE, TRANSITION>(source, id, command, state);
	}

	@Override
	public SetAccepting<CONSTRAINEDELEMENT, STATE, TRANSITION> setAccepting(
			Object source, int id, String command, STATE state) {
		return new SetAcceptingClaim<CONSTRAINEDELEMENT, STATE, TRANSITION>(source, id, command, state);
	}

	@Override
	public DeleteEdgeAction<CONSTRAINEDELEMENT, STATE, TRANSITION> deleteEdgeAction(
			Object source, int id, String command,
			TRANSITION transition) 
	{
		return new DeleteEdgeClaimAction<CONSTRAINEDELEMENT, STATE, TRANSITION>(source, id, command, transition);
	}

	@Override
	public SetInitial<CONSTRAINEDELEMENT, STATE, TRANSITION> setInitial(
			Object source, int id, String command, STATE state) {
		
		return new SetInitialClaim<CONSTRAINEDELEMENT, STATE, TRANSITION>(source, id, command, state);
	}

}
