package it.polimi.constraints.transitions;

import static org.junit.Assert.*;
import it.polimi.automata.io.in.propositions.StringToClaimPropositions;
import it.polimi.automata.state.State;
import it.polimi.automata.state.StateFactory;
import it.polimi.automata.transition.ClaimTransitionFactory;
import it.polimi.automata.transition.Transition;

import org.junit.Test;

public class ColoredPluggingTransitionTest {

	@Test
	public void testEquals() {
		
		State source=new StateFactory().create("q8", 8);
		State destination=new StateFactory().create("10 - 2 - 2", 40);
		Transition transition1=new ClaimTransitionFactory().create(63, new StringToClaimPropositions().transform("tro^to^ntz"));
		Transition transition2=new ClaimTransitionFactory().create(74, new StringToClaimPropositions().transform("tro^to^ntz"));
		
		PluggingTransition incomingTransition1=new PluggingTransition(source, destination, transition1, true);
		PluggingTransition incomingTransition2=new PluggingTransition(source, destination, transition2, true);
		assertTrue(incomingTransition1.equals(incomingTransition2));
	}

	@Test
	public void testEqualHashCode() {
		
		State source=new StateFactory().create("q8", 8);
		State destination=new StateFactory().create("10 - 2 - 2", 40);
		Transition transition1=new ClaimTransitionFactory().create(63, new StringToClaimPropositions().transform("tro^to^ntz"));
		Transition transition2=new ClaimTransitionFactory().create(74, new StringToClaimPropositions().transform("tro^to^ntz"));
		
		PluggingTransition incomingTransition1=new PluggingTransition(source, destination, transition1, true);
		PluggingTransition incomingTransition2=new PluggingTransition(source, destination, transition2, true);
		assertTrue(incomingTransition1.hashCode()==incomingTransition2.hashCode());
	}
}
