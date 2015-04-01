package it.polimi.refinementchecker;

import it.polimi.automata.BA;
import it.polimi.automata.IBA;
import it.polimi.checker.Checker;
import it.polimi.checker.ModelCheckingResults;
import it.polimi.constraints.Constraint;
import it.polimi.constraints.Replacement;
import it.polimi.constraints.SubProperty;
import it.polimi.refinementchecker.checker.AutomatonDecorator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Preconditions;

/**
 * Is used to check the refinement of a transparent state. The refinement
 * checker updates the constraint associated with the transparent state and the
 * constraints associated with the other transparent states.<br>
 * 
 * 
 * @author claudiomenghi
 */
public class ReplacementChecker {

	/**
	 * is the logger of the ModelChecker class
	 */
	private static final Logger logger = LoggerFactory
			.getLogger(ReplacementChecker.class);

	/**
	 * contains the constraint to be updated
	 */
	private final Constraint constraint;

	/**
	 * contains the refinement to be verified
	 */
	private final Replacement refinement;

	/**
	 * contains the new constraint generated
	 */
	private Constraint newConstraint;

	/**
	 * creates a new Refinement Checker. The refinement checker is used to check
	 * the refinement of a transparent state. The refinement checker updates the
	 * constraint associated with the transparent state and the constraints
	 * associated with the other transparent states.
	 * 
	 * @param constraint
	 *            is the constraint that must be considered by the
	 *            RefinementChecker
	 * @param component
	 *            is the replacement to be considered by the refinement checker
	 * @throws NullPointerException
	 *             if one of the parameters is null
	 */
	public ReplacementChecker(Constraint constraint, Replacement replacement) {
		Preconditions.checkNotNull(constraint,
				"The constraint to be checked cannot be null");
		Preconditions.checkNotNull(replacement,
				"The constraint to be checked cannot be null");
		Preconditions
				.checkArgument(
						constraint.getConstrainedStates().contains(
								replacement.getModelState()),
						"The state constrained in the refinement must be contained into the set of the states of the constraint");
		this.constraint = constraint;
		this.refinement = replacement;
	}

	public Constraint newConstraint() {
		return this.newConstraint;
	}

	/**
	 * returns the updated constraint
	 * 
	 * @return the updated constraint
	 */
	public int check() {

		logger.debug("Starting the refinement checking phase");

		logger.debug("Updating the paths with no transparent states");

		/*
		 * COMPUTING THE INTERSECTION BETWEEN THE CLAIM: the automaton
		 * associated with the SUBPROPERTY and the model: the automaton which
		 * corresponds with the REFINEMENT
		 */

		// GETTING THE CLAIM
		// gets the sub-property associated with the model state, i.e., the
		// claim automaton
		SubProperty subproperty = this.constraint
				.getSubproperties(this.refinement.getModelState());
		// sets the initial and accepting states depending on the incoming and
		// out-coming transitions
		BA claim = subproperty.getAutomaton();

		// GETTING THE MODEL
		// gets the model to be considered, i.e., the model of the refinement
		// where the transparent states have been removed
		IBA model = this.refinement.getAutomaton();

		AutomatonDecorator automatonDecorator=new AutomatonDecorator();
		automatonDecorator.decorateClaim(claim, subproperty);
		automatonDecorator.decorateModel(model, refinement);
		

		Checker checker = new Checker(model, claim, new ModelCheckingResults(
				true, true, true));
		// checking if there exists a path that does not satisfy the property
		return checker.check();
	}
}
