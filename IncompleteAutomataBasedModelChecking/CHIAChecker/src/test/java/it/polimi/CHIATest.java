/**
 * 
 */
package it.polimi;

import static org.junit.Assert.assertTrue;
import it.polimi.automata.BA;
import it.polimi.automata.IBA;
import it.polimi.automata.io.BAReader;
import it.polimi.automata.io.IBAReader;
import it.polimi.automata.state.State;
import it.polimi.automata.state.StateFactory;
import it.polimi.automata.state.impl.StateFactoryImpl;
import it.polimi.automata.transition.Transition;
import it.polimi.automata.transition.TransitionFactory;
import it.polimi.automata.transition.impl.TransitionFactoryClaimImpl;
import it.polimi.automata.transition.impl.TransitionFactoryModelImpl;

import java.io.File;
import java.io.FileNotFoundException;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author claudiomenghi
 * 
 */
public class CHIATest {

	/**
	 * is the logger of the ModelChecker class
	 */
	private static final Logger logger = LoggerFactory
			.getLogger(CHIATest.class);
	
	
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
		logger.info("Running the test: testCHIA");
		BAReader< State, StateFactory<State>, Transition, TransitionFactory<State, Transition>> claimReader = 
				new BAReader< State, StateFactory<State>, Transition, TransitionFactory<State, Transition>>(
				new TransitionFactoryClaimImpl<State>(Transition.class),
				new StateFactoryImpl(),
				new File(getClass().getClassLoader()
						.getResource("sendingmessage/SendingMessageClaim.xml").getFile()));

		BA<State, Transition> claim = claimReader.read();

		IBAReader<State, StateFactory<State>, Transition, TransitionFactory<State, Transition>> modelReader = new IBAReader<State, StateFactory<State>, Transition, TransitionFactory<State, Transition>>(
				 new TransitionFactoryModelImpl<State>(Transition.class),
				new StateFactoryImpl(),
				new File(getClass().getClassLoader()
						.getResource("sendingmessage/SendingMessageModel.xml").getFile()));

		IBA<State, Transition> model = modelReader.read();
		CHIA chia = new CHIA(claim, model);
		int result = chia.check();
		assertTrue(result == -1);

		String constraint = chia.getConstraint();
		
		assertTrue(("¬((([@q1- [start]@([<SIGMA>])*@send2- [fail]@]∧[@send1- [fail]@([<SIGMA>])*.[send^!success].([!success])*@q2- [fail]@])∨([@q1- [start]@([<SIGMA>])*.[send^!success].([!success])*@send2- [fail]@]∧[@send1- [fail]@([!success])*@q2- [fail]@])))")
				.equals(constraint)
				|| ("¬((([@q1- [start]@([<SIGMA>])*.[send^!success].([!success])*@send2- [fail]@]∧[@send1- [fail]@([!success])*@q2- [fail]@])∨([@q1- [start]@([<SIGMA>])*@send2- [fail]@]∧[@send1- [fail]@([<SIGMA>])*.[send^!success].([!success])*@q2- [fail]@])))")
				.equals(constraint));
	}

	
}
