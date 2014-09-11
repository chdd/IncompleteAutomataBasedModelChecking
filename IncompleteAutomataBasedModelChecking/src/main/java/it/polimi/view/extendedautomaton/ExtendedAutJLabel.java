package it.polimi.view.extendedautomaton;

import it.polimi.model.IncompleteBuchiAutomaton;
import it.polimi.model.State;
import it.polimi.model.Transition;
import it.polimi.view.automaton.AutJLabel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.Map.Entry;

import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.util.mxConstants;
import com.mxgraph.util.mxUtils;
import com.mxgraph.view.mxGraph;
import com.mxgraph.view.mxStylesheet;

public class ExtendedAutJLabel<S extends State, T extends Transition<S>, A extends IncompleteBuchiAutomaton<S,T>> extends AutJLabel<S, T, A> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	public ExtendedAutJLabel(Dimension d, A  a ){
		super(d,a);
	}
	
	
	
	public void updateGraph(A  a ){
		this.a=a;
		this.removeGraph();
		mxGraphComponent graphComponent = new mxGraphComponent(graph);
		graphComponent.getViewport().setOpaque(true);
		//graphComponent.getViewport().setBackground(Color.lightGray);
		graphComponent.setGridVisible(true);
		graphComponent.setGridStyle(mxGraphComponent.GRID_STYLE_LINE);
		graphComponent.setAutoscrolls(true);
		graphComponent.setDragEnabled(true);
		graphComponent.setEventsEnabled(false);
		graphComponent.setSize(d);
		mxStylesheet stylesheet = graph.getStylesheet();
	    this.settingStyle(stylesheet);
		this.loadAutomata(graph);
		this.setVisible(true);
		this.setEnabled(false);
		this.repaint();
		
	}
	private void removeGraph(){
		Object[] cells=new Object[this.vertexMap.size()];
		int i=0;
		for(Entry<State, Object> e: this.vertexMap.entrySet()){
			cells[i]=e.getValue();
			i++;
		}
		this.graph.removeCells(cells);
	}
	private Point calculatePosition(int num){
		return new Point(num%numPerRow*nodeXstep,num/numPerRow*nodeYstep);
	}
	
	protected void printState(Map<State, Object> vertexMap, S s, Point position, mxGraph graph, Object defaultParent){
		if(a.isTransparent(s)){
    		  
    		  if(a.isAccept(s)){
    			  vertexMap.put(s, graph.insertVertex(defaultParent, s.getName(), s.getName(), position.x, position.y, nodeXsize, nodeYsize, "TransparentFinalState"));
    		  }
    		  else{
    			  vertexMap.put(s, graph.insertVertex(defaultParent, s.getName(), s.getName(), position.x, position.y, nodeXsize, nodeYsize, "TransparentState"));
    		  }
    	  }
    	  else{
    		 super.printState(vertexMap, s, position, graph, defaultParent);
    	  }

	}
	
	protected void loadAutomata(mxGraph graph){
	  	  Object defaultParent = graph.getDefaultParent();
	        
	  	  vertexMap=new HashMap<State, Object>();
	  	  
	  	  int index=0;
	  	  
	  	  for(S s: a.getStates()){
	  		  
	  		  Point position=this.calculatePosition(index);
	  		  this.printState(vertexMap, s, position, graph, defaultParent);
			  index++;
	      }
	      for(S s: a.getStates()){
	    	  if(a.getTransitionsWithSource(s)!=null){
	    		  for(T t: a.getTransitionsWithSource(s)){

	    	  		  this.printTransition(vertexMap, t, s,  graph, defaultParent);
		          }  
	    	  }
	      }
	}
	protected void printTransition(Map<State, Object> vertexMap, T t, S s, mxGraph graph, Object defaultParent){
	 	  graph.insertEdge(defaultParent, null, t.getCharacter(), vertexMap.get(s), vertexMap.get(t.getDestination()), "EdgesStyle");
	       
	}
	 protected void settingStyle(mxStylesheet stylesheet){
   	  	super.settingStyle(stylesheet);
         
         
         Hashtable<String, Object> styleTransparentState = new Hashtable<String, Object>();
         styleTransparentState.put(mxConstants.STYLE_FILLCOLOR, mxUtils.getHexColorString(Color.GRAY));
         styleTransparentState.put(mxConstants.STYLE_STROKEWIDTH, 1.5);
         styleTransparentState.put(mxConstants.STYLE_STROKECOLOR, mxUtils.getHexColorString(Color.BLACK));
         styleTransparentState.put(mxConstants.STYLE_SHAPE, mxConstants.SHAPE_ELLIPSE);
         styleTransparentState.put(mxConstants.STYLE_PERIMETER, mxConstants.PERIMETER_ELLIPSE);
         
         Hashtable<String, Object> styleTransparentFinalState = new Hashtable<String, Object>();
         styleTransparentFinalState.put(mxConstants.STYLE_FILLCOLOR, mxUtils.getHexColorString(Color.GRAY));
         styleTransparentFinalState.put(mxConstants.STYLE_STROKEWIDTH, 1.5);
         styleTransparentFinalState.put(mxConstants.STYLE_STROKECOLOR, mxUtils.getHexColorString(Color.BLACK));
         styleTransparentFinalState.put(mxConstants.STYLE_SHAPE, mxConstants.SHAPE_DOUBLE_ELLIPSE);
         styleTransparentFinalState.put(mxConstants.STYLE_PERIMETER, mxConstants.PERIMETER_ELLIPSE);
         
           
         Hashtable<String, Object> styleEdges = new Hashtable<String, Object>();
         styleEdges.put(mxConstants.STYLE_STROKECOLOR, mxUtils.getHexColorString(Color.BLACK));
        
         stylesheet.putCellStyle("TransparentState", styleTransparentState);
         stylesheet.putCellStyle("TransparentFinalState", styleTransparentFinalState);
         
         stylesheet.putCellStyle("EdgesStyle", styleEdges);
         
        
     }

}
