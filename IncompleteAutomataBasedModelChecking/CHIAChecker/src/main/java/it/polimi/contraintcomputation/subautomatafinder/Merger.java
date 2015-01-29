package it.polimi.contraintcomputation.subautomatafinder;

import it.polimi.automata.labeling.Label;
import it.polimi.automata.state.State;
import it.polimi.automata.transition.Transition;
import it.polimi.contraintcomputation.abstractedBA.AbstractedBA;
import it.polimi.contraintcomputation.component.Component;

import java.util.Map.Entry;
import java.util.Set;

public class Merger<L extends Label, S extends State, T extends Transition<L>> {

	private AbstractedBA<L, S, T, Component<L, S, T>> automata;
	private Component<L, S, T> componentA;
	private Component<L, S, T> componentB;
	private Set<Entry<Entry<S, S>, T>> transitions;

	/**
	 * creates a new merger.The merger merges the components a and b into a
	 * single component
	 * 
	 * @param automata
	 *            is the automata to be modified
	 * @param componentA
	 *            is the first component to be merged
	 * @param componentB
	 *            is the second component to be merged
	 * @param statesConnectionMap
	 *            maps the states of the componentA and B to be connected
	 * @throws NullPointerException
	 *             if the returnSubAutomata or the component a or b is null
	 * @throws IllegalArgumentException
	 *             if the componentA or the componentB is not contained into the
	 *             states of the automaton
	 */
	public Merger(AbstractedBA<L, S, T, Component<L, S, T>> automata,
			Component<L, S, T> componentA, Component<L, S, T> componentB,
			Set<Entry<Entry<S, S>, T>> transitions) {
		if (automata == null) {
			throw new NullPointerException(
					"The subautomata to be returned cannot be null");
		}
		if (componentA == null) {
			throw new NullPointerException(
					"The first component of to be merged cannot be null");
		}
		if (componentB == null) {
			throw new NullPointerException(
					"The second component of to be merged cannot be null");
		}
		
		
		this.automata = automata;
		this.componentA = componentA;
		this.componentB = componentB;
		this.transitions=transitions;
	}

	public void merge() {
		
		this.componentA.merge(componentB, transitions);
		
		for(T t: this.automata.getInTransitions(componentB)){
			Component<L, S, T> source=this.automata.getTransitionSource(t);
			this.automata.removeTransition(t);
			this.automata.addTransition(source, componentA, t);
		}
		for(T t: this.automata.getOutTransitions(componentB)){
			Component<L, S, T> destination=this.automata.getTransitionDestination(t);
			this.automata.removeTransition(t);
			this.automata.addTransition(componentA, destination, t);
		}
		this.automata.removeState(componentB);
		
	}

	
}
