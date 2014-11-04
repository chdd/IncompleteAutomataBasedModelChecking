package it.polimi.view.automaton;

import it.polimi.model.impl.automata.BAImpl;
import it.polimi.model.impl.states.State;
import it.polimi.model.impl.states.StateFactory;
import it.polimi.model.impl.transitions.LabelledTransition;
import it.polimi.model.interfaces.automata.drawable.DrawableBA;
import it.polimi.model.interfaces.transitions.LabelledTransitionFactory;
import it.polimi.view.menu.Actions;
import it.polimi.view.menu.BAStateMenu;
import it.polimi.view.menu.Plugin;

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

import javax.swing.JComponent;
import javax.swing.JPopupMenu;

import org.apache.commons.collections15.Transformer;

import edu.uci.ics.jung.algorithms.layout.AbstractLayout;
import edu.uci.ics.jung.visualization.control.EditingModalGraphMouse;
import edu.uci.ics.jung.visualization.control.ModalGraphMouse;


public class BuchiAutomatonJPanel
	<STATE extends State, 
	STATEFACTORY extends StateFactory<STATE>,
	TRANSITION extends LabelledTransition,
	TRANSITIONFACTORY extends LabelledTransitionFactory<TRANSITION>,
	BA extends DrawableBA<STATE,TRANSITION, TRANSITIONFACTORY>> 
	extends AutomatonJPanel<STATE, STATEFACTORY, TRANSITION, TRANSITIONFACTORY,BA>  {

	
	private TRANSITIONFACTORY transitionFactory;
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
	public BuchiAutomatonJPanel(BA a, ActionListener l, AbstractLayout<STATE, TRANSITION> layout){
		super(a, l, layout); 
		this.transitionFactory=a.getTransitionFactory();
		this.setEditingMode();
	}
	
	protected JPopupMenu getStateMenu(){
		 return new BAStateMenu(view);
	}
	
	
	protected BuchiAutomatonShapeTransformer getShapeTransformer(BA a){
		return new BuchiAutomatonShapeTransformer(a);
	}
	
	protected BuchiAutomatonStatePaintTransformer getPaintTransformer(BA a){
		return new BuchiAutomatonStatePaintTransformer(a);
	}
	
	

	@Override
	protected Transformer<TRANSITION, Stroke> getStrokeEdgeStrokeTransformer() {
		return new BuchiAutomatonEdgeStrokeTransormer<TRANSITION>();
	}

	@Override
	protected Transformer<STATE, Stroke> getStateStrokeTransformer(BA a) {
		return new BuchiAutomatonStateStrokeTransofmer(a);
	}

	@Override
	public void setEditingMode() {
		
		EditingModalGraphMouse<STATE,TRANSITION> gm = new EditingModalGraphMouse<STATE,TRANSITION>(this.getRenderContext(), 
                new StateFactory<STATE>(), this.transitionFactory); 
		this.setGraphMouse(gm);
		
		gm.setMode(ModalGraphMouse.Mode.EDITING);
		
        Plugin myPlugin = new Plugin(view);
        // Add some popup menus for the edges and vertices to our mouse plugin.
        JPopupMenu edgeMenu = new Actions.EdgeMenu();
        JPopupMenu vertexMenu =this.getStateMenu();
        myPlugin.setEdgePopup(edgeMenu);
        myPlugin.setVertexPopup(vertexMenu);
        
        gm.remove(gm.getPopupEditingPlugin());
        gm.add(myPlugin);   // Add our new plugin to the mouse
        this.setGraphMouse(gm);	
     }
	
	public class BuchiAutomatonStatePaintTransformer implements Transformer<STATE, Paint> {
	
		protected BA a;
		
		public BuchiAutomatonStatePaintTransformer(BA a){
			this.a=a;
		}
		@Override
		public Paint transform(STATE input) {
			return Color.WHITE;
		}

	}
	
	public class BuchiAutomatonEdgeStrokeTransormer<T extends LabelledTransition> implements Transformer<T, Stroke>{

		@Override
		public Stroke transform(T input) {
			return new BasicStroke();
		}
	}
	
	public class BuchiAutomatonShapeTransformer implements Transformer<STATE, Shape>{

	private static final int stateRadius=30;
	private static final int borderRadiusDinstance=4;
	private static final int borderSize=1;
	
	protected BA a;
	
	public BuchiAutomatonShapeTransformer(BA a){
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

		protected BA a;
	
		public BuchiAutomatonStateStrokeTransofmer(BA a){
		this.a=a;
		}
	
		@Override
		public Stroke transform(STATE input) {
			return new BasicStroke();
		}
	}
}
