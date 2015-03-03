/**
 * 
 */
package it.polimi.constraints.impl;

import it.polimi.automata.BA;
import it.polimi.automata.state.State;
import it.polimi.automata.state.impl.StateImpl;
import it.polimi.automata.transition.Transition;
import it.polimi.constraints.Component;

import org.apache.commons.lang3.Validate;

import com.google.common.base.Preconditions;

/**
 * Contains a component. A component is a sub part of the intersection automaton
 * that corresponds with a transparent state of the model and will generate a
 * constraint
 * 
 * @author claudiomenghi
 * 
 */
public class ComponentImpl<S extends State, T extends Transition, A extends BA<S, T>>
		 implements  Component<S, T, A> {

	/**
	 * contains the id of the state
	 */
	private final int id;

	/**
	 * contains the name of the state
	 */
	private String name;

	/**
	 * is the state of the original model to which this component is associated
	 */
	private S modelState;

	private A automaton;
	
	

//	private TransitionFactory<S, T> transitionFactory;

	/**
	 * creates a state with the specified id. The model state represents the
	 * state of the model to which the component corresponds with
	 * 
	 * @param id
	 *            is the id of the state
	 * @param modelState
	 *            is the state of the model to which the component correspond
	 * @throws NullPointerException
	 *             if the state of the modelState is null
	 * @throws IllegalArgumentException
	 *             if the value of the id is less than 0
	 */
	protected ComponentImpl(int id, S modelState, boolean transparent,
			A automaton) {
		
		Preconditions
		.checkNotNull(modelState, "The model state be null");
		Preconditions
		.checkNotNull(automaton, "The automaton of the component cannot be null");
		Validate.isTrue(id >= 0, "The id cannot be < 0", id);

		this.id = id;
		this.name = "";
		this.modelState = modelState;
		this.automaton = automaton;
	}

	/**
	 * creates a new state with the specified name, id. The model state
	 * represents the state of the model to which the component corresponds with
	 * 
	 * @param name
	 *            contains the name of the state
	 * @param id
	 *            contains the id of the state
	 * @param modelState
	 *            is the state of the model to which the component correspond
	 * @see StateImpl#StateImpl(int)
	 * @throws NullPointerException
	 *             is generated when the name of the state or when the state of
	 *             the model is null
	 * @throws IllegalArgumentException
	 *             if the value of the id is less than 0
	 */
	protected ComponentImpl(String name, int id, S modelState, boolean transparent,
			A automaton) {
		this(id, modelState, transparent, automaton);
		Preconditions
		.checkNotNull(name, "The name of the state cannot be null");

		this.name = name;
		this.modelState = modelState;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getId() {
		return this.id;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getName() {
		return name;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public S getModelState() {
		return modelState;
	}



	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return this.getId() + ": " + this.getName();
	}

	public String toStringAutomata() {
		return super.toString();
	}

	/**
	 * {@inheritDoc}
	 */
	public void removeInitialState(S state) {
		automaton.removeInitialState(state);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ComponentImpl other = (ComponentImpl) obj;
		if (id != other.id)
			return false;
		return true;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public A getAutomaton() {
		return this.automaton;
	}
}
