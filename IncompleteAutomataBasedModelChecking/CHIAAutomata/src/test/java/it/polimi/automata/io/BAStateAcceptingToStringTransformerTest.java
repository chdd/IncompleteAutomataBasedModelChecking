/**
 * 
 */
package it.polimi.automata.io;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.Set;

import it.polimi.Constants;
import it.polimi.automata.BA;
import it.polimi.automata.labeling.Label;
import it.polimi.automata.state.State;
import it.polimi.automata.transition.Transition;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

/**
 * @author claudiomenghi
 * 
 */
public class BAStateAcceptingToStringTransformerTest {

	@Mock
	private BA<Label, State, Transition<Label>> ba;

	@Mock
	private State state1;

	@Mock
	private State state2;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		Set<State> returnedSet=new HashSet<State>();
		returnedSet.add(state1);
		when(ba.getAcceptStates()).thenReturn(returnedSet);
	}

	/**
	 * Test method for
	 * {@link it.polimi.automata.io.BAStateAcceptingToStringTransformer#BAStateAcceptingToStringTransformer(null)}
	 * .
	 */
	@Test(expected = NullPointerException.class)
	public void testBAStateAcceptingToStringTransformerNull() {
		new BAStateAcceptingToStringTransformer<Label, State, Transition<Label>>(
				null);
	}

	/**
	 * Test method for
	 * {@link it.polimi.automata.io.BAStateAcceptingToStringTransformer#BAStateAcceptingToStringTransformer(it.polimi.automata.BA)}
	 * .
	 */
	@Test
	public void testBAStateAcceptingToStringTransformer() {
		assertNotNull(new BAStateAcceptingToStringTransformer<Label, State, Transition<Label>>(
				ba));
	}

	/**
	 * Test method for
	 * {@link it.polimi.automata.io.BAStateAcceptingToStringTransformer#transform(null)}
	 * .
	 */
	@Test(expected = NullPointerException.class)
	public void testTransformNull() {
		BAStateAcceptingToStringTransformer<Label, State, Transition<Label>> transformer = new BAStateAcceptingToStringTransformer<Label, State, Transition<Label>>(
				ba);
		transformer.transform(null);
	}
	
	/**
	 * Test method for
	 * {@link it.polimi.automata.io.BAStateAcceptingToStringTransformer#transform(it.polimi.automata.state.State)}
	 * .
	 */
	@Test
	public void testTransform() {
		BAStateAcceptingToStringTransformer<Label, State, Transition<Label>> transformer = new BAStateAcceptingToStringTransformer<Label, State, Transition<Label>>(
				ba);
		assertEquals(transformer.transform(state1), Constants.TRUEVALUE);
		assertEquals(transformer.transform(state2),  Constants.FALSEVALUE);
	}

}
