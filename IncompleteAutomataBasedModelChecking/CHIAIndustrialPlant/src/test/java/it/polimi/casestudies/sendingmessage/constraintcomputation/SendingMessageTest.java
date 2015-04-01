/**
 * 
 */
package it.polimi.casestudies.sendingmessage.constraintcomputation;

import static org.junit.Assert.assertTrue;
import it.polimi.CHIA;
import it.polimi.automata.BA;
import it.polimi.automata.IBA;
import it.polimi.automata.io.in.BAReader;
import it.polimi.automata.io.in.IBAReader;
import it.polimi.automata.io.in.states.ElementToBAStateTransformer;
import it.polimi.automata.io.in.states.ElementToIBAStateTransformer;
import it.polimi.automata.io.in.transitions.ElementToBATransitionTransformer;
import it.polimi.automata.io.in.transitions.ElementToIBATransitionTransformer;
import it.polimi.automata.state.State;
import it.polimi.automata.state.StateFactory;
import it.polimi.automata.transition.Transition;
import it.polimi.automata.transition.ClaimTransitionFactory;
import it.polimi.automata.transition.ModelTransitionFactory;
import it.polimi.checker.ModelCheckingResults;
import it.polimi.checker.intersection.IntersectionRule;
import it.polimi.constraints.Constraint;
import it.polimi.constraints.io.out.ConstraintWriter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;

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
	 * @throws IOException 
	 * @throws SAXException 
	 * @throws ParserConfigurationException 
	 * 
	 * @throws GraphIOException
	 */
	@Test
	public void testCHIA() throws ParserConfigurationException, SAXException, IOException {
		logger.info("Running the test: testCHIA");
		
		
		BAReader claimReader = 
				new BAReader(
				new File(getClass().getClassLoader()
						.getResource("it/polimi/casestudies/sendingmessage/SendingMessageClaim.xml").getFile()));

		BA claim = claimReader.read();

		
		
		IBAReader modelReader = new IBAReader(
				new File(getClass().getClassLoader()
						.getResource("it/polimi/casestudies/sendingmessage/SendingMessageModel.xml").getFile()));
		IBA model = modelReader.read();
		CHIA chia = new CHIA(
				claim, model, new ModelCheckingResults(true, true, true));
		int result = chia.check();
		assertTrue(result == -1);

		Constraint constraint = chia.getConstraint();

		new ConstraintWriter(constraint, new File(
				"/Users/Claudio1/Desktop/CHIAResults/Results.xml")).write();
		System.out.println(constraint.toString());

	}

}
