package it.polimi.io.ba.transformers;

import it.polimi.automata.labeling.Label;
import it.polimi.automata.transition.Transition;

import org.apache.commons.collections15.Transformer;


/**
 * contains the Transformer that given a transition return its string representation
 * 
 * @see Transformer
 * 
 * @author claudiomenghi
 * 
 * @param <LABEL>
 *            is the type of the Label which is applied to the transitions of
 *            the Buchi Automaton which must implement the interface
 *            {@link Label}
 * @param <TRANSITION>
 *            is the type of the transitions of the automaton. It must implement
 *            the interface {@link Transition}
 */
public class BATransitionToStringTransformer<LABEL extends Label, TRANSITION extends Transition<LABEL>> implements Transformer<TRANSITION, String>{

	/**
	 * returns the string representation of the transition
	 * 
	 * @return the string representation of the transition
	 * @throws NullPointerException
	 *             if the transition to be transformed is null
	 */
	@Override
	public String transform(TRANSITION input) {
		if(input==null){
			throw new NullPointerException("The transition to be converted cannot be null");
		}
		return input.getLabels().toString();
	}
}

