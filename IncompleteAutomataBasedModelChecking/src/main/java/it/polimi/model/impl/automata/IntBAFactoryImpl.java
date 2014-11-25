package it.polimi.model.impl.automata;

import it.polimi.model.impl.states.IntersectionState;
import it.polimi.model.impl.states.State;
import it.polimi.model.impl.transitions.Transition;
import it.polimi.model.interfaces.automata.BA;
import it.polimi.model.interfaces.automata.IBA;
import it.polimi.model.interfaces.automata.IIntBA;
import it.polimi.model.interfaces.automata.IntBAFactory;
import it.polimi.model.interfaces.states.IntersectionStateFactory;
import it.polimi.model.interfaces.transitions.ConstrainedTransitionFactory;

import org.apache.commons.collections15.Factory;

public class IntBAFactoryImpl<
		CONSTRAINEDELEMENT extends State,
		STATE extends State, TRANSITION extends Transition, INTERSECTIONSTATE extends IntersectionState<STATE>, INTERSECTIONTRANSITION extends Transition>
		implements
		IntBAFactory<STATE, TRANSITION, INTERSECTIONSTATE, INTERSECTIONTRANSITION, IIntBA<STATE, TRANSITION, INTERSECTIONSTATE, INTERSECTIONTRANSITION>>

{

	/**
	 * contains the {@link Factory} of the {@link Transition} of the {@link BA}
	 */
	protected ConstrainedTransitionFactory<CONSTRAINEDELEMENT, INTERSECTIONTRANSITION> transitionFactory;

	/**
	 * contains the {@link Factory} of the {@link State} of the {@link BA}
	 */
	protected IntersectionStateFactory<STATE, INTERSECTIONSTATE> stateFactory;

	/**
	 * crates a new {@link Factory} for the {@link DrawableIBA}
	 * 
	 * @param transitionFactory
	 *            is the {@link Factory} which is used to create the
	 *            {@link Transition} of the {@link DrawableIBA}
	 * @throws NullPointerException
	 *             if the transitionFactory is null
	 */
	public IntBAFactoryImpl(
			ConstrainedTransitionFactory<CONSTRAINEDELEMENT, INTERSECTIONTRANSITION> transitionFactory,
			IntersectionStateFactory<STATE, INTERSECTIONSTATE> stateFactory) {
		this.stateFactory = stateFactory;
		this.transitionFactory = transitionFactory;
	}

	/**
	 * creates a new empty {@link DrawableIBA}
	 * 
	 * @return a new empty {@link DrawableIBA}
	 */
	@Override
	public IIntBA<STATE, TRANSITION, INTERSECTIONSTATE, INTERSECTIONTRANSITION> create(
			IBA<STATE, TRANSITION> model, BA<STATE, TRANSITION> specification) {
		return new IntBAImpl<STATE, TRANSITION, INTERSECTIONSTATE, INTERSECTIONTRANSITION>(
				this.transitionFactory, this.stateFactory);
	}

	@Override
	public IIntBA<STATE, TRANSITION, INTERSECTIONSTATE, INTERSECTIONTRANSITION> create() {
		return new IntBAImpl<STATE, TRANSITION, INTERSECTIONSTATE, INTERSECTIONTRANSITION>(
				this.transitionFactory, this.stateFactory);
	}
}
