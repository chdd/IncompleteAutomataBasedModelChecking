package it.polimi.constraints.impl;

import it.polimi.automata.BA;
import it.polimi.automata.state.State;
import it.polimi.automata.transition.Transition;
import it.polimi.automata.transition.TransitionFactory;
import it.polimi.constraints.Component;

public interface ComponentFactory<S extends State, T extends Transition, A extends BA<S,T>> {

	public Component<S, T, A> create(String name, S modelState,
			boolean transparent, TransitionFactory<S, T> transitionFactory);
}
