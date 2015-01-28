/**
 * 
 */
package it.polimi.contraintcomputation.brzozowski;

import it.polimi.automata.Constants;

import java.util.Map.Entry;

import org.apache.commons.collections15.Transformer;

/**
 * @author claudiomenghi
 * 
 */
public class ConcatenateTransformer implements
		Transformer<Entry<String, String>, String> {

	/**
	 * computes the concatenation of the two strings passed as parameter into
	 * the input entry
	 * 
	 * @param input
	 *            the two string that must be concatenated
	 * @throws NullPointerException
	 *             if the input is null
	 */
	@Override
	public String transform(Entry<String, String> input) {
		if (input == null) {
			throw new NullPointerException("The input cannot be null");
		}
		if (input.getKey().equals(Constants.EMPTYSET)
				|| input.getValue().equals(Constants.EMPTYSET)) {
			return Constants.EMPTYSET;
		}
		if (input.getKey().equals(Constants.LAMBDA)) {
			return input.getValue();
		}
		if (input.getValue().equals(Constants.LAMBDA)) {
			return input.getKey();
		}
		return input.getKey() + "." + input.getValue();
	}

}
