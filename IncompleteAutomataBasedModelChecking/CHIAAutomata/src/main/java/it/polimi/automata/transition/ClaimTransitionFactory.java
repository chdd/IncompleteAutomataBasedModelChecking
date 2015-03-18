package it.polimi.automata.transition;

import it.polimi.automata.state.State;

import java.util.HashSet;
import java.util.Set;

import org.jgrapht.graph.ClassBasedEdgeFactory;

import com.google.common.base.Preconditions;

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
public class ClaimTransitionFactory<S extends State>  extends ClassBasedEdgeFactory<S, Transition> implements
		TransitionFactory<S, Transition> {

	public ClaimTransitionFactory() {
		super(Transition.class);
	}

	/**
	 * contains the next id of the {@link Transition}
	 */
	protected static int transitionCount = 0;

	/**
	 * {@inheritDoc}
	 */
	public Transition create() {
		
		Transition t = new Transition(new HashSet<IGraphProposition>(),
				ClaimTransitionFactory.transitionCount);
		ClaimTransitionFactory.transitionCount = ClaimTransitionFactory.transitionCount+1;

		return t;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Transition create(Set<IGraphProposition> labels) {
		Preconditions.checkNotNull(labels, "The labels to be added at the Transition cannot be null");

		Transition t = new Transition(labels,
				ClaimTransitionFactory.transitionCount);
		ClaimTransitionFactory.transitionCount = ClaimTransitionFactory.transitionCount+1;

		return t;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Transition create(int id, Set<IGraphProposition> labels) {
		Preconditions.checkArgument(id >=0, "The id must be grater than or equal to zero");
		Preconditions.checkNotNull(labels, "The labels to be added at the Transition cannot be null");

		Transition t = new Transition(labels, id);
		ClaimTransitionFactory.transitionCount = Math.max(
				ClaimTransitionFactory.transitionCount++, id++);

		return t;
	}


}
