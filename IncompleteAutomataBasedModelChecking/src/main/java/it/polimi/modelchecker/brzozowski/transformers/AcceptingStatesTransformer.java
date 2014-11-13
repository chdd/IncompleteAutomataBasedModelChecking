package it.polimi.modelchecker.brzozowski.transformers;

import java.util.ArrayList;

import it.polimi.model.impl.states.IntersectionState;
import it.polimi.model.impl.states.State;
import it.polimi.model.impl.transitions.LabelledTransition;
import it.polimi.model.interfaces.automata.drawable.DrawableIntBA;
import it.polimi.model.interfaces.transitions.ConstrainedTransitionFactory;
import it.polimi.model.interfaces.transitions.LabelledTransitionFactory;
import it.polimi.modelchecker.brzozowski.propositions.states.EmptyProposition;
import it.polimi.modelchecker.brzozowski.propositions.states.LambdaProposition;
import it.polimi.modelchecker.brzozowski.propositions.states.LogicalItem;

import org.apache.commons.collections15.Transformer;

public class AcceptingStatesTransformer<
	CONSTRAINEDELEMENT extends State,
	STATE extends State, 
	TRANSITION extends LabelledTransition<CONSTRAINEDELEMENT>,
	TRANSITIONFACTORY extends LabelledTransitionFactory<CONSTRAINEDELEMENT, TRANSITION>,
	INTERSECTIONSTATE extends IntersectionState<STATE>, 
	INTERSECTIONTRANSITION extends LabelledTransition<CONSTRAINEDELEMENT>,
	INTERSECTIONTRANSITIONFACTORY  extends ConstrainedTransitionFactory<CONSTRAINEDELEMENT,INTERSECTIONTRANSITION>> 
	implements Transformer<
					DrawableIntBA<
						CONSTRAINEDELEMENT, 
						STATE, 
						TRANSITION, 
						INTERSECTIONSTATE, 
						INTERSECTIONTRANSITION,  
						INTERSECTIONTRANSITIONFACTORY>, 
						LogicalItem<CONSTRAINEDELEMENT, INTERSECTIONTRANSITION>[]> {

	private INTERSECTIONSTATE accept;
	private ArrayList<INTERSECTIONSTATE> orderedStates;			
	
	public AcceptingStatesTransformer(ArrayList<INTERSECTIONSTATE> orderedStates,
			DrawableIntBA<CONSTRAINEDELEMENT, STATE, TRANSITION, INTERSECTIONSTATE, INTERSECTIONTRANSITION,  INTERSECTIONTRANSITIONFACTORY> intersectionAutomaton,INTERSECTIONSTATE accept){
		this.orderedStates=orderedStates;
		this.accept=accept;
	}
							
							
	@Override
	public LogicalItem<CONSTRAINEDELEMENT, INTERSECTIONTRANSITION>[] transform(
			DrawableIntBA<CONSTRAINEDELEMENT, STATE, TRANSITION, INTERSECTIONSTATE, INTERSECTIONTRANSITION, INTERSECTIONTRANSITIONFACTORY> intersectionAutomaton) {
		if(accept==null){
			throw new IllegalArgumentException("The accepting state cannot be null");
		}
		if(!intersectionAutomaton.isAccept(accept)){
			throw new IllegalArgumentException("The state "+accept.getName()+" must be accepting");
		}
		LogicalItem<CONSTRAINEDELEMENT, INTERSECTIONTRANSITION>[] ret=new LogicalItem[intersectionAutomaton.getVertexCount()];
		
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
