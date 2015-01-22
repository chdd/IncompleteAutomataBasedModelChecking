/**
 * 
 */
package it.polimi.automata.impl;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.Set;

import it.polimi.automata.labeling.Label;
import it.polimi.automata.state.State;
import it.polimi.automata.transition.Transition;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

/**
 * @author claudiomenghi
 *
 */
public class IBAImplTest {


	@Mock
	private State state1;

	@Mock
	private State state2;

	@Mock
	private State state3;

	@Mock
	private State state4;

	@Mock
	private Label l1;

	@Mock
	private Label l2;

	@Mock
	private Label l3;

	@Mock
	private Transition<Label> t1;

	@Mock
	private Transition<Label> t2;

	@Mock
	private Transition<Label> t3;

	private IBAImpl<Label, State, Transition<Label>> ba;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		this.ba = new IBAImpl<Label, State, Transition<Label>>();
		ba.addInitialState(state1);
		ba.addState(state2);
		ba.addAcceptState(state3);
		this.ba.addCharacter(l1);
		this.ba.addCharacter(l2);
		this.ba.addTransition(state1, state2, t1);
		this.ba.addTransition(state2, state3, t2);
		this.ba.addTransparentState(state2);
		Set<Label> returnSet=new HashSet<Label>();
		returnSet.add(l3);
		when(t3.getLabels()).thenReturn(returnSet);
	}
	
	/**
	 * Test method for {@link it.polimi.automata.impl.IBAImpl#IBAImpl()}.
	 */
	@Test
	public void testIBAImpl() {
		assertNotNull(new IBAImpl<Label, State, Transition<Label>>());
	}

	/**
	 * Test method for {@link it.polimi.automata.impl.IBAImpl#isTransparent(Null)}.
	 */
	@Test(expected=NullPointerException.class)
	public void testIsTransparentNull() {
		this.ba.isTransparent(null);
		
	}
	/**
	 * Test method for {@link it.polimi.automata.impl.IBAImpl#isTransparent(Illegal)}.
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testIsTransparentIllegal() {
		this.ba.isTransparent(state4);
		
	}
	
	/**
	 * Test method for {@link it.polimi.automata.impl.IBAImpl#isTransparent(it.polimi.automata.state.State)}.
	 */
	@Test
	public void testIsTransparent() {
		assertTrue(this.ba.isTransparent(state2));
		assertFalse(this.ba.isTransparent(state1));
		assertFalse(this.ba.isTransparent(state3));
	}

	/**
	 * Test method for {@link it.polimi.automata.impl.IBAImpl#getTransparentStates()}.
	 */
	@Test
	public void testGetTransparentStates() {
		assertTrue(this.ba.getTransparentStates().contains(state2));
		assertFalse(this.ba.getTransparentStates().contains(state1));
		assertFalse(this.ba.getTransparentStates().contains(state3));
	}

	/**
	 * Test method for {@link it.polimi.automata.impl.IBAImpl#addTransparentState(null)}.
	 */
	@Test(expected=NullPointerException.class)
	public void testAddTransparentStateNull() {
		this.ba.addTransparentState(null);
	}
	
	/**
	 * Test method for {@link it.polimi.automata.impl.IBAImpl#addTransparentState(it.polimi.automata.state.State)}.
	 */
	@Test
	public void testAddTransparentState() {
		this.ba.addTransparentState(state4);
		assertTrue(this.ba.getTransparentStates().contains(state2));
		assertTrue(this.ba.getTransparentStates().contains(state4));
		assertFalse(this.ba.getTransparentStates().contains(state1));
		assertFalse(this.ba.getTransparentStates().contains(state3));
		assertTrue(this.ba.getStates().contains(state1));
		assertTrue(this.ba.getStates().contains(state2));
		assertTrue(this.ba.getStates().contains(state3));
		assertTrue(this.ba.getStates().contains(state4));
	}

	/**
	 * Test method for {@link it.polimi.automata.impl.IBAImpl#clone()}.
	 */
	@Test
	public void testClone() {
		IBAImpl<Label, State, Transition<Label>> clone=this.ba.clone();
		assertEquals(clone.getAlphabet(), this.ba.getAlphabet());
		assertEquals(clone.getTransitions(), this.ba.getTransitions());
		assertEquals(clone.getStates(), this.ba.getStates());
		assertEquals(clone.getInitialStates(), this.ba.getInitialStates());
		assertEquals(clone.getAcceptStates(), this.ba.getAcceptStates());
		assertEquals(clone.getOutTransitions(state1), this.ba.getOutTransitions(state1));
		assertEquals(clone.getOutTransitions(state2), this.ba.getOutTransitions(state2));
		assertEquals(clone.getInTransitions(state2), this.ba.getInTransitions(state2));
	}

	/**
	 * Test method for {@link it.polimi.automata.impl.IBAImpl#replace(it.polimi.automata.state.State, it.polimi.automata.IBA, java.util.Map, java.util.Map)}.
	 */
	@Test
	public void testReplace() {
		//TODO
	}

}
