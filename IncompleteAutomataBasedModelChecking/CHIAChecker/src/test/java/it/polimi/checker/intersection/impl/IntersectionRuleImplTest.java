/**
 * 
 */
package it.polimi.checker.intersection.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import it.polimi.automata.state.State;
import it.polimi.automata.state.StateFactory;
import it.polimi.automata.state.StateFactory;
import it.polimi.automata.transition.Transition;
import it.polimi.automata.transition.TransitionFactory;
import it.polimi.automata.transition.ClaimTransitionFactory;

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
public class IntersectionRuleImplTest {
	private Transition modelTransition;
	private Transition claimTransition;
	
	private StateFactory<State> stateFactory;

	private TransitionFactory<State, Transition> transitionFactory;

	TransitionFactory<State, Transition> intersectiontransitionFactory=new ClaimTransitionFactory<State>();
	
	@Before
	public void setUp() {
		transitionFactory = new ClaimTransitionFactory<State>();
		stateFactory=new StateFactory();
		Set<IGraphProposition> modelPropositions = new HashSet<IGraphProposition>();
		modelPropositions.add(new GraphProposition("a", false));
		modelTransition = transitionFactory.create(modelPropositions);

		Set<IGraphProposition> claimPropositions = new HashSet<IGraphProposition>();
		claimPropositions.add(new GraphProposition("b", true));
		claimTransition = transitionFactory.create(claimPropositions);

	}

	
	/**
	 * Test method for
	 * {@link it.polimi.checker.intersection.impl.IntersectionRuleImpl#getIntersectionTransition(it.polimi.automata.transition.Transition, null, it.polimi.automata.transition.TransitionFactory)}
	 * .
	 */
	@Test(expected = NullPointerException.class)
	public void testGetIntersectionNullClaimTransition() {
		new IntersectionRuleImpl<State, Transition>(intersectiontransitionFactory,this.stateFactory)
				.getIntersectionTransition(modelTransition, null);
	}

	
	/**
	 * Test method for
	 * {@link it.polimi.checker.intersection.impl.IntersectionRuleImpl#getIntersectionTransition(it.polimi.automata.transition.Transition, it.polimi.automata.transition.Transition, it.polimi.automata.transition.TransitionFactory)}
	 * .
	 */
	@Test
	public void testGetIntersectionTransition() {
		assertNotNull(new IntersectionRuleImpl<State, Transition>(intersectiontransitionFactory,this.stateFactory)
				.getIntersectionTransition(modelTransition, claimTransition));
	}

	/**
	 * Test method for
	 * {@link it.polimi.checker.intersection.impl.IntersectionRuleImpl#getIntersectionTransition(it.polimi.automata.transition.Transition, it.polimi.automata.transition.Transition, it.polimi.automata.transition.TransitionFactory)}
	 * .
	 */
	@Test
	public void testGetIntersectionTransition2() {

		Set<IGraphProposition> modelPropositions = new HashSet<IGraphProposition>();
		modelPropositions.add(new GraphProposition("b", false));
		modelTransition = transitionFactory.create(modelPropositions);

		Set<IGraphProposition> claimPropositions = new HashSet<IGraphProposition>();
		claimPropositions.add(new GraphProposition("b", false));
		claimTransition = transitionFactory.create(claimPropositions);

		Set<IGraphProposition> intersectionPropositions = new HashSet<IGraphProposition>();
		intersectionPropositions.add(new GraphProposition("b", false));
		Transition intersectionTransition = transitionFactory
				.create(intersectionPropositions);

		assertEquals(
				intersectionTransition.getPropositions(),
				new IntersectionRuleImpl<State, Transition>(intersectiontransitionFactory,this.stateFactory)
						.getIntersectionTransition(modelTransition,
								claimTransition)
						.getPropositions());
	}

	/**
	 * Test method for
	 * {@link it.polimi.checker.intersection.impl.IntersectionRuleImpl#getIntersectionTransition(it.polimi.automata.transition.Transition, it.polimi.automata.transition.Transition, it.polimi.automata.transition.TransitionFactory)}
	 * .
	 */
	@Test
	public void testGetIntersectionTransition3() {

		Set<IGraphProposition> modelPropositions = new HashSet<IGraphProposition>();
		modelPropositions.add(new GraphProposition("b", false));
		modelTransition = transitionFactory.create(modelPropositions);

		Set<IGraphProposition> claimPropositions = new HashSet<IGraphProposition>();
		claimPropositions.add(new GraphProposition("b", true));
		claimTransition = transitionFactory.create(claimPropositions);

		assertNull(new IntersectionRuleImpl<State, Transition>(intersectiontransitionFactory,this.stateFactory)
				.getIntersectionTransition(modelTransition, claimTransition));
	}

	/**
	 * Test method for
	 * {@link it.polimi.checker.intersection.impl.IntersectionRuleImpl#getIntersectionTransition(it.polimi.automata.transition.Transition, it.polimi.automata.transition.Transition, it.polimi.automata.transition.TransitionFactory)}
	 * .
	 */
	@Test
	public void testGetIntersectionTransition4() {

		Set<IGraphProposition> modelPropositions = new HashSet<IGraphProposition>();
		modelPropositions.add(new GraphProposition("a", false));
		modelPropositions.add(new GraphProposition("b", false));
		modelTransition = transitionFactory.create(modelPropositions);

		Set<IGraphProposition> claimPropositions = new HashSet<IGraphProposition>();
		claimPropositions.add(new GraphProposition("b", false));
		claimTransition = transitionFactory.create(claimPropositions);

		Set<IGraphProposition> intersectionPropositions = new HashSet<IGraphProposition>();
		intersectionPropositions.add(new GraphProposition("a", false));
		intersectionPropositions.add(new GraphProposition("b", false));
		Transition intersectionTransition = transitionFactory
				.create(intersectionPropositions);

		assertEquals(
				intersectionTransition.getPropositions(),
				new IntersectionRuleImpl<State, Transition>(intersectiontransitionFactory,this.stateFactory)
						.getIntersectionTransition(modelTransition,
								claimTransition)
						.getPropositions());
	}

	public void testGetIntersectionTransition5() {

		Set<IGraphProposition> modelPropositions = new HashSet<IGraphProposition>();
		modelPropositions.add(new GraphProposition("b", false));
		modelTransition = transitionFactory.create(modelPropositions);

		Set<IGraphProposition> claimPropositions = new HashSet<IGraphProposition>();
		claimPropositions.add(new GraphProposition("a", true));
		claimTransition = transitionFactory.create(claimPropositions);

		Set<IGraphProposition> intersectionPropositions = new HashSet<IGraphProposition>();
		intersectionPropositions.add(new GraphProposition("b", false));
		Transition intersectionTransition = transitionFactory
				.create(intersectionPropositions);

		assertEquals(
				intersectionTransition.getPropositions(),
				new IntersectionRuleImpl<State, Transition>(intersectiontransitionFactory,this.stateFactory)
						.getIntersectionTransition(modelTransition,
								claimTransition)
						.getPropositions());
	}
	
	public void testGetIntersectionTransition6() {

		Set<IGraphProposition> modelPropositions = new HashSet<IGraphProposition>();
		modelPropositions.add(new GraphProposition("b", false));
		modelPropositions.add(new GraphProposition("c", false));
		
		modelTransition = transitionFactory.create(modelPropositions);

		Set<IGraphProposition> claimPropositions = new HashSet<IGraphProposition>();
		claimPropositions.add(new GraphProposition("a", true));
		claimTransition = transitionFactory.create(claimPropositions);

		Set<IGraphProposition> intersectionPropositions = new HashSet<IGraphProposition>();
		intersectionPropositions.add(new GraphProposition("b", false));
		intersectionPropositions.add(new GraphProposition("c", false));
		
		Transition intersectionTransition = transitionFactory
				.create(intersectionPropositions);

		assertEquals(
				intersectionTransition.getPropositions(),
				new IntersectionRuleImpl<State, Transition>(intersectiontransitionFactory,this.stateFactory)
						.getIntersectionTransition(modelTransition,
								claimTransition)
						.getPropositions());
	}
	
	
	public void testGetIntersectionTransition7() {

		Set<IGraphProposition> modelPropositions = new HashSet<IGraphProposition>();
		modelPropositions.add(new GraphProposition("initializaed", false));
		modelTransition = transitionFactory.create(modelPropositions);

		Set<IGraphProposition> claimPropositions = new HashSet<IGraphProposition>();
		claimPropositions.add(new GraphProposition("initializaed", true));
		claimTransition = transitionFactory.create(claimPropositions);

		

		assertNull(new IntersectionRuleImpl<State, Transition>(intersectiontransitionFactory,this.stateFactory)
		.getIntersectionTransition(modelTransition,
				claimTransition));
	}
}
