/**
 * 
 */
package it.polimi.contraintcomputation.brzozowski;

import static org.junit.Assert.*;
import it.polimi.Constants;

import org.junit.Test;

/**
 * @author claudiomenghi
 *
 */
public class IneffectiveStarTransformerTest {


	/**
	 * Test method for {@link it.polimi.contraintcomputation.brzozowski.IneffectiveStarTransformer#transform(null)}.
	 */
	@Test(expected=NullPointerException.class)
	public void testTransformNull() {
		new IneffectiveStarTransformer().transform(null);
	}
	
	/**
	 * Test method for {@link it.polimi.contraintcomputation.brzozowski.IneffectiveStarTransformer#transform(java.lang.String)}.
	 */
	@Test
	public void testTransform() {
		String input="a";
		assertTrue("a".equals(new IneffectiveStarTransformer().transform(input)));
		assertTrue(Constants.LAMBDA.equals(new IneffectiveStarTransformer().transform(Constants.EMPTYSET)));
		assertTrue(Constants.LAMBDA.equals(new IneffectiveStarTransformer().transform(Constants.LAMBDA)));

	}
}
