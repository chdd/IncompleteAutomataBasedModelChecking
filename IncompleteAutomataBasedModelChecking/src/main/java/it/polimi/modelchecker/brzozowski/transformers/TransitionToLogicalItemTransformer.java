package it.polimi.modelchecker.brzozowski.transformers;

import it.polimi.model.impl.labeling.ConstraintProposition;
import it.polimi.model.impl.states.IntersectionState;
import it.polimi.model.impl.states.State;
import it.polimi.model.impl.transitions.Transition;
import it.polimi.model.interfaces.automata.IIntBA;
import it.polimi.model.interfaces.labeling.ConjunctiveClause;
import it.polimi.modelchecker.brzozowski.Brzozowski;
import it.polimi.modelchecker.brzozowski.propositions.states.AtomicProposition;
import it.polimi.modelchecker.brzozowski.propositions.states.EmptyProposition;
import it.polimi.modelchecker.brzozowski.propositions.states.EpsilonProposition;
import it.polimi.modelchecker.brzozowski.propositions.states.LogicalItem;

import org.apache.commons.collections15.Transformer;

/**
 * given a transition returns the logical item to be injected in the matrix representation of the {@link Brzozowski} system 
 * @author claudiomenghi
 *
 * @param <CONSTRAINEDELEMENT> is the type of the element which can be constrained in the intersection
 * @param <STATE> is the type of the states of the original automata
 * @param <TRANSITION> is the type of the transitions of the original automata
 * @param <INTERSECTIONSTATE> is the type of the state of the intersection automata
 * @param <INTERSECTIONTRANSITION> is the type of the transitions of the intersection automata
 * @param <INTERSECTIONTRANSITIONFACTORY> is the type of the factory which allows to create the transitions of the intersection automata
 */
public class TransitionToLogicalItemTransformer
		<CONSTRAINEDELEMENT extends State,
		STATE extends State,
		TRANSITION extends Transition,
		INTERSECTIONSTATE extends IntersectionState<STATE>,
		INTERSECTIONTRANSITION  extends Transition>
		implements Transformer<INTERSECTIONTRANSITION, LogicalItem<CONSTRAINEDELEMENT, INTERSECTIONTRANSITION>> {

	/**
	 * is the {@link DrawableIntBA} that contains the INTERSECTIONTRANSITION to be converted
	 */
	private IIntBA<STATE, TRANSITION, INTERSECTIONSTATE, INTERSECTIONTRANSITION> intersectionautomaton;
	
	/**
	 * creates a new {@link Transformer} that convert a transition into a {@link LogicalItem} to be inserted in the {@link Brzozowski} representation
	 * @param intersectionautomaton is the {@link DrawableIntBA} that contains the INTERSECTIONTRANSITION to be converted
	 * @throws NullPointerException if the {@link DrawableIntBA} is null
	 */
	public TransitionToLogicalItemTransformer(IIntBA<STATE, TRANSITION, INTERSECTIONSTATE, INTERSECTIONTRANSITION> intersectionautomaton){
		if(intersectionautomaton==null){
			throw new NullPointerException("The intersection automaton cannot be null");
		}
		this.intersectionautomaton=intersectionautomaton;
	}
	
	
	
	@Override
	public LogicalItem<CONSTRAINEDELEMENT, INTERSECTIONTRANSITION> transform(
			INTERSECTIONTRANSITION t) {
		
		
			LogicalItem<CONSTRAINEDELEMENT, INTERSECTIONTRANSITION> item=new EmptyProposition<CONSTRAINEDELEMENT, INTERSECTIONTRANSITION>();
			for(ConjunctiveClause clause: t.getCondition().getConjunctiveClauses()){
			
				if(clause instanceof ConstraintProposition){
					item=item.union(new AtomicProposition<CONSTRAINEDELEMENT, INTERSECTIONTRANSITION>(t, 
							(CONSTRAINEDELEMENT)((ConstraintProposition) clause).getConstrainedState(), 
							((ConstraintProposition) clause).getLabel(), false));
					
				}
				else{
					INTERSECTIONSTATE source=this.intersectionautomaton.getGraph().getSource(t);
					if(this.intersectionautomaton.isMixed(source)){
						LogicalItem<CONSTRAINEDELEMENT, INTERSECTIONTRANSITION> proposition=
								new AtomicProposition<CONSTRAINEDELEMENT, INTERSECTIONTRANSITION>(t, 
										(CONSTRAINEDELEMENT)source.getS1(), 
										"λ", true);
						//item=item.union(proposition.concatenate(new EpsilonProposition<CONSTRAINEDELEMENT, INTERSECTIONTRANSITION>(t)));
						item=item.union(proposition);
					}
					else{
						item=item.union(new EpsilonProposition<CONSTRAINEDELEMENT, INTERSECTIONTRANSITION>(t));
					}
				}
				
			}
			return item;
	}
}
