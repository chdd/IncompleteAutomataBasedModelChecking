package it.polimi.contraintcomputation.portreachability;

import it.polimi.automata.BA;
import it.polimi.automata.state.State;
import it.polimi.checker.intersection.IntersectionBuilder;

import java.util.HashSet;
import java.util.Set;

public class AcceptingClaimStatePathChecker {

	private final StatePresencePathChecker statePresencePathChecker;
	
	public AcceptingClaimStatePathChecker(BA ba, Set<State> states, IntersectionBuilder
			intersectionBuilder){
		
		Set<State> claimAcceptStatesIntersection=new HashSet<State>();
		for(State s: intersectionBuilder.getClaim().getAcceptStates()){
			
			for(State intersectionState: intersectionBuilder.getClaimIntersectionStates(s)){
				if(!intersectionBuilder.getIntersectionAutomaton().getAcceptStates().contains(intersectionState)){
					claimAcceptStatesIntersection.add(intersectionState);
				}
			}
		}
		statePresencePathChecker=new StatePresencePathChecker(ba, states, claimAcceptStatesIntersection);
	}
	
	public Boolean perform(State source, State destination) {
		return statePresencePathChecker.perform(source, destination);
	}
}
