package it.polimi.view.menu;

import it.polimi.model.impl.states.State;
import it.polimi.model.impl.transitions.LabelledTransition;

import java.awt.event.ActionListener;

public interface StateListener
	<STATE extends State, 
	TRANSITION extends LabelledTransition> {
    void setVertexAndView(STATE v, ActionListener l);    
}