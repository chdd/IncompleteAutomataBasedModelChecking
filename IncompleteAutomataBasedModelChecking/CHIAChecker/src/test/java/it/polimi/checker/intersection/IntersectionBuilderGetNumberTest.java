package it.polimi.checker.intersection;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Set;

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

import org.junit.Before;
import org.junit.Test;

import rwth.i2.ltl2ba4j.model.IGraphProposition;
import rwth.i2.ltl2ba4j.model.impl.GraphProposition;

public class IntersectionBuilderGetNumberTest {

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
		TransitionFactory<State, Transition> transitionFactory = new ClaimTransitionFactory();
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
		this.claim2 = new BA(new ClaimTransitionFactory());

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

		Set<IGraphProposition> claim2Propositions = new HashSet<IGraphProposition>();
		claim2Propositions.add(new GraphProposition("a", false));
		claim2Propositions.add(new GraphProposition("b", false));
		claim2Propositions.add(new GraphProposition("c", false));

		this.claim2.addPropositions(claim2Propositions);
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
		this.model1Transition1 = modelTransitionFactory
				.create(propositionsModelT1);

		Set<IGraphProposition> propositionsModelT2 = new HashSet<IGraphProposition>();
		propositionsModelT2.add(new GraphProposition("b", false));
		this.model1Transition2 = modelTransitionFactory
				.create(propositionsModelT2);

		Set<IGraphProposition> propositionsModelT3 = new HashSet<IGraphProposition>();
		propositionsModelT3.add(new GraphProposition("c", false));
		this.model1Transition3 = modelTransitionFactory
				.create(propositionsModelT3);

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
		this.model2Transition1 = modelTransitionFactory
				.create(propositionsModel2T1);

		Set<IGraphProposition> propositionsModel2T2 = new HashSet<IGraphProposition>();
		propositionsModel2T2.add(new GraphProposition("b", false));
		this.model2Transition2 = modelTransitionFactory
				.create(propositionsModel2T2);

		Set<IGraphProposition> propositionsModel2T3 = new HashSet<IGraphProposition>();
		propositionsModel2T3.add(new GraphProposition("c", false));
		this.model2Transition3 = modelTransitionFactory
				.create(propositionsModel2T3);

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
	 * {@link it.polimi.checker.intersection.IntersectionBuilder#computeIntersection()}
	 * .
	 */
	@Test(expected = NullPointerException.class)
	public void testGetNumberNullArgument() {
		this.model2.addInitialState(model2State1);
		this.model2.addBlackBoxState(model2State2);
		this.model2.addAcceptState(model2State3);
		this.claim1.addInitialState(claim1State1);
		this.claim1.addAcceptState(claim1State3);
		IntersectionBuilder intersectionBuilder = new IntersectionBuilder(
				model2, claim1, AcceptingPolicy.getAcceptingPolicy(
						AcceptingType.BA, this.model2, this.claim1));

		intersectionBuilder.perform();
		intersectionBuilder.getNumber(null);
	}

	/**
	 * Test method for
	 * {@link it.polimi.checker.intersection.IntersectionBuilder#computeIntersection()}
	 * .
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testGetNumberIllegalArgument() {
		this.model2.addInitialState(model2State1);
		this.model2.addBlackBoxState(model2State2);
		this.model2.addAcceptState(model2State3);
		this.claim1.addInitialState(claim1State1);
		this.claim1.addAcceptState(claim1State3);
		IntersectionBuilder intersectionBuilder = new IntersectionBuilder(
				model2, claim1, AcceptingPolicy.getAcceptingPolicy(
						AcceptingType.BA, this.model2, this.claim1));

		intersectionBuilder.perform();
		intersectionBuilder.getNumber(new StateFactory().create());
	}

	/**
	 * Test method for
	 * {@link it.polimi.checker.intersection.IntersectionBuilder#computeIntersection()}
	 * .
	 */
	@Test
	public void testGetNumber() {
		this.model2.addInitialState(model2State1);
		this.model2.addBlackBoxState(model2State2);
		this.model2.addAcceptState(model2State3);
		this.claim1.addInitialState(claim1State1);
		this.claim1.addAcceptState(claim1State3);
		IntersectionBuilder intersectionBuilder = new IntersectionBuilder(
				model2, claim1, AcceptingPolicy.getAcceptingPolicy(
						AcceptingType.BA, this.model2, this.claim1));

		intersectionBuilder.perform();

		State intersectionState = intersectionBuilder.getIntersectionState(
				model2State1, claim1State1, 0);
		assertEquals(
				"The number of the intersection state corresponds to the number -",
				0, intersectionBuilder.getNumber(intersectionState));
	}

}
