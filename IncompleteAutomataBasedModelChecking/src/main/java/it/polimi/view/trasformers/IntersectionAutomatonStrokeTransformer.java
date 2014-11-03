package it.polimi.view.trasformers;

import it.polimi.model.impl.states.IntersectionState;
import it.polimi.model.impl.states.State;
import it.polimi.model.impl.transitions.ConstrainedTransition;
import it.polimi.model.impl.transitions.LabelledTransition;
import it.polimi.model.interfaces.automata.drawable.DrawableIntBA;
import it.polimi.model.interfaces.transitions.ConstrainedTransitionFactory;
import it.polimi.model.interfaces.transitions.LabelledTransitionFactory;
import it.polimi.view.trasformers.ba.BuchiAutomatonStateStrokeTransofmer;

import java.awt.BasicStroke;
import java.awt.Stroke;

public class IntersectionAutomatonStrokeTransformer<
	STATE extends State, 
	TRANSITION extends LabelledTransition, 
	TRANSITIONFACTORY extends LabelledTransitionFactory<TRANSITION>,
	INTERSECTIONSTATE extends IntersectionState<STATE>, 
	INTERSECTIONTRANSITION extends ConstrainedTransition<STATE>, 
	INTERSECTIONTRANSITIONFACTORY extends ConstrainedTransitionFactory<STATE, INTERSECTIONTRANSITION>,
	BA  extends DrawableIntBA<STATE, TRANSITION, INTERSECTIONSTATE, INTERSECTIONTRANSITION, INTERSECTIONTRANSITIONFACTORY>> extends
	BuchiAutomatonStateStrokeTransofmer<INTERSECTIONSTATE, INTERSECTIONTRANSITION, INTERSECTIONTRANSITIONFACTORY, BA> {

	public IntersectionAutomatonStrokeTransformer(BA a) {
		super(a);
	}
	
	@Override
	public Stroke transform(INTERSECTIONSTATE input) {
		if(this.a.isMixed(input)){
			float dash[]={10.0f};
			return new BasicStroke(0.5f,  BasicStroke.CAP_BUTT, BasicStroke.JOIN_ROUND, 5.0f, dash, 0.0f);
		}
		else{
			return super.transform(input);
		}
		
	}

}
