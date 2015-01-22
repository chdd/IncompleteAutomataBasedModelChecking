package it.polimi.automata.impl;

import it.polimi.automata.IBA;
import it.polimi.automata.IBAFactory;
import it.polimi.automata.labeling.Label;
import it.polimi.automata.state.State;
import it.polimi.automata.transition.Transition;

/**
 * <p>
 * Represents the factory used to create an Incomplete Buchi Automaton <br>
 * Implements the method of the IBAFactory class {@link IBAFactory}
 * 
 * @see {@link IBAFactory}
 *      </p>
 * 
 * @author claudiomenghi
 * @param <S>
 *            is the type of the state of the Incomplete Buchi Automaton. The
 *            type of the states of the Incomplete automaton must implement the
 *            interface {@link State}
 * @param <T>
 *            is the type of the transition of the Incomplete Buchi Automaton.
 *            The type of the transitions of the automaton must implement the
 *            interface {@link Transition}
 * @param <L>
 *            is the type of the label of the transitions depending on whether
 *            the automaton represents the model or the claim it is a set of
 *            proposition or a propositional logic formula {@link Label}
 */
public class IBAFactoryImpl<L extends Label, S extends State, T extends Transition<L>>
		implements IBAFactory<L, S, T> {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public IBA<L, S, T> create() {
		return new IBAImpl<L, S, T>();
	}
}
