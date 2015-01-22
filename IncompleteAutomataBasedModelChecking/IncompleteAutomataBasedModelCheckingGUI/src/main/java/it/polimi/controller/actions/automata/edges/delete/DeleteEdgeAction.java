package it.polimi.controller.actions.automata.edges.delete;

import it.polimi.controller.actions.ActionInterface;
import it.polimi.model.impl.states.State;
import it.polimi.model.impl.transitions.Transition;

import java.awt.event.ActionEvent;

public abstract class DeleteEdgeAction<
CONSTRAINEDELEMENT extends State,
	STATE extends State,
	TRANSITION extends Transition>
		extends ActionEvent implements
		ActionInterface<CONSTRAINEDELEMENT, STATE, TRANSITION> {

	/**
 * 
 */
	private static final long serialVersionUID = 1L;
	protected TRANSITION transition;

	public DeleteEdgeAction(Object source, int id, String command,
			TRANSITION transition) {
		super(source, id, command);
		this.transition = transition;
	}
}