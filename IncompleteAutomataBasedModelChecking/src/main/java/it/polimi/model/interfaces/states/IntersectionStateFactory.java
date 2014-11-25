package it.polimi.model.interfaces.states;

import it.polimi.model.impl.states.IntersectionState;
import it.polimi.model.impl.states.State;

public interface IntersectionStateFactory<STATE extends State, INTERSECTIONSTATE extends IntersectionState<STATE>> 
extends StateFactory<INTERSECTIONSTATE> {

	/**
	 * creates a new {@link IntersectionState} starting from the two
	 * {@link State}s s1 and s2 and with number num
	 * 
	 * @param s1
	 *            is the first {@link State} of the automaton
	 * @param s2
	 *            is the second {@link State} of the automaton
	 * @param num
	 *            is the number which is associated to the state
	 * @return a new {@link IntersectionState}
	 * 
	 * @throws NullPointerException
	 *             if the {@link State} s1 or the {@link State} s2 is null
	 * @throws IllegalArgumentException
	 *             if num is less than 0
	 */
	public IntersectionState<State> create(State s1, State s2, int num);
}
