package it.polimi.constraints;

import it.polimi.automata.BA;
import it.polimi.automata.state.State;
import it.polimi.automata.transition.Transition;

import java.util.Map;
import java.util.Set;

import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;

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
public interface Constraint<S extends State, T extends Transition, A extends BA<S, T>> {

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
	public void addComponent(Component<S, T, A> component);

	/**
	 * returns the components involved in the constraint
	 * 
	 * @return the components involved in the constraint
	 */
	public Set<Component<S, T, A>> getComponents();

	/**
	 * returns the component associated with the transparent state
	 * 
	 * @param transparentState
	 *            is the transparent state under analysis
	 * @return the sub-properties associated with the transparent state
	 * @throws NullPointerException
	 *             if the transparent state is null
	 * @throws IllegalArgumentException
	 *             if the transparent state is not contained into the set of the
	 *             states constrained states
	 */
	public Component<S, T, A> getSubproperties(S transparentState);

	/**
	 * add a reachability entity, i.e., id specifies that the in-coming port of
	 * the destination component is reachable from the outComing port of the
	 * source component
	 * 
	 * @param destinationIncomingPort
	 *            is the in-coming reachable port of the destination Component
	 * @param sourceOutComingPort
	 *            is the out-coming port from which the incomingPort of the
	 *            destination component can be reached.
	 * @throws NullPointerException
	 *             if one of the parameters is null
	 */
	public void addReachabilityRelation(Port<S, T> destinationIncomingPort,
			Port<S, T> sourceOutComingPort);


	/**
	 * sets the port value to the specified color
	 * 
	 * @param port
	 *            is the port to be updated
	 * @param value
	 *            is the new value of the port
	 * @throws NullPointerException
	 *             if the port or the value is null
	 */
	public void setPortValue(Port<S, T> port, Color color);

	/**
	 * returns the value associated with the specified port
	 * 
	 * @param port
	 *            is the port of interest
	 * @return the value associated with the specified port
	 * @throws NullPointerException
	 *             if the parameter is null
	 * @throws IllegalArgumentException
	 *             if the port is not contained into the set of the colored
	 *             ports
	 */
	public Color getPortValue(Port<S, T> port);

	public Map<Port<S, T>, Color> getPortValue();

	public int getTotalStates();

	public int getTotalTransitions();

	public void updatePortRelation(Set<Port<S, T>> incomingPorts,
			Set<Port<S, T>> outcomingPorts);
	
	/**
	 * returns the set of the states of the model constrained by the constraint
	 * 
	 * @return the set of the states of the model constrained by the constraint
	 */
	public Set<S> getConstrainedStates();
	
	
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
	public void addIncomingPort(Component<S, T, A> component,Port<S, T> port);
	
	public void removeIncomintPort(Port<S, T> port);

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
	public void addOutComingPort(Component<S, T, A> component, Port<S, T> port);
	
	public void removeOutComingPort(Port<S, T> port);

	/**
	 * return the set of the incoming ports of the component
	 * 
	 * @return the set of the incoming ports of the component
	 */
	public Set<Port<S, T>> getIncomingPorts(Component<S, T, A> component);

	/**
	 * return the set of the out-coming ports of the component
	 * 
	 * @return the set of the out-coming ports of the component
	 */
	public Set<Port<S, T>> getOutcomingPorts(Component<S, T, A> component);
	
	public DefaultDirectedGraph<Port<S, T>, DefaultEdge> getPortsGraph();
	
	public void setPortGraph(DefaultDirectedGraph<Port<S, T>, DefaultEdge> portsGraph);
}
