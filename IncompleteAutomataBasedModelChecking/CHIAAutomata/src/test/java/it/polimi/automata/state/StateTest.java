package it.polimi.automata.state;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

/**
 * @author claudiomenghi
 * 
 */
public class StateTest {

	private static final String stateName = "name";
	private static final int stateNumber = 1;

	private State state1;
	private State state2;
	private State state3;

	private State state4;
	private State state5;
	private State state6;

	@Before
	public void setUp() {
		this.state1 = new State(1);
		this.state2 = new State(2);
		this.state3 = new State(2);

		this.state4 = new State("name1", 3);
		this.state5 = new State("name1", 4);
		this.state6 = new State("name2", 4);

	}

	/**
	 * Test method for {@link it.polimi.automata.state.State#State()}.
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testStateConstructor() {
		new State(-1);
	}
	/**
	 * Test method for {@link it.polimi.automata.state.State#State()}.
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testStateConstuctor() {
		new State(stateName, -1);
	}
	
	
	
	/**
	 * Test method for {@link it.polimi.automata.state.State#hashCode()}.
	 */
	@Test
	public void testHashCode() {
		assertEquals(this.state2.hashCode(), this.state3.hashCode());
		assertEquals(this.state5.hashCode(), this.state6.hashCode());
		// note that the hashcode function only considers id of the states
		assertFalse(this.state1.hashCode() == this.state2.hashCode());
		assertFalse(this.state4.hashCode() == this.state5.hashCode());
	}

	/**
	 * Test method for {@link it.polimi.automata.state.State#getId()}.
	 */
	@Test
	public void testGetId() {
		assertEquals(this.state2.getId(), this.state3.getId());
		assertEquals(this.state5.getId(), this.state6.getId());
		// note that the hashcode function only considers id of the states
		assertFalse(this.state1.getId() == this.state2.getId());
		assertFalse(this.state4.getId() == this.state5.getId());

	}

	/**
	 * Test method for {@link it.polimi.automata.state.State#getName()} .
	 */
	@Test
	public void testGetName() {
		assertEquals(this.state2.getName(), this.state3.getName());
		assertEquals(this.state2.getName(), "");
		assertEquals(this.state4.getName(), this.state5.getName());
		assertEquals(this.state1.getName(), this.state2.getName());
		assertFalse(this.state5.getName() == this.state6.getName());
	}

	/**
	 * Test method for {@link it.polimi.automata.state.State#StateImpl(int)}.
	 */
	@Test
	public void testStateImplInt() {
		this.state4 = new State(6);
		assertEquals(6, this.state4.getId());
		assertEquals("", this.state4.getName());

	}

	/**
	 * Test method for {@link
	 * it.polimi.automata.state.impl.StateImpl#StateImpl(-1)}.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testStateImplInt_NegativeNumber() {
		new State(-1);
	}

	/**
	 * Test method for
	 * {@link it.polimi.automata.state.State#StateImpl(java.lang.String, int)} .
	 */
	@Test
	public void testStateImplStringInt() {
		this.state4 = new State("name", 6);
		assertEquals(6, this.state4.getId());
		assertEquals("name", this.state4.getName());
	}

	/**
	 * Test method for
	 * {@link it.polimi.automata.state.State#StateImpl(java.lang.String, null)}
	 * .
	 */
	@Test(expected = NullPointerException.class)
	public void testStateImplStringInt_null() {
		new State(null, 6);
	}

	/**
	 * Test method for {@link it.polimi.automata.state.State#toString()}.
	 */
	@Test
	public void testToString() {
		assertEquals(this.state3.toString(), this.state3.getId() + ": "
				+ this.state3.getName());
	}

	/**
	 * Test method for
	 * {@link it.polimi.automata.state.State#equals(java.lang.Object)}.
	 */
	@Test
	public void testEqualsObject() {
		assertEquals(this.state2, this.state3);
		assertFalse(this.state2.equals(this.state1));
		assertEquals(this.state5, this.state6);
	}

	/**
	 * Test method for
	 * {@link it.polimi.automata.state.State#equals(java.lang.Object)}.
	 */
	@Test
	public void testEqualsObject_Other() {
		assertFalse(this.state2.equals(new String()));
	}

	/**
	 * Test method for
	 * {@link it.polimi.automata.state.State#equals(java.lang.Object)}.
	 */
	@Test
	public void testEqualsObject_Same() {
		assertTrue(this.state2.equals(this.state2));
	}

	/**
	 * Test method for
	 * {@link it.polimi.automata.state.State#equals(java.lang.Object)}.
	 */
	@Test
	public void testEqualsObject_Null() {
		assertFalse(this.state2.equals(null));
	}

	/**
	 * Test method for {@link it.polimi.automata.state.State#clone()}.
	 */
	@Test
	public void testClone() {
		State state = new State(stateName, stateNumber);
		State clonedState = state.clone();
		assertEquals(
				"The name of the clone state must be equal to the name of the cloned state",
				stateName, clonedState.getName());
		assertEquals(
				"The id of the clone state must be equal to the id of the cloned state",
				stateNumber, clonedState.getId());
	}
}
