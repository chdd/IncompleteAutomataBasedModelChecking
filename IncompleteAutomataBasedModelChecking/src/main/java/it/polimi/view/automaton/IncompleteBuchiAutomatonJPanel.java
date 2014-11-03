package it.polimi.view.automaton;

import it.polimi.model.impl.states.State;
import it.polimi.model.impl.states.StateFactory;
import it.polimi.model.impl.transitions.LabelledTransition;
import it.polimi.model.interfaces.automata.drawable.DrawableIBA;
import it.polimi.model.interfaces.transitions.LabelledTransitionFactory;
import it.polimi.view.menu.IBAStateMenu;

import java.awt.Color;
import java.awt.Paint;
import java.awt.event.ActionListener;

import javax.swing.JPopupMenu;

import edu.uci.ics.jung.algorithms.layout.AbstractLayout;

public class IncompleteBuchiAutomatonJPanel
<STATE extends State, 
STATEFACTORY extends StateFactory<STATE>,
TRANSITION extends LabelledTransition,
TRANSITIONFACTORY extends LabelledTransitionFactory<TRANSITION>,
IBA extends DrawableIBA<STATE,TRANSITION, TRANSITIONFACTORY>> extends BuchiAutomatonJPanel<STATE, STATEFACTORY, TRANSITION, TRANSITIONFACTORY, IBA> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public IncompleteBuchiAutomatonJPanel(IBA a, ActionListener l, AbstractLayout<STATE, TRANSITION> layout){
		 super(a, l, layout);
	}
	
	protected BuchiAutomatonStatePaintTransformer getPaintTransformer(IBA a){
		return new IncompleteBuchiAutomatonPaintTransformer(a);
	}
	protected JPopupMenu getStateMenu(){
		 return new IBAStateMenu(view);
	}
	
	public class IncompleteBuchiAutomatonPaintTransformer extends BuchiAutomatonStatePaintTransformer {

		public IncompleteBuchiAutomatonPaintTransformer(IBA a) {
			super(a);
		}
		
		@Override
		public Paint transform(STATE input) {
			if(a.isTransparent(input)){
				return Color.GRAY;
			}
			return Color.WHITE;
		}
	}
}
