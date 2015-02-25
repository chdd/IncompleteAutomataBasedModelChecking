package it.polimi.automata.transition;

import it.polimi.automata.state.State;

public interface IntersectionTransition<S extends State> extends Transition {

	/**
	 * returns the transparent state that is associated to the transition, i.e.,
	 * the transparent state that must be able to ``fire" the transition of the
	 * claim. It returns a null value if no transparent state is associated,
	 * i.e., the transition corresponds with a transition generated from a
	 * transition of the model and a transition of the claim
	 * 
	 * @return the transparent state that is associated to the transition, i.e.,
	 *         the transparent state that must be able to ``fire" the transition
	 *         of the claim. It returns a null value if no transparent state is
	 *         associated, i.e., the transition corresponds with a transition
	 *         generated from a transition of the model and a transition of the
	 *         claim
	 */
	public S getTransparentState();
}
