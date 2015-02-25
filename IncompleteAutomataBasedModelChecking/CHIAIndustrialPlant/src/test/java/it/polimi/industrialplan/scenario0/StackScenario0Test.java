/**
 * 
 */
package it.polimi.industrialplan.scenario0;

import static org.junit.Assert.assertTrue;
import it.polimi.CHIA;
import it.polimi.automata.BA;
import it.polimi.automata.IBA;
import it.polimi.automata.io.BAReader;
import it.polimi.automata.io.IBAReader;
import it.polimi.automata.state.State;
import it.polimi.automata.state.StateFactory;
import it.polimi.automata.state.impl.StateFactoryImpl;
import it.polimi.automata.transition.IntersectionTransition;
import it.polimi.automata.transition.Transition;
import it.polimi.automata.transition.TransitionFactory;
import it.polimi.automata.transition.impl.TransitionFactoryClaimImpl;
import it.polimi.automata.transition.impl.TransitionFactoryIntersectionImpl;
import it.polimi.automata.transition.impl.TransitionFactoryModelImpl;

import java.io.File;
import java.io.FileNotFoundException;

import org.junit.Test;


/**
 * @author claudiomenghi
 * 
 */
public class StackScenario0Test {

	/**
	 * Test method for
	 * {@link it.polimi.CHIA#CHIA(it.polimi.automata.BA, it.polimi.automata.IBA)}
	 * .
	 * 
	 * @throws FileNotFoundException
	 * @throws GraphIOException
	 */
	@Test
	public void testCHIA() throws FileNotFoundException {
		BAReader< State, StateFactory<State>, Transition, TransitionFactory<State, Transition>> claimReader = new BAReader
				< State, StateFactory<State>, Transition, TransitionFactory<State, Transition>>(
				new TransitionFactoryClaimImpl<State>(),
				new StateFactoryImpl(),
				new File(getClass().getClassLoader()
						.getResource("industrialplan/scenario0/Scenario0StackClaim.xml").getFile()));

		BA< State, Transition> claim = claimReader.read();

		IBAReader<State, StateFactory<State>, Transition, TransitionFactory<State, Transition>> modelReader = 
				new IBAReader<State, StateFactory<State>, Transition, TransitionFactory<State, Transition>>
				(
				 new TransitionFactoryModelImpl<State>(),
				new StateFactoryImpl(),
				new File(getClass().getClassLoader()
						.getResource("industrialplan/scenario0/Scenario0Stack.xml").getFile()));

		IBA< State, Transition> model = modelReader.read();
		CHIA<State, Transition, IntersectionTransition<State>> chia = new CHIA<State, Transition, IntersectionTransition<State>>(claim, model
				, new StateFactoryImpl(), new TransitionFactoryIntersectionImpl<State>());
		int result = chia.check();
		assertTrue(result == 1);
		
		
	}
}
