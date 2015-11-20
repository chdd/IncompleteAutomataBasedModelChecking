package it.polimi.constraints.transitions;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import it.polimi.automata.io.in.propositions.StringToClaimPropositions;
import it.polimi.automata.state.State;
import it.polimi.automata.state.StateFactory;
import it.polimi.automata.transition.ClaimTransitionFactory;
import it.polimi.automata.transition.Transition;

import org.junit.Test;

public class LabeledPluggingTransitionTest {

	/**
	 * Test method for
	 * {@link it.polimi.constraints.transitions.PluggingTransition# PluggingTransition}
	 * .
	 */
	@Test(expected = NullPointerException.class)
	public void testLabeledPluggingTransition() {
		State sourceState = new StateFactory().create();
		State destinationState = new StateFactory().create();
		Transition transition1 = new ClaimTransitionFactory().create(63,
				new StringToClaimPropositions().transform("tro^to^ntz"));

		new LabeledPluggingTransition(1, sourceState, destinationState,
				transition1, true, null);
	}

	/**
	 * Test method for
	 * {@link it.polimi.constraints.transitions.PluggingTransition# PluggingTransition}
	 * .
	 */
	@Test
	public void testLabeledPluggingTransitionConstructor() {
		State sourceState = new StateFactory().create();
		State destinationState = new StateFactory().create();
		Transition transition1 = new ClaimTransitionFactory().create(63,
				new StringToClaimPropositions().transform("tro^to^ntz"));

		assertNotNull(new LabeledPluggingTransition(sourceState,
				destinationState, transition1, true, Label.Y));
	}

	/**
	 * Test method for {@link
	 * it.polimi.constraints.transitions.LabeledPluggingTransition#}.
	 */
	@Test
	public void testLabeledPluggingGetColor() {
		State sourceState = new StateFactory().create();
		State destinationState = new StateFactory().create();
		Transition transition1 = new ClaimTransitionFactory().create(63,
				new StringToClaimPropositions().transform("tro^to^ntz"));

		LabeledPluggingTransition plugTransition = new LabeledPluggingTransition(
				1, sourceState, destinationState, transition1, true, Label.Y);

		assertEquals(Label.Y, plugTransition.getColor());

		plugTransition.setColor(Label.G);

		assertEquals(Label.G, plugTransition.getColor());

	}

	
	
	
	@Test
	public void testToString() {
		State source = new StateFactory().create("q8", 8);
		State destination = new StateFactory().create("10 - 2 - 2", 40);
		Transition transition1 = new ClaimTransitionFactory().create(63,
				new StringToClaimPropositions().transform("tro^to^ntz"));

		assertNotNull(new LabeledPluggingTransition(1, source, destination, transition1,
				true, Label.G).toString());
	}

}
