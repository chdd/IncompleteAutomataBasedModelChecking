/**
 * 
 */
package it.polimi.contraintcomputation.component;

import it.polimi.automata.impl.BAImpl;
import it.polimi.automata.labeling.Label;
import it.polimi.automata.state.State;
import it.polimi.automata.state.impl.StateImpl;
import it.polimi.automata.transition.Transition;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 * Contains a component. A component is a sub part of the intersection automaton
 * that corresponds with a transparent state of the model and will generate a
 * constraint
 * 
 * @author claudiomenghi
 * 
 */
public class Component<L extends Label, S extends State, T extends Transition<L>>
		extends BAImpl<L, S, T> implements State {

	/**
	 * contains the id of the state
	 */
	private final int id;

	/**
	 * contains the name of the state
	 */
	private String name;
	
	/**
	 * is true if the model of the state represented by the component is a transparent state
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
	private Map<S, Map<S, T>> incomingTransition;

	/**
	 * contains the out coming transitions of the component. The map contains
	 * the source state of the transition, i.e., the state of this component
	 * from which the transition starts, the destination state of the transition
	 * and the transition it self
	 */
	private Map<S, Map<S, T>> outcomingTransition;

	/**
	 * creates a state with the specified id. The model state represents the
	 * state of the model to which the component corresponds with
	 * 
	 * @param id
	 *            is the id of the state
	 * @param modelState
	 *            is the state of the model to which the component correspond
	 * @throws NullPointerException
	 *             if the state of the model is null
	 * @throws IllegalArgumentException
	 *             if the value of the id is less than 0
	 */
	protected Component(int id, S modelState, boolean transparent) {
		super();
		if (modelState == null) {
			throw new NullPointerException(
					"the state of the model that corresponds to the component cannot be null");
		}
		if (id < 0) {
			throw new IllegalArgumentException("The id cannot be < 0");
		}
		this.id = id;
		this.name = "";
		this.setModelState(modelState);
		this.transparent=transparent;
		incomingTransition = new HashMap<S, Map<S, T>>();
		outcomingTransition = new HashMap<S, Map<S, T>>();
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
	protected Component(String name, int id, S modelState, boolean transparent) {
		this(id, modelState, transparent);
		if (name == null) {
			throw new NullPointerException(
					"The name of the state cannot be null");
		}
		this.name = name;
		this.setModelState(modelState);
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
	 * @return the modelState
	 */
	public S getModelState() {
		return modelState;
	}

	/**
	 * @param modelState
	 *            the modelState to set
	 */
	private void setModelState(S modelState) {
		this.modelState = modelState;
	}

	public void addIncomingTransition(S source, T transition, S destination) {

		if (!this.incomingTransition.containsKey(destination)) {
			this.incomingTransition.put(destination, new HashMap<S, T>());
			this.incomingTransition.get(destination).put(source, transition);
		}
		this.incomingTransition.get(destination).put(source, transition);
	}

	public void addOutComingTransition(S source, T transition, S destination) {

		if (!this.outcomingTransition.containsKey(source)) {
			this.outcomingTransition.put(source, new HashMap<S, T>());
			this.outcomingTransition.get(source).put(destination, transition);
		}
		this.outcomingTransition.get(source).put(destination, transition);
	}

	/**
	 * @return the incomingTransition
	 */
	public Map<S, Map<S, T>> getIncomingTransition() {
		return incomingTransition;
	}

	/**
	 * @return the outcomingTransition
	 */
	public Map<S, Map<S, T>> getOutcomingTransition() {
		return outcomingTransition;
	}

	/**
	 * injects the componentB into the component a
	 * 
	 * @param componentB
	 *            is the component to be injected
	 * @param statesConnectionMap
	 *            specifies how the out coming transitions of the componentA
	 *            must be connected with the incoming transitions of the
	 *            componentB
	 * @throws NullPointerException
	 *             if the componentB or the statesConnection map is null
	 */
	public void merge(Component<L, S, T> componentB,
			Set<Entry<Entry<S, S>, T>> transitions) {

		if (componentB == null) {
			throw new NullPointerException(
					"The second component of to be merged cannot be null");
		}
		if (transitions == null) {
			throw new NullPointerException(
					"The map of the states to be connected cannot be null");
		}
		/*
		 * copies the states of the componentB into the componentA
		 */
		this.addStates(componentB.getStates());
		/*
		 * copies the transitions of the componentB into the component A
		 */
		for (T t : componentB.getTransitions()) {
			this.addCharacters(t.getLabels());
			this.addTransition(componentB.getTransitionSource(t),
					componentB.getTransitionDestination(t), t);
		}
		/*
		 * adds the accepting states of B to the component A
		 */
		this.addAcceptStates(componentB.getAcceptStates());
		/*
		 * adds the initial states of B to the component A
		 */
		this.addInitialStates(componentB.getInitialStates());
		/*
		 * connects the final state of the component A to the states of the
		 * component B
		 */
		for (Entry<Entry<S, S>, T> e : transitions) {
			this.addCharacters(e.getValue().getLabels());
			this.addTransition(e.getKey().getKey(), e.getKey().getValue(), e.getValue());
		}
		
		
		/*
		 * adds the incoming transitions of B
		 */
		this.incomingTransition.putAll(componentB.incomingTransition);
		/*
		 * adds the out coming transitions of B
		 */
		this.outcomingTransition.putAll(componentB.outcomingTransition);

	}
	
	/**
	 * returns a copy of the component
	 * @return a copy of the component
	 */
	public Component<L,S,T> duplicate(){
		Component<L,S,T> ret=new ComponentFactory<L,S,T>().create(this.name, this.modelState, this.transparent);
		ret.modelState=this.modelState;
		// coping the states
		ret.addStates(this.getStates());
		// coping the initial states
		ret.addInitialStates(this.getInitialStates());
		// coping the accepting states
		ret.addAcceptStates(this.getAcceptStates());
		// coping the accepting states
		for(T t: this.getTransitions()){
			ret.addCharacters(t.getLabels());
			ret.addTransition(this.getTransitionSource(t), this.getTransitionDestination(t), t);
		}
		ret.incomingTransition=new HashMap<S, Map<S, T>>(this.incomingTransition);
		ret.outcomingTransition=new HashMap<S, Map<S, T>>(this.outcomingTransition);
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
}
