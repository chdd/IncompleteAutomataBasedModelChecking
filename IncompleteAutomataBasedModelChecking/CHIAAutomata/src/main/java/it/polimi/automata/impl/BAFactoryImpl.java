package it.polimi.automata.impl;

import it.polimi.automata.BA;
import it.polimi.automata.BAFactory;
import it.polimi.automata.labeling.Label;
import it.polimi.automata.state.State;
import it.polimi.automata.transition.Transition;

/**
 * <p>
 * Represents the factory used to create a Buchi Automaton <br>
 * Implements the method of the BAFactory class  {@link BAFactory}
 * @see {@link BAFactory}
 * </p>
 * 
 * @author claudiomenghi
 * @param <S>
 *            is the type of the state of the Buchi Automaton. The type of the
 *            states of the automaton must implement the interface {@link State}
 * @param <T>
 *            is the type of the transition of the Buchi Automaton. The typer of
 *            the transitions of the automaton must implement the interface
 *            {@link Transition}
 * @param <L>
 *            is the type of the label of the transitions depending on whether
 *            the automaton represents the model or the claim it is a set of
 *            proposition or a propositional logic formula {@link Label}
 */
public class BAFactoryImpl<
	L extends Label,
	S extends State,
	T extends Transition<L>>
	implements
	BAFactory<L, S, T>{
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public BA<L, S, T> create() {
		return new BAImpl<L, S, T>();
	}
}
