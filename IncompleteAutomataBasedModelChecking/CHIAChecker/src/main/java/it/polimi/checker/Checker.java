package it.polimi.checker;

import java.util.Stack;

import it.polimi.automata.BA;
import it.polimi.automata.IBA;
import it.polimi.automata.IntersectionBA;
import it.polimi.automata.state.IntersectionStateFactory;
import it.polimi.automata.state.State;
import it.polimi.automata.transition.Transition;
import it.polimi.checker.emptiness.EmptinessChecker;
import it.polimi.checker.ibablackboxstateremove.IBABlackBoxRemover;
import it.polimi.checker.intersection.IntersectionBuilder;
import it.polimi.checker.intersection.IntersectionTransitionBuilder;
import it.polimi.checker.intersection.acceptingpolicies.AcceptingPolicy;
import action.CHIAAction;

import com.google.common.base.Preconditions;

/**
 * Contains the model checker used by the CHIA checker which checks whether the
 * property is satisfied, possibly satisfied or not satisfied
 * 
 * @author claudiomenghi
 */
public class Checker extends CHIAAction<SatisfactionValue> {

	private static final String NAME = "CHECK";

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
	private IntersectionBuilder lowerIntersectionBuilder;

	/**
	 * contains the builder which is used to compute the intersection automaton
	 */
	private IntersectionBuilder upperIntersectionBuilder;

	private final AcceptingPolicy acceptingPolicy;
	private SatisfactionValue satisfactionValue;

	private final IntersectionStateFactory intersectionStateFactory;
	
	/**
	 * is the intersection transition builder to be used in the computation of the intersection automaton
	 */
	private final IntersectionTransitionBuilder intersectionTransitionBuilder;
	
	private Stack<State> stateCounterexample;
	private Stack<Transition> transitionCounterexample;

	/**
	 * creates a new {@link Checker}
	 * 
	 * @param model
	 *            is the model to be analyzed by the model checker
	 * @param claim
	 *            is the specification to be considered by the model checker
	 * @param acceptingPolicy
	 *            specifies the acceptingPolicy to be used in the intersection
	 *            procedure
	 * @param intersectionStateFactory
	 *            is the factory to be used in the creation of the states of the
	 *            intersection automaton
	 * @throws NullPointerException
	 *             if the model, the specification or the model checking
	 *             parameters are null
	 */
	public Checker(IBA model, BA claim, AcceptingPolicy acceptingPolicy,
			IntersectionStateFactory intersectionStateFactory, IntersectionTransitionBuilder intersectionTransitionBuilder) {
		super(NAME);
		Preconditions.checkNotNull(model,
				"The model to be checked cannot be null");
		Preconditions.checkNotNull(claim,
				"The specification to be checked cannot be null");
		Preconditions.checkNotNull(acceptingPolicy,
				"The accepting policy cannot be null");
		Preconditions.checkNotNull(intersectionStateFactory,
				"The intersection state factory cannot be null");
		Preconditions.checkNotNull(intersectionTransitionBuilder, "the intersection transition builder cannot be null");

		this.claim = claim;
		this.model = model;
		this.acceptingPolicy = acceptingPolicy;
		this.intersectionStateFactory = intersectionStateFactory;
		this.intersectionTransitionBuilder=intersectionTransitionBuilder;
	}

	/**
	 * creates a new {@link Checker}
	 * 
	 * @param model
	 *            is the model to be analyzed by the model checker
	 * @param claim
	 *            is the specification to be considered by the model checker
	 * @param acceptingPolicy
	 *            specifies the acceptingPolicy to be used in the intersection
	 *            procedure
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
		this.acceptingPolicy = acceptingPolicy;
		this.intersectionStateFactory = new IntersectionStateFactory();
		this.intersectionTransitionBuilder=new IntersectionTransitionBuilder();
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
	public SatisfactionValue perform() {

		if (!this.isPerformed()) {

			// COMPUTES THE INTERSECTION BETWEEN THE MODEL WITHOUT BLACK BOX
			// STATES AND THE CLAIM
			boolean empty = this.checkEmptyIntersectionMc();
			// long stopTime = System.nanoTime();

			// updates the time required to compute the intersection between the
			// model without black box states and the claim
			if (!empty) {
				this.performed();
				this.satisfactionValue = SatisfactionValue.NOTSATISFIED;
				return SatisfactionValue.NOTSATISFIED;
			}

			// COMPUTES THE INTERSECTION BETWEEN THE MODEL AND THE CLAIM
			boolean emptyIntersection = this.checkEmptyIntersection();
			this.performed();
			if (!emptyIntersection) {
				this.satisfactionValue = SatisfactionValue.POSSIBLYSATISFIED;
				return SatisfactionValue.POSSIBLYSATISFIED;
			} else {

				this.satisfactionValue = SatisfactionValue.SATISFIED;
				return SatisfactionValue.SATISFIED;
			}
		}
		return this.satisfactionValue;
	}

	/**
	 * performs the checking activity described at the points 2 and 3 of the
	 * paper: It removes from the model the black box states and it computes
	 * the intersection with the claim if a path is founded a <code>true</code>
	 * result is returned meaning that the model violates the claim
	 * 
	 * @return <code>true</code> if the claim is violated in the model
	 */
	private boolean checkEmptyIntersectionMc() {

		// removes the black box states from the model
		IBA mc = new IBABlackBoxRemover(model)
				.removeBlackBoxes();

		// associating the intersectionBuilder
		this.lowerIntersectionBuilder = new IntersectionBuilder(mc, claim,
				acceptingPolicy, this.intersectionStateFactory, this.intersectionTransitionBuilder);

		// computing the intersection
		IntersectionBA intersectionAutomaton = this.lowerIntersectionBuilder
				.perform();

		EmptinessChecker mcEmptinessChecker=new EmptinessChecker(intersectionAutomaton);
		boolean result=mcEmptinessChecker.isEmpty();
		this.stateCounterexample=mcEmptinessChecker.getCounterExample();
		this.transitionCounterexample=mcEmptinessChecker.getTransitionCounterExample();
		return result;
	}

	/**
	 * performs the checking activity described at the points 4 and 5 of the
	 * paper: It checks the intersection of the model with the black box
	 * states and intersection with the claim. If a path is founded a
	 * <code>true</code> result is returned meaning that the model possibly
	 * violates the claim
	 * 
	 * @return <code>true</code> if the claim is possibly violated in the model
	 */
	private boolean checkEmptyIntersection() {

		this.upperIntersectionBuilder = new IntersectionBuilder(this.model,
				claim, acceptingPolicy, this.intersectionStateFactory, this.intersectionTransitionBuilder);

		// computing the intersection
		IntersectionBA intersectionAutomaton = this.upperIntersectionBuilder
				.perform();
		return new EmptinessChecker(intersectionAutomaton).isEmpty();
	}

	/**
	 * returns the intersection builder used by the model checker
	 * 
	 * @return the intersection builder used by the model checker
	 */
	public IntersectionBuilder getUpperIntersectionBuilder() {
		Preconditions
				.checkState(this.isPerformed(),
						"You must run the model checker before performing this operation");

		return this.upperIntersectionBuilder;
	}

	public IntersectionBA getUpperIntersectionBA() {
		Preconditions
				.checkState(this.isPerformed(),
						"You must run the model checker before performing this operation");

		Preconditions.checkState(this.upperIntersectionBuilder != null,
				"The lower upper autonaton has not been computed");
		return this.upperIntersectionBuilder.getIntersectionAutomaton();

	}

	public IntersectionBA getLowerIntersectionBA() {
		Preconditions
				.checkState(this.isPerformed(),
						"You must run the model checker before performing this operation");
		Preconditions.checkState(this.lowerIntersectionBuilder != null,
				"The lower intersection autonaton has not been computed");
		return this.lowerIntersectionBuilder.getIntersectionAutomaton();

	}

	public int getIntersectionAutomataSize() {
		int size = 0;
		if (this.lowerIntersectionBuilder != null) {
			size = size
					+ this.lowerIntersectionBuilder.getIntersectionAutomaton()
							.size();
		}
		if (this.upperIntersectionBuilder != null) {
			size = size
					+ this.upperIntersectionBuilder.getIntersectionAutomaton()
							.size();
		}
		return size;
	}

	/**
	 * @return the stateCounterexample
	 */
	public Stack<State> getStateCounterexample() {
		return stateCounterexample;
	}

	

	/**
	 * @return the transitionCounterexample
	 */
	public Stack<Transition> getTransitionCounterexample() {
		return transitionCounterexample;
	}
}
