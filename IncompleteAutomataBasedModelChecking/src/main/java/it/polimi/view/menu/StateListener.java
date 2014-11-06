package it.polimi.view.menu;

import it.polimi.model.impl.states.State;
import it.polimi.model.impl.transitions.LabelledTransition;

import java.awt.event.ActionListener;

public interface StateListener
	<
	CONSTRAINEDELEMENT extends State,
	STATE extends State, 
	TRANSITION extends LabelledTransition<CONSTRAINEDELEMENT>> {
    void setVertexAndView(STATE v, ActionListener l);    
}