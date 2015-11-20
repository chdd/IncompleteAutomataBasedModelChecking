/**
 * 
 */
package it.polimi.model.ltltoba;

import static org.junit.Assert.*;
import it.polimi.automata.BA;

import org.junit.Test;

/**
 * @author Claudio1
 *
 */
public class LTLtoBATransformerTest {

	/**
	 * Test method for
	 * {@link it.polimi.model.ltltoba.LTLtoBATransformer#LTLtoBATransformer(java.lang.String)}
	 * .
	 */
	@Test
	public void testLTLtoBATransformer() {
		assertNotNull(new LTLtoBATransformer("a"));
	}

	/**
	 * Test method for
	 * {@link it.polimi.model.ltltoba.LTLtoBATransforme
 * @throws Exception r#perform()}.
	 */
	@Test
	public void testPerform() throws Exception {
		LTLtoBATransformer ltlToBATransformer;
		ltlToBATransformer = new LTLtoBATransformer("[]a");

		BA ba = ltlToBATransformer.perform();

		assertTrue(ba.getInitialStates().size() == 1);
		assertTrue(ba.getAcceptStates().size() == 1);
		assertTrue(ba.getStates().size() == 1);
		assertTrue(ba.getTransitions().size() == 1);
		assertTrue(!ba.getInitialStates().isEmpty());
		assertTrue(!ba.getAcceptStates().isEmpty());
		assertTrue(!ba.getStates().isEmpty());
		assertTrue(ba.getTransitions().size() == 1);

	}

}
