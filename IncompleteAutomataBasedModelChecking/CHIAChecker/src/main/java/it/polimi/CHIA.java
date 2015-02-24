package it.polimi;

import it.polimi.automata.BA;
import it.polimi.automata.IBA;
import it.polimi.automata.state.State;
import it.polimi.automata.state.impl.StateFactoryImpl;
import it.polimi.automata.transition.Transition;
import it.polimi.automata.transition.impl.TransitionFactoryIntersectionImpl;
import it.polimi.checker.ModelChecker;
import it.polimi.checker.ModelCheckingResults;
import it.polimi.checker.intersection.impl.IntersectionRuleImpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * contains the implementation of the CHIA checker
 * 
 * @author claudiomenghi
 *
 */
public class CHIA {

	/**
	 * is the logger of the ModelChecker class
	 */
	private static final Logger logger = LoggerFactory
			.getLogger(CHIA.class);
	
	/**
	 * is the Buchi Automaton that contains the claim to be verified
	 */
	private BA<State, Transition> claim;

	/**
	 * is the Incomplete Buchi Automaton which contains the model that must be
	 * considered in the verification procedure
	 */
	private IBA<State, Transition> model;

	/**
	 * Is the model checker in charge of verifying whether the property is
	 * satisfied, not satisfied or possibly satisfied
	 */
	private ModelChecker<State, Transition> mc;

	
	/**
	 * Contains the model checking results, the verification times the
	 * constraint computes etc
	 */
	private ModelCheckingResults mcResults;

	/**
	 * creates a new CHIA checker
	 * 
	 * @param claim
	 *            is the claim to be verified
	 * @param model
	 *            is the model of the system to be considered in the
	 *            verification procedure
	 * @throws NullPointerException
	 *             is the claim or the model of the system is null
	 */
	public CHIA(BA<State, Transition> claim,
			IBA<State, Transition> model) {
		if (claim == null) {
			throw new NullPointerException("The claim cannot be null");
		}
		if (model == null) {
			throw new NullPointerException(
					"The model of the system cannot be null");
		}
		this.claim = claim;
		this.model = model;
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
		mcResults = new ModelCheckingResults();
		mc = new ModelChecker<State, Transition>(model, claim,
				new IntersectionRuleImpl<State, Transition>(),
				new StateFactoryImpl(),
				new TransitionFactoryIntersectionImpl<State>(Transition.class), mcResults);
		mcResults.setResult(mc.check());

		return mcResults.getResult();
	}

	/*
	/**
	 * returns the constraint associated with the satisfaction of the claim in
	 * the current model
	 * 
	 * @return a String which describes the constraint associated with the
	 *         satisfaction of the claim in the current model
	 * @throws IllegalStateException
	 *             if the property is not possibly satisfied
	 */
/*	public String getConstraint() {
		
		logger.info("Computing the constraint");
		
		if (mcResults.getResult() != -1) {
			throw new IllegalStateException(
					"It is not possible to get the constraint if the property is not possibly satisfied");
		}

		

	}*/
}
