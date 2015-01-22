package it.polimi.view.menu;

import it.polimi.controller.actions.automata.edges.ChangeEdgeLabel;
import it.polimi.controller.actions.automata.edges.delete.DeleteEdgeAction;
import it.polimi.controller.actions.automata.states.accepting.SetAccepting;
import it.polimi.controller.actions.automata.states.initial.SetInitial;
import it.polimi.controller.actions.automata.states.transparent.SetTransparent;
import it.polimi.controller.viewRefinement.ViewHierarchyStateRefinementAction;
import it.polimi.model.impl.states.State;
import it.polimi.model.impl.transitions.Transition;

import javax.swing.tree.DefaultMutableTreeNode;

public abstract class ActionTypesInterface<
CONSTRAINEDELEMENT extends State,
STATE extends State, 
TRANSITION extends Transition> {

	public abstract DeleteEdgeAction<CONSTRAINEDELEMENT, STATE, TRANSITION> deleteEdgeAction(Object source, int id, String command, TRANSITION transition);
	
	public abstract ChangeEdgeLabel<CONSTRAINEDELEMENT, STATE, TRANSITION> getChangingLabelAction(Object source, int id, String command, String edgeLabel, TRANSITION transition);
	
	// STATES
	public abstract SetAccepting<CONSTRAINEDELEMENT, STATE, TRANSITION> setAccepting(Object source, int id, String command, STATE state);
	public abstract SetInitial<CONSTRAINEDELEMENT, STATE, TRANSITION> setInitial(Object source, int id, String command, STATE state);
	
	public SetTransparent<CONSTRAINEDELEMENT, STATE, TRANSITION> setTransparent(Object source, int id, String command, STATE state, DefaultMutableTreeNode parent){
		return new SetTransparent<CONSTRAINEDELEMENT, STATE, TRANSITION>(source, id, command, state, parent);
	}
	
	public ViewHierarchyStateRefinementAction<CONSTRAINEDELEMENT, STATE, TRANSITION> getRefineStateAction(
			Object source, int id, String command, STATE state){
		
		return null;
	}
}
