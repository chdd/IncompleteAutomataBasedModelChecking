package it.polimi.automata.io.out;

import javax.xml.parsers.ParserConfigurationException;

import it.polimi.automata.BA;
import action.CHIAAction;

public class BAToStringTrasformer extends CHIAAction{

	private final static String NAME="PRINT CLAIM";
	
	public BAToStringTrasformer() {
		super(NAME);
	}

	public String toString(BA claim) throws ParserConfigurationException, Exception{
		return new ElementToStringTransformer().transform(new BAToElementTrasformer().transform(claim));
	}
}
