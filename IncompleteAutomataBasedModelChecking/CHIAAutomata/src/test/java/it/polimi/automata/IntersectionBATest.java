/**
 * 
 */
package it.polimi.automata;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;
import it.polimi.automata.state.State;
import it.polimi.automata.transition.ModelTransitionFactory;
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
public class IntersectionBATest {

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

	private IntersectionBA intersectionBA;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		this.intersectionBA = new IntersectionBA();
		intersectionBA.addInitialState(state1);
		intersectionBA.addState(state2);
		intersectionBA.addAcceptState(state3);
		intersectionBA.addMixedState(state3);
		this.intersectionBA.addProposition(l1);
		this.intersectionBA.addProposition(l2);
		this.intersectionBA.addTransition(state1, state2, t1);
		this.intersectionBA.addConstrainedTransition(state2, state3, t2);
		Set<IGraphProposition> returnSet = new HashSet<IGraphProposition>();
		returnSet.add(l3);
		when(t3.getPropositions()).thenReturn(returnSet);
	}

	/**
	 * Test method for
	 * {@link it.polimi.automata.IntersectionBA#getPurelyRegularStates()}.
	 */
	@Test
	public void testGetPurelyRegularStates() {
		Set<State> purelyRegularStates = new HashSet<State>();
		purelyRegularStates.add(state1);
		purelyRegularStates.add(state2);
		assertEquals(
				"The method getPurelyRegularStates returns the set of purely regular states",
				purelyRegularStates,
				this.intersectionBA.getPurelyRegularStates());
	}

	/**
	 * Test method for {@link it.polimi.automata.IntersectionBA#IntBAImpl()}.
	 */
	@Test
	public void testIntBAImpl() {
		assertNotNull(new IntersectionBA());
	}

	/**
	 * Test method for
	 * {@link it.polimi.automata.IntersectionBA#addMixedState(null)}.
	 */
	@Test(expected = NullPointerException.class)
	public void testAddMixedStateNull() {
		this.intersectionBA.addMixedState(null);
	}

	/**
	 * Test method for
	 * {@link it.polimi.automata.IntersectionBA#addMixedState(it.polimi.automata.state.State)}
	 * .
	 */
	@Test
	public void testAddMixedState() {
		this.intersectionBA.addMixedState(state1);
		assertTrue(this.intersectionBA.getStates().contains(state1));
		assertTrue(this.intersectionBA.getStates().contains(state2));
		assertTrue(this.intersectionBA.getStates().contains(state3));
		assertTrue(this.intersectionBA.getInitialStates().contains(state1));
		assertFalse(this.intersectionBA.getInitialStates().contains(state2));
		assertFalse(this.intersectionBA.getInitialStates().contains(state3));
		assertTrue(this.intersectionBA.getAcceptStates().contains(state3));
		assertFalse(this.intersectionBA.getAcceptStates().contains(state2));
		assertFalse(this.intersectionBA.getAcceptStates().contains(state1));
		assertTrue(this.intersectionBA.getMixedStates().contains(state1));
		assertFalse(this.intersectionBA.getMixedStates().contains(state2));
		assertTrue(this.intersectionBA.getMixedStates().contains(state3));
	}

	/**
	 * Test method for
	 * {@link it.polimi.automata.IntersectionBA#addMixedState(it.polimi.automata.state.State)}
	 * .
	 */
	@Test
	public void testAddConstrainedTransition() {

		Transition transition = new ModelTransitionFactory().create();
		this.intersectionBA
				.addConstrainedTransition(state1, state2, transition);
		assertTrue(
				"The transition is added to the set of constrained transition",
				this.intersectionBA.getConstrainedTransitions().contains(
						transition));
	}

	/**
	 * Test method for
	 * {@link it.polimi.automata.IntersectionBA#addMixedState(it.polimi.automata.state.State)}
	 * .
	 */
	@Test
	public void testAddMixedStateNotContainedInTheIBA() {
		this.intersectionBA.addMixedState(state4);
		assertTrue("The state state4 is added to the set of the states",
				this.intersectionBA.getStates().contains(state4));
		assertTrue("The state state4 is added to the set of the mixed states",
				this.intersectionBA.getMixedStates().contains(state4));

		assertTrue(
				"Adding a mixed state does not change the set of initial states",
				this.intersectionBA.getInitialStates().contains(state1));
		assertFalse(
				"Adding a mixed state does not change the set of initial states",
				this.intersectionBA.getInitialStates().contains(state2));
		assertFalse(
				"Adding a mixed state does not change the set of initial states",
				this.intersectionBA.getInitialStates().contains(state3));
		assertTrue(
				"Adding a mixed state does not change the set of accepting states",
				this.intersectionBA.getAcceptStates().contains(state3));
		assertFalse(
				"Adding a mixed state does not change the set of accepting states",
				this.intersectionBA.getAcceptStates().contains(state2));
		assertFalse(
				"Adding a mixed state does not change the set of accepting states",
				this.intersectionBA.getAcceptStates().contains(state1));
		assertFalse(
				"Adding a mixed state does not change the set of mixed states",
				this.intersectionBA.getMixedStates().contains(state1));
		assertFalse(
				"Adding a mixed state does not change the set of mixed states",
				this.intersectionBA.getMixedStates().contains(state2));
		assertTrue(
				"Adding a mixed state does not change the set of mixed states",
				this.intersectionBA.getMixedStates().contains(state3));
	}

	/**
	 * Test method for {@link it.polimi.automata.IntersectionBA#getAddState()}.
	 */
	@Test
	public void testAddState() {
		this.intersectionBA.addMixedState(state1);
		assertTrue(this.intersectionBA.getMixedStates().contains(state1));
		assertTrue(this.intersectionBA.getMixedStates().contains(state3));
	}

	/**
	 * Test method for
	 * {@link it.polimi.automata.IntersectionBA#getRemoveState()}.
	 */
	@Test
	public void testRemove() {
		this.intersectionBA.removeState(state1);
		assertFalse("The state is removed from the set of the state",
				this.intersectionBA.getStates().contains(state1));
		assertFalse(
				"The state removes the state from the set of initial states",
				this.intersectionBA.getInitialStates().contains(state1));

		assertEquals("The other states are not removed from the automaton", 2,
				this.intersectionBA.getStates().size());
	}

	/**
	 * Test method for
	 * {@link it.polimi.automata.IntersectionBA#getRemoveState()}.
	 */
	@Test
	public void testRemove_MixedStates() {
		this.intersectionBA.removeState(state3);
		assertFalse("The state is removed from the set of the state",
				this.intersectionBA.getStates().contains(state3));
		assertFalse("The state removes the state from the set of mixed states",
				this.intersectionBA.getMixedStates().contains(state3));

		assertEquals("The other states are not removed from the automaton", 2,
				this.intersectionBA.getStates().size());
	}

	/**
	 * Test method for {@link it.polimi.automata.IntersectionBA#clone()}.
	 */
	@Test
	public void testClone() {
		IntersectionBA clone = (IntersectionBA) this.intersectionBA.clone();
		assertEquals(clone.getPropositions(),
				this.intersectionBA.getPropositions());
		assertEquals(clone.getTransitions(),
				this.intersectionBA.getTransitions());
		assertEquals(clone.getStates(), this.intersectionBA.getStates());
		assertEquals(clone.getInitialStates(),
				this.intersectionBA.getInitialStates());
		assertEquals(clone.getAcceptStates(),
				this.intersectionBA.getAcceptStates());
		assertEquals(clone.getOutTransitions(state1),
				this.intersectionBA.getOutTransitions(state1));
		assertEquals(clone.getOutTransitions(state2),
				this.intersectionBA.getOutTransitions(state2));
		assertEquals(clone.getInTransitions(state2),
				this.intersectionBA.getInTransitions(state2));
		assertEquals(clone.getMixedStates(),
				this.intersectionBA.getMixedStates());
	}

	/**
	 * Test method for {@link it.polimi.automata.IntersectionBA#getAbstractio()}
	 * .
	 */
	@Test
	public void testGetAbstraction() {

		Set<State> states = new HashSet<State>();
		states.add(state1);
		states.add(state3);
		IntersectionBA abstraction = this.intersectionBA.getAbstraction(states);

		assertEquals(
				"The propositions of the abstraction are equal to the proposition of the intersection BA",
				this.intersectionBA.getPropositions(),
				abstraction.getPropositions());

		assertEquals(
				"The set of hte states of the abstraction contains only the abstracted states",
				states, abstraction.getStates());
		assertTrue(
				"The states that were accepting in the original ba are still accepting in the abstraction",
				abstraction.getAcceptStates().contains(state3));
		assertEquals(
				"The states that were accepting in the original ba are still accepting in the abstraction",
				1, abstraction.getAcceptStates().size());

		assertEquals(
				"The abstraction contains the transitions between the states specified in the  getAbstraction method",
				0, abstraction.getTransitions().size());
		assertTrue(
				"The states that were initial in the original ba are still accepting in the abstraction",
				abstraction.getInitialStates().contains(state1));
		assertEquals(
				"The states that were initial in the original ba are still accepting in the abstraction",
				1, abstraction.getInitialStates().size());

	}

	/**
	 * Test method for {@link it.polimi.automata.IntersectionBA#getAbstractio()}
	 * .
	 */
	@Test
	public void testGetAbstraction_Transition() {

		Set<State> states = new HashSet<State>();
		states.add(state1);
		states.add(state2);
		IntersectionBA abstraction = this.intersectionBA.getAbstraction(states);

		assertEquals(
				"The propositions of the abstraction are equal to the proposition of the intersection BA",
				this.intersectionBA.getPropositions(),
				abstraction.getPropositions());

		assertEquals(
				"The set of hte states of the abstraction contains only the abstracted states",
				states, abstraction.getStates());

		assertTrue(
				"The abstraction contains the transitions between the states specified in the  getAbstraction method",
				abstraction.getTransitions().contains(t1));
		assertEquals(
				"The abstraction contains the transitions between the states specified in the  getAbstraction method",
				1, abstraction.getTransitions().size());
		assertTrue(
				"The states that were initial in the original ba are still accepting in the abstraction",
				abstraction.getInitialStates().contains(state1));
		assertEquals(
				"The states that were initial in the original ba are still accepting in the abstraction",
				1, abstraction.getInitialStates().size());

	}

	/**
	 * Test method for
	 * {@link it.polimi.automata.IntersectionBA#removeTransition()}.
	 */
	@Test
	public void testRemoveTransition() {

		this.intersectionBA.removeTransition(t2);
		assertFalse(
				"Removing a constrained transition removes the transition from the set of constrained transitions",
				this.intersectionBA.getConstrainedTransitions().contains(t2));

		assertTrue(
				"Removing a constrained does not remove not constrained transitions",
				this.intersectionBA.getTransitions().contains(t1));
	}

	/**
	 * Test method for
	 * {@link it.polimi.automata.IntersectionBA#removeTransition()}.
	 */
	@Test
	public void testRemoveNotTransition() {

		this.intersectionBA.removeTransition(t1);
		assertTrue(
				"Removing a not constrained transition does not remove the transition from the set of constrained transitions",
				this.intersectionBA.getConstrainedTransitions().contains(t2));

		assertFalse("Removing a not constrained removes the transition",
				this.intersectionBA.getTransitions().contains(t1));
	}

	/**
	 * Test method for {@link it.polimi.automata.IntersectionBA#toString()}.
	 */
	@Test
	public void testToString() {
		assertNotNull(
				"The to String method returns a String which is not null",
				this.intersectionBA.toString());
	}
}
