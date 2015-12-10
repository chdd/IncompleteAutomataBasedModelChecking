/**
 * 
 */
package it.polimi.constraints.io.in.replacement;

import static org.junit.Assert.*;
import it.polimi.automata.state.State;
import it.polimi.automata.state.StateFactory;
import it.polimi.automata.transition.ModelTransitionFactory;
import it.polimi.constraints.components.Replacement;
import it.polimi.constraints.transitions.PluggingTransition;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;
import org.xml.sax.SAXParseException;

import rwth.i2.ltl2ba4j.model.IGraphProposition;
import rwth.i2.ltl2ba4j.model.impl.GraphProposition;

/**
 * @author Claudio Menghi
 *
 */
public class ElementToReplacementTransformerTest {

	private static final String path = "it.polimi.constraints";

	/**
	 * Test method for
	 * {@link it.polimi.constraints.io.in.replacement.ElementToReplacementTransformer#ElementToReplacementTransformer()}
	 * .
	 */
	@Test
	public void testElementToReplacementTransformer() {
		assertNotNull(new ElementToReplacementTransformer());
	}

	/**
	 * Test method for
	 * {@link it.polimi.constraints.io.in.replacement.ElementToReplacementTransformer#transform(org.w3c.dom.Element)}
	 * .
	 */
	@Test(expected = NullPointerException.class)
	public void testTransformNull() {
		new ElementToReplacementTransformer().transform(null);
	}

	/**
	 * Test method for
	 * {@link it.polimi.constraints.io.in.replacement.ElementToReplacementTransformer#transform(org.w3c.dom.Element)}
	 * .
	 * @throws Exception 
	 */
	@Test
	public void testTransformShouldCorrectlyLoadIBA()
			throws Exception {
		Replacement replacement = new ReplacementReader(new File(getClass()
				.getClassLoader().getResource(path + "/test01/replacement.xml")
				.getFile())).perform();

		State state = new StateFactory().create("439", 439);
		assertTrue(replacement.getAutomaton().getStates().contains(state));
		assertTrue(replacement.getAutomaton().getStates().size() == 1);
		assertTrue(replacement.getAutomaton().getAcceptStates().contains(state));
	}

	/**
	 * Test method for
	 * {@link it.polimi.constraints.io.in.replacement.ElementToReplacementTransformer#transform(org.w3c.dom.Element)}
	 * .
	 * @throws Exception 
	 */
	@Test
	public void testTransformShouldCorrectlyLoadTheIncomingTransitions()
			throws Exception {
		Replacement replacement = new ReplacementReader(new File(getClass()
				.getClassLoader().getResource(path + "/test01/replacement.xml")
				.getFile())).perform();

		Set<PluggingTransition> pluggingTransitions = new HashSet<PluggingTransition>();

		Set<IGraphProposition> propositions = new HashSet<IGraphProposition>();
		propositions.add(new GraphProposition("b", false));
		pluggingTransitions.add(new PluggingTransition(2250, new StateFactory()
				.create("431", 431), new StateFactory().create("439", 439),
				new ModelTransitionFactory().create(2250, propositions), true));

		propositions = new HashSet<IGraphProposition>();
		propositions.add(new GraphProposition("b", false));
		pluggingTransitions.add(new PluggingTransition(2265, new StateFactory()
				.create("432", 432), new StateFactory().create("439", 439),
				new ModelTransitionFactory().create(2265, propositions), true));

		propositions = new HashSet<IGraphProposition>();
		propositions.add(new GraphProposition("b", false));
		pluggingTransitions.add(new PluggingTransition(2261, new StateFactory()
				.create("434", 434), new StateFactory().create("439", 439),
				new ModelTransitionFactory().create(2261, propositions), true));

		propositions = new HashSet<IGraphProposition>();
		propositions.add(new GraphProposition("a", false));
		pluggingTransitions.add(new PluggingTransition(2282, new StateFactory()
				.create("436", 436), new StateFactory().create("439", 439),
				new ModelTransitionFactory().create(2282, propositions), true));
		assertEquals(pluggingTransitions, replacement.getIncomingTransitions());
	}

	/**
	 * Test method for
	 * {@link it.polimi.constraints.io.in.replacement.ElementToReplacementTransformer#transform(org.w3c.dom.Element)}
	 * .
	 * @throws Exception 
	 */
	@Test
	public void testTransformShouldCorrectlyLoadTheOutgoingTransitions()
			throws Exception {

		Replacement replacement = new ReplacementReader(new File(getClass()
				.getClassLoader().getResource(path + "/test01/replacement.xml")
				.getFile())).perform();

		System.out.println(replacement.getOutgoingTransitions());
		Set<PluggingTransition> pluggingTransitions = new HashSet<PluggingTransition>();

		Set<IGraphProposition> propositions = new HashSet<IGraphProposition>();
		propositions.add(new GraphProposition("b", false));
		pluggingTransitions
				.add(new PluggingTransition(
						2255,
						new StateFactory().create("439", 439),
						new StateFactory().create("433", 433),
						new ModelTransitionFactory().create(2255, propositions),
						false));
		assertTrue(replacement.getOutgoingTransitions()
				.contains(
						new PluggingTransition(2255, new StateFactory().create(
								"439", 439), new StateFactory().create("433",
								433), new ModelTransitionFactory().create(2255,
								propositions), false)));

		propositions = new HashSet<IGraphProposition>();
		propositions.add(new GraphProposition("a", false));
		pluggingTransitions
				.add(new PluggingTransition(
						2283,
						new StateFactory().create("439", 439),
						new StateFactory().create("437", 437),
						new ModelTransitionFactory().create(2283, propositions),
						false));
		assertTrue(replacement.getOutgoingTransitions()
				.contains(
						new PluggingTransition(2283, new StateFactory().create(
								"439", 439), new StateFactory().create("437",
								437), new ModelTransitionFactory().create(2283,
								propositions), false)));

		propositions = new HashSet<IGraphProposition>();
		propositions.add(new GraphProposition("a", false));
		pluggingTransitions
				.add(new PluggingTransition(
						2274,
						new StateFactory().create("439", 439),
						new StateFactory().create("432", 432),
						new ModelTransitionFactory().create(2274, propositions),
						false));
		assertTrue(replacement.getOutgoingTransitions()
				.contains(
						new PluggingTransition(2274, new StateFactory().create(
								"439", 439), new StateFactory().create("432",
								432), new ModelTransitionFactory().create(2274,
								propositions), false)));

		propositions = new HashSet<IGraphProposition>();
		propositions.add(new GraphProposition("a", false));
		pluggingTransitions
				.add(new PluggingTransition(
						2276,
						new StateFactory().create("439", 439),
						new StateFactory().create("434", 434),
						new ModelTransitionFactory().create(2276, propositions),
						false));
		assertTrue(replacement.getOutgoingTransitions()
				.contains(
						new PluggingTransition(2276, new StateFactory().create(
								"439", 439), new StateFactory().create("434",
								434), new ModelTransitionFactory().create(2276,
								propositions), false)));
		assertEquals(pluggingTransitions, replacement.getOutgoingTransitions());

	}

	/**
	 * Test method for
	 * {@link it.polimi.constraints.io.in.replacement.ElementToReplacementTransformer#transform(org.w3c.dom.Element)}
	 * .
	 * @throws Exception 
	 */
	@Test(expected=SAXParseException.class)
	public void testNoIBA() throws Exception {
	new ReplacementReader(new File(getClass().getClassLoader()
				.getResource(path + "/test01/replacementNoIBA.xml").getFile()))
				.perform();
	}

}
