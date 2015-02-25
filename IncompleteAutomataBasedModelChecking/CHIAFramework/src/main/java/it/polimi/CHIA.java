package it.polimi;

import it.polimi.automata.BA;
import it.polimi.automata.IBA;
import it.polimi.automata.state.State;
import it.polimi.automata.state.StateFactory;
import it.polimi.automata.transition.IntersectionTransition;
import it.polimi.automata.transition.IntersectionTransitionFactory;
import it.polimi.automata.transition.Transition;
import it.polimi.checker.ModelChecker;
import it.polimi.checker.ModelCheckingResults;
import it.polimi.checker.intersection.IntersectionRule;
import it.polimi.checker.intersection.impl.IntersectionRuleImpl;
import it.polimi.constraints.Constraint;
import it.polimi.contraintcomputation.ConstraintGenerator;

import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * contains the implementation of the CHIA checker
 * 
 * @author claudiomenghi
 *
 */
public class CHIA<S extends State, T extends Transition, I extends IntersectionTransition<S>> {

	/**
	 * is the logger of the ModelChecker class
	 */
	private static final Logger logger = LoggerFactory.getLogger(CHIA.class);

	/**
	 * is the Buchi Automaton that contains the claim to be verified
	 */
	private BA<S, T> claim;

	/**
	 * is the Incomplete Buchi Automaton which contains the model that must be
	 * considered in the verification procedure
	 */
	private IBA<S, T> model;

	/**
	 * Is the model checker in charge of verifying whether the property is
	 * satisfied, not satisfied or possibly satisfied
	 */
	private ModelChecker<S, T, I> mc;

	/**
	 * Contains the model checking results, the verification times the
	 * constraint computes etc
	 */
	private ModelCheckingResults mcResults;

	/**
	 * contains the factory which is used to create the states of the automaton
	 */
	private StateFactory<S> stateFactory;

	/**
	 * is the factory which is used to create the transitions of the
	 * intersection automaton
	 */
	private IntersectionTransitionFactory<S, I> intersectionTransitionFactory;

	/**
	 * creates a new CHIA checker
	 * 
	 * @param claim
	 *            is the claim to be verified
	 * @param model
	 *            is the model of the system to be considered in the
	 *            verification procedure
	 * @param stateFactory
	 *            is the factory which is used in the creation of the
	 *            states of the intersection automaton
	 * @param intersectionTransitionFactory
	 *            is the factory which is used in the creation of the transition
	 *            of the intersection automaton
	 * @throws NullPointerException
	 *             is the claim or the model of the system is null
	 */
	public CHIA(BA<S, T> claim, IBA<S, T> model, StateFactory<S> stateFactory,
			IntersectionTransitionFactory<S, I> intersectionTransitionFactory) {

		Validate.notNull(claim, "The claim cannot  be null");
		Validate.notNull(model, "The model cannot  be null");
		Validate.notNull(stateFactory,
				"The factory of the states cannot  be null");
		Validate.notNull(intersectionTransitionFactory,
				"The factory of the cannot  be null");

		this.claim = claim;
		this.model = model;
		this.stateFactory = stateFactory;
		this.intersectionTransitionFactory = intersectionTransitionFactory;
	}

	/**
	 * computes whether the claim is satisfied, not satisfied or possibly
	 * satisfied in the model
	 * 
	 * @return 1 if the claim is satisfied in the model. 0 if the claim is not
	 *         satisfied in the model. -1 if the claim is possibly satisfied in
	 *         the model
	 */
	public int check() {
		logger.info("Running CHIA");
		mcResults = new ModelCheckingResults();

		IntersectionRule<S, T, I> intersectionRule = new IntersectionRuleImpl<S, T, I>();
		mc = new ModelChecker<S, T, I>(model, claim, intersectionRule,
				stateFactory, intersectionTransitionFactory, mcResults);
		mcResults.setResult(mc.check());

		logger.info("CHIA model checking phase ended");
		return mcResults.getResult();
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
	public Constraint<S, I> getConstraint() {

		logger.info("Computing the constraint");

		Validate.isTrue(mcResults.getResult() == -1, "It is not possible to get the constraint if the property is not possibly satisfied");
		

		ConstraintGenerator<S, T, I> constraintGenerator = new ConstraintGenerator<S, T, I>(
				mc.getIntersectionAutomaton(), model,
				mc.getIntersectionStateModelStateMap(),
				intersectionTransitionFactory);

		Constraint<S, I> constraint = constraintGenerator.generateConstraint();

		logger.info("Constraint computed");

		return constraint;

	}
}
