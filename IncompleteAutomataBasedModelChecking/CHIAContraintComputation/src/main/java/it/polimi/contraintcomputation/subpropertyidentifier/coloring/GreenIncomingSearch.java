package it.polimi.contraintcomputation.subpropertyidentifier.coloring;

import java.util.HashSet;
import java.util.Set;

import it.polimi.automata.state.State;
import it.polimi.automata.transition.Transition;
import it.polimi.constraints.Color;
import it.polimi.contraintcomputation.subpropertyidentifier.SubPropertiesIdentifier;

import com.google.common.base.Preconditions;

/**
 * The GreenIncomingSearch starts from the state s and searches for paths that
 * involve purely regular states an reach an incoming transition of a
 * sub-property.
 * 
 * @author Claudio1
 *
 */
public class GreenIncomingSearch {

	private SubPropertiesIdentifier sbpId;

	private Set<State> visited;

	public GreenIncomingSearch(SubPropertiesIdentifier sbpId) {
		Preconditions.checkNotNull(sbpId,
				"The subproperty identified cannot be null");
		this.sbpId = sbpId;
		this.visited = new HashSet<State>();
	}

	public void startSearch() {

		for (State s : sbpId.getIntersectionBA().getInitialStates()) {
			this.startGreenSearch(s);
		}
	}

	private void startGreenSearch(State s) {
		// Whenever a state s is visited by the algorithm it is hashed

		visited.add(s);
		// each outgoing transition of the state s is analyzed
		for (Transition outTransition : this.sbpId.getIntersectionBA()
				.getOutTransitions(s)) {
			State destination = this.sbpId.getIntersectionBA()
					.getTransitionDestination(outTransition);
			// If the destination state is purely regular
			if (!this.sbpId.getIntersectionBA().getMixedStates()
					.contains(destination)) {
				// if the destination state has not already been hashed then the
				// GreenIncomingSearch is continued
				if (!this.visited.contains(destination)) {
					this.startGreenSearch(destination);
				}
			} else {
				// If this is not the case, it means that the state destination
				// is mixed, thus outTransition is an incoming port and it is
				// marked as green
				this.sbpId.getInPort(outTransition).setColor(Color.GREEN);
			}
		}
	}
}
