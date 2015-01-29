package it.polimi.contraintcomputation.component;

import it.polimi.automata.labeling.Label;
import it.polimi.automata.state.State;
import it.polimi.automata.transition.Transition;

public class ComponentFactory<L extends Label, S extends State, T extends Transition<L>> {

	/**
	 * contains the counter whose value is associated to the next id of the
	 * state to be created
	 */
	private static int stateCount = 0;

	public Component<L,S,T> create(String name,  S modelState, boolean transparent) {

		 Component<L,S,T> s = new Component<L,S,T>(name, ComponentFactory.stateCount, modelState, transparent);
		 ComponentFactory.stateCount = ComponentFactory.stateCount + 1;
		return s;
	}
}
