/**
 * 
 */
package it.polimi.automata.io;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;
import it.polimi.automata.BA;
import it.polimi.automata.BAFactory;
import it.polimi.automata.impl.BAFactoryImpl;
import it.polimi.automata.labeling.Label;
import it.polimi.automata.labeling.LabelFactory;
import it.polimi.automata.labeling.impl.LabelImplFactory;
import it.polimi.automata.state.State;
import it.polimi.automata.state.StateFactory;
import it.polimi.automata.state.impl.StateFactoryImpl;
import it.polimi.automata.state.impl.StateImpl;
import it.polimi.automata.transition.Transition;
import it.polimi.automata.transition.TransitionFactory;
import it.polimi.automata.transition.impl.ModelTransitionFactoryImpl;
import it.polimi.automata.transition.impl.TransitionImpl;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import rwth.i2.ltl2ba4j.model.IGraphProposition;
import rwth.i2.ltl2ba4j.model.impl.GraphProposition;
import edu.uci.ics.jung.graph.DirectedSparseGraph;
import edu.uci.ics.jung.io.GraphIOException;
import edu.uci.ics.jung.io.graphml.GraphMLReader2;

/**
 * @author claudiomenghi
 * 
 */
public class BAReaderTest {

	private LabelFactory<Label> labelFactory;

	private TransitionFactory<Label, Transition<Label>> transitionFactory;

	private StateFactory<State> stateFactory;

	@Mock
	private GraphMLReader2<DirectedSparseGraph<StateImpl, TransitionImpl<Label>>, StateImpl, TransitionImpl<Label>> graphReader;

	private BAFactory<Label, State, Transition<Label>> automatonFactory = new BAFactoryImpl<Label, State, Transition<Label>>();

	@Mock
	private BufferedReader fileReader;

	private BA<Label, State, Transition<Label>> ba;

	@Before
	public void setUp() throws GraphIOException {
		MockitoAnnotations.initMocks(this);
		when(graphReader.readGraph()).thenReturn(
				new DirectedSparseGraph<StateImpl, TransitionImpl<Label>>());
		this.stateFactory = new StateFactoryImpl();
		this.transitionFactory = new ModelTransitionFactoryImpl<Label>();
		this.labelFactory = new LabelImplFactory();

	}

	/**
	 * Test method for
	 * {@link it.polimi.automata.io.BAReader#BAReader(null, it.polimi.automata.transition.TransitionFactory, it.polimi.automata.state.StateFactory, it.polimi.automata.BAFactory, java.io.BufferedReader)}
	 * .
	 */
	@Test(expected = NullPointerException.class)
	public void testBAReaderNullLabelFactory() {
		new BAReader<Label, LabelFactory<Label>, State, StateFactory<State>, Transition<Label>, TransitionFactory<Label, Transition<Label>>, BAFactory<Label, State, Transition<Label>>>(
				null, transitionFactory, stateFactory, automatonFactory,
				fileReader);
	}

	/**
	 * Test method for
	 * {@link it.polimi.automata.io.BAReader#BAReader(it.polimi.automata.labeling.LabelFactory, null, it.polimi.automata.state.StateFactory, it.polimi.automata.BAFactory, java.io.BufferedReader)}
	 * .
	 */
	@Test(expected = NullPointerException.class)
	public void testBAReaderNullTransitionFactory() {
		new BAReader<Label, LabelFactory<Label>, State, StateFactory<State>, Transition<Label>, TransitionFactory<Label, Transition<Label>>, BAFactory<Label, State, Transition<Label>>>(
				labelFactory, null, stateFactory, automatonFactory, fileReader);
	}

	/**
	 * Test method for
	 * {@link it.polimi.automata.io.BAReader#BAReader(it.polimi.automata.labeling.LabelFactory, it.polimi.automata.transition.TransitionFactory, null, it.polimi.automata.BAFactory, java.io.BufferedReader)}
	 * .
	 */
	@Test(expected = NullPointerException.class)
	public void testBAReaderNullStateFactory() {
		new BAReader<Label, LabelFactory<Label>, State, StateFactory<State>, Transition<Label>, TransitionFactory<Label, Transition<Label>>, BAFactory<Label, State, Transition<Label>>>(
				labelFactory, transitionFactory, null, automatonFactory,
				fileReader);
	}

	/**
	 * Test method for
	 * {@link it.polimi.automata.io.BAReader#BAReader(it.polimi.automata.labeling.LabelFactory, it.polimi.automata.transition.TransitionFactory, it.polimi.automata.state.StateFactory, null, java.io.BufferedReader)}
	 * .
	 */
	@Test(expected = NullPointerException.class)
	public void testBAReaderNullBAFactory() {
		new BAReader<Label, LabelFactory<Label>, State, StateFactory<State>, Transition<Label>, TransitionFactory<Label, Transition<Label>>, BAFactory<Label, State, Transition<Label>>>(
				labelFactory, transitionFactory, stateFactory, null, fileReader);
	}

	/**
	 * Test method for
	 * {@link it.polimi.automata.io.BAReader#BAReader(it.polimi.automata.labeling.LabelFactory, it.polimi.automata.transition.TransitionFactory, it.polimi.automata.state.StateFactory, it.polimi.automata.BAFactory, null)}
	 * .
	 */
	@Test(expected = NullPointerException.class)
	public void testBAReaderNullFileReader() {
		new BAReader<Label, LabelFactory<Label>, State, StateFactory<State>, Transition<Label>, TransitionFactory<Label, Transition<Label>>, BAFactory<Label, State, Transition<Label>>>(
				labelFactory, transitionFactory, stateFactory,
				automatonFactory, null);
	}

	/**
	 * Test method for
	 * {@link it.polimi.automata.io.BAReader#BAReader(it.polimi.automata.labeling.LabelFactory, it.polimi.automata.transition.TransitionFactory, it.polimi.automata.state.StateFactory, it.polimi.automata.BAFactory, java.io.BufferedReader)}
	 * .
	 */
	@Test
	public void testBAReader() {
		assertNotNull(new BAReader<Label, LabelFactory<Label>, State, StateFactory<State>, Transition<Label>, TransitionFactory<Label, Transition<Label>>, BAFactory<Label, State, Transition<Label>>>(
				labelFactory, transitionFactory, stateFactory,
				automatonFactory, fileReader));
	}

	/**
	 * Test method for {@link it.polimi.automata.io.BAReader#read()}.
	 * 
	 * @throws GraphIOException
	 * @throws SecurityException
	 * @throws NoSuchFieldException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 */
	@Test
	public void testRead() throws GraphIOException, NoSuchFieldException,
			SecurityException, IllegalArgumentException, IllegalAccessException {
		Field field = BAReader.class.getDeclaredField("graphReader");
		field.setAccessible(true);

		BAReader<Label, LabelFactory<Label>, State, StateFactory<State>, Transition<Label>, TransitionFactory<Label, Transition<Label>>, BAFactory<Label, State, Transition<Label>>> readed = new BAReader<Label, LabelFactory<Label>, State, StateFactory<State>, Transition<Label>, TransitionFactory<Label, Transition<Label>>, BAFactory<Label, State, Transition<Label>>>(
				labelFactory, transitionFactory, stateFactory,
				automatonFactory, fileReader);
		field.set(readed, graphReader);

		assertNotNull(readed.read());
	}

	/**
	 * Test method for {@link it.polimi.automata.io.BAReader#read()}.
	 * 
	 * @throws GraphIOException
	 * @throws SecurityException
	 * @throws NoSuchFieldException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws FileNotFoundException
	 */
	@Test
	public void testRead2() throws GraphIOException, NoSuchFieldException,
			SecurityException, IllegalArgumentException,
			IllegalAccessException, FileNotFoundException {

		ClassLoader classLoader = getClass().getClassLoader();
		BAReader<Label, LabelFactory<Label>, State, StateFactory<State>, Transition<Label>, TransitionFactory<Label, Transition<Label>>, BAFactory<Label, State, Transition<Label>>> readed = new BAReader<Label, LabelFactory<Label>, State, StateFactory<State>, Transition<Label>, TransitionFactory<Label, Transition<Label>>, BAFactory<Label, State, Transition<Label>>>(
				labelFactory, transitionFactory, stateFactory,
				automatonFactory, new BufferedReader(new FileReader(classLoader
						.getResource("loadingBA.xml").getFile())));

		ba = readed.read();
		assertNotNull(ba);
		this.stateFactory = new StateFactoryImpl();
		State state1 = stateFactory.create("state1", 1);
		State state2 = stateFactory.create("state2", 2);
		State state3 = stateFactory.create("state3", 3);
		assertTrue(ba.getStates().contains(state1));
		assertTrue(ba.getStates().contains(state2));
		assertTrue(ba.getStates().contains(state3));

		assertTrue(ba.getInitialStates().contains(state1));
		assertTrue(ba.getAcceptStates().contains(state3));

		this.transitionFactory = new ModelTransitionFactoryImpl<Label>();
		this.labelFactory = new LabelImplFactory();
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

		assertTrue(ba.getTransitions().contains(t));

		assertTrue(ba.getAlphabet().contains(label1));
		assertTrue(ba.getAlphabet().contains(label2));
	}
}
