package it.polimi.checker.ibaTransparentStateRemoval;

import it.polimi.automata.IBA;
import it.polimi.automata.labeling.Label;
import it.polimi.automata.state.State;
import it.polimi.automata.transition.Transition;

/**
 * this class starting from an Incomplete Buchi automaton generates an
 * equivalent Incomplete Buchi Automaton where the transparent states and their
 * incoming and out-coming transitions are simply removed
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
public class IBATransparentStateRemoval<LABEL extends Label, STATE extends State, TRANSITION extends Transition<LABEL>> {

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
	public IBA<LABEL, STATE, TRANSITION> transparentStateRemoval(
			IBA<LABEL, STATE, TRANSITION> iba) {
		if (iba == null) {
			throw new NullPointerException(
					"The Incomplete Buchi Automaton cannot be null");
		}

		IBA<LABEL, STATE, TRANSITION> retIba = iba.clone();
		for (STATE s : retIba.getTransparentStates()) {
			retIba.removeState(s);
		}
		return retIba;
	}

}
