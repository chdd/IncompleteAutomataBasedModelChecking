package it.polimi.constraints.io.out;

import it.polimi.automata.AutomataIOConstants;
import it.polimi.constraints.Constraint;
import it.polimi.constraints.SubProperty;

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
 * 
 * @author claudiomenghi
 *
 */
public class ConstraintWriter {

	/**
	 * is the logger of the SubAutomataIdentifier class
	 */
	private static final Logger logger = LoggerFactory
			.getLogger(ConstraintWriter.class);

	/**
	 * contains the components to be written
	 */
	private Constraint constraint;
	
	/**
	 * contains the file where the intersection automaton must be written
	 */
	private File f;

	/**
	 * creates a new RefinementWriter for the set of components
	 * 
	 * @param components
	 *            is the set of components to be written
	 * @param f
	 *            is the file to which the intersection automaton must be
	 *            written
	 * @throws NullPointerException
	 *             if the components or the file is null
	 * 
	 */
	public ConstraintWriter(Constraint constraint, File f) {
		Preconditions.checkNotNull(constraint,
				"The intersection automaton cannot be null");
		Preconditions.checkNotNull(f,
				"The file where the automaton must be written cannot be null");

		this.constraint = constraint;
		this.f = f;
	}

	public void write() {

		logger.info("Writing the intersection automaton");
		try {

			DocumentBuilderFactory docFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

			Document doc = docBuilder.newDocument();
			Element rootElement = doc
					.createElement(AutomataIOConstants.XML_ELEMENT_CONSTRAINTS);
			doc.appendChild(rootElement);

			SubPropertyToElementTransformer componentTransformer = new SubPropertyToElementTransformer(
					doc);
			for (SubProperty component : constraint.getComponents()) {

				Element componentElement=componentTransformer.transform(component);
				rootElement.appendChild(componentElement);

			}

			
			Element graphPortXMLElement = new PortsGraphToElementTransformer(
					doc).transform(this.constraint.getPortsGraph());
			rootElement.appendChild(graphPortXMLElement);

			Element colors = doc
					.createElement(AutomataIOConstants.XML_ELEMENT_PORTS_COLORS);
			rootElement.appendChild(colors);

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


}
