package it.polimi.modelchecker.intersectionbuilder;

import it.polimi.model.impl.labeling.DNFFormulaImpl;
import it.polimi.model.impl.labeling.SigmaProposition;
import it.polimi.model.impl.states.State;
import it.polimi.model.impl.transitions.Transition;
import it.polimi.model.interfaces.labeling.ConjunctiveClause;
import it.polimi.model.interfaces.transitions.ConstrainedTransitionFactory;

import java.util.HashSet;
import java.util.Set;

/**
 * Makes the transition fireable only when the clauses of the model and the claim are equals
 * @author claudiomenghi
 *
 * @param <STATE> is the type of the states of the original BA
 * @param <TRANSITION> is the type of the transitions of the two original BA
 * @param <INTERSECTIONTRANSITION> is the type of the transitions of the intersection automata
 * @param <INTERSECTIONTRANSITIONFACTORY> is the factory which allows to create the transitions of the intersection automaton
 */
public class EqualClauseIntersectionRule<
	CONSTRAINEDELEMENT extends State,
	STATE extends State,
	TRANSITION extends Transition,
	INTERSECTIONTRANSITION extends Transition,
	INTERSECTIONTRANSITIONFACTORY extends ConstrainedTransitionFactory<CONSTRAINEDELEMENT, INTERSECTIONTRANSITION>>
	extends
		IntersectionRule<CONSTRAINEDELEMENT, STATE, TRANSITION, INTERSECTIONTRANSITION, INTERSECTIONTRANSITIONFACTORY> {

	
	@Override
	public INTERSECTIONTRANSITION getIntersectionTransition(
			TRANSITION modelTransition, TRANSITION claimTransition, INTERSECTIONTRANSITIONFACTORY intersectionTransitionFactory) {
		
		
		Set<ConjunctiveClause> commonClauses=new HashSet<ConjunctiveClause>();
				
		commonClauses.addAll(modelTransition.getCondition().getConjunctiveClauses());
		commonClauses.retainAll(claimTransition.getCondition().getConjunctiveClauses());
		
		if(modelTransition.
				getCondition().
				getConjunctiveClauses().
				contains(new SigmaProposition<STATE>())){
			commonClauses.addAll(claimTransition.getCondition().getConjunctiveClauses());
		}
		if(claimTransition.
				getCondition().
				getConjunctiveClauses().
				contains(new SigmaProposition<STATE>())){
			commonClauses.addAll(modelTransition.getCondition().getConjunctiveClauses());
		}
		
		if(!commonClauses.isEmpty()){
			return intersectionTransitionFactory.create(new DNFFormulaImpl(commonClauses));
		}
		
		return null;
	}

}
