package it.polimi.view.buchiautomaton.transformers;

import it.polimi.model.automata.ba.BuchiAutomaton;
import it.polimi.model.automata.ba.LabelledTransition;
import it.polimi.model.automata.ba.state.State;

import java.awt.Color;
import java.awt.Paint;

import org.apache.commons.collections15.Transformer;

public class BuchiAutomatonPaintTransformer<S extends State, T extends LabelledTransition<S>, A extends BuchiAutomaton<S, T>> implements Transformer<S, Paint> {

	protected A a;
	
	public BuchiAutomatonPaintTransformer(A a){
		this.a=a;
	}
	@Override
	public Paint transform(S input) {
		return Color.WHITE;
	}

}
