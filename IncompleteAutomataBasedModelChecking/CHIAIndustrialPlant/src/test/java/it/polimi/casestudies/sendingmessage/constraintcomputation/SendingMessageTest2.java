/**
 * 
 */
package it.polimi.casestudies.sendingmessage.constraintcomputation;

import static org.junit.Assert.assertEquals;
import it.polimi.automata.BA;
import it.polimi.automata.IBA;
import it.polimi.automata.IntersectionBA;
import it.polimi.automata.io.BAReader;
import it.polimi.automata.io.IBAReader;
import it.polimi.checker.ModelChecker;
import it.polimi.checker.ModelCheckingResults;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.junit.Before;
import org.junit.Test;
import org.xml.sax.SAXException;

/**
 * @author claudiomenghi
 *
 */
public class SendingMessageTest2 {

	@Before
	public void setUp() {

	}

	@Test
	public void checkerTest() throws ParserConfigurationException, SAXException, IOException {

		BAReader claimReader = new BAReader(
				new File(
						getClass()
								.getClassLoader()
								.getResource(
										"it/polimi/casestudies/sendingmessage/SendingMessageClaim.xml")
								.getFile()));

		BA claim = claimReader.read();

		IBAReader modelReader = new IBAReader(
				new File(
						getClass()
								.getClassLoader()
								.getResource(
										"it/polimi/casestudies/sendingmessage/SendingMessageModel.xml")
								.getFile()));
		IBA model = modelReader.read();

		ModelCheckingResults mp = new ModelCheckingResults(true, true, true);
		ModelChecker modelChecker = new ModelChecker(model, claim, mp);

		int res = modelChecker.check();
		IntersectionBA intersectionBA = modelChecker
				.getIntersectionAutomaton();

		assertEquals(-1, res);
		assertEquals(11, intersectionBA.getStates().size());
		assertEquals(1, intersectionBA.getInitialStates().size());
		assertEquals(1, intersectionBA.getAcceptStates().size());
		assertEquals(4, intersectionBA.getMixedStates().size());
		assertEquals(20, intersectionBA.getTransitions().size());
	}
}
