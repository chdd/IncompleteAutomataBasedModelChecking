/**
 * 
 */
package it.polimi.automata.io;

import static org.junit.Assert.assertTrue;
import it.polimi.automata.BA;
import it.polimi.automata.state.State;
import it.polimi.automata.state.StateFactory;
import it.polimi.automata.state.impl.StateFactoryImpl;
import it.polimi.automata.transition.Transition;
import it.polimi.automata.transition.TransitionFactory;
import it.polimi.automata.transition.impl.TransitionFactoryClaimImpl;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Set;

import javax.xml.bind.JAXBException;

import org.junit.Before;
import org.junit.Test;

import rwth.i2.ltl2ba4j.model.IGraphProposition;
import rwth.i2.ltl2ba4j.model.impl.GraphProposition;
import rwth.i2.ltl2ba4j.model.impl.SigmaProposition;

/**
 * @author claudiomenghi
 * 
 */
public class BAReaderTest {

	private StateFactory<State> stateFactory;
	private TransitionFactory<State, Transition> transitionFactory;
	
	private Transition t1;
	private Transition t2;
	private Transition t3;

	@Before
	public void setUp() {

		this.stateFactory = new StateFactoryImpl();
		this.transitionFactory = new TransitionFactoryClaimImpl<State>();
	
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
	public void test() throws FileNotFoundException, JAXBException {
		BAReader<State, StateFactory<State>, Transition, TransitionFactory<State, Transition>> reader = new BAReader<State, StateFactory<State>, Transition, TransitionFactory<State, Transition>>(
				 this.transitionFactory, this.stateFactory,
				new File(getClass().getClassLoader()
						.getResource("SendingMessageClaim.xml").getFile()));

		BA<State, Transition> sendingMessage = reader.read();

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