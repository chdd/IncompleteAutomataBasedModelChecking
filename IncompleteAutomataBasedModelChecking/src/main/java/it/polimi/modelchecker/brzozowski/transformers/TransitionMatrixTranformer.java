package it.polimi.modelchecker.brzozowski.transformers;

import it.polimi.model.impl.states.IntersectionState;
import it.polimi.model.impl.states.State;
import it.polimi.model.impl.transitions.Transition;
import it.polimi.model.interfaces.automata.IIntBA;
import it.polimi.modelchecker.brzozowski.propositions.states.EmptyProposition;
import it.polimi.modelchecker.brzozowski.propositions.states.LogicalItem;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections15.Transformer;

/**
 * transforms the intersection automata into an equivalent matrix that represents the transition relation
 * @author claudiomenghi
 *
 * @param <CONSTRAINEDELEMENT> is the type of the element which can be constrained in the intersection
 * @param <STATE> is the type of the states of the original automata
 * @param <TRANSITION> is the type of the transitions of the original automata
 * @param <TRANSITIONFACTORY> is the type of the factory which allows to create the transition of the original automata
 * @param <INTERSECTIONSTATE> is the type of the state of the intersection automata
 * @param <INTERSECTIONTRANSITION> is the type of the transitions of the intersection automata
 * @param <INTERSECTIONTRANSITIONFACTORY> is the type of the factory which allows to create the transitions of the intersection automata
 */
public class TransitionMatrixTranformer<
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
							LogicalItem<CONSTRAINEDELEMENT, INTERSECTIONTRANSITION>[][]> {

	/**
	 * contains the array of the ordered states
	 */
	private List<INTERSECTIONSTATE> orderedStates;			
	
	/**
	 * creates a new {@link TransitionMatrixTranformer} with the specified order for the states
	 * @param orderedStates is the array which contains an order between the states
	 * @throws NullPointerException if the {@link ArrayList} of the ordered states is null
	 */
	public TransitionMatrixTranformer(List<INTERSECTIONSTATE> orderedStates){
		if(orderedStates==null){
			throw new NullPointerException("The array of the ordered states cannot be null");
		}
		this.orderedStates=orderedStates;
	}
	/**
	 * 							
	 */
	@Override
	public LogicalItem<CONSTRAINEDELEMENT, INTERSECTIONTRANSITION>[][] transform(
			IIntBA< STATE, TRANSITION, INTERSECTIONSTATE, INTERSECTIONTRANSITION> intersectionAutomaton) {
		TransitionToLogicalItemTransformer<CONSTRAINEDELEMENT, STATE, TRANSITION, INTERSECTIONSTATE, INTERSECTIONTRANSITION> dnfToLogic=new 
				TransitionToLogicalItemTransformer<CONSTRAINEDELEMENT, STATE, TRANSITION, INTERSECTIONSTATE, INTERSECTIONTRANSITION>(intersectionAutomaton);
		LogicalItem<CONSTRAINEDELEMENT, INTERSECTIONTRANSITION>[][]  ret=
				new LogicalItem[intersectionAutomaton.getStates().size()][intersectionAutomaton.getStates().size()];
		
		for(int i=0; i< this.orderedStates.size(); i++){
			INTERSECTIONSTATE s1=this.orderedStates.get(i);
			for(int j=0; j<this.orderedStates.size(); j++){
				INTERSECTIONSTATE s2=this.orderedStates.get(j);
				boolean setted=false;
				for(INTERSECTIONTRANSITION t: intersectionAutomaton.getGraph().getOutEdges(s1)){
					if(intersectionAutomaton.getGraph().getDest(t).equals(s2)){
						
						ret[i][j]=dnfToLogic.transform(t);
						setted=true;
					}
				}
				if(!setted){
					ret[i][j]=new EmptyProposition<CONSTRAINEDELEMENT, INTERSECTIONTRANSITION>();
				}
			}
		}
		return ret;
	}

}
