/**
 * 
 */
package it.polimi.casestudies.sendingmessage.constraintcomputation;

import static org.junit.Assert.assertTrue;
import it.polimi.CHIA;
import it.polimi.automata.BA;
import it.polimi.automata.IBA;
import it.polimi.automata.io.BAReader;
import it.polimi.automata.io.IBAReader;
import it.polimi.automata.io.transformer.states.BAStateElementParser;
import it.polimi.automata.io.transformer.states.IBAStateElementParser;
import it.polimi.automata.io.transformer.states.StateElementParser;
import it.polimi.automata.io.transformer.transitions.BATransitionParser;
import it.polimi.automata.io.transformer.transitions.ClaimTransitionParser;
import it.polimi.automata.io.transformer.transitions.IBATransitionParser;
import it.polimi.automata.io.transformer.transitions.ModelTransitionParser;
import it.polimi.automata.state.State;
import it.polimi.automata.state.StateFactory;
import it.polimi.automata.transition.Transition;
import it.polimi.automata.transition.ClaimTransitionFactory;
import it.polimi.automata.transition.ModelTransitionFactory;
import it.polimi.checker.intersection.impl.IntersectionRule;
import it.polimi.constraints.Constraint;
import it.polimi.constraints.io.out.ConstraintWriter;

import java.io.File;
import java.io.FileNotFoundException;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author claudiomenghi
 * 
 */
public class SendingMessageTest {

	/**
	 * is the logger of the ModelChecker class
	 */
	private static final Logger logger = LoggerFactory
			.getLogger(SendingMessageTest.class);

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
		
		StateElementParser<State, Transition, BA<State,Transition>> stateElementParser=new BAStateElementParser(new StateFactory());
		
		ClaimTransitionParser<State, Transition, BA<State,Transition>> transitionElementParser=new 
				BATransitionParser(new ClaimTransitionFactory<State>());
		
		BAReader< State, Transition> claimReader = 
				new BAReader<State, Transition>(
				new File(getClass().getClassLoader()
						.getResource("it/polimi/casestudies/sendingmessage/SendingMessageClaim.xml").getFile()), 
						stateElementParser,
						transitionElementParser);

		BA<State, Transition> claim = claimReader.read();

		ModelTransitionParser<State, Transition, IBA<State,Transition>> modelRransitionElementParser=new 
				IBATransitionParser<State, Transition, IBA<State,Transition>>(new ModelTransitionFactory<State>());
		
		StateElementParser<State, Transition, IBA<State,Transition>> ibaStateElementParser=new IBAStateElementParser(new StateFactory());
		
		
		IBAReader<State, Transition> modelReader = new IBAReader<State,  Transition>(
				new File(getClass().getClassLoader()
						.getResource("it/polimi/casestudies/sendingmessage/SendingMessageModel.xml").getFile()),
						ibaStateElementParser,
						modelRransitionElementParser);
		IBA<State, Transition> model = modelReader.read();
		CHIA<State, Transition> chia = new CHIA<State, Transition>(
				claim, model,
				new IntersectionRule<State, Transition>(new ClaimTransitionFactory<State>(),	new StateFactory()), new ClaimTransitionFactory<State>());
		int result = chia.check();
		assertTrue(result == -1);

		Constraint<State, Transition, BA<State, Transition>> constraint = chia.getConstraint();

		new ConstraintWriter<State, Transition, BA<State, Transition>>(constraint, new File(
				"/Users/Claudio1/Desktop/CHIAResults/Results.xml")).write();
		System.out.println(constraint.toString());

	}

}
