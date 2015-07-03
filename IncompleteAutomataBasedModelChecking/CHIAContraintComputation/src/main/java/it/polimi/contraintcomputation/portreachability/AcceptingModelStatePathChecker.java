package it.polimi.contraintcomputation.portreachability;

import it.polimi.automata.BA;
import it.polimi.automata.state.State;
import it.polimi.checker.intersection.IntersectionBuilder;

import java.util.HashSet;
import java.util.Set;

public class AcceptingModelStatePathChecker {

	private final StatePresencePathChecker statePresencePathChecker;
	
	public AcceptingModelStatePathChecker(BA ba, Set<State> states, IntersectionBuilder
			intersectionBuilder){
		
		Set<State> modelAcceptStatesIntersection=new HashSet<State>();
		for(State s: intersectionBuilder.getModel().getAcceptStates()){
			
			for(State intersectionState: intersectionBuilder.getModelIntersectionStates(s)){
				if(!intersectionBuilder.getIntersectionAutomaton().getAcceptStates().contains(intersectionState)){
					modelAcceptStatesIntersection.add(intersectionState);
				}
			}
		}
		statePresencePathChecker=new StatePresencePathChecker(ba, states, modelAcceptStatesIntersection);
	}
	
	public Boolean perform(State source, State destination) {
		return statePresencePathChecker.perform(source, destination);
	}
}
