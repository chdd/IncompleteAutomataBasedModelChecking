/**
 * 
 */
package it.polimi.automata.io.iba.transformers;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import it.polimi.Constants;
import it.polimi.automata.IBA;
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
public class IBAStateTransparentToStringTransformerTest {

	@Mock
	private IBA<Label, State, Transition<Label>> iba;

	@Mock
	private State state1;

	@Mock
	private State state2;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		when(iba.isTransparent(state1)).thenReturn(true);
		when(iba.isTransparent(state2)).thenReturn(false);
	}

	/**
	 * Test method for
	 * {@link it.polimi.automata.io.iba.transformers.IBAStateTransparentToStringTransformer#IBAStateTransparentToStringTransformer(null)}
	 * .
	 */
	@Test(expected = NullPointerException.class)
	public void testIBAStateTransparentToStringTransformerNull() {
		new IBAStateTransparentToStringTransformer<Label, State, Transition<Label>>(
				null);
	}

	/**
	 * Test method for
	 * {@link it.polimi.automata.io.iba.transformers.IBAStateTransparentToStringTransformer#IBAStateTransparentToStringTransformer(it.polimi.automata.IBA)}
	 * .
	 */
	@Test
	public void testIBAStateTransparentToStringTransformer() {
		assertNotNull(new IBAStateTransparentToStringTransformer<Label, State, Transition<Label>>(
				iba));
	}

	/**
	 * Test method for
	 * {@link it.polimi.automata.io.iba.transformers.IBAStateTransparentToStringTransformer#transform(it.polimi.automata.state.State)}
	 * .
	 */
	@Test
	public void testTransform() {
		IBAStateTransparentToStringTransformer<Label, State, Transition<Label>> transformer = new IBAStateTransparentToStringTransformer<Label, State, Transition<Label>>(
				iba);
		assertEquals(transformer.transform(state1), Constants.TRUEVALUE);
		assertEquals(transformer.transform(state2),  Constants.FALSEVALUE);
	}

}
