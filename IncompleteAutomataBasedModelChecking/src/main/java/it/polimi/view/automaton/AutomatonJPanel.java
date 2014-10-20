package it.polimi.view.automaton;

import it.polimi.model.elements.states.State;
import it.polimi.model.impl.transitions.LabelledTransition;
import it.polimi.model.interfaces.automata.drawable.DrawableBA;
import it.polimi.model.interfaces.transitions.LabelledTransitionFactoryInterface;
import it.polimi.view.trasformers.ShowEdgeArrowsPredicate;

import java.awt.Color;
import java.awt.Paint;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;

import org.apache.commons.collections15.Transformer;

import edu.uci.ics.jung.algorithms.layout.FRLayout;
import edu.uci.ics.jung.graph.Graph;
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
@SuppressWarnings("serial")
public abstract class AutomatonJPanel<S extends State, T extends LabelledTransition,
TRANSITIONFACTORY extends LabelledTransitionFactoryInterface<T>, A extends DrawableBA<S,T, TRANSITIONFACTORY>> extends VisualizationViewer<S,T> {

	/**
	 * contains the {@link Graph} to be inserted in the component
	 */
	
	protected ActionListener view;
	
	public AutomatonJPanel(A  a, ActionListener view){
		super(new FRLayout<S,T>(a));
		
		this.view=view;
		
		this.setBackground(Color.WHITE);
		this.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createRaisedBevelBorder(), 
				BorderFactory.createLoweredBevelBorder()));
	
		this.update(a);
	}
	
	public void setTransformers(A  a){
		this.getRenderContext().setVertexLabelTransformer(new ToStringLabeller<S>());
		this.getRenderContext().setVertexFillPaintTransformer(this.getPaintTransformer(a));
		this.getRenderContext().setVertexLabelTransformer(new ToStringLabeller<S>());
		this.getRenderContext().setVertexShapeTransformer(this.getShapeTransformer(a));
		this.getRenderContext().setVertexStrokeTransformer(this.getStateStrokeTransformer(a));
		
		this.getRenderContext().setEdgeLabelTransformer(new ToStringLabeller<T>());
		this.getRenderContext().setEdgeArrowPredicate(new ShowEdgeArrowsPredicate<S, T>(true, false));
		this.getRenderContext().setEdgeStrokeTransformer(this.getStrokeEdgeStrokeTransformer());
		
		this.getRenderer().getVertexLabelRenderer().setPosition(Renderer.VertexLabel.Position.S);
		EdgeLabelRenderer edgeLabelRenderer=this.getRenderContext().getEdgeLabelRenderer();
		edgeLabelRenderer.setRotateEdgeLabels(true);
		this.getRenderContext().setLabelOffset(20);
		
	}
	
	public void update(A  a){
		this.setTransformers(a);
		
		this.getGraphLayout().setGraph(a);
		this.repaint();
	}
	
	
	
	
	public void setTranformingMode(){
		DefaultModalGraphMouse<S, T> gm=new DefaultModalGraphMouse<S,T>();
		gm.setMode(ModalGraphMouse.Mode.TRANSFORMING);
		this.setGraphMouse(gm);
		this.addKeyListener(gm.getModeKeyListener());
		
	}
	
	public abstract void setEditingMode();
	
	
	protected abstract Transformer<S, Shape> getShapeTransformer(A a);
	
	protected abstract Transformer<T, Stroke> getStrokeEdgeStrokeTransformer();
	protected abstract Transformer<S, Stroke> getStateStrokeTransformer(A a);
	
	
	protected abstract Transformer<S,Paint> getPaintTransformer(A a);
}
