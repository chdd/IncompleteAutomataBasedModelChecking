/**
 * 
 */
package it.polimi.contraintcomputation.abstraction;

import static org.junit.Assert.*;
import it.polimi.automata.IntersectionBA;
import it.polimi.automata.impl.IntBAFactoryImpl;
import it.polimi.automata.labeling.Label;
import it.polimi.automata.labeling.impl.LabelImplFactory;
import it.polimi.automata.state.State;
import it.polimi.automata.state.StateFactory;
import it.polimi.automata.state.impl.StateFactoryImpl;
import it.polimi.automata.transition.Transition;
import it.polimi.automata.transition.TransitionFactory;
import it.polimi.automata.transition.impl.ClaimTransitionFactoryImpl;

import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import rwth.i2.ltl2ba4j.model.IGraphProposition;
import rwth.i2.ltl2ba4j.model.impl.GraphProposition;

/**
 * @author claudiomenghi
 * 
 */
public class FilterTest {

	/*
	 * Claim 1
	 */
	private IntersectionBA<Label, State, Transition<Label>> intersectionBA;
	private State intersectionState1;
	private State intersectionState2;
	private State intersectionState3;
	private Transition<Label> intersectionTransition1;
	private Transition<Label> intersectionTransition2;
	private Transition<Label> intersectionTransition3;

	private Set<Label> intersectionTransition1Labels;
	private Set<Label> intersectionTransition2Labels;
	private Set<Label> intersectionTransition3Labels;

	private TransitionFactory<Label, Transition<Label>> transitionFactory;
	private StateFactory<State> stateFactory;

	@Before
	public void setUp() {
		this.intersectionBA = new IntBAFactoryImpl<Label, State, Transition<Label>>()
				.create();

		stateFactory = new StateFactoryImpl();
		intersectionState1 = stateFactory.create("intersectionState1");
		intersectionState2 = stateFactory.create("intersectionState2");
		intersectionState3 = stateFactory.create("intersectionState3");
		this.intersectionBA.addState(intersectionState1);
		this.intersectionBA.addState(intersectionState2);
		this.intersectionBA.addState(intersectionState3);
		transitionFactory = new ClaimTransitionFactoryImpl<Label>();

		this.intersectionTransition1Labels = new HashSet<Label>();
		Set<IGraphProposition> propositionsT1 = new HashSet<IGraphProposition>();
		propositionsT1.add(new GraphProposition("a", false));
		Label label1 = new LabelImplFactory().create(propositionsT1);
		intersectionTransition1Labels.add(label1);
		intersectionTransition1 = transitionFactory
				.create(intersectionTransition1Labels);

		this.intersectionTransition2Labels = new HashSet<Label>();
		Set<IGraphProposition> propositionsT2 = new HashSet<IGraphProposition>();
		propositionsT2.add(new GraphProposition("b", false));
		Label label2 = new LabelImplFactory().create(propositionsT2);
		intersectionTransition2Labels.add(label2);
		intersectionTransition2 = transitionFactory
				.create(intersectionTransition2Labels);

		this.intersectionTransition3Labels = new HashSet<Label>();
		Set<IGraphProposition> propositionsT3 = new HashSet<IGraphProposition>();
		propositionsT3.add(new GraphProposition("c", false));
		Label label3 = new LabelImplFactory().create(propositionsT3);
		intersectionTransition3Labels.add(label3);
		intersectionTransition3 = transitionFactory
				.create(intersectionTransition3Labels);

		this.intersectionBA.addCharacter(label1);
		this.intersectionBA.addCharacter(label2);
		this.intersectionBA.addCharacter(label3);
		this.intersectionBA.addTransition(intersectionState1,
				intersectionState2, intersectionTransition1);
		this.intersectionBA.addTransition(intersectionState2,
				intersectionState3, intersectionTransition2);
		this.intersectionBA.addTransition(intersectionState3,
				intersectionState3, intersectionTransition3);
	}

	/**
	 * Test method for
	 * {@link it.polimi.contraintcomputation.abstraction.Filter#Filter(null, java.util.Set)}
	 * .
	 */
	@Test(expected = NullPointerException.class)
	public void testFilterNullIntersectionAutomaton() {
		new Filter<Label, State, Transition<Label>>(null, new HashSet<State>());
	}

	/**
	 * Test method for
	 * {@link it.polimi.contraintcomputation.abstraction.Filter#Filter(it.polimi.automata.IntersectionBA, null)}
	 * .
	 */
	@Test(expected = NullPointerException.class)
	public void testFilterNullSetOfStates() {
		new Filter<Label, State, Transition<Label>>(this.intersectionBA, null);
	}

	/**
	 * Test method for
	 * {@link it.polimi.contraintcomputation.abstraction.Filter#Filter(it.polimi.automata.IntersectionBA, null)}
	 * .
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testFilterNotStatesContained() {
		Set<State> states = new HashSet<State>();
		states.add(this.intersectionState1);
		states.add(this.stateFactory.create("intersectionState4"));
		new Filter<Label, State, Transition<Label>>(this.intersectionBA, states);
	}

	/**
	 * Test method for
	 * {@link it.polimi.contraintcomputation.abstraction.Filter#Filter(it.polimi.automata.IntersectionBA, java.util.Set)}
	 * .
	 */
	@Test
	public void testFilter() {
		Set<State> states = new HashSet<State>();
		states.add(this.intersectionState1);
		assertNotNull(new Filter<Label, State, Transition<Label>>(
				this.intersectionBA, states));
	}

	/**
	 * Test method for
	 * {@link it.polimi.contraintcomputation.abstraction.Filter#filter()}.
	 */
	@Test
	public void testFilter1() {
		this.intersectionBA.addInitialState(this.intersectionState1);
		this.intersectionBA.addAcceptState(this.intersectionState3);
		Set<State> states = new HashSet<State>();
		states.add(this.intersectionState1);
		states.add(this.intersectionState3);
		Filter<Label, State, Transition<Label>> filter = new Filter<Label, State, Transition<Label>>(
				this.intersectionBA, states);
		IntersectionBA<Label, State, Transition<Label>> filteredBA = filter
				.filter();
		assertTrue(filteredBA.getStates().contains(this.intersectionState1));
		assertTrue(filteredBA.getStates().contains(this.intersectionState3));
		System.out.println(filteredBA.getInitialStates());
		assertTrue(filteredBA.getInitialStates().contains(this.intersectionState1));
		assertTrue(filteredBA.getInitialStates().contains(this.intersectionState3));
		assertTrue(filteredBA.getAcceptStates().contains(this.intersectionState1));
		assertTrue(filteredBA.getAcceptStates().contains(this.intersectionState3));
	}
	/**
	 * Test method for
	 * {@link it.polimi.contraintcomputation.abstraction.Filter#filter()}.
	 */
	@Test
	public void testFilter2() {
		this.intersectionBA.addInitialState(this.intersectionState1);
		this.intersectionBA.addAcceptState(this.intersectionState3);
		Set<State> states = new HashSet<State>();
		states.add(this.intersectionState1);
		states.add(this.intersectionState2);
		Filter<Label, State, Transition<Label>> filter = new Filter<Label, State, Transition<Label>>(
				this.intersectionBA, states);
		IntersectionBA<Label, State, Transition<Label>> filteredBA = filter
				.filter();
		assertTrue(filteredBA.getStates().contains(this.intersectionState1));
		assertTrue(filteredBA.getStates().contains(this.intersectionState2));
		assertTrue(filteredBA.getInitialStates().contains(intersectionState1));
		assertTrue(filteredBA.getInitialStates().size()==1);
		assertTrue(filteredBA.getAcceptStates().contains(this.intersectionState2));
		assertTrue(filteredBA.getAcceptStates().size()==1);
	}

}
