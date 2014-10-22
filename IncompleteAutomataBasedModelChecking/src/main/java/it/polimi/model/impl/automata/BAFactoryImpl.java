package it.polimi.model.impl.automata;

import it.polimi.model.impl.states.State;
import it.polimi.model.impl.transitions.LabelledTransition;
import it.polimi.model.interfaces.automata.BAFactory;
import it.polimi.model.interfaces.automata.drawable.DrawableBA;
import it.polimi.model.interfaces.transitions.LabelledTransitionFactory;

public class BAFactoryImpl<
	STATE extends State,
	TRANSITION extends LabelledTransition,
	TRANSITIONFACTORY extends LabelledTransitionFactory<TRANSITION>>
	implements
	BAFactory<STATE, TRANSITION, TRANSITIONFACTORY, DrawableBA<STATE,TRANSITION,TRANSITIONFACTORY>>{
	
	protected TRANSITIONFACTORY transitionFactory;
	public BAFactoryImpl(TRANSITIONFACTORY transitionFactory){
		this.transitionFactory=transitionFactory;
	}


	@Override
	public DrawableBA<STATE, TRANSITION, TRANSITIONFACTORY> create() {
		return new BAImpl<STATE, TRANSITION, TRANSITIONFACTORY>(this.transitionFactory);
	}

	

}
