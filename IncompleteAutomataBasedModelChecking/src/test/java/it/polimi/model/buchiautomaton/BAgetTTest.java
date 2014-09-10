package it.polimi.model.buchiautomaton;

import static org.junit.Assert.*;
import it.polimi.model.BuchiAutomaton;
import it.polimi.model.State;
import it.polimi.model.Transition;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

public class BAgetTTest {

	@Test
	public void testGetT1() {
		State s1=new State("s1");
		State s2=new State("s2");
		State s3=new State("s3");
		State s4=new State("s4");
		Set<String> alphabet=new HashSet<String>();
		alphabet.add("a");
		alphabet.add("b");
		alphabet.add("c");
		alphabet.add("d");
		
		BuchiAutomaton<State, Transition<State>> a=new BuchiAutomaton<State, Transition<State>>(alphabet);
		a.addState(s1);
		a.addState(s2);
		a.addState(s3);
		a.addState(s4);
		
		a.addTransition(s1, new Transition<State>("a", s2));
		a.addTransition(s1, new Transition<State>("b", s2));
		a.addTransition(s2, new Transition<State>("c", s3));
		a.addTransition(s3, new Transition<State>("d", s4));
		
		State[] statesordered=new State[4];
		statesordered[0]=s1;
		statesordered[1]=s2;
		statesordered[2]=s3;
		statesordered[3]=s4;
		
		String[][] T=a.getT(s1, s4, statesordered);
		assertTrue(T[0][0].equals("∅"));
		assertTrue(T[0][1].equals("a+b") || T[0][1].equals("b+a"));
		assertTrue(T[0][2].equals("∅"));
		assertTrue(T[0][3].equals("∅"));
		
		assertTrue(T[1][0].equals("∅"));
		assertTrue(T[1][1].equals("∅"));
		assertTrue(T[1][2].equals("c"));
		assertTrue(T[1][3].equals("∅"));
		
		assertTrue(T[2][0].equals("∅"));
		assertTrue(T[2][1].equals("∅"));
		assertTrue(T[2][2].equals("∅"));
		assertTrue(T[2][3].equals("d"));
		
		assertTrue(T[3][0].equals("∅"));
		assertTrue(T[3][1].equals("∅"));
		assertTrue(T[3][2].equals("∅"));
		assertTrue(T[3][3].equals("∅"));
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testGetT2() {
		State s1=new State("s1");
		State s2=new State("s2");
		Set<String> alphabet=new HashSet<String>();
		alphabet.add("a");
		alphabet.add("b");
		
		BuchiAutomaton<State, Transition<State>> a=new BuchiAutomaton<State, Transition<State>>(alphabet);
		a.addState(s1);
		a.addState(s2);
		
		a.addTransition(s1, new Transition<State>("a", s2));
		a.addTransition(s1, new Transition<State>("b", s2));
		
		State[] statesordered=new State[4];
		statesordered[0]=s1;
		statesordered[1]=s2;
		a.getT(null, s2, statesordered);
	}
	@Test(expected=IllegalArgumentException.class)
	public void testGetT3() {
		State s1=new State("s1");
		State s2=new State("s2");
		Set<String> alphabet=new HashSet<String>();
		alphabet.add("a");
		alphabet.add("b");
		
		BuchiAutomaton<State, Transition<State>> a=new BuchiAutomaton<State, Transition<State>>(alphabet);
		a.addState(s1);
		a.addState(s2);
		
		a.addTransition(s1, new Transition<State>("a", s2));
		a.addTransition(s1, new Transition<State>("b", s2));
		
		State[] statesordered=new State[4];
		statesordered[0]=s1;
		statesordered[1]=s2;
		a.getT(s2, null, statesordered);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testGetT4() {
		State s1=new State("s1");
		State s2=new State("s2");
		Set<String> alphabet=new HashSet<String>();
		alphabet.add("a");
		alphabet.add("b");
		
		BuchiAutomaton<State, Transition<State>> a=new BuchiAutomaton<State, Transition<State>>(alphabet);
		a.addState(s1);
		a.addState(s2);
		
		a.addTransition(s1, new Transition<State>("a", s2));
		a.addTransition(s1, new Transition<State>("b", s2));
		
		State[] statesordered=new State[4];
		statesordered[0]=s1;
		statesordered[1]=s2;
		a.getT(s1, s2, null);
	}
	@Test(expected=IllegalArgumentException.class)
	public void testGetT5() {
		State s1=new State("s1");
		State s2=new State("s2");
		State s3=new State("s2");
		
		Set<String> alphabet=new HashSet<String>();
		alphabet.add("a");
		alphabet.add("b");
		
		BuchiAutomaton<State, Transition<State>> a=new BuchiAutomaton<State, Transition<State>>(alphabet);
		a.addState(s1);
		a.addState(s2);
		
		a.addTransition(s1, new Transition<State>("a", s2));
		a.addTransition(s1, new Transition<State>("b", s2));
		
		State[] statesordered=new State[4];
		statesordered[0]=s1;
		statesordered[1]=s2;
		a.getT(s3, s2, statesordered);
	}
	@Test(expected=IllegalArgumentException.class)
	public void testGetT6() {
		State s1=new State("s1");
		State s2=new State("s2");
		State s3=new State("s2");
		
		Set<String> alphabet=new HashSet<String>();
		alphabet.add("a");
		alphabet.add("b");
		
		BuchiAutomaton<State, Transition<State>> a=new BuchiAutomaton<State, Transition<State>>(alphabet);
		a.addState(s1);
		a.addState(s2);
		
		a.addTransition(s1, new Transition<State>("a", s2));
		a.addTransition(s1, new Transition<State>("b", s2));
		
		State[] statesordered=new State[4];
		statesordered[0]=s1;
		statesordered[1]=s2;
		a.getT(s2, s3, statesordered);
	}
	@Test(expected=IllegalArgumentException.class)
	public void testGetT7() {
		State s1=new State("s1");
		State s2=new State("s2");
		State s3=new State("s2");
		
		Set<String> alphabet=new HashSet<String>();
		alphabet.add("a");
		alphabet.add("b");
		
		BuchiAutomaton<State, Transition<State>> a=new BuchiAutomaton<State, Transition<State>>(alphabet);
		a.addState(s1);
		a.addState(s2);
		
		a.addTransition(s1, new Transition<State>("a", s2));
		a.addTransition(s1, new Transition<State>("b", s2));
		
		State[] statesordered=new State[4];
		statesordered[0]=s1;
		statesordered[1]=s3;
		a.getT(s1, s2, statesordered);
	}
	@Test(expected=IllegalArgumentException.class)
	public void testGetT8() {
		State s1=new State("s1");
		State s2=new State("s2");
		
		Set<String> alphabet=new HashSet<String>();
		alphabet.add("a");
		alphabet.add("b");
		
		BuchiAutomaton<State, Transition<State>> a=new BuchiAutomaton<State, Transition<State>>(alphabet);
		a.addState(s1);
		a.addState(s2);
		
		a.addTransition(s1, new Transition<State>("a", s2));
		a.addTransition(s1, new Transition<State>("b", s2));
		
		State[] statesordered=new State[4];
		statesordered[0]=s1;
		statesordered[1]=null;
		a.getT(s1, s2, statesordered);
	}
	@Test
	public void testGetT9() {
		State s1=new State("s1");
		State s2=new State("s2");
		State s3=new State("s3");
		State s4=new State("s4");
		Set<String> alphabet=new HashSet<String>();
		alphabet.add("a");
		alphabet.add("b");
		alphabet.add("c");
		alphabet.add("d");
		
		BuchiAutomaton<State, Transition<State>> a=new BuchiAutomaton<State, Transition<State>>(alphabet);
		a.addState(s1);
		a.addState(s2);
		a.addState(s3);
		a.addState(s4);
		
		a.addTransition(s1, new Transition<State>("a", s2));
		a.addTransition(s1, new Transition<State>("a", s1));
		a.addTransition(s1, new Transition<State>("b", s2));
		a.addTransition(s2, new Transition<State>("c", s3));
		a.addTransition(s3, new Transition<State>("d", s4));
		a.addTransition(s4, new Transition<State>("d", s4));
		
		State[] statesordered=new State[4];
		statesordered[0]=s1;
		statesordered[1]=s2;
		statesordered[2]=s3;
		statesordered[3]=s4;
		
		String[][] T=a.getT(s1, s4, statesordered);
		assertTrue(T[0][0].equals("a"));
		assertTrue(T[0][1].equals("a+b") || T[0][1].equals("b+a"));
		assertTrue(T[0][2].equals("∅"));
		assertTrue(T[0][3].equals("∅"));
		
		assertTrue(T[1][0].equals("∅"));
		assertTrue(T[1][1].equals("∅"));
		assertTrue(T[1][2].equals("c"));
		assertTrue(T[1][3].equals("∅"));
		
		assertTrue(T[2][0].equals("∅"));
		assertTrue(T[2][1].equals("∅"));
		assertTrue(T[2][2].equals("∅"));
		assertTrue(T[2][3].equals("d"));
		
		assertTrue(T[3][0].equals("∅"));
		assertTrue(T[3][1].equals("∅"));
		assertTrue(T[3][2].equals("∅"));
		assertTrue(T[3][3].equals("d"));
	}
	@Test
	public void testGetT10() {
		State s1=new State("s1");
		State s2=new State("s2");
		State s3=new State("s3");
		State s4=new State("s4");
		Set<String> alphabet=new HashSet<String>();
		alphabet.add("a");
		alphabet.add("b");
		alphabet.add("c");
		alphabet.add("d");
		
		BuchiAutomaton<State, Transition<State>> a=new BuchiAutomaton<State, Transition<State>>(alphabet);
		a.addState(s1);
		a.addState(s2);
		a.addState(s3);
		a.addState(s4);
		
		a.addTransition(s1, new Transition<State>("a", s2));
		a.addTransition(s1, new Transition<State>("a", s1));
		a.addTransition(s1, new Transition<State>("b", s2));
		a.addTransition(s2, new Transition<State>("c", s3));
		a.addTransition(s3, new Transition<State>("d", s4));
		a.addTransition(s4, new Transition<State>("d", s4));
		
		State[] statesordered=new State[4];
		statesordered[0]=s1;
		statesordered[1]=s2;
		statesordered[2]=s3;
		statesordered[3]=s4;
		
		String[][] T=a.getT(s1, s1, statesordered);
		assertTrue(T[0][0].equals("a"));
		assertTrue(T[0][1].equals("a+b") || T[0][1].equals("b+a"));
		assertTrue(T[0][2].equals("∅"));
		assertTrue(T[0][3].equals("∅"));
		
		assertTrue(T[1][0].equals("∅"));
		assertTrue(T[1][1].equals("∅"));
		assertTrue(T[1][2].equals("c"));
		assertTrue(T[1][3].equals("∅"));
		
		assertTrue(T[2][0].equals("∅"));
		assertTrue(T[2][1].equals("∅"));
		assertTrue(T[2][2].equals("∅"));
		assertTrue(T[2][3].equals("d"));
		
		assertTrue(T[3][0].equals("∅"));
		assertTrue(T[3][1].equals("∅"));
		assertTrue(T[3][2].equals("∅"));
		assertTrue(T[3][3].equals("d"));
	}
}
