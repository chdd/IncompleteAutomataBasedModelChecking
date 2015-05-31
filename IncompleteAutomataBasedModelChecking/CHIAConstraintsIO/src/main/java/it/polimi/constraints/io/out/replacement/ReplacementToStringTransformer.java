package it.polimi.constraints.io.out.replacement;

import it.polimi.automata.io.out.ElementToStringTransformer;
import it.polimi.constraints.components.Replacement;

import javax.xml.parsers.ParserConfigurationException;

import action.CHIAAction;

public class ReplacementToStringTransformer extends CHIAAction {
	private final static String NAME = "PRINT REPLACEMENT";

	public ReplacementToStringTransformer() {
		super(NAME);
	}

	public String toString(Replacement constraint) throws ParserConfigurationException,
			Exception {
		return new ElementToStringTransformer()
				.transform(new ReplacementToElementTransformer().transform(constraint));
	}
}
