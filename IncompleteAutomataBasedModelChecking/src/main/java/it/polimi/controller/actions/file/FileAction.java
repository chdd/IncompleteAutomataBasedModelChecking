package it.polimi.controller.actions.file;

import it.polimi.controller.actions.ActionInterface;
import it.polimi.model.impl.states.State;
import it.polimi.model.impl.states.StateFactory;
import it.polimi.model.impl.transitions.LabelledTransition;
import it.polimi.model.interfaces.transitions.LabelledTransitionFactory;

import java.awt.event.ActionEvent;

@SuppressWarnings("serial")
public abstract class FileAction<
CONSTRAINEDELEMENT extends State,
STATE extends State, 
STATEFACTORY extends StateFactory<STATE>, 
TRANSITION extends LabelledTransition<CONSTRAINEDELEMENT>, 
TRANSITIONFACTORY extends LabelledTransitionFactory<CONSTRAINEDELEMENT, TRANSITION>> 
extends ActionEvent 
implements ActionInterface<CONSTRAINEDELEMENT,STATE, STATEFACTORY, TRANSITION, TRANSITIONFACTORY>{
	
	public FileAction(Object source, int id, String command) {
		super(source, id, command);
	}

}
