package it.polimi.checker.ibatransparentstateremoval;

import com.google.common.base.Preconditions;

import it.polimi.automata.IBA;
import it.polimi.automata.state.State;
import it.polimi.automata.transition.Transition;

/**
 * this class starting from an Incomplete Buchi automaton generates an
 * equivalent Incomplete Buchi Automaton where the transparent states and their
 * incoming and out-coming transitions are simply removed
 * 
 * @author claudiomenghi
 * @param <S>
 *            is the type of the state of the Buchi Automaton. The type of the
 *            states of the automaton must implement the interface {@link State}
 * @param <T>
 *            is the type of the transition of the Buchi Automaton. The typer of
 *            the transitions of the automaton must implement the interface
 *            {@link Transition}
 */
public class IBATransparentStateRemoval<S extends State, T extends Transition> {

	/**
	 * removes from the Incomplete Buchi Automaton all the transparent states
	 * and the corresponding incoming and out coming transitions
	 * 
	 * @param iba
	 *            is the Incomplete Buchi Automaton to be modified
	 * @return a modified version of the Incomplete Buchi Automaton where the
	 *         transparent states are removed. The initial Incomplete Buchi
	 *         Automaton is not modified
	 * @throws NullPointerException
	 *             if the Incomplete Buchi Automaton is null
	 */
	public IBA<S, T> transparentStateRemoval(
			IBA<S, T> iba) {
		Preconditions.checkNotNull(iba , "The Incomplete Buchi Automaton cannot be null");
		

		IBA<S, T> retIba = iba.clone();
		for (S s : retIba.getTransparentStates()) {
			retIba.removeState(s);
		}
		return retIba;
	}

}
