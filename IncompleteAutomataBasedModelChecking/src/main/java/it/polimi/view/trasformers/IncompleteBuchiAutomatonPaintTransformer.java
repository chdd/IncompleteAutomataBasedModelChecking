package it.polimi.view.trasformers;

import it.polimi.model.impl.states.State;
import it.polimi.model.impl.transitions.LabelledTransition;
import it.polimi.model.interfaces.automata.drawable.DrawableIBA;
import it.polimi.model.interfaces.transitions.LabelledTransitionFactory;
import it.polimi.view.trasformers.ba.BuchiAutomatonStatePaintTransformer;

import java.awt.Color;
import java.awt.Paint;

public class IncompleteBuchiAutomatonPaintTransformer<
	S extends State, 
	T extends LabelledTransition, 
	LABELLEDTRANSITIONFACTORY extends LabelledTransitionFactory<T>,
	A extends DrawableIBA<S, T, LABELLEDTRANSITIONFACTORY>> 
	extends BuchiAutomatonStatePaintTransformer<S,T, LABELLEDTRANSITIONFACTORY, A> {

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
