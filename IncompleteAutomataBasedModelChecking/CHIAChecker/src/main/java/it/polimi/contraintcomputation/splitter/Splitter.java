/**
 * 
 */
package it.polimi.contraintcomputation.splitter;

import it.polimi.automata.labeling.Label;
import it.polimi.automata.state.State;
import it.polimi.automata.transition.Transition;
import it.polimi.automata.transition.TransitionFactory;
import it.polimi.contraintcomputation.abstractedBA.AbstractedBA;
import it.polimi.contraintcomputation.component.Component;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * This class analyzes each component. If a component has more that one initial
 * state, for each initial state a new component is generated. 
 * 
 * @author claudiomenghi
 * 
 */
public class Splitter<L extends Label, S extends State, T extends Transition<L>> {

	
	/**
	 * contains the abstracted Buchi Automaton
	 */
	private AbstractedBA<L, S, T, Component<L, S, T>> abstractedIntersection;

	/**
	 * is the factory which is used to create transitions
	 */
	private TransitionFactory<L, T> transitionFactory;

	
	/**
	 * 
	 * @param abstractedIntersection
	 *            contains the abstracted Buchi Automaton
	 * @param intersectionStateClusterMap
	 *            maps each state of the abstracted automaton to the
	 *            corresponding cluster it belong with
	 * @param modelStateClusterMap
	 *            contains the map from the original transparent states of the
	 *            model to the corresponding clusters
	 * @param intersectionBAFactory
	 * @param transitionFactory
	 * @param stateFactory
	 * @param labelFactory
	 */
	public Splitter(
			AbstractedBA<L, S, T, Component<L, S, T>> abstractedIntersection,
			TransitionFactory<L, T> transitionFactory) {

		if (abstractedIntersection == null) {
			throw new NullPointerException(
					"The abstracted intersection cannot be null");
		}
		

		if (transitionFactory == null) {
			throw new NullPointerException(
					"The transition factory cannot be null");
		}
		this.transitionFactory = transitionFactory;
		this.abstractedIntersection=abstractedIntersection;
	}

	/**
	 * returns a version of the automaton where the states are aggregated
	 * according with the corresponding clusters.
	 * 
	 * @return a version of the automaton where the states are aggregated
	 *         according with the corresponding clusters.
	 */
	public AbstractedBA<L, S, T, Component<L, S, T>> split() {

		Set<Component<L, S, T>> states = this.abstractedIntersection
				.getStates();
		for (Component<L, S, T> s : states) {
			
			if (s.isTransparent() && s.getInitialStates().size() > 1) {
				splitState(s);
			}
		}
		return abstractedIntersection;
	}

	private void splitState(Component<L, S, T> stateToBeSplitted) {
		
		/*
		 * creates a new state for each initial state
		 */
		for (S initialState : stateToBeSplitted.getInitialStates()) {
			Component<L, S, T> copyOfTheState = stateToBeSplitted
					.duplicate();
			this.abstractedIntersection.addState(copyOfTheState);
			/*
			 * copy the out coming transitions
			 */
			for (T t : this.abstractedIntersection
					.getOutTransitions(stateToBeSplitted)) {
				T newTransition = this.transitionFactory.create(t.getLabels());
				Component<L, S, T> destinationState=this.abstractedIntersection
						.getTransitionDestination(t);
				
				this.abstractedIntersection
						.addTransition(copyOfTheState,
								destinationState,
								newTransition);
			}
			/*
			 * copying the incoming transitions to the initState
			 */
			for(T t: this.abstractedIntersection.getInTransitions(stateToBeSplitted)){
				
				Component<L, S, T> prec=this.abstractedIntersection.getTransitionSource(t);
				Set<S> outcomingStates=new HashSet<S>(prec.getOutcomingTransition().keySet());
				Set<S> sourceStates=copyOfTheState.getIncomingTransition().get(initialState).keySet();
				if(!Collections.disjoint(outcomingStates, sourceStates)){
					T newTransition = this.transitionFactory.create(t.getLabels());
					this.abstractedIntersection.addTransition(prec, copyOfTheState, newTransition);
				}
			}
			
			/*
			 * removing all the initial states with the excepting of the current
			 * initial state
			 */
			Set<S> initialStates = new HashSet<S>(
					stateToBeSplitted.getInitialStates());
			initialStates.remove(initialState);
			for (S init : initialStates) {
				copyOfTheState.removeInitialState(init);
			}
		
		}
		this.abstractedIntersection.removeState(stateToBeSplitted);
	}
}