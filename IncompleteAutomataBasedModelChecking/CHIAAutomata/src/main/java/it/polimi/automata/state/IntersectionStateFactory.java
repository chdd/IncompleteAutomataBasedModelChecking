package it.polimi.automata.state;

import com.google.common.base.Preconditions;

/**
 * is the state factory to be used in the creation of the intersection
 * automaton.
 * 
 * @author Claudio Menghi
 *
 */
public class IntersectionStateFactory extends AbstractStateFactory {

	/**
	 * creates a new state of the intersection automaton starting from the state
	 * of the model modelState, the state of the claim claimState specified as
	 * parameters and the number of the state
	 * 
	 * @param modelState
	 *            the state of the model from which the intersection state must
	 *            be created
	 * @param claimState
	 *            the state of the claim from which the intersection state must
	 *            be created
	 * @param num
	 *            the number that must be associated to the state of the
	 *            intersection automaton
	 * @return a new state of the intersection automaton
	 * @throws NullPointerException
	 *             if one of the parameters is null
	 * @throws IllegalArgumentException
	 *             if the number associated with the state is not between 0 and
	 *             2 included
	 */
	public State create(State modelState, State claimState, int num) {
		Preconditions.checkNotNull(modelState,
				"The state of the model cannot be null");
		Preconditions.checkNotNull(claimState,
				"The state of the intersection automaton cannot be null");
		Preconditions
				.checkArgument(num >= 0 && num <= 2,
						"The number must associated with the state must be between 0 and 2 included ");

		State s = new State(modelState.getId() + " - " + claimState.getId()
				+ " - " + num, StateFactory.stateCount);
		StateFactory.stateCount = StateFactory.stateCount + 1;
		return s;
	}
}
