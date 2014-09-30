package it.polimi.view.automaton;

import it.polimi.model.BuchiAutomaton;
import it.polimi.model.State;
import it.polimi.model.Transition;
import it.polimi.view.buchiautomaton.transformers.ShowEdgeArrowsPredicate;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Paint;
import java.awt.Shape;
import java.awt.Stroke;

import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import org.apache.commons.collections15.Transformer;

import edu.uci.ics.jung.algorithms.layout.CircleLayout;
import edu.uci.ics.jung.algorithms.layout.FRLayout;
import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.SparseMultigraph;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.control.DefaultModalGraphMouse;
import edu.uci.ics.jung.visualization.control.ModalGraphMouse;
import edu.uci.ics.jung.visualization.decorators.ToStringLabeller;
import edu.uci.ics.jung.visualization.renderers.EdgeLabelRenderer;
import edu.uci.ics.jung.visualization.renderers.Renderer;

/**
 * visualizes the automaton
 * @author claudiomenghi
 *
 */
public abstract class AutomatonJPanel<S extends State, T extends Transition<S>, A extends BuchiAutomaton<S,T>> extends JPanel {

	/**
	 * contains the {@link Graph} to be inserted in the component
	 */
	protected Graph<S, T> graph;
	
	protected Layout<S,T> layout;
	protected VisualizationViewer<S, T> vv;
	
	public AutomatonJPanel(Dimension d){
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
	
	protected abstract void loadAutomata(A a);
	
	protected abstract Transformer<S, Shape> getShapeTransformer(A a);
	
	protected abstract Transformer<S, Stroke> getStrokeTransformer(A a);
	
	protected abstract Transformer<T, Stroke> getStrokeEdgeTransformer(A a);
	
	protected abstract Transformer<S,Paint> getPaintTransformer(A a);
		
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
}
