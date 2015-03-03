package it.polimi.checker.intersection;

import it.polimi.automata.BA;
import it.polimi.automata.IBA;
import it.polimi.automata.IntersectionBA;
import it.polimi.automata.impl.IntBAImpl;
import it.polimi.automata.state.State;
import it.polimi.automata.state.StateFactory;
import it.polimi.automata.transition.IntersectionTransition;
import it.polimi.automata.transition.IntersectionTransitionFactory;
import it.polimi.automata.transition.Transition;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import rwth.i2.ltl2ba4j.model.IGraphProposition;

import com.google.common.base.Preconditions;

/**
 * Is responsible of the creation of the {@link IntersectionBA}, i.e., the
 * intersection automaton
 * 
 * @author claudiomenghi
 * @param <S>
 *            is the type of the state of the Buchi Automaton. The type of the
 *            states of the automaton must implement the interface {@link State}
 * @param <T>
 *            is the type of the transition of the Buchi Automaton. The typer of
 *            the transitions of the automaton must implement the interface
 *            {@link Transition}
 */
public class IntersectionBuilder<S extends State, T extends Transition, I extends IntersectionTransition<S>> {

	/**
	 * contains the intersection automaton generated
	 */
	private IntersectionBA<S, I> intersection;

	/**
	 * contains the intersection rule which is used to build the intersection
	 * transitions
	 */
	private IntersectionRule<S, T, I> intersectionrule;

	/**
	 * contains the state factory rule which is used to build the intersection
	 * states
	 */
	private StateFactory<S> stateFactory;

	/**
	 * is the factory which allows to create a new transition of the
	 * intersection automaton
	 */
	private IntersectionTransitionFactory<S, I> intersectionTransitionFactory;

	/**
	 * Keeps track of the created states. For each couple of state of the model
	 * and of the claim, given an integer returns the state of the intersection
	 * automaton
	 */
	private Map<S, Map<S, Map<Integer, S>>> createdStates;

	/**
	 * for each state of the model contains the corresponding states of the
	 * intersection automaton
	 */
	private Map<S, Set<S>> modelStatesMap;

	/**
	 * for each state of the claim contains the corresponding states of the
	 * intersection automaton
	 */
	private Map<S, Set<S>> claimStatesMap;

	/**
	 * contains the model to be considered in the intersection procedure
	 */
	private IBA<S, T> model;

	/**
	 * contains the claim to be considered in the intersection procedure
	 */
	private BA<S, T> claim;

	
	/**
	 * crates a new {@link IntersectionBuilder} which is in charge of computing
	 * the intersection automaton
	 * 
	 * @param intersectionrule
	 *            is the rule which is used to compute the intersection
	 *            transitions
	 * @param stateFactory
	 *            is the factory which is used to create the states of the
	 *            intersection automaton
	 * @param transitionFactory
	 *            is the factory which allows to create the transitions of the
	 *            intersection automaton
	 * @param model
	 *            is the model to be considered in the intersection procedure
	 * @param claim
	 *            is the claim to be considered in the intersection procedure
	 * @throws NullPointerException
	 *             if one of the parameters is null
	 */
	public IntersectionBuilder(IntersectionRule<S, T, I> intersectionrule,
			IBA<S, T> model, BA<S, T> claim) {
		Preconditions.checkNotNull(intersectionrule,
				"The intersection rule cannot be null");
		Preconditions.checkNotNull(model,
				"The model of the system cannot be null");
		Preconditions.checkNotNull(claim, "The claim cannot be null");

		this.intersectionrule = intersectionrule;
		this.stateFactory = intersectionrule.getIntersectionStateFactory();
		this.intersection = new IntBAImpl<S, I>(intersectionrule.getIntersectionTransitionFactory());
		this.model = model;
		this.claim = claim;
		this.intersectionTransitionFactory = intersectionrule.getIntersectionTransitionFactory();
	}

	/**
	 * computes the intersection of the model and the claim specified as
	 * parameter
	 * 
	 * @return the intersection of this automaton and the automaton a2
	 */
	public IntersectionBA<S, I> computeIntersection() {

		this.updateAlphabet();
		this.claimStatesMap = new HashMap<S, Set<S>>();
		this.modelStatesMap = new HashMap<S, Set<S>>();
		this.createdStates = new HashMap<S, Map<S, Map<Integer, S>>>();
		for (S modelInit : model.getInitialStates()) {
			for (S claimInit : claim.getInitialStates()) {
				this.computeIntersection(modelInit, claimInit, 0);
			}
		}
		return this.intersection;
	}
	
	public IntersectionBA<S, I> computeIntersection(S modelInitialState, S claimInitialState) {
		
		this.intersection = new IntBAImpl<S, I>(this.intersectionTransitionFactory);
		this.computeIntersection(modelInitialState, claimInitialState, 0);
		
		return this.intersection;
	}

	/**
	 * updates the alphabet of the automaton by adding the set of the characters
	 * of the model and the claim
	 */
	private void updateAlphabet() {
		for (IGraphProposition l : this.model.getAlphabet()) {
			this.intersection.addCharacter(l);
		}
		for (IGraphProposition l : this.claim.getAlphabet()) {
			this.intersection.addCharacter(l);
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
	private S computeIntersection(S modelState, S claimState, int number) {
		S intersectionState;

		// if the state has been already been visited
		if (this.checkVisitedStates(modelState, claimState, number)) {
			return this.createdStates.get(modelState).get(claimState)
					.get(number);
		}

		intersectionState = this.stateFactory.create(modelState.getName()
				+ " - " + claimState.getName() + " - " + number);

		this.addStateIntoTheIntersectionAutomaton(intersectionState,
				modelState, claimState, number);
		this.updateVisitedStates(intersectionState, modelState, claimState,
				number);

		// for each transition in the automaton that exits the model state
		for (T modelTransition : model.getOutTransitions(modelState)) {
			// for each transition in the extended automaton whit exit the claim
			// state
			for (T claimTransition : claim.getOutTransitions(claimState)) {

				I t = this.intersectionrule.getIntersectionTransition(
						modelTransition, claimTransition);
				// if the two transitions are compatible
				if (t != null) {

					// creates a new state made by the states s1next and s2
					// next
					S nextModelState = model
							.getTransitionDestination(modelTransition);
					S nextClaimState = claim
							.getTransitionDestination(claimTransition);

					int nextNumber = this.comuteNumber(nextModelState,
							nextClaimState, number);

					S nextState = this.computeIntersection(nextModelState,
							nextClaimState, nextNumber);

					this.intersection.addTransition(intersectionState,
							nextState, t);
				}
			}
		}

		// if the current state of the extended automaton is transparent
		if (model.isTransparent(modelState)) {
			// for each transition in the automaton a2
			for (T claimTransition : claim.getOutTransitions(claimState)) {

				I t = this.intersectionrule.getIntersectionTransition(
						modelState, claimTransition);

				if (t != null) {
					S nextClaimState = claim
							.getTransitionDestination(claimTransition);

					int nextNumber = this.comuteNumber(modelState,
							nextClaimState, number);

					S nextState = this.computeIntersection(modelState,
							nextClaimState, nextNumber);

					this.intersection.addTransition(intersectionState,
							nextState, t);
				}

			}
		}
		return intersectionState;
	}

	/**
	 * given the number of the previous state, the current model state, the
	 * claim state and the model and the claim returns the number to be
	 * associated to the state to be created
	 * 
	 * @param modelState
	 *            the model state under analysis
	 * @param claimState
	 *            the claim state under analysis
	 * @param prevNumber
	 *            the number of the previous state
	 * @return the number to be associated to the next state of the automaton
	 */
	private int comuteNumber(S modelState, S claimState, int prevNumber) {
		int num = prevNumber;

		if (prevNumber == 0 && model.getAcceptStates().contains(modelState)) {
			num = 1;
		}
		if (prevNumber == 1 && claim.getAcceptStates().contains(claimState)) {
			num = 2;
		}
		if (prevNumber == 2) {
			num = 0;
		}
		return num;
	}

	/**
	 * returns a map which contains for each state of the intersection the
	 * corresponding state of the model
	 * 
	 * @return a map which contains for each state of the intersection the
	 *         corresponding state of the model
	 */
	public Map<S, Set<S>> getModelIntersectionStateMap() {
		return this.modelStatesMap;
	}

	public Map<S, Set<S>> getClaimIntersectionStateMap() {
		return this.claimStatesMap;
	}

	private boolean checkVisitedStates(S modelState, S claimState, int number) {
		return this.createdStates.containsKey(modelState)
				&& this.createdStates.get(modelState).containsKey(claimState)
				&& this.createdStates.get(modelState).get(claimState)
						.containsKey(number);
	}

	private void updateVisitedStates(S intersectionState, S modelState,
			S claimState, int number) {
		if (!this.createdStates.containsKey(modelState)) {
			Map<S, Map<Integer, S>> map1 = new HashMap<S, Map<Integer, S>>();
			Map<Integer, S> map2 = new HashMap<Integer, S>();
			map2.put(number, intersectionState);
			map1.put(claimState, map2);
			this.createdStates.put(modelState, map1);

		} else {
			if (!this.createdStates.get(modelState).containsKey(claimState)) {
				Map<Integer, S> map2 = new HashMap<Integer, S>();
				map2.put(number, intersectionState);
				this.createdStates.get(modelState).put(claimState, map2);
			} else {
				if (!this.createdStates.get(modelState).get(claimState)
						.containsKey(number)) {
					this.createdStates.get(modelState).get(claimState)
							.put(number, intersectionState);
				}
			}
		}
		if (!this.modelStatesMap.containsKey(modelState)) {
			Set<S> relatedStates = new HashSet<S>();
			relatedStates.add(intersectionState);
			this.modelStatesMap.put(modelState, relatedStates);
		} else {
			this.modelStatesMap.get(modelState).add(intersectionState);
		}
		if (!this.claimStatesMap.containsKey(modelState)) {
			Set<S> relatedStates = new HashSet<S>();
			relatedStates.add(intersectionState);
			this.claimStatesMap.put(claimState, relatedStates);
		} else {
			this.claimStatesMap.get(claimState).add(intersectionState);
		}
	}

	private void addStateIntoTheIntersectionAutomaton(S intersectionState,
			S modelState, S claimState, int number) {
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
	
	public boolean getIntersectionStates(S modelState, S claimState){
		if(this.createdStates.get(modelState)!=null && this.createdStates.get(modelState).get(claimState)!=null){
			return true;
		}
		return false;
	}
	
	public IntersectionBA<S, I> getPrecomputedIntersectionAutomaton(){
		return this.intersection;
	}
}

