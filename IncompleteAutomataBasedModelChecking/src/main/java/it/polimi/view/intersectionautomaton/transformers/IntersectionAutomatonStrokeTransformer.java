package it.polimi.view.intersectionautomaton.transformers;

import it.polimi.model.IntersectionAutomaton;
import it.polimi.model.IntersectionState;
import it.polimi.model.LabelledTransition;
import it.polimi.model.graph.State;
import it.polimi.view.buchiautomaton.transformers.BuchiAutomatonStrokeTransformer;

import java.awt.BasicStroke;
import java.awt.Stroke;

public class IntersectionAutomatonStrokeTransformer<S extends State, T extends LabelledTransition<S>, S1 extends IntersectionState<S>, T1 extends LabelledTransition<S1>, A  extends IntersectionAutomaton<S, T, S1, T1>> extends
		BuchiAutomatonStrokeTransformer<S1, T1, A> {

	public IntersectionAutomatonStrokeTransformer(A a) {
		super(a);
	}
	
	@Override
	public Stroke transform(S1 input) {
		if(this.a.isMixed(input)){
			float dash[]={10.0f};
			return new BasicStroke(0.5f,  BasicStroke.CAP_BUTT, BasicStroke.JOIN_ROUND, 5.0f, dash, 0.0f);
		}
		else{
			return super.transform(input);
		}
		
	}

}
