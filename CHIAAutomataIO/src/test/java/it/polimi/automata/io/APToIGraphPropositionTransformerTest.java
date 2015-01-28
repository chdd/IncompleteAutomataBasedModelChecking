/**
 * 
 */
package it.polimi.automata.io;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import rwth.i2.ltl2ba4j.model.IGraphProposition;
import rwth.i2.ltl2ba4j.model.impl.GraphProposition;

/**
 * @author claudiomenghi
 *
 */
public class APToIGraphPropositionTransformerTest {

	private IGraphProposition proposition1;
	private IGraphProposition proposition2;
	@Before
	public void setUp() {
		proposition1=new GraphProposition("abc", false);
		proposition2=new GraphProposition("abc", true);
	}
	
	/**
	 * Test method for {@link it.polimi.automata.io.APToIGraphPropositionTransformer#transform(null)}.
	 */
	@Test(expected = NullPointerException.class)
	public void testTransformNull() {
		new APToIGraphPropositionTransformer().transform(null);
	}
	
	/**
	 * Test method for {@link it.polimi.automata.io.APToIGraphPropositionTransformer#transform(IllegalArgument)}.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testTransformIllegalArgument() {
		new APToIGraphPropositionTransformer().transform("12a");
	}
	
	/**
	 * Test method for {@link it.polimi.automata.io.APToIGraphPropositionTransformer#transform(IllegalArgument)}.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testTransformIllegalArgument2() {
		new APToIGraphPropositionTransformer().transform("!sadsa!");
	}

	
	/**
	 * Test method for {@link it.polimi.automata.io.APToIGraphPropositionTransformer#transform(java.lang.String)}.
	 */
	@Test
	public void testTransform() {
		assertEquals(proposition1, new APToIGraphPropositionTransformer().transform("abc"));
		assertEquals(proposition2, new APToIGraphPropositionTransformer().transform("!abc"));
	}

}
