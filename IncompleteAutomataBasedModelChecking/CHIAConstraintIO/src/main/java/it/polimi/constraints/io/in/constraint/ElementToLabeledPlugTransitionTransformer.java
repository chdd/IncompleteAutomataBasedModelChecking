package it.polimi.constraints.io.in.constraint;

import it.polimi.automata.AutomataIOConstants;
import it.polimi.automata.io.in.propositions.StringToModelPropositions;
import it.polimi.automata.state.State;
import it.polimi.automata.state.StateFactory;
import it.polimi.automata.transition.ModelTransitionFactory;
import it.polimi.automata.transition.Transition;
import it.polimi.constraints.io.ConstraintsIOConstants;
import it.polimi.constraints.transitions.Label;
import it.polimi.constraints.transitions.LabeledPluggingTransition;

import org.w3c.dom.Element;

import com.google.common.base.Preconditions;

/**
 * transforms an XML element which represents a port into the corresponding port
 * Java element.
 * 
 * @author claudiomenghi
 *
 */
public class ElementToLabeledPlugTransitionTransformer {

	private final boolean incoming;
	/**
	 * creates a new Transformer which converts an XML element into the corresponding
	 * port
	 */
	public ElementToLabeledPlugTransitionTransformer( boolean incoming) {
		this.incoming=incoming;
	}

	/**
	 * transforms the XML element e into the corresponding port
	 * 
	 * @param e
	 *            is the element to be converted
	 * @return the corresponding port
	 * @throws NullPointerException
	 *             if one of the parameters is null
	 */
	public LabeledPluggingTransition transform(Element e) {
		Preconditions.checkNotNull(e,
				"The element to be converted cannot be null");

		
		int portId = Integer.parseInt(e
				.getAttribute(AutomataIOConstants.XML_ATTRIBUTE_ID));
		
		
		LabeledPluggingTransition.ID_COUNTER = Math.max(LabeledPluggingTransition.ID_COUNTER, portId + 1);
		
		
		Element sourceStateElement=(Element) e.getElementsByTagName(ConstraintsIOConstants.XML_ELEMENT_PORT_SOURCE_STATE).item(0);
		State sourceState=this.loadState(sourceStateElement);
		
		Element destinationStateElement=(Element) e.getElementsByTagName(ConstraintsIOConstants.XML_ELEMENT_PORT_DESTINATION_STATE).item(0);
		State destinationState=this.loadState(destinationStateElement);
		
		Element transitionElement=(Element) e.getElementsByTagName(ConstraintsIOConstants.XML_ELEMENT_PORT_TRANSITION).item(0);
		Transition transition=this.loadTransition(transitionElement);
		
		try{
			Label label = Label.valueOf(e
				.getAttribute(ConstraintsIOConstants.XML_ATTRIBUTE_LABEL));
			return new LabeledPluggingTransition(portId, sourceState, destinationState, transition, incoming,
					label);
		}
		catch(IllegalArgumentException exception){
			throw new IllegalArgumentException("The value "+ConstraintsIOConstants.XML_ATTRIBUTE_LABEL+" is not a valid label for the incoming/outgoing transition "+e.toString(), exception);
		}
		
		
		
	}
	
	private State loadState(Element sourceStateElement){
		Preconditions.checkNotNull(sourceStateElement, "The state element cannot be null");
		
		Element stateElement=(Element) sourceStateElement.getElementsByTagName(AutomataIOConstants.XML_ELEMENT_STATE).item(0);
		int sourceStateId = Integer.parseInt(stateElement
				.getAttribute(AutomataIOConstants.XML_ATTRIBUTE_ID));
		String stateName=stateElement
				.getAttribute(AutomataIOConstants.XML_ATTRIBUTE_NAME);
		
		State state = new StateFactory().create(stateName, sourceStateId);
		
		return state;
	}
	
	private Transition loadTransition(Element transitionElement){
		int transitionId=Integer.parseInt(transitionElement.getAttribute(ConstraintsIOConstants.XML_ATTRIBUTE_TRANSITION_ID));
		String propositions=transitionElement.getAttribute(ConstraintsIOConstants.XML_ATTRIBUTE_PROPOSITIONS);
		StringToModelPropositions propositionsParser = new StringToModelPropositions();

		Transition transition = new ModelTransitionFactory().create(
				transitionId, propositionsParser.transform(propositions));
		return transition;

	}

}
