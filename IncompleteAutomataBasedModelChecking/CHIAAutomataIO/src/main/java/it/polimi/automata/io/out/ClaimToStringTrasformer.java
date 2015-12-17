package it.polimi.automata.io.out;

import it.polimi.action.CHIAAction;
import it.polimi.automata.BA;

import com.google.common.base.Preconditions;

/**
 * Transforms a BA into the corresponding String representation
 * 
 * @author Claudio Menghi
 *
 */
public class ClaimToStringTrasformer extends CHIAAction<String> {

	private final static String NAME = "PRINT CLAIM";

	/**
	 * the BA to be converted
	 */
	protected BA claim;

	/**
	 * transforms a BA into the corresponding string representation
	 * 
	 * @param claim
	 *            the BA to be converted
	 * @throws NullPointerException
	 *             if the BA to be converted is null
	 */
	public ClaimToStringTrasformer(BA claim) {
		super(NAME);
		Preconditions.checkNotNull(claim, "The claim to be converted cannot be null");
		this.claim = claim;
	}

	/**
	 * transforms the BA into the corresponding string representation
	 * 
	 * @return the String associated with the BA
	 */
	@Override
	public String perform() throws Exception {
		return new ElementToStringTransformer()
				.transform(new BAToElementTrasformer().transform(claim));
	}
}
