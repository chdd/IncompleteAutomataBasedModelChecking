/**
 * 
 */
package it.polimi.automata.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;
import it.polimi.automata.state.State;
import it.polimi.automata.transition.ClaimTransitionFactory;
import it.polimi.automata.transition.Transition;

import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import rwth.i2.ltl2ba4j.model.IGraphProposition;

/**
 * @author claudiomenghi
 * 
 */
public class IntBAImplTest {

	@Mock
	private State state1;

	@Mock
	private State state2;

	@Mock
	private State state3;

	@Mock
	private State state4;

	@Mock
	private IGraphProposition l1;

	@Mock
	private IGraphProposition l2;

	@Mock
	private IGraphProposition l3;

	@Mock
	private Transition t1;

	@Mock
	private Transition t2;

	@Mock
	private Transition t3;

	private IntersectionBA ba;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		this.ba = new IntersectionBA(new ClaimTransitionFactory<State>());
		ba.addInitialState(state1);
		ba.addState(state2);
		ba.addAcceptState(state3);
		ba.addMixedState(state3);
		this.ba.addProposition(l1);
		this.ba.addProposition(l2);
		this.ba.addTransition(state1, state2, t1);
		this.ba.addTransition(state2, state3, t2);
		Set<IGraphProposition> returnSet = new HashSet<IGraphProposition>();
		returnSet.add(l3);
		when(t3.getPropositions()).thenReturn(returnSet);
	}

	/**
	 * Test method for {@link it.polimi.automata.impl.IntersectionBA#IntBAImpl()}.
	 */
	@Test
	public void testIntBAImpl() {
		assertNotNull(new IntersectionBA(new ClaimTransitionFactory<State>()));
	}

	/**
	 * Test method for
	 * {@link it.polimi.automata.impl.IntersectionBA#addMixedState(null)}.
	 */
	@Test(expected = NullPointerException.class)
	public void testAddMixedStateNull() {
		this.ba.addMixedState(null);
	}

	/**
	 * Test method for
	 * {@link it.polimi.automata.impl.IntersectionBA#addMixedState(it.polimi.automata.state.State)}
	 * .
	 */
	@Test
	public void testAddMixedState() {
		this.ba.addMixedState(state1);
		assertTrue(this.ba.getStates().contains(state1));
		assertTrue(this.ba.getStates().contains(state2));
		assertTrue(this.ba.getStates().contains(state3));
		assertTrue(this.ba.getInitialStates().contains(state1));
		assertFalse(this.ba.getInitialStates().contains(state2));
		assertFalse(this.ba.getInitialStates().contains(state3));
		assertTrue(this.ba.getAcceptStates().contains(state3));
		assertFalse(this.ba.getAcceptStates().contains(state2));
		assertFalse(this.ba.getAcceptStates().contains(state1));
		assertTrue(this.ba.getMixedStates().contains(state1));
		assertFalse(this.ba.getMixedStates().contains(state2));
		assertTrue(this.ba.getMixedStates().contains(state3));
	}

	/**
	 * Test method for
	 * {@link it.polimi.automata.impl.IntersectionBA#getMixedStates()}.
	 */
	@Test
	public void testGetMixedStates() {
		this.ba.addMixedState(state1);
		assertTrue(this.ba.getMixedStates().contains(state1));
		assertTrue(this.ba.getMixedStates().contains(state3));
	}

	/**
	 * Test method for {@link it.polimi.automata.impl.IntersectionBA#clone()}.
	 */
	@Test
	public void testClone() {
		IntersectionBA clone = (IntersectionBA) this.ba.clone();
		assertEquals(clone.getPropositions(), this.ba.getPropositions());
		assertEquals(clone.getTransitions(), this.ba.getTransitions());
		assertEquals(clone.getStates(), this.ba.getStates());
		assertEquals(clone.getInitialStates(), this.ba.getInitialStates());
		assertEquals(clone.getAcceptStates(), this.ba.getAcceptStates());
		assertEquals(clone.getOutTransitions(state1),
				this.ba.getOutTransitions(state1));
		assertEquals(clone.getOutTransitions(state2),
				this.ba.getOutTransitions(state2));
		assertEquals(clone.getInTransitions(state2),
				this.ba.getInTransitions(state2));
		assertEquals(clone.getMixedStates(), this.ba.getMixedStates());
	}

}
