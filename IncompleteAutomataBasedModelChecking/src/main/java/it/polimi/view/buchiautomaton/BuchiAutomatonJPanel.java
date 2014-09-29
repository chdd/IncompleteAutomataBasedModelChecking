package it.polimi.view.buchiautomaton;

import it.polimi.model.BuchiAutomaton;
import it.polimi.model.State;
import it.polimi.model.Transition;
import it.polimi.view.automaton.AutomatonJPanel;
import it.polimi.view.automaton.transformers.BuchiAutomatonEdgeStrokeTransormer;
import it.polimi.view.automaton.transformers.BuchiAutomatonPaintTransformer;
import it.polimi.view.automaton.transformers.BuchiAutomatonShapeTransformer;
import it.polimi.view.automaton.transformers.BuchiAutomatonStrokeTransformer;

import java.awt.Dimension;

import javax.swing.JComponent;

import edu.uci.ics.jung.graph.SparseMultigraph;
import edu.uci.ics.jung.graph.util.EdgeType;


public class BuchiAutomatonJPanel<S extends State, T extends Transition<S>, A extends BuchiAutomaton<S,T>> extends AutomatonJPanel<S,T,A>  {

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
	
	protected void loadAutomata(A a){
		this.graph = new SparseMultigraph<S,T>();
		for(S s: a.getStates()){
			this.graph.addVertex(s);
		}
		for(S s: a.getStates()){
			if(a.getTransitionsWithSource(s)!=null){
				for(T t: a.getTransitionsWithSource(s)){
					this.graph.addEdge(t, s, t.getDestination(), EdgeType.DIRECTED);
				}  
			}
		}	
	}

	
}
