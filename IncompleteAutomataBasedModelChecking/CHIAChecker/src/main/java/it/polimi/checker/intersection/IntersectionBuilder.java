package it.polimi.checker.intersection;

import it.polimi.automata.BA;
import it.polimi.automata.IBA;
import it.polimi.automata.IntersectionBA;
import it.polimi.automata.IntersectionBAFactory;
import it.polimi.automata.labeling.Label;
import it.polimi.automata.state.State;
import it.polimi.automata.state.StateFactory;
import it.polimi.automata.transition.Transition;
import it.polimi.automata.transition.TransitionFactory;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Is responsible of the creation of the {@link IntersectionBA}, i.e., the
 * intersection automaton
 * 
 * @author claudiomenghi
 * @param <STATE>
 *            is the type of the state of the Buchi Automaton. The type of the
 *            states of the automaton must implement the interface {@link State}
 * @param <TRANSITION>
 *            is the type of the transition of the Buchi Automaton. The typer of
 *            the transitions of the automaton must implement the interface
 *            {@link Transition}
 * @param <LABEL>
 *            is the type of the label of the transitions depending on whether
 *            the automaton represents the model or the claim it is a set of
 *            proposition or a propositional logic formula {@link Label}
 */
public class IntersectionBuilder<LABEL extends Label, STATE extends State, TRANSITION extends Transition<LABEL>> {

	/**
	 * contains the intersection automaton generated
	 */
	private IntersectionBA<LABEL, STATE, TRANSITION> intersection;

	/**
	 * contains the intersection rule which is used to build the intersection
	 * transitions
	 */
	private IntersectionRule<LABEL, TRANSITION> intersectionrule;

	/**
	 * contains the state factory rule which is used to build the intersection
	 * states
	 */
	private StateFactory<STATE> stateFactory;

	/**
	 * is the factory which allows to create a new transition of the
	 * intersection automaton
	 */
	private TransitionFactory<LABEL, TRANSITION> transitionFactory;

	/**
	 * Keeps track of the created states. For each couple of state of the model
	 * and of the claim, given an integer returns the state of the intersection
	 * automaton
	 */
	private Map<STATE, Map<STATE, Map<Integer, STATE>>> createdStates;

	/**
	 * contains the model to be considered in the intersection procedure
	 */
	private IBA<LABEL, STATE, TRANSITION> model;

	/**
	 * contains the claim to be considered in the intersection procedure
	 */
	private BA<LABEL, STATE, TRANSITION> claim;

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
	 * @param intersectionBAFactory
	 *            is the factory which allows to create a new intersection
	 *            automaton
	 * @param model
	 *            is the model to be considered in the intersection procedure
	 * @param claim
	 *            is the claim to be considered in the intersection procedure
	 * @throws NullPointerException
	 *             if the intersection rule or the stateFactory or the
	 *             intersectionBAFactory or the model or the claim is null
	 */
	public IntersectionBuilder(
			IntersectionRule<LABEL, TRANSITION> intersectionrule,
			StateFactory<STATE> stateFactory,
			IntersectionBAFactory<LABEL, STATE, TRANSITION> intersectionBAFactory,
			TransitionFactory<LABEL, TRANSITION> transitionFactory,
			IBA<LABEL, STATE, TRANSITION> model,
			BA<LABEL, STATE, TRANSITION> claim) {
		if (intersectionrule == null) {
			throw new NullPointerException(
					"The intersection rule cannot be null");
		}
		if (stateFactory == null) {
			throw new NullPointerException("The state factory cannot be null");
		}
		if (intersectionBAFactory == null) {
			throw new NullPointerException(
					"The factory of the intersection automaton cannot be null");
		}
		if (model == null) {
			throw new NullPointerException(
					"The model of the system cannot be null");
		}
		if (claim == null) {
			throw new NullPointerException("The claim cannot be null");
		}
		this.intersectionrule = intersectionrule;
		this.stateFactory = stateFactory;
		this.intersection = intersectionBAFactory.create();
		this.model = model;
		this.claim = claim;
		this.transitionFactory = transitionFactory;
	}

	/**
	 * computes the intersection of the model and the claim specified as
	 * parameter
	 * 
	 * @return the intersection of this automaton and the automaton a2
	 */
	public IntersectionBA<LABEL, STATE, TRANSITION> computeIntersection() {

		this.createdStates = new HashMap<STATE, Map<STATE, Map<Integer, STATE>>>();
		for (STATE modelInit : model.getInitialStates()) {
			for (STATE claimInit : claim.getInitialStates()) {
				this.computeIntersection(modelInit, claimInit, 0);
			}
		}
		return this.intersection;
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
	private STATE computeIntersection(STATE modelState, STATE claimState,
			int number) {
		STATE intersectionState;
		// if the state has been already created
		if (!this.createdStates.containsKey(modelState)
				|| !this.createdStates.get(modelState).containsKey(claimState)
				|| !this.createdStates.get(modelState).get(claimState)
						.containsKey(number)) {

			intersectionState = this.stateFactory.create(modelState.getName()
					+ " - " + claimState.getName() + " - " + number);
			if (!this.createdStates.containsKey(modelState)) {
				Map<STATE, Map<Integer, STATE>> map1 = new HashMap<STATE, Map<Integer, STATE>>();
				Map<Integer, STATE> map2 = new HashMap<Integer, STATE>();
				map2.put(number, intersectionState);
				map1.put(claimState, map2);
				this.createdStates.put(modelState, map1);
			} else {
				if (!this.createdStates.get(modelState).containsKey(claimState)) {
					Map<Integer, STATE> map2 = new HashMap<Integer, STATE>();
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
		} else {
			// creating a new intersection state
			intersectionState = this.createdStates.get(modelState)
					.get(claimState).get(number);
		}

		this.intersection.addState(intersectionState);
		if (number == 0) {
			this.intersection.addInitialState(intersectionState);
		}
		if (number == 2) {
			this.intersection.addAcceptState(intersectionState);
		}
		if (this.model.isTransparent(modelState)) {
			this.intersection.addMixedState(intersectionState);
		}

		// for each transition in the extended automaton whit source s1
		for (TRANSITION modelTransition : model.getOutTransitions(modelState)) {
			// for each transition in the extended automaton whit source s2
			for (TRANSITION claimTransition : claim
					.getOutTransitions(claimState)) {
				// if the characters of the two transitions are equal

				TRANSITION t = this.intersectionrule.getIntersectionTransition(
						modelTransition, claimTransition, transitionFactory);

				if (t != null) {

					// creates a new state made by the states s1next and s2
					// next
					STATE nextModelState = model
							.getTransitionDestination(modelTransition);
					STATE nextClaimState = claim
							.getTransitionDestination(claimTransition);

					int nextNumber = this.comuteNumber(nextModelState,
							nextClaimState, number);

					STATE nextState = this.computeIntersection(nextModelState,
							nextClaimState, nextNumber);

					this.intersection.addTransition(intersectionState,
							nextState, t);
				}
			}
		}

		// if the current state of the extended automaton is transparent
		if (model.isTransparent(modelState)) {
			// for each transition in the automaton a2
			for (TRANSITION claimTransition : claim
					.getOutTransitions(claimState)) {

				TRANSITION t = this.transitionFactory.create(claimTransition
						.getLabels());

				STATE nextClaimState = claim
						.getTransitionDestination(claimTransition);

				int nextNumber = this.comuteNumber(modelState, nextClaimState,
						number);

				STATE nextState = this.computeIntersection(modelState,
						nextClaimState, nextNumber);

				this.intersection
						.addTransition(intersectionState, nextState, t);
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
	private int comuteNumber(STATE modelState, STATE claimState, int prevNumber) {
		int num = 0;

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
	 * returns for each state of the model the corresponding states of the
	 * intersection
	 * 
	 * @return a map that relates each state of the model to the corresponding
	 *         states generated in the intersection automaton
	 */
	public Map<STATE, Set<STATE>> getMapModelIntersection() {
		Map<STATE, Set<STATE>> intersectionStateMap = new HashMap<STATE, Set<STATE>>();

		for (STATE modelState : this.createdStates.keySet()) {
			intersectionStateMap.put(modelState, new HashSet<STATE>());

			Map<STATE, Map<Integer, STATE>> entry = this.createdStates
					.get(modelState);

			for (STATE claimState : entry.keySet()) {

				Map<Integer, STATE> indexMap = entry.get(claimState);

				for (Integer index : indexMap.keySet()) {
					intersectionStateMap.get(modelState).add(
							indexMap.get(index));
				}
			}
		}
		return intersectionStateMap;
	}
}
