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
import it.polimi.modelchecker.ModelCheckingResults;

import java.awt.geom.Point2D;
import java.io.IOException;
import java.util.Map;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.collections15.Transformer;
import org.xml.sax.SAXException;

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
	
	public void setSpecification(DrawableBA<CONSTRAINEDELEMENT, STATE, TRANSITION, TRANSITIONFACTORY> specification);
		
	public STATEFACTORY getSpecificationStateFactory();
	
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
	
	public TRANSITIONFACTORY getSpecificationTransitionFactory();
	
	/**
	 * creates a new claim
	 */
	public void newClaim();
	
	
	
	
	
	public void changeIntersection(DrawableIntBA<CONSTRAINEDELEMENT, STATE, TRANSITION, INTERSECTIONSTATE, INTERSECTIONTRANSITION,  INTERSECTIONTRANSITIONFACTORY> intersection);

	public void check();
	
	public ModelCheckingResults<CONSTRAINEDELEMENT, STATE, TRANSITION, INTERSECTIONSTATE, INTERSECTIONTRANSITION> getVerificationResults();
	
	public void setModel(DrawableIBA<CONSTRAINEDELEMENT, STATE, TRANSITION, TRANSITIONFACTORY> model);
	
	public void loadClaimFromLTL(String ltlFormula);
	
	public TRANSITIONFACTORY getModelTransitionFactory();
	
	public STATEFACTORY getModelStateFactory();
	
	public DefaultTreeModel getModelRefinementHierarchy();
	
	public void setModelRefinement(DefaultTreeModel modelRefinement);
	
	public Map<STATE, DefaultMutableTreeNode> getStateRefinementMap();
	public void setStateRefinementMap(Map<STATE, DefaultMutableTreeNode> stateRefinementMap);

	
	public DefaultTreeModel getflattenModelRefinement();
	public void setflattenModelRefinement(DefaultTreeModel flattenModel);
	
	public Map<STATE, DefaultMutableTreeNode> getFlatstateRefinementMap();
	public void setFlatstateRefinementMap(Map<STATE, DefaultMutableTreeNode> flatstateRefinementMap);	
	public void cleanFlatstateRefinementMap();
	
	public void resetModel(STATE rootState, DrawableIBA<CONSTRAINEDELEMENT, STATE, TRANSITION, TRANSITIONFACTORY> rootModel);
}
