package it.polimi.view.incompleteautomaton.transformers;

import it.polimi.model.IncompleteBuchiAutomaton;
import it.polimi.model.State;
import it.polimi.model.Transition;
import it.polimi.view.automaton.transformers.BuchiAutomatonPaintTransformer;

import java.awt.Color;
import java.awt.Paint;

public class IncompleteBuchiAutomatonPaintTransformer<S extends State, T extends Transition<S>, A extends IncompleteBuchiAutomaton<S, T>> extends BuchiAutomatonPaintTransformer<S,T, A> {

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
