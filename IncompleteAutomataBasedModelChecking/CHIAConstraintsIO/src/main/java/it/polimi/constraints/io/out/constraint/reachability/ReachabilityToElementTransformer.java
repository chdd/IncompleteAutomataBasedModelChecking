package it.polimi.constraints.io.out.constraint.reachability;

import it.polimi.automata.AutomataIOConstants;
import it.polimi.automata.io.XMLTrasformer;
import it.polimi.constraints.reachability.ReachabilityRelation;
import it.polimi.constraints.transitions.ColoredPluggingTransition;

import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.tuple.Triple;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.google.common.base.Preconditions;

public class ReachabilityToElementTransformer extends XMLTrasformer<ReachabilityRelation, Element>{

	public ReachabilityToElementTransformer(Document doc){
		super(doc);
	}

	@Override
	public Element transform(ReachabilityRelation input) throws Exception {
		Preconditions.checkNotNull(input, "The reachability relation to be encoded cannot be null");
		Document doc=this.getDocument();
		Element reachabilityElement = doc
				.createElement(AutomataIOConstants.XML_ELEMENT_REACHABILITIES);
		
		for(Entry<Map.Entry<ColoredPluggingTransition, ColoredPluggingTransition>, Triple<Boolean,Boolean, Boolean>> entry: input.getReachabilityAcceptingMap().entrySet()){
			Element currentReachabilityElement = doc
					.createElement(AutomataIOConstants.XML_ELEMENT_REACHABILITY_ELEMENT);
			currentReachabilityElement.setAttribute(AutomataIOConstants.XML_ELEMENT_REACHABILITY_ELEMENT_SOURCE, Integer.toString(entry.getKey().getKey().getId()));
			currentReachabilityElement.setAttribute(AutomataIOConstants.XML_ELEMENT_REACHABILITY_ELEMENT_DESTINATION, Integer.toString(entry.getKey().getValue().getId()));
			currentReachabilityElement.setAttribute(AutomataIOConstants.XML_ATTRIBUTE_ACCEPTING, Boolean.toString(entry.getValue().getLeft()));
			currentReachabilityElement.setAttribute(AutomataIOConstants.XML_ATTRIBUTE_ACCEPTING_CLAIM, Boolean.toString(entry.getValue().getMiddle()));
			currentReachabilityElement.setAttribute(AutomataIOConstants.XML_ATTRIBUTE_ACCEPTING_MODEL, Boolean.toString(entry.getValue().getRight()));
			
			reachabilityElement.appendChild(currentReachabilityElement);
		}
		return reachabilityElement;
	}

}
