package it.polimi.automata.transition.impl;

import it.polimi.automata.labeling.Label;
import it.polimi.automata.transition.Transition;
import it.polimi.automata.transition.TransitionFactory;

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
public class ModelTransitionFactoryImpl<L extends Label> extends
		ClaimTransitionFactoryImpl<L> {
	/**
	 * {@inheritDoc} <br>
	 * also guarantees that no transition with a negated proposition is created
	 * 
	 * @throws IllegalArgumentException
	 *             if one of the proposition in the label is negated
	 */
	@Override
	public Transition<L> create(Set<L> labels) {
		if (labels == null)
			throw new NullPointerException(
					"The labels to be added at the Transition cannot be null");

		for (L label : labels) {
			for (IGraphProposition p : label.getLabels()) {
				if (p.isNegated()) {
					throw new IllegalArgumentException(
							"The propositions of the model cannot be negated");
				}
			}
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
	public Transition<L> create(int id, Set<L> labels) {
		if (labels == null)
			throw new NullPointerException(
					"The labels to be added at the Transition cannot be null");

		for (L label : labels) {
			for (IGraphProposition p : label.getLabels()) {
				if (p.isNegated()) {
					throw new IllegalArgumentException(
							"The propositions of the model cannot be negated");
				}
			}
		}

		return super.create(id, labels);
	}
}
