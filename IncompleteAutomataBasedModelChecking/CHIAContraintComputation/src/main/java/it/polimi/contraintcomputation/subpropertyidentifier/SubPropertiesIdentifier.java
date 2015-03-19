package it.polimi.contraintcomputation.subpropertyidentifier;

import it.polimi.automata.BA;
import it.polimi.automata.Constants;
import it.polimi.automata.IBA;
import it.polimi.automata.IntersectionBA;
import it.polimi.automata.state.State;
import it.polimi.automata.transition.Transition;
import it.polimi.automata.transition.TransitionFactory;
import it.polimi.checker.ModelCheckingResults;
import it.polimi.checker.intersection.IntersectionBuilder;
import it.polimi.constraints.Color;
import it.polimi.constraints.impl.Constraint;
import it.polimi.constraints.impl.Port;
import it.polimi.contraintcomputation.subautomatafinder.PortSubPropertiesReachabilityChecking;
import it.polimi.contraintcomputation.subautomatafinder.intersection.PortInternalSubPropertiesReachabilityChecker;

import java.awt.Component;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import rwth.i2.ltl2ba4j.model.IGraphProposition;
import rwth.i2.ltl2ba4j.model.impl.GraphProposition;

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
public class SubPropertiesIdentifier {

	private final Set<IGraphProposition> stutteringPropositions;

	/**
	 * is the logger of the SubAutomataIdentifier class
	 */
	private static final Logger logger = LoggerFactory
			.getLogger(SubPropertiesIdentifier.class);

	/**
	 * contains a map that maps each state of the model with a set of states of
	 * the intersection automaton
	 */
	private Map<State, Set<State>> modelStateIntersectionStateMap;

	/**
	 * contains the intersection automaton
	 */
	private IntersectionBA intersectionBA;

	/**
	 * contains the map that connect each state of the model with the
	 * corresponding clusters
	 */
	private Constraint constraint;

	/**
	 * associated each state of the intersection automaton to the component
	 * through which it is associated
	 */
	private Map<State, Component> mapIntersectionStateComponent;
	private final Map<State, Component> modelStateComponent;

	/**
	 * The incoming transitions are the transitions that enters the current
	 * refinement level: they can be initial transition, i.e., transitions that
	 * arrives from the outside or transition that reaches the current level
	 * from the refinement of a transparent state
	 */
	private final Map<Transition, Port> mapIntersectionTransitionOutcomingPort;

	/**
	 * The out-coming transitions are the transition that leave the current
	 * refinement level: they can be final transition, i.e., transitions that
	 * leaves the current refinement level to an ``upper level" component or
	 * transitions that enter the transparent state
	 */
	private final Map<Transition, Port> mapIntersectionTransitionIncomingPort;

	/**
	 * is the original model to be considered
	 */
	private IBA model;

	private IntersectionBuilder intersectionBuilder;

	private final ModelCheckingResults mcResults;

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
	public SubPropertiesIdentifier(
			ModelCheckingResults mcResults) {

		Preconditions.checkNotNull(intersectionBuilder,
				"The intersection builder cannot be null");
	

		// creating the abstracted automaton
		this.constraint = new Constraint();
		// setting the map between the intersection state and the model states
		this.modelStateIntersectionStateMap = intersectionBuilder
				.getModelStateIntersectionStateMap();
		// setting the intersection automaton
		this.intersectionBA = intersectionBuilder
				.getPrecomputedIntersectionAutomaton();
		// setting the model
		this.model = intersectionBuilder.getModel();
		// creating the factory of the components
		this.componentFactory = componentFactory;
		// creating the map between a state and the corresponding component
		this.mapIntersectionStateComponent = new HashMap<S, Component<S, T, A>>();

		this.refinementTransitionFactory = intersectionBuilder
				.getIntersectionrule().getIntersectionTransitionFactory();
		this.mapIntersectionTransitionOutcomingPort = new HashMap<T, Port<S, T>>();
		this.mapIntersectionTransitionIncomingPort = new HashMap<T, Port<S, T>>();
		this.stutteringPropositions = new HashSet<IGraphProposition>();
		this.stutteringPropositions.add(new GraphProposition(
				Constants.STUTTERING_CHARACTER, false));

		logger.info("SubAutomataIdentifier created");
		this.intersectionBuilder = intersectionBuilder;
		this.modelStateComponent = new HashMap<S, Component<S, T, A>>();
		this.mcResults = mcResults;
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
	public Constraint getSubAutomata(Map<Port, Color> inPorts,
			Map<Port, Color> outPorts) {

		Preconditions.checkNotNull(inPorts, "The inport set must be not null");
		Preconditions.checkNotNull(outPorts,
				"The out-port set must be not null");

		logger.info("Computing the subproperties");

		long startSubPropertyComputationTime = System.nanoTime();
		this.createStates();
		this.createTransitions();
		long stopSubPropertyComputationTime = System.nanoTime();

		long checkingMcTime = ((stopSubPropertyComputationTime - startSubPropertyComputationTime));

		mcResults.setSubPropertyComputationTime(checkingMcTime);

		if (mcResults.isPortReachability()) {
			/*
			 * given the intersection automaton and how the specific component
			 * can be entered/ exited through the
			 * mapIntersectionTransitionIncomingPort and the
			 * mapIntersectionTransitionOutcomingPort computes the reachability
			 * relation between the different components of the THIS STEP IS
			 * PERFORMED TO COMPUTE THE REACHABILITY BETWEEN THE PORTS OF THE
			 * DIFFERENT COMPONENTS
			 */

			/*
			 * 2) COMPUTES THE REACHABILITY BETWEEN THE DIFFERENT SUBPROPERTIES
			 * OK :-)
			 */

			long startPortReachabilityTime = System.nanoTime();

			PortSubPropertiesReachabilityChecking<S, T> closure = new PortSubPropertiesReachabilityChecking<S, T>(
					this.intersectionBA,
					this.mapIntersectionTransitionOutcomingPort.values(),
					this.mapIntersectionTransitionIncomingPort.values(),
					outPorts.keySet(), inPorts.keySet());
			closure.computeTransitionsClosure(this.intersectionBA
					.getRegularStates());
			Map<Port, Set<Port>> reachebilityRelation = closure
					.getForwardReachabilityRelation();

			for (Port sourcePort : reachebilityRelation.keySet()) {
				for (Port destionationPort : reachebilityRelation
						.get(sourcePort)) {
					if (!this.constraint.getPorts().contains(sourcePort)) {

						Component component = this.mapIntersectionStateComponent
								.get(sourcePort.getDestination());
						this.constraint.addIncomingPort(component, sourcePort);
					}
					if (!this.constraint.getPorts().contains(destionationPort)) {
						Component component = this.mapIntersectionStateComponent
								.get(destionationPort.getSource());
						this.constraint.addOutComingPort(component,
								destionationPort);
					}
					this.constraint.addReachabilityRelation(sourcePort,
							destionationPort);
				}
			}
			/*
			 * 3) COMPUTES THE REACHABILITY INSIDE THE SUBPROPERTIES
			 */
			PortInternalSubPropertiesReachabilityChecker<State, Transition, IntersectionBA> intBaclosure = new PortInternalSubPropertiesReachabilityChecker<S, T, IntersectionBA<S, T>>(
					this.mapIntersectionTransitionIncomingPort.values(),
					this.mapIntersectionTransitionOutcomingPort.values(),
					this.intersectionBuilder, inPorts.keySet(),
					outPorts.keySet());
			intBaclosure
					.computeIntersectionTransitionsClosure(this.intersectionBA
							.getMixedStates());
			Map<Port, Set<Port>> reachebilityRelation2 = intBaclosure
					.getForwardReachabilityRelation();

			for (Port sourcePort : reachebilityRelation2.keySet()) {
				for (Port destionationPort : reachebilityRelation2
						.get(sourcePort)) {
					if (!this.constraint.getPorts().contains(sourcePort)) {

						Component component = this.mapIntersectionStateComponent
								.get(sourcePort.getDestination());
						this.constraint.addIncomingPort(component, sourcePort);
						this.constraint.setPortValue(sourcePort,
								inPorts.get(sourcePort));
					}
					if (!this.constraint.getPorts().contains(destionationPort)) {
						Component component = this.mapIntersectionStateComponent
								.get(destionationPort.getSource());
						this.constraint.addOutComingPort(component,
								destionationPort);
						this.constraint.setPortValue(destionationPort,
								outPorts.get(destionationPort));
					}
					this.constraint.addReachabilityRelation(sourcePort,
							destionationPort);
				}
			}
			long stopPortReachabilityTime = System.nanoTime();

			long portReachabilityTime = ((stopPortReachabilityTime - startPortReachabilityTime));
			mcResults.setPortReachabilityTime(portReachabilityTime);
		}
		


		logger.info("Subproperties ");
		return this.constraint;
	}

	/**
	 * creates the transitions inside the current refinement
	 */
	private void createTransitions() {
		Set<Transition> visitedTransition = new HashSet<Transition>();
		for (State modelState : this.intersectionBuilder.getModel()
				.getTransparentStates()) {

			Component subproperty = this.modelStateComponent
					.get(modelState);
			if (this.modelStateIntersectionStateMap.containsKey(modelState)) {

				for (State intersectionState : this.modelStateIntersectionStateMap
						.get(modelState)) {

					/*
					 * first the algorithm searches for out-coming transition.
					 * The transition that reaches an intersection state
					 * associated with a transparent state of the model are
					 * POTENTIAL out-coming transitions since they leaves the
					 * current level of refinement, i.e., they enter the
					 * refinement of the transparent state
					 */
					for (Transition incomingTransition : this.intersectionBA
							.getInTransitions(intersectionState)) {
						visitedTransition.add(incomingTransition);
						State sourceIntersectionState = this.intersectionBA
								.getTransitionSource(incomingTransition);

						if (!incomingTransition.getPropositions().equals(
								stutteringPropositions)) {
							if (this.intersectionBuilder
									.getIntersectionTransitionsTransparentStatesMap()
									.containsKey(incomingTransition)) {

								subproperty.getAutomaton().addPropositions(
										incomingTransition.getPropositions());

								Transition newTransition = this.intersectionBuilder
										.getIntersectionrule()
										.getIntersectionTransitionFactory()
										.create(incomingTransition.getId(),
												incomingTransition
														.getPropositions());
								subproperty.getAutomaton().addTransition(
										sourceIntersectionState,
										intersectionState, newTransition);
							}

							else {

								/*
								 * the destination is an intersection state
								 * since I left the current level of refinement
								 * to go to the refinement, i.e., the
								 * intersection
								 */
								Port incomingPort = new Port(
										this.intersectionBuilder
												.getIntersectionStateModelStateMap()
												.get(sourceIntersectionState),
										intersectionState, incomingTransition,
										true);

								this.mapIntersectionTransitionIncomingPort.put(
										incomingTransition, incomingPort);
								/*
								 * the port outcomingPort is out-coming for the
								 * current level of refinement but is an
								 * incoming port with respect to the
								 * intersectionStateComponent
								 */
								this.constraint.addIncomingPort(subproperty,
										incomingPort);

							}
						}
					}
					/*
					 * The transitions that exit a mixed state are potential
					 * incoming transitions since they are potential transitions
					 * that moves from the refinement of the transparent state
					 * to the current level of abstraction
					 */
					for (Transition outcomingTransition : this.intersectionBA
							.getOutTransitions(intersectionState)) {
						if (!visitedTransition.contains(outcomingTransition)) {
							visitedTransition.add(outcomingTransition);
							State destinationIntersectionState = this.intersectionBA
									.getTransitionDestination(outcomingTransition);

							if (!outcomingTransition.getPropositions().equals(
									stutteringPropositions)) {
								if (this.intersectionBuilder
										.getIntersectionTransitionsTransparentStatesMap()
										.containsKey(outcomingTransition)) {

									subproperty.getAutomaton().addPropositions(
											outcomingTransition
													.getPropositions());
									Transition newTransition = this.intersectionBuilder
											.getIntersectionrule()
											.getIntersectionTransitionFactory()
											.create(outcomingTransition.getId(),
													outcomingTransition
															.getPropositions());

									subproperty.getAutomaton().addTransition(
											intersectionState,
											destinationIntersectionState,
											newTransition);
								}

								else {

									/*
									 * the source state is an intersection state
									 * since I leaved the previous level of the
									 * refinement to go to the current one (exit
									 * the transparent)
									 */
									Port outcomingPort = new Port(
											intersectionState,
											this.intersectionBuilder
													.getIntersectionStateModelStateMap()
													.get(destinationIntersectionState),
											outcomingTransition, false);

									this.mapIntersectionTransitionOutcomingPort
											.put(outcomingTransition,
													outcomingPort);

									this.constraint.addOutComingPort(
											subproperty, outcomingPort);
								}
							}
						}
					}
				}
			}
		}
	}

	/**
	 * creates the sub-properties related with the current refinement level
	 */
	private void createStates() {
		for (State modelState : this.intersectionBuilder.getModel()
				.getTransparentStates()) {

			logger.debug("Analizing the intersection state corresponding to the model state: "
					+ modelState.getName());

			/*
			 * creates a component which correspond with the state modelState
			 */
			Component component = componentFactory.create(
					modelState.getName(), modelState,
					model.isTransparent(modelState),
					this.refinementTransitionFactory);
			this.modelStateComponent.put(modelState, component);

			this.constraint.addComponent(component);
			// adds the abstracted automaton

			if (this.model.getTransparentStates().contains(modelState)) {
				this.constraint.addComponent(component);

			}
			// if the transparent state is reachable
			if (modelStateIntersectionStateMap.get(modelState) != null) {
				for (S intersectionState : modelStateIntersectionStateMap
						.get(modelState)) {

					if (this.intersectionBA.getStates().contains(
							intersectionState)) {
						this.mapIntersectionStateComponent.put(
								intersectionState, component);

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
							// abstracted automaton
							component.getAutomaton().addAcceptState(
									intersectionState);
						}
					}
				}
			}
		}
	}
}
