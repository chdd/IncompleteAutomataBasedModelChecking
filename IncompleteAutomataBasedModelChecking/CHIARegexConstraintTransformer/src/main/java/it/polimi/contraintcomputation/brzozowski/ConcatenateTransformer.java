/**
 * 
 */
package it.polimi.contraintcomputation.brzozowski;

import it.polimi.contraintcomputation.Constants;

import java.util.Map.Entry;


/**
 * @author claudiomenghi
 * 
 */
public class ConcatenateTransformer  {

	private String concatenationCharacter;
	
	public ConcatenateTransformer(String concatenationCharacter){
		this.concatenationCharacter=concatenationCharacter;
	}
	
	/**
	 * computes the concatenation of the two strings passed as parameter into
	 * the input entry
	 * 
	 * @param input
	 *            the two string that must be concatenated
	 * @throws NullPointerException
	 *             if the input is null
	 */
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
		return input.getKey() + concatenationCharacter + input.getValue();
	}

}
