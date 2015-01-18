package it.polimi.contraintcomputation.brzozowski;

import it.polimi.automata.BA;
import it.polimi.automata.labeling.Label;
import it.polimi.automata.state.State;
import it.polimi.automata.transition.Transition;

import java.util.List;

import org.apache.commons.collections15.Transformer;

/**
 * Given an ordering between the states (a list) and a final state and an
 * automaton return a vector where the final state is encoded with the character
 * "λ" while all the other states are encoded with the character "∅"
 * 
 * @author claudiomenghi
 * 
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
public class AcceptingStatesTransformer<LABEL extends Label, STATE extends State, TRANSITION extends Transition<LABEL>>
		implements Transformer<BA<LABEL, STATE, TRANSITION>, String[]> {

	/**
	 * contains the state to be considered as final in the automaton
	 */
	private final STATE finalState;
	/**
	 * contains the list which describes an ordering between the states
	 */
	private final List<STATE> orderedStates;

	/**
	 * creates the transformer with the specified ordering between states and
	 * final state
	 * 
	 * @param orderedStates
	 *            is the list of the states
	 * @param finalState
	 *            is the state to be considered as final
	 * @throws NullPointerException
	 *             if the list of the ordered state or the final state is null
	 */
	public AcceptingStatesTransformer(List<STATE> orderedStates,
			STATE finalState) {
		if (finalState == null) {
			throw new NullPointerException("The final state cannot be null");
		}
		if (orderedStates == null) {
			throw new NullPointerException(
					"The list of the states cannot be null");
		}
		this.orderedStates = orderedStates;
		this.finalState = finalState;
	}

	/**
	 * Given an ordering between the states (a list) and a final state and an
	 * automaton return a vector where the final state is encoded with the
	 * character "λ" while all the other states are encoded with the character
	 * "∅"
	 * 
	 * @param automaton
	 *            is the automaton to be considered
	 * @throws NullPointerException
	 *             if the automaton is null
	 * @throws IllegalArgumentException
	 *             if the list of states does not contain all the states of the
	 *             automaton and viceversa
	 * @throws IllegalArgumentException
	 *             if the final state is not contained into the automaton
	 * 
	 */
	@Override
	public String[] transform(BA<LABEL, STATE, TRANSITION> automaton) {

		if (!automaton.getStates().contains(finalState)) {
			throw new IllegalArgumentException(
					"The final state "
							+ finalState.getName()
							+ " must be contained into the set of the states of the automaton");
		}
		if (!automaton.getStates().containsAll(orderedStates)) {
			throw new IllegalArgumentException(
					"The ordered states must be contained into the set of the states of the automaton");
		}
		if (!orderedStates.containsAll(automaton.getStates())) {
			throw new IllegalArgumentException(
					"The states of the automata must be contained into the set of the ordered states");
		}
		String[] ret = new String[automaton.getStates().size()];

		int i = 0;
		// for each state in the stateOrdered vector
		for (STATE s : this.orderedStates) {

			// if the state is equal to the state accept
			if (finalState.equals(s)) {
				// I add the lambda predicate in the s[i] cell of the vector
				ret[i] = "λ";
			} else {
				// I add the empty predicate in the s[i] cell of the vector
				ret[i] = "∅";
			}
			i++;
		}
		// returns the vector s
		return ret;
	}
}
