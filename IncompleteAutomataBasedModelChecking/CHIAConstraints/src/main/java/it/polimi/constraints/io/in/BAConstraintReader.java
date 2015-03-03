package it.polimi.constraints.io.in;

import it.polimi.automata.BA;
import it.polimi.automata.Constants;
import it.polimi.automata.io.transformer.states.StateElementParser;
import it.polimi.automata.io.transformer.transitions.ClaimTransitionParser;
import it.polimi.automata.state.State;
import it.polimi.automata.transition.Transition;
import it.polimi.constraints.Color;
import it.polimi.constraints.Component;
import it.polimi.constraints.Constraint;
import it.polimi.constraints.Port;
import it.polimi.constraints.impl.BAComponentFactory;
import it.polimi.constraints.impl.ConstraintImpl;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

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
 * reads the constraint form the specified file
 * 
 * @author claudiomenghi
 *
 * @param <S>
 *            is the type of the state to be read
 * @param <T>
 *            is the type of the transitions to be read
 */
public class BAConstraintReader<S extends State, T extends Transition> {

	/**
	 * is the logger of the BAReader class
	 */
	private static final Logger logger = LoggerFactory
			.getLogger(BAConstraintReader.class);

	private File file;

	private final StateElementParser<S, T, BA<S, T>> stateElementParser;

	private final ClaimTransitionParser<S, T, BA<S, T>> transitionElementParser;

	/**
	 * contains a map that connects the id with the corresponding state
	 */
	private final Map<Integer, S> mapIdState;

	/**
	 * contains a map that given an id returns the corresponding port
	 */
	private final Map<Integer, Port<S, T>> mapIdPort;

	private BAComponentFactory<S, T> componentFactory;

	/**
	 * creates a new constraint reader
	 * 
	 * @param f
	 *            is the file from which the constraint must be read
	 * @param stateElementParser
	 *            is the parser that is used to load the states from file
	 * @param transitionElementParser
	 *            is the parser that is used to load the transitions from file
	 * @throws NullPointerException
	 *             if one of the parameters is null
	 */
	public BAConstraintReader(File file, 
			StateElementParser<S, T, BA<S, T>> stateElementParser,
			ClaimTransitionParser<S, T, BA<S, T>> transitionElementParser) {
		Preconditions
				.checkNotNull(file,
						"The file from which the constraint must be read cannot be null");
		Preconditions.checkNotNull(stateElementParser,
				"The parser of the states cannot be null");
		Preconditions.checkNotNull(transitionElementParser,
				"The parser of the transitions cannot be null");
	
		this.file = file;
		this.stateElementParser = stateElementParser;
		this.transitionElementParser = transitionElementParser;
		this.mapIdState = new HashMap<Integer, S>();
		this.componentFactory = new BAComponentFactory<S, T>();
		this.mapIdPort = new HashMap<Integer, Port<S, T>>();
	}

	public Constraint<S, T, BA<S,T>> read() {
		Constraint<S, T, BA<S,T>> ret = new ConstraintImpl<S, T, BA<S,T>>();

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

			this.loadConstraint(doc, ret);

		} catch (ParserConfigurationException pce) {
			logger.error(pce.getMessage());
		} catch (SAXException se) {
			logger.error(se.getMessage());
		} catch (IOException ioe) {
			logger.error(ioe.getMessage());
		}

		return ret;
	}

	private void loadConstraint(Element doc, Constraint<S, T, BA<S,T>> constraint) {
		Preconditions.checkNotNull(doc, "The document element cannot be null");
		Preconditions.checkNotNull(constraint,
				"The returning constraint cannot be null");

		NodeList xmlSetOfConstraints = doc
				.getElementsByTagName(Constants.XML_ELEMENT_CONSTRAINT);

		logger.debug(xmlSetOfConstraints.getLength()
				+ " constraints present in the file " + file.getName());
		for (int stateid = 0; stateid < xmlSetOfConstraints.getLength(); stateid++) {
			Node xmlConstraint = xmlSetOfConstraints.item(stateid);
			Element xmlConstraintElement = (Element) xmlConstraint;

			Component<S, T, BA<S,T>> c = this.loadComponent(xmlConstraintElement, constraint);
			constraint.addComponent(c);
		}
		Element portReachability=(Element) doc.getElementsByTagName(
				Constants.XML_ELEMENT_PORTS_REACHABILITY).item(0);
		this.loadPortsRelation(portReachability, constraint);
		
		Element portSColor=(Element) doc.getElementsByTagName(
				Constants.XML_ELEMENT_PORTS_COLORS).item(0);
		this.loadPortsColor(portSColor, constraint);
	}

	private Component<S, T, BA<S,T>> loadComponent(Element doc, Constraint<S, T, BA<S,T>> constraint) {
		Preconditions.checkNotNull(doc, "The document element cannot be null");

		int componentId = Integer.parseInt(doc
				.getAttribute(Constants.XML_ATTRIBUTE_MODEL_STATE_ID));

		String stateName = doc.getAttribute(Constants.XML_ATTRIBUTE_NAME);
		S s = this.stateElementParser.getStateFactory().create(stateName,
				componentId);
		Component<S, T, BA<S,T>> component = this.componentFactory.create(componentId,
				Integer.toString(componentId), s, true,
				this.transitionElementParser.getTransitionFactory());

		Element baElement = (Element) doc.getElementsByTagName(
				Constants.XML_ELEMENT_BA).item(0);
		this.loadStates(baElement, component);
		this.loadTransitions(baElement, component);

		Element outcomingPortsElement = (Element) doc.getElementsByTagName(
				Constants.XML_ELEMENT_PORTS_OUT).item(0);
		this.loadOutputPorts(outcomingPortsElement, component, constraint);
		Element incomingPortsElement = (Element) doc.getElementsByTagName(
				Constants.XML_ELEMENT_PORTS_IN).item(0);

		this.loadIncomingPorts(incomingPortsElement, component, constraint);
		

		return component;
	}
	private void loadPortsColor(Element portsElement,
			Constraint<S, T, BA<S,T>> constraint) {
		Preconditions.checkNotNull(portsElement,
				"The ports element cannot be null");
		Preconditions.checkNotNull(constraint, "The constraint cannot be null");
		NodeList ports=portsElement.getElementsByTagName(Constants.XML_ELEMENT_PORT);
		
		for (int portId = 0; portId < ports.getLength(); portId++) {
			Node xmlport = ports.item(portId);
			Element portElement = (Element) xmlport;
			int currentPortId = Integer.parseInt(portElement
					.getAttribute(Constants.XML_ATTRIBUTE_ID));
			Color c=Color.valueOf(portElement
					.getAttribute(Constants.XML_ATTRIBUTE_COLOR));
			constraint.setPortValue(this.mapIdPort.get(currentPortId), c);
		}
	}

	private void loadPortsRelation(Element portsElement,
			Constraint<S, T, BA<S,T>> constraint) {
		Preconditions.checkNotNull(portsElement,
				"The ports element cannot be null");
		Preconditions.checkNotNull(constraint, "The constraint cannot be null");
		NodeList outReachability = portsElement
				.getElementsByTagName(Constants.XML_ELEMENT_PORTS_OUT_REACHABILITY);
		NodeList outPortRelation = ((Element) outReachability.item(0))
				.getElementsByTagName(Constants.XML_ELEMENT_PORT);

		for (int stateid = 0; stateid < outPortRelation.getLength(); stateid++) {
			Node xmlport = outPortRelation.item(stateid);
			Element portElement = (Element) xmlport;
			int sourcePortId = Integer.parseInt(portElement
					.getAttribute(Constants.XML_ATTRIBUTE_ID));
			NodeList destinationPortRelation = portElement
					.getElementsByTagName(Constants.XML_ELEMENT_PORT);

			for (int portId = 0; portId < destinationPortRelation.getLength(); portId++) {
				Node nodeDestinationPort = destinationPortRelation.item(portId);
				Element elementDestinationPort = (Element) nodeDestinationPort;
				int destinationPortId = Integer.parseInt(elementDestinationPort
						.getAttribute(Constants.XML_ATTRIBUTE_ID));
				System.out.println(destinationPortId);
				constraint.addReachabilityRelation(
						this.mapIdPort.get(sourcePortId),
						this.mapIdPort.get(destinationPortId));
			}
		}

	}

	private void loadOutputPorts(Element portsElement, Component<S, T, BA<S,T>> component, Constraint<S, T, BA<S,T>> constraint) {
		Preconditions.checkNotNull(portsElement,
				"The ports element cannot be null");
		Preconditions.checkNotNull(component, "The component cannot be null");

		NodeList xmlstates = portsElement
				.getElementsByTagName(Constants.XML_ELEMENT_PORT);

		ElementToPortTransformer<S, T, BA<S,T>> elementToPort = new ElementToPortTransformer<S, T, BA<S,T>>(
				this.transitionElementParser.getTransitionFactory(),
				this.stateElementParser.getStateFactory());
		for (int stateid = 0; stateid < xmlstates.getLength(); stateid++) {
			Node xmlport = xmlstates.item(stateid);
			Element portElement = (Element) xmlport;

			Port<S, T> port = elementToPort.transform(portElement, component);
			constraint.addIncomingPort(component, port);
			this.mapIdPort.put(port.getId(), port);
		}
	}

	private void loadIncomingPorts(Element portsElement,
			Component<S, T, BA<S,T>> component,  Constraint<S, T, BA<S,T>> constraint) {
		Preconditions.checkNotNull(portsElement,
				"The ports element cannot be null");
		Preconditions.checkNotNull(component, "The component cannot be null");

		NodeList xmlstates = portsElement
				.getElementsByTagName(Constants.XML_ELEMENT_PORT);

		ElementToPortTransformer<S, T, BA<S,T>> elementToPort = new ElementToPortTransformer<S, T, BA<S,T>>(
				this.transitionElementParser.getTransitionFactory(),
				this.stateElementParser.getStateFactory());
		for (int stateid = 0; stateid < xmlstates.getLength(); stateid++) {
			Node xmlport = xmlstates.item(stateid);
			Element portElement = (Element) xmlport;

			Port<S, T> port = elementToPort.transform(portElement, component);
			constraint.addIncomingPort(component, port);
			this.mapIdPort.put(port.getId(), port);
		}
	}

	private void loadStates(Element baElement, Component<S, T, BA<S,T>> component) {
		Preconditions.checkNotNull(baElement,
				"The document element cannot be null");
		Preconditions.checkNotNull(component, "The component cannot be null");
		NodeList xmlstates = baElement
				.getElementsByTagName(Constants.XML_ELEMENT_STATE);

		for (int stateid = 0; stateid < xmlstates.getLength(); stateid++) {
			Node xmlstate = xmlstates.item(stateid);
			Element eElement = (Element) xmlstate;

			Entry<Integer, S> entry = this.stateElementParser.transform(
					eElement, component.getAutomaton());
			this.mapIdState.put(entry.getKey(), entry.getValue());
		}
	}

	private void loadTransitions(Element baElement, Component<S, T, BA<S,T>> component) {
		Preconditions.checkNotNull(baElement,
				"The document element cannot be null");
		Preconditions.checkNotNull(component, "The component cannot be null");
		NodeList xmltransitions = baElement
				.getElementsByTagName(Constants.XML_TAG_TRANSITION);

		for (int transitionid = 0; transitionid < xmltransitions.getLength(); transitionid++) {
			Node xmltransition = xmltransitions.item(transitionid);
			Element eElement = (Element) xmltransition;

			this.transitionElementParser.transform(eElement, component.getAutomaton(),
					this.mapIdState);
		}
	}
}
