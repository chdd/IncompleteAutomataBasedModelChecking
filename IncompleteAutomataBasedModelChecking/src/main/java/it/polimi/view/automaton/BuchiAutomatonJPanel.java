package it.polimi.view.automaton;

import it.polimi.model.automata.ba.BuchiAutomaton;
import it.polimi.model.automata.ba.state.State;
import it.polimi.model.automata.ba.state.StateFactory;
import it.polimi.model.automata.ba.transition.LabelledTransition;
import it.polimi.model.automata.ba.transition.TransitionFactory;
import it.polimi.view.buchiautomaton.transformers.BuchiAutomatonEdgeStrokeTransormer;
import it.polimi.view.buchiautomaton.transformers.BuchiAutomatonShapeTransformer;
import it.polimi.view.buchiautomaton.transformers.BuchiAutomatonStatePaintTransformer;
import it.polimi.view.buchiautomaton.transformers.BuchiAutomatonStateStrokeTransofmer;
import it.polimi.view.incompleteautomaton.editing.Actions;
import it.polimi.view.incompleteautomaton.editing.BAStateMenu;
import it.polimi.view.incompleteautomaton.editing.Plugin;

import java.awt.Dimension;
import java.awt.Stroke;
import java.awt.event.ActionListener;

import javax.swing.JComponent;
import javax.swing.JPopupMenu;

import org.apache.commons.collections15.Transformer;

import edu.uci.ics.jung.visualization.control.EditingModalGraphMouse;
import edu.uci.ics.jung.visualization.control.ModalGraphMouse;


public class BuchiAutomatonJPanel<S extends State, T extends LabelledTransition, A extends BuchiAutomaton<S,T>> extends AutomatonJPanel<S,T,A>  {

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
	public BuchiAutomatonJPanel(Dimension d, A a, ActionListener l){
		super(d, a, l); 
		this.setEditingMode();
	}
	
	protected JPopupMenu getStateMenu(){
		 return new BAStateMenu(view);
	}
	
	
	protected BuchiAutomatonShapeTransformer<S,T,A> getShapeTransformer(A a){
		return new BuchiAutomatonShapeTransformer<S, T, A>(a);
	}
	
	protected BuchiAutomatonStatePaintTransformer<S,T,A> getPaintTransformer(A a){
		return new BuchiAutomatonStatePaintTransformer<S,T,A>(a);
	}
	
	

	@Override
	protected Transformer<T, Stroke> getStrokeEdgeStrokeTransformer() {
		return new BuchiAutomatonEdgeStrokeTransormer<T>();
	}

	@Override
	protected Transformer<S, Stroke> getStateStrokeTransformer(A a) {
		return new BuchiAutomatonStateStrokeTransofmer<S, T, A>(a);
	}

	@Override
	public void setEditingMode() {
		
		
		 EditingModalGraphMouse<S,T> gm = new EditingModalGraphMouse<S,T>(this.getRenderContext(), 
                 new StateFactory<S>(), new TransitionFactory<T>()); 
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
