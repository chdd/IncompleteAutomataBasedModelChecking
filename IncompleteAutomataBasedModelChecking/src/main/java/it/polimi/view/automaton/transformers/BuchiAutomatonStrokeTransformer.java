package it.polimi.view.automaton.transformers;

import it.polimi.model.BuchiAutomaton;
import it.polimi.model.State;
import it.polimi.model.Transition;

import java.awt.BasicStroke;
import java.awt.Stroke;

import org.apache.commons.collections15.Transformer;

public class BuchiAutomatonStrokeTransformer<S extends State, T extends Transition<S>, A extends BuchiAutomaton<S, T>> implements Transformer<S, Stroke>{
	protected A a;
	
	public BuchiAutomatonStrokeTransformer(A a){
		this.a=a;
	}

	@Override
	public Stroke transform(S input) {
		return new BasicStroke();
	}

}
