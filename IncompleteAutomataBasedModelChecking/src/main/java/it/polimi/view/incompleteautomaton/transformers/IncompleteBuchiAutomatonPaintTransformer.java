package it.polimi.view.incompleteautomaton.transformers;

import it.polimi.model.ba.LabelledTransition;
import it.polimi.model.graph.State;
import it.polimi.model.iba.IncompleteBuchiAutomaton;
import it.polimi.view.buchiautomaton.transformers.BuchiAutomatonPaintTransformer;

import java.awt.Color;
import java.awt.Paint;

public class IncompleteBuchiAutomatonPaintTransformer<S extends State, T extends LabelledTransition<S>, A extends IncompleteBuchiAutomaton<S, T>> extends BuchiAutomatonPaintTransformer<S,T, A> {

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
