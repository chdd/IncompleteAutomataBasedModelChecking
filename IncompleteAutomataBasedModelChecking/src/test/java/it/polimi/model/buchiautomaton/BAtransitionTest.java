package it.polimi.model.buchiautomaton;

import static org.junit.Assert.*;
import it.polimi.model.automata.ba.BuchiAutomaton;
import it.polimi.model.automata.ba.LabelledTransition;
import it.polimi.model.automata.ba.state.State;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

public class BAtransitionTest {

	/**
	 * tests the correct addition of the transition 
	 */
	@Test
	public void testTransition1() {
		Set<String> alphabet=new HashSet<String>();
		alphabet.add("a");
		alphabet.add("b");
		State s1=new State("s1");
		State s2=new State("s2");
		BuchiAutomaton<State, LabelledTransition<State>> a=new BuchiAutomaton<State, LabelledTransition<State>>(alphabet);
		a.addState(s1);
		a.addState(s2);
		LabelledTransition<State> t1=new LabelledTransition<State>("a",s2);
		a.addTransition(s1, t1);
		
		assertTrue(a.getTransitionsWithSource(s1).contains(t1));
		assertTrue(a.getTransitionsWithSource(s1).size()==1);
		assertTrue(a.getTransitionsWithSource(s2).isEmpty());
		
	}
	/**
	 * tests the correct addition of the transition when the character is not in the alphabet
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testTransition2() {
		Set<String> alphabet=new HashSet<String>();
		alphabet.add("a");
		alphabet.add("b");
		State s1=new State("s1");
		State s2=new State("s2");
		BuchiAutomaton<State, LabelledTransition<State>> a=new BuchiAutomaton<State, LabelledTransition<State>>(alphabet);
		a.addState(s1);
		a.addState(s2);
		LabelledTransition<State> t1=new LabelledTransition<State>("c",s2);
		a.addTransition(s1, t1);
	}
	/**
	 * tests the correct addition of the transition when the source of the transition is null
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testTransition3() {
		Set<String> alphabet=new HashSet<String>();
		alphabet.add("a");
		alphabet.add("b");
		State s1=new State("s1");
		State s2=new State("s2");
		BuchiAutomaton<State, LabelledTransition<State>> a=new BuchiAutomaton<State, LabelledTransition<State>>(alphabet);
		a.addState(s1);
		a.addState(s2);
		LabelledTransition<State> t1=new LabelledTransition<State>("a",s2);
		a.addTransition(null, t1);
	}
	/**
	 * tests the correct addition of the transition  when the transition is null
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testTransition4() {
		Set<String> alphabet=new HashSet<String>();
		alphabet.add("a");
		alphabet.add("b");
		State s1=new State("s1");
		State s2=new State("s2");
		BuchiAutomaton<State, LabelledTransition<State>> a=new BuchiAutomaton<State, LabelledTransition<State>>(alphabet);
		a.addState(s1);
		a.addState(s2);
		a.addTransition(s1, null);
	}
	/**
	 * tests the correct addition of the transition when the source state of the transition is not in the set of the states of the graph
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testTransition5() {
		Set<String> alphabet=new HashSet<String>();
		alphabet.add("a");
		alphabet.add("b");
		State s1=new State("s1");
		State s2=new State("s2");
		State s3=new State("s3");
		BuchiAutomaton<State, LabelledTransition<State>> a=new BuchiAutomaton<State, LabelledTransition<State>>(alphabet);
		a.addState(s1);
		a.addState(s2);
		LabelledTransition<State> t1=new LabelledTransition<State>("a",s2);
		a.addTransition(s3, t1);
	}
	/**
	 * tests the correct addition of the transition when the destination state of the transition is not in the set of the states of the graph
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testTransition6() {
		Set<String> alphabet=new HashSet<String>();
		alphabet.add("a");
		alphabet.add("b");
		State s1=new State("s1");
		State s2=new State("s2");
		State s3=new State("s3");
		BuchiAutomaton<State, LabelledTransition<State>> a=new BuchiAutomaton<State, LabelledTransition<State>>(alphabet);
		a.addState(s1);
		a.addState(s2);
		LabelledTransition<State> t1=new LabelledTransition<State>("a",s3);
		a.addTransition(s3, t1);
		
		
	}

}
