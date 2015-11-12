package it.polimi.checker.intersection;

import it.polimi.automata.BA;
import it.polimi.automata.IBA;
import it.polimi.automata.IntersectionBA;
import it.polimi.automata.state.IntersectionStateFactory;
import it.polimi.automata.state.State;
import it.polimi.automata.transition.ClaimTransitionFactory;
import it.polimi.automata.transition.Transition;
import it.polimi.checker.intersection.acceptingpolicies.AcceptingPolicy;
import it.polimi.checker.intersection.acceptingpolicies.KripkeAcceptingPolicy;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.tuple.ImmutableTriple;
import org.apache.commons.lang3.tuple.Triple;

import action.CHIAAction;

import com.google.common.base.Preconditions;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimaps;
import com.google.common.collect.SetMultimap;

/**
 * Computes the intersection between an Incomplete Buchi automaton and a Buchi
 * Automaton
 * 
 * @author Claudio Menghi
 */
public class IntersectionBuilder extends CHIAAction<IntersectionBA> {

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
	private final Map<Transition, State> mapConstrainedTransitionModelBlackBoxState;
	private final SetMultimap<State, Transition> mapBlackBoxStateConstrainedTransition;

	/**
	 * Keeps track of the created states. For each couple of state of the model
	 * and of the claim, given an integer returns the state of the intersection
	 * automaton
	 */
	private final Map<State, Map<State, Map<Integer, State>>> createdStates;

	private final Map<State, Integer> intersectionStateNumber;
	/**
	 * for each state of the model contains the corresponding states of the
	 * intersection automaton
	 */
	private final Map<State, State> intersectionStateModelStateMap;
	private SetMultimap<State, State> modelStateintersectionStateMap;

	/**
	 * for each state of the claim contains the corresponding states of the
	 * intersection automaton
	 */
	private final Map<State, State> intersectionStateClaimStateMap;
	private SetMultimap<State, State> claimStateintersectionStateMap;

	/**
	 * contains the model to be considered in the intersection procedure
	 */
	private final IBA model;

	/**
	 * contains the claim to be considered in the intersection procedure
	 */
	private final BA claim;

	/**
	 * contains the factory which is used to create the states of the
	 * intersection automaton
	 */
	private final IntersectionStateFactory intersectionStateFactory;

	/**
	 * is the accepting policy to be used in the computation of the intersection
	 * automaton
	 */
	private final AcceptingPolicy acceptingPolicy;

	/**
	 * crates a new {@link IntersectionBuilder} which is in charge of computing
	 * the intersection automaton
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
	public IntersectionBuilder(IBA model, BA claim,
			AcceptingPolicy acceptingPolicy) {
		super(NAME);
		Preconditions.checkNotNull(model,
				"The model of the system cannot be null");
		Preconditions.checkNotNull(claim, "The claim cannot be null");
		Preconditions.checkNotNull(acceptingPolicy,
				"The accepting policy cannot be null");
		;

		this.intersectionStateModelStateMap = new HashMap<State, State>();
		this.modelStateintersectionStateMap = HashMultimap.create();
		this.intersectionStateClaimStateMap = new HashMap<State, State>();
		this.claimStateintersectionStateMap = HashMultimap.create();
		this.mapBlackBoxStateConstrainedTransition = HashMultimap.create();
		this.acceptingPolicy = acceptingPolicy;
		this.acceptingPolicy.setClaim(claim);
		this.acceptingPolicy.setModel(model);
		/*
		 * if (acceptingPolicy instanceof KripkeAcceptingPolicy) { Preconditions
		 * .checkArgument( model.getAcceptStates().containsAll(
		 * model.getStates()),
		 * "The Kripke accepting policy is not consistend with the current model. All the states of the model must be accepting for the Kripke policy to be used "
		 * ); }
		 */
		this.intersectionTransitionBuilder = new IntersectionTransitionBuilder();
		this.intersection = new IntersectionBA();
		this.model = model;
		this.claim = claim;
		this.mapConstrainedTransitionModelBlackBoxState = new HashMap<Transition, State>();
		this.visitedStates = new HashSet<Triple<State, State, Integer>>();
		this.createdStates = new HashMap<State, Map<State, Map<Integer, State>>>();
		this.intersectionStateFactory = new IntersectionStateFactory();
		this.intersectionStateNumber = new HashMap<State, Integer>();
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

			for (State modelInit : model.getInitialStates()) {
				for (State claimInit : claim.getInitialStates()) {
					this.computeIntersection(modelInit, claimInit,
							this.acceptingPolicy.comuteInitNumber(modelInit,
									claimInit));
				}
			}
			Multimaps.invertFrom(
					Multimaps.forMap(this.intersectionStateClaimStateMap),
					this.claimStateintersectionStateMap);
			Multimaps.invertFrom(
					Multimaps.forMap(this.intersectionStateModelStateMap),
					this.modelStateintersectionStateMap);

			Multimaps.invertFrom(Multimaps
					.forMap(this.mapConstrainedTransitionModelBlackBoxState),
					this.mapBlackBoxStateConstrainedTransition);

			this.performed();
		}
		return this.intersection;
	}

	/**
	 * @return the mapModelStateIntersectionTransitions
	 */
	public Map<Transition, State> getIntersectionTransitionsBlackBoxStatesMap() {
		return Collections
				.unmodifiableMap(mapConstrainedTransitionModelBlackBoxState);
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
		Preconditions.checkNotNull(intersectionState,
				"The intersection state cannot be null");
		Preconditions.checkArgument(
				this.intersection.getStates().contains(intersectionState),
				"The state " + intersectionState
						+ " is not a state of the intersection automaton");

		this.intersectionStateClaimStateMap.remove(intersectionState);
		this.intersectionStateModelStateMap.remove(intersectionState);
		this.mapBlackBoxStateConstrainedTransition.removeAll(intersectionState);

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
			if (this.mapConstrainedTransitionModelBlackBoxState.containsKey(t)) {
				State blackboxState = this.mapConstrainedTransitionModelBlackBoxState
						.get(t);
				this.mapBlackBoxStateConstrainedTransition.get(blackboxState)
						.remove(t);
				this.mapConstrainedTransitionModelBlackBoxState.remove(t);
			}
		}

		for (Transition t : this.intersection
				.getOutTransitions(intersectionState)) {
			if (this.mapConstrainedTransitionModelBlackBoxState.containsKey(t)) {
				State blackBoxState = this.mapConstrainedTransitionModelBlackBoxState
						.get(t);
				this.mapBlackBoxStateConstrainedTransition.get(blackBoxState)
						.remove(t);
				this.mapConstrainedTransitionModelBlackBoxState.remove(t);
			}
		}
		this.intersection.removeState(intersectionState);
	}

	/**
	 * The intersection computation starts from the initial state of the claim
	 * and the model and computes the intersection automaton. For this reason,
	 * if a particular state of the model or of the claim is not reachable from
	 * the initial state of the model or the claim, no states of the
	 * intersection automaton corresponding to this state are generated. The
	 * update intersection method allows the computation of the portion of the
	 * state space starting from the specified model state, claim state and
	 * number
	 * 
	 * @param modelState
	 *            is the state of the model to be considered
	 * @param claimState
	 *            is the state of the claim to be considered
	 * @param number
	 *            is the "initial number" from which the computation must start
	 * @throws NullPointerException
	 *             if the model state or the claim state is null
	 * @throws IllegalArgumentException
	 *             if the modelState or the claimState is not a state of the
	 *             model or the claim or if no state is associated with the
	 *             model state, claim state and number
	 * @throws IllegalStateException
	 *             if the intersection has still to be computed
	 */
	public void updateIntersection(State modelState, State claimState,
			int number) {
		Preconditions.checkState(this.isPerformed(),
				"The intersection has still not be computed");

		Preconditions.checkNotNull(claimState,
				"The state of the claim cannot be null");
		Preconditions.checkNotNull(modelState,
				"The state of the model cannot be null");
		Preconditions
				.checkArgument(
						this.claim.getStates().contains(claimState),
						"The state "
								+ claimState
								+ " is not contained into the set of the states of the claim");
		Preconditions
				.checkArgument(
						this.model.getStates().contains(modelState),
						"The state "
								+ modelState
								+ " is not contained into the set of the states of the model");
		Preconditions.checkArgument(number >= 0 && number <= 2,
				"The number must be between 0 and 2");

		this.computeIntersection(modelState, claimState, number);
	}

	/**
	 * returns the set of the states of the intersection automaton associated
	 * with the specified state of the claim and of the model
	 * 
	 * @param claimState
	 *            is the state of the claim that is considered
	 * @param modelState
	 *            is the state of the model that is considered
	 * @return the set of the states of the intersection automaton associated
	 *         with the state of the claim and of the model specified as
	 *         parameter
	 * @throws NullPointerException
	 *             if one of the states is null
	 * @throws IllegalArgumentException
	 *             if the state of the claim is not contained into the claim or
	 *             if the state of the model is not contained into the model
	 * @throws IllegalStateException
	 *             if the intersection has still to be computed
	 */
	public Set<State> getIntersectionStates(State modelState, State claimState) {
		Preconditions.checkState(this.isPerformed(),
				"The intersection has still not be computed");
		Preconditions.checkNotNull(claimState,
				"The state of the claim cannot be null");
		Preconditions.checkNotNull(modelState,
				"The state of the model cannot be null");
		Preconditions
				.checkArgument(
						this.claim.getStates().contains(claimState),
						"The state "
								+ claimState
								+ " is not contained into the set of the states of the claim");
		Preconditions
				.checkArgument(
						this.model.getStates().contains(modelState),
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

	/**
	 * returns the intersection state (if it exists) that is associated with the
	 * specified model state, the claim state and the number passed as parameter
	 * 
	 * @param modelState
	 *            is the state of the model to be considered
	 * @param claimState
	 *            the state of the claim under analysis
	 * @param number
	 *            the number to be considered
	 * @return the intersection state associated with the inputs, null if such a
	 *         state does not exist
	 * @throws NullPointerException
	 *             if the model state or the claim state is null
	 * @throws IllegalArgumentException
	 *             if the modelState or the claimState is not a state of the
	 *             model or the claim or if no state is associated with the
	 *             model state, claim state and number
	 * @throws IllegalStateException
	 *             if the intersection has still to be computed
	 */
	public State getIntersectionState(State modelState, State claimState,
			int number) {
		Preconditions.checkState(this.isPerformed(),
				"The intersection has still not be computed");

		Preconditions.checkNotNull(claimState,
				"The state of the claim cannot be null");
		Preconditions.checkNotNull(modelState,
				"The state of the model cannot be null");
		Preconditions
				.checkArgument(
						this.claim.getStates().contains(claimState),
						"The state "
								+ claimState
								+ " is not contained into the set of the states of the claim");
		Preconditions
				.checkArgument(
						this.model.getStates().contains(modelState),
						"The state "
								+ modelState
								+ " is not contained into the set of the states of the model");
		Preconditions.checkArgument(number >= 0 && number <= 2,
				"The number must be between 0 and 2");

		if (!createdStates.containsKey(modelState)) {
			return null;
		} else {
			if (!createdStates.get(modelState).containsKey(claimState)) {
				return null;
			} else {
				if (!createdStates.get(modelState).get(claimState)
						.containsKey(number)) {
					return null;
				}
				return createdStates.get(modelState).get(claimState)
						.get(number);
			}
		}
	}

	/**
	 * returns the set of constrained transitions associated with the black box
	 * state
	 * 
	 * @param blackBoxState
	 *            is the black box state of the model to be considered
	 * @return the set of transition associated with the black box state
	 * @throws NullPointerException
	 *             if the black box state is null
	 * @throws IllegalArgumentException
	 *             if the black box state is not a black box state of the model
	 * @throws IllegalStateException
	 *             if the intersection has still to be computed
	 */
	public Set<Transition> getConstrainedTransitions(State blackBoxState) {
		Preconditions.checkState(this.isPerformed(),
				"The intersection has still not be computed");
		Preconditions.checkNotNull(blackBoxState,
				"The black box state to be considered cannot be null");
		Preconditions.checkArgument(
				this.model.getBlackBoxStates().contains(blackBoxState),
				"The state " + blackBoxState + " is not black box");

		return this.mapBlackBoxStateConstrainedTransition.get(blackBoxState);
	}

	/**
	 * returns the set of the states of the intersection which are associated
	 * with a specific state of the claim
	 * 
	 * @param claimState
	 *            is the state of the claim under interest
	 * @return the set of the states of the intersection automaton associated
	 *         with the claim state
	 * @throws NullPointerException
	 *             if the claim state is null
	 * @throws IllegalArgumentException
	 *             if the claimState is not a state of the claim
	 * @throws IllegalStateException
	 *             if the intersection has still to be computed
	 */
	public Set<State> getClaimIntersectionStates(State claimState) {
		Preconditions.checkState(this.isPerformed(),
				"The intersection has still not be computed");
		Preconditions.checkNotNull(claimState,
				"The state of the claim cannot be null");
		Preconditions
				.checkArgument(
						this.claim.getStates().contains(claimState),
						"The state "
								+ claimState
								+ " is not contained into the set of the states of the claim");
		return this.claimStateintersectionStateMap.get(claimState);
	}

	/**
	 * returns the set of the states of the intersection which are associated
	 * with a specific state of the model
	 * 
	 * @param modelState
	 *            is the state of the model under interest
	 * @return the set of the states of the intersection automaton associated
	 *         with the model state
	 * @throws NullPointerException
	 *             if the model state is null
	 * @throws IllegalArgumentException
	 *             if the modelState is not a state of the model
	 * @throws IllegalStateException
	 *             if the intersection has still to be computed
	 */
	public Set<State> getModelIntersectionStates(State modelState) {
		Preconditions.checkState(this.isPerformed(),
				"The intersection has still not be computed");
		Preconditions
				.checkNotNull(modelState, "The model state cannot be null");

		Preconditions
				.checkArgument(
						this.model.getStates().contains(modelState),
						"The state "
								+ modelState
								+ " is not contained into the set of the states of the model");
		return this.modelStateintersectionStateMap.get(modelState);
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

	/**
	 * returns the model state associated with the specified intersection state
	 * 
	 * @param intersectionState
	 *            the intersection state to be considered
	 * @return the state of the model associated with the specified
	 *         intersections state
	 * @throws NullPointerException
	 *             if the intersection states is null
	 * @throws IllegalArgumentException
	 *             if the intersection state is not contained into the set of
	 *             the states of the intersection automaton
	 */
	public State getModelState(State intersectionState) {
		Preconditions.checkNotNull(intersectionState,
				"The intersection state to be considered cannot be null");
		Preconditions
				.checkArgument(
						this.intersection.getStates().contains(
								intersectionState),
						"The intersection state is not present in the set of the states of the intersection automaton");
		return intersectionStateModelStateMap.get(intersectionState);
	}

	/**
	 * returns the claim state associated with the specified intersection state
	 * 
	 * @param intersectionState
	 *            the intersection state to be considered
	 * @return the state of the claim associated with the specified
	 *         intersections state
	 * @throws NullPointerException
	 *             if the intersection states is null
	 * @throws IllegalArgumentException
	 *             if the intersection state is not contained into the set of
	 *             the states of the intersection automaton
	 */
	public State getClaimState(State intersectionState) {
		Preconditions.checkNotNull(intersectionState,
				"The intersection state to be considered cannot be null");
		Preconditions
				.checkArgument(
						this.intersection.getStates().contains(
								intersectionState),
						"The intersection state is not present in the set of the states of the intersection automaton");
		return this.intersectionStateClaimStateMap.get(intersectionState);
	}

	/**
	 * returns the accepting policy used to compute the intersection
	 * 
	 * @return the accepting policy used to compute the intersection
	 */
	public AcceptingPolicy getAcceptingPolicy() {
		return this.acceptingPolicy;
	}

	/**
	 * returns the model from which the intersection is computed
	 * 
	 * @return the model from which the intersection is computed
	 */
	public IBA getModel() {
		return this.model;
	}

	/**
	 * contains the claim from which the intersection is computed
	 * 
	 * @return the claim from which the intersection is computed
	 */
	public BA getClaim() {

		return this.claim;
	}

	/**
	 * given an intersectionState returns the corresponding number
	 * 
	 * @param intersectionState
	 *            is the intersection state of interest
	 * @return the number of the intersection state
	 * @throws NullPointerException
	 *             if the intersectionState is null
	 * @throws IllegalArgumentException
	 *             if the intersection state is not contained into the set of
	 *             the states of the intersection
	 */
	public int getNumber(State intersectionState) {
		Preconditions.checkNotNull(intersectionState,
				"The state cannot be null");
		Preconditions
				.checkArgument(
						this.intersection.getStates().contains(
								intersectionState),
						"The state "
								+ intersectionState
								+ " is not contained into the set of the states of the intersection");

		return this.intersectionStateNumber.get(intersectionState);
	}

	/**
	 * checks if a state has been already been visited in the intersection
	 * generation
	 * 
	 * @param modelState
	 *            is the state of the model to be considered
	 * @param claimState
	 *            is the state of the claim to be considered
	 * @param number
	 *            is the number of the intersection state
	 * @return true if the state has been already visited, false otherwise
	 */
	private boolean checkVisitedStates(State modelState, State claimState,
			int number) {
		Preconditions
				.checkNotNull(modelState, "The model state cannot be null");
		Preconditions.checkNotNull(claim, "The claim state cannot be null");

		return this.visitedStates
				.contains(new ImmutableTriple<State, State, Integer>(
						modelState, claimState, number));
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
		Preconditions
				.checkArgument(this.model.getStates().contains(modelState));
		Preconditions
				.checkArgument(this.claim.getStates().contains(claimState));

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
			for (Transition modelTransition : model
					.getOutTransitions(modelState)) {
				// for each transition in the extended automaton whit exit the
				// claim
				// state
				for (Transition claimTransition : claim
						.getOutTransitions(claimState)) {

					// if the two transitions are compatible
					if (this.intersectionTransitionBuilder.isCompatible(
							modelTransition, claimTransition)) {

						// creates a new state made by the states s1next and s2
						// next
						State nextModelState = model
								.getTransitionDestination(modelTransition);
						State nextClaimState = claim
								.getTransitionDestination(claimTransition);

						int nextNumber = this.acceptingPolicy.comuteNumber(
								nextModelState, nextClaimState, number);

						State nextState = this.computeIntersection(
								nextModelState, nextClaimState, nextNumber);

						Transition t = this.intersectionTransitionBuilder
								.getIntersectionTransition(modelTransition,
										claimTransition);
						this.intersection.addTransition(intersectionState,
								nextState, t);
					}
				}
			}

			// if the current state of the extended automaton is black box state
			if (model.isBlackBox(modelState)) {
				// for each transition in the automaton a2
				for (Transition claimTransition : claim
						.getOutTransitions(claimState)) {

					State nextClaimState = claim
							.getTransitionDestination(claimTransition);

					int nextNumber = this.acceptingPolicy.comuteNumber(
							modelState, nextClaimState, number);

					State nextState = this.computeIntersection(modelState,
							nextClaimState, nextNumber);

					Transition intersectionTransition = new ClaimTransitionFactory()
							.create(claimTransition.getPropositions());

					this.intersection.addConstrainedTransition(
							intersectionState, nextState,
							intersectionTransition);

					this.mapConstrainedTransitionModelBlackBoxState.put(
							intersectionTransition, modelState);

				}
			}
			return intersectionState;
		}
	}

	private void updateVisitedStates(State intersectionState, State modelState,
			State claimState, int number) {
		Preconditions
				.checkNotNull(modelState, "The model state cannot be null");
		Preconditions.checkNotNull(claim, "The claim state cannot be null");
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

	/**
	 * adds the intersection state to the intersection automaton
	 * 
	 * @param intersectionState
	 *            the intersection state to be added
	 * @param modelState
	 *            the state of the model that corresponds to the intersection
	 *            state
	 * @param claimState
	 *            the state of the claim that corresponds to the intersection
	 *            state
	 * @param number
	 *            the number associated with the intersection state
	 */
	private void addStateIntoTheIntersectionAutomaton(State intersectionState,
			State modelState, State claimState, int number) {
		this.intersectionStateNumber.put(intersectionState, number);
		this.intersection.addState(intersectionState);
		if (this.model.getInitialStates().contains(modelState)
				&& this.claim.getInitialStates().contains(claimState)) {
			if (this.acceptingPolicy instanceof KripkeAcceptingPolicy) {
				this.intersection.addInitialState(intersectionState);
			} else {
				if (number == 0) {
					this.intersection.addInitialState(intersectionState);
				}
			}

		}
		if (number == 2) {
			this.intersection.addAcceptState(intersectionState);
		}
		if (this.model.isBlackBox(modelState)) {
			this.intersection.addMixedState(intersectionState);
		}
	}

	/**
	 * updates the alphabet of the automaton by adding the set of the
	 * propositions of the model and the claim
	 */
	private void updateAlphabet() {
		this.intersection.addPropositions(this.model.getPropositions());
		this.intersection.addPropositions(this.claim.getPropositions());
	}

}
