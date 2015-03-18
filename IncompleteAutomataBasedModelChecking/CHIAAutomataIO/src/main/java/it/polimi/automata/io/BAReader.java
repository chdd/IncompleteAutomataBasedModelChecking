package it.polimi.automata.io;

import it.polimi.automata.BA;
import it.polimi.automata.Constants;
import it.polimi.automata.IBA;
import it.polimi.automata.io.transformer.states.BAStateElementParser;
import it.polimi.automata.io.transformer.transitions.BATransitionParser;
import it.polimi.automata.state.State;
import it.polimi.automata.transition.ModelTransitionFactory;
import it.polimi.automata.transition.Transition;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.JAXBException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.google.common.base.Preconditions;

/**
 * contains the reader which is used to read a Buchi automaton
 * 
 * @author claudiomenghi
 * 
 * @param <S>
 *            is the type of the State of the Buchi Automaton. It must extend
 *            the interface {@link State}
 * @param <T>
 *            is the type of the transitions of the automaton. It must implement
 *            the interface {@link Transition}
 */
public class BAReader {

	/**
	 * is the logger of the BAReader class
	 */
	private static final Logger logger = LoggerFactory
			.getLogger(BAReader.class);

	/**
	 * contains the Buchi Automaton loaded from the file
	 */
	protected final BA ba;

	/**
	 * contains the file from which the Buchi automaton must be reader
	 */
	private final File file;

	/**
	 * contains a map that connects the id with the corresponding state
	 */
	private final Map<Integer, State> mapIdState;

	
	/**
	 * creates a new Buchi automaton reader which can be used to read a Buchi
	 * automaton through the method
	 * 
	 * @see BAReader#read()
	 * 
	 * @param transitionFactory
	 *            is the factory which allows to create the transitions of the
	 *            Buchi automaton
	 * @param file
	 *            is the file from which the automaton must be read
	 * @param stateElementParser
	 *            is the parser which is used to transform an element state into
	 *            a state object
	 * @throws NullPointerException
	 *             if the labelFactory, transitionFactory, stateFactory,
	 *             automatonFactory or the fileReader is null
	 */
	public BAReader(File file) {

		Preconditions.checkNotNull(file, "The fileReader cannot be null");
		
		this.ba = new IBA(new ModelTransitionFactory());
		this.file = file;
		this.mapIdState = new HashMap<Integer, State>();
	}

	/**
	 * read the Buchi Automaton from the reader
	 * 
	 * @return a new Buchi automaton which is parsed from the reader
	 * @throws JAXBException
	 * @throws GraphIOException
	 *             is generated if a problem occurs in the loading of the Buchi
	 *             Automaton
	 */
	public BA read() {

		logger.info("Reding the Buchi automaton");

		Document dom;
		// Make an instance of the DocumentBuilderFactory
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		try {
			// use the factory to take an instance of the document builder
			DocumentBuilder db = dbf.newDocumentBuilder();
			// parse using the builder to get the DOM mapping of the
			// XML file
			dom = db.parse(file);

			Element doc = dom.getDocumentElement();

			this.loadStates(doc);
			this.loadTransitions(doc);

		} catch (ParserConfigurationException pce) {
			logger.error(pce.getMessage());
		} catch (SAXException se) {
			logger.error(se.getMessage());
		} catch (IOException ioe) {
			logger.error(ioe.getMessage());
		}

		logger.info("Buchi automaton readed");
		return this.ba;
	}

	private void loadStates(Element doc) {
		NodeList xmlstates = doc
				.getElementsByTagName(Constants.XML_ELEMENT_STATE);

		for (int stateid = 0; stateid < xmlstates.getLength(); stateid++) {
			Node xmlstate = xmlstates.item(stateid);
			Element eElement = (Element) xmlstate;

			State s=new BAStateElementParser(this.ba).transform(
					eElement);
			this.mapIdState.put(s.getId(), s);

		}
	}

	private void loadTransitions(Element doc) {
		NodeList xmltransitions = doc
				.getElementsByTagName(Constants.XML_TAG_TRANSITION);

		for (int transitionid = 0; transitionid < xmltransitions.getLength(); transitionid++) {
			Node xmltransition = xmltransitions.item(transitionid);
			Element eElement = (Element) xmltransition;
			new BATransitionParser(ba, mapIdState).transform(eElement);

		}
	}
}
