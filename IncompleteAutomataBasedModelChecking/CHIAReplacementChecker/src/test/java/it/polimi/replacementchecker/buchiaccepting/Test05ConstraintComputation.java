package it.polimi.replacementchecker.buchiaccepting;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;
import it.polimi.automata.BA;
import it.polimi.automata.IBA;
import it.polimi.automata.IntersectionBA;
import it.polimi.automata.io.in.ClaimReader;
import it.polimi.automata.io.in.ModelReader;
import it.polimi.automata.io.out.ElementToStringTransformer;
import it.polimi.automata.state.IntersectionStateFactory;
import it.polimi.automata.state.State;
import it.polimi.automata.state.StateFactory;
import it.polimi.checker.Checker;
import it.polimi.checker.SatisfactionValue;
import it.polimi.checker.intersection.acceptingpolicies.AcceptingPolicy;
import it.polimi.checker.intersection.acceptingpolicies.AcceptingPolicy.AcceptingType;
import it.polimi.constraintcomputation.ConstraintGenerator;
import it.polimi.constraints.Constraint;
import it.polimi.constraints.components.Replacement;
import it.polimi.constraints.components.SubProperty;
import it.polimi.constraints.io.in.replacement.ReplacementReader;
import it.polimi.constraints.io.out.constraint.ConstraintToElementTransformer;
import it.polimi.replacementchecker.ReplacementChecker;

import java.io.File;

import javax.xml.parsers.ParserConfigurationException;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class Test05ConstraintComputation {

	private static final String path = "it.polimi.replacementchecker/";

	private IBA model;
	private BA claim;
	
	private AcceptingType acceptingPolicy; 
	

	private Replacement replacement;

	private State state9;
	private State state10;
	private State state11;
	private State state12;
	private State state13;
	private State state14;
	private State state15;
	private State state16;
	private State state17;
	private State state18;
	private State state19;
	private State state20;
	private State state21;

	private State state1;
	private State state2;
	private State state3;
	private State state4;
	
	
	private IntersectionBA upperIntersectionBA;

	@Mock
	private IntersectionStateFactory intersectionStateFactory;

	@Mock
	private StateFactory stateFactory;

	
	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		
		this.acceptingPolicy=AcceptingType.BA;

		this.model = new ModelReader(new File(getClass().getClassLoader()
				.getResource(path + "buchiaccepting/test05/model.xml").getFile())).perform();

		this.claim = new ClaimReader(new File(getClass().getClassLoader()
				.getResource(path + "buchiaccepting/test05/claim.xml").getFile())).perform();

		this.replacement = new ReplacementReader(new File(getClass()
				.getClassLoader().getResource(path + "buchiaccepting/test05/replacement.xml")
				.getFile())).perform();
		stateFactory = new StateFactory();
		stateFactory = new StateFactory();
		state1 = stateFactory.create("1", 1);
		state2 = stateFactory.create("2", 2);
		state3 = stateFactory.create("t0", 3);
		state4 = stateFactory.create("4", 4);

		state9 = stateFactory.create("9", 9);
		state10 = stateFactory.create("10", 10);
		state11 = stateFactory.create("11", 11);
		state12 = stateFactory.create("12", 12);
		state13 = stateFactory.create("13", 13);
		state14 = stateFactory.create("14", 14);
		state15 = stateFactory.create("15", 15);
		state16 = stateFactory.create("16", 16);
		state17 = stateFactory.create("17", 17);
		state18 = stateFactory.create("18", 18);
		state19 = stateFactory.create("19", 19);
		state20 = stateFactory.create("20", 20);
		state21 = stateFactory.create("21", 21);
		
		this.mockIntersectionStateCreation();
		

	}

	
	private void mockIntersectionStateCreation(){
		when(intersectionStateFactory.create(state1, state2, 0)).thenReturn(
				state9);

		when(intersectionStateFactory.create(state4, state2, 0)).thenReturn(
				state10);
		when(intersectionStateFactory.create(state4, state2, 1)).thenReturn(
				state11);
		when(intersectionStateFactory.create(state4, state2, 2)).thenReturn(
				state12);

		when(intersectionStateFactory.create(state3, state2, 0)).thenReturn(
				state13);
		when(intersectionStateFactory.create(state3, state2, 1)).thenReturn(
				state14);
		when(intersectionStateFactory.create(state3, state2, 2)).thenReturn(
				state15);
		
		when(intersectionStateFactory.create(state4, state1, 0)).thenReturn(
				state16);
		when(intersectionStateFactory.create(state4, state1, 1)).thenReturn(
				state17);
		when(intersectionStateFactory.create(state4, state1, 2)).thenReturn(
				state18);

		when(intersectionStateFactory.create(state3, state1, 0)).thenReturn(
				state19);
		when(intersectionStateFactory.create(state3, state1, 1)).thenReturn(
				state20);
		when(intersectionStateFactory.create(state3, state1, 2)).thenReturn(
				state21);
	}
	
	@Test
	public void test() throws ParserConfigurationException, Exception {

		Checker checker = new Checker(model, claim,
				AcceptingPolicy.getAcceptingPolicy(this.acceptingPolicy, model, claim));
		SatisfactionValue returnValue = checker.perform();
		assertTrue("The property must be possibly satisfied",
				returnValue == SatisfactionValue.POSSIBLYSATISFIED);

		System.out.println(checker.getUpperIntersectionBA().toString());
		upperIntersectionBA = checker.getUpperIntersectionBA();
		this.checkStatesPresence();

		ConstraintGenerator cg = new ConstraintGenerator(checker);
		Constraint constraint = cg.perform();
		
		System.out.println(new ElementToStringTransformer()
				.transform(new ConstraintToElementTransformer()
						.transform(constraint)));

		SubProperty subproperty=constraint.getSubProperty(replacement.getModelState());
		ReplacementChecker rc = new ReplacementChecker(
				subproperty,
				replacement, 
				AcceptingPolicy.getAcceptingPolicy(this.acceptingPolicy, replacement.getAutomaton(), subproperty.getAutomaton()));

		SatisfactionValue satisfactionValue = rc.perform();
		System.out.println(rc.getLowerIntersectionBA());
		assertTrue(satisfactionValue == SatisfactionValue.NOTSATISFIED);
		System.out.println(new ElementToStringTransformer()
				.transform(new ConstraintToElementTransformer()
						.transform(constraint)));
		System.out.println(rc.perform());

	}
	
	private void checkStatesPresence(){
		this.upperIntersectionBA.getStates().contains(state9);
		this.upperIntersectionBA.getStates().contains(state10);
		this.upperIntersectionBA.getStates().contains(state11);
		this.upperIntersectionBA.getStates().contains(state12);
		this.upperIntersectionBA.getStates().contains(state13);
		this.upperIntersectionBA.getStates().contains(state14);
		this.upperIntersectionBA.getStates().contains(state15);
		this.upperIntersectionBA.getStates().contains(state16);
		this.upperIntersectionBA.getStates().contains(state17);
		this.upperIntersectionBA.getStates().contains(state18);
		this.upperIntersectionBA.getStates().contains(state19);
		this.upperIntersectionBA.getStates().contains(state20);
		this.upperIntersectionBA.getStates().contains(state21);
	}

}
