package it.polimi.view.automaton;

import it.polimi.model.impl.states.State;
import it.polimi.model.impl.states.StateFactory;
import it.polimi.model.impl.transitions.LabelledTransition;
import it.polimi.model.interfaces.automata.drawable.DrawableBA;
import it.polimi.model.interfaces.transitions.LabelledTransitionFactory;

import java.awt.Color;
import java.awt.Paint;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;

import org.apache.commons.collections15.Predicate;
import org.apache.commons.collections15.Transformer;

import edu.uci.ics.jung.algorithms.layout.AbstractLayout;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.util.Context;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.control.DefaultModalGraphMouse;
import edu.uci.ics.jung.visualization.control.ModalGraphMouse;
import edu.uci.ics.jung.visualization.decorators.ToStringLabeller;
import edu.uci.ics.jung.visualization.renderers.EdgeLabelRenderer;
import edu.uci.ics.jung.visualization.renderers.Renderer.VertexLabel.Position;

/**
 * visualizes the automaton
 * @author claudiomenghi
 *
 */
@SuppressWarnings("serial")
public abstract class AutomatonJPanel
	<STATE extends State, 
	STATEFACTORY extends StateFactory<STATE>,
	TRANSITION extends LabelledTransition,
	TRANSITIONFACTORY extends LabelledTransitionFactory<TRANSITION>,
	BA extends DrawableBA<STATE,TRANSITION, TRANSITIONFACTORY>> 
	extends VisualizationViewer<STATE,TRANSITION> {

	/**
	 * contains the {@link Graph} to be inserted in the component
	 */
	
	protected ActionListener view;
	
	public AutomatonJPanel(BA  a, ActionListener view, AbstractLayout<STATE, TRANSITION> layout){
		super(layout);
		
		this.view=view;
		
		this.setBackground(Color.WHITE);
		this.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createRaisedBevelBorder(), 
				BorderFactory.createLoweredBevelBorder()));
	
		this.update(a);
	}
	
	public void setTransformers(BA  a){
		// vertex
		this.getRenderer().getVertexLabelRenderer().setPosition(Position.CNTR);
		this.getRenderContext().setVertexLabelTransformer(new ToStringLabeller<STATE>());
		this.getRenderContext().setVertexFillPaintTransformer(this.getPaintTransformer(a));
		
		this.getRenderContext().setVertexShapeTransformer(this.getShapeTransformer(a));
		this.getRenderContext().setVertexStrokeTransformer(this.getStateStrokeTransformer(a));
		
		// edges
		this.getRenderContext().setEdgeLabelTransformer(new ToStringLabeller<TRANSITION>());
		this.getRenderContext().setEdgeArrowPredicate(new ShowEdgeArrowsPredicate(true, false));
		this.getRenderContext().setEdgeStrokeTransformer(this.getStrokeEdgeStrokeTransformer());
		
		EdgeLabelRenderer edgeLabelRenderer=this.getRenderContext().getEdgeLabelRenderer();
		edgeLabelRenderer.setRotateEdgeLabels(true);
		this.getRenderContext().setLabelOffset(40);
		
	}
	
	public void update(BA  a){
		this.setTransformers(a);
		
		this.getGraphLayout().setGraph(a);
		this.repaint();
	}
	
	public void setTranformingMode(){
		DefaultModalGraphMouse<STATE, TRANSITION> gm=new DefaultModalGraphMouse<STATE,TRANSITION>();
		gm.setMode(ModalGraphMouse.Mode.TRANSFORMING);
		this.setGraphMouse(gm);
		this.addKeyListener(gm.getModeKeyListener());
		
	}
	
	public abstract void setEditingMode();
	
	
	protected abstract Transformer<STATE, Shape> getShapeTransformer(BA a);
	
	protected abstract Transformer<TRANSITION, Stroke> getStrokeEdgeStrokeTransformer();
	protected abstract Transformer<STATE, Stroke> getStateStrokeTransformer(BA a);
	
	
	protected abstract Transformer<STATE,Paint> getPaintTransformer(BA a);
	
	
	public class ShowEdgeArrowsPredicate implements Predicate<Context<Graph<STATE, TRANSITION>, TRANSITION>> {
		
		protected boolean show_d;
		protected boolean show_u;
	
		public ShowEdgeArrowsPredicate(boolean show_d, boolean show_u)
		{
			this.show_d = show_d;
			this.show_u = show_u;
		}
	
		@Override
		public boolean evaluate(Context<Graph<STATE, TRANSITION>, TRANSITION> context) {
			if (show_d) {
				return true;
			}
			return false;
		}
	}
}
