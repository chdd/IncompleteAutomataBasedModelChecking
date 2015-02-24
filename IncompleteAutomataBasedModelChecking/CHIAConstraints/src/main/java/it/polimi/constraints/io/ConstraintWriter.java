package it.polimi.constraints.io;

import it.polimi.automata.Constants;
import it.polimi.automata.IntersectionBA;
import it.polimi.automata.io.WriterBA;
import it.polimi.automata.state.State;
import it.polimi.automata.transition.Transition;
import it.polimi.constraints.Component;
import it.polimi.constraints.Constraint;
import it.polimi.constraints.Port;

import java.io.File;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import rwth.i2.ltl2ba4j.model.IGraphProposition;
import rwth.i2.ltl2ba4j.model.impl.SigmaProposition;

/**
 * 
 * @author claudiomenghi
 *
 * @param <S>
 *            is the type of the state to be written
 * @param <T>
 *            is the type of the transitions to be written
 */
public class ConstraintWriter<S extends State, T extends Transition> {

	/**
	 * is the logger of the SubAutomataIdentifier class
	 */
	private static final Logger logger = LoggerFactory
			.getLogger(WriterBA.class);

	/**
	 * contains the components to be written
	 */
	private Constraint<S, T> constraint;
	/**
	 * contains the file where the intersection automaton must be written
	 */
	private File f;

	/**
	 * creates a new RefinementWriter for the set of components
	 * 
	 * @param components
	 *            is the set of components to be written
	 * @param f
	 *            is the file to which the intersection automaton must be
	 *            written
	 * @throws NullPointerException
	 *             if the components or the file is null
	 * 
	 */
	public ConstraintWriter(Constraint<S, T> constraint, File f) {
		if (constraint == null) {
			logger.error("The intersection automaton cannot be null");
			throw new NullPointerException(
					"The intersection automaton cannot be null");
		}
		if (f == null) {
			logger.error("The file where the automaton must be written cannot be null");
			throw new NullPointerException(
					"The file where the automaton must be written cannot be null");
		}
		this.constraint = constraint;
		this.f = f;
	}

	public void write() {

		logger.info("Writing the intersection automaton");
		try {

			DocumentBuilderFactory docFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

			Document doc = docBuilder.newDocument();
			Element rootElement = doc
					.createElement(Constants.XML_ELEMENT_CONSTRAINTS);
			doc.appendChild(rootElement);

			for (Component<S, T> component : constraint.getComponents()) {

				// root elements
				Element constraintElement = doc
						.createElement(Constants.XML_ELEMENT_CONSTRAINT);
				rootElement.appendChild(constraintElement);

				// adding the id
				Attr modelTransparentStateIDd = doc
						.createAttribute(Constants.XML_ATTRIBUTE_MODEL_STATE_ID);
				modelTransparentStateIDd.setValue(Integer.toString(component
						.getModelState().getId()));
				constraintElement.setAttributeNode(modelTransparentStateIDd);

				Element baElement = doc.createElement(Constants.XML_ELEMENT_BA);
				constraintElement.appendChild(baElement);

				// computing the states
				this.computingStateElements(doc, baElement, component);

				// computing the transitions
				this.computingTransitionElements(doc, baElement, component);

				// adding the outComing Ports
				Element outComingPorts = doc
						.createElement(Constants.XML_ELEMENT_PORTS_OUT);
				constraintElement.appendChild(outComingPorts);
				this.addPorts(doc, outComingPorts,
						component.getOutcomingPorts());

				// adding the incoming Ports
				Element inComingPorts = doc
						.createElement(Constants.XML_ELEMENT_PORTS_IN);
				constraintElement.appendChild(inComingPorts);
				this.addPorts(doc, inComingPorts, component.getIncomingPorts());

				TransformerFactory transformerFactory = TransformerFactory
						.newInstance();
				Transformer transformer = transformerFactory.newTransformer();
				DOMSource source = new DOMSource(doc);
				StreamResult result = new StreamResult(f);
				transformer.transform(source, result);
			}

			logger.info("Intersection automaton written");

		} catch (ParserConfigurationException pce) {
			logger.error(pce.getMessage());
			pce.printStackTrace();
		} catch (TransformerException tfe) {
			logger.error(tfe.getMessage());
			tfe.printStackTrace();
		}
	}

	private void computingStateElements(Document doc, Element rootElement,
			IntersectionBA<S, T> intersectionAutomaton) {
		for (S s : intersectionAutomaton.getStates()) {
			Element state = doc.createElement(Constants.XML_ELEMENT_STATE);
			rootElement.appendChild(state);

			// adding the id
			Attr id = doc.createAttribute(Constants.XML_ATTRIBUTE_ID);
			id.setValue(Integer.toString(s.getId()));
			state.setAttributeNode(id);

			// adding the name
			Attr name = doc.createAttribute(Constants.XML_ATTRIBUTE_NAME);
			name.setValue(s.getName());
			state.setAttributeNode(name);

			if (intersectionAutomaton.getInitialStates().contains(s)) {
				// adding the id
				Attr initial = doc
						.createAttribute(Constants.XML_ATTRIBUTE_INITIAL);
				initial.setValue(Constants.TRUEVALUE);
				state.setAttributeNode(initial);
			}
			if (intersectionAutomaton.getAcceptStates().contains(s)) {
				// adding the id
				Attr accepting = doc
						.createAttribute(Constants.XML_ATTRIBUTE_ACCEPTING);
				accepting.setValue(Constants.TRUEVALUE);
				state.setAttributeNode(accepting);
			}
		}
	}

	private void addPorts(Document doc, Element portsElement,
			Set<Port<S, T>> ports) {

		for (Port<S, T> port : ports) {
			Element state = doc.createElement(Constants.XML_ELEMENT_PORT);
			portsElement.appendChild(state);

			// transition source
			Attr transitionSource = doc
					.createAttribute(Constants.XML_ATTRIBUTE_TRANSITION_SOURCE);
			transitionSource.setValue(Integer
					.toString(port.getSource().getId()));
			state.setAttributeNode(transitionSource);

			// transition destination
			Attr transitionDestination = doc
					.createAttribute(Constants.XML_ATTRIBUTE_TRANSITION_DESTINATION);
			transitionDestination.setValue(Integer.toString(port
					.getDestination().getId()));
			state.setAttributeNode(transitionDestination);

			// transition id
			Attr transitionId = doc
					.createAttribute(Constants.XML_ATTRIBUTE_TRANSITION_ID);
			transitionId.setValue(Integer
					.toString(port.getTransition().getId()));
			state.setAttributeNode(transitionId);

			// transition label
			Attr transitionPropositions = doc
					.createAttribute(Constants.XML_ATTRIBUTE_TRANSITION_PROPOSITIONS);
			transitionPropositions.setValue(this.propositionsToString(port
					.getTransition().getPropositions()));
			state.setAttributeNode(transitionPropositions);

		}
	}

	private void computingTransitionElements(Document doc, Element rootElement,
			IntersectionBA<S, T> intersectionAutomaton) {
		for (T transition : intersectionAutomaton.getTransitions()) {
			Element transitionElement = doc
					.createElement(Constants.XML_ELEMENT_TRANSITION);
			rootElement.appendChild(transitionElement);

			// adding the id
			Attr id = doc.createAttribute(Constants.XML_ATTRIBUTE_ID);
			id.setValue(Integer.toString(transition.getId()));
			transitionElement.setAttributeNode(id);

			// adding the source
			Attr sourceId = doc
					.createAttribute(Constants.XML_ATTRIBUTE_TRANSITION_SOURCE);
			sourceId.setValue(Integer.toString(intersectionAutomaton
					.getTransitionSource(transition).getId()));
			transitionElement.setAttributeNode(sourceId);

			// adding the destination
			Attr destinationId = doc
					.createAttribute(Constants.XML_ATTRIBUTE_TRANSITION_DESTINATION);
			destinationId.setValue(Integer.toString(intersectionAutomaton
					.getTransitionDestination(transition).getId()));
			transitionElement.setAttributeNode(destinationId);

			// adding the propositions
			Attr propositions = doc
					.createAttribute(Constants.XML_ATTRIBUTE_TRANSITION_PROPOSITIONS);

			propositions.setValue(this.propositionsToString(transition
					.getPropositions()));
			transitionElement.setAttributeNode(propositions);
		}
	}

	private String propositionsToString(Set<IGraphProposition> propositions) {

		String ret = "";
		for (IGraphProposition label : propositions) {
			if (label instanceof SigmaProposition) {
				ret = ret + Constants.SIGMA;
			} else {
				ret = ret + "(" + label.toString() + ")"
						+ Constants.AND_NOT_ESCAPED;
			}
		}
		if (ret.endsWith(Constants.AND_NOT_ESCAPED)) {
			ret = ret.substring(0,
					ret.length() - Constants.AND_NOT_ESCAPED.length());
		}
		return ret;
	}

}
