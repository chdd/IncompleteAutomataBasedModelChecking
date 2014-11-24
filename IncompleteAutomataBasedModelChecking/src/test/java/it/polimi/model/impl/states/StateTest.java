package it.polimi.model.impl.states;

import static org.junit.Assert.*;

import org.junit.Test;

public class StateTest {

	@Test
	public void testStateCreation1() {
		State s = new State(0);
		assertTrue(s.getName().equals(""));
		assertTrue(s.getId() == 0);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testStateCreation2() {
		new State(-1);
	}

	@Test
	public void testStateCreation3() {
		State s = new State("c", 2);
		assertTrue(s.getName().equals("c"));
		assertTrue(s.getId() == 2);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testStateCreation4() {
		new State("c", -1);
	}

	@Test(expected = NullPointerException.class)
	public void testStateCreation5() {
		new State(null, 0);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testStateCreation6() {
		new State(null, -1);
	}
	
	@Test
	public void equals() {
		new State("c", 1).equals(new State("c", 1));
	}
}
