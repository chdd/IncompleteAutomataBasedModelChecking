package it.polimi.checker;

import it.polimi.automata.BA;
import it.polimi.automata.IBA;
import it.polimi.automata.IntersectionBA;
import it.polimi.checker.emptiness.EmptinessChecker;
import it.polimi.checker.ibatransparentstateremoval.IBATransparentStateRemoval;
import it.polimi.checker.intersection.IntersectionBuilder;
import it.polimi.checker.intersection.acceptingpolicies.AcceptingPolicy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import action.CHIAAction;

import com.google.common.base.Preconditions;

/**
 * Contains the model checker used by the CHIA checker which checks whether the
 * property is satisfied, possibly satisfied or not satisfied
 * 
 * @author claudiomenghi
 */
public class Checker extends CHIAAction {

	private static final String NAME="CHECK";
	/**
	 * is the logger of the ModelChecker class
	 */
	private static final Logger logger = LoggerFactory.getLogger(Checker.class);

	/**
	 * contains the specification to be checked
	 */
	private final BA claim;

	/**
	 * contains the model to be checked
	 */
	private final IBA model;

	/**
	 * contains the builder which is used to compute the intersection automaton
	 */
	private IntersectionBuilder intersectionBuilder;

	private final AcceptingPolicy acceptingPolicy;
	/**
	 * creates a new {@link Checker}
	 * 
	 * @param model
	 *            is the model to be analyzed by the model checker
	 * @param claim
	 *            is the specification to be considered by the model checker
	 * @param acceptingPolicy
	 *            specifies the acceptingPolicy to be used in the intersection procedure
	 * @throws NullPointerException
	 *             if the model, the specification or the model checking
	 *             parameters are null
	 */
	public Checker(IBA model, BA claim, AcceptingPolicy acceptingPolicy) {
		super(NAME);
		Preconditions.checkNotNull(model,
				"The model to be checked cannot be null");
		Preconditions.checkNotNull(claim,
				"The specification to be checked cannot be null");
		Preconditions.checkNotNull(acceptingPolicy,
				"The accepting policy cannot be null");

		this.claim = claim;
		this.model = model;
		this.acceptingPolicy=acceptingPolicy;
		this.intersectionBuilder = new IntersectionBuilder(model, claim, acceptingPolicy);
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
	public SatisfactionValue check() {
		logger.info("Checking procedure started");
		
		// COMPUTES THE INTERSECTION BETWEEN THE MODEL WITHOUT TRANSPARENT
		// STATES AND THE CLAIM
		boolean empty = this.checkEmptyIntersectionMc();
		//long stopTime = System.nanoTime();
		
		// updates the time required to compute the intersection between the
		// model without transparent states and the claim
		if (!empty) {
			this.performed();
			logger.info("Checking procedure ended");
			
			return SatisfactionValue.NOTSATISFIED;
		}

		// COMPUTES THE INTERSECTION BETWEEN THE MODEL AND THE CLAIM
		boolean emptyIntersection = this.checkEmptyIntersection();
		this.performed();
		if (!emptyIntersection) {

			logger.info("Checking procedure ended");
			return SatisfactionValue.POSSIBLYSATISFIED;
		} else {

			logger.info("Checking procedure ended");
			return SatisfactionValue.SATISFIED;
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
		IBA mc = new IBATransparentStateRemoval()
				.removeTransparentStates(model);
		
		// associating the intersectionBuilder
		this.intersectionBuilder = new IntersectionBuilder(mc, claim, acceptingPolicy);

		// computing the intersection
		IntersectionBA intersectionAutomaton = this.intersectionBuilder
				.computeIntersection();

		return new EmptinessChecker(intersectionAutomaton).isEmpty();
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

		this.intersectionBuilder = new IntersectionBuilder(this.model, claim, acceptingPolicy);

		// computing the intersection
		IntersectionBA intersectionAutomaton = this.intersectionBuilder
				.computeIntersection();
		return new EmptinessChecker(intersectionAutomaton).isEmpty();
	}

	
	/**
	 * returns the intersection builder used by the model checker
	 * @return the intersection builder used by the model checker
	 */
	public IntersectionBuilder getIntersectionBuilder() {
		Preconditions
				.checkState(this.isPerformed(),
						"You must run the model checker before performing this operation");

		return this.intersectionBuilder;
	}
}
