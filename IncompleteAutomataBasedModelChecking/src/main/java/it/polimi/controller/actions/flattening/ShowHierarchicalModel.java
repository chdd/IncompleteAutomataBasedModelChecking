package it.polimi.controller.actions.flattening;

import it.polimi.controller.actions.ActionInterface;
import it.polimi.model.ModelInterface;
import it.polimi.model.RefinementNode;
import it.polimi.model.impl.states.IntersectionState;
import it.polimi.model.impl.states.State;
import it.polimi.model.impl.transitions.Transition;
import it.polimi.view.ViewInterface;

import javax.swing.tree.DefaultMutableTreeNode;

public class ShowHierarchicalModel<
CONSTRAINEDELEMENT extends State,
STATE extends State, 
TRANSITION extends Transition>
		implements
		ActionInterface<CONSTRAINEDELEMENT, STATE,  TRANSITION> {

	
	public ShowHierarchicalModel(){
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <INTERSECTIONSTATE extends IntersectionState<STATE>, 
	INTERSECTIONTRANSITION extends Transition> void perform(
			ModelInterface<CONSTRAINEDELEMENT, STATE, TRANSITION, INTERSECTIONSTATE, INTERSECTIONTRANSITION> model,
			ViewInterface<CONSTRAINEDELEMENT, STATE, TRANSITION, INTERSECTIONSTATE, INTERSECTIONTRANSITION> view)
			throws Exception {
		
		model.setModel(((RefinementNode<STATE, TRANSITION>)((DefaultMutableTreeNode)model.getModelRefinementHierarchy().getRoot()).getUserObject()).getAutomaton());
	}
	

}

