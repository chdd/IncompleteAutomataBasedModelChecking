package it.polimi.model;

import it.polimi.model.impl.states.IntersectionState;
import it.polimi.model.impl.states.IntersectionStateFactory;
import it.polimi.model.impl.states.State;
import it.polimi.model.impl.states.StateFactory;
import it.polimi.model.impl.transitions.LabelledTransition;
import it.polimi.model.interfaces.automata.drawable.DrawableBA;
import it.polimi.model.interfaces.automata.drawable.DrawableIBA;
import it.polimi.model.interfaces.automata.drawable.DrawableIntBA;
import it.polimi.model.interfaces.transitions.ConstrainedTransitionFactory;
import it.polimi.model.interfaces.transitions.LabelledTransitionFactory;
import it.polimi.modelchecker.ModelCheckerParameters;

import java.awt.geom.Point2D;
import java.io.IOException;

import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.collections15.Transformer;
import org.xml.sax.SAXException;

import edu.uci.ics.jung.algorithms.layout.AbstractLayout;
import edu.uci.ics.jung.io.GraphIOException;

/**
 * contains the interface that the model of the application must implement
 * @author claudiomenghi
 *
 */
public interface ModelInterface<
	CONSTRAINEDELEMENT extends State,
	STATE extends State,
	STATEFACTORY extends StateFactory<STATE>,
	TRANSITION extends LabelledTransition<CONSTRAINEDELEMENT>,
	TRANSITIONFACTORY extends LabelledTransitionFactory<CONSTRAINEDELEMENT, TRANSITION>,
	INTERSECTIONSTATE extends IntersectionState<STATE>,
	INTERSECTIONSTATEFACTORY extends IntersectionStateFactory<STATE, INTERSECTIONSTATE>,
	INTERSECTIONTRANSITION extends LabelledTransition<CONSTRAINEDELEMENT>,
	INTERSECTIONTRANSITIONFACTORY extends ConstrainedTransitionFactory<CONSTRAINEDELEMENT, INTERSECTIONTRANSITION>>
	{
	/**
	 * loads the model of the system from the file whose path is specified in the string modelFilePath
	 * @param modelFilePath is the path of the file from which the model must be loaded
	 * @throws GraphIOException 
	 * @throws JAXBException
	 * @throws SAXException
	 * @throws IOException
	 * @throws ParserConfigurationException
	 */
	public Transformer<STATE, Point2D> loadModel(String modelFilePath) throws IOException, GraphIOException;
	
	/**
	 * loads the model of the system from the file whose path is specified in the string modelFilePath
	 * @param modelFilePath is the path of the file from which the model must be loaded
	 * @throws GraphIOException 
	 * @throws JAXBException
	 * @throws SAXException
	 * @throws IOException
	 * @throws ParserConfigurationException
	 */
	public Transformer<INTERSECTIONSTATE, Point2D> loadIntersection(String modelFilePath) throws IOException, GraphIOException;
	
	/**
	 * returns the model of the system
	 * @return the model of the system
	 */
	public DrawableIBA<CONSTRAINEDELEMENT, STATE, TRANSITION, TRANSITIONFACTORY> getModel();
	/**
	 * returns the specification of the system
	 * @return the specification of the system
	 */
	public DrawableBA<CONSTRAINEDELEMENT, STATE, TRANSITION, TRANSITIONFACTORY> getSpecification();
	/**
	 * returns the automaton that is intersection between the model and the specification
	 * @return the automaton that is the intersection between the model and the specification
	 */
	public DrawableIntBA<CONSTRAINEDELEMENT, STATE, TRANSITION, INTERSECTIONSTATE, INTERSECTIONTRANSITION,  INTERSECTIONTRANSITIONFACTORY> getIntersection();
	
	/**
	 * save the model in the file with path filePath
	 * @param filePath
	 * @throws GraphIOException 
	 * @throws JAXBException
	 * @throws IOException
	 */
	public void saveModel(String filePath, AbstractLayout<STATE, TRANSITION> layout) throws IOException, GraphIOException;
	/**
	 * load the model from the specified filePath
	 * @param specificationFilePath is the path of the file that contains the specification to be loaded
	 * @throws GraphIOException 
	 * @throws JAXBException
	 * @throws SAXException
	 * @throws IOException
	 * @throws ParserConfigurationException
	 */
	public Transformer<STATE, Point2D> changeSpecification(String specificationFilePath) throws IOException, GraphIOException;
	/**
	 * save the specification in the file with path filePath
	 * @param filePath is the path of the file where the specification must be saved
	 * @throws GraphIOException 
	 * @throws JAXBException
	 * @throws IOException
	 */
	public void saveSpecification(String filePath, AbstractLayout<STATE, TRANSITION> layout) throws IOException, GraphIOException;
	
	/**
	 * save the intersection in the file with path filePath
	 * @param filePath is the path of the file where the specification must be saved
	 * @throws GraphIOException 
	 * @throws JAXBException
	 * @throws IOException
	 */
	public void saveIntersection(String filePath, AbstractLayout<INTERSECTIONSTATE, INTERSECTIONTRANSITION>  layout) throws IOException, GraphIOException;
	
	
	/**
	 * adds the regular state s into the model
	 * @param s the state to be added
	 */
	public void addRegularStateToTheModel(STATE s, boolean regular, boolean initial, boolean accepting);
	/**
	 * adds the regular state s into the model
	 * @param s the state to be added
	 */
	public void addRegularStateToTheSpecification(STATE s, boolean initial, boolean accepting);
	
	/**
	 * creates a new model 
	 */
	public void newModel();
	
	/**
	 * creates a new claim
	 */
	public void newClaim();
	
	
	
	
	
	public void changeIntersection(DrawableIntBA<CONSTRAINEDELEMENT, STATE, TRANSITION, INTERSECTIONSTATE, INTERSECTIONTRANSITION,  INTERSECTIONTRANSITIONFACTORY> intersection);

	public void check();
	
	public ModelCheckerParameters<CONSTRAINEDELEMENT, STATE, TRANSITION, INTERSECTIONSTATE, INTERSECTIONTRANSITION> getVerificationResults();
	
	public void loadClaimFromLTL(String ltlFormula);
}
