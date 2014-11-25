package it.polimi.controller.actions.automata.states.delete;

import it.polimi.model.ModelInterface;
import it.polimi.model.impl.states.IntersectionState;
import it.polimi.model.impl.states.State;
import it.polimi.model.impl.transitions.Transition;
import it.polimi.view.ViewInterface;

import javax.swing.tree.DefaultMutableTreeNode;

@SuppressWarnings("serial")
public class DeleteModelState<
CONSTRAINEDELEMENT extends State, 
STATE extends State, 
TRANSITION extends Transition>	extends DeleteState<CONSTRAINEDELEMENT, STATE, TRANSITION> {


	protected DefaultMutableTreeNode parent;
	 
	public DeleteModelState(Object source, int id, String command, STATE state, DefaultMutableTreeNode parent) {
		super(source, id, command, state);
		this.parent=parent;
	}

	@Override
	public <INTERSECTIONSTATE extends IntersectionState<STATE>, 
	INTERSECTIONTRANSITION extends Transition> void perform(
			ModelInterface<CONSTRAINEDELEMENT, STATE, TRANSITION,  INTERSECTIONSTATE, INTERSECTIONTRANSITION> model,
			ViewInterface<CONSTRAINEDELEMENT, STATE, TRANSITION, INTERSECTIONSTATE, INTERSECTIONTRANSITION> view)
			throws Exception {
		model.getModel().removeState(state);
		if(model.getModel().isTransparent(state)){
			model.getStateRefinementMap().get(state).removeFromParent();
			model.getStateRefinementMap().remove(state);
		}
	}

}
