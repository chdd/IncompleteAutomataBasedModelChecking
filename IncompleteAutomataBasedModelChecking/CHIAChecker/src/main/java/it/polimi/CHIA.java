package it.polimi;

import it.polimi.automata.BA;
import it.polimi.automata.IBA;
import it.polimi.automata.impl.IntBAFactoryImpl;
import it.polimi.automata.labeling.Label;
import it.polimi.automata.labeling.impl.LabelFactoryImpl;
import it.polimi.automata.state.State;
import it.polimi.automata.state.impl.StateFactoryImpl;
import it.polimi.automata.transition.Transition;
import it.polimi.automata.transition.impl.IntersectionTransitionFactoryImpl;
import it.polimi.checker.ModelChecker;
import it.polimi.checker.ModelCheckingResults;
import it.polimi.checker.intersection.impl.IntersectionRuleImpl;
import it.polimi.contraintcomputation.ConstraintGenerator;

/**
 * contains the implementation of the CHIA checker
 * 
 * @author claudiomenghi
 *
 */
public class CHIA {

	/**
	 * is the Buchi Automaton that contains the claim to be verified
	 */
	private BA<Label, State, Transition<Label>> claim;

	/**
	 * is the Incomplete Buchi Automaton which contains the model that must be
	 * considered in the verification procedure
	 */
	private IBA<Label, State, Transition<Label>> model;

	/**
	 * Is the model checker in charge of verifying whether the property is
	 * satisfied, not satisfied or possibly satisfied
	 */
	private ModelChecker<Label, State, Transition<Label>> mc;

	/**
	 * Is the constraint generator which is in charge of computing the
	 * constraints in the case the property is possibly satisfied
	 */
	private ConstraintGenerator<Label, State, Transition<Label>> cg;

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
	public CHIA(BA<Label, State, Transition<Label>> claim,
			IBA<Label, State, Transition<Label>> model) {
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
		mc = new ModelChecker<Label, State, Transition<Label>>(model, claim,
				new IntersectionRuleImpl<Label, Transition<Label>>(),
				new IntBAFactoryImpl<Label, State, Transition<Label>>(),
				new StateFactoryImpl(),
				new IntersectionTransitionFactoryImpl<Label>(), mcResults);
		mcResults.setResult(mc.check());

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
	public String getConstraint() {
		if (mcResults.getResult() != -1) {
			throw new IllegalStateException(
					"It is not possible to get the constraint if the property is not possibly satisfied");
		}
		cg = new ConstraintGenerator<Label, State, Transition<Label>>(
				this.mc.getIntersectionAutomaton(), this.model,
				this.mc.getIntersectionStateModelStateMap(),
				new LabelFactoryImpl(),
				new IntersectionTransitionFactoryImpl<Label>());
		return cg.generateConstraint();

	}
}
