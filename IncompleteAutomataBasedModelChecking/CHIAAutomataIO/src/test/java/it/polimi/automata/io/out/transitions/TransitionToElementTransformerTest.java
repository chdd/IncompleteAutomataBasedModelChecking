package it.polimi.automata.io.out.transitions;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Set;

import it.polimi.automata.transition.ModelTransitionFactory;
import it.polimi.automata.transition.Transition;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.junit.Test;
import org.w3c.dom.Element;

import rwth.i2.ltl2ba4j.model.IGraphProposition;
import rwth.i2.ltl2ba4j.model.impl.GraphProposition;

public class TransitionToElementTransformerTest {

	/**
	 * Test method for
	 * {@link it.polimi.automata.io.out.states.TransitionToElementTransformer#TransitionToElementTransformer(Document)}
	 * .
	 */
	@Test(expected = NullPointerException.class)
	public void testTransitionToElementTransformer_Null() {
		new TransitionToElementTransformer(null);
	}

	/**
	 * Test method for
	 * {@link it.polimi.automata.io.out.states.TransitionToElementTransformer#TransitionToElementTransformer(Document)}
	 * .
	 * 
	 * @throws ParserConfigurationException
	 */
	@Test
	public void testTransitionToElementTransformer()
			throws ParserConfigurationException {
		assertNotNull("The constructor correctly inizializes the transformer",
				new TransitionToElementTransformer(DocumentBuilderFactory
						.newInstance().newDocumentBuilder().newDocument()));
	}

	/**
	 * Test method for
	 * {@link it.polimi.automata.io.out.states.TransitionToElementTransformer#transform(Transition)}
	 * .
	 * 
	 * @throws ParserConfigurationException
	 */
	@Test(expected = NullPointerException.class)
	public void testTransitionToElementTransform_Null()
			throws ParserConfigurationException {
		assertNotNull("The constructor correctly inizializes the transformer",
				new TransitionToElementTransformer(DocumentBuilderFactory
						.newInstance().newDocumentBuilder().newDocument())
						.transform(null));
	}

	/**
	 * Test method for
	 * {@link it.polimi.automata.io.out.states.TransitionToElementTransformer#transform(Transition)}
	 * .
	 * 
	 * @throws ParserConfigurationException
	 */
	@Test
	public void testTransitionToElementTransform()
			throws ParserConfigurationException {

		Set<IGraphProposition> propositions=new HashSet<IGraphProposition>();
		propositions.add(new GraphProposition("a", false));
		Transition transition = new ModelTransitionFactory().create(1,
				propositions);

		Element transitionElement = new TransitionToElementTransformer(
				DocumentBuilderFactory.newInstance().newDocumentBuilder()
						.newDocument()).transform(transition);
		assertEquals("The id is setted correctly", "1",
				transitionElement.getAttribute("id"));
		assertEquals("The AP is setted correctly", "a",
				transitionElement.getAttribute("propositions"));
	}
}
