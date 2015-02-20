package it.polimi.contraintcomputation.subautomatafinder;

import java.util.Map;

import it.polimi.automata.state.State;
import it.polimi.automata.transition.Transition;
import it.polimi.automata.transition.TransitionFactory;
import it.polimi.contraintcomputation.abstractedBA.AbstractedBA;
import it.polimi.contraintcomputation.component.Component;

public class Merger< S extends State, T extends Transition> {

	private AbstractedBA<S, T, Component<S, T>> automata;
	private MergingElement<S, T> mergingElement;
	private TransitionFactory<S, T> transitionFactory;

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
	public Merger(AbstractedBA<S, T, Component<S, T>> automata,
			MergingElement<S,T> mergingElement, TransitionFactory<S, T> transitionFactory) {
		if (automata == null) {
			throw new NullPointerException(
					"The subautomata to be returned cannot be null");
		}
		

		this.automata = automata;
		this.mergingElement=mergingElement;
		this.transitionFactory=transitionFactory;
	}

	public void merge(Map<T, T> oldNewTransitionMap) {
		T transition;
		if(oldNewTransitionMap.containsKey(mergingElement.getComponentTransition())){
			transition=oldNewTransitionMap.get(mergingElement.getComponentTransition());
		}
		else{
			transition=mergingElement.getComponentTransition();
		}
		Component<S, T> componentA = automata.getTransitionSource(transition);
		Component<S, T> componentB = automata.getTransitionDestination(transition);
		componentA.merge(componentB, mergingElement.getMergingEntries(),
				transitionFactory);
		for(T componentBIncomingTransition: automata.getInTransitions(componentB)){
			Component<S, T> source=automata.getTransitionSource(componentBIncomingTransition);
			this.automata.removeTransition(componentBIncomingTransition);
			if(!this.automata.isPredecessor(source, componentA)){
				this.automata.addTransition(source, componentA, componentBIncomingTransition);
			}
			else{
				//TODO
				//oldNewTransitionMap.put(componentBIncomingTransition, this.automata.getTransitions(source, componentA));
			}
		}
		for(T componentBOutcomingTransition: automata.getInTransitions(componentB)){
			Component<S, T> destination=automata.getTransitionSource(componentBOutcomingTransition);
			this.automata.removeTransition(componentBOutcomingTransition);
			if(!this.automata.isPredecessor(componentA, destination)){
				this.automata.addTransition(componentA, destination, componentBOutcomingTransition);
			}
			else{
				//TODO
				//oldNewTransitionMap.put(componentBOutcomingTransition, this.automata.getTransitions(componentA, destination));
			}
		}
		
		
		this.automata.removeState(componentB);
	}

}
