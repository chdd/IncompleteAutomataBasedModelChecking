package it.polimi.constraints.io.out;

import it.polimi.automata.BA;
import it.polimi.automata.Constants;
import it.polimi.automata.state.State;
import it.polimi.automata.transition.Transition;
import it.polimi.constraints.Color;

import java.io.File;
import java.util.Map.Entry;

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
 * @param <S>
 *            is the type of the state to be written
 * @param <I>
 *            is the type of the transitions to be written
 */
public class ConstraintWriter<S extends State, I extends Transition,  A extends BA<S, I>> {

	/**
	 * is the logger of the SubAutomataIdentifier class
	 */
	private static final Logger logger = LoggerFactory
			.getLogger(ConstraintWriter.class);

	/**
	 * contains the components to be written
	 */
	private Constraint<S, I, A> constraint;
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
	public ConstraintWriter(Constraint<S, I, A> constraint, File f) {
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
					.createElement(Constants.XML_ELEMENT_CONSTRAINTS);
			doc.appendChild(rootElement);

			SubPropertyToElementTransformer<S, I, A> componentTransformer = new SubPropertyToElementTransformer<S, I, A>(
					doc, constraint);
			for (SubProperty<S, I, A> component : constraint.getComponents()) {

				Element componentElement=componentTransformer.transform(component);
				rootElement.appendChild(componentElement);

			}

			
			Element graphPortXMLElement = new PortsGraphToElementTransformer<S, I>(
					doc).transform(this.constraint.getPortsGraph());
			rootElement.appendChild(graphPortXMLElement);

			Element colors = doc
					.createElement(Constants.XML_ELEMENT_PORTS_COLORS);
			rootElement.appendChild(colors);

			this.addPortsColor(doc, colors, constraint);

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

	private void addPortsColor(Document doc, Element colorElement,
			Constraint<S, I, A> constraint) {

		PortsColorToElementTransformer<S, I, A> portToElementTrasformer=new PortsColorToElementTransformer<S, I, A>(doc, constraint);
		for (Entry<Port<S, I>, Color> e : constraint.getPortValue().entrySet()) {
			Element portElement = portToElementTrasformer.transform(e.getKey());
			colorElement.appendChild(portElement);
		}
	}
}
