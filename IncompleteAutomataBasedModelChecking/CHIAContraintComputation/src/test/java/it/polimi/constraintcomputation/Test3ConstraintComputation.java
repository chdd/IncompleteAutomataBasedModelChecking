package it.polimi.constraintcomputation;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;
import it.polimi.automata.BA;
import it.polimi.automata.IBA;
import it.polimi.automata.IntersectionBA;
import it.polimi.automata.io.in.BAReader;
import it.polimi.automata.io.in.IBAReader;
import it.polimi.automata.io.out.ElementToStringTransformer;
import it.polimi.automata.state.IntersectionStateFactory;
import it.polimi.automata.state.State;
import it.polimi.automata.state.StateFactory;
import it.polimi.checker.Checker;
import it.polimi.checker.SatisfactionValue;
import it.polimi.checker.intersection.IntersectionTransitionBuilder;
import it.polimi.checker.intersection.acceptingpolicies.NormalAcceptingPolicy;
import it.polimi.constraints.Constraint;
import it.polimi.constraints.components.SubProperty;
import it.polimi.constraints.io.out.constraint.ConstraintToElementTransformer;
import it.polimi.constraints.transitions.ColoredPluggingTransition;
import it.polimi.contraintcomputation.ConstraintGenerator;

import java.io.File;
import java.util.AbstractMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.xml.parsers.ParserConfigurationException;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class Test3ConstraintComputation {

	private static final String path = "it.polimi.constraintcomputation/";

	private IBA model;
	private BA claim;

	private State state9;
	private State state10;
	private State state11;
	private State state12;
	private State state13;
	private State state14;
	private State state15;
	private State state16;
	private State state17;
	private State state18;
	private State state19;

	private State state1;
	private State state2;
	private State state3;

	@Mock
	private IntersectionStateFactory intersectionStateFactory;

	private StateFactory stateFactory;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		this.model = new IBAReader(new File(getClass().getClassLoader()
				.getResource(path + "test3/model.xml").getFile())).perform();

		this.claim = new BAReader(new File(getClass().getClassLoader()
				.getResource(path + "test3/claim.xml").getFile())).perform();

		stateFactory = new StateFactory();
		state1 = stateFactory.create("1", 1);
		state2 = stateFactory.create("2", 2);
		state3 = stateFactory.create("3", 3);

		state9 = stateFactory.create("9", 9);
		state10 = stateFactory.create("10", 10);
		state11 = stateFactory.create("11", 11);
		state12 = stateFactory.create("12", 12);
		state13 = stateFactory.create("13", 13);
		state14 = stateFactory.create("14", 14);
		state15 = stateFactory.create("15", 15);
		state16 = stateFactory.create("16", 16);
		state17 = stateFactory.create("17", 17);
		state18 = stateFactory.create("18", 18);
		state19 = stateFactory.create("19", 19);
		when(intersectionStateFactory.create(state2, state2, 0)).thenReturn(
				state9);
		when(intersectionStateFactory.create(state3, state2, 1)).thenReturn(
				state10);
		when(intersectionStateFactory.create(state1, state2, 2)).thenReturn(
				state11);
		when(intersectionStateFactory.create(state3, state2, 0)).thenReturn(
				state12);
		when(intersectionStateFactory.create(state1, state2, 0)).thenReturn(
				state13);
		when(intersectionStateFactory.create(state3, state1, 1)).thenReturn(
				state14);
		when(intersectionStateFactory.create(state1, state1, 2)).thenReturn(
				state15);
		when(intersectionStateFactory.create(state3, state1, 0)).thenReturn(
				state16);
		when(intersectionStateFactory.create(state1, state1, 0)).thenReturn(
				state17);
		when(intersectionStateFactory.create(state3, state1, 2)).thenReturn(
				state18);
		when(intersectionStateFactory.create(state3, state2, 2)).thenReturn(
				state19);

	}

	@Test
	public void test() throws ParserConfigurationException, Exception {

		Checker checker = new Checker(model, claim,
				new NormalAcceptingPolicy(), intersectionStateFactory, new IntersectionTransitionBuilder());
		SatisfactionValue returnValue = checker.perform();
		assertTrue("The property must be possibly satisfied",
				returnValue == SatisfactionValue.POSSIBLYSATISFIED);

		System.out.println(checker.getUpperIntersectionBA().toString());
		IntersectionBA upperIntersectionBA = checker.getUpperIntersectionBA();

		assertTrue(upperIntersectionBA.getStates().contains(state9));
		assertTrue(upperIntersectionBA.getStates().contains(state10));
		assertTrue(upperIntersectionBA.getStates().contains(state11));
		assertTrue(upperIntersectionBA.getStates().contains(state12));
		assertTrue(upperIntersectionBA.getStates().contains(state13));
		assertTrue(upperIntersectionBA.getStates().contains(state14));
		assertTrue(upperIntersectionBA.getStates().contains(state15));
		assertTrue(upperIntersectionBA.getStates().contains(state16));
		assertTrue(upperIntersectionBA.getStates().contains(state17));
		assertTrue(upperIntersectionBA.getStates().contains(state18));
		assertTrue(upperIntersectionBA.getStates().contains(state19));

		ConstraintGenerator cg = new ConstraintGenerator(checker);
		Constraint constraint = cg.perform();
		cg.computeIndispensable();
		cg.computePortReachability();
		cg.coloring();
		System.out.println(new ElementToStringTransformer()
				.transform(new ConstraintToElementTransformer()
						.transform(constraint)));
		assertTrue(constraint.getSubProperties().size() == 1);
		SubProperty subProperty = constraint.getSubProperties().iterator()
				.next();
		assertTrue(subProperty.isIndispensable() == true);

		BA subPropertyAutomaton = constraint.getSubProperties().iterator()
				.next().getAutomaton();

		assertTrue(subPropertyAutomaton.getStates().contains(state10));
		assertTrue(subPropertyAutomaton.getStates().contains(state12));
		assertTrue(subPropertyAutomaton.getStates().contains(state14));
		assertTrue(subPropertyAutomaton.getStates().contains(state16));
		assertTrue(subPropertyAutomaton.getStates().contains(state18));
		assertTrue(subPropertyAutomaton.getStates().contains(state19));

		Set<ColoredPluggingTransition> incomingTransitions = subProperty
				.getIncomingTransitions();

		// checking the incoming transition of the state 10
		Set<ColoredPluggingTransition> incomingTransitions10 = this
				.getIncomingTransition(state10, incomingTransitions);
		assertTrue(incomingTransitions10.size() == 2);
		Iterator<ColoredPluggingTransition> iterator = incomingTransitions10
				.iterator();
		ColoredPluggingTransition next = iterator.next();
		assertTrue(next.getSource().equals(state2)
				|| next.getSource().equals(state1));
		assertTrue(next.getSource().equals(state2)
				|| next.getSource().equals(state1));

		// checking the incoming transition of the state 12
		Set<ColoredPluggingTransition> incomingTransitions12 = this
				.getIncomingTransition(state12, incomingTransitions);
		assertTrue(incomingTransitions12.size() == 1);
		assertTrue(incomingTransitions12.iterator().next().getSource()
				.equals(state1));

		// checking the incoming transition of the state 14
		Set<ColoredPluggingTransition> incomingTransitions14 = this
				.getIncomingTransition(state14, incomingTransitions);
		assertTrue(incomingTransitions14.size() == 1);
		assertTrue(incomingTransitions14.iterator().next().getSource()
				.equals(state1));

		assertTrue(constraint.getSubProperties().iterator().next()
				.getAutomaton().getStates().size() == 6);
		assertTrue(constraint.getSubProperties().iterator().next()
				.getAutomaton().getTransitions().size() == 9);
		
		this.testReachabilityElementsState14State16(constraint);

	}

	public Set<ColoredPluggingTransition> getIncomingTransition(
			State destinationState,
			Set<ColoredPluggingTransition> incomingTransitions) {
		Set<ColoredPluggingTransition> incomingTransitionOfTheDestinationState = new HashSet<ColoredPluggingTransition>();
		for (ColoredPluggingTransition incomingTransition : incomingTransitions) {
			if (incomingTransition.getDestination().equals(destinationState)) {
				incomingTransitionOfTheDestinationState.add(incomingTransition);
			}
		}
		return incomingTransitionOfTheDestinationState;
	}

	public Set<ColoredPluggingTransition> getOutgoingTransition(
			State sourceState,
			Set<ColoredPluggingTransition> outgoingTransitions) {
		Set<ColoredPluggingTransition> outgoingTransitionOfTheDestinationState = new HashSet<ColoredPluggingTransition>();
		for (ColoredPluggingTransition outgoingTransition : outgoingTransitions) {
			if (outgoingTransition.getSource().equals(sourceState)) {
				outgoingTransitionOfTheDestinationState.add(outgoingTransition);
			}
		}
		return outgoingTransitionOfTheDestinationState;
	}

	public void testReachabilityElementsState14State16(Constraint constraint) {
		Set<ColoredPluggingTransition> outgoingTransitions = this
				.getOutgoingTransition(this.state14, constraint
						.getSubProperties().iterator().next()
						.getOutgoingTransitions());
		assertTrue(
				"The expected number of outgoing transitions for the state 14 is one",
				outgoingTransitions.size() == 1);

		Set<ColoredPluggingTransition> incomingTransition = this
				.getIncomingTransition(this.state16, constraint
						.getSubProperties().iterator().next()
						.getIncomingTransitions());
		assertTrue(
				"The expected number of incoming transitions for the state 16 is one",
				incomingTransition.size() == 1);

		Map<Map.Entry<ColoredPluggingTransition, ColoredPluggingTransition>, Boolean> map = constraint
				.getSubProperties().iterator().next()
				.getLowerReachabilityRelation().getReachabilityAcceptingMap();
		assertTrue(
				"The outgoing transition of the state 14 and the incoming transition of the state 16 must be connected",
				map.containsKey(new AbstractMap.SimpleEntry<ColoredPluggingTransition, ColoredPluggingTransition>(
						outgoingTransitions.iterator().next(),
						incomingTransition.iterator().next())));

		assertTrue(
				"The reachability reachability replation between the state 14 and the state 15 must be accepting",
				map.get(new AbstractMap.SimpleEntry<ColoredPluggingTransition, ColoredPluggingTransition>(
						outgoingTransitions.iterator().next(),
						incomingTransition.iterator().next()))==true);
	}

}
