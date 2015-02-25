package it.polimi.automata.transition.impl;

import it.polimi.automata.state.State;
import it.polimi.automata.transition.IntersectionTransition;

import java.util.Set;

import rwth.i2.ltl2ba4j.model.IGraphProposition;

@SuppressWarnings("serial")
public class IntersectionTransitionImpl<S extends State> extends TransitionImpl
		implements IntersectionTransition<S> {

	/**
	 * is the transparent state that allows to perform the transition
	 */
	private S state;

	/**
	 * creates a new intersection transition
	 * 
	 * @param propositions
	 *            is the set of the propositions that labels the transition
	 * @param id
	 *            is the id of the proposition
	 * @param state
	 *            is the transparent state of the model that allows to perform
	 *            the transition. If the transition does not involve any
	 *            assumption on the transparent state of the model this value is
	 *            null
	 * @throws NullPointerException
	 *             if the propositions or the id is null
	 */
	protected IntersectionTransitionImpl(Set<IGraphProposition> propositions, int id,
			S state) {
		super(propositions, id);
		this.state = state;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public S getTransparentState() {
		return this.state;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString(){
		if(this.state!=null){
			return "<"+super.toString()+" {s: "+this.state.getId()+"}>";
		}
		else{
			return super.toString();
		}
		
	}
}
