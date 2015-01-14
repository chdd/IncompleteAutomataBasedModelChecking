package it.polimi.automata.impl;

import it.polimi.automata.BA;
import it.polimi.automata.IBA;
import it.polimi.automata.State;
import it.polimi.automata.Transition;
import it.polimi.automata.labeling.Label;

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
 * @param <STATE>
 *            is the type of the state of the Buchi Automaton. The type of the
 *            states of the automaton must implement the interface {@link State}
 * @param <TRANSITION>
 *            is the type of the transition of the Buchi Automaton. The typer of
 *            the transitions of the automaton must implement the interface
 *            {@link Transition}
 * @param <LABEL>
 *            is the type of the label of the transitions depending on whether
 *            the automaton represents the model or the claim it is a set of
 *            proposition or a propositional logic formula {@link Label}
 */
public class IBAImpl<LABEL extends Label, STATE extends StateImpl, TRANSITION extends TransitionImpl<LABEL>>
		extends BAImpl<LABEL, STATE, TRANSITION> implements
		IBA<LABEL, STATE, TRANSITION> {

	/**
	 * contains the set of the transparent states of the automaton
	 */
	private Set<STATE> transparentStates;

	/**
	 * creates a new incomplete Buchi automaton
	 */
	public IBAImpl() {
		super();
		this.transparentStates = new HashSet<STATE>();
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean isTransparent(STATE s) {
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
	public Set<STATE> getTransparentStates() {
		return Collections.unmodifiableSet(this.transparentStates);
	}

	/**
	 * {@inheritDoc}
	 */
	public void addTransparentState(STATE s) {
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
		IBAImpl<LABEL, STATE, TRANSITION> other = (IBAImpl<LABEL, STATE, TRANSITION>) obj;
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
	public IBAImpl<LABEL, STATE, TRANSITION> clone() {
		IBAImpl<LABEL, STATE, TRANSITION> clone = new IBAImpl<LABEL, STATE, TRANSITION>();
		clone.alphabet = new HashSet<LABEL>(this.getAlphabet());
		clone.acceptStates = new HashSet<STATE>(this.getAcceptStates());
		clone.initialStates = new HashSet<STATE>(this.getInitialStates());
		for (TRANSITION t : this.automataGraph.getEdges()) {
			clone.addTransition(this.automataGraph.getSource(t),
					this.automataGraph.getDest(t), t);
		}
		for (STATE s : this.getStates()) {
			clone.addState(s);
		}
		clone.transparentStates = new HashSet<STATE>(
				this.getTransparentStates());

		return clone;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public IBA<LABEL, STATE, TRANSITION> replace(STATE transparentState,
			IBA<LABEL, STATE, TRANSITION> ibaToInject,
			Map<STATE, Set<Entry<TRANSITION, STATE>>> inComing,
			Map<STATE, Set<Entry<TRANSITION, STATE>>> outComing) {
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
		for (STATE s : inComing.keySet()) {
			if (!this.automataGraph.getPredecessors(transparentState).contains(
					s)) {
				throw new IllegalArgumentException(
						"The source of an incoming transition to be injected was not connected to the transparent state");
			}
		}
		for (Set<Entry<TRANSITION, STATE>> e : inComing.values()) {
			for (Entry<TRANSITION, STATE> entry : e) {
				if (!ibaToInject.getInitialStates().contains(entry.getValue())) {
					throw new IllegalArgumentException(
							"The state pointed by an incoming transition is not an initial state of the ibaToInject");
				}
			}
		}
		for (Set<Entry<TRANSITION, STATE>> e : outComing.values()) {
			for (Entry<TRANSITION, STATE> entry : e) {
				if (!this.automataGraph.getSuccessors(transparentState)
						.contains(entry.getValue())) {
					throw new IllegalArgumentException(
							"the destination of an out-coming transition was not connected to the transparent state");
				}
			}
		}
		for (STATE s : outComing.keySet()) {
			if (!ibaToInject.getAcceptStates().contains(s)) {
				throw new IllegalArgumentException(
						"The source of an out-coming transition is not a final state of the ibaToInject");
			}
		}

		IBAImpl<LABEL, STATE, TRANSITION> newIba = (IBAImpl<LABEL, STATE, TRANSITION>) this
				.clone();

		for (STATE s : ibaToInject.getStates()) {
			newIba.addState(s);
		}
		for (STATE s : ibaToInject.getTransparentStates()) {
			newIba.addTransparentState(s);
		}
		// copy the transition into the refinement
		for (TRANSITION t : ibaToInject.getTransitions()) {
			newIba.addTransition(ibaToInject.getTransitionSource(t),
					ibaToInject.getTransitionDestination(t), t);
		}
		// if the transparent state is accepting
		if (this.getAcceptStates().contains(transparentState)) {
			// adding the accepting states of the IBA to inject to the accepting states of the refinement 
			for (STATE s : ibaToInject.getAcceptStates()) {
				newIba.addAcceptState(s);
			}
		}
		// if the transparent state is initial
		if (this.getInitialStates().contains(transparentState)) {
			// add the initial state of the IBA to inject to the initial states of the refinement
			for (STATE s : ibaToInject.getInitialStates()) {
				newIba.addInitialState(s);
			}
		}
		// processing the incoming transitions
		// for each entry
		for(Entry<STATE, Set<Entry<TRANSITION, STATE>>> entry: inComing.entrySet()){
			// for each transition
			for(Entry<TRANSITION, STATE> transitionEntry: entry.getValue()){
				newIba.addTransition(entry.getKey(), transitionEntry.getValue(), transitionEntry.getKey());
			}
		}
		
		// processing the out-coming transitions
		for(Entry<STATE, Set<Entry<TRANSITION, STATE>>> entry: outComing.entrySet()){
			// for each transition
			for(Entry<TRANSITION, STATE> transitionEntry: entry.getValue()){
				newIba.addTransition(entry.getKey(), transitionEntry.getValue(), transitionEntry.getKey());
			}
		}
		
		// removing the transparent state to the new IBA
		newIba.removeState(transparentState);
		return newIba;
	}
}
