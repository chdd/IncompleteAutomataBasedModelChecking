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
	
	private IBA ba;
	private IBA baInject;

	private Map<State, Set<Entry<Transition, State >>> inEntry;
	private Map<State, Set<Entry<Transition, State >>> outEntry;
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		this.ba = new IBA(new ClaimTransitionFactory());
		ba.addInitialState(state1);
		ba.addState(state2);
		ba.addAcceptState(state3);
		this.ba.addProposition(l1);
		this.ba.addProposition(l2);
		this.ba.addTransition(state1, state2, t1);
		this.ba.addTransition(state2, state3, t2);
		this.ba.addBlackBoxState(state2);
		
		Set<IGraphProposition> returnSet=new HashSet<IGraphProposition>();
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
		
		Set<Entry<Transition, State>> incomingTransition=new HashSet<Entry<Transition, State>>();
		incomingTransition.add(new AbstractMap.SimpleEntry<Transition, State>(inConnection1, state1Inject));
		inEntry=new HashMap<State, Set<Entry<Transition,State>>>();
		inEntry.put(state1, incomingTransition);
		
		Set<Entry<Transition, State>> outcomingTransition=new HashSet<Entry<Transition, State>>();
		outcomingTransition.add(new AbstractMap.SimpleEntry<Transition, State>(outConnection2, state3));
		outEntry=new HashMap<State, Set<Entry<Transition,State>>>();
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
	@Test(expected=NullPointerException.class)
	public void testIsBlackBoxStateNull() {
		this.ba.isBlackBox(null);
		
	}
	/**
	 * Test method for {@link it.polimi.automata.IBA#isBlackBox(Illegal)}.
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testIsBlackBoxStateIllegal() {
		this.ba.isBlackBox(state4);
		
	}
	
	/**
	 * Test method for {@link it.polimi.automata.IBA#isBlackBox(it.polimi.automata.state.State)}.
	 */
	@Test
	public void testIsBlackBoxState() {
		assertTrue(this.ba.isBlackBox(state2));
		assertFalse(this.ba.isBlackBox(state1));
		assertFalse(this.ba.isBlackBox(state3));
	}

	/**
	 * Test method for {@link it.polimi.automata.IBA#getBlackBoxStates()}.
	 */
	@Test
	public void testGetBlackBoxStateStates() {
		assertTrue(this.ba.getBlackBoxStates().contains(state2));
		assertFalse(this.ba.getBlackBoxStates().contains(state1));
		assertFalse(this.ba.getBlackBoxStates().contains(state3));
	}

	/**
	 * Test method for {@link it.polimi.automata.IBA#addBlackBoxState(null)}.
	 */
	@Test(expected=NullPointerException.class)
	public void testAddBlackBoxStateStateNull() {
		this.ba.addBlackBoxState(null);
	}
	
	/**
	 * Test method for {@link it.polimi.automata.IBA#addBlackBoxState(it.polimi.automata.state.State)}.
	 */
	@Test
	public void testAddBlackBoxStateState() {
		this.ba.addBlackBoxState(state4);
		assertTrue(this.ba.getBlackBoxStates().contains(state2));
		assertTrue(this.ba.getBlackBoxStates().contains(state4));
		assertFalse(this.ba.getBlackBoxStates().contains(state1));
		assertFalse(this.ba.getBlackBoxStates().contains(state3));
		assertTrue(this.ba.getStates().contains(state1));
		assertTrue(this.ba.getStates().contains(state2));
		assertTrue(this.ba.getStates().contains(state3));
		assertTrue(this.ba.getStates().contains(state4));
	}

	/**
	 * Test method for {@link it.polimi.automata.IBA#clone()}.
	 */
	@Test
	public void testClone() {
		IBA clone=this.ba.clone();
		assertEquals(clone.getPropositions(), this.ba.getPropositions());
		assertEquals(clone.getTransitions(), this.ba.getTransitions());
		assertEquals(clone.getStates(), this.ba.getStates());
		assertEquals(clone.getInitialStates(), this.ba.getInitialStates());
		assertEquals(clone.getAcceptStates(), this.ba.getAcceptStates());
		assertEquals(clone.getOutTransitions(state1), this.ba.getOutTransitions(state1));
		assertEquals(clone.getOutTransitions(state2), this.ba.getOutTransitions(state2));
		assertEquals(clone.getInTransitions(state2), this.ba.getInTransitions(state2));
	}
}
