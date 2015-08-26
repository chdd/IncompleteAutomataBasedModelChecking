package it.polimi.contraintcomputation.subpropertyidentifier;

import it.polimi.automata.AutomataConstants;
import it.polimi.automata.BA;
import it.polimi.automata.state.State;
import it.polimi.automata.transition.ClaimTransitionFactory;
import it.polimi.automata.transition.Transition;
import it.polimi.checker.Checker;
import it.polimi.constraints.components.SubProperty;
import it.polimi.constraints.transitions.Color;
import it.polimi.constraints.transitions.ColoredPluggingTransition;
import it.polimi.contraintcomputation.CHIAOperation;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

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
public class SubPropertyIdentifier extends CHIAOperation {

	private final Set<IGraphProposition> stutteringPropositions;

	

	/**
	 * contains the subProperty that refers to the transparent state
	 */
	private final SubProperty subProperty;

	/**
	 * is the checker which has been used to check the model against the
	 * corresponding claim
	 */
	private final Checker checker;

	/**
	 * the transparent state that is considered
	 */
	private final State transparentState;

	/**
	 * The incoming transitions are the transitions that enters the current
	 * refinement level: they can be initial transition, i.e., transitions that
	 * arrives from the outside or transition that reaches the current level
	 * from the refinement of a transparent state
	 */
	private final Map<Transition, ColoredPluggingTransition> mapIntersectionTransitionOutcomingPort;

	/**
	 * The out-coming transitions are the transition that leave the current
	 * refinement level: they can be final transition, i.e., transitions that
	 * leaves the current refinement level to an ``upper level" component or
	 * transitions that enter the transparent state
	 */
	private final Map<Transition, ColoredPluggingTransition> mapIntersectionTransitionIncomingPort;

	/**
	 * creates an identifier that is used to isolate the sub-property that
	 * refers to the transparentState
	 * 
	 * @param checker
	 *            is the checker that has been used to check the model and the
	 *            claim
	 * @param transparentState
	 *            is the transparent state of interest
	 * @throws NullPointerException
	 *             if the checker is null or the transparentState is null
	 * @throws IllegalArgumentException
	 *             the transparentState must be a transparent state of the model
	 * @throws IllegalStateException
	 *             the checking activity must be performed before the
	 *             sub-property identification
	 * 
	 */
	public SubPropertyIdentifier(Checker checker, State transparentState) {

		super();
		Preconditions.checkNotNull(checker, "The checker cannot be null");
		Preconditions.checkNotNull(transparentState,
				"The transparent state to be considered cannot be null");

		Preconditions
				.checkState(
						checker.isPerformed(),
						"The checking activity must be performed before the computation of the sub-property");
		Preconditions
				.checkArgument(checker.getUpperIntersectionBuilder().getModel()
						.getTransparentStates().contains(transparentState),
						"The state to be considered must be a transparent state of the model");

		this.transparentState = transparentState;
		this.checker = checker;

		this.subProperty = new SubProperty(transparentState, new BA(
				new ClaimTransitionFactory()));

		this.mapIntersectionTransitionOutcomingPort = new HashMap<Transition, ColoredPluggingTransition>();
		this.mapIntersectionTransitionIncomingPort = new HashMap<Transition, ColoredPluggingTransition>();
		this.stutteringPropositions = new HashSet<IGraphProposition>();
		this.stutteringPropositions.add(new GraphProposition(
				AutomataConstants.STUTTERING_CHARACTER, false));

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
	public SubProperty getSubProperty() {
		
		if (this.isPerformed()) {
			return this.subProperty;
		}

		this.addPropositions();
		this.createStates();
		this.createTransitions();

		this.setPerformed(true);
		return this.subProperty;
	}

	/**
	 * adds the propositions associated to the claim to the set of propositions
	 * of the automaton associated with the sub-property
	 */
	private void addPropositions() {
		/*
		 * creates a component which correspond with the state modelState
		 */
		this.subProperty.getAutomaton().addPropositions(
				this.checker.getUpperIntersectionBuilder().getClaim()
						.getPropositions());

	}

	/**
	 * creates the states of the automaton associated with the sub-property
	 */
	private void createStates() {

		/*
		 * gets the intersectionState associated with the state of the model
		 * modelState
		 */
		for (State intersectionState : this.checker.getUpperIntersectionBuilder()
				.getModelIntersectionStates(this.transparentState)) {
			this.subProperty.getAutomaton().addState(intersectionState);
			if (this.checker.getUpperIntersectionBuilder()
					.getIntersectionAutomaton().getInitialStates()
					.contains(intersectionState)) {
				// add the component to the initial states of the
				// abstracted
				// automaton
				this.subProperty.getAutomaton().addInitialState(
						intersectionState);
			}
			if (this.checker.getUpperIntersectionBuilder()
					.getIntersectionAutomaton().getAcceptStates()
					.contains(intersectionState)) {
				// add the component to the accepting states of the
				// abstracted automaton
				this.subProperty.getAutomaton().addAcceptState(
						intersectionState);
			}
		}
	}

	/**
	 * creates the transitions to be inserted into the automaton that refines
	 * the sub-property. These transitions include all the transitions that are
	 * associated to a transparent state of the model
	 */
	private void createInternalTransitions() {
		for (Transition internalTransition : this.checker
				.getUpperIntersectionBuilder().getConstrainedTransitions(
						this.transparentState)) {
			if (!internalTransition.getPropositions().equals(
					stutteringPropositions)) {

				Transition newTransition = new ClaimTransitionFactory().create(
						internalTransition.getId(),
						internalTransition.getPropositions());
				State sourceState = this.checker.getUpperIntersectionBuilder()
						.getIntersectionAutomaton()
						.getTransitionSource(internalTransition);
				State destinationState = this.checker.getUpperIntersectionBuilder()
						.getIntersectionAutomaton()
						.getTransitionDestination(internalTransition);
				this.subProperty.getAutomaton().addTransition(sourceState,
						destinationState, newTransition);
			}
		}
	}

	/**
	 * creates the incoming transitions associated with the sub-property
	 */
	private void createIncomingTransitions() {
		for (State intersectionState : this.checker.getUpperIntersectionBuilder()
				.getModelIntersectionStates(this.transparentState)) {

			/*
			 * first the algorithm searches for out-coming transition. The
			 * transition that reaches an intersection state associated with a
			 * transparent state of the model are POTENTIAL out-coming
			 * transitions since they leaves the current level of refinement,
			 * i.e., they enter the refinement of the transparent state
			 */
			for (Transition incomingTransition : this.checker
					.getUpperIntersectionBuilder().getIntersectionAutomaton()
					.getInTransitions(intersectionState)) {
				
				if(!this.checker.getUpperIntersectionBuilder().getConstrainedTransitions(this.transparentState).contains(incomingTransition)){
					State sourceIntersectionState = this.checker
							.getUpperIntersectionBuilder().getIntersectionAutomaton()
							.getTransitionSource(incomingTransition);

					if (!incomingTransition.getPropositions().equals(
							stutteringPropositions)) {

						/*
						 * the destination is an intersection state since I left the
						 * current level of refinement to go to the refinement,
						 * i.e., the intersection
						 */
						ColoredPluggingTransition incomingPort = new ColoredPluggingTransition(
								this.checker.getUpperIntersectionBuilder()
										.getModelState(sourceIntersectionState),
								intersectionState, incomingTransition, true,
								Color.BLACK);
						
						if(!this.subProperty.getIncomingTransitions().contains(incomingPort)){
							this.mapIntersectionTransitionIncomingPort.put(
									incomingTransition, incomingPort);
							/*
							 * the port outcomingPort is out-coming for the current
							 * level of refinement but is an incoming port with respect
							 * to the intersectionStateComponent
							 */
							this.subProperty.addIncomingTransition(incomingPort);
						}
						else{
							this.mapIntersectionTransitionIncomingPort.put(
									incomingTransition, this.subProperty.getIncomingTransition(incomingPort));
						}
						

					}
				}
			}
		}
	}

	/**
	 * creates the outgoing transition associated with the sub-property
	 */
	private void createOutgoingTransitions() {

		for (State intersectionState : this.checker.getUpperIntersectionBuilder()
				.getModelIntersectionStates(this.transparentState)) {
			/*
			 * The transitions that exit a mixed state are potential incoming
			 * transitions since they are potential transitions that moves from
			 * the refinement of the transparent state to the current level of
			 * abstraction
			 */
			for (Transition outcomingTransition : this.checker
					.getUpperIntersectionBuilder().getIntersectionAutomaton()
					.getOutTransitions(intersectionState)) {
				if(!this.checker.getUpperIntersectionBuilder().getIntersectionAutomaton().getConstrainedTransitions().contains(outcomingTransition)){
					State destinationIntersectionState = this.checker
							.getUpperIntersectionBuilder().getIntersectionAutomaton()
							.getTransitionDestination(outcomingTransition);

					if (!outcomingTransition.getPropositions().equals(
							stutteringPropositions)) {

						/*
						 * the source state is an intersection state since I leaved
						 * the previous level of the refinement to go to the current
						 * one (exit the transparent)
						 */
						ColoredPluggingTransition outcomingPort = new ColoredPluggingTransition(
								intersectionState, this.checker
										.getUpperIntersectionBuilder().getModelState(
												destinationIntersectionState),
								outcomingTransition, false, Color.BLACK);

						if(!this.subProperty.getOutgoingTransitions().contains(outcomingPort)){
							this.mapIntersectionTransitionOutcomingPort.put(
									outcomingTransition, outcomingPort);

							this.subProperty.addOutgoingTransition(outcomingPort);
						}
						else{
							this.mapIntersectionTransitionOutcomingPort.put(
									outcomingTransition, this.subProperty.getOutgoingTransition(outcomingPort));
						}
					}
				}
			}
		}
	}

	/**
	 * creates the transitions inside the current refinement
	 */
	private void createTransitions() {
		this.createInternalTransitions();
		this.createIncomingTransitions();
		this.createOutgoingTransitions();
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
	public ColoredPluggingTransition getOutgoingPort(Transition t) {
		Preconditions
				.checkState(this.isPerformed(),
						"You must compute the subproperties before performing this operation");
		Preconditions.checkNotNull(t,
				"The transition to be considered cannot be null");
		Preconditions.checkArgument(this.isOutTransition(t),
				"The transition "+t+" must be associated with an outgoing transition");
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
	public ColoredPluggingTransition getIncomingTransition(Transition t) {
		Preconditions
				.checkState(this.isPerformed(),
						"You must compute the subproperties before performing this operation");
		Preconditions.checkNotNull(t,
				"The transition to be considered cannot be null");
		Preconditions.checkArgument(this.isInTransition(t),
				"The transition "+t+" must be associated with a port");
		return mapIntersectionTransitionIncomingPort.get(t);
	}

	/**
	 * @return the mapIntersectionTransitionIncomingPort
	 */
	public Map<Transition, ColoredPluggingTransition> getMapIntersectionTransitionIncomingPort() {
		Preconditions
				.checkState(
						this.isPerformed(),
						"The map of the incoming ports can be obtained only after the sub-PropertyIdentifier has been performed");

		return Collections
				.unmodifiableMap(mapIntersectionTransitionIncomingPort);
	}

	/**
	 * @return the mapIntersectionTransitionOutcomingPort
	 */
	public Map<Transition, ColoredPluggingTransition> getMapIntersectionTransitionOutgoingPorts() {
		Preconditions
				.checkState(
						this.isPerformed(),
						"The map of the outcoming ports can be obtained only after the sub-PropertyIdentifier has been performed");
		return Collections
				.unmodifiableMap(mapIntersectionTransitionOutcomingPort);
	}

	/**
	 * returns the checker which is associated with the specific sub-property
	 * identifier
	 * 
	 * @return the checker associated with the sub-property identifier
	 */
	public Checker getChecker() {
		return this.checker;
	}
}
