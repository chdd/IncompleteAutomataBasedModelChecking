package it.polimi.view.menu.actions;

import javax.swing.tree.DefaultMutableTreeNode;

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
	CONSTRAINEDELEMENT extends State,
	STATE extends State, 
	STATEFACTORY extends StateFactory<STATE>, 
	TRANSITION extends LabelledTransition<CONSTRAINEDELEMENT>, 
	TRANSITIONFACTORY extends LabelledTransitionFactory<CONSTRAINEDELEMENT, TRANSITION>>
extends
		ClaimActionFactory<CONSTRAINEDELEMENT, STATE, STATEFACTORY, TRANSITION, TRANSITIONFACTORY> {

	@Override
	public ChangeEdgeLabel<CONSTRAINEDELEMENT, STATE, STATEFACTORY, TRANSITION, TRANSITIONFACTORY> getChangingLabelAction(
			Object source, int id, String command, String edgeLabel,
			TRANSITION transition) {
		return new ChangeModelEdgeLabel<CONSTRAINEDELEMENT, STATE, STATEFACTORY, TRANSITION, TRANSITIONFACTORY>(source, id, command, edgeLabel, transition);
	}

	
	public DeleteState<CONSTRAINEDELEMENT, STATE, STATEFACTORY, TRANSITION, TRANSITIONFACTORY> getDeleteStateAction(
			Object source, int id, String command, STATE state, DefaultMutableTreeNode parent) {
		return new DeleteModelState<CONSTRAINEDELEMENT, STATE, STATEFACTORY, TRANSITION, TRANSITIONFACTORY>(source, id, command, state, parent);
		
	}

	@Override
	public SetAccepting<CONSTRAINEDELEMENT, STATE, STATEFACTORY, TRANSITION, TRANSITIONFACTORY> setAccepting(
			Object source, int id, String command, STATE state) {
		return new SetAcceptingModel<CONSTRAINEDELEMENT, STATE, STATEFACTORY, TRANSITION, TRANSITIONFACTORY>(source, id, command, state);
	}

	
	public DeleteEdgeAction<CONSTRAINEDELEMENT, STATE, STATEFACTORY, TRANSITION, TRANSITIONFACTORY> deleteEdgeAction(
			Object source, int id, String command,
			TRANSITION transition, DefaultMutableTreeNode treeNode) {
		
		return new DeleteEdgeModel<CONSTRAINEDELEMENT, STATE, STATEFACTORY, TRANSITION, TRANSITIONFACTORY>(source, id, command, transition, treeNode);
	}

	@Override
	public SetInitial<CONSTRAINEDELEMENT, STATE, STATEFACTORY, TRANSITION, TRANSITIONFACTORY> setInitial(
			Object source, int id, String command, STATE state) {
		return new SetInitialModel<CONSTRAINEDELEMENT, STATE, STATEFACTORY, TRANSITION, TRANSITIONFACTORY>(source, id, command, state);
	}
}