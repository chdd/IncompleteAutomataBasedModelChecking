/**
 * 
 */
package it.polimi.automata.io;

import it.polimi.automata.Constants;
import it.polimi.automata.labeling.Label;
import it.polimi.automata.labeling.LabelFactory;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

import org.apache.commons.collections15.Transformer;

import rwth.i2.ltl2ba4j.model.IGraphProposition;

/**
 * Starting from the string computes the corresponding Label. 
 * @author claudiomenghi
 * 
 */
class StringToLabelTransformer<L extends Label> implements Transformer<String, L> {

	private LabelFactory<L> labelFactory;
	
	public StringToLabelTransformer(LabelFactory<L> labelFactory){
		this.labelFactory=labelFactory;
	}
	
	/**
	 * Starting from the string computes the corresponding Label. 
	 * 
	 * @param input
	 *            is the input to be computed
	 * @throws NullPointerException
	 *             if the input is null
	 */
	@Override
	public L transform(String input) {

		if (input == null) {
			throw new NullPointerException("The input must be not null");
		}
		
		String[] aps=input.split(Pattern.quote(Constants.AND));
		
		Set<IGraphProposition> propositions=new HashSet<IGraphProposition>();
		for(String ap: new HashSet<String>(Arrays.asList(aps))){
			propositions.add(new APToIGraphPropositionTransformer().transform(ap));
		}
		
		return labelFactory.create(propositions);
	}

}
