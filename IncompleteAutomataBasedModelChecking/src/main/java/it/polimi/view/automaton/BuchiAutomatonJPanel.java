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
import it.polimi.view.trasformers.BuchiAutomatonEdgeStrokeTransormer;
import it.polimi.view.trasformers.BuchiAutomatonShapeTransformer;
import it.polimi.view.trasformers.BuchiAutomatonStatePaintTransformer;
import it.polimi.view.trasformers.BuchiAutomatonStateStrokeTransofmer;

import java.awt.Dimension;
import java.awt.Stroke;
import java.awt.event.ActionListener;

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
	
	
	protected BuchiAutomatonShapeTransformer<STATE,TRANSITION, TRANSITIONFACTORY, BA> getShapeTransformer(BA a){
		return new BuchiAutomatonShapeTransformer<STATE, TRANSITION, TRANSITIONFACTORY,BA>(a);
	}
	
	protected BuchiAutomatonStatePaintTransformer<STATE,TRANSITION, TRANSITIONFACTORY, BA> getPaintTransformer(BA a){
		return new BuchiAutomatonStatePaintTransformer<STATE,TRANSITION, TRANSITIONFACTORY, BA>(a);
	}
	
	

	@Override
	protected Transformer<TRANSITION, Stroke> getStrokeEdgeStrokeTransformer() {
		return new BuchiAutomatonEdgeStrokeTransormer<TRANSITION>();
	}

	@Override
	protected Transformer<STATE, Stroke> getStateStrokeTransformer(BA a) {
		return new BuchiAutomatonStateStrokeTransofmer<STATE, TRANSITION, TRANSITIONFACTORY, BA>(a);
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
	
	
}
