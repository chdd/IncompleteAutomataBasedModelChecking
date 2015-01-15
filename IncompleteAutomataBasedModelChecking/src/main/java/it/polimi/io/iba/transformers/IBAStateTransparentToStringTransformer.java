package it.polimi.io.iba.transformers;

import it.polimi.automata.IBA;
import it.polimi.automata.labeling.Label;
import it.polimi.automata.state.State;
import it.polimi.automata.transition.Transition;

import org.apache.commons.collections15.Transformer;

/**
 * contains the Transformer that given a State returns a true which is "true" or
 * false depending on whether the state is transparent or not or not
 * 
 * @see Transformer
 * 
 * @author claudiomenghi
 * 
 * @param <LABEL>
 *            is the type of the Label which is applied to the transitions of
 *            the Incomplete Buchi Automaton which must implement the interface
 *            {@link Label}
 * @param <STATE>
 *            is the type of the State of the Incomplete Buchi Automaton. It must extend
 *            the interface {@link State}
 * @param <TRANSITION>
 *            is the type of the transitions of the automaton. It must implement
 *            the interface {@link Transition}
 */
public class IBAStateTransparentToStringTransformer<LABEL extends Label, STATE extends State, TRANSITION extends Transition<LABEL>> implements Transformer<STATE, String> {

	/**
	 * is the Incomplete Buchi Automaton to be transformed
	 */
	private IBA<LABEL, STATE, TRANSITION> iba;
	
	/**
	 * creates the Transformer
	 * 
	 * @param ba
	 *            is the Incomplete Buchi Automaton where the state is placed
	 * @throws NullPointerException
	 *             if the Incomplete Buchi Automaton is null
	 */
	public IBAStateTransparentToStringTransformer(IBA<LABEL, STATE, TRANSITION>  ba){
		if(ba==null){
			throw new NullPointerException("The ba cannot be null");
		}
		this.iba=ba;
	}
	
	/**
	 * returns "true" if the state is transparent, "false" otherwise
	 * 
	 * @return "true" if the state is transparent, "false" otherwise
	 * @throws NullPointerException
	 *             if the state to be transformed is null
	 */
	@Override
	public String transform(STATE input) {
		return Boolean.toString(iba.isTransparent(input));
	}
}