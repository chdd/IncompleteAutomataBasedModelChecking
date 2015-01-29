/**
 * 
 */
package it.polimi.automata.io;

import static org.junit.Assert.assertNotNull;
import it.polimi.automata.IBA;
import it.polimi.automata.IBAFactory;
import it.polimi.automata.impl.IBAFactoryImpl;
import it.polimi.automata.labeling.Label;
import it.polimi.automata.labeling.LabelFactory;
import it.polimi.automata.labeling.impl.LabelFactoryImpl;
import it.polimi.automata.state.State;
import it.polimi.automata.state.StateFactory;
import it.polimi.automata.state.impl.StateFactoryImpl;
import it.polimi.automata.transition.Transition;
import it.polimi.automata.transition.TransitionFactory;
import it.polimi.automata.transition.impl.ModelTransitionFactoryImpl;

import java.io.IOException;
import java.io.StringWriter;
import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import rwth.i2.ltl2ba4j.model.IGraphProposition;
import rwth.i2.ltl2ba4j.model.impl.GraphProposition;
import edu.uci.ics.jung.io.GraphIOException;

/**
 * @author claudiomenghi
 *
 */
public class IBAWriterTest {

	private IBA<Label, State, Transition<Label>> iba;
	private IBAFactory<Label, State, Transition<Label>> baFactory;
	private StateFactory<State> stateFactory;
	private TransitionFactory<Label, Transition<Label>> transitionFactory;
	private LabelFactory<Label> labelFactory;

	@Before
	public void setUp() throws GraphIOException {
		this.baFactory = new IBAFactoryImpl<Label, State, Transition<Label>>();
		iba = baFactory.create();
		this.stateFactory = new StateFactoryImpl();
		State state1 = stateFactory.create("state1", 1);
		State state2 = stateFactory.create("state2", 2);
		State state3 = stateFactory.create("state3", 3);

		this.transitionFactory = new ModelTransitionFactoryImpl<Label>();
		this.labelFactory = new LabelFactoryImpl();
		Set<Label> labels = new HashSet<Label>();
		Set<IGraphProposition> propositions1 = new HashSet<IGraphProposition>();
		propositions1.add(new GraphProposition("a", false));
		propositions1.add(new GraphProposition("b", false));

		Label label1 = labelFactory.create(propositions1);
		labels.add(label1);
		Set<IGraphProposition> propositions2 = new HashSet<IGraphProposition>();
		propositions2.add(new GraphProposition("c", false));
		Label label2 = labelFactory.create(propositions2);

		labels.add(label2);

		Transition<Label> t = this.transitionFactory.create(1, labels);
		iba.addCharacter(label1);
		iba.addCharacter(label2);
		iba.addState(state1);
		iba.addState(state2);
		iba.addState(state3);
		iba.addInitialState(state1);
		iba.addTransparentState(state2);
		iba.addAcceptState(state3);
		iba.addTransition(state1, state2, t);

	}

	/**
	 * Test method for {@link it.polimi.automata.io.IBAWriter#IBAWriter(null)}.
	 */
	@Test(expected = NullPointerException.class)
	public void testIBAWriterNull() {
		new IBAWriter<Label, State, Transition<Label>>(null);
	}

	/**
	 * Test method for
	 * {@link it.polimi.automata.io.IBAWriter#IBAWriter(it.polimi.automata.BA)}.
	 */
	@Test
	public void testIBAWriter() {
		assertNotNull(new IBAWriter<Label, State, Transition<Label>>(iba));
	}

	/**
	 * Test method for {@link it.polimi.automata.io.IBAWriter#save(null)}.
	 * 
	 * @throws IOException
	 */
	@Test(expected = NullPointerException.class)
	public void testSaveWriterNull() throws IOException {
		new IBAWriter<Label, State, Transition<Label>>(iba).save(null);
	}

	/**
	 * Test method for
	 * {@link it.polimi.automata.io.IBAWriter#save(java.io.Writer)}.
	 * 
	 * @throws IOException
	 */
	@Test
	public void testSaveWriter() throws IOException {
		StringWriter returnedString = new StringWriter();
		new IBAWriter<Label, State, Transition<Label>>(iba).save(returnedString);
		assertNotNull(returnedString);
	}

}
