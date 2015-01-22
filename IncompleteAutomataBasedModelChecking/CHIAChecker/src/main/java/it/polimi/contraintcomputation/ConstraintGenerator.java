package it.polimi.contraintcomputation;

import it.polimi.automata.IntersectionBA;
import it.polimi.automata.labeling.Label;
import it.polimi.automata.state.State;
import it.polimi.automata.transition.Transition;
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
public class ConstraintGenerator<LABEL extends Label, STATE extends State, TRANSITION extends Transition<LABEL>> {

	/**
	 * contains a map that maps each state of the model with a set of states of
	 * the intersection automaton
	 */
	private Map<STATE, Set<STATE>> modelIntersectionStatesMap;
	private IntersectionBA<LABEL, STATE, TRANSITION> intBA;

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
	public ConstraintGenerator(IntersectionBA<LABEL, STATE, TRANSITION> intBA,
			Map<STATE, Set<STATE>> modelIntersectionStatesMap) {
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
		Map<STATE, Set<Set<STATE>>> modelStateSubAutomataMap = new SubAutomataIdentifier<LABEL, STATE, TRANSITION>(
				this.intBA, modelIntersectionStatesMap).getSubAutomata();
		/*
		 * The abstraction of the state space is a more concise version of the
		 * intersection automaton I where the portions of the state space which
		 * do not correspond to transparent states are removed
		 */
		IntersectionBA<LABEL, STATE, TRANSITION> abstractedIntersection = new Abstractor<LABEL, STATE, TRANSITION>(
				this.intBA).abstractIntersection();

		// each component of the map is analyzed
		for (STATE s : modelStateSubAutomataMap.keySet()) {
			for (Set<STATE> component : modelStateSubAutomataMap.get(s)) {
				IntersectionBA<LABEL, STATE, TRANSITION> filteredIntersection = new Filter<LABEL, STATE, TRANSITION>(
						abstractedIntersection, component)
						.filter();

				for (STATE initState : filteredIntersection.getInitialStates()) {
					for (STATE finalState : filteredIntersection
							.getAcceptStates()) {
						String regex = new Brzozowski<LABEL, STATE, TRANSITION>(
								filteredIntersection, initState, finalState)
								.getRegularExpression();
						for (TRANSITION incomingTransition : this.intBA
								.getInTransitions(initState)) {
							if (!filteredIntersection.getInTransitions(
									initState).contains(incomingTransition)) {
								for (TRANSITION outcomingTransition : this.intBA
										.getOutTransitions(finalState)) {
									if (!filteredIntersection
											.getOutTransitions(finalState)
											.contains(outcomingTransition)) {
										Proposition<LABEL, STATE, TRANSITION> p = new Proposition<LABEL, STATE, TRANSITION>(
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
