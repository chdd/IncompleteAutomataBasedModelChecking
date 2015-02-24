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
public class UnionTransformer  {

	private String orCharacter;
	public UnionTransformer(String orCharacter){
		this.orCharacter=orCharacter;
	}
	
	/**
	 * computes the union of the two strings passed as parameter into the input
	 * entry
	 * 
	 * @param input
	 *            the two strings to which compute the union
	 * @throws NullPointerException
	 *             if the input is null
	 */
	public String transform(Entry<String, String> input) {
		if (input == null) {
			throw new NullPointerException("The input cannot be null");
		}
		if (input.getKey().equals(Constants.EMPTYSET)) {
			return input.getValue();
		}
		if (input.getValue().equals(Constants.EMPTYSET)) {
			return input.getKey();
		}
		return "((" + input.getKey() + ")"+orCharacter+"(" + input.getValue() + "))";
	}

}
