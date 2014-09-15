package it.polimi.view.intersectionautomaton;

import it.polimi.model.IntersectionAutomaton;
import it.polimi.model.IntersectionState;
import it.polimi.model.State;
import it.polimi.model.Transition;
import it.polimi.view.extendedautomaton.ExtendedAutJLabel;
import it.polimi.view.style.IntersectionAutomatonStyleSheet;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.util.Hashtable;
import java.util.Map;

import com.mxgraph.util.mxConstants;
import com.mxgraph.util.mxUtils;
import com.mxgraph.view.mxGraph;
import com.mxgraph.view.mxStylesheet;

@SuppressWarnings("serial")
public class IntersectionAutJLabel<S1 extends State, T1 extends Transition<S1>,
									S extends IntersectionState<S1>, T extends Transition<S>,
									A extends IntersectionAutomaton<S1, T1, S, T>> 
									extends ExtendedAutJLabel<S,T,A>{

	public IntersectionAutJLabel(Dimension d, A a) {
		super(d,a);
	}
	
	protected void printState(Map<State, Object> vertexMap, S s, Point position, mxGraph graph, Object defaultParent){
		
		  if(a.isMixed(s)){
        	  
        	  if(a.isAccept(s)){
      			  vertexMap.put(s, graph.insertVertex(defaultParent, s.getName(), s.getName(), position.x, position.y, nodeXsize, nodeYsize, "MixedFinalState"));
      		  }
      		  else{
      			  vertexMap.put(s, graph.insertVertex(defaultParent, s.getName(), s.getName(), position.x, position.y, nodeXsize, nodeYsize, "MixedState"));
      		  }
          }
		  else{
			  super.printState(vertexMap, s, position, graph, defaultParent);
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
	 protected void settingStyle(mxStylesheet stylesheet){
		 this.settingStyle();
   	  
         Hashtable<String, Object> stylemixedState = new Hashtable<String, Object>();
         stylemixedState.put(mxConstants.STYLE_FILLCOLOR, mxUtils.getHexColorString(Color.WHITE));
         stylemixedState.put(mxConstants.STYLE_STROKEWIDTH, 1.5);
         stylemixedState.put(mxConstants.STYLE_STROKECOLOR, mxUtils.getHexColorString(Color.BLACK));
         stylemixedState.put(mxConstants.STYLE_SHAPE, mxConstants.SHAPE_ELLIPSE);
         stylemixedState.put(mxConstants.STYLE_PERIMETER, mxConstants.PERIMETER_ELLIPSE);
         stylemixedState.put(mxConstants.STYLE_DASHED, true);
         
         
         Hashtable<String, Object> styleMixedEdges = new Hashtable<String, Object>();
         styleMixedEdges.put(mxConstants.STYLE_STROKECOLOR, mxUtils.getHexColorString(Color.BLACK));
         styleMixedEdges.put(mxConstants.STYLE_DASHED, true);
         
         
         Hashtable<String, Object> styleMixedFinalState = new Hashtable<String, Object>();
         styleMixedFinalState.put(mxConstants.STYLE_FILLCOLOR, mxUtils.getHexColorString(Color.WHITE));
         styleMixedFinalState.put(mxConstants.STYLE_STROKEWIDTH, 1.5);
         styleMixedFinalState.put(mxConstants.STYLE_STROKECOLOR, mxUtils.getHexColorString(Color.BLACK));
         styleMixedFinalState.put(mxConstants.STYLE_SHAPE, mxConstants.SHAPE_DOUBLE_ELLIPSE);
         styleMixedFinalState.put(mxConstants.STYLE_PERIMETER, mxConstants.PERIMETER_ELLIPSE);
         styleMixedFinalState.put(mxConstants.STYLE_DASHED, true);
         
         
         stylesheet.putCellStyle("StyleMixedEdges", styleMixedEdges);
         stylesheet.putCellStyle("MixedState", stylemixedState);
         stylesheet.putCellStyle("MixedFinalState", styleMixedFinalState);
     }
	 
	 protected void settingStyle(){
		 this.graph.setStylesheet(new IntersectionAutomatonStyleSheet());
	 }


}
