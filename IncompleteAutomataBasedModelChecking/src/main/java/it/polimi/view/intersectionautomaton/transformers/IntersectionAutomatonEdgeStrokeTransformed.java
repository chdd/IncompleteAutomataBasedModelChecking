package it.polimi.view.intersectionautomaton.transformers;

import java.awt.BasicStroke;
import java.awt.Stroke;

import it.polimi.model.automata.ba.state.State;
import it.polimi.model.automata.ba.transition.ConstrainedTransition;
import it.polimi.model.automata.ba.transition.LabelledTransition;
import it.polimi.model.automata.intersection.IntersectionState;
import it.polimi.view.buchiautomaton.transformers.BuchiAutomatonEdgeStrokeTransormer;

public class IntersectionAutomatonEdgeStrokeTransformed<S1 extends State, T1 extends LabelledTransition, S extends IntersectionState<S1>, T extends LabelledTransition> extends
		BuchiAutomatonEdgeStrokeTransormer<S, T> {
	@Override
	public Stroke transform(T input) {
		if(input instanceof ConstrainedTransition)
		{
			float dash[]={10.0f};
			return new BasicStroke(0.5f,  BasicStroke.CAP_BUTT, BasicStroke.JOIN_ROUND, 5.0f, dash, 0.0f);
		}
		else{
			return super.transform(input);
		}
	}
}