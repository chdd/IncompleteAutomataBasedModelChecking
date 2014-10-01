package it.polimi.model.io;

import it.polimi.model.automata.ba.BuchiAutomaton;
import it.polimi.model.automata.ba.LabelledTransition;
import it.polimi.model.graph.State;

import java.io.ByteArrayInputStream;
import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

/**
 * @author claudiomenghi
 * This class implements the singleton pattern to ensure that the class only has one instance, 
 * and provide a global point of access to it.
 * The same construction process can be used to create different implementations.
 */
@SuppressWarnings({ "unchecked", "rawtypes"})
public class AutomatonBuilder<S extends State, T extends LabelledTransition<S>, A extends BuchiAutomaton<S, T>> {
	

	/**
	 * loads the automaton from a file which contains the XML representation of the automaton
	 * @param type is the type of the automaton to be loaded
	 * @param filePath is the path of the file from which the automaton must be loaded
	 * @return the loaded automaton of type type
	 * @throws BuilderException is generated if problems are detected in the loading of the automaton
	 */
	public A loadAutomaton(Class<? extends BuchiAutomaton> type, String filePath) throws BuilderException  {
		
		File file = new File(filePath);
		JAXBContext jaxbContext;
		try {
			jaxbContext = JAXBContext.newInstance(type);
		} catch (JAXBException e) {
			throw new BuilderException("JAXB loading problem, check that the dtd is in the same folder of the xml file");
		}
		Unmarshaller jaxbUnmarshaller;
		try {
			jaxbUnmarshaller = jaxbContext.createUnmarshaller();
		} catch (JAXBException e) {
			throw new BuilderException("JAXB loading problem, check that your  dtd is in the same folder of the xml file");
		}
		try {
			return (A) jaxbUnmarshaller.unmarshal(file);
		} catch (JAXBException e) {
			throw new BuilderException("JAXB loading problem, check that your  dtd is in the same folder of the xml file");
		}
	}
	
	/**
	 * loads the automaton from a String which contains the XML representation of the automaton
	 * @param type is the type of the automaton to be loaded
	 * @param automatonText contains the XML description of the automaton
	 * @return the loaded automaton of the specified type
	 * @throws JAXBException
	 */
	public A loadAutomatonFromText(Class<? extends BuchiAutomaton> type,  String automatonText) throws BuilderException{
		JAXBContext jaxbContext=null;
		try {
			jaxbContext = JAXBContext.newInstance(type);
		} catch (JAXBException e) {
			new BuilderException(e.toString());
		}
		 
		Unmarshaller jaxbUnmarshaller=null;
		try {
			jaxbUnmarshaller = jaxbContext.createUnmarshaller();
		} catch (JAXBException e) {
			new BuilderException(e.toString());
		}
		try {
			return (A) jaxbUnmarshaller.unmarshal(new ByteArrayInputStream(automatonText.getBytes()));
		} catch (JAXBException e) {
			new BuilderException(e.toString());
		}
		return null;
	}
}
