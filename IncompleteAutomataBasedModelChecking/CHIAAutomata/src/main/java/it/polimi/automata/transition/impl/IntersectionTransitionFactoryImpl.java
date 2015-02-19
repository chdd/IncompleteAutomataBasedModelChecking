package it.polimi.automata.transition.impl;

import it.polimi.automata.state.State;
import it.polimi.automata.transition.Transition;
import it.polimi.automata.transition.TransitionFactory;

/**
 * is the factory that allows to create transitions of the type Transition of
 * the intersection automaton It extends the {@link ClaimTransitionFactoryImpl}
 * 
 * @see {@link Transition}. It implements the {@link TransitionFactory}
 *      interface
 * 
 * @author claudiomenghi
 * 
 * @param <L>
 *            is the type of the label of the transitions depending on whether
 *            the automaton represents the model or the claim it is a set of
 *            proposition or a propositional logic formula {@link Label}
 */
@SuppressWarnings("serial")
public class IntersectionTransitionFactoryImpl<S extends State> extends
		ClaimTransitionFactoryImpl<S> {

	public IntersectionTransitionFactoryImpl(
			Class<? extends Transition> edgeClass) {
		super(edgeClass);
	}

}
