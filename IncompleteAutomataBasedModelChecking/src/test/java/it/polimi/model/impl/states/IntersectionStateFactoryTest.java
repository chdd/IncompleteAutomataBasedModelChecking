package it.polimi.model.impl.states;

import static org.junit.Assert.*;
import it.polimi.model.interfaces.states.IntersectionStateFactory;

import org.junit.Test;

public class IntersectionStateFactoryTest {

	@Test
	public void testConstructor1() {

		IntersectionStateFactory<State,IntersectionState<State>> factory = new IntersectionStateFactoryImpl();
		IntersectionState<State> s1 = factory.create();
		assertTrue(s1.getId() >= 0);
		assertTrue(s1.getName() != null);
		assertTrue(s1.getNumber() >= 0 && s1.getNumber() <= 2);
		assertTrue(s1.getS1() != null);
		assertTrue(s1.getS2() != null);
	}

	@Test(expected = NullPointerException.class)
	public void testConstructor2() {

		IntersectionStateFactory<State,IntersectionState<State>> factory = new IntersectionStateFactoryImpl();
		factory.create(null);
	}

	@Test
	public void testConstructor3() {

		IntersectionStateFactory<State,IntersectionState<State>> factory = new IntersectionStateFactoryImpl();
		IntersectionState<State> s1 = factory.create("test");
		assertTrue(s1.getId() >= 0);
		assertTrue(s1.getName().equals("test"));
		assertTrue(s1.getNumber() >= 0 && s1.getNumber() <= 2);
		assertTrue(s1.getS1() != null);
		assertTrue(s1.getS2() != null);
	}

	@Test
	public void testConstructor4() {

		State s1 = new State("name1", 0);
		State s2 = new State("name2", 0);

		IntersectionStateFactory<State,IntersectionState<State>> factory = new IntersectionStateFactoryImpl();
		IntersectionState<State> intState = factory.create(s1, s2, 0);
		assertTrue(intState.getId() >= 0);
		assertTrue(intState.getName().equals(s1.getName() + "-" + s2.getName() + "-" +intState.getNumber()));
		assertTrue(intState.getNumber() == 0);
		assertTrue(intState.getS1().equals(s1));
		assertTrue(intState.getS2().equals(s2));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testConstructor5() {

		State s1 = new State("name1", 0);
		State s2 = new State("name2", 0);

		IntersectionStateFactory<State,IntersectionState<State>> factory = new IntersectionStateFactoryImpl();
		factory.create(s1, s2, 5);
	}

	@Test(expected = NullPointerException.class)
	public void testConstructor6() {

		State s1 = new State("name1", 0);

		IntersectionStateFactory<State,IntersectionState<State>> factory = new IntersectionStateFactoryImpl();
		factory.create(s1, null, 2);
	}

	@Test
	public void testConstructor7() {

		IntersectionStateFactory<State,IntersectionState<State>> factory = new IntersectionStateFactoryImpl();
		IntersectionState<State> s1 = factory.create("test", 5);
		assertTrue(s1.getId() == 5);
		assertTrue(s1.getName().equals("test"));
		assertTrue(s1.getNumber() >= 0 && s1.getNumber() <= 2);
		assertTrue(s1.getS1() != null);
		assertTrue(s1.getS2() != null);
	}

	@Test(expected = NullPointerException.class)
	public void testConstructor8() {

		IntersectionStateFactory<State,IntersectionState<State>> factory = new IntersectionStateFactoryImpl();
		factory.create(null, 5);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testConstructor9() {

		IntersectionStateFactory<State,IntersectionState<State>>  factory = new IntersectionStateFactoryImpl();
		factory.create("name", -2);
	}
	
	@Test(expected = NullPointerException.class)
	public void testConstructor10() {

		IntersectionStateFactory<State,IntersectionState<State>>  factory = new IntersectionStateFactoryImpl();
		factory.create(null);
	}
	
	@Test
	public void testConstructor11() {

		IntersectionStateFactory<State,IntersectionState<State>>  factory = new IntersectionStateFactoryImpl();
		IntersectionState<State> s1 = factory.create("prova");
		assertTrue(s1.getId() >= 0);
		assertTrue(s1.getName().equals("prova"));
		assertTrue(s1.getNumber() >= 0 && s1.getNumber() <= 2);
		assertTrue(s1.getS1() != null);
		assertTrue(s1.getS2() != null);
	}
	
}
