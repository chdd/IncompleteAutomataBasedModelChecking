package it.polimi.automata.io.ba.transformers;

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
 * @param <L>
 *            is the type of the Label which is applied to the transitions of
 *            the Buchi Automaton which must implement the interface
 *            {@link Label}
 * @param <T>
 *            is the type of the transitions of the automaton. It must implement
 *            the interface {@link Transition}
 */
public class BATransitionToStringTransformer<L extends Label, T extends Transition<L>> implements Transformer<T, String>{

	/**
	 * returns the string representation of the transition
	 * 
	 * @return the string representation of the transition
	 * @throws NullPointerException
	 *             if the transition to be transformed is null
	 */
	@Override
	public String transform(T input) {
		if(input==null){
			throw new NullPointerException("The transition to be converted cannot be null");
		}
		return input.getLabels().toString();
	}
}

