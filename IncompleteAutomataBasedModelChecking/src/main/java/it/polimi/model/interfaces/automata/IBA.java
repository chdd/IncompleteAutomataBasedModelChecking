package it.polimi.model.interfaces.automata;

import it.polimi.model.elements.states.State;
import it.polimi.model.impl.transitions.LabelledTransition;
import it.polimi.model.interfaces.transitions.LabelledTransitionFactoryInterface;

import java.util.Set;

/**
 * is the interface of an incomplete Buchi automaton {@link IBA}
 * @author claudiomenghi
 *
 * @param <S> is the type of the states of the {@link IBA}
 * @param <T> is the type of the transitions of the {@link IBA}
 */
public interface IBA<S extends State, T extends LabelledTransition, TFactory extends LabelledTransitionFactoryInterface<T>> extends BA<S, T, TFactory> {

	/**
	 * check if the state is transparent
	 * @param s is the state to be checked if transparent
	 * @return true if the state s is transparent, false otherwise
	 * @throws IllegalArgumentException if the state s is null
	 */
	public boolean isTransparent(S s);
	
	/**
	 * returns the set of the transparent states of the {@link IBA}
	 * @return the set of the transparent states of the {@link IBA} (if no transparent states are present an empty set is returned)
	 */
	public Set<S>  getTransparentStates();
}
