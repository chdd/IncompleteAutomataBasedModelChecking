package it.polimi.model;

import it.polimi.model.graph.State;
import it.polimi.model.io.AutomatonBuilder;
import it.polimi.model.io.BuilderException;

import java.io.IOException;

import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

/**
 * contains the model of the application: the model of the system, its specification and the intersection between the model and the specification
 * @author claudiomenghi
 */
public class Model implements ModelInterface{

	/**
	 * contains the model of the system
	 */
	private IncompleteBuchiAutomaton<State, LabelledTransition<State>> model;
	
	/**
	 * contains the specification of the system
	 */
	private BuchiAutomaton<State, LabelledTransition<State>>  specification;
	/**
	 * contains the intersection between the model and the specification
	 */
	private IntersectionAutomaton<State, LabelledTransition<State>, IntersectionState<State>, LabelledTransition<IntersectionState<State>>> intersection;
	
	public Model(){
		this.model=new IncompleteBuchiAutomaton<State, LabelledTransition<State>>();
		this.specification=new BuchiAutomaton<State, LabelledTransition<State>>();
		this.intersection=new IntersectionAutomaton<State, LabelledTransition<State>, IntersectionState<State>, LabelledTransition<IntersectionState<State>>>();
	}
	/**
	 * creates a new model of the application: the model of the system is loaded from the file with path modelFilePath, its specification is loaded from the 
	 * file with path specificationFilePath 
	 * @param modelFilePath is the path of the file which contains the model of the system
	 * @param specificationFilePath is the path of the file which contains the specification of the system 
	 * @throws JAXBException
	 * @throws SAXException
	 * @throws IOException
	 * @throws ParserConfigurationException
	 */
	public Model(String modelFilePath, String specificationFilePath) throws BuilderException{
		this.model=new AutomatonBuilder<State, LabelledTransition<State>, IncompleteBuchiAutomaton<State, LabelledTransition<State>>>()
				.loadAutomaton(IncompleteBuchiAutomaton.class, modelFilePath);
		this.specification=new AutomatonBuilder<State, LabelledTransition<State>, BuchiAutomaton<State, LabelledTransition<State>>>()
				.loadAutomaton(BuchiAutomaton.class, specificationFilePath);
		this.intersection=new IntersectionAutomaton<State, LabelledTransition<State>, IntersectionState<State>, LabelledTransition<IntersectionState<State>>>(model, specification);
	}
	
	/**
	 * @see {@link ModelInterface}
	 */
	@Override
	public void changeModel(String modelFilePath) throws BuilderException{
		this.model=new AutomatonBuilder<State, LabelledTransition<State>, IncompleteBuchiAutomaton<State, LabelledTransition<State>>>()
				.loadAutomaton(IncompleteBuchiAutomaton.class, modelFilePath);
		this.intersection=new IntersectionAutomaton<State, LabelledTransition<State>, IntersectionState<State>, LabelledTransition<IntersectionState<State>>>(model, specification);
	}
	/**
	 * @see {@link ModelInterface}
	 */
	@Override
	public void changeSpecification(String specificationFilePath) throws BuilderException{
		this.specification=new AutomatonBuilder<State, LabelledTransition<State>, BuchiAutomaton<State, LabelledTransition<State>>>()
				.loadAutomaton(BuchiAutomaton.class, specificationFilePath);
		this.intersection=new IntersectionAutomaton<State, LabelledTransition<State>, IntersectionState<State>, LabelledTransition<IntersectionState<State>>>(model, specification);
	}
	/**
	 * @see {@link ModelInterface}
	 */
	@Override
	public IncompleteBuchiAutomaton<State, LabelledTransition<State>> getModel(){
		return this.model;
	}
	/**
	 * @see {@link ModelInterface}
	 */
	@Override
	public BuchiAutomaton<State, LabelledTransition<State>> getSpecification(){
		return this.specification;
	}
	/**
	 * @see {@link ModelInterface}
	 */
	@Override
	public IntersectionAutomaton<State, LabelledTransition<State>, IntersectionState<State>, LabelledTransition<IntersectionState<State>>> getIntersection(){
		return this.intersection;
	}
	/**
	 * @see {@link ModelInterface}
	 */
	@Override
	public void loadModelFromXML(String automatonXML) throws BuilderException{
		this.model=new AutomatonBuilder<State, LabelledTransition<State>, IncompleteBuchiAutomaton<State, LabelledTransition<State>>>()
				.loadAutomatonFromText(IncompleteBuchiAutomaton.class, automatonXML);
	}
	/**
	 * @see {@link ModelInterface}
	 */
	@Override
	public void loadSpecificationFromXML(String automatonXML) throws BuilderException{
		this.specification=new AutomatonBuilder<State, LabelledTransition<State>, BuchiAutomaton<State, LabelledTransition<State>>>()
				.loadAutomatonFromText(BuchiAutomaton.class, automatonXML);
	}
	/**
	 * @see {@link ModelInterface}
	 */
	@Override
	public void saveModel(String filePath) throws BuilderException{
		try {
			this.model.toFile(filePath);
		} catch (JAXBException | IOException e) {
			new BuilderException(e.toString());
		}
	}
	/**
	 * @see {@link ModelInterface}
	 */
	@Override
	public void saveSpecification(String filePath) throws BuilderException{
		try{
			this.specification.toFile(filePath);
		} catch (JAXBException | IOException e) {
			new BuilderException(e.toString());
		}
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
		this.model.addState(s);
		this.intersection=new IntersectionAutomaton<State, LabelledTransition<State>, IntersectionState<State>, LabelledTransition<IntersectionState<State>>>(model, specification);
	}
	@Override
	public void addTransitionToTheModel(State source, State destination, String character){
		this.model.addTransition(source, new LabelledTransition<State>(character, destination));
		this.intersection=new IntersectionAutomaton<State, LabelledTransition<State>, IntersectionState<State>, LabelledTransition<IntersectionState<State>>>(model, specification);
	}
	@Override
	public void addRegularStateToTheSpecification(State s, boolean initial, boolean accepting) {
		if(initial){
			this.specification.addInitialState(s);
		}
		if(accepting){
			this.specification.addAcceptState(s);
		}
		this.specification.addState(s);
		this.intersection=new IntersectionAutomaton<State, LabelledTransition<State>, IntersectionState<State>, LabelledTransition<IntersectionState<State>>>(model, specification);
	}
	@Override
	public void addTransitionToTheSpecification(State source, State destination, String character) {
		this.specification.addTransition(source, new LabelledTransition<State>(character, destination));
		this.intersection=new IntersectionAutomaton<State, LabelledTransition<State>, IntersectionState<State>, LabelledTransition<IntersectionState<State>>>(model, specification);
	}
	@Override
	public void addCharacterToTheModed(String character) {
		this.model.addCharacter(character);
		this.intersection=new IntersectionAutomaton<State, LabelledTransition<State>, IntersectionState<State>, LabelledTransition<IntersectionState<State>>>(model, specification);
		
	}
	@Override
	public void addCharacterToTheSpecification(String character) {
		this.specification.addCharacter(character);
		this.intersection=new IntersectionAutomaton<State, LabelledTransition<State>, IntersectionState<State>, LabelledTransition<IntersectionState<State>>>(model, specification);
	}
	
}
