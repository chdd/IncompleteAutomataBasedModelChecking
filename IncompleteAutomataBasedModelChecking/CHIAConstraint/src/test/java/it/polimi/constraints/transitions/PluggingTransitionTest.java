/**
 * 
 */
package it.polimi.constraints.transitions;

import static org.junit.Assert.*;
import it.polimi.automata.io.in.propositions.StringToClaimPropositions;
import it.polimi.automata.state.State;
import it.polimi.automata.state.StateFactory;
import it.polimi.automata.transition.ClaimTransitionFactory;
import it.polimi.automata.transition.ModelTransitionFactory;
import it.polimi.automata.transition.Transition;

import org.junit.Test;

/**
 * @author Claudio Menghi
 *
 */
public class PluggingTransitionTest {

	/**
	 * Test method for {@link it.polimi.constraints.transitions.PluggingTransition# PluggingTransition}.
	 */
	@Test(expected=NullPointerException.class)
	public void testPluggingTransitionNullTransition() {
		State sourceState=new StateFactory().create();
		State destinationState=new StateFactory().create();
		new PluggingTransition(sourceState, destinationState, null, true);
	}
	
	/**
	 * Test method for {@link it.polimi.constraints.transitions.PluggingTransition# PluggingTransition}.
	 */
	@Test(expected=NullPointerException.class)
	public void testPluggingTransitionNullDestinationState() {
		State sourceState=new StateFactory().create();
		Transition transition=new ModelTransitionFactory().create();
		new PluggingTransition(sourceState, null, transition, true);
	}
	
	/**
	 * Test method for {@link it.polimi.constraints.transitions.PluggingTransition# PluggingTransition}.
	 */
	@Test(expected=NullPointerException.class)
	public void testPluggingTransitionNullSourceState() {
		State destinationState=new StateFactory().create();
		Transition transition=new ModelTransitionFactory().create();
		new PluggingTransition(null, destinationState, transition, true);
	}
	
	/**
	 * Test method for {@link it.polimi.constraints.transitions.PluggingTransition# PluggingTransition}.
	 */
	@Test
	public void testPluggingTransition() {
		State sourceState=new StateFactory().create();
		State destinationState=new StateFactory().create();
		Transition transition=new ModelTransitionFactory().create();
		assertNotNull(new PluggingTransition(sourceState, destinationState, transition, true));
	}
	
	/**
	 * Test method for {@link it.polimi.constraints.transitions.PluggingTransition#toString()}.
	 */
	@Test
	public void testToString() {
		State sourceState=new StateFactory().create();
		State destinationState=new StateFactory().create();
		Transition transition=new ModelTransitionFactory().create();
		assertNotNull(new PluggingTransition(sourceState, destinationState, transition, true).toString());
	}

	/**
	 * Test method for {@link it.polimi.constraints.transitions.PluggingTransition#equals(java.lang.Object)}.
	 */
	@Test
	public void testEqualsObject() {
		State sourceState=new StateFactory().create();
		State destinationState=new StateFactory().create();
		Transition transition=new ModelTransitionFactory().create();
		PluggingTransition pluggingTransition=new PluggingTransition(1, sourceState, destinationState, transition, true);
		PluggingTransition copyOfThePluggingTransition=new PluggingTransition(1, sourceState, destinationState, transition, true);
		assertEquals(pluggingTransition, copyOfThePluggingTransition);
	
	}

	/**
	 * Test method for {@link it.polimi.constraints.transitions.PluggingTransition#getSource()}.
	 */
	@Test
	public void testGetSource() {
		State sourceState=new StateFactory().create();
		State destinationState=new StateFactory().create();
		Transition transition=new ModelTransitionFactory().create();
		PluggingTransition pluggingTransition=new PluggingTransition(sourceState, destinationState, transition, true);
		assertEquals(sourceState, pluggingTransition.getSource());
	}

	/**
	 * Test method for {@link it.polimi.constraints.transitions.PluggingTransition#getDestination()}.
	 */
	@Test
	public void testGetDestination() {
		State sourceState=new StateFactory().create();
		State destinationState=new StateFactory().create();
		Transition transition=new ModelTransitionFactory().create();
		PluggingTransition pluggingTransition=new PluggingTransition(sourceState, destinationState, transition, true);
		assertEquals(destinationState, pluggingTransition.getDestination());
	}

	/**
	 * Test method for {@link it.polimi.constraints.transitions.PluggingTransition#getTransition()}.
	 */
	@Test
	public void testGetTransition() {
		State sourceState=new StateFactory().create();
		State destinationState=new StateFactory().create();
		Transition transition=new ModelTransitionFactory().create();
		PluggingTransition pluggingTransition=new PluggingTransition(sourceState, destinationState, transition, true);
		assertEquals(transition, pluggingTransition.getTransition());
	}

	/**
	 * Test method for {@link it.polimi.constraints.transitions.PluggingTransition#isIncoming()}.
	 */
	@Test
	public void testIsIncomingTrue() {
		State sourceState=new StateFactory().create();
		State destinationState=new StateFactory().create();
		Transition transition=new ModelTransitionFactory().create();
		PluggingTransition pluggingTransition=new PluggingTransition(sourceState, destinationState, transition, true);
		assertEquals(true, pluggingTransition.isIncoming());
	}
	
	/**
	 * Test method for {@link it.polimi.constraints.transitions.PluggingTransition#isIncoming()}.
	 */
	@Test
	public void testIsIncomingFalse() {
		State sourceState=new StateFactory().create();
		State destinationState=new StateFactory().create();
		Transition transition=new ModelTransitionFactory().create();
		PluggingTransition pluggingTransition=new PluggingTransition(sourceState, destinationState, transition, false);
		assertEquals(false, pluggingTransition.isIncoming());
	}
	
	/**
	 * Test method for {@link it.polimi.constraints.transitions.PluggingTransition#hashCode()}.
	 */
	@Test
	public void testHashCode() {
		State sourceState=new StateFactory().create();
		State destinationState=new StateFactory().create();
		Transition transition=new ModelTransitionFactory().create();
		PluggingTransition pluggingTransition=new PluggingTransition(1, sourceState, destinationState, transition, true);
		PluggingTransition copyOfThePluggingTransition=new PluggingTransition(1, sourceState, destinationState, transition, true);
		assertEquals(pluggingTransition.hashCode(), copyOfThePluggingTransition.hashCode());
	}
	
	@Test
	public void testEquals() {

		State source = new StateFactory().create("q8", 8);
		State destination = new StateFactory().create("10 - 2 - 2", 40);
		Transition transition1 = new ClaimTransitionFactory().create(63,
				new StringToClaimPropositions().transform("tro^to^ntz"));
		Transition transition2 = new ClaimTransitionFactory().create(63,
				new StringToClaimPropositions().transform("tro^to^ntz"));

		PluggingTransition incomingTransition1 = new PluggingTransition(1, source,
				destination, transition1, true);
		PluggingTransition incomingTransition2 = new PluggingTransition(1, source,
				destination, transition2, true);
		assertTrue(incomingTransition1.equals(incomingTransition2));
	}

	@Test
	public void testEqualHashCode() {

		State source = new StateFactory().create("q8", 8);
		State destination = new StateFactory().create("10 - 2 - 2", 40);
		Transition transition1 = new ClaimTransitionFactory().create(63,
				new StringToClaimPropositions().transform("tro^to^ntz"));
		Transition transition2 = new ClaimTransitionFactory().create(63,
				new StringToClaimPropositions().transform("tro^to^ntz"));

		PluggingTransition incomingTransition1 = new PluggingTransition(1, source,
				destination, transition1, true);
		PluggingTransition incomingTransition2 = new PluggingTransition(1, source,
				destination, transition2, true);
		assertTrue(incomingTransition1.hashCode() == incomingTransition2
				.hashCode());

		assertTrue(incomingTransition1.hashCode()==incomingTransition2.hashCode());
	}

	@Test
	public void testEqualHashCode2() {

		State source = new StateFactory().create("q8", 8);
		State destination = new StateFactory().create("10 - 2 - 2", 40);
		Transition transition1 = new ClaimTransitionFactory().create(63,
				new StringToClaimPropositions().transform("tro^to^ntz"));

		PluggingTransition incomingTransition1 = new PluggingTransition(source,
				destination, transition1, true);
		assertTrue(incomingTransition1.equals(incomingTransition1));
		assertTrue(incomingTransition1.hashCode()==incomingTransition1.hashCode());
	}

	@Test
	public void testEqualHashCode3() {

		State source = new StateFactory().create("q8", 8);
		State destination = new StateFactory().create("10 - 2 - 2", 40);
		Transition transition1 = new ClaimTransitionFactory().create(63,
				new StringToClaimPropositions().transform("tro^to^ntz"));
		Transition transition2 = new ClaimTransitionFactory().create(74,
				new StringToClaimPropositions().transform("tro^to^ntz"));

		PluggingTransition incomingTransition1 = new PluggingTransition(source,
				destination, transition1, true);
		PluggingTransition incomingTransition2 = new PluggingTransition(source,
				destination, transition2, false);
		assertFalse(incomingTransition1.equals(incomingTransition2));
		assertFalse(incomingTransition1.hashCode()==incomingTransition2.hashCode());
	}

	@Test
	public void testEqualHashCode4() {

		State source = new StateFactory().create("q8", 8);
		State destination = new StateFactory().create("10 - 2 - 2", 40);
		Transition transition1 = new ClaimTransitionFactory().create(63,
				new StringToClaimPropositions().transform("tro^to^ntz"));
	
		PluggingTransition incomingTransition1 = new PluggingTransition(source,
				destination, transition1, true);
		assertFalse(incomingTransition1.equals(null));
	}

	@Test
	public void testEqualHashCode5() {

		State source = new StateFactory().create("q8", 8);
		State destination = new StateFactory().create("10 - 2 - 2", 40);
		Transition transition1 = new ClaimTransitionFactory().create(63,
				new StringToClaimPropositions().transform("tro^to^ntz"));
		Transition transition2 = new ClaimTransitionFactory().create(74,
				new StringToClaimPropositions().transform("tro"));

		PluggingTransition incomingTransition1 = new PluggingTransition(source,
				destination, transition1, true);
		PluggingTransition incomingTransition2 = new PluggingTransition(source,
				destination, transition2, true);
		assertFalse(incomingTransition1.equals(incomingTransition2));
		assertFalse(incomingTransition1.hashCode()==incomingTransition2.hashCode());
	}

	@Test
	public void testEqualHashCode6() {

		State source = new StateFactory().create("q8", 8);
		State destination = new StateFactory().create("10 - 2 - 2", 40);
		Transition transition1 = new ClaimTransitionFactory().create(63,
				new StringToClaimPropositions().transform("tro^to^ntz"));
		Transition transition2 = new ClaimTransitionFactory().create(74,
				new StringToClaimPropositions().transform("tro^to^ntz"));

		PluggingTransition incomingTransition1 = new PluggingTransition(source,
				destination, transition1, true);
		PluggingTransition incomingTransition2 = new PluggingTransition(
				new StateFactory().create(), destination, transition2, true);
		assertFalse(incomingTransition1.equals(incomingTransition2));
		assertFalse(incomingTransition1.hashCode()==incomingTransition2.hashCode());
	}
	
	@Test
	public void testEqualHashCode7() {

		State source = new StateFactory().create("q8", 8);
		State destination = new StateFactory().create("10 - 2 - 2", 40);
		Transition transition1 = new ClaimTransitionFactory().create(63,
				new StringToClaimPropositions().transform("tro^to^ntz"));
		Transition transition2 = new ClaimTransitionFactory().create(74,
				new StringToClaimPropositions().transform("tro^to^ntz"));

		PluggingTransition incomingTransition1 = new PluggingTransition(source,
				destination, transition1, true);
		PluggingTransition incomingTransition2 = new PluggingTransition(source,
				new StateFactory().create(), transition2, true);
		assertFalse(incomingTransition1.equals(incomingTransition2));
		assertFalse(incomingTransition1.hashCode()==incomingTransition2.hashCode());
	}

	@Test
	public void testEqualHashCode8() {

		State source = new StateFactory().create("q8", 8);
		State destination = new StateFactory().create("10 - 2 - 2", 40);
		Transition transition1 = new ClaimTransitionFactory().create(63,
				new StringToClaimPropositions().transform("tro^to^ntz"));
		
		PluggingTransition incomingTransition1 = new PluggingTransition(source,
				destination, transition1, true);
		PluggingTransition incomingTransition2 = new PluggingTransition(source,
				destination, new ModelTransitionFactory().create(), true);
		assertFalse(incomingTransition1.equals(incomingTransition2));
		assertFalse(incomingTransition1.hashCode()==incomingTransition2.hashCode());
	}
	
	@Test
	public void testEqualHashCode9() {

		State source = new StateFactory().create("q8", 8);
		State destination = new StateFactory().create("10 - 2 - 2", 40);
		Transition transition1 = new ClaimTransitionFactory().create(63,
				new StringToClaimPropositions().transform("tro^to^ntz"));
		
		PluggingTransition incomingTransition1 = new PluggingTransition(source,
				destination, transition1, true);
		assertFalse(incomingTransition1.equals(new StateFactory().create()));
	}
}
