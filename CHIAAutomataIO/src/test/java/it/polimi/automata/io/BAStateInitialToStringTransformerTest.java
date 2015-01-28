/**
 * 
 */
package it.polimi.automata.io;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;
import it.polimi.Constants;
import it.polimi.automata.BA;
import it.polimi.automata.labeling.Label;
import it.polimi.automata.state.State;
import it.polimi.automata.transition.Transition;

import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

/**
 * @author claudiomenghi
 * 
 */
public class BAStateInitialToStringTransformerTest {

	@Mock
	private BA<Label, State, Transition<Label>> ba;

	@Mock
	private State state1;

	@Mock
	private State state2;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		Set<State> returnedSet = new HashSet<State>();
		returnedSet.add(state1);
		when(ba.getInitialStates()).thenReturn(returnedSet);
	}

	/**
	 * Test method for
	 * {@link it.polimi.automata.io.BAStateInitialToStringTransformer#BAStateInitialToStringTransformer(null)}
	 * .
	 */
	@Test(expected = NullPointerException.class)
	public void testBAStateInitialToStringTransformerNull() {

		new BAStateInitialToStringTransformer<Label, State, Transition<Label>>(
				null);
	}

	/**
	 * Test method for
	 * {@link it.polimi.automata.io.BAStateInitialToStringTransformer#BAStateInitialToStringTransformer(it.polimi.automata.BA)}
	 * .
	 */
	@Test
	public void testBAStateInitialToStringTransformer() {
		assertNotNull(new BAStateInitialToStringTransformer<Label, State, Transition<Label>>(
				ba));
	}

	/**
	 * Test method for
	 * {@link it.polimi.automata.io.BAStateInitialToStringTransformer#transform(null)}
	 * .
	 */
	@Test(expected = NullPointerException.class)
	public void testTransformNull() {
		BAStateInitialToStringTransformer<Label, State, Transition<Label>> initialTransformer=new BAStateInitialToStringTransformer<Label, State, Transition<Label>>(
				ba);
		initialTransformer.transform(null);
	}
	
	/**
	 * Test method for
	 * {@link it.polimi.automata.io.BAStateInitialToStringTransformer#transform(it.polimi.automata.state.State)}
	 * .
	 */
	@Test
	public void testTransform() {
		BAStateInitialToStringTransformer<Label, State, Transition<Label>> initialTransformer=new BAStateInitialToStringTransformer<Label, State, Transition<Label>>(
				ba);
		assertEquals(initialTransformer.transform(state1), Constants.TRUEVALUE);
		assertFalse(initialTransformer.transform(state2).equals(Constants.TRUEVALUE));
	}

}
