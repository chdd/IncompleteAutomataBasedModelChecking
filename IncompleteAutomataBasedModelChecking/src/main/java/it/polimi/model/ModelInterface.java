package it.polimi.model;

import it.polimi.model.io.BuilderException;

import java.io.IOException;

import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

/**
 * contains the interface that the model of the application must implement
 * @author claudiomenghi
 *
 */
public interface ModelInterface {
	/**
	 * loads the model of the system from the file whose path is specified in the string modelFilePath
	 * @param modelFilePath is the path of the file from which the model must be loaded
	 * @throws JAXBException
	 * @throws SAXException
	 * @throws IOException
	 * @throws ParserConfigurationException
	 */
	public void changeModel(String modelFilePath) throws BuilderException;
	/**
	 * returns the model of the system
	 * @return the model of the system
	 */
	public IncompleteBuchiAutomaton<State, Transition<State>> getModel();
	/**
	 * returns the specification of the system
	 * @return the specification of the system
	 */
	public BuchiAutomaton<State, Transition<State>> getSpecification();
	/**
	 * returns the automaton that is intersection between the model and the specification
	 * @return the automaton that is the intersection between the model and the specification
	 */
	public IntersectionAutomaton<State, Transition<State>, IntersectionState<State>, Transition<IntersectionState<State>>> getIntersection();
	/**
	 * load the automaton from the specified string
	 * @param automatonXML contains the model of the system
	 * @throws JAXBException
	 */
	public void loadModelFromXML(String automatonXML) throws BuilderException;
	/**
	 * contains the specification of the system
	 * @param automatonXML contains the specification of the system
	 * @throws JAXBException
	 * @throws SAXException
	 * @throws IOException
	 * @throws ParserConfigurationException
	 */
	public void loadSpecificationFromXML(String automatonXML) throws BuilderException;
	/**
	 * save the model in the file with path filePath
	 * @param filePath
	 * @throws JAXBException
	 * @throws IOException
	 */
	public void saveModel(String filePath) throws BuilderException;
	/**
	 * load the model from the specified filePath
	 * @param specificationFilePath is the path of the file that contains the specification to be loaded
	 * @throws JAXBException
	 * @throws SAXException
	 * @throws IOException
	 * @throws ParserConfigurationException
	 */
	public void changeSpecification(String specificationFilePath) throws BuilderException;
	/**
	 * save the specification in the file with path filePath
	 * @param filePath is the path of the file where the specification must be saved
	 * @throws JAXBException
	 * @throws IOException
	 */
	public void saveSpecification(String filePath) throws BuilderException;
	
	/**
	 * adds the regular state s into the model
	 * @param s the state to be added
	 */
	public void addRegularStateToTheModel(State s, boolean regular, boolean initial, boolean accepting);
	/**
	 * adds the regular state s into the model
	 * @param s the state to be added
	 */
	public void addRegularStateToTheSpecification(State s, boolean initial, boolean accepting);
	
	public void addTransitionToTheModel(State source, State destination, String character);
	
	public void addTransitionToTheSpecification(State source, State destination, String character);
	
	public void addCharacterToTheModed(String character);
	public void addCharacterToTheSpecification(String character);

		
	
}
