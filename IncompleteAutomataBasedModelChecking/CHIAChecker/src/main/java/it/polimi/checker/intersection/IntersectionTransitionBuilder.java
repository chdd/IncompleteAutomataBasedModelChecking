package it.polimi.checker.intersection;

import it.polimi.automata.transition.ClaimTransitionFactory;
import it.polimi.automata.transition.PropositionalLogicConstants;
import it.polimi.automata.transition.Transition;

import java.util.Set;

import rwth.i2.ltl2ba4j.model.IGraphProposition;
import rwth.i2.ltl2ba4j.model.impl.GraphProposition;
import rwth.i2.ltl2ba4j.model.impl.SigmaProposition;

import com.google.common.base.Preconditions;

/**
 * Defines an rule that specifies how the transitions of the intersection
 * automaton are generated starting from the transition of the model and the
 * claim. <br>
 * 
 * The rule specifies that the transition of the model can be performed only if
 * it satisfies the conditions specified in the claim. Or, the transition of the
 * claim can be performed if the model is in a black box state
 * 
 * @author Claudio Menghi
 * 
 */
public class IntersectionTransitionBuilder {

	/**
	 * is the factory that allows to create transitions
	 */
	private final ClaimTransitionFactory transitionFactory;

	/**
	 * creates a new Builder that allow to create transitions of the
	 * intersection automaton
	 */
	public IntersectionTransitionBuilder() {
		this.transitionFactory = new ClaimTransitionFactory();
	}

	/**
	 * Given two transitions, one of the model and one of the claim, creates the
	 * intersection transitions. Throws an InternalError if the two transitions
	 * are not compatible, i.e., they cannot be fired together. To check whether
	 * two transitions can be fired together run the is compatible method.
	 * 
	 * @param modelTransition
	 *            the transition of the model to be fired
	 * @param claimTransition
	 *            is the transition of the claim to be fired
	 * @return a transition that is the synchronous execution between the
	 *         modelTransition and the claimTransition
	 * @throws NullPointerException
	 *             if the transition of the model or the claim is null
	 * @throws InternalError
	 *             if the two transitions are not compatible
	 * 
	 */
	public Transition getIntersectionTransition(Transition modelTransition,
			Transition claimTransition) {

		Preconditions.checkNotNull(modelTransition,
				"The model transition cannot be null");
		Preconditions.checkNotNull(claimTransition,
				"The claim transition cannot be null");
		if (!this.isCompatible(modelTransition, claimTransition)) {
			throw new InternalError("The transition of the model:  "
					+ modelTransition + " and the transition of the claim: "
					+ claimTransition + " are not compatible");
		}
		return transitionFactory.create(modelTransition.getPropositions());

	}

	/**
	 * checks whether the transition of the model and the transition of the
	 * claim are compatible, i.e., it is possible to fire the transition of the
	 * model and the claim synchronously
	 * 
	 * @param modelTransition
	 *            is the transition of the model to be fired
	 * @param claimTransition
	 *            is the transition of the claim to be fired
	 * @return true if the label of the transition of model satisfies the label
	 *         of the transition of the claim
	 *         <ul>
	 *         <li>returns true if the stuttering character is contained both on
	 *         the model and the claim label</li>
	 *         <li>returns true if the Sigma proposition is</li>
	 *         <li>returns false if there is a claim proposition that is
	 *         negated, but the proposition is contained in the labels of the
	 *         model</li>
	 *         <li>returns false if there is a claim proposition that is not
	 *         negated, but the proposition is not contained inside the set of
	 *         propositions of the model</li>
	 *         <ul>
	 * 
	 * @throws NullPointerException
	 *             if the transition of the model or the transition of the claim
	 *             is null
	 */
	public boolean isCompatible(Transition modelTransition,
			Transition claimTransition) {
		Preconditions.checkNotNull(
				"The transition of the model cannot be null", modelTransition);
		Preconditions.checkNotNull(
				"The transition of the claim cannot be null", claimTransition);
		return this.satisfies(modelTransition.getPropositions(),
				claimTransition.getPropositions());
	}

	/**
	 * returns true if the label of the model satisfies the label of the claim.
	 * 
	 * @param modelLabel
	 *            is the label of the model
	 * @param claimLabel
	 *            is the label of the claim
	 * @return true if the label of the model satisfies the label of the claim
	 *         <ul>
	 *         <li>returns true if the stuttering character is contained both on
	 *         the model and the claim label</li>
	 *         <li>returns true if the sigma proposition is</li>
	 *         <li>returns false if there is a claim proposition that is
	 *         negated, but the proposition is contained in the labels of the
	 *         model</li>
	 *         <li>returns false if there is a claim proposition that is not
	 *         negated, but the proposition is not contained inside the set of
	 *         propositions of the model</li>
	 *         <ul>
	 * 
	 */
	protected boolean satisfies(Set<IGraphProposition> modelLabel,
			Set<IGraphProposition> claimLabel) {
		Preconditions
				.checkNotNull(modelLabel, "The model label cannot be null");
		Preconditions
				.checkNotNull(claimLabel, "The claim label cannot be null");

		IGraphProposition stuttering = new GraphProposition(
				PropositionalLogicConstants.STUTTERING_CHARACTER, false);
		if (modelLabel.contains(stuttering)) {
			return claimLabel.contains(stuttering);
		}
		// if the proposition is SigmaProposition it is satisfied by the
		// proposition of
		// the model
		if (claimLabel.contains(new SigmaProposition())) {
			return true;
		}
		// for each proposition of the claim
		for (IGraphProposition claimProposition : claimLabel) {

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
}
