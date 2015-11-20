/**
 * 
 */
package it.polimi.constraints.io.in.constraint;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * @author Claudio1
 *
 */
public class ElementToSubPropertyTransformerTest {

	/**
	 * Test method for {@link it.polimi.constraints.io.in.constraint.ElementToSubPropertyTransformer#ElementToSubPropertyTransformer()}.
	 */
	@Test
	public void testElementToSubPropertyTransformer() {
		assertNotNull(new ElementToSubPropertyTransformer());
	}

	/**
	 * Test method for {@link it.polimi.constraints.io.in.constraint.ElementToSubPropertyTransformer#transform(org.w3c.dom.Element)}.
	 */
	@Test(expected=NullPointerException.class)
	public void testTransformNull() {
		new ElementToSubPropertyTransformer().transform(null);
	}

}
