package it.polimi.contraintcomputation.statereachability;

import it.polimi.automata.BA;
import it.polimi.automata.state.State;

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
 * @param <A>
 *            is the type of the automaton to be considered
 */
public class ReachabilityChecker<A extends BA> {

	/**
	 * is the automaton to be considered
	 */
	private final A ba;

	/**
	 * contains the forward reachability between the states of the automaton
	 */
	private final Map<State, Set<State>> forwardReachabilty;
	/**
	 * contains the backward reachability between the states of the automaton
	 */
	private final Map<State, Set<State>> backwardReachabilty;

	/**
	 * is true if the reachability relation has been already computed
	 */
	private boolean performed;

	/**
	 * contains the set of the reachable states
	 */
	private final Set<State> statesUnderAnalysis;

	/**
	 * creates a new reachability checker
	 * 
	 * @param ba
	 *            is the automaton to be considered
	 */
	public ReachabilityChecker(A ba, Set<State> statesUnderAnalysis) {
		Preconditions.checkNotNull(ba,
				"The automaton to be considered cannot be null");
		Preconditions.checkNotNull(statesUnderAnalysis,
				"The automaton to be considered cannot be null");

		this.ba = ba;
		this.statesUnderAnalysis = statesUnderAnalysis;
		performed = false;
		this.forwardReachabilty = new HashMap<State, Set<State>>();
		this.backwardReachabilty = new HashMap<State, Set<State>>();

	}

	/**
	 * returns for each state the set of the reachable states
	 * 
	 * @return for each state the set of the reachable states
	 */
	public Map<State, Set<State>> forwardReachabilitycheck() {
		if (performed) {
			return this.forwardReachabilty;
		}
		for (State s : statesUnderAnalysis) {
			this.forwardReachabilty.put(s, ba.getSuccessors(s));
			this.forwardReachabilty.get(s).add(s);
			this.backwardReachabilty.put(s, ba.getPredecessors(s));
			this.backwardReachabilty.get(s).add(s);
		}

		for (State k : statesUnderAnalysis) {
			for (State i : statesUnderAnalysis) {
				for (State j : statesUnderAnalysis) {
					if (this.forwardReachabilty.get(i).contains(k)
							&& this.forwardReachabilty.get(k).contains(j)) {
						this.forwardReachabilty.get(i).add(j);
						this.backwardReachabilty.get(j).add(i);
					}
				}
			}
		}
		performed = true;
		return this.forwardReachabilty;
	}

	public Map<State, Set<State>> backWardReachabilitycheck() {
		if (performed) {
			return this.backwardReachabilty;
		}
		this.forwardReachabilitycheck();
		performed = true;
		return this.backwardReachabilty;
	}
}
