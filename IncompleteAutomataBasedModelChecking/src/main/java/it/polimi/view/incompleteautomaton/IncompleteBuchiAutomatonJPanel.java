package it.polimi.view.incompleteautomaton;

import it.polimi.model.automata.ba.LabelledTransition;
import it.polimi.model.automata.ba.state.State;
import it.polimi.model.automata.iba.IncompleteBuchiAutomaton;
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
