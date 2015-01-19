package it.polimi.automata.transition.impl;

import it.polimi.automata.labeling.Label;
import it.polimi.automata.transition.Transition;
import it.polimi.automata.transition.TransitionFactory;

import java.util.HashSet;
import java.util.Set;

/**
 * is the factory that allows to create transitions of the type
 * Transition<LABEL>
 * 
 * @see {@link Transition}. It implements the {@link TransitionFactory}
 *      interface
 * 
 * @author claudiomenghi
 * @param <LABEL>
 *            is the type of the label of the transitions depending on whether
 *            the automaton represents the model or the claim it is a set of
 *            proposition or a propositional logic formula {@link Label}
 */
public class ClaimTransitionFactoryImpl<LABEL extends Label> implements
		TransitionFactory<LABEL, Transition<LABEL>> {

	/**
	 * contains the next id of the {@link TransitionImpl}
	 */
	protected static int transitionCount = 0;

	/**
	 * {@inheritDoc}
	 */
	public Transition<LABEL> create() {
		Transition<LABEL> t = new TransitionImpl<LABEL>(new HashSet<LABEL>(),
				ClaimTransitionFactoryImpl.transitionCount);
		ClaimTransitionFactoryImpl.transitionCount = ClaimTransitionFactoryImpl.transitionCount++;

		return t;
	}

	/**
	 * {@inheritDoc}
	 */
	public Transition<LABEL> create(Set<LABEL> labels) {
		assert labels != null : "The labels to be added at the Transition cannot be null";

		Transition<LABEL> t = new TransitionImpl<LABEL>(labels,
				ClaimTransitionFactoryImpl.transitionCount);
		ClaimTransitionFactoryImpl.transitionCount = ClaimTransitionFactoryImpl.transitionCount++;

		return t;
	}

	/**
	 * {@inheritDoc}
	 */
	public Transition<LABEL> create(int id, Set<LABEL> labels) {
		assert id >= 0 : "The id must be grater than or equal to zero";
		assert labels != null : "The labels to be added at the Transition cannot be null";

		TransitionImpl<LABEL> t = new TransitionImpl<LABEL>(labels, id);
		ClaimTransitionFactoryImpl.transitionCount = Math.max(
				ClaimTransitionFactoryImpl.transitionCount++, id++);

		return t;
	}

}
