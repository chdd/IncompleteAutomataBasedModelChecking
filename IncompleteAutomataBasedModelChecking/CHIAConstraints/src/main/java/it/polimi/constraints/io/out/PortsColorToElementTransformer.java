package it.polimi.constraints.io.out;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.google.common.base.Preconditions;

import it.polimi.automata.BA;
import it.polimi.automata.Constants;
import it.polimi.automata.io.Transformer;
import it.polimi.automata.state.State;
import it.polimi.automata.transition.Transition;
import it.polimi.constraints.Constraint;
import it.polimi.constraints.Port;

public class PortsColorToElementTransformer<S extends State, T extends Transition, A extends BA<S, T>> implements Transformer<Port<S,T>,Element> {

	private final Document doc;
	private final Constraint<S, T, A> constraint;

	/**
	 * creates a new PortsGraph To Element Transformer element transformer
	 * 
	 * @param doc
	 *            is the document where the element must be placed
	 */
	public PortsColorToElementTransformer(Document doc, Constraint<S, T, A> constraint) {
		Preconditions.checkNotNull(doc, "The document element cannot be null");
		Preconditions.checkNotNull(constraint, "The contraint cannot be null");
		
		this.doc = doc;
		this.constraint=constraint;
	}
	
	@Override
	public Element transform(Port<S, T> input) {
		Element portElement = doc.createElement(Constants.XML_ELEMENT_PORT);

		Attr portId = doc.createAttribute(Constants.XML_ATTRIBUTE_ID);
		portId.setValue(Integer.toString(input.getId()));
		portElement.setAttributeNode(portId);

		// transition source
		Attr nextPortColor = doc
				.createAttribute(Constants.XML_ATTRIBUTE_COLOR);
		nextPortColor.setValue(this.constraint.getPortValue(input).toString());
		portElement.setAttributeNode(nextPortColor);
		return portElement;
	}

}
