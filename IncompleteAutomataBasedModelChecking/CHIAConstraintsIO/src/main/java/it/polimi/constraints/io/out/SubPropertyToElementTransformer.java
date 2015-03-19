package it.polimi.constraints.io.out;

import it.polimi.automata.BA;
import it.polimi.automata.Constants;
import it.polimi.automata.io.Transformer;
import it.polimi.automata.io.out.StateToElementTransformer;
import it.polimi.automata.io.out.TransitionToElementTransformer;
import it.polimi.automata.state.State;
import it.polimi.automata.transition.Transition;
import it.polimi.constraints.impl.Port;
import it.polimi.constraints.impl.SubProperty;

import java.util.Set;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.google.common.base.Preconditions;

/**
 * transforms the component to an XML element.
 * 
 * @author claudiomenghi
 *
 */
public class SubPropertyToElementTransformer
		implements Transformer<SubProperty, Element> {

	private final Document doc;
	private final Constraint<S, T, A> constraint;

	/**
	 * creates a new PortsGraph To Element Transformer element transformer
	 * 
	 * @param doc
	 *            is the document where the element must be placed
	 */
	public SubPropertyToElementTransformer(Document doc, Constraint<S, T, A> constraint) {
		Preconditions.checkNotNull(doc, "The document element cannot be null");
		Preconditions.checkNotNull(constraint, "The contraint cannot be null");
		
		this.doc = doc;
		this.constraint=constraint;
	}
	
	@Override
	public Element transform(SubProperty input) {
		
		// root elements
		Element constraintElement = doc
				.createElement(Constants.XML_ELEMENT_CONSTRAINT);
	
		// adding the id
		Attr modelTransparentStateIDd = doc
				.createAttribute(Constants.XML_ATTRIBUTE_MODEL_STATE_ID);
		modelTransparentStateIDd.setValue(Integer.toString(input
				.getModelState().getId()));
		constraintElement.setAttributeNode(modelTransparentStateIDd);

		// adding the name of the state
		Attr modelTransparentStateName = doc
				.createAttribute(Constants.XML_ATTRIBUTE_NAME);
		modelTransparentStateName.setValue(input.getName());
		constraintElement.setAttributeNode(modelTransparentStateName);
		
		Element baElement = doc.createElement(Constants.XML_ELEMENT_BA);
		constraintElement.appendChild(baElement);

		// computing the states
		this.computingStateElements(doc, baElement, input);

		// computing the transitions
		this.computingTransitionElements(doc, baElement, input);

		// adding the outComing Ports
		Element outComingPorts = doc
				.createElement(Constants.XML_ELEMENT_PORTS_OUT);
		constraintElement.appendChild(outComingPorts);
		this.addPorts(doc, outComingPorts,
				this.constraint.getOutcomingPorts(input));

		// adding the incoming Ports
		Element inComingPorts = doc
				.createElement(Constants.XML_ELEMENT_PORTS_IN);
		constraintElement.appendChild(inComingPorts);
		this.addPorts(doc, inComingPorts, 
				this.constraint.getIncomingPorts(input));

		return constraintElement;
	}
	
	private void computingStateElements(Document doc, Element rootElement,
			SubProperty intersectionAutomaton) {

		StateToElementTransformer stateTransformer = new StateToElementTransformer(
				intersectionAutomaton.getAutomaton(), doc);
		for (S s : intersectionAutomaton.getAutomaton().getStates()) {
			Element xmlStateElement = stateTransformer.transform(s);
			rootElement.appendChild(xmlStateElement);
		}
	}

	private void addPorts(Document doc, Element portsElement,
			Set<Port> ports) {

		PortToElementTransformer transformer = new PortToElementTransformer(
				doc);
		for (Port port : ports) {
			Element portElement = transformer.transform(port);
			portsElement.appendChild(portElement);
		}
	}
	
	private void computingTransitionElements(Document doc, Element rootElement,
			SubProperty intersectionAutomaton) {
		TransitionToElementTransformer transitionTransformer = new TransitionToElementTransformer(
				intersectionAutomaton.getAutomaton(), doc);
		for (Transition transition : intersectionAutomaton.getAutomaton()
				.getTransitions()) {
			Element transitionElement = transitionTransformer
					.transform(transition);
			rootElement.appendChild(transitionElement);
		}
	}
}