package it.polimi.automata.factories;

import it.polimi.automata.IBA;
import it.polimi.automata.State;
import it.polimi.automata.Transition;
import it.polimi.automata.labeling.Label;

/**
 * <p>
 * Represents the factory used to create an Incomplete Buchi Automaton <br>
 * </p>
 * 
 * @author claudiomenghi
 * @param <STATE>
 *            is the type of the state of the Incomplete Buchi Automaton. The type of the
 *            states of the Incomplete automaton must implement the interface {@link State}
 * @param <TRANSITION>
 *            is the type of the transition of the Incomplete Buchi Automaton. The type of
 *            the transitions of the automaton must implement the interface
 *            {@link Transition}
 * @param <LABEL>
 *            is the type of the label of the transitions depending on whether
 *            the automaton represents the model or the claim it is a set of
 *            proposition or a propositional logic formula {@link Label}
 */
public interface IBAFactory<LABEL extends Label, STATE extends State, TRANSITION extends Transition<LABEL>>{

	/**
	 * {@inheritDoc}
	 */
	public IBA<LABEL, STATE, TRANSITION> create();
}
