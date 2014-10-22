package it.polimi.model.impl.automata;

import it.polimi.model.impl.states.State;
import it.polimi.model.impl.transitions.LabelledTransition;
import it.polimi.model.interfaces.automata.IBAFactoryInterface;
import it.polimi.model.interfaces.automata.drawable.DrawableIBA;
import it.polimi.model.interfaces.transitions.LabelledTransitionFactoryInterface;

public class IBAFactory<
	STATE extends State,
	TRANSITION extends LabelledTransition,
	TRANSITIONFACTORY extends LabelledTransitionFactoryInterface<TRANSITION>>
	implements
		IBAFactoryInterface<STATE, TRANSITION, TRANSITIONFACTORY, DrawableIBA<STATE,TRANSITION,TRANSITIONFACTORY>> {

	protected TRANSITIONFACTORY transitionFactory;
	public IBAFactory(TRANSITIONFACTORY transitionFactory){
		this.transitionFactory=transitionFactory;
	}


	@Override
	public DrawableIBA<STATE, TRANSITION, TRANSITIONFACTORY> create() {
		return new IBAImpl<STATE, TRANSITION, TRANSITIONFACTORY>(this.transitionFactory);
	}

}
