package it.polimi.automata.io.transformer.propositions;

import it.polimi.automata.Constants;

import java.util.HashSet;
import java.util.Set;

import rwth.i2.ltl2ba4j.model.IGraphProposition;
import rwth.i2.ltl2ba4j.model.impl.GraphProposition;
import rwth.i2.ltl2ba4j.model.impl.SigmaProposition;

import com.google.common.base.Preconditions;


public class ModelPropositionParser {

	/**
	 * Starting from the string computes the corresponding proposition. The
	 * string must satisfy the regular expression {@link Constants#APREGEX} or
	 * the regular expression {@link Constants#NOTAPREGEX}
	 * 
	 * @param input
	 *            is the input to be computed
	 * @throws NullPointerException
	 *             if the input is null
	 * @throws IllegalArgumentException
	 *             if the input does not match the regular expression
	 *             {@link Constants#APREGEX} or the regular expression
	 *             {@link Constants#NOTAPREGEX}
	 */
	public Set<IGraphProposition> computePropositions(String input) {

		Preconditions.checkNotNull("The input must be not null");
		Preconditions.checkArgument(!input.matches(Constants.MODEL_PROPOSITIONS), "The input "+input+" must match the regular expression: "
				+ Constants.MODEL_PROPOSITIONS);
		
		Set<IGraphProposition> propositions=new HashSet<IGraphProposition>();
		
		if(input.equals(Constants.SIGMA)){
			propositions.add(new SigmaProposition());
		}
		else{
			String[] apsStrings=input.split(Constants.AND);
			
			for(String ap: apsStrings){
				propositions.add(new GraphProposition(ap, false));
			}
		}
		return propositions;
	}
}
