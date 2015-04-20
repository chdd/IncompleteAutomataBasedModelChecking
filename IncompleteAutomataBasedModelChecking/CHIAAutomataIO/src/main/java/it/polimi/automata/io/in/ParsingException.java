package it.polimi.automata.io.in;

import java.io.StringWriter;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Element;

public class ParsingException extends RuntimeException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ParsingException(String exception){
		super(exception);
	}

	public static ParsingException createException(Element element) {
		try {

			TransformerFactory transFactory = TransformerFactory.newInstance();
			Transformer transformer;
			transformer = transFactory.newTransformer();
			StringWriter buffer = new StringWriter();
			transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION,
					"yes");
			transformer.transform(new DOMSource(element), new StreamResult(
					buffer));
			String str = buffer.toString();
			return new ParsingException(str);

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}
}
