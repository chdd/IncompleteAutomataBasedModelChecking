package it.polimi.automata.io.in.transitions;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;
import it.polimi.automata.AutomataIOConstants;
import it.polimi.automata.IBA;
import it.polimi.automata.state.State;
import it.polimi.automata.state.StateFactory;
import it.polimi.automata.transition.ModelTransitionFactory;
import it.polimi.automata.transition.Transition;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.w3c.dom.Element;

import rwth.i2.ltl2ba4j.model.IGraphProposition;
import rwth.i2.ltl2ba4j.model.impl.GraphProposition;


/**
 * 
 * @author Claudio Menghi
 *
 */
public class IBAElementToTransitionTransformerTest {

	
	@Mock
	private Element transitionNoId;

	@Mock
	private Element transitionNotCorrectId;

	@Mock
	private Element transitionStringId;

	@Mock
	private Element transition;

	@Mock
	private Element transitionNoPropositions;

	@Mock
	private Element transitionNoSource;

	@Mock
	private Element transitionNotCorrectSourceState;

	@Mock
	private Element transitionNotCorrectDestinationState;

	@Mock
	private State state1;

	@Mock
	private State state2;

	@Mock
	private Element transitionNoDestination;

	public IBAElementToTransitionTransformerTest() {
		MockitoAnnotations.initMocks(this);
		when(transitionNoId.hasAttribute("id")).thenReturn(true);

		when(transitionStringId.hasAttribute("id")).thenReturn(true);
		when(transitionStringId.getAttribute("id")).thenReturn(
				"this is a wrong id");

		when(transitionNoSource.hasAttribute("id")).thenReturn(true);
		when(transitionNoSource.getAttribute("id")).thenReturn("1");
		when(
				transitionNoSource
						.hasAttribute(AutomataIOConstants.XML_ATTRIBUTE_TRANSITION_SOURCE))
				.thenReturn(false);

		when(transitionNoDestination.hasAttribute("id")).thenReturn(true);
		when(transitionNoDestination.getAttribute("id")).thenReturn("1");
		when(
				transitionNoDestination
						.hasAttribute(AutomataIOConstants.XML_ATTRIBUTE_TRANSITION_SOURCE))
				.thenReturn(true);
		when(
				transitionNoDestination
						.hasAttribute(AutomataIOConstants.XML_ATTRIBUTE_TRANSITION_DESTINATION))
				.thenReturn(false);

		when(transitionNotCorrectSourceState.hasAttribute("id")).thenReturn(
				true);
		when(transitionNotCorrectSourceState.getAttribute("id"))
				.thenReturn("1");
		when(
				transitionNotCorrectSourceState
						.hasAttribute(AutomataIOConstants.XML_ATTRIBUTE_TRANSITION_SOURCE))
				.thenReturn(true);
		when(
				transitionNotCorrectSourceState
						.hasAttribute(AutomataIOConstants.XML_ATTRIBUTE_TRANSITION_DESTINATION))
				.thenReturn(true);
		when(
				transitionNotCorrectSourceState
						.getAttribute(AutomataIOConstants.XML_ATTRIBUTE_TRANSITION_SOURCE))
				.thenReturn("2");

		when(transitionNotCorrectDestinationState.hasAttribute("id"))
				.thenReturn(true);
		when(transitionNotCorrectDestinationState.getAttribute("id"))
				.thenReturn("1");
		when(
				transitionNotCorrectDestinationState
						.hasAttribute(AutomataIOConstants.XML_ATTRIBUTE_TRANSITION_SOURCE))
				.thenReturn(true);
		when(
				transitionNotCorrectDestinationState
						.hasAttribute(AutomataIOConstants.XML_ATTRIBUTE_TRANSITION_DESTINATION))
				.thenReturn(true);
		when(
				transitionNotCorrectDestinationState
						.getAttribute(AutomataIOConstants.XML_ATTRIBUTE_TRANSITION_SOURCE))
				.thenReturn("1");
		when(
				transitionNotCorrectDestinationState
						.getAttribute(AutomataIOConstants.XML_ATTRIBUTE_TRANSITION_DESTINATION))
				.thenReturn("2");

		when(transitionNotCorrectId.hasAttribute("id")).thenReturn(true);
		when(transitionNotCorrectId.getAttribute("id")).thenReturn("wrongId");
		when(
				transitionNotCorrectId
						.hasAttribute(AutomataIOConstants.XML_ATTRIBUTE_TRANSITION_SOURCE))
				.thenReturn(true);
		when(
				transitionNotCorrectId
						.hasAttribute(AutomataIOConstants.XML_ATTRIBUTE_TRANSITION_DESTINATION))
				.thenReturn(true);
		when(
				transitionNotCorrectId
						.getAttribute(AutomataIOConstants.XML_ATTRIBUTE_TRANSITION_SOURCE))
				.thenReturn("1");
		when(
				transitionNotCorrectId
						.getAttribute(AutomataIOConstants.XML_ATTRIBUTE_TRANSITION_DESTINATION))
				.thenReturn("2");

		when(transition.hasAttribute("id")).thenReturn(true);
		when(transition.getAttribute("id")).thenReturn("1");
		when(
				transition
						.hasAttribute(AutomataIOConstants.XML_ATTRIBUTE_TRANSITION_SOURCE))
				.thenReturn(true);
		when(
				transition
						.hasAttribute(AutomataIOConstants.XML_ATTRIBUTE_TRANSITION_DESTINATION))
				.thenReturn(true);
		when(
				transition
						.getAttribute(AutomataIOConstants.XML_ATTRIBUTE_TRANSITION_SOURCE))
				.thenReturn("1");
		when(
				transition
						.getAttribute(AutomataIOConstants.XML_ATTRIBUTE_TRANSITION_DESTINATION))
				.thenReturn("2");
		when(
				transition
						.hasAttribute(AutomataIOConstants.XML_ATTRIBUTE_TRANSITION_PROPOSITIONS))
				.thenReturn(true);
		when(
				transition
						.getAttribute(AutomataIOConstants.XML_ATTRIBUTE_TRANSITION_PROPOSITIONS))
				.thenReturn("a^b");

		when(transitionNoPropositions.hasAttribute("id")).thenReturn(true);
		when(transitionNoPropositions.getAttribute("id")).thenReturn("1");
		when(
				transitionNoPropositions
						.hasAttribute(AutomataIOConstants.XML_ATTRIBUTE_TRANSITION_SOURCE))
				.thenReturn(true);
		when(
				transitionNoPropositions
						.hasAttribute(AutomataIOConstants.XML_ATTRIBUTE_TRANSITION_DESTINATION))
				.thenReturn(true);
		when(
				transitionNoPropositions
						.getAttribute(AutomataIOConstants.XML_ATTRIBUTE_TRANSITION_SOURCE))
				.thenReturn("1");
		when(
				transitionNoPropositions
						.getAttribute(AutomataIOConstants.XML_ATTRIBUTE_TRANSITION_DESTINATION))
				.thenReturn("2");
		when(
				transitionNoPropositions
						.hasAttribute(AutomataIOConstants.XML_ATTRIBUTE_TRANSITION_PROPOSITIONS))
				.thenReturn(false);
		when(
				transitionNoPropositions
						.getAttribute(AutomataIOConstants.XML_ATTRIBUTE_TRANSITION_PROPOSITIONS))
				.thenReturn("a^b");

	}
	
	/**
	 * Test method for
	 * {@link it.polimi.automata.io.in.propositions.IBAElementToTransitionTransformer#IBAElementToTransitionTransformer(IBA,Map)}
	 * .
	 */
	@Test(expected = NullPointerException.class)
	public void testIBAElementToTransitionTransformerNullMap() {
		new IBAElementToTransitionTransformer(new IBA(
				new ModelTransitionFactory()), null);
	}

	/**
	 * Test method for
	 * {@link it.polimi.automata.io.in.propositions.IBAElementToTransitionTransformer#IBAElementToTransitionTransformer(IBA,Map)}
	 * .
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testIBAElementToTransitionTransformerStateDifference() {
		IBA iba = new IBA(new ModelTransitionFactory());
		iba.addState(new StateFactory().create());
		new IBAElementToTransitionTransformer(iba, new HashMap<Integer, State>());
	}

	/**
	 * Test method for
	 * {@link it.polimi.automata.io.in.propositions.IBAElementToTransitionTransformer#IBAElementToTransitionTransformer(IBA,Map)}
	 * .
	 */
	@Test
	public void testIBAElementToTransitionTransformer() {
		assertNotNull(
				"The initialization procedure returns an object which is not null",
				new IBAElementToTransitionTransformer(new IBA(
						new ModelTransitionFactory()),
						new HashMap<Integer, State>()));
	}

	/**
	 * Test method for
	 * {@link it.polimi.automata.io.in.propositions.IBAElementToTransitionTransformer#transform(Element)}
	 * .
	 */
	@Test(expected = NullPointerException.class)
	public void testTransformNullElement() {
		BAElementToTransitionTransformer transformer = new BAElementToTransitionTransformer(
				new IBA(new ModelTransitionFactory()),
				new HashMap<Integer, State>());
		transformer.transform(null);
	}

	/**
	 * Test method for
	 * {@link it.polimi.automata.io.in.propositions.IBAElementToTransitionTransformer#transform(Element)}
	 * .
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testTransformNoIdTransition() {
		BAElementToTransitionTransformer transformer = new BAElementToTransitionTransformer(
				new IBA(new ModelTransitionFactory()),
				new HashMap<Integer, State>());
		transformer.transform(transitionNoId);
	}

	/**
	 * Test method for
	 * {@link it.polimi.automata.io.in.propositions.IBAElementToTransitionTransformer#transform(Element)}
	 * .
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testTransformNoStringIdTransition() {
		BAElementToTransitionTransformer transformer = new BAElementToTransitionTransformer(
				new IBA(new ModelTransitionFactory()),
				new HashMap<Integer, State>());
		transformer.transform(transitionNoId);
	}

	/**
	 * Test method for
	 * {@link it.polimi.automata.io.in.propositions.IBAElementToTransitionTransformer#transform(Element)}
	 * .
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testTransformNoSourceTransition() {
		IBAElementToTransitionTransformer transformer = new IBAElementToTransitionTransformer(
				new IBA(new ModelTransitionFactory()),
				new HashMap<Integer, State>());
		transformer.transform(transitionNoSource);
	}

	/**
	 * Test method for
	 * {@link it.polimi.automata.io.in.propositions.IBAElementToTransitionTransformer#transform(Element)}
	 * .
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testTransformNoDestinationTransition() {
		IBAElementToTransitionTransformer transformer = new IBAElementToTransitionTransformer(
				new IBA(new ModelTransitionFactory()),
				new HashMap<Integer, State>());
		transformer.transform(transitionNoDestination);
	}

	/**
	 * Test method for
	 * {@link it.polimi.automata.io.in.propositions.IBAElementToTransitionTransformer#transform(Element)}
	 * .
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testTransformNoCorrectSourceState() {
		IBAElementToTransitionTransformer transformer = new IBAElementToTransitionTransformer(
				new IBA(new ModelTransitionFactory()),
				new HashMap<Integer, State>());
		transformer.transform(this.transitionNotCorrectSourceState);
	}

	/**
	 * Test method for
	 * {@link it.polimi.automata.io.in.propositions.IBAElementToTransitionTransformer#transform(Element)}
	 * .
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testTransformNoCorrectDestinationState() {
		Map<Integer, State> map = new HashMap<Integer, State>();
		map.put(1, state1);
		IBA IBA = new IBA(new ModelTransitionFactory());
		IBA.addState(state1);
		IBAElementToTransitionTransformer transformer = new IBAElementToTransitionTransformer(
				IBA, map);
		transformer.transform(this.transitionNotCorrectDestinationState);
	}

	/**
	 * Test method for
	 * {@link it.polimi.automata.io.in.propositions.IBAElementToTransitionTransformer#transform(Element)}
	 * 
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testTransformNoCorrectTransitionId() {
		Map<Integer, State> map = new HashMap<Integer, State>();
		map.put(1, state1);
		map.put(2, state2);
		IBA iba = new IBA(new ModelTransitionFactory());
		iba.addState(state1);
		iba.addState(state2);
		IBAElementToTransitionTransformer transformer = new IBAElementToTransitionTransformer(
				iba, map);
		transformer.transform(this.transitionNotCorrectId);
	}

	/**
	 * Test method for
	 * {@link it.polimi.automata.io.in.propositions.BAElementToTransitionTransformer#transform(Element)}
	 * .
	 */
	@Test
	public void testTransform() {
		Map<Integer, State> map = new HashMap<Integer, State>();
		map.put(1, state1);
		map.put(2, state2);
		IBA iba = new IBA(new ModelTransitionFactory());
		iba.addProposition(new GraphProposition("a", false));
		iba.addProposition(new GraphProposition("b", false));
		iba.addState(state1);
		iba.addState(state2);
		IBAElementToTransitionTransformer transformer = new IBAElementToTransitionTransformer(
				iba, map);
		Set<IGraphProposition> propositions = new HashSet<IGraphProposition>();
		propositions.add(new GraphProposition("a", false));
		propositions.add(new GraphProposition("b", false));

		Transition t = transformer.transform(this.transition);
		assertTrue("The transition is added to the IBA", iba.getTransitions()
				.contains(t));
		assertTrue("The transition source is set correctly", iba
				.getTransitionSource(t).equals(state1));
		assertTrue("The transition destination is set correctly", iba
				.getTransitionDestination(t).equals(state2));
		assertEquals("The label of the transition is correct", propositions,
				t.getPropositions());
		assertEquals("The id of the transition is correct", 1, t.getId());

	}

	/**
	 * Test method for
	 * {@link it.polimi.automata.io.in.propositions.BAElementToTransitionTransformer#transform(Element)}
	 * .
	 */
	@Test
	public void testTransformTransitionNoPropositions() {
		Map<Integer, State> map = new HashMap<Integer, State>();
		map.put(1, state1);
		map.put(2, state2);
		IBA iba = new IBA(new ModelTransitionFactory());
		iba.addState(state1);
		iba.addState(state2);
		IBAElementToTransitionTransformer transformer = new IBAElementToTransitionTransformer(
				iba, map);
		Set<IGraphProposition> propositions = new HashSet<IGraphProposition>();

		Transition t = transformer.transform(this.transitionNoPropositions);
		assertTrue("The transition is added to the IBA", iba.getTransitions()
				.contains(t));
		assertTrue("The transition source is set correctly", iba
				.getTransitionSource(t).equals(state1));
		assertTrue("The transition destination is set correctly", iba
				.getTransitionDestination(t).equals(state2));
		assertEquals("The label of the transition is correct", propositions,
				t.getPropositions());
		assertEquals("The id of the transition is correct", 1, t.getId());

	}
}
