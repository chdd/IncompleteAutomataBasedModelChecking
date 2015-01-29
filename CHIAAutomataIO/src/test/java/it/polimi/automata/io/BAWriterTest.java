/**
 * 
 */
package it.polimi.automata.io;

import static org.junit.Assert.assertNotNull;
import it.polimi.automata.BA;
import it.polimi.automata.BAFactory;
import it.polimi.automata.impl.BAFactoryImpl;
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
public class BAWriterTest {

	private BA<Label, State, Transition<Label>> ba;
	private BAFactory<Label, State, Transition<Label>> baFactory;
	private StateFactory<State> stateFactory;
	private TransitionFactory<Label, Transition<Label>> transitionFactory;
	private LabelFactory<Label> labelFactory;

	@Before
	public void setUp() throws GraphIOException {
		this.baFactory = new BAFactoryImpl<Label, State, Transition<Label>>();
		ba = baFactory.create();
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
		ba.addCharacter(label1);
		ba.addCharacter(label2);
		ba.addState(state1);
		ba.addState(state2);
		ba.addState(state3);
		ba.addInitialState(state1);
		ba.addAcceptState(state3);
		ba.addTransition(state1, state2, t);

	}

	/**
	 * Test method for {@link it.polimi.automata.io.BAWriter#BAWriter(null)}.
	 */
	@Test(expected = NullPointerException.class)
	public void testBAWriterNull() {
		new BAWriter<Label, State, Transition<Label>>(null);
	}

	/**
	 * Test method for
	 * {@link it.polimi.automata.io.BAWriter#BAWriter(it.polimi.automata.BA)}.
	 */
	@Test
	public void testBAWriter() {
		assertNotNull(new BAWriter<Label, State, Transition<Label>>(ba));
	}

	/**
	 * Test method for {@link it.polimi.automata.io.BAWriter#save(null)}.
	 * 
	 * @throws IOException
	 */
	@Test(expected = NullPointerException.class)
	public void testSaveWriterNull() throws IOException {
		new BAWriter<Label, State, Transition<Label>>(ba).save(null);
	}

	/**
	 * Test method for
	 * {@link it.polimi.automata.io.BAWriter#save(java.io.Writer)}.
	 * 
	 * @throws IOException
	 */
	@Test
	public void testSaveWriter() throws IOException {
		StringWriter returnedString = new StringWriter();
		new BAWriter<Label, State, Transition<Label>>(ba).save(returnedString);
		assertNotNull(returnedString);
	}

}
