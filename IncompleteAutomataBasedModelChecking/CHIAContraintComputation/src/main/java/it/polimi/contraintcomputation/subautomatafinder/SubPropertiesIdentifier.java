package it.polimi.contraintcomputation.subautomatafinder;

import it.polimi.automata.IBA;
import it.polimi.automata.IntersectionBA;
import it.polimi.automata.state.State;
import it.polimi.automata.transition.Transition;
import it.polimi.automata.transition.TransitionFactory;
import it.polimi.constraints.Component;
import it.polimi.constraints.ComponentFactory;
import it.polimi.constraints.Constraint;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class identifies the sub-automata of the automaton that refer to the
 * <b>transparent</b> states of the original model. In particular it isolates
 * the portions of the state space that refer to the transparent states of the
 * model into components. <br>
 * 
 * Each component includes the refinements of the transparent states that make
 * the intersection not empty
 *
 * @author claudiomenghi
 * 
 */
public class SubPropertiesIdentifier<S extends State, T extends Transition> {

	/**
	 * is the logger of the SubAutomataIdentifier class
	 */
	private static final Logger logger = LoggerFactory
			.getLogger(SubPropertiesIdentifier.class);

	/**
	 * contains a map that maps each state of the model with a set of states of
	 * the intersection automaton
	 */
	private Map<S, Set<S>> modelStateIntersectionStateMap;

	/**
	 * contains the intersection automaton
	 */
	private IntersectionBA<S, T> intersectionBA;

	/**
	 * contains the map that connect each state of the model with the
	 * corresponding clusters
	 */
	private Constraint<S, T> constraint;

	/**
	 * associated each state of the intersection automaton to the component
	 * through which it is associated
	 */
	private Map<S, Component<S, T>> mapIntersectionStateComponent;

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
	 * @param modelStateIntersectionStateMap
	 *            maps each state of the model to the corresponding states of
	 *            the intersection automaton
	 * @throws NullPointerException
	 *             if the intersection automaton or the map is null
	 */
	public SubPropertiesIdentifier(IntersectionBA<S, T> intersectionBA,
			IBA<S, T> model, Map<S, Set<S>> modelStateIntersectionStateMap,
			TransitionFactory<S, T> refinementTransitionFactory) {

		Validate.notNull(intersectionBA, "The intersection automaton cannot be null");
		Validate.notNull(model, "The model of the automaton cannot be null");
		Validate.notNull(modelStateIntersectionStateMap, "The map between the states of the intersection automaton and the states of the model cannot be null");
		Validate.notNull(refinementTransitionFactory, "The factory used to create the transitions of the refinement of a component cannot be null");
		Validate.isTrue(model.getStates().containsAll(
				modelStateIntersectionStateMap.keySet()), "some of the states of the modelStateIntersectionStateMap is not contained into the set of the states of the model");
		

		// creating the abstracted automaton
		this.constraint = new Constraint<S, T>();
		// setting the map between the intersection state and the model states
		this.modelStateIntersectionStateMap = modelStateIntersectionStateMap;
		// setting the intersection automaton
		this.intersectionBA = intersectionBA;
		// setting the model
		this.model = model;
		// creating the factory of the components
		componentFactory = new ComponentFactory<S, T>();
		// creating the map between a state and the corresponding component
		this.mapIntersectionStateComponent = new HashMap<S, Component<S, T>>();

		this.refinementTransitionFactory = refinementTransitionFactory;
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
	public Constraint<S, T> getSubAutomata() {

		logger.info("Computing the subproperties");

		this.createStates();
		this.createTransitions();

		logger.info("Subproperties ");
		return this.constraint;
	}

	private void createTransitions() {
		for (S intersectionState : this.mapIntersectionStateComponent.keySet()) {

			Component<S, T> intersectionStateComponent = this.mapIntersectionStateComponent
					.get(intersectionState);

			// analyzing the incoming transitions
			for (T incomingTransition : this.intersectionBA
					.getInTransitions(intersectionState)) {

				S sourceIntersectionState = this.intersectionBA
						.getTransitionSource(incomingTransition);
				Component<S, T> intersectionSourceComponent = this.mapIntersectionStateComponent
						.get(sourceIntersectionState);

				if (intersectionSourceComponent
						.equals(intersectionStateComponent)) {

					T transition = this.refinementTransitionFactory
							.create(incomingTransition.getPropositions());
					intersectionSourceComponent.addCharacters(transition
							.getPropositions());
					intersectionSourceComponent.addTransition(
							intersectionState, sourceIntersectionState,
							transition);
				}
				
				else {
					T port = this.refinementTransitionFactory
							.create(incomingTransition.getPropositions());
					
					intersectionSourceComponent.addOutComingPort(
							sourceIntersectionState, port,
							intersectionStateComponent.getModelState(), model);
					
					intersectionStateComponent.addIncomingPort(
							intersectionSourceComponent.getModelState(), port,
							intersectionState, model);
				}
			}
		}
	}

	/**
	 * creates the states of the abstracted automaton
	 */
	private void createStates() {
		for (S modelState : this.modelStateIntersectionStateMap.keySet()) {

			logger.debug("Analizing the intersection state corresponding to the model state: "
					+ modelState.getName());
			/*
			 * creates a component which correspond with the state modelState
			 */
			Component<S, T> c = componentFactory.create(modelState.getName(),
					modelState, model.isTransparent(modelState),
					this.refinementTransitionFactory);
			// adds the abstracted automaton

			if(this.model.getTransparentStates().contains(modelState)){
				this.constraint.addComponent(c);

			}
		
			for (S intersectionState : modelStateIntersectionStateMap
					.get(modelState)) {

				if (this.intersectionBA.getStates().contains(intersectionState)) {
					this.mapIntersectionStateComponent
							.put(intersectionState, c);

					c.addState(intersectionState);
					if (this.intersectionBA.getInitialStates().contains(
							intersectionState)) {
						// add the component to the initial states of the
						// abstracted
						// automaton
						c.addInitialState(intersectionState);
					}
					if (this.intersectionBA.getAcceptStates().contains(
							intersectionState)) {
						// add the component to the accepting states of the
						// abstracted
						// automaton
						c.addAcceptState(intersectionState);
					}
				}
			}
		}
	}
}
