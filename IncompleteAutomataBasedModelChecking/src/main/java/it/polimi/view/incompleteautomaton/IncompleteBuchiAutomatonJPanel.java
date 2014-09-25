package it.polimi.view.incompleteautomaton;

import it.polimi.model.IncompleteBuchiAutomaton;
import it.polimi.model.State;
import it.polimi.model.Transition;
import it.polimi.view.automaton.BuchiAutomatonJPanel;
import it.polimi.view.automaton.transformers.BuchiAutomatonPaintTransformer;
import it.polimi.view.incompleteautomaton.transformers.IncompleteBuchiAutomatonPaintTransformer;

import java.awt.Dimension;
import java.awt.event.ActionListener;

public class IncompleteBuchiAutomatonJPanel<S extends State, T extends Transition<S>, A extends IncompleteBuchiAutomaton<S,T>> extends BuchiAutomatonJPanel<S, T, A> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	public IncompleteBuchiAutomatonJPanel(Dimension d, ActionListener container){
		super(d, container);
	}
	
	protected BuchiAutomatonPaintTransformer<S,T,A> getPaintTransformer(A a){
		return new IncompleteBuchiAutomatonPaintTransformer<S,T,A>(a);
	}
	
	
}
