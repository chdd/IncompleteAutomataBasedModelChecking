/**
 * 
 */
package it.polimi.automata.io;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;
import it.polimi.automata.BA;
import it.polimi.automata.Constants;
import it.polimi.automata.impl.BAFactoryImpl;
import it.polimi.automata.labeling.Label;
import it.polimi.automata.state.StateFactory;
import it.polimi.automata.state.impl.StateImpl;
import it.polimi.automata.transition.Transition;
import it.polimi.automata.transition.impl.TransitionImpl;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import edu.uci.ics.jung.io.graphml.NodeMetadata;

/**
 * @author claudiomenghi
 *
 */
public class MetadataToBAStateTransformerTest {

	
	private BA<Label, StateImpl, TransitionImpl<Label>> ba;
	
	@Mock
	private StateFactory<StateImpl> stateFactory;

	@Mock
	private StateImpl state1;
	
	@Mock
	private NodeMetadata state1Metadata;

	@Mock
	private StateImpl state2;
	
	@Mock
	private NodeMetadata state2Metadata;
	
	@Mock
	private StateImpl state3;

	@Mock
	private NodeMetadata state3Metadata;
	
	@Before
	public void setUp() {
		this.ba=new BAFactoryImpl<Label, StateImpl, TransitionImpl<Label>>().create();
		MockitoAnnotations.initMocks(this);
		when(state1.getId()).thenReturn(1);
		when(state1.getName()).thenReturn("state1");
		when(state1Metadata.getProperty(Constants.NAMETAG)).thenReturn("state1");
		when(state1Metadata.getProperty(Constants.INITIALTAG)).thenReturn(Constants.FALSEVALUE);
		when(state1Metadata.getProperty(Constants.ACCEPTINGTAG)).thenReturn(Constants.FALSEVALUE);
		when(state1Metadata.getId()).thenReturn("1");
		
		when(stateFactory.create("state1", 1)).thenReturn(state1);
		
		when(state2.getId()).thenReturn(2);
		when(state2.getName()).thenReturn("state2");
		when(state2Metadata.getProperty(Constants.NAMETAG)).thenReturn("state2");
		when(state2Metadata.getProperty(Constants.INITIALTAG)).thenReturn(Constants.TRUEVALUE);
		when(state2Metadata.getProperty(Constants.ACCEPTINGTAG)).thenReturn(Constants.FALSEVALUE);
		when(state2Metadata.getId()).thenReturn("2");
		
		when(stateFactory.create("state2", 2)).thenReturn(state2);
		
		when(state3.getId()).thenReturn(3);
		when(state3.getName()).thenReturn("state3");
		when(state3Metadata.getProperty(Constants.NAMETAG)).thenReturn("state3");
		when(state3Metadata.getProperty(Constants.INITIALTAG)).thenReturn(Constants.FALSEVALUE);
		when(state3Metadata.getProperty(Constants.ACCEPTINGTAG)).thenReturn(Constants.TRUEVALUE);
		when(state3Metadata.getId()).thenReturn("3");
		
		when(stateFactory.create("state3", 3)).thenReturn(state3);
	}
	
	/**
	 * Test method for {@link it.polimi.automata.io.MetadataToBAStateTransformer#MetadataToBAStateTransformer(null, it.polimi.automata.BA)}.
	 */
	@Test(expected = NullPointerException.class)
	public void testMetadataToBAStateTransformerStateFactoryNull() {
		new MetadataToBAStateTransformer<Label, StateImpl, TransitionImpl<Label>>(null, ba);
	}
	
	/**
	 * Test method for {@link it.polimi.automata.io.MetadataToBAStateTransformer#MetadataToBAStateTransformer(it.polimi.automata.state.StateFactory, null)}.
	 */
	@Test(expected = NullPointerException.class)
	public void testMetadataToBAStateTransformerAutomataNull() {
		new MetadataToBAStateTransformer<Label, StateImpl, Transition<Label>>(stateFactory, null);
	}
	
	/**
	 * Test method for {@link it.polimi.automata.io.MetadataToBAStateTransformer#MetadataToBAStateTransformer(it.polimi.automata.state.StateFactory, it.polimi.automata.BA)}.
	 */
	@Test
	public void testMetadataToBAStateTransformer() {
		assertNotNull(new MetadataToBAStateTransformer<Label, StateImpl, TransitionImpl<Label>>(stateFactory, ba));
	}

	/**
	 * Test method for {@link it.polimi.automata.io.MetadataToBAStateTransformer#transform(null)}.
	 */
	@Test(expected = NullPointerException.class)
	public void testTransformNull() {
		MetadataToBAStateTransformer<Label, StateImpl, TransitionImpl<Label>> metadataTransformer=new MetadataToBAStateTransformer<Label, StateImpl, TransitionImpl<Label>>(stateFactory, ba);
		metadataTransformer.transform(null);
	}
	
	/**
	 * Test method for {@link it.polimi.automata.io.MetadataToBAStateTransformer#transform(edu.uci.ics.jung.io.graphml.NodeMetadata)}.
	 */
	@Test
	public void testTransform() {
		MetadataToBAStateTransformer<Label, StateImpl, TransitionImpl<Label>> metadataTransformer=new MetadataToBAStateTransformer<Label, StateImpl, TransitionImpl<Label>>(stateFactory, ba);
		assertEquals(this.state1, metadataTransformer.transform(this.state1Metadata));
		assertEquals(this.state2, metadataTransformer.transform(this.state2Metadata));
		assertEquals(this.state3, metadataTransformer.transform(this.state3Metadata));
		assertTrue(this.ba.getInitialStates().contains(state2));
		assertFalse(this.ba.getInitialStates().contains(state1));
		assertFalse(this.ba.getInitialStates().contains(state3));
		assertTrue(this.ba.getAcceptStates().contains(state3));
		assertFalse(this.ba.getAcceptStates().contains(state1));
		assertFalse(this.ba.getAcceptStates().contains(state2));
		
	}

}
