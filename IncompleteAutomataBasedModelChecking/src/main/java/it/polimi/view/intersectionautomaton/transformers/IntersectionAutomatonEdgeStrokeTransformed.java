package it.polimi.view.intersectionautomaton.transformers;

import it.polimi.model.automata.ba.state.State;
import it.polimi.model.automata.ba.transition.ConstrainedTransition;
import it.polimi.view.buchiautomaton.transformers.BuchiAutomatonEdgeStrokeTransormer;

import java.awt.BasicStroke;
import java.awt.Stroke;

public class IntersectionAutomatonEdgeStrokeTransformed<S extends State, T extends ConstrainedTransition<S>> extends
BuchiAutomatonEdgeStrokeTransormer<T> {
	@Override
	public Stroke transform(T input) {
		if(input.getConstrainedState()!=null)
		{
			float dash[]={10.0f};
			return new BasicStroke(0.5f,  BasicStroke.CAP_BUTT, BasicStroke.JOIN_ROUND, 5.0f, dash, 0.0f);
		}
		else{
			return super.transform(input);
		}
	}
}
