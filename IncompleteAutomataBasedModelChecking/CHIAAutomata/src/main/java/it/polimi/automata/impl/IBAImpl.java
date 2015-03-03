package it.polimi.automata.impl;

import it.polimi.automata.BA;
import it.polimi.automata.IBA;
import it.polimi.automata.state.State;
import it.polimi.automata.transition.Transition;
import it.polimi.automata.transition.TransitionFactory;

import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import rwth.i2.ltl2ba4j.model.IGraphProposition;

import com.google.common.base.Preconditions;

/**
 * <p>
 * Represents an Incomplete Buchi Automaton which extends a Buchi automaton with
 * transparent states, implements the {@link IBA} interface <br>
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
 *            is the type of the state of the Buchi Automaton. The type of the
 *            states of the automaton must implement the interface {@link State}
 * @param <T>
 *            is the type of the transition of the Buchi Automaton. The typer of
 *            the transitions of the automaton must implement the interface
 *            {@link Transition}
 */
public class IBAImpl<S extends State, T extends Transition> extends
		BAImpl<S, T> implements IBA<S, T> {

	/**
	 * contains the set of the transparent states of the automaton
	 */
	private Set<S> transparentStates;

	/**
	 * creates a new incomplete Buchi automaton
	 */
	public IBAImpl(TransitionFactory<S, T> transitionFactory) {
		super(transitionFactory);
		this.transparentStates = new HashSet<S>();
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean isTransparent(S s) {
		Preconditions.checkNotNull(s, "The state to be added cannot be null");
		Preconditions
				.checkArgument(this.getStates().contains(s),
						"The state is not contained into the set of the states of the IBA");

		return this.transparentStates.contains(s);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Set<S> getTransparentStates() {
		return Collections.unmodifiableSet(this.transparentStates);
	}

	/**
	 * {@inheritDoc}
	 */
	public void addTransparentState(S s) {
		Preconditions.checkNotNull(s, "The state to be added cannot be null");

		this.transparentStates.add(s);
		if (!this.getStates().contains(s)) {
			this.addState(s);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public IBAImpl<S, T> clone() {
		IBAImpl<S, T> clone = new IBAImpl<S, T>(
				(TransitionFactory<S, T>) this.automataGraph.getEdgeFactory());
		for (IGraphProposition l : this.getAlphabet()) {
			clone.addCharacter(l);
		}
		for (S s : this.getStates()) {
			clone.addState(s);
		}
		for (S s : this.getAcceptStates()) {
			clone.addAcceptState(s);
		}
		for (S s : this.getInitialStates()) {
			clone.addInitialState(s);
		}
		for (T t : this.getTransitions()) {
			clone.addTransition(this.getTransitionSource(t),
					this.getTransitionDestination(t), t);
		}

		clone.transparentStates = new HashSet<S>(this.getTransparentStates());

		return clone;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public IBA<S, T> replace(S transparentState, IBA<S, T> ibaToInject,
			Map<S, Set<Entry<T, S>>> inComing,
			Map<S, Set<Entry<T, S>>> outComing) {
		Preconditions.checkNotNull(transparentState,
				"The state to be replaced is null");
		Preconditions.checkNotNull(ibaToInject, "The IBA to inject is null");
		Preconditions.checkNotNull(inComing, "The inComing map is null");
		Preconditions.checkNotNull(outComing, "The outComing map is null");
		Preconditions.checkArgument(this.isTransparent(transparentState),
				"The state t must be transparent");

		for (S s : inComing.keySet()) {
			if (!this.getPredecessors(transparentState).contains(s)) {
				throw new IllegalArgumentException(
						"The source of an incoming transition to be injected was not connected to the transparent state");
			}
		}
		for (Set<Entry<T, S>> e : inComing.values()) {
			for (Entry<T, S> entry : e) {
				if (!ibaToInject.getInitialStates().contains(entry.getValue())) {
					throw new IllegalArgumentException(
							"The state pointed by an incoming transition is not an initial state of the ibaToInject");
				}
			}
		}
		for (Set<Entry<T, S>> e : outComing.values()) {
			for (Entry<T, S> entry : e) {
				if (!this.getSuccessors(transparentState).contains(
						entry.getValue())) {
					throw new IllegalArgumentException(
							"the destination of an out-coming transition was not connected to the transparent state");
				}
			}
		}
		for (S s : outComing.keySet()) {
			if (!ibaToInject.getAcceptStates().contains(s)) {
				throw new IllegalArgumentException(
						"The source of an out-coming transition is not a final state of the ibaToInject");
			}
		}

		IBAImpl<S, T> newIba = (IBAImpl<S, T>) this.clone();

		for (S s : ibaToInject.getStates()) {
			newIba.addState(s);
		}
		for (S s : ibaToInject.getTransparentStates()) {
			newIba.addTransparentState(s);
		}
		// copy the transition into the refinement
		for (T t : ibaToInject.getTransitions()) {
			newIba.addTransition(ibaToInject.getTransitionSource(t),
					ibaToInject.getTransitionDestination(t), t);
		}
		// if the transparent state is accepting
		if (this.getAcceptStates().contains(transparentState)) {
			// adding the accepting states of the IBA to inject to the accepting
			// states of the refinement
			for (S s : ibaToInject.getAcceptStates()) {
				newIba.addAcceptState(s);
			}
		}
		// if the transparent state is initial
		if (this.getInitialStates().contains(transparentState)) {
			// add the initial state of the IBA to inject to the initial states
			// of the refinement
			for (S s : ibaToInject.getInitialStates()) {
				newIba.addInitialState(s);
			}
		}
		// processing the incoming transitions
		// for each entry
		for (Entry<S, Set<Entry<T, S>>> entry : inComing.entrySet()) {
			// for each transition
			for (Entry<T, S> transitionEntry : entry.getValue()) {
				newIba.addTransition(entry.getKey(),
						transitionEntry.getValue(), transitionEntry.getKey());
			}
		}

		// processing the out-coming transitions
		for (Entry<S, Set<Entry<T, S>>> entry : outComing.entrySet()) {
			// for each transition
			for (Entry<T, S> transitionEntry : entry.getValue()) {
				newIba.addTransition(entry.getKey(),
						transitionEntry.getValue(), transitionEntry.getKey());
			}
		}

		// removing the transparent state to the new IBA
		newIba.removeState(transparentState);
		return newIba;
	}
	
	public void removeState(S state){
		super.removeState(state);
		if(this.transparentStates.contains(state)){
			this.transparentStates.remove(state);
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		String ret = "";

		ret = "ALPHABET: " + this.getAlphabet() + "\n";
		ret = ret + "STATES: " + this.automataGraph.vertexSet() + "\n";
		ret = ret + "INITIAL STATES: " + this.getInitialStates() + "\n";
		ret = ret + "ACCEPTING STATES: " + this.getAcceptStates() + "\n";
		ret = ret + "TRANSPARENT STATES: " + this.getTransparentStates() + "\n";
		ret = ret + "TRANSITIONS\n";
		for (S s : this.automataGraph.vertexSet()) {
			ret = ret + "state " + s + " ->\n";
			for (T outEdge : this.automataGraph.outgoingEdgesOf(s)) {
				ret = ret + "\t \t" + outEdge + "\t"
						+ this.getTransitionDestination(outEdge);
			}
			ret = ret + "\n";

		}
		return ret;
	}
}
