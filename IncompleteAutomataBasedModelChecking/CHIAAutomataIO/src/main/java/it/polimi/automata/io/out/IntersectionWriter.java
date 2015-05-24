package it.polimi.automata.io.out;

import it.polimi.automata.AutomataIOConstants;
import it.polimi.automata.IntersectionBA;
import it.polimi.automata.io.out.states.IntBAStateToElementTransformer;
import it.polimi.automata.io.out.transitions.IntBATransitionToElementTransformer;
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
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import action.CHIAAction;

import com.google.common.base.Preconditions;

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
public class IntersectionWriter extends CHIAAction{

	private static final String NAME="WRITE INTERSECTION";
	/**
	 * is the logger of the SubAutomataIdentifier class
	 */
	private static final Logger logger = LoggerFactory
			.getLogger(IntersectionWriter.class);

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
	public IntersectionWriter(File f) {
		super(NAME);
		Preconditions.checkNotNull(f,
				"The file where the automaton must be written cannot be null");

		this.f = f;
	}

	public void write(IntersectionBA intersectionAutomaton) {

		logger.info("Writing the intersection automaton");
		try {

			DocumentBuilderFactory docFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

			// root elements
			Document doc = docBuilder.newDocument();
			Element intersectionAutomatonElement = doc.createElement(AutomataIOConstants.XML_ELEMENT_INTERSECTION);
			doc.appendChild(intersectionAutomatonElement);

			this.computingStateElements(doc, intersectionAutomatonElement, intersectionAutomaton);
			this.computingTransitionElements(doc, intersectionAutomatonElement, intersectionAutomaton);

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

	private void computingStateElements(Document doc, Element intersectionAutomatonElement, IntersectionBA intersectionAutomaton) {
		
		Element statesElement=doc.createElement(AutomataIOConstants.XML_ELEMENT_STATES);
		intersectionAutomatonElement.appendChild(statesElement);
		IntBAStateToElementTransformer stateTransformer = new IntBAStateToElementTransformer(
				intersectionAutomaton, doc);
		for (State s : intersectionAutomaton.getStates()) {
			Element xmlStateElement = stateTransformer.transform(s);
			statesElement.appendChild(xmlStateElement);

		}
	}

	private void computingTransitionElements(Document doc, Element intersectionAutomatonElement, IntersectionBA intersectionAutomaton) {
		Element transitionsElement=doc.createElement(AutomataIOConstants.XML_ELEMENT_TRANSITIONS);
		intersectionAutomatonElement.appendChild(transitionsElement);
		
		IntBATransitionToElementTransformer transitionTransformer = new IntBATransitionToElementTransformer(
				intersectionAutomaton, doc);
		for (Transition transition : intersectionAutomaton.getTransitions()) {
			Element transitionElement =transitionTransformer.transform(transition);
			transitionsElement.appendChild(transitionElement);
		}
	}
}
