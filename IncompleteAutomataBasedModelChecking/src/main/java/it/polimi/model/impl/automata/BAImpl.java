package it.polimi.model.impl.automata;

import it.polimi.model.impl.labeling.Proposition;
import it.polimi.model.impl.states.State;
import it.polimi.model.impl.transitions.Transition;
import it.polimi.model.interfaces.automata.BA;
import it.polimi.model.interfaces.states.StateFactory;
import it.polimi.model.interfaces.transitions.TransitionFactory;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.Stack;

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
	 * contains the initial states of the {@link BA}
	 */
	protected Set<STATE> initialStates;

	/**
	 * contains the {@link StateFactory} which allows to create the
	 * {@link State} of the {@link BA}
	 */
	protected StateFactory<STATE> stateFactory;

	/**
	 * contains the set of the {@link Proposition} of the {@link BA}
	 */
	protected Set<Proposition> alphabet;

	/**
	 * contains the graph on which the {@link BA} is based
	 */
	protected DirectedSparseGraph<STATE, TRANSITION> automataGraph;

	/**
	 * contains the accepting {@link State}s of the {@link BA}
	 */
	protected Set<STATE> acceptStates;

	/**
	 * contains the {@link Factory} which allows to create the
	 * {@link Transition}s of the {@link BA}
	 */
	protected TransitionFactory<TRANSITION> transitionFactory;

	// TODO to be removed
	protected Map<Integer, STATE> mapNameState;

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
		this.mapNameState = new HashMap<Integer, STATE>();
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
		this.addVertex(s);
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
	 * adds the {@link Set} of the {@link State} to the accepting States of the
	 * {@link BA}
	 * 
	 * @param states
	 *            the {@link Set} of the {@link State} to be added to the
	 *            {@link Set} of the accepting {@link State}s of the {@link BA}
	 * @throws NullPointerException
	 *             if the {@link Set} of the {@link State} is null
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
		this.addVertex(s);
	}

	/**
	 * check is the {@link State} s is contained into the set of the accept
	 * {@link State} of the automaton
	 * 
	 * @param s
	 *            the {@link State} to be checked if accepting
	 * @return true if the {@link State}s is contained into the {@link Set} of the accept
	 *         {@link State}s of the automaton, false otherwise
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
	 * returns the {@link Set} of the {@link State}s of the {@link BA}
	 * 
	 * @return the {@link Set} of the {@link State}s of the {@link BA}
	 */
	public Set<STATE> getStates() {
		return Collections.unmodifiableSet(new HashSet<STATE>(
				this.automataGraph.getVertices()));
	}

	public void setAlphabet(Set<Proposition> propositions) {
		this.alphabet = new HashSet<Proposition>();
		for (Proposition p : propositions) {
			this.addCharacter(p);
		}
	}

	public void setAcceptStates(Set<STATE> acceptStates) {
		this.acceptStates = new HashSet<STATE>();
		for (STATE s : acceptStates) {
			this.addAcceptState(s);
		}
	}

	/**
	 * Returns the alphabet of the {@link BAImpl}
	 * 
	 * @return the alphabet of the {@link BAImpl}
	 */
	public Set<Proposition> getAlphabet() {
		return alphabet;
	}

	/**
	 * adds the character character in the alphabet of the automaton
	 * 
	 * @param character
	 *            is the character to be added in the alphabet of the automaton
	 * @throws IllegalArgumentException
	 *             if the character is null or if the character is already
	 *             contained into the alphabet of the automaton
	 */
	public void addCharacter(Proposition character) {
		if (character == null) {
			throw new IllegalArgumentException(
					"The character to be inserted into the alphabet cannot be null");
		}
		this.alphabet.add(character);
	}

	public void addCharacters(Set<Proposition> characters) {
		if (characters == null) {
			throw new IllegalArgumentException(
					"The character to be inserted into the alphabet cannot be null");
		}
		for (Proposition character : characters) {
			this.addCharacter(character);
		}
	}

	public void setStates(Set<STATE> states) {
		for (STATE s : states) {
			this.addVertex(s);
		}
	}

	/**
	 * add the transition t, with source source to the set of the transitions of
	 * the automaton
	 * 
	 * @param source
	 *            is the source of the transition
	 * @param t
	 *            is the transition to be added
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
		this.alphabet.addAll(transition.getCondition().getPropositions());

		if (this.automataGraph.isSuccessor(source, destination)) {
			TRANSITION t = this.automataGraph.findEdge(source, destination);
			this.automataGraph.removeEdge(t);

			// TODO to be changed the merging of the transitions
			/*
			 * TRANSITION t=this.transitionFactory.create( new dnfFormula)
			 * .getCondition() .addDisjunctionClause(
			 * transition.getCondition().getConjunctiveClauses());
			 */
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

	/**
	 * generates a new random graph (note that almost every graph is connected
	 * with the parameters n, 2ln(n)/n
	 * 
	 * @param n
	 *            : number of nodes
	 * @param p
	 *            : probability through which each transition is included in the
	 *            graph
	 * @return a new random graph
	 */
	public void getRandomAutomaton2(int n, double transitionProbability,
			int numInitial, int numAccepting, Set<Proposition> alphabet) {
		if (transitionProbability >= 1 || transitionProbability < 0) {
			throw new IllegalArgumentException(
					"The value of p must be included in the trange [0,1]");
		}

		this.reset();
		this.addCharacters(alphabet);
		Random r = new Random();

		for (int i = 0; i < n; i++) {

			STATE s = stateFactory.create("s" + i);
			this.addVertex(s);
		}

		for (int i = 0; i < numInitial; i++) {
			int transp = r.nextInt(this.automataGraph.getVertices().size());
			Iterator<STATE> it = this.automataGraph.getVertices().iterator();
			for (int j = 0; j < transp; j++) {
				it.next();
			}
			this.addInitialState(it.next());
		}
		for (int i = 0; i < numAccepting; i++) {
			int transp = r.nextInt(this.automataGraph.getVertices().size());
			Iterator<STATE> it = this.automataGraph.getVertices().iterator();
			for (int j = 0; j < transp; j++) {
				it.next();
			}
			this.addAcceptState(it.next());
		}
		for (STATE s1 : this.automataGraph.getVertices()) {
			for (STATE s2 : this.automataGraph.getVertices()) {
				double randInt = r.nextInt(11) / 10.0;
				if (randInt <= transitionProbability) {

					Proposition character = this.getRandomString(alphabet,
							r.nextInt(alphabet.size()));
					// TODO to be changed the merging of the transitions
					/*
					 * this.addTransition( s1, s2, this.transitionFactory
					 * .create(new DNFFormula<CONSTRAINEDELEMENT>( new
					 * ConjunctiveClauseImpl<CONSTRAINEDELEMENT>( character))));
					 */

				}

			}
		}
	}

	public Proposition getRandomString(Set<Proposition> alphabet, int position) {

		Iterator<Proposition> it = alphabet.iterator();
		for (int i = 0; i < position; i++) {

			it.next();
		}
		return it.next();
	}

	protected Stack<STATE> stack;
	protected Stack<TRANSITION> stacktransitions;

	/**
	 * returns true if the automaton is empty
	 * 
	 * @return true if the automaton is empty
	 */
	public boolean isEmpty() {
		boolean res = true;
		Set<STATE> visitedStates = new HashSet<STATE>();
		this.stacktransitions = new Stack<TRANSITION>();
		for (STATE init : this.getInitialStates()) {
			stack = new Stack<STATE>();
			if (firstDFS(visitedStates, init, stack)) {

				return false;
			}
		}
		// clear the set of the visited states
		visitedStates.clear();
		return res;
	}

	/**
	 * returns true if an accepting path is found
	 * 
	 * @param visitedStates
	 *            contains the set of the visited states by the algorithm
	 * @param currState
	 *            is the current states under analysis
	 * @return true if an accepting path is found, false otherwise
	 */
	protected boolean firstDFS(Set<STATE> visitedStates, STATE currState,
			Stack<STATE> statesOfThePath) {
		// if the current state have been already visited (and the second DFS
		// has not been started) it means that the path is not accepting
		if (visitedStates.contains(currState)) {
			return false;
		} else {
			// I add the state in the set of visited states
			visitedStates.add(currState);
			// I add the state in the state of the path
			statesOfThePath.push(currState);
			// if the state is accepting
			if (this.isAccept(currState)) {
				for (TRANSITION t : this.automataGraph.getOutEdges(currState)) {

					this.stacktransitions.add(t);
					Stack<STATE> stackSecondDFS = new Stack<STATE>();
					// I start the second DFS if the answer of the second DFS is
					// true I return true
					if (this.secondDFS(new HashSet<STATE>(),
							this.automataGraph.getDest(t), statesOfThePath,
							stackSecondDFS)) {
						statesOfThePath.addAll(stackSecondDFS);
						return true;
					}
					this.stacktransitions.remove(t);
				}
			}
			// otherwise, I check each transition that leaves the state
			// currState
			for (TRANSITION t : this.automataGraph.getOutEdges(currState)) {
				this.stacktransitions.add(t);
				// I call the first DFS method, If the answer is true I return
				// true
				if (firstDFS(visitedStates, this.automataGraph.getDest(t),
						statesOfThePath)) {
					return true;
				}
				this.stacktransitions.remove(t);

			}
			// I remove the state from the stack of the states of the current
			// path
			statesOfThePath.pop();
			return false;
		}
	}

	/**
	 * contains the second DFS procedure
	 * 
	 * @param visitedStates
	 *            contains the set of the states visited in the SECOND DFS
	 *            procedure
	 * @param currState
	 *            is the current state under analysis
	 * @param statesOfThePath
	 *            is the state of the path that is currently analyzed
	 * @return true if an accepting path is found (a path that contains a state
	 *         in the set of the states statesOfThePath), false otherwise
	 */
	// note that at the beginning the visited states do not contain the current
	// state
	protected boolean secondDFS(Set<STATE> visitedStates, STATE currState,
			Stack<STATE> statesOfThePath, Stack<STATE> stackSecondDFS) {
		// if the state is in the set of the states on the path the an accepting
		// path is found
		if (statesOfThePath.contains(currState)) {
			return true;
		} else {
			// if the state is in the set of the visited states of the second
			// DFS, the path is not accepting
			if (visitedStates.contains(currState)) {
				return false;
			} else {
				stackSecondDFS.push(currState);
				// add the state into the set of the visited states
				visitedStates.add(currState);
				// for each transition that leaves the current state
				for (TRANSITION t : this.automataGraph.getOutEdges(currState)) {

					this.stacktransitions.add(t);
					// if the second DFS returns a true answer than the
					// accepting path has been found
					if (secondDFS(visitedStates, this.automataGraph.getDest(t),
							statesOfThePath, stackSecondDFS)) {
						return true;
					}
					this.stacktransitions.remove(t);
				}
				stackSecondDFS.pop();
				// otherwise the path is not accepting
				return false;
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.uci.ics.jung.graph.AbstractTypedGraph#getDefaultEdgeType()
	 */
	public EdgeType getDefaultEdgeType() {
		return EdgeType.DIRECTED;
	}

	/**
	 * adds the {@link State} state to the {@link Set} of the {@link State}
	 * present in the {@link DirectedSparseGraph}
	 * 
	 * @param state
	 *            is the {@link State} to be added to the {@link BA}
	 * @throws InternalError
	 *             if the {@link State} to be added is null
	 */
	protected void addVertex(STATE state) {
		if (state == null) {
			throw new InternalError("The state to be added cannot be null");
		}
		this.automataGraph.addVertex(state);
		this.mapNameState.put(state.getId(), state);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * edu.uci.ics.jung.graph.DirectedSparseGraph#removeVertex(java.lang.Object)
	 */
	public boolean removeVertex(STATE vertex) {
		if (this.initialStates.contains(vertex)) {
			this.initialStates.remove(vertex);
		}
		if (this.acceptStates.contains(vertex)) {
			this.acceptStates.remove(vertex);
		}
		return this.automataGraph.removeVertex(vertex);
	}

	/**
	 * returns the {@link State} with the specified id
	 * 
	 * @param id
	 *            is the id of the {@link State} to be searched
	 * @return the {@link State} with the specified id
	 * @throws IllegalArgumentException
	 *             if the id is not an id of the states of the automaton or if
	 *             the id is not grater than or equal to zero
	 * 
	 */
	public STATE getState(int id) {
		if (id < 0) {
			throw new IllegalArgumentException(
					"the id must be grater than or equal to zero");
		}
		if (!this.mapNameState.containsKey(id)) {
			throw new IllegalArgumentException(
					"The state with the id "
							+ id
							+ " is not contained in the set of the states of the automaton");
		}

		return this.mapNameState.get(id);
	}

	/**
	 * returns the transitions that exits the {@link State} state
	 * 
	 * @return the transitions that exits the {@link State} state
	 */
	public Set<TRANSITION> getOutTransition(STATE state) {
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

	/**
	 * returns the number of the states of the {@link BAImpl}
	 */
	public int getStateNumber() {
		return this.automataGraph.getVertexCount();
	}

	public TransitionFactory<TRANSITION> getTransitionFactory() {
		return this.transitionFactory;
	}
}
