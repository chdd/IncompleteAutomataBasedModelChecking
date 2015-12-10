package it.polimi.replacementchecker.kripkeaccepting;


import static org.junit.Assert.assertTrue;
import it.polimi.automata.BA;
import it.polimi.automata.IBA;
import it.polimi.automata.io.in.ClaimReader;
import it.polimi.automata.io.in.ModelReader;
import it.polimi.automata.io.out.ElementToStringTransformer;
import it.polimi.checker.Checker;
import it.polimi.checker.SatisfactionValue;
import it.polimi.checker.intersection.acceptingpolicies.AcceptingPolicy;
import it.polimi.checker.intersection.acceptingpolicies.AcceptingPolicy.AcceptingType;
import it.polimi.constraintcomputation.ConstraintGenerator;
import it.polimi.constraints.Constraint;
import it.polimi.constraints.components.Replacement;
import it.polimi.constraints.components.SubProperty;
import it.polimi.constraints.io.in.constraint.ConstraintReader;
import it.polimi.constraints.io.in.replacement.ReplacementReader;
import it.polimi.constraints.io.out.constraint.ConstraintToElementTransformer;
import it.polimi.replacementchecker.ReplacementChecker;

import java.io.File;

import javax.xml.parsers.ParserConfigurationException;

import org.junit.Before;
import org.junit.Test;

public class Test01ReplacementChecker {

	private static final String path = "it.polimi.replacementchecker/";

	private Constraint constraint;
	private Replacement replacement;
	
	private IBA refinement;
	private BA claim;
	private IBA model;
	private AcceptingType acceptingPolicy; 
	
	
	@Before
	public void setUp() throws Exception{
		this.replacement = new ReplacementReader(new File(getClass().getClassLoader()
				.getResource(path + "kripke/test01/replacement.xml").getFile())).perform();
		
		this.constraint=new ConstraintReader(new File(getClass().getClassLoader()
				.getResource(path + "kripke/test01/constraint.xml").getFile())).perform();
		this.refinement=new ModelReader(new File(getClass().getClassLoader()
				.getResource(path + "kripke/test01/refinement.xml").getFile())).perform();
		
		this.claim=new ClaimReader(new File(getClass().getClassLoader()
				.getResource(path + "kripke/test01/claim.xml").getFile())).perform();
		this.model=new ModelReader(new File(getClass().getClassLoader()
				.getResource(path + "kripke/test01/model.xml").getFile())).perform();
		this.acceptingPolicy=AcceptingType.KRIPKE;
	}
	@Test
	public void test() throws ParserConfigurationException, Exception {
		
		
		Checker checker=new Checker(model, claim, 
				AcceptingPolicy.getAcceptingPolicy(this.acceptingPolicy, model, claim));
		
		checker.perform();
		
		System.out.println(checker.getUpperIntersectionBA());
		ConstraintGenerator cg = new ConstraintGenerator(checker);
		Constraint constraint = cg.perform();
		
		System.out.println(new ElementToStringTransformer()
				.transform(new ConstraintToElementTransformer()
						.transform(constraint)));
		
		checker=new Checker(refinement, claim, 
				AcceptingPolicy.getAcceptingPolicy(this.acceptingPolicy, refinement, claim));
		SatisfactionValue ret=checker.perform();
		assertTrue(ret==SatisfactionValue.NOTSATISFIED);
		
		SubProperty subProperty=this.constraint.getSubProperty(this.replacement.getModelState());
		ReplacementChecker replacementChecker=new ReplacementChecker( replacement, subProperty,
				AcceptingPolicy.getAcceptingPolicy(this.acceptingPolicy, replacement.getAutomaton(), subProperty.getAutomaton()));
		
		SatisfactionValue retValue=replacementChecker.perform();
		System.out.println(retValue);
		System.out.println(replacementChecker.getLowerIntersectionBA());
		
		assertTrue(retValue==SatisfactionValue.NOTSATISFIED);
		
		
	}

}
