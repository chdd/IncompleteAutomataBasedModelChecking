package it.polimi.controller.actions.automata.states.delete;

import it.polimi.controller.actions.ActionInterface;
import it.polimi.model.impl.states.State;
import it.polimi.model.impl.states.StateFactory;
import it.polimi.model.impl.transitions.LabelledTransition;
import it.polimi.model.interfaces.transitions.LabelledTransitionFactory;

import java.awt.event.ActionEvent;

public abstract class DeleteState
	<STATE extends State, 
	STATEFACTORY extends StateFactory<STATE>, 
	TRANSITION extends LabelledTransition, 
	TRANSITIONFACTORY extends LabelledTransitionFactory<TRANSITION>
	>
	extends ActionEvent implements
	ActionInterface<STATE, STATEFACTORY, TRANSITION, TRANSITIONFACTORY> {

	/**
	 * 
	 */	
	private static final long serialVersionUID = 1L;
	protected STATE state;

	public DeleteState(Object source, int id, String command, STATE state){
		super(source, id, command);
		this.state=state;
	} 

}
