package it.polimi.view.incompleteautomaton.transformers;

import it.polimi.model.automata.ba.state.State;
import it.polimi.model.automata.ba.transition.LabelledTransition;
import it.polimi.model.automata.iba.IncompleteBuchiAutomaton;
import it.polimi.view.buchiautomaton.transformers.BuchiAutomatonStatePaintTransformer;

import java.awt.Color;
import java.awt.Paint;

public class IncompleteBuchiAutomatonPaintTransformer<S extends State, T extends LabelledTransition, A extends IncompleteBuchiAutomaton<S, T>> extends BuchiAutomatonStatePaintTransformer<S,T, A> {

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
