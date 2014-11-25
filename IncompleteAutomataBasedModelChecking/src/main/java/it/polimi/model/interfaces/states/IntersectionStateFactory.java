package it.polimi.model.interfaces.states;

import it.polimi.model.impl.states.IntersectionState;
import it.polimi.model.impl.states.State;

public interface IntersectionStateFactory<STATE extends State, INTERSECTIONSTATE extends IntersectionState<STATE>> 
extends StateFactory<INTERSECTIONSTATE> {

}
