package it.polimi.automata.impl;

import it.polimi.automata.BA;
import it.polimi.automata.IntersectionBA;
import it.polimi.automata.state.State;
import it.polimi.automata.transition.Transition;
import it.polimi.automata.transition.TransitionFactory;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import rwth.i2.ltl2ba4j.model.IGraphProposition;

import com.google.common.base.Preconditions;

/**
 * <p>
 * Represents the Intersection Buchi Automaton which extends a Buchi automaton
 * with mixed states, implements the {@link IntersectionBA} interface <br>
 * <br>
 * 
 * @see BA The state of the automaton must must implement the {@link State}
 *      interface <br>
 *      The transition of the automaton must must implement the
 *      {@link Transition} interface <br>
 *      </p>
 * 
 * @author claudiomenghi
 * @param <S>
 *            is the type of the state of the Intersection Buchi Automaton. The
 *            type of the states of the automaton must implement the interface
 *            {@link State}
 * @param <T>
 *            is the type of the transition of the Intersection Buchi Automaton.
 *            The type of the transitions of the automaton must implement the
 *            interface {@link Transition}
 */
public class IntBAImpl<S extends State, T extends Transition> extends
		BAImpl<S, T> implements IntersectionBA<S, T> {

	/**
	 * contains the set of the mixed states
	 */
	private Set<S> mixedStates;
	
	
	/**
	 * creates a new intersection automaton
	 */
	public IntBAImpl(TransitionFactory<S, T> transitionFactory) {
		super(transitionFactory);
		this.mixedStates = new HashSet<S>();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addMixedState(S s) {
		Preconditions.checkNotNull(s == null,
				"The state to be added cannot be null");

		this.mixedStates.add(s);
		if (!this.getStates().contains(s)) {
			this.addState(s);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Set<S> getMixedStates() {
		return Collections.unmodifiableSet(mixedStates);
	}

	/**
	 * returns a copy of the intersection automaton
	 * 
	 * @return a copy of the intersection automaton
	 */
	@Override
	public Object clone() {
		IntersectionBA<S, T> retBA = new IntBAImpl<S, T>((TransitionFactory<S,T>)
				this.automataGraph.getEdgeFactory());
		for (IGraphProposition l : this.getAlphabet()) {
			retBA.addCharacter(l);
		}

		for (S s : this.getStates()) {
			retBA.addState(s);
			if (this.getAcceptStates().contains(s)) {
				retBA.addAcceptState(s);
			}
			if (this.getInitialStates().contains(s)) {
				retBA.addInitialState(s);
			}
			if (this.getMixedStates().contains(s)) {
				retBA.addMixedState(s);
			}
		}
		for (T t : this.getTransitions()) {
			retBA.addTransition(this.getTransitionSource(t),
					this.getTransitionDestination(t), t);
		}

		return retBA;
	}

	public String toString() {
		String ret = super.toString();
		ret = ret + "MIXED STATES: " + this.mixedStates + "\n";

		return ret;
	}
	public void removeState(S state){
		super.removeState(state);
		if(this.mixedStates.contains(state)){
			this.mixedStates.remove(state);
		}
	}

	@Override
	public Set<S> getRegularStates() {
		Set<S> regularStates=new HashSet<S>();
		regularStates.addAll(this.getStates());
		regularStates.removeAll(this.getMixedStates());
		return regularStates;
	}
}