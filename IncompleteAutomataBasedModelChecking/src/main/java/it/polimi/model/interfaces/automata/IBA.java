package it.polimi.model.interfaces.automata;

import it.polimi.model.impl.states.State;
import it.polimi.model.impl.transitions.LabelledTransition;
import it.polimi.model.interfaces.transitions.LabelledTransitionFactory;

import java.util.Set;

/**
 * is the interface of an incomplete Buchi automaton {@link IBA}
 * @author claudiomenghi
 *
 * @param <STATE> is the type of the states of the {@link IBA}
 * @param <TRANSITION> is the type of the transitions of the {@link IBA}
 */
public interface IBA<
	STATE extends State, 
	TRANSITION extends LabelledTransition, 
	TRANSITIONFACTORY extends LabelledTransitionFactory<TRANSITION>> 
	extends BA<STATE, TRANSITION, TRANSITIONFACTORY> {

	/**
	 * check if the state is transparent
	 * @param s is the state to be checked if transparent
	 * @return true if the state s is transparent, false otherwise
	 * @throws IllegalArgumentException if the state s is null
	 */
	public boolean isTransparent(STATE s);
	
	/**
	 * returns the set of the transparent states of the {@link IBA}
	 * @return the set of the transparent states of the {@link IBA} (if no transparent states are present an empty set is returned)
	 */
	public Set<STATE>  getTransparentStates();
	
	/**
	 * adds the transparent state s to the states of the {@link IBA}
	 * @param s the state to be added in the {@link IBA}
	 */
	public void addTransparentState(STATE s);
}
