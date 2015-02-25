/**
 * 
 */
package it.polimi.automata.transition.impl;

import static org.junit.Assert.assertNotNull;
import it.polimi.automata.state.State;

import org.junit.Test;

/**
 * @author claudiomenghi
 *
 */
public class IntersectionTransitionFactoryImplTest {

	@Test
	public void test() {
		assertNotNull(new TransitionFactoryIntersectionImpl<State>());
	}

}
