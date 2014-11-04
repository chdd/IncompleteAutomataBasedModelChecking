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

import edu.uci.ics.jung.visualization.VisualizationViewer;


public interface TransitionListener
	<STATE extends State,
	TRANSITION extends LabelledTransition> {
 
	void setEdgeAndView(TRANSITION e, VisualizationViewer<STATE, TRANSITION> visComp,  ActionListener visView); 
   
}