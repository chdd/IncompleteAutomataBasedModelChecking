package it.polimi.view.automaton;

import it.polimi.model.BuchiAutomaton;
import it.polimi.model.State;
import it.polimi.model.Transition;
import it.polimi.view.stylesheets.BuchiAutomatonStyleSheet;

import java.awt.Dimension;
import java.awt.Point;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JLabel;

import com.mxgraph.layout.mxFastOrganicLayout;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.view.mxGraph;

/**
 * is used to print the {@link BuchiAutomaton}
 * @author claudiomenghi
 *
 * @param <S> is the type of the {@link State} of the {@link BuchiAutomaton}
 * @param <T> is the type of the {@link Transition} of the {@link BuchiAutomaton}
 * @param <A> is the {@link BuchiAutomaton} to be printed
 */
public class AutJLabel<S extends State, T extends Transition<S>, A extends BuchiAutomaton<S,T>> extends JLabel {

	/**
	 * contains the width of the {@link State}
	 */
	protected int stateWidth;
	
	/**
	 * contains the height of the {@link State}
	 */
	protected int stateHeigth;
	
	/**
	 * contains the distance between the {@link State} over the X axis
	 */
	protected int nodeXstep;
	
	/**
	 * contains the distance between the {@link State} over the Y axis
	 */
	protected int nodeYstep;
	
	/**
	 * contains the number of the states for each row
	 */
	protected int numPerRow;
	
	/**
	 * contains the Automaton to be printed
	 */
	protected A  a=null;
	
	/**
	 * contains the {@link Dimension} of the {@link JLabel}
	 */
	protected Dimension d;
	
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * contains the graphical version of the {@link BuchiAutomaton} which is printed
	 */
	protected mxGraph graph;
	protected Map<State, Object> vertexMap;
	
	/**
	 * creates a new {@link JLabel} which is used to show the {@link BuchiAutomaton}
	 * @param d is the {@link Dimension} of the {@link JLabel}
	 * @param a is the {@link BuchiAutomaton} to be printed
	 * @throws IllegalArgumentException if the {@link Dimension} d or the {@link BuchiAutomaton} a is null
	 */
	public AutJLabel(Dimension d, A  a){
		if(d==null){
			throw new IllegalArgumentException("The dimension d cannot be null");
		}
		if(a==null){
			throw new IllegalArgumentException("The automaton a cannot be null");
		}
		
		this.a=a;
		this.d=d;
		
		// computes the width of the states 
		this.stateWidth=d.width/10;
		// computes the height of the states
		this.stateHeigth=d.height/10;
		// computes the distance on the x axis between the states
		this.nodeXstep=d.width/5;
		// computes the distance on the y axis between the states
		this.nodeYstep=d.width/5;
		// computes the number of the nodes for each row
		numPerRow=d.width/(nodeXstep+stateWidth)+1;
		
		// creates the mxGraph
		this.graph = new mxGraph();
		
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
		graphComponent.setGridVisible(true);
		graphComponent.setGridStyle(mxGraphComponent.GRID_STYLE_LINE);
		graphComponent.setAutoscrolls(true);
		graphComponent.setDragEnabled(true);
		graphComponent.setEventsEnabled(false);
		graphComponent.setSize(d);
		
		// sets the style sheet for the current automata
		this.setStyleSheet();
		
	   
		
		this.add(graphComponent);
		this.loadAutomata(graph);
		this.setVisible(true);
		this.setEnabled(false);
	}
	
	protected void setStyleSheet(){
		this.graph.setStylesheet(new BuchiAutomatonStyleSheet());
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
		this.graph.setStylesheet(new BuchiAutomatonStyleSheet());
		   
		this.loadAutomata(graph);
		this.setVisible(true);
		this.setEnabled(false);
		this.repaint();
		
	}
	private void removeGraph(){
		this.graph.removeCells();
	}
	private Point calculatePosition(int num){
		return new Point(num%numPerRow*nodeXstep,num/numPerRow*nodeYstep);
	}
	
	protected void addState(Map<State, Object> vertexMap, S s, Point position, mxGraph graph, Object defaultParent){
		  if(a.isAccept(s)){
    		  vertexMap.put(s, graph.insertVertex(defaultParent, s.getName(), s.getName(), position.x, position.y, stateWidth, stateHeigth, "RegularFinalState"));
          }
		  else{
			  if(a.isInitial(s)){
				  vertexMap.put(s, graph.insertVertex(defaultParent, s.getName(), s.getName(), position.x, position.y, stateWidth, stateHeigth, "InitialState"));
					
			  }
			  else{
				  vertexMap.put(s, graph.insertVertex(defaultParent, s.getName(), s.getName(), position.x, position.y, stateWidth, stateHeigth, "RegularState"));
			  }
		 }
	}
	
	protected void loadAutomata(mxGraph graph){
	  	  Object defaultParent = graph.getDefaultParent();
	        
	  	  vertexMap=new HashMap<State, Object>();
	  	  
	  	  int index=0;
	  	  
	  	  for(S s: a.getStates()){
	  		  
	  		  Point position=this.calculatePosition(index);
	  		  this.addState(vertexMap, s, position, graph, defaultParent);
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
	 

}
