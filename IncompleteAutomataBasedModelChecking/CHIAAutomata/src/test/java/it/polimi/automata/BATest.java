/**
 * 
 */
package it.polimi.automata;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.Set;

import it.polimi.automata.BA;
import it.polimi.automata.state.State;
import it.polimi.automata.state.StateFactory;
import it.polimi.automata.transition.PropositionalLogicConstants;
import it.polimi.automata.transition.Transition;
import it.polimi.automata.transition.TransitionFactory;
import it.polimi.automata.transition.ClaimTransitionFactory;

import org.jgrapht.DirectedGraph;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import rwth.i2.ltl2ba4j.model.IGraphProposition;
import rwth.i2.ltl2ba4j.model.impl.GraphProposition;

/**
 * @author claudiomenghi
 * 
 */
public class BATest {

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

	private BA ba;
	private TransitionFactory<State, Transition> transitioFactory;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		transitioFactory = new ClaimTransitionFactory();
		this.ba = new BA(transitioFactory);
		ba.addInitialState(state1);
		ba.addState(state2);
		ba.addAcceptState(state3);
		this.ba.addProposition(l1);
		this.ba.addProposition(l2);
		this.ba.addTransition(state1, state2, t1);
		this.ba.addTransition(state2, state3, t2);
		Set<IGraphProposition> returnSet = new HashSet<IGraphProposition>();
		returnSet.add(l3);
		when(t3.getPropositions()).thenReturn(returnSet);
	}

	/**
	 * Test method for {@link it.polimi.automata.BA#BAImpl()}.
	 */
	@Test
	public void testBAImpl() {
		BA ba = new BA(new ClaimTransitionFactory());
		assertNotNull(ba);
		assertNotNull(ba.getInitialStates());
		assertNotNull(ba.getAcceptStates());
		assertNotNull(ba.getPropositions());
		assertTrue(ba.getInitialStates().isEmpty());
		assertTrue(ba.getAcceptStates().isEmpty());
		assertTrue(ba.getPropositions().size() == 1);
	}

	/**
	 * Test method for {@link it.polimi.automata.BA#addProposition()}.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testAddProposition() {
		ba.addProposition(new GraphProposition("proposition", true));
	}

	/**
	 * Test method for {@link it.polimi.automata.BA#getPropositions()}.
	 */
	@Test
	public void testGetPropositions() {

		assertFalse(
				"The getPropositions method does not return propositions that are not contained in the alphabet ",
				ba.getPropositions().contains(
						new GraphProposition("NotContainedProposition", true)));
		
		assertFalse(
				"The getPropositions method does not return propositions that are not contained in the alphabet ",
				ba.getPropositions().contains(
						new GraphProposition("NotContainedProposition", false)));
	}

	/**
	 * Test method for {@link it.polimi.automata.BA#getInitialStates()} .
	 */
	@Test
	public void testGetInitialStates() {
		assertTrue(ba.getInitialStates().contains(state1));
		assertFalse(ba.getInitialStates().contains(state2));
		assertFalse(ba.getInitialStates().contains(state3));
	}

	/**
	 * Test method for {@link it.polimi.automata.BA#getStates()}.
	 */
	@Test
	public void testGetStates() {
		assertTrue(ba.getStates().contains(state1));
		assertTrue(ba.getStates().contains(state2));
		assertTrue(ba.getStates().contains(state3));
	}

	/**
	 * Test method for {@link it.polimi.automata.BA#getAcceptStates()}.
	 */
	@Test
	public void testGetAcceptStates() {
		assertTrue(ba.getAcceptStates().contains(state3));
		assertFalse(ba.getAcceptStates().contains(state2));
		assertFalse(ba.getAcceptStates().contains(state1));
	}

	/**
	 * Test method for {@link it.polimi.automata.BA#getPropositions()}.
	 */
	@Test
	public void testGetAlphabet() {
		assertTrue(ba.getPropositions().contains(l1));
		assertTrue(ba.getPropositions().contains(l2));
		assertFalse(ba.getPropositions().contains(l3));
	}

	/**
	 * Test method for
	 * {@link it.polimi.automata.BA#getOutTransitions(it.polimi.automata.state.State)}
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
	 * {@link it.polimi.automata.BA#getInTransitions(it.polimi.automata.state.State)}
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
	 * {@link it.polimi.automata.BA#getTransitionDestination(it.polimi.automata.transition.Transition)}
	 * .
	 */
	@Test
	public void testGetTransitionDestination() {
		assertTrue(ba.getTransitionDestination(t1).equals(state2));
	}

	/**
	 * Test method for
	 * {@link it.polimi.automata.BA#getTransitionSource(it.polimi.automata.transition.Transition)}
	 * .
	 */
	@Test
	public void testGetTransitionSource() {
		assertTrue(ba.getTransitionSource(t1).equals(state1));
	}

	/**
	 * Test method for {@link it.polimi.automata.BA#getTransitions()}.
	 */
	@Test
	public void testGetTransitions() {
		assertTrue(ba.getTransitions().contains(t1));
		assertTrue(ba.getTransitions().contains(t2));
		assertFalse(ba.getTransitions().contains(t3));
		ba.addState(state4);
		assertTrue(ba.getOutTransitions(state4).isEmpty());
	}

	/**
	 * Test method for {@link it.polimi.automata.BA#addInitialState(Null)}.
	 */
	@Test(expected = NullPointerException.class)
	public void testAddInitialStateNull() {
		this.ba.addInitialState(null);
	}

	/**
	 * Test method for
	 * {@link it.polimi.automata.BA#addInitialState(it.polimi.automata.state.State)}
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
	 * Test method for {@link it.polimi.automata.BA#addAcceptState(null)}.
	 */
	@Test(expected = NullPointerException.class)
	public void testAddAcceptStateNull() {
		this.ba.addAcceptState(null);
	}

	/**
	 * Test method for
	 * {@link it.polimi.automata.BA#addAcceptState(it.polimi.automata.state.State)}
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
	 * Test method for {@link it.polimi.automata.BA#addState(null)}.
	 */
	@Test(expected = NullPointerException.class)
	public void testAddStateNull() {
		this.ba.addState(null);
	}

	/**
	 * Test method for
	 * {@link it.polimi.automata.BA#addState(it.polimi.automata.state.State)} .
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
	 * Test method for
	 * {@link it.polimi.automata.BA#addState(it.polimi.automata.state.State)} .
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testAddStateAlreadyContainedState() {
		this.ba.addState(state3);
	}

	/**
	 * Test method for
	 * {@link it.polimi.automata.BA#getPredecessors(it.polimi.automata.state.State)}
	 * .
	 */
	@Test(expected = NullPointerException.class)
	public void testGetPredecessorsNullState() {
		this.ba.getPredecessors(null);
	}

	/**
	 * Test method for
	 * {@link it.polimi.automata.BA#getPredecessors(it.polimi.automata.state.State)}
	 * .
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testGetPredecessorsNotContainedState() {
		this.ba.getPredecessors(state4);
	}

	/**
	 * Test method for
	 * {@link it.polimi.automata.BA#getPredecessors(it.polimi.automata.labeling.Label)}
	 * .
	 */
	@Test
	public void testGetPredecessors() {

		assertEquals(
				"Getting the successor does not introduce additional states",
				1, this.ba.getPredecessors(state3).size());
		assertTrue("The successor returned are the correct successors", this.ba
				.getPredecessors(state3).contains(state2));
		assertTrue(
				"The successor of a state with no outgoing transition is the empty set",
				this.ba.getPredecessors(state1).isEmpty());
	}

	/**
	 * Test method for
	 * {@link it.polimi.automata.BA#isPredecessor(it.polimi.automata.state.State)}
	 * .
	 */
	@Test(expected = NullPointerException.class)
	public void testGetPredecessorNullSourceState() {
		this.ba.isPredecessor(null, state3);
	}

	/**
	 * Test method for
	 * {@link it.polimi.automata.BA#isPredecessor(it.polimi.automata.state.State)}
	 * .
	 */
	@Test(expected = NullPointerException.class)
	public void testGetPredecessorNullDestinationState() {
		this.ba.isPredecessor(state3, null);
	}

	/**
	 * Test method for
	 * {@link it.polimi.automata.BA#isPredecessor(it.polimi.automata.state.State)}
	 * .
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testGetPredecessorNotContainedSourceState() {
		this.ba.isPredecessor(state4, state3);
	}

	/**
	 * Test method for
	 * {@link it.polimi.automata.BA#isPredecessor(it.polimi.automata.state.State)}
	 * .
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testGetPredecessorNotContainedDestinationState() {
		this.ba.isPredecessor(state3, state4);
	}

	/**
	 * Test method for
	 * {@link it.polimi.automata.BA#isPredecessor(it.polimi.automata.state.State)}
	 * .
	 */
	@Test
	public void testGetPredecessor() {

		assertTrue(
				"The method returns true if the there exist a transition from the source to the destination",
				this.ba.isPredecessor(state2, state3));
		assertFalse(
				"The method returns false if it does not exist a transition from the source to the destination",
				this.ba.isPredecessor(state3, state2));
	}

	/**
	 * Test method for
	 * {@link it.polimi.automata.BA#getSuccessors(it.polimi.automata.state.State)}
	 * .
	 */
	@Test(expected = NullPointerException.class)
	public void testGetSuccessorsNullState() {
		this.ba.getSuccessors(null);
	}

	/**
	 * Test method for
	 * {@link it.polimi.automata.BA#getSuccessors(it.polimi.automata.state.State)}
	 * .
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testGetSuccessorsNotContainedState() {
		this.ba.getSuccessors(state4);
	}

	/**
	 * Test method for
	 * {@link it.polimi.automata.BA#getSuccessors(it.polimi.automata.labeling.Label)}
	 * .
	 */
	@Test
	public void testGetSuccessors() {
		assertEquals(
				"Getting the successor does not introduce additional states",
				1, this.ba.getSuccessors(state1).size());
		assertTrue("The successor returned are the correct successors", this.ba
				.getSuccessors(state1).contains(state2));
		assertTrue(
				"The successor of a state with no outgoing transition is the empty set",
				this.ba.getSuccessors(state3).isEmpty());
	}

	/**
	 * Test method for {@link it.polimi.automata.BA#addPropositions(null)} .
	 */
	@Test(expected = NullPointerException.class)
	public void testAddCharactersNull() {
		this.ba.addPropositions(null);
	}

	/**
	 * Test method for
	 * {@link it.polimi.automata.BA#addProposition(it.polimi.automata.labeling.Label)}
	 * .
	 */
	@Test
	public void testAddCharacters() {
		this.ba.addProposition(l3);
		assertTrue(this.ba.getPropositions().contains(l1));
		assertTrue(this.ba.getPropositions().contains(l2));
		assertTrue(this.ba.getPropositions().contains(l3));
	}

	/**
	 * Test method for
	 * {@link it.polimi.automata.BA#addTransition(null, it.polimi.automata.state.State, it.polimi.automata.transition.Transition)}
	 * .
	 */
	@Test(expected = NullPointerException.class)
	public void testAddTransitionSourceNull() {
		this.ba.addTransition(null, state3, t3);
	}

	/**
	 * Test method for
	 * {@link it.polimi.automata.BA#addTransition(it.polimi.automata.state.State, null, it.polimi.automata.transition.Transition)}
	 * .
	 */
	@Test(expected = NullPointerException.class)
	public void testAddTransitionDestinationNull() {
		this.ba.addTransition(state1, null, t3);
	}

	/**
	 * Test method for
	 * {@link it.polimi.automata.BA#addTransition(it.polimi.automata.state.State, it.polimi.automata.state.State, null)}
	 * .
	 */
	@Test(expected = NullPointerException.class)
	public void testAddTransitionTransitionNull() {
		this.ba.addTransition(state1, state2, null);
	}

	/**
	 * Test method for
	 * {@link it.polimi.automata.BA#addTransition(it.polimi.automata.state.State, it.polimi.automata.state.State, it.polimi.automata.transition.Transition)}
	 * .
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testAddTransitionSourceNotPresent() {
		this.ba.addProposition(l3);
		this.ba.addTransition(state4, state2, t3);
	}

	/**
	 * Test method for
	 * {@link it.polimi.automata.BA#addTransition(it.polimi.automata.state.State, it.polimi.automata.state.State, it.polimi.automata.transition.Transition)}
	 * .
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testAddTransitionAlreadyPresent() {
		this.ba.addTransition(state2, state3, t2);
	}

	/**
	 * Test method for
	 * {@link it.polimi.automata.BA#addTransition(it.polimi.automata.state.State, it.polimi.automata.state.State, it.polimi.automata.transition.Transition)}
	 * .
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testAddTransitionDestinationNotPresent() {
		this.ba.addProposition(l3);
		this.ba.addTransition(state2, state4, t3);
	}

	/**
	 * Test method for
	 * {@link it.polimi.automata.BA#addTransition(it.polimi.automata.state.State, it.polimi.automata.state.State, it.polimi.automata.transition.Transition)}
	 * .
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testAddTransitionLabeledWithNegatedPropositions() {

		Set<IGraphProposition> propositions = new HashSet<IGraphProposition>();
		propositions.add(new GraphProposition("NotContainedProposition", true));

		Transition transition = this.transitioFactory.create(propositions);
		this.ba.addTransition(state2, state3, transition);
	}
	
	/**
	 * Test method for
	 * {@link it.polimi.automata.BA#addTransition(it.polimi.automata.state.State, it.polimi.automata.state.State, it.polimi.automata.transition.Transition)}
	 * .
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testAddTransition_LabeledWithNegatedPropositions() {

		Set<IGraphProposition> propositions = new HashSet<IGraphProposition>();
		propositions.add(new GraphProposition("NotContainedProposition", true));

		Transition transition = this.transitioFactory.create(propositions);
		this.ba.addTransition(state2, state3, transition);
	}

	/**
	 * Test method for
	 * {@link it.polimi.automata.BA#addTransition(it.polimi.automata.state.State, it.polimi.automata.state.State, it.polimi.automata.transition.Transition)}
	 * .
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testAddTransitionCharacterNotContained() {
		this.ba.addTransition(state2, state4, t3);
	}

	/**
	 * Test method for
	 * {@link it.polimi.automata.BA#addTransition(it.polimi.automata.state.State, it.polimi.automata.state.State, it.polimi.automata.transition.Transition)}
	 * .
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testAddTransitionCharacterNotContained2() {
		Set<IGraphProposition> transitionLabels = new HashSet<IGraphProposition>();
		transitionLabels.add(new GraphProposition("NotContainedProposition",
				true));
		this.ba.addTransition(state2, state4,
				this.transitioFactory.create(transitionLabels));
	}

	/**
	 * Test method for
	 * {@link it.polimi.automata.BA#addTransition(it.polimi.automata.state.State, it.polimi.automata.state.State, it.polimi.automata.transition.Transition)}
	 * .
	 */
	@Test
	public void testAddTransition() {
		this.ba.addProposition(l3);
		this.ba.addTransition(state3, state3, t3);
		assertTrue(this.ba.getOutTransitions(state3).contains(t3));
		assertTrue(this.ba.getInTransitions(state3).contains(t3));
		assertTrue(this.ba.getTransitionDestination(t3).equals(state3));
		assertTrue(this.ba.getTransitionSource(t3).equals(state3));
	}

	/**
	 * Test method for {@link it.polimi.automata.BA#removeState(null)}.
	 */
	@Test(expected = NullPointerException.class)
	public void testRemoveStateNull() {
		this.ba.removeState(null);
	}

	/**
	 * Test method for
	 * {@link it.polimi.automata.BA#removeState(IllegalArgument)}.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testRemoveStateIllegal() {
		this.ba.removeState(this.state4);
	}

	/**
	 * Test method for
	 * {@link it.polimi.automata.BA#removeState(it.polimi.automata.state.State)}
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
	 * Test method for {@link it.polimi.automata.BA#removeTransition(null)}.
	 */
	@Test(expected = NullPointerException.class)
	public void testRemoveTransitionNull() {
		this.ba.removeTransition(null);
	}

	/**
	 * Test method for
	 * {@link it.polimi.automata.BA#removeTransition(IllegalTransition)} .
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testRemoveTransitionIllegalTransition() {
		this.ba.removeTransition(t3);
	}

	/**
	 * Test method for
	 * {@link it.polimi.automata.BA#removeTransition(it.polimi.automata.transition.Transition)}
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
	 * Test method for {@link it.polimi.automata.BA#removeAcceptingState(null)}.
	 */
	@Test(expected = NullPointerException.class)
	public void testRemoveAcceptingStateNull() {
		this.ba.removeAcceptingState(null);
	}

	/**
	 * Test method for
	 * {@link it.polimi.automata.BA#removeAcceptingState(IllegalState)} .
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testRemoveAcceptingStateIllegalArgument() {
		this.ba.removeAcceptingState(state4);
	}

	/**
	 * Test method for
	 * {@link it.polimi.automata.BA#removeAcceptingState(it.polimi.automata.state.State)}
	 * .
	 */
	@Test
	public void testRemoveAcceptingState() {
		this.ba.removeAcceptingState(state3);
		assertTrue(this.ba.getAcceptStates().isEmpty());
		assertTrue(this.ba.getStates().contains(state3));
	}

	/**
	 * Test method for {@link it.polimi.automata.BA#removeInitialState(null)}.
	 */
	@Test(expected = NullPointerException.class)
	public void testRemoveInitialStateNull() {
		this.ba.removeInitialState(null);
	}

	/**
	 * Test method for {@link it.polimi.automata.BA#removeInitialState(Illegal)}
	 * .
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testRemoveInitialStateIllegal() {
		this.ba.removeInitialState(state2);
	}

	/**
	 * Test method for
	 * {@link it.polimi.automata.BA#removeInitialState(it.polimi.automata.state.State)}
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
	 * Test method for {@link it.polimi.automata.BA#getGraph()} .
	 */
	@Test
	public void testGetGraph() {
		DirectedGraph<State, Transition> directedGraph = this.ba.getGraph();

		assertTrue("The graph must contain the states of the BA",
				directedGraph.containsVertex(state1));
		assertTrue("The graph must contain the states of the BA",
				directedGraph.containsVertex(state2));
		assertTrue("The graph must contain the states of the BA",
				directedGraph.containsEdge(t1));
		assertTrue("The graph must contain the states of the BA",
				directedGraph.containsEdge(t2));
	}

	/**
	 * Test method for {@link it.polimi.automata.BA#getTransitionFactory()} .
	 */
	@Test
	public void testGetTransitionFactory() {

		assertSame(
				"The transition factory returned is the transition factory on which the IBA is based",
				transitioFactory, this.ba.getTransitionFactory());

	}

	/**
	 * Test method for {@link it.polimi.automata.BA#toString()} .
	 */
	@Test
	public void testToString() {

		assertNotNull("The toString method returns a string which is not null",
				ba.toString());
	}

	/**
	 * Test method for {@link it.polimi.automata.BA#clone()} .
	 */
	@Test
	public void testAddClone() {
		assertNotNull("the cloned BA is not null", this.ba.clone());
		Set<State> states = new HashSet<State>();
		states.add(state1);
		states.add(state2);
		states.add(state3);
		assertNotNull(
				"the cloned BA contains all the states of the original BA",
				this.ba.clone().getStates().equals(states));
		assertNotNull("the cloned BA does not contain additional states",
				this.ba.clone().getStates().size() == 3);

		assertNotNull(
				"the cloned BA contains all the initial states of the original BA",
				this.ba.clone().getInitialStates().contains(state1));
		assertNotNull(
				"the cloned BA does not contain additional initial states",
				this.ba.clone().getInitialStates().size() == 1);

		assertNotNull(
				"the cloned BA contains all the accepting states of the original BA",
				this.ba.clone().getAcceptStates().contains(state3));
		assertNotNull(
				"the cloned BA does not contain additional accepting states",
				this.ba.clone().getAcceptStates().size() == 1);

		Set<Transition> transitions = new HashSet<Transition>();
		transitions.add(t1);
		transitions.add(t2);

		assertNotNull(
				"the cloned BA contains all the transitions of the original BA",
				this.ba.clone().getTransitions().equals(transitions));
		assertNotNull("the cloned BA does not contain additional transitions",
				this.ba.clone().getTransitions().size() == 2);

	}

	/**
	 * Test method for {@link it.polimi.automata.BA#addStuttering()} .
	 */
	@Test(expected = NullPointerException.class)
	public void testAddStutteringNullState() {
		this.ba.addStuttering(null);
	}

	/**
	 * Test method for {@link it.polimi.automata.BA#addStuttering()} .
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testAddStutteringNotContainedState() {
		this.ba.addStuttering(new StateFactory().create("", 200000));
	}

	@Test
	public void testAddStuttering() {
		this.ba.addStuttering(state1);
		assertTrue("The stuttering adds a self loop on the state", !this.ba
				.getTransitions(state1, state1).isEmpty());
		assertTrue(
				"The transition is labeled with the stuttering character",
				this.ba.getTransitions(state1, state1).iterator().next()
						.getPropositions().size() == 1
						&& this.ba
								.getTransitions(state1, state1)
								.iterator()
								.next()
								.getPropositions()
								.contains(
										new GraphProposition(
												PropositionalLogicConstants.STUTTERING_CHARACTER,
												false)));

	}

	/**
	 * Test method for {@link it.polimi.automata.BA#testSize()} .
	 */
	@Test
	public void testSize() {
		assertEquals(
				"The size of the BA is the sum of the number of states and transitions ",
				5, this.ba.size());
	}
}
