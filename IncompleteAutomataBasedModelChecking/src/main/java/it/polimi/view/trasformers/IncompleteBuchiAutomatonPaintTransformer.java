package it.polimi.view.trasformers;

import it.polimi.model.automata.ba.transition.LabelledTransition;
import it.polimi.model.automata.impl.IBAImpl;
import it.polimi.model.elements.states.State;

import java.awt.Color;
import java.awt.Paint;

public class IncompleteBuchiAutomatonPaintTransformer<S extends State, T extends LabelledTransition, A extends IBAImpl<S, T>> extends BuchiAutomatonStatePaintTransformer<S,T, A> {

	public IncompleteBuchiAutomatonPaintTransformer(A a) {
		super(a);
	}
	
	@Override
	public Paint transform(S input) {
		if(a.isTransparent(input)){
			return Color.GRAY;
		}
		return Color.WHITE;
	}

}
