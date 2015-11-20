/**
 * 
 */
package it.polimi.constraints.components;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Set;

import it.polimi.automata.BA;
import it.polimi.automata.state.State;
import it.polimi.automata.state.StateFactory;
import it.polimi.automata.transition.ClaimTransitionFactory;
import it.polimi.automata.transition.Transition;
import it.polimi.constraints.transitions.Label;
import it.polimi.constraints.transitions.LabeledPluggingTransition;

import org.junit.Ignore;
import org.junit.Test;

/**
 * @author Claudio Menghi
 *
 */
public class SubPropertyTest {

	/**
	 * Test method for
	 * {@link it.polimi.constraints.components.SubProperty#SubProperty(it.polimi.automata.state.State, it.polimi.automata.BA)}
	 * .
	 */
	@Test
	public void testSubProperty() {
		assertNotNull(new SubProperty(new StateFactory().create(), new BA(
				new ClaimTransitionFactory())));
	}

	/**
	 * Test method for
	 * {@link it.polimi.constraints.components.SubProperty#SubProperty(it.polimi.automata.state.State, it.polimi.automata.BA)}
	 * .
	 */
	@Test(expected = NullPointerException.class)
	public void testSubPropertyNullBA() {
		assertNotNull(new SubProperty(new StateFactory().create(), null));
	}

	/**
	 * Test method for
	 * {@link it.polimi.constraints.components.SubProperty#hashCode()}.
	 */
	@Test
	public void testHashCode() {
		BA ba = new BA(new ClaimTransitionFactory());
		State state = new StateFactory().create();
		assertFalse(new SubProperty(state, ba).hashCode() == new SubProperty(
				state, ba).hashCode());
		assertFalse(new SubProperty(new StateFactory().create(), ba).hashCode() == new SubProperty(
				new StateFactory().create(), ba).hashCode());

	}

	/**
	 * Test method for
	 * {@link it.polimi.constraints.components.SubProperty#equals(java.lang.Object)}
	 * .
	 */
	@Test
	@Ignore
	public void testEqualsObject() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for
	 * {@link it.polimi.constraints.components.SubProperty#getAutomaton()}.
	 */
	@Test
	public void testGetAutomaton() {

		BA ba = new BA(new ClaimTransitionFactory());

		assertEquals(ba,
				new SubProperty(new StateFactory().create(), ba).getAutomaton());
	}

	/**
	 * Test method for
	 * {@link it.polimi.constraints.components.SubProperty#getOutgoingTransitions()}
	 * .
	 */
	@Test
	public void testGetOutgoingTransitions() {
		BA ba = new BA(new ClaimTransitionFactory());
		State source = new StateFactory().create();
		State destination = new StateFactory().create();
		ba.addState(source);
		Transition transition = new ClaimTransitionFactory().create();

		LabeledPluggingTransition lb = new LabeledPluggingTransition(source,
				destination, transition, true, Label.Y);
		SubProperty sb = new SubProperty(new StateFactory().create(), ba);
		sb.addOutgoingTransition(lb);
		Set<LabeledPluggingTransition> outgoingTransitions = new HashSet<LabeledPluggingTransition>();
		outgoingTransitions.add(lb);

		assertEquals(outgoingTransitions, sb.getOutgoingTransitions());
		assertTrue(sb.getIncomingTransitions().isEmpty());
		
	}

	/**
	 * Test method for
	 * {@link it.polimi.constraints.components.SubProperty#getIncomingTransitions()}
	 * .
	 */
	@Test
	public void testGetIncomingTransitions() {
		BA ba = new BA(new ClaimTransitionFactory());
		State source = new StateFactory().create();
		State destination = new StateFactory().create();
		ba.addState(destination);
		Transition transition = new ClaimTransitionFactory().create();

		LabeledPluggingTransition lb = new LabeledPluggingTransition(source,
				destination, transition, true, Label.Y);
		SubProperty sb = new SubProperty(new StateFactory().create(), ba);
		sb.addIncomingTransition(lb);
		Set<LabeledPluggingTransition> incomingTransitions = new HashSet<LabeledPluggingTransition>();
		incomingTransitions.add(lb);

		assertEquals(incomingTransitions, sb.getIncomingTransitions());
		assertTrue(sb.getOutgoingTransitions().isEmpty());
	}

	/**
	 * Test method for
	 * {@link it.polimi.constraints.components.SubProperty#addIncomingTransition(it.polimi.constraints.transitions.LabeledPluggingTransition)}
	 * .
	 */
	@Test
	public void testAddIncomingTransition() {
		BA ba = new BA(new ClaimTransitionFactory());
		State source = new StateFactory().create();
		State destination = new StateFactory().create();
		ba.addState(destination);
		Transition transition = new ClaimTransitionFactory().create();

		LabeledPluggingTransition lb = new LabeledPluggingTransition(source,
				destination, transition, true, Label.Y);
		SubProperty sb = new SubProperty(new StateFactory().create(), ba);
		sb.addIncomingTransition(lb);
		Set<LabeledPluggingTransition> incomingTransitions = new HashSet<LabeledPluggingTransition>();
		incomingTransitions.add(lb);

		assertEquals(incomingTransitions, sb.getIncomingTransitions());
		assertTrue(sb.getOutgoingTransitions().isEmpty());
	}

	/**
	 * Test method for
	 * {@link it.polimi.constraints.components.SubProperty#getIncomingTransition(it.polimi.constraints.transitions.LabeledPluggingTransition)}
	 * .
	 */
	@Ignore
	public void testGetIncomingTransition() {
		
	}

	/**
	 * Test method for
	 * {@link it.polimi.constraints.components.SubProperty#getOutgoingTransition(it.polimi.constraints.transitions.LabeledPluggingTransition)}
	 * .
	 */
	@Test
	@Ignore
	public void testGetOutgoingTransition() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for
	 * {@link it.polimi.constraints.components.SubProperty#addOutgoingTransition(it.polimi.constraints.transitions.LabeledPluggingTransition)}
	 * .
	 */
	@Test
	@Ignore
	public void testAddOutgoingTransition() {
		BA ba = new BA(new ClaimTransitionFactory());
		State source = new StateFactory().create();
		State destination = new StateFactory().create();
		ba.addState(destination);
		Transition transition = new ClaimTransitionFactory().create();

		LabeledPluggingTransition lb = new LabeledPluggingTransition(source,
				destination, transition, true, Label.Y);
		SubProperty sb = new SubProperty(new StateFactory().create(), ba);
		sb.addOutgoingTransition(lb);
		Set<LabeledPluggingTransition> outgoingTransitions = new HashSet<LabeledPluggingTransition>();
		outgoingTransitions.add(lb);

		assertEquals(outgoingTransitions, sb.getOutgoingTransitions());
		assertTrue(sb.getIncomingTransitions().isEmpty());
	}

	/**
	 * Test method for
	 * {@link it.polimi.constraints.components.SubProperty#removePluggingTransition(it.polimi.constraints.transitions.LabeledPluggingTransition)}
	 * .
	 */
	@Test
	@Ignore
	public void testRemovePluggingTransition() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for
	 * {@link it.polimi.constraints.components.SubProperty#addReachabilityRelation(it.polimi.constraints.transitions.LabeledPluggingTransition, it.polimi.constraints.transitions.LabeledPluggingTransition, java.lang.Boolean, java.lang.Boolean)}
	 * .
	 */
	@Test
	@Ignore
	public void testAddReachabilityRelation() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for
	 * {@link it.polimi.constraints.components.SubProperty#getLowerReachabilityRelation()}
	 * .
	 */
	@Test
	@Ignore
	public void testGetLowerReachabilityRelation() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for
	 * {@link it.polimi.constraints.components.SubProperty#getUpperReachabilityRelation()}
	 * .
	 */
	@Test
	@Ignore
	public void testGetUpperReachabilityRelation() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for
	 * {@link it.polimi.constraints.components.SubProperty#addPossibleReachabilityRelation(it.polimi.constraints.transitions.LabeledPluggingTransition, it.polimi.constraints.transitions.LabeledPluggingTransition, java.lang.Boolean, java.lang.Boolean)}
	 * .
	 */
	@Test
	@Ignore
	public void testAddPossibleReachabilityRelation() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for
	 * {@link it.polimi.constraints.components.SubProperty#isIndispensable()}.
	 */
	@Test
	@Ignore
	public void testIsIndispensable() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for
	 * {@link it.polimi.constraints.components.SubProperty#setIndispensable(boolean)}
	 * .
	 */
	@Test
	@Ignore
	public void testSetIndispensable() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for
	 * {@link it.polimi.constraints.components.SubProperty#getGreenIncomingTransitions()}
	 * .
	 */
	@Test
	@Ignore
	public void testGetGreenIncomingTransitions() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for
	 * {@link it.polimi.constraints.components.SubProperty#setGreenIncomingTransition(it.polimi.constraints.transitions.LabeledPluggingTransition)}
	 * .
	 */
	@Test
	@Ignore
	public void testSetGreenIncomingTransition() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for
	 * {@link it.polimi.constraints.components.SubProperty#setYellowIncomingTransition(it.polimi.constraints.transitions.LabeledPluggingTransition)}
	 * .
	 */
	@Test
	@Ignore
	public void testSetYellowIncomingTransition() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for
	 * {@link it.polimi.constraints.components.SubProperty#incrementNumberRedOutgoingTransitions()}
	 * .
	 */
	@Test
	@Ignore
	public void testIncrementNumberRedOutgoingTransitions() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for
	 * {@link it.polimi.constraints.components.SubProperty#incrementNumberYellowOutgoingTransitions()}
	 * .
	 */
	@Test
	@Ignore
	public void testIncrementNumberYellowOutgoingTransitions() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for
	 * {@link it.polimi.constraints.components.SubProperty#decrementNumberYellowOutgoingTransitions()}
	 * .
	 */
	@Test
	@Ignore
	public void testDecrementNumberYellowOutgoingTransitions() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for
	 * {@link it.polimi.constraints.components.SubProperty#getNumYellowIncomingTransitions()}
	 * .
	 */
	@Test
	@Ignore
	public void testGetNumYellowIncomingTransitions() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for
	 * {@link it.polimi.constraints.components.SubProperty#getNumIncomingTransitions()}
	 * .
	 */
	@Test
	@Ignore
	public void testGetNumIncomingTransitions() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for
	 * {@link it.polimi.constraints.components.SubProperty#getNumRedOutgoingTransitions()}
	 * .
	 */
	@Test
	@Ignore
	public void testGetNumRedOutgoingTransitions() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for
	 * {@link it.polimi.constraints.components.SubProperty#setNumRedOutgoingTransitions(int)}
	 * .
	 */
	@Test
	@Ignore
	public void testSetNumRedOutgoingTransitions() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for
	 * {@link it.polimi.constraints.components.SubProperty#getNumYellowOutgoingTransitions()}
	 * .
	 */
	@Test
	@Ignore
	public void testGetNumYellowOutgoingTransitions() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for
	 * {@link it.polimi.constraints.components.SubProperty#setNumYellowOutgoingTransitions(int)}
	 * .
	 */
	@Test
	@Ignore
	public void testSetNumYellowOutgoingTransitions() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for
	 * {@link it.polimi.constraints.components.SubProperty#getNumOutgoingTransitions()}
	 * .
	 */
	@Test
	@Ignore
	public void testGetNumOutgoingTransitions() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for
	 * {@link it.polimi.constraints.components.SubProperty#setNumOutgoingTransitions(int)}
	 * .
	 */
	@Test
	@Ignore
	public void testSetNumOutgoingTransitions() {
		fail("Not yet implemented");
	}

}
