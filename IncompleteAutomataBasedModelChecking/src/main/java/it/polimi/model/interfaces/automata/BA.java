package it.polimi.model.interfaces.automata;

import it.polimi.model.elements.states.State;
import it.polimi.model.impl.transitions.LabelledTransition;
import it.polimi.model.interfaces.transitions.LabelledTransitionFactoryInterface;

import java.util.Set;

import rwth.i2.ltl2ba4j.model.IGraphProposition;

/**
 * is the interface of the {@link BA}
 * @author claudiomenghi
 *
 * @param <S> is the type of the states of the {@link BA}
 * @param <T> is the type of the transitions of the {@link BA}
 */
public interface BA<S extends State, T extends LabelledTransition, TFactory extends LabelledTransitionFactoryInterface<T>> {

	/**
	 * returns the initial states of the {@link BA}
	 * @return the initial states of the {@link BA}
	 */
	public Set<S> getInitialStates();
	
	/**
	 * returns the alphabet of the {@link BA}
	 * @return the alphabet of the {@link BA}
	 */
	public Set<IGraphProposition> getAlphabet();
		
	/**
	 * return the transitions that exits the {@link State} state
	 * @return the transitions that exits the {@link State} state
	 */
	public Set<T> getOutTransition(S state);
	
	/**
	 * returns the destination of the {@link LabelledTransition} transition
	 * @param transition is the transition to be analyzed
	 * @return the {@link State} which is destination of the {@link LabelledTransition} transition
	 */
	public S getTransitionDestination(T transition);
	
	/** 
	 * Returns the set of accepting states of the {@link BA}. 
	 * @return set of the accepting states of the {@link BA} (see {@link State})
	 */
	public Set<S> getAcceptStates();
	
	/**
	 * returns true if the {@link State} state is accepting false otherwise
	 * @param state is the {@link State} to be checked if accepting
	 * @return true if the {@link State} state is accepting, false otherwise
	 */
	public boolean isAccept(S state);
	
	/**
	 * check is the state s is contained into the set of the initial states of the automaton
	 * @param s the state to be checked if present
	 * @return true if the state s is contained into the set of the initial states of the automaton, false otherwise
	 * @throws IllegalArgumentException if the state s is null
	 */
	public boolean isInitial(S s);
	
	/**
	 * returns the number of the states of the {@link BA}
	 * @return the number of the states of the {@link BA}
	 */
	public int getStateNumber();
}
