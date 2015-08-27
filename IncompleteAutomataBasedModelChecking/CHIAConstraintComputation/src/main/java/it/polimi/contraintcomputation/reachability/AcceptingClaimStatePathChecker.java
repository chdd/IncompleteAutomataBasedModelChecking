package it.polimi.contraintcomputation.reachability;

import it.polimi.automata.BA;
import it.polimi.automata.IntersectionBA;
import it.polimi.automata.state.State;
import it.polimi.checker.intersection.IntersectionBuilder;

import java.util.HashSet;
import java.util.Set;

public class AcceptingClaimStatePathChecker {

	private final StatePresencePathChecker statePresencePathChecker;

	public AcceptingClaimStatePathChecker(IntersectionBA ba, Set<State> states,
			IntersectionBuilder intersectionBuilder) {

		Set<State> interestingStates = new HashSet<State>();
		BA claim = intersectionBuilder.getClaim();
		for (State state : states) {
			State claimState = intersectionBuilder.getClaimState(state);
			if (claim.getAcceptStates().contains(claimState)) {
				interestingStates.add(state);
			}
		}
		statePresencePathChecker = new StatePresencePathChecker(ba, states,
				interestingStates);
	}

	public Boolean perform(State source, State destination) {
		return statePresencePathChecker.perform(source, destination);
	}
}
