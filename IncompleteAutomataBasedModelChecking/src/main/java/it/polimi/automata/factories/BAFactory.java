package it.polimi.automata.factories;

import it.polimi.automata.BA;
import it.polimi.automata.State;
import it.polimi.automata.Transition;
import it.polimi.automata.labeling.Label;

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
public interface BAFactory<LABEL extends Label, STATE extends State, TRANSITION extends Transition<LABEL>, AUTOMATON extends BA<LABEL, STATE, TRANSITION>>
		extends Factory<AUTOMATON> {

	/**
	 * creates the Buchi Automaton
	 * @return the Buchi automaton
	 */
	public BA<LABEL, STATE, TRANSITION> createBA();
}
