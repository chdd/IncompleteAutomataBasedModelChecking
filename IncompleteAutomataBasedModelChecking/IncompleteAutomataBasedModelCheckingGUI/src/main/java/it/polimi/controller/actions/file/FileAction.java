package it.polimi.controller.actions.file;

import it.polimi.controller.actions.ActionInterface;
import it.polimi.model.impl.states.State;
import it.polimi.model.impl.transitions.Transition;

import java.awt.event.ActionEvent;

@SuppressWarnings("serial")
public abstract class FileAction<
CONSTRAINEDELEMENT extends State,
STATE extends State,  
TRANSITION extends Transition> 
extends ActionEvent 
implements ActionInterface<CONSTRAINEDELEMENT,STATE, TRANSITION>{
	
	public FileAction(Object source, int id, String command) {
		super(source, id, command);
	}

}
