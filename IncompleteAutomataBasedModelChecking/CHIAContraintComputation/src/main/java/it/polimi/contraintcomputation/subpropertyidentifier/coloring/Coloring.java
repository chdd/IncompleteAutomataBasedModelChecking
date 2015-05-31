package it.polimi.contraintcomputation.subpropertyidentifier.coloring;

import java.util.HashSet;
import java.util.Set;

import it.polimi.automata.IntersectionBA;
import it.polimi.automata.state.State;
import it.polimi.constraints.transitions.Color;
import it.polimi.contraintcomputation.subpropertyidentifier.SubPropertyIdentifier;

import com.google.common.base.Preconditions;

/**
 * contains the coloring function. The coloring function is used to compute for
 * each sub-property the corresponding automaton
 * 
 * @author Claudio1
 *
 */
public class Coloring {

	/**
	 * contains the identifier which has been used to generate the sub-property
	 */
	private SubPropertyIdentifier subPropertyIdentifier;

	/**
	 * The coloring algorithm "colors" the incoming and outgoing transitions of
	 * the sub-property associated with the sub-property identifier
	 * 
	 * @param subPropertyIdentifier
	 *            is the sub-property identifier to be considered by the
	 *            coloring algorithm
	 * @throws NullPointerException
	 *             if the subPropertyIdentifier is null
	 */
	public Coloring(SubPropertyIdentifier subPropertyIdentifier) {
		Preconditions.checkNotNull(subPropertyIdentifier,
				"The subproperty identified cannot be null");
		this.subPropertyIdentifier = subPropertyIdentifier;
	}

	/**
	 * starts the coloring function.
	 */
	public void startColoring() {

		IntersectionBA intersectionAutomaton = this.subPropertyIdentifier
				.getChecker().getIntersectionBuilder()
				.getIntersectionAutomaton();

		Set<State> initialStates = new HashSet<State>(
				intersectionAutomaton.getInitialStates());
		initialStates.removeAll(intersectionAutomaton.getMixedStates());

		ForwardColoring greenForwardColoring = new ForwardColoring(
				intersectionAutomaton,
				intersectionAutomaton.getPurelyRegularStates(), Color.GREEN,
				this.subPropertyIdentifier);
		for (State initialState : initialStates) {
			greenForwardColoring.color(initialState);
		}

		initialStates = new HashSet<State>(
				intersectionAutomaton.getInitialStates());
		initialStates.removeAll(subPropertyIdentifier.getSubProperty()
				.getAutomaton().getStates());
		Set<State> states = new HashSet<State>(
				intersectionAutomaton.getStates());
		states.removeAll(subPropertyIdentifier.getSubProperty().getAutomaton()
				.getStates());
		ForwardColoring yellowForwardColoring = new ForwardColoring(
				intersectionAutomaton, states, Color.YELLOW,
				this.subPropertyIdentifier);
		for (State initialState : initialStates) {
			yellowForwardColoring.color(initialState);
		}

		BackwardColoring backwardRedColoring = new BackwardColoring(
				intersectionAutomaton,
				intersectionAutomaton.getPurelyRegularStates(), Color.RED,
				this.subPropertyIdentifier);
		backwardRedColoring.perform();

		BackwardColoring backwardYellowColoring = new BackwardColoring(
				intersectionAutomaton, states, Color.YELLOW,
				this.subPropertyIdentifier);
		backwardYellowColoring.perform();
	}
}
