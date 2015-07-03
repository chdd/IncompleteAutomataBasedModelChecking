package it.polimi.checker.abstractor;

import it.polimi.automata.BA;
import it.polimi.automata.state.State;

import java.util.Set;

import com.google.common.base.Preconditions;

/**
 * creates an abstraction version of the automaton A which only contains the set
 * of states passed as parameter
 * 
 * @author Claudio Menghi
 *
 * @param <A>
 *            the type of the automaton to be abstracted
 */
public class Abstractor<A extends BA> {

	/**
	 * performs the abstraction
	 * 
	 * @param ba
	 *            is the type of the automaton to be abstracted
	 * @param states
	 *            is the set of the states to be maintained in the automaton
	 * @return the abstracted version of the automaton where only the states and
	 *         their incoming and outgoing transitions are present
	 * @throws NullPointerException
	 *             if the automaton or the set of the states is null
	 * @throws IllegalArgumentException
	 *             if the set of the states is not contained in the automaton
	 */
	public A perform(A ba, Set<State> states) {
		Preconditions.checkNotNull(ba,
				"The automaton to be considered cannot be null");
		Preconditions.checkNotNull(states,
				"The set of the states to be maintained cannot be null");
		Preconditions
				.checkArgument(ba.getStates().containsAll(states),
						"All the states in the set states must be contained in the automaton");

		// clones the incomplete Buchi Automaton
		@SuppressWarnings("unchecked")
		A retIba = (A) ba.clone();
		// removes the transparent states from the Buchi Automaton
		for (State s : ba.getStates()) {
			if (!states.contains(s)) {
				retIba.removeState(s);
			}
		}
		// returns the new Buchi Automaton
		return retIba;
	}

}
