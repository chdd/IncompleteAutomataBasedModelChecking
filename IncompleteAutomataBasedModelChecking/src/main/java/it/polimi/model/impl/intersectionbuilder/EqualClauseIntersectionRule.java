package it.polimi.model.impl.intersectionbuilder;

import java.util.HashSet;
import java.util.Set;

import it.polimi.model.impl.labeling.DNFFormula;
import it.polimi.model.impl.labeling.SigmaProposition;
import it.polimi.model.impl.states.State;
import it.polimi.model.impl.transitions.ConstrainedTransition;
import it.polimi.model.impl.transitions.LabelledTransition;
import it.polimi.model.interfaces.labeling.ConjunctiveClause;
import it.polimi.model.interfaces.transitions.ConstrainedTransitionFactory;

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
	STATE extends State,
	TRANSITION extends LabelledTransition,
	INTERSECTIONTRANSITION extends ConstrainedTransition<STATE>,
	INTERSECTIONTRANSITIONFACTORY extends ConstrainedTransitionFactory<STATE, INTERSECTIONTRANSITION>>
	extends
		IntersectionRule<STATE, TRANSITION, INTERSECTIONTRANSITION, INTERSECTIONTRANSITIONFACTORY> {

	
	@Override
	public INTERSECTIONTRANSITION getIntersectionTransition(
			TRANSITION modelTransition, TRANSITION claimTransition, INTERSECTIONTRANSITIONFACTORY intersectionTransitionFactory) {
		
		
		Set<ConjunctiveClause> commonClauses=new HashSet<ConjunctiveClause>();
				
		if(modelTransition.getDnfFormula().getConjunctiveClauses().contains(new SigmaProposition())){
			return intersectionTransitionFactory.create(claimTransition.getDnfFormula());
		}
		if(claimTransition.getDnfFormula().getConjunctiveClauses().contains(new SigmaProposition())){
			return intersectionTransitionFactory.create(modelTransition.getDnfFormula());
		}
		commonClauses.addAll(modelTransition.getDnfFormula().getConjunctiveClauses());
		commonClauses.retainAll(claimTransition.getDnfFormula().getConjunctiveClauses());
		
		if(!commonClauses.isEmpty()){
			return intersectionTransitionFactory.create(new DNFFormula(commonClauses));
		}
		
		return null;
	}

}
