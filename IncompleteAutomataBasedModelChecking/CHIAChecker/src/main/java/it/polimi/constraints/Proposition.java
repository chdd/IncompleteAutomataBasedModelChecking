package it.polimi.constraints;

import it.polimi.automata.labeling.Label;
import it.polimi.automata.state.State;
import it.polimi.automata.transition.Transition;

/**
 * contains a new Proposition. The proposition encodes the condition a state of
 * the automaton must satisfy
 * 
 * @see {@link State}
 * @see {@link Label}
 * 
 * @author claudiomenghi
 * @param <STATE>
 *            is the type of the state constrained by the proposition.
 * @param <TRANSITION>
 *            is the type of the transition of the Buchi Automaton. The typer of
 *            the transitions of the automaton must implement the interface
 *            {@link Transition}
 * @param <LABEL>
 *            is the type of the label of the transitions depending on whether
 *            the automaton represents the model or the claim it is a set of
 *            proposition or a propositional logic formula {@link Label}
 */
public class Proposition<LABEL extends Label, STATE extends State, TRANSITION extends Transition<LABEL>> {

	/**
	 * is the regular expression that must be satisfied in the refinement when
	 * the incoming transition is performed to enter the state and the
	 * out-coming transition is executed to exit it.
	 */
	private final String regex;
	/**
	 * contains the state that is constrained
	 */
	private final STATE state;
	/**
	 * contains the transition that is performed to enter the state of the
	 * automaton
	 */
	private final TRANSITION incoming;
	/**
	 * contains the transition that is performed to exit the state of the
	 * automaton
	 */
	private final TRANSITION outcoming;

	/**
	 * creates a new Proposition
	 * 
	 * @param state
	 *            is the state constrained by the proposition
	 * @param regex
	 *            is regular expression the refinement of the state must satisfy
	 * @param incoming
	 *            contains the transition that is performed to enter the state
	 *            of the automaton
	 * @param outcoming
	 *            contains the transition that is performed to exit the state of
	 *            the automaton
	 * @throws NullPointerException
	 *             if the state, the regular expression, the incoming or
	 *             out-coming transition is null
	 * 
	 */
	public Proposition(STATE state, String regex, TRANSITION incoming,
			TRANSITION outcoming) {
		if (state == null) {
			throw new NullPointerException(
					"The state to be constrained cannot be null");
		}
		if (regex == null) {
			throw new NullPointerException(
					"The regular expression associated with the state cannot be null");
		}
		if (incoming == null) {
			throw new NullPointerException(
					"The incoming transition cannot be null");
		}
		if (outcoming == null) {
			throw new NullPointerException(
					"The outcoming transition cannot be null");
		}

		this.state = state;
		this.regex = regex;
		this.incoming = incoming;
		this.outcoming = outcoming;
	}

	/**
	 * the regular expression that constraint the state when it is entered and
	 * exited using the incoming and out-coming transition, respectively
	 * 
	 * @return the regular expression that constraint the state when it is
	 *         entered and exited using the incoming and out-coming transition,
	 *         respectively
	 */
	public String getLabel() {
		return regex;
	}

	/**
	 * the state that is constrained
	 * 
	 * @return the state that is constrained
	 */
	public STATE getState() {
		return state;
	}

	/**
	 * the transition that is performed to enter the state
	 * 
	 * @return the incoming the transition that is performed to enter the state
	 */
	public TRANSITION getIncoming() {
		return incoming;
	}

	/**
	 * the transition that is performed to exit the state
	 * 
	 * @return the out-coming transition: the transition that is performed to
	 *         exit the state
	 */
	public TRANSITION getOutcoming() {
		return outcoming;
	}
}
