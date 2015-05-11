package it.polimi.constraints.io.in;

import it.polimi.automata.AutomataIOConstants;
import it.polimi.automata.io.Transformer;
import it.polimi.constraints.ColoredPort;

import java.util.Map;

import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;
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
public class ElementToPortGraphTransformer implements
		Transformer<Element, DefaultDirectedGraph<ColoredPort, DefaultEdge>> {

	private final Map<Integer, ColoredPort> mapIdPort;
	private final DefaultDirectedGraph<ColoredPort, DefaultEdge> graph;

	public ElementToPortGraphTransformer(Map<Integer, ColoredPort> mapIdPort,
			DefaultDirectedGraph<ColoredPort, DefaultEdge> graph) {
		Preconditions.checkNotNull(mapIdPort, "The map id port cannot be null");
		this.mapIdPort = mapIdPort;
		this.graph = graph;
	}

	@Override
	public DefaultDirectedGraph<ColoredPort, DefaultEdge> transform(Element input) {

		Preconditions.checkNotNull(input,
				"The port reachability element cannot be null");

		NodeList outPortRelation = input
				.getElementsByTagName(AutomataIOConstants.XML_ELEMENT_TRANSITION);

		for (int stateid = 0; stateid < outPortRelation.getLength(); stateid++) {
			Node xmlport = outPortRelation.item(stateid);

			Element portElement = (Element) xmlport;
			int sourcePortId = Integer.parseInt(portElement
					.getAttribute(AutomataIOConstants.XML_ATTRIBUTE_PORT_SOURCE));
			int destinationPortId = Integer.parseInt(portElement
					.getAttribute(AutomataIOConstants.XML_ATTRIBUTE_PORT_DESTINATION));
			if (!graph.containsVertex(this.mapIdPort.get(sourcePortId))) {
				graph.addVertex(this.mapIdPort.get(sourcePortId));
			}
			if (!graph.containsVertex(this.mapIdPort.get(destinationPortId))) {
				graph.addVertex(this.mapIdPort.get(destinationPortId));
			}

			graph.addEdge(this.mapIdPort.get(sourcePortId),
					this.mapIdPort.get(destinationPortId));

		}

		return graph;
	}

}
