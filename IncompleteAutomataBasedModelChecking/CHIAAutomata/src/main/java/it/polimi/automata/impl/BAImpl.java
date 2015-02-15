package it.polimi.automata.impl;

import it.polimi.automata.BA;
import it.polimi.automata.labeling.Label;
import it.polimi.automata.state.State;
import it.polimi.automata.transition.Transition;

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
public class BAImpl<L extends Label, S extends State, T extends Transition<L>>
		implements BA<L, S, T> {

	/**
	 * contains the initial states of the Buchi automaton
	 */
	private Set<S> initialStates;

	/**
	 * contains the accepting states of the Buchi automaton
	 */
	private Set<S> acceptStates;

	/**
	 * contains the set of the alphabet of the Buchi automaton
	 */
	private Set<L> alphabet;

	/**
	 * contains the graph on which the Buchi automaton is based
	 */
	private DirectedSparseGraph<S, T> automataGraph;

	/**
	 * creates a new empty Buchi automaton
	 */
	public BAImpl() {

		this.alphabet = new HashSet<L>();
		this.acceptStates = new HashSet<S>();
		this.initialStates = new HashSet<S>();
		this.automataGraph = new DirectedSparseGraph<S, T>();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Set<S> getInitialStates() {
		return Collections.unmodifiableSet(this.initialStates);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Set<S> getStates() {
		return Collections.unmodifiableSet(new HashSet<S>(this.automataGraph
				.getVertices()));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Set<S> getAcceptStates() {
		return Collections.unmodifiableSet(this.acceptStates);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Set<L> getAlphabet() {
		return Collections.unmodifiableSet(alphabet);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Set<T> getOutTransitions(S state) {
		if (state == null) {
			throw new NullPointerException("The state s cannot be null");
		}
		if (this.automataGraph.getOutEdges(state) == null) {
			return Collections.unmodifiableSet(new HashSet<T>());
		}
		return Collections.unmodifiableSet(new HashSet<T>(this.automataGraph
				.getOutEdges(state)));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Set<T> getInTransitions(S state) {
		if (state == null) {
			throw new NullPointerException("The state s cannot be null");
		}
		return Collections.unmodifiableSet(new HashSet<T>(this.automataGraph
				.getInEdges(state)));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public S getTransitionDestination(T transition) {
		if (transition == null) {
			throw new NullPointerException("The transition t cannot be null");
		}
		if (!this.getTransitions().contains(transition)) {
			throw new IllegalArgumentException(
					"The transition is not contained into the set of the transition of the BA");
		}
		return this.automataGraph.getDest(transition);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public S getTransitionSource(T transition) {
		if (transition == null) {
			throw new NullPointerException("The transition t cannot be null");
		}
		if (!this.getTransitions().contains(transition)) {
			throw new IllegalArgumentException(
					"The transition is already contained into the set of the transition of the BA");
		}
		return this.automataGraph.getSource(transition);
	}

	public Set<S> getSuccessors(S s) {
		if (s == null) {
			throw new NullPointerException("The state s cannot be null");
		}
		if (!this.getStates().contains(s)) {
			throw new IllegalArgumentException(
					"The state is not contained into the states of the automaton");
		}
		return new HashSet<>(this.automataGraph.getSuccessors(s));
	}

	public Set<S> getPredecessors(S s) {
		if (s == null) {
			throw new NullPointerException("The state s cannot be null");
		}
		if (!this.getStates().contains(s)) {
			throw new IllegalArgumentException(
					"The state is not contained into the states of the automaton");
		}
		return new HashSet<>(this.automataGraph.getPredecessors(s));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Set<T> getTransitions() {
		return Collections.unmodifiableSet(new HashSet<T>(this.automataGraph
				.getEdges()));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addInitialState(S s) {
		if (s == null)
			throw new NullPointerException(
					"The state s to be added cannot be null");
		this.initialStates.add(s);
		this.addState(s);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addInitialStates(Set<S> states) {
		if (states == null)
			throw new NullPointerException(
					"The state to be added cannot be null");
		for (S s : states) {
			this.addInitialState(s);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addAcceptState(S s) {
		if (s == null)
			throw new NullPointerException(
					"The state s to be added cannot be null");
		this.acceptStates.add(s);
		this.addState(s);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addAcceptStates(Set<S> states) {
		if (states == null)
			throw new NullPointerException(
					"The state to be added cannot be null");
		for (S s : states) {
			this.addAcceptState(s);
		}

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addState(S state) {
		if (state == null)
			throw new NullPointerException(
					"The state to be added cannot be null");
		this.automataGraph.addVertex(state);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addStates(Set<S> states) {
		if (states == null)
			throw new NullPointerException(
					"The state to be added cannot be null");
		for (S s : states) {
			this.addState(s);
		}

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addCharacter(L character) {
		if (character == null)
			throw new NullPointerException(
					"The set of the proposition cannot be null");
		this.alphabet.add(character);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addCharacters(Set<L> characters) {
		if (characters == null)
			throw new NullPointerException(
					"The set of the characters cannot be null");
		for (L l : characters) {
			this.addCharacter(l);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addTransition(S source, S destination, T transition) {

		if (source == null)
			throw new NullPointerException("The source state cannot be null");
		if (destination == null)
			throw new NullPointerException(
					"The destination state cannot be null");
		if (transition == null)
			throw new NullPointerException("The transition cannot be null");
		if (!this.alphabet.containsAll(transition.getLabels()))
			throw new IllegalArgumentException(
					"The label of the transition is not contained into the alphabet of the automaton");
		if (!this.getStates().contains(source))
			throw new IllegalArgumentException(
					"The source state is not contained into the set of the states of the automaton");
		if (!this.getStates().contains(destination))
			throw new IllegalArgumentException(
					"The destination state is not contained into the set of the states of the automaton");
		if (this.automataGraph.isPredecessor(source, destination)) {
			throw new IllegalArgumentException(
					"The source state is already connected to the destination state");
		}
		if (this.getTransitions().contains(transition)) {
			throw new IllegalArgumentException(
					"The transition is already contained into the set of transitions of the grap");
		}
		this.automataGraph.addEdge(transition, source, destination,
				EdgeType.DIRECTED);
	}

	public boolean isPredecessor(S source, S destination) {
		return this.automataGraph.isPredecessor(source, destination);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void removeState(S state) {
		if (state == null)
			throw new NullPointerException("The state to removed is null");
		if (!this.automataGraph.containsVertex(state))
			throw new IllegalArgumentException(
					"The state to removed is not contained into the set of the states of the Buchi automaton");

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
	@Override
	public void removeTransition(T transition) {
		if (transition == null)
			throw new NullPointerException(
					"The transition to be removed cannot be null");
		if (!this.automataGraph.getEdges().contains(transition))
			throw new IllegalArgumentException(
					"The transition to be removed must be contained into the set of the transitions of the Buchi automaton");

		this.automataGraph.removeEdge(transition);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void removeAcceptingState(S state) {
		if (state == null)
			throw new NullPointerException("The state cannot be null");
		if (!this.acceptStates.contains(state))
			throw new IllegalArgumentException("The state: " + state.getId()
					+ " must be contained in the set of the accepting states");

		this.acceptStates.remove(state);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void removeInitialState(S state) {
		if (state == null)
			throw new NullPointerException("The state cannot be null");
		if (!this.initialStates.contains(state))
			throw new IllegalArgumentException("The state: " + state.getId()
					+ " must be contained in the set of the initial states");

		this.initialStates.remove(state);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public DirectedSparseGraph<S, T> getGraph() {
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

	public String toString() {
		String ret = "";
		ret = "ALPHABET: " + this.alphabet + "\n";
		ret = "STATES: " + this.automataGraph.getVertices() + "\n";
		ret = ret + "INITIAL STATES: " + this.initialStates + "\n";
		ret = ret + "ACCEPTING STATES: " + this.acceptStates + "\n";
		ret = ret + "TRANSITIONS\n";
		for (S s : this.automataGraph.getVertices()) {
			ret = ret + "state " + s + " ->\n";
			for (T outEdge : this.automataGraph.getOutEdges(s)) {
				ret = ret + "\t \t" + outEdge + "\t"
						+ this.getTransitionDestination(outEdge);
			}
			ret = ret + "\n";

		}
		return ret;
	}

	/**
	 * returns a copy of the automaton
	 * 
	 * @return a copy of the automaton
	 */
	public Object clone() {
		BAImpl<L, S, T> ret = new BAImpl<L, S, T>();
		// coping the states
		ret.addStates(this.getStates());
		// coping the initial states
		ret.addInitialStates(this.getInitialStates());
		// coping the accepting states
		ret.addAcceptStates(this.getAcceptStates());
		// coping the accepting states
		for (T t : this.getTransitions()) {
			ret.addTransition(ret.getTransitionSource(t),
					ret.getTransitionDestination(t), t);
		}
		return ret;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public T getTransition(S source, S destination) {
		if (source == null) {
			throw new NullPointerException("The source state cannot be null");
		}
		if (destination == null) {
			throw new NullPointerException(
					"The destination state cannot be null");
		}
		if (!this.automataGraph.containsVertex(source)) {
			throw new IllegalArgumentException(
					"The source state must be contained into the set of the states of the BA");
		}
		if (!this.automataGraph.containsVertex(destination)) {
			throw new IllegalArgumentException(
					"The destination state must be contained into the set of the states of the BA");
		}
		T t = this.automataGraph.findEdge(source, destination);
		if (t == null) {
			throw new IllegalArgumentException(
					"There is no connection between the source and the destination state");
		}
		return t;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isSuccessor(S source, S destination) {
		if (source == null) {
			throw new NullPointerException("The source state cannot be null");
		}
		if (destination == null) {
			throw new NullPointerException(
					"The destination state cannot be null");
		}
		if (!this.automataGraph.containsVertex(source)) {
			throw new IllegalArgumentException(
					"The source state must be contained into the set of the states of the BA");
		}
		if (!this.automataGraph.containsVertex(destination)) {
			throw new IllegalArgumentException(
					"The destination state must be contained into the set of the states of the BA");
		}
		return this.automataGraph.isSuccessor(source, destination);
	}
}
