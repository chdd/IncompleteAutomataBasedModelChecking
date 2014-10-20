package it.polimi.model.interfaces.automata.drawable;

import it.polimi.model.elements.states.IntersectionState;
import it.polimi.model.elements.states.State;
import it.polimi.model.impl.transitions.ConstrainedTransition;
import it.polimi.model.impl.transitions.LabelledTransition;
import it.polimi.model.interfaces.automata.IIntBA;
import it.polimi.model.interfaces.transitions.ConstrainedTransitionFactoryInterface;

/**
 * is the interface the {@link DrawableIntBA} the drawable intersection automaton must implement
 * @author claudiomenghi
 *
 * @param <S1> is the type of the {@link State} of the two original automata
 * @param <T1> is the type of the {@link LabelledTransition} of the two original automata
 * @param <S> is the type of the states of the {@link IntersectionState} that is the state that i generated from the two original automata
 * @param <T> is the type of the {@link ConstrainedTransition} which is generate is the intersection of a Buchi and an Incomplete Buchi automata
 */
public interface DrawableIntBA <S1 extends State, T1 extends LabelledTransition,S extends IntersectionState<S1>, 
T  extends ConstrainedTransition<S1>, 
TFactory extends ConstrainedTransitionFactoryInterface<S1,T>> extends IIntBA<S1,T1,S,T, TFactory>, DrawableIBA<S, T, TFactory> {

}
