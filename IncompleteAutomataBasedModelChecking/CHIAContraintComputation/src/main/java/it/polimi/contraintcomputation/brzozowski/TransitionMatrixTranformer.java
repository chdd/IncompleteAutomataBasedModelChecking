package it.polimi.contraintcomputation.brzozowski;

import it.polimi.automata.BA;
import it.polimi.Constants;
import it.polimi.automata.state.State;
import it.polimi.automata.transition.Transition;

import java.util.ArrayList;
import java.util.List;


/**
 * Given an ordering between the states (a list) and a final state and an
 * automaton return a matrix where the cell [i][j] encodes the transition
 * between the state i and j of the link. If no transitions are present the cell
 * contains the "∅" character
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
class TransitionMatrixTranformer<S extends State, T extends Transition> {

	/**
	 * contains the array of the ordered states
	 */
	private List<S> orderedStates;

	/**
	 * creates a new {@link TransitionMatrixTranformer} with the specified order
	 * for the states
	 * 
	 * @param orderedStates
	 *            is the array which contains an order between the states
	 * @throws NullPointerException
	 *             if the {@link ArrayList} of the ordered states is null
	 */
	public TransitionMatrixTranformer(List<S> orderedStates) {
		if (orderedStates == null) {
			throw new NullPointerException(
					"The array of the ordered states cannot be null");
		}
		this.orderedStates = orderedStates;
	}

	/**
	 * Given an ordering between the states (a list) and a final state and an
	 * automaton return a matrix where the cell [i][j] encodes the transition
	 * between the state i and j of the link. If no transitions are present the
	 * cell contains the "∅" character
	 * 
	 * @param automaton
	 *            is the automaton to be encoded
	 * @throws NullPointerException
	 *             if the automaton is null
	 * @throws IllegalArgumentException
	 *             if the list of states does not contain all the states of the
	 *             automaton and vice-versa
	 */
	public String[][] transform(BA<S, T> automaton) {
		if (automaton == null) {
			throw new NullPointerException("The automaton cannot be null");
		}
		if (!automaton.getStates().containsAll(orderedStates)) {
			throw new IllegalArgumentException(
					"The ordered states must be contained into the set of the states of the automaton");
		}
		if (!orderedStates.containsAll(automaton.getStates())) {
			throw new IllegalArgumentException(
					"The states of the automata must be contained into the set of the ordered states");
		}

		String[][] ret = new String[automaton.getStates().size()][automaton
				.getStates().size()];

		for (int i = 0; i < this.orderedStates.size(); i++) {
			S s1 = this.orderedStates.get(i);
			for (int j = 0; j < this.orderedStates.size(); j++) {
				S s2 = this.orderedStates.get(j);
				boolean setted = false;
				for (T t : automaton.getOutTransitions(s1)) {
					if (automaton.getTransitionDestination(t).equals(s2)) {
						if(t.getPropositions().isEmpty()){
							ret[i][j]=Constants.LAMBDA;
						}
						else{
							ret[i][j] = t.getPropositions().toString();
						}
						setted = true;
					}
				}
				if (!setted) {
					ret[i][j] = Constants.EMPTYSET;
				}
			}
		}
		return ret;
	}

}
