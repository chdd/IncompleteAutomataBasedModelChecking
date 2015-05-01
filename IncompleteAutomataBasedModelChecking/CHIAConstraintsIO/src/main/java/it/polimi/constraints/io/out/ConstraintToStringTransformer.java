package it.polimi.constraints.io.out;

import java.io.StringWriter;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Element;

import com.google.common.base.Preconditions;

import it.polimi.constraints.Constraint;

public class ConstraintToStringTransformer {

	/**
	 * transforms the constraint into the corresponding String representation
	 * 
	 * @param constraint
	 *            the constraint to be transformed
	 * @return the string representation of the constraint
	 * @throws Exception
	 *             if a problem occurs during the transformation
	 */
	public String transform(Constraint constraint) throws Exception {
		Preconditions.checkNotNull(constraint);
		Element constraintElement = new ConstraintToElementTransformer()
				.transform(constraint);

		TransformerFactory transformerFactory = TransformerFactory
				.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		DOMSource source = new DOMSource(constraintElement);

		StringWriter stringWriter = new StringWriter();
		StreamResult result = new StreamResult(stringWriter);
		transformer.transform(source, result);

		return stringWriter.toString();
	}
}