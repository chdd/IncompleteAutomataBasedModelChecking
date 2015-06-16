package it.polimi.replacementchecker;

import static org.junit.Assert.assertTrue;
import it.polimi.automata.BA;
import it.polimi.automata.IBA;
import it.polimi.automata.io.in.BAReader;
import it.polimi.automata.io.in.IBAReader;
import it.polimi.automata.io.out.ElementToStringTransformer;
import it.polimi.automata.state.IntersectionStateFactory;
import it.polimi.checker.Checker;
import it.polimi.checker.SatisfactionValue;
import it.polimi.checker.intersection.IntersectionTransitionBuilder;
import it.polimi.checker.intersection.acceptingpolicies.AcceptingPolicy;
import it.polimi.checker.intersection.acceptingpolicies.NormalAcceptingPolicy;
import it.polimi.constraints.Constraint;
import it.polimi.constraints.components.Replacement;
import it.polimi.constraints.io.in.replacement.ReplacementReader;
import it.polimi.constraints.io.out.constraint.ConstraintToElementTransformer;
import it.polimi.contraintcomputation.ConstraintGenerator;

import java.io.File;

import javax.xml.parsers.ParserConfigurationException;

import org.junit.Before;
import org.junit.Test;

public class Test4ConstraintComputation {

	private static final String path = "it.polimi.replacementchecker/";

	private IBA model;
	private BA claim;

	

	private IntersectionStateFactory intersectionStateFactory;

	private Replacement replacement;
	
	private AcceptingPolicy acceptingPolicy; 
	

	@Before
	public void setUp() throws Exception {
		this.model = new IBAReader(new File(getClass().getClassLoader()
				.getResource(path + "test4/model.xml").getFile())).perform();

		this.claim = new BAReader(new File(getClass().getClassLoader()
				.getResource(path + "test4/claim.xml").getFile())).perform();

		this.replacement=new ReplacementReader(new File(getClass().getClassLoader()
				.getResource(path + "test4/replacement.xml").getFile())).perform();
		this.intersectionStateFactory=new IntersectionStateFactory();
		this.acceptingPolicy=new NormalAcceptingPolicy();
	}

	@Test
	public void test() throws ParserConfigurationException, Exception {

		Checker checker = new Checker(model, claim,
				this.acceptingPolicy, intersectionStateFactory, new IntersectionTransitionBuilder());
		SatisfactionValue returnValue = checker.perform();
		assertTrue("The property must be possibly satisfied",
				returnValue == SatisfactionValue.POSSIBLYSATISFIED);

		System.out.println(checker.getUpperIntersectionBA().toString());
		
		
		ConstraintGenerator cg = new ConstraintGenerator(checker);
		Constraint constraint = cg.perform();
		cg.computeIndispensable();
		cg.computePortReachability();
		cg.coloring();
		
		System.out.println(new ElementToStringTransformer()
				.transform(new ConstraintToElementTransformer()
						.transform(constraint)));
		ReplacementChecker rc=new ReplacementChecker(constraint.getSubProperty(replacement.getModelState()), replacement, this.acceptingPolicy);
		
		SatisfactionValue satisfactionValue=rc.perform();

		System.out.println(rc.getLowerIntersectionBA());
		assertTrue(satisfactionValue==SatisfactionValue.NOTSATISFIED);
		System.out.println(new ElementToStringTransformer()
				.transform(new ConstraintToElementTransformer()
						.transform(constraint)));
		System.out.println(rc.perform());
		

	}


}
