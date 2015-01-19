/**
 * 
 */
package it.polimi.automata.state.impl;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import it.polimi.automata.state.State;

import org.junit.Test;

/**
 * @author claudiomenghi
 * 
 */
public class StateFactoryImplTest {

	private static final String name1 = "name";
	private static final String name2 = "name2";

	private static final String emptyName = "";
	private static final int id1 = 1;
	private static final int id2 = 2;

	/**
	 * Test method for
	 * {@link it.polimi.automata.state.impl.StateFactoryImpl#create()}.
	 */
	@Test
	public void testCreate() {
		StateFactoryImpl factory = new StateFactoryImpl();
		State state1 = factory.create();
		State state2 = factory.create();
		assertFalse(state1.equals(state2));
		assertTrue(state1.getName().equals(emptyName));
		assertTrue(state2.getName().equals(emptyName));
	}

	/**
	 * Test method for
	 * {@link it.polimi.automata.state.impl.StateFactoryImpl#create(java.lang.String)}
	 * .
	 */
	@Test
	public void testCreateString() {
		StateFactoryImpl factory = new StateFactoryImpl();
		State state1 = factory.create(StateFactoryImplTest.name1);
		State state2 = factory.create(StateFactoryImplTest.name1);
		assertFalse(state1.equals(state2));
		assertTrue(state1.getName().equals(StateFactoryImplTest.name1));
		assertTrue(state2.getName().equals(StateFactoryImplTest.name1));

	}

	/**
	 * Test method for
	 * {@link it.polimi.automata.state.impl.StateFactoryImpl#create(java.lang.String, int)}
	 * .
	 */
	@Test
	public void testCreateStringInt() {
		StateFactoryImpl factory = new StateFactoryImpl();
		State state1 = factory.create(StateFactoryImplTest.name1,
				StateFactoryImplTest.id1);
		State state2 = factory.create(StateFactoryImplTest.name2,
				StateFactoryImplTest.id2);
		assertFalse(state1.equals(state2));
		assertTrue(state1.getName().equals(StateFactoryImplTest.name1));
		assertTrue(state2.getName().equals(StateFactoryImplTest.name2));
	}
}
