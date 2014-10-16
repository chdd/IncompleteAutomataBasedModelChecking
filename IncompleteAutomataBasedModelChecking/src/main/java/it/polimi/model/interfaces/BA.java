package it.polimi.model.interfaces;

import it.polimi.model.automata.ba.transition.LabelledTransition;
import it.polimi.model.elements.states.State;

import java.util.Set;

import rwth.i2.ltl2ba4j.model.IGraphProposition;

/**
 * is the interface of the {@link BA}
 * @author claudiomenghi
 *
 * @param <S> is the type of the states of the {@link BA}
 * @param <T> is the type of the transitions of the {@link BA}
 */
public interface BA<S extends State, T extends LabelledTransition> {

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
	 * returns true if the {@link State} state is accepting false otherwise
	 * @param state is the {@link State} to be checked if accepting
	 * @return true if the {@link State} state is accepting, false otherwise
	 */
	public boolean isAccept(S state);
	
}
