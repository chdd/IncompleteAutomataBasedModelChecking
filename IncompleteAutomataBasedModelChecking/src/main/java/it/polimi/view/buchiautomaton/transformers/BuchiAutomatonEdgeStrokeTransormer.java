package it.polimi.view.buchiautomaton.transformers;

import it.polimi.model.automata.ba.state.State;
import it.polimi.model.automata.ba.transition.LabelledTransition;

import java.awt.BasicStroke;
import java.awt.Stroke;

import org.apache.commons.collections15.Transformer;

public class BuchiAutomatonEdgeStrokeTransormer<S extends State, T extends LabelledTransition> implements Transformer<T, Stroke>{
	
	@Override
	public Stroke transform(T input) {
		return new BasicStroke();
	}
}
