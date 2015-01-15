package it.polimi.automata;

import it.polimi.automata.labeling.Label;
import it.polimi.automata.state.State;
import it.polimi.automata.transition.Transition;

import org.apache.commons.collections15.Factory;

/**
 * <p>
 * Represents the factory used to create a Buchi Automaton <br>
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
public interface BAFactory<LABEL extends Label, STATE extends State, TRANSITION extends Transition<LABEL>>
		extends Factory<BA<LABEL, STATE, TRANSITION>> {

	/**
	 * {@inheritDoc}
	 */
	public BA<LABEL, STATE, TRANSITION> create();
}
