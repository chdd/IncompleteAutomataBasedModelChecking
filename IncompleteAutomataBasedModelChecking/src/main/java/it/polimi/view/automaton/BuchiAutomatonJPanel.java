package it.polimi.view.automaton;

import it.polimi.model.elements.states.FactoryState;
import it.polimi.model.elements.states.State;
import it.polimi.model.impl.automata.BAImpl;
import it.polimi.model.impl.transitions.LabelledTransition;
import it.polimi.model.impl.transitions.LabelledTransitionFactory;
import it.polimi.model.interfaces.automata.drawable.DrawableBA;
import it.polimi.model.interfaces.transitions.LabelledTransitionFactoryInterface;
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

import edu.uci.ics.jung.visualization.control.EditingModalGraphMouse;
import edu.uci.ics.jung.visualization.control.ModalGraphMouse;


public class BuchiAutomatonJPanel<
	S extends State, 
	T extends LabelledTransition,
	TRANSITIONFACTORY extends LabelledTransitionFactoryInterface<T>, 
	A extends DrawableBA<S,T, TRANSITIONFACTORY>> extends AutomatonJPanel<S,T, TRANSITIONFACTORY,A>  {

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
	public BuchiAutomatonJPanel(A a, ActionListener l){
		super(a, l); 
		this.setEditingMode();
	}
	
	protected JPopupMenu getStateMenu(){
		 return new BAStateMenu(view);
	}
	
	
	protected BuchiAutomatonShapeTransformer<S,T, TRANSITIONFACTORY, A> getShapeTransformer(A a){
		return new BuchiAutomatonShapeTransformer<S, T, TRANSITIONFACTORY, A>(a);
	}
	
	protected BuchiAutomatonStatePaintTransformer<S,T, TRANSITIONFACTORY, A> getPaintTransformer(A a){
		return new BuchiAutomatonStatePaintTransformer<S,T, TRANSITIONFACTORY, A>(a);
	}
	
	

	@Override
	protected Transformer<T, Stroke> getStrokeEdgeStrokeTransformer() {
		return new BuchiAutomatonEdgeStrokeTransormer<T>();
	}

	@Override
	protected Transformer<S, Stroke> getStateStrokeTransformer(A a) {
		return new BuchiAutomatonStateStrokeTransofmer<S, T, TRANSITIONFACTORY, A>(a);
	}

	@Override
	public void setEditingMode() {
		
		LabelledTransitionFactoryInterface<T> tf=(LabelledTransitionFactoryInterface<T>) new LabelledTransitionFactory();
		EditingModalGraphMouse<S,T> gm = new EditingModalGraphMouse<S,T>(this.getRenderContext(), 
                new FactoryState<S>(), tf); 
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
