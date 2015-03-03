package it.polimi.constraints.impl;

import it.polimi.automata.BA;
import it.polimi.automata.impl.BAImpl;
import it.polimi.automata.state.State;
import it.polimi.automata.state.impl.StateFactoryImpl;
import it.polimi.automata.transition.Transition;
import it.polimi.automata.transition.TransitionFactory;
import it.polimi.constraints.Component;

import com.google.common.base.Preconditions;

public class BAComponentFactory<S extends State, T extends Transition>
		implements ComponentFactory<S, T, BA<S, T>> {

	
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
	public Component<S, T, BA<S, T>> create(String name, S modelState,
			boolean transparent, TransitionFactory<S, T> transitionFactory) {

		Preconditions.checkNotNull(name,
				"The name of the component cannot be null");

		Preconditions.checkNotNull(modelState,
				"The state of the model cannot be null");

		Preconditions.checkNotNull(transitionFactory,
				"The transition Factory cannot be null");

		BA<S, T> ba = new BAImpl<S, T>(transitionFactory);
		ComponentImpl<S, T, BA<S, T>> s = new ComponentImpl<S, T, BA<S, T>>(
				name, StateFactoryImpl.stateCount, modelState, transparent, ba);
		StateFactoryImpl.stateCount = StateFactoryImpl.stateCount + 1;
		return s;
	}

	/**
	 * creates a component with the specified name, which refers to a model
	 * state and that is refined by the specific BA
	 * 
	 * @param name
	 *            is the name of the component
	 * @param modelState
	 *            is the state of the model to which the component refers
	 * @param transitionFactory
	 *            is the factory which allows to create the transitions of the
	 *            component
	 * @param automaton
	 *            is the automaton that refines the component
	 * @return a new component
	 * @throws NullPointerException
	 *             if one of the parameters is null
	 */
	public Component<S, T, BA<S, T>> create(int id, String name, S modelState,
			TransitionFactory<S, T> transitionFactory, BA<S, T> automaton) {

		Preconditions.checkNotNull(name,
				"The name of the component cannot be null");

		Preconditions.checkNotNull(modelState,
				"The state of the model cannot be null");

		Preconditions.checkNotNull(transitionFactory,
				"The transition Factory cannot be null");
		Preconditions.checkNotNull(automaton,
				"The automaton cannot be null");

		ComponentImpl<S, T, BA<S, T>> s = new ComponentImpl<S, T, BA<S, T>>(
				name, id, modelState, true, automaton);
		StateFactoryImpl.stateCount = StateFactoryImpl.stateCount + 1;
		return s;
	}

	public ComponentImpl<S, T, BA<S, T>> create(int id, String name,
			S modelState, boolean transparent,
			TransitionFactory<S, T> transitionFactory) {
		Preconditions.checkNotNull(name,
				"The name of the component cannot be null");
		Preconditions
				.checkNotNull(modelState,
						"The state of the model associated with the component cannot be null");

		BA<S, T> ba = new BAImpl<S, T>(transitionFactory);

		ComponentImpl<S, T, BA<S, T>> component = new ComponentImpl<S, T, BA<S, T>>(
				name, id, modelState, transparent, ba);
		StateFactoryImpl.stateCount = Math.max(StateFactoryImpl.stateCount + 1,
				id + 1);
		return component;

	}
}
