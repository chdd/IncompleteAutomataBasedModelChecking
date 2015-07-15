package it.polimi.constraints.io.in.constraint;

import it.polimi.automata.AutomataIOConstants;
import it.polimi.automata.io.Transformer;
import it.polimi.constraints.reachability.ReachabilityRelation;
import it.polimi.constraints.transitions.ColoredPluggingTransition;

import java.util.Map;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.google.common.base.Preconditions;

/**
 * loads the reachability graph between the ports of the sub-properties from the
 * specified XML element.
 * 
 * @author claudiomenghi
 *
 */
public class ElementToReachabilityRelationTransformer implements
		Transformer<Element, ReachabilityRelation> {

	private final Map<Integer, ColoredPluggingTransition> mapIdPort;

	public ElementToReachabilityRelationTransformer(
			Map<Integer, ColoredPluggingTransition> mapIdPort) {
		Preconditions.checkNotNull(mapIdPort, "The map id port cannot be null");
		this.mapIdPort = mapIdPort;
	}

	@Override
	public ReachabilityRelation transform(Element input) {

		Preconditions.checkNotNull(input,
				"The port reachability element cannot be null");

		ReachabilityRelation reachabilityRelation = new ReachabilityRelation();

		NodeList outPortRelation = input
				.getElementsByTagName(AutomataIOConstants.XML_ELEMENT_REACHABILITY_ELEMENT);

		for (int stateid = 0; stateid < outPortRelation.getLength(); stateid++) {
			Node xmlport = outPortRelation.item(stateid);

			Element portElement = (Element) xmlport;

			int sourcePortId = Integer
					.parseInt(portElement
							.getAttribute(AutomataIOConstants.XML_ATTRIBUTE_PORT_SOURCE));
			int destinationPortId = Integer
					.parseInt(portElement
							.getAttribute(AutomataIOConstants.XML_ATTRIBUTE_PORT_DESTINATION));
			Boolean accepting=Boolean.parseBoolean(portElement
							.getAttribute(AutomataIOConstants.XML_ATTRIBUTE_ACCEPTING));
			Boolean claimaccepting=Boolean.parseBoolean(portElement
					.getAttribute(AutomataIOConstants.XML_ATTRIBUTE_ACCEPTING_CLAIM));
	
			Boolean modelaccepting=Boolean.parseBoolean(portElement
					.getAttribute(AutomataIOConstants.XML_ATTRIBUTE_ACCEPTING_MODEL));
	
			if (!this.mapIdPort.containsKey(sourcePortId)) {
				throw new IllegalArgumentException(
						"The transition with id "
								+ sourcePortId
								+ " is not contained into the set of incoming/outgoing transitions of the automaotn");
			}
			if (!this.mapIdPort.containsKey(destinationPortId)) {
				throw new IllegalArgumentException(
						"The transition with id "
								+ destinationPortId
								+ " is not contained into the set of incoming/outgoing transitions of the automaotn");
			}
			reachabilityRelation.addTransition(
					this.mapIdPort.get(sourcePortId),
					this.mapIdPort.get(destinationPortId),
					accepting, modelaccepting, claimaccepting);
		}

		return reachabilityRelation;

	}
}
