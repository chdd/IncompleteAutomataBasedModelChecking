package it.polimi.constraints.io.out.constraint;

import it.polimi.automata.io.out.ElementToStringTransformer;
import it.polimi.constraints.Constraint;

import javax.xml.parsers.ParserConfigurationException;

import action.CHIAAction;

public class ConstraintToStringTrasformer extends CHIAAction {
	private final static String NAME = "PRINT CONSTRAINT";

	public ConstraintToStringTrasformer() {
		super(NAME);
	}

	public String toString(Constraint constraint) throws ParserConfigurationException,
			Exception {
		return new ElementToStringTransformer()
				.transform(new ConstraintToElementTransformer().transform(constraint));
	}
}
