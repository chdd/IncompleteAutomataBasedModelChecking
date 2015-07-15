/**
 * 
 */
package it.polimi.checker.intersection;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import it.polimi.automata.BA;
import it.polimi.automata.IBA;
import it.polimi.automata.IntersectionBA;
import it.polimi.automata.state.IntersectionStateFactory;
import it.polimi.automata.state.State;
import it.polimi.automata.state.StateFactory;
import it.polimi.automata.transition.ClaimTransitionFactory;
import it.polimi.automata.transition.ModelTransitionFactory;
import it.polimi.automata.transition.Transition;
import it.polimi.automata.transition.TransitionFactory;
import it.polimi.checker.emptiness.EmptinessChecker;
import it.polimi.checker.intersection.acceptingpolicies.AcceptingPolicy;
import it.polimi.checker.intersection.acceptingpolicies.AcceptingPolicy.AcceptingType;

import java.util.ArrayList;
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
public class IntersectionBuilderTest {

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

	private Set<IGraphProposition> propositionsT1;
	private Set<IGraphProposition> propositionsT2;
	private Set<IGraphProposition> propositionsT3;
	
	private StateFactory stateFactory;
	
	@Before
	public void setUp() {
		TransitionFactory<State, Transition> transitionFactory=new ClaimTransitionFactory();
		this.claim1 = new IBA(transitionFactory);

		stateFactory = new StateFactory();
		claim1State1 = stateFactory.create("claimState1");
		claimState2 = stateFactory.create("claimState2");
		claim1State3 = stateFactory.create("claimState3");
		this.claim1.addState(claim1State1);
		this.claim1.addState(claimState2);
		this.claim1.addState(claim1State3);
		
		 propositionsT1 = new HashSet<IGraphProposition>();
		propositionsT1.add(new GraphProposition("a", false));
		claim1Transition1 = transitionFactory.create(propositionsT1);

		 propositionsT2 = new HashSet<IGraphProposition>();
		propositionsT2.add(new GraphProposition("b", false));
		claim1Transition2 = transitionFactory.create(propositionsT2);

		 propositionsT3 = new HashSet<IGraphProposition>();
		propositionsT3.add(new GraphProposition("c", false));
		claim1Transition3 = transitionFactory.create(propositionsT3);

		this.claim1.addPropositions(propositionsT1);
		this.claim1.addPropositions(propositionsT2);
		this.claim1.addPropositions(propositionsT3);
		this.claim1.addTransition(claim1State1, claimState2, claim1Transition1);
		this.claim1.addTransition(claimState2, claim1State3, claim1Transition2);
		this.claim1
				.addTransition(claim1State3, claim1State3, claim1Transition3);

		// --------------------------------------
		// CLAIM 2
		// --------------------------------------
		this.claim2 = new IBA(transitionFactory);

		stateFactory = new StateFactory();
		claim2State1 = stateFactory.create("claimState1");
		claim2State2 = stateFactory.create("claimState2");
		claim2State3 = stateFactory.create("claimState3");
		this.claim2.addState(claim2State1);
		this.claim2.addState(claim2State2);
		this.claim2.addState(claim2State3);

		Set<IGraphProposition> claim2PropositionsT1 = new HashSet<IGraphProposition>();
		claim2PropositionsT1.add(new GraphProposition("a", false));
		claim2Transition1 = transitionFactory.create(claim2PropositionsT1);

		Set<IGraphProposition> claim2PropositionsT2 = new HashSet<IGraphProposition>();
		claim2PropositionsT2.add(new GraphProposition("b", true));
		claim2Transition2 = transitionFactory.create(claim2PropositionsT2);

		Set<IGraphProposition> claim2PropositionsT3 = new HashSet<IGraphProposition>();
		claim2PropositionsT3.add(new GraphProposition("c", false));
		claim2Transition3 = transitionFactory.create(claim2PropositionsT3);

		this.claim2.addPropositions(claim2PropositionsT1);
		this.claim2.addPropositions(claim2PropositionsT2);
		this.claim2.addPropositions(claim2PropositionsT3);
		this.claim2
				.addTransition(claim2State1, claim2State2, claim2Transition1);
		this.claim2
				.addTransition(claim2State2, claim2State3, claim2Transition2);
		this.claim2
				.addTransition(claim2State3, claim2State3, claim2Transition3);

		/*
		 * MODEL
		 */
		this.model1 = new IBA(transitionFactory);

		StateFactory modelStateFactory = new StateFactory();
		this.model1State1 = modelStateFactory.create("modelState1");
		this.model1State2 = modelStateFactory.create("modelState2");
		this.model1State3 = modelStateFactory.create("modelState3");
		this.model1.addState(model1State1);
		this.model1.addState(model1State2);
		this.model1.addState(model1State3);
		TransitionFactory<State, Transition> modelTransitionFactory = new ModelTransitionFactory();

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
		this.model2 = new IBA(transitionFactory);

		modelStateFactory = new StateFactory();
		this.model2State1 = modelStateFactory.create("modelState1");
		this.model2State2 = modelStateFactory.create("modelState2");
		this.model2State3 = modelStateFactory.create("modelState3");
		this.model2.addState(model2State1);
		this.model2.addState(model2State2);
		this.model2.addState(model2State3);

		Set<IGraphProposition> propositionsModel2T1 = new HashSet<IGraphProposition>();
		propositionsModel2T1.add(new GraphProposition("a", false));
		this.model2Transition1 = modelTransitionFactory.create(propositionsModel2T1);

		Set<IGraphProposition> propositionsModel2T2 = new HashSet<IGraphProposition>();
		propositionsModel2T2.add(new GraphProposition("b", false));
		this.model2Transition2 = modelTransitionFactory.create(propositionsModel2T2);

		Set<IGraphProposition> propositionsModel2T3 = new HashSet<IGraphProposition>();
		propositionsModel2T3.add(new GraphProposition("c", false));
		this.model2Transition3 = modelTransitionFactory.create(propositionsModel2T3);

		this.model2.addPropositions(propositionsModel2T1);
		this.model2.addPropositions(propositionsModel2T2);
		this.model2.addPropositions(propositionsModel2T3);
		this.model2
				.addTransition(model2State1, model2State2, model2Transition1);
		this.model2
				.addTransition(model2State2, model2State3, model2Transition2);
		this.model2
				.addTransition(model2State3, model2State3, model2Transition3);
	}

	

	

	
	/**
	 * Test method for
	 * {@link it.polimi.checker.intersection.IntersectionBuilder#IntersectionBuilder(it.polimi.checker.intersection.IntersectionRule, it.polimi.automata.state.StateFactory, it.polimi.automata.IntersectionBAFactory, it.polimi.automata.transition.TransitionFactory, null, it.polimi.automata.BA)}
	 * .
	 */
	@Test(expected = NullPointerException.class)
	public void testIntersectionBuilderNullModel() {
		new IntersectionBuilder(
				 null, claim1, AcceptingPolicy.getAcceptingPolicy(AcceptingType.BA), new IntersectionStateFactory(), new IntersectionTransitionBuilder());
	}

	/**
	 * Test method for
	 * {@link it.polimi.checker.intersection.IntersectionBuilder#IntersectionBuilder(it.polimi.checker.intersection.IntersectionRule, it.polimi.automata.state.StateFactory, it.polimi.automata.IntersectionBAFactory, it.polimi.automata.transition.TransitionFactory, it.polimi.automata.IBA, null)}
	 * .
	 */
	@Test(expected = NullPointerException.class)
	public void testIntersectionBuilderNullClaim() {
		new IntersectionBuilder(
				model1, null, AcceptingPolicy.getAcceptingPolicy(AcceptingType.BA), new IntersectionStateFactory(), new IntersectionTransitionBuilder());
	}

	

	/**
	 * Test method for
	 * {@link it.polimi.checker.intersection.IntersectionBuilder#computeIntersection()}
	 * .
	 */
	@Test
	public void testComputeIntersection() {
		IntersectionBA intersection = new IntersectionBuilder(
				 model1, claim1, AcceptingPolicy.getAcceptingPolicy(AcceptingType.BA), new IntersectionStateFactory(), new IntersectionTransitionBuilder())
				.perform();
		assertTrue(intersection.getInitialStates().isEmpty());
	}

	/**
	 * Test method for
	 * {@link it.polimi.checker.intersection.IntersectionBuilder#computeIntersection()}
	 * .
	 */
	@Test
	public void testComputeIntersection1() {
		this.model1.addInitialState(model1State1);
		IntersectionBA intersection = new IntersectionBuilder(
				 model1, claim1, AcceptingPolicy.getAcceptingPolicy(AcceptingType.BA), new IntersectionStateFactory(), new IntersectionTransitionBuilder())
				.perform();
		assertTrue(intersection.getInitialStates().isEmpty());
	}

	/**
	 * Test method for
	 * {@link it.polimi.checker.intersection.IntersectionBuilder#computeIntersection()}
	 * .
	 */
	@Test
	public void testComputeIntersection2() {
		this.claim1.addInitialState(claim1State1);
		IntersectionBA intersection = new IntersectionBuilder(
				 model1, claim1, AcceptingPolicy.getAcceptingPolicy(AcceptingType.BA), new IntersectionStateFactory(), new IntersectionTransitionBuilder())
				.perform();
		assertTrue(intersection.getInitialStates().isEmpty());
	}

	/**
	 * Test method for
	 * {@link it.polimi.checker.intersection.IntersectionBuilder#computeIntersection()}
	 * .
	 */
	@Test
	public void testComputeIntersection3() {

		this.model1.addInitialState(model1State1);
		this.claim1.addInitialState(claim1State1);
		IntersectionBA intersection = new IntersectionBuilder(
				 model1, claim1, AcceptingPolicy.getAcceptingPolicy(AcceptingType.BA), new IntersectionStateFactory(), new IntersectionTransitionBuilder())
				.perform();
		assertEquals(model1State1.getId() + " - " + claim1State1.getId()
				+ " - " + 0,
				new ArrayList<State>(intersection.getInitialStates()).get(0)
						.getName());
		assertTrue(intersection.getStates().size() == 3);
		assertTrue(new EmptinessChecker(
				intersection).isEmpty());
	}

	/**
	 * Test method for
	 * {@link it.polimi.checker.intersection.IntersectionBuilder#computeIntersection()}
	 * .
	 */
	@Test
	public void testComputeIntersection4() {

		this.model1.addInitialState(model1State1);
		this.model1.addAcceptState(model1State3);
		this.claim1.addInitialState(claim1State1);
		this.claim1.addAcceptState(claim1State3);
		IntersectionBA intersection = new IntersectionBuilder(
				 model1, claim1, AcceptingPolicy.getAcceptingPolicy(AcceptingType.BA), new IntersectionStateFactory(), new IntersectionTransitionBuilder())
				.perform();
		assertEquals(model1State1.getId() + " - " + claim1State1.getId()
				+ " - " + 0,
				new ArrayList<State>(intersection.getInitialStates()).get(0)
						.getName());
		assertTrue(intersection.getStates().size() == 5);
		assertFalse(new EmptinessChecker(
				intersection).isEmpty());
	}

	/**
	 * Test method for
	 * {@link it.polimi.checker.intersection.IntersectionBuilder#computeIntersection()}
	 * .
	 */
	@Test
	public void testComputeIntersection5() {
		this.model1.addInitialState(model1State1);
		this.model1.addAcceptState(model1State3);
		this.claim1.addInitialState(claim1State1);
		this.claim1.addAcceptState(claim1State3);
		Transition int1 = new ArrayList<Transition>(
				this.model1.getOutTransitions(new ArrayList<State>(this.model1
						.getInitialStates()).get(0))).get(0);

		assertEquals(this.propositionsT1, int1.getPropositions());
		Transition int2 = new ArrayList<Transition>(
				this.model1.getOutTransitions(this.model1
						.getTransitionDestination(int1))).get(0);
		assertEquals(this.propositionsT2, int2.getPropositions());
		Transition int3 = new ArrayList<Transition>(
				this.model1.getOutTransitions(this.model1
						.getTransitionDestination(int2))).get(0);
		assertEquals(this.propositionsT3, int3.getPropositions());

		Transition int4 = new ArrayList<Transition>(
				this.model1.getOutTransitions(this.model1
						.getTransitionDestination(int3))).get(0);
		assertEquals(this.propositionsT3, int4.getPropositions());
		Transition int5 = new ArrayList<Transition>(
				this.model1.getOutTransitions(this.model1
						.getTransitionDestination(int4))).get(0);
		assertEquals(this.propositionsT3, int5.getPropositions());

	}

	/**
	 * Test method for
	 * {@link it.polimi.checker.intersection.IntersectionBuilder#computeIntersection()}
	 * .
	 */
	@Test
	public void testComputeIntersection6() {

		this.model1.addInitialState(model1State1);
		this.model1.addAcceptState(model1State3);
		this.claim2.addInitialState(claim2State1);
		this.claim2.addAcceptState(claim2State3);
		IntersectionBA intersection = new IntersectionBuilder(
				 model1, claim2, AcceptingPolicy.getAcceptingPolicy(AcceptingType.BA), new IntersectionStateFactory(), new IntersectionTransitionBuilder())
				.perform();
		assertTrue(new EmptinessChecker(
				intersection).isEmpty());
	}

	/**
	 * Test method for
	 * {@link it.polimi.checker.intersection.IntersectionBuilder#computeIntersection()}
	 * .
	 */
	@Test
	public void testComputeIntersection7() {

		this.model2.addInitialState(model2State1);
		this.model2.addTransparentState(model2State2);
		this.model2.addAcceptState(model2State3);
		this.claim1.addInitialState(claim1State1);
		this.claim1.addAcceptState(claim1State3);
		IntersectionBuilder intersectionBuilder = new IntersectionBuilder(
				 model2, claim1, AcceptingPolicy.getAcceptingPolicy(AcceptingType.BA), new IntersectionStateFactory(), new IntersectionTransitionBuilder());

		IntersectionBA intersection = intersectionBuilder
				.perform();
		assertFalse(new EmptinessChecker(
				intersection).isEmpty());
		assertTrue(intersection.getStates().size() == 6);
		assertFalse(intersection.getInitialStates().isEmpty());
	}

	

}
