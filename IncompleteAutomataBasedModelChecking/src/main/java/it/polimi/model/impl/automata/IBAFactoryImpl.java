package it.polimi.model.impl.automata;

import org.apache.commons.collections15.Factory;

import it.polimi.model.impl.states.State;
import it.polimi.model.impl.transitions.LabelledTransition;
import it.polimi.model.interfaces.automata.IBAFactory;
import it.polimi.model.interfaces.automata.drawable.DrawableIBA;
import it.polimi.model.interfaces.transitions.LabelledTransitionFactory;

/**
 * contains a new {@link Factory} for a {@link DrawableIBA}
 * @author claudiomenghi
 *
 * @param <STATE> is the type of the {@link State} of the {@link DrawableIBA}
 * @param <TRANSITION> is the type of the {@link LabelledTransition} of the {@link DrawableIBA}
 * @param <TRANSITIONFACTORY> is the {@link Factory} which is used to create the {@link LabelledTransition} of the {@link DrawableIBA}
 */
public class IBAFactoryImpl<
	CONSTRAINEDELEMENT extends State,
	STATE extends State,
	TRANSITION extends LabelledTransition<CONSTRAINEDELEMENT>,
	TRANSITIONFACTORY extends LabelledTransitionFactory<CONSTRAINEDELEMENT, TRANSITION>>
	implements
		IBAFactory<CONSTRAINEDELEMENT, STATE, TRANSITION, TRANSITIONFACTORY, DrawableIBA<CONSTRAINEDELEMENT, STATE,TRANSITION,TRANSITIONFACTORY>> {

	/**
	 * contains the {@link LabelledTransitionFactory} which is used to create the {@link LabelledTransition} of the {@link DrawableIBA}
	 */
	protected TRANSITIONFACTORY transitionFactory;
	
	/**
	 * crates a new {@link Factory} for the {@link DrawableIBA}
	 * @param transitionFactory is the {@link Factory} which is used to create the {@link LabelledTransition} of the {@link DrawableIBA}
	 * @throws NullPointerException if the transitionFactory is null
	 */
	public IBAFactoryImpl(TRANSITIONFACTORY transitionFactory){
		if(transitionFactory==null){
			throw new NullPointerException("The transition factory cannot be null");
		}
		this.transitionFactory=transitionFactory;
	}
	
	/**
	 * creates a new empty {@link DrawableIBA}
	 * @return a new empty {@link DrawableIBA}
	 */
	@Override
	public DrawableIBA<CONSTRAINEDELEMENT, STATE, TRANSITION, TRANSITIONFACTORY> create() {
		return new IBAImpl<CONSTRAINEDELEMENT, STATE, TRANSITION, TRANSITIONFACTORY>(this.transitionFactory);
	}
}
