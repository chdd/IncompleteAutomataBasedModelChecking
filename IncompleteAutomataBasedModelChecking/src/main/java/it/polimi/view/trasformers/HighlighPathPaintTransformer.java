package it.polimi.view.trasformers;

import it.polimi.model.automata.ba.transition.ConstrainedTransition;
import it.polimi.model.automata.ba.transition.LabelledTransition;
import it.polimi.model.elements.states.IntersectionState;
import it.polimi.model.elements.states.State;
import it.polimi.model.interfaces.DrawableIntBA;

import java.awt.Color;
import java.awt.Paint;
import java.util.Stack;

public class HighlighPathPaintTransformer<S1 extends State, T1 extends LabelledTransition,S extends IntersectionState<S1>, T 
extends ConstrainedTransition<S1>, A  extends DrawableIntBA<S1, T1, S, T>> extends BuchiAutomatonStatePaintTransformer<S,T, A> {

	private Stack<S> states;
	
	public HighlighPathPaintTransformer(A a, Stack<S> states) {
		super(a);
		this.states=states;
	}
	
	@Override
	public Paint transform(S input) {
		
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
