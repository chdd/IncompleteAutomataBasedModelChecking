package it.polimi.view.buchiautomaton.transformers;

import it.polimi.model.automata.ba.BuchiAutomaton;
import it.polimi.model.automata.ba.state.State;
import it.polimi.model.automata.ba.transition.LabelledTransition;

import java.awt.BasicStroke;
import java.awt.Stroke;

import org.apache.commons.collections15.Transformer;

public class BuchiAutomatonStateStrokeTransofmer<S extends State, T extends LabelledTransition, A extends BuchiAutomaton<S, T>> implements Transformer<S, Stroke> {

	protected A a;
	
	public BuchiAutomatonStateStrokeTransofmer(A a){
		this.a=a;
	}
	
	@Override
	public Stroke transform(S input) {
		return new BasicStroke();
	}

}
