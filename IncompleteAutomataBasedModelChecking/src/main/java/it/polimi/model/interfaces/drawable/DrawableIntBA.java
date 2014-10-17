package it.polimi.model.interfaces.drawable;

import it.polimi.model.automata.ba.transition.ConstrainedTransition;
import it.polimi.model.automata.ba.transition.LabelledTransition;
import it.polimi.model.elements.states.IntersectionState;
import it.polimi.model.elements.states.State;
import it.polimi.model.interfaces.IIntBA;

/**
 * is the interface the {@link DrawableIntBA} the drawable intersection automaton must implement
 * @author claudiomenghi
 *
 * @param <S1> is the type of the {@link State} of the two original automata
 * @param <T1> is the type of the {@link LabelledTransition} of the two original automata
 * @param <S> is the type of the states of the {@link IntersectionState} that is the state that i generated from the two original automata
 * @param <T> is the type of the {@link ConstrainedTransition} which is generate is the intersection of a Buchi and an Incomplete Buchi automata
 */
public interface DrawableIntBA <S1 extends State, T1 extends LabelledTransition,S extends IntersectionState<S1>, T 
extends ConstrainedTransition<S1>> extends IIntBA<S1,T1,S,T>, DrawableIBA<S, T> {

}
