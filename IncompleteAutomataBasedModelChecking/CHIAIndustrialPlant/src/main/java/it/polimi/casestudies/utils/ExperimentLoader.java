package it.polimi.casestudies.utils;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class ExperimentLoader {

	private final File experimentIndexFile;

	public ExperimentLoader(File experimentIndexFile) {

		this.experimentIndexFile = experimentIndexFile;
	}

	public Set<Experiment> read() throws ParserConfigurationException,
			SAXException, IOException {

		Set<Experiment> experiments = new HashSet<Experiment>();
		Document dom;
		// Make an instance of the DocumentBuilderFactory
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		// use the factory to take an instance of the document builder
		DocumentBuilder db = dbf.newDocumentBuilder();
		// parse using the builder to get the DOM mapping of the
		// XML file
		dom = db.parse(experimentIndexFile);

		Element doc = dom.getDocumentElement();

		NodeList xmlstates = doc
				.getElementsByTagName(ExperimentsConstants.XML_ELEMENT_EXPERIMENT);

		for (int stateid = 0; stateid < xmlstates.getLength(); stateid++) {
			Node xmlstate = xmlstates.item(stateid);
			Element eElement = (Element) xmlstate;
			
			
			File modelFile = new File(
					getClass()
							.getClassLoader()
							.getResource(
									ExperimentsConstants.XML_EXPERIMENTS_FOLDER
											+ eElement
													.getAttribute(ExperimentsConstants.XML_ATTRIBUTE_MODEL))
							.getFile());
			File claimFile = new File(
					getClass()
							.getClassLoader()
							.getResource(
									ExperimentsConstants.XML_EXPERIMENTS_FOLDER
											+ eElement
													.getAttribute(ExperimentsConstants.XML_ATTRIBUTE_CLAIM))
							.getFile());

			File resultsFile = new File(
					ExperimentsConstants.XML_RESULT_FOLDER
							+ eElement
									.getAttribute(ExperimentsConstants.XML_ATTRIBUTE_RESULT));

			Boolean ltl= Boolean.parseBoolean(eElement
					.getAttribute(ExperimentsConstants.XML_ATTRIBUTE_LTL));
			experiments.add(new Experiment(claimFile, modelFile, resultsFile, ltl));

		}

		return experiments;
	}
}
