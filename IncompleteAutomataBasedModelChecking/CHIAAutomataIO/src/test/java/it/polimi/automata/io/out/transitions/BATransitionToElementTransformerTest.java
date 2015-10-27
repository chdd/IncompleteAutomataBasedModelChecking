package it.polimi.automata.io.out.transitions;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Set;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import it.polimi.automata.BA;
import it.polimi.automata.state.State;
import it.polimi.automata.state.StateFactory;
import it.polimi.automata.transition.ModelTransitionFactory;
import it.polimi.automata.transition.Transition;

import org.junit.Test;
import org.w3c.dom.Element;

import rwth.i2.ltl2ba4j.model.IGraphProposition;
import rwth.i2.ltl2ba4j.model.impl.GraphProposition;

public class BATransitionToElementTransformerTest {

	/**
	 * Test method for
	 * {@link it.polimi.automata.io.out.states.BATransitionToElementTransformer#BATransitionToElementTransformer(Document)}
	 * .
	 */
	@Test(expected = NullPointerException.class)
	public void testTransitionToElementTransformer_NullDocument() {
		new BATransitionToElementTransformer(new BA(new ModelTransitionFactory()),null);
	}

	/**
	 * Test method for
	 * {@link it.polimi.automata.io.out.states.BATransitionToElementTransformer#BATransitionToElementTransformer(Document)}
	 * .
	 * @throws ParserConfigurationException 
	 */
	@Test(expected = NullPointerException.class)
	public void testTransitionToElementTransformer_NullBA() throws ParserConfigurationException {
		new BATransitionToElementTransformer(null,DocumentBuilderFactory
				.newInstance().newDocumentBuilder().newDocument());
	}
	
	/**
	 * Test method for
	 * {@link it.polimi.automata.io.out.states.BATransitionToElementTransformer#transform(Document)}
	 * .
	 * @throws ParserConfigurationException 
	 */
	@Test(expected = NullPointerException.class)
	public void testTransformNullPointerException() throws ParserConfigurationException {
		new BATransitionToElementTransformer(new BA(new ModelTransitionFactory()),DocumentBuilderFactory
				.newInstance().newDocumentBuilder().newDocument()).transform(null);
	}
	
	/**
	 * Test method for
	 * {@link it.polimi.automata.io.out.states.BATransitionToElementTransformer#transform(Document)}
	 * .
	 * @throws ParserConfigurationException 
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testTransformIllegalArgument() throws ParserConfigurationException {
		new BATransitionToElementTransformer(new BA(new ModelTransitionFactory()),DocumentBuilderFactory
				.newInstance().newDocumentBuilder().newDocument()).transform(new ModelTransitionFactory().create());
	}
	
	/**
	 * Test method for
	 * {@link it.polimi.automata.io.out.states.BATransitionToElementTransformer#transform(Document)}
	 * .
	 * @throws ParserConfigurationException 
	 */
	@Test
	public void testTransform() throws ParserConfigurationException {
		
		BA ba=new BA(new ModelTransitionFactory());
		State state1=new StateFactory().create("state1", 1);
		State state2=new StateFactory().create("state2", 2);
		ba.addState(state1);
		ba.addState(state2);
		IGraphProposition propositionA=new GraphProposition("a", false);
		ba.addProposition(propositionA);
		Set<IGraphProposition> propositions=new HashSet<IGraphProposition>();
		propositions.add(propositionA);
		
		Transition transition=new ModelTransitionFactory().create(1, propositions);
		ba.addTransition(state1, state2, transition);
		
		Element element=new BATransitionToElementTransformer(ba, DocumentBuilderFactory
				.newInstance().newDocumentBuilder().newDocument()).transform(transition);
		
		assertNotNull("The transformed element must not be null", element);
		assertEquals("The id is setted correctly", "1",
				element.getAttribute("id"));
		assertEquals("The AP is setted correctly", "a",
				element.getAttribute("propositions"));
		
		assertEquals("The source of the transition must be correct", "1",
				element.getAttribute("source"));
		
		assertEquals("The destination of the transition must be correct", "2",
				element.getAttribute("destination"));
		
	}
}
