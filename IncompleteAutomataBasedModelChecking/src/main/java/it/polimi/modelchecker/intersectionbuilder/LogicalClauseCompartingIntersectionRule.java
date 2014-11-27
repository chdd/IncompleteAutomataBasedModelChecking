package it.polimi.modelchecker.intersectionbuilder;

import it.polimi.model.impl.labeling.DNFFormulaImpl;
import it.polimi.model.impl.labeling.SigmaProposition;
import it.polimi.model.impl.states.State;
import it.polimi.model.impl.transitions.Transition;
import it.polimi.model.interfaces.labeling.ConjunctiveClause;
import it.polimi.model.interfaces.transitions.ConstrainedTransitionFactory;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.collections15.Factory;

/**
 * Fires the transition only when the condition specified on the
 * {@link Transition} of the clause is satisfied by the {@link Transition} of
 * the model
 * 
 * @author claudiomenghi
 * 
 * @param <STATE>
 *            is the type of the states of the original BA
 * @param <TRANSITION>
 *            is the type of the transitions of the two original BA
 * @param <INTERSECTIONTRANSITION>
 *            is the type of the transitions of the intersection automata
 * @param <INTERSECTIONTRANSITIONFACTORY>
 *            is the factory which allows to create the transitions of the
 *            intersection automaton
 */
public class LogicalClauseCompartingIntersectionRule<CONSTRAINEDELEMENT extends State, STATE extends State, TRANSITION extends Transition, INTERSECTIONTRANSITION extends Transition, INTERSECTIONTRANSITIONFACTORY extends ConstrainedTransitionFactory<CONSTRAINEDELEMENT, INTERSECTIONTRANSITION>>
		extends
		IntersectionRule<CONSTRAINEDELEMENT, STATE, TRANSITION, INTERSECTIONTRANSITION, INTERSECTIONTRANSITIONFACTORY> {

	/**
	 * computes the intersection transition between a transition of the model
	 * and the transition of the claim
	 * 
	 * @param modelTransition
	 *            is the {@link Transition} of the model
	 * @param claimTransition
	 *            is the {@link Transition} of the claim
	 * @param intersectionTransitionFactory
	 *            contains the {@link Factory} to be used to create intersection
	 *            transition
	 * @return a new intersection transition that represents the intersection
	 *         between the transition of the model and the one of the claim, or
	 *         null if the two transitions cannot be fired together
	 * @throws NullPointerException
	 *             if the modelTransition, the claimTransition or the
	 *             intersectionTransitionFactory is null
	 * 
	 */
	@Override
	public INTERSECTIONTRANSITION getIntersectionTransition(
			TRANSITION modelTransition, TRANSITION claimTransition,
			INTERSECTIONTRANSITIONFACTORY intersectionTransitionFactory) {
		if (modelTransition == null) {
			throw new NullPointerException(
					"The model transition cannot be null");
		}
		if (claimTransition == null) {
			throw new NullPointerException(
					"The claim transition cannot be null");
		}
		if (intersectionTransitionFactory == null) {
			throw new NullPointerException(
					"The intersection factory cannot be null");
		}

		// if the model transition is labeled with SigmaProposition the
		// transition returned is labeled
		// with the condition of the claim
		if (modelTransition.getCondition().getConjunctiveClauses()
				.contains(new SigmaProposition<STATE>())) {

			return intersectionTransitionFactory.create(new DNFFormulaImpl(
					claimTransition.getCondition().getConjunctiveClauses()));
		}

		// if the transition of the claim is labeled with a SigmaProposition the
		// transition returned is labeled
		// with the condition of the model
		if (claimTransition.getCondition().getConjunctiveClauses()
				.contains(new SigmaProposition<STATE>())) {
			return intersectionTransitionFactory.create(new DNFFormulaImpl(
					modelTransition.getCondition().getConjunctiveClauses()));
		}

		// else the conjunctive clauses of the model and the claim are compared
		Set<ConjunctiveClause> commonClauses = new HashSet<ConjunctiveClause>();

		// for each ConjunctiveClause of the transition of the model
		for (ConjunctiveClause modelClause : modelTransition.getCondition()
				.getConjunctiveClauses()) {

			// for each ConjunctiveClause of the transition of the claim
			for (ConjunctiveClause claimClause : claimTransition.getCondition()
					.getConjunctiveClauses()) {

				// if the clause of the model satisfies the clause of the claim
				if (modelClause.satisfies(claimClause)) {
					commonClauses.add(modelClause);
				}
			}
		}

		if (!commonClauses.isEmpty()) {
			return intersectionTransitionFactory.create(new DNFFormulaImpl(
					commonClauses));
		}

		return null;
	}
}
