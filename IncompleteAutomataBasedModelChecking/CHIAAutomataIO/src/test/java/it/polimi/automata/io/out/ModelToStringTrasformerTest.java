/**
 * 
 */
package it.polimi.automata.io.out;

import static org.junit.Assert.assertNotNull;
import it.polimi.automata.IBA;
import it.polimi.automata.state.State;
import it.polimi.automata.state.StateFactory;
import it.polimi.automata.transition.ModelTransitionFactory;

import org.junit.Test;

import rwth.i2.ltl2ba4j.model.impl.GraphProposition;

/**
 * @author Claudio1
 *
 */
public class ModelToStringTrasformerTest {

	/**
	 * Test method for
	 * {@link it.polimi.automata.io.out.ClaimToStringTrasformer#ClaimToStringTrasformer(it.polimi.automata.BA)}
	 * .
	 */
	@Test
	public void testModelToStringTrasformer() {
		assertNotNull("The constructor returns a not null object",
				new ModelToStringTrasformer(
						new IBA(new ModelTransitionFactory())));
	}

	/**
	 * Test method for
	 * {@link it.polimi.automata.io.out.ClaimToStringTrasformer#ClaimToStringTrasformer(it.polimi.automata.BA)}
	 * .
	 */
	@Test(expected = NullPointerException.class)
	public void testModelToStringTrasformerNull() {
		new ModelToStringTrasformer(null);
	}

	/**
	 * Test method for
	 * {@link it.polimi.automata.io.out.ClaimToStringTrasformer#perform()}.
	 * @throws Exception 
	 */
	@Test
	public void testPerform() throws Exception {
		
		IBA iba=new IBA(new ModelTransitionFactory());
		iba.addProposition(new GraphProposition("label", false));
		
		State state1=new StateFactory().create("state1", 1);
		State state2=new StateFactory().create("state2", 2);
		State state3=new StateFactory().create("state3", 3);
		
		iba.addInitialState(state1);
		iba.addState(state2);
		iba.addAcceptState(state3);
		
		iba.addTransition(state1, state2, new ModelTransitionFactory().create());
		assertNotNull(new ModelToStringTrasformer(iba).perform());
	}

}
