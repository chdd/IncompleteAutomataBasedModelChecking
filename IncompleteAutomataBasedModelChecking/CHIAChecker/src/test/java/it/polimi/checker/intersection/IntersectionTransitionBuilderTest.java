/**
 * 
 */
package it.polimi.checker.intersection;

import static org.junit.Assert.*;
import it.polimi.automata.state.State;
import it.polimi.automata.transition.ClaimTransitionFactory;
import it.polimi.automata.transition.ModelTransitionFactory;
import it.polimi.automata.transition.PropositionalLogicConstants;
import it.polimi.automata.transition.Transition;
import it.polimi.automata.transition.TransitionFactory;

import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import rwth.i2.ltl2ba4j.model.IGraphProposition;
import rwth.i2.ltl2ba4j.model.impl.GraphProposition;
import rwth.i2.ltl2ba4j.model.impl.SigmaProposition;

/**
 * @author Claudio1
 *
 */
public class IntersectionTransitionBuilderTest {

	/**
	 * Test method for
	 * {@link it.polimi.checker.intersection.IntersectionTransitionBuilder#IntersectionTransitionBuilder()}
	 * .
	 */
	@Test
	public void testIntersectionTransitionBuilder() {
		assertNotNull(
				"It is possible to create the intersection transition builder",
				new IntersectionTransitionBuilder());
	}

	/**
	 * Test method for
	 * {@link it.polimi.checker.intersection.IntersectionTransitionBuilder#getIntersectionTransition(it.polimi.automata.state.State, it.polimi.automata.state.State, it.polimi.automata.transition.Transition, it.polimi.automata.transition.Transition)}
	 * .
	 */
	// @Test
	// public void testGetIntersectionTransition() {
	// fail("Not yet implemented");
	// }

	/**
	 * Test method for
	 * {@link it.polimi.checker.intersection.IntersectionTransitionBuilder#satisfies(java.util.Set, java.util.Set)}
	 * .
	 */
	@Test(expected = NullPointerException.class)
	public void testSatisfiesNullClaimFormula() {
		new IntersectionTransitionBuilder().satisfies(
				new HashSet<IGraphProposition>(), null);
	}

	/**
	 * Test method for
	 * {@link it.polimi.checker.intersection.IntersectionTransitionBuilder#satisfies(java.util.Set, java.util.Set)}
	 * .
	 */
	@Test(expected = NullPointerException.class)
	public void testSatisfiesNullModelFormula() {
		new IntersectionTransitionBuilder().satisfies(null,
				new HashSet<IGraphProposition>());
	}

	/**
	 * Test method for
	 * {@link it.polimi.checker.intersection.IntersectionTransitionBuilder#satisfies(java.util.Set, java.util.Set)}
	 * .
	 */
	@Test
	public void testSatisfiesStuttering() {

		Set<IGraphProposition> claimPropositions = new HashSet<IGraphProposition>();
		Set<IGraphProposition> modelPropositions = new HashSet<IGraphProposition>();
		claimPropositions.add(new GraphProposition(
				PropositionalLogicConstants.STUTTERING_CHARACTER, false));
		modelPropositions.add(new GraphProposition(
				PropositionalLogicConstants.STUTTERING_CHARACTER, false));
		assertTrue(
				"If the stuttering character is contained both on the claim and on the model the condition is satisfied",
				new IntersectionTransitionBuilder().satisfies(
						modelPropositions, claimPropositions));
	}

	/**
	 * Test method for
	 * {@link it.polimi.checker.intersection.IntersectionTransitionBuilder#satisfies(java.util.Set, java.util.Set)}
	 * .
	 */
	@Test
	public void testSatisfiesSigma() {

		Set<IGraphProposition> claimPropositions = new HashSet<IGraphProposition>();
		Set<IGraphProposition> modelPropositions = new HashSet<IGraphProposition>();
		claimPropositions.add(new GraphProposition("a", false));
		claimPropositions.add(new SigmaProposition());
		modelPropositions.add(new GraphProposition("a", false));
		modelPropositions.add(new GraphProposition("b", true));
		assertTrue(
				"If the claim label contains the Sigma proposition the condition is satisfied",
				new IntersectionTransitionBuilder().satisfies(
						modelPropositions, claimPropositions));
	}

	/**
	 * Test method for
	 * {@link it.polimi.checker.intersection.IntersectionTransitionBuilder#satisfies(java.util.Set, java.util.Set)}
	 * .
	 */
	@Test
	public void testClaimNegatedProposition() {

		IGraphProposition aProposition = new GraphProposition("a", false);
		IGraphProposition bProposition = new GraphProposition("b", false);
		Set<IGraphProposition> claimPropositions = new HashSet<IGraphProposition>();
		Set<IGraphProposition> modelPropositions = new HashSet<IGraphProposition>();
		claimPropositions.add(new GraphProposition("b", true));
		claimPropositions.add(aProposition);
		modelPropositions.add(aProposition);
		modelPropositions.add(bProposition);
		assertFalse(
				"If the propositions of the model contains a proposition which is negate in the claim the return value is false",
				new IntersectionTransitionBuilder().satisfies(
						modelPropositions, claimPropositions));
	}

	/**
	 * Test method for
	 * {@link it.polimi.checker.intersection.IntersectionTransitionBuilder#satisfies(java.util.Set, java.util.Set)}
	 * .
	 */
	@Test
	public void testClaimProposition() {

		IGraphProposition aProposition = new GraphProposition("a", false);
		IGraphProposition bProposition = new GraphProposition("b", false);
		Set<IGraphProposition> claimPropositions = new HashSet<IGraphProposition>();
		Set<IGraphProposition> modelPropositions = new HashSet<IGraphProposition>();
		claimPropositions.add(bProposition);
		claimPropositions.add(aProposition);
		modelPropositions.add(aProposition);
		assertFalse(
				"If the propositions of the model does not contain a proposition which is present in the claim the return value is false",
				new IntersectionTransitionBuilder().satisfies(
						modelPropositions, claimPropositions));
	}

	/**
	 * Test method for
	 * {@link it.polimi.checker.intersection.IntersectionTransitionBuilder#satisfies(java.util.Set, java.util.Set)}
	 * .
	 */
	@Test
	public void testClaimPropositionSatisfied() {

		IGraphProposition aProposition = new GraphProposition("a", false);
		IGraphProposition bProposition = new GraphProposition("b", false);
		IGraphProposition cPropositionNegated = new GraphProposition("c", true);
		Set<IGraphProposition> claimPropositions = new HashSet<IGraphProposition>();
		Set<IGraphProposition> modelPropositions = new HashSet<IGraphProposition>();
		claimPropositions.add(bProposition);
		claimPropositions.add(aProposition);
		claimPropositions.add(cPropositionNegated);
		modelPropositions.add(aProposition);
		modelPropositions.add(bProposition);
		assertTrue(
				"If the propositions of the model satisfy the claim the return value is true",
				new IntersectionTransitionBuilder().satisfies(
						modelPropositions, claimPropositions));
	}

	/**
	 * Test method for
	 * {@link it.polimi.checker.intersection.IntersectionTransitionBuilder#isCompatible(Transition, Transition)}
	 * .
	 */
	@Test(expected = NullPointerException.class)
	public void testIsCompatibleNullModelTransition() {
		new IntersectionTransitionBuilder().isCompatible(null,
				new ClaimTransitionFactory().create());
	}

	/**
	 * Test method for
	 * {@link it.polimi.checker.intersection.IntersectionTransitionBuilder#isCompatible(Transition, Transition)}
	 * .
	 */
	@Test(expected = NullPointerException.class)
	public void testIsCompatibleNullClaimTransition() {
		new IntersectionTransitionBuilder().isCompatible(
				new ModelTransitionFactory().create(), null);
	}

	/**
	 * Test method for
	 * {@link it.polimi.checker.intersection.IntersectionTransitionBuilder#isCompatible(Transition, Transition)}
	 * .
	 */
	@Test
	public void isCompatible() {
		assertTrue("Two transitions with no labels are compatible",
				new IntersectionTransitionBuilder().isCompatible(
						new ModelTransitionFactory().create(),
						new ClaimTransitionFactory().create()));

	}


	private Transition modelTransition;
	private Transition claimTransition;

	private TransitionFactory<State, Transition> transitionFactory;

	TransitionFactory<State, Transition> intersectiontransitionFactory = new ClaimTransitionFactory();

	@Before
	public void setUp() {
		transitionFactory = new ClaimTransitionFactory();
		Set<IGraphProposition> modelPropositions = new HashSet<IGraphProposition>();
		modelPropositions.add(new GraphProposition("a", false));
		modelTransition = transitionFactory.create(modelPropositions);

		Set<IGraphProposition> claimPropositions = new HashSet<IGraphProposition>();
		claimPropositions.add(new GraphProposition("b", true));
		claimTransition = transitionFactory.create(claimPropositions);

	}


	/**
	 * Test method for
	 * {@link it.polimi.checker.intersection.IntersectionTransitionBuilder#getIntersectionTransition(Transition, Transition)}
	 * .
	 */
	@Test(expected = NullPointerException.class)
	public void testGetIntersectionNullClaimTransition() {
		new IntersectionTransitionBuilder().getIntersectionTransition(
				modelTransition, null);
	}

	/**
	 * Test method for
	 * {@link it.polimi.checker.intersection.IntersectionTransitionBuilder#getIntersectionTransition(Transition, Transition)}
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
				new IntersectionTransitionBuilder().getIntersectionTransition(
						 modelTransition,
						claimTransition).getPropositions());
	}

	/**
	 * Test method for
	 * {@link it.polimi.checker.intersection.IntersectionTransitionBuilder#getIntersectionTransition(Transition, Transition)}
	 * .
	 */
	@Test(expected=InternalError.class)
	public void testGetIntersectionTransition3() {

		Set<IGraphProposition> modelPropositions = new HashSet<IGraphProposition>();
		modelPropositions.add(new GraphProposition("b", false));
		modelTransition = transitionFactory.create(modelPropositions);

		Set<IGraphProposition> claimPropositions = new HashSet<IGraphProposition>();
		claimPropositions.add(new GraphProposition("b", true));
		claimTransition = transitionFactory.create(claimPropositions);

		assertNull(new IntersectionTransitionBuilder()
				.getIntersectionTransition( modelTransition,
						claimTransition));
	}

	/**
	 * Test method for
	 * {@link it.polimi.checker.intersection.IntersectionTransitionBuilder#getIntersectionTransition(Transition, Transition)}
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
				new IntersectionTransitionBuilder().getIntersectionTransition(
						 modelTransition,
						claimTransition).getPropositions());
	}

	/**
	 * Test method for
	 * {@link it.polimi.checker.intersection.IntersectionTransitionBuilder#getIntersectionTransition(Transition, Transition)}
	 * .
	 */
	@Test
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
				new IntersectionTransitionBuilder().getIntersectionTransition(
						 modelTransition,
						claimTransition).getPropositions());
	}

	/**
	 * Test method for
	 * {@link it.polimi.checker.intersection.IntersectionTransitionBuilder#getIntersectionTransition(Transition, Transition)}
	 * .
	 */
	@Test
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
				new IntersectionTransitionBuilder().getIntersectionTransition(
						 modelTransition,
						claimTransition).getPropositions());
	}

	/**
	 * Test method for
	 * {@link it.polimi.checker.intersection.IntersectionTransitionBuilder#getIntersectionTransition(Transition, Transition)}
	 * .
	 */
	@Test(expected=InternalError.class)
	public void testGetIntersectionTransition7() {

		Set<IGraphProposition> modelPropositions = new HashSet<IGraphProposition>();
		modelPropositions.add(new GraphProposition("initializaed", false));
		modelTransition = transitionFactory.create(modelPropositions);

		Set<IGraphProposition> claimPropositions = new HashSet<IGraphProposition>();
		claimPropositions.add(new GraphProposition("initializaed", true));
		claimTransition = transitionFactory.create(claimPropositions);

		assertNull(new IntersectionTransitionBuilder()
				.getIntersectionTransition(modelTransition,
						claimTransition));
	}
}
