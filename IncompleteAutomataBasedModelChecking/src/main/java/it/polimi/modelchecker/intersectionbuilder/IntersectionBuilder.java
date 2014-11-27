package it.polimi.modelchecker.intersectionbuilder;

import it.polimi.model.impl.automata.IntBAImpl;
import it.polimi.model.impl.states.IntersectionState;
import it.polimi.model.impl.states.State;
import it.polimi.model.impl.transitions.Transition;
import it.polimi.model.interfaces.automata.BA;
import it.polimi.model.interfaces.automata.IBA;
import it.polimi.model.interfaces.automata.IIntBA;
import it.polimi.model.interfaces.states.IntersectionStateFactory;
import it.polimi.model.interfaces.transitions.ConstrainedTransitionFactory;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.collections15.Factory;

/**
 * Is responsible of the creation of the {@link IIntBA}, i.e., the intersection
 * automaton
 * 
 * @author claudiomenghi
 * 
 * @param <CONSTRAINEDELEMENT>
 *            is the type of the constrained element that can be put over the
 *            intersection transition
 * @param <STATE>
 *            is the type of the {@link State} of the original {@link BA} and
 *            {@link IBA}, that is the specification and the model of the system
 * @param <TRANSITION>
 *            is the type of the {@link Transition} of the original {@link BA}
 *            and {@link IBA}
 * @param <INTERSECTIONSTATE>
 *            is the type of the {@link IntersectionState} of the intersection
 *            automaton
 * @param <INTERSECTIONTRANSITION>
 *            is the type of the {@link Transition} of the intersection
 *            automaton
 */
public class IntersectionBuilder<CONSTRAINEDELEMENT extends State, STATE extends State, TRANSITION extends Transition, INTERSECTIONSTATE extends IntersectionState<STATE>, INTERSECTIONTRANSITION extends Transition> {

	/**
	 * contains the intersection automaton generated
	 */
	private IntBAImpl<STATE, TRANSITION, INTERSECTIONSTATE, INTERSECTIONTRANSITION> intersection;

	/**
	 * contains the {@link Factory} that allows to create intersection states
	 */
	private IntersectionStateFactory<STATE, INTERSECTIONSTATE> stateFactory;

	/**
	 * contains the {@link Factory} that allows to create intersection
	 * transitions
	 */
	private ConstrainedTransitionFactory<CONSTRAINEDELEMENT, INTERSECTIONTRANSITION> transitionFactory;
	
	/**
	 * contains the {@link IntersectionRule} which is used to build the intersection transitions
	 */
	private IntersectionRule<CONSTRAINEDELEMENT, STATE, TRANSITION, INTERSECTIONTRANSITION, ConstrainedTransitionFactory<CONSTRAINEDELEMENT, INTERSECTIONTRANSITION>> intersectionrule;

	/**
	 * computes the intersection of the model and the claim specified as
	 * parameter
	 * 
	 * @param model
	 *            is the model under analysis
	 * @param claim
	 *            is the claim under analysis
	 * @param stateFactory
	 *            is the {@link Factory} that allows to create
	 *            {@link IntersectionState}
	 * @param transitionFactory
	 *            is the {@link Factory} that allows to create intersection
	 *            transitions
	 * @param intersectionrule contains the rule that is used to compute the intersection transitions
	 * 
	 * @return the intersection of this automaton and the automaton a2
	 * @throws NullPointerException
	 *             if the model, the claim, the stateFactory, the
	 *             transitionFactory or the intesection rule is null
	 */
	public IIntBA<STATE, TRANSITION, INTERSECTIONSTATE, INTERSECTIONTRANSITION> computeIntersection(
			IBA<STATE, TRANSITION> model,
			BA<STATE, TRANSITION> claim,
			IntersectionStateFactory<STATE, INTERSECTIONSTATE> stateFactory,
			ConstrainedTransitionFactory<CONSTRAINEDELEMENT, INTERSECTIONTRANSITION> transitionFactory,
			IntersectionRule<CONSTRAINEDELEMENT, STATE, TRANSITION, INTERSECTIONTRANSITION, ConstrainedTransitionFactory<CONSTRAINEDELEMENT, INTERSECTIONTRANSITION>> intersectionrule) {

		if (model == null) {
			throw new NullPointerException("The model cannot be null");
		}
		if (claim == null) {
			throw new NullPointerException("The specification cannot be null");
		}
		if (stateFactory == null) {
			throw new NullPointerException("The state factory cannot be null");
		}
		if (transitionFactory == null) {
			throw new NullPointerException(
					"The transition factory cannot be null");
		}
		if(intersectionrule==null){
			throw new NullPointerException("The intersection rule used to generate the intersection cannot be null");
		}
		this.stateFactory = stateFactory;
		this.transitionFactory = transitionFactory;
		this.intersection = new IntBAImpl<STATE, TRANSITION, INTERSECTIONSTATE, INTERSECTIONTRANSITION>(
				transitionFactory, stateFactory);
		this.intersection.addPropositions(model.getPropositions());
		this.intersection.addPropositions(claim.getPropositions());
		this.intersectionrule=intersectionrule;
		
		for (STATE s1 : model.getInitialStates()) {
			for (STATE s2 : claim.getInitialStates()) {
				INTERSECTIONSTATE p = this.addIntersectionState(s1, s2, 0,
						true, model, claim, intersection);

				Map<Entry<Entry<STATE, STATE>, Integer>, INTERSECTIONSTATE> map = new HashMap<Entry<Entry<STATE, STATE>, Integer>, INTERSECTIONSTATE>();
				this.computeIntersection(intersection, map, p, model, claim);
			}
		}
		return this.intersection;
	}

	/**
	 * is a recursive procedure that computes the intersection of this automaton
	 * and the automaton a2
	 * 
	 * @param s1
	 *            is the current state of this automaton under analysis
	 * @param s2
	 *            is the current state of the automaton a2 under analysis
	 * @param visitedStates
	 *            contains the set of the visited states of the intersection
	 *            automaton
	 * @param currentState
	 *            contains the current state of the intersection automaton under
	 *            analysis
	 */
	private void computeIntersection(
			IntBAImpl<STATE, TRANSITION, INTERSECTIONSTATE, INTERSECTIONTRANSITION> intersection,
			Map<Entry<Entry<STATE, STATE>, Integer>, INTERSECTIONSTATE> visitedStatesMap,
			INTERSECTIONSTATE currentState, IBA<STATE, TRANSITION> model,
			BA<STATE, TRANSITION> specification) {
		// if the state currentState has been already been visited it returns
		if (visitedStatesMap
				.containsKey(new AbstractMap.SimpleEntry<Entry<STATE, STATE>, Integer>(
						new AbstractMap.SimpleEntry<STATE, STATE>(currentState
								.getS1(), currentState.getS2()), currentState
								.getNumber()))) {
			return;
		} else {
			// add the current state to the set of the visited states
			visitedStatesMap
					.put(new AbstractMap.SimpleEntry<Entry<STATE, STATE>, Integer>(
							new AbstractMap.SimpleEntry<STATE, STATE>(
									currentState.getS1(), currentState.getS2()),
							currentState.getNumber()), currentState);

			// for each transition in the extended automaton whit source s1
			for (TRANSITION t1 : model.getOutTransitions(currentState.getS1())) {
				// for each transition in the extended automaton whit source s2
				for (TRANSITION t2 : specification
						.getOutTransitions(currentState.getS2())) {
					// if the characters of the two transitions are equal

					INTERSECTIONTRANSITION t = this.intersectionrule
							.getIntersectionTransition(t1, t2,
									this.transitionFactory);
					if (t != null) {

						// creates a new state made by the states s1next and s2
						// next
						STATE s1next = model.getTransitionDestination(t1);
						STATE s2next = specification
								.getTransitionDestination(t2);

						int num = this.comuteNumber(s1next, s2next,
								currentState.getNumber(), model, specification);

						INTERSECTIONSTATE p;
						if (visitedStatesMap
								.containsKey(new AbstractMap.SimpleEntry<Entry<STATE, STATE>, Integer>(
										new AbstractMap.SimpleEntry<STATE, STATE>(
												s1next, s2next), num))) {
							p = visitedStatesMap
									.get(new AbstractMap.SimpleEntry<Entry<STATE, STATE>, Integer>(
											new AbstractMap.SimpleEntry<STATE, STATE>(
													s1next, s2next), num));
						} else {
							p = this.addIntersectionState(s1next, s2next, num,
									false, model, specification, intersection);
						}
						intersection.addTransition(currentState, p, t);

						// re-executes the recursive procedure
						this.computeIntersection(intersection,
								visitedStatesMap, p, model, specification);

					}
				}
			}
			// if the current state of the extended automaton is transparent
			if (model.isTransparent(currentState.getS1())) {
				// for each transition in the automaton a2
				for (TRANSITION t2 : specification
						.getOutTransitions(currentState.getS2())) {
					// a new state is created made by the transparent state and
					// the state s2next
					STATE s2next = specification.getTransitionDestination(t2);
					INTERSECTIONSTATE p;
					int num = this.comuteNumber(currentState.getS1(), s2next,
							currentState.getNumber(), model, specification);

					if (visitedStatesMap
							.containsKey(new AbstractMap.SimpleEntry<Entry<STATE, STATE>, Integer>(
									new AbstractMap.SimpleEntry<STATE, STATE>(
											currentState.getS1(), s2next), num))) {
						p = visitedStatesMap
								.get(new AbstractMap.SimpleEntry<Entry<STATE, STATE>, Integer>(
										new AbstractMap.SimpleEntry<STATE, STATE>(
												currentState.getS1(), s2next),
										num));
					} else {
						p = this.addIntersectionState(currentState.getS1(),
								s2next, num, false, model, specification,
								intersection);
					}

					// the new state is connected by the previous one through a
					// constrained transition
					INTERSECTIONTRANSITION t = this.transitionFactory.create(
							t2.getCondition(),
							(CONSTRAINEDELEMENT) currentState.getS1());
					intersection.addTransition(currentState, p, t);
					// the recursive procedure is recalled
					this.computeIntersection(intersection, visitedStatesMap, p,
							model, specification);
				}
			}
		}
	}

	/**
	 * add a new state in the intersection automaton that is under computation
	 * based on the current state that is the state of the intersection
	 * automaton which precedes the state that is generated by this method (the
	 * current state is used to compute the number 0,1,2
	 * 
	 * @param s1
	 *            is the state of the extended automaton (this)
	 * @param s2
	 *            is the state of the specification
	 * @param currentState
	 *            is the current state of the intersection (the one that
	 *            precedes the state that is generated in this method)
	 * @return the new intersection state
	 */
	private INTERSECTIONSTATE addIntersectionState(
			STATE s1,
			STATE s2,
			int number,
			boolean initial,
			IBA<STATE, TRANSITION> model,
			BA<STATE, TRANSITION> specification,
			IntBAImpl<STATE, TRANSITION, INTERSECTIONSTATE, INTERSECTIONTRANSITION> intersection) {

		INTERSECTIONSTATE p = generateIntersectionState(s1, s2, number, model,
				specification);
		if (initial) {
			intersection.addInitialState(p);
		}
		if (p.getNumber() == 2) {
			intersection.addAcceptState(p);
		}
		if (model.isTransparent(s1)) {
			intersection.addMixedState(p);
		}
		intersection.addState(p);
		return p;
	}

	/**
	 * generates a new intersection state
	 * 
	 * @param s1
	 *            : is the state of the automaton a1 to be included in the
	 *            intersection state
	 * @param s2
	 *            : is the state of the automaton a2 to be included in the
	 *            intersection state
	 * @param currentState
	 *            : is the current state to be considered in the generation of
	 *            the automaton state
	 * @return a new intersection state
	 */
	protected INTERSECTIONSTATE generateIntersectionState(STATE s1, STATE s2,
			int number, IBA<STATE, TRANSITION> model,
			BA<STATE, TRANSITION> specification) {

		return this.stateFactory.create(s1, s2, number);
	}

	protected int comuteNumber(STATE s1, STATE s2, int prevNumber,
			IBA<STATE, TRANSITION> model, BA<STATE, TRANSITION> specification) {
		int num = 0;

		if (prevNumber == 0 && model.isAccept(s1)) {
			num = 1;
		}
		if (prevNumber == 1 && specification.isAccept(s2)) {
			num = 2;
		}
		if (prevNumber == 2) {
			num = 0;
		}
		return num;
	}

}
