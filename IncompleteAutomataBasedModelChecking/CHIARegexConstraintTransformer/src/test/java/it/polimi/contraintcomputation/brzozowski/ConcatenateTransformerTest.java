/**
 * 
 */
package it.polimi.contraintcomputation.brzozowski;

import static org.junit.Assert.*;
import it.polimi.contraintcomputation.Constants;

import java.util.AbstractMap;

import org.junit.Test;

/**
 * @author claudiomenghi
 *
 */
public class ConcatenateTransformerTest {

	
	/**
	 * Test method for {@link it.polimi.contraintcomputation.brzozowski.ConcatenateTransformer#transform(null)}.
	 */
	@Test(expected=NullPointerException.class)
	public void testTransformNull() {
		new ConcatenateTransformer(Constants.CONCATENATIONDOT).transform(null);
	}

	
	/**
	 * Test method for {@link it.polimi.contraintcomputation.brzozowski.ConcatenateTransformer#transform(java.util.Map.Entry)}.
	 */
	@Test
	public void testTransform() {
		ConcatenateTransformer transformer=new ConcatenateTransformer(Constants.CONCATENATIONDOT);
		assertEquals(Constants.EMPTYSET,transformer.transform(new AbstractMap.SimpleEntry<String, String>(Constants.EMPTYSET, "a")));
		assertEquals(Constants.EMPTYSET,transformer.transform(new AbstractMap.SimpleEntry<String, String>("a", Constants.EMPTYSET)));
		assertEquals("a",transformer.transform(new AbstractMap.SimpleEntry<String, String>(Constants.LAMBDA, "a")));
		assertEquals("a",transformer.transform(new AbstractMap.SimpleEntry<String, String>("a", Constants.LAMBDA)));
		
		assertEquals("a.b",transformer.transform(new AbstractMap.SimpleEntry<String, String>("a", "b")));
	}

}
