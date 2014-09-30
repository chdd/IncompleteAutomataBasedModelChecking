/**
 * 
 */
package it.polimi.model;

import static org.junit.Assert.*;
import it.polimi.model.graph.State;

import org.junit.Test;

/**
 * @author Claudio Menghi
 * contains the tests that are related with the class State
 */
public class StateTest {

	/**
	 * tests the correct creation of the state (the correct setting of the field name)
	 */
	@Test
	public void testConstructor1() {
		State s1=new State("s1");
		assertTrue(s1.getName().equals("s1"));
	}
	/**
	 * verifies that is not possible to create a state with a null name
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testConstructor2() {
		new State(null);
	}
	
	/**
	 * tests the correct behavior of the toString method
	 */
	@Test
	public void testToString() {
		State s1=new State("s1");
		assertTrue(s1.getName().equals("s1"));
	}
	/**
	 * tests the correct behavior of the equal method
	 */
	@Test
	public void testEquals() {
		State s1=new State("s1");
		State s2=new State("s1");
		State s3=new State("s2");
		assertTrue(s1.equals(s2));
		assertFalse(s1.equals(s3));
	}
	/**
	 * tests the correct behavior of the hash code method
	 */
	@Test
	public void testHashCode() {
		State s1=new State("s1");
		State s2=new State("s1");
		State s3=new State("s2");
		assertTrue(s1.hashCode()==s2.hashCode());
		assertFalse(s1.hashCode()==s3.hashCode());
	}
	/**
	 * tests the correct behavior of the method set name
	 */
	@Test
	public void testSetName() {
		State s1=new State("s1");
		assertFalse(s1.getName().equals("s2"));
	}


}
