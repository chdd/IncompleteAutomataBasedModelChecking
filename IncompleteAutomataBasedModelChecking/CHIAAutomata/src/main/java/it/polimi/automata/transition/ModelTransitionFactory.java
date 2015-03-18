package it.polimi.automata.transition;

import java.util.Set;

import rwth.i2.ltl2ba4j.model.IGraphProposition;

import com.google.common.base.Preconditions;

/**
 * is the factory that allows to create transitions of the type Transition
 * 
 * @see {@link Transition}. It implements the {@link TransitionFactory}
 *      interface
 * 
 * @author claudiomenghi
 * @param <S>
 *            is the type of the states of the automaton
 */
@SuppressWarnings("serial")
public class ModelTransitionFactory extends
		ClaimTransitionFactory {

	public ModelTransitionFactory() {
		super();
	}

	/**
	 * {@inheritDoc} <br>
	 * also guarantees that no transition with a negated proposition is created
	 * 
	 * @throws IllegalArgumentException
	 *             if one of the proposition in the label is negated
	 */
	@Override
	public Transition create(Set<IGraphProposition> labels) {
		Preconditions.checkNotNull(labels, "The labels to be added at the Transition cannot be null");
		for (IGraphProposition p : labels) {
			Preconditions.checkArgument(!p.isNegated(), "The propositions of the model cannot be negated");
		}
		return super.create(labels);
	}

	/**
	 * {@inheritDoc} <br>
	 * also guarantees that no transition with a negated proposition is created
	 * 
	 * @throws IllegalArgumentException
	 *             if one of the proposition in the label is negated
	 */
	@Override
	public Transition create(int id, Set<IGraphProposition> labels) {
		Preconditions.checkNotNull(labels, "The labels to be added at the Transition cannot be null");

		for (IGraphProposition p : labels) {
			Preconditions.checkArgument(!p.isNegated(), "The propositions of the model cannot be negated");
		}

		return super.create(id, labels);
	}
}
