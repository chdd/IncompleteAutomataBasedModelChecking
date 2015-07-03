package it.polimi.constraints.io.out.replacement;

import it.polimi.automata.io.out.ElementToStringTransformer;
import it.polimi.constraints.components.Replacement;

import javax.xml.parsers.ParserConfigurationException;

import action.CHIAAction;

public class ReplacementToStringTransformer extends CHIAAction<String> {
	private final static String NAME = "PRINT REPLACEMENT";

	protected Replacement constraint;
	public ReplacementToStringTransformer(Replacement constraint) {
		super(NAME);
		this.constraint=constraint;
	}

	public String perform() throws ParserConfigurationException,
			Exception {
		return new ElementToStringTransformer()
				.transform(new ReplacementToElementTransformer().transform(constraint));
	}
}
