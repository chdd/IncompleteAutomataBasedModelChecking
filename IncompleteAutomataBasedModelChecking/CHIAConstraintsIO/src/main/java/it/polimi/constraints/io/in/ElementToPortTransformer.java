package it.polimi.constraints.io.in;

import it.polimi.automata.Constants;
import it.polimi.automata.io.transformer.propositions.ModelPropositionParser;
import it.polimi.automata.state.State;
import it.polimi.automata.state.StateFactory;
import it.polimi.automata.transition.ModelTransitionFactory;
import it.polimi.automata.transition.Transition;
import it.polimi.constraints.impl.Port;

import org.w3c.dom.Element;

import com.google.common.base.Preconditions;


/**
 * trans
 * @author claudiomenghi
 *
 */
public class ElementToPortTransformer{


	/**
	 * creates a new Transformer which converts an element to a corresponding
	 * port
	 * 
	 * @param transitionFactory
	 *            is the factory used to crate the transition of the port
	 * @throws NullPointerException
	 *             if the transition factory is null
	 */
	public ElementToPortTransformer() {
		
	}

	/**
	 * transforms the XML element e into the corresponding port
	 * 
	 * @param e
	 *            is the element to be converted
	 * @return the corresponding port
	 * @throws NullPointerException
	 *             if the one of the parameters is null
	 */
	public Port transform(Element e) {
		Preconditions.checkNotNull(e,
				"The element to be converted cannot be null");
		
		String labels = e
				.getAttribute(Constants.XML_ATTRIBUTE_TRANSITION_PROPOSITIONS);
		ModelPropositionParser propositionsParser = new ModelPropositionParser();

		int transitionId = Integer.parseInt(e
				.getAttribute(Constants.XML_ATTRIBUTE_ID));

		Port.ID_COUNTER=Math.max(Port.ID_COUNTER, transitionId+1);
		Transition transition = new ModelTransitionFactory().create(transitionId,
				propositionsParser.computePropositions(labels));
		
		int sourceStateId = Integer.parseInt(e
				.getAttribute(Constants.XML_ATTRIBUTE_TRANSITION_SOURCE));

		State sourceState = new StateFactory().create(Integer.toString(sourceStateId), sourceStateId);

		Boolean incoming= Boolean.parseBoolean(e.getAttribute(Constants.XML_ATTRIBUTE_INCOMING));
		
		int destinationStateId = Integer.parseInt(e
				.getAttribute(Constants.XML_ATTRIBUTE_TRANSITION_DESTINATION));

		State destinationState = new StateFactory().create(Integer.toString(destinationStateId), destinationStateId);
		
		return new Port(sourceState, destinationState, transition, incoming);
	}
}
