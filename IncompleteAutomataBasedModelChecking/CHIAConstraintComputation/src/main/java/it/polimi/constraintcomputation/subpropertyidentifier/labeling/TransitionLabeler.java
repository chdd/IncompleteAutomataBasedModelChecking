package it.polimi.constraintcomputation.subpropertyidentifier.labeling;

import java.util.HashSet;
import java.util.Set;

import it.polimi.automata.IntersectionBA;
import it.polimi.automata.state.State;
import it.polimi.constraintcomputation.subpropertyidentifier.SubPropertyIdentifier;
import it.polimi.constraints.transitions.Label;

import com.google.common.base.Preconditions;

/**
 * contains the labeling component. The labeling component is used to compute
 * the labeling of the incoming and outgoing transitions associated with a
 * sub-property. <br/>
 * 
 * Given a sub-property Sb, and given the set of its incoming and outgoing
 * transitions  DeltainSb and DeltaoutSb and the intersection automaton I, 
 * and incoming transition deltainSb=<s,a,s'> is
 * <ul>
 * <li>R if and only if there exists an accepting run starting from s' such that
 * all the states of the run are purely regular</li>
 * <li>Y if and only if it is not marked as R and there exists a possibly
 * accepting run starting from s' that includes only states that are not
 * associated with the automaton Sb</li>
 * </ul>
 * an outgoing transition deltaoutSb=<s,a,s'> is marked as
 * <ul>
 * <li>G if and only if it is reachable from the initial state through a run
 * that involves only purely regular states</li>
 * <li>Y if and only if it is not green and it is reachable from the initial
 * state through a run that involves also mixed states that are not associated
 * with the automaton Sb</li>
 * </ul>
 * 
 * 
 * @author Claudio Menghi
 *
 */
public class TransitionLabeler {

    /**
     * contains the identifier which has been used to generate the sub-property
     */
    private SubPropertyIdentifier subPropertyIdentifier;

    /**
     * The labeling algorithm labels each incoming and outgoing transitions of
     * the sub-property associated with the corresponding label.
     * 
     * @param subPropertyIdentifier
     *            is the sub-property identifier to be considered by the
     *            labeling algorithm
     * @throws NullPointerException
     *             if the subPropertyIdentifier is null
     */
    public TransitionLabeler(SubPropertyIdentifier subPropertyIdentifier) {
        Preconditions.checkNotNull(subPropertyIdentifier,
                "The subproperty identified cannot be null");
        this.subPropertyIdentifier = subPropertyIdentifier;
    }

    /**
     * performs the labeling
     */
    public void perform() {

        // gets the intersection automaton
        IntersectionBA intersectionAutomaton = this.subPropertyIdentifier
                .getChecker().getUpperIntersectionBuilder()
                .getIntersectionAutomaton();

        this.computeGreenIncomingTransitions(intersectionAutomaton);

        this.computeYellowIncomingTransitions(intersectionAutomaton);

        this.computeRedOutgoingTransitions(intersectionAutomaton);

        this.computeYellowOutgoingTransitions(intersectionAutomaton);
    }

    /**
     * @param intersectionAutomaton
     */
    private void computeYellowOutgoingTransitions(
            IntersectionBA intersectionAutomaton) {
        // computes the states of the intersection automaton which are not
        // states of the sub-property
        Set<State> states = new HashSet<State>(
                intersectionAutomaton.getStates());
        states.removeAll(subPropertyIdentifier.perform().getAutomaton()
                .getStates());
        BackwardLabeler backwardYellowColoring = new BackwardLabeler(states,
                Label.Y, this.subPropertyIdentifier);
        backwardYellowColoring.perform();
    }

    /**
     * @param intersectionAutomaton
     */
    private void computeRedOutgoingTransitions(
            IntersectionBA intersectionAutomaton) {
        BackwardLabeler backwardRedColoring = new BackwardLabeler(
                intersectionAutomaton.getPurelyRegularStates(), Label.R,
                this.subPropertyIdentifier);
        backwardRedColoring.perform();
    }

    /**
     * @param intersectionAutomaton
     * @return
     */
    private void computeYellowIncomingTransitions(
            IntersectionBA intersectionAutomaton) {
        // computes the initial states of the intersection automaton which are
        // not initial states of the sub-property
        Set<State> initialStates = new HashSet<State>(
                intersectionAutomaton.getInitialStates());
        initialStates.removeAll(subPropertyIdentifier.perform().getAutomaton()
                .getStates());
        // computes the states of the intersection automaton which are not
        // states of the sub-property
        Set<State> states = new HashSet<State>(
                intersectionAutomaton.getStates());
        states.removeAll(subPropertyIdentifier.perform().getAutomaton()
                .getStates());
        ForwardLabeler yellowForwardColoring = new ForwardLabeler(states,
                Label.Y, this.subPropertyIdentifier);
        for (State initialState : initialStates) {
            yellowForwardColoring.perform(initialState);
        }
    }

    /**
     * @param intersectionAutomaton
     */
    private void computeGreenIncomingTransitions(
            IntersectionBA intersectionAutomaton) {
        // gets the initial states that are not mixed
        Set<State> initialStates = new HashSet<State>(
                intersectionAutomaton.getInitialStates());
        initialStates.removeAll(intersectionAutomaton.getMixedStates());

        // computes the GREEN incoming transitions of the sub-property, i.e.,
        // the incoming transitions to be marked as G
        ForwardLabeler greenForwardColoring = new ForwardLabeler(
                intersectionAutomaton.getPurelyRegularStates(), Label.G,
                this.subPropertyIdentifier);
        for (State initialState : initialStates) {
            greenForwardColoring.perform(initialState);
        }
    }
}
