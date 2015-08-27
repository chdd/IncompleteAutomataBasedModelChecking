package it.polimi.constraints.io.out;

import it.polimi.automata.AutomataIOConstants;
import it.polimi.automata.io.Transformer;
import it.polimi.constraints.transitions.LabeledPluggingTransition;

import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.google.common.base.Preconditions;

/**
 * transforms the graph which specifies how the ports of the sub-properties are
 * connected into an XML element
 * 
 * @author claudiomenghi
 *
 */
public class PortsGraphToElementTransformer
		implements
		Transformer<DefaultDirectedGraph<LabeledPluggingTransition, DefaultEdge>, Element> {

	
	private final Document doc;

	/**
	 * creates a new PortsGraph To Element Transformer element transformer
	 * 
	 * @param doc
	 *            is the document where the element must be placed
	 */
	public PortsGraphToElementTransformer(Document doc) {
		Preconditions.checkNotNull(doc, "The document element cannot be null");
		this.doc = doc;
	}
	
	/**
	 * transforms the graph of the ports into an XML element
	 * 
	 * @return an XML element which encodes the graph which specifies the
	 *         connection between the ports
	 * @throws NullPointerException
	 *             if the input is null
	 */
	@Override
	public Element transform(DefaultDirectedGraph<LabeledPluggingTransition, DefaultEdge> input) {
		Preconditions.checkNotNull(input, "The graph to be converted cannot be null");
		

		Element portReachability = doc.createElement(AutomataIOConstants.XML_ELEMENT_PORTS_REACHABILITY);
	
		for (DefaultEdge port : input.edgeSet()) {
			
			Element edge = doc.createElement(AutomataIOConstants.XML_ELEMENT_TRANSITION);
			portReachability.appendChild(edge);
			
//			ColoredPluggingTransition sourceport=input.getEdgeSource(port);
//			edge.setAttribute(AutomataIOConstants.XML_ATTRIBUTE_PORT_SOURCE, Integer.toString(sourceport.getId()));
			
//			ColoredPluggingTransition destinationport=input.getEdgeTarget(port);
//			edge.setAttribute(AutomataIOConstants.XML_ATTRIBUTE_PORT_DESTINATION, Integer.toString(destinationport.getId()));
			
			portReachability.appendChild(edge);
		}
		return portReachability;
	}

}
