package it.polimi.constraints;

import java.util.Set;

import it.polimi.automata.state.State;
import it.polimi.automata.transition.Transition;
import it.polimi.constraints.impl.ComponentImpl;

/**
 * contains a constraint, i.e., the set of automata to be considered in the
 * refinement of the transparent states. Each component included in the
 * constraint contains the sub-properties the automaton that refines a
 * transparent state must satisfy<br>
 * 
 * The constraint also contains the reachability relation between the incoming
 * and out-coming ports of the components which allows to update the constraint
 * once a component is refined
 * 
 * @author claudiomenghi
 *
 * @param <S>
 *            is the type of the states of the automaton to be considered in the
 *            refinement of the transparent state
 * @param <T>
 *            is the type of the transitions of the automaton to be considered
 *            in the refinement of the transparent state
 */
public interface Constraint<S extends State, T extends Transition> {
	
	
	/**
	 * adds the component to the set of sub-properties to be considered in the
	 * refinement of the transparent states
	 * 
	 * @param component
	 *            is the component to be considered in the refinement of a
	 *            transparent state of the model
	 * @throws NullPointerException
	 *             if the component is null
	 */
	public void addComponent(ComponentImpl<S, T> component);
	
	/**
	 * returns the components involved in the constraint
	 * 
	 * @return the components involved in the constraint
	 */
	public Set<Component<S, T>> getComponents();
	
	/**
	 * add a reachability entity, i.e., id specifies that the in-coming port of
	 * the destination component is reachable from the outComing port of the
	 * source component
	 * 
	 * @param incomingPort
	 *            is the in-coming reachable port of the destination Component
	 * @param outComingPort
	 *            is the out-coming port from which the incomingPort of the
	 *            destination component can be reached.
	 * @throws NullPointerException
	 *             if one of the parameters is null
	 */
	public void addReachabilityRelation(
			Port<S,T> incomingPort, Port<S,T> outComingPort);

}
