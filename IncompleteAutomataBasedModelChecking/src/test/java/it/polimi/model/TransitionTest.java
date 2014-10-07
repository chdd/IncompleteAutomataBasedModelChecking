package it.polimi.model;

import static org.junit.Assert.*;
import it.polimi.model.automata.ba.LabelledTransition;
import it.polimi.model.automata.ba.state.State;

import org.junit.Test;

/**
 * @author Claudio Menghi
 * contains the tests that are related with the class Transition
 */
public class TransitionTest {

	/**
	 * tests the correct creation of a transition (the string that labels the transition and the destination state)
	 */
	@Test
	public void testTransitionCreation() {
		State s1=new State("s1");
		LabelledTransition<State> t=new LabelledTransition<State>("a", s1);
		assertTrue(t.getCharacter().equals("a"));
		assertTrue(t.getDestination().equals(s1));
	}
	/**
	 * tests that it is not possible to create a transition with a null character
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testTransitionCreation1() {
		State s1=new State("s1");
		new LabelledTransition<State>(null, s1);;
	}
	/**
	 * tests that it is not possible to create a transition with a null destination state
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testTransitionCreation2() {
		new LabelledTransition<State>("a", null);;
	}
	/**
	 * tests that it is not possible to create a transition with a null character and destination state
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testTransitionCreation3() {
		new LabelledTransition<State>(null, null);;
	}
	/**
	 * tests the equals method of the transition 
	 */
	public void testEquals() {
		State s1=new State("s1");
		LabelledTransition<State> t=new LabelledTransition<State>("a", s1);
		LabelledTransition<State> t1=new LabelledTransition<State>("a", s1);
		assertEquals(t, t1);
		assertTrue(t.equals(t1));
	}
	/**
	 * tests the hash code method of the transition 
	 */
	public void testHashCode() {
		State s1=new State("s1");
		LabelledTransition<State> t=new LabelledTransition<State>("a", s1);
		LabelledTransition<State> t1=new LabelledTransition<State>("a", s1);
		assertEquals(t.hashCode(), t1.hashCode());
	}
	/**
	 * tests the toString method of the transition 
	 */
	public void testToString() {
		State s1=new State("s1");
		LabelledTransition<State> t=new LabelledTransition<State>("a", s1);
		assertEquals("a->s1", t.toString());
	}
	

}
