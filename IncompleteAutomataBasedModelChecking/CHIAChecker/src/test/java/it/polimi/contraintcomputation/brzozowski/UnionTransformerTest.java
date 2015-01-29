/**
 * 
 */
package it.polimi.contraintcomputation.brzozowski;

import static org.junit.Assert.assertEquals;
import it.polimi.Constants;

import java.util.AbstractMap;

import org.junit.Test;

/**
 * @author claudiomenghi
 *
 */
public class UnionTransformerTest {

	/**
	 * Test method for {@link it.polimi.contraintcomputation.brzozowski.UnionTransformer#transform(null)}.
	 */
	@Test(expected=NullPointerException.class)
	public void testTransformNull() {
		new UnionTransformer(Constants.UNIONPLUS).transform(null);
	}

	
	/**
	 * Test method for {@link it.polimi.contraintcomputation.brzozowski.UnionTransformer#transform(java.util.Map.Entry)}.
	 */
	@Test
	public void testTransform() {
		UnionTransformer transformer=new UnionTransformer(Constants.UNIONPLUS);
		assertEquals("a",transformer.transform(new AbstractMap.SimpleEntry<String, String>(Constants.EMPTYSET, "a")));
		assertEquals("a",transformer.transform(new AbstractMap.SimpleEntry<String, String>("a", Constants.EMPTYSET)));
		assertEquals("((a)+(b))",transformer.transform(new AbstractMap.SimpleEntry<String, String>("a", "b")));
	}

}
