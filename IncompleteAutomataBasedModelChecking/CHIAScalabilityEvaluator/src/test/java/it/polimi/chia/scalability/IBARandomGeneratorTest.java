/**
 * 
 */
package it.polimi.chia.scalability;

import static org.junit.Assert.*;
import it.polimi.automata.BA;
import it.polimi.automata.IBA;
import it.polimi.automata.state.StateFactory;
import it.polimi.constraints.components.Replacement;

import org.junit.Test;

/**
 * @author Claudio Menghi
 *
 */
public class IBARandomGeneratorTest {

	@Test
	public void test() {

		double transparentStateDensity = 0.5;
		double replacementDensity = 0.5;
		BARandomGeneratorTest baRndGn = new BARandomGeneratorTest();
		BA ba = baRndGn.setUp();
		System.out.println(ba);

		StateFactory stateFactory = new StateFactory();
		IBARandomGenerator ibaRndGen = new IBARandomGenerator(ba, stateFactory,
				transparentStateDensity, replacementDensity);
		IBA iba = ibaRndGen.perform();
		System.out.println(iba);

		assertTrue(
				"The number of transparent states of the iba must be consistent with the transparent state density",
				iba.getBlackBoxStates().size() == Math.round(ba.getStates()
						.size() * transparentStateDensity));
		assertTrue(
				"The number of transparent states of the iba must be consistent with the replacement density",
				iba.getStates().size() == (Math.round(ba.getStates().size()
						* transparentStateDensity)
						+ ba.getStates().size() - Math.round(ba.getStates()
						.size() * replacementDensity)));

		for (Replacement rep : ibaRndGen.getTransparentStateReplacementMap()
				.values()) {
			assertTrue(
					"The number "
							+ rep.getAutomaton().getStates().size()
							+ " of the states of the replacement must be consistent with the replacement density: the expected number of states is: "+
							(int) Math
							.round((ba.getStates().size() - 1) * replacementDensity),
					rep.getAutomaton().getStates().size() ==(int) Math
							.round((ba.getStates().size() - 1) * replacementDensity));
		}
	}
}
