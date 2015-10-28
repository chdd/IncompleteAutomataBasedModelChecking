/**
 * 
 */
package it.polimi.automata.io.out;

import static org.junit.Assert.*;
import it.polimi.automata.BA;
import it.polimi.automata.state.StateFactory;
import it.polimi.automata.transition.ClaimTransitionFactory;

import org.junit.Test;

import rwth.i2.ltl2ba4j.model.impl.GraphProposition;

/**
 * @author Claudio1
 *
 */
public class ClaimToStringTrasformerTest {

	/**
	 * Test method for
	 * {@link it.polimi.automata.io.out.ClaimToStringTrasformer#ClaimToStringTrasformer(it.polimi.automata.BA)}
	 * .
	 */
	@Test
	public void testClaimToStringTrasformer() {
		assertNotNull("The constructor returns a not null object",
				new ClaimToStringTrasformer(
						new BA(new ClaimTransitionFactory())));
	}

	/**
	 * Test method for
	 * {@link it.polimi.automata.io.out.ClaimToStringTrasformer#ClaimToStringTrasformer(it.polimi.automata.BA)}
	 * .
	 */
	@Test(expected = NullPointerException.class)
	public void testClaimToStringTrasformerNull() {
		new ClaimToStringTrasformer(null);
	}

	/**
	 * Test method for
	 * {@link it.polimi.automata.io.out.ClaimToStringTrasformer#perform()}.
	 * @throws Exception 
	 */
	@Test
	public void testPerform() throws Exception {
		
		BA ba=new BA(new ClaimTransitionFactory());
		ba.addProposition(new GraphProposition("label", false));
		ba.addInitialState(new StateFactory().create("state1", 1));
		ba.addState(new StateFactory().create("state2", 2));
		ba.addAcceptState(new StateFactory().create("state3", 3));
		assertNotNull(new ClaimToStringTrasformer(ba).perform());
	}

}
