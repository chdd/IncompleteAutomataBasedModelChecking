package it.polimi.automata.impl;

import it.polimi.automata.BA;
import it.polimi.automata.IBA;
import it.polimi.automata.labeling.Label;
import it.polimi.automata.state.State;
import it.polimi.automata.transition.Transition;

import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

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
 *      The label of the transition must implement the label interface and
 *      depending on whether the automaton represents the model or the claim it
 *      is a set of proposition or a propositional logic formula
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
 * @param <L>
 *            is the type of the label of the transitions depending on whether
 *            the automaton represents the model or the claim it is a set of
 *            proposition or a propositional logic formula {@link Label}
 */
public class IBAImpl<L extends Label, S extends State, T extends Transition<L>>
		extends BAImpl<L, S, T> implements
		IBA<L, S, T> {

	/**
	 * contains the set of the transparent states of the automaton
	 */
	private Set<S> transparentStates;

	/**
	 * creates a new incomplete Buchi automaton
	 */
	protected IBAImpl() {
		super();
		this.transparentStates = new HashSet<S>();
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean isTransparent(S s) {
		if (s == null) {
			throw new IllegalArgumentException(
					"The state to be added cannot be null");
		}
		if (!this.getGraph().containsVertex(s)) {
			throw new IllegalArgumentException(
					"The state is not contained into the set of the states of the IBA");
		}
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
		if (s == null) {
			throw new IllegalArgumentException(
					"The state to be added cannot be null");
		}
		this.transparentStates.add(s);
		this.addState(s);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime
				* result
				+ ((transparentStates == null) ? 0 : transparentStates
						.hashCode());
		return result;
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		IBAImpl<L, S, T> other = (IBAImpl<L, S, T>) obj;
		if (transparentStates == null) {
			if (other.transparentStates != null)
				return false;
		} else if (!transparentStates.equals(other.transparentStates))
			return false;
		return true;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public IBAImpl<L, S, T> clone() {
		IBAImpl<L, S, T> clone = new IBAImpl<L, S, T>();
		for(L l: this.getAlphabet()){
			clone.addCharacter(l);
		}
		for (S s : this.getStates()) {
			clone.addState(s);
		}
		for(S s: this.getAcceptStates()){
			clone.addAcceptState(s);
		}
		for(S s: this.getInitialStates()){
			clone.addInitialState(s);
		}
		for (T t : this.getGraph().getEdges()) {
			clone.addTransition(this.getGraph().getSource(t),
					this.getGraph().getDest(t), t);
		}
		
		clone.transparentStates = new HashSet<S>(
				this.getTransparentStates());

		return clone;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public IBA<L, S, T> replace(S transparentState,
			IBA<L, S, T> ibaToInject,
			Map<S, Set<Entry<T, S>>> inComing,
			Map<S, Set<Entry<T, S>>> outComing) {
		if (transparentState == null) {
			throw new NullPointerException("The state to be replaced is null");
		}
		if (ibaToInject == null) {
			throw new NullPointerException("The IBA to inject is null");
		}
		if (inComing == null) {
			throw new NullPointerException("The inComing map is null");
		}
		if (outComing == null) {
			throw new NullPointerException("The outComing map is null");
		}
		if (!this.isTransparent(transparentState)) {
			throw new IllegalArgumentException(
					"The state t must be transparent");
		}
		for (S s : inComing.keySet()) {
			if (!this.getGraph().getPredecessors(transparentState).contains(
					s)) {
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
				if (!this.getGraph().getSuccessors(transparentState)
						.contains(entry.getValue())) {
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

		IBAImpl<L, S, T> newIba = (IBAImpl<L, S, T>) this
				.clone();

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
			// adding the accepting states of the IBA to inject to the accepting states of the refinement 
			for (S s : ibaToInject.getAcceptStates()) {
				newIba.addAcceptState(s);
			}
		}
		// if the transparent state is initial
		if (this.getInitialStates().contains(transparentState)) {
			// add the initial state of the IBA to inject to the initial states of the refinement
			for (S s : ibaToInject.getInitialStates()) {
				newIba.addInitialState(s);
			}
		}
		// processing the incoming transitions
		// for each entry
		for(Entry<S, Set<Entry<T, S>>> entry: inComing.entrySet()){
			// for each transition
			for(Entry<T, S> transitionEntry: entry.getValue()){
				newIba.addTransition(entry.getKey(), transitionEntry.getValue(), transitionEntry.getKey());
			}
		}
		
		// processing the out-coming transitions
		for(Entry<S, Set<Entry<T, S>>> entry: outComing.entrySet()){
			// for each transition
			for(Entry<T, S> transitionEntry: entry.getValue()){
				newIba.addTransition(entry.getKey(), transitionEntry.getValue(), transitionEntry.getKey());
			}
		}
		
		// removing the transparent state to the new IBA
		newIba.removeState(transparentState);
		return newIba;
	}
}
