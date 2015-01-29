package it.polimi.automata.transition.impl;

import it.polimi.automata.labeling.Label;
import it.polimi.automata.labeling.impl.LabelImplFactory;
import it.polimi.automata.transition.Transition;
import it.polimi.automata.transition.TransitionFactory;

import java.util.HashSet;
import java.util.Set;

import rwth.i2.ltl2ba4j.model.IGraphProposition;

/**
 * is the factory that allows to create transitions of the type
 * Transition<LABEL>
 * 
 * @see {@link Transition}. It implements the {@link TransitionFactory}
 *      interface
 * 
 * @author claudiomenghi
 * @param <L>
 *            is the type of the label of the transitions depending on whether
 *            the automaton represents the model or the claim it is a set of
 *            proposition or a propositional logic formula {@link Label}
 */
public class ClaimTransitionFactoryImpl<L extends Label> implements
		TransitionFactory<L, Transition<L>> {

	/**
	 * contains the next id of the {@link TransitionImpl}
	 */
	private static int transitionCount = 0;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Transition<L> create() {
		Transition<L> t = new TransitionImpl<L>(new HashSet<L>(),
				ClaimTransitionFactoryImpl.transitionCount);
		ClaimTransitionFactoryImpl.transitionCount = ClaimTransitionFactoryImpl.transitionCount+1;

		return t;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Transition<L> create(Set<L> labels) {
		if(labels == null)
			throw new NullPointerException("The labels to be added at the Transition cannot be null");

		Transition<L> t = new TransitionImpl<L>(labels,
				ClaimTransitionFactoryImpl.transitionCount);
		ClaimTransitionFactoryImpl.transitionCount = ClaimTransitionFactoryImpl.transitionCount+1;

		return t;
	}


	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Transition<L> create(int id, Set<L> labels) {
		if(id < 0) 
			throw new IllegalArgumentException("The id must be grater than or equal to zero");
		if( labels == null)
			throw new NullPointerException("The labels to be added at the Transition cannot be null");

		TransitionImpl<L> t = new TransitionImpl<L>(labels, id);
		ClaimTransitionFactoryImpl.transitionCount = Math.max(
				ClaimTransitionFactoryImpl.transitionCount++, id++);

		return t;
	}

}
