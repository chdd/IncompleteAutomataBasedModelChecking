/**
 * 
 */
package it.polimi.automata;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import it.polimi.automata.IBA;
import it.polimi.automata.state.State;
import it.polimi.automata.transition.Transition;
import it.polimi.automata.transition.ClaimTransitionFactory;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import rwth.i2.ltl2ba4j.model.IGraphProposition;

/**
 * @author claudiomenghi
 *
 */
public class IBATest {

	@Mock
	private State state1;

	@Mock
	private State state1Inject;

	@Mock
	private State state2;

	@Mock
	private State state2Inject;

	@Mock
	private State state3;

	@Mock
	private State state3Inject;

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

	@Mock
	private Transition t1Inject;

	@Mock
	private Transition t2Inject;

	@Mock
	private Transition t3Inject;

	@Mock
	private Transition inConnection1;

	@Mock
	private Transition inConnection2;

	@Mock
	private Transition outConnection1;

	@Mock
	private Transition outConnection2;

	private IBA iba;
	private IBA baInject;

	private Map<State, Set<Entry<Transition, State>>> inEntry;
	private Map<State, Set<Entry<Transition, State>>> outEntry;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		this.iba = new IBA(new ClaimTransitionFactory());
		iba.addInitialState(state1);
		iba.addState(state2);
		iba.addAcceptState(state3);
		this.iba.addProposition(l1);
		this.iba.addProposition(l2);
		this.iba.addTransition(state1, state2, t1);
		this.iba.addTransition(state2, state3, t2);
		this.iba.addBlackBoxState(state2);

		Set<IGraphProposition> returnSet = new HashSet<IGraphProposition>();
		returnSet.add(l3);
		when(t3.getPropositions()).thenReturn(returnSet);

		this.baInject = new IBA(new ClaimTransitionFactory());
		baInject.addInitialState(state1Inject);
		baInject.addState(state2Inject);
		baInject.addAcceptState(state3Inject);
		this.baInject.addProposition(l1);
		this.baInject.addProposition(l2);
		this.baInject.addTransition(state1Inject, state2Inject, t1Inject);
		this.baInject.addTransition(state2Inject, state3Inject, t2Inject);
		this.baInject.addBlackBoxState(state2Inject);

		Set<Entry<Transition, State>> incomingTransition = new HashSet<Entry<Transition, State>>();
		incomingTransition.add(new AbstractMap.SimpleEntry<Transition, State>(
				inConnection1, state1Inject));
		inEntry = new HashMap<State, Set<Entry<Transition, State>>>();
		inEntry.put(state1, incomingTransition);

		Set<Entry<Transition, State>> outcomingTransition = new HashSet<Entry<Transition, State>>();
		outcomingTransition.add(new AbstractMap.SimpleEntry<Transition, State>(
				outConnection2, state3));
		outEntry = new HashMap<State, Set<Entry<Transition, State>>>();
		outEntry.put(state3Inject, outcomingTransition);

	}

	/**
	 * Test method for {@link it.polimi.automata.IBA#IBAImpl()}.
	 */
	@Test
	public void testIBAImpl() {
		assertNotNull(new IBA(new ClaimTransitionFactory()));
	}

	/**
	 * Test method for {@link it.polimi.automata.IBA#isBlackBox(Null)}.
	 */
	@Test(expected = NullPointerException.class)
	public void testIsBlackBoxStateNull() {
		this.iba.isBlackBox(null);

	}

	/**
	 * Test method for {@link it.polimi.automata.IBA#isBlackBox(Illegal)}.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testIsBlackBoxStateIllegal() {
		this.iba.isBlackBox(state4);

	}

	/**
	 * Test method for
	 * {@link it.polimi.automata.IBA#isBlackBox(it.polimi.automata.state.State)}
	 * .
	 */
	@Test
	public void testIsBlackBoxState() {
		assertTrue(this.iba.isBlackBox(state2));
		assertFalse(this.iba.isBlackBox(state1));
		assertFalse(this.iba.isBlackBox(state3));
	}

	/**
	 * Test method for {@link it.polimi.automata.IBA#getBlackBoxStates()}.
	 */
	@Test
	public void testGetBlackBoxStateStates() {
		assertTrue(this.iba.getBlackBoxStates().contains(state2));
		assertFalse(this.iba.getBlackBoxStates().contains(state1));
		assertFalse(this.iba.getBlackBoxStates().contains(state3));
	}

	/**
	 * Test method for {@link it.polimi.automata.IBA#addBlackBoxState(null)}.
	 */
	@Test(expected = NullPointerException.class)
	public void testAddBlackBoxStateStateNull() {
		this.iba.addBlackBoxState(null);
	}

	/**
	 * Test method for
	 * {@link it.polimi.automata.IBA#addBlackBoxState(it.polimi.automata.state.State)}
	 * .
	 */
	@Test
	public void testAddBlackBoxStateState() {
		this.iba.addBlackBoxState(state4);
		assertTrue(this.iba.getBlackBoxStates().contains(state2));
		assertTrue(this.iba.getBlackBoxStates().contains(state4));
		assertFalse(this.iba.getBlackBoxStates().contains(state1));
		assertFalse(this.iba.getBlackBoxStates().contains(state3));
		assertTrue(this.iba.getStates().contains(state1));
		assertTrue(this.iba.getStates().contains(state2));
		assertTrue(this.iba.getStates().contains(state3));
		assertTrue(this.iba.getStates().contains(state4));
	}

	/**
	 * Test method for {@link it.polimi.automata.IBA#clone()}.
	 */
	@Test
	public void testClone() {
		IBA clone = this.iba.clone();
		assertEquals(clone.getPropositions(), this.iba.getPropositions());
		assertEquals(clone.getTransitions(), this.iba.getTransitions());
		assertEquals(clone.getStates(), this.iba.getStates());
		assertEquals(clone.getInitialStates(), this.iba.getInitialStates());
		assertEquals(clone.getAcceptStates(), this.iba.getAcceptStates());
		assertEquals(clone.getOutTransitions(state1),
				this.iba.getOutTransitions(state1));
		assertEquals(clone.getOutTransitions(state2),
				this.iba.getOutTransitions(state2));
		assertEquals(clone.getInTransitions(state2),
				this.iba.getInTransitions(state2));
	}

	/**
	 * Test method for {@link it.polimi.automata.IBA#getRegularStates()}.
	 */
	@Test
	public void testGetRegularStates() {
		assertEquals(
				"The method getRegularStates returns the correct number of regular states",
				2, this.iba.getRegularStates().size());
		Set<State> regularStates = new HashSet<State>();
		regularStates.add(state1);
		regularStates.add(state3);
		assertTrue(
				"The method getRegularStates returns the correct regular states",
				this.iba.getRegularStates().equals(regularStates));
	}

	/**
	 * Test method for {@link it.polimi.automata.IBA#testRemoveState()}.
	 */
	@Test
	public void testRemoveStateRegularState() {
		this.iba.removeState(state1);
		assertFalse("The removeState method correctly removes the regular states", this.iba
				.getRegularStates().contains(state1));
		assertFalse("The removeState method removes the state also from the set of initial states", this.iba
				.getInitialStates().contains(state1));
		assertTrue("The removeState method does not remove other states", this.iba
				.getInitialStates().size()==0);
		assertTrue("The removeState method does not remove other states", this.iba
				.getRegularStates().size()==1);
		assertTrue("The removeState method does not remove other states", this.iba
				.getBlackBoxStates().size()==1);
		assertTrue("The removeState method does not remove other states", this.iba
				.getBlackBoxStates().contains(state2));
		assertTrue("The removeState method does not remove other states", this.iba
				.getRegularStates().contains(state3));
	}
	
	/**
	 * Test method for {@link it.polimi.automata.IBA#testRemoveState()}.
	 */
	@Test
	public void testRemoveStateBlackBoxState() {
		this.iba.removeState(state2);
		assertFalse("The removeState method correctly removes the black box state", this.iba
				.getBlackBoxStates().contains(state2));
		assertFalse("The removeState method removes the state also from the set of the states of the automata", this.iba
				.getStates().contains(state2));
		assertTrue("The removeState method does not add states to the set of black box states", this.iba
				.getBlackBoxStates().size()==0);
		assertTrue("The removeState method does not remove other states", this.iba
				.getStates().size()==2);
		assertTrue("The removeState method does not remove other states", this.iba
				.getBlackBoxStates().size()==0);
		assertTrue("The removeState method does not remove other states", this.iba
				.getRegularStates().contains(state1));
		assertTrue("The removeState method does not remove other states", this.iba
				.getRegularStates().contains(state3));
		assertTrue("The removeState method does not remove other states", this.iba
				.getStates().contains(state3));
	}

	/**
	 * Test method for {@link it.polimi.automata.IBA#toString()}.
	 */
	@Test
	public void testToString() {
		assertNotNull(this.iba.toString());
	}
}
