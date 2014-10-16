package it.polimi.model.interfaces;

import it.polimi.model.automata.ba.transition.LabelledTransition;
import it.polimi.model.elements.states.State;

/**
 * is the interface of an incomplete Buchi automaton {@link IBA}
 * @author claudiomenghi
 *
 * @param <S> is the type of the states of the {@link IBA}
 * @param <T> is the type of the transitions of the {@link IBA}
 */
public interface IBA<S extends State, T extends LabelledTransition> extends BA<S, T> {

	/**
	 * check if the state is transparent
	 * @param s is the state to be checked if transparent
	 * @return true if the state s is transparent, false otherwise
	 * @throws IllegalArgumentException if the state s is null
	 */
	public boolean isTransparent(S s);
}
