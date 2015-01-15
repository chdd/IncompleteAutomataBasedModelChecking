package it.polimi.automata.impl;

import it.polimi.automata.IBA;
import it.polimi.automata.IBAFactory;
import it.polimi.automata.labeling.Label;
import it.polimi.automata.state.State;
import it.polimi.automata.state.impl.StateImpl;
import it.polimi.automata.transition.Transition;
import it.polimi.automata.transition.impl.TransitionImpl;

/**
 * <p>
 * Represents the factory used to create an Incomplete Buchi Automaton <br>
 * Implements the method of the IBAFactory class {@link IBAFactory}
 * 
 * @see {@link IBAFactory}
 *      </p>
 * 
 * @author claudiomenghi
 * @param <STATE>
 *            is the type of the state of the Incomplete Buchi Automaton. The
 *            type of the states of the Incomplete automaton must implement the
 *            interface {@link State}
 * @param <TRANSITION>
 *            is the type of the transition of the Incomplete Buchi Automaton.
 *            The type of the transitions of the automaton must implement the
 *            interface {@link Transition}
 * @param <LABEL>
 *            is the type of the label of the transitions depending on whether
 *            the automaton represents the model or the claim it is a set of
 *            proposition or a propositional logic formula {@link Label}
 */
public class IBAFactoryImpl<LABEL extends Label, STATE extends StateImpl, TRANSITION extends TransitionImpl<LABEL>>
		implements IBAFactory<LABEL, STATE, TRANSITION> {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public IBA<LABEL, STATE, TRANSITION> create() {
		return new IBAImpl<LABEL, STATE, TRANSITION>();
	}
}
