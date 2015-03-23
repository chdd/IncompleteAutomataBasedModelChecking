package it.polimi.constraints.io.in;

import it.polimi.automata.AutomataIOConstants;
import it.polimi.constraints.Constraint;
import it.polimi.constraints.Port;
import it.polimi.constraints.SubProperty;

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
 * Loads the constraint from the specified file
 * 
 * @author claudiomenghi
 *
 */
public class ConstraintReader {

	/**
	 * is the logger of the BAReader class
	 */
	private static final Logger logger = LoggerFactory
			.getLogger(ConstraintReader.class);

	/**
	 * is the file from which the constraint must be read
	 */
	private final File file;

	/**
	 * contains a map that given an id returns the corresponding port
	 */
	private final Map<Integer, Port> mapIdPort;

	/**
	 * creates a new constraint reader
	 * 
	 * @param file
	 *            is the file from which the constraint must be rea
	 * @throws NullPointerException
	 *             if one of the parameters is null
	 */
	public ConstraintReader(File file) {
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
				.getElementsByTagName(AutomataIOConstants.XML_ELEMENT_CONSTRAINT);

		logger.debug(xmlSetOfConstraints.getLength()
				+ " constraints present in the file " + file.getName());
		for (int stateid = 0; stateid < xmlSetOfConstraints.getLength(); stateid++) {
			Node xmlConstraint = xmlSetOfConstraints.item(stateid);
			Element xmlConstraintElement = (Element) xmlConstraint;

			SubProperty subProperty = new ElementToSubPropertyTransformer()
					.transform(xmlConstraintElement);
			constraint.addSubProperty(subProperty);

			Element xmlOutComingPorts = (Element) xmlConstraintElement
					.getElementsByTagName(AutomataIOConstants.XML_ELEMENT_PORTS_OUT)
					.item(0);
			NodeList xmlOutComingPortsList = xmlOutComingPorts
					.getElementsByTagName(AutomataIOConstants.XML_ELEMENT_PORT);
			for (int portId = 0; portId < xmlOutComingPortsList.getLength(); portId++) {
				Element xmlOutComingPort = (Element) xmlOutComingPortsList
						.item(portId);
				Port port = new ElementToPortTransformer()
						.transform(xmlOutComingPort);
				this.mapIdPort.put(port.getId(), port);
				subProperty.addOutComingPort(port);
			}

			Element xmlInComingPorts = (Element) xmlConstraintElement
					.getElementsByTagName(AutomataIOConstants.XML_ELEMENT_PORTS_IN).item(
							0);
			NodeList xmlInComingPortsList = xmlInComingPorts
					.getElementsByTagName(AutomataIOConstants.XML_ELEMENT_PORT);
			for (int portId = 0; portId < xmlInComingPortsList.getLength(); portId++) {
				Element xmlInComingPort = (Element) xmlInComingPortsList
						.item(portId);
				Port port = new ElementToPortTransformer()
						.transform(xmlInComingPort);
				this.mapIdPort.put(port.getId(), port);
				subProperty.addIncomingPort(port);
			}

		}
		Preconditions
				.checkArgument(
						doc.getElementsByTagName(
								AutomataIOConstants.XML_ELEMENT_PORTS_REACHABILITY)
								.getLength() >= 1,
						"Port reachability element not present");
		Element portReachability = (Element) doc.getElementsByTagName(
				AutomataIOConstants.XML_ELEMENT_PORTS_REACHABILITY).item(0);
		new ElementToPortGraphTransformer(this.mapIdPort,
				constraint.getPortsGraph()).transform(portReachability);
	}
}
