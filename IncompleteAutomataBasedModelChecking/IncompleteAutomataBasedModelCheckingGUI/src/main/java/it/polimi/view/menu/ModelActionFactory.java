package it.polimi.view.menu;

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
import it.polimi.model.impl.transitions.Transition;

import javax.swing.tree.DefaultMutableTreeNode;

public class ModelActionFactory<
	CONSTRAINEDELEMENT extends State,
	STATE extends State, 
	TRANSITION extends Transition>
extends
		ClaimActionFactory<CONSTRAINEDELEMENT, STATE, TRANSITION> {

	@Override
	public ChangeEdgeLabel<CONSTRAINEDELEMENT, STATE, TRANSITION> getChangingLabelAction(
			Object source, int id, String command, String edgeLabel,
			TRANSITION transition) {
		return new ChangeModelEdgeLabel<CONSTRAINEDELEMENT, STATE, TRANSITION>(source, id, command, edgeLabel, transition);
	}

	
	public DeleteState<CONSTRAINEDELEMENT, STATE, TRANSITION> getDeleteStateAction(
			Object source, int id, String command, STATE state, DefaultMutableTreeNode parent) {
		return new DeleteModelState<CONSTRAINEDELEMENT, STATE, TRANSITION>(source, id, command, state, parent);
		
	}

	@Override
	public SetAccepting<CONSTRAINEDELEMENT, STATE, TRANSITION> setAccepting(
			Object source, int id, String command, STATE state) {
		return new SetAcceptingModel<CONSTRAINEDELEMENT, STATE, TRANSITION>(source, id, command, state);
	}

	
	public DeleteEdgeAction<CONSTRAINEDELEMENT, STATE, TRANSITION> deleteEdgeAction(
			Object source, int id, String command,
			TRANSITION transition, DefaultMutableTreeNode treeNode) {
		
		return new DeleteEdgeModel<CONSTRAINEDELEMENT, STATE, TRANSITION>(source, id, command, transition, treeNode);
	}

	@Override
	public SetInitial<CONSTRAINEDELEMENT, STATE, TRANSITION> setInitial(
			Object source, int id, String command, STATE state) {
		return new SetInitialModel<CONSTRAINEDELEMENT, STATE, TRANSITION>(source, id, command, state);
	}
}
