package it.polimi.automata.io.out;

import java.io.StringWriter;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Element;

import com.google.common.base.Preconditions;

public class ElementToStringTransformer {

	/**
	 * transforms the constraint into the corresponding String representation
	 * 
	 * @param constraint
	 *            the constraint to be transformed
	 * @return the string representation of the constraint
	 * @throws Exception
	 *             if a problem occurs during the transformation
	 */
	public String transform(Element element) throws Exception {
		Preconditions.checkNotNull(element);
		
		TransformerFactory transformerFactory = TransformerFactory
				.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		DOMSource source = new DOMSource(element);
		transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		transformer.setOutputProperty(OutputKeys.METHOD, "xml");
		transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");

		StringWriter stringWriter = new StringWriter();
		StreamResult result = new StreamResult(stringWriter);
		transformer.transform(source, result);

		return stringWriter.toString();
	}
}
