package it.polimi.automata.io.out;

import it.polimi.automata.IntersectionBA;

import javax.xml.parsers.ParserConfigurationException;

import com.google.common.base.Preconditions;

import action.CHIAAction;

public class IntersectionToStringTransformer extends CHIAAction<String>{

	private final static String NAME="PRINT INTERSECTION";
	
	protected IntersectionBA intersection;
	
	public IntersectionToStringTransformer(IntersectionBA intersection){
		super(NAME);
		Preconditions.checkNotNull(intersection, "The intersection automaton cannot be null");
		this.intersection=intersection;
	}
	
	public IntersectionToStringTransformer() {
		super(NAME);
	}

	public String perform() throws ParserConfigurationException, Exception{
		return new ElementToStringTransformer().transform(new IntersectionToElementTransformer().transform(intersection));
	}
}
