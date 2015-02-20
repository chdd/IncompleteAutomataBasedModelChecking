package it.polimi.contraintcomputation.component;

import it.polimi.automata.state.State;
import it.polimi.automata.transition.Transition;

public class ComponentFactory< S extends State, T extends Transition> {

	/**
	 * contains the counter whose value is associated to the next id of the
	 * state to be created
	 */
	private static int stateCount = 0;

	public Component<S,T> create(String name,  S modelState, boolean transparent) {

		 Component<S,T> s = new Component<S,T>(name, ComponentFactory.stateCount, modelState, transparent);
		 ComponentFactory.stateCount = ComponentFactory.stateCount + 1;
		return s;
	}
}
