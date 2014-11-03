package it.polimi.view.trasformers.ba;

import it.polimi.model.impl.states.State;
import it.polimi.model.impl.transitions.LabelledTransition;
import it.polimi.model.interfaces.automata.drawable.DrawableBA;
import it.polimi.model.interfaces.transitions.LabelledTransitionFactory;

import java.awt.Color;
import java.awt.Paint;

import org.apache.commons.collections15.Transformer;

public class BuchiAutomatonStatePaintTransformer<
	S extends State, 
	T extends LabelledTransition,
	LABELLEDTRANSITIONFACTORY extends LabelledTransitionFactory<T>,
	A extends DrawableBA<S, T, LABELLEDTRANSITIONFACTORY>> implements Transformer<S, Paint> {

	protected A a;
	
	public BuchiAutomatonStatePaintTransformer(A a){
		this.a=a;
	}
	@Override
	public Paint transform(S input) {
		return Color.WHITE;
	}

}
