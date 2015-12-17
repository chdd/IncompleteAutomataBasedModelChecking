package it.polimi.automata.io.in;

import it.polimi.action.CHIAAction;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.w3c.dom.ls.LSInput;
import org.w3c.dom.ls.LSResourceResolver;
import org.xml.sax.SAXException;

public abstract class XMLReader<O> extends CHIAAction<O> {

	public XMLReader(String name) {
		super(name);
	}

	protected boolean validateAgainstXSD(InputStream xml, InputStream xsd)
			throws SAXException, IOException {
		SchemaFactory factory = SchemaFactory
				.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
		factory.setResourceResolver(new LSResourceResolver() {

			@Override
			public LSInput resolveResource(String type, String namespaceURI,
					String publicId, String systemId, String baseURI) {

				LSInputImpl input = new LSInputImpl();

				InputStream stream = ClassLoader.getSystemResourceAsStream(systemId);
				if(stream==null){
					throw new NullPointerException("The resource "+systemId+" cannot be founded");
				}
				input.setPublicId(publicId);
				input.setSystemId(systemId);
				input.setBaseURI(baseURI);
				input.setCharacterStream(new InputStreamReader(stream));
				return input;
			}
		});
		Schema schema = factory.newSchema(new StreamSource(xsd));
		Validator validator = schema.newValidator();
		validator.validate(new StreamSource(xml));

		return true;

	}
}
