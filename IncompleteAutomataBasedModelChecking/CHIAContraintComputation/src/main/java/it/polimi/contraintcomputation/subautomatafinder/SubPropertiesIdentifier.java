package it.polimi.contraintcomputation.subautomatafinder;

import it.polimi.automata.IBA;
import it.polimi.automata.IntersectionBA;
import it.polimi.automata.state.State;
import it.polimi.automata.transition.IntersectionTransition;
import it.polimi.automata.transition.IntersectionTransitionFactory;
import it.polimi.automata.transition.Transition;
import it.polimi.constraints.impl.ComponentFactory;
import it.polimi.constraints.impl.ComponentImpl;
import it.polimi.constraints.impl.ConstraintImpl;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Preconditions;

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
public class SubPropertiesIdentifier<S extends State, T extends Transition, I extends IntersectionTransition<S>> {

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
	private IntersectionBA<S, I> intersectionBA;

	/**
	 * contains the map that connect each state of the model with the
	 * corresponding clusters
	 */
	private ConstraintImpl<S, I> constraint;

	/**
	 * associated each state of the intersection automaton to the component
	 * through which it is associated
	 */
	private Map<S, ComponentImpl<S, I>> mapIntersectionStateComponent;

	/**
	 * is the original model to be considered
	 */
	private IBA<S, T> model;

	/**
	 * is the factory which is used to create components
	 */
	private ComponentFactory<S, I> componentFactory;

	/**
	 * is the factory which is used to create transitions in the refined
	 * component
	 */
	private IntersectionTransitionFactory<S, I> refinementTransitionFactory;

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
	public SubPropertiesIdentifier(IntersectionBA<S, I> intersectionBA,
			IBA<S, T> model, Map<S, Set<S>> modelStateIntersectionStateMap,
			IntersectionTransitionFactory<S, I> refinementTransitionFactory) {

		Preconditions.checkNotNull(intersectionBA, "The intersection automaton cannot be null");
		Preconditions.checkNotNull(model, "The model of the automaton cannot be null");
		Preconditions.checkNotNull(modelStateIntersectionStateMap, "The map between the states of the intersection automaton and the states of the model cannot be null");
		Preconditions.checkNotNull(refinementTransitionFactory, "The factory used to create the transitions of the refinement of a component cannot be null");
		Preconditions.checkArgument(model.getStates().containsAll(
				modelStateIntersectionStateMap.keySet()), "some of the states of the modelStateIntersectionStateMap is not contained into the set of the states of the model");
		

		// creating the abstracted automaton
		this.constraint = new ConstraintImpl<S, I>();
		// setting the map between the intersection state and the model states
		this.modelStateIntersectionStateMap = modelStateIntersectionStateMap;
		// setting the intersection automaton
		this.intersectionBA = intersectionBA;
		// setting the model
		this.model = model;
		// creating the factory of the components
		componentFactory = new ComponentFactory<S, I>();
		// creating the map between a state and the corresponding component
		this.mapIntersectionStateComponent = new HashMap<S, ComponentImpl<S, I>>();

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
	public ConstraintImpl<S, I> getSubAutomata() {

		logger.info("Computing the subproperties");

		this.createStates();
		this.createTransitions();

		logger.info("Subproperties ");
		return this.constraint;
	}

	private void createTransitions() {
		for (S intersectionState : this.mapIntersectionStateComponent.keySet()) {

			ComponentImpl<S, I> intersectionStateComponent = this.mapIntersectionStateComponent
					.get(intersectionState);

			// analyzing the incoming transitions
			for (I incomingTransition : this.intersectionBA
					.getInTransitions(intersectionState)) {

				S sourceIntersectionState = this.intersectionBA
						.getTransitionSource(incomingTransition);
				ComponentImpl<S, I> intersectionSourceComponent = this.mapIntersectionStateComponent
						.get(sourceIntersectionState);

				if (incomingTransition.getTransparentState()!=null) {

					I transition = this.refinementTransitionFactory
							.create(incomingTransition.getId(), incomingTransition.getPropositions());
					intersectionSourceComponent.addCharacters(transition
							.getPropositions());
					intersectionSourceComponent.addTransition(
							intersectionState, sourceIntersectionState,
							transition);
				}
				
				else {
					I port = this.refinementTransitionFactory
							.create(incomingTransition.getId(), incomingTransition.getPropositions());
					
					intersectionSourceComponent.addOutComingPort(
							sourceIntersectionState, port,
							intersectionStateComponent.getModelState());
					
					intersectionStateComponent.addIncomingPort(
							intersectionSourceComponent.getModelState(), port,
							intersectionState);
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
			ComponentImpl<S, I> c = componentFactory.create(modelState.getName(),
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
