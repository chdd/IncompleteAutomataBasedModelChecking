package it.polimi.contraintcomputation;

import it.polimi.automata.IntersectionBA;
import it.polimi.automata.labeling.Label;
import it.polimi.automata.state.State;
import it.polimi.automata.transition.Transition;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import edu.uci.ics.jung.graph.DirectedSparseGraph;

/**
 * This class identifies the sub-automata of the automaton that refer to the
 * transparent states of M. In particular it isolates the portions of the state
 * space that refer to the different transparent states of M.
 * 
 * @author claudiomenghi
 * 
 */
class SubAutomataIdentifier<L extends Label, S extends State, T extends Transition<L>> {

	/**
	 * contains the set of the states that has been encountered by <i>some<i>
	 * invocation of the first DFS
	 */
	private Set<S> hashedStates;

	/**
	 * contains a map that maps each state of the model with a set of states of
	 * the intersection automaton
	 */
	private Map<S, Set<S>> modelIntersectionStatesMap;

	/**
	 * contains a map that maps each state of the intersection automaton with
	 * the cluster it belongs with
	 */
	private Map<S, Set<S>> intersectionStateClusterMap;

	/**
	 * contains the intersection automaton
	 */
	private IntersectionBA<L, S, T> intersectionBA;

	/**
	 * contains the map that connect each state of the model with the
	 * corresponding clusters
	 */
	private Map<S, Set<Set<S>>> returnSubAutomata;

	/**
	 * creates an identifier for the sub automata of the intersection automata
	 * 
	 * @param intersectionBA
	 *            is the intersection automata to be considered
	 * @param map
	 *            maps each state of the model to the corresponding mixed states
	 *            of the intersection automaton
	 * @throws NullPointerException
	 *             if the intersection automaton or the map is null
	 */
	public SubAutomataIdentifier(IntersectionBA<L, S, T> intersectionBA,
			Map<S, Set<S>> map) {

		if (intersectionBA == null) {
			throw new NullPointerException(
					"The intersection automaton cannot be null");
		}
		if (map == null) {
			throw new NullPointerException(
					"The map of the intersection automaton cannot be null");
		}
		this.modelIntersectionStatesMap = map;
		this.intersectionBA = intersectionBA;
		this.intersectionStateClusterMap = new HashMap<S, Set<S>>();
		this.returnSubAutomata = new HashMap<S, Set<Set<S>>>();
		this.hashedStates = new HashSet<S>();
	}

	/**
	 * the sub-automata of the automaton that refer to the transparent states of
	 * M.
	 * 
	 * @return the sub-automata of the automaton that refer to the transparent
	 *         states of M.
	 */
	public Map<S, Set<Set<S>>> getSubAutomata() {

		DirectedSparseGraph<S, T> graph = this.intersectionBA.getGraph();
		// considering a specific transparent state
		for (Entry<S, Set<S>> entry : this.modelIntersectionStatesMap
				.entrySet()) {
			this.hashedStates=new HashSet<S>();
			this.returnSubAutomata.put(entry.getKey(), new HashSet<Set<S>>());
			for (S init : this.intersectionBA.getInitialStates()) {
				firstDFS(init, graph, entry.getValue(), null, entry.getKey());
			}
		}
		return returnSubAutomata;
	}

	/**
	 * returns true if an accepting path is found
	 * 
	 * @param currState
	 *            is the current states under analysis
	 * @return true if an accepting path is found, false otherwise
	 */
	private void firstDFS(S currState, DirectedSparseGraph<S, T> graph,
			Set<S> currentStates, Set<S> currentCluster, S modelState) {

		this.hashedStates.add(currState);
		if (currentCluster != null && currentStates.contains(currState)) {
			currentCluster.add(currState);
		} else {
			if (currentStates.contains(currState)) {
				currentCluster = new HashSet<S>();
				currentCluster.add(currState);
			} else {
				currentCluster = null;
			}
		}
		for (S nextState : graph.getSuccessors(currState)) {
			if (!this.hashedStates.contains(nextState)) {
				this.firstDFS(nextState, graph, currentStates, currentCluster,
						modelState);

			}
			else{
				if(currentStates.contains(nextState) && currentCluster!=null){
					currentCluster.addAll(this.intersectionStateClusterMap.get(nextState));
					this.returnSubAutomata.get(modelState).remove(this.intersectionStateClusterMap.get(nextState));
					this.intersectionStateClusterMap.put(nextState, currentCluster);
				}
				
			}
		}
		if(currentCluster!=null){
			this.returnSubAutomata.get(modelState).add(currentCluster);
			this.intersectionStateClusterMap.put(currState, currentCluster);
		}
		return;
	}

	public Map<S, Set<S>> getModelStateClusterMap() {
		return this.modelIntersectionStatesMap;
	}

	public Map<S, Set<S>> getIntersectionStateClusterMap() {
		return this.intersectionStateClusterMap;
	}
}
