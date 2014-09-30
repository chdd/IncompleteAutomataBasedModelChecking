package it.polimi.view.buchiautomaton.transformers;

import it.polimi.model.BuchiAutomaton;
import it.polimi.model.LabelledTransition;
import it.polimi.model.graph.State;

import java.awt.BasicStroke;
import java.awt.Stroke;

import org.apache.commons.collections15.Transformer;

public class BuchiAutomatonStrokeTransformer<S extends State, T extends LabelledTransition<S>, A extends BuchiAutomaton<S, T>> implements Transformer<S, Stroke>{
	protected A a;
	
	public BuchiAutomatonStrokeTransformer(A a){
		this.a=a;
	}

	@Override
	public Stroke transform(S input) {
		return new BasicStroke();
	}

}
