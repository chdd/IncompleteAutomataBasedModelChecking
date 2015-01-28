package it.polimi.automata.io;

import it.polimi.automata.Constants;

import org.apache.commons.collections15.Transformer;

import rwth.i2.ltl2ba4j.model.IGraphProposition;
import rwth.i2.ltl2ba4j.model.impl.GraphProposition;
import rwth.i2.ltl2ba4j.model.impl.SigmaProposition;

/**
 * Starting from the string computes the corresponding proposition. The string
 * must satisfy the regular expression {@link Constants#APREGEX} or the regular
 * expression {@link Constants#NOTAPREGEX}
 * 
 * @author claudiomenghi
 * 
 */
class APToIGraphPropositionTransformer implements
		Transformer<String, IGraphProposition> {

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
	@Override
	public IGraphProposition transform(String input) {

		if (input == null) {
			throw new NullPointerException("The input must be not null");
		}
		if (!input.matches(Constants.APREGEX)
				&& !input.matches(Constants.NOTAPREGEX)) {
			throw new IllegalArgumentException(
					"The input "+input+" must match the regular expression: "
							+ Constants.APREGEX + " or " + Constants.NOTAPREGEX);
		}
		
		if (input.matches(Constants.APREGEX)) {
			if(input.equals(Constants.SIGMA)){
				return new SigmaProposition();
			}
			else{
				return new GraphProposition(input, false);
			}
		} else {
			return new GraphProposition(input.substring(1), true);
		}
	}

}
