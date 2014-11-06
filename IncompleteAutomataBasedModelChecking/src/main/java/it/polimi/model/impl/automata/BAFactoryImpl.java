package it.polimi.model.impl.automata;

import org.apache.commons.collections15.Factory;

import it.polimi.model.impl.states.State;
import it.polimi.model.impl.transitions.LabelledTransition;
import it.polimi.model.interfaces.automata.BAFactory;
import it.polimi.model.interfaces.automata.drawable.DrawableBA;
import it.polimi.model.interfaces.transitions.LabelledTransitionFactory;

/**
 * contains a {@link Factory} method which is used to generate a new empty {@link DrawableBA}
 * @author claudiomenghi
 *
 * @param <STATE> is the type of the {@link State} of the {@link DrawableBA}
 * @param <TRANSITION> is the type of the {@link LabelledTransition} of the {@link DrawableBA}
 * @param <TRANSITIONFACTORY> is the {@link Factory} which is used to create transitions of the {@link DrawableBA}
 */
public class BAFactoryImpl<
	CONSTRAINEDELEMENT extends State,
	STATE extends State,
	TRANSITION extends LabelledTransition<CONSTRAINEDELEMENT>,
	TRANSITIONFACTORY extends LabelledTransitionFactory<CONSTRAINEDELEMENT, TRANSITION>>
	implements
	BAFactory<CONSTRAINEDELEMENT, STATE, TRANSITION, TRANSITIONFACTORY, DrawableBA<CONSTRAINEDELEMENT, STATE,TRANSITION,TRANSITIONFACTORY>>{
	
	/**
	 * contains the {@link Factory} of the {@link LabelledTransition} of the {@link DrawableBA}
	 */
	protected TRANSITIONFACTORY transitionFactory;
	
	/**
	 * crates a new {@link Factory} for the {@link DrawableBA}
	 * @param transitionFactory is the {@link Factory} which is used to create the {@link LabelledTransition} of the {@link DrawableBA}
	 * @throws NullPointerException if the {@link LabelledTransitionFactory} is null
	 */
	public BAFactoryImpl(TRANSITIONFACTORY transitionFactory){
		if(transitionFactory==null){
			throw new NullPointerException("The transition factory cannot be null");
		}
		this.transitionFactory=transitionFactory;
	}

	/**
	 * crates a new {@link DrawableBA}
	 * @return a new empty {@link DrawableBA}
	 */
	@Override
	public DrawableBA<CONSTRAINEDELEMENT, STATE, TRANSITION, TRANSITIONFACTORY> create() {
		return new BAImpl<CONSTRAINEDELEMENT, STATE, TRANSITION, TRANSITIONFACTORY>(this.transitionFactory);
	}
}
