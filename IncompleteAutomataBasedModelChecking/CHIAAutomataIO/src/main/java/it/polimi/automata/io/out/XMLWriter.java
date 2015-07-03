package it.polimi.automata.io.out;

import java.io.File;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Element;

public class XMLWriter {

	private File file;
	private Element element;
	
	public XMLWriter(File file, Element element) {
		this.file = file;
		this.element = element;
	}

	public Void perform() {

		try {

			TransformerFactory transformerFactory = TransformerFactory
					.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(element);
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.setOutputProperty(OutputKeys.METHOD, "xml");
			transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
			
			StreamResult result = new StreamResult(file);
			transformer.transform(source, result);

			return null;

		} catch (TransformerException tfe) {
			tfe.printStackTrace();
		}
		return null;
	}
}
