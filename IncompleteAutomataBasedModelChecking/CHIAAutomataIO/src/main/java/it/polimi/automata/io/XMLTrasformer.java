package it.polimi.automata.io;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.google.common.base.Preconditions;

public abstract class XMLTrasformer<I, O extends Element> implements Transformer<I, O>{

	private final Document doc;
	
	public XMLTrasformer(Document doc){
		Preconditions.checkNotNull(doc, "The document cannot be null");
		this.doc=doc;
	}
	
	public XMLTrasformer() throws ParserConfigurationException{
		DocumentBuilderFactory docFactory = DocumentBuilderFactory
				.newInstance();
		DocumentBuilder docBuilder;
		docBuilder = docFactory.newDocumentBuilder();
		this.doc=docBuilder.newDocument();
	}
	
	public Document getDocument(){
		return this.doc;
	}
	
	
}
