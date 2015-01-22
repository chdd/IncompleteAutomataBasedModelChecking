package it.polimi.contraintcomputation.abstraction;

import it.polimi.automata.IntersectionBA;
import it.polimi.automata.labeling.Label;
import it.polimi.automata.state.State;
import it.polimi.automata.transition.Transition;

import java.util.HashSet;
import java.util.Set;

/**
 * creates a new Intersection automaton which contains the only states in the
 * set componentStates
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
public class Filter<LABEL extends Label, STATE extends State, TRANSITION extends Transition<LABEL>> {

	/**
	 * contains the filtered intersection automaton
	 */
	private IntersectionBA<LABEL, STATE, TRANSITION> newIntersection;

	/**
	 * contains the set of the states to be included in the intersection
	 * automaton
	 */
	private Set<STATE> componentStates;

	/**
	 * creates a new Filter
	 * 
	 * @param intersection
	 *            is the intersection automaton to be filtered
	 * @param componentStates
	 *            is the set of the states to be included in the intersection
	 *            automaton
	 * @throws NullPointerException
	 *             if the set of the states in the intersection automaton or the
	 *             set of the states in the componentStates is null
	 */
	public Filter(IntersectionBA<LABEL, STATE, TRANSITION> intersection,
			Set<STATE> componentStates) {
		if (intersection == null) {
			throw new NullPointerException(
					"The intersection automaton to be filtered cannot be null");
		}
		if (componentStates == null) {
			throw new NullPointerException(
					"The set of the component states cannot be null");
		}
		this.newIntersection = intersection.clone();
		this.componentStates = componentStates;

	}

	/**
	 * returns a new Intersection automaton which contains the only states in
	 * the set componentStates
	 * 
	 * @return a new Intersection automaton which contains the only states in
	 *         the set componentStates
	 */
	public IntersectionBA<LABEL, STATE, TRANSITION> filter() {
		for (STATE s : componentStates) {

			for (TRANSITION t : this.newIntersection.getInTransitions(s)) {
				if (!componentStates.contains(this.newIntersection
						.getTransitionSource(t))) {
					this.newIntersection.addInitialState(s);
				}
			}
			for (TRANSITION t : this.newIntersection.getOutTransitions(s)) {
				if (!componentStates.contains(this.newIntersection
						.getTransitionDestination(t))) {
					this.newIntersection.addAcceptState(s);
				}
			}
		}
		Set<STATE> statesToBeRemoved = new HashSet<STATE>(
				this.newIntersection.getStates());
		statesToBeRemoved.removeAll(componentStates);
		for (STATE s : statesToBeRemoved) {
			this.newIntersection.removeState(s);
		}

		return this.newIntersection;
	}

}
