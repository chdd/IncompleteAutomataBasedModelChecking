package it.polimi.model.impl.states;

import static org.junit.Assert.*;

import org.junit.Test;

public class IntersectionStateTest {

	@Test
	public void testConstructor1() {
		State s1 = new State(1);
		State s2 = new State(3);
		String name = "name";
		int id = 1;
		int number = 2;
		IntersectionState<State> intStateS1 = new IntersectionState<State>(s1,
				s2, name, number, id);
		assertTrue(intStateS1.getS1().equals(s1));
		assertTrue(intStateS1.getS2().equals(s2));
		assertTrue(intStateS1.getName().equals(name));
		assertTrue(intStateS1.getId() == id);
		assertTrue(intStateS1.getNumber() == number);
	}

	@Test(expected = NullPointerException.class)
	public void testConstructor2() {
		State s1 = new State(3);
		String name = "name";
		int id = 1;
		int number = 5;
		new IntersectionState<State>(null, s1, name, id, number);
	}

	@Test(expected = NullPointerException.class)
	public void testConstructor3() {
		State s1 = new State(3);
		String name = "name";
		int id = 1;
		int number = 5;
		new IntersectionState<State>(s1, null, name, id, number);
	}

	@Test(expected = NullPointerException.class)
	public void testConstructor4() {
		State s1 = new State(3);
		
		int id = 1;
		int number = 5;
		new IntersectionState<State>(s1, s1, null, id, number);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testConstructor5() {
		State s1 = new State(3);
		State s2 = new State(3);
		String name = "name";
		
		int number = 5;
		new IntersectionState<State>(s1, s2, name, -5, number);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testConstructor6() {
		State s1 = new State(3);
		State s2 = new State(3);
		String name = "name";
		int id = 1;
		
		new IntersectionState<State>(s1, s2, name, id, -2);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testConstructor7() {
		State s1 = new State(3);
		State s2 = new State(3);
		String name = "name";
		int id = 1;
		
		new IntersectionState<State>(s1, s2, name, 4, id);
	}
}
