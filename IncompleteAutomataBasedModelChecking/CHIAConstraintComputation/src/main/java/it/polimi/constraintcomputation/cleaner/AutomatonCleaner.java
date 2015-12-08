package it.polimi.constraintcomputation.cleaner;

import it.polimi.automata.BA;
import it.polimi.automata.state.State;
import it.polimi.automata.transition.Transition;
import it.polimi.checker.emptiness.EmptinessChecker;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.jgrapht.alg.StrongConnectivityInspector;

import com.google.common.base.Preconditions;

/**
 * The intersection cleaner  returns  the states from which it is not possible to reach an accepting
 * state from the intersection automaton. <br/> 
 * Indeed, the states from which it is not possible to reach an accepting state are not useful in the
 * constraint computation.<br>
 *  
 * @author Claudio Menghi
 * 
 */
public class AutomatonCleaner {

    /**
     * contains the CHIA logger
     */
    private static final Logger LOGGER = Logger
            .getLogger(AutomatonCleaner.class);
    /**
     * contains the automaton to be considered by the {@link EmptinessChecker}
     */
    private final BA automaton;

    /**
     * creates a new Automaton Cleaner
     * 
     * @param automaton
     *            contains the automaton from which the states that do not
     *            belong to an infinite accepting run must be computed
     * @throws NullPointerException
     *             if one of the parameters is null
     */
    public AutomatonCleaner(BA automaton) {
        super();
        Preconditions.checkNotNull(automaton, "The automaton to be cleaned");

        this.automaton = automaton;

    }

    /**
     * Returns  the states from which it is not possible to reach an accepting
     * state from the intersection automaton. <br/>
     * The procedure has a temporal complexity |S|+|T|, where |S| is the
     * cardinality of the set of the states of the automaton and |T| is the
     * cardinality of the set of transitions of the automaton
     * 
     * @return The set of the states that have been removed from the automaton
     */
    public Set<State> clean() {


        LOGGER.debug("finding the strongly connected components");
        /*
         * contains the set of the visited states
         */
        StrongConnectivityInspector<State, Transition> connectivityInspector = new StrongConnectivityInspector<State, Transition>(
                this.automaton.getGraph());
        List<Set<State>> connectedSets = connectivityInspector
                .stronglyConnectedSets();

        /*
         * contains the set of the states that has been encountered by
         * <i>some<i> invocation of the first DFS
         */
        Set<State> next = new HashSet<State>();
        for (Set<State> scc : connectedSets) {
            if (!Collections.disjoint(scc, this.automaton.getAcceptStates())) {
                // if the strongly connected component has a size which is grater than 1 its states are added to the 
                // states to be visited next since the strongly connected components are not trivial by definition
                if (scc.size() > 1) {
                    next.addAll(scc);
                } else {
                    // if the strongly connected component has exactly one state it means 
                    // it must have a self loop for not being a trivial strongly connected
                    // component and thus to have an infinite accepting loop
                    State scState = scc.iterator().next();
                    if (this.automaton.getSuccessors(scState).contains(scState)) {
                        next.add(scState);
                    }
                }
            }
        }

        LOGGER.debug("The set of the states in next has size: "+next.size());
        // by starting from the states in the set next the state space is explored and the
        // visited states are added to the set visited
        Set<State> visited = new HashSet<State>();
        while (!next.isEmpty()) {

            State currentState = next.iterator().next();
            visited.add(currentState);
            next.remove(currentState);

            Set<State> potentialNext = new HashSet<State>(
                    this.automaton.getPredecessors(currentState));
            potentialNext.removeAll(visited);
            next.addAll(potentialNext);
        }

        LOGGER.debug("The set of the visited states has size: "+visited.size());
        // the states toBeRemoved contains the set of the states that are not contained
        // in an infinite path through which an accepting state can be entered infinitely often
        Set<State> toBeRemoved = new HashSet<State>(this.automaton.getStates());
        toBeRemoved.removeAll(visited);

        return toBeRemoved;
    }
}
