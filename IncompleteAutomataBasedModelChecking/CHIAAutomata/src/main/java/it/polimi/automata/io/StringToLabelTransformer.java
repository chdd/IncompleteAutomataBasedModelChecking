/**
 * 
 */
package it.polimi.automata.io;

import it.polimi.Constants;
import it.polimi.automata.labeling.Label;
import it.polimi.automata.labeling.impl.LabelImplFactory;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.collections15.Transformer;

import rwth.i2.ltl2ba4j.model.IGraphProposition;

/**
 * Starting from the string computes the corresponding Label. 
 * @author claudiomenghi
 * 
 */
class StringToLabelTransformer implements Transformer<String, Label> {

	/**
	 * Starting from the string computes the corresponding Label. 
	 * 
	 * @param input
	 *            is the input to be computed
	 * @throws NullPointerException
	 *             if the input is null
	 */
	@Override
	public Label transform(String input) {

		if (input == null) {
			throw new NullPointerException("The input must be not null");
		}
		String[] aps=input.split(Constants.AND);
		
		Set<IGraphProposition> propositions=new HashSet<IGraphProposition>();
		for(String ap: new HashSet<String>(Arrays.asList(aps))){
			propositions.add(new APToIGraphPropositionTransformer().transform(ap));
		}
		
		return new LabelImplFactory().create(propositions);
	}

}
