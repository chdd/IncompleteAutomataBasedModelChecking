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
public class IBAFactoryImplTest {

	/**
	 * Test method for {@link it.polimi.automata.impl.IBAFactoryImpl#create()}.
	 */
	@Test
	public void testCreate() {
		assertNotNull(new IBAFactoryImpl<Label, State, Transition<Label>>().create());
	}

}
