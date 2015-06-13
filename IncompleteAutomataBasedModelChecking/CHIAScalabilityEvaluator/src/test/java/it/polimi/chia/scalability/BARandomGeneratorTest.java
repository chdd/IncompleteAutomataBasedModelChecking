/**
 * 
 */
package it.polimi.chia.scalability;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import it.polimi.automata.BA;
import it.polimi.automata.state.State;
import it.polimi.automata.state.StateFactory;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import org.junit.Test;

import rwth.i2.ltl2ba4j.model.IGraphProposition;
import rwth.i2.ltl2ba4j.model.impl.GraphProposition;

/**
 * Tests the correct behavior of the BA generator
 *
 * @author Claudio Menghi
 * @since June 11, 2015
 */
public class BARandomGeneratorTest {

	private Random random;
	private State state1;
	private State state2;
	private State state3;

	/**
	 * It tests the method which returns a random Buchi automaton. It generates
	 * a random automaton with 3 accepting states, an acceptance density of 0.3
	 * and a transition density of 2
	 */
	@Test
	public void testGenerateRandomBA() {

		BA ba = this.setUp();

		assertTrue(ba.getInitialStates().size() == 1);
		assertTrue(ba
				.getOutTransitions(ba.getInitialStates().iterator().next())
				.size() == 2);
		assertTrue(ba.getStates().contains(state1));
		assertTrue(ba.getStates().contains(state2));
		assertTrue(ba.getStates().contains(state3));
		assertTrue(ba.getAcceptStates().size() == 1);
		assertTrue(ba.getTransitions(state1, state2).size() == 4);
		assertTrue(ba.getTransitions(state2, state2).size() == 4);
		assertTrue(ba
				.getOutTransitions(ba.getInitialStates().iterator().next())
				.size() == 2);

	}

	protected BA setUp() {
		StateFactory stateFactory = mock(StateFactory.class);
		int numStates = 3;
		double acceptanceDensity = 0.3;
		double transitionDensity = 2;

		state1 = new StateFactory().create();
		state2 = new StateFactory().create();
		state3 = new StateFactory().create();

		when(stateFactory.create()).thenReturn(state1).thenReturn(state2)
				.thenReturn(state3);

		random = mock(Random.class);

		when(random.nextInt(numStates - 1)).thenReturn(0).thenReturn(1)
				.thenReturn(0).thenReturn(1).thenReturn(0).thenReturn(1)
				.thenReturn(0).thenReturn(1);

		IGraphProposition proposition1 = new GraphProposition("a", false);
		IGraphProposition proposition2 = new GraphProposition("b", false);

		Set<IGraphProposition> propositions = new HashSet<IGraphProposition>();
		propositions.add(proposition1);
		propositions.add(proposition2);

		BARandomGenerator baRandGenerator = new BARandomGenerator(propositions,
				stateFactory, transitionDensity, acceptanceDensity, numStates,
				random);
		BA ba = baRandGenerator.generateRandomBA();
		return ba;
	}
}
