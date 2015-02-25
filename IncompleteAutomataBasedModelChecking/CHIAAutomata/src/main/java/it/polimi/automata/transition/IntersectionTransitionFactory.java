package it.polimi.automata.transition;

import java.util.Set;

import rwth.i2.ltl2ba4j.model.IGraphProposition;
import it.polimi.automata.state.State;

public interface IntersectionTransitionFactory<S extends State, T extends Transition> extends TransitionFactory<S, T> {

	public T create(int id, Set<IGraphProposition> labels, S state);
	public T create(Set<IGraphProposition> labels, S state);
}
