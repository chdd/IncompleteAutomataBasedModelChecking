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
	private final Map<Transition, State> mapConstrainedTransitionModelTransparentState;
	private final SetMultimap<State, Transition> mapTransparentStateConstrainedTransition;

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
			AcceptingPolicy acceptingPolicy,
			IntersectionStateFactory intersectionStateFactory,
			IntersectionTransitionBuilder intersectionTransitionBuilder) {
		super(NAME);
		Preconditions.checkNotNull(model,
				"The model of the system cannot be null");
		Preconditions.checkNotNull(claim, "The claim cannot be null");
		Preconditions.checkNotNull(acceptingPolicy,
				"The accepting policy cannot be null");
		Preconditions.checkNotNull(intersectionStateFactory,
				"The intersection state factory cannot be null");
		Preconditions.checkNotNull(intersectionTransitionBuilder, "The intersection transition builder cannot be null");

		this.intersectionStateModelStateMap = new HashMap<State, State>();
		this.modelStateintersectionStateMap = HashMultimap.create();
		this.intersectionStateClaimStateMap = new HashMap<State, State>();
		this.claimStateintersectionStateMap = HashMultimap.create();
		this.mapTransparentStateConstrainedTransition = HashMultimap.create();
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
		this.intersectionTransitionBuilder = intersectionTransitionBuilder;
		this.intersection = new IntersectionBA();
		this.model = model;
		this.claim = claim;
		this.mapConstrainedTransitionModelTransparentState = new HashMap<Transition, State>();
		this.visitedStates = new HashSet<Triple<State, State, Integer>>();
		this.createdStates = new HashMap<State, Map<State, Map<Integer, State>>>();
		this.intersectionStateFactory = intersectionStateFactory;
		this.intersectionStateNumber=new HashMap<State, Integer>();
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
		for (IGraphProposition l : this.model.getPropositions()) {
			this.intersection.addProposition(l);
		}
		for (IGraphProposition l : this.claim.getPropositions()) {
			this.intersection.addProposition(l);
		}
	}
	public void updateIntersection(State modelState, State claimState,
			int number){
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
					if (this.intersectionTransitionBuilder.isCompatible(modelTransition, claimTransition)) {

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
								.getIntersectionTransition(intersectionState, nextState, modelTransition,
										claimTransition);
						this.intersection.addTransition(intersectionState,
								nextState, t);
					}
				}
			}

			// if the current state of the extended automaton is transparent
			if (model.isTransparent(modelState)) {
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

					this.mapConstrainedTransitionModelTransparentState.put(
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
		return Collections
				.unmodifiableMap(mapConstrainedTransitionModelTransparentState);
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

		this.intersectionStateModelStateMap.put(intersectionState, modelState);
		this.intersectionStateClaimStateMap.put(intersectionState, claimState);
	}

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
		if (this.model.isTransparent(modelState)) {
			this.intersection.addMixedState(intersectionState);
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
	 * returns the set of the states of the intersection which are associated
	 * with a specific state of the claim
	 * 
	 * @param claimState
	 *            is the state of the claim under interest
	 * @return the set of the states of the intersection automaton associated
	 *         with the claim state
	 * @throws NullPointerException
	 *             if the claim state is null
	 * @throws IllegalStateException
	 *             if the intersection has still to be computed
	 */
	public Set<State> getClaimIntersectionStates(State claimState) {
		Preconditions.checkState(this.isPerformed(),
				"The intersection has still not be computed");
		Preconditions.checkNotNull(claimState,
				"The state of the claim cannot be null");
		return this.claimStateintersectionStateMap.get(claimState);
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
	 */
	public Set<State> getIntersectionStates(State claimState, State modelState) {
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

	public State getIntersectionState(State claimState, State modelState, int number) {
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
			return null;
		} else {
			if (!createdStates.get(modelState).containsKey(claimState)) {
				return null;
			} else {
				return createdStates
								.get(modelState).get(claimState).get(number);
			}
		}
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
	 * @throws IllegalStateException
	 *             if the intersection has still to be computed
	 */
	public Set<State> getModelIntersectionStates(State modelState) {
		Preconditions.checkState(this.isPerformed(),
				"The intersection has still not be computed");
		Preconditions
				.checkNotNull(modelState, "The claim state cannot be null");

		return this.modelStateintersectionStateMap.get(modelState);
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
	 *             if the intersection state si not contained into the set of
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
	 * returns the model from which the intersection is computed
	 * 
	 * @return the model from which the intersection is computed
	 * @throws IllegalStateException
	 *             if the intersection has still to be computed
	 */
	public IBA getModel() {
		Preconditions
				.checkState(
						this.isPerformed(),
						"it is necessary to compute the intersection before returning the model from which it is computed");
		return this.model;
	}

	/**
	 * contains the claim from which the intersection is computed
	 * 
	 * @return the claim from which the intersection is computed
	 * @throws IllegalStateException
	 *             if the intersection has still to be computed
	 */
	public BA getClaim() {
		Preconditions
				.checkState(
						this.isPerformed(),
						"it is necessary to compute the intersection before returning the claim from which it is computed");

		return this.claim;
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
	 * returns the set of constrained transitions associated with the
	 * transparent state
	 * 
	 * @param transparentState
	 *            is the transparent state of the model to be considered
	 * @return the set of transition associated with the transparent state
	 * @throws NullPointerException
	 *             if the transparent state is null
	 * @throws IllegalArgumentException
	 *             if the transparent state is not a transparent state of the
	 *             model
	 */
	public Set<Transition> getConstrainedTransitions(State transparentState) {
		Preconditions.checkNotNull(transparentState,
				"The transparent state to be considered cannot be null");
		Preconditions.checkArgument(
				this.model.getTransparentStates().contains(transparentState),
				"The state " + transparentState + " is not transparent");
		Preconditions
				.checkState(
						this.intersection.getTransitions().containsAll(
								this.mapTransparentStateConstrainedTransition
										.get(transparentState)),
						"Internal error there are transitions associated to the transparent state "
								+ transparentState
								+ " which do not belongs to the intersection automaton");
		return this.mapTransparentStateConstrainedTransition
				.get(transparentState);
	}

	public AcceptingPolicy getAcceptingPolicy() {
		return this.acceptingPolicy;
	}
	
	public int getNumber(State intersectionState){
		Preconditions.checkNotNull("The state cannot be null");
		Preconditions.checkArgument(this.intersection.getStates().contains(intersectionState));
		
		return this.intersectionStateNumber.get(intersectionState);
	}

}
