package it.polimi.contraintcomputation;

import rwth.i2.ltl2ba4j.model.impl.GraphProposition;
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
 * @param <S>
 *            is the type of the state constrained by the proposition.
 * @param <T>
 *            is the type of the transition of the Buchi Automaton. The typer of
 *            the transitions of the automaton must implement the interface
 *            {@link Transition}
 * @param <L>
 *            is the type of the label of the transitions depending on whether
 *            the automaton represents the model or the claim it is a set of
 *            proposition or a propositional logic formula {@link Label}
 */
class RegexProposition<L extends Label, S extends State, T extends Transition<L>> extends GraphProposition {

	/**
	 * contains the state that is constrained
	 */
	private final S state;
	/**
	 * contains the transition that is performed to enter the state of the
	 * automaton
	 */
	private final T incoming;
	/**
	 * contains the transition that is performed to exit the state of the
	 * automaton
	 */
	private final T outcoming;

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
	public RegexProposition(S state, String regex, T incoming,
			T outcoming) {
		super(regex, false);
		if (state == null) {
			throw new NullPointerException(
					"The state to be constrained cannot be null");
		}
		if(regex==null){
			throw new NullPointerException(
					"The regular expression to be constrained cannot be null");
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
		this.incoming = incoming;
		this.outcoming = outcoming;
	}

	/**
	 * the state that is constrained
	 * 
	 * @return the state that is constrained
	 */
	public S getState() {
		return state;
	}

	/**
	 * the transition that is performed to enter the state
	 * 
	 * @return the incoming the transition that is performed to enter the state
	 */
	public T getIncoming() {
		return incoming;
	}

	/**
	 * the transition that is performed to exit the state
	 * 
	 * @return the out-coming transition: the transition that is performed to
	 *         exit the state
	 */
	public T getOutcoming() {
		return outcoming;
	}
}
