package it.polimi.controller.actions.automata.states.initial;

import it.polimi.controller.actions.ActionInterface;
import it.polimi.model.impl.states.State;
import it.polimi.model.impl.transitions.Transition;

import java.awt.event.ActionEvent;

public abstract class SetInitial<
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
	protected STATE state;
	
	public SetInitial(Object source, int id, String command, STATE state){
		super(source, id, command);
		this.state=state;
	} 
}
