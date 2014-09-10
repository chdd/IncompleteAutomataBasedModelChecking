package it.polimi.model.buchiautomaton;

import static org.junit.Assert.*;
import it.polimi.model.BuchiAutomaton;
import it.polimi.model.State;
import it.polimi.model.Transition;

import org.junit.Test;

public class BuchiAutomatonStatesTests {

	/**
	 * tests the adding the state in the set of the states of the automaton
	 */
	@Test
	public void testStateAddition1() {
		State s1=new State("s1");
		State s2=new State("s2");
		BuchiAutomaton<State, Transition<State>> a=new BuchiAutomaton<State, Transition<State>>();
		a.addState(s1);
		assertTrue(a.isContained(s1));
		assertFalse(a.isContained(s2));
		assertFalse(a.isContained(null));
	}
	/**
	 * tests the adding the state in the set of the states of the automaton
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testStateAddition2() {
		BuchiAutomaton<State, Transition<State>> a=new BuchiAutomaton<State, Transition<State>>();
		a.addState(null);
	}
	/**
	 * tests the get the states of the automaton
	 */
	@Test
	public void getStates() {
		State s1=new State("s1");
		BuchiAutomaton<State, Transition<State>> a=new BuchiAutomaton<State, Transition<State>>();
		a.addState(s1);
		assertTrue(a.getStates().size()==1);
		assertTrue(a.getStates().contains(s1));
		
	}
	/**
	 * tests the adding the state in the set of the states of the automaton
	 */
	@Test
	public void testInitialStateAddition1() {
		State s1=new State("s1");
		State s2=new State("s2");
		BuchiAutomaton<State, Transition<State>> a=new BuchiAutomaton<State, Transition<State>>();
		a.addInitialState(s1);
		assertTrue(a.isContained(s1));
		assertFalse(a.isContained(s2));
		assertFalse(a.isContained(null));
		a.addInitialState(s1);
		a.addInitialState(s2);
		assertTrue(a.isContained(s1));
		assertTrue(a.isContained(s2));
	}
	/**
	 * tests the adding the state in the set of the states of the automaton
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testInitialStateAddition2() {
		BuchiAutomaton<State, Transition<State>> a=new BuchiAutomaton<State, Transition<State>>();
		a.addInitialState(null);
	}
	/**
	 * tests the get initial states method of the automaton
	 */
	@Test
	public void getInitialStates() {
		State s1=new State("s1");
		BuchiAutomaton<State, Transition<State>> a=new BuchiAutomaton<State, Transition<State>>();
		a.addInitialState(s1);
		assertTrue(a.getInitialStates().size()==1);
		assertTrue(a.getInitialStates().contains(s1));
		assertTrue(a.getStates().size()==1);
		assertTrue(a.getStates().contains(s1));
		
	}
	/**
	 * tests the adding the state in the set of the states of the automaton
	 */
	@Test
	public void testAcceptStateAddition1() {
		State s1=new State("s1");
		State s2=new State("s2");
		BuchiAutomaton<State, Transition<State>> a=new BuchiAutomaton<State, Transition<State>>();
		a.addAcceptState(s1);
		assertTrue(a.isContained(s1));
		assertFalse(a.isContained(s2));
		assertFalse(a.isContained(null));
		a.addAcceptState(s1);
		a.addAcceptState(s2);
		assertTrue(a.isContained(s1));
		assertTrue(a.isContained(s2));
	}
	/**
	 * tests the adding the state in the set of the states of the automaton
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testAcceptStateAddition2() {
		BuchiAutomaton<State, Transition<State>> a=new BuchiAutomaton<State, Transition<State>>();
		a.addInitialState(null);
	}
	/**
	 * tests the get initial states method of the automaton
	 */
	@Test
	public void getAcceptStates() {
		State s1=new State("s1");
		BuchiAutomaton<State, Transition<State>> a=new BuchiAutomaton<State, Transition<State>>();
		a.addAcceptState(s1);
		assertTrue(a.getAcceptStates().size()==1);
		assertTrue(a.getAcceptStates().contains(s1));
		assertTrue(a.getStates().size()==1);
		assertTrue(a.getStates().contains(s1));
		
	}

}
