/**
 * 
 */
package it.polimi.automata.state;

import static org.junit.Assert.*;
import it.polimi.automata.state.State;
import it.polimi.automata.state.StateFactory;

import org.junit.Before;
import org.junit.Test;

/**
 * @author Claudio Menghi
 * 
 */
public class StateFactoryTest {

	private StateFactory stateFactory;

	/**
	 * creates a new StateFactory
	 */
	@Before
	public void setUp() {
		this.stateFactory = new StateFactory();
	}

	/**
	 * Test method for {@link it.polimi.automata.state.StateFactory#create()}.
	 */
	@Test
	public void testCreate() {
		State state = this.stateFactory.create();
		assertNotNull(state);
		assertEquals(state.getName(), "" + state.getId());
		assertTrue(state.getId() >= 0);
	}

	/**
	 * Test method for
	 * {@link it.polimi.automata.state.StateFactory#create(java.lang.String)} .
	 */
	@Test
	public void testCreateString() {
		State state = this.stateFactory.create("name");
		assertNotNull(state);
		assertEquals(state.getName(), "name");
		assertTrue(state.getId() >= 0);
	}

	/**
	 * Test method for
	 * {@link it.polimi.automata.state.StateFactory#createFromLabel(null)} .
	 */
	@Test(expected = NullPointerException.class)
	public void testCreateString_Null() {
		this.stateFactory.create(null);
	}

	/**
	 * Test method for
	 * {@link it.polimi.automata.state.StateFactory#create(java.lang.String, int)}
	 * .
	 */
	@Test
	public void testCreateStringInt() {
		State state = this.stateFactory.create("name", 5);
		assertNotNull(state);
		assertEquals(state.getName(), "name");
		assertEquals(state.getId(), 5);
	}

	/**
	 * Test method for
	 * {@link it.polimi.automata.state.StateFactory#create(null, int)} .
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

	@Test
	public void testCreateVertex() {
		assertNotNull(
				"The createVertex() method must return a not null referece",
				this.stateFactory.createVertex());
		assertTrue(
				"The id of the state created by the createVertex() method must be higher than or equal to zero",
				this.stateFactory.createVertex().getId() >= 0);
		State state=this.stateFactory.createVertex();
		assertTrue("The name of the state created must be equal to its id",
				Integer.toString(state.getId())
						.equals(state.getName()));

	}
}
