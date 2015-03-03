package it.polimi.checker.intersection.impl;

import it.polimi.automata.transition.IntersectionTransition;
import it.polimi.automata.transition.IntersectionTransitionFactory;
import it.polimi.automata.transition.Transition;
import it.polimi.checker.intersection.IntersectionRule;

import java.util.Set;

import com.google.common.base.Preconditions;

import it.polimi.automata.state.State;
import it.polimi.automata.state.StateFactory;
import rwth.i2.ltl2ba4j.model.IGraphProposition;
import rwth.i2.ltl2ba4j.model.impl.GraphProposition;
import rwth.i2.ltl2ba4j.model.impl.SigmaProposition;

/**
 * Defines an {@link IntersectionRule} that specifies how the transitions of the
 * intersection automaton is generated starting from the transition of the model
 * and the claim. <br>
 * The transition of the model can be performed only if it satisfies the
 * conditions specified in the claim
 * 
 * @author claudiomenghi
 * 
 */
public class IntersectionRuleImpl<S extends State, T extends Transition, I extends IntersectionTransition<S>>
		extends IntersectionRule<S, T, I> {

	
	public IntersectionRuleImpl(
			IntersectionTransitionFactory<S, I> intersectionTransitionFactory,
			StateFactory<S> stateFactory) {
		super(intersectionTransitionFactory, stateFactory);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public I getIntersectionTransition(T modelTransition, T claimTransition) {

		Preconditions.checkNotNull(modelTransition,
				"The model transition cannot be null");
		Preconditions.checkNotNull(claimTransition,
				"The claim transition cannot be null");
	
		if (this.satisfies(modelTransition.getPropositions(),
				claimTransition.getPropositions())) {
			return intersectionTransitionFactory.create(modelTransition
					.getPropositions());
		} else {
			return null;
		}
	}

	/**
	 * returns true if the label of the model satisfies the label of the claim
	 * 
	 * @param modelLabel
	 *            is the label of the model
	 * @param claimLabel
	 *            is the label of the claim
	 * @return true if the label of the model satisfies the label of the claim
	 */
	protected boolean satisfies(Set<IGraphProposition> modelLabel,
			Set<IGraphProposition> claimLabel) {
		Preconditions.checkNotNull(modelLabel, "The model cannot be null");
		Preconditions.checkNotNull(claimLabel, "The claim cannot be null");

		// for each proposition of the claim
		for (IGraphProposition claimProposition : claimLabel) {
			// if the proposition is sigma it is satisfied by the proposition of
			// the model
			if (claimProposition instanceof SigmaProposition) {
				return true;
			}
			// if the claim proposition is negated it must not be contained into
			// the set of the proposition of the model
			// e.g. if the proposition is !a a must not be contained into the
			// propositions of the model
			// if the claim contains !a and the model a the condition is not
			// satisfied
			if (claimProposition.isNegated()) {
				if (modelLabel.contains(new GraphProposition(claimProposition
						.getLabel(), false))) {
					return false;
				}
			} else {
				// if the claim is not negated it MUST be contained into the
				// propositions of the model
				// if the claim is labeled with a and the model does not contain
				// the proposition a the transition is not satisfied
				if (!modelLabel.contains(new GraphProposition(claimProposition
						.getLabel(), false))) {
					return false;
				}
			}
		}
		return true;
	}

	@Override
	public I getIntersectionTransition(S modelState, T claimTransition) {
		Preconditions
				.checkNotNull(modelState, "The model state cannot be null");
		Preconditions.checkNotNull(claimTransition,
				"The claim transition cannot be null");
		Preconditions.checkNotNull(intersectionTransitionFactory,
				"The intersection factory cannot be null");

		return intersectionTransitionFactory.create(
				claimTransition.getPropositions(), modelState);

	}
}
