/**
 * 
 */
package it.polimi.constraints.reachability;

import static org.junit.Assert.*;
import it.polimi.automata.state.State;
import it.polimi.automata.state.StateFactory;
import it.polimi.automata.transition.ModelTransitionFactory;
import it.polimi.automata.transition.Transition;
import it.polimi.constraints.transitions.Label;
import it.polimi.constraints.transitions.LabeledPluggingTransition;

import org.junit.Test;

import com.google.common.collect.Multimap;

/**
 * @author Claudio1
 *
 */
public class ReachabilityRelationTest {

	/**
	 * Test method for
	 * {@link it.polimi.constraints.reachability.ReachabilityRelation#ReachabilityRelation()}
	 * .
	 */
	@Test
	public void testReachabilityRelation() {
		assertNotNull(new ReachabilityRelation());
	}

	/**
	 * Test method for
	 * {@link it.polimi.constraints.reachability.ReachabilityRelation#addTransition(it.polimi.constraints.transitions.LabeledPluggingTransition, it.polimi.constraints.transitions.LabeledPluggingTransition, java.lang.Boolean, java.lang.Boolean)}
	 * .
	 */
	@Test(expected = NullPointerException.class)
	public void testAddTransitionNullIncoming() {

		State sourceState1 = new StateFactory().create();
		State destinationState1 = new StateFactory().create();
		Transition transition1 = new ModelTransitionFactory().create();
		LabeledPluggingTransition outTransition = new LabeledPluggingTransition(
				sourceState1, destinationState1, transition1, true, Label.R);

		ReachabilityRelation rr = new ReachabilityRelation();
		rr.addTransition(outTransition, null, true, false);

	}

	/**
	 * Test method for
	 * {@link it.polimi.constraints.reachability.ReachabilityRelation#addTransition(it.polimi.constraints.transitions.LabeledPluggingTransition, it.polimi.constraints.transitions.LabeledPluggingTransition, java.lang.Boolean, java.lang.Boolean)}
	 * .
	 */
	@Test(expected = NullPointerException.class)
	public void testAddTransitionNullOutgoing() {

		State sourceState = new StateFactory().create();
		State destinationState = new StateFactory().create();
		Transition transition = new ModelTransitionFactory().create();

		LabeledPluggingTransition inTransition = new LabeledPluggingTransition(
				sourceState, destinationState, transition, true, Label.R);

		ReachabilityRelation rr = new ReachabilityRelation();
		rr.addTransition(null, inTransition, true, false);
	}

	/**
	 * Test method for
	 * {@link it.polimi.constraints.reachability.ReachabilityRelation#addTransition(it.polimi.constraints.transitions.LabeledPluggingTransition, it.polimi.constraints.transitions.LabeledPluggingTransition, java.lang.Boolean, java.lang.Boolean)}
	 * .
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testAddTransitionIllegalOutgoing() {

		State sourceState = new StateFactory().create();
		State destinationState = new StateFactory().create();
		Transition transition = new ModelTransitionFactory().create();

		LabeledPluggingTransition inTransition = new LabeledPluggingTransition(
				sourceState, destinationState, transition, true, Label.R);

		State sourceState1 = new StateFactory().create();
		State destinationState1 = new StateFactory().create();
		Transition transition1 = new ModelTransitionFactory().create();
		LabeledPluggingTransition outTransition = new LabeledPluggingTransition(
				sourceState1, destinationState1, transition1, true, Label.R);

		ReachabilityRelation rr = new ReachabilityRelation();
		rr.addTransition(outTransition, inTransition, true, false);
	}

	/**
	 * Test method for
	 * {@link it.polimi.constraints.reachability.ReachabilityRelation#addTransition(it.polimi.constraints.transitions.LabeledPluggingTransition, it.polimi.constraints.transitions.LabeledPluggingTransition, java.lang.Boolean, java.lang.Boolean)}
	 * .
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testAddTransitionIllegalIncoming() {

		State sourceState = new StateFactory().create();
		State destinationState = new StateFactory().create();
		Transition transition = new ModelTransitionFactory().create();

		LabeledPluggingTransition inTransition = new LabeledPluggingTransition(
				sourceState, destinationState, transition, false, Label.R);

		State sourceState1 = new StateFactory().create();
		State destinationState1 = new StateFactory().create();
		Transition transition1 = new ModelTransitionFactory().create();
		LabeledPluggingTransition outTransition = new LabeledPluggingTransition(
				sourceState1, destinationState1, transition1, false, Label.R);

		ReachabilityRelation rr = new ReachabilityRelation();
		rr.addTransition(outTransition, inTransition, true, false);
	}

	/**
	 * Test method for
	 * {@link it.polimi.constraints.reachability.ReachabilityRelation#addTransition(it.polimi.constraints.transitions.LabeledPluggingTransition, it.polimi.constraints.transitions.LabeledPluggingTransition, java.lang.Boolean, java.lang.Boolean)}
	 * .
	 */
	@Test
	public void testAddTransition() {

		State sourceState = new StateFactory().create();
		State destinationState = new StateFactory().create();
		Transition transition = new ModelTransitionFactory().create();

		LabeledPluggingTransition inTransition = new LabeledPluggingTransition(
				sourceState, destinationState, transition, true, Label.R);

		State sourceState1 = new StateFactory().create();
		State destinationState1 = new StateFactory().create();
		Transition transition1 = new ModelTransitionFactory().create();
		LabeledPluggingTransition outTransition = new LabeledPluggingTransition(
				sourceState1, destinationState1, transition1, false, Label.R);

		ReachabilityRelation rr = new ReachabilityRelation();
		rr.addTransition(outTransition, inTransition, true, false);

		assertTrue(rr.get(sourceState1).size() == 1);
		assertTrue(rr.get(sourceState1).iterator().next()
				.getIncomingTransition().equals(inTransition));
		assertTrue(rr.get(sourceState1).iterator().next()
				.getOutgoingTransition().equals(outTransition));
	}

	/**
	 * Test method for
	 * {@link it.polimi.constraints.reachability.ReachabilityRelation#getReachabilityAcceptingMap()}
	 * .
	 */
	@Test
	public void testGetReachabilityAcceptingMap() {
		State sourceState = new StateFactory().create();
		State destinationState = new StateFactory().create();
		Transition transition = new ModelTransitionFactory().create();

		LabeledPluggingTransition inTransition = new LabeledPluggingTransition(
				sourceState, destinationState, transition, true, Label.R);

		State sourceState1 = new StateFactory().create();
		State destinationState1 = new StateFactory().create();
		Transition transition1 = new ModelTransitionFactory().create();
		LabeledPluggingTransition outTransition = new LabeledPluggingTransition(
				sourceState1, destinationState1, transition1, false, Label.R);

		ReachabilityRelation rr = new ReachabilityRelation();
		rr.addTransition(outTransition, inTransition, true, false);

		Multimap<State, ReachabilityEntry> map=rr.getReachabilityAcceptingMap();
		assertTrue(map.get(sourceState1).size() == 1);
		assertTrue(map.get(sourceState1).iterator().next()
				.getIncomingTransition().equals(inTransition));
		assertTrue(map.get(sourceState1).iterator().next()
				.getOutgoingTransition().equals(outTransition));
	}

	/**
	 * Test method for
	 * {@link it.polimi.constraints.reachability.ReachabilityRelation#get(it.polimi.automata.state.State)}
	 * .
	 */
	@Test
	public void testGet() {
		State sourceState = new StateFactory().create();
		State destinationState = new StateFactory().create();
		Transition transition = new ModelTransitionFactory().create();

		LabeledPluggingTransition inTransition = new LabeledPluggingTransition(
				sourceState, destinationState, transition, true, Label.R);

		State sourceState1 = new StateFactory().create();
		State destinationState1 = new StateFactory().create();
		Transition transition1 = new ModelTransitionFactory().create();
		LabeledPluggingTransition outTransition = new LabeledPluggingTransition(
				sourceState1, destinationState1, transition1, false, Label.R);

		ReachabilityRelation rr = new ReachabilityRelation();
		rr.addTransition(outTransition, inTransition, true, false);

		assertTrue(rr.get(sourceState1).size() == 1);
		assertTrue(rr.get(sourceState1).iterator().next()
				.getIncomingTransition().equals(inTransition));
		assertTrue(rr.get(sourceState1).iterator().next()
				.getOutgoingTransition().equals(outTransition));
	}

	/**
	 * Test method for
	 * {@link it.polimi.constraints.reachability.ReachabilityRelation#toString()}
	 * .
	 */
	@Test
	public void testToString() {
		State sourceState = new StateFactory().create();
		State destinationState = new StateFactory().create();
		Transition transition = new ModelTransitionFactory().create();

		LabeledPluggingTransition inTransition = new LabeledPluggingTransition(
				sourceState, destinationState, transition, true, Label.R);

		State sourceState1 = new StateFactory().create();
		State destinationState1 = new StateFactory().create();
		Transition transition1 = new ModelTransitionFactory().create();
		LabeledPluggingTransition outTransition = new LabeledPluggingTransition(
				sourceState1, destinationState1, transition1, false, Label.R);

		ReachabilityRelation rr = new ReachabilityRelation();
		rr.addTransition(outTransition, inTransition, true, false);
		assertNotNull(rr.toString());
	}

}
