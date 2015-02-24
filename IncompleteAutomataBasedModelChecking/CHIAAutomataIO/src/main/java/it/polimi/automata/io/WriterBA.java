package it.polimi.automata.io;

import it.polimi.automata.Constants;
import it.polimi.automata.IntersectionBA;
import it.polimi.automata.state.State;
import it.polimi.automata.transition.Transition;

import java.io.File;

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
 * write the intersection automaton into an XML file
 * 
 * @author claudiomenghi
 *
 * @param S
 *            is the type of the states of the intersection automaton
 * @param T
 *            is the type of the transitions of the intersection automaton
 */
public class WriterBA<S extends State, T extends Transition> {

	/**
	 * is the logger of the SubAutomataIdentifier class
	 */
	private static final Logger logger = LoggerFactory
			.getLogger(WriterBA.class);

	/**
	 * contains the intersection automaton to be written
	 */
	private IntersectionBA<S, T> intersectionAutomaton;
	/**
	 * contains the file where the intersection automaton must be written
	 */
	private File f;

	/**
	 * creates a new Writer for the intersection automaton
	 * 
	 * @param intersectionAutomaton
	 *            is the automaton to be written on the XML file
	 * @param f
	 *            is the file to which the intersection automaton must be
	 *            written
	 * @throws NullPointerException
	 *             if the intersection automaton or the file is null
	 * 
	 */
	public WriterBA(IntersectionBA<S, T> intersectionAutomaton, File f) {
		if (intersectionAutomaton == null) {
			logger.error("The intersection automaton cannot be null");
			throw new NullPointerException(
					"The intersection automaton cannot be null");
		}
		if (f == null) {
			logger.error("The file where the automaton must be written cannot be null");
			throw new NullPointerException(
					"The file where the automaton must be written cannot be null");
		}
		this.intersectionAutomaton = intersectionAutomaton;
		this.f = f;
	}

	public void write() {

		logger.info("Writing the intersection automaton");
		try {

			DocumentBuilderFactory docFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

			// root elements
			Document doc = docBuilder.newDocument();
			Element rootElement = doc.createElement(Constants.XML_ELEMENT_BA);
			doc.appendChild(rootElement);

			this.computingStateElements(doc, rootElement);
			this.computingTransitionElements(doc, rootElement);

			TransformerFactory transformerFactory = TransformerFactory
					.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(f);
			transformer.transform(source, result);

			logger.info("Intersection automaton written");

		} catch (ParserConfigurationException pce) {
			logger.error(pce.getMessage());
			pce.printStackTrace();
		} catch (TransformerException tfe) {
			logger.error(tfe.getMessage());
			tfe.printStackTrace();
		}
	}

	private void computingStateElements(Document doc, Element rootElement) {
		for (S s : this.intersectionAutomaton.getStates()) {
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

			if (this.intersectionAutomaton.getInitialStates().contains(s)) {
				// adding the id
				Attr initial = doc
						.createAttribute(Constants.XML_ATTRIBUTE_INITIAL);
				initial.setValue(Constants.TRUEVALUE);
				state.setAttributeNode(initial);
			}
			if (this.intersectionAutomaton.getAcceptStates().contains(s)) {
				// adding the id
				Attr accepting = doc
						.createAttribute(Constants.XML_ATTRIBUTE_ACCEPTING);
				accepting.setValue(Constants.TRUEVALUE);
				state.setAttributeNode(accepting);
			}
		}
	}

	private void computingTransitionElements(Document doc, Element rootElement) {
		for (T transition : this.intersectionAutomaton.getTransitions()) {
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
			sourceId.setValue(Integer.toString(this.intersectionAutomaton
					.getTransitionSource(transition).getId()));
			transitionElement.setAttributeNode(sourceId);

			// adding the destination
			Attr destinationId = doc
					.createAttribute(Constants.XML_ATTRIBUTE_TRANSITION_DESTINATION);
			destinationId.setValue(Integer.toString(this.intersectionAutomaton
					.getTransitionDestination(transition).getId()));
			transitionElement.setAttributeNode(destinationId);
			

			// adding the propositions
			Attr propositions = doc
					.createAttribute(Constants.XML_ATTRIBUTE_TRANSITION_PROPOSITIONS);
			
			String ret = "";
			for (IGraphProposition label : transition.getPropositions()) {
				if(label instanceof SigmaProposition){
					ret = ret + Constants.SIGMA;
				}
				else{
					ret = ret + "(" + label.toString() + ")" + Constants.AND_NOT_ESCAPED;
				}
			}
			if (ret.endsWith(Constants.AND_NOT_ESCAPED)) {
				ret = ret.substring(0, ret.length() - Constants.AND_NOT_ESCAPED.length());
			}
			
			propositions.setValue(ret);
			transitionElement.setAttributeNode(propositions);

		}
	}
}
