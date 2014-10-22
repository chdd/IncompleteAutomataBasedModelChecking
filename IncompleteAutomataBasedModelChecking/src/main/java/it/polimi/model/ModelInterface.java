package it.polimi.model;

import it.polimi.model.impl.automata.IntBAImpl;
import it.polimi.model.impl.states.IntersectionState;
import it.polimi.model.impl.states.State;
import it.polimi.model.impl.transitions.ConstrainedTransition;
import it.polimi.model.impl.transitions.LabelledTransition;
import it.polimi.model.interfaces.automata.drawable.DrawableBA;
import it.polimi.model.interfaces.automata.drawable.DrawableIBA;
import it.polimi.model.interfaces.transitions.ConstrainedTransitionFactoryInterface;
import it.polimi.model.interfaces.transitions.LabelledTransitionFactoryInterface;
import it.polimi.modelchecker.ModelCheckerParameters;

import java.io.IOException;

import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import edu.uci.ics.jung.io.GraphIOException;

/**
 * contains the interface that the model of the application must implement
 * @author claudiomenghi
 *
 */
public interface ModelInterface {
	/**
	 * loads the model of the system from the file whose path is specified in the string modelFilePath
	 * @param modelFilePath is the path of the file from which the model must be loaded
	 * @throws GraphIOException 
	 * @throws JAXBException
	 * @throws SAXException
	 * @throws IOException
	 * @throws ParserConfigurationException
	 */
	public void changeModel(String modelFilePath) throws IOException, GraphIOException;
	/**
	 * returns the model of the system
	 * @return the model of the system
	 */
	public DrawableIBA<State, LabelledTransition, LabelledTransitionFactoryInterface<LabelledTransition>> getModel();
	/**
	 * returns the specification of the system
	 * @return the specification of the system
	 */
	public DrawableBA<State, LabelledTransition, LabelledTransitionFactoryInterface<LabelledTransition>> getSpecification();
	/**
	 * returns the automaton that is intersection between the model and the specification
	 * @return the automaton that is the intersection between the model and the specification
	 */
	public IntBAImpl<State, LabelledTransition, IntersectionState<State>, ConstrainedTransition<State>,
	LabelledTransitionFactoryInterface<LabelledTransition>,
	ConstrainedTransitionFactoryInterface<State, ConstrainedTransition<State>>> getIntersection();
	
	/**
	 * save the model in the file with path filePath
	 * @param filePath
	 * @throws GraphIOException 
	 * @throws JAXBException
	 * @throws IOException
	 */
	public void saveModel(String filePath) throws IOException, GraphIOException;
	/**
	 * load the model from the specified filePath
	 * @param specificationFilePath is the path of the file that contains the specification to be loaded
	 * @throws GraphIOException 
	 * @throws JAXBException
	 * @throws SAXException
	 * @throws IOException
	 * @throws ParserConfigurationException
	 */
	public void changeSpecification(String specificationFilePath) throws IOException, GraphIOException;
	/**
	 * save the specification in the file with path filePath
	 * @param filePath is the path of the file where the specification must be saved
	 * @throws GraphIOException 
	 * @throws JAXBException
	 * @throws IOException
	 */
	public void saveSpecification(String filePath) throws IOException, GraphIOException;
	
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
	
	
	
	
	public void changeIntersection(IntBAImpl<State, LabelledTransition, IntersectionState<State>, ConstrainedTransition<State>,
			LabelledTransitionFactoryInterface<LabelledTransition>,
			ConstrainedTransitionFactoryInterface<State, ConstrainedTransition<State>>> intersection);

	public void check();
	
	public ModelCheckerParameters<State, IntersectionState<State>> getVerificationResults();
	
	public void loadClaim(String claim);
}
