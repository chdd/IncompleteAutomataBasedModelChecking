package it.polimi.automata;

import it.polimi.automata.labeling.Label;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 * <p>
 * Represents an Incomplete Buchi Automaton which extends a Buchi automaton with
 * transparent states<br>
 * 
 * @see BA The state of the automaton must must implement the {@link State}
 *      interface <br>
 *      The transition of the automaton must must implement the
 *      {@link Transition} interface <br>
 *      The label of the transition must implement the label interface and
 *      depending on whether the automaton represents the model or the claim it
 *      is a set of proposition or a propositional logic formula
 *      </p>
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
public interface IBA<LABEL extends Label, STATE extends State, TRANSITION extends Transition<LABEL>>
		extends BA<LABEL, STATE, TRANSITION> {

	/**
	 * check if the state is transparent
	 * 
	 * @param s
	 *            is the state to be checked if transparent
	 * @return true if the state s is transparent, false otherwise
	 * @throws IllegalArgumentException
	 *             if the state s is null or if the state is not contained into
	 *             the set of the states of the automaton
	 */
	public boolean isTransparent(STATE s);

	/**
	 * returns the set of the transparent states of the Incomplete Buchi
	 * Automaton
	 * 
	 * @return the set of the transparent states of the Incomplete Buchi
	 *         Automaton (if no transparent states are present an empty set is
	 *         returned)
	 */
	public Set<STATE> getTransparentStates();

	/**
	 * adds the transparent state s to the states of the {@link IBA} if the
	 * state is already transparent no action is performed
	 * 
	 * @param s
	 *            the state to be added in the {@link IBA}
	 * @throws NullPointerException
	 *             if the state s is null
	 */
	public void addTransparentState(STATE s);

	/**
	 * returns a new Incomplete Buchi automaton where the transparent state is
	 * replaced by the ibaToInject the inComing and outComing transitions
	 * specifies how the initial and the accepting states are connected with the
	 * current Incomplete Buchi automaton
	 * 
	 * @see IBA#clone()
	 * 
	 * @param transparentState
	 *            is the transparent state to be replaced
	 * @param ibaToInject
	 *            is the Incomplete Buchi Automaton to be injected inside the
	 *            Incomplete Buchi Automaton
	 * @param inComing
	 *            are the transitions that connect the refinement of the
	 *            transparent state with the current incomplete Buchi Automaton
	 * @param outComing
	 *            are the transitions that connect the refinement of the
	 *            transparent state with the current incomplete Buchi Automaton
	 * @throws NullPointerException
	 *             if the transparent state, the ibaToInject, the inComing or
	 *             outComing transitions are null
	 */
	public IBA<LABEL, STATE, TRANSITION> replace(STATE transparentState,
			IBA<LABEL, STATE, TRANSITION> ibaToInject,
			Map<STATE, Entry<TRANSITION, STATE>> inComing,
			Map<STATE, Entry<TRANSITION, STATE>> outComing);

	/**
	 * creates a copy of the Incomplete Buchi Automaton
	 * 
	 * @return a copy of the Incomplete Buchi Automaton
	 */
	public IBA<LABEL, STATE, TRANSITION> clone();
}
