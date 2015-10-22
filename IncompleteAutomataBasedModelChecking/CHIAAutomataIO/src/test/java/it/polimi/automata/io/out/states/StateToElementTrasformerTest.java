package it.polimi.automata.io.out.states;

import static org.junit.Assert.*;
import it.polimi.automata.state.StateFactory;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.junit.Test;
import org.w3c.dom.Element;

/**
 * @author Claudio Menghi
 *
 */
public class StateToElementTrasformerTest {

	/**
	 * Test method for
	 * {@link it.polimi.automata.io.out.states.StateToElementTrasformer#StateToElementTrasformer(Document)}
	 * .
	 */
	@Test(expected = NullPointerException.class)
	public void testStateToElementTrasformerNull() {
		new StateToElementTrasformer(null);
	}

	/**
	 * Test method for
	 * {@link it.polimi.automata.io.out.states.StateToElementTrasformer#StateToElementTrasformer(Document)}
	 * .
	 * 
	 * @throws ParserConfigurationException
	 */
	@Test
	public void testStateToElementTrasformer()
			throws ParserConfigurationException {
		assertNotNull(
				"The constructor of the StateToElementTransformer class creates a new document",
				new StateToElementTrasformer(DocumentBuilderFactory
						.newInstance().newDocumentBuilder().newDocument()));
	}

	/**
	 * Test method for
	 * {@link it.polimi.automata.io.out.states.StateToElementTrasformer#transform(State)}
	 * .
	 * 
	 * @throws ParserConfigurationException
	 */
	@Test(expected = NullPointerException.class)
	public void testTransformNull() throws ParserConfigurationException {
		new StateToElementTrasformer(DocumentBuilderFactory.newInstance()
				.newDocumentBuilder().newDocument()).transform(null);
	}

	/**
	 * Test method for
	 * {@link it.polimi.automata.io.out.states.StateToElementTrasformer#transform(State)}
	 * .
	 * 
	 * @throws ParserConfigurationException
	 */
	@Test
	public void testTransform() throws ParserConfigurationException {
		Element element = new StateToElementTrasformer(DocumentBuilderFactory
				.newInstance().newDocumentBuilder().newDocument())
				.transform(new StateFactory().create("name", 1));
		assertEquals("The element associated to a state has two attributes", 2,
				element.getAttributes().getLength());
		assertEquals(
				"The name attribute of the element associated to the state corresponds to the name of the state",
				"name", element.getAttribute("name"));
		assertEquals(
				"The id attribute of the element associated to the state corresponds to the id of the state",
				1, Integer.parseInt(element.getAttribute("id")));
	}

}
