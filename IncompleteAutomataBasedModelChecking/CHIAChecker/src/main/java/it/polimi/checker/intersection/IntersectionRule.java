package it.polimi.checker.intersection;

import it.polimi.automata.state.State;
import it.polimi.automata.transition.IntersectionTransition;
import it.polimi.automata.transition.IntersectionTransitionFactory;
import it.polimi.automata.transition.Transition;

/**
 * Defines an {@link IntersectionRule} that specifies how the transitions of the
 * intersection automaton is generated starting from the transition of the model
 * and the claim
 * 
 * @author claudiomenghi
 * 
 * @param <S>
 *            is the type of the states of the model and the claim under
 *            analysis
 * @param <T>
 *            is the type of the transitions under analysis
 */
public interface IntersectionRule<S extends State, T extends Transition, I extends IntersectionTransition<S>> {

	/**
	 * specifies how the intersection transition between a transition of the
	 * model and the transition of the claim is computed. Returns null when the
	 * two transitions are not compatible
	 * 
	 * @param modelTransition
	 *            is the transition of the model
	 * @param claimTransition
	 *            is the transition of the claim
	 * @param intersectionTransitionFactory
	 *            contains the factory to be used to create the intersection
	 *            transition
	 * @return a new transition that represents the intersection between the
	 *         transition of the model and the one of the claim, or null if the
	 *         two transitions cannot be fired together. null if a new
	 *         transition cannot be created
	 * @throws NullPointerException
	 *             if the modelTransition, the claimTransition or the
	 *             intersectionTransitionFactory is null
	 * 
	 */
	public abstract I getIntersectionTransition(T modelTransition,
			T claimTransition,
			IntersectionTransitionFactory<S, I> intersectionTransitionFactory);

	/**
	 * specifies how the intersection transition must be created when the model
	 * is in a transparent state
	 * 
	 * @param modelState
	 *            is the transparent state to be considered
	 * @param claimTransition
	 *            is the transition performed by the claim
	 * @param intersectionTransitionFactory
	 *            is the transition factory used to create the transition
	 * @return a new intersection transition
	 * @throws NullPointerException
	 *             if one of the parameters is null
	 */
	public abstract I getIntersectionTransition(S modelState,
			T claimTransition,
			IntersectionTransitionFactory<S, I> intersectionTransitionFactory);

}
