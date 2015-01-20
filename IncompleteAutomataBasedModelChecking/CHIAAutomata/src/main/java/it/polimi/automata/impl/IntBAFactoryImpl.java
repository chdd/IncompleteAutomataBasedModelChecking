package it.polimi.automata.impl;

import it.polimi.automata.IntersectionBAFactory;
import it.polimi.automata.labeling.Label;
import it.polimi.automata.state.State;
import it.polimi.automata.transition.Transition;


/**
 * <p>
 * Represents the factory used to create the Intersection Automaton <br>
 * Implements the method of the IntersectionBAFactory class {@link IntersectionBAFactory}
 * 
 * @see {@link IntersectionBAFactory}
 *      </p>
 * 
 * @author claudiomenghi
 * @param <STATE>
 *            is the type of the state of the Intersection Buchi Automaton. The
 *            type of the states of the Incomplete automaton must implement the
 *            interface {@link State}
 * @param <TRANSITION>
 *            is the type of the transition of the Intersection Buchi Automaton.
 *            The type of the transitions of the automaton must implement the
 *            interface {@link Transition}
 * @param <LABEL>
 *            is the type of the label of the transitions depending on whether
 *            the automaton represents the model or the claim it is a set of
 *            proposition or a propositional logic formula {@link Label}
 */
public class IntBAFactoryImpl<LABEL extends Label, STATE extends State, TRANSITION extends Transition<LABEL>> implements IntersectionBAFactory<LABEL, STATE, TRANSITION>{
	/**
	 * {@inheritDoc}
	 */
	@Override
	public IntBAImpl<LABEL, STATE, TRANSITION> create() {
		return new IntBAImpl<LABEL, STATE, TRANSITION>();
	}
}
