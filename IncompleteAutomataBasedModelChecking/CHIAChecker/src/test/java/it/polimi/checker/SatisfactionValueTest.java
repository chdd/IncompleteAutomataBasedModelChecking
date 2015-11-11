/**
 * 
 */
package it.polimi.checker;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * @author Claudio Menghi
 *
 */
public class SatisfactionValueTest {

	/**
	 * Test method for {@link it.polimi.checker.SatisfactionValue#toString()}.
	 */
	@Test
	public void testToString() {
		assertEquals("The satisfaction value SATISFIED is correct",
				"SATISFIED", SatisfactionValue.SATISFIED.toString());
		assertEquals("The satisfaction value NOT SATISFIED is correct",
				"NOT SATISFIED", SatisfactionValue.NOTSATISFIED.toString());
		assertEquals("The satisfaction value POSSIBLY SATISFIED is correct",
				"POSSIBLY SATISFIED",
				SatisfactionValue.POSSIBLYSATISFIED.toString());
	}

}
