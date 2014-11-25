package it.polimi.controller.viewRefinement;

import it.polimi.controller.actions.ActionInterface;
import it.polimi.model.ModelInterface;
import it.polimi.model.RefinementNode;
import it.polimi.model.impl.states.IntersectionState;
import it.polimi.model.impl.states.State;
import it.polimi.model.impl.transitions.Transition;
import it.polimi.view.ViewInterface;

import java.awt.event.ActionEvent;


@SuppressWarnings("serial")
public class ViewFlatStateRefinementAction<
CONSTRAINEDELEMENT extends State,
STATE extends State, 
TRANSITION extends Transition>		extends ActionEvent implements
ActionInterface<CONSTRAINEDELEMENT, STATE,  TRANSITION> {

	protected RefinementNode<STATE, TRANSITION> refinement;
	
	public ViewFlatStateRefinementAction(Object source, int id, String command, RefinementNode<STATE, TRANSITION> refinement) {
		super(source, id, command);
		this.refinement=refinement;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <INTERSECTIONSTATE extends IntersectionState<STATE>, 
	INTERSECTIONTRANSITION extends Transition> void perform(
			ModelInterface<CONSTRAINEDELEMENT, STATE, TRANSITION, INTERSECTIONSTATE, INTERSECTIONTRANSITION> model,
			ViewInterface<CONSTRAINEDELEMENT, STATE, TRANSITION, INTERSECTIONSTATE, INTERSECTIONTRANSITION> view)
			throws Exception {
		
		model.setModel(((RefinementNode<STATE, TRANSITION>)model.getFlatstateRefinementMap().get(refinement.getState()).getUserObject()).getAutomaton());
	
	}
	
}


