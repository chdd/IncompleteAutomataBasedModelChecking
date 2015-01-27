package it.polimi.contraintcomputation;

import it.polimi.automata.IntersectionBA;
import it.polimi.automata.IntersectionBAFactory;
import it.polimi.automata.labeling.Label;
import it.polimi.automata.state.State;
import it.polimi.automata.transition.Transition;

import java.util.Set;

/**
 * creates a new Intersection automaton which contains the only states in the
 * set componentStates
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
class Filter<L extends Label, S extends State, T extends Transition<L>> {

	/**
	 * contains the filtered intersection automaton
	 */
	private IntersectionBA<L, S, T> newIntersection;

	/**
	 * contains the set of the states to be included in the intersection
	 * automaton
	 */
	private Set<S> componentStates;

	private IntersectionBA<L, S, T> intersection;

	/**
	 * creates a new Filter
	 * 
	 * @param intersection
	 *            is the intersection automaton to be filtered
	 * @param componentStates
	 *            is the set of the states to be included in the intersection
	 *            automaton
	 * @param intersectionBAFactory
	 *            is the factory to be used in creating an Intersection buchi
	 *            automaton
	 * @throws NullPointerException
	 *             if the set of the states in the intersection automaton or the
	 *             set of the states in the componentStates is null
	 */
	public Filter(IntersectionBA<L, S, T> intersection, Set<S> componentStates,
			IntersectionBAFactory<L, S, T> intersectionBAFactory) {
		if (intersection == null) {
			throw new NullPointerException(
					"The intersection automaton to be filtered cannot be null");
		}
		if (componentStates == null) {
			throw new NullPointerException(
					"The set of the component states cannot be null");
		}
		if (intersectionBAFactory == null) {
			throw new NullPointerException(
					"The intersection ba factory cannot be null");
		}
		if (!intersection.getStates().containsAll(componentStates)) {
			throw new IllegalArgumentException(
					"The states must be contained into the intersection");
		}
		this.newIntersection = intersectionBAFactory.create();
		this.componentStates = componentStates;
		this.intersection = intersection;

	}

	/**
	 * returns a new Intersection automaton which contains the only states in
	 * the set componentStates
	 * 
	 * @return a new Intersection automaton which contains the only states in
	 *         the set componentStates
	 */
	public IntersectionBA<L, S, T> filter() {
		for (S s : componentStates) {
			this.newIntersection.addState(s);

			if(this.intersection.getInitialStates().contains(s)){
				this.newIntersection.addInitialState(s);
			}
			
			if(this.intersection.getAcceptStates().contains(s)){
				this.newIntersection.addAcceptState(s);
			}
			
			for (T t : this.intersection.getInTransitions(s)) {
				if (!componentStates.contains(this.intersection
						.getTransitionSource(t))) {
					this.newIntersection.addInitialState(s);
				}
				else{
					for(L l: t.getLabels()){
						this.newIntersection.addCharacter(l);
					}
					this.newIntersection.addState(this.intersection.getTransitionSource(t));
					if(!this.newIntersection.getGraph().isNeighbor(this.intersection.getTransitionSource(t), s)){
						this.newIntersection.addTransition(this.intersection.getTransitionSource(t), s, t);
					}
				}
			}
			for (T t : this.intersection.getOutTransitions(s)) {
				if (!componentStates.contains(this.intersection
						.getTransitionDestination(t))) {
					this.newIntersection.addAcceptState(s);
				}
				else{
					for(L l: t.getLabels()){
						this.newIntersection.addCharacter(l);
					}
					this.newIntersection.addState(this.intersection.getTransitionDestination(t));
					if(!this.newIntersection.getTransitions().contains(t)){
						this.newIntersection.addTransition(s, this.intersection.getTransitionDestination(t), t);
					}
				}
			}
		}
		return this.newIntersection;
	}

}
