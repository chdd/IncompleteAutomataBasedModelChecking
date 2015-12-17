package it.polimi.constraintcomputation.subpropertyidentifier.labeling;

import it.polimi.automata.IntersectionBA;
import it.polimi.automata.state.State;
import it.polimi.automata.transition.Transition;
import it.polimi.constraintcomputation.subpropertyidentifier.SubPropertyIdentifier;
import it.polimi.constraints.transitions.Label;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.jgrapht.alg.StrongConnectivityInspector;

import com.google.common.base.Preconditions;

/**
 * it is used to label with the specified label all the outgoing transitions of
 * a sub-property <br />
 * 
 * It modifies the sub-property contained in the {@link SubPropertyIdentifier}
 * passed as parameter
 * 
 * @author Claudio Menghi
 *
 */
public class BackwardLabeler {

    /**
     * contains the intersection automaton to be considered in the labeling
     */
    private final IntersectionBA intersectionAutomaton;

    /**
     * contains the set of the states to be considered in the automaton
     * exploration
     */
    private final Set<State> states;

    /**
     * contains the label the developer wants to associate to the outgoing
     * transitions of the sub-property
     */
    private final Label label;

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
     * creates a new Backward labeler function. This function is used to mark
     * with the specified label all the outgoing transitions of the sub-property
     * 
     * @param states
     *            is the set of the states of the intersection automaton to be
     *            considered
     * @param label
     *            is the label to be associated with the outgoing transitions
     * @param subProperty
     *            is the sub-property to be considered by the
     *            {@link BackwardLabeler}
     * @throws NullPointerException
     *             if one of the parameters is null
     * @throws IllegalArgumentException
     *             if the set of the states to be considered is not included in
     *             the set of the states of the automaton
     */
    public BackwardLabeler(Set<State> states, Label label,
            SubPropertyIdentifier subProperty) {
        Preconditions.checkNotNull(states,
                "The set of the states to be considered cannot be null");
        Preconditions.checkNotNull(label,
                "The color to be considered cannot be null");
        Preconditions
                .checkArgument(
                        subProperty.getChecker().getUpperIntersectionBA()
                                .getStates().containsAll(states),
                        "All the states to be considered must be contained into the set of the states of the automaton");

        this.intersectionAutomaton = subProperty.getChecker()
                .getUpperIntersectionBA();
        this.states = states;
        this.label = label;
        this.subPropertyIntifier = subProperty;
        this.visitedStates = new HashSet<State>();
    }

    /**
     * modifies the outgoing transitions of the sub-property with the following
     * policy: <br>
     * 
     * if the outgoing transition allows to reach a SSC that contains an
     * accepting state that can be entered infinitely often, the outgoing
     * transition is marked with the label specified as parameter
     * 
     */
    public void perform() {
        // constructs an abstracted version of the intersection automaton that
        // contains only the specified states
        IntersectionBA abstractedIntersectionAutomaton = this.intersectionAutomaton
                .getAbstraction(this.states);

        // finds the strongly connected components of the intersection automaton
        StrongConnectivityInspector<State, Transition> connectivityInspector = new StrongConnectivityInspector<State, Transition>(
                abstractedIntersectionAutomaton.getGraph());
        List<Set<State>> connectedSets = connectivityInspector
                .stronglyConnectedSets();

        Set<State> next = new HashSet<State>();
        // for each strongly connected component
        for (Set<State> scc : connectedSets) {
            // if at least an accepting state is contained, its states are added
            // to the set next to be visited next
            if(states.containsAll(scc)){
                if (!Collections.disjoint(scc,
                        abstractedIntersectionAutomaton.getAcceptStates())) {
                    if(scc.size()>1){
                        next.addAll(scc);
                    }
                    else{
                        State state=scc.iterator().next();
                        if(abstractedIntersectionAutomaton.isSuccessor(state, state)){
                            next.add(state);
                        }
                    }
                }    
            }
        }

        // while the set of the states to be visited next is not empty
        while (!next.isEmpty()) {
            // pick a state, add the state to the set of visited states
            State s = next.iterator().next();
            visitedStates.add(s);
            // removes the state from the set next
            next.remove(s);
            // analyzes an incoming transition
            for (Transition incomingTransition : intersectionAutomaton
                    .getInTransitions(s)) {
                State source = intersectionAutomaton
                        .getTransitionSource(incomingTransition);
                // if the source is a state of the automaton that corresponds to
                // the sub-property
                if (this.subPropertyIntifier.perform().getAutomaton()
                        .getStates().contains(source)) {
                    // the transition is an incoming transition and must be
                    // labeled
                    label(incomingTransition);
                } else {
                    // if the state can be traversed and has not been visited it
                    // is added to the set of the states to be visited next
                    if (!visitedStates.contains(source)) {
                        if (this.states.contains(source)) {
                            next.add(source);
                        }
                    }
                }
            }
        }
    }

    /**
     * performs the labeling action over the outgoing transition
     * 
     * @param outgoingTransition
     *            the outgoing transition to be labeled
     */
    private void label(Transition outgoingTransition) {
        // if the outgoing transition is not labeled as R
        if (!(this.subPropertyIntifier
                .getOutgoingTransition(outgoingTransition).getLabel() == Label.R)) {

            // modifies the number of yellow outgoing transitions
            if (label.equals(Label.Y)) {
                this.subPropertyIntifier.perform()
                        .incrementNumberYellowOutgoingTransitions();
            }
            // modifies the number of red outgoing transitions
            if (label.equals(Label.R)) {
                this.subPropertyIntifier.perform()
                        .incrementNumberRedOutgoingTransitions();
            }
            // performs the actual labeling
            this.subPropertyIntifier.getOutgoingTransition(outgoingTransition)
                    .setLabel(label);
        }
    }
}
