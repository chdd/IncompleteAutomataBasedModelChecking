package it.polimi.automata.io;

import it.polimi.automata.AutomataIOConstants;
import it.polimi.automata.IBA;
import it.polimi.automata.io.in.propositions.StringToClaimPropositions;
import it.polimi.automata.io.in.states.ElementToIBAStateTransformer;
import it.polimi.automata.io.in.transitions.ElementToIBATransitionTransformer;
import it.polimi.automata.state.State;
import it.polimi.automata.state.StateFactory;
import it.polimi.automata.transition.ClaimTransitionFactory;
import it.polimi.automata.transition.ModelTransitionFactory;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import rwth.i2.ltl2ba4j.model.IGraphProposition;

import com.google.common.base.Preconditions;

/**
 * contains the reader which is used to read an <b>Incomplete</b> Buchi
 * automaton
 * 
 * @author claudiomenghi
 * 
 */
public class IBAReader {

	/**
	 * contains the Incomplete Buchi Automaton loaded from the file
	 */
	protected IBA iba;

	/**
	 * is the File from which the IBA must be read
	 */
	private File file;

	
	private Map<Integer, State> mapIdState;

	private static final String IBA_XSD_PATH = "IBA.xsd";



	/**
	 * creates a new Buchi automaton reader which can be used to read a Buchi
	 * automaton through the method
	 * 
	 * @see BAReader#read()
	 * 
	 * @param file
	 *            is the reader from which the Buchi automaton must be loaded
	 * @param transitionFactory
	 *            is the factory which allows to create the transitions of the
	 *            Buchi automaton
	 * @param stateFactory
	 *            is the factory which allows to create the states of the Buchi
	 *            automaton
	 * 
	 * @throws NullPointerException
	 *             if one of the parameters is null
	 */
	public IBAReader(File file) {
		Preconditions.checkNotNull(file, "The fileReader cannot be null");
		
		this.mapIdState = new HashMap<Integer, State>();

		this.iba = new IBA(new ModelTransitionFactory());
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
	public IBA read() {

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
			
			//File xsd = new File(this.getClass().getClassLoader()
			//		.getResource(IBA_XSD_PATH).getFile());
			//this.validateAgainstXSD(new FileInputStream(this.file),
			//		new FileInputStream(xsd));

			this.loadPropositions(doc);
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
	
	private void loadPropositions(Element doc) {
		NodeList xmlPropositions = doc
				.getElementsByTagName(AutomataIOConstants.XML_ELEMENT_PROPOSITION);

		StringToClaimPropositions propositionParser = new StringToClaimPropositions();
		for (int stateid = 0; stateid < xmlPropositions.getLength(); stateid++) {
			Node xmlstate = xmlPropositions.item(stateid);
			Element eElement = (Element) xmlstate;
			Set<IGraphProposition> propositions = propositionParser
					.computePropositions(eElement
							.getAttribute(AutomataIOConstants.XML_ELEMENT_PROPOSITION_VALUE));
			this.iba.addPropositions(propositions);
		}
	}

	private void loadTransitions(Element doc) {
		NodeList xmltransitions = doc
				.getElementsByTagName(AutomataIOConstants.XML_TAG_TRANSITION);

		for (int transitionid = 0; transitionid < xmltransitions.getLength(); transitionid++) {
			Node xmltransition = xmltransitions.item(transitionid);
			Element eElement = (Element) xmltransition;
			new ElementToIBATransitionTransformer(new ClaimTransitionFactory(), iba, this.mapIdState).transform(eElement);
		}
	}
	private boolean validateAgainstXSD(InputStream xml, InputStream xsd)
			throws SAXException, IOException {
		SchemaFactory factory = SchemaFactory
				.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
		Schema schema = factory.newSchema(new StreamSource(xsd));
		Validator validator = schema.newValidator();
		validator.validate(new StreamSource(xml));
		return true;

	}
}
