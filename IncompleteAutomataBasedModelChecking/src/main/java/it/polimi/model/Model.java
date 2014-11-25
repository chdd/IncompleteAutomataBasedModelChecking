package it.polimi.model;

import it.polimi.io.IntBAReader;
import it.polimi.model.impl.automata.BAImpl;
import it.polimi.model.impl.automata.IBAImpl;
import it.polimi.model.impl.automata.IntBAFactoryImpl;
import it.polimi.model.impl.automata.IntBAImpl;
import it.polimi.model.impl.states.IntersectionState;
import it.polimi.model.impl.states.State;
import it.polimi.model.impl.transitions.Transition;
import it.polimi.model.interfaces.automata.BA;
import it.polimi.model.interfaces.automata.IBA;
import it.polimi.model.interfaces.automata.IIntBA;
import it.polimi.model.interfaces.automata.IntBAFactory;
import it.polimi.model.interfaces.states.IntersectionStateFactory;
import it.polimi.model.interfaces.states.StateFactory;
import it.polimi.model.interfaces.transitions.ConstrainedTransitionFactory;
import it.polimi.model.interfaces.transitions.TransitionFactory;
import it.polimi.model.ltltoba.LTLtoBATransformer;
import it.polimi.modelchecker.ModelChecker;
import it.polimi.modelchecker.ModelCheckingResults;

import java.awt.geom.Point2D;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import org.apache.commons.collections15.Transformer;

import edu.uci.ics.jung.io.GraphIOException;

/**
 * contains the model of the application: the model of the system, its specification and the intersection between the model and the specification
 * @author claudiomenghi
 */
public class Model<
	CONSTRAINEDELEMENT extends State,
	STATE extends State,
	TRANSITION extends Transition,
	INTERSECTIONSTATE extends IntersectionState<STATE>,
	INTERSECTIONTRANSITION extends Transition>
	implements ModelInterface<CONSTRAINEDELEMENT, STATE,  TRANSITION,  INTERSECTIONSTATE,  INTERSECTIONTRANSITION>{

	/**
	 * contains the model of the system
	 */
	private IBA<STATE, TRANSITION> model;
	
	/**
	 * contains the specification o the system
	 */
	private BA<STATE, TRANSITION>  specification;
	
	/**
	 * contains the intersection between the model and the specification
	 */
	private IIntBA<STATE, TRANSITION, INTERSECTIONSTATE, INTERSECTIONTRANSITION> intersection;
	
	private DefaultTreeModel modelRefinement;
	private DefaultTreeModel modelflattenModel;
	
	private Map<STATE, DefaultMutableTreeNode> modelstateRefinementMap;
	private Map<STATE, DefaultMutableTreeNode> modelflatstateRefinementMap;
	
	
	public void resetModel(STATE rootState, IBA<STATE, TRANSITION> rootModel){
		this.model=rootModel;
		DefaultMutableTreeNode rootnode=new DefaultMutableTreeNode(new
				RefinementNode< STATE, TRANSITION> (rootState, rootModel));
		
		this.modelRefinement.setRoot(rootnode);
		this.modelstateRefinementMap.put(rootState, rootnode);
		this.modelflatstateRefinementMap=new HashMap<STATE, DefaultMutableTreeNode>();
	}
	
	private IntersectionStateFactory<STATE, INTERSECTIONSTATE> intersectionStateFactory;
	
	private ConstrainedTransitionFactory<CONSTRAINEDELEMENT, INTERSECTIONTRANSITION>  intersectionTransitionFactory;
	
	private TransitionFactory<TRANSITION> transitionFactory;
	
	private StateFactory<STATE> stateFactory;
	
	
	
	
	
	private IntBAReader<
		CONSTRAINEDELEMENT,
		STATE, 
		TRANSITION, 
		INTERSECTIONSTATE, 
		INTERSECTIONTRANSITION, 
		IIntBA<STATE,TRANSITION,INTERSECTIONSTATE,INTERSECTIONTRANSITION>, 
		IntBAFactory<STATE,TRANSITION, INTERSECTIONSTATE,
			INTERSECTIONTRANSITION,
		IIntBA<STATE, TRANSITION, INTERSECTIONSTATE, INTERSECTIONTRANSITION>>> intBaReader;
	
	
	private ModelCheckingResults<STATE, TRANSITION, INTERSECTIONSTATE, INTERSECTIONTRANSITION> mp=new ModelCheckingResults<STATE, TRANSITION, INTERSECTIONSTATE, INTERSECTIONTRANSITION>();
	
	
	public Model(StateFactory<STATE> stateFactory, TransitionFactory<TRANSITION> transitionFactory, IntersectionStateFactory<STATE, INTERSECTIONSTATE> intersectionStateFactory, ConstrainedTransitionFactory<CONSTRAINEDELEMENT, INTERSECTIONTRANSITION> intersectionTransitionFactory){
		this.setStateRefinementMap(new HashMap<STATE, DefaultMutableTreeNode>());
		this.setFlatstateRefinementMap(new HashMap<STATE, DefaultMutableTreeNode>());
		this.stateFactory=stateFactory;
		this.transitionFactory=transitionFactory;
		this.intersectionStateFactory=intersectionStateFactory;
		this.intersectionTransitionFactory=intersectionTransitionFactory;
		this.model=new IBAImpl<STATE, TRANSITION>(transitionFactory, this.stateFactory);
		this.specification=new BAImpl<STATE, TRANSITION>(transitionFactory, this.stateFactory);
		this.intersection=new IntBAImpl<STATE, TRANSITION, INTERSECTIONSTATE, INTERSECTIONTRANSITION>(this.intersectionTransitionFactory, this.intersectionStateFactory);
		
		STATE s=this.stateFactory.create("Model");
		DefaultMutableTreeNode rootnode=new DefaultMutableTreeNode(new
				RefinementNode< STATE, TRANSITION> (s, this.model));
		this.modelRefinement=new DefaultTreeModel(rootnode);
		this.modelstateRefinementMap.put(s, rootnode);
	}
	
	
	
	public void setSpecification(BA<STATE, TRANSITION> specification){
		this.specification=specification;
	}
	public void setModel(IBA<STATE, TRANSITION> model){
		this.model=model;
	}
	
	
	/**
	 * @see {@link ModelInterface}
	 */
	@Override
	public IBA<STATE, TRANSITION> getModel(){
		return this.model;
	}
	
	
	
	
	/**
	 * @see {@link ModelInterface}
	 */
	@Override
	public BA<STATE, TRANSITION> getSpecification(){
		return this.specification;
	}
	/**
	 * @see {@link ModelInterface}
	 */
	@Override
	public IIntBA<STATE, TRANSITION, INTERSECTIONSTATE, INTERSECTIONTRANSITION> getIntersection(){
		return this.intersection;
	}
	
	@Override
	public void addRegularStateToTheModel(STATE s, boolean regular, boolean initial, boolean accepting) {
		if(!regular){
			this.model.addTransparentState(s);
		}
		if(initial){
			this.model.addInitialState(s);
		}
		if(accepting){
			this.model.addAcceptState(s);
		}
		this.model.addState(s);
	}
	
	@Override
	public void addRegularStateToTheSpecification(STATE s, boolean initial, boolean accepting) {
		if(initial){
			this.specification.addInitialState(s);
		}
		if(accepting){
			this.specification.addAcceptState(s);
		}
		this.specification.addState(s);
	}
	
	public void changeIntersection(IIntBA<STATE, TRANSITION, INTERSECTIONSTATE, INTERSECTIONTRANSITION> intersection){
		this.intersection=intersection;
	}
	
	
	@Override
	public void loadClaimFromLTL(String ltlFormula){
		LTLtoBATransformer<STATE,  TRANSITION> ltltoBa=new LTLtoBATransformer<STATE,  TRANSITION>(this.stateFactory, this.transitionFactory);
		this.specification=ltltoBa.transform(ltlFormula);
	}
	
	@Override
	public void check(){
		mp=new ModelCheckingResults<STATE, TRANSITION, INTERSECTIONSTATE, INTERSECTIONTRANSITION>();
		
		ModelChecker< STATE, TRANSITION,  INTERSECTIONSTATE,INTERSECTIONTRANSITION> mc=new 
		ModelChecker<STATE, TRANSITION, INTERSECTIONSTATE, INTERSECTIONTRANSITION>(this.getModel(), this.getSpecification(), mp);
		mc.check();
		this.changeIntersection(mc.getIntersection());
	}
	
	public ModelCheckingResults<STATE, TRANSITION, INTERSECTIONSTATE, INTERSECTIONTRANSITION> getVerificationResults(){
		return this.mp;
	}
	
	@Override
	public Transformer<INTERSECTIONSTATE, Point2D> loadIntersection(String intersectionPath) throws IOException,
			GraphIOException {
		
		this.intBaReader=
				new IntBAReader<
				CONSTRAINEDELEMENT,
				STATE, 
				TRANSITION, 
				INTERSECTIONSTATE, 
				INTERSECTIONTRANSITION, 
				IIntBA<STATE,TRANSITION,INTERSECTIONSTATE,INTERSECTIONTRANSITION>, 
				IntBAFactory<STATE,TRANSITION, INTERSECTIONSTATE,
					INTERSECTIONTRANSITION, IIntBA<STATE, TRANSITION, INTERSECTIONSTATE, INTERSECTIONTRANSITION>>>(
						this.intersectionTransitionFactory, 
						this.intersectionStateFactory, 
						new  IntBAFactoryImpl<CONSTRAINEDELEMENT, STATE,TRANSITION,  INTERSECTIONSTATE, INTERSECTIONTRANSITION>(this.intersectionTransitionFactory, 	this.intersectionStateFactory)
						, new BufferedReader(new FileReader(intersectionPath))
				
				);
		this.intersection=this.intBaReader.readGraph();
		return this.intBaReader.getStatePositionTransformer();
	}

	@Override
	public void newModel() {
		this.model=new IBAImpl<STATE, TRANSITION>(this.transitionFactory, this.stateFactory);
	}

	@Override
	public void newClaim() {
		this.specification=new BAImpl<STATE, TRANSITION>(this.transitionFactory, this.stateFactory);
	}

	/**
	 * @return the modelRefinement
	 */
	public DefaultTreeModel getModelRefinementHierarchy() {
		return modelRefinement;
	}

	/**
	 * @param modelRefinement the modelRefinement to set
	 */
	public void setModelRefinement(DefaultTreeModel modelRefinement) {
		this.modelRefinement = modelRefinement;
	}
	
	/**
	 * @return the modelRefinement
	 */
	public DefaultTreeModel getflattenModelRefinement() {
		return this.modelflattenModel;
	}

	/**
	 * @param modelRefinement the modelRefinement to set
	 */
	public void setflattenModelRefinement(DefaultTreeModel flattenModel) {
		this.modelflattenModel = flattenModel;
	}

	

	/**
	 * @return the stateRefinementMap
	 */
	public Map<STATE, DefaultMutableTreeNode> getStateRefinementMap() {
		return modelstateRefinementMap;
	}

	/**
	 * @param stateRefinementMap the stateRefinementMap to set
	 */
	public void setStateRefinementMap(Map<STATE, DefaultMutableTreeNode> stateRefinementMap) {
		this.modelstateRefinementMap = stateRefinementMap;
	}

	/**
	 * @return the flatstateRefinementMap
	 */
	public Map<STATE, DefaultMutableTreeNode> getFlatstateRefinementMap() {
		return modelflatstateRefinementMap;
	}

	/**
	 * @param flatstateRefinementMap the flatstateRefinementMap to set
	 */
	public void setFlatstateRefinementMap(Map<STATE, DefaultMutableTreeNode> flatstateRefinementMap) {
		this.modelflatstateRefinementMap = flatstateRefinementMap;
	}
	
	public void cleanFlatstateRefinementMap(){
		this.modelflatstateRefinementMap=new HashMap<STATE, DefaultMutableTreeNode>();
	}
}
