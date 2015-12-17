package it.polimi.automata.io.out;

import it.polimi.action.CHIAAction;
import it.polimi.automata.IBA;

import com.google.common.base.Preconditions;

/**
 * Transforms an IBA into the corresponding String representation
 * 
 * @author Claudio Menghi
 *
 */
public class ModelToStringTrasformer extends CHIAAction<String> {

	private final static String NAME = "PRINT CLAIM";

	/**
	 * the BA to be converted
	 */
	protected IBA model;

	/**
	 * transforms a IBA into the corresponding string representation
	 * 
	 * @param model
	 *            the IBA to be converted
	 * @throws NullPointerException
	 *             if the IBA to be converted is null
	 */
	public ModelToStringTrasformer(IBA model) {
		super(NAME);
		Preconditions.checkNotNull(model, "The claim to be converted cannot be null");
		this.model = model;
	}

	/**
	 * transforms the IBA into the corresponding string representation
	 * 
	 * @return the String associated with the IBA
	 */
	@Override
	public String perform() throws Exception {
		return new ElementToStringTransformer()
				.transform(new IBAToElementTrasformer().transform(model));
	}
}
