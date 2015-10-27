package it.polimi.automata.io.out.states;

import static org.junit.Assert.*;
import it.polimi.automata.IntersectionBA;
import it.polimi.automata.state.State;
import it.polimi.automata.state.StateFactory;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.junit.Test;
import org.w3c.dom.Element;

public class IntBAStateToElementTransformerTest {


	/**
	 * Test method for
	 * {@link it.polimi.automata.io.out.states.IntBAStateToElementTransformerTest#IBAStateToElementTransformer(Document)}
	 * .
	 * 
	 * @throws ParserConfigurationException
	 */
	@Test(expected = NullPointerException.class)
	public void testIBAStateToElementTransformerNull()
			throws ParserConfigurationException {
	
		new IntBAStateToElementTransformer(null, DocumentBuilderFactory
				.newInstance().newDocumentBuilder().newDocument());
	}

	/**
	 * Test method for
	 * {@link it.polimi.automata.io.out.states.IntBAStateToElementTransformerTest#IntBAStateToElementTransformerTest(Document)}
	 * .
	 * 
	 * @throws ParserConfigurationException
	 */
	@Test
	public void testStateToElementTrasformer()
			throws ParserConfigurationException {
		assertNotNull(
				"The constructor of the BAStateToElementTransformer class creates a new document",
				new IntBAStateToElementTransformer(new IntersectionBA(), DocumentBuilderFactory
						.newInstance().newDocumentBuilder().newDocument()));
	}

	/**
	 * Test method for
	 * {@link it.polimi.automata.io.out.states.IntBAStateToElementTransformerTest#transform(Document)}
	 * .
	 * 
	 * @throws ParserConfigurationException
	 */
	@Test(expected = NullPointerException.class)
	public void testStateToElementTrasformerNull()
			throws ParserConfigurationException {
		new IntBAStateToElementTransformer(new IntersectionBA(),
				DocumentBuilderFactory.newInstance().newDocumentBuilder()
						.newDocument()).transform(null);
	}

	/**
	 * Test method for
	 * {@link it.polimi.automata.io.out.states.IntBAStateToElementTransformerTest#transform(Document)}
	 * .
	 * 
	 * @throws ParserConfigurationException
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testStateToElementTrasformerNotBAState()
			throws ParserConfigurationException {
		State state1 = new StateFactory().create("nome", 1);
		IntersectionBA intersectionBA = new IntersectionBA();
		new IntBAStateToElementTransformer(intersectionBA, DocumentBuilderFactory
				.newInstance().newDocumentBuilder().newDocument())
				.transform(state1);
	}

	/**
	 * Test method for
	 * {@link it.polimi.automata.io.out.states.IntBAStateToElementTransformerTest#transform(Document)}
	 * .
	 * 
	 * @throws ParserConfigurationException
	 */
	@Test
	public void testStateToElementTrasformerNotInitialNotAcceptingState()
			throws ParserConfigurationException {
		State state1 = new StateFactory().create("nome", 1);
		IntersectionBA intersectionBA = new IntersectionBA();
		intersectionBA.addState(state1);
		Element element = new IntBAStateToElementTransformer(intersectionBA,
				DocumentBuilderFactory.newInstance().newDocumentBuilder()
						.newDocument()).transform(state1);
		assertTrue("It must not exist the initial attribute",
				!element.hasAttribute("accepting"));
		assertTrue("It must not exist the initial atribute",
				!element.hasAttribute("accepting"));
		assertTrue("It must not exist the initial attribute",
				!element.hasAttribute("accepting"));
		assertTrue("It must not exist the blackbox atribute",
				!element.hasAttribute("accepting"));
		

	}

	/**
	 * Test method for
	 * {@link it.polimi.automata.io.out.states.IntBAStateToElementTransformerTest#transform(Document)}
	 * .
	 * 
	 * @throws ParserConfigurationException
	 */
	@Test
	public void testStateToElementTrasformerInitialAcceptingState()
			throws ParserConfigurationException {
		State state1 = new StateFactory().create("nome", 1);
		IntersectionBA intersectionBa = new IntersectionBA();
		intersectionBa.addState(state1);
		intersectionBa.addInitialState(state1);
		intersectionBa.addAcceptState(state1);
		intersectionBa.addMixedState(state1);
		Element element = new IntBAStateToElementTransformer(intersectionBa,
				DocumentBuilderFactory.newInstance().newDocumentBuilder()
						.newDocument()).transform(state1);
		assertTrue("It must  exist the initial attribute",
				element.hasAttribute("initial"));
		assertTrue("It must  exist the accepting attribute",
				element.hasAttribute("accepting"));

		assertTrue("The accepting attribute must be true", Boolean.parseBoolean(element.getAttribute("accepting")));
		assertTrue("The initial attribute must be true", Boolean.parseBoolean(element.getAttribute("initial")));
		assertTrue("The mixed attribute must be true", Boolean.parseBoolean(element.getAttribute("mixed")));
	}

}
