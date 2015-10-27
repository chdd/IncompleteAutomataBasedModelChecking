package it.polimi.automata.io.out.transitions;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Set;

import it.polimi.automata.IntersectionBA;
import it.polimi.automata.state.State;
import it.polimi.automata.state.StateFactory;
import it.polimi.automata.transition.ModelTransitionFactory;
import it.polimi.automata.transition.Transition;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.junit.Test;
import org.w3c.dom.Element;

import rwth.i2.ltl2ba4j.model.IGraphProposition;
import rwth.i2.ltl2ba4j.model.impl.GraphProposition;

public class IntBATransitionToElementTransformerTest {

	/**
	 * Test method for
	 * {@link it.polimi.automata.io.out.transitions.IntBATransitionToElementTransformer#transform(Document)}
	 * .
	 * @throws ParserConfigurationException 
	 */
	@Test(expected = NullPointerException.class)
	public void testTransitionToElementTransformer_NullDocument() {
		new IntBATransitionToElementTransformer(new IntersectionBA(),null);
	}

	/**
	 * Test method for
	 * {@link it.polimi.automata.io.out.transitions.IntBATransitionToElementTransformer#transform(Document)}
	 * .
	 * @throws ParserConfigurationException 
	 */
	@Test(expected = NullPointerException.class)
	public void testTransitionToElementTransformer_NullBA() throws ParserConfigurationException {
		new IntBATransitionToElementTransformer(null,DocumentBuilderFactory
				.newInstance().newDocumentBuilder().newDocument());
	}
	
	/**
	 * Test method for
	 * {@link it.polimi.automata.io.out.states.IntBATransitionToElementTransformer#transform(Document)}
	 * .
	 * @throws ParserConfigurationException 
	 */
	@Test(expected = NullPointerException.class)
	public void testTransformNullPointerException() throws ParserConfigurationException {
		new IntBATransitionToElementTransformer(new IntersectionBA(),DocumentBuilderFactory
				.newInstance().newDocumentBuilder().newDocument()).transform(null);
	}
	
	/**
	 * Test method for
	 * {@link it.polimi.automata.io.out.states.IntBATransitionToElementTransformer#transform(Document)}
	 * .
	 * @throws ParserConfigurationException 
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testTransformIllegalArgument() throws ParserConfigurationException {
		new IntBATransitionToElementTransformer(new IntersectionBA(),DocumentBuilderFactory
				.newInstance().newDocumentBuilder().newDocument()).transform(new ModelTransitionFactory().create());
	}
	
	/**
	 * Test method for
	 * {@link it.polimi.automata.io.out.states.IntBATransitionToElementTransformer#transform(Document)}
	 * .
	 * @throws ParserConfigurationException 
	 */
	@Test
	public void testTransformNotConstrained() throws ParserConfigurationException {
		
		IntersectionBA ba=new IntersectionBA();

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
		
		Element element=new IntBATransitionToElementTransformer(ba, DocumentBuilderFactory
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
		assertFalse("A non constrained transition does not have a constrained attribute", element.hasAttribute("constrained"));
		
	}
	
	/**
	 * Test method for
	 * {@link it.polimi.automata.io.out.states.IntBATransitionToElementTransformer#transform(Document)}
	 * .
	 * @throws ParserConfigurationException 
	 */
	@Test
	public void testTransformConstrained() throws ParserConfigurationException {
		
		IntersectionBA ba=new IntersectionBA();

		State state1=new StateFactory().create("state1", 1);
		State state2=new StateFactory().create("state2", 2);
		ba.addState(state1);
		ba.addState(state2);
		IGraphProposition propositionA=new GraphProposition("a", false);
		ba.addProposition(propositionA);
		Set<IGraphProposition> propositions=new HashSet<IGraphProposition>();
		propositions.add(propositionA);
		
		Transition transition=new ModelTransitionFactory().create(1, propositions);
		ba.addConstrainedTransition(state1, state2, transition);
		
		Element element=new IntBATransitionToElementTransformer(ba, DocumentBuilderFactory
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
		assertTrue("A constrained transition has a constrained attribute", element.hasAttribute("constrained"));
		assertTrue("A constrained transition has a constrained attribute", Boolean.parseBoolean(element.getAttribute("constrained")));
		
	}

}
