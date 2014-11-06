package it.polimi.model;

import it.polimi.io.BAReader;
import it.polimi.io.BAWriter;
import it.polimi.io.IBAReader;
import it.polimi.io.IBAWriter;
import it.polimi.io.IntBAReader;
import it.polimi.io.IntBAWriter;
import it.polimi.model.impl.automata.BAFactoryImpl;
import it.polimi.model.impl.automata.BAImpl;
import it.polimi.model.impl.automata.IBAFactoryImpl;
import it.polimi.model.impl.automata.IBAImpl;
import it.polimi.model.impl.automata.IntBAFactoryImpl;
import it.polimi.model.impl.automata.IntBAImpl;
import it.polimi.model.impl.states.IntersectionState;
import it.polimi.model.impl.states.IntersectionStateFactory;
import it.polimi.model.impl.states.State;
import it.polimi.model.impl.states.StateFactory;
import it.polimi.model.impl.transitions.LabelledTransition;
import it.polimi.model.interfaces.automata.BAFactory;
import it.polimi.model.interfaces.automata.IBAFactory;
import it.polimi.model.interfaces.automata.IntBAFactory;
import it.polimi.model.interfaces.automata.drawable.DrawableBA;
import it.polimi.model.interfaces.automata.drawable.DrawableIBA;
import it.polimi.model.interfaces.automata.drawable.DrawableIntBA;
import it.polimi.model.interfaces.transitions.ConstrainedTransitionFactory;
import it.polimi.model.interfaces.transitions.LabelledTransitionFactory;
import it.polimi.model.ltltoba.LTLtoBATransformer;
import it.polimi.modelchecker.ModelChecker;
import it.polimi.modelchecker.ModelCheckerParameters;

import java.awt.geom.Point2D;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.collections15.Transformer;
import org.xml.sax.SAXException;

import edu.uci.ics.jung.algorithms.layout.AbstractLayout;
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
	
	/**
	 * contains the specification of the system
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
	
	private BAWriter<CONSTRAINEDELEMENT, STATE, 
	TRANSITION,
	TRANSITIONFACTORY,
	DrawableBA<CONSTRAINEDELEMENT, STATE, TRANSITION, TRANSITIONFACTORY>> baWriter;
	
	private IBAWriter<CONSTRAINEDELEMENT, STATE, 
	TRANSITION,
	TRANSITIONFACTORY, 
	DrawableIBA<CONSTRAINEDELEMENT, STATE, TRANSITION, TRANSITIONFACTORY>> ibaToFile;
	
	/**
	 * 
	 */
	private BAReader<CONSTRAINEDELEMENT, STATE, 
		TRANSITION,
		TRANSITIONFACTORY, 
		STATEFACTORY,
		DrawableBA<CONSTRAINEDELEMENT, STATE, TRANSITION, TRANSITIONFACTORY>,
		BAFactory<CONSTRAINEDELEMENT, STATE, TRANSITION, TRANSITIONFACTORY, 
			DrawableBA<CONSTRAINEDELEMENT, STATE, TRANSITION, TRANSITIONFACTORY>>> baReader;
	
	/**
	 * 
	 */
	private IBAReader<CONSTRAINEDELEMENT, STATE, 
		TRANSITION,
		TRANSITIONFACTORY, 
		STATEFACTORY,
		DrawableIBA<CONSTRAINEDELEMENT, STATE, TRANSITION, TRANSITIONFACTORY>,
		IBAFactory<CONSTRAINEDELEMENT,STATE, TRANSITION, TRANSITIONFACTORY,
			DrawableIBA<CONSTRAINEDELEMENT, STATE, TRANSITION, TRANSITIONFACTORY>>> ibaReader;
	
	private IntBAWriter<CONSTRAINEDELEMENT,
		STATE, 
		TRANSITION, 
		INTERSECTIONSTATE, 
		INTERSECTIONTRANSITION, 
		TRANSITIONFACTORY, 
		INTERSECTIONTRANSITIONFACTORY,
		DrawableIntBA<CONSTRAINEDELEMENT,STATE, TRANSITION, INTERSECTIONSTATE, INTERSECTIONTRANSITION, INTERSECTIONTRANSITIONFACTORY>> intBaWriter;
	
	
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
	
	
	private ModelCheckerParameters<CONSTRAINEDELEMENT,STATE, TRANSITION, INTERSECTIONSTATE, INTERSECTIONTRANSITION> mp=new ModelCheckerParameters<CONSTRAINEDELEMENT,STATE, TRANSITION, INTERSECTIONSTATE, INTERSECTIONTRANSITION>();
	
	
	public Model(STATEFACTORY stateFactory, TRANSITIONFACTORY transitionFactory, INTERSECTIONSTATEFACTORY intersectionStateFactory, INTERSECTIONTRANSITIONFACTORY intersectionTransitionFactory){
		this.stateFactory=stateFactory;
		this.transitionFactory=transitionFactory;
		this.intersectionStateFactory=intersectionStateFactory;
		this.intersectionTransitionFactory=intersectionTransitionFactory;
		this.model=new IBAImpl<CONSTRAINEDELEMENT,STATE, TRANSITION,  TRANSITIONFACTORY>(transitionFactory);
		this.specification=new BAImpl<CONSTRAINEDELEMENT,STATE, TRANSITION, TRANSITIONFACTORY>(transitionFactory);
		this.intersection=new IntBAImpl<CONSTRAINEDELEMENT,STATE, TRANSITION, INTERSECTIONSTATE, INTERSECTIONTRANSITION,
		TRANSITIONFACTORY,
		INTERSECTIONTRANSITIONFACTORY>(this.model, this.specification,intersectionTransitionFactory);
	}
	
	/**
	 * creates a new model of the application: the model of the system is loaded from the file with path modelFilePath, its specification is loaded from the 
	 * file with path specificationFilePath 
	 * @param modelFilePath is the path of the file which contains the model of the system
	 * @param specificationFilePath is the path of the file which contains the specification of the system 
	 * @throws GraphIOException 
	 * @throws JAXBException
	 * @throws SAXException
	 * @throws IOException
	 * @throws ParserConfigurationException
	 */
	public Model(String modelFilePath, String specificationFilePath) throws IOException, GraphIOException{
		
		ibaReader=new IBAReader<CONSTRAINEDELEMENT,STATE, 
				TRANSITION,
				TRANSITIONFACTORY, 
				STATEFACTORY,
				DrawableIBA<CONSTRAINEDELEMENT,STATE, TRANSITION, TRANSITIONFACTORY>,
				IBAFactory<CONSTRAINEDELEMENT,STATE, TRANSITION, TRANSITIONFACTORY, 
				DrawableIBA<CONSTRAINEDELEMENT,STATE, TRANSITION, TRANSITIONFACTORY>>>(
						this.transitionFactory, 
						this.stateFactory, 
						new IBAFactoryImpl<CONSTRAINEDELEMENT,STATE, TRANSITION, TRANSITIONFACTORY>(this.transitionFactory),
						new BufferedReader(new FileReader(modelFilePath))
						);
		this.model=this.ibaReader.readGraph();
		this.baReader=new BAReader<CONSTRAINEDELEMENT,STATE, 
				TRANSITION,
				TRANSITIONFACTORY, 
				STATEFACTORY,
				DrawableBA<CONSTRAINEDELEMENT,STATE, TRANSITION, TRANSITIONFACTORY>,
				BAFactory<CONSTRAINEDELEMENT,STATE, TRANSITION, TRANSITIONFACTORY, 
					DrawableBA<CONSTRAINEDELEMENT,STATE, TRANSITION, TRANSITIONFACTORY>>>(
							this.transitionFactory,
							this.stateFactory,
						new BAFactoryImpl<CONSTRAINEDELEMENT,STATE, TRANSITION, TRANSITIONFACTORY>(this.transitionFactory),
						new BufferedReader(new FileReader(specificationFilePath)));
		
		this.specification=this.baReader.readGraph();
		this.intersection=new IntBAImpl<CONSTRAINEDELEMENT,STATE, TRANSITION, INTERSECTIONSTATE, INTERSECTIONTRANSITION,  TRANSITIONFACTORY, INTERSECTIONTRANSITIONFACTORY>
		(model, specification, this.intersectionTransitionFactory);
	}
	
	/**
	 * @see {@link ModelInterface}
	 */
	@Override
	public Transformer<STATE, Point2D> loadModel(String modelFilePath) throws IOException, GraphIOException{
		this.ibaReader=new IBAReader<
				CONSTRAINEDELEMENT,
				STATE, 
				TRANSITION,
				TRANSITIONFACTORY, 
				STATEFACTORY,
				DrawableIBA<CONSTRAINEDELEMENT,STATE, TRANSITION, TRANSITIONFACTORY>,
				IBAFactory<CONSTRAINEDELEMENT,STATE, TRANSITION, TRANSITIONFACTORY, 
				DrawableIBA<CONSTRAINEDELEMENT,STATE, TRANSITION, TRANSITIONFACTORY>>>(
						this.transitionFactory,
						this.stateFactory, 
						new IBAFactoryImpl<CONSTRAINEDELEMENT,STATE, TRANSITION, TRANSITIONFACTORY>(this.transitionFactory),
						new BufferedReader(new FileReader(modelFilePath)));
		this.model=this.ibaReader.readGraph();
		this.intersection=new IntBAImpl<CONSTRAINEDELEMENT,STATE, TRANSITION, INTERSECTIONSTATE, INTERSECTIONTRANSITION, TRANSITIONFACTORY,
		INTERSECTIONTRANSITIONFACTORY>(model, specification, this.intersectionTransitionFactory);
		return this.ibaReader.getStatePositionTransformer();
		
	}
	/**
	 * @see {@link ModelInterface}
	 */
	@Override
	public Transformer<STATE, Point2D> changeSpecification(String specificationFilePath) throws IOException, GraphIOException{
		this.baReader=new BAReader<CONSTRAINEDELEMENT,STATE, 
				TRANSITION,
				TRANSITIONFACTORY, 
				STATEFACTORY,
				DrawableBA<CONSTRAINEDELEMENT, STATE, TRANSITION, TRANSITIONFACTORY>,
				BAFactory<CONSTRAINEDELEMENT,STATE, TRANSITION, TRANSITIONFACTORY, 
					DrawableBA<CONSTRAINEDELEMENT, STATE, TRANSITION, TRANSITIONFACTORY>>>(
							this.transitionFactory,
							this.stateFactory, 
						new BAFactoryImpl<CONSTRAINEDELEMENT, STATE, TRANSITION, TRANSITIONFACTORY>(this.transitionFactory),
						new BufferedReader(new FileReader(specificationFilePath)));
		this.specification=this.baReader.readGraph();
		this.intersection=new IntBAImpl<CONSTRAINEDELEMENT, STATE, TRANSITION, INTERSECTIONSTATE, INTERSECTIONTRANSITION, TRANSITIONFACTORY, INTERSECTIONTRANSITIONFACTORY>
						(model, specification, this.intersectionTransitionFactory);
		return this.baReader.getStatePositionTransformer();
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
	
	/**
	 * @see {@link ModelInterface}
	 */
	@Override
	public void saveModel(String filePath, AbstractLayout<STATE, TRANSITION> layout) throws IOException, GraphIOException{
		this.ibaToFile
				=new IBAWriter<CONSTRAINEDELEMENT,STATE, 
						TRANSITION,
						TRANSITIONFACTORY, 
						DrawableIBA<CONSTRAINEDELEMENT,STATE, TRANSITION, TRANSITIONFACTORY>>(layout);
		this.ibaToFile.save(this.model, new PrintWriter(new BufferedWriter(new FileWriter(filePath))));
	}
	/**
	 * @see {@link ModelInterface}
	 */
	@Override
	public void saveSpecification(String filePath, AbstractLayout<STATE, TRANSITION> layout) throws IOException, GraphIOException{
		this.baWriter=new BAWriter<CONSTRAINEDELEMENT,STATE, 
				TRANSITION,
				TRANSITIONFACTORY,
				DrawableBA<CONSTRAINEDELEMENT,STATE, TRANSITION, TRANSITIONFACTORY>>(layout);
		
		
		baWriter.save(this.specification, new PrintWriter(new BufferedWriter(new FileWriter(filePath))));
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
		mp=new ModelCheckerParameters<CONSTRAINEDELEMENT,STATE, TRANSITION, INTERSECTIONSTATE, INTERSECTIONTRANSITION>();
		ModelChecker<CONSTRAINEDELEMENT, STATE, TRANSITION, TRANSITIONFACTORY, INTERSECTIONSTATE,INTERSECTIONTRANSITION,
		INTERSECTIONTRANSITIONFACTORY> mc=new 
		ModelChecker<CONSTRAINEDELEMENT, STATE, TRANSITION,TRANSITIONFACTORY, INTERSECTIONSTATE, INTERSECTIONTRANSITION,
		INTERSECTIONTRANSITIONFACTORY>(this.getModel(), this.getSpecification(), mp);
		mc.check();
		this.changeIntersection(mc.getIntersection());
	}
	
	public ModelCheckerParameters<CONSTRAINEDELEMENT,STATE, TRANSITION, INTERSECTIONSTATE, INTERSECTIONTRANSITION> getVerificationResults(){
		return this.mp;
	}
	@Override
	public void saveIntersection(String filePath, AbstractLayout<INTERSECTIONSTATE, INTERSECTIONTRANSITION> layout) throws IOException,
			GraphIOException {
		this.intBaWriter=new IntBAWriter<CONSTRAINEDELEMENT,
				STATE, 
				TRANSITION, 
				INTERSECTIONSTATE, 
				INTERSECTIONTRANSITION, 
				TRANSITIONFACTORY, 
				INTERSECTIONTRANSITIONFACTORY,
				DrawableIntBA<CONSTRAINEDELEMENT,STATE, 
							TRANSITION, 
							INTERSECTIONSTATE,
							INTERSECTIONTRANSITION, 
							INTERSECTIONTRANSITIONFACTORY>>(layout);
	
		this.intBaWriter.save(this.intersection, new PrintWriter(new BufferedWriter(new FileWriter(filePath))));
		
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
	
	
}
