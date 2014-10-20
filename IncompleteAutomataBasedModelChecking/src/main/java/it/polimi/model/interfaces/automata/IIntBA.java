package it.polimi.model.interfaces.automata;

import it.polimi.model.impl.automata.IntBAImpl;
import it.polimi.model.impl.states.IntersectionState;
import it.polimi.model.impl.states.State;
import it.polimi.model.impl.transitions.ConstrainedTransition;
import it.polimi.model.impl.transitions.LabelledTransition;
import it.polimi.model.interfaces.transitions.ConstrainedTransitionFactoryInterface;

/**
 * is the interface the {@link IntBAImpl} must implement
 * @author claudiomenghi
 *
 * @param <STATE> is the type of the states of the two original automata
 * @param <TRANSITION> is the type of the transitions of the two original automata
 * @param <INTERSECTIONSTATE> is the type of the states of the intersection automata
 * @param <INTERSECTIONTRANSITION> is the type of the transitions of the intersection automata
 */
public interface IIntBA<
	STATE extends State, 
	TRANSITION extends LabelledTransition,
	INTERSECTIONSTATE extends IntersectionState<STATE>, 
	INTERSECTIONTRANSITION extends ConstrainedTransition<STATE>, 
	TRANSITIONFACTORY extends ConstrainedTransitionFactoryInterface<STATE,INTERSECTIONTRANSITION>>
	extends IBA<INTERSECTIONSTATE,INTERSECTIONTRANSITION, TRANSITIONFACTORY> {

	/**
	 * returns true if the state s is mixed, false otherwise
	 * @param s the state to be checked if transparent or not
	 * @return true if the state s is mixed, false otherwise
	 * @throws IllegalArgumentException if the state s is null
	 */
	public boolean isMixed(INTERSECTIONSTATE s);
	
}
