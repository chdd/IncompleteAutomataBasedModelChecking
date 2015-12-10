package it.polimi.constraints.io.in.constraint;

import it.polimi.automata.AutomataIOConstants;
import it.polimi.automata.io.in.XMLReader;
import it.polimi.constraints.Constraint;
import it.polimi.constraints.components.SubProperty;
import it.polimi.constraints.io.ConstraintsIOConstants;
import it.polimi.constraints.reachability.ReachabilityEntry;
import it.polimi.constraints.reachability.ReachabilityRelation;
import it.polimi.constraints.transitions.LabeledPluggingTransition;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.google.common.base.Preconditions;

/**
 * The ConstraintReader is used to load the constraint from an XML file. The XML
 * files must be conform with respect to the XSD} file Constraint.xsd. The
 * reader uses the ElementToPort, ElementToPortGraph and ElementToSubProperty
 * transformers to convert the XML elements into the corresponding Java objects.
 * 
 * @author claudiomenghi
 *
 */
public class ConstraintReader extends XMLReader<Constraint> {

	private static final String NAME = "READ CONSTRAINT";
	/**
	 * is the logger of the BAReader class
	 */
	private static final Logger logger = Logger
			.getLogger(ConstraintReader.class);

	/**
	 * is the file from which the constraint must be read
	 */
	private final File file;

	/**
	 * creates a new constraint reader
	 * 
	 * @param file
	 *            is the file from which the constraint must be read
	 * @throws NullPointerException
	 *             if the file to be considered is null
	 */
	public ConstraintReader(File file) {
		super(NAME);
		Preconditions
				.checkNotNull(file,
						"The file from which the constraint must be read cannot be null");

		this.file = file;
	}

	public Constraint perform() {
		Constraint ret = new Constraint();

		Document dom;
		// Make an instance of the DocumentBuilderFactory
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		try {
			
			if(ClassLoader.getSystemResourceAsStream(ConstraintsIOConstants.CONSTRAINT_XSD_PATH)==null){
				throw new NullPointerException("The resource "+ConstraintsIOConstants.CONSTRAINT_XSD_PATH+" cannot be founded");
			}
			
			this.validateAgainstXSD(
					new FileInputStream(this.file),
					ClassLoader.getSystemResourceAsStream(ConstraintsIOConstants.CONSTRAINT_XSD_PATH));
			
			// File xsdFile = new
			// File(this.getClass().getClassLoader().getResource(ConstraintsIOConstants.CONSTRAINT_XSD_PATH).getFile());
			// this.validateAgainstXSD(new FileInputStream(this.file), new
			// FileInputStream(xsdFile));

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

		// contains the list of sub-properties
		NodeList xmlSubproperties = doc
				.getElementsByTagName(ConstraintsIOConstants.XML_ELEMENT_SUBPROPERTY);

		logger.debug(xmlSubproperties.getLength()
				+ " subproperties present in the file " + file.getName());
		for (int stateid = 0; stateid < xmlSubproperties.getLength(); stateid++) {

			Map<Integer, LabeledPluggingTransition> mapIdPort = new HashMap<Integer, LabeledPluggingTransition>();

			// considers each sub-property
			Node xmlSubproperty = xmlSubproperties.item(stateid);
			Element xmlSubPropertyElement = (Element) xmlSubproperty;

			
			// loads the sub-property
			SubProperty subProperty = new ElementToSubPropertyTransformer()
					.transform(xmlSubPropertyElement);
			constraint.addSubProperty(subProperty);

			loadOutgoingTransitions(mapIdPort, xmlSubPropertyElement,
					subProperty);

			this.loadIncomingTransitions(mapIdPort, xmlSubPropertyElement, subProperty);

			Element lowerReachability = (Element) xmlSubPropertyElement
					.getElementsByTagName(
							AutomataIOConstants.XML_LOWER_REACHABILITY).item(0);
			ReachabilityRelation lowerReachabilityRelation = new ElementToReachabilityRelationTransformer(
					mapIdPort).transform(lowerReachability);

			for (ReachabilityEntry entry : lowerReachabilityRelation
					.getReachabilityAcceptingMap().values()) {
				subProperty.addReachabilityRelation(
						entry.getOutgoingTransition(),
						entry.getIncomingTransition(),
						entry.isModelAccepting(), entry.isClaimAccepting());
			}
			Element upperReachability = (Element) xmlSubPropertyElement
					.getElementsByTagName(
							AutomataIOConstants.XML_UPPER_REACHABILITY).item(0);
			ReachabilityRelation upperReachabilityRelation = new ElementToReachabilityRelationTransformer(
					mapIdPort).transform(upperReachability);
			for (ReachabilityEntry entry : upperReachabilityRelation
					.getReachabilityAcceptingMap().values()) {
				subProperty.addPossibleReachabilityRelation(
						entry.getOutgoingTransition(),
						entry.getIncomingTransition(),
						entry.isModelAccepting(), entry.isClaimAccepting());
			}
		}

		logger.debug("constraint loaded ");

	}

	/**
	 * @param mapIdPort
	 * @param xmlSubPropertyElement
	 * @param subProperty
	 */
	private void loadOutgoingTransitions(
			Map<Integer, LabeledPluggingTransition> mapIdPort,
			Element xmlSubPropertyElement, SubProperty subProperty) {
		// loads the out-coming ports
		Element xmlOutComingPorts = (Element) xmlSubPropertyElement
				.getElementsByTagName(
						AutomataIOConstants.XML_ELEMENT_TRANSITIONS_OUT)
				.item(0);
		NodeList xmlOutComingPortsList = xmlOutComingPorts
				.getElementsByTagName(ConstraintsIOConstants.XML_ELEMENT_LABELED_PLUG_TRANSITION);
		for (int portId = 0; portId < xmlOutComingPortsList.getLength(); portId++) {
			Element xmlOutComingPort = (Element) xmlOutComingPortsList
					.item(portId);
			LabeledPluggingTransition port = new ElementToLabeledPlugTransitionTransformer(
					false).transform(xmlOutComingPort);
			mapIdPort.put(port.getId(), port);
			subProperty.addOutgoingTransition(port);
		}
	}

	/**
	 * @param mapIdPort
	 * @param xmlSubPropertyElement
	 * @param subProperty
	 */
	private void loadIncomingTransitions(
			Map<Integer, LabeledPluggingTransition> mapIdPort,
			Element xmlSubPropertyElement, SubProperty subProperty) {
		// loads the incoming ports
		Element xmlInComingPorts = (Element) xmlSubPropertyElement
				.getElementsByTagName(
						AutomataIOConstants.XML_ELEMENT_TRANSITIONS_IN)
				.item(0);
		NodeList xmlInComingPortsList = xmlInComingPorts
				.getElementsByTagName(ConstraintsIOConstants.XML_ELEMENT_LABELED_PLUG_TRANSITION);
		for (int portId = 0; portId < xmlInComingPortsList.getLength(); portId++) {
			Element xmlInComingPort = (Element) xmlInComingPortsList
					.item(portId);
			LabeledPluggingTransition port = new ElementToLabeledPlugTransitionTransformer(
					true).transform(xmlInComingPort);
			mapIdPort.put(port.getId(), port);
			subProperty.addIncomingTransition(port);
		}
	}
}
