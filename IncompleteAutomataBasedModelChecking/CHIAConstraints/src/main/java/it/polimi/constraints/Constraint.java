package it.polimi.constraints;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.lang3.Validate;

import it.polimi.automata.state.State;
import it.polimi.automata.transition.Transition;

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
public class Constraint<S extends State, T extends Transition> {

	/**
	 * is the set of the components to be considered in the refinement of the
	 * transparent states
	 */
	private Set<Component<S, T>> components;

	/**
	 * contains the map that specifies for each component, for each incoming
	 * port, the reachable out-coming ports and the corresponding components
	 */
	private Map<Component<S, T>, Map<T, Set<Entry<T, Component<S, T>>>>> incomingReachableTransitionMap;

	/**
	 * contains the map that specifies for each component, for each out-coming
	 * port, the reachable in-coming ports and the corresponding components
	 */
	private Map<Component<S, T>, Map<T, Set<Entry<T, Component<S, T>>>>> outcomingReachableTransitionMap;

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
	public Constraint() {
		this.components = new HashSet<Component<S, T>>();
		this.incomingReachableTransitionMap = new HashMap<Component<S, T>, Map<T, Set<Entry<T, Component<S, T>>>>>();
		this.outcomingReachableTransitionMap = new HashMap<Component<S, T>, Map<T, Set<Entry<T, Component<S, T>>>>>();
		this.portValue = new HashMap<T, Color>();
	}

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
	public void addComponent(Component<S, T> component) {
		Validate.notNull(component, "The component cannot be null");

		this.components.add(component);
		this.incomingReachableTransitionMap.put(component,
				new HashMap<T, Set<Entry<T, Component<S, T>>>>());
		this.outcomingReachableTransitionMap.put(component,
				new HashMap<T, Set<Entry<T, Component<S, T>>>>());
	}

	/**
	 * returns the components involved in the constraint
	 * 
	 * @return the components involved in the constraint
	 */
	public Set<Component<S, T>> getComponents() {
		return this.components;
	}

	/**
	 * add a reachability entity, i.e., id specifies that the in-coming port of
	 * the destination component is reachable from the outComing port of the
	 * source component
	 * 
	 * @param destinationComponent
	 *            is the component which is reachable from the source Component
	 * @param incomingPort
	 *            is the in-coming reachable port of the destination Component
	 * @param sourceComponent
	 *            is the component from which the destination component can be
	 *            reached
	 * @param outComingPort
	 *            is the out-coming port from which the incomingPort of the
	 *            destination component can be reached.
	 * @throws NullPointerException
	 *             if the destinationComponent, the incomingPort, the
	 *             sourceComponent and the outComingPort is null throws
	 * @throws IllegalArgumentException
	 *             if the incomingPort is not an incoming port if the
	 *             destination component
	 * @throws IllegalArgumentException
	 *             if the outComingPort is not an out-coming port of the source
	 *             component
	 * @throws IllegalArgumentException
	 *             if the source or the destination component is not contained
	 *             into the set of components
	 */
	public void addReachabilityRelation(Component<S, T> destinationComponent,
			T incomingPort, Component<S, T> sourceComponent, T outComingPort) {
		// validates the parameters
		Validate.notNull(destinationComponent,
				"The destination component cannot be null");
		Validate.notNull(incomingPort, "The incomingPort port cannot be null");
		Validate.notNull(sourceComponent,
				"The sourceComponent component cannot be null");
		Validate.notNull(outComingPort, "The outcomingPort port cannot be null");
		Validate.isTrue(components.contains(destinationComponent));
		Validate.isTrue(components.contains(sourceComponent));
		Validate.isTrue(destinationComponent.getIncomingPorts().contains(
				incomingPort));
		Validate.isTrue(sourceComponent.getOutcomingPorts().contains(
				outComingPort));
		if (this.incomingReachableTransitionMap.get(destinationComponent).get(
				incomingPort) == null) {
			this.incomingReachableTransitionMap.get(destinationComponent).put(
					incomingPort, new HashSet<Entry<T, Component<S, T>>>());
		}
		this.incomingReachableTransitionMap
				.get(destinationComponent)
				.get(incomingPort)
				.add(new AbstractMap.SimpleEntry<T, Component<S, T>>(
						outComingPort, sourceComponent));

		if (this.outcomingReachableTransitionMap.get(sourceComponent).get(
				outComingPort) == null) {
			this.outcomingReachableTransitionMap.get(sourceComponent).put(
					outComingPort, new HashSet<Entry<T, Component<S, T>>>());
		}
		this.outcomingReachableTransitionMap
				.get(sourceComponent)
				.get(outComingPort)
				.add(new AbstractMap.SimpleEntry<T, Component<S, T>>(
						incomingPort, destinationComponent));

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
