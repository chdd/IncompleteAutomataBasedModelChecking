package it.polimi.constraints.io.out.constraint.reachability;

import it.polimi.automata.AutomataIOConstants;
import it.polimi.automata.io.XMLTrasformer;
import it.polimi.constraints.reachability.ReachabilityRelation;
import it.polimi.constraints.transitions.ColoredPluggingTransition;

import java.util.Map.Entry;

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
		
		for(Entry<ColoredPluggingTransition, ColoredPluggingTransition> entry: input.getMap().entries()){
			Element currentReachabilityElement = doc
					.createElement(AutomataIOConstants.XML_ELEMENT_REACHABILITY_ELEMENT);
			currentReachabilityElement.setAttribute(AutomataIOConstants.XML_ELEMENT_REACHABILITY_ELEMENT_SOURCE, Integer.toString(entry.getKey().getId()));
			currentReachabilityElement.setAttribute(AutomataIOConstants.XML_ELEMENT_REACHABILITY_ELEMENT_DESTINATION, Integer.toString(entry.getValue().getId()));
			reachabilityElement.appendChild(currentReachabilityElement);
		}
		return reachabilityElement;
	}

}
