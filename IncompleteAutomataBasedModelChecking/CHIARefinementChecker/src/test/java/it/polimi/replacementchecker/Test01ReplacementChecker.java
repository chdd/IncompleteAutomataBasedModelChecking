package it.polimi.replacementchecker;

import static org.junit.Assert.assertTrue;
import it.polimi.checker.SatisfactionValue;
import it.polimi.checker.intersection.acceptingpolicies.AcceptingPolicy;
import it.polimi.checker.intersection.acceptingpolicies.KripkeAcceptingPolicy;
import it.polimi.constraints.Constraint;
import it.polimi.constraints.components.Replacement;
import it.polimi.constraints.io.in.constraint.ConstraintReader;
import it.polimi.constraints.io.in.replacement.ReplacementReader;

import java.io.File;

import org.junit.Before;
import org.junit.Test;

public class Test01ReplacementChecker {

	private static final String path = "it.polimi.replacementchecker/";

	private Constraint constraint;
	private Replacement replacement;
	
	private AcceptingPolicy acceptingPolicy; 
	@Before
	public void setUp(){
		this.replacement = new ReplacementReader(new File(getClass().getClassLoader()
				.getResource(path + "test1/replacement.xml").getFile())).perform();
		
		this.constraint=new ConstraintReader(new File(getClass().getClassLoader()
				.getResource(path + "test1/constraint.xml").getFile())).perform();
		this.acceptingPolicy=new KripkeAcceptingPolicy();
	}
	@Test
	public void test() {
		ReplacementChecker replacementChecker=new ReplacementChecker(this.constraint.getSubProperty(this.replacement.getModelState()), replacement, acceptingPolicy);
		
		SatisfactionValue retValue=replacementChecker.perform();
		System.out.println(replacementChecker.getLowerIntersectionBA());
		assertTrue(retValue==SatisfactionValue.NOTSATISFIED);
	}

}
