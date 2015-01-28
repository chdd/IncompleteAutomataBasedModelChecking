/**
 * 
 */
package it.polimi.automata.io;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;
import it.polimi.automata.labeling.Label;
import it.polimi.automata.transition.Transition;
import it.polimi.automata.transition.TransitionFactory;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import edu.uci.ics.jung.io.graphml.HyperEdgeMetadata;

/**
 * @author claudiomenghi
 *
 */
public class HyperMetadataToTransitionTransformerTest {

	@Mock
	private TransitionFactory<Label, Transition<Label>> factory;
	
	@Mock
	private Transition<Label> t;

	@Mock
	private HyperEdgeMetadata tMetadata;


	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		
		when(factory.create()).thenReturn(t);
		
	}
	
	/**
	 * Test method for {@link it.polimi.automata.io.HyperMetadataToTransitionTransformer#HyperMetadataToTransitionTransformer(null)}.
	 */
	@Test(expected = NullPointerException.class)
	public void testHyperMetadataToTransitionTransformerNull() {
		new HyperMetadataToTransitionTransformer<Label, Transition<Label>, TransitionFactory<Label, Transition<Label>>>(null);
	}
	
	/**
	 * Test method for {@link it.polimi.automata.io.HyperMetadataToTransitionTransformer#HyperMetadataToTransitionTransformer(it.polimi.automata.transition.TransitionFactory)}.
	 */
	@Test
	public void testHyperMetadataToTransitionTransformer() {
		assertNotNull(new HyperMetadataToTransitionTransformer<Label, Transition<Label>, TransitionFactory<Label, Transition<Label>>>(factory));
	}

	/**
	 * Test method for {@link it.polimi.automata.io.HyperMetadataToTransitionTransformer#transform(null)}.
	 */
	@Test(expected = NullPointerException.class)
	public void testTransformNull() {
		new HyperMetadataToTransitionTransformer<Label, Transition<Label>, TransitionFactory<Label, Transition<Label>>>(factory).transform(null);
	}
	
	/**
	 * Test method for {@link it.polimi.automata.io.HyperMetadataToTransitionTransformer#transform(edu.uci.ics.jung.io.graphml.HyperEdgeMetadata)}.
	 */
	@Test
	public void testTransform() {
		assertEquals(t, new HyperMetadataToTransitionTransformer<Label, Transition<Label>, TransitionFactory<Label, Transition<Label>>>(factory).transform(tMetadata));
	}

}
