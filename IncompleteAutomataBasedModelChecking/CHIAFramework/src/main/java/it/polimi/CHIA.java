package it.polimi;

import it.polimi.automata.BA;
import it.polimi.automata.IBA;
import it.polimi.checker.Checker;
import it.polimi.checker.ModelCheckingResults;
import it.polimi.checker.SatisfactionValue;
import it.polimi.checker.intersection.acceptingpolicies.AcceptingPolicy;
import it.polimi.constraints.Constraint;
import it.polimi.contraintcomputation.CHIAOperation;
import it.polimi.contraintcomputation.ConstraintGenerator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Preconditions;

/**
 * contains the implementation of the CHIA checker
 * 
 * @author claudiomenghi
 *
 */
public class CHIA extends CHIAOperation{

	/**
	 * is the logger of the ModelChecker class
	 */
	private static final Logger logger = LoggerFactory.getLogger(CHIA.class);

	/**
	 * is the Buchi Automaton that contains the claim to be verified
	 */
	private final BA claim;

	/**
	 * is the Incomplete Buchi Automaton which contains the model that must be
	 * considered in the verification procedure
	 */
	private final IBA model;
	
	private final AcceptingPolicy acceptingPolicy;

	/**
	 * Is the model checker in charge of verifying whether the property is
	 * satisfied, not satisfied or possibly satisfied
	 */
	private Checker modelChecker;

	/**
	 * Contains the model checking results, the verification times the
	 * constraint computes etc
	 */
	private final ModelCheckingResults mcResults;

	/**
	 * @return the mcResults
	 */
	public ModelCheckingResults getMcResults() {
		return mcResults;
	}

	/**
	 * creates a new CHIA checker
	 * 
	 * @param claim
	 *            is the claim to be verified
	 * @param model
	 *            is the model of the system to be considered in the
	 *            verification procedure
	 * @param results
	 *            is the object in which the model checking results are stored
	 * @throws NullPointerException
	 *             if one of the parameters is null
	 */
	public CHIA(BA claim, IBA model, AcceptingPolicy acceptingPolicy, ModelCheckingResults results) {

		Preconditions.checkNotNull(claim, "The claim cannot  be null");
		Preconditions.checkNotNull(model, "The model cannot  be null");
		Preconditions.checkNotNull(results, "The model cannot  be null");
		Preconditions.checkNotNull(acceptingPolicy, "The acceptingPolicy cannot  be null");

		this.acceptingPolicy=acceptingPolicy;
		this.claim = claim;
		this.model = model;
		this.mcResults = results;
	}

	/**
	 * computes whether the claim is satisfied, not satisfied or possibly
	 * satisfied in the model
	 * 
	 * @return 1 if the claim is satisfied in the model. 0 if the claim is not
	 *         satisfied in the model. -1 if the claim is possibly satisfied in
	 *         the model
	 */
	public SatisfactionValue check() {
		logger.info("Running CHIA");

		modelChecker = new Checker(model, claim, acceptingPolicy);
		mcResults.setResult(modelChecker.check());

		logger.info("CHIA model checking phase ended");
		return mcResults.getResult();
	}

	public Constraint generateConstraint(){
		if(this.modelChecker==null){
			this.check();
		}
		ConstraintGenerator constraintGenerator=new ConstraintGenerator(this.modelChecker);
		constraintGenerator.generateConstraint();
		//return constraintGenerator.computePortReachability();
		return null;
	}
	
	public Constraint generateConstraintNoPortReachability(){
		if(this.modelChecker==null){
			this.check();
		}
		ConstraintGenerator constraintGenerator=new ConstraintGenerator(this.modelChecker);
		return constraintGenerator.generateConstraint();
	}
	
	/**
	 * returns the model checker used by CHIA
	 * 
	 * @return the model checker used by CHIA
	 */
	public Checker getModelChecker() {
		return this.modelChecker;
	}

	/**
	 * returns the constraint associated with the satisfaction of the claim in
	 * the current model
	 * 
	 * @return a String which describes the constraint associated with the
	 *         satisfaction of the claim in the current model
	 * @throws IllegalStateException
	 *             if the property is not possibly satisfied
	 */
	/*public Constraint getConstraint() {

		logger.info("Computing the constraint");

		Preconditions
				.checkArgument(
						mcResults.getResult() == -1,
						"It is not possible to get the constraint if the property is not possibly satisfied");

		ConstraintGenerator constraintGenerator = new ConstraintGenerator(
				modelChecker.getIntersectionBuilder(), mcResults);

		Constraint constraint = constraintGenerator.generateConstraint();
		logger.info("Constraint computed");

		return constraint;

	}*/
}
