package it.polimi.contraintcomputation.reachability;

import it.polimi.automata.IntersectionBA;
import it.polimi.automata.state.State;
import it.polimi.checker.intersection.IntersectionBuilder;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.google.common.base.Preconditions;

/**
 * checks whether there exists a path from the source to the destination state
 * which contains an accepting state
 * 
 * @author Claudio
 *
 */
public class AcceptingStatePathChecker {

	/**
	 * the Buchi automaton to be considered
	 */
	private final IntersectionBA intersection;

	private boolean performed;

	/**
	 * the set of the states that can be traversed during the reachability
	 * search
	 */
	private final Set<State> states;
	/**
	 * This map specifies for each accepting state the set of the states that
	 * are reachable through a backward search
	 */
	private final Map<State, Set<State>> acceptingStateBackwardReachability;

	private final Map<State, Map<State, Boolean>> reachability;

	private final Map<State, State> changeMap;
	/**
	 * This map specifies for each accepting state the set of the states that
	 * are reachable through a forward search
	 */
	private final Map<State, Set<State>> acceptingStateForwardReachability;

	/**
	 * creates a new checker that aims to detect whether there exists a path
	 * from the source to the destination that contains an accepting state
	 * 
	 * @param intersection
	 *            the BA under analysis
	 * @param states
	 *            the set of the states that can be traversed in the
	 *            reachability search
	 * @throws NullPointerException
	 *             if the BA under analysis is null
	 */
	public AcceptingStatePathChecker(IntersectionBuilder intersectionBuilder, IntersectionBA intersection, Set<State> states) {
		Preconditions.checkNotNull(intersection, "The BA under analysis cannot be null");
		Preconditions
				.checkArgument(
						intersection.getStates().containsAll(states),
						"The set of the states that can be traversed in the reachability search must be contained into the set of the states of the BA");
		this.intersection = intersection;
		this.acceptingStateBackwardReachability = new HashMap<State, Set<State>>();
		this.acceptingStateForwardReachability = new HashMap<State, Set<State>>();
		this.states = Collections.unmodifiableSet(states);
		this.reachability = new HashMap<State, Map<State, Boolean>>();
		this.changeMap=new HashMap<State, State>();
		Set<State> acceptingStates = new HashSet<State>(
				intersection.getAcceptStates());
		acceptingStates.retainAll(states);
		for(State acceptingState: acceptingStates){
			for(State prevState: intersection.getPredecessors(acceptingState)){
				if(intersectionBuilder.getNumber(prevState)==1){
					this.changeMap.put(prevState, acceptingState);
				}
			}
		}
		
		performed = false;
	}

	/**
	 * checks whether there exists a path from the source to the destination
	 * state that contains an accepting state
	 * 
	 * @param source
	 *            is the source under analysis
	 * @param destination
	 *            is the destination under analysis
	 * @return true if there exists a path from the source to the destination
	 *         that contains an accepting state;
	 * @throws NullPointerException
	 *             if the source or the destination is null;
	 * @throws IllegalArgumentException
	 *             if the source or the destination is not a state of the ba
	 * 
	 */
	public Boolean perform(State source, State destination) {
		Preconditions.checkNotNull(source, "The source state cannot be null");
		Preconditions.checkNotNull(destination,
				"The destination state cannot be null");
		Preconditions.checkArgument(this.intersection.getStates().contains(source),
				"The source state must be a state of the BA");
		Preconditions.checkArgument(this.intersection.getStates().contains(destination),
				"The destination state must be a state of the BA");
		Preconditions
				.checkArgument(
						this.states.contains(source),
						"The source state must be contained into the set of the states that can be traversed");
		Preconditions
				.checkArgument(
						this.states.contains(destination),
						"The destination state must be contained into the set of the states that can be traversed");
		if (!this.performed) {

			for (Entry<State,State> entry : this.changeMap.entrySet()) {

				
				this.acceptingStateBackwardReachability.put(entry.getKey(), new HashSet<State>());
				this.acceptingStateForwardReachability.put(entry.getValue(), new HashSet<State>());
				
				backwardCheck(entry.getKey());
				forwardCheck(entry.getValue());
			}
			performed = true;
		}
		if (this.hasBeenAlreadyChecked(source, destination)) {
			return reachability.get(source).get(destination);
		} else {
			for (Entry<State,State> entry : this.changeMap.entrySet()) {
	
				if (this.acceptingStateBackwardReachability.containsKey(entry.getKey())
						&& 
						this.acceptingStateBackwardReachability.get(entry.getKey())
								.contains(source) 
								&&
								this.acceptingStateForwardReachability.containsKey(entry.getValue()) &&
								this.acceptingStateForwardReachability.get(entry.getValue()).contains(destination)) {
					if(reachability.containsKey(source)){
						reachability.get(source).put(destination, true);
						return true;
					}
					else{
						Map<State, Boolean> map=new HashMap<State, Boolean>();
						map.put(destination, true);
						reachability.put(source, map);
						return true;
					}
				}
			}
			if(reachability.containsKey(source)){
				reachability.get(source).put(destination, false);
				return false;
			}
			else{
				Map<State, Boolean> map=new HashMap<State, Boolean>();
				map.put(destination, false);
				reachability.put(source, map);
				return false;
			}
		}
	}

	/**
	 * backward search the states reachable for each accepting states
	 * 
	 * @return
	 */
	private void backwardCheck(State acceptingState) {
		
		this.acceptingStateBackwardReachability.get(acceptingState).add(acceptingState);
		Set<State> nextStates = new HashSet<State>();
		nextStates.add(acceptingState);
		Set<State> visitedStates = new HashSet<State>();
		while (nextStates.size() > 0) {
			State next = nextStates.iterator().next();
			nextStates.remove(next);
			visitedStates.add(next);
			for (State pred : this.intersection.getPredecessors(next)) {
				if (this.states.contains(pred) && !visitedStates.contains(pred)) {
					this.acceptingStateBackwardReachability.get(acceptingState)
							.add(pred);
					nextStates.add(pred);
				}
			}
		}

	}

	/**
	 * backward search the states reachable for each accepting states
	 * 
	 * @return
	 */
	private void forwardCheck(State acceptingState) {
		
		this.acceptingStateForwardReachability.get(acceptingState).add(acceptingState);
		Set<State> nextStates = new HashSet<State>();
		nextStates.add(acceptingState);
		Set<State> visitedStates = new HashSet<State>();
		while (nextStates.size() > 0) {
			State next = nextStates.iterator().next();
			nextStates.remove(next);
			visitedStates.add(next);
			for (State succ : this.intersection.getSuccessors(next)) {
				if (this.states.contains(succ) && !visitedStates.contains(succ)) {
					this.acceptingStateForwardReachability.get(acceptingState)
							.add(succ);
					nextStates.add(succ);
				}
			}
		}

	}

	private boolean hasBeenAlreadyChecked(State source, State destination) {
		if (this.reachability.containsKey(source)) {
			if (this.reachability.get(source).containsKey(destination)) {
				return true;
			}
		}
		return false;
	}
}
