package it.polimi.model;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * @author Claudio Menghi
 * contains the tests that are related with the class IntersectionState
 */
public class IntersectionStateTest {


	/**
	 * tests the correct creation of the state (the correct setting of the field name)
	 */
	@Test
	public void testConstructor1() {
		State s1=new State("s1");
		State s2=new State("s2");
		IntersectionState<State> intersectionState=new IntersectionState<State>(s1,s2,0);
		assertTrue(intersectionState.getName().equals("s1-s2-0"));
	}
	/**
	 * verifies that is not possible to create a state with a null name
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testConstructor2() {
		State s1=new State("s1");
		new IntersectionState<State>(s1,null,0);
	}
	/**
	 * verifies that is not possible to create a state with a null name
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testConstructor3() {
		State s1=new State("s1");
		new IntersectionState<State>(null,s1,0);
	}
	/**
	 * verifies that is not possible to create a state with a number not included in 0,2
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testConstructor4() {
		State s1=new State("s1");
		State s2=new State("s1");
		new IntersectionState<State>(s1,s2,-1);
	}
	/**
	 * verifies that is not possible to create a state with a number not included in 0,2
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testConstructor5() {
		State s1=new State("s1");
		State s2=new State("s1");
		new IntersectionState<State>(s1,s2,3);
	}
	/**
	 * tests the correct behavior of the toString method
	 */
	@Test
	public void testToString() {
		State s1=new State("s1");
		State s2=new State("s2");
		IntersectionState<State> intersectionState=new IntersectionState<State>(s1,s2,0);
		assertTrue(intersectionState.getName().equals("s1-s2-0"));
	}
	/**
	 * tests the get state methods of the IntersectionState
	 */
	@Test
	public void testGetter() {
		State s1=new State("s1");
		State s2=new State("s2");
		IntersectionState<State> intersectionState=new IntersectionState<State>(s1,s2,0);
		assertTrue(intersectionState.getS1().equals(s1));
		assertTrue(intersectionState.getS2().equals(s2));
		assertEquals(intersectionState.getNumber(), 0);
	}
	/**
	 * tests the correct behavior of the equal method
	 */
	@Test
	public void testEquals() {
		State s1=new State("s1");
		State s2=new State("s2");

		State s3=new State("s1");
		State s4=new State("s2");
		
		IntersectionState<State> intersectionState1=new IntersectionState<State>(s1,s2,0);
		IntersectionState<State> intersectionState2=new IntersectionState<State>(s3,s4,0);
		IntersectionState<State> intersectionState3=new IntersectionState<State>(s3,s4,1);
		IntersectionState<State> intersectionState4=new IntersectionState<State>(s2,s4,1);
		
		assertTrue(intersectionState1.equals(intersectionState2));
		assertFalse(intersectionState1.equals(intersectionState3));
		assertFalse(intersectionState4.equals(intersectionState3));
	}
	/**
	 * tests the correct behavior of the hash code method
	 */
	@Test
	public void testHashCode() {
		State s1=new State("s1");
		State s2=new State("s2");

		State s3=new State("s1");
		State s4=new State("s2");
		
		IntersectionState<State> intersectionState1=new IntersectionState<State>(s1,s2,0);
		IntersectionState<State> intersectionState2=new IntersectionState<State>(s3,s4,0);
		IntersectionState<State> intersectionState3=new IntersectionState<State>(s3,s4,1);
		IntersectionState<State> intersectionState4=new IntersectionState<State>(s2,s4,1);
		
		assertTrue(intersectionState1.hashCode()==intersectionState2.hashCode());
		assertFalse(intersectionState1.hashCode()==intersectionState3.hashCode());
		assertFalse(intersectionState4.hashCode()==intersectionState3.hashCode());
	}


}
