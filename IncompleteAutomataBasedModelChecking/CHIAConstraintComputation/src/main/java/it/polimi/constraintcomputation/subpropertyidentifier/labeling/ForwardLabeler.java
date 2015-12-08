package it.polimi.constraintcomputation.subpropertyidentifier.labeling;

import it.polimi.automata.IntersectionBA;
import it.polimi.automata.state.State;
import it.polimi.automata.transition.Transition;
import it.polimi.constraintcomputation.subpropertyidentifier.SubPropertyIdentifier;
import it.polimi.constraints.transitions.Label;
import it.polimi.constraints.transitions.LabeledPluggingTransition;

import java.util.HashSet;
import java.util.Set;

import com.google.common.base.Preconditions;

/**
 * it is used to label with the specified label all the incoming transitions
 * that are reachable from the initial state of the automaton through a run
 * which involves only the states passed as parameter. <br />
 * 
 * It modifies the sub-property contained in the {@link SubPropertyIdentifier}
 * passed as parameter
 * 
 * @author Claudio Menghi
 *
 */
public class ForwardLabeler {

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
     * contains the label to be associated to the incoming transition
     */
    private final Label label;

    /**
     * the identifier used to compute the sub-property
     */
    private final SubPropertyIdentifier subPropertyIdentifier;

    /**
     * The set of the visited states
     */
    private final Set<State> visitedStates;

    /**
     * creates a new Forward labeler function. This function is used to mark
     * with the specified label all the incoming transitions that are reachable
     * from the initial state through a run that only contains the states
     * specified as parameter
     * 
     * @param states
     *            is the set of the states of the intersection automaton to be
     *            considered
     * @param label
     *            is the label to be associated with the incoming transitions
     * @param subPropertyIdentifier
     *            is the identifier used to compute the sub-property
     * @throws NullPointerException
     *             if one of the parameters is null
     * @throws IllegalArgumentException
     *             if the set of the states to be considered is not included in
     *             the set of the states of the automaton
     */
    public ForwardLabeler(Set<State> states, Label label,
            SubPropertyIdentifier subPropertyIdentifier) {
        Preconditions.checkNotNull(states,
                "The set of the states to be considered cannot be null");
        Preconditions.checkNotNull(label,
                "The color to be considered cannot be null");
        Preconditions
                .checkArgument(
                        subPropertyIdentifier.getChecker()
                                .getUpperIntersectionBA().getStates()
                                .containsAll(states),
                        "All the states to be considered must be contained into the set of the states of the automaton");

        this.intersectionAutomaton = subPropertyIdentifier.getChecker()
                .getUpperIntersectionBA();
        this.states = states;
        this.label = label;
        this.subPropertyIdentifier = subPropertyIdentifier;
        this.visitedStates = new HashSet<State>();
    }

    /**
     * starts the coloring function from the specified state
     * 
     * @param currenState
     *            is the initial state to be considered in the search for the
     *            labeling
     * @throws NullPointerException
     *             if the current state is null
     * @throws IllegalArgumentException
     *             if the state is not contained into the set of the states of
     *             the automaton
     */
    public void perform(State currenState) {
        Preconditions.checkNotNull(currenState,
                "The state to be considered cannot be null");
        Preconditions
                .checkArgument(
                        this.intersectionAutomaton.getStates().contains(
                                currenState),
                        "The current state must be contained into the set of the states of the automaton");

        visitedStates.add(currenState);

        // for each outgoing transition
        for (Transition outTransition : this.intersectionAutomaton
                .getOutTransitions(currenState)) {
            State destinationState = this.intersectionAutomaton
                    .getTransitionDestination(outTransition);

            // if the destination state can be visited and has not visited
            // before it is traversed
            if (this.states.contains(destinationState)) {
                if (!this.visitedStates.contains(destinationState)) {
                    this.perform(destinationState);
                }
            } else {
                // otherwise the outgoing transition could be an incoming
                // transition
                if (this.subPropertyIdentifier.isInTransition(outTransition)) {
                    label(outTransition);
                }
            }
        }
    }

    /**
     * labels the outgoing transition with the specified label
     * 
     * @param outTransition
     *            the transition to be labeled
     */
    private void label(Transition outTransition) {
        LabeledPluggingTransition incomingTransition = subPropertyIdentifier
                .getIncomingTransition(outTransition);
        if (this.label == Label.G) {

            this.subPropertyIdentifier.perform().setGreenIncomingTransition(
                    incomingTransition);
            incomingTransition.setColor(this.label);
        } else {
            if (incomingTransition.getLabel() != Label.G) {
                subPropertyIdentifier.getIncomingTransition(outTransition)
                        .setColor(Label.Y);
                this.subPropertyIdentifier.perform()
                        .setYellowIncomingTransition(incomingTransition);

            }
        }
    }
}
