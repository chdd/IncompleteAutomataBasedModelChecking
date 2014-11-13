package it.polimi.modelchecker;

import it.polimi.model.impl.automata.BAImpl;
import it.polimi.model.impl.automata.IBAImpl;
import it.polimi.model.impl.automata.IntBAImpl;
import it.polimi.model.impl.states.IntersectionState;
import it.polimi.model.impl.states.State;
import it.polimi.model.impl.transitions.ConstrainedTransitionFactoryImpl;
import it.polimi.model.impl.transitions.LabelledTransition;
import it.polimi.model.interfaces.automata.BA;
import it.polimi.model.interfaces.automata.IBA;
import it.polimi.model.interfaces.transitions.ConstrainedTransitionFactory;
import it.polimi.model.interfaces.transitions.LabelledTransitionFactory;
import it.polimi.modelchecker.brzozowski.Brzozowski;
import it.polimi.modelchecker.brzozowski.Constraint;

/**
 * @author claudiomenghi
 * 
 * @param <STATE> is the type of the states of the specification ({@link BAImpl}) and of the  model ({@link IBAImpl})
 * @param <TRANSITION> is the type of the transition of the specification ({@link BAImpl}) and of the  model ({@link IBAImpl})
 * @param <INTERSECTIONSTATE>  is the type of the states of the {@link IntBAImpl}
 * @param <INTERSECTIONTRANSITION>  is the type of the states of the {@link IntBAImpl}
 */
public class ModelChecker
	<
	CONSTRAINEDELEMENT extends State,
	STATE extends State, 
	TRANSITION extends LabelledTransition<CONSTRAINEDELEMENT>, 
	TRANSITIONFACTORY extends LabelledTransitionFactory<CONSTRAINEDELEMENT, TRANSITION>,
	INTERSECTIONSTATE extends IntersectionState<STATE>, 
	INTERSECTIONTRANSITION extends LabelledTransition<CONSTRAINEDELEMENT>,
	INTERSECTIONTRANSITIONFACTORY  extends ConstrainedTransitionFactory<CONSTRAINEDELEMENT,INTERSECTIONTRANSITION>> 
{
	
	
	/**
	 * contains the specification to be checked
	 */
	private BA<CONSTRAINEDELEMENT,STATE,TRANSITION, TRANSITIONFACTORY> specification;
	
	/**
	 * contains the model to be checked
	 */
	private  IBA<CONSTRAINEDELEMENT, STATE, TRANSITION, TRANSITIONFACTORY> model;
	
	/**
	 * contains the intersection automaton of the model and its specification after the model checking procedure is performed
	 */
	private IntBAImpl<CONSTRAINEDELEMENT, STATE,TRANSITION, INTERSECTIONSTATE, INTERSECTIONTRANSITION, TRANSITIONFACTORY, INTERSECTIONTRANSITIONFACTORY> ris;
	
	/**
	 * contains the results of the verification (if the specification is satisfied or not, the time required by the model checking procedure etc)
	 */
	private ModelCheckingResults<CONSTRAINEDELEMENT,STATE,TRANSITION,  INTERSECTIONSTATE, INTERSECTIONTRANSITION> verificationResults;
	
	/**
	 * creates a new {@link ModelChecker}
	 * @param model is the model to be analyzed by the model checker
	 * @param specification is the specification to be considered by the model checker
	 * @param mp is an object where the results of the verification (e.g., time required from the verification procedure are stored)
	 * @throws IllegalArgumentException if the model, the specification or the model checking parameters are null
	 */
	public ModelChecker(IBA<CONSTRAINEDELEMENT, STATE, TRANSITION, TRANSITIONFACTORY> model, BA<CONSTRAINEDELEMENT, STATE,TRANSITION, TRANSITIONFACTORY> specification, ModelCheckingResults<CONSTRAINEDELEMENT,STATE, TRANSITION, INTERSECTIONSTATE,INTERSECTIONTRANSITION> mp){
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
		this.verificationResults=mp;
	}
	/**
	 * checks if the model against is specification
	 * @param returnConstraint contains the computed constraints (if any) after the verification procedure
	 * @return 0 if the property is not satisfied, 1 if the property is satisfied, -1 if the property is satisfied with constraints.
	 */
	public int check(){
		// resets the value of the verification parameters
		this.verificationResults.reset();
		
		// SPECIFICATION
		// updates the set of the number of the states in the specification
		this.verificationResults.setNumStatesSpecification(this.specification.getStateNumber());
		// updates the number of accepting states of the specification
		this.verificationResults.setNumAcceptStatesSpecification(this.specification.getAcceptStates().size());
		
		// MODEL
		// updates the number of the states of the model
		this.verificationResults.setNumStatesModel(this.model.getStateNumber());
		// updates the number of accepting states of the model
		this.verificationResults.setNumAcceptStatesModel(this.model.getAcceptStates().size());
		// updates the number of transparent states in the model
		this.verificationResults.setNumTransparentStatesModel(this.model.getTransparentStates().size());
		
		// COMPUTES THE INTERSECTION BETWEEN THE MODEL AND THE SPECIFICATION
		long startIntersectionTime = System.nanoTime();   
		this.ris=new IntBAImpl<CONSTRAINEDELEMENT,STATE,TRANSITION, INTERSECTIONSTATE, INTERSECTIONTRANSITION, TRANSITIONFACTORY, INTERSECTIONTRANSITIONFACTORY>(this.model, this.specification, (INTERSECTIONTRANSITIONFACTORY) new ConstrainedTransitionFactoryImpl());
		long stopTime = System.nanoTime(); 
		
		// updates the time required to compute the intersection between the model and the specification
		this.verificationResults.setIntersectionTime((stopTime-startIntersectionTime)/1000000000.0);
		
		// INTERSECTION
		// sets the number of the states in the intersection
		this.verificationResults.setNumStatesIntersection(this.ris.getVertexCount());
		
		// sets the number of accepting states of the intersection
		this.verificationResults.setNumAcceptingStatesIntersection(this.ris.getAcceptStates().size());
		// sets the number of initial states in the intersection
		this.verificationResults.setNumInitialStatesIntersection(this.ris.getInitialStates().size());
		// sets the number of mixed states in the intersection
		this.verificationResults.setNumMixedStatesIntersection(this.ris.getMixedStates().size());
		
		// verifies if the intersection (without mixed states) is empty
		long startEmptyTime = System.nanoTime();   
		boolean res=ris.isEmpty(this.verificationResults);
		long stopEmptyTime = System.nanoTime();   
		// sets the time required to verify if the intersection is empty (without mixed states)
		this.verificationResults.setEmptyTime((stopEmptyTime-startEmptyTime)/1000000000.0);
		
		// if the intersection is not empty it means that the property is not satisfied
		if(!res){
			// set the verification result to zero
			this.verificationResults.setResult(0);
			// returns the verification result
			return 0;
			
		}
		else{
			// check if the automaton including its mixed states is empty
			startEmptyTime = System.nanoTime();   
			boolean resComplete=ris.isCompleteEmpty();
			stopEmptyTime = System.nanoTime();   
			// sets the time required from the emptiness procedure
			this.verificationResults.setEmptyTime(this.verificationResults.getEmptyTime()+((stopEmptyTime-startEmptyTime)/1000000000.0));
			// if the intersection is empty it means the the property is satisfied (no constrained accepting path are present)
			if(resComplete){
				// sets the result of the verification to 1
				this.verificationResults.setResult(1);
				// returns the result of the verification
				return 1;
			}
			else{
				// sets the result of the verification
				this.verificationResults.setResult(-1);
				// compute the constraints
				Brzozowski<CONSTRAINEDELEMENT,STATE,TRANSITION,INTERSECTIONSTATE,INTERSECTIONTRANSITION,TRANSITIONFACTORY, INTERSECTIONTRANSITIONFACTORY> brzozowski=new Brzozowski<CONSTRAINEDELEMENT,STATE,TRANSITION,INTERSECTIONSTATE,INTERSECTIONTRANSITION,TRANSITIONFACTORY, INTERSECTIONTRANSITIONFACTORY>(ris);
				Constraint<CONSTRAINEDELEMENT, INTERSECTIONTRANSITION> returnConstraint;
				Constraint<CONSTRAINEDELEMENT, INTERSECTIONTRANSITION> simplifiedConstraint;
				long startConstraintTime = System.nanoTime();   
				returnConstraint=brzozowski.getConstraint();
				long stopConstraintTime = System.nanoTime();
				
				long startSimplificationTime = System.nanoTime();   
				simplifiedConstraint=new Constraint<CONSTRAINEDELEMENT, INTERSECTIONTRANSITION> (
						returnConstraint.getLogicalItem().simplify());
				long stopSimplificationTime = System.nanoTime();
				
				this.verificationResults.setSimplifiedConstraint(simplifiedConstraint);
				this.verificationResults.setSimplificationTime((startSimplificationTime-stopSimplificationTime)/1000000000.0);
				
				this.verificationResults.setConstraint(returnConstraint);
				// sets the time required to compute the constraints
				this.verificationResults.setConstraintComputationTime((stopConstraintTime-startConstraintTime)/1000000000.0);
				// sets the total time required by the verification procedure
				this.verificationResults.setTotalTime(this.verificationResults.getIntersectionTime()+this.verificationResults.getEmptyTime()+this.verificationResults.getConstraintComputationTime());
				// returns -1 which indicates that the property is possibly satisfied
				return -1;
			}
		}
	}
	/**
	 * returns the intersection between the model and the specification
	 * @return the intersection automaton that contains the intersection of the model and the specification
	 */
	public IntBAImpl<CONSTRAINEDELEMENT, STATE,TRANSITION, INTERSECTIONSTATE, INTERSECTIONTRANSITION, TRANSITIONFACTORY, INTERSECTIONTRANSITIONFACTORY> getIntersection(){
		return this.ris;
	}
	/**
	 * returns the verification results, the time required from the verification procedure, the number of states generated etc
	 * @return the resulting parameters of the verification, the number of the states of the intersection automaton the time required 
	 * from the verification procedure etc
	 */
	public ModelCheckingResults<CONSTRAINEDELEMENT,STATE, TRANSITION, INTERSECTIONSTATE, INTERSECTIONTRANSITION> getParameters() {
		return verificationResults;
	}
	
}
