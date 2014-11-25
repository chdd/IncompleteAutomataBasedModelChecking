package it.polimi.model.impl.automata;

import it.polimi.model.impl.labeling.DNFFormulaImpl;
import it.polimi.model.impl.labeling.Proposition;
import it.polimi.model.impl.states.State;
import it.polimi.model.impl.transitions.Transition;
import it.polimi.model.interfaces.automata.BA;
import it.polimi.model.interfaces.labeling.DNFFormula;
import it.polimi.model.interfaces.states.StateFactory;
import it.polimi.model.interfaces.transitions.TransitionFactory;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.collections15.Factory;

import edu.uci.ics.jung.graph.DirectedSparseGraph;
import edu.uci.ics.jung.graph.util.EdgeType;

/**
 * @author claudiomenghi contains a complete {@link BAImpl}
 * @param <STATE>
 *            the type of the states
 * @param <TRANSITION>
 *            the type of the transitions
 */
public class BAImpl<STATE extends State, TRANSITION extends Transition>
		implements BA<STATE, TRANSITION> {

	/**
	 * contains the {@link StateFactory} which allows to create the
	 * {@link State} of the {@link BA}
	 */
	protected StateFactory<STATE> stateFactory;

	/**
	 * contains the {@link Factory} which allows to create the
	 * {@link Transition}s of the {@link BA}
	 */
	protected TransitionFactory<TRANSITION> transitionFactory;

	/**
	 * contains the initial states of the {@link BA}
	 */
	protected Set<STATE> initialStates;

	/**
	 * contains the accepting {@link State}s of the {@link BA}
	 */
	protected Set<STATE> acceptStates;

	/**
	 * contains the set of the {@link Proposition} of the {@link BA}
	 */
	protected Set<Proposition> alphabet;

	/**
	 * contains the graph on which the {@link BA} is based
	 */
	protected DirectedSparseGraph<STATE, TRANSITION> automataGraph;

	/**
	 * creates a new empty {@link BAImpl}
	 * 
	 * @param transitionFactory
	 *            is the {@link Factory} which allows to create the
	 *            {@link Transition} of the {@link BA}
	 * @param stateFactory
	 *            is the {@link Factory} which allows to create the
	 *            {@link Transition} of the {@link BA}
	 * 
	 * @throws NullPointerException
	 *             if the {@link TransitionFactory} or the {@link StateFactory}
	 *             is null
	 */
	public BAImpl(TransitionFactory<TRANSITION> transitionFactory,
			StateFactory<STATE> stateFactory) {

		if (transitionFactory == null) {
			throw new NullPointerException(
					"The transition factory cannot be null");
		}
		if (stateFactory == null) {
			throw new NullPointerException("The state factory cannot be null");
		}
		this.alphabet = new HashSet<Proposition>();
		this.acceptStates = new HashSet<STATE>();
		this.initialStates = new HashSet<STATE>();
		this.transitionFactory = transitionFactory;
		this.stateFactory = stateFactory;
		this.automataGraph = new DirectedSparseGraph<STATE, TRANSITION>();
	}

	/**
	 * sets the {@link Set} of initial {@link State} of the {@link BA}
	 * 
	 * @param initialStates
	 *            is the {@link Set} of initial {@link State}s of the {@link BA}
	 * @throws NullPointerException
	 *             if the {@link Set} of the initialStates is null
	 * @throws NullPointerException
	 *             if there exists a {@link State} in the {@link Set} of the
	 *             initial {@link State} which is null
	 */
	public void setInitialStates(Set<STATE> initialStates) {

		this.initialStates = new HashSet<STATE>();
		this.addInitialStates(initialStates);
	}

	/**
	 * adds the {@link Set} of {@link State} to the {@link BA}
	 * 
	 * @param states
	 *            the {@link Set} of the {@link State} to be added to the
	 *            {@link BA}
	 * @throws NullPointerException
	 *             if the {@link Set} of the {@link State} is null
	 */
	public void addInitialStates(Set<STATE> states) {
		if (initialStates == null) {
			throw new NullPointerException(
					"The set of initial states cannot be null");
		}
		for (STATE state : states) {
			this.addInitialState(state);
		}
	}

	/**
	 * Add a new initial {@link State} in the set of the {@link State}s of the
	 * {@link BA}. The {@link State} is also added in the set of the
	 * {@link State} of the {@link BA} through the method {@link addState}
	 * 
	 * @param s
	 *            {@link State} is the initial state to be added in the set of
	 *            the {@link State}s of the {@link BA}
	 * @throws NullPointerException
	 *             is generate if the {@link State} s to be added is null
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
	 * Returns the {@link Set} of the initial {@link State} of the {@link BA}.
	 * 
	 * @return the {@link Set} of the initial {@link State} of the {@link BA}
	 */
	public Set<STATE> getInitialStates() {
		return Collections.unmodifiableSet(this.initialStates);
	}

	/**
	 * check is the {@link State} s is contained into the set of the initial
	 * states of the automaton
	 * 
	 * @param s
	 *            the state to be checked if initial
	 * @return true if the state s is contained into the set of the initial
	 *         states of the automaton, false otherwise
	 * @throws NullPointerException
	 *             is generate if the state s is null
	 */
	public boolean isInitial(STATE s) {
		if (s == null) {
			throw new NullPointerException(
					"The state s to be added cannot be null");
		}
		return this.initialStates.contains(s);
	}

	/**
	 * sets the {@link Set} of accepting {@link State} of the {@link BA}
	 * 
	 * @param acceptStates
	 *            the {@link Set} of the accepting {@link State} of the
	 *            {@link BA}
	 * @throws NullPointerException
	 *             if the {@link Set} of acceptState is null or if there exists
	 *             an initial {@link State} which is null
	 */
	public void setAcceptStates(Set<STATE> acceptStates) {
		this.acceptStates = new HashSet<STATE>();
		this.addAcceptStates(acceptStates);
	}

	/**
	 * adds the {@link Set} of the {@link State} to the accepting States of the
	 * {@link BA}
	 * 
	 * @param states
	 *            the {@link Set} of the {@link State} to be added to the
	 *            {@link Set} of the accepting {@link State}s of the {@link BA}
	 * @throws NullPointerException
	 *             if the {@link Set} of the {@link State} is null or if there
	 *             is a {@link State} in the {@link Set} that is null
	 */
	public void addAcceptStates(Set<STATE> states) {
		if (states == null) {
			throw new NullPointerException(
					"The set of the states cannot be null");
		}
		for (STATE state : states) {
			this.addAcceptState(state);
		}
	}

	/**
	 * Add a new accept {@link State} in the {@link Set} of the {@link State} of
	 * the {@link BA}. The {@link State} is also added in the {@link Set} of the
	 * {@link State} of the {@link BA} through the method {@link addState}
	 * 
	 * @param s
	 *            is the {@link State} to be added to the accepting
	 *            {@link State} of the {@link BA}
	 * @throws NullPointerException
	 *             is generate if the {@link State} s to be added is null
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
	 * check is the {@link State} s is contained into the set of the accept
	 * {@link State} of the automaton
	 * 
	 * @param s
	 *            the {@link State} to be checked if accepting
	 * @return true if the {@link State}s is contained into the {@link Set} of
	 *         the accept {@link State}s of the automaton, false otherwise
	 * @throws NullPointerException
	 *             is generate if the {@link State} s is null
	 */
	public boolean isAccept(STATE s) {
		if (s == null) {
			throw new NullPointerException(
					"The state s to be added cannot be null");
		}
		return this.acceptStates.contains(s);
	}

	/**
	 * Returns the {@link Set} of accepting {@link State}s of the {@link BA}.
	 * 
	 * @return {@link Set} of the accepting {@link State} of the {@link BA}
	 */
	public Set<STATE> getAcceptStates() {
		return Collections.unmodifiableSet(this.acceptStates);
	}

	/**
	 * add the {@link Set} of the {@link State} of the {@link BA}
	 * 
	 * @param states
	 *            the {@link Set} of the {@link State} to be added to the
	 *            {@link BA}
	 * @throws NullPointerException
	 *             if the {@link Set} of the {@link State}s is null
	 * @throws NullPointerException
	 *             if there exists a null {@link State} in the {@link Set} of
	 *             the {@link State}s
	 * 
	 */
	public void addStates(Set<STATE> states) {
		if (states == null) {
			throw new NullPointerException(
					"The set of the states cannot be null");
		}
		for (STATE s : states) {
			this.addState(s);
		}
	}

	/**
	 * adds the {@link State} state to the {@link Set} of the {@link State}
	 * present in the {@link DirectedSparseGraph}
	 * 
	 * @param state
	 *            is the {@link State} to be added to the {@link BA}
	 * @throws NullPointerException
	 *             if the {@link State} to be added is null
	 */
	public void addState(STATE state) {
		if (state == null) {
			throw new NullPointerException(
					"The state to be added cannot be null");
		}
		this.automataGraph.addVertex(state);
	}

	/**
	 * returns the {@link Set} of the {@link State}s of the {@link BA}
	 * 
	 * @return the {@link Set} of the {@link State}s of the {@link BA}
	 */
	public Set<STATE> getStates() {
		return Collections.unmodifiableSet(new HashSet<STATE>(
				this.automataGraph.getVertices()));
	}

	/**
	 * sets the {@link Proposition}s of the {@link BA}
	 * 
	 * @param propositions
	 *            the {@link Proposition} of the {@link BA}
	 * @throws NullPointerException
	 *             if the {@link Set} of the {@link Proposition}s is null or if
	 *             there exists a proposition in the {@link Set} which is null
	 */
	public void setPropositions(Set<Proposition> propositions) {
		this.alphabet = new HashSet<Proposition>();
		this.addPropositions(propositions);
	}

	/**
	 * adds the {@link Set} of the {@link Proposition} to the
	 * {@link Proposition} of the {@link BA}
	 * 
	 * @param propositions
	 *            the {@link Proposition} to be added to the {@link BA}
	 * @throws NullPointerException
	 *             if the {@link Set} of the {@link Proposition}s is null or if
	 *             there exists a proposition in the {@link Set} which is null
	 */
	public void addPropositions(Set<Proposition> propositions) {
		if (propositions == null) {
			throw new NullPointerException(
					"The set of the proposition cannot be null");
		}
		for (Proposition p : propositions) {
			this.addProposition(p);
		}
	}

	/**
	 * adds the {@link Proposition} to the alphabet of the {@link BA}
	 * 
	 * @param proposition
	 *            is the {@link Proposition} to be added in the alphabet of the
	 *            {@link BA}
	 * @throws IllegalArgumentException
	 *             if the {@link Proposition} is null or if the
	 *             {@link Proposition} is already contained into the
	 *             {@link Proposition}s of the {@link BA}
	 */
	public void addProposition(Proposition proposition) {
		if (proposition == null) {
			throw new IllegalArgumentException(
					"The proposition to be inserted cannot be null");
		}
		this.alphabet.add(proposition);
	}

	/**
	 * Returns the {@link Set} which contains the {@link Proposition} that is
	 * the alphabet of the {@link BAImpl}
	 * 
	 * @return the {@link Set} which contains the {@link Proposition} that is
	 *         the alphabet of the {@link BAImpl}
	 */
	public Set<Proposition> getPropositions() {
		return Collections.unmodifiableSet(alphabet);
	}

	/**
	 * add the transition t, with source source to the set of the transitions of
	 * the automaton
	 * 
	 * @param source
	 *            is the source {@link State} of the transition
	 * @param destination
	 *            is the destination {@link State} of the transition
	 * @param transition
	 *            is the {@link Transition} to be added
	 * @throws NullPointerException
	 *             if the source {@link State}, the destination {@link State} or
	 *             the {@link Transition} is null
	 * 
	 * @throws IllegalArgumentException
	 *             is generated in one of the following cases <br/>
	 *             the source is null <br/>
	 *             the transition is null <br/>
	 *             the character of the transition is not contained in the
	 *             alphabet of the automaton <br/>
	 *             the source is not contained into the set of the states of the
	 *             automaton <br/>
	 *             the destination of the transition is not contained into the
	 *             set of the states of the automaton <br/>
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

		this.alphabet.addAll(transition.getCondition().getPropositions());

		if (this.automataGraph.isSuccessor(source, destination)) {
			// removes the old transition
			TRANSITION t = this.automataGraph.findEdge(source, destination);
			this.automataGraph.removeEdge(t);

			// creates the new transition
			DNFFormula f = new DNFFormulaImpl();
			f.addDisjunctionClause(t.getCondition());
			f.addDisjunctionClauses(transition.getCondition()
					.getConjunctiveClauses());
			TRANSITION newTransition = this.transitionFactory.create(f);

			// adds the new transition
			this.automataGraph.addEdge(newTransition, source, destination);
		} else {
			this.automataGraph.addEdge(transition, source, destination,
					EdgeType.DIRECTED);
		}
	}

	/**
	 * resets the automaton, removes its states, its initial states, the
	 * accepting states, the transitions and the alphabet
	 */
	public void reset() {
		this.initialStates = new HashSet<STATE>();
		this.alphabet = new HashSet<Proposition>();
		this.acceptStates = new HashSet<STATE>();
		this.automataGraph = new DirectedSparseGraph<STATE, TRANSITION>();
		this.alphabet.clear();
	}

	
	
	

	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.uci.ics.jung.graph.AbstractTypedGraph#getDefaultEdgeType()
	 */
	public EdgeType getDefaultEdgeType() {
		return EdgeType.DIRECTED;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * edu.uci.ics.jung.graph.DirectedSparseGraph#removeVertex(java.lang.Object)
	 */
	protected boolean removeVertex(STATE vertex) {
		if (this.initialStates.contains(vertex)) {
			this.initialStates.remove(vertex);
		}
		if (this.acceptStates.contains(vertex)) {
			this.acceptStates.remove(vertex);
		}
		return this.automataGraph.removeVertex(vertex);
	}

	/**
	 * returns the transitions that exits the {@link State} state
	 * 
	 * @return the transitions that exits the {@link State} state
	 */
	public Set<TRANSITION> getOutTransitions(STATE state) {
		return new HashSet<TRANSITION>(this.automataGraph.getOutEdges(state));
	}

	/**
	 * returns the destination of the {@link Transition} transition
	 * 
	 * @return the {@link State} which is the destination of the
	 *         {@link Transition} transition
	 */
	public STATE getTransitionDestination(TRANSITION transition) {
		return this.automataGraph.getDest(transition);
	}

	
	public TransitionFactory<TRANSITION> getTransitionFactory() {
		return this.transitionFactory;
	}
}
