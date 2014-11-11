package it.polimi.view.menu.actions;

import it.polimi.controller.actions.automata.edges.ChangeEdgeLabel;
import it.polimi.controller.actions.automata.edges.delete.DeleteEdgeAction;
import it.polimi.controller.actions.automata.states.accepting.SetAccepting;
import it.polimi.controller.actions.automata.states.initial.SetInitial;
import it.polimi.controller.actions.automata.states.transparent.SetTransparent;
import it.polimi.controller.viewRefinement.ViewHierarchyStateRefinementAction;
import it.polimi.model.impl.states.State;
import it.polimi.model.impl.states.StateFactory;
import it.polimi.model.impl.transitions.LabelledTransition;
import it.polimi.model.interfaces.transitions.LabelledTransitionFactory;

import javax.swing.tree.DefaultMutableTreeNode;

public abstract class ActionTypesInterface<
CONSTRAINEDELEMENT extends State,
STATE extends State, 
STATEFACTORY extends StateFactory<STATE>, 
TRANSITION extends LabelledTransition<CONSTRAINEDELEMENT>, 
TRANSITIONFACTORY extends LabelledTransitionFactory<CONSTRAINEDELEMENT, TRANSITION>> {

	public abstract DeleteEdgeAction<CONSTRAINEDELEMENT, STATE, STATEFACTORY, TRANSITION, TRANSITIONFACTORY> deleteEdgeAction(Object source, int id, String command, TRANSITION transition);
	
	public abstract ChangeEdgeLabel<CONSTRAINEDELEMENT, STATE, STATEFACTORY, TRANSITION, TRANSITIONFACTORY> getChangingLabelAction(Object source, int id, String command, String edgeLabel, TRANSITION transition);
	
	// STATES
	public abstract SetAccepting<CONSTRAINEDELEMENT, STATE, STATEFACTORY, TRANSITION, TRANSITIONFACTORY> setAccepting(Object source, int id, String command, STATE state);
	public abstract SetInitial<CONSTRAINEDELEMENT, STATE, STATEFACTORY, TRANSITION, TRANSITIONFACTORY> setInitial(Object source, int id, String command, STATE state);
	
	public SetTransparent<CONSTRAINEDELEMENT, STATE, STATEFACTORY, TRANSITION, TRANSITIONFACTORY> setTransparent(Object source, int id, String command, STATE state, DefaultMutableTreeNode parent){
		return new SetTransparent<CONSTRAINEDELEMENT, STATE, STATEFACTORY, TRANSITION, TRANSITIONFACTORY>(source, id, command, state, parent);
	}
	
	public ViewHierarchyStateRefinementAction<CONSTRAINEDELEMENT, STATE, STATEFACTORY, TRANSITION, TRANSITIONFACTORY> getRefineStateAction(
			Object source, int id, String command, STATE state){
		
		return null;
	}
}
