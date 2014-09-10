package it.polimi.model.buchiautomaton;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Set;

import it.polimi.model.BuchiAutomaton;
import it.polimi.model.State;
import it.polimi.model.Transition;

import org.junit.Test;

public class BAresetTest {

	@Test
	public void test() {
		State s1=new State("s1");
		State s2=new State("s2");
		Set<String> alphabet=new HashSet<String>();
		alphabet.add("a");
		alphabet.add("b");
		
		BuchiAutomaton<State, Transition<State>> a=new BuchiAutomaton<State, Transition<State>>(alphabet);
		a.addInitialState(s1);
		a.addAcceptState(s2);
		a.addTransition(s1, new Transition<State>("a", s2));
		a.reset();
		assertTrue(a.getAlphabet().isEmpty());
		assertTrue(a.getStates().isEmpty());
		assertTrue(a.getInitialStates().isEmpty());
		assertTrue(a.getAcceptStates().isEmpty());
		assertTrue(a.getTransitionsWithSource(s1).isEmpty());
	}
}
