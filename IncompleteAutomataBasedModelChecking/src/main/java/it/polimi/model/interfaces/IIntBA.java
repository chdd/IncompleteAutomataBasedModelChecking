package it.polimi.model.interfaces;

import it.polimi.model.automata.ba.transition.ConstrainedTransition;
import it.polimi.model.automata.ba.transition.LabelledTransition;
import it.polimi.model.elements.states.IntersectionState;
import it.polimi.model.elements.states.State;
import it.polimi.model.impl.automata.IntBAImpl;

/**
 * is the interface the {@link IntBAImpl} must implement
 * @author claudiomenghi
 *
 * @param <S1> is the type of the states of the two original automata
 * @param <T1> is the type of the transitions of the two original automata
 * @param <S> is the type of the states of the intersection automata
 * @param <T> is the type of the transitions of the intersection automata
 */
public interface IIntBA<S1 extends State, T1 extends LabelledTransition,S extends IntersectionState<S1>, T 
extends ConstrainedTransition<S1>> extends IBA<S,T> {

	/**
	 * returns true if the state s is mixed, false otherwise
	 * @param s the state to be checked if transparent or not
	 * @return true if the state s is mixed, false otherwise
	 * @throws IllegalArgumentException if the state s is null
	 */
	public boolean isMixed(S s);
	
}
