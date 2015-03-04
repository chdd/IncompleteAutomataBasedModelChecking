package it.polimi.contraintcomputation.subautomatafinder;

import it.polimi.automata.BA;
import it.polimi.automata.IBA;
import it.polimi.automata.IntersectionBA;
import it.polimi.automata.state.State;
import it.polimi.automata.transition.IntersectionTransition;
import it.polimi.automata.transition.IntersectionTransitionFactory;
import it.polimi.automata.transition.Transition;
import it.polimi.constraints.Component;
import it.polimi.constraints.Constraint;
import it.polimi.constraints.Port;
import it.polimi.constraints.impl.ComponentFactory;
import it.polimi.constraints.impl.ConstraintImpl;
import it.polimi.constraints.impl.PortImpl;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
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
public class SubPropertiesIdentifier<S extends State, T extends Transition, I extends IntersectionTransition<S>, A extends BA<S, I>> {

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
	private ConstraintImpl<S, I, A> constraint;

	/**
	 * associated each state of the intersection automaton to the component
	 * through which it is associated
	 */
	private Map<S, Component<S, I, A>> mapIntersectionStateComponent;

	private Map<I, Port<S, I>> mapIntersectionTransitionIncomingPort;
	private Map<I, Port<S, I>> mapIntersectionTransitionOutcomingPort;

	/**
	 * is the original model to be considered
	 */
	private IBA<S, T> model;

	/**
	 * is the factory which is used to create components
	 */
	private ComponentFactory<S, I, A> componentFactory;

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
			IntersectionTransitionFactory<S, I> refinementTransitionFactory,
			ComponentFactory<S, I, A> componentFactory) {

		Preconditions.checkNotNull(intersectionBA,
				"The intersection automaton cannot be null");
		Preconditions.checkNotNull(model,
				"The model of the automaton cannot be null");
		Preconditions
				.checkNotNull(
						modelStateIntersectionStateMap,
						"The map between the states of the intersection automaton and the states of the model cannot be null");
		Preconditions
				.checkNotNull(
						refinementTransitionFactory,
						"The factory used to create the transitions of the refinement of a component cannot be null");
		Preconditions
				.checkArgument(
						model.getStates().containsAll(
								modelStateIntersectionStateMap.keySet()),
						"some of the states of the modelStateIntersectionStateMap is not contained into the set of the states of the model");

		// creating the abstracted automaton
		this.constraint = new ConstraintImpl<S, I, A>();
		// setting the map between the intersection state and the model states
		this.modelStateIntersectionStateMap = modelStateIntersectionStateMap;
		// setting the intersection automaton
		this.intersectionBA = intersectionBA;
		// setting the model
		this.model = model;
		// creating the factory of the components
		this.componentFactory = componentFactory;
		// creating the map between a state and the corresponding component
		this.mapIntersectionStateComponent = new HashMap<S, Component<S, I, A>>();

		this.mapIntersectionTransitionIncomingPort = new HashMap<I, Port<S, I>>();
		this.mapIntersectionTransitionOutcomingPort = new HashMap<I, Port<S, I>>();
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
	public Constraint<S, I, A> getSubAutomata() {

		logger.info("Computing the subproperties");

		this.createStates();
		this.createTransitions();

		IntersectionBA<S, I> intersectionCopy=(IntersectionBA<S, I>) this.intersectionBA.clone();
		
		/*
		 * removes the transitions assocaited with the transparent state 
		 */
		intersectionCopy=new TransparentStateRelatedTransitionsRemover<S,I>().removeTransparentStatesRelatedTransitions(intersectionCopy);
		TransitionsTransitiveClosure<S, I, A> closure = new TransitionsTransitiveClosure<S, I, A>(
				intersectionCopy, constraint,
				mapIntersectionTransitionIncomingPort,
				mapIntersectionTransitionOutcomingPort);
		closure.computeTransitionsClosure();
		
		for(Component<S,I, A> c: constraint.getComponents()){
			Map<S,Set<S>> reachabilityMap=new ReachabilityChecker<S, I, A>(c.getAutomaton()).check();
			
			for(Port<S, I> incomingPort: this.constraint.getIncomingPorts(c)){
				for(Port<S, I> outComintPort: this.constraint.getOutcomingPorts(c)){
					if(reachabilityMap.get(incomingPort.getDestination()).contains(outComintPort.getSource())){
						this.constraint.addReachabilityRelation(incomingPort, outComintPort);
					}
				}
			}
		}

		/*
		 * removes the components associated to the states that are not transparent
		 */ 
		this.removeNotTransparentComponents();

		logger.info("Subproperties ");
		return this.constraint;
	}
	
	private void removeNotTransparentComponents(){
		
		Set<Component<S, I, A>> components=new HashSet<Component<S,I,A>>(this.constraint.getComponents());
		for(Component<S, I, A> s: components){
			
			if(!this.model.isTransparent(s.getModelState())){
				this.constraint.getComponents().remove(s);
			}
		}
	}

	private void createTransitions() {
		System.out.println(this.mapIntersectionStateComponent.keySet());
		for (S intersectionState : this.mapIntersectionStateComponent.keySet()) {

			Component<S, I, A> intersectionStateComponent = this.mapIntersectionStateComponent
					.get(intersectionState);

			// analyzing the incoming transitions
			for (I incomingTransition : this.intersectionBA
					.getInTransitions(intersectionState)) {

				S sourceIntersectionState = this.intersectionBA
						.getTransitionSource(incomingTransition);
				Component<S, I, A> intersectionSourceComponent = this.mapIntersectionStateComponent
						.get(sourceIntersectionState);

				if (incomingTransition.getTransparentState() != null) {

					I transition = this.refinementTransitionFactory.create(
							incomingTransition.getId(),
							incomingTransition.getPropositions());
					intersectionSourceComponent.getAutomaton().addCharacters(
							transition.getPropositions());
					intersectionSourceComponent.getAutomaton().addTransition(
							sourceIntersectionState, intersectionState,
							transition);
				}

				else {

					I transition = this.refinementTransitionFactory.create(
							incomingTransition.getId(),
							incomingTransition.getPropositions());
					Port<S, I> incomingPort = new PortImpl<S, I>(
							intersectionSourceComponent.getModelState(),
							intersectionState, transition, true);

					this.mapIntersectionTransitionIncomingPort.put(
							incomingTransition, incomingPort);
					this.constraint.addIncomingPort(intersectionStateComponent,
							incomingPort);

					Port<S, I> outComingPort = new PortImpl<S, I>(
							sourceIntersectionState,
							intersectionStateComponent.getModelState(),
							transition, false);

					this.mapIntersectionTransitionOutcomingPort.put(
							incomingTransition, outComingPort);

					this.constraint.addOutComingPort(
							intersectionSourceComponent, outComingPort);
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
			Component<S, I, A> component = componentFactory.create(
					modelState.getName(), modelState,
					model.isTransparent(modelState),
					this.refinementTransitionFactory);

			this.constraint.addComponent(component);
			// adds the abstracted automaton

			if (this.model.getTransparentStates().contains(modelState)) {
				this.constraint.addComponent(component);

			}

			for (S intersectionState : modelStateIntersectionStateMap
					.get(modelState)) {

				if (this.intersectionBA.getStates().contains(intersectionState)) {
					this.mapIntersectionStateComponent.put(intersectionState,
							component);

					component.getAutomaton().addState(intersectionState);
					if (this.intersectionBA.getInitialStates().contains(
							intersectionState)) {
						// add the component to the initial states of the
						// abstracted
						// automaton
						component.getAutomaton().addInitialState(
								intersectionState);
					}
					if (this.intersectionBA.getAcceptStates().contains(
							intersectionState)) {
						// add the component to the accepting states of the
						// abstracted
						// automaton
						component.getAutomaton().addAcceptState(
								intersectionState);
					}
				}
			}
		}
	}

}
