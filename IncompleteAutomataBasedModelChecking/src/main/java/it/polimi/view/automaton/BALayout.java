package it.polimi.view.automaton;

import java.awt.geom.Point2D;

import it.polimi.model.impl.states.State;
import it.polimi.model.impl.transitions.LabelledTransition;
import edu.uci.ics.jung.algorithms.layout.FRLayout;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.util.Pair;

public class BALayout
	<STATE extends State,
	TRANSITION extends LabelledTransition> 
	extends FRLayout<STATE, TRANSITION> {

	public BALayout(Graph<STATE, TRANSITION> g) {
		super(g);
	}
	
	protected void calcAttraction(TRANSITION e) {
    	Pair<STATE> endpoints = getGraph().getEndpoints(e);
    	
        STATE v1 = endpoints.getFirst();
        STATE v2 = endpoints.getSecond();
        
        if(v1.equals(v2)){
        	Point2D p1 = transform(v1);
            double xDelta = p1.getX();
            double yDelta = p1.getY();
            double deltaLength = Math.sqrt((xDelta * xDelta)
                    + (yDelta * yDelta));
            double dx = (xDelta / deltaLength);
            double dy = (yDelta / deltaLength) ;
        }
        else{
      //  	super.calcAttraction(e);
        }
  
    }
	
	

}
