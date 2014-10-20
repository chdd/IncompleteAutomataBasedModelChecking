package it.polimi.view.trasformers;

import it.polimi.model.impl.transitions.LabelledTransition;

import java.awt.BasicStroke;
import java.awt.Stroke;

import org.apache.commons.collections15.Transformer;

public class BuchiAutomatonEdgeStrokeTransormer<T extends LabelledTransition> implements Transformer<T, Stroke>{

	@Override
	public Stroke transform(T input) {
		return new BasicStroke();
	}
}
