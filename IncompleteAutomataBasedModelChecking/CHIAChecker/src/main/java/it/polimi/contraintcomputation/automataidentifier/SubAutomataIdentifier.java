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
public class SubAutomataIdentifier<LABEL extends Label, STATE extends State, TRANSITION extends Transition<LABEL>> {

	/**
	 * contains a map that maps each state of the model with a set of states of
	 * the intersection automaton
	 */
	private Map<STATE, Set<STATE>> modelIntersectionStatesMap;

	/**
	 * contains a map that maps each state of the intersection automaton with
	 * the cluster it belongs with
	 */
	private Map<STATE, Set<STATE>> intersectionClusterMap;

	/**
	 * contains the intersection automaton
	 */
	private IntersectionBA<LABEL, STATE, TRANSITION> intersectionBA;

	/**
	 * contains the map that connect each state of the model with the
	 * corresponding clusters
	 */
	private Map<STATE, Set<Set<STATE>>> returnSubAutomata;

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
			IntersectionBA<LABEL, STATE, TRANSITION> intersectionBA,
			Map<STATE, Set<STATE>> map) {

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
		this.intersectionClusterMap = new HashMap<STATE, Set<STATE>>();
		this.returnSubAutomata = new HashMap<STATE, Set<Set<STATE>>>();
	}

	/**
	 * the sub-automata of the automaton that refer to the transparent states of
	 * M.
	 * 
	 * @return the sub-automata of the automaton that refer to the transparent
	 *         states of M.
	 */
	public Map<STATE, Set<Set<STATE>>> getSubAutomata() {

		for (STATE intersectionState : this.intersectionBA.getStates()) {
			this.intersectionClusterMap.put(intersectionState,
					new HashSet<STATE>());
		}
		for (STATE modelState : modelIntersectionStatesMap.keySet()) {
			Set<STATE> abstraction = new HashSet<STATE>(
					modelIntersectionStatesMap.get(modelState));
			while (!abstraction.isEmpty()) {
				STATE currState = abstraction.iterator().next();
				this.exploration(modelState, currState);
				abstraction.remove(currState);
			}
			
			Set<Set<STATE>> clusters=new HashSet<Set<STATE>>();
			for(STATE s: modelIntersectionStatesMap.get(modelState)){
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
	private void exploration(STATE modelState, STATE currState) {

		this.intersectionClusterMap.get(currState).add(currState);

		for (TRANSITION t : this.intersectionBA.getInTransitions(currState)) {
			STATE prev = this.intersectionBA.getTransitionSource(t);
			if (this.modelIntersectionStatesMap.get(modelState).contains(prev)) {
				this.intersectionClusterMap.get(prev).addAll(
						this.intersectionClusterMap.get(currState));
			}
		}
	}
}
