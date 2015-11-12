/**
 * 
 */
package it.polimi.checker;

import static org.junit.Assert.*;
import it.polimi.automata.BA;
import it.polimi.automata.IBA;
import it.polimi.automata.state.State;
import it.polimi.automata.state.StateFactory;
import it.polimi.automata.transition.ClaimTransitionFactory;
import it.polimi.automata.transition.ModelTransitionFactory;
import it.polimi.automata.transition.Transition;
import it.polimi.automata.transition.TransitionFactory;
import it.polimi.checker.intersection.acceptingpolicies.AcceptingPolicy;
import it.polimi.checker.intersection.acceptingpolicies.AcceptingPolicy.AcceptingType;

import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import rwth.i2.ltl2ba4j.model.IGraphProposition;
import rwth.i2.ltl2ba4j.model.impl.GraphProposition;

/**
 * @author claudiomenghi
 * 
 */
public class ModelCheckerTest {

	/*
	 * Claim 1
	 */
	private BA claim1;
	private State claim1State1;
	private State claimState2;
	private State claim1State3;
	private Transition claim1Transition1;
	private Transition claim1Transition2;
	private Transition claim1Transition3;
	
	/*
	 * Claim 2
	 */
	private BA claim2;
	private State claim2State1;
	private State claim2State2;
	private State claim2State3;
	private Transition claim2Transition1;
	private Transition claim2Transition2;
	private Transition claim2Transition3;
	private Set<IGraphProposition> claim2Transition1Labels;
	private Set<IGraphProposition> claim2Transition2Labels;
	private Set<IGraphProposition> claim2Transition3Labels;

	
	/*
	 * Model 1
	 */
	private IBA model1;
	private State model1State1;
	private State model1State2;
	private State model1State3;
	private Transition model1Transition1;
	private Transition model1Transition2;
	private Transition model1Transition3;
	private Set<IGraphProposition> model1T1Labels;
	private Set<IGraphProposition> model1T2Labels;
	private Set<IGraphProposition> model1T3Labels;

	/*
	 * Model 2
	 */
	private IBA model2;
	private State model2State1;
	private State model2State2;
	private State model2State3;
	private Transition model2Transition1;
	private Transition model2Transition2;
	private Transition model2Transition3;
	private Set<IGraphProposition> model2T1Labels;
	private Set<IGraphProposition> model2T2Labels;
	private Set<IGraphProposition> model2T3Labels;

	

	TransitionFactory<State, Transition> intersectiontransitionFactory=new ClaimTransitionFactory();

	StateFactory statefactory;
	@Before
	public void setUp() {
		TransitionFactory<State, Transition> transitionFactory = new ClaimTransitionFactory();

		this.claim1 = new IBA(transitionFactory);
		statefactory = new StateFactory();
		claim1State1 = statefactory.create("claimState1");
		claimState2 = statefactory.create("claimState2");
		claim1State3 = statefactory.create("claimState3");
		this.claim1.addState(claim1State1);
		this.claim1.addState(claimState2);
		this.claim1.addState(claim1State3);
		
		this.model1T1Labels =  new HashSet<IGraphProposition>();
		model1T1Labels.add(new GraphProposition("a", false));
		claim1Transition1 = transitionFactory.create(model1T1Labels);

		this.model1T2Labels =  new HashSet<IGraphProposition>();
		model1T2Labels.add(new GraphProposition("b", false));
		claim1Transition2 = transitionFactory.create(model1T2Labels);

		this.model1T3Labels = new HashSet<IGraphProposition>();
		model1T3Labels.add(new GraphProposition("c", false));
		claim1Transition3 = transitionFactory.create(model1T3Labels);

		this.claim1.addPropositions(model1T1Labels);
		this.claim1.addPropositions(model1T2Labels);
		this.claim1.addPropositions(model1T3Labels);
		this.claim1.addTransition(claim1State1, claimState2, claim1Transition1);
		this.claim1.addTransition(claimState2, claim1State3, claim1Transition2);
		this.claim1
				.addTransition(claim1State3, claim1State3, claim1Transition3);

		// --------------------------------------
		// CLAIM 2
		// --------------------------------------
		this.claim2 = new BA(new ClaimTransitionFactory()); 
		
		statefactory = new StateFactory();
		claim2State1 = statefactory.create("claimState1");
		claim2State2 = statefactory.create("claimState2");
		claim2State3 = statefactory.create("claimState3");
		this.claim2.addState(claim2State1);
		this.claim2.addState(claim2State2);
		this.claim2.addState(claim2State3);

		this.claim2Transition1Labels =  new HashSet<IGraphProposition>();
		claim2Transition1Labels.add(new GraphProposition("a", false));
		claim2Transition1 = transitionFactory.create(claim2Transition1Labels);

		this.claim2Transition2Labels = new HashSet<IGraphProposition>();
		claim2Transition2Labels.add(new GraphProposition("b", true));
		claim2Transition2 = transitionFactory.create(claim2Transition2Labels);

		this.claim2Transition3Labels = new HashSet<IGraphProposition>();
		claim2Transition3Labels.add(new GraphProposition("c", false));
		claim2Transition3 = transitionFactory.create(claim2Transition3Labels);

		this.claim2.addProposition(new GraphProposition("a", false));
		this.claim2.addProposition(new GraphProposition("b", false));
		this.claim2.addProposition(new GraphProposition("c", false));
		this.claim2
				.addTransition(claim2State1, claim2State2, claim2Transition1);
		this.claim2
				.addTransition(claim2State2, claim2State3, claim2Transition2);
		this.claim2
				.addTransition(claim2State3, claim2State3, claim2Transition3);

		/*
		 * MODEL
		 */
		TransitionFactory<State, Transition> modelTransitionFactory = new ModelTransitionFactory();

		this.model1 = new IBA(modelTransitionFactory);

		StateFactory modelStateFactory = new StateFactory();
		this.model1State1 = modelStateFactory.create("modelState1");
		this.model1State2 = modelStateFactory.create("modelState2");
		this.model1State3 = modelStateFactory.create("modelState3");
		this.model1.addState(model1State1);
		this.model1.addState(model1State2);
		this.model1.addState(model1State3);
		
		Set<IGraphProposition> propositionsModelT1 = new HashSet<IGraphProposition>();
		propositionsModelT1.add(new GraphProposition("a", false));
		this.model1Transition1 = modelTransitionFactory.create(propositionsModelT1);

		Set<IGraphProposition> propositionsModelT2 = new HashSet<IGraphProposition>();
		propositionsModelT2.add(new GraphProposition("b", false));
		this.model1Transition2 = modelTransitionFactory.create(propositionsModelT2);

		Set<IGraphProposition> propositionsModelT3 = new HashSet<IGraphProposition>();
		propositionsModelT3.add(new GraphProposition("c", false));
		this.model1Transition3 = modelTransitionFactory.create(propositionsModelT3);

		this.model1.addPropositions(propositionsModelT1);
		this.model1.addPropositions(propositionsModelT2);
		this.model1.addPropositions(propositionsModelT3);
		this.model1
				.addTransition(model1State1, model1State2, model1Transition1);
		this.model1
				.addTransition(model1State2, model1State3, model1Transition2);
		this.model1
			.addTransition(model1State3, model1State3, model1Transition3);

		/*
		 * MODEL
		 */
	this.model2 = new IBA(modelTransitionFactory);

		modelStateFactory = new StateFactory();
		this.model2State1 = modelStateFactory.create("modelState1");
		this.model2State2 = modelStateFactory.create("modelState2");
		this.model2State3 = modelStateFactory.create("modelState3");
		this.model2.addState(model2State1);
		this.model2.addState(model2State2);
		this.model2.addState(model2State3);

		model2T1Labels = new HashSet<IGraphProposition>();
		model2T1Labels.add(new GraphProposition("a", false));
		this.model2Transition1 = modelTransitionFactory.create(model2T1Labels);

		model2T2Labels =new HashSet<IGraphProposition>();
		model2T2Labels.add(new GraphProposition("b", false));
		this.model2Transition2 = modelTransitionFactory.create(model2T2Labels);

		model2T3Labels = new HashSet<IGraphProposition>();
		model2T3Labels.add(new GraphProposition("c", false));
		this.model2Transition3 = modelTransitionFactory.create(model2T3Labels);

		this.model2.addPropositions(model2T1Labels);
		this.model2.addPropositions(model2T2Labels);
		this.model2.addPropositions(model2T3Labels);
		this.model2
				.addTransition(model2State1, model2State2, model2Transition1);
		this.model2
				.addTransition(model2State2, model2State3, model2Transition2);
		this.model2
				.addTransition(model2State3, model2State3, model2Transition3);
	}

	/**
	 * Test method for
	 * {@link it.polimi.checker.Checker#ModelChecker(null, it.polimi.automata.BA, it.polimi.checker.ModelCheckingResults)}
	 * .
	 */
	@Test(expected = NullPointerException.class)
	public void testModelCheckerNullModel() {
		new Checker(
				null, claim1, AcceptingPolicy.getAcceptingPolicy(AcceptingType.BA, model1, claim1));
	}

	/**
	 * Test method for
	 * {@link it.polimi.checker.Checker#ModelChecker(it.polimi.automata.IBA, null, it.polimi.checker.ModelCheckingResults)}
	 * .
	 */
	@Test(expected = NullPointerException.class)
	public void testModelCheckerNullClaim() {
			new Checker(
				model1, null, AcceptingPolicy.getAcceptingPolicy(AcceptingType.BA, model1, claim1));
	}

	
	/**
	 * Test method for {@link it.polimi.checker.Checker#check()}.
	 */
	@Test
	public void testCheck() {

		Checker mck = new Checker(
				model1, claim1, AcceptingPolicy.getAcceptingPolicy(AcceptingType.BA, model1, claim1));
		assertEquals(SatisfactionValue.SATISFIED, mck.perform());
	}
	
	/**
	 * Test method for {@link it.polimi.checker.Checker#check()}.
	 */
	@Test
	public void testCheck1() {
		model1.addInitialState(model1State1);
		claim1.addInitialState(claim1State1);
		Checker mck = new Checker(
				model1, claim1, AcceptingPolicy.getAcceptingPolicy(AcceptingType.BA, model1, claim1));
		assertEquals(SatisfactionValue.SATISFIED, mck.perform());
	}
	
	/**
	 * Test method for {@link it.polimi.checker.Checker#check()}.
	 */
	@Test
	public void testCheck2() {
		model1.addInitialState(model1State1);
		model1.addAcceptState(model1State3);
		claim1.addInitialState(claim1State1);
		claim1.addAcceptState(claim1State3);
		Checker mck = new Checker(
				model1, claim1, AcceptingPolicy.getAcceptingPolicy(AcceptingType.BA, model1, claim1));
		assertEquals(SatisfactionValue.NOTSATISFIED, mck.perform());
		assertNotNull(mck.getCounterexample());
		assertEquals(10, mck.getIntersectionAutomataSize());
	}
	
	/**
	 * Test method for {@link it.polimi.checker.Checker#check()}.
	 */
	@Test
	public void testCheck3() {
		Checker mck = new Checker(
				model2, claim2, AcceptingPolicy.getAcceptingPolicy(AcceptingType.BA, model1, claim1));
		assertEquals(SatisfactionValue.SATISFIED, mck.perform());
	}
	
	/**
	 * Test method for {@link it.polimi.checker.Checker#check()}.
	 */
	@Test
	public void testCheck5() {
		model2.addInitialState(model2State1);
		claim1.addInitialState(claim1State1);
		claim1.addAcceptState(claim1State3);
		Checker mck = new Checker(
				model2, claim1, AcceptingPolicy.getAcceptingPolicy(AcceptingType.BA, model1, claim1));
		assertEquals(SatisfactionValue.SATISFIED, mck.perform());
	}
	
	/**
	 * Test method for {@link it.polimi.checker.Checker#check()}.
	 */
	@Test
	public void testCheck6() {
		model1.addInitialState(model1State1);
		model1.addAcceptState(model1State3);
		model1.addBlackBoxState(model1State2);
		claim1.addInitialState(claim1State1);
		claim1.addAcceptState(claim1State3);
		Checker mck = new Checker(
				model1, claim1, AcceptingPolicy.getAcceptingPolicy(AcceptingType.BA, model1, claim1));
		assertEquals(SatisfactionValue.POSSIBLYSATISFIED, mck.perform());
	}
	

	/**
	 * Test method for
	 * {@link it.polimi.checker.Checker#getVerificationResults()}.
	 */
	@Test
	public void testGetVerificationTimes() {
		model1.addInitialState(model1State1);
		model1.addAcceptState(model1State3);
		model1.addBlackBoxState(model1State2);
		claim1.addInitialState(claim1State1);
		claim1.addAcceptState(claim1State3);
		Checker mck = new Checker(
				model1, claim1, AcceptingPolicy.getAcceptingPolicy(AcceptingType.BA, model1, claim1));
		assertNotNull(mck);
	}
	
	/**
	 * Test method for {@link it.polimi.checker.Checker#check()}.
	 */
	@Test
	public void testCheck7() {
		model1.addInitialState(model1State1);
		model1.addAcceptState(model1State3);
		model1.addBlackBoxState(model1State2);
		claim1.addInitialState(claim1State1);
		claim1.addAcceptState(claim1State3);
		Checker mck = new Checker(
				model1, claim1, AcceptingPolicy.getAcceptingPolicy(AcceptingType.BA, model1, claim1));
		assertEquals(SatisfactionValue.POSSIBLYSATISFIED, mck.perform());
		assertEquals(SatisfactionValue.POSSIBLYSATISFIED, mck.perform());
	}
	
	/**
	 * Test method for {@link it.polimi.checker.Checker#check()}.
	 */
	@Test
	public void testCheckSize() {
		model1.addInitialState(model1State1);
		model1.addAcceptState(model1State3);
		model1.addBlackBoxState(model1State2);
		claim1.addInitialState(claim1State1);
		claim1.addAcceptState(claim1State3);
		Checker mck = new Checker(
				model1, claim1, AcceptingPolicy.getAcceptingPolicy(AcceptingType.BA, model1, claim1));
		assertEquals(SatisfactionValue.POSSIBLYSATISFIED, mck.perform());
		assertEquals(13 ,mck.getUpperIntersectionBA().size());
		assertEquals(1,mck.getLowerIntersectionBA().size());
		assertEquals(14, mck.getIntersectionAutomataSize());
	}
	
	/**
	 * Test method for {@link it.polimi.checker.Checker#check()}.
	 */
	@Test(expected=IllegalStateException.class)
	public void testCheckUpperIntersectionBuilderException() {
		model1.addInitialState(model1State1);
		model1.addAcceptState(model1State3);
		claim1.addInitialState(claim1State1);
		claim1.addAcceptState(claim1State3);
		Checker mck = new Checker(
				model1, claim1, AcceptingPolicy.getAcceptingPolicy(AcceptingType.BA, model1, claim1));
		mck.perform();
		mck.getUpperIntersectionBA();
	}
	
	/**
	 * Test method for {@link it.polimi.checker.Checker#check()}.
	 */
	@Test(expected=IllegalStateException.class)
	public void testCheckLowerIntersectionBuilderNotPerformedException() {
		model1.addInitialState(model1State1);
		model1.addAcceptState(model1State3);
		claim1.addInitialState(claim1State1);
		claim1.addAcceptState(claim1State3);
		Checker mck = new Checker(
				model1, claim1, AcceptingPolicy.getAcceptingPolicy(AcceptingType.BA, model1, claim1));
		mck.getUpperIntersectionBA();
	}
	
	/**
	 * Test method for {@link it.polimi.checker.Checker#check()}.
	 */
	@Test(expected=IllegalStateException.class)
	public void testCheckUpperIntersectionBuilderNotPerformedException() {
		model1.addInitialState(model1State1);
		model1.addAcceptState(model1State3);
		claim1.addInitialState(claim1State1);
		claim1.addAcceptState(claim1State3);
		Checker mck = new Checker(
				model1, claim1, AcceptingPolicy.getAcceptingPolicy(AcceptingType.BA, model1, claim1));
		mck.getUpperIntersectionBA();
	}
	
	/**
	 * Test method for {@link it.polimi.checker.Checker#check()}.
	 */
	@Test(expected=IllegalStateException.class)
	public void testGetUpperIntersectionBuilderException() {
		model1.addInitialState(model1State1);
		model1.addAcceptState(model1State3);
		claim1.addInitialState(claim1State1);
		claim1.addAcceptState(claim1State3);
		Checker mck = new Checker(
				model1, claim1, AcceptingPolicy.getAcceptingPolicy(AcceptingType.BA, model1, claim1));
		mck.getUpperIntersectionBuilder();
	}
	
	/**
	 * Test method for {@link it.polimi.checker.Checker#check()}.
	 */
	@Test
	public void testGetUpperIntersectionBuilder() {
		model1.addInitialState(model1State1);
		model1.addAcceptState(model1State3);
		model1.addBlackBoxState(model1State2);
		claim1.addInitialState(claim1State1);
		claim1.addAcceptState(claim1State3);
		Checker mck = new Checker(
				model1, claim1, AcceptingPolicy.getAcceptingPolicy(AcceptingType.BA, model1, claim1));
		mck.perform();
		assertNotNull(mck.getUpperIntersectionBuilder());
	}
}
