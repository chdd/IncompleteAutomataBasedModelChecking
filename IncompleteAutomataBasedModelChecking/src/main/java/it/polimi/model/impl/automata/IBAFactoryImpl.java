package it.polimi.model.impl.automata;

import it.polimi.model.impl.states.State;
import it.polimi.model.impl.transitions.LabelledTransition;
import it.polimi.model.interfaces.automata.IBAFactory;
import it.polimi.model.interfaces.automata.drawable.DrawableIBA;
import it.polimi.model.interfaces.transitions.LabelledTransitionFactory;

public class IBAFactoryImpl<
	STATE extends State,
	TRANSITION extends LabelledTransition,
	TRANSITIONFACTORY extends LabelledTransitionFactory<TRANSITION>>
	implements
		IBAFactory<STATE, TRANSITION, TRANSITIONFACTORY, DrawableIBA<STATE,TRANSITION,TRANSITIONFACTORY>> {

	protected TRANSITIONFACTORY transitionFactory;
	public IBAFactoryImpl(TRANSITIONFACTORY transitionFactory){
		this.transitionFactory=transitionFactory;
	}


	@Override
	public DrawableIBA<STATE, TRANSITION, TRANSITIONFACTORY> create() {
		return new IBAImpl<STATE, TRANSITION, TRANSITIONFACTORY>(this.transitionFactory);
	}

}
