package it.polimi.constraintcomputation.reachability.statepresence;

import it.polimi.automata.BA;
import it.polimi.automata.state.State;

import java.util.AbstractMap;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.google.common.base.Preconditions;

/**
 * Computes the states that are backward and forward reachable from the set of
 * interesting states <br>
 * 
 * Checks if the destination is reachable from the source and at least an
 * interesting state is traversed along the search
 * 
 * @author Claudio Menghi
 *
 */
public class StatePresencePathChecker {

    /**
     * the Buchi automaton to be considered
     */
    private final BA ba;

    /**
     * the set of the states that can be traversed during the reachability
     * search
     */
    private final Set<State> states;

    /**
     * the set of the states that are of interest
     */
    private final Set<State> intersetingStates;

    /**
     * This map specifies for each interesting state the set of the states that
     * are reachable through a backward search
     */
    private final Map<State, Set<State>> interestingStateBackwardReachability;

    /**
     * This map specifies for each interesting state the set of the states that
     * are reachable through a forward search
     */
    private final Map<State, Set<State>> interestingStateForwardReachability;

    /**
     * specifies that from the first state of the entry it is possible to reach
     * the second state of the entry by crossing at least an interesting state
     */
    private final Set<Entry<State, State>> reachable;

    /**
     * specifies if the search has been performed
     */
    private boolean performed;

    /**
     * creates a new checker that aims to detect whether there exists a path
     * from the source to the destination that contains an accepting state.<br/>
     * 
     * THE SET OF THE INTERESTING STATES MUST BE INCLUDED INTO THE SET OF THE
     * STATES THAT CAN BE TRAVERSED
     * 
     * @param ba
     *            the BA under analysis
     * @param states
     *            the set of the states that can be traversed in the
     *            reachability search
     * @param includedStates
     *            is the set of the states whose presence must be checked along
     *            the run
     * @throws NullPointerException
     *             if the BA under analysis or one of the two sets is null
     * @throws IllegalArgumentException
     *             if the one of the two sets has a state that is not contained
     *             into the set of the states of the BA
     * @throws IllegalArgumentException
     *             if the set of interesting states is not included into the set
     *             of the states that can be traversed
     * 
     */
    public StatePresencePathChecker(BA ba, Set<State> states,
            Set<State> intersetingStates) {
        Preconditions.checkNotNull(ba, "The BA under analysis cannot be null");
        Preconditions.checkNotNull(states,
                "The set of the states under analysis cannot be null");
        Preconditions.checkNotNull(intersetingStates,
                "The set of the interesting states cannot be null");

        Preconditions
                .checkArgument(
                        ba.getStates().containsAll(states),
                        "The set of the states to be traversed must be contained into the set of the states of the automaton");

        Preconditions
                .checkArgument(
                        ba.getStates().containsAll(intersetingStates),
                        "the set of interesting states must be contained into the set of the states of the automaton");

        Preconditions
                .checkArgument(
                        states.containsAll(intersetingStates),
                        "The set of interesting states is not included into the set of the states that can be traversed");
        this.ba = ba;
        this.interestingStateBackwardReachability = new HashMap<State, Set<State>>();
        this.interestingStateForwardReachability = new HashMap<State, Set<State>>();
        this.states = Collections.unmodifiableSet(states);
        this.intersetingStates = Collections.unmodifiableSet(intersetingStates);

        this.reachable = new HashSet<Map.Entry<State, State>>();
        this.performed = false;
        // creates for each of the intersting state an entry in the backward and
        // in the forward map
        for (State state : intersetingStates) {
            this.interestingStateBackwardReachability.put(state,
                    new HashSet<State>());
            this.interestingStateForwardReachability.put(state,
                    new HashSet<State>());
        }
    }

    /**
     * checks if the destination is reachable from the source and at least an
     * interesting state is traversed along the search
     * 
     * @param source
     *            is the source state of interest
     * @param destination
     *            is the destination state of interest
     * @return true if the destination is reachable from the source and at least
     *         an interesting state is traversed along the search
     * @throws IllegalStateException
     *             if the search has not been performed before invoking this
     *             method
     * @throws NullPointerException
     *             if the source or the destination is null
     * @throws IllegalArgumentException
     *             if the source or the destination is not in the states of the
     *             BA
     * @throws IllegalArgumentException
     *             if the source or the destination is not included into the set
     *             of the states of interest
     */
    public boolean isReachableByPassingAnInterestingState(State source,
            State destination) {
        Preconditions.checkState(performed,
                "Checks if the search has been performed");
        Preconditions.checkNotNull(source, "The source state cannot be null");
        Preconditions.checkNotNull(destination,
                "The destination state cannot be null");
        Preconditions.checkArgument(this.ba.getStates().contains(source),
                "The source state must be a state of the BA");
        Preconditions.checkArgument(this.ba.getStates().contains(destination),
                "The destination state must be a state of the BA");
        Preconditions
                .checkArgument(
                        this.states.contains(source),
                        "The source state must be contained into the set of the states that can be traversed");
        Preconditions
                .checkArgument(
                        this.states.contains(destination),
                        "The destination state must be contained into the set of the states that can be traversed");

        return this.reachable
                .contains(new AbstractMap.SimpleEntry<State, State>(source,
                        destination));
    }

    /**
     * computes whether there exists a path from every possible source to
     * destination state that contains at least a state of interest
     */
    public void perform() {

        // for each state of interest computes the backward and the forward
        // reachable states
        for (State interestingState : this.intersetingStates) {
            backwardCheck(interestingState);
            forwardCheck(interestingState);
        }

        for (State interstingState : this.intersetingStates) {
            this.findConnectedStates(interstingState);
        }
        this.performed = true;
    }

    /**
     * Finds the source and the destination state that are connected with a path
     * that crosses the interesting state
     * 
     * @param interstingState
     *            the state that is currently analyzed
     */
    private void findConnectedStates(State interstingState) {
        for (State sourceState : this.interestingStateBackwardReachability
                .get(interstingState)) {
            for (State destinationState : this.interestingStateForwardReachability
                    .get(interstingState)) {
                this.reachable.add(new AbstractMap.SimpleEntry<State, State>(
                        sourceState, destinationState));
            }
        }
    }

    /**
     * returns the states that are backward reachable from the specified state
     * (including the state itself)
     * 
     * @return the states that are backward reachable from the specified state
     * @throws NullPointerException
     *             if the specified state is null
     */
    private void backwardCheck(State state) {

        this.interestingStateBackwardReachability.get(state).add(state);
        // the set of the states to be analyzed next
        Set<State> nextStates = new HashSet<State>();
        nextStates.add(state);
        Set<State> visitedStates = new HashSet<State>();
        while (!nextStates.isEmpty()) {
            State next = nextStates.iterator().next();
            nextStates.remove(next);
            visitedStates.add(next);
            for (State predecessor : this.ba.getPredecessors(next)) {
                if (this.states.contains(predecessor)
                        && !visitedStates.contains(predecessor)) {
                    this.interestingStateBackwardReachability.get(state).add(
                            predecessor);
                    nextStates.add(predecessor);
                }
            }
        }

    }

    /**
     * returns the states that are forward reachable from the specified state
     * (including the state itself)
     * 
     * @return the states that are forward reachable from the specified state
     * @throws NullPointerException
     *             if the specified state is null
     */
    private void forwardCheck(State state) {

        this.interestingStateForwardReachability.get(state).add(state);
        Set<State> nextStates = new HashSet<State>();
        nextStates.add(state);
        Set<State> visitedStates = new HashSet<State>();
        while (!nextStates.isEmpty()) {
            State next = nextStates.iterator().next();
            nextStates.remove(next);
            visitedStates.add(next);
            for (State successorState : this.ba.getSuccessors(next)) {
                if (this.states.contains(successorState)
                        && !visitedStates.contains(successorState)) {
                    this.interestingStateForwardReachability.get(state).add(
                            successorState);
                    nextStates.add(successorState);
                }
            }
        }
    }
}
