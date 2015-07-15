package it.polimi.replacementchecker;

import it.polimi.automata.IntersectionBA;
import it.polimi.automata.state.IntersectionStateFactory;
import it.polimi.automata.state.State;
import it.polimi.automata.state.StateFactory;
import it.polimi.automata.transition.ClaimTransitionFactory;
import it.polimi.automata.transition.ModelTransitionFactory;
import it.polimi.automata.transition.Transition;
import it.polimi.checker.intersection.IntersectionTransitionBuilder;
import it.polimi.checker.intersection.acceptingpolicies.KripkeAcceptingPolicy;
import it.polimi.constraints.components.Replacement;
import it.polimi.constraints.components.SubProperty;
import it.polimi.constraints.reachability.ReachabilityEntry;
import it.polimi.constraints.reachability.ReachabilityRelation;
import it.polimi.constraints.transitions.Color;
import it.polimi.constraints.transitions.ColoredPluggingTransition;
import it.polimi.constraints.transitions.PluggingTransition;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.tuple.ImmutableTriple;
import org.apache.commons.lang3.tuple.Triple;

import rwth.i2.ltl2ba4j.model.IGraphProposition;
import action.CHIAAction;

import com.google.common.base.Preconditions;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimaps;
import com.google.common.collect.SetMultimap;

/**
 * Computes the intersection between an Incomplete Buchi automaton and a Buchi
 * Automaton
 * 
 * @author claudiomenghi
 */
public class ReplacementIntersectionBuilder extends CHIAAction<IntersectionBA> {

	private final static String NAME = "COMPUTE INTERSECTION";
	/**
	 * contains the intersection automaton generated
	 */
	private IntersectionBA intersection;

	/**
	 * contains the set of the visited states
	 */
	private final Set<Triple<State, State, Integer>> visitedStates;

	/**
	 * contains the intersection rule which is used to build the intersection
	 * transitions
	 */
	private final IntersectionTransitionBuilder intersectionTransitionBuilder;

	/**
	 * contains a map that associate to each constraint transition the
	 * corresponding model state
	 */
	private final Map<Transition, State> mapConstrainedTransitionModelTransparentState;
	private final SetMultimap<State, Transition> mapTransparentStateConstrainedTransition;

	/**
	 * Keeps track of the created states. For each couple of state of the model
	 * and of the claim, given an integer returns the state of the intersection
	 * automaton
	 */
	private final Map<State, Map<State, Map<Integer, State>>> createdStates;

	/**
	 * for each state of the model contains the corresponding states of the
	 * intersection automaton
	 */
	private final Map<State, State> intersectionStateModelStateMap;
	private SetMultimap<State, State> modelStateintersectionStateMap;
	
	private final ReachabilityRelation reachabilityRelation;

	/**
	 * for each state of the claim contains the corresponding states of the
	 * intersection automaton
	 */
	private final Map<State, State> intersectionStateClaimStateMap;
	private SetMultimap<State, State> claimStateintersectionStateMap;

	/**
	 * contains the model to be considered in the intersection procedure
	 */
	private final Replacement replacement;

	/**
	 * contains the claim to be considered in the intersection procedure
	 */
	private final SubProperty subproperty;

	/**
	 * contains the factory which is used to create the states of the
	 * intersection automaton
	 */
	private final IntersectionStateFactory intersectionStateFactory;

	/**
	 * is the accepting policy to be used in the computation of the intersection
	 * automaton
	 */
	private final ReplacementAcceptingPolicy acceptingPolicy;

	private final boolean underApproximation;
	/**
	 * crates a new {@link ReplacementIntersectionBuilder} which is in charge of
	 * computing the intersection automaton
	 * 
	 * @param acceptingPolicy
	 *            is the policy to be used to identify the accepting state of
	 *            the intersection automaton
	 * @param model
	 *            is the model to be considered in the intersection procedure
	 * @param claim
	 *            is the claim to be considered in the intersection procedure
	 * @param intersectionStateFactory
	 *            is the factory which is used to create the states of the
	 *            intersection automaton
	 * @param intersectionTransitionBuilder
	 *            is used to compute the intersection transitions from the
	 *            transition of the model and of the claim
	 * @throws NullPointerException
	 *             if one of the parameters is null
	 * @throws IllegalArgumentException
	 *             if the accepting policy is a {@link KripkeAcceptingPolicy}
	 *             and not all the states of the model are accepting
	 */
	public ReplacementIntersectionBuilder(Replacement replacement,
			SubProperty subproperty,
			IntersectionStateFactory intersectionStateFactory,
			IntersectionTransitionBuilder intersectionTransitionBuilder,
			ReachabilityRelation reachabilityRelation,
			boolean underApproximation) {
		super(NAME);
		Preconditions.checkNotNull(replacement,
				"The model of the system cannot be null");
		Preconditions.checkNotNull(subproperty, "The claim cannot be null");
		Preconditions.checkNotNull(intersectionStateFactory,
				"The intersection state factory cannot be null");
		Preconditions.checkNotNull(intersectionTransitionBuilder,
				"The intersection transition builder cannot be null");

		this.intersectionStateModelStateMap = new HashMap<State, State>();
		this.modelStateintersectionStateMap = HashMultimap.create();
		this.intersectionStateClaimStateMap = new HashMap<State, State>();
		this.claimStateintersectionStateMap = HashMultimap.create();
		this.mapTransparentStateConstrainedTransition = HashMultimap.create();
		this.acceptingPolicy = new ReplacementAcceptingPolicy(subproperty,
				replacement);
		/*
		 * if (acceptingPolicy instanceof KripkeAcceptingPolicy) { Preconditions
		 * .checkArgument( model.getAcceptStates().containsAll(
		 * model.getStates()),
		 * "The Kripke accepting policy is not consistend with the current model. All the states of the model must be accepting for the Kripke policy to be used "
		 * ); }
		 */
		this.intersectionTransitionBuilder = intersectionTransitionBuilder;
		this.intersection = new IntersectionBA();
		this.replacement = replacement;
		this.subproperty = subproperty;
		this.mapConstrainedTransitionModelTransparentState = new HashMap<Transition, State>();
		this.visitedStates = new HashSet<Triple<State, State, Integer>>();
		this.createdStates = new HashMap<State, Map<State, Map<Integer, State>>>();
		this.intersectionStateFactory = intersectionStateFactory;
		this.reachabilityRelation=reachabilityRelation;
		this.underApproximation=underApproximation;
	}

	
	
	/**
	 * computes the intersection of the model and the claim specified as
	 * parameter
	 * 
	 * @return the intersection of this automaton and the automaton a2
	 */
	public IntersectionBA perform() {
		if (!this.isPerformed()) {
			this.updateAlphabet();
			this.addingGreenState();

			for (State modelInit : this.replacement.getAutomaton()
					.getInitialStates()) {
				for (State claimInit : this.subproperty.getAutomaton()
						.getInitialStates()) {
					this.computeIntersection(modelInit, claimInit,
							this.acceptingPolicy.comuteInitNumber(modelInit,
									claimInit));
				}
			}
			this.addingRedState();
			Multimaps.invertFrom(
					Multimaps.forMap(this.intersectionStateClaimStateMap),
					this.claimStateintersectionStateMap);
			Multimaps.invertFrom(
					Multimaps.forMap(this.intersectionStateModelStateMap),
					this.modelStateintersectionStateMap);

			Multimaps
					.invertFrom(
							Multimaps
									.forMap(this.mapConstrainedTransitionModelTransparentState),
							this.mapTransparentStateConstrainedTransition);

			this.performed();
		}
		return this.intersection;
	}

	/**
	 * updates the alphabet of the automaton by adding the set of the characters
	 * of the model and the claim
	 */
	private void updateAlphabet() {
		for (IGraphProposition l : this.replacement.getAutomaton()
				.getPropositions()) {
			this.intersection.addProposition(l);
		}
		for (IGraphProposition l : this.subproperty.getAutomaton()
				.getPropositions()) {
			this.intersection.addProposition(l);
		}
	}

	public void updateIntersection(State modelState, State claimState,
			int number) {
		this.computeIntersection(modelState, claimState, number);
	}

	/**
	 * is a recursive procedure that computes the intersection of this automaton
	 * and the automaton a2
	 * 
	 * @param modelState
	 *            is the current state of the model under analysis
	 * @param claimState
	 *            is the current state of the claim under analysis
	 * @param number
	 *            is the number of the state under analysis
	 * @return the state that is generated
	 */
	private State computeIntersection(State modelState, State claimState,
			int number) {
		Preconditions.checkArgument(this.replacement.getAutomaton().getStates()
				.contains(modelState));
		Preconditions.checkArgument(this.subproperty.getAutomaton().getStates()
				.contains(claimState));

		// if the state has been already been visited
		if (this.checkVisitedStates(modelState, claimState, number)) {
			return this.createdStates.get(modelState).get(claimState)
					.get(number);
		} else {

			State intersectionState = this.intersectionStateFactory.create(
					modelState, claimState, number);
			this.addStateIntoTheIntersectionAutomaton(intersectionState,
					modelState, claimState, number);
			this.updateVisitedStates(intersectionState, modelState, claimState,
					number);

			// for each transition in the automaton that exits the model state
			for (Transition modelTransition : this.replacement.getAutomaton()
					.getOutTransitions(modelState)) {
				// for each transition in the extended automaton whit exit the
				// claim
				// state
				for (Transition claimTransition : this.subproperty
						.getAutomaton().getOutTransitions(claimState)) {

					// if the two transitions are compatible
					if (this.intersectionTransitionBuilder.isCompatible(
							modelTransition, claimTransition)) {

						// creates a new state made by the states s1next and s2
						// next
						State nextModelState = this.replacement.getAutomaton()
								.getTransitionDestination(modelTransition);
						State nextClaimState = this.subproperty.getAutomaton()
								.getTransitionDestination(claimTransition);

						int nextNumber = this.acceptingPolicy.comuteNumber(
								nextModelState, nextClaimState, number);

						State nextState = this.computeIntersection(
								nextModelState, nextClaimState, nextNumber);

						Transition t = this.intersectionTransitionBuilder
								.getIntersectionTransition(intersectionState,
										nextState, modelTransition,
										claimTransition);
						this.intersection.addTransition(intersectionState,
								nextState, t);
					}
				}
			}

			this.checkReachabilityTransitions(intersectionState, modelState, claimState, number);
			// if the current state of the extended automaton is transparent
			if (this.replacement.getAutomaton().isTransparent(modelState)) {
				computeTransparent(modelState, claimState, number,
						intersectionState);
			}
			return intersectionState;
		}
	}

	private void checkReachabilityTransitions(State intersectionState,
			State modelState, State claimState, int number) {

		Collection<ReachabilityEntry> subPropertyReachabilityEntries = this.reachabilityRelation.get(claimState);

		for (ReachabilityEntry subpropertyReachabilityEntry : subPropertyReachabilityEntries) {
			for (PluggingTransition replacementOutgoingTransition : this.replacement
					.getOutcomingTransition()) {
				if(replacementOutgoingTransition.getSource().equals(modelState)){
					if (Utils.isOutgoingEqual(replacementOutgoingTransition,
							subpropertyReachabilityEntry.getOutgoingTransition())) {

						for (PluggingTransition incomingTransition : this.replacement
								.getIncomingPorts()) {
							if (Utils.isIncomingEqual(incomingTransition,
									subpropertyReachabilityEntry.getIncomingTransition())) {

								int nextnumber = this.acceptingPolicy.getNumber(
										subpropertyReachabilityEntry.isAccepting(),
										subpropertyReachabilityEntry.isClaimAccepting(),
										subpropertyReachabilityEntry.isModelAccepting(),
										number, subpropertyReachabilityEntry
												.getIncomingTransition()
												.getDestination(),
										incomingTransition.getDestination());

								State nextModelState = incomingTransition
										.getDestination();
								State nextClaimState = subpropertyReachabilityEntry
										.getIncomingTransition().getDestination();
								State nextState = this.computeIntersection(
										nextModelState, nextClaimState, nextnumber);

								Transition t = new ModelTransitionFactory()
										.create();
								this.intersection.addTransition(intersectionState,
										nextState, t);
							}
						}

					}
				}
			}
		}
	}

	/**
	 * @param modelState
	 * @param claimState
	 * @param number
	 * @param intersectionState
	 */
	private void computeTransparent(State modelState, State claimState,
			int number, State intersectionState) {
		// for each transition in the automaton a2
		for (Transition claimTransition : this.subproperty.getAutomaton()
				.getOutTransitions(claimState)) {

			State nextClaimState = this.subproperty.getAutomaton()
					.getTransitionDestination(claimTransition);

			int nextNumber = this.acceptingPolicy.comuteNumber(modelState,
					nextClaimState, number);

			State nextState = this.computeIntersection(modelState,
					nextClaimState, nextNumber);

			Transition intersectionTransition = new ClaimTransitionFactory()
					.create(claimTransition.getPropositions());

			this.intersection.addConstrainedTransition(intersectionState,
					nextState, intersectionTransition);

			this.mapConstrainedTransitionModelTransparentState.put(
					intersectionTransition, modelState);

		}
	}

	/**
	 * @return the mapModelStateIntersectionTransitions
	 */
	public Map<Transition, State> getIntersectionTransitionsTransparentStatesMap() {
		return Collections
				.unmodifiableMap(mapConstrainedTransitionModelTransparentState);
	}

	private boolean checkVisitedStates(State modelState, State claimState,
			int number) {
		Preconditions
				.checkNotNull(modelState, "The model state cannot be null");
		Preconditions
				.checkNotNull(claimState, "The claim state cannot be null");
		Preconditions.checkArgument(number >= 0 && number <= 2,
				"The number must be between 0 and 2");

		return this.visitedStates
				.contains(new ImmutableTriple<State, State, Integer>(
						modelState, claimState, number));
	}

	private void updateVisitedStates(State intersectionState, State modelState,
			State claimState, int number) {
		Preconditions
				.checkNotNull(modelState, "The model state cannot be null");
		Preconditions
				.checkNotNull(claimState, "The claim state cannot be null");
		Preconditions.checkArgument(number >= 0 && number <= 2,
				"The number must be between 0 and 2");
		this.visitedStates.add(new ImmutableTriple<State, State, Integer>(
				modelState, claimState, number));

		if (!this.createdStates.containsKey(modelState)) {
			Map<State, Map<Integer, State>> map1 = new HashMap<State, Map<Integer, State>>();
			Map<Integer, State> map2 = new HashMap<Integer, State>();
			map2.put(number, intersectionState);
			map1.put(claimState, map2);
			this.createdStates.put(modelState, map1);

		} else {
			if (!this.createdStates.get(modelState).containsKey(claimState)) {
				Map<Integer, State> map2 = new HashMap<Integer, State>();
				map2.put(number, intersectionState);
				this.createdStates.get(modelState).put(claimState, map2);
			} else {
				if (!this.createdStates.get(modelState).get(claimState)
						.containsKey(new Integer(number))) {
					this.createdStates.get(modelState).get(claimState)
							.put(new Integer(number), intersectionState);
				}
			}
		}

		this.intersectionStateModelStateMap.put(intersectionState, modelState);
		this.intersectionStateClaimStateMap.put(intersectionState, claimState);
	}

	private void addStateIntoTheIntersectionAutomaton(State intersectionState,
			State modelState, State claimState, int number) {

		this.intersection.addState(intersectionState);
		if (this.replacement.getAutomaton().getInitialStates()
				.contains(modelState)
				&& this.subproperty.getAutomaton().getInitialStates()
						.contains(claimState)) {
			// if (this.acceptingPolicy instanceof KripkeAcceptingPolicy) {
			// this.intersection.addInitialState(intersectionState);
			// } else {
			if (number == 0) {
				this.intersection.addInitialState(intersectionState);
			}
			// }

		}
		if (number == 2) {
			this.intersection.addAcceptState(intersectionState);
		}
		if (this.replacement.getAutomaton().isTransparent(modelState)) {
			this.intersection.addMixedState(intersectionState);
		}
	}

	/**
	 * @param intersectionBuilder
	 * @param transitionFactory
	 * @param greenState
	 */
	private void addingGreenState() {
		State greenState = new StateFactory().create("GREEN");
		this.intersection.addState(greenState);
		this.intersection.addInitialState(greenState);

		for (ColoredPluggingTransition initTransitionSubProperty : this.subproperty
				.getIncomingTransitions()) {
			if ((underApproximation && initTransitionSubProperty.getColor() == Color.GREEN) ||
					(!underApproximation && ( initTransitionSubProperty.getColor() == Color.GREEN ||  initTransitionSubProperty.getColor() == Color.YELLOW))) {
				for (PluggingTransition initTransitionReplacement : this.replacement
						.getIncomingPorts()) {
					// if the two incoming transitions have the same source and
					// the same label
					if (initTransitionSubProperty.getSource().equals(
							initTransitionReplacement.getSource())
							&& initTransitionSubProperty
									.getTransition()
									.getPropositions()
									.equals(initTransitionReplacement
											.getTransition().getPropositions())) {

						State state = this.computeIntersection(
								initTransitionReplacement.getDestination(),
								initTransitionSubProperty.getDestination(), 0);

						Transition transition = new ModelTransitionFactory()
								.create();
						this.intersection.addTransition(greenState, state,
								transition);
					}
				}
			}
		}
	}
	
	/**
	 * @param intersectionBuilder
	 * @param transitionFactory
	 * @param stateFactory
	 */
	private void addingRedState() {
		State redState = new StateFactory().create("RED");
		this.intersection.addAcceptState(redState);
		this.intersection.addTransition(redState,
				redState, new ModelTransitionFactory().create());
		for (ColoredPluggingTransition outTransitionSubProperty : this.subproperty
				.getOutgoingTransitions()) {
			if ((underApproximation && outTransitionSubProperty.getColor() == Color.RED) ||
					(!underApproximation && (outTransitionSubProperty.getColor() == Color.RED || outTransitionSubProperty.getColor() == Color.YELLOW))) {
				for (PluggingTransition outTransitionReplacement : this.replacement
						.getOutcomingTransition()) {
					if (outTransitionSubProperty.getDestination().equals(
							outTransitionReplacement.getDestination())
							&& outTransitionSubProperty
									.getTransition()
									.getPropositions()
									.equals(outTransitionReplacement
											.getTransition().getPropositions())) {
						// getting the created intersection states
						Set<State> outIntersectionStates = this.getIntersectionStates(
										outTransitionSubProperty.getSource(),
										outTransitionReplacement.getSource());
						// connecting the green and the intersection states
						// associated with the destination of the incoming
						// transition
						for (State outState : outIntersectionStates) {
							if (!this.intersection
									.isPredecessor(outState, redState)) {
								this.intersection
										.addTransition(outState, redState,
												new ModelTransitionFactory().create());
							}
						}
					}
				}
			}
		}
	}

	/**
	 * removes the intersection state from the intersection automaton and the
	 * maps used to store the relationships between the states of the model and
	 * the claim and the intersection automaton
	 * 
	 * @param intersectionState
	 *            the intersection state to be removed
	 * @throws NullPointerException
	 *             if the intersection state is null
	 * @throws IllegalStateException
	 *             if the intersection automaton has still to be computed
	 * @throws IllegalArgumentException
	 *             if the intersection state is not in the set of the states of
	 *             the intersection automaton
	 */
	public void removeIntersectionState(State intersectionState) {
		Preconditions
				.checkState(
						this.isPerformed(),
						"It is not possible to remove an intersection state if the intersection has still to be computed");
		Preconditions.checkArgument(
				this.intersection.getStates().contains(intersectionState),
				"The state " + intersectionState
						+ " is not a state of the intersection automaton");

		this.intersectionStateClaimStateMap.remove(intersectionState);
		this.intersectionStateModelStateMap.remove(intersectionState);
		this.mapTransparentStateConstrainedTransition
				.removeAll(intersectionState);

		this.claimStateintersectionStateMap = HashMultimap.create();
		this.modelStateintersectionStateMap = HashMultimap.create();

		Multimaps.invertFrom(
				Multimaps.forMap(this.intersectionStateClaimStateMap),
				this.claimStateintersectionStateMap);
		Multimaps.invertFrom(
				Multimaps.forMap(this.intersectionStateModelStateMap),
				this.modelStateintersectionStateMap);

		for (Transition t : this.intersection
				.getInTransitions(intersectionState)) {
			if (this.mapConstrainedTransitionModelTransparentState
					.containsKey(t)) {
				State transparentState = this.mapConstrainedTransitionModelTransparentState
						.get(t);
				this.mapTransparentStateConstrainedTransition.get(
						transparentState).remove(t);
				this.mapConstrainedTransitionModelTransparentState.remove(t);
			}
		}

		for (Transition t : this.intersection
				.getOutTransitions(intersectionState)) {
			if (this.mapConstrainedTransitionModelTransparentState
					.containsKey(t)) {
				State transparentState = this.mapConstrainedTransitionModelTransparentState
						.get(t);
				this.mapTransparentStateConstrainedTransition.get(
						transparentState).remove(t);
				this.mapConstrainedTransitionModelTransparentState.remove(t);
			}
		}
		this.intersection.removeState(intersectionState);
	}

	/**
	 * returns the intersection automaton
	 * 
	 * @return the intersection automaton which have been computed
	 * @throws IllegalStateException
	 *             if the intersection has still to be computed
	 */
	public IntersectionBA getIntersectionAutomaton() {
		Preconditions
				.checkState(
						this.isPerformed(),
						"it is necessary to compute the intersection before returning the intersection automaton");
		return this.intersection;

	}
	
	public Set<State> getIntersectionStates(State claimState, State modelState) {
		Preconditions.checkNotNull(claimState,
				"The state of the claim cannot be null");
		Preconditions.checkNotNull(modelState,
				"The state of the model cannot be null");
		Preconditions
				.checkArgument(
						this.subproperty.getAutomaton().getStates().contains(claimState),
						"The state "
								+ claimState
								+ " is not contained into the set of the states of the claim");
		Preconditions
				.checkArgument(
						this.replacement.getAutomaton().getStates().contains(modelState),
						"The state "
								+ modelState
								+ " is not contained into the set of the states of the model");
		if (!createdStates.containsKey(modelState)) {
			return new HashSet<State>();
		} else {
			if (!createdStates.get(modelState).containsKey(claimState)) {
				return new HashSet<State>();
			} else {
				return Collections
						.unmodifiableSet(new HashSet<State>(createdStates
								.get(modelState).get(claimState).values()));
			}
		}
	}
}
