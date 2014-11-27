package it.polimi.modelchecker.intersectionbuilder;

import org.apache.commons.collections15.Factory;

import it.polimi.model.impl.states.State;
import it.polimi.model.impl.transitions.Transition;
import it.polimi.model.interfaces.transitions.ConstrainedTransitionFactory;

/**
 * Defines an Abstract {@link IntersectionRule} that specifies how the
 * transitions of the intersection automaton are generated starting from the
 * transition of the model and the claim
 * 
 * @author claudiomenghi
 * 
 * @param <STATE>
 *            is the type of the states of the original BA
 * @param <TRANSITION>
 *            is the type of the transitions of the two original BA
 * @param <INTERSECTIONTRANSITION>
 *            is the type of the transitions of the intersection automata
 * @param <INTERSECTIONTRANSITIONFACTORY>
 *            is the factory which allows to create the transitions of the
 *            intersection automaton
 */
public abstract class IntersectionRule<CONSTRAINEDELEMENT extends State, STATE extends State, TRANSITION extends Transition, INTERSECTIONTRANSITION extends Transition, INTERSECTIONTRANSITIONFACTORY extends ConstrainedTransitionFactory<CONSTRAINEDELEMENT, INTERSECTIONTRANSITION>> {

	/**
	 * specifies how the intersection transition between a transition of the
	 * model and the transition of the claim is computed
	 * 
	 * @param modelTransition
	 *            is the {@link Transition} of the model
	 * @param claimTransition
	 *            is the {@link Transition} of the claim
	 * @param intersectionTransitionFactory
	 *            contains the {@link Factory} to be used to create intersection
	 *            transition
	 * @return a new intersection transition that represents the intersection
	 *         between the transition of the model and the one of the claim, or
	 *         null if the two transitions cannot be fired together
	 * @throws NullPointerException
	 *             if the modelTransition, the claimTransition or the
	 *             intersectionTransitionFactory is null
	 * 
	 */
	public abstract INTERSECTIONTRANSITION getIntersectionTransition(
			TRANSITION modelTransition, TRANSITION claimTransition,
			INTERSECTIONTRANSITIONFACTORY intersectionTransitionFactory);

}
