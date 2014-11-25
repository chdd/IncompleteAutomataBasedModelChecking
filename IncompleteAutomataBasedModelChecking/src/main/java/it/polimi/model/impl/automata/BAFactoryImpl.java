package it.polimi.model.impl.automata;

import it.polimi.model.impl.states.State;
import it.polimi.model.impl.transitions.Transition;
import it.polimi.model.interfaces.automata.BA;
import it.polimi.model.interfaces.automata.BAFactory;
import it.polimi.model.interfaces.states.StateFactory;
import it.polimi.model.interfaces.transitions.TransitionFactory;

import org.apache.commons.collections15.Factory;

/**
 * contains a {@link Factory} method which is used to generate a new empty {@link BA}
 * @author claudiomenghi
 *
 * @param <STATE> is the type of the {@link State} of the {@link BA}
 * @param <TRANSITION> is the type of the {@link Transition} of the {@link BA}
 */
public class BAFactoryImpl<
	STATE extends State,
	TRANSITION extends Transition>
	implements
	BAFactory<STATE, TRANSITION, BA<STATE,TRANSITION>>{
	
	/**
	 * contains the {@link Factory} of the {@link Transition} of the {@link BA}
	 */
	protected TransitionFactory<TRANSITION> transitionFactory;
	
	/**
	 * contains the {@link Factory} of the {@link State} of the {@link BA}
	 */
	protected StateFactory<STATE> stateFactory;
	
	/**
	 * crates a new {@link Factory} for the {@link BA}
	 * @param transitionFactory is the {@link Factory} which is used to create the {@link Transition} of the {@link BA}
	 * @throws NullPointerException if the {@link TransitionFactory} is null or if the {@link StateFactory} is null
	 */
	public BAFactoryImpl(TransitionFactory<TRANSITION> transitionFactory, StateFactory<STATE> stateFactory){
		if(transitionFactory==null){
			throw new NullPointerException("The transition factory cannot be null");
		}
		if(stateFactory==null){
			throw new NullPointerException("The state factory cannot be null");
		}
		this.transitionFactory=transitionFactory;
		this.stateFactory=stateFactory;
	}

	/**
	 * crates a new {@link BA}
	 * @return a new empty {@link BA}
	 */
	@Override
	public BA<STATE, TRANSITION> create() {
		return new BAImpl<STATE, TRANSITION>(this.transitionFactory, this.stateFactory);
	}
}
