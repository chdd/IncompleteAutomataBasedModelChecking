package it.polimi.contraintcomputation.brzozowski;

import it.polimi.automata.BA;
import it.polimi.Constants;
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
 * @param <S>
 *            is the type of the state of the Buchi Automaton. The type of the
 *            states of the automaton must implement the interface {@link State}
 * @param <T>
 *            is the type of the transition of the Buchi Automaton. The typer of
 *            the transitions of the automaton must implement the interface
 *            {@link Transition}
 * @param <L>
 *            is the type of the label of the transitions depending on whether
 *            the automaton represents the model or the claim it is a set of
 *            proposition or a propositional logic formula {@link Label}
 */
class AcceptingStatesTransformer<L extends Label, S extends State, T extends Transition<L>>
		implements Transformer<BA<L, S, T>, String[]> {

	/**
	 * contains the state to be considered as final in the automaton
	 */
	private final S finalState;
	/**
	 * contains the list which describes an ordering between the states
	 */
	private final List<S> orderedStates;

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
	 * @throws IllegalArgumentException
	 *             if the finalState is not contained in the list of ordered
	 *             states
	 */
	public AcceptingStatesTransformer(List<S> orderedStates, S finalState) {
		if (finalState == null) {
			throw new NullPointerException("The final state cannot be null");
		}
		if (orderedStates == null) {
			throw new NullPointerException(
					"The list of the states cannot be null");
		}
		if (!orderedStates.contains(finalState)) {
			throw new IllegalArgumentException(
					"The final state must be contained into the list of ordered states");
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
	 *             automaton and vice versa
	 * @throws IllegalArgumentException
	 *             if the final state is not contained into the automaton
	 * 
	 */
	@Override
	public String[] transform(BA<L, S, T> automaton) {

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
		for (S s : this.orderedStates) {

			// if the state is equal to the state accept
			if (finalState.equals(s)) {
				// I add the lambda predicate in the s[i] cell of the vector
				ret[i] = Constants.LAMBDA;
			} else {
				// I add the empty predicate in the s[i] cell of the vector
				ret[i] = Constants.EMPTYSET;
			}
			i++;
		}
		// returns the vector s
		return ret;
	}
}
