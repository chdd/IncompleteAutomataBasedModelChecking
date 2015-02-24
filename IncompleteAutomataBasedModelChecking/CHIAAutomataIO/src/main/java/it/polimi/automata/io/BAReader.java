package it.polimi.automata.io;

import it.polimi.automata.BA;
import it.polimi.automata.Constants;
import it.polimi.automata.impl.IBAImpl;
import it.polimi.automata.state.State;
import it.polimi.automata.state.StateFactory;
import it.polimi.automata.transition.Transition;
import it.polimi.automata.transition.TransitionFactory;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.xml.bind.JAXBException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import rwth.i2.ltl2ba4j.model.IGraphProposition;
import rwth.i2.ltl2ba4j.model.impl.GraphProposition;
import rwth.i2.ltl2ba4j.model.impl.SigmaProposition;

/**
 * contains the reader which is used to read a Buchi automaton
 * 
 * @author claudiomenghi
 * 
 * @param <S>
 *            is the type of the State of the Buchi Automaton. It must extend
 *            the interface {@link State}
 * @param <G>
 *            is the factory which is used to create the states of the Buchi
 *            Automaton. it must implement the interface {@link StateFactory}
 * @param <T>
 *            is the type of the transitions of the automaton. It must implement
 *            the interface {@link Transition}
 * @param <H>
 *            is the factory which allows to create the transitions. It must
 *            implement the interface {@link TransitionFactory}
 */
public class BAReader<S extends State, G extends StateFactory<S>, T extends Transition, H extends TransitionFactory<S, T>> {

	/**
	 * is the logger of the BAReader class
	 */
	private static final Logger logger = LoggerFactory
			.getLogger(BAReader.class);
	
	/**
	 * contains the Buchi Automaton loaded from the file
	 */
	protected final BA<S, T> ba;

	/**
	 * contains the file from which the Buchi automaton must be reader
	 */
	private final File file;
	
	/**
	 * contains a map that connects the id with the corresponding state
	 */
	private final Map<Integer, S> mapIdState;
	
	/**
	 * is the factory which is used to create the states of the Buchi automaton
	 */
	private final G stateFactory;

	/**
	 * is the factory which is used to create the transitions of the Buchi automaotn
	 */
	private final H transitionFactory;
	
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
	 *            is the file from which the automaton must be read
	 * @throws NullPointerException
	 *             if the labelFactory, transitionFactory, stateFactory,
	 *             automatonFactory or the fileReader is null
	 */
	public BAReader(H transitionFactory, G stateFactory, File file) {
		Validate.notNull(transitionFactory, "The transition factory cannot be null");
		Validate.notNull(stateFactory,"The state factory cannot be null");
		Validate.notNull(file,"The fileReader cannot be null");

		this.ba = new IBAImpl<S, T>(transitionFactory);

		this.mapIdState=new HashMap<Integer, S>();
		this.file = file;
		this.stateFactory=stateFactory;
		this.transitionFactory=transitionFactory;
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
	public BA<S, T> read(){

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
		NodeList xmlstates=doc.getElementsByTagName(Constants.XML_ELEMENT_STATE);
		
		for(int stateid=0; stateid<xmlstates.getLength(); stateid++){
			Node xmlstate=xmlstates.item(stateid);
			Element eElement = (Element) xmlstate;
			
			int id= Integer.parseInt(eElement.getAttribute(Constants.XML_ATTRIBUTE_ID));
			
			S s=stateFactory.create(eElement.getAttribute(Constants.XML_ATTRIBUTE_NAME), id);
			this.ba.addState(s);
			this.mapIdState.put(id, s);
			
			if(!eElement.getAttribute(Constants.XML_ATTRIBUTE_INITIAL).isEmpty()){
				this.ba.addInitialState(s);
			}
			if(!eElement.getAttribute(Constants.XML_ATTRIBUTE_ACCEPTING).isEmpty()){
				this.ba.addAcceptState(s);
			}
			
		}
	}
	
	private void loadTransitions(Element doc) {
		NodeList xmltransitions=doc.getElementsByTagName(Constants.XML_TAG_TRANSITION);
		
		for(int transitionid=0; transitionid<xmltransitions.getLength(); transitionid++){
			Node xmltransition=xmltransitions.item(transitionid);
			Element eElement = (Element) xmltransition;
			
			int id= Integer.parseInt(eElement.getAttribute(Constants.XML_ATTRIBUTE_TRANSITION_ID));
			
			Set<IGraphProposition> propositions=this.computePropositions(eElement.getAttribute(Constants.XML_ATTRIBUTE_TRANSITION_PROPOSITIONS));
			
			T t=transitionFactory.create(id, propositions);
			
			int sourceId=Integer.parseInt(eElement.getAttribute(Constants.XML_ATTRIBUTE_TRANSITION_SOURCE));
			int destinationId=Integer.parseInt(eElement.getAttribute(Constants.XML_ATTRIBUTE_TRANSITION_DESTINATION));
			this.ba.addTransition(this.mapIdState.get(sourceId), this.mapIdState.get(destinationId), t);
		}
	}
	
	/**
	 * Starting from the string computes the corresponding proposition. The
	 * string must satisfy the regular expression {@link Constants#APREGEX} or
	 * the regular expression {@link Constants#NOTAPREGEX}
	 * 
	 * @param input
	 *            is the input to be computed
	 * @throws NullPointerException
	 *             if the input is null
	 * @throws IllegalArgumentException
	 *             if the input does not match the regular expression
	 *             {@link Constants#APREGEX} or the regular expression
	 *             {@link Constants#NOTAPREGEX}
	 */
	public Set<IGraphProposition> computePropositions(String input) {

		Set<IGraphProposition> propositions=new HashSet<IGraphProposition>();
		if (input == null) {
			throw new NullPointerException("The input must be not null");
		}
		if (!input.matches(Constants.CLAIM_PROPOSITIONAL_FORMULA)) {
			throw new IllegalArgumentException(
					"The input "+input+" must match the regular expression: "
							+ Constants.CLAIM_PROPOSITIONAL_FORMULA);
		}
		
		if(input.equals(Constants.SIGMA)){
			propositions.add(new SigmaProposition());
		}
		else{
			String[] apsStrings=input.split(Constants.AND);
			
			for(String ap: apsStrings){
				if(ap.startsWith(Constants.NOT)){
					propositions.add(new GraphProposition(ap.substring(Constants.NOT.length()), true));
				}
				else{
					propositions.add(new GraphProposition(ap, false));
				}
				
			}
		}
		
		this.ba.addCharacters(propositions);
		return propositions;
	}
}
