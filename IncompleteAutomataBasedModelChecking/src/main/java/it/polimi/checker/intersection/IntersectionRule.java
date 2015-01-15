package it.polimi.checker.intersection;

import org.apache.commons.collections15.Factory;

import it.polimi.automata.labeling.Label;
import it.polimi.automata.state.State;
import it.polimi.automata.transition.Transition;
import it.polimi.automata.transition.impl.IntersectionTransitionFactoryImpl;
import it.polimi.model.interfaces.transitions.ConstrainedTransitionFactory;

/**
 * Defines an Abstract {@link IntersectionRule} that specifies how the
 * transitions of the intersection automaton is generated starting from the
 * transition of the model and the claim
 * 
 * @author claudiomenghi
 * 
 * @param <STATE>
 *            is the type of the state of the Buchi Automaton. The type of the
 *            states of the automaton must implement the interface {@link State}
 * @param <TRANSITION>
 *            is the type of the transition of the Buchi Automaton. The type of
 *            the transitions of the automaton must implement the interface
 *            {@link Transition}
 * @param <LABEL>
 *            is the type of the label of the transitions depending on whether
 *            the automaton represents the model or the claim it is a set of
 *            proposition or a propositional logic formula {@link Label}
 */
public abstract class IntersectionRule<LABEL extends Label, STATE extends State, TRANSITION extends Transition<LABEL>> {

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
	 *         null if the two transitions cannot be fired together
	 * @throws NullPointerException
	 *             if the modelTransition, the claimTransition or the
	 *             intersectionTransitionFactory is null
	 * 
	 */
	public abstract TRANSITION getIntersectionTransition(
			TRANSITION modelTransition, TRANSITION claimTransition,
			IntersectionTransitionFactoryImpl<LABEL> intersectionTransitionFactory);

}
