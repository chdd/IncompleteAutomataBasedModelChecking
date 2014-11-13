package it.polimi.modelchecker.brzozowski.transformers;

import java.util.ArrayList;

import it.polimi.model.impl.states.IntersectionState;
import it.polimi.model.impl.states.State;
import it.polimi.model.impl.transitions.LabelledTransition;
import it.polimi.model.interfaces.automata.drawable.DrawableIntBA;
import it.polimi.model.interfaces.transitions.ConstrainedTransitionFactory;
import it.polimi.model.interfaces.transitions.LabelledTransitionFactory;
import it.polimi.modelchecker.brzozowski.propositions.states.EmptyProposition;
import it.polimi.modelchecker.brzozowski.propositions.states.LogicalItem;

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
							LogicalItem<CONSTRAINEDELEMENT, INTERSECTIONTRANSITION>[][]> {

	/**
	 * contains the array of the ordered states
	 */
	private ArrayList<INTERSECTIONSTATE> orderedStates;			
	
	/**
	 * creates a new {@link TransitionMatrixTranformer} with the specified order for the states
	 * @param orderedStates is the array which contains an order between the states
	 * @throws NullPointerException if the {@link ArrayList} of the ordered states is null
	 */
	public TransitionMatrixTranformer(ArrayList<INTERSECTIONSTATE> orderedStates){
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
			DrawableIntBA<CONSTRAINEDELEMENT, STATE, TRANSITION, INTERSECTIONSTATE, INTERSECTIONTRANSITION, INTERSECTIONTRANSITIONFACTORY> intersectionAutomaton) {
		TransitionToLogicalItemTransformer<CONSTRAINEDELEMENT, STATE, TRANSITION, INTERSECTIONSTATE, INTERSECTIONTRANSITION, INTERSECTIONTRANSITIONFACTORY> dnfToLogic=new 
				TransitionToLogicalItemTransformer<CONSTRAINEDELEMENT, STATE, TRANSITION, INTERSECTIONSTATE, INTERSECTIONTRANSITION, INTERSECTIONTRANSITIONFACTORY>(intersectionAutomaton);
		LogicalItem<CONSTRAINEDELEMENT, INTERSECTIONTRANSITION>[][]  ret=
				new LogicalItem[intersectionAutomaton.getVertexCount()][intersectionAutomaton.getVertexCount()];
		
		for(int i=0; i< this.orderedStates.size(); i++){
			INTERSECTIONSTATE s1=this.orderedStates.get(i);
			for(int j=0; j<this.orderedStates.size(); j++){
				INTERSECTIONSTATE s2=this.orderedStates.get(j);
				boolean setted=false;
				for(INTERSECTIONTRANSITION t: intersectionAutomaton.getOutEdges(s1)){
					if(intersectionAutomaton.getDest(t).equals(s2)){
						
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
