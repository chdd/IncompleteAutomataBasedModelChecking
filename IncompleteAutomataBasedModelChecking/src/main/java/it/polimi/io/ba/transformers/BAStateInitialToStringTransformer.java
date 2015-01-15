package it.polimi.io.ba.transformers;

import it.polimi.automata.BA;
import it.polimi.automata.State;
import it.polimi.automata.Transition;
import it.polimi.automata.labeling.Label;

import org.apache.commons.collections15.Transformer;

/**
 * contains the Transformer that given a State returns a true which is "true" or
 * false depending on whether the state is initial or not
 * 
 * @see Transformer
 * 
 * @author claudiomenghi
 * 
 * @param <LABEL>
 *            is the type of the Label which is applied to the transitions of
 *            the Buchi Automaton which must implement the interface
 *            {@link Label}
 * @param <STATE>
 *            is the type of the State of the Buchi Automaton. It must extend
 *            the interface {@link State}
 * @param <TRANSITION>
 *            is the type of the transitions of the automaton. It must implement
 *            the interface {@link Transition}
 */
public class BAStateInitialToStringTransformer<LABEL extends Label, STATE extends State, TRANSITION extends Transition<LABEL>> implements Transformer<STATE, String>{

	/**
	 * is the Buchi Automaton to be transformed
	 */
	private BA<LABEL, STATE, TRANSITION> ba;
	
	/**
	 * creates the Transformer
	 * 
	 * @param ba
	 *            is the Buchi Automaton where the state is placed
	 * @throws NullPointerException
	 *             if the Buchi Automaton is null
	 */
	public BAStateInitialToStringTransformer(BA<LABEL, STATE, TRANSITION> ba){
		if(ba==null){
			throw new NullPointerException("The ba cannot be null");
		}
		this.ba=ba;
	}
	
	/**
	 * returns "true" if the state is initial, "false" otherwise
	 * 
	 * @return "true" if the state is initial, "false" otherwise
	 * @throws NullPointerException
	 *             if the state to be transformed is null
	 */
	@Override
	public String transform(STATE state) {
		if(state==null){
			throw new NullPointerException("The state to be transformed cannot be null");
		}
		return Boolean.toString(ba.getInitialStates().contains(state));
	}
}