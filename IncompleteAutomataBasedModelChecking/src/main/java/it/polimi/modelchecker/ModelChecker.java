package it.polimi.modelchecker;

import it.polimi.model.BuchiAutomaton;
import it.polimi.model.IncompleteBuchiAutomaton;
import it.polimi.model.IntersectionAutomaton;
import it.polimi.model.IntersectionState;
import it.polimi.model.State;
import it.polimi.model.Transition;
import it.polimi.modelchecker.brzozowski.predicates.AbstractConstraint;
import it.polimi.modelchecker.brzozowski.predicates.EmptyConstraint;

public class ModelChecker<S1 extends State, T1 extends Transition<S1>> {
	
	private BuchiAutomaton<S1,T1> specification;
	private  IncompleteBuchiAutomaton<S1, T1> model;
	/**
	 * contains the intersection automaton of the model and its specification after the model checking procedure is performed
	 */
	private IntersectionAutomaton<S1,T1, IntersectionState<S1>, Transition<IntersectionState<S1>>> ris;
	private ModelCheckerParameters parameters;
	
	public ModelChecker(IncompleteBuchiAutomaton<S1, T1> model, BuchiAutomaton<S1,T1> specification, ModelCheckerParameters mp){
		this.specification=specification;
		this.model=model;
		this.parameters=mp;
	}
	/**
	 * checks if the model against is specification
	 * @param returnConstraint contains the computed constraints (if any) after the verification procedure
	 * @return 0 if the property is not satisfied, 1 if the property is satisfied, -1 if the property is satisfied with constraints.
	 */
	public int check(AbstractConstraint<S1> returnConstraint){
		this.parameters.reset();
		
		this.parameters.setNumStatesSpecification(this.specification.getStates().size());
		this.parameters.setNumAcceptStatesSpecification(this.specification.getAcceptStates().size());
		this.parameters.setNumStatesModel(this.model.getStates().size());
		this.parameters.setNumAcceptStatesModel(this.model.getAcceptStates().size());
		this.parameters.setNumTransparentStatesModel(this.model.getTransparentStates().size());
		
		long startIntersectionTime = System.nanoTime();   
		this.ris=this.model.computeIntersection(this.specification);
		long stopTime = System.nanoTime(); 
		
		this.parameters.setIntersectionTime((stopTime-startIntersectionTime)/1000000000.0);
		this.parameters.setNumAcceptingStatesIntersection(this.ris.getAcceptStates().size());
		this.parameters.setNumInitialStatesIntersection(this.ris.getInitialStates().size());
		this.parameters.setNumStatesIntersection(this.ris.getStates().size());
		this.parameters.setNumMixedStatesIntersection(this.ris.getMixedStates().size());
		
		long startEmptyTime = System.nanoTime();   
		boolean res=ris.isNotEmpty();
		long stopEmptyTime = System.nanoTime();   
		this.parameters.setEmptyTime((stopEmptyTime-startEmptyTime)/1000000000.0);
		
		
		if(res){
			this.parameters.setResult(0);
			return 0;
		}
		else{
			long startConstraintTime = System.nanoTime();   
			returnConstraint=ris.getConstraint();
			long stopConstraintTime = System.nanoTime(); 
			this.parameters.setConstraintComputationTime((stopConstraintTime-startConstraintTime)/1000000000.0);
			this.parameters.setTotalTime(this.parameters.getIntersectionTime()+this.parameters.getEmptyTime()+this.parameters.getConstraintComputationTime());
			System.out.println("Total time: "+(this.parameters.getIntersectionTime()+this.parameters.getEmptyTime()+this.parameters.getConstraintComputationTime()));
			if(returnConstraint instanceof EmptyConstraint){
				this.parameters.setResult(1);
				return 1;
			}
			else{
				this.parameters.setResult(-1);
				return -1;
			}
		}
	}
	
	public IntersectionAutomaton<S1,T1, IntersectionState<S1>, Transition<IntersectionState<S1>>> getIntersection(){
		return this.ris;
	}
	/**
	 * @return the parameters
	 */
	public ModelCheckerParameters getParameters() {
		return parameters;
	}
	
}
