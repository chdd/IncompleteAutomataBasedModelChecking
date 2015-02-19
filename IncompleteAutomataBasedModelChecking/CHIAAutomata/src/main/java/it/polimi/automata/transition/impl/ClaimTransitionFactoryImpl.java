package it.polimi.automata.transition.impl;

import it.polimi.automata.state.State;
import it.polimi.automata.transition.Transition;
import it.polimi.automata.transition.TransitionFactory;

import java.util.HashSet;
import java.util.Set;

import org.jgrapht.graph.ClassBasedEdgeFactory;

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
@SuppressWarnings("serial")
public class ClaimTransitionFactoryImpl<S extends State>  extends ClassBasedEdgeFactory<S, Transition> implements
		TransitionFactory<S, Transition> {

	public ClaimTransitionFactoryImpl(Class<? extends Transition> edgeClass) {
		super(edgeClass);
	}

	/**
	 * contains the next id of the {@link TransitionImpl}
	 */
	private static int transitionCount = 0;

	/**
	 * {@inheritDoc}
	 */
	public Transition create() {
		Transition t = new TransitionImpl(new HashSet<IGraphProposition>(),
				ClaimTransitionFactoryImpl.transitionCount);
		ClaimTransitionFactoryImpl.transitionCount = ClaimTransitionFactoryImpl.transitionCount+1;

		return t;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Transition create(Set<IGraphProposition> labels) {
		if(labels == null)
			throw new NullPointerException("The labels to be added at the Transition cannot be null");

		Transition t = new TransitionImpl(labels,
				ClaimTransitionFactoryImpl.transitionCount);
		ClaimTransitionFactoryImpl.transitionCount = ClaimTransitionFactoryImpl.transitionCount+1;

		return t;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Transition create(int id, Set<IGraphProposition> labels) {
		if(id < 0) 
			throw new IllegalArgumentException("The id must be grater than or equal to zero");
		if( labels == null)
			throw new NullPointerException("The labels to be added at the Transition cannot be null");

		Transition t = new TransitionImpl(labels, id);
		ClaimTransitionFactoryImpl.transitionCount = Math.max(
				ClaimTransitionFactoryImpl.transitionCount++, id++);

		return t;
	}


}
