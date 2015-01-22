/**
 * 
 */
package it.polimi.automata.io;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;
import it.polimi.automata.BAFactory;
import it.polimi.automata.impl.BAFactoryImpl;
import it.polimi.automata.labeling.Label;
import it.polimi.automata.labeling.LabelFactory;
import it.polimi.automata.state.StateFactory;
import it.polimi.automata.state.impl.StateImpl;
import it.polimi.automata.transition.TransitionFactory;
import it.polimi.automata.transition.impl.TransitionImpl;

import java.io.BufferedReader;
import java.lang.reflect.Field;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import edu.uci.ics.jung.graph.DirectedSparseGraph;
import edu.uci.ics.jung.io.GraphIOException;
import edu.uci.ics.jung.io.graphml.GraphMLReader2;

/**
 * @author claudiomenghi
 * 
 */
public class BAReaderTest {

	@Mock
	private LabelFactory<Label> labelFactory;

	@Mock
	private TransitionFactory<Label, TransitionImpl<Label>> transitionFactory;

	@Mock
	private StateFactory<StateImpl> stateFactory;

	@Mock
	private GraphMLReader2<DirectedSparseGraph<StateImpl, TransitionImpl<Label>>, StateImpl, TransitionImpl<Label>> graphReader;
	
	private BAFactory<Label, StateImpl, TransitionImpl<Label>> automatonFactory=new BAFactoryImpl<Label, StateImpl, TransitionImpl<Label>>();

	@Mock
	private BufferedReader fileReader;

	@Before
	public void setUp() throws GraphIOException {
		MockitoAnnotations.initMocks(this);
		when(graphReader.readGraph()).thenReturn(new DirectedSparseGraph<StateImpl, TransitionImpl<Label>>());

	}

	/**
	 * Test method for
	 * {@link it.polimi.automata.io.BAReader#BAReader(null, it.polimi.automata.transition.TransitionFactory, it.polimi.automata.state.StateFactory, it.polimi.automata.BAFactory, java.io.BufferedReader)}
	 * .
	 */
	@Test(expected=NullPointerException.class)
	public void testBAReaderNullLabelFactory() {
		new BAReader<Label, LabelFactory<Label>, StateImpl, StateFactory<StateImpl>, TransitionImpl<Label>, TransitionFactory<Label, TransitionImpl<Label>>, BAFactory<Label, StateImpl, TransitionImpl<Label>>>(
				null, transitionFactory, stateFactory, automatonFactory,
				fileReader);
	}

	/**
	 * Test method for
	 * {@link it.polimi.automata.io.BAReader#BAReader(it.polimi.automata.labeling.LabelFactory, null, it.polimi.automata.state.StateFactory, it.polimi.automata.BAFactory, java.io.BufferedReader)}
	 * .
	 */
	@Test(expected=NullPointerException.class)
	public void testBAReaderNullTransitionFactory() {
		new BAReader<Label, LabelFactory<Label>, StateImpl, StateFactory<StateImpl>, TransitionImpl<Label>, TransitionFactory<Label, TransitionImpl<Label>>, BAFactory<Label, StateImpl, TransitionImpl<Label>>>(
				labelFactory, null, stateFactory, automatonFactory,
				fileReader);
	}
	
	/**
	 * Test method for
	 * {@link it.polimi.automata.io.BAReader#BAReader(it.polimi.automata.labeling.LabelFactory, it.polimi.automata.transition.TransitionFactory, null, it.polimi.automata.BAFactory, java.io.BufferedReader)}
	 * .
	 */
	@Test(expected=NullPointerException.class)
	public void testBAReaderNullStateFactory() {
		new BAReader<Label, LabelFactory<Label>, StateImpl, StateFactory<StateImpl>, TransitionImpl<Label>, TransitionFactory<Label, TransitionImpl<Label>>, BAFactory<Label, StateImpl, TransitionImpl<Label>>>(
				labelFactory, transitionFactory, null, automatonFactory,
				fileReader);
	}
	
	/**
	 * Test method for
	 * {@link it.polimi.automata.io.BAReader#BAReader(it.polimi.automata.labeling.LabelFactory, it.polimi.automata.transition.TransitionFactory, it.polimi.automata.state.StateFactory, null, java.io.BufferedReader)}
	 * .
	 */
	@Test(expected=NullPointerException.class)
	public void testBAReaderNullBAFactory() {
		new BAReader<Label, LabelFactory<Label>, StateImpl, StateFactory<StateImpl>, TransitionImpl<Label>, TransitionFactory<Label, TransitionImpl<Label>>, BAFactory<Label, StateImpl, TransitionImpl<Label>>>(
				labelFactory, transitionFactory, stateFactory, null,
				fileReader);
	}
	
	/**
	 * Test method for
	 * {@link it.polimi.automata.io.BAReader#BAReader(it.polimi.automata.labeling.LabelFactory, it.polimi.automata.transition.TransitionFactory, it.polimi.automata.state.StateFactory, it.polimi.automata.BAFactory, null)}
	 * .
	 */
	@Test(expected=NullPointerException.class)
	public void testBAReaderNullFileReader() {
		new BAReader<Label, LabelFactory<Label>, StateImpl, StateFactory<StateImpl>, TransitionImpl<Label>, TransitionFactory<Label, TransitionImpl<Label>>, BAFactory<Label, StateImpl, TransitionImpl<Label>>>(
				labelFactory, transitionFactory, stateFactory, automatonFactory,
				null);
	}
	
	/**
	 * Test method for
	 * {@link it.polimi.automata.io.BAReader#BAReader(it.polimi.automata.labeling.LabelFactory, it.polimi.automata.transition.TransitionFactory, it.polimi.automata.state.StateFactory, it.polimi.automata.BAFactory, java.io.BufferedReader)}
	 * .
	 */
	@Test
	public void testBAReader() {
		assertNotNull(new BAReader<Label, LabelFactory<Label>, StateImpl, StateFactory<StateImpl>, TransitionImpl<Label>, TransitionFactory<Label, TransitionImpl<Label>>, BAFactory<Label, StateImpl, TransitionImpl<Label>>>(
				labelFactory, transitionFactory, stateFactory, automatonFactory,
				fileReader));
	}

	/**
	 * Test method for {@link it.polimi.automata.io.BAReader#read()}.
	 * @throws GraphIOException 
	 * @throws SecurityException 
	 * @throws NoSuchFieldException 
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 */
	@Test
	public void testRead() throws GraphIOException, NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		Field field = BAReader.class
				.getDeclaredField("graphReader");
		field.setAccessible(true);
		
		BAReader<Label, LabelFactory<Label>, StateImpl, StateFactory<StateImpl>, TransitionImpl<Label>, TransitionFactory<Label, TransitionImpl<Label>>, BAFactory<Label, StateImpl, TransitionImpl<Label>>> readed=new BAReader<Label, LabelFactory<Label>, StateImpl, StateFactory<StateImpl>, TransitionImpl<Label>, TransitionFactory<Label, TransitionImpl<Label>>, BAFactory<Label, StateImpl, TransitionImpl<Label>>>(
				labelFactory, transitionFactory, stateFactory, automatonFactory,
				fileReader);
		field.set(readed, graphReader);
		
		assertNotNull(readed.read());
	}

}
