package it.polimi.automata.io;

import it.polimi.automata.AutomataIOConstants;
import it.polimi.automata.IntersectionBA;
import it.polimi.automata.io.out.StateToElementTransformer;
import it.polimi.automata.io.out.TransitionToElementTransformer;
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
public class WriterBA {

	/**
	 * is the logger of the SubAutomataIdentifier class
	 */
	private static final Logger logger = LoggerFactory
			.getLogger(WriterBA.class);

	/**
	 * contains the intersection automaton to be written
	 */
	private IntersectionBA intersectionAutomaton;
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
	public WriterBA(IntersectionBA intersectionAutomaton, File f) {
		Preconditions.checkNotNull(intersectionAutomaton,
				"The intersection automaton cannot be null");
		Preconditions.checkNotNull(f,
				"The file where the automaton must be written cannot be null");

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
			Element rootElement = doc.createElement(AutomataIOConstants.XML_ELEMENT_BA);
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
		StateToElementTransformer stateTransformer = new StateToElementTransformer(
				this.intersectionAutomaton, doc);
		for (State s : this.intersectionAutomaton.getStates()) {
			Element xmlStateElement = stateTransformer.transform(s);
			rootElement.appendChild(xmlStateElement);

		}
	}

	private void computingTransitionElements(Document doc, Element rootElement) {
		TransitionToElementTransformer transitionTransformer = new TransitionToElementTransformer(
				this.intersectionAutomaton, doc);
		for (Transition transition : this.intersectionAutomaton.getTransitions()) {
			Element transitionElement =transitionTransformer.transform(transition);
			rootElement.appendChild(transitionElement);
		}
	}
}
