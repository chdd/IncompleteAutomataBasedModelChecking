package it.polimi.view.automaton;

import it.polimi.model.impl.automata.BAImpl;
import it.polimi.model.impl.states.State;
import it.polimi.model.impl.transitions.Transition;
import it.polimi.model.interfaces.automata.BA;
import it.polimi.model.interfaces.states.StateFactory;
import it.polimi.model.interfaces.transitions.TransitionFactory;
import it.polimi.view.menu.Plugin;
import it.polimi.view.menu.actions.ClaimActionFactory;
import it.polimi.view.menu.states.BAStateMenu;
import it.polimi.view.menu.transition.BATransitionMenu;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Paint;
import java.awt.Polygon;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.event.ActionListener;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JPopupMenu;

import org.apache.commons.collections15.Transformer;

import edu.uci.ics.jung.algorithms.layout.AbstractLayout;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.control.DefaultModalGraphMouse;
import edu.uci.ics.jung.visualization.control.EditingModalGraphMouse;
import edu.uci.ics.jung.visualization.control.ModalGraphMouse;
import edu.uci.ics.jung.visualization.decorators.ToStringLabeller;
import edu.uci.ics.jung.visualization.renderers.EdgeLabelRenderer;
import edu.uci.ics.jung.visualization.renderers.Renderer.VertexLabel.Position;


public class BuchiAutomatonJPanel
<
CONSTRAINTELEMENT extends State,
STATE extends State, 
TRANSITION extends Transition,
	AUTOMATON extends BA<STATE,TRANSITION>> 
	extends  VisualizationViewer<STATE,TRANSITION>  {
	
	protected ActionListener view;
	
	protected  RefinementTree<
							CONSTRAINTELEMENT,
							STATE, 
							TRANSITION> parentNode;
	
	private TransitionFactory<TRANSITION> transitionFactory;
	private StateFactory<STATE> stateFactory;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * creates a new {@link JComponent} which prints the automaton a
	 * @param d is the {@link Dimension} of the component
	 * @param a is the {@link BAImpl} to be printed
	 * @throws IllegalArgumentException if the {@link Dimension} d of the {@link BAImpl} d is null
	 */
	public BuchiAutomatonJPanel(AUTOMATON a, ActionListener l, AbstractLayout<STATE, TRANSITION> layout,  RefinementTree<							CONSTRAINTELEMENT,
																										STATE, 
																										TRANSITION> parentNode){
		super(layout);
		if(parentNode!=null){
			this.parentNode=parentNode;
		}
		
		this.view=l;
		
		this.setBackground(Color.WHITE);
		this.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createRaisedBevelBorder(), 
				BorderFactory.createLoweredBevelBorder()));
	
		this.update(a);
		
		this.transitionFactory=a.getTransitionFactory();
		this.stateFactory=a.getStateFactory();
		this.setEditingMode();
	}
	
	public void update(AUTOMATON  a){
		this.setTransformers(a);
		
		this.getGraphLayout().setGraph(a.getGraph());
		this.repaint();
	}
	
	public void setTransformers(AUTOMATON  a){
		// vertex
		this.getRenderer().getVertexLabelRenderer().setPosition(Position.CNTR);
		this.getRenderContext().setVertexLabelTransformer(new ToStringLabeller<STATE>());
		this.getRenderContext().setVertexFillPaintTransformer(this.getPaintTransformer(a));
		this.getRenderContext().setVertexShapeTransformer(this.getShapeTransformer(a));
		this.getRenderContext().setVertexStrokeTransformer(this.getStateStrokeTransformer(a));
		
		
		// edges
		this.getRenderContext().setEdgeLabelTransformer(new ToStringLabeller<TRANSITION>());
		//this.getRenderContext().setEdgeArrowPredicate(new ShowEdgeArrowsPredicate(true, false));
		this.getRenderContext().setEdgeStrokeTransformer(this.getStrokeEdgeStrokeTransformer());
		this.getRenderContext().setLabelOffset(+10);
		EdgeLabelRenderer edgeLabelRenderer=this.getRenderContext().getEdgeLabelRenderer();
		edgeLabelRenderer.setRotateEdgeLabels(true);
		
		this.getRenderer().setEdgeLabelRenderer(new LabelledTransitionRender<CONSTRAINTELEMENT, STATE,TRANSITION>());
		
	}
	
	public void setTranformingMode(){
		DefaultModalGraphMouse<STATE, TRANSITION> gm=new DefaultModalGraphMouse<STATE,TRANSITION>();
		gm.setMode(ModalGraphMouse.Mode.TRANSFORMING);
		this.setGraphMouse(gm);
		this.addKeyListener(gm.getModeKeyListener());
		
	}
	
	protected JPopupMenu getStateMenu(){
		return new BAStateMenu<CONSTRAINTELEMENT, STATE,  TRANSITION, 
				ClaimActionFactory<CONSTRAINTELEMENT, STATE,  TRANSITION>>(
						new ClaimActionFactory
						<CONSTRAINTELEMENT, STATE,  TRANSITION>()
				);
	}
	
	
	protected BuchiAutomatonShapeTransformer getShapeTransformer(AUTOMATON a){
		return new BuchiAutomatonShapeTransformer(a);
	}
	
	protected BuchiAutomatonStatePaintTransformer getPaintTransformer(AUTOMATON a){
		return new BuchiAutomatonStatePaintTransformer(a);
	}
	
	

	protected Transformer<TRANSITION, Stroke> getStrokeEdgeStrokeTransformer() {
		return new BuchiAutomatonEdgeStrokeTransormer();
	}

	protected Transformer<STATE, Stroke> getStateStrokeTransformer(AUTOMATON a) {
		return new BuchiAutomatonStateStrokeTransofmer(a);
	}

	public void setEditingMode() {
		
		EditingModalGraphMouse<STATE,TRANSITION> gm = new EditingModalGraphMouse<STATE,TRANSITION>(
				this.getRenderContext(), 
                this.stateFactory, 
                this.transitionFactory); 
		this.setGraphMouse(gm);
		
		gm.setMode(ModalGraphMouse.Mode.EDITING);
		
        Plugin<CONSTRAINTELEMENT, STATE,  TRANSITION> myPlugin = new Plugin<CONSTRAINTELEMENT, STATE,  TRANSITION>(this.view);
        // Add some popup menus for the edges and vertices to our mouse plugin.
        JPopupMenu edgeMenu =this.getTransitionPopupMenu();
        		
        JPopupMenu vertexMenu =this.getStateMenu();
        myPlugin.setEdgePopup(edgeMenu);
        myPlugin.setVertexPopup(vertexMenu);
        
        gm.remove(gm.getPopupEditingPlugin());
        gm.add(myPlugin);   // Add our new plugin to the mouse
        this.setGraphMouse(gm);	
     }
	
	public JPopupMenu getTransitionPopupMenu(){
		
		return new BATransitionMenu<CONSTRAINTELEMENT, STATE,  TRANSITION, 
				ClaimActionFactory<CONSTRAINTELEMENT, STATE,  TRANSITION>>(
						new ClaimActionFactory
						<CONSTRAINTELEMENT, STATE,  TRANSITION>()
				);
	}
	
	public class BuchiAutomatonStatePaintTransformer implements Transformer<STATE, Paint> {
	
		protected AUTOMATON a;
		
		public BuchiAutomatonStatePaintTransformer(AUTOMATON a){
			this.a=a;
		}
		@Override
		public Paint transform(STATE input) {
			return Color.WHITE;
		}

	}
	
	public class BuchiAutomatonEdgeStrokeTransormer implements Transformer<TRANSITION, Stroke>{

		@Override
		public Stroke transform(TRANSITION input) {
			return new BasicStroke();
		}
	}
	
	
	
	public class BuchiAutomatonShapeTransformer implements Transformer<STATE, Shape>{

	private static final int stateRadius=30;
	private static final int borderRadiusDinstance=4;
	private static final int borderSize=1;
	
	protected AUTOMATON a;
	
	public BuchiAutomatonShapeTransformer(AUTOMATON a){
		this.a=a;
	}

	@Override
	public Shape transform(STATE input) {
		int diameter=stateRadius*2;
		
		int innerRadius=stateRadius-borderRadiusDinstance;
		int innerDiameter=innerRadius*2;
		
		int innerAreaRadius=innerRadius-borderSize;
		int innerAreaDiameter=innerAreaRadius*2;
		
		
		if(a.isAccept(input) && a.isInitial(input)){
			
			Area ret=new Area(new Ellipse2D.Float(-stateRadius, -stateRadius, diameter, diameter));
			ret.subtract(new Area(new Ellipse2D.Float(-innerRadius, -innerRadius, innerDiameter, innerDiameter)));
			ret.add(new Area(new Ellipse2D.Float(-innerAreaRadius, -innerAreaRadius, innerAreaDiameter, innerAreaDiameter)));
			
			Polygon p=new Polygon();
			p.addPoint(-stateRadius+borderRadiusDinstance,  -stateRadius);
			p.addPoint(-stateRadius+borderRadiusDinstance*2, -stateRadius+borderRadiusDinstance*2);
			p.addPoint(-stateRadius,  -stateRadius+borderRadiusDinstance);
			
			ret.add(new Area(p));
			return ret;
		}
		if(a.isAccept(input)){
			Area ret=new Area(new Ellipse2D.Float(-stateRadius, -stateRadius, diameter, diameter));
			ret.subtract(new Area(new Ellipse2D.Float(-innerRadius, -innerRadius, innerDiameter, innerDiameter)));
			ret.add(new Area(new Ellipse2D.Float(-innerAreaRadius, -innerAreaRadius, innerAreaDiameter, innerAreaDiameter)));
			
			return ret;
		}
		if(a.isInitial(input)){
			Area ret=new Area(new Ellipse2D.Float(-stateRadius, -stateRadius, diameter, diameter));
			Polygon p=new Polygon();
			p.addPoint(-stateRadius+borderRadiusDinstance,  -stateRadius);
			p.addPoint(-stateRadius+borderRadiusDinstance*2, -stateRadius+borderRadiusDinstance*2);
			p.addPoint(-stateRadius,  -stateRadius+borderRadiusDinstance);
			
			ret.add(new Area(p));
			return ret;
		}
		else return new Area(new Ellipse2D.Float(-stateRadius, -stateRadius, diameter, diameter));
	}
	}
	public class BuchiAutomatonStateStrokeTransofmer implements Transformer<STATE, Stroke> {

		protected AUTOMATON a;
	
		public BuchiAutomatonStateStrokeTransofmer(AUTOMATON a){
		this.a=a;
		}
	
		@Override
		public Stroke transform(STATE input) {
			return new BasicStroke();
		}
	}
}
