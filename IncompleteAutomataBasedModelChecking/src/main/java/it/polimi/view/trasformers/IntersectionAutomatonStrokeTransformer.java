package it.polimi.view.trasformers;

import it.polimi.model.automata.ba.transition.ConstrainedTransition;
import it.polimi.model.automata.ba.transition.LabelledTransition;
import it.polimi.model.elements.states.IntersectionState;
import it.polimi.model.elements.states.State;
import it.polimi.model.impl.IntersectionAutomaton;
import it.polimi.model.interfaces.DrawableIntBA;

import java.awt.BasicStroke;
import java.awt.Stroke;

public class IntersectionAutomatonStrokeTransformer<S extends State, T extends LabelledTransition, S1 extends IntersectionState<S>, T1 extends ConstrainedTransition<S>, A  extends DrawableIntBA<S, T, S1, T1>> extends
BuchiAutomatonStateStrokeTransofmer<S1, T1, A> {

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
