package it.polimi.view.automaton;

import it.polimi.model.BuchiAutomaton;
import it.polimi.model.State;
import it.polimi.model.Transition;
import it.polimi.view.automaton.transformers.BuchiAutomatonEdgeStrokeTransormer;
import it.polimi.view.automaton.transformers.BuchiAutomatonPaintTransformer;
import it.polimi.view.automaton.transformers.BuchiAutomatonShapeTransformer;
import it.polimi.view.automaton.transformers.BuchiAutomatonStrokeTransformer;
import it.polimi.view.automaton.transformers.ShowEdgeArrowsPredicate;
import it.polimi.view.incompleteautomaton.editing.IncompleteBuchiAutomatonJStateCreator;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.ContainerEvent;
import java.awt.event.ContainerListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import edu.uci.ics.jung.algorithms.layout.CircleLayout;
import edu.uci.ics.jung.algorithms.layout.FRLayout;
import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.SparseMultigraph;
import edu.uci.ics.jung.graph.util.EdgeType;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.control.DefaultModalGraphMouse;
import edu.uci.ics.jung.visualization.control.ModalGraphMouse;
import edu.uci.ics.jung.visualization.decorators.ToStringLabeller;
import edu.uci.ics.jung.visualization.renderers.EdgeLabelRenderer;
import edu.uci.ics.jung.visualization.renderers.Renderer;


public class BuchiAutomatonJPanel<S extends State, T extends Transition<S>, A extends BuchiAutomaton<S,T>> extends JPanel{

	/**
	 * contains the {@link Graph} to be inserted in the component
	 */
	protected Graph<S, T> graph;
	
	private Layout<S,T> layout;
	private VisualizationViewer<S, T> vv;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * creates a new {@link JComponent} which prints the automaton a
	 * @param d is the {@link Dimension} of the component
	 * @param a is the {@link BuchiAutomaton} to be printed
	 * @throws IllegalArgumentException if the {@link Dimension} d of the {@link BuchiAutomaton} d is null
	 */
	public BuchiAutomatonJPanel(Dimension d, ActionListener container){
		super();
		if(d==null){
			throw new IllegalArgumentException("The dimension cannot be null");
		}
		this.setSize(d);
		this.setMinimumSize(d);
		this.setMaximumSize(d);
		this.setPreferredSize(d);
		
		this.setBackground(Color.WHITE);
		this.graph = new SparseMultigraph<S,T>();
		this.layout=new CircleLayout<S,T>(this.graph);
		Dimension size=new Dimension(this.getSize().width-50, this.getSize().height-50);
		layout.setSize(size);
		this.vv=new VisualizationViewer<S,T>(layout);
		vv.setSize(d);
		vv.setMinimumSize(d);
		vv.setMaximumSize(d);
		vv.setPreferredSize(d);
		vv.setVisible(true);
		vv.setFocusable(true);
		vv.setBackground(Color.WHITE);
		vv.setBorder(new LineBorder(Color.getColor("myColor")));
		vv.setAutoscrolls(true);
		
		
		// add the key listener after this instruction if the user press p can moves the nodes in the graph
		DefaultModalGraphMouse<S, T> gm=new DefaultModalGraphMouse<S,T>();
		gm.setMode(ModalGraphMouse.Mode.TRANSFORMING);
		vv.setGraphMouse(gm);
		vv.addKeyListener(gm.getModeKeyListener());
		
		this.add(vv);
		
	}
	
	public void update(A  a){
		
		this.loadAutomata(a);
		this.layout.reset();
		//this.layout=new CircleLayout<S,T>(this.graph);
		this.layout=new FRLayout<S,T>(this.graph);
		//this.layout=new KKLayout<S,T>(this.graph);
		this.vv.setGraphLayout(layout);
		
		vv.getRenderContext().setVertexFillPaintTransformer(this.getPaintTransformer(a));
		vv.getRenderContext().setVertexLabelTransformer(new ToStringLabeller<S>());
		vv.getRenderContext().setVertexShapeTransformer(this.getShapeTransformer(a));
		vv.getRenderContext().setEdgeLabelTransformer(new ToStringLabeller<T>());
		vv.getRenderContext().setEdgeArrowPredicate(new ShowEdgeArrowsPredicate<S, T>(true, false));
		vv.getRenderContext().setVertexStrokeTransformer(this.getStrokeTransformer(a));
		vv.getRenderContext().setEdgeStrokeTransformer(this.getStrokeEdgeTransformer(a));
		vv.getRenderer().getVertexLabelRenderer().setPosition(Renderer.VertexLabel.Position.S);
		
		EdgeLabelRenderer edgeLabelRenderer=vv.getRenderContext().getEdgeLabelRenderer();
		edgeLabelRenderer.setRotateEdgeLabels(true);
		vv.getRenderContext().setLabelOffset(20);
		
		vv.repaint();
		
		this.repaint();
	}
	
	protected BuchiAutomatonShapeTransformer<S, T, A> getShapeTransformer(A a){
		return new BuchiAutomatonShapeTransformer<S, T, A>(a);
	}
	
	protected BuchiAutomatonPaintTransformer<S,T,A> getPaintTransformer(A a){
		return new BuchiAutomatonPaintTransformer<S,T,A>(a);
	}
	
	protected BuchiAutomatonStrokeTransformer<S, T, A> getStrokeTransformer(A a){
		return new BuchiAutomatonStrokeTransformer<S, T, A>(a);
	}
	
	protected BuchiAutomatonEdgeStrokeTransormer<S, T> getStrokeEdgeTransformer(A a){
		return new BuchiAutomatonEdgeStrokeTransormer<S, T>();
	}
	
	protected void loadAutomata(A a){
		this.graph = new SparseMultigraph<S,T>();
		for(S s: a.getStates()){
			this.graph.addVertex(s);
		}
		for(S s: a.getStates()){
			if(a.getTransitionsWithSource(s)!=null){
				for(T t: a.getTransitionsWithSource(s)){
					this.graph.addEdge(t, s, t.getDestination(), EdgeType.DIRECTED);
				}  
			}
		}	
	}

	

	

	
	

	

	

	
}
