package it.polimi.view.trasformers;

import it.polimi.model.automata.ba.BuchiAutomaton;
import it.polimi.model.automata.ba.state.State;
import it.polimi.model.automata.ba.transition.LabelledTransition;

import java.awt.Color;
import java.awt.Paint;

import org.apache.commons.collections15.Transformer;

public class BuchiAutomatonStatePaintTransformer<S extends State, T extends LabelledTransition, A extends BuchiAutomaton<S, T>> implements Transformer<S, Paint> {

	protected A a;
	
	public BuchiAutomatonStatePaintTransformer(A a){
		this.a=a;
	}
	@Override
	public Paint transform(S input) {
		return Color.WHITE;
	}

}
