package it.polimi.contraintcomputation.subpropertyidentifier.coloring;

import it.polimi.automata.IntersectionBA;
import it.polimi.automata.state.State;
import it.polimi.automata.transition.Transition;
import it.polimi.constraints.transitions.Label;
import it.polimi.contraintcomputation.subpropertyidentifier.SubPropertyIdentifier;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.jgrapht.alg.StrongConnectivityInspector;

import com.google.common.base.Preconditions;

public class BackwardColoring {

	/**
	 * contains the intersection automaton to be considered in the coloring
	 * function
	 */
	private final IntersectionBA intersectionAutomaton;
	
	/**
	 * contains the set of the states to be considered in the automaton
	 * exploration
	 */
	private final Set<State> states;

	/**
	 * contains the color that the developer wants to associate to the incoming
	 * and outgoing transitions of the subproperty
	 */
	private final Label color;

	/**
	 * is the identifier which has been used to generate the sub-property of
	 * interest
	 */
	private final SubPropertyIdentifier subPropertyIntifier;

	/**
	 * contains the set of the visited states. It is used to has the already
	 * visited states to guarantee that a state is not visited twice
	 */
	private final Set<State> visitedStates;

	/**
	 * creates a new Backward coloring function. This function is used to mark
	 * with the specified color all the incoming ports that are reachable from
	 * the initial state through a path that only contains purely regular states
	 * 
	 * @param intersectionAutomaton
	 *            is the intersection automaton to be considered
	 * @param states
	 *            is the set of the states of the intersection automaton to be
	 *            considered
	 * @param color
	 *            is the color to be associated with the incoming ports
	 * @throws NullPointerException
	 *             if one of the parameters is null
	 * @throws IllegalArgumentException
	 *             if the set of the states to be considered is not included in
	 *             the set of the states of the automaton
	 */
	public BackwardColoring(IntersectionBA intersectionAutomaton,
			Set<State> states, Label color, SubPropertyIdentifier subProperty) {
		Preconditions.checkNotNull(intersectionAutomaton,
				"The intersection automaton cannot be null");
		Preconditions.checkNotNull(states,
				"The set of the states to be considered cannot be null");
		Preconditions.checkNotNull(color,
				"The color to be considered cannot be null");
		Preconditions
				.checkArgument(
						intersectionAutomaton.getStates().containsAll(
								states),
						"All the states to be considered must be contained into the set of the states of the automaton");

		this.intersectionAutomaton = intersectionAutomaton;
		this.states = states;
		this.color = color;
		this.subPropertyIntifier = subProperty;
		this.visitedStates = new HashSet<State>();
	}

	public void perform() {
		IntersectionBA abstractedIntersectionAutomaton = this.intersectionAutomaton
				.getAbstraction(this.states);

		StrongConnectivityInspector<State, Transition> connectivityInspector = new StrongConnectivityInspector<State, Transition>(
				abstractedIntersectionAutomaton.getGraph());
		List<Set<State>> connectedSets = connectivityInspector
				.stronglyConnectedSets();

		Set<State> next = new HashSet<State>();
		for (Set<State> scc : connectedSets) {
			if (!Collections.disjoint(scc,
					abstractedIntersectionAutomaton.getAcceptStates())) {
				if (scc.size() > 1) {
					next.addAll(scc);
				} else {
					State scState = scc.iterator().next();
					if (abstractedIntersectionAutomaton.getSuccessors(scState)
							.contains(scState)) {
						next.add(scState);
					}
				}
			}
		}

		while (next.size() > 0) {
			State s = next.iterator().next();
			visitedStates.add(s);
			next.remove(s);
			for (Transition t : intersectionAutomaton
					.getInTransitions(s)) {
				State source = intersectionAutomaton
						.getTransitionSource(t);
				if (this.subPropertyIntifier.getSubProperty().getAutomaton()
						.getStates().contains(source)) {
					if (!(this.subPropertyIntifier.getOutgoingPort(t).getColor() == Label.R)) {
						
						
						if(color.equals(Label.Y) && !(this.subPropertyIntifier.getOutgoingPort(t).getColor() == Label.Y)){
							this.subPropertyIntifier.getSubProperty().incrementNumberYellowOutgoingTransitions();
						}
						if(color.equals(Label.R) && !(this.subPropertyIntifier.getOutgoingPort(t).getColor() == Label.R)){
							this.subPropertyIntifier.getSubProperty().incrementNumberRedOutgoingTransitions();
						}
						this.subPropertyIntifier.getOutgoingPort(t).setColor(color);

					}
				} else {
					if (!visitedStates.contains(source) && this.states.contains(source)) {
						next.add(source);
					}
				}
			}
		}
	}
}
