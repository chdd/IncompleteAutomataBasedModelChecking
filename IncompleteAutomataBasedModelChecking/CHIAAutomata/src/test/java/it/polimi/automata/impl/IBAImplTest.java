/**
 * 
 */
package it.polimi.automata.impl;

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
import it.polimi.automata.transition.impl.TransitionFactoryClaimImpl;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import rwth.i2.ltl2ba4j.model.IGraphProposition;

/**
 * @author claudiomenghi
 *
 */
public class IBAImplTest {


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
	
	private IBAImpl<State, Transition> ba;
	private IBAImpl<State, Transition> baInject;

	private Map<State, Set<Entry<Transition, State >>> inEntry;
	private Map<State, Set<Entry<Transition, State >>> outEntry;
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		this.ba = new IBAImpl<State, Transition>(new TransitionFactoryClaimImpl<State>());
		ba.addInitialState(state1);
		ba.addState(state2);
		ba.addAcceptState(state3);
		this.ba.addCharacter(l1);
		this.ba.addCharacter(l2);
		this.ba.addTransition(state1, state2, t1);
		this.ba.addTransition(state2, state3, t2);
		this.ba.addTransparentState(state2);
		
		Set<IGraphProposition> returnSet=new HashSet<IGraphProposition>();
		returnSet.add(l3);
		when(t3.getPropositions()).thenReturn(returnSet);
		
		this.baInject = new IBAImpl<State, Transition>(new TransitionFactoryClaimImpl<State>());
		baInject.addInitialState(state1Inject);
		baInject.addState(state2Inject);
		baInject.addAcceptState(state3Inject);
		this.baInject.addCharacter(l1);
		this.baInject.addCharacter(l2);
		this.baInject.addTransition(state1Inject, state2Inject, t1Inject);
		this.baInject.addTransition(state2Inject, state3Inject, t2Inject);
		this.baInject.addTransparentState(state2Inject);
		
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
	 * Test method for {@link it.polimi.automata.impl.IBAImpl#IBAImpl()}.
	 */
	@Test
	public void testIBAImpl() {
		assertNotNull(new IBAImpl<State, Transition>(new TransitionFactoryClaimImpl<State>()));
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
		IBAImpl<State, Transition> clone=this.ba.clone();
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
	 * Test method for {@link it.polimi.automata.impl.IBAImpl#replace(null, it.polimi.automata.IBA, java.util.Map, java.util.Map)}.
	 */
	@Test(expected=NullPointerException.class)
	public void testReplaceNullTransparentState() {
		this.ba.replace(null, this.baInject, this.inEntry, this.outEntry);
	}
	
	/**
	 * Test method for {@link it.polimi.automata.impl.IBAImpl#replace(it.polimi.automata.state.State, null, java.util.Map, java.util.Map)}.
	 */
	@Test(expected=NullPointerException.class)
	public void testReplaceNullIBA() {
		this.ba.replace(this.state2, null, this.inEntry, this.outEntry);
	}
	
	/**
	 * Test method for {@link it.polimi.automata.impl.IBAImpl#replace(it.polimi.automata.state.State, it.polimi.automata.IBA, null, java.util.Map)}.
	 */
	@Test(expected=NullPointerException.class)
	public void testReplaceNullIncoming() {
		this.ba.replace(this.state2, this.baInject, null, this.outEntry);
	}
	
	/**
	 * Test method for {@link it.polimi.automata.impl.IBAImpl#replace(it.polimi.automata.state.State, it.polimi.automata.IBA, java.util.Map, null)}.
	 */
	@Test(expected=NullPointerException.class)
	public void testReplaceNullOutcoming() {
		this.ba.replace(this.state2, this.baInject, this.inEntry, null);
	}
	
	/**
	 * Test method for {@link it.polimi.automata.impl.IBAImpl#replace(it.polimi.automata.state.State, it.polimi.automata.IBA, java.util.Map, java.util.Map)}.
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testReplaceNotTransparent() {
		this.ba.replace(this.state1, this.baInject, this.inEntry, this.outEntry);
	}
	
	/**
	 * Test method for {@link it.polimi.automata.impl.IBAImpl#replace(it.polimi.automata.state.State, it.polimi.automata.IBA, IllegalMap, java.util.Map)}.
	 * when one of the NEW incoming connection does not correspond to an old incoming connection
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testReplaceInvalidIncoming() {
		Set<Entry<Transition, State>> incomingTransition=new HashSet<Entry<Transition, State>>();
		incomingTransition.add(new AbstractMap.SimpleEntry<Transition, State>(inConnection1, state1Inject));
		inEntry.put(state3, incomingTransition);
		this.ba.replace(this.state2, this.baInject, this.inEntry, this.outEntry);
	}
	
	
	/**
	 * Test method for {@link it.polimi.automata.impl.IBAImpl#replace(it.polimi.automata.state.State, it.polimi.automata.IBA, IllegalMap, java.util.Map)}.
	 * when the destination of the NEW incoming connection does not correspond to an initial state of the BA to be injected
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testReplaceInvalidIcomingFinal() {
		Set<Entry<Transition, State>> incomingTransition=new HashSet<Entry<Transition, State>>();
		incomingTransition.add(new AbstractMap.SimpleEntry<Transition, State>(inConnection1, state2Inject));
		inEntry.put(state1, incomingTransition);
		this.ba.replace(this.state2, this.baInject, this.inEntry, this.outEntry);
	}
	
	/**
	 * Test method for {@link it.polimi.automata.impl.IBAImpl#replace(it.polimi.automata.state.State, it.polimi.automata.IBA, java.util.Map, IllegalMap)}.
	 * when one of the NEW out-coming connection does not correspond to an old out-coming connection
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testReplaceInvalidOutcoming() {
		Set<Entry<Transition, State>> outcomingTransition=new HashSet<Entry<Transition, State>>();
		outcomingTransition.add(new AbstractMap.SimpleEntry<Transition, State>(outConnection2, state1));
		outEntry.put(state3Inject, outcomingTransition);
		this.ba.replace(this.state2, this.baInject, this.inEntry, this.outEntry);
	}
	
	/**
	 * Test method for {@link it.polimi.automata.impl.IBAImpl#replace(it.polimi.automata.state.State, it.polimi.automata.IBA, java.util.Map, IllegalMap)}.
	 * when the initial state of the NEW out-coming connection does not correspond to a final state of the refinement
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testReplaceOutcomingInitial() {
		Set<Entry<Transition, State>> outcomingTransition=new HashSet<Entry<Transition, State>>();
		outcomingTransition.add(new AbstractMap.SimpleEntry<Transition, State>(outConnection2, state3));
		outEntry.put(state2Inject, outcomingTransition);
		this.ba.replace(this.state2, this.baInject, this.inEntry, this.outEntry);
	}
	
	
	/**
	 * Test method for {@link it.polimi.automata.impl.IBAImpl#replace(it.polimi.automata.state.State, it.polimi.automata.IBA, java.util.Map, java.util.Map)}.
	 */
	@Test
	public void testReplace() {
		
		IBA<State, Transition> refinement=this.ba.replace(this.state2, this.baInject, this.inEntry, this.outEntry);
		assertTrue(refinement.getStates().contains(state1));
		assertFalse(refinement.getStates().contains(state2));
		assertTrue(refinement.getStates().contains(state3));
		assertTrue(refinement.getStates().contains(state1Inject));
		assertTrue(refinement.getStates().contains(state2Inject));
		assertTrue(refinement.getStates().contains(state3Inject));
		
		assertTrue(refinement.getInitialStates().contains(state1));
		assertFalse(refinement.getInitialStates().contains(state2));
		assertFalse(refinement.getInitialStates().contains(state3));
		assertFalse(refinement.getInitialStates().contains(state1Inject));
		assertFalse(refinement.getInitialStates().contains(state2Inject));
		assertFalse(refinement.getInitialStates().contains(state3Inject));
		
		assertTrue(refinement.getAcceptStates().contains(state3));
		assertFalse(refinement.getAcceptStates().contains(state2));
		assertFalse(refinement.getAcceptStates().contains(state1));
		assertFalse(refinement.getAcceptStates().contains(state1Inject));
		assertFalse(refinement.getAcceptStates().contains(state2Inject));
		assertFalse(refinement.getAcceptStates().contains(state3Inject));
		
		assertFalse(refinement.getTransitions().contains(t1));
		assertFalse(refinement.getTransitions().contains(t2));
		assertFalse(refinement.getTransitions().contains(t3));
		assertTrue(refinement.getTransitions().contains(t1Inject));
		assertTrue(refinement.getTransitions().contains(t2Inject));
		assertTrue(refinement.getTransitions().contains(inConnection1));
		assertTrue(refinement.getTransitions().contains(outConnection2));
		
		assertTrue(refinement.getTransparentStates().contains(state2Inject));
		
		assertTrue(refinement.getOutTransitions(state1).contains(inConnection1));
		assertTrue(refinement.getTransitionDestination(inConnection1).equals(state1Inject));
		
		assertTrue(refinement.getOutTransitions(state1Inject).contains(t1Inject));
		assertTrue(refinement.getTransitionDestination(t1Inject).equals(state2Inject));
		
		assertTrue(refinement.getOutTransitions(state2Inject).contains(t2Inject));
		assertTrue(refinement.getTransitionDestination(t2Inject).equals(state3Inject));
		
		assertTrue(refinement.getOutTransitions(state3Inject).contains(outConnection2));
		assertTrue(refinement.getTransitionDestination(outConnection2).equals(state3));
		
	}
	
	
	/**
	 * Test method for {@link it.polimi.automata.impl.IBAImpl#replace(it.polimi.automata.state.State, it.polimi.automata.IBA, java.util.Map, java.util.Map)}.
	 * performs the same test but considers the state state2 as accepting
	 */
	@Test
	public void testReplace2() {
		
		this.ba.addAcceptState(state2);
		IBA<State, Transition> refinement=this.ba.replace(this.state2, this.baInject, this.inEntry, this.outEntry);
		assertTrue(refinement.getStates().contains(state1));
		assertFalse(refinement.getStates().contains(state2));
		assertTrue(refinement.getStates().contains(state3));
		assertTrue(refinement.getStates().contains(state1Inject));
		assertTrue(refinement.getStates().contains(state2Inject));
		assertTrue(refinement.getStates().contains(state3Inject));
		
		assertTrue(refinement.getInitialStates().contains(state1));
		assertFalse(refinement.getInitialStates().contains(state2));
		assertFalse(refinement.getInitialStates().contains(state3));
		assertFalse(refinement.getInitialStates().contains(state1Inject));
		assertFalse(refinement.getInitialStates().contains(state2Inject));
		assertFalse(refinement.getInitialStates().contains(state3Inject));
		
		assertTrue(refinement.getAcceptStates().contains(state3));
		assertFalse(refinement.getAcceptStates().contains(state2));
		assertFalse(refinement.getAcceptStates().contains(state1));
		assertFalse(refinement.getAcceptStates().contains(state1Inject));
		assertFalse(refinement.getAcceptStates().contains(state2Inject));
		assertTrue(refinement.getAcceptStates().contains(state3Inject));
		
		assertFalse(refinement.getTransitions().contains(t1));
		assertFalse(refinement.getTransitions().contains(t2));
		assertFalse(refinement.getTransitions().contains(t3));
		assertTrue(refinement.getTransitions().contains(t1Inject));
		assertTrue(refinement.getTransitions().contains(t2Inject));
		assertTrue(refinement.getTransitions().contains(inConnection1));
		assertTrue(refinement.getTransitions().contains(outConnection2));
		
		assertTrue(refinement.getTransparentStates().contains(state2Inject));
		
		assertTrue(refinement.getOutTransitions(state1).contains(inConnection1));
		assertTrue(refinement.getTransitionDestination(inConnection1).equals(state1Inject));
		
		assertTrue(refinement.getOutTransitions(state1Inject).contains(t1Inject));
		assertTrue(refinement.getTransitionDestination(t1Inject).equals(state2Inject));
		
		assertTrue(refinement.getOutTransitions(state2Inject).contains(t2Inject));
		assertTrue(refinement.getTransitionDestination(t2Inject).equals(state3Inject));
		
		assertTrue(refinement.getOutTransitions(state3Inject).contains(outConnection2));
		assertTrue(refinement.getTransitionDestination(outConnection2).equals(state3));
		
	}
	
	/**
	 * Test method for {@link it.polimi.automata.impl.IBAImpl#replace(it.polimi.automata.state.State, it.polimi.automata.IBA, java.util.Map, java.util.Map)}.
	 * performs the same test but considers the state state2 as initial
	 */
	@Test
	public void testReplace3() {
		
		this.ba.addAcceptState(state2);
		this.ba.addInitialState(state2);
		IBA<State, Transition> refinement=this.ba.replace(this.state2, this.baInject, this.inEntry, this.outEntry);
		assertTrue(refinement.getStates().contains(state1));
		assertFalse(refinement.getStates().contains(state2));
		assertTrue(refinement.getStates().contains(state3));
		assertTrue(refinement.getStates().contains(state1Inject));
		assertTrue(refinement.getStates().contains(state2Inject));
		assertTrue(refinement.getStates().contains(state3Inject));
		
		assertTrue(refinement.getInitialStates().contains(state1));
		assertFalse(refinement.getInitialStates().contains(state2));
		assertFalse(refinement.getInitialStates().contains(state3));
		assertTrue(refinement.getInitialStates().contains(state1Inject));
		assertFalse(refinement.getInitialStates().contains(state2Inject));
		assertFalse(refinement.getInitialStates().contains(state3Inject));
		
		assertTrue(refinement.getAcceptStates().contains(state3));
		assertFalse(refinement.getAcceptStates().contains(state2));
		assertFalse(refinement.getAcceptStates().contains(state1));
		assertFalse(refinement.getAcceptStates().contains(state1Inject));
		assertFalse(refinement.getAcceptStates().contains(state2Inject));
		assertTrue(refinement.getAcceptStates().contains(state3Inject));
		
		assertFalse(refinement.getTransitions().contains(t1));
		assertFalse(refinement.getTransitions().contains(t2));
		assertFalse(refinement.getTransitions().contains(t3));
		assertTrue(refinement.getTransitions().contains(t1Inject));
		assertTrue(refinement.getTransitions().contains(t2Inject));
		assertTrue(refinement.getTransitions().contains(inConnection1));
		assertTrue(refinement.getTransitions().contains(outConnection2));
		
		assertTrue(refinement.getTransparentStates().contains(state2Inject));
		
		assertTrue(refinement.getOutTransitions(state1).contains(inConnection1));
		assertTrue(refinement.getTransitionDestination(inConnection1).equals(state1Inject));
		
		assertTrue(refinement.getOutTransitions(state1Inject).contains(t1Inject));
		assertTrue(refinement.getTransitionDestination(t1Inject).equals(state2Inject));
		
		assertTrue(refinement.getOutTransitions(state2Inject).contains(t2Inject));
		assertTrue(refinement.getTransitionDestination(t2Inject).equals(state3Inject));
		
		assertTrue(refinement.getOutTransitions(state3Inject).contains(outConnection2));
		assertTrue(refinement.getTransitionDestination(outConnection2).equals(state3));
		
	}


}
