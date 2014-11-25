package it.polimi.controller.actions.automata.states.transparent;

import it.polimi.controller.actions.ActionInterface;
import it.polimi.model.ModelInterface;
import it.polimi.model.RefinementNode;
import it.polimi.model.impl.states.IntersectionState;
import it.polimi.model.impl.states.State;
import it.polimi.model.impl.transitions.Transition;
import it.polimi.view.ViewInterface;

import java.awt.event.ActionEvent;

import javax.swing.tree.DefaultMutableTreeNode;

@SuppressWarnings("serial")
public class SetTransparent<
	CONSTRAINEDELEMENT extends State,
	STATE extends State, 
	TRANSITION extends Transition>
		extends ActionEvent implements
		ActionInterface<CONSTRAINEDELEMENT,STATE, TRANSITION> {
	
	/**
	 * is the state to be changed into transparent
	 */
	protected STATE state;
	
	/**
	 * is the refinement node where the state is contained
	 */
	protected DefaultMutableTreeNode parent;

	/**
	 * creates a new {@link SetTransparent} action
	 * @param source 
	 * @param id
	 * @param command
	 * @param state: is the state to be changed in transparent
	 * @param parent: is the current refinement node
	 */
	public SetTransparent(Object source, int id, String command, STATE state, DefaultMutableTreeNode parent) {
		super(source, id, command);
		if(state==null){
			throw new NullPointerException("The state to be setted as transparent cannot be null");
		}
		if(parent==null){
			throw new NullPointerException("The refinement node that contains the transparent state cannot be null");
		}
		this.state = state;
		this.parent=parent;
	}

	@Override
	public <INTERSECTIONSTATE extends IntersectionState<STATE>, 
	INTERSECTIONTRANSITION extends Transition> void perform(
			ModelInterface<CONSTRAINEDELEMENT, STATE,  TRANSITION,  INTERSECTIONSTATE,  INTERSECTIONTRANSITION> model,
			ViewInterface<CONSTRAINEDELEMENT, STATE, TRANSITION, INTERSECTIONSTATE, INTERSECTIONTRANSITION> view)
			throws Exception {
		if (model.getModel().isTransparent(state)) {
			model.getModel().getTransparentStates().remove(state);
			model.getModelRefinementHierarchy().removeNodeFromParent(model.getStateRefinementMap().get(state));
		} else {
			
			
			model.getModel().addTransparentState(state);
			
			DefaultMutableTreeNode child=new DefaultMutableTreeNode(
					new RefinementNode<
					STATE, 
					TRANSITION
					>(state, model.getModel().getTransitionFactory(), model.getModel().getStateFactory()));
			model.getStateRefinementMap().put(state, child);
			model.getModelRefinementHierarchy().insertNodeInto(child, 
					parent, 
					model.getModelRefinementHierarchy().getChildCount(parent));
		}
	}
}
