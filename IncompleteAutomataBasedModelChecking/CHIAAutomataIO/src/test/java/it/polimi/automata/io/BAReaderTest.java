/**
 * 
 */
package it.polimi.automata.io;

import static org.junit.Assert.assertTrue;
import it.polimi.automata.BA;
import it.polimi.automata.io.in.BAReader;
import it.polimi.automata.state.State;
import it.polimi.automata.state.StateFactory;
import it.polimi.automata.transition.ClaimTransitionFactory;
import it.polimi.automata.transition.Transition;
import it.polimi.automata.transition.TransitionFactory;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;

import org.junit.Before;
import org.junit.Test;
import org.xml.sax.SAXException;

import rwth.i2.ltl2ba4j.model.IGraphProposition;
import rwth.i2.ltl2ba4j.model.impl.GraphProposition;
import rwth.i2.ltl2ba4j.model.impl.SigmaProposition;

/**
 * @author claudiomenghi
 * 
 */
public class BAReaderTest {

	private StateFactory stateFactory;
	private TransitionFactory<State, Transition> transitionFactory;

	private Transition t1;
	private Transition t2;
	private Transition t3;

	@Before
	public void setUp() {

		this.stateFactory = new StateFactory();
		this.transitionFactory = new ClaimTransitionFactory();

		Set<IGraphProposition> propositions1 = new HashSet<IGraphProposition>();
		propositions1.add(new SigmaProposition());
		t1 = this.transitionFactory.create(1, propositions1);

		Set<IGraphProposition> propositions2 = new HashSet<IGraphProposition>();
		propositions2.add(new GraphProposition("send", false));
		propositions2.add(new GraphProposition("success", true));
		t2 = this.transitionFactory.create(2, propositions2);

		Set<IGraphProposition> propositions3 = new HashSet<IGraphProposition>();
		propositions3.add(new GraphProposition("success", true));
		t3 = this.transitionFactory.create(3, propositions3);
	}

	@Test
	public void test() throws JAXBException, ParserConfigurationException, SAXException, IOException {
		BAReader reader = new BAReader(new File(getClass().getClassLoader()
				.getResource("SendingMessageClaim.xml").getFile()));

		BA sendingMessage = reader.read();

		assertTrue(sendingMessage.getStates().contains(
				stateFactory.create("p1", 1)));
		assertTrue(sendingMessage.getStates().contains(
				stateFactory.create("p2", 2)));

		assertTrue(sendingMessage.getInitialStates().contains(
				stateFactory.create("p1", 1)));
		assertTrue(sendingMessage.getAcceptStates().contains(
				stateFactory.create("p1", 2)));

		assertTrue(sendingMessage.getTransitions().contains(t1));

		assertTrue(sendingMessage.getTransitions().contains(t2));
		assertTrue(sendingMessage.getTransitions().contains(t3));

	}
}
