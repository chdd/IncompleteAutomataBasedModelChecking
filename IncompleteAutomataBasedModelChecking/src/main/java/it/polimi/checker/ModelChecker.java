package it.polimi.checker;

import it.polimi.automata.BA;
import it.polimi.automata.IBA;
import it.polimi.automata.IntersectionBA;
import it.polimi.automata.IntersectionBAFactory;
import it.polimi.automata.labeling.Label;
import it.polimi.automata.state.State;
import it.polimi.automata.state.StateFactory;
import it.polimi.automata.transition.Transition;
import it.polimi.automata.transition.TransitionFactory;
import it.polimi.checker.emptiness.EmptinessChecker;
import it.polimi.checker.ibaTransparentStateRemoval.IBATransparentStateRemoval;
import it.polimi.checker.intersection.IntersectionBuilder;
import it.polimi.checker.intersection.IntersectionRule;
import it.polimi.checker.intersection.impl.IntersectionRuleImpl;
import it.polimi.modelchecker.brzozowski.Brzozowski;
import it.polimi.modelchecker.brzozowski.Constraint;

/**
 * @author claudiomenghi
 * 
 */
public class ModelChecker<LABEL extends Label, STATE extends State, TRANSITION extends Transition<LABEL>>

{

	/**
	 * contains the specification to be checked
	 */
	private BA<LABEL, STATE, TRANSITION> claim;

	/**
	 * contains the model to be checked
	 */
	private IBA<LABEL, STATE, TRANSITION> model;

	/**
	 * contains the intersection automaton of the model and its specification
	 * after the model checking procedure is performed
	 */
	private IntersectionBA<LABEL, STATE, TRANSITION> ris;

	private IntersectionRule<LABEL, TRANSITION> intersectionRule;

	private StateFactory<STATE> intersectionStateFactory;
	private IntersectionBAFactory<LABEL, STATE, TRANSITION> intersectionBAFactory;
	private TransitionFactory<LABEL, TRANSITION> intersectionTransitionFactory;

	/**
	 * contains the results of the verification (if the specification is
	 * satisfied or not, the time required by the model checking procedure etc)
	 */
	private ModelCheckingResults verificationResults;

	/**
	 * creates a new {@link ModelChecker}
	 * 
	 * @param model
	 *            is the model to be analyzed by the model checker
	 * @param specification
	 *            is the specification to be considered by the model checker
	 * @param mp
	 *            is an object where the results of the verification (e.g., time
	 *            required from the verification procedure are stored)
	 * @throws IllegalArgumentException
	 *             if the model, the specification or the model checking
	 *             parameters are null
	 */
	public ModelChecker(IBA<LABEL, STATE, TRANSITION> model,
			BA<LABEL, STATE, TRANSITION> claim, ModelCheckingResults mp) {
		if (model == null) {
			throw new IllegalArgumentException(
					"The model to be checked cannot be null");
		}
		if (claim == null) {
			throw new IllegalArgumentException(
					"The specification to be checked cannot be null");
		}
		if (mp == null) {
			throw new IllegalArgumentException(
					"The model checking parameters cannot be null");
		}

		this.claim = claim;
		this.model = model;
		this.verificationResults = mp;
		this.intersectionRule = new IntersectionRuleImpl<LABEL, TRANSITION>();

	}

	/**
	 * checks if the model against is specification
	 * 
	 * @param returnConstraint
	 *            contains the computed constraints (if any) after the
	 *            verification procedure
	 * @return 0 if the property is not satisfied, 1 if the property is
	 *         satisfied, -1 if the property is satisfied with constraints.
	 */
	public int check() {
		// resets the value of the verification parameters
		this.verificationResults.reset();

		// SPECIFICATION
		// updates the set of the number of the states in the claim
		this.verificationResults.setNumStatesSpecification(this.claim
				.getStates().size());
		// updates the number of accepting states of the claim
		this.verificationResults.setNumAcceptStatesSpecification(this.claim
				.getAcceptStates().size());

		// MODEL
		// updates the number of the states of the model
		this.verificationResults.setNumStatesModel(this.model.getStates()
				.size());
		// updates the number of accepting states of the model
		this.verificationResults.setNumAcceptStatesModel(this.model
				.getAcceptStates().size());
		// updates the number of transparent states in the model
		this.verificationResults.setNumTransparentStatesModel(this.model
				.getTransparentStates().size());

		// COMPUTES THE INTERSECTION BETWEEN THE MODEL WITHOUT TRANSPARENT
		// STATES AND THE CLAIM
		long startIntersectionTime = System.nanoTime();
		boolean violated = this.checkViolation();
		long stopTime = System.nanoTime();

		// updates the time required to compute the intersection between the
		// model without transparent states and the claim
		this.verificationResults
				.setViolationTime((stopTime - startIntersectionTime) / 1000000000.0);
		if (violated) {
			return -1;
		}

		// COMPUTES THE INTERSECTION BETWEEN THE MODEL AND THE CLAIM
		long startCheckingPossible = System.nanoTime();
		boolean possiblyViolated = this.checkPossibleViolation();
		long stopCheckingPossible = System.nanoTime();

		// updates the time required to compute the intersection between the
		// model without transparent states and the claim
		this.verificationResults
				.setPossibleViolationTime((stopCheckingPossible - startCheckingPossible) / 1000000000.0);
		// INTERSECTION
		// sets the number of the states in the intersection
		this.verificationResults.setNumStatesIntersection(this.ris.getStates()
				.size());

		// sets the number of accepting states of the intersection
		this.verificationResults.setNumAcceptingStatesIntersection(this.ris
				.getAcceptStates().size());
		// sets the number of initial states in the intersection
		this.verificationResults.setNumInitialStatesIntersection(this.ris
				.getInitialStates().size());
		// sets the number of mixed states in the intersection
		this.verificationResults.setNumMixedStatesIntersection(this.ris
				.getMixedStates().size());
		if (!possiblyViolated) {
			return 1;
		}

		
		return 0;
	}

	/**
	 * performs the checking activity described at the points 2 and 3 of the
	 * paper: It extract from the model the transparent state and it computes
	 * the intersection with the claim if a path is founded a <code>true</code>
	 * result is returned meaning that the model violates the claim
	 * 
	 * @return <code>true</code> if the claim is violated in the model
	 */
	private boolean checkViolation() {

		// removes the transparent states from the model
		IBA<LABEL, STATE, TRANSITION> mc = new IBATransparentStateRemoval<LABEL, STATE, TRANSITION>()
				.transparentStateRemoval(model);
		// computing the intersection
		BA<LABEL, STATE, TRANSITION> intersection = new IntersectionBuilder<LABEL, STATE, TRANSITION>(
				this.intersectionRule, intersectionStateFactory,
				intersectionBAFactory, intersectionTransitionFactory, mc, claim)
				.computeIntersection();
		return !new EmptinessChecker<LABEL, STATE, TRANSITION>(intersection)
				.isEmpty();
	}

	/**
	 * performs the checking activity described at the points 4 and 5 of the
	 * paper: It checks the intersection of the model with the transparent
	 * states and intersection with the claim. If a path is founded a
	 * <code>true</code> result is returned meaning that the model possibly
	 * violates the claim
	 * 
	 * @return <code>true</code> if the claim is possibly violated in the model
	 */
	private boolean checkPossibleViolation() {

		// computing the intersection
		this.ris = new IntersectionBuilder<LABEL, STATE, TRANSITION>(
				this.intersectionRule, intersectionStateFactory,
				intersectionBAFactory, intersectionTransitionFactory, model,
				claim).computeIntersection();
		return !new EmptinessChecker<LABEL, STATE, TRANSITION>(this.ris)
				.isEmpty();
	}
	
	private boolean computeConstraints() {
		Brzozowski<CONSTRAINEDELEMENT, STATE, TRANSITION, INTERSECTIONSTATE, INTERSECTIONTRANSITION> brzozowski = new Brzozowski<CONSTRAINEDELEMENT, STATE, TRANSITION, INTERSECTIONSTATE, INTERSECTIONTRANSITION>(
				ris);
		Constraint<CONSTRAINEDELEMENT, INTERSECTIONTRANSITION> returnConstraint;
		Constraint<CONSTRAINEDELEMENT, INTERSECTIONTRANSITION> simplifiedConstraint;
		long startConstraintTime = System.nanoTime();
		returnConstraint = brzozowski.getConstraint();
		long stopConstraintTime = System.nanoTime();

		long startSimplificationTime = System.nanoTime();
		simplifiedConstraint = new Constraint<CONSTRAINEDELEMENT, INTERSECTIONTRANSITION>(
				returnConstraint.getLogicalItem().simplify());
		long stopSimplificationTime = System.nanoTime();

		this.verificationResults
				.setSimplifiedConstraint(simplifiedConstraint);
		this.verificationResults
				.setSimplificationTime((startSimplificationTime - stopSimplificationTime) / 1000000000.0);

		this.verificationResults.setConstraint(returnConstraint);
		// sets the time required to compute the constraints
		this.verificationResults
				.setConstraintComputationTime((stopConstraintTime - startConstraintTime) / 1000000000.0);
		// sets the total time required by the verification procedure
		this.verificationResults.setTotalTime(this.verificationResults
				.getViolationTime()
				+ this.verificationResults.getPossibleViolationTime()
				+ this.verificationResults
						.getConstraintComputationTime());
		// returns -1 which indicates that the property is possibly
		// satisfied
		return -1;
	}

	/**
	 * returns the intersection between the model and the specification
	 * 
	 * @return the intersection automaton that contains the intersection of the
	 *         model and the specification
	 */
	public IntersectionBA<LABEL, STATE, TRANSITION> getIntersection() {
		return this.ris;
	}

	/**
	 * returns the verification results, the time required from the verification
	 * procedure, the number of states generated etc
	 * 
	 * @return the resulting parameters of the verification, the number of the
	 *         states of the intersection automaton the time required from the
	 *         verification procedure etc
	 */
	public ModelCheckingResults getVerificationTimes() {
		return verificationResults;
	}

}
