package it.polimi.constraintcomputation;

import static org.junit.Assert.assertTrue;
import it.polimi.automata.BA;
import it.polimi.automata.IBA;
import it.polimi.automata.io.in.BAReader;
import it.polimi.automata.io.in.IBAReader;
import it.polimi.automata.io.out.ElementToStringTransformer;
import it.polimi.checker.Checker;
import it.polimi.checker.SatisfactionValue;
import it.polimi.checker.intersection.acceptingpolicies.AcceptingPolicy;
import it.polimi.checker.intersection.acceptingpolicies.AcceptingPolicy.AcceptingType;
import it.polimi.constraints.Constraint;
import it.polimi.constraints.io.out.constraint.ConstraintToElementTransformer;
import it.polimi.contraintcomputation.ConstraintGenerator;

import java.io.File;

import javax.xml.parsers.ParserConfigurationException;

import org.junit.Before;
import org.junit.Test;

public class Test1ConstraintComputation {

	private static final String path = "it.polimi.constraintcomputation/";

	private IBA model;
	private BA claim;
	
	@Before
	public void setUp() throws Exception{
		this.model = new IBAReader(new File(getClass().getClassLoader()
				.getResource(path + "test1/model.xml").getFile())).perform();
		
		this.claim=new BAReader(new File(getClass().getClassLoader()
				.getResource(path + "test1/claim.xml").getFile())).perform();
	}
	@Test
	public void test() throws ParserConfigurationException, Exception {
		
		Checker checker=new Checker(model, claim, AcceptingPolicy.getAcceptingPolicy(AcceptingType.NORMAL));
		SatisfactionValue returnValue=checker.perform();
		assertTrue("The property must be possibly satisfied", returnValue==SatisfactionValue.POSSIBLYSATISFIED);
		
		System.out.println(checker.getUpperIntersectionBA().toString());
		ConstraintGenerator cg=new ConstraintGenerator(checker);
		Constraint constraint=cg.perform();
		cg.computeIndispensable();
		cg.computePortReachability();
		cg.coloring();
		System.out.println(new ElementToStringTransformer().transform(new ConstraintToElementTransformer().transform(constraint)));
		assertTrue(constraint.getSubProperties().size()==1);
		assertTrue(constraint.getSubProperties().iterator().next().isIndispensable()==true);
		assertTrue(constraint.getSubProperties().iterator().next().getAutomaton().getStates().size()==6);
		assertTrue(constraint.getSubProperties().iterator().next().getAutomaton().getTransitions().size()==9);
	}

}
