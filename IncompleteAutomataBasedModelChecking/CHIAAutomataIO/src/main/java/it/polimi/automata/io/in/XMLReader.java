package it.polimi.automata.io.in;

import java.io.IOException;
import java.io.InputStream;

import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.xml.sax.SAXException;

import action.CHIAAction;

public abstract class XMLReader extends CHIAAction {

	public XMLReader(String name) {
		super(name);
	}

	protected boolean validateAgainstXSD(InputStream xml, InputStream xsd)
			throws SAXException, IOException {
		SchemaFactory factory = SchemaFactory
				.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
		Schema schema = factory.newSchema(new StreamSource(xsd));
		Validator validator = schema.newValidator();
		validator.validate(new StreamSource(xml));
		return true;

	}
}
