package it.polimi.controller.actions.automata.edges;

import it.polimi.controller.actions.ActionInterface;
import it.polimi.model.impl.states.State;
import it.polimi.model.impl.transitions.Transition;

import java.awt.event.ActionEvent;

public abstract class ChangeEdgeLabel
	<
	CONSTRAINEDELEMENT extends State,
	STATE extends State, 
	TRANSITION extends Transition
	>
extends ActionEvent implements
		ActionInterface<CONSTRAINEDELEMENT, STATE, TRANSITION> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected String edgeLabel;
	protected TRANSITION transition;
	
	public ChangeEdgeLabel(Object source, int id, String command, String edgeLabel, TRANSITION transition){
		super(source, id, command);
		this.edgeLabel=edgeLabel;
		this.transition=transition;
	}
}
