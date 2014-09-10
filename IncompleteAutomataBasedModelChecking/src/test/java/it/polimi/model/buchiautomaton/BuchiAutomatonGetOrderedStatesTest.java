package it.polimi.model.buchiautomaton;

import static org.junit.Assert.*;
import it.polimi.model.BuchiAutomaton;
import it.polimi.model.State;
import it.polimi.model.Transition;

import org.junit.Test;

public class BuchiAutomatonGetOrderedStatesTest {

	@Test
	public void testOrderedStates1() {
		State s1=new State("s1");
		State s2=new State("s2");
		State s3=new State("s3");
		BuchiAutomaton<State, Transition<State>> a=new BuchiAutomaton<State, Transition<State>>();
		a.addState(s1);
		a.addState(s2);
		a.addState(s3);
		State[] states=a.getOrderedStates(s1, s3);
		assertTrue(states[0].equals(s1));
		assertTrue(states[2].equals(s3));
		assertTrue(states[1].equals(s2));
	}
	@Test
	public void testOrderedStates2() {
		State s1=new State("s1");
		State s2=new State("s2");
		State s3=new State("s3");
		State s4=new State("s4");
		BuchiAutomaton<State, Transition<State>> a=new BuchiAutomaton<State, Transition<State>>();
		a.addState(s1);
		a.addState(s2);
		a.addState(s3);
		a.addState(s4);
		State[] states=a.getOrderedStates(s1, s3);
		assertTrue(states[0].equals(s1));
		assertTrue(states[3].equals(s3));
		assertTrue(states[1].equals(s2) || states[1].equals(s4));
		assertTrue(states[2].equals(s2) || states[2].equals(s4));
	}
	@Test
	public void testOrderedStates3() {
		State s1=new State("s1");
		State s2=new State("s2");
		State s3=new State("s3");
		State s4=new State("s4");
		BuchiAutomaton<State, Transition<State>> a=new BuchiAutomaton<State, Transition<State>>();
		a.addState(s1);
		a.addState(s2);
		a.addState(s3);
		a.addState(s4);
		State[] states=a.getOrderedStates(s2, s2);
		assertTrue(states[0].equals(s2));
		assertTrue(states[1].equals(s1) || states[1].equals(s3) || states[1].equals(s4));
		assertTrue(states[2].equals(s1) || states[2].equals(s3) || states[2].equals(s4));
		assertTrue(states[3].equals(s1) || states[3].equals(s3) || states[3].equals(s4));

	}


}
