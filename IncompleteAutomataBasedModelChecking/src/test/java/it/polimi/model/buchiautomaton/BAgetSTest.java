package it.polimi.model.buchiautomaton;

import static org.junit.Assert.*;
import it.polimi.model.BuchiAutomaton;
import it.polimi.model.State;
import it.polimi.model.Transition;

import org.junit.Test;

/**
 * tests the correct behavior of the method getS which returns a vector where the final state is marked with lambda
 * @author claudiomenghi
 *
 */
public class BAgetSTest {

	/**
	 * tests that when a state which is not accepting is passed as a final state in the method getS, the method getS returns an
	 * exception
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testGetS1() {
		// creates the states of the automaton
		State s1=new State("s1");
		State s2=new State("s2");
		State s3=new State("s3");
		State s4=new State("s4");
		// creates the BuchiAutomaton
		BuchiAutomaton<State, Transition<State>> a=new BuchiAutomaton<State, Transition<State>>();
		// add the states to the BuchiAutomaton
		a.addState(s1);
		a.addState(s2);
		a.addState(s3);
		a.addState(s4);
		// creates an ordering between the states of the automaton
		State[] statesordered=new State[4];
		statesordered[0]=s1;
		statesordered[1]=s2;
		statesordered[2]=s3;
		statesordered[3]=s4;
		
		// get the matrix S
		a.getS(s3, statesordered);
	}
	
	/**
	 * tests the correct behavior of the method getS when an accepting state is passed as parameter
	 */
	@Test
	public void testGetS2() {
		// creates the states of the automaton
		State s1=new State("s1");
		State s2=new State("s2");
		State s3=new State("s3");
		State s4=new State("s4");
		// creates the BuchiAutomaton
		BuchiAutomaton<State, Transition<State>> a=new BuchiAutomaton<State, Transition<State>>();
		// add the states to the BuchiAutomaton
		a.addState(s1);
		a.addState(s2);
		a.addAcceptState(s3);
		a.addAcceptState(s4);
		
		// creates an ordering between the states of the automaton
		State[] statesordered=new State[4];
		statesordered[0]=s1;
		statesordered[1]=s2;
		statesordered[2]=s3;
		statesordered[3]=s4;
		
		// get the matrix S
		String[] S=a.getS(s3, statesordered);
		assertTrue(S[0].equals("∅"));
		assertTrue(S[1].equals("∅"));
		assertTrue(S[2].equals("λ"));
		assertTrue(S[3].equals("∅"));
	}
	/**
	 * Tests the correct behavior of the method when a single accepting state is present in the BA
	 */
	@Test
	public void testGetS3() {
		State s1=new State("s1");
		BuchiAutomaton<State, Transition<State>> a=new BuchiAutomaton<State, Transition<State>>();
		a.addAcceptState(s1);
		State[] statesordered=new State[1];
		statesordered[0]=s1;
		
		String[] S=a.getS(s1, statesordered);
		assertTrue(S[0].equals("λ"));
	}
	/**
	 * tests that the {@link IllegalArgumentException} is generated when a null accepting state is passed as parameter
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testGetS4() {
		State s1=new State("s1");
		BuchiAutomaton<State, Transition<State>> a=new BuchiAutomaton<State, Transition<State>>();
		a.addState(s1);
		State[] statesordered=new State[4];
		statesordered[0]=s1;
		a.getS(null, statesordered);
	}
	/**
	 * tests that the {@link IllegalArgumentException} is generated when a null vector of ordered state is passed as parameter
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testGetS5() {
		State s1=new State("s1");
		BuchiAutomaton<State, Transition<State>> a=new BuchiAutomaton<State, Transition<State>>();
		a.addState(s1);
		State[] statesordered=new State[1];
		statesordered[0]=s1;
		a.getS(s1, null);
	}
	/**
	 * tests that the {@link IllegalArgumentException} is generated when the vector of the ordered states does not contains all the states
	 * of the automaton
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testGetS6() {
		State s1=new State("s1");
		State s2=new State("s2");
		BuchiAutomaton<State, Transition<State>> a=new BuchiAutomaton<State, Transition<State>>();
		a.addAcceptState(s1);
		a.addState(s2);
		
		State[] statesordered=new State[2];
		statesordered[0]=s1;
		
		a.getS(s1, statesordered);
	}
	/**
	 * tests that the {@link IllegalArgumentException} is generated when the vector of the ordered states contains  states that
	 * are not present in the automaton
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testGetS7() {
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
