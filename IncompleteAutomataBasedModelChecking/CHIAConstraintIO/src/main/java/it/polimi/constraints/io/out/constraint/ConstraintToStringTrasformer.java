package it.polimi.constraints.io.out.constraint;

import it.polimi.action.CHIAAction;
import it.polimi.automata.io.out.ElementToStringTransformer;
import it.polimi.constraints.Constraint;

import javax.xml.parsers.ParserConfigurationException;

public class ConstraintToStringTrasformer extends CHIAAction<String> {
	private final static String NAME = "PRINT CONSTRAINT";

	protected Constraint constraint;
	public ConstraintToStringTrasformer(Constraint constraint) {
		super(NAME);
		this.constraint=constraint;
	}

	public String perform() throws ParserConfigurationException,
			Exception {
		return new ElementToStringTransformer()
				.transform(new ConstraintToElementTransformer().transform(constraint));
	}
}
