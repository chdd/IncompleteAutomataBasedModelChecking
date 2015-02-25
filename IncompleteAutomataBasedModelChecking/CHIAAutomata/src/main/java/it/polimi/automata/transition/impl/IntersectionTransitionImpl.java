package it.polimi.automata.transition.impl;

import it.polimi.automata.state.State;
import it.polimi.automata.transition.IntersectionTransition;

import java.util.Set;

import rwth.i2.ltl2ba4j.model.IGraphProposition;

@SuppressWarnings("serial")
public class IntersectionTransitionImpl<S extends State> extends TransitionImpl implements IntersectionTransition<S> {

	/**
	 * is the transparent state that allows to perform the transition
	 */
	private S state;
	
	
	protected IntersectionTransitionImpl(Set<IGraphProposition> labels, int id,
			S state) {
		super(labels, id);
		this.state = state;
	}

	/**
	 * returns true if the transition is a possible transition, i.e., a
	 * transition associated with a transparent state, false otherwise
	 * 
	 * @return true if the transition is a possible transition, i.e., a
	 *         transition associated with a transparent state, false otherwise
	 */
	public S getTransparentState() {
		return this.state;
	}
	
	 

}
