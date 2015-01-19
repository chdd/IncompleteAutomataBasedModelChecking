package it.polimi.automata.state.impl;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * Tests the class {@link StateImpl}
 * 
 * @author claudiomenghi
 * 
 */
public class StateImplTest {

	private static final String name1 = "name";
	private static final String name2 = "name2";
	private static final String emptyName = "";
	private static final int id1 = 1;
	private static final int id2 = 2;

	/**
	 * Tests the constructor of the class StateImpl
	 */
	@Test
	public void constructorTest() {
		StateImpl state = new StateImpl(StateImplTest.name1, StateImplTest.id1);
		assertTrue(state.id == StateImplTest.id1);
		assertTrue(state.name == StateImplTest.name1);

		state = new StateImpl(StateImplTest.id1);
		assertTrue(state.id == StateImplTest.id1);
		assertTrue(state.name == emptyName);
	}

	/**
	 * Tests the method getId of the class StateImpl
	 */
	@Test
	public void getIdTest() {
		StateImpl state = new StateImpl(StateImplTest.name1, StateImplTest.id1);
		assertTrue(state.getId() == StateImplTest.id1);

		state = new StateImpl(StateImplTest.id1);
		assertTrue(state.getId() == StateImplTest.id1);
	}

	/**
	 * Tests the behavior of the method getName
	 */
	public void getNameTest() {
		StateImpl state = new StateImpl(StateImplTest.name1, StateImplTest.id1);
		assertTrue(state.getName() == StateImplTest.name1);

		state = new StateImpl(StateImplTest.id1);
		assertTrue(state.getName() == emptyName);
	}

	/**
	 * Tests the behavior of the method setName
	 */
	public void setNameTest() {
		StateImpl state = new StateImpl(StateImplTest.name1, StateImplTest.id1);
		assertTrue(state.getName() == StateImplTest.name1);

		state.setName(StateImplTest.name2);
		assertTrue(state.name == name2);
		assertTrue(state.getName() == StateImplTest.name2);
	}
	
	/**
	 * Tests the behavior of the toString method
	 */
	public void toStringTest() {
		StateImpl state = new StateImpl(StateImplTest.name1, StateImplTest.id1);
		assertTrue(state.toString().equals(StateImplTest.id1+": "+StateImplTest.name1));
	}
	
	/**
	 * Tests the behavior of the toString method
	 */
	public void toStringHashCodeEquals() {
		StateImpl state1 = new StateImpl(StateImplTest.name1, StateImplTest.id1);
		StateImpl state2 = new StateImpl(StateImplTest.name2, StateImplTest.id1);
		assertTrue(state1.equals(state2));
		StateImpl state3 = new StateImpl(StateImplTest.name1, StateImplTest.id2);
		assertFalse(state1.equals(state3));
		assertFalse(state2.equals(state3));
	}
		

}
