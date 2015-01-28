package it.polimi.automata.io;

import it.polimi.automata.BA;
import it.polimi.automata.labeling.Label;
import it.polimi.automata.state.State;
import it.polimi.automata.transition.Transition;

import org.apache.commons.collections15.Transformer;

/**
 * contains the Transformer that given a State returns a true which is "true" or
 * false depending on whether the state is accepting or not
 * 
 * @see Transformer
 * 
 * @author claudiomenghi
 * 
 * @param <L>
 *            is the type of the Label which is applied to the transitions of
 *            the Buchi Automaton which must implement the interface
 *            {@link Label}
 * @param <S>
 *            is the type of the State of the Buchi Automaton. It must extend
 *            the interface {@link State}
 * @param <T>
 *            is the type of the transitions of the automaton. It must implement
 *            the interface {@link Transition}
 */
class BAStateAcceptingToStringTransformer<L extends Label, S extends State, T extends Transition<L>>
		implements Transformer<S, String> {

	/**
	 * is the Buchi Automaton to be transformed
	 */
	private BA<L, S, T> ba;

	/**
	 * creates the Transformer
	 * 
	 * @param ba
	 *            is the Buchi Automaton where the state is placed
	 * @throws NullPointerException
	 *             if the Buchi Automaton is null
	 */
	public BAStateAcceptingToStringTransformer(BA<L, S, T> ba) {
		if (ba == null) {
			throw new NullPointerException("The ba cannot be null");
		}
		this.ba = ba;
	}

	/**
	 * returns "true" if the state is accepting, "false" otherwise
	 * 
	 * @return "true" if the state is accepting, "false" otherwise
	 * @throws NullPointerException
	 *             if the state to be transformed is null
	 */
	@Override
	public String transform(S state) {
		if(state==null){
			throw new NullPointerException("The state to be transformed cannot be null");
		}
		return Boolean.toString(ba.getAcceptStates().contains(state));
	}
}