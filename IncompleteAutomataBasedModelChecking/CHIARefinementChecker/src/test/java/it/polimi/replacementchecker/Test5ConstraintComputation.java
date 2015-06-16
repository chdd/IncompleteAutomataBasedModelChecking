package it.polimi.replacementchecker;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;
import it.polimi.automata.BA;
import it.polimi.automata.IBA;
import it.polimi.automata.IntersectionBA;
import it.polimi.automata.io.in.BAReader;
import it.polimi.automata.io.in.IBAReader;
import it.polimi.automata.io.out.ElementToStringTransformer;
import it.polimi.automata.state.IntersectionStateFactory;
import it.polimi.automata.state.State;
import it.polimi.automata.state.StateFactory;
import it.polimi.automata.transition.ClaimTransitionFactory;
import it.polimi.automata.transition.Transition;
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
import java.util.HashSet;
import java.util.Set;

import javax.xml.parsers.ParserConfigurationException;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import rwth.i2.ltl2ba4j.model.IGraphProposition;
import rwth.i2.ltl2ba4j.model.impl.GraphProposition;
import rwth.i2.ltl2ba4j.model.impl.SigmaProposition;

public class Test5ConstraintComputation {

	private static final String path = "it.polimi.replacementchecker/";

	private IBA model;
	private BA claim;
	
	private AcceptingPolicy acceptingPolicy; 
	

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
	
	private Transition modelTransition1;
	private Transition modelTransition3;
	private Transition modelTransition7;
	private Transition modelTransition9;
	
	private Transition claimTransition1;
	private Transition claimTransition2;
	private Transition claimTransition3;
	
	private Transition intersectionTransition1;
	
	private IntersectionBA upperIntersectionBA;

	@Mock
	private IntersectionStateFactory intersectionStateFactory;

	@Mock
	private StateFactory stateFactory;

	
	private IntersectionTransitionBuilder intersectionTransitionFactory;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		
		this.acceptingPolicy=new NormalAcceptingPolicy();

		this.model = new IBAReader(new File(getClass().getClassLoader()
				.getResource(path + "test5/model.xml").getFile())).perform();

		this.claim = new BAReader(new File(getClass().getClassLoader()
				.getResource(path + "test5/claim.xml").getFile())).perform();

		this.replacement = new ReplacementReader(new File(getClass()
				.getClassLoader().getResource(path + "test5/replacement.xml")
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
		
		IGraphProposition b=new GraphProposition("b", false);
		
		IGraphProposition a=new GraphProposition("a", false);
		
		Set<IGraphProposition> labels=new HashSet<IGraphProposition>();
		labels.add(b);
		this.modelTransition1=new ClaimTransitionFactory().create(1, labels);
		
		labels=new HashSet<IGraphProposition>();
		labels.add(a);
		this.modelTransition3=new ClaimTransitionFactory().create(3, labels);
		
		labels=new HashSet<IGraphProposition>();
		labels.add(a);
		this.modelTransition7=new ClaimTransitionFactory().create(7, labels);
		
		labels=new HashSet<IGraphProposition>();
		labels.add(a);
		this.modelTransition9=new ClaimTransitionFactory().create(9, labels);
		
		labels=new HashSet<IGraphProposition>();
		labels.add(new SigmaProposition());
		this.claimTransition1=new ClaimTransitionFactory().create(1, labels);
		
		labels=new HashSet<IGraphProposition>();
		labels.add(new GraphProposition("b", true));
		this.claimTransition2=new ClaimTransitionFactory().create(2, labels);
		
		labels=new HashSet<IGraphProposition>();
		labels.add(new GraphProposition("b", true));
		labels.add(new GraphProposition("a", true));
		this.claimTransition3=new ClaimTransitionFactory().create(3, labels);
	
		this.intersectionTransitionFactory=new IntersectionTransitionBuilder();
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
				this.acceptingPolicy, intersectionStateFactory,
				new IntersectionTransitionBuilder());
		SatisfactionValue returnValue = checker.perform();
		assertTrue("The property must be possibly satisfied",
				returnValue == SatisfactionValue.POSSIBLYSATISFIED);

		System.out.println(checker.getUpperIntersectionBA().toString());
		upperIntersectionBA = checker.getUpperIntersectionBA();
		this.checkStatesPresence();

		ConstraintGenerator cg = new ConstraintGenerator(checker);
		Constraint constraint = cg.perform();
		cg.computeIndispensable();
		cg.computePortReachability();
		cg.coloring();

		ReplacementChecker rc = new ReplacementChecker(
				constraint.getSubProperty(replacement.getModelState()),
				replacement, this.acceptingPolicy);

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
