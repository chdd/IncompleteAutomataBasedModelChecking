/**
 * 
 */
package it.polimi.constraints;

import it.polimi.automata.IBA;
import it.polimi.automata.IntersectionBA;
import it.polimi.automata.impl.IntBAImpl;
import it.polimi.automata.state.State;
import it.polimi.automata.state.impl.StateImpl;
import it.polimi.automata.transition.Transition;
import it.polimi.automata.transition.TransitionFactory;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang3.Validate;

/**
 * Contains a component. A component is a sub part of the intersection automaton
 * that corresponds with a transparent state of the model and will generate a
 * constraint
 * 
 * @author claudiomenghi
 * 
 */
public class Component<S extends State, T extends Transition> extends
		IntBAImpl<S, T> implements State, IntersectionBA<S, T> {

	/**
	 * contains the id of the state
	 */
	private final int id;

	/**
	 * contains the name of the state
	 */
	private String name;

	/**
	 * is true if the model of the state represented by the component is a
	 * transparent state
	 */
	private boolean transparent;

	/**
	 * is the state of the original model to which this component is associated
	 */
	private S modelState;

	/**
	 * contains the incoming transitions of the component. The map contains the
	 * destination state of the transition, i.e., the state of this component to
	 * which the transition is connected, the source state of the transition and
	 * the transition itself
	 */
	private Set<Port<S, T>> incomingTransition;

	/**
	 * contains the out coming transitions of the component. The map contains
	 * the source state of the transition, i.e., the state of this component
	 * from which the transition starts, the destination state of the transition
	 * and the transition it self
	 */
	private Set<Port<S, T>> outcomingTransition;

	private TransitionFactory<S, T> transitionFactory;

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
	protected Component(int id, S modelState, boolean transparent,
			TransitionFactory<S, T> transitionFactory) {
		super(transitionFactory);

		Validate.notNull(modelState, "The model state be null");
		Validate.isTrue(id >= 0, "The id cannot be < 0", id);

		this.id = id;
		this.name = "";
		this.modelState = modelState;
		this.transparent = transparent;
		incomingTransition = new HashSet<Port<S, T>>();
		outcomingTransition = new HashSet<Port<S, T>>();
		this.transitionFactory = transitionFactory;
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
	protected Component(String name, int id, S modelState, boolean transparent,
			TransitionFactory<S, T> transitionFactory) {
		this(id, modelState, transparent, transitionFactory);
		Validate.notNull(name, "The name of the state cannot be null");

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
	 * returns the model of the state
	 * 
	 * @return the modelState
	 */
	public S getModelState() {
		return modelState;
	}

	public void addIncomingPort(S source, T transition, S destination,
			IBA<S, T> model) {
		this.incomingTransition.add(new Port<S, T>(source, destination,
				transition, model));
	}

	public void addOutComingPort(S source, T transition, S destination,
			IBA<S, T> model) {
		this.outcomingTransition.add(new Port<S, T>(source, destination,
				transition, model));
	}

	/**
	 * @return the incomingPorts
	 */
	public Set<Port<S, T>> getIncomingPorts() {
		return incomingTransition;
	}

	/**
	 * @return the outcomingPorts
	 */
	public Set<Port<S, T>> getOutcomingPorts() {
		return outcomingTransition;
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
	 * returns a copy of the component
	 * 
	 * @return a copy of the component
	 */
	public Component<S, T> duplicate() {
		Component<S, T> ret = new ComponentFactory<S, T>().create(this.name,
				this.modelState, this.transparent, this.transitionFactory);
		ret.modelState = this.modelState;
		// coping the states
		ret.addStates(this.getStates());
		// coping the initial states
		ret.addInitialStates(this.getInitialStates());
		// coping the accepting states
		ret.addAcceptStates(this.getAcceptStates());
		// coping the accepting states
		for (T t : this.getTransitions()) {
			ret.addCharacters(t.getPropositions());
			ret.addTransition(this.getTransitionSource(t),
					this.getTransitionDestination(t), t);
		}
		ret.incomingTransition = new HashSet<Port<S, T>>(
				this.incomingTransition);
		ret.outcomingTransition = new HashSet<Port<S, T>>(
				this.outcomingTransition);
		return ret;
	}

	/**
	 * {@inheritDoc}
	 */
	public void removeInitialState(S state) {
		super.removeInitialState(state);
		this.incomingTransition.remove(state);

	}

	/**
	 * @return the transparent
	 */
	public boolean isTransparent() {
		return transparent;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
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
		@SuppressWarnings("unchecked")
		Component<S, T> other = (Component<S, T>) obj;
		if (id != other.id)
			return false;
		return true;
	}
}
