package it.polimi.refinementchecker;

import it.polimi.automata.BA;
import it.polimi.automata.state.State;
import it.polimi.automata.transition.Transition;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.google.common.base.Preconditions;

/**
 * ReachabilityChecker is the checker which allows to compute a data structure
 * which can then answer reachability queries on any pair of vertices in as low
 * as O(1) time. The reachabilty checker exploits the The Floyd–Warshall
 * algorithm which computes the transitive closure of any directed graph, which
 * gives rise to the reachability relation as in the definition, above. The
 * algorithm requires O(|V|^3) time and O(|V|^2) space in the worst case.
 * 
 * @author claudiomenghi
 *
 * @param <S>
 *            is the type of the states of the automaton
 * @param <T>
 *            is the type of the transitions of the automaton
 */
public class ReachabilityChecker<S extends State, T extends Transition, A extends BA<S, T>> {

	/**
	 * is the automaton to be considered
	 */
	private final A ba;

	/**
	 * contains the set of the reachable states
	 */
	private Map<S, Set<S>> reachableStates;

	/**
	 * creates a new reachability checker
	 * 
	 * @param ba
	 *            is the automaton to be considered
	 */
	public ReachabilityChecker(A ba) {
		Preconditions.checkNotNull(ba,
				"The automaton to be considered cannot be null");
		this.ba = ba;
		this.reachableStates = new HashMap<S, Set<S>>();

	}

	/**
	 * returns for each state the set of the reachable states
	 * 
	 * @return for each state the set of the reachable states
	 */
	public Map<S, Set<S>> check() {
		this.reachableStates = new HashMap<S, Set<S>>();
		for (S s : ba.getStates()) {
			this.reachableStates.put(s, ba.getSuccessors(s));
		}

		for (S k : this.ba.getStates()) {
			for (S i : this.ba.getStates()) {
				for (S j : this.ba.getStates()) {
					if (this.reachableStates.get(i).contains(k)
							&& this.reachableStates.get(k).contains(j)) {
						this.reachableStates.get(i).add(j);
					}
				}
			}
		}
		return this.reachableStates;
	}
}
