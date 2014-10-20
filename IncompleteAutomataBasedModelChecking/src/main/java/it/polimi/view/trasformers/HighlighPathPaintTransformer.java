package it.polimi.view.trasformers;

import it.polimi.model.impl.states.IntersectionState;
import it.polimi.model.impl.states.State;
import it.polimi.model.impl.transitions.ConstrainedTransition;
import it.polimi.model.impl.transitions.LabelledTransition;
import it.polimi.model.interfaces.automata.drawable.DrawableIntBA;
import it.polimi.model.interfaces.transitions.ConstrainedTransitionFactoryInterface;
import it.polimi.model.interfaces.transitions.LabelledTransitionFactoryInterface;

import java.awt.Color;
import java.awt.Paint;
import java.util.Stack;

public class HighlighPathPaintTransformer<
	S1 extends State, 
	T1 extends LabelledTransition,
	S extends IntersectionState<S1>, 
	T extends ConstrainedTransition<S1>, 
	LABELLEDTRANSITIONFACTORY extends LabelledTransitionFactoryInterface<T1>,
	CONSTRAINEDTRANSITIONFACTORY extends ConstrainedTransitionFactoryInterface<S1, T>,
	A  extends DrawableIntBA<S1, T1, S, T, CONSTRAINEDTRANSITIONFACTORY>> extends BuchiAutomatonStatePaintTransformer<S,T, CONSTRAINEDTRANSITIONFACTORY, A> {

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
