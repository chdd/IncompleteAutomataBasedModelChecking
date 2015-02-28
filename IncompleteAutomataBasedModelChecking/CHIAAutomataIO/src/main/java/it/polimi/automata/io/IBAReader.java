package it.polimi.automata.io;

import it.polimi.automata.Constants;
import it.polimi.automata.IBA;
import it.polimi.automata.impl.IBAImpl;
import it.polimi.automata.io.transformer.states.StateElementParser;
import it.polimi.automata.io.transformer.transitions.ModelTransitionParser;
import it.polimi.automata.io.transformer.transitions.TransitionElementParser;
import it.polimi.automata.state.State;
import it.polimi.automata.state.StateFactory;
import it.polimi.automata.transition.Transition;
import it.polimi.automata.transition.TransitionFactory;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

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

/**
 * contains the reader which is used to read an <b>Incomplete</b> Buchi
 * automaton
 * 
 * @author claudiomenghi
 * 
 * @param <S>
 *            is the type of the State of the Incomplete Buchi Automaton. It
 *            must extend the interface {@link State}
 * @param <G>
 *            is the factory which is used to create the states of the
 *            Incomplete Buchi Automaton. it must implement the interface
 *            {@link StateFactory}
 * @param <T>
 *            is the type of the transitions of the automaton. It must implement
 *            the interface {@link Transition}
 * @param <H>
 *            is the factory which allows to create the transitions. It must
 *            implement the interface {@link TransitionFactory}
 */
public class IBAReader<S extends State, T extends Transition> {

	/**
	 * contains the Incomplete Buchi Automaton loaded from the file
	 */
	protected IBA<S, T> iba;

	private File file;

	private Map<Integer, S> mapIdState;

	private final StateElementParser<S, T, IBA<S, T>> stateElementParser;

	private final TransitionElementParser<S, T, IBA<S, T>> transitionElementParser;


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
	public IBAReader(File file,
			StateElementParser<S, T, IBA<S, T>> stateElementParser,
			ModelTransitionParser<S, T, IBA<S, T>> transitionElementParser) {
		Preconditions.checkNotNull(file, "The fileReader cannot be null");
		Preconditions.checkNotNull(stateElementParser,
				"The state element parser cannot be null");

		Preconditions.checkNotNull(transitionElementParser,
				"The transition factory cannot be null");

		this.mapIdState = new HashMap<Integer, S>();

		this.iba = new IBAImpl<S, T>(
				transitionElementParser.getTransitionFactory());
		this.file = file;
		this.stateElementParser = stateElementParser;
		this.transitionElementParser = transitionElementParser;
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
	public IBA<S, T> read() {

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
				.getElementsByTagName(Constants.XML_ELEMENT_STATE);

		for (int stateid = 0; stateid < xmlstates.getLength(); stateid++) {

			Node xmlstate = xmlstates.item(stateid);
			Element eElement = (Element) xmlstate;

			Entry<Integer, S> entry = this.stateElementParser.transform(
					eElement, this.iba);
			this.mapIdState.put(entry.getKey(), entry.getValue());
		}
	}

	private void loadTransitions(Element doc) {
		NodeList xmltransitions = doc
				.getElementsByTagName(Constants.XML_TAG_TRANSITION);

		for (int transitionid = 0; transitionid < xmltransitions.getLength(); transitionid++) {
			Node xmltransition = xmltransitions.item(transitionid);
			Element eElement = (Element) xmltransition;
			this.transitionElementParser.transform(eElement, this.iba,
					this.mapIdState);
			
		}
	}
}
