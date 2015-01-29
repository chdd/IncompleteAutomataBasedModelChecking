/**
 * 
 */
package it.polimi.checker.intersection.impl;

import static org.junit.Assert.*;
import it.polimi.automata.labeling.Label;
import it.polimi.automata.labeling.impl.LabelFactoryImpl;
import it.polimi.automata.transition.Transition;
import it.polimi.automata.transition.TransitionFactory;
import it.polimi.automata.transition.impl.ClaimTransitionFactoryImpl;
import it.polimi.automata.transition.impl.IntersectionTransitionFactoryImpl;

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
	private Transition<Label> modelTransition;
	private Transition<Label> claimTransition;

	private TransitionFactory<Label, Transition<Label>> transitionFactory;

	@Before
	public void setUp() {
		transitionFactory = new ClaimTransitionFactoryImpl<Label>();

		Set<Label> modelLabels = new HashSet<Label>();
		Set<IGraphProposition> modelPropositions = new HashSet<IGraphProposition>();
		modelPropositions.add(new GraphProposition("a", false));
		Label modelLabel = new LabelFactoryImpl().create(modelPropositions);
		modelLabels.add(modelLabel);
		modelTransition = transitionFactory.create(modelLabels);

		Set<Label> claimlabelT2 = new HashSet<Label>();
		Set<IGraphProposition> claimPropositions = new HashSet<IGraphProposition>();
		claimPropositions.add(new GraphProposition("b", true));
		Label claimLabel = new LabelFactoryImpl().create(claimPropositions);
		claimlabelT2.add(claimLabel);
		claimTransition = transitionFactory.create(claimlabelT2);

	}

	/**
	 * Test method for
	 * {@link it.polimi.checker.intersection.impl.IntersectionRuleImpl#getIntersectionTransition(null, it.polimi.automata.transition.Transition, it.polimi.automata.transition.TransitionFactory)}
	 * .
	 */
	@Test(expected = NullPointerException.class)
	public void testGetIntersectionNullModelTransition() {
		new IntersectionRuleImpl<Label, Transition<Label>>()
				.getIntersectionTransition(null, claimTransition,
						new IntersectionTransitionFactoryImpl<Label>());
	}

	/**
	 * Test method for
	 * {@link it.polimi.checker.intersection.impl.IntersectionRuleImpl#getIntersectionTransition(it.polimi.automata.transition.Transition, null, it.polimi.automata.transition.TransitionFactory)}
	 * .
	 */
	@Test(expected = NullPointerException.class)
	public void testGetIntersectionNullClaimTransition() {
		new IntersectionRuleImpl<Label, Transition<Label>>()
				.getIntersectionTransition(modelTransition, null,
						new IntersectionTransitionFactoryImpl<Label>());
	}

	/**
	 * Test method for
	 * {@link it.polimi.checker.intersection.impl.IntersectionRuleImpl#getIntersectionTransition(it.polimi.automata.transition.Transition, it.polimi.automata.transition.Transition, null)}
	 * .
	 */
	@Test(expected = NullPointerException.class)
	public void testGetIntersectionNullTransitionFactory() {
		new IntersectionRuleImpl<Label, Transition<Label>>()
				.getIntersectionTransition(modelTransition, claimTransition,
						null);
	}

	/**
	 * Test method for
	 * {@link it.polimi.checker.intersection.impl.IntersectionRuleImpl#getIntersectionTransition(it.polimi.automata.transition.Transition, it.polimi.automata.transition.Transition, it.polimi.automata.transition.TransitionFactory)}
	 * .
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testGetIntersectionTransitionIllegalModelTransition() {
		new IntersectionRuleImpl<Label, Transition<Label>>()
				.getIntersectionTransition(claimTransition, claimTransition,
						new IntersectionTransitionFactoryImpl<Label>());
	}

	/**
	 * Test method for
	 * {@link it.polimi.checker.intersection.impl.IntersectionRuleImpl#getIntersectionTransition(it.polimi.automata.transition.Transition, it.polimi.automata.transition.Transition, it.polimi.automata.transition.TransitionFactory)}
	 * .
	 */
	@Test
	public void testGetIntersectionTransition() {
		assertNull(new IntersectionRuleImpl<Label, Transition<Label>>()
				.getIntersectionTransition(modelTransition, claimTransition,
						new IntersectionTransitionFactoryImpl<Label>()));
	}

	/**
	 * Test method for
	 * {@link it.polimi.checker.intersection.impl.IntersectionRuleImpl#getIntersectionTransition(it.polimi.automata.transition.Transition, it.polimi.automata.transition.Transition, it.polimi.automata.transition.TransitionFactory)}
	 * .
	 */
	@Test
	public void testGetIntersectionTransition2() {

		Set<Label> modelLabels = new HashSet<Label>();
		Set<IGraphProposition> modelPropositions = new HashSet<IGraphProposition>();
		modelPropositions.add(new GraphProposition("b", false));
		Label modelLabel = new LabelFactoryImpl().create(modelPropositions);
		modelLabels.add(modelLabel);
		modelTransition = transitionFactory.create(modelLabels);

		Set<Label> claimlabelT2 = new HashSet<Label>();
		Set<IGraphProposition> claimPropositions = new HashSet<IGraphProposition>();
		claimPropositions.add(new GraphProposition("b", false));
		Label claimLabel = new LabelFactoryImpl().create(claimPropositions);
		claimlabelT2.add(claimLabel);
		claimTransition = transitionFactory.create(claimlabelT2);

		Set<Label> intersectionLabels = new HashSet<Label>();
		Set<IGraphProposition> intersectionPropositions = new HashSet<IGraphProposition>();
		intersectionPropositions.add(new GraphProposition("b", false));
		Label intersectionLabel = new LabelFactoryImpl()
				.create(modelPropositions);
		intersectionLabels.add(intersectionLabel);
		Transition<Label> intersectionTransition = transitionFactory
				.create(intersectionLabels);

		assertEquals(
				intersectionTransition.getLabels(),
				new IntersectionRuleImpl<Label, Transition<Label>>()
						.getIntersectionTransition(modelTransition,
								claimTransition,
								new IntersectionTransitionFactoryImpl<Label>())
						.getLabels());
	}

	/**
	 * Test method for
	 * {@link it.polimi.checker.intersection.impl.IntersectionRuleImpl#getIntersectionTransition(it.polimi.automata.transition.Transition, it.polimi.automata.transition.Transition, it.polimi.automata.transition.TransitionFactory)}
	 * .
	 */
	@Test
	public void testGetIntersectionTransition3() {

		Set<Label> modelLabels = new HashSet<Label>();
		Set<IGraphProposition> modelPropositions = new HashSet<IGraphProposition>();
		modelPropositions.add(new GraphProposition("b", false));
		Label modelLabel = new LabelFactoryImpl().create(modelPropositions);
		modelLabels.add(modelLabel);
		modelTransition = transitionFactory.create(modelLabels);

		Set<Label> claimlabelT2 = new HashSet<Label>();
		Set<IGraphProposition> claimPropositions = new HashSet<IGraphProposition>();
		claimPropositions.add(new GraphProposition("b", true));
		Label claimLabel = new LabelFactoryImpl().create(claimPropositions);
		claimlabelT2.add(claimLabel);
		claimTransition = transitionFactory.create(claimlabelT2);

		assertNull(new IntersectionRuleImpl<Label, Transition<Label>>()
				.getIntersectionTransition(modelTransition, claimTransition,
						new IntersectionTransitionFactoryImpl<Label>()));
	}

	/**
	 * Test method for
	 * {@link it.polimi.checker.intersection.impl.IntersectionRuleImpl#getIntersectionTransition(it.polimi.automata.transition.Transition, it.polimi.automata.transition.Transition, it.polimi.automata.transition.TransitionFactory)}
	 * .
	 */
	@Test
	public void testGetIntersectionTransition4() {

		Set<Label> modelLabels = new HashSet<Label>();
		Set<IGraphProposition> modelPropositions = new HashSet<IGraphProposition>();
		modelPropositions.add(new GraphProposition("a", false));
		modelPropositions.add(new GraphProposition("b", false));
		Label modelLabel = new LabelFactoryImpl().create(modelPropositions);
		modelLabels.add(modelLabel);
		modelTransition = transitionFactory.create(modelLabels);

		Set<Label> claimlabelT2 = new HashSet<Label>();
		Set<IGraphProposition> claimPropositions = new HashSet<IGraphProposition>();
		claimPropositions.add(new GraphProposition("b", false));
		Label claimLabel = new LabelFactoryImpl().create(claimPropositions);
		claimlabelT2.add(claimLabel);
		claimTransition = transitionFactory.create(claimlabelT2);

		Set<Label> intersectionLabels = new HashSet<Label>();
		Set<IGraphProposition> intersectionPropositions = new HashSet<IGraphProposition>();
		intersectionPropositions.add(new GraphProposition("a", false));
		intersectionPropositions.add(new GraphProposition("b", false));
		Label intersectionLabel = new LabelFactoryImpl()
				.create(modelPropositions);
		intersectionLabels.add(intersectionLabel);
		Transition<Label> intersectionTransition = transitionFactory
				.create(intersectionLabels);

		assertEquals(
				intersectionTransition.getLabels(),
				new IntersectionRuleImpl<Label, Transition<Label>>()
						.getIntersectionTransition(modelTransition,
								claimTransition,
								new IntersectionTransitionFactoryImpl<Label>())
						.getLabels());
	}

	public void testGetIntersectionTransition5() {

		Set<Label> modelLabels = new HashSet<Label>();
		Set<IGraphProposition> modelPropositions = new HashSet<IGraphProposition>();
		modelPropositions.add(new GraphProposition("b", false));
		Label modelLabel = new LabelFactoryImpl().create(modelPropositions);
		modelLabels.add(modelLabel);
		modelTransition = transitionFactory.create(modelLabels);

		Set<Label> claimlabelT2 = new HashSet<Label>();
		Set<IGraphProposition> claimPropositions = new HashSet<IGraphProposition>();
		claimPropositions.add(new GraphProposition("a", true));
		Label claimLabel = new LabelFactoryImpl().create(claimPropositions);
		claimlabelT2.add(claimLabel);
		claimTransition = transitionFactory.create(claimlabelT2);

		Set<Label> intersectionLabels = new HashSet<Label>();
		Set<IGraphProposition> intersectionPropositions = new HashSet<IGraphProposition>();
		intersectionPropositions.add(new GraphProposition("b", false));
		Label intersectionLabel = new LabelFactoryImpl()
				.create(modelPropositions);
		intersectionLabels.add(intersectionLabel);
		Transition<Label> intersectionTransition = transitionFactory
				.create(intersectionLabels);

		assertEquals(
				intersectionTransition.getLabels(),
				new IntersectionRuleImpl<Label, Transition<Label>>()
						.getIntersectionTransition(modelTransition,
								claimTransition,
								new IntersectionTransitionFactoryImpl<Label>())
						.getLabels());
	}
	
	public void testGetIntersectionTransition6() {

		Set<Label> modelLabels = new HashSet<Label>();
		Set<IGraphProposition> modelPropositions = new HashSet<IGraphProposition>();
		modelPropositions.add(new GraphProposition("b", false));
		modelPropositions.add(new GraphProposition("c", false));
		
		Label modelLabel = new LabelFactoryImpl().create(modelPropositions);
		modelLabels.add(modelLabel);
		modelTransition = transitionFactory.create(modelLabels);

		Set<Label> claimlabelT2 = new HashSet<Label>();
		Set<IGraphProposition> claimPropositions = new HashSet<IGraphProposition>();
		claimPropositions.add(new GraphProposition("a", true));
		Label claimLabel = new LabelFactoryImpl().create(claimPropositions);
		claimlabelT2.add(claimLabel);
		claimTransition = transitionFactory.create(claimlabelT2);

		Set<Label> intersectionLabels = new HashSet<Label>();
		Set<IGraphProposition> intersectionPropositions = new HashSet<IGraphProposition>();
		intersectionPropositions.add(new GraphProposition("b", false));
		intersectionPropositions.add(new GraphProposition("c", false));
		
		Label intersectionLabel = new LabelFactoryImpl()
				.create(modelPropositions);
		intersectionLabels.add(intersectionLabel);
		Transition<Label> intersectionTransition = transitionFactory
				.create(intersectionLabels);

		assertEquals(
				intersectionTransition.getLabels(),
				new IntersectionRuleImpl<Label, Transition<Label>>()
						.getIntersectionTransition(modelTransition,
								claimTransition,
								new IntersectionTransitionFactoryImpl<Label>())
						.getLabels());
	}
}
