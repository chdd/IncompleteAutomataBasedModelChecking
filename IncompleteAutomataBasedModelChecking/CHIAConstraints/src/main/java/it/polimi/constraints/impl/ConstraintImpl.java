package it.polimi.constraints.impl;

import it.polimi.automata.state.State;
import it.polimi.automata.transition.Transition;
import it.polimi.constraints.Color;
import it.polimi.constraints.Component;
import it.polimi.constraints.Constraint;
import it.polimi.constraints.Port;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.Validate;
import org.jgrapht.DirectedGraph;
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
public class ConstraintImpl<S extends State, T extends Transition> implements Constraint<S, T> {

	/**
	 * is the set of the components to be considered in the refinement of the
	 * transparent states
	 */
	private Set<Component<S, T>> components;

	/**
	 * describes how the incoming and outcoming ports of the component are related
	 */
	private DirectedGraph<Port<S,T>, DefaultEdge> portsRelation;
	

	/**
	 * specifies for each port the corresponding color The red color means that
	 * from this port it is possible to reach an accepting state The green color
	 * means that the port is reachable from an initial state The yellow color
	 * means that the port is possibly reachable from an initial state and from
	 * the port it is possibly reachable an accepting state
	 */
	private Map<T, Color> portValue;

	/**
	 * creates a new empty constraint
	 */
	public ConstraintImpl() {
		this.components = new HashSet<Component<S, T>>();
		this.portValue = new HashMap<T, Color>();
		this.portsRelation=new DefaultDirectedGraph<Port<S,T>, DefaultEdge>(DefaultEdge.class);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addComponent(ComponentImpl<S, T> component) {
		Validate.notNull(component, "The component cannot be null");

		this.components.add(component);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Set<Component<S, T>> getComponents() {
		return this.components;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addReachabilityRelation(
			Port<S,T> incomingPort, Port<S,T> outComingPort) {
		// validates the parameters
		Validate.notNull(incomingPort, "The incomingPort port cannot be null");
		Validate.notNull(outComingPort, "The outcomingPort port cannot be null");
		
		portsRelation.addEdge(incomingPort, outComingPort);
	}

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
	public void setPortValue(T port, Color value) {
		Validate.notNull(port, "The port cannot be null");
		Validate.notNull(value, "The value cannot be null");
		this.portValue.put(port, value);
	}

	/**
	 * returns the value associated with the specified port
	 * 
	 * @param port
	 *            is the port of interest
	 * @return the value associated with the specified port
	 * @throws IllegalArgumentException
	 *             if the port is not contained into the set of the colored ports
	 */
	public Color getPortValue(T port) {
		Validate.isTrue(!this.portValue.keySet().contains(port),
				"The port must be contained into the set of the ports: ", port);
		return portValue.get(port);
	}

}
