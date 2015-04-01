package it.polimi.automata.io;

import it.polimi.automata.AutomataIOConstants;
import it.polimi.automata.IBAWithInvariants;
import it.polimi.automata.io.in.BAReader;
import it.polimi.automata.io.in.states.ElementToIBAStateTransformer;
import it.polimi.automata.io.in.transitions.ElementToIBATransitionTransformer;
import it.polimi.automata.state.State;
import it.polimi.automata.state.StateFactory;
import it.polimi.automata.transition.ModelTransitionFactory;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.JAXBException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.google.common.base.Preconditions;

public class IBAWithInvariantsReader {

	/**
	 * contains the Incomplete Buchi Automaton loaded from the file
	 */
	protected IBAWithInvariants iba;

	private File file;

	private Map<Integer, State> mapIdState;


	/**
	 * creates a new Buchi automaton reader which can be used to read a Buchi
	 * automaton through the method
	 * 
	 * @see BAReader#read()
	 * 
	 * @param transitionFactory
	 *            is the factory which allows to create the transitions of the
	 *            Buchi automaton
	 * @param stateFactory
	 *            is the factory which allows to create the states of the Buchi
	 *            automaton
	 * @param file
	 *            is the reader from which the Buchi automaton must be loaded
	 * @throws NullPointerException
	 *             if one of the parameters is null
	 */
	public IBAWithInvariantsReader(File file) {
		Preconditions.checkNotNull(file, "The fileReader cannot be null");
	
		this.mapIdState = new HashMap<Integer, State>();

		this.iba = new IBAWithInvariants(new ModelTransitionFactory());
		this.file = file;
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
	public IBAWithInvariants read() {

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
			System.out.println(pce.getMessage());
		} catch (SAXException se) {
			System.out.println(se.getMessage());
		} catch (IOException ioe) {
			System.err.println(ioe.getMessage());
		}

		return this.iba;
	}

	private void loadStates(Element doc) {
		NodeList xmlstates = doc
				.getElementsByTagName(AutomataIOConstants.XML_ELEMENT_STATE);

		for (int stateid = 0; stateid < xmlstates.getLength(); stateid++) {

			Node xmlstate = xmlstates.item(stateid);
			Element eElement = (Element) xmlstate;

			State state = new ElementToIBAStateTransformer(new StateFactory(), iba).transform(
					eElement);
			this.mapIdState.put(state.getId(), state);
		}
	}

	private void loadTransitions(Element doc) {
		NodeList xmltransitions = doc
				.getElementsByTagName(AutomataIOConstants.XML_TAG_TRANSITION);

		for (int transitionid = 0; transitionid < xmltransitions.getLength(); transitionid++) {
			Node xmltransition = xmltransitions.item(transitionid);
			Element eElement = (Element) xmltransition;
			new ElementToIBATransitionTransformer(new ModelTransitionFactory(), iba, this.mapIdState).transform(eElement);
		}
	}
}
