package it.polimi.checker;

import it.polimi.automata.BA;
import it.polimi.automata.IBA;
import it.polimi.automata.IntersectionBA;
import it.polimi.automata.state.State;
import it.polimi.automata.transition.Transition;
import it.polimi.checker.emptiness.EmptinessChecker;
import it.polimi.checker.ibatransparentstateremoval.IBATransparentStateRemoval;
import it.polimi.checker.intersection.IntersectionBuilder;
import it.polimi.checker.intersection.IntersectionRule;

import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Preconditions;

/**
 * Contains the model checker used by the CHIA checker which checks whether the
 * property is satisfied, possibly satisfied or not satisfied
 * 
 * @author claudiomenghi
 */
public class ModelChecker< S extends State, T extends Transition> {

	/**
	 * is the logger of the ModelChecker class
	 */
	private static final Logger logger = LoggerFactory
			.getLogger(ModelChecker.class);

	/**
	 * contains the specification to be checked
	 */
	private BA<S, T> claim;

	/**
	 * contains the model to be checked
	 */
	private IBA<S, T> model;

	/**
	 * contains the intersection automaton of the model and its specification
	 * after the model checking procedure is performed
	 */
	private IntersectionBA<S, T> intersectionAutomaton;

	/**
	 * is the intersection rule to be used in computing the intersection
	 */
	private IntersectionRule<S, T> intersectionRule;

	
	/**
	 * contains the results of the verification (if the specification is
	 * satisfied or not, the time required by the model checking procedure etc)
	 */
	private ModelCheckingResults verificationResults;

	private IntersectionBuilder<S, T> intersectionBuilder;

	/**
	 * creates a new {@link ModelChecker}
	 * 
	 * @param model
	 *            is the model to be analyzed by the model checker
	 * @param specification
	 *            is the specification to be considered by the model checker
	 * @param intersectionBAFactory
	 *            is the factory in charge of creating intersection automata
	 * @param intersectionRule
	 *            is the intersection rule to be used in computing the
	 *            intersection
	 * @param intersectionStateFactory
	 *            is the intersection factory to be used in computing the states
	 *            of the intersection
	 * @param intersectionTransitionFactory
	 *            is the intersection factory to be used in computing the
	 *            transitions of the intersection automaton
	 * @param mp
	 *            is an object where the results of the verification (e.g., time
	 *            required from the verification procedure are stored)
	 * @throws NullPointerException
	 *             if the model, the specification or the model checking
	 *             parameters are null
	 */
	public ModelChecker(IBA<S, T> model, BA<S, T> claim,
			IntersectionRule<S, T> intersectionRule,
			ModelCheckingResults mp) {
		Preconditions.checkNotNull(model, "The model to be checked cannot be null");
		Preconditions.checkNotNull(claim, "The specification to be checked cannot be null");
		Preconditions.checkNotNull(intersectionRule, "The intersection rule cannot be null");
		Preconditions.checkNotNull(mp, "The model checking parameters cannot be null");

		this.claim = claim;
		this.model = model;
		this.verificationResults = mp;
		this.intersectionRule = intersectionRule;
		this.intersectionBuilder = new IntersectionBuilder<S, T>(
				this.intersectionRule,  model,
				claim);
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
		logger.info("Checking procedure started");
		// resets the value of the verification parameters
		this.verificationResults.reset();
		logger.info("Verification results resetted");

		// SPECIFICATION
		// updates the set of the number of the states in the claim
		this.verificationResults.setNumStatesSpecification(this.claim
				.getStates().size());

		logger.info("The claims has: " + this.claim.getStates().size()
				+ " states");
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

		logger.info("The model has: " + this.model.getStates().size()
				+ " states");
		// COMPUTES THE INTERSECTION BETWEEN THE MODEL WITHOUT TRANSPARENT
		// STATES AND THE CLAIM
		long startIntersectionTime = System.nanoTime();
		boolean empty = this.checkEmptyIntersectionMc();
		long stopTime = System.nanoTime();

		double checkingTime = ((stopTime - startIntersectionTime) / 1000000000.0);
		logger.info("The emptiness checker returned: " + empty + " in: "
				+ checkingTime + "ms");

		// updates the time required to compute the intersection between the
		// model without transparent states and the claim
		this.verificationResults.setViolationTime(checkingTime);
		if (!empty) {
			logger.info("The claim is not satisfied");
			return 0;
		}

		logger.info("Checking the intersection between the claim and the original model");
		// COMPUTES THE INTERSECTION BETWEEN THE MODEL AND THE CLAIM
		long startCheckingPossible = System.nanoTime();
		boolean emptyIntersection = this.checkEmptyIntersection();
		long stopCheckingPossible = System.nanoTime();
		checkingTime = (stopCheckingPossible - startCheckingPossible) / 1000000000.0;
		logger.info("The emptiness checker returns: " + emptyIntersection
				+ " in: " + checkingTime + " ms");

		// updates the time required to compute the intersection between the
		// model without transparent states and the claim
		this.verificationResults.setPossibleViolationTime(checkingTime);
		// INTERSECTION
		// sets the number of the states in the intersection
		this.verificationResults
				.setNumStatesIntersection(this.intersectionAutomaton
						.getStates().size());

		// sets the number of accepting states of the intersection
		this.verificationResults
				.setNumAcceptingStatesIntersection(this.intersectionAutomaton
						.getAcceptStates().size());
		// sets the number of initial states in the intersection
		this.verificationResults
				.setNumInitialStatesIntersection(this.intersectionAutomaton
						.getInitialStates().size());
		// sets the number of mixed states in the intersection
		this.verificationResults
				.setNumMixedStatesIntersection(this.intersectionAutomaton
						.getMixedStates().size());
		if (!emptyIntersection) {
			logger.info("The claim is possibly satisfied");
			return -1;
		} else {
			logger.info("The claim is satisfied");
			return 1;
		}
	}

	/**
	 * performs the checking activity described at the points 2 and 3 of the
	 * paper: It extract from the model the transparent state and it computes
	 * the intersection with the claim if a path is founded a <code>true</code>
	 * result is returned meaning that the model violates the claim
	 * 
	 * @return <code>true</code> if the claim is violated in the model
	 */
	private boolean checkEmptyIntersectionMc() {

		// removes the transparent states from the model
		IBA<S, T> mc = new IBATransparentStateRemoval<S, T>()
				.transparentStateRemoval(model);
		logger.debug("Transparent states removed from the model");

		// computing the intersection
		this.intersectionAutomaton = new IntersectionBuilder<S, T>(
				this.intersectionRule, mc, claim)
				.computeIntersection();
		logger.debug("Intersection automaton computed");
		return new EmptinessChecker<S, T>(intersectionAutomaton).isEmpty();
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
	private boolean checkEmptyIntersection() {

		// computing the intersection
		this.intersectionAutomaton = this.intersectionBuilder
				.computeIntersection();
		return new EmptinessChecker<S, T>(this.intersectionAutomaton)
				.isEmpty();
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

	/**
	 * returns a map that relates each state of the model to the
	 * corresponding states of the intersection automaton
	 * 
	 * @return a map that relates each state of the model to the
	 *         corresponding states of the intersection automaton
	 */
	public Map<S, Set<S>> getModelIntersectionStateMap() {
		return this.intersectionBuilder.getModelIntersectionStateMap();
	}
	
	/**
	 * returns a map that relates each state of the claim to the
	 * corresponding states of the intersection automaton
	 * 
	 * @return a map that relates each state of the claim to the
	 *         corresponding states of the intersection automaton
	 */
	public Map<S, Set<S>> getClaimIntersectionStateMap() {
		return this.intersectionBuilder.getClaimIntersectionStateMap();
	}

	/**
	 * returns the intersection automaton
	 * 
	 * @return the intersection automaton
	 */
	public IntersectionBA<S, T> getIntersectionAutomaton() {
		return intersectionAutomaton;
	}

	public  IntersectionBuilder<S, T> getIntersectionBuilder(){
		return this.intersectionBuilder;
	}
}
