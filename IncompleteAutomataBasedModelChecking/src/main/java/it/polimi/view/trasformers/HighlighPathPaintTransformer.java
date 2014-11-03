package it.polimi.view.trasformers;

import it.polimi.model.impl.states.IntersectionState;
import it.polimi.model.impl.states.State;
import it.polimi.model.impl.transitions.ConstrainedTransition;
import it.polimi.model.impl.transitions.LabelledTransition;
import it.polimi.model.interfaces.automata.drawable.DrawableIntBA;
import it.polimi.model.interfaces.transitions.ConstrainedTransitionFactory;
import it.polimi.model.interfaces.transitions.LabelledTransitionFactory;
import it.polimi.view.trasformers.ba.BuchiAutomatonStatePaintTransformer;

import java.awt.Color;
import java.awt.Paint;
import java.util.Stack;

public class HighlighPathPaintTransformer<
	STATE extends State, 
	TRANSITION extends LabelledTransition,
	TRANSITIONFACTORY extends LabelledTransitionFactory<TRANSITION>,
	INTERSECTIONSTATE extends IntersectionState<STATE>, 
	INTERSECTIONTRANSITION extends ConstrainedTransition<STATE>, 
	INTERSECTIONTRANSITIONFACTORY extends ConstrainedTransitionFactory<STATE, INTERSECTIONTRANSITION>,
	INTERSECTIONAUTOMATON  extends DrawableIntBA<STATE, TRANSITION, INTERSECTIONSTATE, INTERSECTIONTRANSITION, INTERSECTIONTRANSITIONFACTORY>> 
	extends BuchiAutomatonStatePaintTransformer<INTERSECTIONSTATE,INTERSECTIONTRANSITION, INTERSECTIONTRANSITIONFACTORY, INTERSECTIONAUTOMATON> {

	private Stack<INTERSECTIONSTATE> states;
	
	public HighlighPathPaintTransformer(INTERSECTIONAUTOMATON a, Stack<INTERSECTIONSTATE> states) {
		super(a);
		this.states=states;
	}
	
	@Override
	public Paint transform(INTERSECTIONSTATE input) {
		
		if(this.states!=null){
			if(this.states.contains(input)){
				return Color.RED;
			}
			if(a.isTransparent(input)){
				return Color.GRAY;
			}
		
		}
		return Color.WHITE;
	}

}
