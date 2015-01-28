/**
 * 
 */
package it.polimi.contraintcomputation.brzozowski;

import static org.junit.Assert.*;
import it.polimi.automata.Constants;

import org.junit.Test;

/**
 * @author claudiomenghi
 *
 */
public class StarTransformerTest {

	/**
	 * Test method for {@link it.polimi.contraintcomputation.brzozowski.StarTransformer#transform(null)}.
	 */
	@Test(expected=NullPointerException.class)
	public void testTransformNull() {
		new StarTransformer().transform(null);
	}
	
	/**
	 * Test method for {@link it.polimi.contraintcomputation.brzozowski.StarTransformer#transform(java.lang.String)}.
	 */
	@Test
	public void testTransform() {
		String input="a";
		assertTrue("(a)*".equals(new StarTransformer().transform(input)));
		assertTrue(Constants.LAMBDA.equals(new StarTransformer().transform(Constants.EMPTYSET)));
		assertTrue(Constants.LAMBDA.equals(new StarTransformer().transform(Constants.LAMBDA)));

	}

}
