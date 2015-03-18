package it.polimi.contraintcomputation.subautomatafinder;

import it.polimi.automata.BA;
import it.polimi.automata.state.State;
import it.polimi.automata.transition.Transition;

import java.util.Collection;
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


	private Map<S, Collection<S>> forwardReachabilty;
	private Map<S, Collection<S>> backwardReachabilty;

	private boolean performed;
	
	/**
	 * contains the set of the reachable states
	 */
	private final Set<S> statesUnderAnalysis;

	/**
	 * creates a new reachability checker
	 * 
	 * @param ba
	 *            is the automaton to be considered
	 */
	public ReachabilityChecker(A ba, Set<S> statesUnderAnalysis) {
		Preconditions.checkNotNull(ba,
				"The automaton to be considered cannot be null");
		Preconditions.checkNotNull(statesUnderAnalysis,
				"The automaton to be considered cannot be null");
		
		this.ba = ba;
		this.statesUnderAnalysis=statesUnderAnalysis;
		performed=false;
		this.forwardReachabilty = new HashMap<S, Collection<S>>();
		this.backwardReachabilty=new HashMap<S, Collection<S>>();

	}


	/**
	 * returns for each state the set of the reachable states
	 * 
	 * @return for each state the set of the reachable states
	 */
	public Map<S, Collection<S>> forwardReachabilitycheck() {
		if(performed){
			return this.forwardReachabilty;
		}
		this.forwardReachabilty = new HashMap<S, Collection<S>>();
		for (S s : statesUnderAnalysis) {
			this.forwardReachabilty.put(s, ba.getSuccessors(s));
			this.forwardReachabilty.get(s).add(s);
			this.backwardReachabilty.put(s, ba.getPredecessors(s));
			this.backwardReachabilty.get(s).add(s);
		}

		for (S k : statesUnderAnalysis) {
			for (S i : statesUnderAnalysis) {
				for (S j : statesUnderAnalysis) {
					if (this.forwardReachabilty.get(i).contains(k)
							&& this.forwardReachabilty.get(k).contains(j)) {
						this.forwardReachabilty.get(i).add(j);
						this.backwardReachabilty.get(j).add(i);
					}
				}
			}
		}
		performed=true;
		return this.forwardReachabilty;
	}
	public Map<S, Collection<S>> backWardReachabilitycheck() {
		if(performed){
			return this.backwardReachabilty;
		}
		this.forwardReachabilitycheck();
		performed=true;
		return this.backwardReachabilty;
	}
}
