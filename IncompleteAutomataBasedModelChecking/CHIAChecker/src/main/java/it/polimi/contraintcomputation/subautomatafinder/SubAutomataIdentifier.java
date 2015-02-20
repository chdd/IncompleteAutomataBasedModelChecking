package it.polimi.contraintcomputation.subautomatafinder;

import it.polimi.automata.IBA;
import it.polimi.automata.IntersectionBA;
import it.polimi.automata.state.State;
import it.polimi.automata.transition.Transition;
import it.polimi.automata.transition.TransitionFactory;
import it.polimi.contraintcomputation.abstractedBA.AbstractedBA;
import it.polimi.contraintcomputation.component.Component;
import it.polimi.contraintcomputation.component.ComponentFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class identifies the sub-automata of the automaton that refer to the
 * transparent states of M. In particular it isolates the portions of the state
 * space that refer to the different transparent states of M. <br>
 *
 * The identification is performed in two steps.<br>
 * First a DFS search over the state space is performed and the state of the
 * intersection automaton that refers to the same transparent state of the model
 * are grouped into components. If in the search a state s followed by an
 * already visited state s' is reached, it may be the case in which s and s' are
 * associated to the same original transparent state of the model in this case
 * the components related to s and s' are marked to be merge <br>
 * 
 * In the merging phase the components that are connected and refers to the same
 * transparent state are merged
 *
 * @author claudiomenghi
 * 
 */
public class SubAutomataIdentifier<S extends State, T extends Transition> {

	/**
	 * is the logger of the SubAutomataIdentifier class
	 */
	private static final Logger logger = LoggerFactory
			.getLogger(SubAutomataIdentifier.class);

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
	private IntersectionBA<S, T> intersectionBA;

	/**
	 * contains the map that connect each state of the model with the
	 * corresponding clusters
	 */
	private AbstractedBA<S, T, Component<S, T>> abstractedAutomata;

	/**
	 * contains the transitions that connect the component to be merged and the
	 * corresponding states of the automata to be connected
	 */
	private List<MergingElement<S, T>> toBeMerged;

	/**
	 * associated each state of the intersection automaton to the component
	 * through which it is associated
	 */
	private Map<S, Component<S, T>> mapStateComponent;

	/**
	 * is the original model to be considered
	 */
	private IBA<S, T> model;

	/**
	 * is the factory which is used to create components
	 */
	private ComponentFactory<S, T> componentFactory;

	/**
	 * is the factory which is used to create transitions in the refined
	 * component
	 */
	private TransitionFactory<S, T> refinementTransitionFactory;

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
	public SubAutomataIdentifier(IntersectionBA<S, T> intersectionBA,
			IBA<S, T> model, Map<S, S> intersectionStateModelStateMap,
			TransitionFactory<Component<S, T>, T> componentsTransitionFactory,
			TransitionFactory<S, T> transitionFactory) {

		if (intersectionBA == null) {
			logger.error("The intersection automaton cannot be null");
			throw new NullPointerException(
					"The intersection automaton cannot be null");
		}
		if (model == null) {
			logger.error("The model of the automaton cannot be null");
			throw new NullPointerException(
					"The model of the automaton cannot be null");
		}
		if (intersectionStateModelStateMap == null) {
			logger.error("The map between the states of the intersection automaton and the states of the model cannot be null");
			throw new NullPointerException(
					"The map between the states of the intersection automaton and the states of the model cannot be null");
		}
		if (componentsTransitionFactory == null) {
			logger.error("The factory used to create the transitions between the components cannot be null");
			throw new NullPointerException(
					"The factory used to create the transitions  between the components cannot be null");
		}
		if (transitionFactory == null) {
			logger.error("The factory used to create the transitions of the refinement of a component cannot be null");
			throw new NullPointerException(
					"The factory used to create the transitions of the refinement of a component cannot be null");
		}
		// creating the abstracted automaton
		this.abstractedAutomata = new AbstractedBA<S, T, Component<S, T>>(
				componentsTransitionFactory);
		// setting the map between the intersection state and the model states
		this.intersectionStateModelStateMap = intersectionStateModelStateMap;
		// setting the intersection automaton
		this.intersectionBA = intersectionBA;
		// creating the set of the visited state
		this.hashedStates = new HashSet<S>();
		// creating the list of the merging elements
		this.toBeMerged = new ArrayList<MergingElement<S, T>>();
		// setting the model
		this.model = model;
		// creating the factory of the components
		componentFactory = new ComponentFactory<S, T>();
		// creating the map between a state and the corresponding component
		this.mapStateComponent = new HashMap<S, Component<S, T>>();
		logger.info("SubAutomataIdentifier created");
	}

	/**
	 * returns an abstracted version of the intersection automaton, where each
	 * state is a component and represents a state of the original model and
	 * aggregates the states of the intersection automaton which refer to the
	 * same transparent state
	 * 
	 * @return the sub-automata of the automaton that refer to the transparent
	 *         states of M.
	 */
	public AbstractedBA<S, T, Component<S, T>> getSubAutomata() {

		logger.info("Abstracting the automaton");
		// copies the alphabet of the automaton
		this.copyAlphabet();
		// considering a specific transparent state
		for (S init : this.intersectionBA.getInitialStates()) {
			// gets the state of the model which correspond to the initial state
			if (!this.intersectionStateModelStateMap.containsKey(init)) {
				logger.error("The intersection state "
						+ init
						+ " is not contained into the map between the intersection states and the states of the model");
				throw new Error(
						"The intersection state "
								+ init
								+ " is not contained into the map between the intersection states and the states of the model");
			}
			S modelState = this.intersectionStateModelStateMap.get(init);
			/*
			 * creates a component with the same name of the model state and a
			 * "transparent" value which depends on the type of the state of the
			 * model
			 */
			Component<S, T> c = componentFactory.create(modelState.getName(),
					modelState, model.isTransparent(modelState));
			// adds the abstracted automaton
			this.abstractedAutomata.addState(c);
			// add the component to the initial states of the abstracted
			// automaton
			this.abstractedAutomata.addInitialState(c);
			// if the state is accepting also adds the state to the set of
			// accepting states of the abstracted automaton
			if (this.intersectionBA.getAcceptStates().contains(init)) {
				this.abstractedAutomata.addAcceptState(c);
			}
			// adds the state init to the set of the states of the component
			c.addState(init);
			// since init is initial to the whole system is also initial for the
			// component
			c.addInitialState(init);

			// starts the DFS
			firstDFS(init, c);
		}

		logger.info("Merging: " + this.toBeMerged.size() + " components");
		this.merge();
		return abstractedAutomata;
	}

	/**
	 * copies the alphabet of the intersection automaton into the automaton to
	 * be returned
	 */
	private void copyAlphabet() {
		this.abstractedAutomata
				.addCharacters(this.intersectionBA.getAlphabet());
	}

	private void merge() {

		Map<T, T> oldTransitionNewTransitionMap = new HashMap<T, T>();
		for (MergingElement<S, T> mergingElement : this.toBeMerged) {
			new Merger<S, T>(this.abstractedAutomata, mergingElement,
					refinementTransitionFactory)
					.merge(oldTransitionNewTransitionMap);
		}
	}

	/**
	 * visited the intersection automaton and aggregates the states into
	 * component
	 * 
	 * @param currState
	 *            is the current states under analysis
	 * @param currComponent
	 *            is the current component under analysis
	 * @throws NullPointerException
	 *             if the state or the component under analysis is null
	 */
	private void firstDFS(S currState, Component<S, T> currComponent) {

		if (currState == null) {
			logger.error("The state of the intersection automaton to be analyzed cannot be null");
			throw new NullPointerException(
					"The state of the intersection automaton to be analyzed cannot be null");
		}
		if (currComponent == null) {
			logger.error("The current component to be populated cannot be null");
			throw new NullPointerException(
					"The current component to be populated cannot be null");
		}
		logger.debug("Analyzing the state: " + currState.getName());

		// if the current state has been already visited the method returns
		if (this.hashedStates.contains(currState)) {
			return;
		}
		// otherwise the state is hashed
		this.hashedStates.add(currState);

		// checking the out coming transition of the current state
		for (T intersectionOutcomingTransition : this.intersectionBA
				.getOutTransitions(currState)) {
			// getting the destination of the transition
			S intersectionDestinationState = this.intersectionBA
					.getTransitionDestination(intersectionOutcomingTransition);

			// if the destination is not visited
			if (!this.hashedStates.contains(intersectionDestinationState)) {
				this.checkNotVisitedState(currState,
						intersectionDestinationState,
						intersectionOutcomingTransition, currComponent);
			}
			// the destination state has already been visited
			else {

				this.checkVisitedState(currState, intersectionDestinationState,
						intersectionOutcomingTransition, currComponent);
			}
		}
	}

	/**
	 * computes the component corresponding to an NOT already visited state
	 * 
	 * @param intersectionSourceState
	 *            is the source state of the intersection automaton
	 * @param intersectionDestinationState
	 *            is the destination state of the intersection automaton
	 * @param intersectiontransition
	 *            is the transition which connects the source and the
	 *            destination state
	 * @param sourceComponent
	 *            is the component associated with the source state
	 */
	private void checkVisitedState(S intersectionSourceState,
			S intersectionDestinationState, T intersectiontransition,
			Component<S, T> sourceComponent) {
		/*
		 * gets the model state associated with the intersection state
		 * destination state
		 */
		S modelState = this.intersectionStateModelStateMap
				.get(intersectionDestinationState);
		/*
		 * if the model state is NOT equal with the one of the current component
		 */
		if (!modelState.equals(sourceComponent.getModelState())) {
			/*
			 * connect the currentComponent which is associated with the current
			 * state with the component associated with the destination state
			 * through the transition T
			 */
			this.connectComponents(intersectionSourceState,
					intersectionDestinationState, intersectiontransition,
					sourceComponent,
					this.mapStateComponent.get(intersectionDestinationState));
		} else {

			/*
			 * If the current component contains the destination state the
			 * transition is injected into the current component
			 */
			if (sourceComponent.getStates().contains(
					intersectionDestinationState)) {
				sourceComponent.addCharacters(intersectiontransition
						.getLabels());

				sourceComponent.addTransition(intersectionSourceState,
						intersectionDestinationState, intersectiontransition);
			} else {
				/*
				 * adds the transition from the current component to the
				 * component associated with the destination state if this
				 * transition is not already present, and add the two component
				 * into the set of component to be merged, by adding the
				 * transition t, the current state and the destination state to
				 * the merging set, meaning that the component associated with
				 * the state currentState, destinationState connected by the
				 * transition t must be merged
				 */

				// gets the component associated with the destination
				// state
				Component<S, T> destinationComponent = mapStateComponent
						.get(intersectionDestinationState);

				this.updateMerging(intersectionSourceState,
						intersectionDestinationState, intersectiontransition,
						sourceComponent, destinationComponent);
				this.connectComponents(intersectionSourceState,
						intersectionDestinationState, intersectiontransition,
						sourceComponent, destinationComponent);

			}
		}
	}

	/**
	 * computes the component corresponding to an NOT already visited state
	 * 
	 * @param intersectionSourceState
	 *            is the source state of the intersection automaton
	 * @param intersectionDestinationState
	 *            is the destination state of the intersection automaton
	 * @param intersectiontransition
	 *            is the transition which connects the source and the
	 *            destination state
	 * @param sourceComponent
	 *            is the component associated with the source state
	 */
	private void checkNotVisitedState(S intersectionSourceState,
			S intersectionDestinationState, T intersectiontransition,
			Component<S, T> sourceComponent) {
		/*
		 * if the state of the model corresponding with the destination state is
		 * the SAME state of the model of the source state
		 */
		if (this.intersectionStateModelStateMap.get(
				intersectionDestinationState).equals(
				sourceComponent.getModelState())) {
			/*
			 * the destination state and the transitions are added to the
			 * component
			 */
			this.addStateToTheComponent(intersectionSourceState,
					intersectionDestinationState, intersectiontransition,
					sourceComponent);

			this.firstDFS(intersectionDestinationState, sourceComponent);
		} else {

			/*
			 * otherwise a new component is created and the intersection
			 * transition is used to connect the source component and the new
			 * component
			 */
			Component<S, T> destinationComponent = this.createComponent(
					intersectionSourceState, intersectionDestinationState,
					sourceComponent, intersectiontransition);

			this.connectComponents(intersectionSourceState,
					intersectionDestinationState, intersectiontransition,
					sourceComponent, destinationComponent);
			this.firstDFS(intersectionDestinationState, destinationComponent);
		}
	}

	/**
	 * adds the state destination state and the transition t inside the current
	 * component
	 * 
	 * @param intersectionSourceState
	 *            is the source state of the intersection automaton
	 * @param intersectionDestinationState
	 *            is the destination state of the intersection automaton
	 * @param intersectiontransition
	 *            is the transition which connects the source and the
	 *            destination state
	 * @param currComponent
	 *            is the component associated with the source state
	 */
	private void addStateToTheComponent(S intersectionSourceState,
			S intersectionDestinationState, T intersectiontransition,
			Component<S, T> currComponent) {
		// add the destination state to the state of the component
		currComponent.addState(intersectionDestinationState);
		// add the transition t to the set of transition of the
		// component
		currComponent.addCharacters(intersectiontransition.getLabels());
		currComponent.addTransition(intersectionSourceState,
				intersectionDestinationState, intersectiontransition);
		// add the component to the state associated with the
		// destination state
		mapStateComponent.put(intersectionDestinationState, currComponent);
		// continues in the exploration
		if (this.intersectionBA.getInitialStates().contains(
				intersectionDestinationState)) {
			this.abstractedAutomata.addInitialState(currComponent);
		}
		if (this.intersectionBA.getAcceptStates().contains(
				intersectionDestinationState)) {
			this.abstractedAutomata.addAcceptState(currComponent);
		}
	}

	public void closeComponent(S sourceState,  S destinationState, Component<S, T> sourceComponent,
			T outcomingTransition) {
		// adding the current state into the set of the accepting state of the
		// component since from this state it is possible to exit the component
		sourceComponent.addAcceptState(sourceState);
		/*
		 * adds the transition t to the set of out coming transition of the
		 * current state
		 */
		sourceComponent.addOutComingTransition(sourceState, outcomingTransition, destinationState);

	}

	private Component<S, T> createComponent(S currState, S destinationState,
			Component<S, T> currComponent, T t) {
		/*
		 * gets the model state associated with the destination state
		 */
		S modelState = this.intersectionStateModelStateMap
				.get(destinationState);
		/*
		 * creates a new component
		 */
		Component<S, T> destinationComponent;
		if (model.isTransparent(modelState)) {
			destinationComponent = this.componentFactory.create(
					modelState.getName(),
					this.intersectionStateModelStateMap.get(destinationState),
					model.isTransparent(modelState));
		} else {
			destinationComponent = this.componentFactory.create(
					destinationState.getName(),
					this.intersectionStateModelStateMap.get(destinationState),
					model.isTransparent(modelState));
		}
		this.abstractedAutomata.addState(destinationComponent);
		this.abstractedAutomata.addCharacters(t.getLabels());
		destinationComponent.addInitialState(destinationState);
		destinationComponent.addIncomingTransition(currState, t,
				destinationState);
		mapStateComponent.put(destinationState, destinationComponent);
		if (this.intersectionBA.getInitialStates().contains(destinationState)) {
			this.abstractedAutomata.addInitialState(destinationComponent);
		}
		if (this.intersectionBA.getAcceptStates().contains(destinationState)) {
			this.abstractedAutomata.addAcceptState(destinationComponent);
		}
		return destinationComponent;
	}

	/**
	 * connect the currentComponent which is associated with the current state
	 * with the component associated with the destination state through the
	 * transition T
	 * 
	 * @param intersectionSourceState
	 *            is the current state under analysis which is associated with
	 *            the current component
	 * @param intersectionDestinationState
	 *            is the destination state whose component must be connected
	 *            with the current component
	 * @param sourceComponent
	 *            is the current component which is the source state of the
	 *            connection
	 * @param intersectionTransition
	 *            is the connection to be used to connect the currComponent and
	 *            the component associated with the destination state
	 */
	private void connectComponents(S intersectionSourceState,
			S intersectionDestinationState, T intersectionTransition,
			Component<S, T> sourceComponent,
			Component<S, T> destinationComponent) {

		/*
		 * add the current state to the accepting state of the current component
		 * since form the current state it is possible to leave the current
		 * component and enter the destination component
		 */
		sourceComponent.addAcceptState(intersectionSourceState);

		/*
		 * adds the destination state to the initial state of the destination
		 * component since the destination state is the entry poing of the
		 * destination component
		 */
		destinationComponent.addInitialState(intersectionDestinationState);
		this.abstractedAutomata.addCharacters(intersectionTransition
				.getLabels());
		/*
		 * if a transition between the two component is not present a transition
		 * is between the two component is added
		 */
		this.abstractedAutomata.addTransition(sourceComponent,
				destinationComponent, intersectionTransition);
		sourceComponent.addOutComingTransition(intersectionSourceState,
				intersectionTransition, intersectionDestinationState);
		destinationComponent.addIncomingTransition(intersectionSourceState,
				intersectionTransition, intersectionDestinationState);
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
	 * @param intersectionSourceState
	 *            is the state that is currently analyzed
	 * @param intersectionDestinationState
	 *            is the destination state of the transition
	 * @param sourceComponent
	 *            is the current component under analysis
	 * @param intersectionTransition
	 *            is the transition that connect the current state and the
	 *            destination state
	 */
	private void updateMerging(S intersectionSourceState,
			S intersectionDestinationState, T intersectionTransition,
			Component<S, T> sourceComponent,
			Component<S, T> destinationComponent) {

		MergingElement<S, T> element = new MergingElement<S, T>(
				intersectionTransition);
		element.addMergingEntry(new MergingEntry<S, T>(intersectionSourceState,
				intersectionDestinationState, intersectionTransition));
		toBeMerged.add(element);

	}
}
