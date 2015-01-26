package it.polimi.contraintcomputation;

import it.polimi.automata.IntersectionBA;
import it.polimi.automata.labeling.Label;
import it.polimi.automata.state.State;
import it.polimi.automata.transition.Transition;
import it.polimi.automata.transition.TransitionFactory;
import it.polimi.automata.transition.impl.IntersectionTransitionFactoryImpl;
import it.polimi.constraints.Proposition;
import it.polimi.contraintcomputation.abstraction.Abstractor;
import it.polimi.contraintcomputation.abstraction.Filter;
import it.polimi.contraintcomputation.automataidentifier.SubAutomataIdentifier;
import it.polimi.contraintcomputation.brzozowski.Brzozowski;

import java.util.Map;
import java.util.Set;

/**
 * The constraint generator computes a constraint. A constraint is a (set of)
 * sub-model(s) for the unspecified components is produce
 * 
 * @author claudiomenghi
 * 
 */
public class ConstraintGenerator<L extends Label, S extends State, T extends Transition<L>> {

	/**
	 * contains a map that maps each state of the model with a set of states of
	 * the intersection automaton
	 */
	private Map<S, Set<S>> modelIntersectionStatesMap;
	private IntersectionBA<L, S, T> intBA;
	
	private TransitionFactory<L, T> transitionFactory;

	/**
	 * creates a new ConstraintGenerator object which starting from the
	 * intersection automaton and the map between the states of the model and
	 * the corresponding states of the intersection automaton computes the
	 * constraints
	 * 
	 * @param intBA
	 *            is the intersection automaton
	 * @param modelIntersectionStatesMap
	 *            is the map between the states of the model and the
	 *            corresponding states in the intersection automaton
	 * @throws NullPointerException
	 *             if the intersection automaton or the map is null
	 */
	public ConstraintGenerator(IntersectionBA<L, S, T> intBA,
			Map<S, Set<S>> modelIntersectionStatesMap) {
		if (intBA == null) {
			throw new NullPointerException(
					"The intersection model cannot be null");
		}
		if (modelIntersectionStatesMap == null) {
			throw new NullPointerException(
					"The map between the states of the model and the intersection states cannot be null");
		}
		this.modelIntersectionStatesMap = modelIntersectionStatesMap;
		this.intBA = intBA;
	}

	public void generateConstraint() {
		/*
		 * returns the set of the components (set of states) that correspond to
		 * the parts of the automaton that refer to different states of the
		 * model
		 */
		Map<S, Set<Set<S>>> modelStateSubAutomataMap = new SubAutomataIdentifier<L, S, T>(
				this.intBA, modelIntersectionStatesMap).getSubAutomata();
		/*
		 * The abstraction of the state space is a more concise version of the
		 * intersection automaton I where the portions of the state space which
		 * do not correspond to transparent states are removed
		 */
		IntersectionBA<L, S, T> abstractedIntersection = new Abstractor<L, S, T>(
				this.intBA, transitionFactory).abstractIntersection();

		// each component of the map is analyzed
		for (S s : modelStateSubAutomataMap.keySet()) {
			for (Set<S> component : modelStateSubAutomataMap.get(s)) {
				// filter the state space to analyze a specific component
				IntersectionBA<L, S, T> filteredIntersection = new Filter<L, S, T>(
						abstractedIntersection, component)
						.filter();

				// for each initial and final state compute the regular expression
				for (S initState : filteredIntersection.getInitialStates()) {
					for (S finalState : filteredIntersection
							.getAcceptStates()) {
						String regex = new Brzozowski<L, S, T>(
								filteredIntersection, initState, finalState)
								.getRegularExpression();
						for (T incomingTransition : this.intBA
								.getInTransitions(initState)) {
							if (!filteredIntersection.getInTransitions(
									initState).contains(incomingTransition)) {
								for (T outcomingTransition : this.intBA
										.getOutTransitions(finalState)) {
									if (!filteredIntersection
											.getOutTransitions(finalState)
											.contains(outcomingTransition)) {
										Proposition<L, S, T> p = new Proposition<L, S, T>(
												s, regex, incomingTransition,
												outcomingTransition);
									}
								}
							}
						}
					}
				}
			}
		}

	}
}
