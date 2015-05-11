package it.polimi.checker.intersection;

import it.polimi.automata.BA;
import it.polimi.automata.IBA;
import it.polimi.automata.IntersectionBA;
import it.polimi.automata.state.State;
import it.polimi.automata.state.StateFactory;
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

import rwth.i2.ltl2ba4j.model.IGraphProposition;

import com.google.common.base.Preconditions;

/**
 * Computes the intersection between an Incomplete Buchi automaton and a Buchi
 * Automaton
 * 
 * @author claudiomenghi
 */
public class IntersectionBuilder {

	/**
	 * contains the intersection automaton generated
	 */
	protected IntersectionBA intersection;

	protected final Set<Triple<State, State, Integer>> visitedStates;

	/**
	 * contains the intersection rule which is used to build the intersection
	 * transitions
	 */
	protected final IntersectionRule intersectionrule;

	protected Map<Transition, State> mapModelStateIntersectionTransitions;

	/**
	 * Keeps track of the created states. For each couple of state of the model
	 * and of the claim, given an integer returns the state of the intersection
	 * automaton
	 */
	protected Map<State, Map<State, Map<Integer, State>>> createdStates;

	/**
	 * for each state of the model contains the corresponding states of the
	 * intersection automaton
	 */
	protected Map<State, Set<State>> modelStatesIntersectionStateMap;
	protected final Map<State, State> intersectionStateModelStateMap;

	/**
	 * for each state of the claim contains the corresponding states of the
	 * intersection automaton
	 */
	protected Map<State, Set<State>> claimIntersectionStatesMap;
	protected final Map<State, State> intersectionStateClaimStateMap;

	/**
	 * contains the model to be considered in the intersection procedure
	 */
	protected final IBA model;

	/**
	 * contains the claim to be considered in the intersection procedure
	 */
	protected final BA claim;
	protected final AcceptingPolicy acceptingPolicy;
	protected boolean intersectionComputed = false;

	/**
	 * crates a new {@link IntersectionBuilder} which is in charge of computing
	 * the intersection automaton
	 * 
	 * @param intersectionrule
	 *            is the rule which is used to compute the intersection
	 *            transitions
	 * @param model
	 *            is the model to be considered in the intersection procedure
	 * @param claim
	 *            is the claim to be considered in the intersection procedure
	 * @throws NullPointerException
	 *             if one of the parameters is null
	 */
	public IntersectionBuilder(IBA model, BA claim,
			AcceptingPolicy acceptingPolicy) {
		Preconditions.checkNotNull(model,
				"The model of the system cannot be null");
		Preconditions.checkNotNull(claim, "The claim cannot be null");
		Preconditions.checkNotNull(acceptingPolicy,
				"The accepting policy cannot be null");

		this.acceptingPolicy = acceptingPolicy;
		this.acceptingPolicy.setClaim(claim);
		this.acceptingPolicy.setModel(model);
		if (acceptingPolicy instanceof KripkeAcceptingPolicy) {
			Preconditions
					.checkArgument(
							model.getAcceptStates().containsAll(
									model.getStates()),
							"The Kripke accepting policy is not consistend with the current model. All the states of the model must be accepting for the Kripke policy to be used ");
		}
		this.intersectionrule = new IntersectionRule();
		this.intersection = new IntersectionBA();
		this.model = model;
		this.claim = claim;
		this.intersectionStateModelStateMap = new HashMap<State, State>();
		this.mapModelStateIntersectionTransitions = new HashMap<Transition, State>();
		this.intersectionStateClaimStateMap = new HashMap<State, State>();
		this.visitedStates = new HashSet<Triple<State, State, Integer>>();
		this.intersectionComputed = false;
	}

	public Map<State, State> getIntersectionStateModelStateMap() {
		return this.intersectionStateModelStateMap;
	}

	/**
	 * computes the intersection of the model and the claim specified as
	 * parameter
	 * 
	 * @return the intersection of this automaton and the automaton a2
	 */
	public IntersectionBA computeIntersection() {

		if (!intersectionComputed) {
			this.updateAlphabet();
			this.claimIntersectionStatesMap = new HashMap<State, Set<State>>();
			this.modelStatesIntersectionStateMap = new HashMap<State, Set<State>>();
			this.createdStates = new HashMap<State, Map<State, Map<Integer, State>>>();
			for (State modelInit : model.getInitialStates()) {
				for (State claimInit : claim.getInitialStates()) {
					this.computeIntersection(modelInit, claimInit, this.acceptingPolicy.comuteInitNumber(modelInit, claimInit));
				}
			}
			this.intersectionComputed = true;
		}
		return this.intersection;
	}

	public IntersectionBA computeIntersection(State modelInitialState,
			State claimInitialState) {

		this.intersection = new IntersectionBA();
		this.computeIntersection(modelInitialState, claimInitialState, this.acceptingPolicy.comuteInitNumber(modelInitialState, claimInitialState));
		
		this.intersectionComputed = true;
		return this.intersection;
	}

	/**
	 * updates the alphabet of the automaton by adding the set of the characters
	 * of the model and the claim
	 */
	private void updateAlphabet() {
		for (IGraphProposition l : this.model.getPropositions()) {
			this.intersection.addProposition(l);
		}
		for (IGraphProposition l : this.claim.getPropositions()) {
			this.intersection.addProposition(l);
		}
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
			State intersectionState = new StateFactory().create(modelState
					.getId() + " - " + claimState.getId() + " - " + number);

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

					Transition t = this.intersectionrule
							.getIntersectionTransition(modelTransition,
									claimTransition);
					// if the two transitions are compatible
					if (t != null) {

						// creates a new state made by the states s1next and s2
						// next
						State nextModelState = model
								.getTransitionDestination(modelTransition);
						State nextClaimState = claim
								.getTransitionDestination(claimTransition);

						int nextNumber = this.acceptingPolicy.comuteNumber(nextModelState,
								nextClaimState, number);
						if (nextNumber < 0 || nextNumber > 2) {
							throw new InternalError("next number not correct");
						}
						State nextState = this.computeIntersection(
								nextModelState, nextClaimState, nextNumber);

						this.intersection.addTransition(intersectionState,
								nextState, t);
					}
				}
			}

			// if the current state of the extended automaton is transparent
			if (model.isTransparent(modelState)) {
				if (!this.modelStatesIntersectionStateMap
						.containsKey(modelState)) {
					this.modelStatesIntersectionStateMap.put(modelState,
							new HashSet<State>());
				}
				// for each transition in the automaton a2
				for (Transition claimTransition : claim
						.getOutTransitions(claimState)) {

					State nextClaimState = claim
							.getTransitionDestination(claimTransition);

					int nextNumber = this.acceptingPolicy.comuteNumber(modelState,
							nextClaimState, number);

					State nextState = this.computeIntersection(modelState,
							nextClaimState, nextNumber);

					Transition intersectionTransition = new ClaimTransitionFactory()
							.create(claimTransition.getPropositions());

					this.intersection.addConstrainedTransition(
							intersectionState, nextState,
							intersectionTransition);

					this.mapModelStateIntersectionTransitions.put(
							intersectionTransition, modelState);

				}
			}
			return intersectionState;
		}
	}

	/**
	 * @return the mapModelStateIntersectionTransitions
	 */
	public Map<Transition, State> getIntersectionTransitionsTransparentStatesMap() {
		return mapModelStateIntersectionTransitions;
	}

	/**
	 * returns a map which contains for each state of the intersection the
	 * corresponding state of the model
	 * 
	 * @return a map which contains for each state of the intersection the
	 *         corresponding state of the model
	 */
	public Map<State, Set<State>> getModelStateIntersectionStateMap() {
		Preconditions.checkState(intersectionComputed,
				"The intersection has still not be computed");
		return this.modelStatesIntersectionStateMap;
	}

	/**
	 * given a state of the model returns the corresponding states of the
	 * intersection automaton
	 * 
	 * @param modelState
	 *            is the state of the model whose corresponding intersection
	 *            states must be returned
	 * @return the intersection states that correspond to the state of the model
	 *         specified as parameter
	 * @throws NullPointerException
	 *             if the state of the model is null
	 * @throws IllegalArgumentException
	 *             if the state is not a state of the model
	 */
	public Set<State> getAssociatedStates(State modelState) {
		Preconditions.checkNotNull(modelState,
				"The state of the model to be considered cannot be null");
		Preconditions.checkArgument(
				this.model.getStates().contains(modelState),
				"You must specify a state of the model");
		if (this.modelStatesIntersectionStateMap.containsKey(modelState)) {
			return Collections
					.unmodifiableSet(this.modelStatesIntersectionStateMap
							.get(modelState));
		} else {
			return Collections.unmodifiableSet(new HashSet<State>());
		}
	}

	public Map<State, Set<State>> getClaimStateIntersectionStateMap() {
		Preconditions.checkState(intersectionComputed,
				"The intersection has still not be computed");
		return this.claimIntersectionStatesMap;
	}

	public Set<State> getClaimReleatedIntersectionStates(State claimState) {
		Preconditions.checkState(intersectionComputed,
				"The intersection has still not be computed");
		Preconditions
				.checkNotNull(claimState, "The claim state cannot be null");

		if (!this.claimIntersectionStatesMap.containsKey(claimState)) {
			return new HashSet<State>();
		}
		return this.claimIntersectionStatesMap.get(claimState);
	}

	public Set<State> getModelReleatedIntersectionStates(State modelState) {
		Preconditions.checkState(intersectionComputed,
				"The intersection has still not be computed");
		Preconditions
				.checkNotNull(modelState, "The claim state cannot be null");
		if (!this.modelStatesIntersectionStateMap.containsKey(modelState)) {
			return new HashSet<State>();
		}
		return this.modelStatesIntersectionStateMap.get(modelState);
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
	 */
	public Set<State> getClaimIntersectionStates(State claimState) {
		Preconditions.checkState(intersectionComputed,
				"The intersection has still not be computed");
		Preconditions.checkNotNull(claimState,
				"The state of the claim cannot be null");
		if (!this.claimIntersectionStatesMap.containsKey(claimState)) {
			return new HashSet<State>();
		}
		return this.claimIntersectionStatesMap.get(claimState);
	}

	private boolean checkVisitedStates(State modelState, State claimState,
			int number) {
		Preconditions
				.checkNotNull(modelState, "The model state cannot be null");
		Preconditions.checkNotNull(claim, "The claim state cannot be null");
		Preconditions.checkArgument(number >= 0 && number <= 2,
				"The number must be between 0 and 2");

		return this.visitedStates
				.contains(new ImmutableTriple<State, State, Integer>(
						modelState, claimState, number));
		/*
		 * this.createdStates.containsKey(modelState) &&
		 * this.createdStates.get(modelState).containsKey(claimState) &&
		 * this.createdStates.get(modelState).get(claimState) .containsKey(new
		 * Integer(number));
		 */
	}

	private void updateVisitedStates(State intersectionState, State modelState,
			State claimState, int number) {
		Preconditions
				.checkNotNull(modelState, "The model state cannot be null");
		Preconditions.checkNotNull(claim, "The claim state cannot be null");
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
		if (!this.modelStatesIntersectionStateMap.containsKey(modelState)) {
			this.modelStatesIntersectionStateMap.put(modelState,
					new HashSet<State>());
		}
		if (!this.claimIntersectionStatesMap.containsKey(claimState)) {
			this.claimIntersectionStatesMap.put(claimState,
					new HashSet<State>());
		}
		this.modelStatesIntersectionStateMap.get(modelState).add(
				intersectionState);

		this.claimIntersectionStatesMap.get(claimState).add(intersectionState);
		this.intersectionStateModelStateMap.put(intersectionState, modelState);
		this.intersectionStateClaimStateMap.put(intersectionState, claimState);

	}

	private void addStateIntoTheIntersectionAutomaton(State intersectionState,
			State modelState, State claimState, int number) {

		this.intersection.addState(intersectionState);
		if (this.model.getInitialStates().contains(modelState)
				&& this.claim.getInitialStates().contains(claimState)) {
			this.intersection.addInitialState(intersectionState);
		}
		if (number == 2) {
			this.intersection.addAcceptState(intersectionState);
		}
		if (this.model.isTransparent(modelState)) {
			this.intersection.addMixedState(intersectionState);
		}
	}

	public IntersectionBA getPrecomputedIntersectionAutomaton() {
		Preconditions.checkState(intersectionComputed,
				"The intersection has still not be computed");
		return this.intersection;
	}

	public IBA getModel() {
		return this.model;
	}

	/**
	 * contains the claim to be considered in the intersection procedure
	 */
	public BA getClaim() {
		return this.claim;
	}

	/**
	 * @return the intersectionStateClaimStateMap
	 */
	public Map<State, State> getIntersectionStateClaimStateMap() {
		return intersectionStateClaimStateMap;
	}
}
