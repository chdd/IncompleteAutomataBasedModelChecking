/**
 * 
 */
package it.polimi.constraints.components;

import static org.junit.Assert.*;
import it.polimi.automata.IBA;
import it.polimi.automata.state.State;
import it.polimi.automata.state.StateFactory;
import it.polimi.automata.transition.ModelTransitionFactory;
import it.polimi.automata.transition.Transition;
import it.polimi.constraints.transitions.PluggingTransition;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import rwth.i2.ltl2ba4j.model.IGraphProposition;
import rwth.i2.ltl2ba4j.model.impl.GraphProposition;

/**
 * @author Claudio1
 *
 */
public class ReplacementTest {

	/**
	 * Test method for
	 * {@link it.polimi.constraints.components.Replacement#Replacement(it.polimi.automata.state.State, it.polimi.automata.IBA, java.util.Set, java.util.Set)}
	 * .
	 */
	@Test(expected = NullPointerException.class)
	public void testReplacement() {
		new Replacement(null, new IBA(new ModelTransitionFactory()),
				new HashSet<PluggingTransition>(),
				new HashSet<PluggingTransition>());
	}

	/**
	 * Test method for
	 * {@link it.polimi.constraints.components.Replacement#Replacement(it.polimi.automata.state.State, it.polimi.automata.IBA, java.util.Set, java.util.Set)}
	 * .
	 */
	@Test(expected = NullPointerException.class)
	public void testReplacement1() {
		new Replacement(new StateFactory().create(), null,
				new HashSet<PluggingTransition>(),
				new HashSet<PluggingTransition>());
	}

	/**
	 * Test method for
	 * {@link it.polimi.constraints.components.Replacement#Replacement(it.polimi.automata.state.State, it.polimi.automata.IBA, java.util.Set, java.util.Set)}
	 * .
	 */
	@Test(expected = NullPointerException.class)
	public void testReplacement2() {
		new Replacement(new StateFactory().create(), new IBA(
				new ModelTransitionFactory()), null,
				new HashSet<PluggingTransition>());
	}

	/**
	 * Test method for
	 * {@link it.polimi.constraints.components.Replacement#Replacement(it.polimi.automata.state.State, it.polimi.automata.IBA, java.util.Set, java.util.Set)}
	 * .
	 */
	@Test(expected = NullPointerException.class)
	public void testReplacement3() {
		new Replacement(new StateFactory().create(), new IBA(
				new ModelTransitionFactory()),
				new HashSet<PluggingTransition>(), null);
	}

	/**
	 * Test method for
	 * {@link it.polimi.constraints.components.Replacement#Replacement(it.polimi.automata.state.State, it.polimi.automata.IBA, java.util.Set, java.util.Set)}
	 * .
	 */
	@Test
	public void testReplacement4() {
		assertNotNull(new Replacement(new StateFactory().create(), new IBA(
				new ModelTransitionFactory()),
				new HashSet<PluggingTransition>(),
				new HashSet<PluggingTransition>()));
	}

	/**
	 * Test method for
	 * {@link it.polimi.constraints.components.Replacement#getAutomaton()}.
	 */
	@Test
	public void testGetAutomaton() {

		IBA automaton = new IBA(new ModelTransitionFactory());
		Replacement replacement = new Replacement(new StateFactory().create(),
				automaton, new HashSet<PluggingTransition>(),
				new HashSet<PluggingTransition>());
		assertTrue(replacement.getAutomaton() == automaton);
	}

	/**
	 * Test method for
	 * {@link it.polimi.constraints.components.Replacement#addOutgoingTransition(it.polimi.constraints.transitions.PluggingTransition)}
	 * .
	 */
	@Test
	public void testAddOutgoingTransition() {
		State sourceState = new StateFactory().create();
		State destinationState = new StateFactory().create();
		Transition transition = new ModelTransitionFactory().create();

		PluggingTransition pluggingTransition = new PluggingTransition(
				sourceState, destinationState, transition, false);
		Set<PluggingTransition> outgoingTransitions = new HashSet<PluggingTransition>();
		outgoingTransitions.add(pluggingTransition);
		IBA automaton = new IBA(new ModelTransitionFactory());
		automaton.addState(sourceState);
		Replacement replacement = new Replacement(new StateFactory().create(),
				automaton, new HashSet<PluggingTransition>(),
				outgoingTransitions);
		assertEquals(outgoingTransitions, replacement.getOutgoingTransitions());
		assertTrue(replacement.hasOutgoingTransition(sourceState,
				destinationState, transition.getPropositions()));
	}

	/**
	 * Test method for
	 * {@link it.polimi.constraints.components.Replacement#getOutgoingTransitions()}
	 * .
	 */
	@Test
	public void testGetOutgoingTransitions() {
		State sourceState = new StateFactory().create();
		State destinationState = new StateFactory().create();
		Transition transition = new ModelTransitionFactory().create();

		PluggingTransition pluggingTransition = new PluggingTransition(
				sourceState, destinationState, transition, false);
		Set<PluggingTransition> outgoingTransitions = new HashSet<PluggingTransition>();
		outgoingTransitions.add(pluggingTransition);
		IBA automaton = new IBA(new ModelTransitionFactory());
		automaton.addState(sourceState);
		Replacement replacement = new Replacement(new StateFactory().create(),
				automaton, new HashSet<PluggingTransition>(),
				outgoingTransitions);
		assertEquals(outgoingTransitions, replacement.getOutgoingTransitions());
	}

	/**
	 * Test method for
	 * {@link it.polimi.constraints.components.Replacement#getOutgoingTransitions(it.polimi.automata.state.State, it.polimi.automata.state.State, java.util.Set)}
	 * .
	 */
	@Test
	public void testGetOutgoingTransitionsStateStateSetOfIGraphProposition() {
		State sourceState = new StateFactory().create();
		State destinationState = new StateFactory().create();
		Transition transition = new ModelTransitionFactory().create();
		Set<IGraphProposition> propositions = new HashSet<IGraphProposition>();
		propositions.add(new GraphProposition("a", false));
		Transition transition2 = new ModelTransitionFactory()
				.create(propositions);

		PluggingTransition pluggingTransition = new PluggingTransition(
				sourceState, destinationState, transition, false);
		PluggingTransition pluggingTransition1 = new PluggingTransition(
				sourceState, destinationState, transition2, false);
		Set<PluggingTransition> outgoingTransitions = new HashSet<PluggingTransition>();
		outgoingTransitions.add(pluggingTransition);
		outgoingTransitions.add(pluggingTransition1);
		IBA automaton = new IBA(new ModelTransitionFactory());
		automaton.addState(sourceState);
		Replacement replacement = new Replacement(new StateFactory().create(),
				automaton, new HashSet<PluggingTransition>(),
				outgoingTransitions);
		assertEquals(outgoingTransitions, replacement.getOutgoingTransitions());
		assertTrue(replacement.getOutgoingTransitions(sourceState,
				destinationState, transition.getPropositions()).size() == 1);
	}

	/**
	 * Test method for
	 * {@link it.polimi.constraints.components.Replacement#hasOutgoingTransition(it.polimi.automata.state.State, it.polimi.automata.state.State, java.util.Set)}
	 * .
	 */
	@Test
	public void testHasOutgoingTransition() {
		State sourceState = new StateFactory().create();
		State destinationState = new StateFactory().create();
		Transition transition = new ModelTransitionFactory().create();

		PluggingTransition pluggingTransition = new PluggingTransition(
				sourceState, destinationState, transition, false);
		Set<PluggingTransition> outgoingTransitions = new HashSet<PluggingTransition>();
		outgoingTransitions.add(pluggingTransition);
		IBA automaton = new IBA(new ModelTransitionFactory());
		automaton.addState(sourceState);
		Replacement replacement = new Replacement(new StateFactory().create(),
				automaton, new HashSet<PluggingTransition>(),
				outgoingTransitions);
		assertEquals(outgoingTransitions, replacement.getOutgoingTransitions());
		assertTrue(replacement.hasOutgoingTransition(sourceState,
				destinationState, transition.getPropositions()));
	}

	/**
	 * Test method for
	 * {@link it.polimi.constraints.components.Replacement#addIncomingTransition(it.polimi.constraints.transitions.PluggingTransition)}
	 * .
	 */
	@Test
	public void testAddIncomingTransition() {
		State sourceState = new StateFactory().create();
		State destinationState = new StateFactory().create();
		Transition transition = new ModelTransitionFactory().create();

		PluggingTransition pluggingTransition = new PluggingTransition(
				sourceState, destinationState, transition, false);
		Set<PluggingTransition> incomingTransitions = new HashSet<PluggingTransition>();
		incomingTransitions.add(pluggingTransition);
		IBA automaton = new IBA(new ModelTransitionFactory());
		automaton.addState(destinationState);
		Replacement replacement = new Replacement(new StateFactory().create(),
				automaton, incomingTransitions,
				new HashSet<PluggingTransition>());
		assertTrue(replacement.hasIncomingTransition(sourceState,
				transition.getPropositions()));
	}

	/**
	 * Test method for
	 * {@link it.polimi.constraints.components.Replacement#getIncomingTransitions()}
	 * .
	 */
	@Test
	public void testGetIncomingTransitions() {
		State sourceState = new StateFactory().create();
		State destinationState = new StateFactory().create();
		Transition transition = new ModelTransitionFactory().create();

		PluggingTransition pluggingTransition = new PluggingTransition(
				sourceState, destinationState, transition, false);
		Set<PluggingTransition> incomingTransitions = new HashSet<PluggingTransition>();
		incomingTransitions.add(pluggingTransition);
		IBA automaton = new IBA(new ModelTransitionFactory());
		automaton.addState(destinationState);
		Replacement replacement = new Replacement(new StateFactory().create(),
				automaton, incomingTransitions,
				new HashSet<PluggingTransition>());
		assertEquals(incomingTransitions, replacement.getIncomingTransitions());
	}

	/**
	 * Test method for
	 * {@link it.polimi.constraints.components.Replacement#getIncomingTransitions(it.polimi.automata.state.State, java.util.Set)}
	 * .
	 */
	@Test
	public void testGetIncomingTransitionsStateSetOfIGraphProposition() {
		State sourceState = new StateFactory().create();
		State destinationState = new StateFactory().create();
		Transition transition = new ModelTransitionFactory().create();
		State sourceState2 = new StateFactory().create();
		Transition transition2 = new ModelTransitionFactory().create();

		PluggingTransition pluggingTransition = new PluggingTransition(
				sourceState, destinationState, transition, false);
		PluggingTransition pluggingTransition1 = new PluggingTransition(
				sourceState2, destinationState, transition2, false);
		Set<PluggingTransition> incomingTransitions = new HashSet<PluggingTransition>();
		incomingTransitions.add(pluggingTransition);
		incomingTransitions.add(pluggingTransition1);

		IBA automaton = new IBA(new ModelTransitionFactory());
		automaton.addState(destinationState);
		Replacement replacement = new Replacement(new StateFactory().create(),
				automaton, incomingTransitions,
				new HashSet<PluggingTransition>());
		assertTrue(replacement.hasIncomingTransition(sourceState,
				transition.getPropositions()));
		assertEquals(1, replacement.getIncomingTransitions(sourceState,
				transition.getPropositions()).size());
	}

	/**
	 * Test method for
	 * {@link it.polimi.constraints.components.Replacement#hasIncomingTransition(it.polimi.automata.state.State, java.util.Set)}
	 * .
	 */
	@Test
	public void testHasIncomingTransition() {
		State sourceState = new StateFactory().create();
		State destinationState = new StateFactory().create();
		Transition transition = new ModelTransitionFactory().create();

		PluggingTransition pluggingTransition = new PluggingTransition(
				sourceState, destinationState, transition, false);
		Set<PluggingTransition> incomingTransitions = new HashSet<PluggingTransition>();
		incomingTransitions.add(pluggingTransition);
		IBA automaton = new IBA(new ModelTransitionFactory());
		automaton.addState(destinationState);
		Replacement replacement = new Replacement(new StateFactory().create(),
				automaton, incomingTransitions,
				new HashSet<PluggingTransition>());
		assertTrue(replacement.hasIncomingTransition(sourceState,
				transition.getPropositions()));
	}

	/**
	 * Test method for
	 * {@link it.polimi.constraints.components.Replacement#removePluggingTransition(it.polimi.constraints.transitions.PluggingTransition)}
	 * .
	 */
	@Test
	public void testRemovePluggingOutgoingTransition() {
		State sourceState = new StateFactory().create();
		State destinationState = new StateFactory().create();
		Transition transition = new ModelTransitionFactory().create();

		PluggingTransition pluggingTransition = new PluggingTransition(
				sourceState, destinationState, transition, false);
		Set<PluggingTransition> outgoingTransitions = new HashSet<PluggingTransition>();
		outgoingTransitions.add(pluggingTransition);
		IBA automaton = new IBA(new ModelTransitionFactory());
		automaton.addState(sourceState);
		Replacement replacement = new Replacement(new StateFactory().create(),
				automaton, new HashSet<PluggingTransition>(),
				outgoingTransitions);
		assertTrue(replacement.hasOutgoingTransition(sourceState,
				destinationState, transition.getPropositions()));
		replacement.removePluggingTransition(pluggingTransition);
		assertTrue(replacement.getOutgoingTransitions().size() == 0);
		assertFalse(replacement.hasOutgoingTransition(sourceState,
				destinationState, transition.getPropositions()));
	}

	/**
	 * Test method for
	 * {@link it.polimi.constraints.components.Replacement#removePluggingTransition(it.polimi.constraints.transitions.PluggingTransition)}
	 * .
	 */
	@Test
	public void testRemovePluggingIncomingTransition() {
		State sourceState = new StateFactory().create();
		State destinationState = new StateFactory().create();
		Transition transition = new ModelTransitionFactory().create();

		PluggingTransition pluggingTransition = new PluggingTransition(
				sourceState, destinationState, transition, false);
		Set<PluggingTransition> incomingTransitions = new HashSet<PluggingTransition>();
		incomingTransitions.add(pluggingTransition);
		IBA automaton = new IBA(new ModelTransitionFactory());
		automaton.addState(destinationState);
		Replacement replacement = new Replacement(new StateFactory().create(),
				automaton, incomingTransitions,
				new HashSet<PluggingTransition>());
		assertTrue(replacement.hasIncomingTransition(sourceState,
				transition.getPropositions()));
		replacement.removePluggingTransition(pluggingTransition);
		assertTrue(replacement.getIncomingTransitions().size() == 0);
		assertFalse(replacement.hasIncomingTransition(destinationState,
				transition.getPropositions()));
	}

	/**
	 * Test method for
	 * {@link it.polimi.constraints.components.Replacement#removePluggingTransition(it.polimi.constraints.transitions.PluggingTransition)}
	 * .
	 */
	@Test(expected = NullPointerException.class)
	public void testRemovePluggingTransitionNull() {
		State sourceState = new StateFactory().create();
		State destinationState = new StateFactory().create();
		Transition transition = new ModelTransitionFactory().create();

		PluggingTransition pluggingTransition = new PluggingTransition(
				sourceState, destinationState, transition, false);
		Set<PluggingTransition> outgoingTransitions = new HashSet<PluggingTransition>();
		outgoingTransitions.add(pluggingTransition);
		IBA automaton = new IBA(new ModelTransitionFactory());
		automaton.addState(sourceState);
		Replacement replacement = new Replacement(new StateFactory().create(),
				automaton, new HashSet<PluggingTransition>(),
				outgoingTransitions);
		replacement.removePluggingTransition(null);
	}

	/**
	 * Test method for
	 * {@link it.polimi.constraints.components.Replacement#removePluggingTransition(it.polimi.constraints.transitions.PluggingTransition)}
	 * .
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testRemovePluggingIllegalTransition() {
		State sourceState = new StateFactory().create();
		State destinationState = new StateFactory().create();
		Transition transition = new ModelTransitionFactory().create();

		PluggingTransition pluggingTransition = new PluggingTransition(
				sourceState, destinationState, transition, false);
		PluggingTransition illegalPluggingTransition = new PluggingTransition(
				new StateFactory().create(), destinationState, transition,
				false);
		Set<PluggingTransition> outgoingTransitions = new HashSet<PluggingTransition>();
		outgoingTransitions.add(pluggingTransition);
		IBA automaton = new IBA(new ModelTransitionFactory());
		automaton.addState(sourceState);
		Replacement replacement = new Replacement(new StateFactory().create(),
				automaton, new HashSet<PluggingTransition>(),
				outgoingTransitions);
		replacement.removePluggingTransition(illegalPluggingTransition);
	}

}
