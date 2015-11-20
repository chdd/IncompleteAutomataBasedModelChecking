package it.polimi.constraints.reachability;

import static org.junit.Assert.*;
import it.polimi.automata.state.State;
import it.polimi.automata.state.StateFactory;
import it.polimi.automata.transition.ModelTransitionFactory;
import it.polimi.automata.transition.Transition;
import it.polimi.constraints.transitions.Label;
import it.polimi.constraints.transitions.LabeledPluggingTransition;

import org.junit.Test;

public class ReachabilityEntryTest {

	@Test
	public void testReachabilityEntry() {
		
		State sourceState=new StateFactory().create();
		State destinationState=new StateFactory().create();
		Transition transition=new ModelTransitionFactory().create();
		
		LabeledPluggingTransition inTransition=new LabeledPluggingTransition(sourceState, destinationState, transition, true, Label.R);
		
		State sourceState1=new StateFactory().create();
		State destinationState1=new StateFactory().create();
		Transition transition1=new ModelTransitionFactory().create();
		
		LabeledPluggingTransition outTransition=new LabeledPluggingTransition(sourceState1, destinationState1, transition1, false, Label.R);
		
		assertNotNull(new ReachabilityEntry(inTransition, outTransition, true, false));
	}
	
	@Test(expected=NullPointerException.class)
	public void testReachabilityEntryNullOutgoing() {
		
		State sourceState=new StateFactory().create();
		State destinationState=new StateFactory().create();
		Transition transition=new ModelTransitionFactory().create();
		
		LabeledPluggingTransition inTransition=new LabeledPluggingTransition(sourceState, destinationState, transition, false, Label.R);
		
		new ReachabilityEntry(inTransition, null, true, false);
	}

	@Test(expected=NullPointerException.class)
	public void testReachabilityEntryNullIncoming() {
		
		State sourceState1=new StateFactory().create();
		State destinationState1=new StateFactory().create();
		Transition transition1=new ModelTransitionFactory().create();
		
		LabeledPluggingTransition outTransition=new LabeledPluggingTransition(sourceState1, destinationState1, transition1, false, Label.R);
		
		new ReachabilityEntry(null, outTransition, true, false);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testGetIncomingTransitionIllegalIncoming() {
		State sourceState=new StateFactory().create();
		State destinationState=new StateFactory().create();
		Transition transition=new ModelTransitionFactory().create();
		
		LabeledPluggingTransition inTransition=new LabeledPluggingTransition(sourceState, destinationState, transition, false, Label.R);
		
		State sourceState1=new StateFactory().create();
		State destinationState1=new StateFactory().create();
		Transition transition1=new ModelTransitionFactory().create();
		
		LabeledPluggingTransition outTransition=new LabeledPluggingTransition(sourceState1, destinationState1, transition1, false, Label.R);
		
		ReachabilityEntry re=new ReachabilityEntry(inTransition, outTransition, true, false);
		assertTrue(inTransition==re.getIncomingTransition());
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testGetIncomingTransitionIllegalOutgoing() {
		State sourceState=new StateFactory().create();
		State destinationState=new StateFactory().create();
		Transition transition=new ModelTransitionFactory().create();
		
		LabeledPluggingTransition inTransition=new LabeledPluggingTransition(sourceState, destinationState, transition, true, Label.R);
		
		State sourceState1=new StateFactory().create();
		State destinationState1=new StateFactory().create();
		Transition transition1=new ModelTransitionFactory().create();
		
		LabeledPluggingTransition outTransition=new LabeledPluggingTransition(sourceState1, destinationState1, transition1, true, Label.R);
		
		ReachabilityEntry re=new ReachabilityEntry(inTransition, outTransition, true, false);
		assertTrue(inTransition==re.getIncomingTransition());
	}
	
	@Test
	public void testGetIncomingTransition() {
		State sourceState=new StateFactory().create();
		State destinationState=new StateFactory().create();
		Transition transition=new ModelTransitionFactory().create();
		
		LabeledPluggingTransition inTransition=new LabeledPluggingTransition(sourceState, destinationState, transition, true, Label.R);
		
		State sourceState1=new StateFactory().create();
		State destinationState1=new StateFactory().create();
		Transition transition1=new ModelTransitionFactory().create();
		
		LabeledPluggingTransition outTransition=new LabeledPluggingTransition(sourceState1, destinationState1, transition1, false, Label.R);
		
		ReachabilityEntry re=new ReachabilityEntry(inTransition, outTransition, true, false);
		assertTrue(inTransition==re.getIncomingTransition());
	}

	@Test
	public void testGetOutgoingTransition() {
		State sourceState=new StateFactory().create();
		State destinationState=new StateFactory().create();
		Transition transition=new ModelTransitionFactory().create();
		
		LabeledPluggingTransition inTransition=new LabeledPluggingTransition(sourceState, destinationState, transition, true, Label.R);
		
		State sourceState1=new StateFactory().create();
		State destinationState1=new StateFactory().create();
		Transition transition1=new ModelTransitionFactory().create();
		
		LabeledPluggingTransition outTransition=new LabeledPluggingTransition(sourceState1, destinationState1, transition1, false, Label.R);
		
		ReachabilityEntry re=new ReachabilityEntry(inTransition, outTransition, true, false);
		assertTrue(outTransition==re.getOutgoingTransition());

	}

	@Test
	public void testIsModelAccepting() {
		State sourceState=new StateFactory().create();
		State destinationState=new StateFactory().create();
		Transition transition=new ModelTransitionFactory().create();
		
		LabeledPluggingTransition inTransition=new LabeledPluggingTransition(sourceState, destinationState, transition, true, Label.R);
		
		State sourceState1=new StateFactory().create();
		State destinationState1=new StateFactory().create();
		Transition transition1=new ModelTransitionFactory().create();
		
		LabeledPluggingTransition outTransition=new LabeledPluggingTransition(sourceState1, destinationState1, transition1, false, Label.R);
		
		ReachabilityEntry re=new ReachabilityEntry(inTransition, outTransition, true, false);
		assertTrue(re.isModelAccepting());
	}

	@Test
	public void testIsClaimAccepting() {
		State sourceState=new StateFactory().create();
		State destinationState=new StateFactory().create();
		Transition transition=new ModelTransitionFactory().create();
		
		LabeledPluggingTransition inTransition=new LabeledPluggingTransition(sourceState, destinationState, transition, true, Label.R);
		
		State sourceState1=new StateFactory().create();
		State destinationState1=new StateFactory().create();
		Transition transition1=new ModelTransitionFactory().create();
		
		LabeledPluggingTransition outTransition=new LabeledPluggingTransition(sourceState1, destinationState1, transition1, false, Label.R);
		
		ReachabilityEntry re=new ReachabilityEntry(inTransition, outTransition, true, false);
		assertFalse(re.isClaimAccepting());
	}

	@Test
	public void testToString() {
		State sourceState=new StateFactory().create();
		State destinationState=new StateFactory().create();
		Transition transition=new ModelTransitionFactory().create();
		
		LabeledPluggingTransition inTransition=new LabeledPluggingTransition(sourceState, destinationState, transition, true, Label.R);
		
		State sourceState1=new StateFactory().create();
		State destinationState1=new StateFactory().create();
		Transition transition1=new ModelTransitionFactory().create();
		
		LabeledPluggingTransition outTransition=new LabeledPluggingTransition(sourceState1, destinationState1, transition1, false, Label.R);
		
		ReachabilityEntry re=new ReachabilityEntry(inTransition, outTransition, true, false);
		assertNotNull(re.toString());
	}

}
