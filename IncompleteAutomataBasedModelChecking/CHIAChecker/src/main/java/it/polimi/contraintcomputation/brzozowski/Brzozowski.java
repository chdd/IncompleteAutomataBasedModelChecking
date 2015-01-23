package it.polimi.contraintcomputation.brzozowski;

import it.polimi.automata.BA;
import it.polimi.automata.labeling.Label;
import it.polimi.automata.state.State;
import it.polimi.automata.transition.Transition;

import java.util.ArrayList;
import java.util.List;

/**
 * Contains the algorithm that given an automaton, an initial and a final state
 * returns the regular expression that is recognized by the automaton with the
 * specified initial and final state using the Brzozowski algebraic method
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
public class Brzozowski<L extends Label, S extends State, T extends Transition<L>> {

	/**
	 * contains the automaton to be analyzed
	 */
	private final BA<L, S, T> automaton;

	/**
	 * contains the list of the states of the automaton
	 */
	private final List<S> orderedStates;

	/**
	 * is the initial state to be considered
	 */
	private final S initState;

	/**
	 * is the state to be considered as final
	 */
	private final S finalState;

	/**
	 * creates a new Brzozowski solver
	 * 
	 * @param automaton
	 *            is the automaton to be analyzed using the solver
	 * @param initState
	 *            is the initial state to be considered
	 * @param finalState
	 *            is the state to be considered as final
	 * @throws NullPointerException
	 *             is the automaton, the initial or the final state are null
	 * @throws IllegalArgumentException
	 *             if the initState or the finalState are not contained in the
	 *             automaton
	 */
	public Brzozowski(BA<L, S, T> automaton, S initState,
			S finalState) {
		if (automaton == null) {
			throw new NullPointerException(
					"The automaton to be analyzed cannot be null");
		}
		if (initState == null) {
			throw new NullPointerException(
					"The initialState to be considered cannot be null");
		}
		if (finalState == null) {
			throw new NullPointerException(
					"The finals state to be considered cannot be null");
		}
		if (!automaton.getStates().contains(initState)) {
			throw new NullPointerException(
					"The initial state must be contained into the states of the automaton");
		}
		if (!automaton.getStates().contains(finalState)) {
			throw new NullPointerException(
					"The final state must be contained into the set of the states of the automaton");
		}
		this.automaton = automaton;
		this.orderedStates = new ArrayList<S>(automaton.getStates());
		this.initState = initState;
		this.finalState = finalState;
	}

	/**
	 * returns a String which represents the regular expression recognized by
	 * the automaton when the initState and the finalState are interpreted as
	 * initial and final, respectively
	 * 
	 * @return the String representation of the regular expression recognized by
	 *         the automaton
	 */
	public String getRegularExpression() {

		this.orderedStates.remove(this.initState);
		this.orderedStates.add(0, initState);

		String[][] t = new TransitionMatrixTranformer<L, S, T>(
				this.orderedStates).transform(this.automaton);
		String[] constr1 = new AcceptingStatesTransformer<L, S, T>(
				orderedStates, this.finalState).transform(automaton);
		this.solveSystem(t, constr1);

		return constr1[0];
	}

	/**
	 * Given the matrixes t which encodes the transition relation of the
	 * automaton and the matrix s which encodes the set of accepting state
	 * compute solves the system of equation using the Brzozowski
	 * 
	 * @param t
	 *            is the matrix which encodes the transition relation of the
	 *            system
	 * @param s
	 *            is the matrix which encodes the final state of the system
	 */
	private void solveSystem(String[][] t, String[] s) {

		int m = automaton.getStates().size();
		for (int n = m - 1; n >= 0; n--) {
			s[n] = "(" + t[n][n] + ")*" + s[n];
			for (int j = 0; j < n; j++) {
				t[n][j] = "(" + t[n][n] + ")*" + t[n][j];
			}
			for (int i = 0; i < n; i++) {
				s[i] = "(" + s[i] + ")|(" + t[i][n] + "." + s[n] + ")";
				for (int j = 0; j < n; j++) {
					t[i][j] = "(" + t[i][j] + ")|(" + t[i][n] + "." + t[n][j]
							+ ")";
				}
			}
			for (int i = 0; i < n; i++) {
				t[i][n] = "∅";
			}
		}
	}
}
