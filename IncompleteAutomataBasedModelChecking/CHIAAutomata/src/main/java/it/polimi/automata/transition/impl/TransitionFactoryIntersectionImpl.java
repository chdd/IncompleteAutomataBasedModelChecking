package it.polimi.automata.transition.impl;

import java.util.HashSet;
import java.util.Set;

import org.jgrapht.graph.ClassBasedEdgeFactory;

import com.google.common.base.Preconditions;

import rwth.i2.ltl2ba4j.model.IGraphProposition;
import it.polimi.automata.state.State;
import it.polimi.automata.transition.IntersectionTransition;
import it.polimi.automata.transition.IntersectionTransitionFactory;
import it.polimi.automata.transition.Transition;
import it.polimi.automata.transition.TransitionFactory;

/**
 * is the factory that allows to create transitions of the type Transition of
 * the intersection automaton It extends the {@link TransitionFactoryClaimImpl}
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
public class TransitionFactoryIntersectionImpl<S extends State> extends
		ClassBasedEdgeFactory<S, IntersectionTransition<S>> implements
		IntersectionTransitionFactory<S, IntersectionTransition<S>> {

	@SuppressWarnings("unchecked")
	public TransitionFactoryIntersectionImpl() {
		super(
				(Class<? extends IntersectionTransition<S>>) IntersectionTransition.class);
	}

	public IntersectionTransition<S> create(int id,
			Set<IGraphProposition> labels, S state) {
		Preconditions.checkNotNull(labels,
				"The set of the labels cannot be null");
		Preconditions.checkNotNull(state,
				"The set of the states cannot be null");
		Preconditions.checkArgument(id >= 0, "The id must be grater than zero");

		return new IntersectionTransitionImpl<S>(labels, id, state);
	}

	/**
	 * {@inheritDoc}
	 */
	public IntersectionTransition<S> create() {

		IntersectionTransitionImpl<S> t = new IntersectionTransitionImpl<S>(
				new HashSet<IGraphProposition>(),
				TransitionFactoryClaimImpl.transitionCount, null);
		TransitionFactoryClaimImpl.transitionCount = TransitionFactoryClaimImpl.transitionCount + 1;

		return t;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public IntersectionTransition<S> create(Set<IGraphProposition> labels) {
		Preconditions.checkNotNull(labels,
				"The labels to be added at the Transition cannot be null");

		IntersectionTransitionImpl<S> t = new IntersectionTransitionImpl<S>(
				labels, TransitionFactoryClaimImpl.transitionCount, null);
		TransitionFactoryClaimImpl.transitionCount = TransitionFactoryClaimImpl.transitionCount + 1;

		return t;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public IntersectionTransition<S> create(int id,
			Set<IGraphProposition> labels) {
		Preconditions.checkNotNull(labels,
				"The labels to be added at the Transition cannot be null");
		Preconditions.checkArgument(id >= 0,
				"The id must be grater than or equal to zero");

		IntersectionTransitionImpl<S> t = new IntersectionTransitionImpl<S>(
				labels, id, null);
		TransitionFactoryClaimImpl.transitionCount = Math.max(
				TransitionFactoryClaimImpl.transitionCount++, id++);

		return t;
	}

	@Override
	public IntersectionTransition<S> create(Set<IGraphProposition> labels,
			S state) {
		Preconditions.checkNotNull(labels, "The labels cannot be null");
		IntersectionTransitionImpl<S> t = new IntersectionTransitionImpl<S>(
				labels, TransitionFactoryClaimImpl.transitionCount, state);
		TransitionFactoryClaimImpl.transitionCount = TransitionFactoryClaimImpl.transitionCount + 1;
		return t;
	}
}
