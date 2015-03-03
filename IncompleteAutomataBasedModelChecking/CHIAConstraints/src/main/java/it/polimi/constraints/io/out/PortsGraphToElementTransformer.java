package it.polimi.constraints.io.out;

import it.polimi.automata.Constants;
import it.polimi.automata.io.Transformer;
import it.polimi.automata.state.State;
import it.polimi.automata.transition.Transition;
import it.polimi.constraints.Port;

import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DirectedPseudograph;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.google.common.base.Preconditions;

/**
 * transforms the graph which specifies how the ports of the sub-properties are
 * connected into an XML element
 * 
 * @author claudiomenghi
 *
 * @param <S>
 *            is the type of the states which are included in the ports of the
 *            automaton
 * @param <T>
 *            is the type of the transitions constrained by the ports
 */
public class PortsGraphToElementTransformer<S extends State, T extends Transition>
		implements
		Transformer<DirectedPseudograph<Port<S, T>, DefaultEdge>, Element> {

	
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
	public Element transform(DirectedPseudograph<Port<S, T>, DefaultEdge> input) {
		Preconditions.checkNotNull(input, "The graph to be converted cannot be null");
		

		Element portReachability = doc.createElement(Constants.XML_ELEMENT_PORTS_OUT_REACHABILITY);
	
		for (DefaultEdge port : input.edgeSet()) {
			
			Element edge = doc.createElement(Constants.XML_ELEMENT_TRANSITION);
			portReachability.appendChild(edge);
			
			Port<S, T> sourceport=input.getEdgeSource(port);
			Element sourcePortElement = new PortToElementTransformer<S, T>(doc).transform(sourceport);
			edge.appendChild(sourcePortElement);
			
			Port<S, T> destinationport=input.getEdgeTarget(port);
			Element destinationPortElement = new PortToElementTransformer<S, T>(doc).transform(destinationport);
			edge.appendChild(destinationPortElement);
		}
		return portReachability;
	}

}
