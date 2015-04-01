package it.polimi.contraintcomputation.portreachability;

import it.polimi.automata.BA;
import it.polimi.automata.state.State;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
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
	 * creates a new reachability checker
	 * 
	 * @param ba
	 *            is the automaton to be considered
	 */
	public ReachabilityChecker(A ba) {
		Preconditions.checkNotNull(ba,
				"The automaton to be considered cannot be null");

		this.ba = ba;
		performed = false;
		this.forwardReachabilty = new HashMap<State, Set<State>>();
		this.backwardReachabilty = new HashMap<State, Set<State>>();

	}

	public void computeReachabilityRelation(Set<State> statesUnderAnalysis) {
		Preconditions.checkNotNull(statesUnderAnalysis,
				"The automaton to be considered cannot be null");
		Preconditions.checkArgument(
				this.ba.getStates().containsAll(statesUnderAnalysis),
				"Not all the states are contained in the automaton");

		for (State s : statesUnderAnalysis) {
			Set<State> nextStates=new HashSet<State>(ba.getSuccessors(s));
			nextStates.retainAll(statesUnderAnalysis);
			this.forwardReachabilty.put(s, nextStates);
			this.forwardReachabilty.get(s).add(s);
			Set<State> prevStates=new HashSet<State>(ba.getPredecessors(s));
			prevStates.retainAll(statesUnderAnalysis);
			this.backwardReachabilty.put(s, prevStates);
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
	}

	/**
	 * returns for each state the set of the reachable states
	 * 
	 * @return for each state the set of the reachable states
	 */
	public Map<State, Set<State>> getForwardReachability() {
		Preconditions
				.checkState(
						performed,
						"It is necessary to compute the reachability relation before getting the forward relation");

		return Collections.unmodifiableMap(this.forwardReachabilty);
	}

	public Map<State, Set<State>> getBackwardReachability() {
		Preconditions
				.checkState(
						performed,
						"It is necessary to compute the reachability relation before getting the backward relation");

		return Collections.unmodifiableMap(this.backwardReachabilty);
	}
}
