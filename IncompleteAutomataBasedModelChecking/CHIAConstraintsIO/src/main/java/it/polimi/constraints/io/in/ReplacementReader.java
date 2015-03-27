package it.polimi.constraints.io.in;

import it.polimi.constraints.Replacement;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import com.google.common.base.Preconditions;

/**
 * The ReplacementReader loads the replacement of a transparent state of the
 * model from the specified XML file. The XML files must be conform with respect
 * to the XSD file Replacement.xsd. The ReplacementReader uses the
 * ElementToReplacement transformers which converts an XML element into the
 * corresponding JAVA object which uses the ElementToPort} transformers and
 * other transformers previously described, such as the ElementToIBA
 * transformer.
 * 
 * @author claudiomenghi
 *
 */
public class ReplacementReader {

	/**
	 * is the logger of the BAReader class
	 */
	private static final Logger logger = LoggerFactory
			.getLogger(ConstraintReader.class);

	private static final String REPLACEMENT_XSD_PATH="Replacement.xsd";
	
	private final File file;
	
	/**
	 * creates a new Replacement reader which can be used to read a Replacement
	 * automaton through the method
	 * @see Replacement#read()
	 * 
	 * @param filePath
	 *            is the path of the file from which the automaton must be read
	 * @throws NullPointerException
	 *             if one of the parameters is null
	 */
	public ReplacementReader(String filePath) {

		Preconditions.checkNotNull(filePath, "The fileReader cannot be null");
		this.file = new File(filePath);
	}

	/**
	 * creates a new constraint reader
	 * 
	 * @param f
	 *            is the file from which the constraint must be read
	 * @throws NullPointerException
	 *             if one of the parameters is null
	 */
	public ReplacementReader(File file) {
		Preconditions
				.checkNotNull(file,
						"The file from which the constraint must be read cannot be null");

		this.file = file;
	}

	public Replacement read() {

		Document dom;
		// Make an instance of the DocumentBuilderFactory
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		try {
			// use the factory to take an instance of the document builder
			DocumentBuilder db = dbf.newDocumentBuilder();
			if(this.getClass().getClassLoader()
					.getResource(REPLACEMENT_XSD_PATH)==null){
				throw new InternalError("It was not possible to load the BA.xsd from "+REPLACEMENT_XSD_PATH);
			}
			File xsd = new File(this.getClass().getClassLoader().getResource(REPLACEMENT_XSD_PATH).getFile());
			this.validateAgainstXSD(new FileInputStream(this.file), new FileInputStream(xsd));
			// parse using the builder to get the DOM mapping of the
			// XML file
			dom = db.parse(file);

			Element doc = dom.getDocumentElement();

			Replacement ret = this.loadRefinement(doc);
			return ret;

		} catch (ParserConfigurationException pce) {
			logger.error(pce.getMessage());
		} catch (SAXException se) {
			logger.error(se.getMessage());
		} catch (IOException ioe) {
			logger.error(ioe.getMessage());
		}

		return null;
	}

	private Replacement loadRefinement(Element doc) {
		Preconditions.checkNotNull(doc, "The document element cannot be null");

		Replacement refinement = new ElementToReplacementTransformer()
				.transform(doc);

		return refinement;

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
