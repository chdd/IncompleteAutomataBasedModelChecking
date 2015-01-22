package it.polimi.contraintcomputation.brzozowski.transformers;

import it.polimi.automata.BA;
import it.polimi.automata.labeling.Label;
import it.polimi.automata.state.State;
import it.polimi.automata.transition.Transition;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections15.Transformer;

/**
 * Given an ordering between the states (a list) and a final state and an
 * automaton return a matrix where the cell [i][j] encodes the transition
 * between the state i and j of the link. If no transitions are present the cell
 * contains the "∅" character
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
public class TransitionMatrixTranformer<LABEL extends Label, STATE extends State, TRANSITION extends Transition<LABEL>>
		implements Transformer<BA<LABEL, STATE, TRANSITION>, String[][]> {

	/**
	 * contains the array of the ordered states
	 */
	private List<STATE> orderedStates;

	/**
	 * creates a new {@link TransitionMatrixTranformer} with the specified order
	 * for the states
	 * 
	 * @param orderedStates
	 *            is the array which contains an order between the states
	 * @throws NullPointerException
	 *             if the {@link ArrayList} of the ordered states is null
	 */
	public TransitionMatrixTranformer(List<STATE> orderedStates) {
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
	@Override
	public String[][] transform(BA<LABEL, STATE, TRANSITION> automaton) {
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
			STATE s1 = this.orderedStates.get(i);
			for (int j = 0; j < this.orderedStates.size(); j++) {
				STATE s2 = this.orderedStates.get(j);
				boolean setted = false;
				for (TRANSITION t : automaton.getGraph().getOutEdges(s1)) {
					if (automaton.getGraph().getDest(t).equals(s2)) {

						ret[i][j] = t.toString();
						setted = true;
					}
				}
				if (!setted) {
					ret[i][j] = "∅";
				}
			}
		}
		return ret;
	}

}
