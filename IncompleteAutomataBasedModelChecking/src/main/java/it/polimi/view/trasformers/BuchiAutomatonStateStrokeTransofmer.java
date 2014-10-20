package it.polimi.view.trasformers;

import it.polimi.model.impl.states.State;
import it.polimi.model.impl.transitions.LabelledTransition;
import it.polimi.model.interfaces.automata.drawable.DrawableBA;
import it.polimi.model.interfaces.transitions.LabelledTransitionFactoryInterface;

import java.awt.BasicStroke;
import java.awt.Stroke;

import org.apache.commons.collections15.Transformer;

public class BuchiAutomatonStateStrokeTransofmer<
	S extends State, 
	T extends LabelledTransition, 
	LABELLEDTRANSITIONFACTORY extends LabelledTransitionFactoryInterface<T>,
	A extends DrawableBA<S, T, LABELLEDTRANSITIONFACTORY>> implements Transformer<S, Stroke> {

	protected A a;
	
	public BuchiAutomatonStateStrokeTransofmer(A a){
		this.a=a;
	}
	
	@Override
	public Stroke transform(S input) {
		return new BasicStroke();
	}

}
