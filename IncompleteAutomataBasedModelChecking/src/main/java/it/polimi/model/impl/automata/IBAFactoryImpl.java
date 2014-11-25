package it.polimi.model.impl.automata;

import it.polimi.model.impl.states.State;
import it.polimi.model.impl.transitions.Transition;
import it.polimi.model.interfaces.automata.BA;
import it.polimi.model.interfaces.automata.IBA;
import it.polimi.model.interfaces.automata.IBAFactory;
import it.polimi.model.interfaces.states.StateFactory;
import it.polimi.model.interfaces.transitions.TransitionFactory;

import org.apache.commons.collections15.Factory;

/**
 * contains a new {@link Factory} for a {@link DrawableIBA}
 * @author claudiomenghi
 *
 * @param <STATE> is the type of the {@link State} of the {@link DrawableIBA}
 * @param <TRANSITION> is the type of the {@link Transition} of the {@link DrawableIBA}
 * @param <TRANSITIONFACTORY> is the {@link Factory} which is used to create the {@link Transition} of the {@link DrawableIBA}
 */
public class IBAFactoryImpl<
	STATE extends State,
	TRANSITION extends Transition>
	implements
		IBAFactory<STATE, TRANSITION, IBA<STATE,TRANSITION>> {

	/**
	 * contains the {@link Factory} of the {@link Transition} of the {@link BA}
	 */
	protected TransitionFactory<TRANSITION> transitionFactory;
	
	/**
	 * contains the {@link Factory} of the {@link State} of the {@link BA}
	 */
	protected StateFactory<STATE> stateFactory;
	
	
	/**
	 * crates a new {@link Factory} for the {@link DrawableIBA}
	 * @param transitionFactory is the {@link Factory} which is used to create the {@link Transition} of the {@link DrawableIBA}
	 * @throws NullPointerException if the transitionFactory is null
	 */
	public IBAFactoryImpl( TransitionFactory<TRANSITION> transitionFactory, StateFactory<STATE> stateFactory){
		if(transitionFactory==null){
			throw new NullPointerException("The transition factory cannot be null");
		}
		this.transitionFactory=transitionFactory;
		this.stateFactory=stateFactory;
	}
	
	/**
	 * creates a new empty {@link DrawableIBA}
	 * @return a new empty {@link DrawableIBA}
	 */
	@Override
	public IBA<STATE, TRANSITION> create() {
		return new IBAImpl<STATE, TRANSITION>(this.transitionFactory, this.stateFactory);
	}
}
