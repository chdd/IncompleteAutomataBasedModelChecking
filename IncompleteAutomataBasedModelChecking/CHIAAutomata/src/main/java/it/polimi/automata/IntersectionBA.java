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
 * The IntersectionBA class is used to represent the intersection automaton. The
 * intersection automaton extends a BA. It contains the set of the mixed states
 * which are obtained by considering a black box state of the model and a
 * state of the claim and the set of the constrained transitions, i.e., the set
 * of transitions of the intersection automaton which are generated by
 * performing a transition in the claim when the model is in a black box state.
 * 
 * @author claudiomenghi
 */
public class IntersectionBA extends BA {

	/**
	 * contains the set of the mixed states
	 */
	private final Set<State> mixedStates;

	/**
	 * constrained transitions
	 */
	private final Set<Transition> constrainedTransitions;

	/**
	 * creates a new intersection automaton
	 */
	public IntersectionBA() {
		super(new ClaimTransitionFactory());
		this.mixedStates = new HashSet<State>();
		this.constrainedTransitions = new HashSet<Transition>();
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

		Preconditions.checkNotNull(s, "The state to be added cannot be null");

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
	 * adds a constraint transition to the intersection automaton
	 * 
	 * @see {@link #addTransition(State, State, Transition)}
	 * @param source
	 *            is the source of the transition
	 * @param destination
	 *            is the destination of the transition
	 * @param transition
	 *            is the transition to be added
	 */
	public void addConstrainedTransition(State source, State destination,
			Transition transition) {
		super.addTransition(source, destination, transition);
		this.constrainedTransitions.add(transition);
	}

	/**
	 * removes the transition from the intersection automaton. If the transition
	 * is constrained it is also removed from the set of the constrained
	 * transitions
	 * 
	 * @param transition
	 *            is the transition to be removed
	 * @throws NullPointerException
	 *             if the transition is null
	 */
	public void removeTransition(Transition transition) {
		super.removeTransition(transition);
		if (this.constrainedTransitions.contains(transition)) {
			this.constrainedTransitions.remove(transition);
		}
	}

	/**
	 * removes the state from the intersection automaton. If the state is mixed
	 * is also removed from the set of the mixed states
	 * 
	 * @param state
	 *            the state to be removed
	 * @throws NullPointerException
	 *             if the state is null
	 */
	public void removeState(State state) {
		super.removeState(state);
		if (this.mixedStates.contains(state)) {
			this.mixedStates.remove(state);
		}
	}

	public Set<State> getPurelyRegularStates() {
		Set<State> regularStates = new HashSet<State>();
		regularStates.addAll(this.getStates());
		regularStates.removeAll(this.getMixedStates());
		return Collections.unmodifiableSet(regularStates);
	}

	

	/**
	 * creates a copy of the Intersection Buchi Automaton
	 * 
	 * @return a copy of the Intersection Buchi Automaton
	 */
	@Override
	public IntersectionBA clone() {
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

	/**
	 * returns an abstraction of the intersection automaton that contains only
	 * the purely regular states and the transitions between them
	 * 
	 * @return an abstraction of the intersection automaton that contains only
	 *         the purely regular states and the transitions between them
	 */
	public IntersectionBA getAbstraction(Set<State> abstractionStates) {
		IntersectionBA ret = new IntersectionBA();

		ret.addPropositions(this.getPropositions());
		for (State s : abstractionStates) {
			ret.addState(s);
			if (this.getAcceptStates().contains(s)) {
				ret.addAcceptState(s);
			}
			if (this.getInitialStates().contains(s)) {
				ret.addInitialState(s);
			}
			if (this.getMixedStates().contains(s)) {
				ret.addMixedState(s);
			}
		}

		for (Transition t : this.getTransitions()) {
			if (abstractionStates.contains(this.getTransitionSource(t))
					&& abstractionStates.contains(this
							.getTransitionDestination(t))) {
				ret.addTransition(this.getTransitionSource(t),
						this.getTransitionDestination(t), t);
			}
		}
		return ret;
	}

	/**
	 * @return the constrainedTransitions
	 */
	public Set<Transition> getConstrainedTransitions() {
		return Collections.unmodifiableSet(constrainedTransitions);
	}
	

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		String ret = super.toString();
		ret = ret + "MIXED STATES: " + this.mixedStates + "\n";

		return ret;
	}

}