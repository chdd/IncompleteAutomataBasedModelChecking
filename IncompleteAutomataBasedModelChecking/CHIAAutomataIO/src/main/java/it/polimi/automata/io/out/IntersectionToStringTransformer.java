package it.polimi.automata.io.out;

import it.polimi.automata.IntersectionBA;

import javax.xml.parsers.ParserConfigurationException;

import action.CHIAAction;

public class IntersectionToStringTransformer extends CHIAAction{

	private final static String NAME="PRINT INTERSECTION";
	
	public IntersectionToStringTransformer() {
		super(NAME);
	}

	public String toString(IntersectionBA intersection) throws ParserConfigurationException, Exception{
		return new ElementToStringTransformer().transform(new IntersectionToElementTransformer().transform(intersection));
	}
}
