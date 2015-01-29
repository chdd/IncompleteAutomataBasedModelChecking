/**
 * 
 */
package it.polimi.automata.state.impl;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

/**
 * @author claudiomenghi
 * 
 */
public class StateFactoryImplTest {

	private StateFactoryImpl stateFactory;

	/**
	 * creates a new StateFactory
	 */
	@Before
	public void setUp() {
		this.stateFactory = new StateFactoryImpl();
	}

	/**
	 * Test method for
	 * {@link it.polimi.automata.state.impl.StateFactoryImpl#create()}.
	 */
	@Test
	public void testCreate() {
		StateImpl state = this.stateFactory.create();
		assertNotNull(state);
		assertEquals(state.getName(), "");
		assertTrue(state.getId() >= 0);
	}

	/**
	 * Test method for
	 * {@link it.polimi.automata.state.impl.StateFactoryImpl#create(java.lang.String)}
	 * .
	 */
	@Test
	public void testCreateString() {
		StateImpl state = this.stateFactory.create("name");
		assertNotNull(state);
		assertEquals(state.getName(), "name");
		assertTrue(state.getId() >= 0);
	}

	/**
	 * Test method for
	 * {@link it.polimi.automata.state.impl.StateFactoryImpl#createFromLabel(null)} .
	 */
	@Test(expected = NullPointerException.class)
	public void testCreateString_Null() {
		this.stateFactory.create(null);
	}

	/**
	 * Test method for
	 * {@link it.polimi.automata.state.impl.StateFactoryImpl#create(java.lang.String, int)}
	 * .
	 */
	@Test
	public void testCreateStringInt() {
		StateImpl state = this.stateFactory.create("name", 5);
		assertNotNull(state);
		assertEquals(state.getName(), "name");
		assertEquals(state.getId(), 5);
	}

	/**
	 * Test method for
	 * {@link it.polimi.automata.state.impl.StateFactoryImpl#create(null, int)}
	 * .
	 */
	@Test(expected = NullPointerException.class)
	public void testCreateNullInt() {
		this.stateFactory.create(null, 5);
	}

	/**
	 * Test method for {@link
	 * it.polimi.automata.state.impl.StateFactoryImpl#create(java.lang.String,
	 * -1)} .
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testCreateStringNegInt() {
		this.stateFactory.create("name", -1);
	}

}
