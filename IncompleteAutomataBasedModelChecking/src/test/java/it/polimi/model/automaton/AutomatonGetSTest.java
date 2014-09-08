package it.polimi.model.automaton;

import static org.junit.Assert.*;
import it.polimi.model.BuchiAutomaton;
import it.polimi.model.State;
import it.polimi.model.Transition;

import org.junit.Test;

public class AutomatonGetSTest {

	@Test
	public void testOrderedStates1() {
		State s1=new State("s1");
		State s2=new State("s2");
		State s3=new State("s3");
		State s4=new State("s4");
		BuchiAutomaton<State, Transition<State>> a=new BuchiAutomaton<State, Transition<State>>();
		a.addState(s1);
		a.addState(s2);
		a.addState(s3);
		a.addState(s4);
		State[] statesordered=new State[4];
		statesordered[0]=s1;
		statesordered[1]=s2;
		statesordered[2]=s3;
		statesordered[3]=s4;
		
		String[] S=a.getS(s3, statesordered);
		assertTrue(S[0].equals("∅"));
		assertTrue(S[1].equals("∅"));
		assertTrue(S[2].equals("λ"));
		assertTrue(S[3].equals("∅"));
	}
	@Test
	public void testOrderedStates2() {
		State s1=new State("s1");
		BuchiAutomaton<State, Transition<State>> a=new BuchiAutomaton<State, Transition<State>>();
		a.addState(s1);
		State[] statesordered=new State[1];
		statesordered[0]=s1;
		
		String[] S=a.getS(s1, statesordered);
		assertTrue(S[0].equals("λ"));
	}
	@Test(expected=IllegalArgumentException.class)
	public void testOrderedStates3() {
		State s1=new State("s1");
		BuchiAutomaton<State, Transition<State>> a=new BuchiAutomaton<State, Transition<State>>();
		a.addState(s1);
		State[] statesordered=new State[4];
		statesordered[0]=s1;
		a.getS(null, statesordered);
	}
	@Test(expected=IllegalArgumentException.class)
	public void testOrderedStates4() {
		State s1=new State("s1");
		BuchiAutomaton<State, Transition<State>> a=new BuchiAutomaton<State, Transition<State>>();
		a.addState(s1);
		State[] statesordered=new State[1];
		statesordered[0]=s1;
		a.getS(s1, null);
	}
	@Test(expected=IllegalArgumentException.class)
	public void testOrderedStates5() {
		State s1=new State("s1");
		State s2=new State("s2");
		BuchiAutomaton<State, Transition<State>> a=new BuchiAutomaton<State, Transition<State>>();
		a.addState(s1);
		State[] statesordered=new State[2];
		statesordered[0]=s1;
		statesordered[1]=s2;
		
		a.getS(s1, statesordered);
	}




}
