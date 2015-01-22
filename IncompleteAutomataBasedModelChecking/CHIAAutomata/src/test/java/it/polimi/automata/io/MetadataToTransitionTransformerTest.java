/**
 * 
 */
package it.polimi.automata.io;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;
import it.polimi.Constants;
import it.polimi.automata.labeling.Label;
import it.polimi.automata.labeling.LabelFactory;
import it.polimi.automata.labeling.impl.LabelImplFactory;
import it.polimi.automata.state.State;
import it.polimi.automata.BA;
import it.polimi.automata.transition.Transition;
import it.polimi.automata.transition.TransitionFactory;
import it.polimi.automata.transition.impl.ClaimTransitionFactoryImpl;

import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import rwth.i2.ltl2ba4j.model.IGraphProposition;
import rwth.i2.ltl2ba4j.model.impl.GraphProposition;
import edu.uci.ics.jung.io.graphml.EdgeMetadata;

/**
 * @author claudiomenghi
 * 
 */
public class MetadataToTransitionTransformerTest {

	private LabelFactory<Label> labelFactory;

	private TransitionFactory<Label, Transition<Label>> transitionFactory;

	@Mock
	private EdgeMetadata input;

	@Mock
	private BA<Label, State, Transition<Label>> ba;

	private Transition<Label> ret;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		when(input.getId()).thenReturn("1");
		when(input.getProperty(Constants.LABELSTAG)).thenReturn(
				Constants.LPAR + "a" + Constants.RPAR + Constants.OR
						+ Constants.LPAR + "!b" + Constants.RPAR);

		labelFactory = new LabelImplFactory();
		transitionFactory = new ClaimTransitionFactoryImpl<Label>();

		Set<IGraphProposition> labels1 = new HashSet<IGraphProposition>();
		labels1.add(new GraphProposition("a", false));

		Set<IGraphProposition> labels2 = new HashSet<IGraphProposition>();
		labels2.add(new GraphProposition("b", true));

		Set<Label> labels = new HashSet<Label>();
		labels.add(labelFactory.create(labels1));
		labels.add(labelFactory.create(labels2));
		ret = transitionFactory.create(1, labels);

	}

	/**
	 * Test method for
	 * {@link it.polimi.automata.io.MetadataToTransitionTransformer#MetadataToTransitionTransformer(null, it.polimi.automata.labeling.LabelFactory)}
	 * .
	 */
	@Test(expected = NullPointerException.class)
	public void testMetadataToTransitionTransformerNullTransitionFactory() {
		new MetadataToTransitionTransformer<Label, LabelFactory<Label>, State, Transition<Label>, TransitionFactory<Label, Transition<Label>>>(
				null, labelFactory, this.ba);
	}

	/**
	 * Test method for
	 * {@link it.polimi.automata.io.MetadataToTransitionTransformer#MetadataToTransitionTransformer(it.polimi.automata.transition.TransitionFactory, null)}
	 * .
	 */
	@Test(expected = NullPointerException.class)
	public void testMetadataToTransitionTransformerNullLabelFactory() {
		new MetadataToTransitionTransformer<Label, LabelFactory<Label>, State, Transition<Label>, TransitionFactory<Label, Transition<Label>>>(
				transitionFactory, null, this.ba);
	}
	
	/**
	 * Test method for
	 * {@link it.polimi.automata.io.MetadataToTransitionTransformer#MetadataToTransitionTransformer(it.polimi.automata.transition.TransitionFactory, ,null)}
	 * .
	 */
	@Test(expected = NullPointerException.class)
	public void testMetadataToTransitionTransformerNullBA() {
		new MetadataToTransitionTransformer<Label, LabelFactory<Label>, State, Transition<Label>, TransitionFactory<Label, Transition<Label>>>(
				transitionFactory, labelFactory, null);
	}

	/**
	 * Test method for
	 * {@link it.polimi.automata.io.MetadataToTransitionTransformer#MetadataToTransitionTransformer(it.polimi.automata.transition.TransitionFactory, it.polimi.automata.labeling.LabelFactory)}
	 * .
	 */
	@Test
	public void testMetadataToTransitionTransformer() {
		assertNotNull(new MetadataToTransitionTransformer<Label, LabelFactory<Label>, State, Transition<Label>, TransitionFactory<Label, Transition<Label>>>(
				transitionFactory, labelFactory, this.ba));
	}

	/**
	 * Test method for
	 * {@link it.polimi.automata.io.MetadataToTransitionTransformer#transform(null)}
	 * .
	 */
	@Test(expected = NullPointerException.class)
	public void testTransformNull() {
		MetadataToTransitionTransformer<Label, LabelFactory<Label>, State, Transition<Label>, TransitionFactory<Label, Transition<Label>>> transformer = new MetadataToTransitionTransformer<Label, LabelFactory<Label>, State, Transition<Label>, TransitionFactory<Label, Transition<Label>>>(
				transitionFactory, labelFactory, this.ba);
		transformer.transform(null);
	}

	/**
	 * Test method for
	 * {@link it.polimi.automata.io.MetadataToTransitionTransformer#transform(edu.uci.ics.jung.io.graphml.EdgeMetadata)}
	 * .
	 */
	@Test
	public void testTransform() {
		MetadataToTransitionTransformer<Label, LabelFactory<Label>, State, Transition<Label>, TransitionFactory<Label, Transition<Label>>> transformer = new MetadataToTransitionTransformer<Label, LabelFactory<Label>, State, Transition<Label>, TransitionFactory<Label, Transition<Label>>>(
				transitionFactory, labelFactory, this.ba);
		assertEquals(ret, transformer.transform(input));
	}

}
