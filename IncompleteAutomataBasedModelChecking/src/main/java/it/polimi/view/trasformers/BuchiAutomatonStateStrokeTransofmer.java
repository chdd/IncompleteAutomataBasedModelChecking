package it.polimi.view.trasformers;

import it.polimi.model.automata.ba.transition.LabelledTransition;
import it.polimi.model.automata.impl.BAImpl;
import it.polimi.model.elements.states.State;

import java.awt.BasicStroke;
import java.awt.Stroke;

import org.apache.commons.collections15.Transformer;

public class BuchiAutomatonStateStrokeTransofmer<S extends State, T extends LabelledTransition, A extends BAImpl<S, T>> implements Transformer<S, Stroke> {

	protected A a;
	
	public BuchiAutomatonStateStrokeTransofmer(A a){
		this.a=a;
	}
	
	@Override
	public Stroke transform(S input) {
		return new BasicStroke();
	}

}
