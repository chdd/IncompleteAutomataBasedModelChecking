package it.polimi.contraintcomputation.subpropertyidentifier;

import it.polimi.automata.AutomataConstants;
import it.polimi.automata.BA;
import it.polimi.automata.state.State;
import it.polimi.automata.transition.ClaimTransitionFactory;
import it.polimi.automata.transition.Transition;
import it.polimi.checker.Checker;
import it.polimi.constraints.Color;
import it.polimi.constraints.ColoredPort;
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
	private final Map<Transition, ColoredPort> mapIntersectionTransitionOutcomingPort;

	/**
	 * The out-coming transitions are the transition that leave the current
	 * refinement level: they can be final transition, i.e., transitions that
	 * leaves the current refinement level to an ``upper level" component or
	 * transitions that enter the transparent state
	 */
	private final Map<Transition, ColoredPort> mapIntersectionTransitionIncomingPort;

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
	public SubPropertiesIdentifier(Checker checker, State transparentState) {

		super();
		Preconditions.checkNotNull(checker, "The checker cannot be null");
		Preconditions.checkNotNull(transparentState,
				"The transparent state to be considered cannot be null");

		Preconditions
				.checkState(
						checker.isPerformed(),
						"The checking activity must be performed before the computation of the sub-property");
		Preconditions
				.checkArgument(checker.getIntersectionBuilder().getModel()
						.getTransparentStates().contains(transparentState),
						"The state to be considered must be a transparent state of the model");

		this.transparentState = transparentState;
		this.checker = checker;

		this.subProperty = new SubProperty(transparentState, new BA(
				new ClaimTransitionFactory()));

		this.mapIntersectionTransitionOutcomingPort = new HashMap<Transition, ColoredPort>();
		this.mapIntersectionTransitionIncomingPort = new HashMap<Transition, ColoredPort>();
		this.stutteringPropositions = new HashSet<IGraphProposition>();
		this.stutteringPropositions.add(new GraphProposition(
				AutomataConstants.STUTTERING_CHARACTER, false));

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
	public SubProperty getSubProperty() {
		logger.info("Subproperty computation started");

		if (this.isPerformed()) {
			return this.subProperty;
		}

		this.addPropositions();
		this.createStates();
		this.createTransitions();

		logger.info("Subproperty computation ended");
		return this.subProperty;
	}

	private void addPropositions() {
		/*
		 * creates a component which correspond with the state modelState
		 */
		this.subProperty.getAutomaton().addPropositions(
				this.checker.getIntersectionBuilder().getClaim()
						.getPropositions());

	}

	/**
	 * creates the sub-properties related with the current refinement level
	 */
	private void createStates() {

		/*
		 * gets the intersectionState associated with the state of the model
		 * modelState
		 */
		for (State intersectionState : this.checker.getIntersectionBuilder()
				.getModelIntersectionStates(this.transparentState)) {

			this.subProperty.getAutomaton().addState(intersectionState);
			if (this.checker.getIntersectionBuilder()
					.getIntersectionAutomaton().getInitialStates()
					.contains(intersectionState)) {
				// add the component to the initial states of the
				// abstracted
				// automaton
				this.subProperty.getAutomaton().addInitialState(
						intersectionState);
			}
			if (this.checker.getIntersectionBuilder()
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
	 * creates the transitions inside the current refinement
	 */
	private void createTransitions() {

		for (State intersectionState : this.checker.getIntersectionBuilder()
				.getModelIntersectionStates(this.transparentState)) {

			/*
			 * first the algorithm searches for out-coming transition. The
			 * transition that reaches an intersection state associated with a
			 * transparent state of the model are POTENTIAL out-coming
			 * transitions since they leaves the current level of refinement,
			 * i.e., they enter the refinement of the transparent state
			 */
			for (Transition incomingTransition : this.checker
					.getIntersectionBuilder().getIntersectionAutomaton()
					.getInTransitions(intersectionState)) {
				State sourceIntersectionState = this.checker
						.getIntersectionBuilder().getIntersectionAutomaton()
						.getTransitionSource(incomingTransition);

				if (!incomingTransition.getPropositions().equals(
						stutteringPropositions)) {
					if (this.checker.getIntersectionBuilder()
							.getIntersectionTransitionsTransparentStatesMap()
							.containsKey(incomingTransition)) {

						Transition newTransition = new ClaimTransitionFactory()
								.create(incomingTransition.getId(),
										incomingTransition.getPropositions());
						this.subProperty.getAutomaton().addTransition(
								sourceIntersectionState, intersectionState,
								newTransition);
					}

					else {

						/*
						 * the destination is an intersection state since I left
						 * the current level of refinement to go to the
						 * refinement, i.e., the intersection
						 */
						ColoredPort incomingPort = new ColoredPort(this.checker
								.getIntersectionBuilder().getModelState(
										sourceIntersectionState),
								intersectionState, incomingTransition, true,
								Color.YELLOW);

						this.mapIntersectionTransitionIncomingPort.put(
								incomingTransition, incomingPort);
						/*
						 * the port outcomingPort is out-coming for the current
						 * level of refinement but is an incoming port with
						 * respect to the intersectionStateComponent
						 */
						this.subProperty.addIncomingPort(incomingPort);

					}
				}
			}
			/*
			 * The transitions that exit a mixed state are potential incoming
			 * transitions since they are potential transitions that moves from
			 * the refinement of the transparent state to the current level of
			 * abstraction
			 */
			for (Transition outcomingTransition : this.checker
					.getIntersectionBuilder().getIntersectionAutomaton()
					.getOutTransitions(intersectionState)) {
				State destinationIntersectionState = this.checker
						.getIntersectionBuilder().getIntersectionAutomaton()
						.getTransitionDestination(outcomingTransition);

				if (!outcomingTransition.getPropositions().equals(
						stutteringPropositions)) {
					if (this.checker.getIntersectionBuilder()
							.getIntersectionTransitionsTransparentStatesMap()
							.containsKey(outcomingTransition)) {

						Transition newTransition = new ClaimTransitionFactory()
								.create(outcomingTransition.getId(),
										outcomingTransition.getPropositions());

						this.subProperty.getAutomaton().addTransition(
								intersectionState,
								destinationIntersectionState, newTransition);
					}

					else {

						/*
						 * the source state is an intersection state since I
						 * leaved the previous level of the refinement to go to
						 * the current one (exit the transparent)
						 */
						ColoredPort outcomingPort = new ColoredPort(
								intersectionState, this.checker
										.getIntersectionBuilder()
										.getModelState(
												destinationIntersectionState),
								outcomingTransition, false, Color.YELLOW);

						this.mapIntersectionTransitionOutcomingPort.put(
								outcomingTransition, outcomingPort);

						this.subProperty.addOutComingPort(outcomingPort);
					}

				}
			}
		}
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
	public ColoredPort getOutPort(Transition t) {
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
	public ColoredPort getInPort(Transition t) {
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
	public Map<Transition, ColoredPort> getMapIntersectionTransitionIncomingPort() {
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
	public Map<Transition, ColoredPort> getMapIntersectionTransitionOutcomingPort() {
		Preconditions
				.checkState(
						this.isPerformed(),
						"The map of the outcoming ports can be obtained only after the sub-PropertyIdentifier has been performed");
		return Collections
				.unmodifiableMap(mapIntersectionTransitionOutcomingPort);
	}
}
