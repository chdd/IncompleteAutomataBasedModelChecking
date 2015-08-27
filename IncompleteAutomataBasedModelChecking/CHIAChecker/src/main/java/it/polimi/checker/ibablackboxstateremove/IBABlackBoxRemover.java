package it.polimi.checker.ibablackboxstateremove;

import it.polimi.automata.IBA;
import it.polimi.automata.state.State;

import java.util.HashSet;
import java.util.Set;

import com.google.common.base.Preconditions;

/**
 * this class starting from an Incomplete Buchi automaton generates an
 * equivalent Incomplete Buchi Automaton where the black box states and their
 * incoming and out-coming transitions are simply removed
 * 
 * @author claudiomenghi
 */
public class IBABlackBoxRemover {

	/**
	 * contains the IBA from which the black box states must be removed
	 */
	private final IBA iba;
	/**
	 * creates a black bos state remover
	 * 
	 * @param iba
	 *            is the Incomplete Buchi Automaton to be modified
	 * @throws NullPointerException
	 *             if the Incomplete Buchi Automaton is null
	 */
	public IBABlackBoxRemover(IBA iba) {
		Preconditions.checkNotNull(iba,
				"The Incomplete Buchi Automaton cannot be null");
		this.iba=iba;

	}

	/**
	 * removes from the Incomplete Buchi Automaton all the black box states
	 * and the corresponding incoming and out coming transitions
	 * 
	 * @return a modified version of the Incomplete Buchi Automaton where the
	 *         black box states are removed. The initial Incomplete Buchi
	 *         Automaton is not modified
	 */
	public IBA removeBlackBoxes() {
		
		// clones the incomplete Buchi Automaton
		IBA retIba = iba.clone();
		// removes the black box states from the Buchi Automaton
		Set<State> blackBoxStates = new HashSet<State>(
				retIba.getBlackBoxStates());
		for (State s : blackBoxStates) {
			retIba.removeState(s);
		}
		// returns the new Buchi Automaton
		return retIba;
	}

}
