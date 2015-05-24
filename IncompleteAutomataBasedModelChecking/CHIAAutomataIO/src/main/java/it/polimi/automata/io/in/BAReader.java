package it.polimi.automata.io.in;

import it.polimi.automata.BA;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.xml.bind.JAXBException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import com.google.common.base.Preconditions;

/**
 * The BAReader class is used to load a BA from a file. It is based on the
 * ElementToBATransformer which takes as input an XML element which contains the
 * XML representation of the BA and convert it into a BA object. The BA uses
 * three different transformers StringToPropositionTransformer,
 * ElementToBAStateTransformer and ElementToTransitionTransformer which are used
 * to transform a String into the corresponding set of propositions, and an XML
 * element into the corresponding state and transition, respectively.
 * 
 * @author claudiomenghi
 * 
 */
public class BAReader extends XMLReader{

	private final static String NAME="READ CLAIM";
	
	/**
	 * is the logger of the BAReader class
	 */
	private static final Logger logger = LoggerFactory
			.getLogger(BAReader.class);

	/**
	 * contains the file from which the Buchi automaton must be reader
	 */
	private final File file;

	private static final String BA_XSD_PATH = "BA.xsd";

	
	/**
	 * creates a new Buchi automaton reader which can be used to read a Buchi
	 * automaton through the method
	 * @see BAReader#read()
	 * 
	 * @param filePath
	 *            is the path of the file from which the automaton must be read
	 * @throws NullPointerException
	 *             if one of the parameters is null
	 */
	public BAReader(String filePath) {
		super(NAME);
		Preconditions.checkNotNull(filePath, "The fileReader cannot be null");
		this.file = new File(filePath);
	}
	
	/**
	 * creates a new Buchi automaton reader which can be used to read a Buchi
	 * automaton through the method
	 * 
	 * @see BAReader#read()
	 * 
	 * @param file
	 *            is the file from which the automaton must be read
	 * @throws NullPointerException
	 *             if one of the parameters is null
	 */
	public BAReader(File file) {
		super(NAME);
		Preconditions.checkNotNull(file, "The fileReader cannot be null");
		this.file = file;
	}

	/**
	 * read the Buchi Automaton from the reader
	 * 
	 * @return a new Buchi automaton which is parsed from the reader
	 * @throws ParserConfigurationException
	 * @throws IOException
	 * @throws SAXException
	 * @throws FileNotFoundException
	 * @throws JAXBException
	 * @throws GraphIOException
	 *             is generated if a problem occurs in the loading of the Buchi
	 *             Automaton
	 */
	public BA read() throws ParserConfigurationException,
			FileNotFoundException, SAXException, IOException {
		
		logger.info("Reding the Claim");

		Document dom;
		// Make an instance of the DocumentBuilderFactory
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		if(this.getClass().getClassLoader()
				.getResource(BA_XSD_PATH)==null){
			throw new InternalError("It was not possible to load the BA.xsd from "+BA_XSD_PATH);
		}
		File xsd = new File(this.getClass().getClassLoader().getResource(BA_XSD_PATH).getFile());
		this.validateAgainstXSD(new FileInputStream(this.file), new FileInputStream(xsd));

		// use the factory to take an instance of the document builder
		DocumentBuilder db = dbf.newDocumentBuilder();
		// parse using the builder to get the DOM mapping of the
		// XML file
		dom = db.parse(file);

		Element doc = dom.getDocumentElement();
		BA ba=new BAElementToTransformer().transform(doc);
		
		logger.info("Claim Readed");
		this.performed();

		return ba;
	}

	
}
