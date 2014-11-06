package it.polimi.modelchecker;

import it.polimi.model.impl.labeling.ConstraintProposition;
import it.polimi.model.impl.states.IntersectionState;
import it.polimi.model.impl.states.State;
import it.polimi.model.impl.transitions.LabelledTransition;
import it.polimi.model.interfaces.automata.drawable.DrawableIntBA;
import it.polimi.model.interfaces.labeling.ConjunctiveClause;
import it.polimi.model.interfaces.transitions.ConstrainedTransitionFactory;
import it.polimi.modelchecker.brzozowski.propositions.states.AtomicProposition;
import it.polimi.modelchecker.brzozowski.propositions.states.EmptyProposition;
import it.polimi.modelchecker.brzozowski.propositions.states.EpsilonProposition;
import it.polimi.modelchecker.brzozowski.propositions.states.LogicalItem;

import org.apache.commons.collections15.Transformer;

public class DnfToLogicalItemTransformer
	<	CONSTRAINEDELEMENT extends State,
		STATE extends State,
		TRANSITION extends LabelledTransition<CONSTRAINEDELEMENT>,
		INTERSECTIONSTATE extends IntersectionState<STATE>,
		INTERSECTIONTRANSITION  extends LabelledTransition<CONSTRAINEDELEMENT>,
		INTERSECTIONTRANSITIONFACTORY  extends ConstrainedTransitionFactory<CONSTRAINEDELEMENT,INTERSECTIONTRANSITION>>
		implements Transformer<INTERSECTIONTRANSITION, LogicalItem<CONSTRAINEDELEMENT, INTERSECTIONTRANSITION>> {

	private DrawableIntBA<CONSTRAINEDELEMENT, STATE, TRANSITION, INTERSECTIONSTATE, INTERSECTIONTRANSITION,  INTERSECTIONTRANSITIONFACTORY> intersectionautomaton;
	
	
	public DnfToLogicalItemTransformer(DrawableIntBA<CONSTRAINEDELEMENT, STATE, TRANSITION, INTERSECTIONSTATE, INTERSECTIONTRANSITION,  INTERSECTIONTRANSITIONFACTORY> intersectionautomaton){
		this.intersectionautomaton=intersectionautomaton;
	}
	
	
	
	@Override
	public LogicalItem<CONSTRAINEDELEMENT, INTERSECTIONTRANSITION> transform(
			INTERSECTIONTRANSITION t) {
		
		
			LogicalItem<CONSTRAINEDELEMENT, INTERSECTIONTRANSITION> item=new EmptyProposition<CONSTRAINEDELEMENT, INTERSECTIONTRANSITION>();
			for(ConjunctiveClause<CONSTRAINEDELEMENT> clause: t.getDnfFormula().getConjunctiveClauses()){
			
				if(clause instanceof ConstraintProposition){
					item=item.union(new AtomicProposition<CONSTRAINEDELEMENT, INTERSECTIONTRANSITION>(t, 
							(CONSTRAINEDELEMENT)((ConstraintProposition) clause).getConstrainedState(), 
							((ConstraintProposition) clause).getLabel()));
					
				}
				else{
					INTERSECTIONSTATE source=this.intersectionautomaton.getSource(t);
					if(this.intersectionautomaton.isMixed(source)){
						LogicalItem<CONSTRAINEDELEMENT, INTERSECTIONTRANSITION> proposition=
								new AtomicProposition<CONSTRAINEDELEMENT, INTERSECTIONTRANSITION>(t, 
										(CONSTRAINEDELEMENT)source.getS1(), 
										"λ");
						item=item.union(proposition.concatenate(new EpsilonProposition<CONSTRAINEDELEMENT, INTERSECTIONTRANSITION>(t)));
					}
					else{
						item=item.union(new EpsilonProposition<CONSTRAINEDELEMENT, INTERSECTIONTRANSITION>(t));
					}
				}
				
			}
			return item;
	}
}
