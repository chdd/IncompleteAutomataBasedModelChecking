package it.polimi.constraints;

import java.util.Set;

import it.polimi.automata.IntersectionBA;
import it.polimi.automata.state.State;
import it.polimi.automata.transition.Transition;

/**
 * Contains a component. A component is a sub part of the intersection automaton
 * that corresponds with a transparent state of the model and will generate a
 * constraint
 * 
 * @author claudiomenghi
 */
public interface Component<S extends State, T extends Transition> extends IntersectionBA<S, T> {

	/**
	 * returns the model of the state
	 * 
	 * @return the modelState
	 */
	public S getModelState();

	/**
	 * adds an incoming port to the component
	 * 
	 * @param source
	 *            is the source state of the port
	 * @param transition
	 *            is the transition that connect the source state to the
	 *            destination state, i.e., the state of the automaton that
	 *            refines the component
	 * @param destination
	 *            is the state of the automaton that refines the component to be
	 *            connected
	 * @throws NullPointerException
	 *             if one of the parameters is null
	 * @throws IllegalArgumentException
	 *             if the destination state is not a state of the component
	 */
	public void addIncomingPort(S source, T transition, S destination);

	/**
	 * adds an out-coming port to the component
	 * 
	 * @param source
	 *            is the source state of the port, i.e., the state that refines
	 *            the component to be connected
	 * @param transition
	 *            is the transition that connect the source state (i.e., the
	 *            state of the automaton that refines the component) to the
	 *            destination state
	 * @param destination
	 *            is the destination state of the port
	 * @throws NullPointerException
	 *             if one of the parameters is null
	 * @throws IllegalArgumentException
	 *             if the source state is not a state of the component
	 */
	public void addOutComingPort(S source, T transition, S destination);

	/**
	 * return the set of the incoming ports of the component
	 * 
	 * @return the set of the incoming ports of the component
	 */
	public Set<Port<S, T>> getIncomingPorts();
	
	/**
	 * return the set of the out-coming ports of the component
	 * 
	 * @return the set of the out-coming ports of the component
	 */
	public Set<Port<S, T>> getOutcomingPorts() ;
}
