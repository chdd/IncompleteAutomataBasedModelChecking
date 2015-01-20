package it.polimi.automata.impl;

import it.polimi.automata.BA;
import it.polimi.automata.IntersectionBA;
import it.polimi.automata.labeling.Label;
import it.polimi.automata.state.State;
import it.polimi.automata.transition.Transition;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * <p>
 * Represents the Intersection Buchi Automaton which extends a Buchi automaton
 * with mixed states, implements the {@link IntersectionBA} interface <br>
 * <br>
 * 
 * @see BA The state of the automaton must must implement the {@link State}
 *      interface <br>
 *      The transition of the automaton must must implement the
 *      {@link Transition} interface <br>
 *      The label of the transition must implement the label interface and
 *      depending on whether the automaton represents the model or the claim it
 *      is a set of proposition or a propositional logic formula
 *      </p>
 * 
 * @author claudiomenghi
 * @param <STATE>
 *            is the type of the state of the Intersection Buchi Automaton. The
 *            type of the states of the automaton must implement the interface
 *            {@link State}
 * @param <TRANSITION>
 *            is the type of the transition of the Intersection Buchi Automaton.
 *            The type of the transitions of the automaton must implement the
 *            interface {@link Transition}
 * @param <LABEL>
 *            is the type of the label of the transitions depending on whether
 *            the automaton represents the model or the claim it is a set of
 *            proposition or a propositional logic formula {@link Label}
 */
public class IntBAImpl<LABEL extends Label, STATE extends State, TRANSITION extends Transition<LABEL>>
		extends BAImpl<LABEL, STATE, TRANSITION> implements
		IntersectionBA<LABEL, STATE, TRANSITION> {

	/**
	 * contains the set of the mixed states
	 */
	private Set<STATE> mixedStates;

	/**
	 * creates a new intersection automaton
	 */
	protected IntBAImpl() {
		super();
		this.mixedStates = new HashSet<STATE>();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addMixedState(STATE s) {
		if (s == null) {
			throw new NullPointerException(
					"The state to be added cannot be null");
		}
		this.mixedStates.add(s);
		this.addState(s);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Set<STATE> getMixedStates() {
		return Collections.unmodifiableSet(mixedStates);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return super.toString() + "mixedStates: " + this.mixedStates + "\n";
	}

	/**
	 * returns a copy of the intersection automaton
	 * 
	 * @return a copy of the intersection automaton
	 */
	@Override
	public IntersectionBA<LABEL, STATE, TRANSITION> clone() {
		IntersectionBA<LABEL, STATE, TRANSITION> retBA = new IntBAFactoryImpl<LABEL, STATE, TRANSITION>()
				.create();
		for (STATE s : this.getStates()) {
			retBA.addState(s);
			if (this.getAcceptStates().contains(s)) {
				retBA.addAcceptState(s);
			}
			if (this.getInitialStates().contains(s)) {
				retBA.addInitialState(s);
			}
			if (this.getMixedStates().contains(s)) {
				retBA.addMixedState(s);
			}
		}
		for (TRANSITION t : this.getTransitions()) {
			retBA.addTransition(this.getTransitionSource(t),
					this.getTransitionDestination(t), t);
		}
		
		

		return retBA;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((mixedStates == null) ? 0 : mixedStates.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		@SuppressWarnings("unchecked")
		IntBAImpl<LABEL, STATE, TRANSITION> other = (IntBAImpl<LABEL, STATE, TRANSITION>) obj;
		if (mixedStates == null) {
			if (other.mixedStates != null)
				return false;
		} else if (!mixedStates.equals(other.mixedStates))
			return false;
		return true;
	}
}