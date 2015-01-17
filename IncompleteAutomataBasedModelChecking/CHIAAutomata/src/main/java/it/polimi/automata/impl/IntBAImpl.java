package it.polimi.automata.impl;

import it.polimi.automata.BA;
import it.polimi.automata.IntersectionBA;
import it.polimi.automata.labeling.Label;
import it.polimi.automata.state.State;
import it.polimi.automata.state.impl.StateImpl;
import it.polimi.automata.transition.Transition;
import it.polimi.automata.transition.impl.TransitionImpl;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * <p>
 * Represents the Intersection Buchi Automaton which extends a Buchi automaton with
 * mixed states, implements the {@link IntersectionBA} interface <br>
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
 *            is the type of the state of the Intersection Buchi Automaton. The type of the
 *            states of the automaton must implement the interface {@link State}
 * @param <TRANSITION>
 *            is the type of the transition of the Intersection Buchi Automaton. The type of
 *            the transitions of the automaton must implement the interface
 *            {@link Transition}
 * @param <LABEL>
 *            is the type of the label of the transitions depending on whether
 *            the automaton represents the model or the claim it is a set of
 *            proposition or a propositional logic formula {@link Label}
 */
public class IntBAImpl<LABEL extends Label, STATE extends StateImpl, TRANSITION extends TransitionImpl<LABEL>>
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
			throw new IllegalArgumentException(
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
}