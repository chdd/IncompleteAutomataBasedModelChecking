package it.polimi.automata.impl;

import it.polimi.automata.BA;
import it.polimi.automata.state.State;
import it.polimi.automata.transition.Transition;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang3.Validate;
import org.jgrapht.EdgeFactory;
import org.jgrapht.graph.DirectedPseudograph;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Preconditions;

import rwth.i2.ltl2ba4j.model.IGraphProposition;

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
 */
public class BAImpl<S extends State, T extends Transition> implements BA<S, T> {

	/**
	 * is the logger of the SubAutomataIdentifier class
	 */
	private static final Logger logger = LoggerFactory.getLogger(BAImpl.class);

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
	private Set<IGraphProposition> alphabet;

	/**
	 * contains the graph on which the Buchi automaton is based
	 */
	protected DirectedPseudograph<S, T> automataGraph;

	/**
	 * creates a new empty Buchi automaton
	 * 
	 * @param transitionFactory
	 *            is the factory which is used to create the transitions of the
	 *            Buchi automaton
	 */
	public BAImpl(EdgeFactory<S, T> transitionFactory) {
		Validate.notNull(transitionFactory,
				"The transition factory cannot be null");

		this.alphabet = new HashSet<IGraphProposition>();
		this.acceptStates = new HashSet<S>();
		this.initialStates = new HashSet<S>();
		this.automataGraph = new DirectedPseudograph<S, T>(transitionFactory);
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
				.vertexSet()));
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
	public Set<IGraphProposition> getAlphabet() {
		return Collections.unmodifiableSet(alphabet);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Set<T> getTransitions() {
		return Collections.unmodifiableSet(new HashSet<T>(this.automataGraph
				.edgeSet()));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Set<T> getOutTransitions(S state) {
		Preconditions.checkNotNull(state, "The state s cannot be null");
		Preconditions
				.checkArgument(
						this.getStates().contains(state),
						"The state "
								+ state
								+ " is not contained into the set of the states of the automaton");

		if (this.automataGraph.outgoingEdgesOf(state) == null) {
			return Collections.unmodifiableSet(new HashSet<T>());
		}
		if (this.automataGraph.outgoingEdgesOf(state) == null) {
			return new HashSet<T>();
		}
		return Collections.unmodifiableSet(new HashSet<T>(this.automataGraph
				.outgoingEdgesOf(state)));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Set<T> getInTransitions(S state) {
		Preconditions.checkNotNull(state, "The state s cannot be null");
		Preconditions
				.checkArgument(
						this.getStates().contains(state),
						"The state "
								+ state
								+ " is not contained into the set of the states of the automaton");

		if (this.automataGraph.incomingEdgesOf(state) == null) {
			return new HashSet<T>();
		}
		return Collections.unmodifiableSet(new HashSet<T>(this.automataGraph
				.incomingEdgesOf(state)));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public S getTransitionDestination(T transition) {
		Preconditions.checkNotNull(transition,
				"The transition t cannot be null");
		Preconditions
				.checkArgument(this.getTransitions().contains(transition),
						"The transition is not contained into the set of the transition of the BA");

		return this.automataGraph.getEdgeTarget(transition);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public S getTransitionSource(T transition) {
		Preconditions.checkNotNull(transition,
				"The transition t cannot be null");
		Preconditions
				.checkArgument(
						this.getTransitions().contains(transition),
						"The transition "
								+ transition
								+ " is not contained into the set of the transition of the BA");

		return this.automataGraph.getEdgeSource(transition);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Set<S> getSuccessors(S s) {
		Preconditions.checkNotNull(s, "The state s cannot be null");
		Preconditions.checkArgument(this.getStates().contains(s),
				"The state is not contained into the states of the automaton");

		Set<S> successors = new HashSet<S>();
		for (T t : this.getOutTransitions(s)) {
			successors.add(this.automataGraph.getEdgeTarget(t));
		}
		return successors;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Set<S> getPredecessors(S s) {
		Preconditions.checkNotNull(s, "The state s cannot be null");
		Preconditions.checkArgument(this.getStates().contains(s),
				"The state is not contained into the states of the automaton");

		Set<S> predecessors = new HashSet<S>();
		for (T t : this.getInTransitions(s)) {
			predecessors.add(this.automataGraph.getEdgeSource(t));
		}
		return predecessors;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addInitialState(S s) {
		Preconditions.checkNotNull(s, "The state s to be added cannot be null");
		this.initialStates.add(s);
		if (!this.getStates().contains(s)) {
			this.addState(s);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addInitialStates(Set<S> states) {
		Preconditions.checkNotNull(states,
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
		Preconditions.checkNotNull(s, "The state s to be added cannot be null");
		this.acceptStates.add(s);
		if (!this.getStates().contains(s)) {
			this.addState(s);
		}

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addAcceptStates(Set<S> states) {
		Preconditions.checkNotNull(states,
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
		Preconditions.checkNotNull(state,
				"The state to be added cannot be null");

		Preconditions
				.checkArgument(
						!this.getStates().contains(state),
						"The state "
								+ state
								+ " is already contained into the set of the states of the automaton");

		this.automataGraph.addVertex(state);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addStates(Set<S> states) {
		Preconditions.checkNotNull(states,
				"The state to be added cannot be null");
		for (S s : states) {
			this.addState(s);
		}

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addCharacter(IGraphProposition character) {
		Preconditions.checkNotNull(character,
				"The set of the proposition cannot be null");
		this.alphabet.add(character);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addCharacters(Set<IGraphProposition> characters) {
		Preconditions.checkNotNull(characters,
				"The set of the characters cannot be null");
		for (IGraphProposition l : characters) {
			this.addCharacter(l);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addTransition(S source, S destination, T transition) {

		Preconditions.checkNotNull(source, "The source state cannot be null");
		Preconditions.checkNotNull(destination,
				"The destination state cannot be null");
		Preconditions.checkNotNull(transition, "The transition cannot be null");
		Preconditions
				.checkArgument(
						this.alphabet.containsAll(transition.getPropositions()),
						"Some of the propositions "
								+ transition.getPropositions()
								+ " of the transition are not contained into the alphabet of the automaton");
		Preconditions
				.checkArgument(this.getStates().contains(source),
						"The source state is not contained into the set of the states of the automaton");
		Preconditions
				.checkArgument(
						this.getStates().contains(destination),
						"The destination state is not contained into the set of the states of the automaton");

		Preconditions
				.checkArgument(!this.getTransitions().contains(transition),
						"The transition is already contained into the set of transitions of the graph");

		logger.debug("Transitions: " + transition.toString());
		logger.debug("Adding the transition: " + transition.getId() + " from "
				+ source.toString() + " to " + destination.toString());

		this.automataGraph.addEdge(source, destination, transition);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isPredecessor(S source, S destination) {
		Preconditions.checkNotNull(source, "The source state cannot be null");
		Preconditions.checkNotNull(destination,
				"The destination state cannot be null");
		return this.automataGraph.containsEdge(source, destination);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void removeState(S state) {
		Preconditions.checkNotNull(state, "The state to removed is null");
		Preconditions
				.checkArgument(
						this.automataGraph.containsVertex(state),
						"The state "
								+ state.getId()
								+ " to removed is not contained into the set of the states of the Buchi automaton");

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
		Preconditions.checkNotNull(transition,
				"The transition to be removed cannot be null");
		Preconditions
				.checkArgument(
						this.automataGraph.edgeSet().contains(transition),
						"The transition to be removed must be contained into the set of the transitions of the Buchi automaton");

		this.automataGraph.removeEdge(transition);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void removeAcceptingState(S state) {
		Preconditions.checkNotNull(state, "The state cannot be null");
		Preconditions
				.checkArgument(
						this.acceptStates.contains(state),
						"The state: "
								+ state.getId()
								+ " must be contained in the set of the accepting states");

		this.acceptStates.remove(state);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void removeInitialState(S state) {
		Preconditions.checkNotNull(state, "The state cannot be null");
		Preconditions
				.checkArgument(
						this.initialStates.contains(state),
						"The state: "
								+ state.getId()
								+ " must be contained in the set of the initial states");

		this.initialStates.remove(state);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		String ret = "";
		
		ret = "ALPHABET: " + this.alphabet + "\n";
		ret = ret + "STATES: " + this.automataGraph.vertexSet() + "\n";
		ret = ret + "INITIAL STATES: " + this.initialStates + "\n";
		ret = ret + "ACCEPTING STATES: " + this.acceptStates + "\n";
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

	/**
	 * returns a copy of the automaton
	 * 
	 * @return a copy of the automaton
	 */
	public Object clone() {
		BAImpl<S, T> ret = new BAImpl<S, T>(this.automataGraph.getEdgeFactory());
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
	public Set<T> getTransitions(S source, S destination) {
		Preconditions.checkNotNull(source, "The source state cannot be null");
		Preconditions.checkNotNull(destination,
				"The destination state cannot be null");
		Preconditions
				.checkArgument(this.automataGraph.containsVertex(source),
						"The source state must be contained into the set of the states of the BA");
		Preconditions
				.checkArgument(this.automataGraph.containsVertex(destination),
						"The destination state must be contained into the set of the states of the BA");
		Preconditions
				.checkArgument(this.isSuccessor(source, destination),
						"There is no connection between the source and the destination state");
		Set<T> t = this.automataGraph.getAllEdges(source, destination);
		return t;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isSuccessor(S source, S destination) {
		Preconditions.checkNotNull(source, "The source state cannot be null");
		Preconditions.checkNotNull(destination,
				"The destination state cannot be null");
		Preconditions
				.checkArgument(this.automataGraph.containsVertex(source),
						"The source state must be contained into the set of the states of the BA");
		Preconditions
				.checkArgument(this.automataGraph.containsVertex(destination),
						"The destination state must be contained into the set of the states of the BA");

		return this.automataGraph.containsEdge(source, destination);
	}

	public DirectedPseudograph<S, T> getGraph() {
		return this.automataGraph;
	}
}
