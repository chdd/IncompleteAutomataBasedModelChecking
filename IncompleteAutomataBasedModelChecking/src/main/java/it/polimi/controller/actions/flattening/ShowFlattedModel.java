package it.polimi.controller.actions.flattening;

import it.polimi.controller.actions.ActionInterface;
import it.polimi.model.ModelInterface;
import it.polimi.model.RefinementNode;
import it.polimi.model.impl.states.IntersectionState;
import it.polimi.model.impl.states.State;
import it.polimi.model.impl.transitions.Transition;
import it.polimi.model.interfaces.automata.IBA;
import it.polimi.view.ViewInterface;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

public class ShowFlattedModel<
CONSTRAINEDELEMENT extends State,
STATE extends State, 
TRANSITION extends Transition>
		implements
		ActionInterface<CONSTRAINEDELEMENT, STATE, TRANSITION> {

	Map<STATE, DefaultMutableTreeNode> newMap=new HashMap<STATE, DefaultMutableTreeNode>();
	
	@SuppressWarnings("unchecked")
	@Override
	public <INTERSECTIONSTATE extends IntersectionState<STATE>, 
	INTERSECTIONTRANSITION extends Transition> void perform(
			ModelInterface<CONSTRAINEDELEMENT, STATE, TRANSITION, INTERSECTIONSTATE, INTERSECTIONTRANSITION> model,
			ViewInterface<CONSTRAINEDELEMENT, STATE, TRANSITION, INTERSECTIONSTATE, INTERSECTIONTRANSITION> view)
			throws Exception {
		
		model.cleanFlatstateRefinementMap();
		DefaultMutableTreeNode ret=this.getRefinedIBA((DefaultMutableTreeNode) model.getModelRefinementHierarchy().getRoot());
		model.setModel(((RefinementNode<STATE, TRANSITION>)ret.getUserObject()).
				getAutomaton());
		model.setFlatstateRefinementMap(newMap);
		model.setflattenModelRefinement(new DefaultTreeModel(ret));
	}
	
	@SuppressWarnings("unchecked")
	public <INTERSECTIONSTATE extends IntersectionState<STATE>, 
	INTERSECTIONTRANSITION extends Transition>
		DefaultMutableTreeNode getRefinedIBA(DefaultMutableTreeNode currNode){
		
		DefaultMutableTreeNode retNode=new DefaultMutableTreeNode();
		IBA<STATE, TRANSITION> retAutomaton=((RefinementNode<
		STATE, 
		TRANSITION>) currNode.getUserObject()).getAutomaton().clone();
		
		Enumeration<DefaultMutableTreeNode> nodes=currNode.children();
		while(nodes.hasMoreElements()){
			DefaultMutableTreeNode nextNode=nodes.nextElement();
			DefaultMutableTreeNode flattenedNode=this.getRefinedIBA(nextNode);
			retNode.add(flattenedNode);
			
			IBA<STATE, TRANSITION> childAutomaton=((RefinementNode<STATE, TRANSITION>)flattenedNode.getUserObject()).getAutomaton();
			
			if(!childAutomaton.getStates().isEmpty()){
				retAutomaton.replace(((RefinementNode<
						STATE, 
						TRANSITION>) nextNode.getUserObject()).getState(), childAutomaton);
			}	
		}
		
		retNode.setUserObject(new RefinementNode< STATE, TRANSITION>((
				(RefinementNode<STATE, TRANSITION>) currNode.getUserObject()
				).getState(), retAutomaton));
		newMap.put(
				((RefinementNode<STATE, TRANSITION>) currNode.getUserObject()
				).getState(), retNode);
		return retNode;
	}
}
