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
 * @param <LABEL>
 *            is the type of the label of the transitions depending on whether
 *            the automaton represents the model or the claim it is a set of
 *            proposition or a propositional logic formula {@link Label}
 */
public class ModelTransitionFactoryImpl<LABEL extends Label> extends
		ClaimTransitionFactoryImpl<LABEL> {
	/**
	 * {@inheritDoc} <br>
	 * also guarantees that no transition with a negated proposition is created
	 * 
	 * @throws IllegalArgumentException
	 *             if one of the proposition in the label is negated
	 */
	public Transition<LABEL> create(Set<LABEL> labels) {
		if (labels == null) {
			throw new NullPointerException(
					"The labels to be added at the Transition cannot be null");
		}
		for (LABEL label : labels) {
			for (IGraphProposition p : label.getAtomicPropositions()) {
				if (p.isNegated()) {
					throw new IllegalArgumentException(
							"The propositions of the model cannot be negated");
				}
			}
		}
		Transition<LABEL> t = super.create(labels);

		return t;
	}

	/**
	 * {@inheritDoc} <br>
	 * also guarantees that no transition with a negated proposition is created
	 * 
	 * @throws IllegalArgumentException
	 *             if one of the proposition in the label is negated
	 */
	public Transition<LABEL> create(int id, Set<LABEL> labels) {
		if (labels == null) {
			throw new NullPointerException(
					"The labels to be added at the Transition cannot be null");
		}
		for (LABEL label : labels) {
			for (IGraphProposition p : label.getAtomicPropositions()) {
				if (p.isNegated()) {
					throw new IllegalArgumentException(
							"The propositions of the model cannot be negated");
				}
			}
		}
		Transition<LABEL> t = super.create(id, labels);

		return t;
	}
}
