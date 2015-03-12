package it.polimi.constraints.impl;

import it.polimi.automata.BA;
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

import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;

import com.google.common.base.Preconditions;

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
 * @param <A>
 *            is the type of the automaton which is used to specify the
 *            constraint
 */
public class ConstraintImpl<S extends State, T extends Transition,  A extends BA<S, T>>
		implements Constraint<S, T, A> {

	/**
	 * is the set of the components to be considered in the refinement of the
	 * transparent states
	 */
	private Set<Component<S, T, A>> components;

	private DefaultDirectedGraph<Port<S, T>, DefaultEdge> portsGraph;

	/**
	 * specifies for each port the corresponding color The red color means that
	 * from this port it is possible to reach an accepting state The green color
	 * means that the port is reachable from an initial state The yellow color
	 * means that the port is possibly reachable from an initial state and from
	 * the port it is possibly reachable an accepting state
	 */
	private Map<Port<S, T>, Color> portValue;

	private Map<Port<S, T>, Component<S, T, A>> mapPortComponent;

	/**
	 * contains the incoming transitions of the component. The map contains the
	 * destination state of the transition, i.e., the state of this component to
	 * which the transition is connected, the source state of the transition and
	 * the transition itself
	 */
	private Map<Component<S, T, A>, Set<Port<S, T>>> incomingPorts;

	/**
	 * contains the out coming transitions of the component. The map contains
	 * the source state of the transition, i.e., the state of this component
	 * from which the transition starts, the destination state of the transition
	 * and the transition it self
	 */
	private Map<Component<S, T, A>, Set<Port<S, T>>> outcomingPorts;

	/**
	 * creates a new empty constraint
	 */
	public ConstraintImpl() {
		this.components = new HashSet<Component<S, T, A>>();
		this.portValue = new HashMap<Port<S, T>, Color>();
		this.portsGraph = new DefaultDirectedGraph<Port<S, T>, DefaultEdge>(
				DefaultEdge.class);
		this.mapPortComponent = new HashMap<Port<S, T>, Component<S, T, A>>();
		this.incomingPorts = new HashMap<Component<S, T, A>, Set<Port<S, T>>>();
		this.outcomingPorts = new HashMap<Component<S, T, A>, Set<Port<S, T>>>();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addComponent(Component<S, T, A> component) {
		Preconditions.checkNotNull(component, "The component cannot be null");

		this.components.add(component);
		this.incomingPorts.put(component, new HashSet<Port<S, T>>());
		this.outcomingPorts.put(component, new HashSet<Port<S, T>>());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Set<Component<S, T, A>> getComponents() {
		return this.components;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addReachabilityRelation(Port<S, T> sourcePort,
			Port<S, T> destinationPort) {
		// validates the parameters
		Preconditions.checkNotNull(sourcePort,
				"The incomingPort port cannot be null");
		Preconditions.checkNotNull(destinationPort,
				"The outcomingPort port cannot be null");
		Preconditions.checkArgument(this.portsGraph.containsVertex(sourcePort), "The vertex: "+sourcePort+" is not contained in the graph");
		Preconditions.checkArgument(this.portsGraph.containsVertex(destinationPort), "The vertex: "+destinationPort+" is not contained in the graph");

		this.portsGraph.addEdge(sourcePort, destinationPort);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setPortValue(Port<S, T> port, Color color) {
		Preconditions.checkNotNull(port, "The port cannot be null");
		Preconditions.checkNotNull(color, "The value cannot be null");
		Preconditions.checkArgument(this.portsGraph.containsVertex(port), "The vertex: "+port+" is not contained in the graph");


		this.portValue.put(port, color);
	}

	public Map<Port<S, T>, Color> getPortValue() {
		return this.portValue;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Color getPortValue(Port<S, T> port) {
		Preconditions.checkNotNull(port, "The port cannot be null");
		Preconditions.checkArgument(this.portValue.containsKey(port),
				"The port " + port
						+ " must be contained into the set of the ports: ",
				port);
		return this.portValue.get(port);
	}

	public String toString() {
		return this.portValue.toString();
	}

	@Override
	public int getTotalStates() {
		int totalStates = 0;
		for (Component<S, T, A> c : components) {
			totalStates = totalStates + c.getAutomaton().getStates().size();
		}
		return totalStates;
	}

	@Override
	public int getTotalTransitions() {
		int totalTransitions = 0;
		for (Component<S, T, A> c : components) {
			totalTransitions = totalTransitions
					+ c.getAutomaton().getTransitions().size();
		}
		return totalTransitions;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Set<S> getConstrainedStates() {
		Set<S> constrainedStates = new HashSet<S>();
		for (Component<S, T, A> c : components) {
			constrainedStates.add(c.getModelState());
		}
		return constrainedStates;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Component<S, T, A> getSubproperties(S transparentState) {
		Preconditions.checkNotNull(transparentState,
				"The transparent state under analysis cannot be null");
		Preconditions
				.checkArgument(
						this.getConstrainedStates().contains(transparentState),
						"The state "
								+ transparentState
								+ " is not contained into the set of constrained states");

		for (Component<S, T, A> c : components) {
			if (c.getModelState().equals(transparentState)) {
				return c;
			}
		}
		throw new IllegalArgumentException("The state " + transparentState
				+ " is not contained into the set of constrained states");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addIncomingPort(Component<S, T, A> component, Port<S, T> port) {
		Preconditions.checkArgument(component.getAutomaton().getStates()
				.contains(port.getDestination()), "The destination state "
				+ port.getDestination()
				+ " must be contained into the states of the component");

		this.incomingPorts.get(component).add(port);
		this.mapPortComponent.put(port, component);
		this.portValue.put(port, Color.YELLOW);
		this.portsGraph.addVertex(port);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addOutComingPort(Component<S, T, A> component, Port<S, T> port) {
		Preconditions.checkNotNull(component, "The component cannot be null");

		Preconditions.checkNotNull(port, "The port state cannot be null");
		Preconditions.checkArgument(this.components.contains(component),
				"The component " + component
						+ " must be contained into the set of components");
		Preconditions
				.checkArgument(
						component.getAutomaton().getStates()
								.contains(port.getSource()),
						"The source state "
								+ port.getSource()
								+ " must be contained into the states of the component "
								+ component.getName());

		this.outcomingPorts.get(component).add(port);
		this.mapPortComponent.put(port, component);
		this.portValue.put(port, Color.YELLOW);
		this.portsGraph.addVertex(port);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Set<Port<S, T>> getIncomingPorts(Component<S, T, A> component) {
		Preconditions.checkNotNull(component, "The component cannot be null");
		Preconditions.checkArgument(this.components.contains(component),
				"The component " + component
						+ " is not contained into the set of components");
		Preconditions.checkArgument(this.incomingPorts.containsKey(component),
				"The component " + component
						+ " is not be contained into the map components -port");

		return this.incomingPorts.get(component);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Set<Port<S, T>> getOutcomingPorts(Component<S, T, A> component) {
		Preconditions.checkNotNull(component, "The component cannot be null");

		return this.outcomingPorts.get(component);
	}

	@Override
	public void removeIncomintPort(Port<S, T> port) {
		Preconditions.checkNotNull(port, "The port cannot be null");
		Preconditions.checkArgument(this.mapPortComponent.containsKey(port),
				"The port " + port + " is not a port of the constraint");
		Component<S, T, A> component = this.mapPortComponent.get(port);
		this.incomingPorts.get(component).remove(port);
		this.mapPortComponent.remove(port);
		this.portValue.remove(port);
		this.portsGraph.removeVertex(port);

	}

	@Override
	public void removePort(Port<S, T> port) {
		Preconditions.checkNotNull(port, "The port cannot be null");
		Preconditions.checkArgument(this.mapPortComponent.containsKey(port),
				"The port " + port + " is not a port of the constraint");
		Component<S, T, A> component = this.mapPortComponent.get(port);
		this.incomingPorts.get(component).remove(port);
		this.outcomingPorts.get(component).remove(port);
		this.portValue.remove(port);
		this.portsGraph.removeVertex(port);

	}

	@Override
	public void removeOutComingPort(Port<S, T> port) {
		Preconditions.checkNotNull(port, "The port cannot be null");
		Preconditions.checkArgument(this.mapPortComponent.containsKey(port),
				"The port " + port + " is not a port of the constraint");

		Component<S, T, A> component = this.mapPortComponent.get(port);
		this.outcomingPorts.get(component).remove(port);
		this.mapPortComponent.remove(port);
		this.portValue.remove(port);
		this.portsGraph.removeVertex(port);
	}

	@Override
	public DefaultDirectedGraph<Port<S, T>, DefaultEdge> getPortsGraph() {
		return this.portsGraph;
	}

	@Override
	public void setPortGraph(
			DefaultDirectedGraph<Port<S, T>, DefaultEdge> portsGraph) {
		this.portsGraph = portsGraph;

	}

	public void replace(
			Component<S, T, A> oldComponent,
			ConstraintImpl<S, T, A> newConstraint,
			Map<Port<S, T>, Set<Port<S, T>>> mapOldPropertyNewConstraintIncomingPorts,
			Map<Port<S, T>, Set<Port<S, T>>> mapOldPropertyNewConstraintOutcomingPorts,
			Map<Port<S, T>, Port<S, T>> intersectionIncomingPortClaimPortMap,
			Map<Port<S, T>, Port<S, T>> intersectionOutcomingPortClaimPortMap
			)

	{
		Preconditions.checkNotNull(oldComponent, "The component to be replaced cannot be null");
		Preconditions.checkNotNull(newConstraint, "The new constraint cannot be null");
		Preconditions.checkArgument(this.components.contains(oldComponent), "The old component must be contained into the set of the components of the constraint");

		this.union(newConstraint);
		// coping the old INCOMING transition to the new ports 
		for(Port<S, T> oldIncomingPort: mapOldPropertyNewConstraintIncomingPorts.keySet()){
			if(intersectionIncomingPortClaimPortMap.containsKey(oldIncomingPort)){
				for(DefaultEdge e: this.portsGraph.incomingEdgesOf(intersectionIncomingPortClaimPortMap.get(oldIncomingPort))){
					for(Port<S, T> newIncomingPort: mapOldPropertyNewConstraintIncomingPorts.get(oldIncomingPort)){
						this.portsGraph.addEdge(this.portsGraph.getEdgeSource(e), newIncomingPort);
					}
				}
			}
		}
		// coping the old OUTCOMING transitions to the new ports
		for(Port<S, T> oldOutcomingPort: mapOldPropertyNewConstraintOutcomingPorts.keySet()){
			if(intersectionOutcomingPortClaimPortMap.containsKey(oldOutcomingPort)){
				for(DefaultEdge e: this.portsGraph.outgoingEdgesOf(intersectionOutcomingPortClaimPortMap.get(oldOutcomingPort))){
					for(Port<S, T> newOutcomingPort: mapOldPropertyNewConstraintOutcomingPorts.get(oldOutcomingPort)){
						this.portsGraph.addEdge(newOutcomingPort, this.portsGraph.getEdgeTarget(e));
					}
				}
			}
		}
		
		this.removeComponent(oldComponent);
	}

	@Override
	public void union(ConstraintImpl<S, T, A> other) {
		Preconditions.checkNotNull(other, "The other component cannot be null");

		this.mapPortComponent.putAll(other.mapPortComponent);
		this.components.addAll(other.getComponents());
		this.incomingPorts.putAll(other.incomingPorts);
		this.outcomingPorts.putAll(other.outcomingPorts);
		this.portValue.putAll(other.portValue);
		// generates the union of the two graphs
		Graphs.addGraph(this.portsGraph, other.portsGraph);
	}

	@Override
	public void removeComponent(Component<S, T, A> component) {

		Preconditions.checkNotNull(component,
				"The other component cannot be null");
		this.components.remove(component);

		Set<Port<S, T>> incomingPorts = new HashSet<Port<S, T>>();
		incomingPorts.addAll(this.incomingPorts.get(component));

		for (Port<S, T> incomingPort : incomingPorts) {
			this.removePort(incomingPort);
		}

		Set<Port<S, T>> outComingPorts = new HashSet<Port<S, T>>();
		outComingPorts.addAll(this.outcomingPorts.get(component));

		for (Port<S, T> outcomingPort : outComingPorts) {
			this.removePort(outcomingPort);

		}

		this.incomingPorts.remove(component);
		this.outcomingPorts.remove(component);
	}

	@Override
	public Set<Port<S, T>> getIncomingPorts() {
		Set<Port<S, T>> toBeReturned = new HashSet<Port<S, T>>();
		for (Set<Port<S, T>> ports : this.incomingPorts.values()) {
			toBeReturned.addAll(ports);
		}
		return toBeReturned;
	}

	@Override
	public Set<Port<S, T>> getOutcomingPorts() {
		Set<Port<S, T>> toBeReturned = new HashSet<Port<S, T>>();
		for (Set<Port<S, T>> ports : this.outcomingPorts.values()) {
			toBeReturned.addAll(ports);
		}
		return toBeReturned;
	}

	@Override
	public Set<Port<S, T>> getPorts() {
		Set<Port<S, T>> toBeReturned = new HashSet<Port<S, T>>();
		toBeReturned.addAll(getIncomingPorts());
		toBeReturned.addAll(getOutcomingPorts());
		return toBeReturned;
	}

	@Override
	public Component<S, T, A> getComponent(Port<S, T> port) {
		Preconditions.checkNotNull(port, "The port of the component cannot be null");
		Preconditions.checkArgument(this.mapPortComponent.containsKey(port), "The port "+port+" is not contained in the mapo port components");
		return this.mapPortComponent.get(port);
	}
}
