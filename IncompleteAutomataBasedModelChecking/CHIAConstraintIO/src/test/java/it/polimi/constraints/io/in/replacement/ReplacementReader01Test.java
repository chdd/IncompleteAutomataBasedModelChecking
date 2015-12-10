/**
 * 
 */
package it.polimi.constraints.io.in.replacement;

import static org.junit.Assert.assertNotNull;

import java.io.File;

import org.junit.Test;

/**
 * @author Claudio Menghi
 *
 */
public class ReplacementReader01Test {

	private static final String path = "it.polimi.constraints/io/in/replacement/test01/";

	
	/**
	 * Test method for
	 * {@link it.polimi.constraints.io.in.replacement.ElementToReplacementTransformer#transform(org.w3c.dom.Element)}
	 * .
	 * @throws Exception 
	 */
	@Test
	public void testTransformShouldCorrectlyLoadIBA()
			throws Exception {

		new File(getClass()
		.getClassLoader().getResource(path + "ReplacementSwitchToGoTwo.xml").getFile());
		new ReplacementReader(new File(getClass()
				.getClassLoader().getResource(path + "ReplacementSwitchToGoTwo.xml")
				.getFile()));
		assertNotNull(new ReplacementReader(new File(getClass()
				.getClassLoader().getResource(path + "ReplacementSwitchToGoTwo.xml")
				.getFile())).perform());
	}

	

}
