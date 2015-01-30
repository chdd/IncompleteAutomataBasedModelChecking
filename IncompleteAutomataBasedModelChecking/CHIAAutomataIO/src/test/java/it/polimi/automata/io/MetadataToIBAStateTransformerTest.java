/**
 * 
 */
package it.polimi.automata.io;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;
import it.polimi.automata.Constants;
import it.polimi.automata.IBA;
import it.polimi.automata.impl.IBAFactoryImpl;
import it.polimi.automata.labeling.Label;
import it.polimi.automata.state.StateFactory;
import it.polimi.automata.state.impl.StateImpl;
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
public class MetadataToIBAStateTransformerTest {

	private IBA<Label, StateImpl, TransitionImpl<Label>> iba;

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
		this.iba = new IBAFactoryImpl<Label, StateImpl, TransitionImpl<Label>>()
				.create();
		MockitoAnnotations.initMocks(this);
		when(state1.getId()).thenReturn(1);
		when(state1.getName()).thenReturn("state1");
		when(state1Metadata.getProperty(Constants.NAMETAG))
				.thenReturn("state1");
		when(state1Metadata.getProperty(Constants.INITIALTAG)).thenReturn(
				Constants.FALSEVALUE);
		when(state1Metadata.getProperty(Constants.ACCEPTINGTAG)).thenReturn(
				Constants.FALSEVALUE);
		when(state1Metadata.getProperty(Constants.TRANSPARENTTAG)).thenReturn(
				Constants.FALSEVALUE);
		when(state1Metadata.getId()).thenReturn("1");

		when(stateFactory.create("state1", 1)).thenReturn(state1);

		when(state2.getId()).thenReturn(2);
		when(state2.getName()).thenReturn("state2");
		when(state2Metadata.getProperty(Constants.NAMETAG))
				.thenReturn("state2");
		when(state2Metadata.getProperty(Constants.INITIALTAG)).thenReturn(
				Constants.TRUEVALUE);
		when(state2Metadata.getProperty(Constants.ACCEPTINGTAG)).thenReturn(
				Constants.FALSEVALUE);
		when(state2Metadata.getProperty(Constants.TRANSPARENTTAG)).thenReturn(
				Constants.TRUEVALUE);
		when(state2Metadata.getId()).thenReturn("2");

		when(stateFactory.create("state2", 2)).thenReturn(state2);

		when(state3.getId()).thenReturn(3);
		when(state3.getName()).thenReturn("state3");
		when(state3Metadata.getProperty(Constants.NAMETAG))
				.thenReturn("state3");
		when(state3Metadata.getProperty(Constants.INITIALTAG)).thenReturn(
				Constants.FALSEVALUE);
		when(state3Metadata.getProperty(Constants.ACCEPTINGTAG)).thenReturn(
				Constants.TRUEVALUE);
		when(state3Metadata.getProperty(Constants.TRANSPARENTTAG)).thenReturn(
				Constants.FALSEVALUE);
		when(state3Metadata.getId()).thenReturn("3");

		when(stateFactory.create("state3", 3)).thenReturn(state3);
	}

	/**
	 * Test method for
	 * {@link it.polimi.automata.io.MetadataToIBAStateTransformer#MetadataToIBAStateTransformer(null, it.polimi.automata.IBA)}
	 * .
	 */
	@Test(expected = NullPointerException.class)
	public void testMetadataToIBAStateTransformerNullFactory() {
		new MetadataToIBAStateTransformer<Label, StateImpl, TransitionImpl<Label>>(null, iba);
	}

	/**
	 * Test method for
	 * {@link it.polimi.automata.io.MetadataToIBAStateTransformer#MetadataToIBAStateTransformer(it.polimi.automata.state.StateFactory, null)}
	 * .
	 */
	@Test(expected = NullPointerException.class)
	public void testMetadataToIBAStateTransformerNullAutomata() {
		new MetadataToIBAStateTransformer<Label, StateImpl, TransitionImpl<Label>>(stateFactory, null);
	}
	
	/**
	 * Test method for
	 * {@link it.polimi.automata.io.MetadataToIBAStateTransformer#MetadataToIBAStateTransformer(it.polimi.automata.state.StateFactory, it.polimi.automata.IBA)}
	 * .
	 */
	@Test
	public void testMetadataToIBAStateTransformer() {
		assertNotNull(new MetadataToIBAStateTransformer<Label, StateImpl, TransitionImpl<Label>>(stateFactory, iba));
	}

	/**
	 * Test method for
	 * {@link it.polimi.automata.io.MetadataToIBAStateTransformer#transform(null)}
	 * .
	 */
	@Test(expected = NullPointerException.class)
	public void testTransformNull() {
		new MetadataToIBAStateTransformer<Label, StateImpl, TransitionImpl<Label>>(stateFactory, iba).transform(null);
	}
	
	/**
	 * Test method for
	 * {@link it.polimi.automata.io.MetadataToIBAStateTransformer#transform(edu.uci.ics.jung.io.graphml.NodeMetadata)}
	 * .
	 */
	@Test
	public void testTransform() {
		MetadataToIBAStateTransformer<Label, StateImpl, TransitionImpl<Label>> metadataTransformer=new MetadataToIBAStateTransformer<Label, StateImpl, TransitionImpl<Label>>(stateFactory, iba);
		assertEquals(this.state1, metadataTransformer.transform(this.state1Metadata));
		assertEquals(this.state2, metadataTransformer.transform(this.state2Metadata));
		assertEquals(this.state3, metadataTransformer.transform(this.state3Metadata));
		assertTrue(this.iba.getInitialStates().contains(state2));
		assertFalse(this.iba.getInitialStates().contains(state1));
		assertFalse(this.iba.getInitialStates().contains(state3));
		assertTrue(this.iba.getAcceptStates().contains(state3));
		assertFalse(this.iba.getAcceptStates().contains(state1));
		assertFalse(this.iba.getAcceptStates().contains(state2));
		assertTrue(this.iba.getTransparentStates().contains(state2));
		assertFalse(this.iba.getTransparentStates().contains(state1));
		assertFalse(this.iba.getTransparentStates().contains(state3));
	
	}

}
