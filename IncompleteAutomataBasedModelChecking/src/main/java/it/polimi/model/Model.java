package it.polimi.model;

import it.polimi.model.automata.ba.BAtoFile;
import it.polimi.model.automata.ba.BuchiAutomaton;
import it.polimi.model.automata.ba.state.State;
import it.polimi.model.automata.ba.transition.ConstrainedTransition;
import it.polimi.model.automata.ba.transition.LabelledTransition;
import it.polimi.model.automata.ba.transition.TransitionFactory;
import it.polimi.model.automata.iba.IBAtoFile;
import it.polimi.model.automata.iba.IncompleteBuchiAutomaton;
import it.polimi.model.automata.intersection.IntersectionAutomaton;
import it.polimi.model.automata.intersection.IntersectionState;
import it.polimi.model.io.AutomatonBuilder;

import java.io.IOException;
import java.util.Set;

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
	private IncompleteBuchiAutomaton<State, LabelledTransition> model;
	
	/**
	 * contains the specification of the system
	 */
	private BuchiAutomaton<State, LabelledTransition>  specification;
	/**
	 * contains the intersection between the model and the specification
	 */
	private IntersectionAutomaton<State, LabelledTransition, IntersectionState<State>, ConstrainedTransition<State>> intersection;
	
	public Model(){
		this.model=new IncompleteBuchiAutomaton<State, LabelledTransition>();
		this.specification=new BuchiAutomaton<State, LabelledTransition>();
		this.intersection=new IntersectionAutomaton<State, LabelledTransition, IntersectionState<State>, ConstrainedTransition<State>>();
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
		this.model=new AutomatonBuilder()
				.loadIBAAutomaton(modelFilePath);
		this.specification=new AutomatonBuilder()
				.loadBAAutomaton(specificationFilePath);
		this.intersection=new IntersectionAutomaton<State, LabelledTransition, IntersectionState<State>, ConstrainedTransition<State>>(model, specification);
	}
	
	/**
	 * @see {@link ModelInterface}
	 */
	@Override
	public void changeModel(String modelFilePath) throws IOException, GraphIOException{
		this.model=new AutomatonBuilder()
				.loadIBAAutomaton(modelFilePath);
		this.intersection=new IntersectionAutomaton<State, LabelledTransition, IntersectionState<State>, ConstrainedTransition<State>>(model, specification);
	}
	/**
	 * @see {@link ModelInterface}
	 */
	@Override
	public void changeSpecification(String specificationFilePath) throws IOException, GraphIOException{
		this.specification=new AutomatonBuilder()
				.loadBAAutomaton(specificationFilePath);
		this.intersection=new IntersectionAutomaton<State, LabelledTransition, IntersectionState<State>, ConstrainedTransition<State>>(model, specification);
	}
	/**
	 * @see {@link ModelInterface}
	 */
	@Override
	public IncompleteBuchiAutomaton<State, LabelledTransition> getModel(){
		return this.model;
	}
	/**
	 * @see {@link ModelInterface}
	 */
	@Override
	public BuchiAutomaton<State, LabelledTransition> getSpecification(){
		return this.specification;
	}
	/**
	 * @see {@link ModelInterface}
	 */
	@Override
	public IntersectionAutomaton<State, LabelledTransition, IntersectionState<State>, ConstrainedTransition<State>> getIntersection(){
		return this.intersection;
	}
	
	/**
	 * @see {@link ModelInterface}
	 */
	@Override
	public void saveModel(String filePath) throws IOException, GraphIOException{
			IBAtoFile ibaToFile=new IBAtoFile();
			ibaToFile.toFile(filePath, this.model);
	}
	/**
	 * @see {@link ModelInterface}
	 */
	@Override
	public void saveSpecification(String filePath) throws IOException, GraphIOException{
			BAtoFile baToFile=new BAtoFile();
			baToFile.toFile(filePath, this.specification);
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
		this.intersection=new IntersectionAutomaton<State, LabelledTransition, IntersectionState<State>, ConstrainedTransition<State>>(model, specification);
	}
	@Override
	public void addTransitionToTheModel(String source, String destination, Set<String> character){
		
		
		this.model.addTransition(this.model.getVertex(Integer.parseInt(source)), this.model.getVertex(Integer.parseInt(destination)), new TransitionFactory<LabelledTransition>().create(character));
		this.intersection=new IntersectionAutomaton<State, LabelledTransition, IntersectionState<State>, ConstrainedTransition<State>>(model, specification);
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
		this.intersection=new IntersectionAutomaton<State, LabelledTransition, IntersectionState<State>, ConstrainedTransition<State>>(model, specification);
	}
	@Override
	public void addTransitionToTheSpecification(String source, String destination, Set<String> character) {
		this.specification.addTransition(this.specification.getVertex(Integer.parseInt(source)), this.specification.getVertex(Integer.parseInt(destination)),new TransitionFactory<LabelledTransition>().create(character));
		this.intersection=new IntersectionAutomaton<State, LabelledTransition, IntersectionState<State>, ConstrainedTransition<State>>(model, specification);
	}
	@Override
	public void addCharacterToTheModed(String character) {
		this.model.addCharacter(character);
		this.intersection=new IntersectionAutomaton<State, LabelledTransition, IntersectionState<State>, ConstrainedTransition<State>>(model, specification);
		
	}
	@Override
	public void addCharacterToTheSpecification(String character) {
		this.specification.addCharacter(character);
		this.intersection=new IntersectionAutomaton<State, LabelledTransition, IntersectionState<State>, ConstrainedTransition<State>>(model, specification);
	}
	
}
