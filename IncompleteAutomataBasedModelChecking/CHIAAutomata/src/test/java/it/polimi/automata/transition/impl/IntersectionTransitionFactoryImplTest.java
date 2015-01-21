/**
 * 
 */
package it.polimi.automata.transition.impl;

import static org.junit.Assert.*;
import it.polimi.automata.labeling.Label;

import org.junit.Test;

/**
 * @author claudiomenghi
 *
 */
public class IntersectionTransitionFactoryImplTest {

	@Test
	public void test() {
		assertNotNull(new IntersectionTransitionFactoryImpl<Label>());
	}

}
