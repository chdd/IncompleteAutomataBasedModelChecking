package it.polimi.automata.factories.impl;

import it.polimi.automata.Transition;
import it.polimi.automata.factories.TransitionFactory;
import it.polimi.automata.impl.TransitionImpl;
import it.polimi.automata.labeling.Label;

import java.util.HashSet;
import java.util.Set;

/**
 * is the factory that allows to create transitions of the type Transition<LABEL>
 * 
 * @see {@link Transition}. It implements the {@link TransitionFactory} interface
 * 
 * @author claudiomenghi
 * 
 */
public class TransitionFactoryImpl<LABEL extends Label> implements
		TransitionFactory<LABEL, Transition<LABEL>> {

	/**
	 * contains the next id of the {@link TransitionImpl}
	 */
	protected static int transitionCount = 0;

	/**
	 * {@inheritDoc}
	 */
	public Transition<LABEL> create(){
		Transition<LABEL> t = new TransitionImpl<LABEL>(new HashSet<LABEL>(),
				TransitionFactoryImpl.transitionCount);
		TransitionFactoryImpl.transitionCount = TransitionFactoryImpl.transitionCount++;

		return t;
	}
	
	/**
	 * {@inheritDoc}
	 */
	public Transition<LABEL> create(Set<LABEL> labels) {
		if (labels == null) {
			throw new NullPointerException(
					"The labels to be added at the Transition cannot be null");
		}
		Transition<LABEL> t = new TransitionImpl<LABEL>(labels,
				TransitionFactoryImpl.transitionCount);
		TransitionFactoryImpl.transitionCount = TransitionFactoryImpl.transitionCount++;

		return t;
	}

	/**
	 * {@inheritDoc}
	 */
	public Transition<LABEL> create(int id, Set<LABEL> labels) {
		if (id < 0) {
			throw new IllegalArgumentException(
					"The id must be grater than or equal to zero");
		}
		if (labels == null) {
			throw new NullPointerException(
					"The labels to be added at the Transition cannot be null");
		}
		TransitionImpl<LABEL> t = new TransitionImpl<LABEL>(labels, id);
		TransitionFactoryImpl.transitionCount = Math.max(
				TransitionFactoryImpl.transitionCount++, id++);

		return t;
	}

}
