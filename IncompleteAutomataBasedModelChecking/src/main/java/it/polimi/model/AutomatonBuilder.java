package it.polimi.model;

import java.io.File;
import java.io.IOException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

/**
 * @author claudiomenghi
 * This class implements the Builder pattern which allows to separate the construction of the automata from their representations. 
 * The same construction process can be used to create different implementations.
 */
public class AutomatonBuilder<S extends State, T extends Transition<S>, A extends BuchiAutomaton<S, T>> {
	

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public A loadAutomaton(Class<? extends BuchiAutomaton> type, String filePath) throws JAXBException, SAXException, IOException, ParserConfigurationException{
		A a;
		File file = new File(filePath);
		JAXBContext jaxbContext = JAXBContext.newInstance(type);
 
		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
		a = (A) jaxbUnmarshaller.unmarshal(file);
		return a;
	}
}
