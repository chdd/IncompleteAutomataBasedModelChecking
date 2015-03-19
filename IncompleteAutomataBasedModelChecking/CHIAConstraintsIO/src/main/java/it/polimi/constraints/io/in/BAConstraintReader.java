package it.polimi.constraints.io.in;

import it.polimi.automata.BA;
import it.polimi.automata.Constants;
import it.polimi.automata.state.State;
import it.polimi.automata.transition.Transition;
import it.polimi.constraints.Color;
import it.polimi.constraints.impl.Constraint;
import it.polimi.constraints.impl.Port;
import it.polimi.constraints.impl.SubProperty;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

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

	private final File file;

	/**
	 * contains a map that given an id returns the corresponding port
	 */
	private final Map<Integer, Port> mapIdPort;

	
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
	public BAConstraintReader(File file) {
		Preconditions
				.checkNotNull(file,
						"The file from which the constraint must be read cannot be null");
	
		this.file = file;
		this.mapIdPort = new HashMap<Integer, Port>();
	}

	public Constraint read() {
		Constraint ret = new Constraint();

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

	private void loadConstraint(Element doc, Constraint constraint) {
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

			SubProperty component=new ElementToSubPropertyTransformer<S, T>(this.stateElementParser, this.transitionElementParser, this.componentFactory).transform(xmlConstraintElement);
			constraint.addComponent(component);
			
			
			Element xmlOutComingPorts=(Element) xmlConstraintElement.getElementsByTagName(
					Constants.XML_ELEMENT_PORTS_OUT).item(0);
			NodeList xmlOutComingPortsList=xmlOutComingPorts.getElementsByTagName(Constants.XML_ELEMENT_PORT);
			for (int portId = 0; portId < xmlOutComingPortsList.getLength(); portId++) {
				Element  xmlOutComingPort=(Element) xmlOutComingPortsList.item(portId);
				Port<S,T> port=new ElementToPortTransformer<S,T,BA<S,T>>(this.transitionElementParser.getTransitionFactory(), this.stateElementParser.getStateFactory()).transform(xmlOutComingPort);
				this.mapIdPort.put(port.getId(), port);
				constraint.addOutComingPort(component, port);
			}
			
			Element xmlInComingPorts=(Element) xmlConstraintElement.getElementsByTagName(
					Constants.XML_ELEMENT_PORTS_IN).item(0);
			NodeList xmlInComingPortsList=xmlInComingPorts.getElementsByTagName(Constants.XML_ELEMENT_PORT);
			for (int portId = 0; portId < xmlInComingPortsList.getLength(); portId++) {
				Element  xmlInComingPort=(Element) xmlInComingPortsList.item(portId);
				Port<S,T> port=new ElementToPortTransformer<S,T,BA<S,T>>(this.transitionElementParser.getTransitionFactory(), this.stateElementParser.getStateFactory()).transform(xmlInComingPort);
				this.mapIdPort.put(port.getId(), port);
				constraint.addIncomingPort(component, port);
			}
				
		}
		Preconditions.checkArgument(doc.getElementsByTagName(
				Constants.XML_ELEMENT_PORTS_REACHABILITY).getLength()>=1, "Port reachability element not present");
		Element portReachability=(Element) doc.getElementsByTagName(
				Constants.XML_ELEMENT_PORTS_REACHABILITY).item(0);
		new ElementToPortGraphTransformer<S, T>(this.mapIdPort, constraint.getPortsGraph()).transform(portReachability);
		
		Element portSColor=(Element) doc.getElementsByTagName(
				Constants.XML_ELEMENT_PORTS_COLORS).item(0);
		this.loadPortsColor(portSColor, constraint);
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
}
