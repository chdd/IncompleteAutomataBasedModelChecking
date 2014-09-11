package it.polimi.model;

import java.io.ByteArrayInputStream;
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
@SuppressWarnings({ "unchecked", "rawtypes"})
public class AutomatonBuilder<S extends State, T extends Transition<S>, A extends BuchiAutomaton<S, T>> {
	

	/**
	 * loads the automaton from a file which contains the XML representation of the automaton
	 * @param type is the type of the automaton to be loaded
	 * @param filePath is the path of the file from which the automaton must be loaded
	 * @return the loaded automaton of type type
	 * @throws JAXBException
	 * @throws SAXException
	 * @throws IOException
	 * @throws ParserConfigurationException
	 */
	public A loadAutomaton(Class<? extends BuchiAutomaton> type, String filePath) throws JAXBException, SAXException, IOException, ParserConfigurationException{
		File file = new File(filePath);
		JAXBContext jaxbContext = JAXBContext.newInstance(type);
 
		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
		return (A) jaxbUnmarshaller.unmarshal(file);
	}
	
	/**
	 * loads the automaton from a String which contains the XML representation of the automaton
	 * @param type is the type of the automaton to be loaded
	 * @param automatonText contains the XML description of the automaton
	 * @return the loaded automaton of the specified type
	 * @throws JAXBException
	 */
	public A loadAutomatonFromText(Class<? extends BuchiAutomaton> type,  String automatonText) throws JAXBException{
		JAXBContext jaxbContext = JAXBContext.newInstance(type);
		 
		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
		return (A) jaxbUnmarshaller.unmarshal(new ByteArrayInputStream(automatonText.getBytes()));
	}
}
