package it.polimi.contraintcomputation.reachability;

import it.polimi.automata.BA;
import it.polimi.automata.IBA;
import it.polimi.automata.state.State;
import it.polimi.checker.intersection.IntersectionBuilder;

import java.util.HashSet;
import java.util.Set;

public class AcceptingModelStatePathChecker {

	private final StatePresencePathChecker statePresencePathChecker;

	public AcceptingModelStatePathChecker(BA ba, Set<State> states,
			IntersectionBuilder intersectionBuilder) {

		Set<State> interestingStates = new HashSet<State>();
		IBA model = intersectionBuilder.getModel();
		for (State state : states) {
			State modelState = intersectionBuilder.getModelState(state);
			if (model.getAcceptStates().contains(modelState)) {
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
