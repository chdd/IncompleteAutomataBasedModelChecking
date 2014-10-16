package it.polimi.view.trasformers;

import it.polimi.model.automata.ba.transition.LabelledTransition;
import it.polimi.model.automata.impl.BAImpl;
import it.polimi.model.elements.states.State;

import java.awt.Color;
import java.awt.Paint;

import org.apache.commons.collections15.Transformer;

public class BuchiAutomatonStatePaintTransformer<S extends State, T extends LabelledTransition, A extends BAImpl<S, T>> implements Transformer<S, Paint> {

	protected A a;
	
	public BuchiAutomatonStatePaintTransformer(A a){
		this.a=a;
	}
	@Override
	public Paint transform(S input) {
		return Color.WHITE;
	}

}
