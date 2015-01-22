/**
 * 
 */
package it.polimi.automata.impl;

import static org.junit.Assert.*;
import it.polimi.automata.labeling.Label;
import it.polimi.automata.state.State;
import it.polimi.automata.transition.Transition;

import org.junit.Test;

/**
 * @author claudiomenghi
 *
 */
public class IntBAFactoryImplTest {

	/**
	 * Test method for {@link it.polimi.automata.impl.IntBAFactoryImpl#create()}.
	 */
	@Test
	public void testCreate() {
		assertNotNull(new IntBAFactoryImpl<Label, State, Transition<Label>>());
	}

}
