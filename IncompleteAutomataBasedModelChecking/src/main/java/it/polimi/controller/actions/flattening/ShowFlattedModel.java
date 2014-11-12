package it.polimi.controller.actions.flattening;

import it.polimi.controller.actions.ActionInterface;
import it.polimi.model.ModelInterface;
import it.polimi.model.RefinementNode;
import it.polimi.model.impl.states.IntersectionState;
import it.polimi.model.impl.states.IntersectionStateFactory;
import it.polimi.model.impl.states.State;
import it.polimi.model.impl.states.StateFactory;
import it.polimi.model.impl.transitions.LabelledTransition;
import it.polimi.model.interfaces.automata.drawable.DrawableIBA;
import it.polimi.model.interfaces.transitions.ConstrainedTransitionFactory;
import it.polimi.model.interfaces.transitions.LabelledTransitionFactory;
import it.polimi.view.ViewInterface;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

public class ShowFlattedModel<
CONSTRAINEDELEMENT extends State,
STATE extends State, 
STATEFACTORY extends StateFactory<STATE>, 
TRANSITION extends LabelledTransition<CONSTRAINEDELEMENT>, 
TRANSITIONFACTORY extends LabelledTransitionFactory<CONSTRAINEDELEMENT, TRANSITION>>
		implements
		ActionInterface<CONSTRAINEDELEMENT, STATE, STATEFACTORY, TRANSITION, TRANSITIONFACTORY> {

	Map<STATE, DefaultMutableTreeNode> newMap=new HashMap<STATE, DefaultMutableTreeNode>();
	
	@SuppressWarnings("unchecked")
	@Override
	public <INTERSECTIONSTATE extends IntersectionState<STATE>, INTERSECTIONSTATEFACTORY extends IntersectionStateFactory<STATE, INTERSECTIONSTATE>, INTERSECTIONTRANSITION extends LabelledTransition<CONSTRAINEDELEMENT>, INTERSECTIONTRANSITIONFACTORY extends ConstrainedTransitionFactory<CONSTRAINEDELEMENT, INTERSECTIONTRANSITION>> void perform(
			ModelInterface<CONSTRAINEDELEMENT, STATE, STATEFACTORY, TRANSITION, TRANSITIONFACTORY, INTERSECTIONSTATE, INTERSECTIONSTATEFACTORY, INTERSECTIONTRANSITION, INTERSECTIONTRANSITIONFACTORY> model,
			ViewInterface<CONSTRAINEDELEMENT, STATE, TRANSITION, INTERSECTIONSTATE, INTERSECTIONTRANSITION, TRANSITIONFACTORY, INTERSECTIONTRANSITIONFACTORY> view)
			throws Exception {
		
		model.cleanFlatstateRefinementMap();
		DefaultMutableTreeNode ret=this.getRefinedIBA((DefaultMutableTreeNode) model.getModelRefinementHierarchy().getRoot());
		model.setModel(((RefinementNode<CONSTRAINEDELEMENT, STATE, TRANSITION, TRANSITIONFACTORY>)ret.getUserObject()).
				getAutomaton());
		model.setFlatstateRefinementMap(newMap);
		model.setflattenModelRefinement(new DefaultTreeModel(ret));
	}
	
	@SuppressWarnings("unchecked")
	public <INTERSECTIONSTATE extends IntersectionState<STATE>, INTERSECTIONSTATEFACTORY extends IntersectionStateFactory<STATE, INTERSECTIONSTATE>, INTERSECTIONTRANSITION extends LabelledTransition<CONSTRAINEDELEMENT>, INTERSECTIONTRANSITIONFACTORY extends ConstrainedTransitionFactory<CONSTRAINEDELEMENT, INTERSECTIONTRANSITION>>
		DefaultMutableTreeNode getRefinedIBA(DefaultMutableTreeNode currNode){
		
		DefaultMutableTreeNode retNode=new DefaultMutableTreeNode();
		DrawableIBA<CONSTRAINEDELEMENT, STATE, TRANSITION, TRANSITIONFACTORY> retAutomaton=((RefinementNode<CONSTRAINEDELEMENT,
		STATE, 
		TRANSITION, 
		TRANSITIONFACTORY>) currNode.getUserObject()).getAutomaton().clone();
		
		Enumeration<DefaultMutableTreeNode> nodes=currNode.children();
		while(nodes.hasMoreElements()){
			DefaultMutableTreeNode nextNode=nodes.nextElement();
			DefaultMutableTreeNode flattenedNode=this.getRefinedIBA(nextNode);
			retNode.add(flattenedNode);
			
			DrawableIBA<CONSTRAINEDELEMENT, STATE, TRANSITION, TRANSITIONFACTORY> childAutomaton=((RefinementNode<CONSTRAINEDELEMENT, STATE, TRANSITION, TRANSITIONFACTORY>)flattenedNode.getUserObject()).getAutomaton();
			
			if(!childAutomaton.getStates().isEmpty()){
				retAutomaton.replace(((RefinementNode<CONSTRAINEDELEMENT,
						STATE, 
						TRANSITION, 
						TRANSITIONFACTORY>) nextNode.getUserObject()).getState(), childAutomaton);
			}	
		}
		
		retNode.setUserObject(new RefinementNode<CONSTRAINEDELEMENT, STATE, TRANSITION, TRANSITIONFACTORY>((
				(RefinementNode<CONSTRAINEDELEMENT,STATE, TRANSITION, TRANSITIONFACTORY>) currNode.getUserObject()
				).getState(), retAutomaton));
		newMap.put(
				((RefinementNode<CONSTRAINEDELEMENT,STATE, TRANSITION, TRANSITIONFACTORY>) currNode.getUserObject()
				).getState(), retNode);
		return retNode;
	}
}
