package it.polimi.view.automaton;

import it.polimi.model.automata.ba.transition.LabelledTransition;
import it.polimi.model.automata.impl.IBAImpl;
import it.polimi.model.elements.states.State;
import it.polimi.view.menu.IBAStateMenu;
import it.polimi.view.trasformers.BuchiAutomatonStatePaintTransformer;
import it.polimi.view.trasformers.IncompleteBuchiAutomatonPaintTransformer;

import java.awt.event.ActionListener;

import javax.swing.JPopupMenu;

public class IncompleteBuchiAutomatonJPanel<S extends State, T extends LabelledTransition, A extends IBAImpl<S,T>> extends BuchiAutomatonJPanel<S, T, A> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public IncompleteBuchiAutomatonJPanel(A a, ActionListener l){
		 super(a, l);
	}
	
	protected BuchiAutomatonStatePaintTransformer<S,T,A> getPaintTransformer(A a){
		return new IncompleteBuchiAutomatonPaintTransformer<S,T,A>(a);
	}
	protected JPopupMenu getStateMenu(){
		 return new IBAStateMenu(view);
	}
}
