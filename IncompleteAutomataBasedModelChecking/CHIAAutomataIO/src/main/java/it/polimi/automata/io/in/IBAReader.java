package it.polimi.automata.io.in;

import it.polimi.automata.IBA;

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
 * contains the reader which is used to read an <b>Incomplete</b> Buchi
 * automaton
 * 
 * @author claudiomenghi
 * 
 */
public class IBAReader extends XMLReader {

	private final static String NAME="READ MODEL";
	
	/**
	 * is the logger of the BAReader class
	 */
	private static final Logger logger = LoggerFactory
			.getLogger(IBAReader.class);
	/**
	 * is the File from which the IBA must be read
	 */
	private final File file;

	private static final String IBA_XSD_PATH = "IBA.xsd";

	/**
	 * creates a new IBAReader which reads the file from the specified path
	 * 
	 * @param filePath
	 *            is the path from which the file must be loaded
	 * @throws NullPointerException
	 *             if the path is null
	 */
	public IBAReader(String filePath) {
		super(NAME);
		Preconditions.checkNotNull(filePath,
				"The path of the file cannot be null");
		this.file = new File(filePath);
	}

	/**
	 * creates a new Buchi automaton reader which can be used to read a Buchi
	 * automaton through the method
	 * 
	 * @see BAReader#read()
	 * 
	 * @param file
	 *            is the reader from which the Buchi automaton must be loaded
	 * @throws NullPointerException
	 *             if one of the parameters is null
	 */
	public IBAReader(File file) {
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
	public IBA read() throws ParserConfigurationException,
			FileNotFoundException, SAXException, IOException {

		logger.info("Reding the Model");

		Document dom;

		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		
		if(this.getClass().getClassLoader()
				.getResource(IBA_XSD_PATH)==null){
			throw new InternalError("It was not possible to load the IBA.xsd from "+IBA_XSD_PATH);
		}
		File xsd = new File(this.getClass().getClassLoader()
				.getResource(IBA_XSD_PATH).getFile());
		this.validateAgainstXSD(new FileInputStream(this.file),
				new FileInputStream(xsd));

		// use the factory to take an instance of the document builder
		DocumentBuilder db = dbf.newDocumentBuilder();
		// parse using the builder to get the DOM mapping of the
		// XML file
		dom = db.parse(file);

		Element doc = dom.getDocumentElement();

		IBA iba = new IBAElementToTransformer().transform(doc);
		this.performed();
		logger.info("Model readed");

		return iba;
		

	}
}
