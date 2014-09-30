package it.polimi.view.incompleteautomaton;

import it.polimi.model.IncompleteBuchiAutomaton;
import it.polimi.model.State;
import it.polimi.model.LabelledTransition;
import it.polimi.view.buchiautomaton.BuchiAutomatonJPanel;
import it.polimi.view.buchiautomaton.transformers.BuchiAutomatonPaintTransformer;
import it.polimi.view.incompleteautomaton.transformers.IncompleteBuchiAutomatonPaintTransformer;

import java.awt.Dimension;

public class IncompleteBuchiAutomatonJPanel<S extends State, T extends LabelledTransition<S>, A extends IncompleteBuchiAutomaton<S,T>> extends BuchiAutomatonJPanel<S, T, A> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public IncompleteBuchiAutomatonJPanel(Dimension d){
		 super(d);
	}
	
	protected BuchiAutomatonPaintTransformer<S,T,A> getPaintTransformer(A a){
		return new IncompleteBuchiAutomatonPaintTransformer<S,T,A>(a);
	}
}
