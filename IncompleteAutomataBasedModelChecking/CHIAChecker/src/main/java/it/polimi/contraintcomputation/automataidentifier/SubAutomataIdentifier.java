package it.polimi.contraintcomputation.automataidentifier;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import it.polimi.automata.IntersectionBA;
import it.polimi.automata.labeling.Label;
import it.polimi.automata.state.State;
import it.polimi.automata.transition.Transition;

/**
 * This class identifies the sub-automata of the automaton that refer to the
 * transparent states of M. In particular it isolates the portions of the state
 * space that refer to the different transparent states of M.
 * 
 * @author claudiomenghi
 * 
 */
public class SubAutomataIdentifier<L extends Label, S extends State, T extends Transition<L>> {

	/**
	 * contains a map that maps each state of the model with a set of states of
	 * the intersection automaton
	 */
	private Map<S, Set<S>> modelIntersectionStatesMap;

	/**
	 * contains a map that maps each state of the intersection automaton with
	 * the cluster it belongs with
	 */
	private Map<S, Set<S>> intersectionClusterMap;

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
	public SubAutomataIdentifier(
			IntersectionBA<L, S, T> intersectionBA,
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
		this.intersectionClusterMap = new HashMap<S, Set<S>>();
		this.returnSubAutomata = new HashMap<S, Set<Set<S>>>();
	}

	/**
	 * the sub-automata of the automaton that refer to the transparent states of
	 * M.
	 * 
	 * @return the sub-automata of the automaton that refer to the transparent
	 *         states of M.
	 */
	public Map<S, Set<Set<S>>> getSubAutomata() {

		for (S intersectionState : this.intersectionBA.getStates()) {
			this.intersectionClusterMap.put(intersectionState,
					new HashSet<S>());
		}
		for (S modelState : modelIntersectionStatesMap.keySet()) {
			Set<S> abstraction = new HashSet<S>(
					modelIntersectionStatesMap.get(modelState));
			while (!abstraction.isEmpty()) {
				S currState = abstraction.iterator().next();
				this.exploration(modelState, currState);
				abstraction.remove(currState);
			}
			
			Set<Set<S>> clusters=new HashSet<Set<S>>();
			for(S s: modelIntersectionStatesMap.get(modelState)){
				clusters.add(this.intersectionClusterMap.get(s));
			}
			this.returnSubAutomata.put(modelState, clusters);
		}
		return returnSubAutomata;
	}

	/**
	 * explores the state space to identify the sub-automata of the automaton
	 * that refer to the transparent states of M. In particular it isolates the
	 * portions of the state space that refer to the different transparent
	 * states of M.
	 * 
	 * @param currState
	 *            is the current state to be analyzed
	 * @param currentCluster
	 *            is the set of the states in the cluster
	 * @param currentAbstraction
	 *            contains the current set of states that abstract the state
	 *            space
	 */
	private void exploration(S modelState, S currState) {

		this.intersectionClusterMap.get(currState).add(currState);

		for (T t : this.intersectionBA.getInTransitions(currState)) {
			S prev = this.intersectionBA.getTransitionSource(t);
			if (this.modelIntersectionStatesMap.get(modelState).contains(prev)) {
				this.intersectionClusterMap.get(prev).addAll(
						this.intersectionClusterMap.get(currState));
			}
		}
	}
}
