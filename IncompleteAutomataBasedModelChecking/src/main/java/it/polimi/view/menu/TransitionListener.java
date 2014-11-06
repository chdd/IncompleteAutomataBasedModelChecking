package it.polimi.view.menu;

/*
 * EdgeMenuListener.java
*
* Created on March 21, 2007, 2:41 PM; Updated May 29, 2007
* Copyright March 21, 2007 Grotto Networking
*/

import it.polimi.model.impl.states.State;
import it.polimi.model.impl.transitions.LabelledTransition;

import java.awt.event.ActionListener;


public interface TransitionListener
	<
	CONSTRAINEDELEMENT extends State,
	STATE extends State,
	TRANSITION extends LabelledTransition<CONSTRAINEDELEMENT>> {
 
	void setEdgeAndView(TRANSITION e, ActionListener visView); 
   
}