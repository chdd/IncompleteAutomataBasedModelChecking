package it.polimi.view.buchiautomaton;

import it.polimi.model.automata.ba.BuchiAutomaton;
import it.polimi.model.automata.ba.state.State;
import it.polimi.model.automata.ba.transition.LabelledTransition;
import it.polimi.view.automaton.AutomatonJPanel;
import it.polimi.view.buchiautomaton.transformers.BuchiAutomatonEdgeStrokeTransormer;
import it.polimi.view.buchiautomaton.transformers.BuchiAutomatonPaintTransformer;
import it.polimi.view.buchiautomaton.transformers.BuchiAutomatonShapeTransformer;
import it.polimi.view.buchiautomaton.transformers.BuchiAutomatonStrokeTransformer;

import java.awt.Dimension;

import javax.swing.JComponent;


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
	public BuchiAutomatonJPanel(Dimension d){
		super(d);
		/* EditingModalGraphMouse<S,T> gm = new EditingModalGraphMouse<S,T>(vv.getRenderContext(), 
                 new StateFactory<S>(), sgv.edgeFactory); 
        vv.setGraphMouse(gm);*/
	}
	
	protected BuchiAutomatonShapeTransformer<S,T,A> getShapeTransformer(A a){
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
	
	
}
