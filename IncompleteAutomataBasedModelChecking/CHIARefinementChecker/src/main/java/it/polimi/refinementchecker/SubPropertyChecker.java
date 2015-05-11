package it.polimi.refinementchecker;

import it.polimi.automata.BA;
import it.polimi.automata.IBA;
import it.polimi.automata.state.State;
import it.polimi.checker.Checker;
import it.polimi.checker.ModelCheckingResults;
import it.polimi.checker.intersection.acceptingpolicies.AcceptingPolicy;
import it.polimi.constraints.Replacement;
import it.polimi.constraints.SubProperty;
import it.polimi.refinementchecker.checker.AutomatonDecorator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Preconditions;

/**
 * Is used to check the replacement of a transparent state i.e., check whether
 * the original property is satisfied, possibly satisfied or not satisfied given
 * a specific replacement.
 * 
 * It takes as input the constraint and a replacement for one of the transparent
 * states involved in the constraint. The method check returns 1 if the property
 * is satisfied given the current replacement, -1 if the property is possibly
 * satisfied or 0 if the property is not satisfied. When a -1 value is generated
 * the satisfaction of the property may depends on the refinement of other
 * transparent states involved in the constraint or on the refinement of the
 * transparent states of the model specified into the replacement itself.
 * 
 * 
 * @author claudiomenghi
 */
public class SubPropertyChecker {

	/**
	 * is the logger of the ModelChecker class
	 */
	private static final Logger logger = LoggerFactory
			.getLogger(SubPropertyChecker.class);

	/**
	 * contains the replacement to be verified
	 */
	private final Replacement replacement;

	/**
	 * the sub-property to be considered
	 */
	private final SubProperty subproperty;

	private final AcceptingPolicy acceptingPolicy;
	/**
	 * the checker used to check the refinement
	 */
	private Checker checker;

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
	public SubPropertyChecker(SubProperty subProperty, Replacement replacement, AcceptingPolicy acceptingPolicy) {
		Preconditions.checkNotNull(subProperty,
				"The constraint to be checked cannot be null");
		Preconditions.checkNotNull(replacement,
				"The constraint to be checked cannot be null");
		Preconditions.checkNotNull(acceptingPolicy,
				"The acceptingPolicy cannot be null");
		this.acceptingPolicy=acceptingPolicy;
		Preconditions
				.checkArgument(
						subProperty.getModelState().equals(
								replacement.getModelState()),
						"The sub-property and the replacement must refer to the same model state\\"
								+ "The sub-property refers to the model state"
								+ subProperty.getModelState()
								+ "\\"
								+ "while the replacement refers to the model state"
								+ replacement.getModelState());
		this.replacement = replacement;
		this.subproperty = subProperty;
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

		// searching for paths that starts internally into the component
		BA claim = subproperty.getAutomaton().clone();
		IBA model = this.replacement.getAutomaton().clone();
		int internalRes=this.searchInternalStartingPaths(model, claim);
		if(internalRes==0){
			checker.getVerificationResults().setResult(0);
			return 0;
		}
		
		// searching for paths that arrives from out-side
		// sets the initial and accepting states depending on the incoming and
		// out-coming transitions
		claim = subproperty.getAutomaton().clone();
		for (State s : claim.getInitialStates()) {
			claim.removeInitialState(s);
		}
	
		model = this.replacement.getAutomaton().clone();
		for (State s : model.getInitialStates()) {
			model.removeInitialState(s);
		}
		int extenalRes=this.searchExternalStartingPaths(model, claim);
		if(extenalRes==0){
			checker.getVerificationResults().setResult(0);
			return 0;
		}
		if (internalRes == -1 || extenalRes == -1) {
			checker.getVerificationResults().setResult(-1);
			return -1;
		}
		checker.getVerificationResults().setResult(1);
		return 1;
	}

	private int searchInternalStartingPaths(IBA model, BA claim) {
		AutomatonDecorator automatonDecorator = new AutomatonDecorator();

		// ------------------------------------------------------------------
		// searching for infinite violating paths
		// ------------------------------------------------------------------
		checker = new Checker(model, claim, acceptingPolicy, new ModelCheckingResults(true,
				true, true));

		int infiniteres = checker.check();
		// if the result is 0 it means that there exists an infinite violating
		// path:
		// a path that connects a green incoming ports that is infinite and
		// accepting inside the refinement of the replacement
		if (infiniteres == 0) {
			checker.getVerificationResults().setResult(0);
			return infiniteres;
		}
		// ------------------------------------------------------------------
		// searching for finite violating paths
		// ------------------------------------------------------------------
		// it also add the stuttering character
		automatonDecorator
				.decorateRedFinalStatesSubproperty(claim, subproperty);
		automatonDecorator.decorateFinalStatesReplacement(model, replacement);
		checker = new Checker(model, claim, acceptingPolicy, new ModelCheckingResults(true,
				true, true));
		int finiteres = checker.check();
		// if the result is 0 it means that there exists an finite violating
		// path:
		// a path that connects a green incoming ports with a red outgoing port
		if (finiteres == 0) {
			checker.getVerificationResults().setResult(0);
			return finiteres;
		}

		if (finiteres == -1 || infiniteres == -1) {
			checker.getVerificationResults().setResult(-1);
			return -1;
		}
		checker.getVerificationResults().setResult(1);
		return 1;
	}

	private int searchExternalStartingPaths(IBA model, BA claim) {
		AutomatonDecorator automatonDecorator = new AutomatonDecorator();
		automatonDecorator.decorateGreenInitialStatesSubproperty(claim,
				subproperty);
		automatonDecorator.decorateInitialStatesReplacement(model, replacement);

		// ------------------------------------------------------------------
		// searching for infinite violating paths
		// ------------------------------------------------------------------
		checker = new Checker(model, claim, acceptingPolicy, new ModelCheckingResults(true,
				true, true));

		int infiniteres = checker.check();
		// if the result is 0 it means that there exists an infinite violating
		// path:
		// a path that connects a green incoming ports that is infinite and
		// accepting inside the refinement of the replacement
		if (infiniteres == 0) {
			checker.getVerificationResults().setResult(0);
			return infiniteres;
		}
		// ------------------------------------------------------------------
		// searching for finite violating paths
		// ------------------------------------------------------------------
		// it also add the stuttering character
		automatonDecorator
				.decorateRedFinalStatesSubproperty(claim, subproperty);
		automatonDecorator.decorateFinalStatesReplacement(model, replacement);
		checker = new Checker(model, claim, acceptingPolicy, new ModelCheckingResults(true,
				true, true));
		int finiteres = checker.check();
		// if the result is 0 it means that there exists an finite violating
		// path:
		// a path that connects a green incoming ports with a red outgoing port
		if (finiteres == 0) {
			checker.getVerificationResults().setResult(0);
			return finiteres;
		}

		if (finiteres == -1 || infiniteres == -1) {
			checker.getVerificationResults().setResult(-1);
			return -1;
		}
		checker.getVerificationResults().setResult(1);
		return 1;

	}

	public Checker getChecker() {
		return this.checker;
	}

	/**
	 * @return the replacement considered by the replacement checker
	 */
	public Replacement getReplacement() {
		return replacement;
	}

	/**
	 * @return the sub-property considered by the replacement checker
	 */
	public SubProperty getSubproperty() {
		return subproperty;
	}
}
