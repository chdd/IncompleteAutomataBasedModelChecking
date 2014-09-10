package it.polimi.modelchecker;

import it.polimi.model.BuchiAutomaton;
import it.polimi.model.IncompleteBuchiAutomaton;
import it.polimi.model.IntersectionAutomaton;
import it.polimi.model.IntersectionState;
import it.polimi.model.State;
import it.polimi.model.Transition;
import it.polimi.modelchecker.brzozowski.Brzozowski;
import it.polimi.modelchecker.brzozowski.predicates.AbstractPredicate;

/**
 * contains the model checking algorithm
 * @author claudiomenghi
 *
 * @param <S1> is the type of the states of the specification and of the  model
 * @param <T1> is the type of the transition of the specification and the model
 */
public class ModelChecker<S1 extends State, T1 extends Transition<S1>, S extends IntersectionState<S1>, T extends Transition<S>> {
	
	/**
	 * contains the specification to be checked
	 */
	private BuchiAutomaton<S1,T1> specification;
	/**
	 * contains the model to be checked
	 */
	private  IncompleteBuchiAutomaton<S1, T1> model;
	
	/**
	 * contains the intersection automaton of the model and its specification after the model checking procedure is performed
	 */
	private IntersectionAutomaton<S1,T1, S, T> ris;
	
	/**
	 * contains the results of the verification (if the specification is satisfied or not, the time required by the model checking procedure etc)
	 */
	private ModelCheckerParameters parameters;
	
	/**
	 * creates a new model checker
	 * @param model is the model to be analyzed by the model checker
	 * @param specification is the specification to be considered by the model checker
	 * @param mp is an object where the results of the verification (e.g., time required from the verification procedure are stored)
	 * @throws IllegalArgumentException if the model, the specification or the model checking parameters are null
	 */
	public ModelChecker(IncompleteBuchiAutomaton<S1, T1> model, BuchiAutomaton<S1,T1> specification, ModelCheckerParameters mp){
		if(model==null){
			throw new IllegalArgumentException("The model to be checked cannot be null");
		}
		if(specification==null){
			throw new IllegalArgumentException("The specification to be checked cannot be null");
		}
		if(mp==null){
			throw new IllegalArgumentException("The model checking parameters cannot be null");
		}
		this.specification=specification;
		this.model=model;
		this.parameters=mp;
	}
	/**
	 * checks if the model against is specification
	 * @param returnConstraint contains the computed constraints (if any) after the verification procedure
	 * @return 0 if the property is not satisfied, 1 if the property is satisfied, -1 if the property is satisfied with constraints.
	 */
	public int check(AbstractPredicate<S1> returnConstraint){
		this.parameters.reset();
		this.parameters.setNumStatesSpecification(this.specification.getStates().size());
		this.parameters.setNumAcceptStatesSpecification(this.specification.getAcceptStates().size());
		this.parameters.setNumStatesModel(this.model.getStates().size());
		this.parameters.setNumAcceptStatesModel(this.model.getAcceptStates().size());
		this.parameters.setNumTransparentStatesModel(this.model.getTransparentStates().size());
		
		long startIntersectionTime = System.nanoTime();   
		this.ris=new IntersectionAutomaton<S1,T1, S, T>(this.model, this.specification);
		long stopTime = System.nanoTime(); 
		
		
		this.parameters.setIntersectionTime((stopTime-startIntersectionTime)/1000000000.0);
		this.parameters.setNumAcceptingStatesIntersection(this.ris.getAcceptStates().size());
		this.parameters.setNumInitialStatesIntersection(this.ris.getInitialStates().size());
		this.parameters.setNumStatesIntersection(this.ris.getStates().size());
		this.parameters.setNumMixedStatesIntersection(this.ris.getMixedStates().size());
		
		long startEmptyTime = System.nanoTime();   
		boolean res=ris.isEmpty();
		long stopEmptyTime = System.nanoTime();   
		this.parameters.setEmptyTime((stopEmptyTime-startEmptyTime)/1000000000.0);
		
		
		if(!res){
			this.parameters.setResult(0);
			return 0;
		}
		else{
			startEmptyTime = System.nanoTime();   
			boolean resComplete=ris.isCompleteEmpty();
			stopEmptyTime = System.nanoTime();   
			this.parameters.setEmptyTime((stopEmptyTime-startEmptyTime)/1000000000.0);
				
			if(resComplete){
				this.parameters.setResult(1);
				return 1;
			}
			else{
				Brzozowski<S1,T1,S,T> brzozowski=new Brzozowski<S1,T1,S,T>(ris);
				long startConstraintTime = System.nanoTime();   
				returnConstraint=brzozowski.getConstraint();
				long stopConstraintTime = System.nanoTime(); 
				this.parameters.setConstraintComputationTime((stopConstraintTime-startConstraintTime)/1000000000.0);
				this.parameters.setTotalTime(this.parameters.getIntersectionTime()+this.parameters.getEmptyTime()+this.parameters.getConstraintComputationTime());
				System.out.println("Total time: "+(this.parameters.getIntersectionTime()+this.parameters.getEmptyTime()+this.parameters.getConstraintComputationTime()));
				return -1;
			}
		}
	}
	/**
	 * returns the intersection between the model and the specification
	 * @return the intersection automaton that contains the intersection of the model and the specification
	 */
	public IntersectionAutomaton<S1,T1, S, T> getIntersection(){
		return this.ris;
	}
	/**
	 * returns the verification results, the time required from the verification procedure, the number of states generated etc
	 * @return the resulting parameters of the verification, the number of the states of the intersection automaton the time required 
	 * from the verification procedure etc
	 */
	public ModelCheckerParameters getParameters() {
		return parameters;
	}
	
}
