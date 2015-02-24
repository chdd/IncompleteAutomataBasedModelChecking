package it.polimi.constraints.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.polimi.automata.state.State;
import it.polimi.automata.state.impl.StateFactoryImpl;
import it.polimi.automata.transition.Transition;
import it.polimi.automata.transition.TransitionFactory;

public class ComponentFactory<S extends State, T extends Transition> {

	/**
	 * is the logger of the SubAutomataIdentifier class
	 */
	private static final Logger logger = LoggerFactory
			.getLogger(ComponentFactory.class);

	
	/**
	 * creates a new component with the specified name, model state, transparent
	 * attribute and transition factory
	 * 
	 * @param name
	 * @param modelState
	 * @param transparent
	 * @param transitionFactory
	 * @return
	 */
	public ComponentImpl<S, T> create(String name, S modelState,
			boolean transparent, TransitionFactory<S, T> transitionFactory) {

		if (transitionFactory == null) {
			logger.error("The transition Factory cannot be null");
			throw new NullPointerException(
					"The transition Factory cannot be null");
		}
		ComponentImpl<S, T> s = new ComponentImpl<S, T>(name,
				StateFactoryImpl.stateCount, modelState, transparent,
				transitionFactory);
		StateFactoryImpl.stateCount = StateFactoryImpl.stateCount + 1;
		return s;
	}
}
