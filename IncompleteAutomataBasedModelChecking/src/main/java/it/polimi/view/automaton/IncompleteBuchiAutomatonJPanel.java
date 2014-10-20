package it.polimi.view.automaton;

import it.polimi.model.impl.states.State;
import it.polimi.model.impl.transitions.LabelledTransition;
import it.polimi.model.interfaces.automata.drawable.DrawableIBA;
import it.polimi.model.interfaces.transitions.LabelledTransitionFactoryInterface;
import it.polimi.view.menu.IBAStateMenu;
import it.polimi.view.trasformers.BuchiAutomatonStatePaintTransformer;
import it.polimi.view.trasformers.IncompleteBuchiAutomatonPaintTransformer;

import java.awt.event.ActionListener;

import javax.swing.JPopupMenu;

public class IncompleteBuchiAutomatonJPanel<S extends State, T extends LabelledTransition,
TRANSITIONFACTORY extends LabelledTransitionFactoryInterface<T>, A extends DrawableIBA<S,T, TRANSITIONFACTORY>> extends BuchiAutomatonJPanel<S, T, TRANSITIONFACTORY, A> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public IncompleteBuchiAutomatonJPanel(A a, ActionListener l){
		 super(a, l);
	}
	
	protected BuchiAutomatonStatePaintTransformer<S,T, TRANSITIONFACTORY, A> getPaintTransformer(A a){
		return new IncompleteBuchiAutomatonPaintTransformer<S,T, TRANSITIONFACTORY, A>(a);
	}
	protected JPopupMenu getStateMenu(){
		 return new IBAStateMenu(view);
	}
}
