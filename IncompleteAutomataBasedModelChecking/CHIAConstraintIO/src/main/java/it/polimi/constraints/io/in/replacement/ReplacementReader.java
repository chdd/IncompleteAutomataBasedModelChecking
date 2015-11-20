package it.polimi.constraints.io.in.replacement;

import it.polimi.automata.io.in.XMLReader;
import it.polimi.constraints.components.Replacement;
import it.polimi.constraints.io.ConstraintsIOConstants;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import com.google.common.base.Preconditions;

/**
 * The ReplacementReader loads the replacement of a black box state of the model
 * from the specified XML file. The XML files must be conform with respect to
 * the XSD file Replacement.xsd. The ReplacementReader uses the
 * ElementToReplacement transformers which converts an XML element into the
 * corresponding JAVA object which uses the ElementToPort} transformers and
 * other transformers previously described, such as the ElementToIBA
 * transformer.
 * 
 * @author claudiomenghi
 *
 */
public class ReplacementReader extends XMLReader<Replacement> {

	private final static String NAME = "READ REPLACEMENT";

	/**
	 * is the logger of the BAReader class
	 */
	private static final Logger logger = Logger
			.getLogger(ReplacementReader.class);
	private final File file;

	/**
	 * creates a new constraint reader
	 * 
	 * @param f
	 *            is the file from which the constraint must be read
	 * @throws NullPointerException
	 *             if one of the parameters is null
	 */
	public ReplacementReader(File file) {
		super(NAME);
		Preconditions
				.checkNotNull(file,
						"The file from which the constraint must be read cannot be null");

		this.file = file;
	}

	public Replacement perform() throws FileNotFoundException, SAXException,
			IOException, ParserConfigurationException {

		logger.debug("reading the replacement");
		Document dom;
		// Make an instance of the DocumentBuilderFactory
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		// use the factory to take an instance of the document builder
		DocumentBuilder db = dbf.newDocumentBuilder();
		this.validateAgainstXSD(
				new FileInputStream(this.file),
				ClassLoader.getSystemResourceAsStream(ConstraintsIOConstants.REPLACEMENT_XSD_PATH));
		// parse using the builder to get the DOM mapping of the
		// XML file
		dom = db.parse(file);

		Element doc = dom.getDocumentElement();

		Replacement ret = this.loadReplacement(doc);

		logger.debug("replacement loaded");
		return ret;

	}

	private Replacement loadReplacement(Element doc) {
		Preconditions.checkNotNull(doc, "The document element cannot be null");

		Replacement replacement = new ElementToReplacementTransformer()
				.transform(doc);

		return replacement;

	}
}
