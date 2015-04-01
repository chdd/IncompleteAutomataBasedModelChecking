package it.polimi.contraintcomputation.subpropertyidentifier;

import it.polimi.automata.AutomataConstants;
import it.polimi.automata.BA;
import it.polimi.automata.IBA;
import it.polimi.automata.IntersectionBA;
import it.polimi.automata.state.State;
import it.polimi.automata.transition.ClaimTransitionFactory;
import it.polimi.automata.transition.Transition;
import it.polimi.checker.ModelCheckingResults;
import it.polimi.checker.intersection.IntersectionBuilder;
import it.polimi.constraints.Color;
import it.polimi.constraints.Port;
import it.polimi.constraints.SubProperty;
import it.polimi.contraintcomputation.CHIAOperation;

import java.util.Collections;
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
public class SubPropertiesIdentifier extends CHIAOperation {

	private final Set<IGraphProposition> stutteringPropositions;

	/**
	 * is the logger of the SubAutomataIdentifier class
	 */
	private static final Logger logger = LoggerFactory
			.getLogger(SubPropertiesIdentifier.class);

	/**
	 * contains the intersection automaton
	 */
	private final IntersectionBA intersectionBA;

	/**
	 * contains the subPropertis identified
	 */
	private Set<SubProperty> subProperties;

	/**
	 * associates each state of the intersection automaton to the sub-property
	 * it belongs with
	 */
	private Map<State, SubProperty> mapIntersectionStateComponent;

	/**
	 * associates each state of the model to the sub-property it belongs with
	 */
	private final Map<State, SubProperty> modelModelStateSubProperty;

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
	private final IBA model;

	private final ModelCheckingResults mcResults;

	private final IntersectionBuilder intersectionBuilder;

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
	public SubPropertiesIdentifier(IntersectionBuilder intersectionBuilder,
			ModelCheckingResults mcResults) {

		super();
		Preconditions.checkNotNull(intersectionBuilder,
				"The intersection builder cannot be null");

		this.subProperties = new HashSet<SubProperty>();
		// setting the intersection automaton
		this.intersectionBA = intersectionBuilder
				.getPrecomputedIntersectionAutomaton();
		// setting the model
		this.model = intersectionBuilder.getModel();
		// creating the map between a state and the corresponding component
		this.mapIntersectionStateComponent = new HashMap<State, SubProperty>();

		this.mapIntersectionTransitionOutcomingPort = new HashMap<Transition, Port>();
		this.mapIntersectionTransitionIncomingPort = new HashMap<Transition, Port>();
		this.stutteringPropositions = new HashSet<IGraphProposition>();
		this.stutteringPropositions.add(new GraphProposition(
				AutomataConstants.STUTTERING_CHARACTER, false));

		logger.info("SubAutomataIdentifier created");
		this.modelModelStateSubProperty = new HashMap<State, SubProperty>();
		this.mcResults = mcResults;
		this.intersectionBuilder = intersectionBuilder;
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
	public Set<SubProperty> getSubProperties() {
		
		logger.info("Computing the subproperties");
		if (this.isPerformed()) {
			return Collections.unmodifiableSet(this.subProperties);
		}

		long startSubPropertyComputationTime = System.nanoTime();
		this.createStates();
		this.createTransitions();
		long stopSubPropertyComputationTime = System.nanoTime();

		long checkingMcTime = ((stopSubPropertyComputationTime - startSubPropertyComputationTime));

		mcResults.setSubPropertyComputationTime(checkingMcTime);

		logger.info("Subproperties ");
		this.setPerformed(true);
		return this.subProperties;
	}

	/**
	 * creates the transitions inside the current refinement
	 */
	private void createTransitions() {
		for (State modelState : this.model.getTransparentStates()) {

			SubProperty subproperty = this.modelModelStateSubProperty
					.get(modelState);

			for (State intersectionState : this.intersectionBuilder
					.getAssociatedStates(modelState)) {

				/*
				 * first the algorithm searches for out-coming transition. The
				 * transition that reaches an intersection state associated with
				 * a transparent state of the model are POTENTIAL out-coming
				 * transitions since they leaves the current level of
				 * refinement, i.e., they enter the refinement of the
				 * transparent state
				 */
				for (Transition incomingTransition : this.intersectionBA
						.getInTransitions(intersectionState)) {
					State sourceIntersectionState = this.intersectionBA
							.getTransitionSource(incomingTransition);

					if (!incomingTransition.getPropositions().equals(
							stutteringPropositions)) {
						if (this.intersectionBuilder
								.getIntersectionTransitionsTransparentStatesMap()
								.containsKey(incomingTransition)) {

							Transition newTransition = new ClaimTransitionFactory()
									.create(incomingTransition.getId(),
											incomingTransition
													.getPropositions());
							subproperty.getAutomaton().addTransition(
									sourceIntersectionState, intersectionState,
									newTransition);
						}

						else {

							/*
							 * the destination is an intersection state since I
							 * left the current level of refinement to go to the
							 * refinement, i.e., the intersection
							 */
							Port incomingPort = new Port(
									this.intersectionBuilder
											.getIntersectionStateModelStateMap()
											.get(sourceIntersectionState),
									intersectionState, incomingTransition,
									true, Color.BLACK);

							this.mapIntersectionTransitionIncomingPort.put(
									incomingTransition, incomingPort);
							/*
							 * the port outcomingPort is out-coming for the
							 * current level of refinement but is an incoming
							 * port with respect to the
							 * intersectionStateComponent
							 */
							subproperty.addIncomingPort(incomingPort);

						}
					}
				}
				/*
				 * The transitions that exit a mixed state are potential
				 * incoming transitions since they are potential transitions
				 * that moves from the refinement of the transparent state to
				 * the current level of abstraction
				 */
				for (Transition outcomingTransition : this.intersectionBA
						.getOutTransitions(intersectionState)) {
					State destinationIntersectionState = this.intersectionBA
							.getTransitionDestination(outcomingTransition);

					if (!outcomingTransition.getPropositions().equals(
							stutteringPropositions)) {
						if (this.intersectionBuilder
								.getIntersectionTransitionsTransparentStatesMap()
								.containsKey(outcomingTransition)) {

							Transition newTransition = new ClaimTransitionFactory()
									.create(outcomingTransition.getId(),
											outcomingTransition
													.getPropositions());

							subproperty.getAutomaton()
									.addTransition(intersectionState,
											destinationIntersectionState,
											newTransition);
						}

						else {

							/*
							 * the source state is an intersection state since I
							 * leaved the previous level of the refinement to go
							 * to the current one (exit the transparent)
							 */
							Port outcomingPort = new Port(
									intersectionState,
									this.intersectionBuilder
											.getIntersectionStateModelStateMap()
											.get(destinationIntersectionState),
									outcomingTransition, false, Color.BLACK);

							this.mapIntersectionTransitionOutcomingPort.put(
									outcomingTransition, outcomingPort);

							subproperty.addOutComingPort(outcomingPort);
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
		for (State modelState : this.model.getTransparentStates()) {

			logger.debug("Analizing the intersection state corresponding to the model state: "
					+ modelState.getName());

			/*
			 * creates a component which correspond with the state modelState
			 */
			BA ba = new BA(new ClaimTransitionFactory());
			ba.addPropositions(intersectionBuilder.getClaim().getPropositions());
			SubProperty subproperty = new SubProperty(modelState, ba,
					new HashSet<Port>(), new HashSet<Port>());

			this.modelModelStateSubProperty.put(modelState, subproperty);

			this.subProperties.add(subproperty);

			/*
			 * gets the intersectionState associated with the state of the model
			 * modelState
			 */
			for (State intersectionState : this.intersectionBuilder
					.getAssociatedStates(modelState)) {

				if (this.intersectionBA.getStates().contains(intersectionState)) {
					this.mapIntersectionStateComponent.put(intersectionState,
							subproperty);

					subproperty.getAutomaton().addState(intersectionState);
					if (this.intersectionBA.getInitialStates().contains(
							intersectionState)) {
						// add the component to the initial states of the
						// abstracted
						// automaton
						subproperty.getAutomaton().addInitialState(
								intersectionState);
					}
					if (this.intersectionBA.getAcceptStates().contains(
							intersectionState)) {
						// add the component to the accepting states of the
						// abstracted automaton
						subproperty.getAutomaton().addAcceptState(
								intersectionState);
					}
				}

			}
		}
	}

	/**
	 * @return the mapIntersectionTransitionOutcomingPort
	 */
	public Map<Transition, Port> getMapIntersectionTransitionOutcomingPort() {
		Preconditions
				.checkState(
						this.isPerformed(),
						"The map of the outcoming ports can be obtained only after the sub-PropertyIdentifier has been performed");
		return mapIntersectionTransitionOutcomingPort;
	}

	/**
	 * returns true if the intersection transition T is associated with an
	 * in-coming port
	 * 
	 * @param t
	 *            is the transition of the intersection automaton
	 * @return true if the transition of the intersection automaton is
	 *         associated with a in-coming port
	 * @throws NullPointerException
	 *             if the transition t is null
	 */
	public boolean isInTransition(Transition t) {
		Preconditions
				.checkState(this.isPerformed(),
						"You must compute the subproperties before performing this operation");
		Preconditions.checkNotNull(t,
				"The transition to be considered cannot be null");
		if (mapIntersectionTransitionIncomingPort.containsKey(t)) {
			return true;
		}
		return false;
	}

	/**
	 * returns true if the intersection transition T is associated with an
	 * out-coming port
	 * 
	 * @param t
	 *            is the transition of the intersection automaton
	 * @return true if the transition of the intersection automaton is
	 *         associated with a out-coming port
	 * @throws NullPointerException
	 *             if the transition t is null
	 */
	public boolean isOutTransition(Transition t) {
		Preconditions
				.checkState(this.isPerformed(),
						"You must compute the subproperties before performing this operation");
		Preconditions.checkNotNull(t,
				"The transition to be considered cannot be null");
		if (mapIntersectionTransitionOutcomingPort.containsKey(t)) {
			return true;
		}
		return false;
	}

	/**
	 * return the out-coming port associated with the intersection transition T
	 * 
	 * @param t
	 *            is the transition of the intersection automaton which is
	 *            associated with a port
	 * @return the out-coming port associated with the transition t
	 * @throws NullPointerException
	 *             if the transition t is null
	 * @throws IllegalArgumentException
	 *             if the transition t is not associated with an outcoming port
	 */
	public Port getOutPort(Transition t) {
		Preconditions
				.checkState(this.isPerformed(),
						"You must compute the subproperties before performing this operation");
		Preconditions.checkNotNull(t,
				"The transition to be considered cannot be null");
		Preconditions.checkArgument(this.isOutTransition(t),
				"The transition t must be associated with an outcoming port");
		return mapIntersectionTransitionOutcomingPort.get(t);
	}

	/**
	 * return the in-coming port associated with the intersection transition T
	 * 
	 * @param t
	 *            is the transition of the intersection automaton which is
	 *            associated with a port
	 * @return the in-coming port associated with the transition t
	 * @throws NullPointerException
	 *             if the transition t is null
	 * @throws IllegalArgumentException
	 *             if the transition t is not associated with a port
	 */
	public Port getInPort(Transition t) {
		Preconditions
				.checkState(this.isPerformed(),
						"You must compute the subproperties before performing this operation");
		Preconditions.checkNotNull(t,
				"The transition to be considered cannot be null");
		Preconditions.checkArgument(this.isInTransition(t),
				"The transition t must be associated with a port");
		return mapIntersectionTransitionIncomingPort.get(t);
	}

	/**
	 * @return the mapIntersectionTransitionIncomingPort
	 */
	public Map<Transition, Port> getMapIntersectionTransitionIncomingPort() {
		Preconditions
				.checkState(
						this.isPerformed(),
						"The map of the incoming ports can be obtained only after the sub-PropertyIdentifier has been performed");

		return mapIntersectionTransitionIncomingPort;
	}
	/**
	 * returns the set of the incoming ports of the sub-properties
	 * @return the set of the incoming ports of the sub-properties 
	 */
	public Set<Port> inPorts(){
		Preconditions
		.checkState(
				this.isPerformed(),
				"The map of the incoming ports can be obtained only after the sub-PropertyIdentifier has been performed");

		return Collections.unmodifiableSet(new HashSet<Port>(this.mapIntersectionTransitionIncomingPort.values()));
	}
	/**
	 * returns the set of the out-coming ports of the sub-properties
	 * @return the set of the out-coming ports of the sub-properties 
	 */
	public Set<Port> outPorts(){
		Preconditions
		.checkState(
				this.isPerformed(),
				"The map of the outcoming ports can be obtained only after the sub-PropertyIdentifier has been performed");

		return Collections.unmodifiableSet(new HashSet<Port>(this.mapIntersectionTransitionOutcomingPort.values()));
	}
}

