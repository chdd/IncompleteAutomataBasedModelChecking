package it.polimi.view.menu;

import it.polimi.model.impl.states.State;
import it.polimi.model.impl.transitions.Transition;

import java.awt.event.ActionListener;

public interface StateListener
	<
	CONSTRAINEDELEMENT extends State,
	STATE extends State, 
	TRANSITION extends Transition> {
    void setVertexAndView(STATE v, ActionListener l);    
}