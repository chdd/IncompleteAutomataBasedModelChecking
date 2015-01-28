/**
 * 
 */
package it.polimi.automata;

import static org.junit.Assert.assertTrue;
import it.polimi.automata.impl.BAFactoryImpl;
import it.polimi.automata.io.BAReader;
import it.polimi.automata.labeling.Label;
import it.polimi.automata.labeling.LabelFactory;
import it.polimi.automata.labeling.impl.LabelImplFactory;
import it.polimi.automata.state.State;
import it.polimi.automata.state.StateFactory;
import it.polimi.automata.state.impl.StateFactoryImpl;
import it.polimi.automata.transition.Transition;
import it.polimi.automata.transition.TransitionFactory;
import it.polimi.automata.transition.impl.ClaimTransitionFactoryImpl;
import it.polimi.automata.transition.impl.ModelTransitionFactoryImpl;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import rwth.i2.ltl2ba4j.model.IGraphProposition;
import rwth.i2.ltl2ba4j.model.impl.GraphProposition;
import rwth.i2.ltl2ba4j.model.impl.SigmaProposition;
import edu.uci.ics.jung.io.GraphIOException;

/**
 * @author claudiomenghi
 * 
 */
public class SendingMessageClaimTest {

	private StateFactory<State> stateFactory;
	private TransitionFactory<Label, Transition<Label>> transitionFactory;
	private LabelFactory<Label> labelFactory;

	private Transition<Label> t1;
	private Transition<Label> t2;
	private Transition<Label> t3;

	@Before
	public void setUp() throws GraphIOException {

		this.stateFactory = new StateFactoryImpl();
		this.transitionFactory = new ClaimTransitionFactoryImpl<Label>();
		this.labelFactory = new LabelImplFactory();

		Set<IGraphProposition> propositions1 = new HashSet<IGraphProposition>();
		propositions1.add(new SigmaProposition());
		Set<Label> labels1 = new HashSet<Label>();
		labels1.add(this.labelFactory.create(propositions1));
		t1 = this.transitionFactory.create(1, labels1);

		Set<IGraphProposition> propositions2 = new HashSet<IGraphProposition>();
		propositions2.add(new GraphProposition("send", false));
		propositions2.add(new GraphProposition("success", true));

		Set<Label> labels2 = new HashSet<Label>();
		labels2.add(this.labelFactory.create(propositions2));
		t2 = this.transitionFactory.create(2, labels2);

		Set<IGraphProposition> propositions3 = new HashSet<IGraphProposition>();
		propositions3.add(new GraphProposition("success", true));
		Set<Label> labels3 = new HashSet<Label>();
		labels3.add(this.labelFactory.create(propositions3));
		t3 = this.transitionFactory.create(3, labels3);
	}

	@Test
	public void test() throws FileNotFoundException, GraphIOException {
		BAReader<Label, LabelFactory<Label>, State, StateFactory<State>, Transition<Label>, TransitionFactory<Label, Transition<Label>>, BAFactory<Label, State, Transition<Label>>> reader = new BAReader<Label, LabelFactory<Label>, State, StateFactory<State>, Transition<Label>, TransitionFactory<Label, Transition<Label>>, BAFactory<Label, State, Transition<Label>>>(
				this.labelFactory, this.transitionFactory, this.stateFactory,
				new BAFactoryImpl<Label, State, Transition<Label>>(),
				new BufferedReader(new FileReader(getClass().getClassLoader()
						.getResource("SendingMessageClaim.xml").getFile())));

		BA<Label, State, Transition<Label>> sendingMessage = reader.read();

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
