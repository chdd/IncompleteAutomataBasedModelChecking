package it.polimi.automata.state.impl;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

/**
 * @author claudiomenghi
 * 
 */
public class StateImplTest {

	private StateImpl state1;
	private StateImpl state2;
	private StateImpl state3;
	private StateImpl state4;
	private StateImpl state5;
	private StateImpl state6;

	@Before
	public void setUp() {
		this.state1 = new StateImpl(1);
		this.state2 = new StateImpl(2);
		this.state3 = new StateImpl(2);
		
		this.state4=new StateImpl("name1", 3);
		this.state5=new StateImpl("name1", 4);
		this.state6 = new StateImpl("name2", 4);
		
	}

	/**
	 * Test method for
	 * {@link it.polimi.automata.state.impl.StateImpl#hashCode()}.
	 */
	@Test
	public void testHashCode() {
		assertEquals(this.state2.hashCode(), this.state3.hashCode());
		assertEquals(this.state5.hashCode(), this.state6.hashCode());
		// note that the hashcode function only considers id of the states
		assertFalse(this.state1.hashCode()==this.state2.hashCode());
		assertFalse(this.state4.hashCode()==this.state5.hashCode());
	}

	/**
	 * Test method for {@link it.polimi.automata.state.impl.StateImpl#getId()}.
	 */
	@Test
	public void testGetId() {
		assertEquals(this.state2.getId(), this.state3.getId());
		assertEquals(this.state5.getId(), this.state6.getId());
		// note that the hashcode function only considers id of the states
		assertFalse(this.state1.getId()==this.state2.getId());
		assertFalse(this.state4.getId()==this.state5.getId());

	}

	/**
	 * Test method for {@link it.polimi.automata.state.impl.StateImpl#getName()}
	 * .
	 */
	@Test
	public void testGetName() {
		assertEquals(this.state2.getName(), this.state3.getName());
		assertEquals(this.state2.getName(), "");
		assertEquals(this.state4.getName(), this.state5.getName());
		assertEquals(this.state1.getName(), this.state2.getName());
		assertFalse(this.state5.getName()==this.state6.getName());
	}

	/**
	 * Test method for
	 * {@link it.polimi.automata.state.impl.StateImpl#StateImpl(int)}.
	 */
	@Test
	public void testStateImplInt() {
		this.state4=new StateImpl(6);
		assertEquals(6, this.state4.getId());
		assertEquals("", this.state4.getName());
		
	}
	
	/**
	 * Test method for
	 * {@link it.polimi.automata.state.impl.StateImpl#StateImpl(-1)}.
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testStateImplInt_NegativeNumber() {
		new StateImpl(-1);
	}

	/**
	 * Test method for
	 * {@link it.polimi.automata.state.impl.StateImpl#StateImpl(java.lang.String, int)}
	 * .
	 */
	@Test
	public void testStateImplStringInt() {
		this.state4=new StateImpl("name", 6);
		assertEquals(6, this.state4.getId());
		assertEquals("name", this.state4.getName());
	}
	/**
	 * Test method for
	 * {@link it.polimi.automata.state.impl.StateImpl#StateImpl(java.lang.String, null)}
	 * .
	 */
	@Test(expected=NullPointerException.class)
	public void testStateImplStringInt_null() {
		new StateImpl(null, 6);
	}

	/**
	 * Test method for
	 * {@link it.polimi.automata.state.impl.StateImpl#setName(java.lang.String)}
	 * .
	 */
	@Test
	public void testSetName() {
		this.state4.setName("prova");
		assertEquals("prova", this.state4.getName());
	}
	/**
	 * Test method for
	 * {@link it.polimi.automata.state.impl.StateImpl#setName(null)}
	 * .
	 */
	@Test(expected=NullPointerException.class)
	public void testSetName_null() {
		this.state4.setName(null);
	}


	/**
	 * Test method for
	 * {@link it.polimi.automata.state.impl.StateImpl#toString()}.
	 */
	@Test
	public void testToString() {
		assertEquals(this.state3.toString(), this.state3.getId()+": "+this.state3.getName());
	}

	/**
	 * Test method for
	 * {@link it.polimi.automata.state.impl.StateImpl#equals(java.lang.Object)}.
	 */
	@Test
	public void testEqualsObject() {
		assertEquals(this.state2 , this.state3);
		assertFalse(this.state2.equals(this.state1));
		assertEquals(this.state5 , this.state6);
	}
	
	/**
	 * Test method for
	 * {@link it.polimi.automata.state.impl.StateImpl#equals(java.lang.Object)}.
	 */
	@Test
	public void testEqualsObject_Other() {
		assertFalse(this.state2.equals(new String()));
	}
	
	/**
	 * Test method for
	 * {@link it.polimi.automata.state.impl.StateImpl#equals(java.lang.Object)}.
	 */
	@Test
	public void testEqualsObject_Same() {
		assertTrue(this.state2.equals(this.state2));
	}
	
	/**
	 * Test method for
	 * {@link it.polimi.automata.state.impl.StateImpl#equals(java.lang.Object)}.
	 */
	@Test
	public void testEqualsObject_Null() {
		assertFalse(this.state2.equals(null));
	}

}
