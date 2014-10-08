package it.polimi.model.automata.ba.transition;

import java.util.HashSet;
import java.util.Set;

import it.polimi.model.automata.ba.state.State;

public class ConstrainedTransitionFactory<S extends State, T extends ConstrainedTransition<S>> extends TransitionFactory<T> {

	public T create(Set<String> labels, State s) {
		
		T t=(T) new ConstrainedTransition(labels, s,  TransitionFactory.transitionCount);
		TransitionFactory.transitionCount++;
		return t;
	}
	@Override
	public T create() {
		
		T t=(T) new ConstrainedTransition(new HashSet<String>(), null, TransitionFactory.transitionCount);
		TransitionFactory.transitionCount++;
		return t;
	}
	
	public T create(Set<String> labels) {
		
		T t=(T) new ConstrainedTransition(labels, null, TransitionFactory.transitionCount);
		TransitionFactory.transitionCount++;
		return t;
	}
}

