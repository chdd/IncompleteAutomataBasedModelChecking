package it.polimi.view.automaton;

import it.polimi.model.BuchiAutomaton;
import it.polimi.model.State;
import it.polimi.model.Transition;
import it.polimi.view.automaton.transformers.BuchiAutomatonEdgeStrokeTransormer;
import it.polimi.view.automaton.transformers.BuchiAutomatonPaintTransformer;
import it.polimi.view.automaton.transformers.BuchiAutomatonShapeTransformer;
import it.polimi.view.automaton.transformers.BuchiAutomatonStrokeTransformer;
import it.polimi.view.automaton.transformers.ShowEdgeArrowsPredicate;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import edu.uci.ics.jung.algorithms.layout.CircleLayout;
import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.SparseMultigraph;
import edu.uci.ics.jung.graph.util.EdgeType;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.control.DefaultModalGraphMouse;
import edu.uci.ics.jung.visualization.control.ModalGraphMouse;
import edu.uci.ics.jung.visualization.decorators.ToStringLabeller;



public class BuchiAutomatonJPanel<S extends State, T extends Transition<S>, A extends BuchiAutomaton<S,T>> extends JPanel  {

	

	
	/**
	 * contains the {@link Graph} to be inserted in the component
	 */
	protected Graph<S, T> graph;
	
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
	public BuchiAutomatonJPanel(Dimension d){
		super();
		if(d==null){
			throw new IllegalArgumentException("The dimension cannot be null");
		}
		this.setSize(d);
		this.setMinimumSize(d);
		this.setMaximumSize(d);
		 this.setPreferredSize(d);
		
		this.setBackground(Color.WHITE);
		JOptionPane.showMessageDialog(this, "Download completed", "Question",
		        JOptionPane.INFORMATION_MESSAGE);
		
		
	}
	
	public void update(A  a){
		this.graph = new SparseMultigraph<S,T>();
		this.loadAutomata(a);
		Layout<S,T> layout=new CircleLayout<S,T>(this.graph);
		Dimension size=new Dimension(this.getSize().width-50, this.getSize().height-50);
		layout.setSize(size);
		VisualizationViewer<S, T> vv=new VisualizationViewer<S,T>(layout);
		vv.setSize(this.getSize());
		vv.setMinimumSize(size);
		vv.setMaximumSize(size);
		vv.setPreferredSize(size);
		vv.setVisible(true);
		vv.setFocusable(true);
		vv.getRenderContext().setVertexFillPaintTransformer(this.getPaintTransformer(a));
		vv.getRenderContext().setVertexLabelTransformer(new ToStringLabeller<S>());
		vv.getRenderContext().setVertexShapeTransformer(this.getShapeTransformer(a));
		vv.getRenderContext().setEdgeLabelTransformer(new ToStringLabeller<T>());
		vv.getRenderContext().setEdgeArrowPredicate(new ShowEdgeArrowsPredicate<S, T>(true, false));
		vv.getRenderContext().setVertexStrokeTransformer(this.getStrokeTransformer(a));
		vv.getRenderContext().setEdgeStrokeTransformer(this.getStrokeEdgeTransformer(a));
		vv.setBackground(Color.WHITE);
		vv.setBorder(new LineBorder(Color.getColor("myColor")));
		vv.setAutoscrolls(true);
		
		DefaultModalGraphMouse<S, T> gm=new DefaultModalGraphMouse<S,T>();
		gm.setMode(ModalGraphMouse.Mode.TRANSFORMING);
		vv.setGraphMouse(gm);
		
		this.add(vv);
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
	 	 
		for(S s: a.getStates()){
			graph.addVertex(s);
		}
		for(S s: a.getStates()){
			if(a.getTransitionsWithSource(s)!=null){
				for(T t: a.getTransitionsWithSource(s)){
					graph.addEdge(t, s, t.getDestination(), EdgeType.DIRECTED);
				}  
			}
		}
	}

	
	
}
