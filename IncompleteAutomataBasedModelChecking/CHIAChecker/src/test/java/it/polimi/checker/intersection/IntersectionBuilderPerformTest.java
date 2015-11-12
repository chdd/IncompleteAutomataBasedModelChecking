package it.polimi.checker.intersection;

import static org.junit.Assert.*;
import it.polimi.automata.BA;
import it.polimi.automata.IBA;
import it.polimi.automata.IntersectionBA;
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

public class IntersectionBuilderPerformTest {

	
	/*
	 * Claim
	 */
	private BA claim;
	private State claimState1;
	private State claimState2;
	private State claimState3;
	private Transition claimTransition1;
	private Transition claimTransition2;
	private Transition claimTransition3;

	/*
	 * Model
	 */
	private IBA model;
	private State modelState1;
	private State modelState2;
	private State modelState3;
	private Transition modelTransition1;
	private Transition modelTransition2;
	private Transition modelTransition3;

	private Set<IGraphProposition> propositionsT1;
	private Set<IGraphProposition> propositionsT2;
	private Set<IGraphProposition> propositionsT3;

	private StateFactory stateFactory;

	@Before
	public void setUp() {
		TransitionFactory<State, Transition> transitionFactory = new ClaimTransitionFactory();
		this.claim = new IBA(transitionFactory);

		stateFactory = new StateFactory();
		claimState1 = stateFactory.create("1",1);
		claimState2 = stateFactory.create("2",2);
		claimState3 = stateFactory.create("3",3);
		this.claim.addState(claimState1);
		this.claim.addState(claimState2);
		this.claim.addState(claimState3);

		propositionsT1 = new HashSet<IGraphProposition>();
		propositionsT1.add(new GraphProposition("a", false));
		claimTransition1 = transitionFactory.create(propositionsT1);

		propositionsT2 = new HashSet<IGraphProposition>();
		propositionsT2.add(new GraphProposition("b", false));
		claimTransition2 = transitionFactory.create(propositionsT2);

		propositionsT3 = new HashSet<IGraphProposition>();
		propositionsT3.add(new GraphProposition("c", false));
		claimTransition3 = transitionFactory.create(propositionsT3);

		this.claim.addPropositions(propositionsT1);
		this.claim.addPropositions(propositionsT2);
		this.claim.addPropositions(propositionsT3);
		this.claim.addTransition(claimState1, claimState2, claimTransition1);
		this.claim.addTransition(claimState2, claimState3, claimTransition2);
		this.claim
				.addTransition(claimState3, claimState3, claimTransition3);

		

		/*
		 * MODEL
		 */
		this.model = new IBA(transitionFactory);

		StateFactory modelStateFactory = new StateFactory();
		this.modelState1 = modelStateFactory.create("4",4);
		this.modelState2 = modelStateFactory.create("5",5);
		this.modelState3 = modelStateFactory.create("6",6);
		this.model.addState(modelState1);
		this.model.addState(modelState2);
		this.model.addState(modelState3);

		TransitionFactory<State, Transition> modelTransitionFactory = new ModelTransitionFactory();

		Set<IGraphProposition> propositionsModelT1 = new HashSet<IGraphProposition>();
		propositionsModelT1.add(new GraphProposition("a", false));
		this.modelTransition1 = modelTransitionFactory
				.create(propositionsModelT1);

		Set<IGraphProposition> propositionsModel2T2 = new HashSet<IGraphProposition>();
		propositionsModel2T2.add(new GraphProposition("b", false));
		this.modelTransition2 = modelTransitionFactory
				.create(propositionsModel2T2);

		Set<IGraphProposition> propositionsModel2T3 = new HashSet<IGraphProposition>();
		propositionsModel2T3.add(new GraphProposition("c", false));
		this.modelTransition3 = modelTransitionFactory
				.create(propositionsModel2T3);

		this.model.addPropositions(propositionsModelT1);
		this.model.addPropositions(propositionsModel2T2);
		this.model.addPropositions(propositionsModel2T3);
		this.model
				.addTransition(modelState1, modelState2, modelTransition1);
		this.model
				.addTransition(modelState2, modelState3, modelTransition2);
		this.model
				.addTransition(modelState3, modelState3, modelTransition3);
	}
	
	/**
	 * Test method for
	 * {@link it.polimi.checker.intersection.IntersectionBuilder#perform()}
	 * .
	 */
	@Test
	public void testgetIntersectionStateIllegalStateException() {
		this.model.addInitialState(modelState1);
		this.model.addBlackBoxState(modelState2);
		this.model.addAcceptState(modelState3);
		this.claim.addInitialState(claimState1);
		this.claim.addAcceptState(claimState3);
		IntersectionBuilder intersectionBuilder = new IntersectionBuilder(
				model, claim, AcceptingPolicy.getAcceptingPolicy(
						AcceptingType.BA, this.model, this.claim));
		IntersectionBA intersectionBa=intersectionBuilder.perform();
		assertTrue("The intersection is not computed twice on the same automata", intersectionBa==intersectionBuilder.perform());
	}

	
	

}
