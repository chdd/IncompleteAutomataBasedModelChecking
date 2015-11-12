package it.polimi.checker.intersection;

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

public class IntersectionBuilderGetModelStateTest {
	/*
	 * Claim
	 */
	private BA claim;
	private State claim1State1;
	private State claimState2;
	private State claim1State3;
	private Transition claim1Transition1;
	private Transition claim1Transition2;
	private Transition claim1Transition3;

	/*
	 * Model
	 */
	private IBA model;
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
		this.claim = new IBA(transitionFactory);

		stateFactory = new StateFactory();
		claim1State1 = stateFactory.create("claimState1");
		claimState2 = stateFactory.create("claimState2");
		claim1State3 = stateFactory.create("claimState3");
		this.claim.addState(claim1State1);
		this.claim.addState(claimState2);
		this.claim.addState(claim1State3);

		propositionsT1 = new HashSet<IGraphProposition>();
		propositionsT1.add(new GraphProposition("a", false));
		claim1Transition1 = transitionFactory.create(propositionsT1);

		propositionsT2 = new HashSet<IGraphProposition>();
		propositionsT2.add(new GraphProposition("b", false));
		claim1Transition2 = transitionFactory.create(propositionsT2);

		propositionsT3 = new HashSet<IGraphProposition>();
		propositionsT3.add(new GraphProposition("c", false));
		claim1Transition3 = transitionFactory.create(propositionsT3);

		this.claim.addPropositions(propositionsT1);
		this.claim.addPropositions(propositionsT2);
		this.claim.addPropositions(propositionsT3);
		this.claim.addTransition(claim1State1, claimState2, claim1Transition1);
		this.claim.addTransition(claimState2, claim1State3, claim1Transition2);
		this.claim
				.addTransition(claim1State3, claim1State3, claim1Transition3);

		

		/*
		 * MODEL
		 */
		this.model = new IBA(transitionFactory);

		StateFactory modelStateFactory = new StateFactory();
		this.model2State1 = modelStateFactory.create("modelState1");
		this.model2State2 = modelStateFactory.create("modelState2");
		this.model2State3 = modelStateFactory.create("modelState3");
		this.model.addState(model2State1);
		this.model.addState(model2State2);
		this.model.addState(model2State3);

		TransitionFactory<State, Transition> modelTransitionFactory = new ModelTransitionFactory();

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

		this.model.addPropositions(propositionsModel2T1);
		this.model.addPropositions(propositionsModel2T2);
		this.model.addPropositions(propositionsModel2T3);
		this.model
				.addTransition(model2State1, model2State2, model2Transition1);
		this.model
				.addTransition(model2State2, model2State3, model2Transition2);
		this.model
				.addTransition(model2State3, model2State3, model2Transition3);
	}

	/**
	 * Test method for
	 * {@link it.polimi.checker.intersection.IntersectionBuilder#computeIntersection()}
	 * .
	 */
	@Test(expected = NullPointerException.class)
	public void testGetNumberNullArgument() {
		this.model.addInitialState(model2State1);
		this.model.addBlackBoxState(model2State2);
		this.model.addAcceptState(model2State3);
		this.claim.addInitialState(claim1State1);
		this.claim.addAcceptState(claim1State3);
		IntersectionBuilder intersectionBuilder = new IntersectionBuilder(
				model, claim, AcceptingPolicy.getAcceptingPolicy(
						AcceptingType.BA, this.model, this.claim));

		intersectionBuilder.perform();
		intersectionBuilder.getModelState(null);
	}

	/**
	 * Test method for
	 * {@link it.polimi.checker.intersection.IntersectionBuilder#computeIntersection()}
	 * .
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testGetNumberIllegalArgument() {
		this.model.addInitialState(model2State1);
		this.model.addBlackBoxState(model2State2);
		this.model.addAcceptState(model2State3);
		this.claim.addInitialState(claim1State1);
		this.claim.addAcceptState(claim1State3);
		IntersectionBuilder intersectionBuilder = new IntersectionBuilder(
				model, claim, AcceptingPolicy.getAcceptingPolicy(
						AcceptingType.BA, this.model, this.claim));

		intersectionBuilder.perform();
		intersectionBuilder.getModelState(new StateFactory().create());
	}

	/**
	 * Test method for
	 * {@link it.polimi.checker.intersection.IntersectionBuilder#computeIntersection()}
	 * .
	 */
	@Test
	public void testGetNumber() {
		this.model.addInitialState(model2State1);
		this.model.addBlackBoxState(model2State2);
		this.model.addAcceptState(model2State3);
		this.claim.addInitialState(claim1State1);
		this.claim.addAcceptState(claim1State3);
		IntersectionBuilder intersectionBuilder = new IntersectionBuilder(
				model, claim, AcceptingPolicy.getAcceptingPolicy(
						AcceptingType.BA, this.model, this.claim));

		intersectionBuilder.perform();

		State intersectionState = intersectionBuilder.getIntersectionState(
				model2State1, claim1State1, 0);
		assertEquals(
				"The state of the model returned corresponds to the state of the model",
				model2State1, intersectionBuilder.getModelState(intersectionState));
	}

}
