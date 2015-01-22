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

import edu.uci.ics.jung.graph.util.EdgeType;

/**
 * @author claudiomenghi
 * 
 */
public class BAImplTest {

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

	private BAImpl<Label, State, Transition<Label>> ba;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		this.ba = new BAImpl<Label, State, Transition<Label>>();
		ba.addInitialState(state1);
		ba.addState(state2);
		ba.addAcceptState(state3);
		this.ba.addCharacter(l1);
		this.ba.addCharacter(l2);
		this.ba.addTransition(state1, state2, t1);
		this.ba.addTransition(state2, state3, t2);
		Set<Label> returnSet=new HashSet<Label>();
		returnSet.add(l3);
		when(t3.getLabels()).thenReturn(returnSet);
	}

	/**
	 * Test method for {@link it.polimi.automata.impl.BAImpl#BAImpl()}.
	 */
	@Test
	public void testBAImpl() {
		BAImpl<Label, State, Transition<Label>> ba = new BAImpl<Label, State, Transition<Label>>();
		assertNotNull(ba);
		assertNotNull(ba.getInitialStates());
		assertNotNull(ba.getAcceptStates());
		assertNotNull(ba.getAlphabet());
		assertTrue(ba.getInitialStates().isEmpty());
		assertTrue(ba.getAcceptStates().isEmpty());
		assertTrue(ba.getAlphabet().isEmpty());
	}

	/**
	 * Test method for {@link it.polimi.automata.impl.BAImpl#getInitialStates()}
	 * .
	 */
	@Test
	public void testGetInitialStates() {
		assertTrue(ba.getInitialStates().contains(state1));
		assertFalse(ba.getInitialStates().contains(state2));
		assertFalse(ba.getInitialStates().contains(state3));
	}

	/**
	 * Test method for {@link it.polimi.automata.impl.BAImpl#getStates()}.
	 */
	@Test
	public void testGetStates() {
		assertTrue(ba.getStates().contains(state1));
		assertTrue(ba.getStates().contains(state2));
		assertTrue(ba.getStates().contains(state3));
	}

	/**
	 * Test method for {@link it.polimi.automata.impl.BAImpl#getAcceptStates()}.
	 */
	@Test
	public void testGetAcceptStates() {
		assertTrue(ba.getAcceptStates().contains(state3));
		assertFalse(ba.getAcceptStates().contains(state2));
		assertFalse(ba.getAcceptStates().contains(state1));
	}

	/**
	 * Test method for {@link it.polimi.automata.impl.BAImpl#getAlphabet()}.
	 */
	@Test
	public void testGetAlphabet() {
		assertTrue(ba.getAlphabet().contains(l1));
		assertTrue(ba.getAlphabet().contains(l2));
		assertFalse(ba.getAlphabet().contains(l3));
	}

	/**
	 * Test method for
	 * {@link it.polimi.automata.impl.BAImpl#getOutTransitions(it.polimi.automata.state.State)}
	 * .
	 */
	@Test
	public void testGetOutTransitions() {
		assertTrue(ba.getOutTransitions(state1).contains(t1));
		assertFalse(ba.getOutTransitions(state1).contains(t2));
		assertTrue(ba.getOutTransitions(state3).isEmpty());
	}

	/**
	 * Test method for
	 * {@link it.polimi.automata.impl.BAImpl#getInTransitions(it.polimi.automata.state.State)}
	 * .
	 */
	@Test
	public void testGetInTransitions() {
		assertTrue(ba.getInTransitions(state2).contains(t1));
		assertFalse(ba.getInTransitions(state1).contains(t2));
		assertFalse(ba.getInTransitions(state1).contains(t1));
		assertTrue(ba.getInTransitions(state1).isEmpty());
	}

	/**
	 * Test method for
	 * {@link it.polimi.automata.impl.BAImpl#getTransitionDestination(it.polimi.automata.transition.Transition)}
	 * .
	 */
	@Test
	public void testGetTransitionDestination() {
		assertTrue(ba.getTransitionDestination(t1).equals(state2));
	}

	/**
	 * Test method for
	 * {@link it.polimi.automata.impl.BAImpl#getTransitionSource(it.polimi.automata.transition.Transition)}
	 * .
	 */
	@Test
	public void testGetTransitionSource() {
		assertTrue(ba.getTransitionSource(t1).equals(state1));
	}

	/**
	 * Test method for {@link it.polimi.automata.impl.BAImpl#getTransitions()}.
	 */
	@Test
	public void testGetTransitions() {
		assertTrue(ba.getTransitions().contains(t1));
		assertTrue(ba.getTransitions().contains(t2));
		assertFalse(ba.getTransitions().contains(t3));
	}

	/**
	 * Test method for
	 * {@link it.polimi.automata.impl.BAImpl#addInitialState(Null)}.
	 */
	@Test(expected = NullPointerException.class)
	public void testAddInitialStateNull() {
		this.ba.addInitialState(null);
	}

	/**
	 * Test method for
	 * {@link it.polimi.automata.impl.BAImpl#addInitialState(it.polimi.automata.state.State)}
	 * .
	 */
	@Test
	public void testAddInitialState() {
		this.ba.addInitialState(state3);
		assertTrue(this.ba.getInitialStates().contains(state3));

		this.ba.addInitialState(state4);
		assertTrue(this.ba.getInitialStates().contains(state4));
		assertTrue(this.ba.getStates().contains(state4));
	}

	/**
	 * Test method for
	 * {@link it.polimi.automata.impl.BAImpl#addAcceptState(null)}.
	 */
	@Test(expected = NullPointerException.class)
	public void testAddAcceptStateNull() {
		this.ba.addAcceptState(null);
	}

	/**
	 * Test method for
	 * {@link it.polimi.automata.impl.BAImpl#addAcceptState(it.polimi.automata.state.State)}
	 * .
	 */
	@Test
	public void testAddAcceptState() {
		this.ba.addAcceptState(state1);
		assertTrue(this.ba.getAcceptStates().contains(state1));

		this.ba.addAcceptState(state4);
		assertTrue(this.ba.getAcceptStates().contains(state4));
		assertTrue(this.ba.getStates().contains(state4));
	}

	/**
	 * Test method for {@link it.polimi.automata.impl.BAImpl#addState(null)}.
	 */
	@Test(expected = NullPointerException.class)
	public void testAddStateNull() {
		this.ba.addState(null);
	}

	/**
	 * Test method for
	 * {@link it.polimi.automata.impl.BAImpl#addState(it.polimi.automata.state.State)}
	 * .
	 */
	@Test
	public void testAddState() {
		this.ba.addState(state4);
		assertTrue(this.ba.getStates().contains(state4));
		assertTrue(this.ba.getStates().contains(state1));
		assertTrue(this.ba.getStates().contains(state2));
		assertTrue(this.ba.getStates().contains(state3));
		assertTrue(this.ba.getInitialStates().contains(state1));
		assertFalse(this.ba.getInitialStates().contains(state2));
		assertFalse(this.ba.getInitialStates().contains(state3));
		assertFalse(this.ba.getInitialStates().contains(state4));
		assertTrue(this.ba.getAcceptStates().contains(state3));
		assertFalse(this.ba.getAcceptStates().contains(state2));
		assertFalse(this.ba.getAcceptStates().contains(state4));
		assertFalse(this.ba.getAcceptStates().contains(state1));
	}

	/**
	 * Test method for {@link it.polimi.automata.impl.BAImpl#addCharacter(null)}
	 * .
	 */
	@Test(expected = NullPointerException.class)
	public void testAddCharacterNull() {
		this.ba.addCharacter(null);
	}

	/**
	 * Test method for
	 * {@link it.polimi.automata.impl.BAImpl#addCharacter(it.polimi.automata.labeling.Label)}
	 * .
	 */
	@Test
	public void testAddCharacter() {
		this.ba.addCharacter(l3);
		assertTrue(this.ba.getAlphabet().contains(l1));
		assertTrue(this.ba.getAlphabet().contains(l2));
		assertTrue(this.ba.getAlphabet().contains(l3));
	}

	/**
	 * Test method for
	 * {@link it.polimi.automata.impl.BAImpl#addTransition(null, it.polimi.automata.state.State, it.polimi.automata.transition.Transition)}
	 * .
	 */
	@Test(expected = NullPointerException.class)
	public void testAddTransitionSourceNull() {
		this.ba.addTransition(null, state3, t3);
	}

	/**
	 * Test method for
	 * {@link it.polimi.automata.impl.BAImpl#addTransition(it.polimi.automata.state.State, null, it.polimi.automata.transition.Transition)}
	 * .
	 */
	@Test(expected = NullPointerException.class)
	public void testAddTransitionDestinationNull() {
		this.ba.addTransition(state1, null, t3);
	}

	/**
	 * Test method for
	 * {@link it.polimi.automata.impl.BAImpl#addTransition(it.polimi.automata.state.State, it.polimi.automata.state.State, null)}
	 * .
	 */
	@Test(expected = NullPointerException.class)
	public void testAddTransitionTransitionNull() {
		this.ba.addTransition(state1, state2, null);
	}

	/**
	 * Test method for
	 * {@link it.polimi.automata.impl.BAImpl#addTransition(it.polimi.automata.state.State, it.polimi.automata.state.State, it.polimi.automata.transition.Transition)}
	 * .
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testAddTransitionSourceNotPresent() {
		this.ba.addCharacter(l3);
		this.ba.addTransition(state4, state2, t3);
	}
	
	/**
	 * Test method for
	 * {@link it.polimi.automata.impl.BAImpl#addTransition(it.polimi.automata.state.State, it.polimi.automata.state.State, it.polimi.automata.transition.Transition)}
	 * .
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testAddTransitionDestinationNotPresent() {
		this.ba.addCharacter(l3);
		this.ba.addTransition(state2, state4, t3);
	}
	
	/**
	 * Test method for
	 * {@link it.polimi.automata.impl.BAImpl#addTransition(it.polimi.automata.state.State, it.polimi.automata.state.State, it.polimi.automata.transition.Transition)}
	 * .
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testAddTransitionCharacterNotContained() {
		this.ba.addTransition(state2, state4, t3);
	}
	
	/**
	 * Test method for
	 * {@link it.polimi.automata.impl.BAImpl#addTransition(it.polimi.automata.state.State, it.polimi.automata.state.State, AlreadyPresentTransitionFromSourceToDestination)}
	 * .
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testAddTransitionSourceDestinationTransitionAlreadyPresent() {
		this.ba.addCharacter(l3);
		this.ba.addTransition(state1, state2, t3);
	}
	
	/**
	 * Test method for
	 * {@link it.polimi.automata.impl.BAImpl#addTransition(it.polimi.automata.state.State, it.polimi.automata.state.State, it.polimi.automata.transition.Transition)}
	 * .
	 */
	@Test
	public void testAddTransition() {
		this.ba.addCharacter(l3);
		this.ba.addTransition(state3, state3, t3);
		assertTrue(this.ba.getOutTransitions(state3).contains(t3));
		assertTrue(this.ba.getInTransitions(state3).contains(t3));
		assertTrue(this.ba.getTransitionDestination(t3).equals(state3));
		assertTrue(this.ba.getTransitionSource(t3).equals(state3));
	}

	/**
	 * Test method for {@link it.polimi.automata.impl.BAImpl#removeState(null)}.
	 */
	@Test(expected = NullPointerException.class)
	public void testRemoveStateNull() {
		this.ba.removeState(null);
	}

	/**
	 * Test method for
	 * {@link it.polimi.automata.impl.BAImpl#removeState(IllegalArgument)}.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testRemoveStateIllegal() {
		this.ba.removeState(this.state4);
	}

	/**
	 * Test method for
	 * {@link it.polimi.automata.impl.BAImpl#removeState(it.polimi.automata.state.State)}
	 * .
	 */
	@Test
	public void testRemoveState() {
		this.ba.removeState(state2);
		assertFalse(this.ba.getStates().contains(state2));
		assertFalse(this.ba.getTransitions().contains(t1));
		assertFalse(this.ba.getTransitions().contains(t2));
		assertTrue(this.ba.getOutTransitions(state1).isEmpty());
		assertTrue(this.ba.getInTransitions(state3).isEmpty());
		assertTrue(this.ba.getStates().contains(state1));
		assertTrue(this.ba.getStates().contains(state3));
		this.ba.removeState(state1);
		assertFalse(this.ba.getInitialStates().contains(state1));
		assertTrue(this.ba.getStates().contains(state3));
		this.ba.removeState(state3);
		assertFalse(this.ba.getAcceptStates().contains(state1));

	}

	/**
	 * Test method for
	 * {@link it.polimi.automata.impl.BAImpl#removeTransition(null)}.
	 */
	@Test(expected = NullPointerException.class)
	public void testRemoveTransitionNull() {
		this.ba.removeTransition(null);
	}

	/**
	 * Test method for
	 * {@link it.polimi.automata.impl.BAImpl#removeTransition(IllegalTransition)}
	 * .
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testRemoveTransitionIllegalTransition() {
		this.ba.removeTransition(t3);
	}

	/**
	 * Test method for
	 * {@link it.polimi.automata.impl.BAImpl#removeTransition(it.polimi.automata.transition.Transition)}
	 * .
	 */
	@Test
	public void testRemoveTransition() {
		this.ba.removeTransition(t2);
		assertFalse(this.ba.getTransitions().contains(t2));
		assertFalse(this.ba.getOutTransitions(state1).contains(t2));
		assertFalse(this.ba.getInTransitions(state2).contains(t2));
		assertTrue(this.ba.getTransitions().contains(t1));

	}

	/**
	 * Test method for
	 * {@link it.polimi.automata.impl.BAImpl#removeAcceptingState(null)}.
	 */
	@Test(expected = NullPointerException.class)
	public void testRemoveAcceptingStateNull() {
		this.ba.removeAcceptingState(null);
	}

	/**
	 * Test method for
	 * {@link it.polimi.automata.impl.BAImpl#removeAcceptingState(IllegalState)}
	 * .
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testRemoveAcceptingStateIllegalArgument() {
		this.ba.removeAcceptingState(state4);
	}

	/**
	 * Test method for
	 * {@link it.polimi.automata.impl.BAImpl#removeAcceptingState(it.polimi.automata.state.State)}
	 * .
	 */
	@Test
	public void testRemoveAcceptingState() {
		this.ba.removeAcceptingState(state3);
		assertTrue(this.ba.getAcceptStates().isEmpty());
		assertTrue(this.ba.getStates().contains(state3));
	}

	/**
	 * Test method for
	 * {@link it.polimi.automata.impl.BAImpl#removeInitialState(null)}.
	 */
	@Test(expected = NullPointerException.class)
	public void testRemoveInitialStateNull() {
		this.ba.removeInitialState(null);
	}
	
	/**
	 * Test method for
	 * {@link it.polimi.automata.impl.BAImpl#removeInitialState(Illegal)}.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testRemoveInitialStateIllegal() {
		this.ba.removeInitialState(state2);
	}
	
	/**
	 * Test method for
	 * {@link it.polimi.automata.impl.BAImpl#removeInitialState(it.polimi.automata.state.State)}
	 * .
	 */
	@Test
	public void testRemoveInitialState() {
		this.ba.removeInitialState(state1);
		assertTrue(this.ba.getInitialStates().isEmpty());
		assertTrue(this.ba.getStates().contains(state1));
		assertTrue(this.ba.getStates().contains(state2));
		assertTrue(this.ba.getStates().contains(state3));
	}

	/**
	 * Test method for {@link it.polimi.automata.impl.BAImpl#getGraph()}.
	 */
	@Test
	public void testGetGraph() {
		assertNotNull(this.ba.getGraph());
	}

	/**
	 * Test method for
	 * {@link it.polimi.automata.impl.BAImpl#getDefaultEdgeType()}.
	 */
	@Test
	public void testGetDefaultEdgeType() {
		assertTrue(this.ba.getDefaultEdgeType().equals(EdgeType.DIRECTED));
	}

}
