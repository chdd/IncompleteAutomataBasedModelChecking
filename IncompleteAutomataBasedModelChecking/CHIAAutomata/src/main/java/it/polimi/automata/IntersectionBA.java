package it.polimi.automata;

import it.polimi.automata.labeling.Label;
import it.polimi.automata.state.State;
import it.polimi.automata.transition.Transition;

import java.util.Set;

/**
 * <p>
 * Represents a the <b>Intersection</b> of a Buchi Automaton and an Incomplete Buchi automaton<br>
 * The state of the automaton must must implement the {@link State} interface <br>
 * The transition of the automaton must must implement the {@link Transition}
 * interface <br>
 * The label of the transition must implement the label interface and depending
 * on whether the automaton represents the model or the claim it is a set of
 * proposition or a propositional logic formula
 * </p>
 * 
 * @author claudiomenghi
 * @param <STATE>
 *            is the type of the state of the Intersection Buchi Automaton. The type of the
 *            states of the automaton must implement the interface {@link State}
 * @param <TRANSITION>
 *            is the type of the transition of the Intersection Buchi Automaton. The typer of
 *            the transitions of the automaton must implement the interface
 *            {@link Transition}
 * @param <LABEL>
 *            is the type of the label of the transitions depending on whether
 *            the automaton represents the model or the claim it is a set of
 *            proposition or a propositional logic formula {@link Label}
 */
public interface IntersectionBA<LABEL extends Label, STATE extends State, TRANSITION extends Transition<LABEL>>
	extends BA<LABEL, STATE,TRANSITION> {
	
	/**
	 * returns the set of the mixed states of the Intersection Buchi automaton
	 * 
	 * @return the set of the mixed states of the Intersection Buchi automaton
	 */
	public Set<STATE> getMixedStates();
	
	/**
	 * adds the mixed state s to the states of the {@link IBA} and to the
	 * set of the mixed state<br>
	 * if the state is already mixed no action is performed <br>
	 * if the state is a state of the BA but is not mixed, it is also
	 * added to the set of the mixed state
	 * 
	 * @param s
	 *            the state to be added in the {@link IBA}
	 * @throws NullPointerException
	 *             if the state s is null
	 */
	public void addMixedState(STATE s);
}
