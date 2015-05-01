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
 */
@SuppressWarnings("serial")
public class ClaimTransitionFactory  extends ClassBasedEdgeFactory<State, Transition> implements
		TransitionFactory<State, Transition> {

	public ClaimTransitionFactory() {
		super(Transition.class);
	}

	/**
	 * {@inheritDoc}
	 */
	public Transition create() {
		
		Transition t = new Transition(new HashSet<IGraphProposition>(),
				Transition.transition_counter);
		Transition.transition_counter = Transition.transition_counter+1;

		return t;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Transition create(Set<IGraphProposition> labels) {
		Preconditions.checkNotNull(labels, "The labels to be added at the Transition cannot be null");

		Transition t = new Transition(labels,Transition.transition_counter);
		Transition.transition_counter = Transition.transition_counter+1;

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
		Transition.transition_counter = Math.max(
				Transition.transition_counter+1, id+1);

		return t;
	}


}
