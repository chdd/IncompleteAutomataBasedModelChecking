package it.polimi.controller.actions.automata.edges.delete;

import it.polimi.model.ModelInterface;
import it.polimi.model.impl.states.IntersectionState;
import it.polimi.model.impl.states.State;
import it.polimi.model.impl.transitions.Transition;
import it.polimi.view.ViewInterface;

import javax.swing.tree.DefaultMutableTreeNode;

@SuppressWarnings("serial")
public class DeleteEdgeModel<
	CONSTRAINEDELEMENT extends State,
	STATE extends State, 
	TRANSITION extends Transition>
extends
		DeleteEdgeAction<CONSTRAINEDELEMENT ,STATE, TRANSITION> {

	protected DefaultMutableTreeNode parent;
	
	
	public DeleteEdgeModel(Object source, int id, String command,
			TRANSITION transition, DefaultMutableTreeNode parent) {
		super(source, id, command, transition);
		this.parent=parent;
	}

	@Override
	public <INTERSECTIONSTATE extends IntersectionState<STATE>, 
	INTERSECTIONTRANSITION extends Transition> 
	void perform(
			ModelInterface<CONSTRAINEDELEMENT, STATE,  TRANSITION,  INTERSECTIONSTATE, INTERSECTIONTRANSITION> model,
			ViewInterface<CONSTRAINEDELEMENT, STATE, TRANSITION, INTERSECTIONSTATE, INTERSECTIONTRANSITION> view)
			throws Exception {
		model.getModel().removeTransition(transition);
	}

}
