package it.polimi.automata;

import it.polimi.automata.state.State;
import it.polimi.automata.transition.Transition;
import it.polimi.automata.transition.TransitionFactory;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.jgrapht.DirectedGraph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DirectedPseudograph;
import org.jgrapht.graph.UnmodifiableDirectedGraph;

import rwth.i2.ltl2ba4j.model.IGraphProposition;
import rwth.i2.ltl2ba4j.model.impl.GraphProposition;
import rwth.i2.ltl2ba4j.model.impl.SigmaProposition;

import com.google.common.base.Preconditions;

/**
 * <p>
 * Represents a Buchi Automaton. The BA class contains the description of a
 * Buchi Automaton. The BA class is based on the DirectedPseudograph class of
 * the JGrapT library. The DirectedPseudograph allows to create a non-simple
 * directed graph in which both graph loops and multiple edges are permitted.
 * The BA class maintains the set of initial states, the set of accepting
 * states, and the alphabet of the Buchi Automaton. The constructor of the BA
 * requires to specify the transition factory to be used, depending on whether
 * the BA is used to represent the model or the claim to be checked. The BA
 * class provides methods to access the elements of the BA, such as the one that
 * allow to get the successors of a state (getSuccessors()) to obtain the
 * initial states (getInitialStates()). <br>
 * </p>
 * 
 * @author claudiomenghi
 */
public class BA {

	/**
	 * contains the initial states of the Buchi automaton
	 */
	protected Set<State> initialStates;

	/**
	 * contains the accepting states of the Buchi automaton
	 */
	protected Set<State> acceptStates;

	/**
	 * contains the set of the alphabet of the Buchi automaton
	 */
	protected Set<IGraphProposition> propositions;

	/**
	 * contains the graph on which the Buchi automaton is based
	 */
	protected DirectedPseudograph<State, Transition> automataGraph;

	/**
	 * creates a new empty Buchi automaton
	 * 
	 * @param transitionFactory
	 *            is the factory which is used to create the transitions of the
	 *            Buchi automaton
	 */
	public BA(TransitionFactory<State, Transition> transitionFactory) {
		Preconditions.checkNotNull(transitionFactory,
				"The transition factory cannot be null");

		this.propositions = new HashSet<IGraphProposition>();
		this.propositions.add(new SigmaProposition());
		this.acceptStates = new HashSet<State>();
		this.initialStates = new HashSet<State>();
		this.automataGraph = new DirectedPseudograph<State, Transition>(
				transitionFactory);
	}

	/**
	 * returns the set of initial states of the Buchi automaton
	 * 
	 * @return the set of initial states of the Buchi automaton
	 */
	public Set<State> getInitialStates() {
		return this.initialStates;
	}

	/**
	 * returns the set of the states of the Buchi automaton
	 * 
	 * @return the set of the states of the Buchi automaton
	 */
	public Set<State> getStates() {
		return this.automataGraph.vertexSet();
	}

	/**
	 * returns the set of accepting states of the Buchi automaton
	 * 
	 * @return set of the accepting states of the Buchi automaton
	 */
	public Set<State> getAcceptStates() {
		return this.acceptStates;
	}

	/**
	 * returns the propositions of the Buchi automaton
	 * 
	 * @return the propositions of the Buchi automaton
	 */
	public Set<IGraphProposition> getPropositions() {
		return Collections.unmodifiableSet(propositions);
	}

	/**
	 * returns the set of the transitions of the Buchi automaton
	 * 
	 * @return the set of the transitions of the Buchi automaton
	 */
	public Set<Transition> getTransitions() {
		return this.automataGraph.edgeSet();
	}

	/**
	 * return the set of transitions that exits the state
	 * 
	 * @param state
	 *            is the state under analysis
	 * 
	 * @return the set of transitions that exits the state
	 * 
	 * @throws NullPointerException
	 *             if the state is null
	 * @throws IllegalArgumentException
	 *             if the state is not contained into the set of state of the
	 *             Buchi automaton
	 */
	public Set<Transition> getOutTransitions(State state) {
		Preconditions.checkNotNull(state, "The state s cannot be null");
		Preconditions
				.checkArgument(
						this.getStates().contains(state),
						"The state "
								+ state
								+ " is not contained into the set of the states of the automaton");

		if (this.automataGraph.outgoingEdgesOf(state) == null) {
			return Collections.unmodifiableSet(new HashSet<Transition>());
		}
		if (this.automataGraph.outgoingEdgesOf(state) == null) {
			return Collections.unmodifiableSet(new HashSet<Transition>());
		}
		return Collections.unmodifiableSet(this.automataGraph.outgoingEdgesOf(state));
	}

	/**
	 * return the set of transitions that enters the state
	 * 
	 * @param state
	 *            is the state under analysis
	 * 
	 * @return the set of transitions that enter the state
	 * 
	 * @throws NullPointerException
	 *             if the state is null
	 * @throws IllegalArgumentException
	 *             if the state is not contained into the set of state of the
	 *             Buchi automaton
	 */
	public Set<Transition> getInTransitions(State state) {
		Preconditions.checkNotNull(state, "The state s cannot be null");
		Preconditions
				.checkArgument(
						this.getStates().contains(state),
						"The state "
								+ state
								+ " is not contained into the set of the states of the automaton");

		if (this.automataGraph.incomingEdgesOf(state) == null) {
			return Collections.unmodifiableSet(new HashSet<Transition>());
		}
		return this.automataGraph.incomingEdgesOf(state);
	}

	/**
	 * returns the state that is the destination of the transition
	 * 
	 * @param transition
	 *            is the transition to be analyzed
	 * @return the state which is destination of the transition
	 * 
	 * @throws NullPointerException
	 *             if the transition is null
	 * @throws IllegalArgumentException
	 *             if the transition is not contained in the set of transitions
	 *             of the automaton
	 */
	public State getTransitionDestination(Transition transition) {
		Preconditions.checkNotNull(transition,
				"The transition t cannot be null");
		Preconditions
				.checkArgument(
						this.getTransitions().contains(transition),
						"The transition "
								+ transition
								+ " is not contained into the set of the transition of the BA");

		return this.automataGraph.getEdgeTarget(transition);
	}

	/**
	 * returns the state that is the source of the transition
	 * 
	 * @param transition
	 *            is the transition to be analyzed
	 * @return the state which is the source of the transition
	 * 
	 * @throws NullPointerException
	 *             if the transition is null
	 * @throws IllegalArgumentException
	 *             if the transition is not contained in the set of transitions
	 *             of the automaton
	 */
	public State getTransitionSource(Transition transition) {
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
	 * returns the successors of the s
	 * 
	 * @throws NullPointerException
	 *             if the state s is null
	 * @throws IllegalArgumentException
	 *             if the state s is not contained into the set of the states of
	 *             the automaton
	 * @return the successors of the state s
	 */
	public Set<State> getSuccessors(State s) {
		Preconditions.checkNotNull(s, "The state s cannot be null");
		Preconditions.checkArgument(this.getStates().contains(s), "The state "
				+ s + " is not contained into the states of the automaton");

		return Collections.unmodifiableSet(new HashSet<State>(Graphs
				.successorListOf(this.automataGraph, s)));
	}

	/**
	 * returns the predecessors of a the state s
	 * 
	 * @throws NullPointerException
	 *             if the state s is null
	 * @throws IllegalArgumentException
	 *             if the state s is not contained into the set of the states of
	 *             the automaton
	 * @return the predecessors of the state s
	 */
	public Set<State> getPredecessors(State s) {
		Preconditions.checkNotNull(s, "The state s cannot be null");
		Preconditions.checkArgument(this.getStates().contains(s),
				"The state is not contained into the states of the automaton");

		return Collections.unmodifiableSet(new HashSet<State>(Graphs
				.predecessorListOf(this.automataGraph, s)));
	}

	/**
	 * adds the initial state to the Buchi automaton. If the state is not
	 * contained into the states of the automaton it is also added to the states
	 * of the automaton
	 * 
	 * @param state
	 *            the state to be added as initial state
	 * @throws NullPointerException
	 *             if the state is null
	 */
	public void addInitialState(State state) {
		Preconditions.checkNotNull(state,
				"The state s to be added cannot be null");
		this.initialStates.add(state);
		if (!this.getStates().contains(state)) {
			this.addState(state);
		}
	}

	/**
	 * adds the initial states to the Buchi automaton. If a state is not
	 * contained into the states of the automaton it is also added to the states
	 * of the automaton
	 * 
	 * @param states
	 *            is the set of the states to be added to the automaton
	 * @throws NullPointerException
	 *             if the set of the states or a specific state is null
	 */
	public void addInitialStates(Set<State> states) {
		Preconditions.checkNotNull(states,
				"The state to be added cannot be null");
		for (State s : states) {
			this.addInitialState(s);
		}
	}

	/**
	 * adds the accepting state to the Buchi automaton. If the state is not
	 * contained into the state of the automaton it is also added to the states
	 * of the automaton
	 * 
	 * @param state
	 *            the state to be added as accepting state
	 * @throws NullPointerException
	 *             if the state is null
	 */
	public void addAcceptState(State s) {
		Preconditions.checkNotNull(s, "The state s to be added cannot be null");
		this.acceptStates.add(s);
		if (!this.getStates().contains(s)) {
			this.addState(s);
		}

	}

	/**
	 * adds the accepting states to the Buchi automaton. If a state is not
	 * contained into the state of the automaton it is also added to the states
	 * of the automaton
	 * 
	 * @param states
	 *            the set of the states to be added as accepting states
	 * @throws NullPointerException
	 *             if the states is null or if an element into the set is null
	 */
	public void addAcceptStates(Set<State> states) {
		Preconditions.checkNotNull(states,
				"The state to be added cannot be null");
		for (State s : states) {
			this.addAcceptState(s);
		}

	}

	/**
	 * adds the state to the set of the state of the Buchi automaton. If the
	 * state is already present into the set of the states of the automaton no
	 * actions are performed
	 * 
	 * @param state
	 *            is the state to be added to the states of the Buchi automaton
	 * @throws NullPointerException
	 *             if the state to be added is null
	 */
	public void addState(State state) {
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
	 * adds the states to the set of the states of the Buchi automaton. If the a
	 * state is already present into the set of the states of the automaton no
	 * actions are performed
	 * 
	 * @param states
	 *            the set of the states to be added to the states of the Buchi
	 *            automaton
	 * @throws NullPointerException
	 *             if a state to be added is null or if the set of the states is
	 *             null
	 */
	public void addStates(Set<State> states) {
		Preconditions.checkNotNull(states,
				"The state to be added cannot be null");
		for (State s : states) {
			this.addState(s);
		}

	}

	/**
	 * adds the propositions to the proposition of the Buchi automaton. If a
	 * proposition is already contained in the set of characters of the Buchi
	 * automaton no actions are performed
	 * 
	 * @param proposition
	 *            the character to be added to the Buchi automaton
	 * @throws NullPointerException
	 *             is generated if the character to be added is null
	 * @throws IllegalArgumentException
	 *             if the proposition is negated
	 */
	public void addProposition(IGraphProposition proposition) {
		Preconditions.checkNotNull(proposition,
				"The set of the proposition cannot be null");
		Preconditions.checkArgument(!proposition.isNegated(),
				"The proposition to be added to the BA cannot be negated");

		this.propositions.add(proposition);
	}

	/**
	 * adds the propositions to the propositions of the Buchi automaton. If a
	 * proposition is already contained in the set of characters of the Buchi
	 * automaton no actions are performed
	 * 
	 * @param propositions
	 *            the set of the characters to be added to the Buchi automaton
	 * @throws NullPointerException
	 *             if the set of the characters or any character inside the set
	 *             is null
	 */
	public void addPropositions(Set<IGraphProposition> propositions) {
		Preconditions.checkNotNull(propositions,
				"The set of the characters cannot be null");
		for (IGraphProposition l : propositions) {
			this.addProposition(l);
		}
	}

	/**
	 * add the transition t which connects the source and the destination state
	 * 
	 * @param source
	 *            is the source of the transition
	 * @param destination
	 *            is the destination of the transition
	 * @param transition
	 *            is the transition to be added
	 * @throws NullPointerException
	 *             if the source state the destination state or the transition
	 *             is null
	 * 
	 * @throws IllegalArgumentException
	 *             is generated in one of the following cases <br/>
	 *             the label of the transition is not contained in the alphabet
	 *             of the automaton <br/>
	 *             the source is not contained into the set of the states of the
	 *             automaton <br/>
	 *             the destination of the transition is not contained into the
	 *             set of the states of the automaton <br/>
	 *             a transition that connect source to the destination is
	 *             already present
	 */
	public void addTransition(State source, State destination,
			Transition transition) {

		Preconditions.checkNotNull(source, "The source state cannot be null");
		Preconditions.checkNotNull(destination,
				"The destination state cannot be null");
		Preconditions.checkNotNull(transition, "The transition cannot be null");

		for (IGraphProposition p : transition.getPropositions()) {
			if (p.isNegated()) {
				IGraphProposition proposition = new GraphProposition(
						p.getLabel(), false);
				Preconditions
						.checkArgument(
								this.getPropositions().contains(proposition),
								"The proposition "
										+ proposition
										+ " of the transition is not contained into the alphabet of the automaton");
			} else {
				Preconditions
						.checkArgument(
								this.getPropositions().contains(p),
								"The proposition "
										+ p
										+ " of the transition is not contained into the alphabet of the automaton");
			}
		}
		Preconditions
				.checkArgument(this.getStates().contains(source),
						"The source state is not contained into the set of the states of the automaton");
		Preconditions
				.checkArgument(
						this.getStates().contains(destination),
						"The destination state is not contained into the set of the states of the automaton");

		this.automataGraph.addEdge(source, destination, transition);
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean isPredecessor(State source, State destination) {
		Preconditions.checkNotNull(source, "The source state cannot be null");
		Preconditions.checkNotNull(destination,
				"The destination state cannot be null");
		return this.automataGraph.containsEdge(source, destination);
	}

	/**
	 * removes the specified state from the set of the states of the Buchi
	 * automaton
	 * 
	 * @param state
	 *            is the state to be removed from the states of the Buchi
	 *            automaton
	 * @throws NullPointerException
	 *             if the state to be removed is null
	 * @throws IllegalArgumentException
	 *             if the state to be removed is not contained into the set of
	 *             the states of the Buchi automaton
	 * 
	 *             <pre>
	 * state != null
	 * </pre>
	 */
	public void removeState(State state) {
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
	 * removes the specified transitions from the set of transitions of the
	 * Buchi automaton
	 * 
	 * @param transition
	 *            is the transition to be removed from the transitions of the
	 *            Buchi automaton
	 * @throws NullPointerException
	 *             if the transition to be removed is null
	 * @throws IllegalArgumentException
	 *             if the transition to be removed is not contained into the set
	 *             of transitions of the automaton
	 */
	public void removeTransition(Transition transition) {
		Preconditions.checkNotNull(transition,
				"The transition to be removed cannot be null");
		Preconditions
				.checkArgument(
						this.automataGraph.edgeSet().contains(transition),
						"The transition to be removed must be contained into the set of the transitions of the Buchi automaton");

		this.automataGraph.removeEdge(transition);
	}

	/**
	 * removes the specified state from the set of the accepting states of the
	 * Buchi automaton the method does not remove the states from the states of
	 * the automaton
	 * 
	 * @param state
	 *            is the accepting state to be removed from the accepting states
	 *            of the Buchi automaton
	 * @throws NullPointerException
	 *             if the state to be removed is null
	 * @throws IllegalArgumentException
	 *             if the state to be removed is not contained into the set of
	 *             the accepting states of the Buchi automaton
	 */
	public void removeAcceptingState(State state) {
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
	 * removes the specified state from the set of the initial states of the
	 * Buchi automaton the method does not remove the states from the states of
	 * the automaton
	 * 
	 * @param state
	 *            is the initial state to be removed from the initial states of
	 *            the Buchi automaton
	 * @throws NullPointerException
	 *             if the state to be removed is null
	 * @throws IllegalArgumentException
	 *             if the state to be removed is not contained into the set of
	 *             the initial states of the Buchi automaton
	 */
	public void removeInitialState(State state) {
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

		ret = "ALPHABET: " + this.propositions + "\n";
		ret = ret + "STATES: " + this.automataGraph.vertexSet() + "\n";
		ret = ret + "INITIAL STATES: " + this.initialStates + "\n";
		ret = ret + "ACCEPTING STATES: " + this.acceptStates + "\n";
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

	/**
	 * returns a copy of the automaton
	 * 
	 * @return a copy of the automaton
	 */
	@Override
	public BA clone() {
		BA ret = new BA(
				(TransitionFactory<State, Transition>) this.automataGraph
						.getEdgeFactory());
		// coping the states
		ret.addStates(this.getStates());
		// coping the initial states
		ret.addInitialStates(this.getInitialStates());
		// coping the accepting states
		ret.addAcceptStates(this.getAcceptStates());
		// coping the accepting states
		ret.addPropositions(this.getPropositions());
		for (Transition t : this.getTransitions()) {
			ret.addTransition(this.getTransitionSource(t),
					this.getTransitionDestination(t), t);
		}
		return ret;
	}

	/**
	 * returns the transition between the source and the destination state. if
	 * no transition is present an Illegal argument exception is thrown
	 * 
	 * @param source
	 *            is the source state of the transition
	 * @param destination
	 *            is the destination state of the transition
	 * @return the transition between the source and the destination state
	 * @throws NullPointerException
	 *             if the source state or the destination state is null
	 * @throws IllegalArgumentException
	 *             if the source or the destination state is not a state of the
	 *             Buchi automaton or if it does not exists a transition that
	 *             connect the source and the destination state
	 */
	public Set<Transition> getTransitions(State source, State destination) {
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
		Set<Transition> t = this.automataGraph.getAllEdges(source, destination);
		return t;
	}

	/**
	 * returns true if the destination is a direct successor of of the source,
	 * i.e., there exists A transition that connect the source and the
	 * destination. Otherwise a false value is returned
	 * 
	 * @param source
	 *            is the source of the transition
	 * @param destination
	 *            is the destination of the transition
	 * @return true if there exists a transition between the source and the
	 *         destination, false otherwise
	 * @throws NullPointerException
	 *             if the source and the destination are null
	 * @throws IllegalArgumentException
	 *             if the source or the destination are not contained into the
	 *             set of the states of the automaton
	 */
	public boolean isSuccessor(State source, State destination) {
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

	/**
	 * adds to the accepting state s a transition labeled with the special
	 * stuttering character (a character which is outside the alphabet), which
	 * is a character that does not belongs to the alphabet
	 * 
	 * @param s
	 *            the state to be stuttered
	 */
	public void addStuttering(State s) {
		Preconditions.checkNotNull(s, "The state s cannot be null");
		Preconditions
				.checkArgument(
						this.getStates().contains(s),
						"The state "
								+ s
								+ " must be contained into the set of the states of the automaton ");
		Set<IGraphProposition> propositions = new HashSet<IGraphProposition>();
		propositions.add(new GraphProposition(
				AutomataConstants.STUTTERING_CHARACTER, false));
		this.addPropositions(propositions);
		Transition stutteringTransition = ((TransitionFactory<State, Transition>) this.automataGraph
				.getEdgeFactory()).create(propositions);
		this.addTransition(s, s, stutteringTransition);

	}

	public TransitionFactory<State, Transition> getTransitionFactory() {
		return (TransitionFactory<State, Transition>) this.automataGraph
				.getEdgeFactory();
	}

	/**
	 * returns the graph over which the BA is constructed
	 * 
	 * @return the graph over which the BA is constructed
	 */
	public DirectedGraph<State, Transition> getGraph() {
		return new UnmodifiableDirectedGraph<State, Transition>(
				this.automataGraph);
	}
}
