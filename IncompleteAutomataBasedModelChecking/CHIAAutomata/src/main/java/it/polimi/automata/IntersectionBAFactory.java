package it.polimi.automata;

import it.polimi.automata.labeling.Label;
import it.polimi.automata.state.State;
import it.polimi.automata.transition.Transition;

/**
 * <p>
 * Represents the factory used to create the intersection automaton Buchi Automaton <br>
 * </p>
 * 
 * @author claudiomenghi
 * @param <S>
 *            is the type of the state of the Intersection Automaton. The type of the
 *            states of the Incomplete automaton must implement the interface {@link State}
 * @param <T>
 *            is the type of the transition of the Intersection Automaton. The type of
 *            the transitions of the automaton must implement the interface
 *            {@link Transition}
 * @param <L>
 *            is the type of the label of the transitions depending on whether
 *            the automaton represents the model or the claim it is a set of
 *            proposition or a propositional logic formula {@link Label}
 */
public interface IntersectionBAFactory<L extends Label, S extends State, T extends Transition<L>>{

	/**
	 * {@inheritDoc}
	 */
	public IntersectionBA<L, S, T> create();
}
