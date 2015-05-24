package it.polimi.automata.io.in.transitions;

import it.polimi.automata.AutomataIOConstants;
import it.polimi.automata.IBA;
import it.polimi.automata.io.Transformer;
import it.polimi.automata.io.in.propositions.StringToModelPropositions;
import it.polimi.automata.state.State;
import it.polimi.automata.transition.ClaimTransitionFactory;
import it.polimi.automata.transition.Transition;

import java.util.Map;
import java.util.Set;

import org.w3c.dom.Element;

import rwth.i2.ltl2ba4j.model.IGraphProposition;

import com.google.common.base.Preconditions;

public class IBAElementToTransitionTransformer  implements Transformer<Element, Transition>{

	
	/**
	 * is the factory which is used to create the transitions of the Buchi
	 * automaotn
	 */
	private final ClaimTransitionFactory transitionFactory;
	
	private final IBA iba;
	private final Map<Integer, State> idStateMap;
	
	public IBAElementToTransitionTransformer(ClaimTransitionFactory transitionFactory, IBA iba,
			Map<Integer, State> idStateMap){
		Preconditions.checkNotNull(transitionFactory,"The transition factory cannot be null");
		this.transitionFactory=transitionFactory;
		this.iba=iba;
		this.idStateMap=idStateMap;
	}
	
	@Override
	public Transition transform(Element eElement) {
		
		int id = Integer.parseInt(eElement
				.getAttribute(AutomataIOConstants.XML_ATTRIBUTE_TRANSITION_ID));

		Set<IGraphProposition> propositions = new StringToModelPropositions()
				.transform(eElement
						.getAttribute(AutomataIOConstants.XML_ATTRIBUTE_TRANSITION_PROPOSITIONS));

		if(!iba.getPropositions().containsAll(propositions)){
			iba.addPropositions(propositions);
		}
		Transition t = transitionFactory.create(id, propositions);

		int sourceId = Integer.parseInt(eElement
				.getAttribute(AutomataIOConstants.XML_ATTRIBUTE_TRANSITION_SOURCE));
		int destinationId = Integer
				.parseInt(eElement
						.getAttribute(AutomataIOConstants.XML_ATTRIBUTE_TRANSITION_DESTINATION));
		iba.addTransition(idStateMap.get(sourceId),
				idStateMap.get(destinationId), t);
		
		return t;
	}
}
