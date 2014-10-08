package it.polimi.view.automaton;

import it.polimi.model.automata.ba.state.State;
import it.polimi.model.automata.ba.transition.LabelledTransition;
import it.polimi.model.automata.iba.IncompleteBuchiAutomaton;
import it.polimi.view.View;
import it.polimi.view.buchiautomaton.transformers.BuchiAutomatonStatePaintTransformer;
import it.polimi.view.incompleteautomaton.editing.IBAStateMenu;
import it.polimi.view.incompleteautomaton.transformers.IncompleteBuchiAutomatonPaintTransformer;

import java.awt.Dimension;
import java.awt.event.ActionListener;

import javax.swing.JPopupMenu;

public class IncompleteBuchiAutomatonJPanel<S extends State, T extends LabelledTransition, A extends IncompleteBuchiAutomaton<S,T>> extends BuchiAutomatonJPanel<S, T, A> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public IncompleteBuchiAutomatonJPanel(Dimension d, A a, ActionListener l){
		 super(d, a, l);
	}
	
	protected BuchiAutomatonStatePaintTransformer<S,T,A> getPaintTransformer(A a){
		return new IncompleteBuchiAutomatonPaintTransformer<S,T,A>(a);
	}
	protected JPopupMenu getStateMenu(){
		 return new IBAStateMenu(view);
	}
}
