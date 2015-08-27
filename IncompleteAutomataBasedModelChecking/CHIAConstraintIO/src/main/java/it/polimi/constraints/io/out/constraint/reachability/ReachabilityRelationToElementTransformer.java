package it.polimi.constraints.io.out.constraint.reachability;

import it.polimi.automata.AutomataIOConstants;
import it.polimi.automata.io.XMLTrasformer;
import it.polimi.constraints.io.ConstraintsIOConstants;
import it.polimi.constraints.reachability.ReachabilityEntry;
import it.polimi.constraints.reachability.ReachabilityRelation;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.google.common.base.Preconditions;

public class ReachabilityRelationToElementTransformer extends XMLTrasformer<ReachabilityRelation, Element>{

	public ReachabilityRelationToElementTransformer(Document doc){
		super(doc);
	}

	@Override
	public Element transform(ReachabilityRelation input) throws Exception {
		Preconditions.checkNotNull(input, "The reachability relation to be encoded cannot be null");
		Document doc=this.getDocument();
		Element reachabilityElement = doc
				.createElement(AutomataIOConstants.XML_ELEMENT_REACHABILITIES);
		
		for(ReachabilityEntry entry: input.getReachabilityAcceptingMap().values()){
			Element currentReachabilityElement = doc
					.createElement(AutomataIOConstants.XML_ELEMENT_REACHABILITY_ELEMENT);
			currentReachabilityElement.setAttribute(AutomataIOConstants.XML_ELEMENT_REACHABILITY_ELEMENT_SOURCE, Integer.toString(entry.getOutgoingTransition().getId()));
			currentReachabilityElement.setAttribute(AutomataIOConstants.XML_ELEMENT_REACHABILITY_ELEMENT_DESTINATION, Integer.toString(entry.getIncomingTransition().getId()));
			currentReachabilityElement.setAttribute(ConstraintsIOConstants.XML_ATTRIBUTE_ACCEPTING_CLAIM, Boolean.toString(entry.isClaimAccepting()));
			currentReachabilityElement.setAttribute(ConstraintsIOConstants.XML_ATTRIBUTE_ACCEPTING_MODEL, Boolean.toString(entry.isModelAccepting()));
			
			reachabilityElement.appendChild(currentReachabilityElement);
		}
		return reachabilityElement;
	}

}
