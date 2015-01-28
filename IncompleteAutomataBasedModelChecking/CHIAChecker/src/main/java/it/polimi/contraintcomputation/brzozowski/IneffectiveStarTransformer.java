/**
 * 
 */
package it.polimi.contraintcomputation.brzozowski;

import it.polimi.automata.Constants;

import org.apache.commons.collections15.Transformer;

/**
 * @author claudiomenghi
 *
 */
public class IneffectiveStarTransformer implements Transformer<String, String> {

	/**
	 * applies the star operator to the input String
	 * 
	 * @param input
	 *            is the input string to be converted
	 * @return a new string obtained by applying the star operator to the input
	 * @throws NullPointerException
	 *             if the input string is null
	 */
	@Override
	public String transform(String input) {
		if (input == null) {
			throw new NullPointerException("The input string cannot be null");
		}
		if (input.equals(Constants.EMPTYSET)) {
			return Constants.LAMBDA;
		}
		if (input.equals(Constants.LAMBDA)) {
			return Constants.LAMBDA;
		}
		return input;
	}
}
