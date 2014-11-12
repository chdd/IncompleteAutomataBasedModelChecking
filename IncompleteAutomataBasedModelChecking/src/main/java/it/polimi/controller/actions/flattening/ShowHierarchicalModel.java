package it.polimi.controller.actions.flattening;

import it.polimi.controller.actions.ActionInterface;
import it.polimi.model.ModelInterface;
import it.polimi.model.RefinementNode;
import it.polimi.model.impl.states.IntersectionState;
import it.polimi.model.impl.states.IntersectionStateFactory;
import it.polimi.model.impl.states.State;
import it.polimi.model.impl.states.StateFactory;
import it.polimi.model.impl.transitions.LabelledTransition;
import it.polimi.model.interfaces.transitions.ConstrainedTransitionFactory;
import it.polimi.model.interfaces.transitions.LabelledTransitionFactory;
import it.polimi.view.ViewInterface;

import javax.swing.tree.DefaultMutableTreeNode;

public class ShowHierarchicalModel<
CONSTRAINEDELEMENT extends State,
STATE extends State, 
STATEFACTORY extends StateFactory<STATE>, 
TRANSITION extends LabelledTransition<CONSTRAINEDELEMENT>, 
TRANSITIONFACTORY extends LabelledTransitionFactory<CONSTRAINEDELEMENT, TRANSITION>>
		implements
		ActionInterface<CONSTRAINEDELEMENT, STATE, STATEFACTORY, TRANSITION, TRANSITIONFACTORY> {

	
	public ShowHierarchicalModel(){
	}
	
	@Override
	public <INTERSECTIONSTATE extends IntersectionState<STATE>, INTERSECTIONSTATEFACTORY extends IntersectionStateFactory<STATE, INTERSECTIONSTATE>, INTERSECTIONTRANSITION extends LabelledTransition<CONSTRAINEDELEMENT>, INTERSECTIONTRANSITIONFACTORY extends ConstrainedTransitionFactory<CONSTRAINEDELEMENT, INTERSECTIONTRANSITION>> void perform(
			ModelInterface<CONSTRAINEDELEMENT, STATE, STATEFACTORY, TRANSITION, TRANSITIONFACTORY, INTERSECTIONSTATE, INTERSECTIONSTATEFACTORY, INTERSECTIONTRANSITION, INTERSECTIONTRANSITIONFACTORY> model,
			ViewInterface<CONSTRAINEDELEMENT, STATE, TRANSITION, INTERSECTIONSTATE, INTERSECTIONTRANSITION, TRANSITIONFACTORY, INTERSECTIONTRANSITIONFACTORY> view)
			throws Exception {
		
		model.setModel(((RefinementNode<CONSTRAINEDELEMENT, STATE, TRANSITION, TRANSITIONFACTORY>)((DefaultMutableTreeNode)model.getModelRefinementHierarchy().getRoot()).getUserObject()).getAutomaton());
	}
	

}

