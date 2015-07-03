package it.polimi.automata;

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
 * The \texttt{IBA} class contains the class which describes an Incomplete Buchi
 * Automaton. The \texttt{IBA} class extends \texttt{BA} by storing the set of
 * the \emph{transparent} states. <br>
 * 
 * @author claudiomenghi
 */
public class IBA extends BA {

	/**
	 * contains the set of the transparent states of the automaton
	 */
	private Set<State> transparentStates;

	/**
	 * creates a new incomplete Buchi automaton
	 */
	public IBA(TransitionFactory<State, Transition> transitionFactory) {
		super(transitionFactory);
		this.transparentStates = new HashSet<State>();
	}

	/**
	 * check if the state is transparent
	 * 
	 * @param s
	 *            is the state to be checked if transparent
	 * @return true if the state s is transparent, false otherwise
	 * @throws NullPointerException
	 *             if the state s is null
	 * @throws IllegalArgumentException
	 *             if the state is not contained into the set of the states of
	 *             the automaton
	 */
	public boolean isTransparent(State s) {
		Preconditions.checkNotNull(s, "The state to be added cannot be null");
		Preconditions
				.checkArgument(this.getStates().contains(s),
						"The state is not contained into the set of the states of the IBA");

		return this.transparentStates.contains(s);
	}

	/**
	 * returns the set of the transparent states of the Incomplete Buchi
	 * Automaton
	 * 
	 * @return the set of the transparent states of the Incomplete Buchi
	 *         Automaton (if no transparent states are present an empty set is
	 *         returned)
	 */
	public Set<State> getTransparentStates() {
		return Collections.unmodifiableSet(this.transparentStates);
	}

	/**
	 * returns the set of the transparent states of the Incomplete Buchi
	 * Automaton
	 * 
	 * @return the set of the transparent states of the Incomplete Buchi
	 *         Automaton
	 */
	public Set<State> getRegularStates() {
		Set<State> states = new HashSet<State>();
		states.addAll(this.getStates());
		states.removeAll(this.getTransparentStates());
		return states;
	}

	/**
	 * adds the transparent state s to the states of the {@link IBA} and to the
	 * set of the transparent state<br>
	 * if the state is already transparent no action is performed <br>
	 * if the state is a state of the BA but is not transparent, it is also
	 * added to the set of the transparent state
	 * 
	 * @param s
	 *            the state to be added in the {@link IBA}
	 * @throws NullPointerException
	 *             if the state s is null
	 */
	public void addTransparentState(State s) {
		Preconditions.checkNotNull(s, "The state to be added cannot be null");

		this.transparentStates.add(s);
		if (!this.getStates().contains(s)) {
			this.addState(s);
		}
	}

	/**
	 * creates a copy of the Incomplete Buchi Automaton
	 * 
	 * @return a copy of the Incomplete Buchi Automaton
	 */
	@Override
	public IBA clone() {
		IBA clone = new IBA(
				(TransitionFactory<State, Transition>) this.automataGraph
						.getEdgeFactory());
		for (IGraphProposition l : this.getPropositions()) {
			clone.addProposition(l);
		}
		for (State s : this.getStates()) {
			clone.addState(s);
		}
		for (State s : this.getAcceptStates()) {
			clone.addAcceptState(s);
		}
		for (State s : this.getInitialStates()) {
			clone.addInitialState(s);
		}
		for (State s : this.getTransparentStates()) {
			clone.addTransparentState(s);
		}
		for (Transition t : this.getTransitions()) {
			clone.addTransition(this.getTransitionSource(t),
					this.getTransitionDestination(t), t);
		}

		return clone;
	}

	public void removeState(State state) {
		super.removeState(state);
		if (this.transparentStates.contains(state)) {
			this.transparentStates.remove(state);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		String ret = "";

		ret = "ALPHABET: " + this.getPropositions() + "\n";
		ret = ret + "STATES: " + this.automataGraph.vertexSet() + "\n";
		ret = ret + "INITIAL STATES: " + this.getInitialStates() + "\n";
		ret = ret + "ACCEPTING STATES: " + this.getAcceptStates() + "\n";
		ret = ret + "TRANSPARENT STATES: " + this.getTransparentStates() + "\n";
		ret = ret + "TRANSITIONS\n";
		for (State s : this.automataGraph.vertexSet()) {
			ret = ret + "state " + s + " ->\n";
			for (Transition outEdge : this.automataGraph.outgoingEdgesOf(s)) {
				ret = ret + "\t \t" + outEdge + "\t"
						+ this.getTransitionDestination(outEdge);
			}
			ret = ret + "\n";

		}
		return ret;
	}
}
