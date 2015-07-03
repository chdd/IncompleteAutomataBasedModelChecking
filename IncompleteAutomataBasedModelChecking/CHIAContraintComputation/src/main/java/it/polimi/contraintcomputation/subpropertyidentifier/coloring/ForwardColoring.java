package it.polimi.contraintcomputation.subpropertyidentifier.coloring;

import it.polimi.automata.IntersectionBA;
import it.polimi.automata.state.State;
import it.polimi.automata.transition.Transition;
import it.polimi.constraints.transitions.Color;
import it.polimi.contraintcomputation.subpropertyidentifier.SubPropertyIdentifier;

import java.util.HashSet;
import java.util.Set;

import com.google.common.base.Preconditions;

/**
 * it is used to label with the specified color all the ports that are reachable
 * from the initial state of the automaton
 * 
 * @author Claudio1
 *
 */
public class ForwardColoring {

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
	 * contains the color to be associated to the port
	 */
	private final Color color;

	private final SubPropertyIdentifier subPropertyIdentifier;

	private final Set<State> visitedStates;

	/**
	 * creates a new Forward coloring function. This function is used to mark
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
	public ForwardColoring(IntersectionBA intersectionAutomaton,
			Set<State> states, Color color, SubPropertyIdentifier subProperty) {
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
		this.subPropertyIdentifier = subProperty;
		this.visitedStates = new HashSet<State>();
	}

	/**
	 * starts the coloring function from the specified state
	 * 
	 * @param currenState
	 *            is the state to be considered in the coloring function
	 * @throws NullPointerException
	 *             if the current state is null
	 * @throws IllegalArgumentException
	 *             if the state is not contained into the set of the states of
	 *             the automaton
	 */
	public void color(State currenState) {
		Preconditions.checkNotNull(currenState,
				"The state to be considered cannot be null");
		Preconditions
				.checkArgument(
						this.intersectionAutomaton.getStates().contains(
								currenState),
						"The current state must be contained into the set of the states of the automaton");

		visitedStates.add(currenState);

		for (Transition outTransition : this.intersectionAutomaton
				.getOutTransitions(currenState)) {
			State destinationState = this.intersectionAutomaton
					.getTransitionDestination(outTransition);
			if (this.states.contains(destinationState)) {
				if (!this.visitedStates.contains(destinationState)) {
					this.color(destinationState);
				}
			} else {
				if(this.subPropertyIdentifier.isInTransition(outTransition)){
					if(this.color==Color.GREEN){
						subPropertyIdentifier.getIncomingPort(outTransition).setColor(this.color);
					}
					else{
						if(subPropertyIdentifier.getIncomingPort(outTransition).getColor()!=Color.GREEN){
							subPropertyIdentifier.getIncomingPort(outTransition).setColor(Color.YELLOW);
						}
					}
				}
			}
		}
	}
}
