package it.polimi.view.menu;

import java.awt.event.ActionListener;

import it.polimi.model.impl.states.State;
import it.polimi.model.impl.transitions.LabelledTransition;
import edu.uci.ics.jung.visualization.VisualizationViewer;

public interface StateMenuListener
	<STATE extends State, 
	TRANSITION extends LabelledTransition> {
    void setVertexAndView(STATE v, VisualizationViewer<STATE,TRANSITION> visView, ActionListener l);    
}