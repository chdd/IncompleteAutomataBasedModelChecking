package it.polimi.view.automaton;

import it.polimi.model.BuchiAutomaton;
import it.polimi.model.State;
import it.polimi.model.Transition;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.JLabel;

import com.mxgraph.layout.mxFastOrganicLayout;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.util.mxConstants;
import com.mxgraph.util.mxUtils;
import com.mxgraph.view.mxGraph;
import com.mxgraph.view.mxStylesheet;

public class AutJLabel<S extends State, T extends Transition<S>, A extends BuchiAutomaton<S,T>> extends JLabel {

	protected static int nodeXsize=80;
	protected static int nodeYsize=30;
	protected static int nodeXstep=120;
	protected static int nodeYstep=60;
	protected int numPerRow=3;
	
	protected A  a=null;
	protected Dimension d;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	protected mxGraph graph;
	protected Map<State, Object> vertexMap;
	
	
	
	public AutJLabel(Dimension d, A  a){
		this.a=a;
		this.d=d;
		numPerRow=d.width/nodeXstep;
		this.graph = new mxGraph();
		graph.getModel().beginUpdate();
		graph.getModel().endUpdate();
		graph.setGridEnabled(true);
		graph.setCellsEditable(false);
		graph.setCellsBendable(false);
		graph.setAllowDanglingEdges(false);
		graph.setCellsDisconnectable(false);
	    	
		mxFastOrganicLayout layout = new mxFastOrganicLayout(graph);

        //set all properties
        layout.setMinDistanceLimit(10);
        layout.setInitialTemp(10);
        layout.setForceConstant(10);
        layout.setDisableEdgeStyle(true);

        //layout graph
        layout.execute(graph.getDefaultParent());
        
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
	     
		
		this.add(graphComponent);
		this.loadAutomata(graph);
		this.setVisible(true);
		this.setEnabled(false);
	}
	
	
	
	public void updateGraph(A  a ){
		this.a=a;
		System.out.println(a);
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
		  if(a.isAccept(s)){
    		  vertexMap.put(s, graph.insertVertex(defaultParent, s.getName(), s.getName(), position.x, position.y, nodeXsize, nodeYsize, "RegularFinalState"));
          }
		  else{
			  if(a.isInitial(s)){
				  vertexMap.put(s, graph.insertVertex(defaultParent, s.getName(), s.getName(), position.x, position.y, nodeXsize, nodeYsize, "InitialState"));
					
			  }
			  else{
				  vertexMap.put(s, graph.insertVertex(defaultParent, s.getName(), s.getName(), position.x, position.y, nodeXsize, nodeYsize, "RegularState"));
			  }
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
   	  
   	  	Hashtable<String, Object> styleRegularState = new Hashtable<String, Object>();
         styleRegularState.put(mxConstants.STYLE_FILLCOLOR, mxUtils.getHexColorString(Color.WHITE));
         styleRegularState.put(mxConstants.STYLE_STROKEWIDTH, 1.5);
         styleRegularState.put(mxConstants.STYLE_STROKECOLOR, mxUtils.getHexColorString(Color.BLACK));
         styleRegularState.put(mxConstants.STYLE_SHAPE, mxConstants.SHAPE_ELLIPSE);
         styleRegularState.put(mxConstants.STYLE_PERIMETER, mxConstants.PERIMETER_ELLIPSE);
         
         Hashtable<String, Object> styleInitialState = new Hashtable<String, Object>();
         styleInitialState.put(mxConstants.STYLE_FILLCOLOR, mxUtils.getHexColorString(Color.GREEN));
         styleInitialState.put(mxConstants.STYLE_STROKEWIDTH, 1.5);
         styleInitialState.put(mxConstants.STYLE_STROKECOLOR, mxUtils.getHexColorString(Color.BLACK));
         styleInitialState.put(mxConstants.STYLE_SHAPE, mxConstants.SHAPE_ELLIPSE);
         styleInitialState.put(mxConstants.STYLE_PERIMETER, mxConstants.PERIMETER_ELLIPSE);
        
         
         Hashtable<String, Object> styleRegularFinalState = new Hashtable<String, Object>();
         styleRegularFinalState.put(mxConstants.STYLE_FILLCOLOR, mxUtils.getHexColorString(Color.WHITE));
         styleRegularFinalState.put(mxConstants.STYLE_STROKEWIDTH, 1.5);
         styleRegularFinalState.put(mxConstants.STYLE_STROKECOLOR, mxUtils.getHexColorString(Color.BLACK));
         styleRegularFinalState.put(mxConstants.STYLE_SHAPE, mxConstants.SHAPE_DOUBLE_ELLIPSE);
         styleRegularFinalState.put(mxConstants.STYLE_PERIMETER, mxConstants.PERIMETER_ELLIPSE);
         
         
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
         
         Hashtable<String, Object> stylemixedState = new Hashtable<String, Object>();
         stylemixedState.put(mxConstants.STYLE_FILLCOLOR, mxUtils.getHexColorString(Color.GREEN));
         stylemixedState.put(mxConstants.STYLE_STROKEWIDTH, 1.5);
         stylemixedState.put(mxConstants.STYLE_STROKECOLOR, mxUtils.getHexColorString(Color.BLACK));
         stylemixedState.put(mxConstants.STYLE_SHAPE, mxConstants.SHAPE_ELLIPSE);
         stylemixedState.put(mxConstants.STYLE_PERIMETER, mxConstants.PERIMETER_ELLIPSE);
         
         
         Hashtable<String, Object> styleMixedFinalState = new Hashtable<String, Object>();
         styleMixedFinalState.put(mxConstants.STYLE_FILLCOLOR, mxUtils.getHexColorString(Color.GREEN));
         styleMixedFinalState.put(mxConstants.STYLE_STROKEWIDTH, 1.5);
         styleMixedFinalState.put(mxConstants.STYLE_STROKECOLOR, mxUtils.getHexColorString(Color.BLACK));
         styleMixedFinalState.put(mxConstants.STYLE_SHAPE, mxConstants.SHAPE_DOUBLE_ELLIPSE);
         styleMixedFinalState.put(mxConstants.STYLE_PERIMETER, mxConstants.PERIMETER_ELLIPSE);
         
         Hashtable<String, Object> styleEdges = new Hashtable<String, Object>();
         styleEdges.put(mxConstants.STYLE_STROKECOLOR, mxUtils.getHexColorString(Color.BLACK));
        
         stylesheet.putCellStyle("TransparentState", styleTransparentState);
         stylesheet.putCellStyle("InitialState", styleInitialState);
         
         
         stylesheet.putCellStyle("TransparentFinalState", styleTransparentFinalState);
         
         stylesheet.putCellStyle("RegularState", styleRegularState);
         stylesheet.putCellStyle("RegularFinalState", styleRegularFinalState);
         
         stylesheet.putCellStyle("MixedState", stylemixedState);
         stylesheet.putCellStyle("MixedFinalState", styleMixedFinalState);
         
         
         stylesheet.putCellStyle("EdgesStyle", styleEdges);
         
        
     }

}
