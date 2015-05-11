package it.polimi.contraintcomputation.subpropertyidentifier.coloring;

import it.polimi.automata.IntersectionBA;
import it.polimi.automata.state.State;
import it.polimi.automata.transition.Transition;
import it.polimi.constraints.Color;
import it.polimi.contraintcomputation.subpropertyidentifier.SubPropertiesIdentifier;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.jgrapht.DirectedGraph;
import org.jgrapht.alg.StrongConnectivityInspector;

import com.google.common.base.Preconditions;

/**
 * The RedOutgoingSearch algorithm first looks for strongly connected components
 * that involve only purely regular states
 * 
 * @author Claudio1
 *
 */
public class RedOutgoingSearch {

	private SubPropertiesIdentifier sbpId;

	public RedOutgoingSearch(SubPropertiesIdentifier sbpId) {
		Preconditions.checkNotNull(sbpId,
				"The subproperty identified cannot be null");
		this.sbpId = sbpId;
	}

	public void startSearch() {

		IntersectionBA intersectionBA = sbpId.getIntersectionBA();
		// The algorithm first construct an abstracted version of the intersection automaton where only the purely regular states of $\mathcal{I}$ are contained
		
		IntersectionBA purelyRegularBA = sbpId.getIntersectionBA()
				.getPurelyRegularStateAbstraction();
		DirectedGraph<State, Transition> graph = purelyRegularBA.getGraph();
		StrongConnectivityInspector<State, Transition> inspector = new StrongConnectivityInspector<State, Transition>(
				graph);
		List<Set<State>> sccs = inspector.stronglyConnectedSets();

		//  Then, the Tarjan algorithm~\cite{} is used to identify the strongly connected components of the abstracted intersection automaton
		// The set next is initialized to contain all the strongly connected components that contain a state which is accepting
		Set<State> next = new HashSet<State>();
		for (Set<State> scc : sccs) {
			if (scc.size() == 1) {
				State s = scc.iterator().next();
				if (purelyRegularBA.getSuccessors(s).contains(s)) {
					next.add(s);
				}
			} else {
				Set<State> tmp = new HashSet<State>(scc);
				tmp.retainAll(purelyRegularBA.getAcceptStates());
				if (tmp.size() > 0) {
					next.addAll(tmp);
				}
			}
		}

		// Then the state space of the intersection automaton is explored to compute the outgoing ports from which it is  possible to reach one of the states in the set next.
		
		 
		 //The set visited Line is used to keep track of the already visited states of the intersection automaton
		Set<State> visited = new HashSet<State>();
		while (!next.isEmpty()) {
			
			// The algorithm iteratively chooses a state s in the set next which is removed from next and added to the set of visited states 
			State s = next.iterator().next();
			visited.add(s);
			next.remove(s);
			//  For each incoming transition of s
			 
			for (Transition outTransition : intersectionBA.getInTransitions(s)) {
				State source = intersectionBA
						.getTransitionSource(outTransition);
				//  if the state s is mixed . 
				if (intersectionBA.getMixedStates().contains(source)) {
					//  the corresponding transition is marked as red $R$ 
					this.sbpId.getOutPort(outTransition).setColor(Color.RED);
				} else {
					//  if the purely regular state s has not already been visited it is added to the set next of states to be considered next 
					if (!visited.contains(source)) {
						next.add(source);
					}
				}
			}
		}

	}
}
