package it.polimi.modelchecker;

import it.polimi.model.automata.ba.BuchiAutomaton;
import it.polimi.model.automata.ba.state.State;
import it.polimi.model.automata.ba.transition.ConstrainedTransition;
import it.polimi.model.automata.ba.transition.LabelledTransition;
import it.polimi.model.automata.iba.IncompleteBuchiAutomaton;
import it.polimi.model.automata.intersection.IntersectionAutomaton;
import it.polimi.model.automata.intersection.IntersectionState;
import it.polimi.modelchecker.brzozowski.Brzozowski;
import it.polimi.modelchecker.brzozowski.Constraint;

/**
 * @author claudiomenghi
 * 
 * @param <S1> is the type of the states of the specification ({@link BuchiAutomaton}) and of the  model ({@link IncompleteBuchiAutomaton})
 * @param <T1> is the type of the transition of the specification ({@link BuchiAutomaton}) and of the  model ({@link IncompleteBuchiAutomaton})
 * @param <S>  is the type of the states of the {@link IntersectionAutomaton}
 * @param <T>  is the type of the states of the {@link IntersectionAutomaton}
 */
public class ModelChecker<S1 extends State, T1 extends LabelledTransition, S extends IntersectionState<S1>, T extends ConstrainedTransition<S1>> {
	
	
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
	private ModelCheckerParameters<S1, S> parameters;
	
	/**
	 * creates a new {@link ModelChecker}
	 * @param model is the model to be analyzed by the model checker
	 * @param specification is the specification to be considered by the model checker
	 * @param mp is an object where the results of the verification (e.g., time required from the verification procedure are stored)
	 * @throws IllegalArgumentException if the model, the specification or the model checking parameters are null
	 */
	public ModelChecker(IncompleteBuchiAutomaton<S1, T1> model, BuchiAutomaton<S1,T1> specification, ModelCheckerParameters<S1, S> mp){
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
	public int check(){
		// resets the value of the verification parameters
		this.parameters.reset();
		
		// SPECIFICATION
		// updates the set of the number of the states in the specification
		this.parameters.setNumStatesSpecification(this.specification.getVertexCount());
		// updates the number of accepting states of the specification
		this.parameters.setNumAcceptStatesSpecification(this.specification.getAcceptStates().size());
		
		// MODEL
		// updates the number of the states of the model
		this.parameters.setNumStatesModel(this.model.getVertexCount());
		// updates the number of accepting states of the model
		this.parameters.setNumAcceptStatesModel(this.model.getAcceptStates().size());
		// updates the number of transparent states in the model
		this.parameters.setNumTransparentStatesModel(this.model.getTransparentStates().size());
		
		// COMPUTES THE INTERSECTION BETWEEN THE MODEL AND THE SPECIFICATION
		long startIntersectionTime = System.nanoTime();   
		this.ris=new IntersectionAutomaton<S1,T1, S, T>(this.model, this.specification);
		long stopTime = System.nanoTime(); 
		
		// updates the time required to compute the intersection between the model and the specification
		this.parameters.setIntersectionTime((stopTime-startIntersectionTime)/1000000000.0);
		
		// INTERSECTION
		// sets the number of the states in the intersection
		this.parameters.setNumStatesIntersection(this.ris.getVertexCount());
		
		// sets the number of accepting states of the intersection
		this.parameters.setNumAcceptingStatesIntersection(this.ris.getAcceptStates().size());
		// sets the number of initial states in the intersection
		this.parameters.setNumInitialStatesIntersection(this.ris.getInitialStates().size());
		// sets the number of mixed states in the intersection
		this.parameters.setNumMixedStatesIntersection(this.ris.getMixedStates().size());
		
		// verifies if the intersection (without mixed states) is empty
		long startEmptyTime = System.nanoTime();   
		boolean res=ris.isEmpty(this.parameters);
		long stopEmptyTime = System.nanoTime();   
		// sets the time required to verify if the intersection is empty (without mixed states)
		this.parameters.setEmptyTime((stopEmptyTime-startEmptyTime)/1000000000.0);
		
		// if the intersection is not empty it means that the property is not satisfied
		if(!res){
			// set the verification result to zero
			this.parameters.setResult(0);
			// returns the verification result
			return 0;
			
		}
		else{
			// check if the automaton including its mixed states is empty
			startEmptyTime = System.nanoTime();   
			boolean resComplete=ris.isCompleteEmpty();
			stopEmptyTime = System.nanoTime();   
			// sets the time required from the emptiness procedure
			this.parameters.setEmptyTime(this.parameters.getEmptyTime()+((stopEmptyTime-startEmptyTime)/1000000000.0));
			// if the intersection is empty it means the the property is satisfied (no constrained accepting path are present)
			if(resComplete){
				// sets the result of the verification to 1
				this.parameters.setResult(1);
				// returns the result of the verification
				return 1;
			}
			else{
				// sets the result of the verification
				this.parameters.setResult(-1);
				// compute the constraints
				Brzozowski<S1,T1,S,T> brzozowski=new Brzozowski<S1,T1,S,T>(ris);
				Constraint<S1> returnConstraint;
				long startConstraintTime = System.nanoTime();   
				returnConstraint=brzozowski.getConstraint();
				long stopConstraintTime = System.nanoTime();
				this.parameters.setConstraint(returnConstraint);
				// sets the time required to compute the constraints
				this.parameters.setConstraintComputationTime((stopConstraintTime-startConstraintTime)/1000000000.0);
				// sets the total time required by the verification procedure
				this.parameters.setTotalTime(this.parameters.getIntersectionTime()+this.parameters.getEmptyTime()+this.parameters.getConstraintComputationTime());
				// returns -1 which indicates that the property is possibly satisfied
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
	public ModelCheckerParameters<S1, S> getParameters() {
		return parameters;
	}
	
}
