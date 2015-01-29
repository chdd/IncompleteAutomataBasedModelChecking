package it.polimi.automata.impl;

import it.polimi.automata.BA;
import it.polimi.automata.IntersectionBA;
import it.polimi.automata.labeling.Label;
import it.polimi.automata.state.State;
import it.polimi.automata.transition.Transition;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

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
 *      The label of the transition must implement the label interface and
 *      depending on whether the automaton represents the model or the claim it
 *      is a set of proposition or a propositional logic formula
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
 * @param <L>
 *            is the type of the label of the transitions depending on whether
 *            the automaton represents the model or the claim it is a set of
 *            proposition or a propositional logic formula {@link Label}
 */
public class IntBAImpl<L extends Label, S extends State, T extends Transition<L>>
		extends BAImpl<L, S, T> implements
		IntersectionBA<L, S, T> {

	/**
	 * contains the set of the mixed states
	 */
	private Set<S> mixedStates;

	/**
	 * creates a new intersection automaton
	 */
	protected IntBAImpl() {
		super();
		this.mixedStates = new HashSet<S>();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addMixedState(S s) {
		if (s == null) {
			throw new NullPointerException(
					"The state to be added cannot be null");
		}
		this.mixedStates.add(s);
		this.addState(s);
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
		IntersectionBA<L, S, T> retBA = new IntBAFactoryImpl<L, S, T>()
				.create();
		for(L l: this.getAlphabet()){
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
	
	public String toString(){
		String ret=super.toString();
		ret=ret+"MIXED STATES: "+this.mixedStates+"\n";
		
		return ret;
	}
}