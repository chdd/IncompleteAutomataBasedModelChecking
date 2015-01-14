package it.polimi.automata.impl;

import it.polimi.automata.BA;
import it.polimi.automata.State;
import it.polimi.automata.Transition;
import it.polimi.automata.labeling.Label;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import edu.uci.ics.jung.graph.DirectedSparseGraph;
import edu.uci.ics.jung.graph.util.EdgeType;

/**
 * <p>
 * Represents a Buchi Automaton, implements the {@link BA} interface <br>
 * 
 * The state of the automaton must must implement the {@link State} interface <br>
 * The transition of the automaton must must implement the {@link Transition}
 * interface <br>
 * The label of the transition must implement the label interface and depending
 * on whether the automaton represents the model or the claim it is a set of
 * proposition or a propositional logic formula
 * </p>
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
public class BAImpl<LABEL extends Label, STATE extends StateImpl, TRANSITION extends TransitionImpl<LABEL>>
		implements BA<LABEL, STATE, TRANSITION> {

	/**
	 * contains the initial states of the Buchi automaton
	 */
	protected Set<STATE> initialStates;

	/**
	 * contains the accepting states of the Buchi automaton
	 */
	protected Set<STATE> acceptStates;

	/**
	 * contains the set of the alphabet of the Buchi automaton
	 */
	protected Set<LABEL> alphabet;

	/**
	 * contains the graph on which the Buchi automaton is based
	 */
	protected DirectedSparseGraph<STATE, TRANSITION> automataGraph;

	/**
	 * creates a new empty Buchi automaton
	 */
	public BAImpl() {

		this.alphabet = new HashSet<LABEL>();
		this.acceptStates = new HashSet<STATE>();
		this.initialStates = new HashSet<STATE>();
		this.automataGraph = new DirectedSparseGraph<STATE, TRANSITION>();
	}

	/**
	 * {@inheritDoc}
	 */
	public Set<STATE> getInitialStates() {
		return Collections.unmodifiableSet(this.initialStates);
	}

	/**
	 * {@inheritDoc}
	 */
	public Set<STATE> getStates() {
		return Collections.unmodifiableSet(new HashSet<STATE>(
				this.automataGraph.getVertices()));
	}

	/**
	 * {@inheritDoc}
	 */
	public Set<STATE> getAcceptStates() {
		return Collections.unmodifiableSet(this.acceptStates);
	}

	/**
	 * {@inheritDoc}
	 */
	public Set<LABEL> getAlphabet() {
		return Collections.unmodifiableSet(alphabet);
	}

	/**
	 * {@inheritDoc}
	 */
	public Set<TRANSITION> getOutTransitions(STATE state) {
		return Collections.unmodifiableSet(new HashSet<TRANSITION>(
				this.automataGraph.getOutEdges(state)));
	}

	/**
	 * {@inheritDoc}
	 */
	public STATE getTransitionDestination(TRANSITION transition) {
		return this.automataGraph.getDest(transition);
	}

	/**
	 * {@inheritDoc}
	 */
	public void addInitialState(STATE s) {
		if (s == null) {
			throw new NullPointerException(
					"The state s to be added cannot be null");
		}
		this.initialStates.add(s);
		this.addState(s);
	}

	/**
	 * {@inheritDoc}
	 */
	public void addAcceptState(STATE s) {
		if (s == null) {
			throw new NullPointerException(
					"The state s to be added cannot be null");
		}
		this.acceptStates.add(s);
		this.addState(s);
	}

	/**
	 * {@inheritDoc}
	 */
	public void addState(STATE state) {
		if (state == null) {
			throw new NullPointerException(
					"The state to be added cannot be null");
		}
		this.automataGraph.addVertex(state);
	}

	/**
	 * {@inheritDoc}
	 */
	public void addCharacter(LABEL character) {
		if (character == null) {
			throw new NullPointerException(
					"The set of the proposition cannot be null");
		}
		this.alphabet.add(character);
	}

	/**
	 * {@inheritDoc}
	 */
	public void addTransition(STATE source, STATE destination,
			TRANSITION transition) {

		if (source == null) {
			throw new NullPointerException("The source state cannot be null");
		}
		if (destination == null) {
			throw new NullPointerException(
					"The destination state cannot be null");
		}
		if (transition == null) {
			throw new NullPointerException("The transition cannot be null");
		}
		if (!this.alphabet.containsAll(transition.getLabels())) {
			throw new IllegalArgumentException(
					"The label of the transition is not contained into the alphabet of the automaton");
		}
		if (!this.automataGraph.getVertices().contains(source)) {
			throw new IllegalArgumentException(
					"The source state is not contained into the set of the states of the automaton");
		}
		if (!this.automataGraph.getVertices().contains(destination)) {
			throw new IllegalArgumentException(
					"The destination state is not contained into the set of the states of the automaton");
		}
		if (this.automataGraph.getSuccessors(source).contains(destination)) {
			throw new IllegalArgumentException(
					"A transition that connect the source and the destination is already present");
		}

		this.automataGraph.addEdge(transition, source, destination,
				EdgeType.DIRECTED);
	}

	/**
	 * {@inheritDoc}
	 */
	public void removeState(STATE state) {
		if (state == null) {
			throw new NullPointerException("The state to removed is null");
		}
		if (!this.automataGraph.containsVertex(state)) {
			throw new IllegalArgumentException(
					"The state to removed is not contained into the set of the states of the Buchi automaton");
		}

		if (this.initialStates.contains(state)) {
			this.initialStates.remove(state);
		}
		if (this.acceptStates.contains(state)) {
			this.acceptStates.remove(state);
		}
		this.automataGraph.removeVertex(state);
	}

	/**
	 * {@inheritDoc}
	 */
	public void removeTransition(TRANSITION transition) {
		if (transition == null) {
			throw new NullPointerException(
					"The transition to be removed cannot be null");
		}
		if (this.automataGraph.getEdges().contains(transition)) {
			throw new IllegalArgumentException(
					"The transition to be removed must be contained into the set of the transitions of the Buchi automaton");
		}
		this.automataGraph.removeEdge(transition);
	}
	
	/**
	 * {@inheritDoc}
	 */
	public void removeAcceptingState(STATE state) {
		if (state == null) {
			throw new NullPointerException("The state cannot be null");
		}
		if (!this.acceptStates.contains(state)) {
			throw new IllegalArgumentException("The state: " + state.getId()
					+ " must be contained in the set of the accepting states");
		}
		this.acceptStates.remove(state);
	}
	
	/**
	 * {@inheritDoc}
	 */
	public void removeInitialState(STATE state) {
		if (state == null) {
			throw new NullPointerException("The state cannot be null");
		}
		if (!this.initialStates.contains(state)) {
			throw new IllegalArgumentException("The state: " + state.getId()
					+ " must be contained in the set of the initial states");
		}
		this.initialStates.remove(state);
	}
	/**
	 * {@inheritDoc}
	 */
	public DirectedSparseGraph<STATE, TRANSITION> getGraph() {
		return this.automataGraph;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.uci.ics.jung.graph.AbstractTypedGraph#getDefaultEdgeType()
	 */
	public EdgeType getDefaultEdgeType() {
		return EdgeType.DIRECTED;
	}
}
