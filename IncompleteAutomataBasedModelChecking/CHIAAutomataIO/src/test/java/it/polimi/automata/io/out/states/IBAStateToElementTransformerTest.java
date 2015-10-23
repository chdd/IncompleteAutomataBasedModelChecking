package it.polimi.automata.io.out.states;

import static org.junit.Assert.*;
import it.polimi.automata.IBA;
import it.polimi.automata.state.State;
import it.polimi.automata.state.StateFactory;
import it.polimi.automata.transition.ClaimTransitionFactory;
import it.polimi.automata.transition.ModelTransitionFactory;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.junit.Test;
import org.w3c.dom.Element;

/**
 * @author Claudio Menghi
 *
 */
public class IBAStateToElementTransformerTest {

	/**
	 * Test method for
	 * {@link it.polimi.automata.io.out.states.BAStateToElementTransformer#BAStateToElementTransformer(Document)}
	 * .
	 * 
	 * @throws ParserConfigurationException
	 */
	@Test(expected = NullPointerException.class)
	public void testIBAStateToElementTransformerNull()
			throws ParserConfigurationException {
		new IBAStateToElementTransformer(null, DocumentBuilderFactory
				.newInstance().newDocumentBuilder().newDocument());
	}

	/**
	 * Test method for
	 * {@link it.polimi.automata.io.out.states.BAStateToElementTransformer#BAStateToElementTransformer(Document)}
	 * .
	 * 
	 * @throws ParserConfigurationException
	 */
	@Test
	public void testStateToElementTrasformer()
			throws ParserConfigurationException {
		assertNotNull(
				"The constructor of the BAStateToElementTransformer class creates a new document",
				new IBAStateToElementTransformer(new IBA(
						new ModelTransitionFactory()), DocumentBuilderFactory
						.newInstance().newDocumentBuilder().newDocument()));
	}

	/**
	 * Test method for
	 * {@link it.polimi.automata.io.out.states.BAStateToElementTransformer#transform(Document)}
	 * .
	 * 
	 * @throws ParserConfigurationException
	 */
	@Test(expected = NullPointerException.class)
	public void testStateToElementTrasformerNull()
			throws ParserConfigurationException {
		new IBAStateToElementTransformer(new IBA(new ModelTransitionFactory()),
				DocumentBuilderFactory.newInstance().newDocumentBuilder()
						.newDocument()).transform(null);
	}

	/**
	 * Test method for
	 * {@link it.polimi.automata.io.out.states.BAStateToElementTransformer#transform(Document)}
	 * .
	 * 
	 * @throws ParserConfigurationException
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testStateToElementTrasformerNotBAState()
			throws ParserConfigurationException {
		State state1 = new StateFactory().create("nome", 1);
		IBA iba = new IBA(new ModelTransitionFactory());
		new IBAStateToElementTransformer(iba, DocumentBuilderFactory
				.newInstance().newDocumentBuilder().newDocument())
				.transform(state1);
	}

	/**
	 * Test method for
	 * {@link it.polimi.automata.io.out.states.BAStateToElementTransformer#transform(Document)}
	 * .
	 * 
	 * @throws ParserConfigurationException
	 */
	@Test
	public void testStateToElementTrasformerNotInitialNotAcceptingState()
			throws ParserConfigurationException {
		State state1 = new StateFactory().create("nome", 1);
		IBA iba = new IBA(new ClaimTransitionFactory());
		iba.addState(state1);
		Element element = new IBAStateToElementTransformer(iba,
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
	 * {@link it.polimi.automata.io.out.states.BAStateToElementTransformer#transform(Document)}
	 * .
	 * 
	 * @throws ParserConfigurationException
	 */
	@Test
	public void testStateToElementTrasformerInitialAcceptingState()
			throws ParserConfigurationException {
		State state1 = new StateFactory().create("nome", 1);
		IBA iba = new IBA(new ClaimTransitionFactory());
		iba.addState(state1);
		iba.addInitialState(state1);
		iba.addAcceptState(state1);
		iba.addBlackBoxState(state1);
		Element element = new IBAStateToElementTransformer(iba,
				DocumentBuilderFactory.newInstance().newDocumentBuilder()
						.newDocument()).transform(state1);
		assertTrue("It must  exist the initial attribute",
				element.hasAttribute("initial"));
		assertTrue("It must  exist the accepting attribute",
				element.hasAttribute("accepting"));

		assertTrue("The accepting attribute must be true", Boolean.parseBoolean(element.getAttribute("accepting")));
		assertTrue("The initial attribute must be true", Boolean.parseBoolean(element.getAttribute("initial")));
		assertTrue("The blackbox attribute must be true", Boolean.parseBoolean(element.getAttribute("blackbox")));
	}
}
