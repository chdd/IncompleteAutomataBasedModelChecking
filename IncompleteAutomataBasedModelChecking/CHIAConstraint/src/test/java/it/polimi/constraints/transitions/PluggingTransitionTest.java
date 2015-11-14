/**
 * 
 */
package it.polimi.constraints.transitions;

import static org.junit.Assert.*;
import it.polimi.automata.state.State;
import it.polimi.automata.state.StateFactory;
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
		assertNotNull(new PluggingTransition(sourceState, destinationState, transition, true));
	}

	/**
	 * Test method for {@link it.polimi.constraints.transitions.PluggingTransition#equals(java.lang.Object)}.
	 */
	@Test
	public void testEqualsObject() {
		State sourceState=new StateFactory().create();
		State destinationState=new StateFactory().create();
		Transition transition=new ModelTransitionFactory().create();
		PluggingTransition pluggingTransition=new PluggingTransition(sourceState, destinationState, transition, true);
		PluggingTransition copyOfThePluggingTransition=new PluggingTransition(sourceState, destinationState, transition, true);
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
		PluggingTransition pluggingTransition=new PluggingTransition(sourceState, destinationState, transition, true);
		PluggingTransition copyOfThePluggingTransition=new PluggingTransition(sourceState, destinationState, transition, true);
		assertEquals(pluggingTransition.hashCode(), copyOfThePluggingTransition.hashCode());
	}
}
