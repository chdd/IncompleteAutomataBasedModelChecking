package it.polimi.automata.factories.impl;

import it.polimi.automata.BA;
import it.polimi.automata.State;
import it.polimi.automata.Transition;
import it.polimi.automata.factories.BAFactory;
import it.polimi.automata.impl.BAImpl;
import it.polimi.automata.impl.StateImpl;
import it.polimi.automata.impl.TransitionImpl;
import it.polimi.automata.labeling.Label;

/**
 * <p>
 * Represents the factory used to create a Buchi Automaton <br>
 * Implements the method of the BAFactory class  {@link BAFactory}
 * @see {@link BAFactory}
 * </p>
 * 
 * @author claudiomenghi
 * @param <STATE>
 *            is the type of the state of the Buchi Automaton. The type of the
 *            states of the automaton must implement the interface {@link State}
 * @param <TRANSITION>
 *            is the type of the transition of the Buchi Automaton. The typer of
 *            the transitions of the automaton must implement the interface
 *            {@link Transition}
 * @param <LABEL>
 *            is the type of the label of the transitions depending on whether
 *            the automaton represents the model or the claim it is a set of
 *            proposition or a propositional logic formula {@link Label}
 */
public class BAFactoryImpl<
	LABEL extends Label,
	STATE extends StateImpl,
	TRANSITION extends TransitionImpl<LABEL>>
	implements
	BAFactory<LABEL, STATE, TRANSITION>{
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public BA<LABEL, STATE, TRANSITION> create() {
		return new BAImpl<LABEL, STATE, TRANSITION>();
	}
}
