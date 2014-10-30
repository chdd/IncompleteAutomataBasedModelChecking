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
import it.polimi.model.impl.transitions.ConstrainedTransition;
import it.polimi.model.impl.transitions.ConstrainedTransitionFactoryImpl;
import it.polimi.model.impl.transitions.LabelledTransition;
import it.polimi.model.impl.transitions.LabelledTransitionFactoryImpl;
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

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import edu.uci.ics.jung.io.GraphIOException;

/**
 * contains the model of the application: the model of the system, its specification and the intersection between the model and the specification
 * @author claudiomenghi
 */
public class Model implements ModelInterface{

	/**
	 * contains the model of the system
	 */
	private DrawableIBA<State, LabelledTransition, LabelledTransitionFactory<LabelledTransition>> model;
	
	/**
	 * contains the specification of the system
	 */
	private DrawableBA<State, LabelledTransition, LabelledTransitionFactory<LabelledTransition>>  specification;
	
	/**
	 * contains the intersection between the model and the specification
	 */
	private DrawableIntBA<State, LabelledTransition, IntersectionState<State>, ConstrainedTransition<State>, 
	ConstrainedTransitionFactory<State, ConstrainedTransition<State>>> intersection;
	
	private BAWriter<State, 
	LabelledTransition,
	LabelledTransitionFactory<LabelledTransition>,
	DrawableBA<State, LabelledTransition, LabelledTransitionFactory<LabelledTransition>>> baWriter;
	
	/**
	 * 
	 */
	private BAReader<State, 
		LabelledTransition,
		LabelledTransitionFactory<LabelledTransition>, 
		StateFactory<State>,
		DrawableBA<State, LabelledTransition, LabelledTransitionFactory<LabelledTransition>>,
		BAFactory<State, LabelledTransition, LabelledTransitionFactory<LabelledTransition>, DrawableBA<State, LabelledTransition, LabelledTransitionFactory<LabelledTransition>>>> baReader;
	
	/**
	 * 
	 */
	private IBAReader<State, 
		LabelledTransition,
		LabelledTransitionFactory<LabelledTransition>, 
		StateFactory<State>,
		DrawableIBA<State, LabelledTransition, LabelledTransitionFactory<LabelledTransition>>,
		IBAFactory<State, LabelledTransition, LabelledTransitionFactory<LabelledTransition>, DrawableIBA<State, LabelledTransition, LabelledTransitionFactory<LabelledTransition>>>> ibaReader;
	
	private IntBAWriter<
		State, 
		LabelledTransition, 
		IntersectionState<State>, 
		ConstrainedTransition<State>, 
		LabelledTransitionFactory<LabelledTransition>, 
		ConstrainedTransitionFactory<State, ConstrainedTransition<State>>,
		DrawableIntBA<State, LabelledTransition, IntersectionState<State>, ConstrainedTransition<State>, ConstrainedTransitionFactory<State, ConstrainedTransition<State>>>> intBaWriter;
	
	
	private IntBAReader<
		State, 
		LabelledTransition, 
		IntersectionState<State>, 
		IntersectionStateFactory<State, IntersectionState<State>>, 
		ConstrainedTransition<State>, 
		LabelledTransitionFactory<LabelledTransition>, 
		ConstrainedTransitionFactory<State,ConstrainedTransition<State>>, 
		DrawableIntBA<State,LabelledTransition,IntersectionState<State>,ConstrainedTransition<State>,
		ConstrainedTransitionFactory<State,ConstrainedTransition<State>>>, 
		IntBAFactory<State,LabelledTransition ,LabelledTransitionFactory<LabelledTransition>,IntersectionState<State>,
			IntersectionStateFactory<State, IntersectionState<State>>,
			ConstrainedTransition<State>,
			ConstrainedTransitionFactory<State,ConstrainedTransition<State>>,
		DrawableIntBA<State, LabelledTransition, IntersectionState<State>, ConstrainedTransition<State>, ConstrainedTransitionFactory<State, ConstrainedTransition<State>>>>> intBaReader;
	
	
	private ModelCheckerParameters<State, IntersectionState<State>> mp=new ModelCheckerParameters<State, IntersectionState<State>>();
	
	
	public Model(){
		this.model=new IBAImpl<State, LabelledTransition,  LabelledTransitionFactory<LabelledTransition>>(new LabelledTransitionFactoryImpl());
		this.specification=new BAImpl<State, LabelledTransition, LabelledTransitionFactory<LabelledTransition>>(new LabelledTransitionFactoryImpl());
		this.intersection=new IntBAImpl<State, LabelledTransition, IntersectionState<State>, ConstrainedTransition<State>
		, LabelledTransitionFactory<LabelledTransition>,
		ConstrainedTransitionFactory<State, ConstrainedTransition<State>>>(this.model, this.specification, new ConstrainedTransitionFactoryImpl());
	
		this.baWriter=new BAWriter<State, 
				LabelledTransition,
				LabelledTransitionFactory<LabelledTransition>,
				DrawableBA<State, LabelledTransition, LabelledTransitionFactory<LabelledTransition>>>();
		
		this.intBaWriter=new IntBAWriter<
				State, 
				LabelledTransition, 
				IntersectionState<State>, 
				ConstrainedTransition<State>, 
				LabelledTransitionFactory<LabelledTransition>, 
				ConstrainedTransitionFactory<State, ConstrainedTransition<State>>,
				DrawableIntBA<State, LabelledTransition, IntersectionState<State>, ConstrainedTransition<State>, ConstrainedTransitionFactory<State, ConstrainedTransition<State>>>>();
	
		
	
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
		
		ibaReader=new IBAReader<State, 
				LabelledTransition,
				LabelledTransitionFactory<LabelledTransition>, 
				StateFactory<State>,
				DrawableIBA<State, LabelledTransition, LabelledTransitionFactory<LabelledTransition>>,
				IBAFactory<State, LabelledTransition, LabelledTransitionFactory<LabelledTransition>, 
				DrawableIBA<State, LabelledTransition, LabelledTransitionFactory<LabelledTransition>>>>(
						new LabelledTransitionFactoryImpl(), 
						new StateFactory<State>(), 
						new IBAFactoryImpl<State, LabelledTransition, LabelledTransitionFactory<LabelledTransition>>(new LabelledTransitionFactoryImpl()),
						new BufferedReader(new FileReader(modelFilePath)));
		this.model=this.ibaReader.readGraph();
		this.baReader=new BAReader<State, 
				LabelledTransition,
				LabelledTransitionFactory<LabelledTransition>, 
				StateFactory<State>,
				DrawableBA<State, LabelledTransition, LabelledTransitionFactory<LabelledTransition>>,
				BAFactory<State, LabelledTransition, LabelledTransitionFactory<LabelledTransition>, 
				DrawableBA<State, LabelledTransition, LabelledTransitionFactory<LabelledTransition>>>>(
						new LabelledTransitionFactoryImpl(), 
						new StateFactory<State>(), 
						new BAFactoryImpl<State, LabelledTransition, LabelledTransitionFactory<LabelledTransition>>(new LabelledTransitionFactoryImpl()),
						new BufferedReader(new FileReader(specificationFilePath)));
		
		this.specification=this.baReader.readGraph();
		this.intersection=new IntBAImpl<State, LabelledTransition, IntersectionState<State>, ConstrainedTransition<State>
		, LabelledTransitionFactory<LabelledTransition>,
		ConstrainedTransitionFactory<State, ConstrainedTransition<State>>>(model, specification, new ConstrainedTransitionFactoryImpl());
	}
	
	/**
	 * @see {@link ModelInterface}
	 */
	@Override
	public void loadModel(String modelFilePath) throws IOException, GraphIOException{
		this.ibaReader=new IBAReader<State, 
				LabelledTransition,
				LabelledTransitionFactory<LabelledTransition>, 
				StateFactory<State>,
				DrawableIBA<State, LabelledTransition, LabelledTransitionFactory<LabelledTransition>>,
				IBAFactory<State, LabelledTransition, LabelledTransitionFactory<LabelledTransition>, 
				DrawableIBA<State, LabelledTransition, LabelledTransitionFactory<LabelledTransition>>>>(
						new LabelledTransitionFactoryImpl(), 
						new StateFactory<State>(), 
						new IBAFactoryImpl<State, LabelledTransition, LabelledTransitionFactory<LabelledTransition>>(new LabelledTransitionFactoryImpl()),
						new BufferedReader(new FileReader(modelFilePath)));
		this.model=ibaReader.readGraph();
		this.intersection=new IntBAImpl<State, LabelledTransition, IntersectionState<State>, ConstrainedTransition<State>
		, LabelledTransitionFactory<LabelledTransition>,
		ConstrainedTransitionFactory<State, ConstrainedTransition<State>>>(model, specification, new ConstrainedTransitionFactoryImpl());
	}
	/**
	 * @see {@link ModelInterface}
	 */
	@Override
	public void changeSpecification(String specificationFilePath) throws IOException, GraphIOException{
		this.baReader=new BAReader<State, 
				LabelledTransition,
				LabelledTransitionFactory<LabelledTransition>, 
				StateFactory<State>,
				DrawableBA<State, LabelledTransition, LabelledTransitionFactory<LabelledTransition>>,
				BAFactory<State, LabelledTransition, LabelledTransitionFactory<LabelledTransition>, 
				DrawableBA<State, LabelledTransition, LabelledTransitionFactory<LabelledTransition>>>>(
						new LabelledTransitionFactoryImpl(), 
						new StateFactory<State>(), 
						new BAFactoryImpl<State, LabelledTransition, LabelledTransitionFactory<LabelledTransition>>(new LabelledTransitionFactoryImpl()),
						new BufferedReader(new FileReader(specificationFilePath)));
		this.specification=this.baReader.readGraph();
		this.intersection=new IntBAImpl<State, LabelledTransition, IntersectionState<State>, ConstrainedTransition<State>
		, LabelledTransitionFactory<LabelledTransition>,
		ConstrainedTransitionFactory<State, ConstrainedTransition<State>>>(model, specification, new ConstrainedTransitionFactoryImpl());
	}
	/**
	 * @see {@link ModelInterface}
	 */
	@Override
	public DrawableIBA<State, LabelledTransition, LabelledTransitionFactory<LabelledTransition>> getModel(){
		return this.model;
	}
	/**
	 * @see {@link ModelInterface}
	 */
	@Override
	public DrawableBA<State, LabelledTransition, LabelledTransitionFactory<LabelledTransition>> getSpecification(){
		return this.specification;
	}
	/**
	 * @see {@link ModelInterface}
	 */
	@Override
	public DrawableIntBA<State, LabelledTransition, IntersectionState<State>, ConstrainedTransition<State>, 
		ConstrainedTransitionFactory<State, ConstrainedTransition<State>>> getIntersection(){
		return this.intersection;
	}
	
	/**
	 * @see {@link ModelInterface}
	 */
	@Override
	public void saveModel(String filePath) throws IOException, GraphIOException{
			IBAWriter<State, 
				LabelledTransition,
				LabelledTransitionFactory<LabelledTransition>, 
				DrawableIBA<State,LabelledTransition,LabelledTransitionFactory<LabelledTransition>>> ibaToFile
				=new IBAWriter<State, 
						LabelledTransition,
						LabelledTransitionFactory<LabelledTransition>, 
						DrawableIBA<State,LabelledTransition,LabelledTransitionFactory<LabelledTransition>>>();
			ibaToFile.save(this.model, new PrintWriter(new BufferedWriter(new FileWriter(filePath))));
	}
	/**
	 * @see {@link ModelInterface}
	 */
	@Override
	public void saveSpecification(String filePath) throws IOException, GraphIOException{
		
		
		baWriter.save(this.specification, new PrintWriter(new BufferedWriter(new FileWriter(filePath))));
	}
	
	@Override
	public void addRegularStateToTheModel(State s, boolean regular, boolean initial, boolean accepting) {
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
		this.intersection=new IntBAImpl<State, LabelledTransition, IntersectionState<State>, ConstrainedTransition<State>,
				LabelledTransitionFactory<LabelledTransition>,
				ConstrainedTransitionFactory<State, ConstrainedTransition<State>>>(model, specification, new ConstrainedTransitionFactoryImpl());
	}
	
	@Override
	public void addRegularStateToTheSpecification(State s, boolean initial, boolean accepting) {
		if(initial){
			this.specification.addInitialState(s);
		}
		if(accepting){
			this.specification.addAcceptState(s);
		}
		this.specification.addVertex(s);
		this.intersection=new IntBAImpl<State, LabelledTransition, IntersectionState<State>, ConstrainedTransition<State>,
				LabelledTransitionFactory<LabelledTransition>,
				ConstrainedTransitionFactory<State, ConstrainedTransition<State>>>(model, specification, new ConstrainedTransitionFactoryImpl());
	}
	
	public void changeIntersection(IntBAImpl<State, LabelledTransition, IntersectionState<State>, ConstrainedTransition<State>,
			LabelledTransitionFactory<LabelledTransition>,
			ConstrainedTransitionFactory<State, ConstrainedTransition<State>>> intersection){
		this.intersection=intersection;
	}
	
	
	@Override
	public void loadClaim(String claim){
		LTLtoBATransformer ltltoBa=new LTLtoBATransformer();
		this.specification=ltltoBa.transform(claim);
	}
	
	@Override
	public void check(){
		mp=new ModelCheckerParameters<State, IntersectionState<State>>();
		ModelChecker<State, LabelledTransition, IntersectionState<State>, ConstrainedTransition<State>,
		LabelledTransitionFactory<LabelledTransition>,
		ConstrainedTransitionFactory<State, ConstrainedTransition<State>>> mc=new 
		ModelChecker<State, LabelledTransition, IntersectionState<State>, ConstrainedTransition<State>,
		LabelledTransitionFactory<LabelledTransition>,
		ConstrainedTransitionFactory<State, ConstrainedTransition<State>>>(this.getModel(), this.getSpecification(), mp);
		mc.check();
		this.changeIntersection(mc.getIntersection());
	}
	
	public ModelCheckerParameters<State, IntersectionState<State>> getVerificationResults(){
		return this.mp;
	}
	@Override
	public void saveIntersection(String filePath) throws IOException,
			GraphIOException {
		
		intBaWriter.save(this.intersection, new PrintWriter(new BufferedWriter(new FileWriter(filePath))));
		
	}
	@Override
	public void loadIntersection(String intersectionPath) throws IOException,
			GraphIOException {
		new IntBAImpl<
		State, 
		LabelledTransition,
		IntersectionState<State>, 
		ConstrainedTransition<State>,
		LabelledTransitionFactory<LabelledTransition>, 
		ConstrainedTransitionFactory<State, ConstrainedTransition<State>>>(model, specification, new ConstrainedTransitionFactoryImpl());
		new ConstrainedTransitionFactoryImpl();
		new IntersectionStateFactory<State, IntersectionState<State>>();
		this.intBaReader=
				new IntBAReader<State, 
				LabelledTransition, 
				IntersectionState<State>, 
				IntersectionStateFactory<State, IntersectionState<State>>, 
				ConstrainedTransition<State>, 
				LabelledTransitionFactory<LabelledTransition>, 
				ConstrainedTransitionFactory<State,ConstrainedTransition<State>>, 
				DrawableIntBA<State,LabelledTransition,IntersectionState<State>,ConstrainedTransition<State>,
				ConstrainedTransitionFactory<State,ConstrainedTransition<State>>>, 
				IntBAFactory<State,LabelledTransition ,LabelledTransitionFactory<LabelledTransition>,IntersectionState<State>,
				IntersectionStateFactory<State, IntersectionState<State>>,
				ConstrainedTransition<State>,
				ConstrainedTransitionFactory<State,ConstrainedTransition<State>>,
				DrawableIntBA<State, LabelledTransition, IntersectionState<State>, ConstrainedTransition<State>, ConstrainedTransitionFactory<State, ConstrainedTransition<State>>>>>
				(
						new ConstrainedTransitionFactoryImpl(), 
						new IntersectionStateFactory<State, IntersectionState<State>>(), 
						new  IntBAFactoryImpl<State, IntersectionState<State>, IntersectionStateFactory<State, IntersectionState<State>>, ConstrainedTransition<State>, 
							ConstrainedTransitionFactory<State,ConstrainedTransition<State>>, LabelledTransition, 
							LabelledTransitionFactory<LabelledTransition>>(new ConstrainedTransitionFactoryImpl(), new LabelledTransitionFactoryImpl()),
						new BufferedReader(new FileReader(intersectionPath))
				);
		this.intersection=this.intBaReader.readGraph();
	}
	
	
}
