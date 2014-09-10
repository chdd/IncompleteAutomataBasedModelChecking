package it.polimi.model.incompletebuchiautomaton;

import static org.junit.Assert.*;
import it.polimi.model.BuchiAutomaton;
import it.polimi.model.IncompleteBuchiAutomaton;
import it.polimi.model.State;
import it.polimi.model.Transition;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

/**
 * tests the correct behavior of the reset method of the incomplete buchi automaton
 * @author claudiomenghi
 */
public class IBAresetTest {

	@Test
	public void test() {
		State s1=new State("s1");
		State s2=new State("s2");
		State s3=new State("s3");
		Set<String> alphabet=new HashSet<String>();
		alphabet.add("a");
		alphabet.add("b");
		
		IncompleteBuchiAutomaton<State, Transition<State>> a=new IncompleteBuchiAutomaton<State, Transition<State>>(alphabet);
		a.addInitialState(s1);
		a.addTransparentState(s3);
		a.addAcceptState(s2);
		a.addTransition(s1, new Transition<State>("a", s2));
		a.reset();
		assertTrue(a.getAlphabet().isEmpty());
		assertTrue(a.getStates().isEmpty());
		assertTrue(a.getInitialStates().isEmpty());
		assertTrue(a.getAcceptStates().isEmpty());
		assertTrue(a.getTransitionsWithSource(s1).isEmpty());
		assertTrue(a.getTransparentStates().isEmpty());
	}

}
