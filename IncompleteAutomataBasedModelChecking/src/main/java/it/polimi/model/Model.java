package it.polimi.model;

import it.polimi.io.IntBAReader;
import it.polimi.model.impl.automata.BAImpl;
import it.polimi.model.impl.automata.IBAImpl;
import it.polimi.model.impl.automata.IntBAFactoryImpl;
import it.polimi.model.impl.automata.IntBAImpl;
import it.polimi.model.impl.states.IntersectionState;
import it.polimi.model.impl.states.IntersectionStateFactory;
import it.polimi.model.impl.states.State;
import it.polimi.model.impl.states.StateFactory;
import it.polimi.model.impl.transitions.LabelledTransition;
import it.polimi.model.interfaces.automata.IntBAFactory;
import it.polimi.model.interfaces.automata.drawable.DrawableBA;
import it.polimi.model.interfaces.automata.drawable.DrawableIBA;
import it.polimi.model.interfaces.automata.drawable.DrawableIntBA;
import it.polimi.model.interfaces.transitions.ConstrainedTransitionFactory;
import it.polimi.model.interfaces.transitions.LabelledTransitionFactory;
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
	STATEFACTORY extends StateFactory<STATE>,
	TRANSITION extends LabelledTransition<CONSTRAINEDELEMENT>,
	TRANSITIONFACTORY extends LabelledTransitionFactory<CONSTRAINEDELEMENT, TRANSITION>,
	INTERSECTIONSTATE extends IntersectionState<STATE>,
	INTERSECTIONSTATEFACTORY extends IntersectionStateFactory<STATE, INTERSECTIONSTATE>,
	INTERSECTIONTRANSITION extends LabelledTransition<CONSTRAINEDELEMENT>,
	INTERSECTIONTRANSITIONFACTORY extends ConstrainedTransitionFactory<CONSTRAINEDELEMENT, INTERSECTIONTRANSITION>>
	implements ModelInterface<CONSTRAINEDELEMENT, STATE, STATEFACTORY, TRANSITION, TRANSITIONFACTORY, INTERSECTIONSTATE, INTERSECTIONSTATEFACTORY, INTERSECTIONTRANSITION, INTERSECTIONTRANSITIONFACTORY>{

	/**
	 * contains the model of the system
	 */
	private DrawableIBA<CONSTRAINEDELEMENT, STATE, TRANSITION, TRANSITIONFACTORY> model;
	
	private DefaultTreeModel modelRefinement;
	private DefaultTreeModel modelflattenModel;
	
	private Map<STATE, DefaultMutableTreeNode> modelstateRefinementMap;
	private Map<STATE, DefaultMutableTreeNode> modelflatstateRefinementMap;
	
	
	public void resetModel(STATE rootState, DrawableIBA<CONSTRAINEDELEMENT, STATE, TRANSITION, TRANSITIONFACTORY> rootModel){
		this.model=rootModel;
		DefaultMutableTreeNode rootnode=new DefaultMutableTreeNode(new
				RefinementNode<CONSTRAINEDELEMENT, STATE, TRANSITION, TRANSITIONFACTORY> (rootState, rootModel));
		
		this.modelRefinement.setRoot(rootnode);
		this.modelstateRefinementMap.put(rootState, rootnode);
		this.modelflatstateRefinementMap=new HashMap<STATE, DefaultMutableTreeNode>();
	}
	
	/**
	 * contains the specification o the system
	 */
	private DrawableBA<CONSTRAINEDELEMENT, STATE, TRANSITION, TRANSITIONFACTORY>  specification;
	
	
	/**
	 * contains the intersection between the model and the specification
	 */
	private DrawableIntBA<CONSTRAINEDELEMENT, STATE, TRANSITION, INTERSECTIONSTATE, INTERSECTIONTRANSITION,  INTERSECTIONTRANSITIONFACTORY> intersection;
	
	private INTERSECTIONSTATEFACTORY intersectionStateFactory;
	
	private INTERSECTIONTRANSITIONFACTORY intersectionTransitionFactory;
	private TRANSITIONFACTORY transitionFactory;
	
	private STATEFACTORY stateFactory;
	
	
	
	public TRANSITIONFACTORY getSpecificationTransitionFactory(){
		return this.transitionFactory;
	}
	
	public STATEFACTORY getSpecificationStateFactory(){
		return this.stateFactory;
	}
	
	
	public TRANSITIONFACTORY getModelTransitionFactory(){
		return this.transitionFactory;
	}
	
	public STATEFACTORY getModelStateFactory(){
		return this.stateFactory;
	}
	
	
	private IntBAReader<
		CONSTRAINEDELEMENT,
		STATE, 
		TRANSITION, 
		INTERSECTIONSTATE, 
		INTERSECTIONSTATEFACTORY, 
		INTERSECTIONTRANSITION, 
		TRANSITIONFACTORY, 
		INTERSECTIONTRANSITIONFACTORY, 
		DrawableIntBA<CONSTRAINEDELEMENT,STATE,TRANSITION,INTERSECTIONSTATE,INTERSECTIONTRANSITION, INTERSECTIONTRANSITIONFACTORY>, 
		IntBAFactory<CONSTRAINEDELEMENT,STATE,TRANSITION ,TRANSITIONFACTORY, INTERSECTIONSTATE,
			INTERSECTIONSTATEFACTORY,
			INTERSECTIONTRANSITION,
			INTERSECTIONTRANSITIONFACTORY,
		DrawableIntBA<CONSTRAINEDELEMENT,STATE, TRANSITION, INTERSECTIONSTATE, INTERSECTIONTRANSITION, INTERSECTIONTRANSITIONFACTORY>>> intBaReader;
	
	
	private ModelCheckingResults<CONSTRAINEDELEMENT,STATE, TRANSITION, INTERSECTIONSTATE, INTERSECTIONTRANSITION> mp=new ModelCheckingResults<CONSTRAINEDELEMENT,STATE, TRANSITION, INTERSECTIONSTATE, INTERSECTIONTRANSITION>();
	
	
	public Model(STATEFACTORY stateFactory, TRANSITIONFACTORY transitionFactory, INTERSECTIONSTATEFACTORY intersectionStateFactory, INTERSECTIONTRANSITIONFACTORY intersectionTransitionFactory){
		this.setStateRefinementMap(new HashMap<STATE, DefaultMutableTreeNode>());
		this.setFlatstateRefinementMap(new HashMap<STATE, DefaultMutableTreeNode>());
		this.stateFactory=stateFactory;
		this.transitionFactory=transitionFactory;
		this.intersectionStateFactory=intersectionStateFactory;
		this.intersectionTransitionFactory=intersectionTransitionFactory;
		this.model=new IBAImpl<CONSTRAINEDELEMENT,STATE, TRANSITION,  TRANSITIONFACTORY>(transitionFactory);
		this.specification=new BAImpl<CONSTRAINEDELEMENT,STATE, TRANSITION, TRANSITIONFACTORY>(transitionFactory);
		this.intersection=new IntBAImpl<CONSTRAINEDELEMENT,STATE, TRANSITION, INTERSECTIONSTATE, INTERSECTIONTRANSITION,
		TRANSITIONFACTORY,
		INTERSECTIONTRANSITIONFACTORY>(this.model, this.specification,intersectionTransitionFactory);
		
		STATE s=this.stateFactory.create("Model");
		DefaultMutableTreeNode rootnode=new DefaultMutableTreeNode(new
				RefinementNode<CONSTRAINEDELEMENT, STATE, TRANSITION, TRANSITIONFACTORY> (s, this.model));
		this.modelRefinement=new DefaultTreeModel(rootnode);
		this.modelstateRefinementMap.put(s, rootnode);
	}
	
	
	
	public void setSpecification(DrawableBA<CONSTRAINEDELEMENT, STATE, TRANSITION, TRANSITIONFACTORY> specification){
		this.specification=specification;
	}
	public void setModel(DrawableIBA<CONSTRAINEDELEMENT, STATE, TRANSITION, TRANSITIONFACTORY> model){
		this.model=model;
	}
	
	
	/**
	 * @see {@link ModelInterface}
	 */
	@Override
	public DrawableIBA<CONSTRAINEDELEMENT,STATE, TRANSITION, TRANSITIONFACTORY> getModel(){
		return this.model;
	}
	
	
	
	
	/**
	 * @see {@link ModelInterface}
	 */
	@Override
	public DrawableBA<CONSTRAINEDELEMENT,STATE, TRANSITION, TRANSITIONFACTORY> getSpecification(){
		return this.specification;
	}
	/**
	 * @see {@link ModelInterface}
	 */
	@Override
	public DrawableIntBA<CONSTRAINEDELEMENT,STATE, TRANSITION, INTERSECTIONSTATE, INTERSECTIONTRANSITION,  INTERSECTIONTRANSITIONFACTORY> getIntersection(){
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
		this.model.addVertex(s);
	}
	
	@Override
	public void addRegularStateToTheSpecification(STATE s, boolean initial, boolean accepting) {
		if(initial){
			this.specification.addInitialState(s);
		}
		if(accepting){
			this.specification.addAcceptState(s);
		}
		this.specification.addVertex(s);
	}
	
	public void changeIntersection(DrawableIntBA<CONSTRAINEDELEMENT, STATE, TRANSITION, INTERSECTIONSTATE, INTERSECTIONTRANSITION,  INTERSECTIONTRANSITIONFACTORY> intersection){
		this.intersection=intersection;
	}
	
	
	@Override
	public void loadClaimFromLTL(String ltlFormula){
		LTLtoBATransformer<CONSTRAINEDELEMENT, STATE, STATEFACTORY, TRANSITION, TRANSITIONFACTORY> ltltoBa=new LTLtoBATransformer<CONSTRAINEDELEMENT, STATE, STATEFACTORY, TRANSITION, TRANSITIONFACTORY>(this.stateFactory, this.transitionFactory);
		this.specification=ltltoBa.transform(ltlFormula);
	}
	
	@Override
	public void check(){
		mp=new ModelCheckingResults<CONSTRAINEDELEMENT,STATE, TRANSITION, INTERSECTIONSTATE, INTERSECTIONTRANSITION>();
		ModelChecker<CONSTRAINEDELEMENT, STATE, TRANSITION, TRANSITIONFACTORY, INTERSECTIONSTATE,INTERSECTIONTRANSITION,
		INTERSECTIONTRANSITIONFACTORY> mc=new 
		ModelChecker<CONSTRAINEDELEMENT, STATE, TRANSITION,TRANSITIONFACTORY, INTERSECTIONSTATE, INTERSECTIONTRANSITION,
		INTERSECTIONTRANSITIONFACTORY>(this.getModel(), this.getSpecification(), mp);
		mc.check();
		this.changeIntersection(mc.getIntersection());
	}
	
	public ModelCheckingResults<CONSTRAINEDELEMENT,STATE, TRANSITION, INTERSECTIONSTATE, INTERSECTIONTRANSITION> getVerificationResults(){
		return this.mp;
	}
	
	@Override
	public Transformer<INTERSECTIONSTATE, Point2D> loadIntersection(String intersectionPath) throws IOException,
			GraphIOException {
		this.intBaReader=
				new IntBAReader<CONSTRAINEDELEMENT,STATE, 
				TRANSITION, 
				INTERSECTIONSTATE, 
				INTERSECTIONSTATEFACTORY, 
				INTERSECTIONTRANSITION, 
				TRANSITIONFACTORY, 
				INTERSECTIONTRANSITIONFACTORY, 
				DrawableIntBA<CONSTRAINEDELEMENT,STATE,TRANSITION,INTERSECTIONSTATE, INTERSECTIONTRANSITION, INTERSECTIONTRANSITIONFACTORY>, 
				IntBAFactory<CONSTRAINEDELEMENT,STATE,TRANSITION ,TRANSITIONFACTORY,INTERSECTIONSTATE,
				INTERSECTIONSTATEFACTORY,
				INTERSECTIONTRANSITION,
				INTERSECTIONTRANSITIONFACTORY,
				DrawableIntBA<CONSTRAINEDELEMENT,STATE, TRANSITION, INTERSECTIONSTATE, INTERSECTIONTRANSITION, INTERSECTIONTRANSITIONFACTORY>>>
				(intersectionTransitionFactory, 
						intersectionStateFactory, 
						new  IntBAFactoryImpl<CONSTRAINEDELEMENT,STATE, INTERSECTIONSTATE, INTERSECTIONSTATEFACTORY, INTERSECTIONTRANSITION, 
									INTERSECTIONTRANSITIONFACTORY, TRANSITION,
									TRANSITIONFACTORY>(this.intersectionTransitionFactory, transitionFactory)
						, new BufferedReader(new FileReader(intersectionPath))
				
				);
		this.intersection=this.intBaReader.readGraph();
		return this.intBaReader.getStatePositionTransformer();
	}

	@Override
	public void newModel() {
		this.model=new IBAImpl<CONSTRAINEDELEMENT,STATE, TRANSITION, TRANSITIONFACTORY>(this.transitionFactory);
	}

	@Override
	public void newClaim() {
		this.specification=new BAImpl<CONSTRAINEDELEMENT,STATE, TRANSITION, TRANSITIONFACTORY>(this.transitionFactory);
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
