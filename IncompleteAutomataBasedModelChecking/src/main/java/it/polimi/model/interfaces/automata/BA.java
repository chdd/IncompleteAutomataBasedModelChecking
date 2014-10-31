package it.polimi.model.interfaces.automata;

import it.polimi.model.impl.states.State;
import it.polimi.model.impl.transitions.LabelledTransition;
import it.polimi.model.interfaces.transitions.LabelledTransitionFactory;

import java.util.Set;

import rwth.i2.ltl2ba4j.model.IGraphProposition;

/**
 * is the interface of the {@link BA}
 * @author claudiomenghi
 *
 * @param <STATE> is the type of the states of the {@link BA}
 * @param <TRANSITION> is the type of the transitions of the {@link BA}
 */
public interface BA<
	STATE extends State, 
	TRANSITION extends LabelledTransition, 
	TRANSITIONFACTORY extends LabelledTransitionFactory<TRANSITION>> {

	/**
	 * returns the initial states of the {@link BA}
	 * @return the initial states of the {@link BA}
	 */
	public Set<STATE> getInitialStates();
	
	/**
	 * returns the alphabet of the {@link BA}
	 * @return the alphabet of the {@link BA}
	 */
	public Set<IGraphProposition> getAlphabet();
	
	public TRANSITIONFACTORY getTransitionFactory();
		
	/**
	 * return the transitions that exits the {@link State} state
	 * @return the transitions that exits the {@link State} state
	 */
	public Set<TRANSITION> getOutTransition(STATE state);
	
	/**
	 * returns the destination of the {@link LabelledTransition} transition
	 * @param transition is the transition to be analyzed
	 * @return the {@link State} which is destination of the {@link LabelledTransition} transition
	 */
	public STATE getTransitionDestination(TRANSITION transition);
	
	/** 
	 * Returns the set of accepting states of the {@link BA}. 
	 * @return set of the accepting states of the {@link BA} (see {@link State})
	 */
	public Set<STATE> getAcceptStates();
	
	/**
	 * returns true if the {@link State} state is accepting false otherwise
	 * @param state is the {@link State} to be checked if accepting
	 * @return true if the {@link State} state is accepting, false otherwise
	 */
	public boolean isAccept(STATE state);
	
	/**
	 * check is the state s is contained into the set of the initial states of the automaton
	 * @param s the state to be checked if present
	 * @return true if the state s is contained into the set of the initial states of the automaton, false otherwise
	 * @throws IllegalArgumentException if the state s is null
	 */
	public boolean isInitial(STATE s);
	
	/**
	 * returns the number of the states of the {@link BA}
	 * @return the number of the states of the {@link BA}
	 */
	public int getStateNumber();
	
	/**
	 * adds the initial state S to the {@link BA}
	 * @param S the state to be added as initial state
	 */
	public void addInitialState(STATE S);
	
	/**
	 * adds the accepting state S to the {@link BA}
	 * @param S the state to be added as accepting state
	 */
	public void addAcceptState(STATE s);
	
	public STATE getState(int id);
}
