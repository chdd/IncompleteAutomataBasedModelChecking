package it.polimi.modelchecker.brzozowski.transformers;

import it.polimi.model.impl.states.IntersectionState;
import it.polimi.model.impl.states.State;
import it.polimi.model.impl.transitions.Transition;
import it.polimi.model.interfaces.automata.IIntBA;
import it.polimi.modelchecker.brzozowski.propositions.states.EmptyProposition;
import it.polimi.modelchecker.brzozowski.propositions.states.LambdaProposition;
import it.polimi.modelchecker.brzozowski.propositions.states.LogicalItem;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections15.Transformer;

public class AcceptingStatesTransformer<
	CONSTRAINEDELEMENT extends State,
	STATE extends State, 
	TRANSITION extends Transition,
	INTERSECTIONSTATE extends IntersectionState<STATE>, 
	INTERSECTIONTRANSITION extends Transition>
	implements Transformer<
					IIntBA<
						STATE, 
						TRANSITION, 
						INTERSECTIONSTATE, 
						INTERSECTIONTRANSITION>, 
						LogicalItem<CONSTRAINEDELEMENT, INTERSECTIONTRANSITION>[]> {

	private INTERSECTIONSTATE accept;
	private List<INTERSECTIONSTATE> orderedStates;			
	
	public AcceptingStatesTransformer(List<INTERSECTIONSTATE> orderedStates,
			IIntBA<STATE, TRANSITION, INTERSECTIONSTATE, INTERSECTIONTRANSITION> intersectionAutomaton,INTERSECTIONSTATE accept){
		this.orderedStates=orderedStates;
		this.accept=accept;
	}
							
							
	@Override
	public LogicalItem<CONSTRAINEDELEMENT, INTERSECTIONTRANSITION>[] transform(
			IIntBA<STATE, TRANSITION, INTERSECTIONSTATE, INTERSECTIONTRANSITION> intersectionAutomaton) {
		if(accept==null){
			throw new IllegalArgumentException("The accepting state cannot be null");
		}
		if(!intersectionAutomaton.isAccept(accept)){
			throw new IllegalArgumentException("The state "+accept.getName()+" must be accepting");
		}
		LogicalItem<CONSTRAINEDELEMENT, INTERSECTIONTRANSITION>[] ret=new LogicalItem[intersectionAutomaton.getStates().size()];
		
		int i=0;
		// for each state in the stateOrdered vector
		for(INTERSECTIONSTATE s: this.orderedStates){
			
			// if the state is equal to the state accept
			if(accept.equals(s)){
				// I add the lambda predicate in the s[i] cell of the vector
				ret[i]=new LambdaProposition<CONSTRAINEDELEMENT, INTERSECTIONTRANSITION>();
			}
			else{
				// I add the empty predicate in the s[i] cell of the vector
				ret[i]=new EmptyProposition<CONSTRAINEDELEMENT, INTERSECTIONTRANSITION>();
			}
			i++;
		}
		// returns the vector s
		return ret;
	}

}
