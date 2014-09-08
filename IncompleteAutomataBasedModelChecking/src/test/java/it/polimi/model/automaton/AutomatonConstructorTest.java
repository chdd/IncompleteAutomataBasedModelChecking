package it.polimi.model.automaton;

import static org.junit.Assert.*;
import it.polimi.model.BuchiAutomaton;
import it.polimi.model.State;
import it.polimi.model.Transition;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

/**
 * @author Claudio Menghi
 * contains the tests that are related with the class Automaton
 */
public class AutomatonConstructorTest {

	/**
	 * tests the correct creation of the state (the correct setting of the field name)
	 */
	@Test
	public void testConstructor1() {
		BuchiAutomaton<State, Transition<State>> a=new BuchiAutomaton<State, Transition<State>>();
		assertTrue(a.getInitialStates()!=null);
		assertTrue(a.getInitialStates().size()==0);
		assertTrue(a.getStates()!=null);
		assertTrue(a.getStates().size()==0);
		assertTrue(a.getAcceptStates()!=null);
		assertTrue(a.getAcceptStates().size()==0);
		assertTrue(a.getAlphabet()!=null);
		assertTrue(a.getAlphabet().size()==0);
	}
	/**
	 * tests the correct creation of the state (the correct setting of the field name)
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testConstructor2() {
		new BuchiAutomaton<State, Transition<State>>(null);
	}

	/**
	 * tests the correct creation of the state (the correct setting of the field name)
	 */
	@Test
	public void testConstructor3() {
		Set<String> alphabet=new HashSet<String>();
		alphabet.add("a");
		alphabet.add("b");
		BuchiAutomaton<State, Transition<State>> a=new BuchiAutomaton<State, Transition<State>>(alphabet);
		assertTrue(a.getInitialStates()!=null);
		assertTrue(a.getInitialStates().size()==0);
		assertTrue(a.getStates()!=null);
		assertTrue(a.getStates().size()==0);
		assertTrue(a.getAcceptStates()!=null);
		assertTrue(a.getAcceptStates().size()==0);
		assertTrue(a.getAlphabet()!=null);
		assertTrue(a.getAlphabet().size()==2);
	}
	/**
	 * tests the correct creation of the state (the correct setting of the field name)
	 */
	@Test
	public void testGetAlphabet() {
		Set<String> alphabet=new HashSet<String>();
		alphabet.add("a");
		alphabet.add("b");
		BuchiAutomaton<State, Transition<State>> a=new BuchiAutomaton<State, Transition<State>>(alphabet);
		assertTrue(a.getAlphabet().contains("a"));
		assertTrue(a.getAlphabet().contains("b"));
	}
	
}
