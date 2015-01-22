/**
 * 
 */
package it.polimi.automata.io;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;
import it.polimi.automata.BA;
import it.polimi.automata.labeling.Label;
import it.polimi.automata.state.impl.StateImpl;
import it.polimi.automata.transition.impl.TransitionImpl;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import edu.uci.ics.jung.graph.DirectedSparseGraph;
import edu.uci.ics.jung.io.graphml.GraphMetadata;

/**
 * @author claudiomenghi
 *
 */
public class MetadataToBATransformerTest {

	@Mock
	private BA<Label, StateImpl, TransitionImpl<Label>> ba;
	
	@Mock
	private DirectedSparseGraph<StateImpl, TransitionImpl<Label>> graph;
	
	@Mock
	private GraphMetadata graphMetadata;
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		when(ba.getGraph()).thenReturn(graph);

	}
	
	/**
	 * Test method for {@link it.polimi.automata.io.MetadataToBATransformer#MetadataToBATransformer(null)}.
	 */
	@Test(expected = NullPointerException.class)
	public void testMetadataToBATransformerNull() {
		new MetadataToBATransformer<Label, StateImpl, TransitionImpl<Label>>(null);
	}
	/**
	 * Test method for {@link it.polimi.automata.io.MetadataToBATransformer#MetadataToBATransformer(it.polimi.automata.BA)}.
	 */
	@Test
	public void testMetadataToBATransformer() {
		assertNotNull(new MetadataToBATransformer<Label, StateImpl, TransitionImpl<Label>>(ba));
	}

	/**
	 * Test method for {@link it.polimi.automata.io.MetadataToBATransformer#transform(null)}.
	 */
	@Test(expected = NullPointerException.class)
	public void testTransformNull() {
		MetadataToBATransformer<Label, StateImpl, TransitionImpl<Label>> transformer=new MetadataToBATransformer<Label, StateImpl, TransitionImpl<Label>>(ba);
		transformer.transform(null);
	}
	
	
	/**
	 * Test method for {@link it.polimi.automata.io.MetadataToBATransformer#transform(edu.uci.ics.jung.io.graphml.GraphMetadata)}.
	 */
	@Test
	public void testTransform() {
		MetadataToBATransformer<Label, StateImpl, TransitionImpl<Label>> transformer=new MetadataToBATransformer<Label, StateImpl, TransitionImpl<Label>>(ba);
		assertEquals(transformer.transform(graphMetadata), this.graph);
		
	}

}
