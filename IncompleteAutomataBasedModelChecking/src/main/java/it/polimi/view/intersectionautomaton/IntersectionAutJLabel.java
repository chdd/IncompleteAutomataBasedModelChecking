package it.polimi.view.intersectionautomaton;

import it.polimi.model.IntersectionAutomaton;
import it.polimi.model.IntersectionState;
import it.polimi.model.State;
import it.polimi.model.Transition;
import it.polimi.view.extendedautomaton.ExtendedAutJLabel;
import it.polimi.view.stylesheets.IntersectionAutomatonStyleSheet;

import java.awt.Dimension;
import java.awt.Point;
import java.util.Map;

import com.mxgraph.view.mxGraph;

@SuppressWarnings("serial")
public class IntersectionAutJLabel<S1 extends State, T1 extends Transition<S1>,
									S extends IntersectionState<S1>, T extends Transition<S>,
									A extends IntersectionAutomaton<S1, T1, S, T>> 
									extends ExtendedAutJLabel<S,T,A>{

	public IntersectionAutJLabel(Dimension d, A a) {
		super(d,a);

	}
	protected void setStyleSheet(){
		this.graph.setStylesheet(new IntersectionAutomatonStyleSheet());
	}
	
	
	protected void addState(Map<State, Object> vertexMap, S s, Point position, mxGraph graph, Object defaultParent){
		
		  if(a.isMixed(s)){
        	  
        	  if(a.isAccept(s)){
      			  vertexMap.put(s, graph.insertVertex(defaultParent, s.getName(), s.getName(), position.x, position.y, stateWidth, stateHeigth, "MixedFinalState"));
      		  }
      		  else{
      			  vertexMap.put(s, graph.insertVertex(defaultParent, s.getName(), s.getName(), position.x, position.y, stateWidth, stateHeigth, "MixedState"));
      		  }
          }
		  else{
			  super.addState(vertexMap, s, position, graph, defaultParent);
		  }
	}
	protected void printTransition(Map<State, Object> vertexMap, T t, S s, mxGraph graph, Object defaultParent){
		 if(a.isMixed(s) && a.isMixed(t.getDestination())){
			 graph.insertEdge(defaultParent, null, t.getCharacter(), vertexMap.get(s), vertexMap.get(t.getDestination()), "StyleMixedEdges");
		 }
		 else{
			 super.printTransition(vertexMap, t, s, graph, defaultParent);
		 }
	}
}
