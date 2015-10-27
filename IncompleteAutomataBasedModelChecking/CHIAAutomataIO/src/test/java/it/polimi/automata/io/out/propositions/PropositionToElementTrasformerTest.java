package it.polimi.automata.io.out.propositions;

import static org.junit.Assert.*;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import it.polimi.automata.io.out.transitions.TransitionToElementTransformer;

import org.junit.Test;

import rwth.i2.ltl2ba4j.model.IGraphProposition;
import rwth.i2.ltl2ba4j.model.impl.GraphProposition;

public class PropositionToElementTrasformerTest {

	/**
	 * Test method for
	 * {@link it.polimi.automata.io.out.propositions.PropositionToElementTrasformer#PropositionToElementTrasformer(Document)}
	 * .
	 */
	@Test(expected = NullPointerException.class)
	public void testPropositionToElementTrasformer_Null() {
		new TransitionToElementTransformer(null);
	}
	
	/**
	 * Test method for
	 * {@link it.polimi.automata.io.out.propositions.PropositionToElementTrasformer#transform(IGraphProposition)}
	 * .
	 * @throws ParserConfigurationException 
	 */
	@Test(expected = NullPointerException.class)
	public void testTransform_Null() throws ParserConfigurationException {
		new PropositionToElementTrasformer(DocumentBuilderFactory
				.newInstance().newDocumentBuilder().newDocument()).transform(null);
	}
	
	/**
	 * Test method for
	 * {@link it.polimi.automata.io.out.propositions.PropositionToElementTrasformer#transform(IGraphProposition)}
	 * .
	 * @throws ParserConfigurationException 
	 */
	@Test
	public void testTransform() throws ParserConfigurationException {
		assertEquals("The atomic proposition is correctly transformed", "a",
		new PropositionToElementTrasformer(DocumentBuilderFactory
				.newInstance().newDocumentBuilder().newDocument()).transform(new GraphProposition("a", false)).getAttribute("name"));
	}

}
