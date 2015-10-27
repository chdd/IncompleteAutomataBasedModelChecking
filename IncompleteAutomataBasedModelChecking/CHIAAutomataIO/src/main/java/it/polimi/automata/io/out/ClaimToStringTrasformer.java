package it.polimi.automata.io.out;

import it.polimi.automata.BA;
import action.CHIAAction;

import com.google.common.base.Preconditions;

public class ClaimToStringTrasformer extends CHIAAction<String>{

	private final static String NAME="PRINT CLAIM";
	
	protected BA claim;
	
	public ClaimToStringTrasformer(BA claim) {
		super(NAME);
		Preconditions.checkNotNull("The claim to be converted cannot be null");
		this.claim=claim;
	}

	@Override
	public String perform() throws Exception {
		return new ElementToStringTransformer().transform(new BAToElementTrasformer().transform(claim));
	}
}
