package it.polimi.model.interfaces.automata;

import it.polimi.model.impl.labeling.Proposition;
import it.polimi.model.impl.states.State;
import it.polimi.model.impl.transitions.Transition;
import it.polimi.model.interfaces.transitions.TransitionFactory;

import java.util.Set;

import edu.uci.ics.jung.graph.DirectedSparseGraph;

/**
 * is the interface of the {@link BA}
 * 
 * @author claudiomenghi
 * 
 * @param <STATE>
 *            is the type of the {@link State} of the {@link BA}
 * @param <TRANSITION>
 *            is the type of the {@link Transition} of the {@link BA}
 */
public interface BA<STATE extends State, TRANSITION extends Transition> extends
		Cloneable {

	/**
	 * returns the initial {@link State}s of the {@link BA}
	 * 
	 * @return the initial {@link State}s of the {@link BA}
	 */
	public Set<STATE> getInitialStates();

	/**
	 * returns the alphabet of the {@link BA}
	 * 
	 * @return the alphabet of the {@link BA}
	 */
	public Set<Proposition> getPropositions();

	public TransitionFactory<TRANSITION> getTransitionFactory();

	/**
	 * return the {@link Transition}s that exits the {@link State} state
	 * 
	 * @param state
	 *            is the {@link State} under analysis
	 * 
	 * @return the {@link Transition}s that exits the {@link State} state
	 * 
	 * @throws NullPointerException
	 *             if the {@link State} is null
	 * @throws IllegalArgumentException
	 *             if the {@link State} is not contained into the {@link Set} of
	 *             {@link State}s of the {@link BA}
	 */
	public Set<TRANSITION> getOutTransitions(STATE state);

	/**
	 * returns the destination of the {@link Transition}
	 * 
	 * @param transition
	 *            is the transition to be analyzed
	 * @return the {@link State} which is destination of the {@link Transition}
	 *         transition
	 */
	public STATE getTransitionDestination(TRANSITION transition);

	/**
	 * Returns the set of accepting states of the {@link BA}.
	 * 
	 * @return set of the accepting states of the {@link BA} (see {@link State})
	 */
	public Set<STATE> getAcceptStates();

	/**
	 * returns true if the {@link State} state is accepting false otherwise
	 * 
	 * @param state
	 *            is the {@link State} to be checked if accepting
	 * @return true if the {@link State} state is accepting, false otherwise
	 */
	public boolean isAccept(STATE state);

	/**
	 * check is the state s is contained into the set of the initial states of
	 * the automaton
	 * 
	 * @param s
	 *            the state to be checked if present
	 * @return true if the state s is contained into the set of the initial
	 *         states of the automaton, false otherwise
	 * @throws IllegalArgumentException
	 *             if the state s is null
	 */
	public boolean isInitial(STATE s);

	/**
	 * adds the initial state S to the {@link BA}
	 * 
	 * @param S
	 *            the state to be added as initial state
	 */
	public void addInitialState(STATE S);

	/**
	 * adds the accepting state S to the {@link BA}
	 * 
	 * @param S
	 *            the state to be added as accepting state
	 */
	public void addAcceptState(STATE s);

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
	public void addPropositions(Set<Proposition> propositions);

	/**
	 * adds the {@link State} state to the {@link Set} of the {@link State}
	 * present in the {@link DirectedSparseGraph}
	 * 
	 * @param state
	 *            is the {@link State} to be added to the {@link BA}
	 * @throws NullPointerException
	 *             if the {@link State} to be added is null
	 */
	public void addState(STATE state);

	/**
	 * returns the {@link Set} of the {@link State}s of the {@link BA}
	 * 
	 * @return the {@link Set} of the {@link State}s of the {@link BA}
	 */
	public Set<STATE> getStates();

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
			TRANSITION transition);
	
	
	
}
