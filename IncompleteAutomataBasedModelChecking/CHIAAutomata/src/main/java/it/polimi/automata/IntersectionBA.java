package it.polimi.automata;

import it.polimi.automata.state.State;
import it.polimi.automata.transition.ClaimTransitionFactory;
import it.polimi.automata.transition.Transition;

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
public class IntersectionBA extends
		BA {

	/**
	 * contains the set of the mixed states
	 */
	private Set<State> mixedStates;
	
	
	/**
	 * creates a new intersection automaton
	 */
	public IntersectionBA() {
		super(new ClaimTransitionFactory());
		this.mixedStates = new HashSet<State>();
	}

	/**
	 * adds the mixed state s to the states of the {@link IBA} and to the set of
	 * the mixed state<br>
	 * if the state is already mixed no action is performed <br>
	 * if the state is a state of the BA but is not mixed, it is also added to
	 * the set of the mixed state
	 * 
	 * @param s
	 *            the state to be added in the {@link IBA}
	 * @throws NullPointerException
	 *             if the state s is null
	 */
	public void addMixedState(State s) {
		Preconditions.checkNotNull(s == null,
				"The state to be added cannot be null");

		this.mixedStates.add(s);
		if (!this.getStates().contains(s)) {
			this.addState(s);
		}
	}

	/**
	 * returns the set of the mixed states of the Intersection Buchi automaton
	 * 
	 * @return the set of the mixed states of the Intersection Buchi automaton
	 */
	public Set<State> getMixedStates() {
		return Collections.unmodifiableSet(mixedStates);
	}

	/**
	 * creates a copy of the Intersection Buchi Automaton
	 * 
	 * @return a copy of the Intersection Buchi Automaton
	 */
	@Override
	public Object clone() {
		IntersectionBA retBA = new IntersectionBA();
		for (IGraphProposition l : this.getPropositions()) {
			retBA.addProposition(l);
		}

		for (State s : this.getStates()) {
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
		for (Transition t : this.getTransitions()) {
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
	public void removeState(State state){
		super.removeState(state);
		if(this.mixedStates.contains(state)){
			this.mixedStates.remove(state);
		}
	}

	public Set<State> getRegularStates() {
		Set<State> regularStates=new HashSet<State>();
		regularStates.addAll(this.getStates());
		regularStates.removeAll(this.getMixedStates());
		return regularStates;
	}
}