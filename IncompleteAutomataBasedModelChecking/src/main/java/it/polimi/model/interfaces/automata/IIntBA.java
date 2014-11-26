package it.polimi.model.interfaces.automata;

import java.util.Set;

import it.polimi.model.impl.automata.IntBAImpl;
import it.polimi.model.impl.states.IntersectionState;
import it.polimi.model.impl.states.State;
import it.polimi.model.impl.transitions.Transition;

/**
 * is the interface the {@link IntBAImpl} must implement
 * @author claudiomenghi
 *
 * @param <STATE> is the type of the states of the two original automata
 * @param <TRANSITION> is the type of the transitions of the two original automata
 * @param <INTERSECTIONSTATE> is the type of the states of the intersection automata
 * @param <INTERSECTIONTRANSITION> is the type of the transitions of the intersection automata
 */
public interface IIntBA<
	STATE extends State, 
	TRANSITION extends Transition,
	INTERSECTIONSTATE extends IntersectionState<STATE>, 
	INTERSECTIONTRANSITION extends Transition>
	extends IBA<INTERSECTIONSTATE,INTERSECTIONTRANSITION> {

	/**
	 * returns true if the state s is mixed, false otherwise
	 * @param s the state to be checked if transparent or not
	 * @return true if the state s is mixed, false otherwise
	 * @throws IllegalArgumentException if the state s is null
	 */
	public boolean isMixed(INTERSECTIONSTATE s);
	
	/**
	 * adds the mixed state s to the set of the mixed states of the {@link IIntBA}
	 * @param s the mixed state to be added in the {@link IIntBA}
	 */
	public void addMixedState(INTERSECTIONSTATE s);
	
	/**
	 * returns the set of the mixed states
	 * @return the set of the mixed states
	 */
	public Set<INTERSECTIONSTATE> getMixedStates();
	
}
