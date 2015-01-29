package it.polimi.contraintcomputation.subautomatafinder;

import it.polimi.automata.IBA;
import it.polimi.automata.IntersectionBA;
import it.polimi.automata.labeling.Label;
import it.polimi.automata.state.State;
import it.polimi.automata.transition.Transition;
import it.polimi.contraintcomputation.abstractedBA.AbstractedBA;
import it.polimi.contraintcomputation.abstractedBA.AbstractedBAFactory;
import it.polimi.contraintcomputation.component.Component;
import it.polimi.contraintcomputation.component.ComponentFactory;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 * This class identifies the sub-automata of the automaton that refer to the
 * transparent states of M. In particular it isolates the portions of the state
 * space that refer to the different transparent states of M.
 * 
 * @author claudiomenghi
 * 
 */
public class SubAutomataIdentifier<L extends Label, S extends State, T extends Transition<L>> {

	/**
	 * contains the set of the states that has been encountered by <i>some<i>
	 * invocation of the first DFS
	 */
	private Set<S> hashedStates;

	/**
	 * contains a map that maps each state of the model with a set of states of
	 * the intersection automaton
	 */
	private Map<S, S> intersectionStateModelStateMap;

	/**
	 * contains the intersection automaton
	 */
	private IntersectionBA<L, S, T> intersectionBA;

	/**
	 * contains the map that connect each state of the model with the
	 * corresponding clusters
	 */
	private AbstractedBA<L, S, T, Component<L, S, T>> returnSubAutomata;

	/**
	 * contains the transitions that connect the component to be merged and the
	 * corresponding states of the automata to be connected
	 */
	private Map<T, Set<Entry<Entry<S, S>, T>>> toBeMerged;

	/**
	 * associated each state of the intersection automaton to the component
	 * through which it is associated
	 */
	private Map<S, Component<L, S, T>> mapStateComponent;

	private IBA<L, S, T> model;

	private ComponentFactory<L, S, T> componentFactory;

	/**
	 * creates an identifier for the sub automata of the intersection automata
	 * 
	 * @param intersectionBA
	 *            is the intersection automata to be considered
	 * @param intersectionStateModelStateMap
	 *            maps each state of the model to the corresponding states of
	 *            the intersection automaton
	 * @throws NullPointerException
	 *             if the intersection automaton or the map is null
	 */
	public SubAutomataIdentifier(IntersectionBA<L, S, T> intersectionBA,
			IBA<L, S, T> model, Map<S, S> intersectionStateModelStateMap,
			AbstractedBAFactory<L, S, T, Component<L, S, T>> factory) {

		if (intersectionBA == null) {
			throw new NullPointerException(
					"The intersection automaton cannot be null");
		}
		if (factory == null) {
			throw new NullPointerException(
					"The map of the intersection automaton cannot be null");
		}
		this.returnSubAutomata = factory.create();
		this.intersectionStateModelStateMap = intersectionStateModelStateMap;
		this.intersectionBA = intersectionBA;
		this.hashedStates = new HashSet<S>();
		this.toBeMerged = new HashMap<T, Set<Entry<Entry<S, S>, T>>>();

		this.model = model;
		componentFactory = new ComponentFactory<L, S, T>();
	}

	/**
	 * the sub-automata of the automaton that refer to the transparent states of
	 * M.
	 * 
	 * @return the sub-automata of the automaton that refer to the transparent
	 *         states of M.
	 */
	public AbstractedBA<L, S, T, Component<L, S, T>> getSubAutomata() {
		this.hashedStates = new HashSet<S>();
		this.mapStateComponent = new HashMap<S, Component<L, S, T>>();

		this.copyAlphabet();
		// considering a specific transparent state
		for (S init : this.intersectionBA.getInitialStates()) {
			Component<L, S, T> c = componentFactory.create(init.getName(),
					this.intersectionStateModelStateMap.get(init),
					model.isTransparent(init));
			this.returnSubAutomata.addState(c);
			this.returnSubAutomata.addInitialState(c);
			if (this.intersectionBA.getAcceptStates().contains(init)) {
				this.returnSubAutomata.addAcceptState(c);
			}
			c.addInitialState(init);
			firstDFS(init, c);
		}

		this.merge();
		return returnSubAutomata;
	}
	
	/**
	 * copies the alphabet of the intersection automaton into the automaton to be returned
	 */
	private void copyAlphabet(){
		this.returnSubAutomata.addCharacters(this.intersectionBA.getAlphabet());
	}

	private void merge() {

		for (T transition : this.toBeMerged.keySet()) {
			Component<L, S, T> sourceComponent = this.returnSubAutomata
					.getTransitionSource(transition);
			Component<L, S, T> destinationComponent = this.returnSubAutomata
					.getTransitionDestination(transition);

			new Merger<>(this.returnSubAutomata, sourceComponent,
					destinationComponent, this.toBeMerged.get(transition))
					.merge();
			;
		}
	}

	/**
	 * returns true if an accepting path is found
	 * 
	 * @param currState
	 *            is the current states under analysis
	 * @return true if an accepting path is found, false otherwise
	 */
	private void firstDFS(S currState, Component<L, S, T> currComponent) {

		if (this.hashedStates.contains(currState)) {
			return;
		}
		this.hashedStates.add(currState);

		// checking the out coming transition of the current state
		for (T t : this.intersectionBA.getOutTransitions(currState)) {
			// getting the destination of the transition
			S destinationState = this.intersectionBA
					.getTransitionDestination(t);

			// if the destination is not visited
			if (!this.hashedStates.contains(destinationState)) {
				/*
				 * if the state of the model corresponding with the destination
				 * state is the SAME state of the model of the current component
				 */
				if (this.intersectionStateModelStateMap.get(destinationState)
						.equals(currComponent.getModelState())) {
					/*
					 * adds the state destination state and the transition t
					 * with source state currState inside the current component
					 */
					this.addStateToTheComponent(currState, destinationState,
							currComponent, t);

					this.firstDFS(destinationState, currComponent);
				} else {

					Component<L, S, T> destinationComponent = this
							.createComponent(currState, destinationState,
									currComponent, t);
					this.firstDFS(destinationState, destinationComponent);
				}
			}
			// the destination state has already been visited
			else {

				/*
				 * gets the model state associated with the intersection state
				 * destination state
				 */
				S modelState = this.intersectionStateModelStateMap
						.get(destinationState);
				/*
				 * if the model state is NOT equal with the one of the current
				 * component
				 */
				if (!modelState.equals(currComponent.getModelState())) {
					/*
					 * connect the currentComponent which is associated with the
					 * current state with the component associated with the
					 * destination state through the transition T
					 */
					this.connectComponents(currState, destinationState,
							currComponent, t);
				} else {

					if (currComponent.getStates().contains(destinationState)) {
						currComponent.addCharacters(t.getLabels());
						currComponent.addTransition(currState, currState, t);
					} else {
						/*
						 * adds the transition from the current component to the
						 * component associated with the destination state if
						 * this transition is not already present, and add the
						 * two component into the set of component to be merged,
						 * by adding the transition t, the current state and the
						 * destination state to the merging set, meaning that
						 * the component associated with the state currentState,
						 * destinationState connected by the transition t must
						 * be merged
						 */
						this.updateMerging(currState, destinationState,
								currComponent, t);
					}
				}
			}
		}
	}

	/**
	 * adds the state destination state and the transition t inside the current
	 * component
	 * 
	 * @param currState
	 *            is the state that is currently analyzed and the source of the
	 *            transition t
	 * @param destinationState
	 *            is the destination state to be added inside the component
	 * @param currComponent
	 *            is the current component to be modified
	 * @param t
	 *            is the transition to be added inside the component
	 */
	private void addStateToTheComponent(S currState, S destinationState,
			Component<L, S, T> currComponent, T t) {
		// add the destination state to the state of the component
		currComponent.addState(destinationState);
		// add the transition t to the set of transition of the
		// component
		currComponent.addCharacters(t.getLabels());
		currComponent.addTransition(currState, destinationState, t);
		// add the component to the state associated with the
		// destination state
		mapStateComponent.put(destinationState, currComponent);
		// continues in the exploration
		if (this.intersectionBA.getInitialStates().contains(destinationState)) {
			this.returnSubAutomata.addInitialState(currComponent);
		}
		if (this.intersectionBA.getAcceptStates().contains(destinationState)) {
			this.returnSubAutomata.addAcceptState(currComponent);

		}
	}

	private Component<L, S, T> createComponent(S currState, S destinationState,
			Component<L, S, T> currComponent, T t) {
		// adding the current state into the set of the accepting state of the
		// component since from this state it is possible to exit the component
		currComponent.addAcceptState(currState);
		/*
		 * adds the transition t to the set of out coming transition of the
		 * current state
		 */
		currComponent.addOutComingTransition(currState, t, destinationState);
		/*
		 * gets the model state associated with the destination state
		 */
		S modelState = this.intersectionStateModelStateMap
				.get(destinationState);
		/*
		 * creates a new component
		 */
		Component<L, S, T> destinationComponent;
		if(model.isTransparent(modelState)){
			destinationComponent = this.componentFactory.create(
					modelState.getName(),
					this.intersectionStateModelStateMap.get(destinationState),
					model.isTransparent(modelState));
		}
		else{
			destinationComponent = this.componentFactory.create(
					destinationState.getName(),
					this.intersectionStateModelStateMap.get(destinationState),
					model.isTransparent(modelState));
		}
		this.returnSubAutomata.addState(destinationComponent);

		this.returnSubAutomata.addTransition(currComponent,
				destinationComponent, t);
		destinationComponent.addInitialState(destinationState);
		destinationComponent.addIncomingTransition(currState, t,
				destinationState);
		mapStateComponent.put(destinationState, destinationComponent);
		if (this.intersectionBA.getInitialStates().contains(destinationState)) {
			this.returnSubAutomata.addInitialState(destinationComponent);
		}
		if (this.intersectionBA.getAcceptStates().contains(destinationState)) {
			this.returnSubAutomata.addAcceptState(destinationComponent);
		}
		return destinationComponent;
	}

	/**
	 * connect the currentComponent which is associated with the current state
	 * with the component associated with the destination state through the
	 * transition T
	 * 
	 * @param currState
	 *            is the current state under analysis which is associated with
	 *            the current component
	 * @param destinationState
	 *            is the destination state whose component must be connected
	 *            with the current component
	 * @param currComponent
	 *            is the current component which is the source state of the
	 *            connection
	 * @param t
	 *            is the connection to be used to connect the currComponent and
	 *            the component associated with the destination state
	 */
	private void connectComponents(S currState, S destinationState,
			Component<L, S, T> currComponent, T t) {

		/*
		 * add the current state to the accepting state of the current component
		 * since form the current state it is possible to leave the current
		 * component and enter the destination component
		 */
		currComponent.addAcceptState(currState);
		/*
		 * gets the component associated with the destination state
		 */
		Component<L, S, T> destinationComponent = this.mapStateComponent
				.get(destinationState);
		/*
		 * adds the destination state to the initial state of the destination
		 * component since the destination state is the entry poing of the
		 * destination component
		 */
		destinationComponent.addInitialState(destinationState);
		/*
		 * if a transition between the two component is not present a transition
		 * is between the two component is added
		 */
		if (!this.returnSubAutomata.isPredecessor(currComponent,
				destinationComponent)) {
			this.returnSubAutomata.addTransition(currComponent,
					this.mapStateComponent.get(destinationState), t);
		}
		currComponent.addOutComingTransition(currState, t, destinationState);
		destinationComponent.addIncomingTransition(currState, t,
				destinationState);
	}

	/**
	 * adds the transition from the current component to the component
	 * associated with the destination state if this transition is not already
	 * present, and add the two component into the set of component to be
	 * merged, by adding the transition t, the current state and the destination
	 * state to the merging set, meaning that the component associated with the
	 * state currentState, destinationState connected by the transition t must
	 * be merged
	 * 
	 * @param currState
	 *            is the state that is currently analyzed
	 * @param destinationState
	 *            is the destination state of the transition
	 * @param currComponent
	 *            is the current component under analysis
	 * @param t
	 *            is the transition that connect the current state and the
	 *            destination state
	 */
	private void updateMerging(S currState, S destinationState,
			Component<L, S, T> currComponent, T t) {
		if (!this.returnSubAutomata.isPredecessor(currComponent,
				this.mapStateComponent.get(destinationState))) {

			this.returnSubAutomata.addTransition(currComponent,
					this.mapStateComponent.get(destinationState), t);
		}

		Component<L, S, T> destinationComponent = this.mapStateComponent
				.get(destinationState);

		T transition = this.returnSubAutomata.getGraph().findEdge(
				currComponent, destinationComponent);
		if (toBeMerged.containsKey(transition)) {
			toBeMerged.get(transition).add(
					new AbstractMap.SimpleEntry<Entry<S, S>, T>(
							new AbstractMap.SimpleEntry<S, S>(currState,
									destinationState), t));
		} else {
			Set<Entry<Entry<S, S>, T>> entrySet = new HashSet<Entry<Entry<S, S>, T>>();
			entrySet.add(new AbstractMap.SimpleEntry<Entry<S, S>, T>(
					new AbstractMap.SimpleEntry<S, S>(currState,
							destinationState), t));
			toBeMerged.put(transition, entrySet);
		}
	}
}
