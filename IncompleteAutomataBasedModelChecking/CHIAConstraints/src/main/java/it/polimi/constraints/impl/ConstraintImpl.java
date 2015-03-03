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

import org.apache.commons.lang3.Validate;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DirectedPseudograph;

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
public class ConstraintImpl<S extends State, T extends Transition, A extends BA<S, T>>
		implements Constraint<S, T, A> {

	/**
	 * is the set of the components to be considered in the refinement of the
	 * transparent states
	 */
	private Set<Component<S, T, A>> components;

	
	private DirectedPseudograph<Port<S, T>, DefaultEdge> portsGraph;

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
	private Map< Component<S, T, A>, Set<Port<S, T>>> incomingPorts;

	/**
	 * contains the out coming transitions of the component. The map contains
	 * the source state of the transition, i.e., the state of this component
	 * from which the transition starts, the destination state of the transition
	 * and the transition it self
	 */
	private Map< Component<S, T, A>, Set<Port<S, T>>> outcomingPorts;
	
	/**
	 * creates a new empty constraint
	 */
	public ConstraintImpl() {
		this.components = new HashSet<Component<S, T, A>>();
		this.portValue = new HashMap<Port<S, T>, Color>();
		this.portsGraph=new DirectedPseudograph<Port<S,T>, DefaultEdge>(DefaultEdge.class);
		this.mapPortComponent=new HashMap<Port<S, T>, Component<S, T, A>>();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addComponent(Component<S, T, A> component) {
		Preconditions.checkNotNull(component, "The component cannot be null");

		this.components.add(component);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void updatePortRelation(Set<Port<S, T>> incomingPorts,
			Set<Port<S, T>> outcomingPorts) {

		Set<Port<S, T>> yellowIncomingPorts=new HashSet<Port<S, T>>();
		// removing red and the yellow incoming ports and back propagating
		for (Port<S, T> incomingPort : incomingPorts) {
			if(!incomingPort.isIncoming()){
				throw new InternalError("The incoming port cannot be null");
			}
			if (this.portValue.get(incomingPort) == Color.RED) {
				
				Set<Port<S, T>> newRedPorts=new HashSet<Port<S, T>>();
				for(DefaultEdge e: this.portsGraph.incomingEdgesOf(incomingPort)){
					Port<S, T> source = this.portsGraph.getEdgeSource(e);
					this.portValue.put(source, Color.RED);
				}
				for(Port<S, T> source :newRedPorts){
					this.portsGraph.removeAllEdges(this.portsGraph.outgoingEdgesOf(source));
				}
			}
			if (this.portValue.get(incomingPort) == Color.YELLOW) {
				yellowIncomingPorts.add(incomingPort);
			}
				
		}
		
		Set<Port<S, T>> yellowOutcomingPorts=new HashSet<Port<S, T>>();
		// removing green out-coming ports and forward propagating
		for (Port<S, T> outcomingPort : outcomingPorts) {
			if(outcomingPort.isIncoming()){
				throw new InternalError("The incoming port cannot be null");
			}
			if (this.portValue.get(outcomingPort) == Color.GREEN) {
				
				Set<Port<S, T>> newGreenPorts=new HashSet<Port<S, T>>();
				for(DefaultEdge e: this.portsGraph.outgoingEdgesOf(outcomingPort)){
					Port<S, T> destination = this.portsGraph.getEdgeTarget(e);
					this.portValue.put(destination, Color.RED);
				}
				for(Port<S, T> target :newGreenPorts){
					this.portsGraph.removeAllEdges(this.portsGraph.incomingEdgesOf(target));
				}
			}
			if (this.portValue.get(outcomingPort) == Color.YELLOW) {
				yellowOutcomingPorts.add(outcomingPort);
			}
		}
		for(Port<S, T> incoming: yellowIncomingPorts){
			for(Port<S, T> outcomingPort: yellowOutcomingPorts){
				for(DefaultEdge incomingEdge: this.portsGraph.incomingEdgesOf(incoming)){
					for(DefaultEdge outcomingEdge: this.portsGraph.incomingEdgesOf(outcomingPort)){
						this.portsGraph.addEdge(this.portsGraph.getEdgeSource(incomingEdge), this.portsGraph.getEdgeTarget(outcomingEdge));
					}
				}
			}
		}
		
		/*
		 * removes from the automaton the sub-properties which are not any more
		 * of interest
		 */
		for (Port<S, T> outcomingPort : outcomingPorts) {
			this.removeOutComingPort(outcomingPort);
		}
		this.portsGraph.removeAllVertices(outcomingPorts);
		
		for (Port<S, T> incomingPort : incomingPorts) {
			this.removeIncomintPort(incomingPort);
		}
		this.portsGraph.removeAllVertices(incomingPorts);
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
		this.portsGraph.addEdge(sourcePort, destinationPort);

	}

	

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setPortValue(Port<S, T> port, Color color) {
		Preconditions.checkNotNull(port, "The port cannot be null");
		Preconditions.checkNotNull(color, "The value cannot be null");

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
		Validate.isTrue(!this.portValue.keySet().contains(port),
				"The port must be contained into the set of the ports: ", port);
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
	public void addIncomingPort(Component<S, T, A> component, Port<S,T> port){
		Preconditions.checkArgument(component.getAutomaton().getStates().contains(port.getDestination()), "The destination state must be contained into the states of the component");
		
		this.incomingPorts.get(component).add(port);
		this.mapPortComponent.put(port, component);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addOutComingPort(Component<S, T, A> component, Port<S,T> port) {
		Preconditions
		.checkNotNull(port, "The port state cannot be null");
		Preconditions.checkArgument(component.getAutomaton().getStates().contains(port.getSource()), "The source state "+port.getSource()+" must be contained into the states of the component "+component.getName());
		
		this.outcomingPorts.get(component).add(port);
		this.mapPortComponent.put(port, component);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Set<Port<S, T>> getIncomingPorts(Component<S, T, A> component) {
		return this.incomingPorts.get(component);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Set<Port<S, T>> getOutcomingPorts(Component<S, T, A> component) {
		return this.outcomingPorts.get(component);
	}
	
	@Override
	public void removeIncomintPort(Port<S, T> port) {
		Component<S, T, A> component=this.mapPortComponent.get(port);
		this.incomingPorts.get(component).remove(port);
		this.mapPortComponent.remove(port);
		
		
	}

	@Override
	public void removeOutComingPort(Port<S, T> port) {
		Component<S, T, A> component=this.mapPortComponent.get(port);
		this.outcomingPorts.get(component).remove(port);
		this.mapPortComponent.remove(port);
	}

	@Override
	public DirectedPseudograph<Port<S, T>, DefaultEdge> getPortsGraph() {
		return this.portsGraph;
	}
}
