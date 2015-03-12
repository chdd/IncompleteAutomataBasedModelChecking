package it.polimi.constraints.io.in;

import it.polimi.automata.BA;
import it.polimi.automata.Constants;
import it.polimi.automata.io.transformer.propositions.ModelPropositionParser;
import it.polimi.automata.state.State;
import it.polimi.automata.state.StateFactory;
import it.polimi.automata.transition.Transition;
import it.polimi.automata.transition.TransitionFactory;
import it.polimi.constraints.Port;
import it.polimi.constraints.impl.PortImpl;

import org.w3c.dom.Element;

import com.google.common.base.Preconditions;

public class ElementToPortTransformer<S extends State, T extends Transition, A extends BA<S,T>> {

	private final TransitionFactory<S, T> transitionFactory;
	
	private final StateFactory<S> stateFactory;

	/**
	 * creates a new Transformer which converts an element to a corresponding
	 * port
	 * 
	 * @param transitionFactory
	 *            is the factory used to crate the transition of the port
	 * @throws NullPointerException
	 *             if the transition factory is null
	 */
	public ElementToPortTransformer(TransitionFactory<S, T> transitionFactory, StateFactory<S> stateFactory) {
		Preconditions.checkNotNull(transitionFactory,
				"The transition factory cannot be null");
		Preconditions.checkNotNull(stateFactory,
				"The state factory cannot be null");
		this.transitionFactory = transitionFactory;
		this.stateFactory= stateFactory;
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
	public Port<S, T> transform(Element e) {
		Preconditions.checkNotNull(e,
				"The element to be converted cannot be null");
		
		String labels = e
				.getAttribute(Constants.XML_ATTRIBUTE_TRANSITION_PROPOSITIONS);
		ModelPropositionParser<S, T> propositionsParser = new ModelPropositionParser<S, T>();

		int transitionId = Integer.parseInt(e
				.getAttribute(Constants.XML_ATTRIBUTE_ID));

		PortImpl.ID_COUNTER=Math.max(PortImpl.ID_COUNTER, transitionId+1);
		T transition = this.transitionFactory.create(transitionId,
				propositionsParser.computePropositions(labels));
		
		int sourceStateId = Integer.parseInt(e
				.getAttribute(Constants.XML_ATTRIBUTE_TRANSITION_SOURCE));

		S sourceState = this.stateFactory.create(Integer.toString(sourceStateId), sourceStateId);

		Boolean incoming= Boolean.parseBoolean(e.getAttribute(Constants.XML_ATTRIBUTE_INCOMING));
		
		int destinationStateId = Integer.parseInt(e
				.getAttribute(Constants.XML_ATTRIBUTE_TRANSITION_DESTINATION));

		S destinationState = this.stateFactory.create(Integer.toString(destinationStateId), destinationStateId);
		
		return new PortImpl<S, T>(transitionId, sourceState, destinationState, transition, incoming);
	}
}
