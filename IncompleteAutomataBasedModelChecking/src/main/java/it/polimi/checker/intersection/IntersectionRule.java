package it.polimi.checker.intersection;

import it.polimi.automata.labeling.Label;
import it.polimi.automata.transition.Transition;
import it.polimi.automata.transition.TransitionFactory;

/**
 * Defines an {@link IntersectionRule} that specifies how the
 * transitions of the intersection automaton is generated starting from the
 * transition of the model and the claim
 * 
 * @author claudiomenghi
 * 
 * @param <LABEL>
 *            is the type of the label of the transitions depending on whether
 *            the automaton represents the model or the claim it is a set of
 *            proposition or a propositional logic formula {@link Label}
 */
public interface IntersectionRule<LABEL extends Label, TRANSITION extends Transition<LABEL>> {

	/**
	 * specifies how the intersection transition between a transition of the
	 * model and the transition of the claim is computed
	 * 
	 * @param modelTransition
	 *            is the transition of the model
	 * @param claimTransition
	 *            is the transition of the claim
	 * @param intersectionTransitionFactory
	 *            contains the factory to be used to create the intersection
	 *            transition
	 * @return a new transition that represents the intersection
	 *         between the transition of the model and the one of the claim, or
	 *         null if the two transitions cannot be fired together.
	 *         null if a new transition cannot be created
	 * @throws NullPointerException
	 *             if the modelTransition, the claimTransition or the
	 *             intersectionTransitionFactory is null
	 * 
	 */
	public abstract TRANSITION getIntersectionTransition(
			TRANSITION modelTransition, TRANSITION claimTransition,
			TransitionFactory<LABEL, TRANSITION> intersectionTransitionFactory);

}
