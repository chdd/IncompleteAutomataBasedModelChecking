package it.polimi.view.automaton.transformers;

import it.polimi.model.State;
import it.polimi.model.Transition;

import java.awt.BasicStroke;
import java.awt.Stroke;

import org.apache.commons.collections15.Transformer;

public class BuchiAutomatonEdgeStrokeTransormer<S extends State, T extends Transition<S>> implements Transformer<T, Stroke>{
	
	@Override
	public Stroke transform(T input) {
		return new BasicStroke();
	}
}
