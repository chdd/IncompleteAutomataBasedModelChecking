package it.polimi.automata.io.in.transitions;

import it.polimi.automata.AutomataIOConstants;
import it.polimi.automata.BA;
import it.polimi.automata.io.Transformer;
import it.polimi.automata.io.in.propositions.StringToClaimPropositions;
import it.polimi.automata.state.State;
import it.polimi.automata.transition.ClaimTransitionFactory;
import it.polimi.automata.transition.Transition;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.w3c.dom.Element;

import rwth.i2.ltl2ba4j.model.IGraphProposition;

import com.google.common.base.Preconditions;

/**
 * Transforms a transition element into the corresponding Transition and adds he
 * 
 * @author Claudio Menghi
 *
 */
public class BAElementToTransitionTransformer implements
		Transformer<Element, Transition> {

	/**
	 * is the factory which is used to create the transitions of the Buchi
	 * automaton
	 */
	private final ClaimTransitionFactory transitionFactory;

	/**
	 * is the BA to be populated
	 */
	private final BA automaton;

	/**
	 * contains a map which associates the id of the state (Integer) to the
	 * corresponding state
	 */
	private final Map<Integer, State> idStateMap;

	/**
	 * creates a new {@link BAElementToTransitionTransformer}
	 * 
	 * @param automaton
	 *            is the automaton to be populated
	 * @param idStateMap
	 *            is a map which associates the id of the state (Integer) to the
	 *            corresponding state
	 * @throws NullPointerException
	 *             if the automaton or the map is empty
	 * @throws if
	 *             the set of the states in the map does not corresponds to the
	 *             states of the automaton
	 */
	public BAElementToTransitionTransformer(BA automaton,
			Map<Integer, State> idStateMap) {
		Preconditions.checkNotNull(automaton,
				"The BA to be populated cannot be null");
		Preconditions.checkNotNull(idStateMap,
				"The map between the id and the states cannot be null");
		Preconditions
				.checkArgument(

						automaton.getStates().equals(
								new HashSet<State>(idStateMap.values())),
						"All the state of the automaton must be contained into the map");
		this.transitionFactory = new ClaimTransitionFactory();
		this.automaton = automaton;
		this.idStateMap = idStateMap;

	}

	/**
	 * transforms an Element into the corresponding transition and add the
	 * transition to the BA
	 * 
	 * @param eElement
	 *            the element to be transformed
	 * @throws NullPointerException
	 *             if the element passed as parameter is null
	 * @throws IllegalArgumentException
	 *             if the transition is not associated with an id
	 */
	@Override
	public Transition transform(Element eElement) {
		Preconditions.checkNotNull(eElement,
				"The element to consider cannot be null");
		Preconditions.checkArgument(eElement
				.hasAttribute(AutomataIOConstants.XML_ATTRIBUTE_TRANSITION_ID),
				"The transition must contain an id attribute");
		Preconditions.checkArgument(eElement
				.hasAttribute(AutomataIOConstants.XML_ATTRIBUTE_TRANSITION_SOURCE),
				"The transition must have a source");
		Preconditions.checkArgument(eElement
				.hasAttribute(AutomataIOConstants.XML_ATTRIBUTE_TRANSITION_DESTINATION),
				"The transition must have a destination");
		
		int sourceId = Integer
				.parseInt(eElement
						.getAttribute(AutomataIOConstants.XML_ATTRIBUTE_TRANSITION_SOURCE));
		int destinationId = Integer
				.parseInt(eElement
						.getAttribute(AutomataIOConstants.XML_ATTRIBUTE_TRANSITION_DESTINATION));
		Preconditions
				.checkArgument(
						idStateMap.containsKey(sourceId),
						"The id: "
								+ sourceId
								+ " is not contained into the set of the id of the states");
		Preconditions
				.checkArgument(
						idStateMap.containsKey(destinationId),
						"The id: "
								+ destinationId
								+ " is not contained into the set of the id of the states");
		
		int id = 0;
		try {
			id = Integer
					.parseInt(eElement
							.getAttribute(AutomataIOConstants.XML_ATTRIBUTE_TRANSITION_ID));
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException(eElement
					.getAttribute(AutomataIOConstants.XML_ATTRIBUTE_TRANSITION_ID)+" is not a valid id for a transition. The id must be an integer.");
		}

		Set<IGraphProposition> propositions=new HashSet<IGraphProposition>();
		if(eElement.hasAttribute(AutomataIOConstants.XML_ATTRIBUTE_TRANSITION_PROPOSITIONS)){
			propositions = new StringToClaimPropositions()
			.transform(eElement
					.getAttribute(AutomataIOConstants.XML_ATTRIBUTE_TRANSITION_PROPOSITIONS));

		}
		
		Transition t = transitionFactory.create(id, propositions);

		
		automaton.addTransition(idStateMap.get(sourceId),
				idStateMap.get(destinationId), t);
		return t;
	}
}
