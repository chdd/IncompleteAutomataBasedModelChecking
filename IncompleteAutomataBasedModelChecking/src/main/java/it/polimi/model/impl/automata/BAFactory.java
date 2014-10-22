package it.polimi.model.impl.automata;

import it.polimi.model.impl.states.State;
import it.polimi.model.impl.transitions.LabelledTransition;
import it.polimi.model.interfaces.automata.BAFactoryInterface;
import it.polimi.model.interfaces.automata.drawable.DrawableBA;
import it.polimi.model.interfaces.transitions.LabelledTransitionFactoryInterface;

public class BAFactory<
	STATE extends State,
	TRANSITION extends LabelledTransition,
	TRANSITIONFACTORY extends LabelledTransitionFactoryInterface<TRANSITION>>
	implements
	BAFactoryInterface<STATE, TRANSITION, TRANSITIONFACTORY, DrawableBA<STATE,TRANSITION,TRANSITIONFACTORY>>{
	
	protected TRANSITIONFACTORY transitionFactory;
	public BAFactory(TRANSITIONFACTORY transitionFactory){
		this.transitionFactory=transitionFactory;
	}


	@Override
	public DrawableBA<STATE, TRANSITION, TRANSITIONFACTORY> create() {
		return new BAImpl<STATE, TRANSITION, TRANSITIONFACTORY>(this.transitionFactory);
	}

	

}
