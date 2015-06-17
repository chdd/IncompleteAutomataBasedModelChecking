package it.polimi.replacementchecker.kripkeaccepting;


import static org.junit.Assert.assertTrue;
import it.polimi.automata.BA;
import it.polimi.automata.IBA;
import it.polimi.automata.io.in.BAReader;
import it.polimi.automata.io.in.IBAReader;
import it.polimi.automata.io.out.ElementToStringTransformer;
import it.polimi.checker.Checker;
import it.polimi.checker.SatisfactionValue;
import it.polimi.checker.intersection.acceptingpolicies.AcceptingPolicy;
import it.polimi.checker.intersection.acceptingpolicies.KripkeAcceptingPolicy;
import it.polimi.constraints.Constraint;
import it.polimi.constraints.components.Replacement;
import it.polimi.constraints.io.in.constraint.ConstraintReader;
import it.polimi.constraints.io.in.replacement.ReplacementReader;
import it.polimi.constraints.io.out.constraint.ConstraintToElementTransformer;
import it.polimi.contraintcomputation.ConstraintGenerator;
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
	private AcceptingPolicy acceptingPolicy; 
	
	
	@Before
	public void setUp() throws Exception{
		this.replacement = new ReplacementReader(new File(getClass().getClassLoader()
				.getResource(path + "kripke/test01/replacement.xml").getFile())).perform();
		
		this.constraint=new ConstraintReader(new File(getClass().getClassLoader()
				.getResource(path + "kripke/test01/constraint.xml").getFile())).perform();
		this.refinement=new IBAReader(new File(getClass().getClassLoader()
				.getResource(path + "kripke/test01/refinement.xml").getFile())).perform();
		
		this.claim=new BAReader(new File(getClass().getClassLoader()
				.getResource(path + "kripke/test01/claim.xml").getFile())).perform();
		this.model=new IBAReader(new File(getClass().getClassLoader()
				.getResource(path + "kripke/test01/model.xml").getFile())).perform();
		this.acceptingPolicy=new KripkeAcceptingPolicy();
	}
	@Test
	public void test() throws ParserConfigurationException, Exception {
		
		
		Checker checker=new Checker(model, claim, this.acceptingPolicy);
		
		checker.perform();
		
		System.out.println(checker.getUpperIntersectionBA());
		ConstraintGenerator cg = new ConstraintGenerator(checker);
		Constraint constraint = cg.perform();
		cg.computeIndispensable();
		cg.computePortReachability();
		cg.coloring();

		System.out.println(new ElementToStringTransformer()
				.transform(new ConstraintToElementTransformer()
						.transform(constraint)));
		
		checker=new Checker(refinement, claim, this.acceptingPolicy);
		SatisfactionValue ret=checker.perform();
		assertTrue(ret==SatisfactionValue.NOTSATISFIED);
		
		ReplacementChecker replacementChecker=new ReplacementChecker(this.constraint.getSubProperty(this.replacement.getModelState()), replacement, this.acceptingPolicy);
		
		SatisfactionValue retValue=replacementChecker.perform();
		System.out.println(retValue);
		System.out.println(replacementChecker.getLowerIntersectionBA());
		
		assertTrue(retValue==SatisfactionValue.NOTSATISFIED);
		
		
	}

}
