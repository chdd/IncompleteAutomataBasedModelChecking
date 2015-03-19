package it.polimi.constraints.impl;

import it.polimi.automata.state.State;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;

import com.google.common.base.Preconditions;

/**
 * The Constraint class describes the constraint associated with the
 * satisfaction of a claim on a model. The constraint contains the set of
 * sub-properties associated with the transparent states of the model, a map
 * that contains for each sub-property how they are connected with the original
 * model (i.e., the in-coming and out-coming) transitions of the sub-property,
 * the color of this ports and a graph that describes how the sub-property are
 * related, i.e., how the ports of the sub-properties are connected.
 * 
 * @author claudiomenghi
 *
 */
public class Constraint {

	/**
	 * is the set of the components to be considered in the refinement of the
	 * transparent states
	 */
	private Set<SubProperty> subProperties;

	private DefaultDirectedGraph<Port, DefaultEdge> portsGraph;

	



	/**
	 * creates a new empty constraint
	 */
	public Constraint() {
		this.subProperties = new HashSet<SubProperty>();
		this.portsGraph = new DefaultDirectedGraph<Port, DefaultEdge>(
				DefaultEdge.class);
		
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
	public void addComponent(SubProperty component) {
		Preconditions.checkNotNull(component, "The component cannot be null");

		this.subProperties.add(component);
	}

	/**
	 * returns the components involved in the constraint
	 * 
	 * @return the components involved in the constraint
	 */
	public Set<SubProperty> getComponents() {
		return this.subProperties;
	}

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
	public void addReachabilityRelation(Port sourcePort, Port destinationPort) {
		// validates the parameters
		Preconditions.checkNotNull(sourcePort,
				"The incomingPort port cannot be null");
		Preconditions.checkNotNull(destinationPort,
				"The outcomingPort port cannot be null");
		Preconditions.checkArgument(this.portsGraph.containsVertex(sourcePort),
				"The vertex: " + sourcePort + " is not contained in the graph");
		Preconditions.checkArgument(
				this.portsGraph.containsVertex(destinationPort), "The vertex: "
						+ destinationPort + " is not contained in the graph");

		this.portsGraph.addEdge(sourcePort, destinationPort);
	}



	public int getTotalStates() {
		int totalStates = 0;
		for (SubProperty c : subProperties) {
			totalStates = totalStates + c.getAutomaton().getStates().size();
		}
		return totalStates;
	}

	public int getTotalTransitions() {
		int totalTransitions = 0;
		for (SubProperty c : subProperties) {
			totalTransitions = totalTransitions
					+ c.getAutomaton().getTransitions().size();
		}
		return totalTransitions;
	}

	/**
	 * returns the set of the states of the model constrained by the constraint
	 * 
	 * @return the set of the states of the model constrained by the constraint
	 */
	public Set<State> getConstrainedStates() {
		Set<State> constrainedStates = new HashSet<State>();
		for (SubProperty c : subProperties) {
			constrainedStates.add(c.getModelState());
		}
		return constrainedStates;
	}

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
	public SubProperty getSubproperties(State transparentState) {
		Preconditions.checkNotNull(transparentState,
				"The transparent state under analysis cannot be null");
		Preconditions
				.checkArgument(
						this.getConstrainedStates().contains(transparentState),
						"The state "
								+ transparentState
								+ " is not contained into the set of constrained states");

		for (SubProperty c : subProperties) {
			if (c.getModelState().equals(transparentState)) {
				return c;
			}
		}
		throw new IllegalArgumentException("The state " + transparentState
				+ " is not contained into the set of constrained states");
	}

	

	public DefaultDirectedGraph<Port, DefaultEdge> getPortsGraph() {
		return this.portsGraph;
	}

	public void setPortGraph(DefaultDirectedGraph<Port, DefaultEdge> portsGraph) {
		this.portsGraph = portsGraph;

	}

	public void replace(SubProperty oldComponent, Constraint newConstraint,
			Map<Port, Set<Port>> mapOldPropertyNewConstraintIncomingPorts,
			Map<Port, Set<Port>> mapOldPropertyNewConstraintOutcomingPorts,
			Map<Port, Port> intersectionIncomingPortClaimPortMap,
			Map<Port, Port> intersectionOutcomingPortClaimPortMap)

	{
		Preconditions.checkNotNull(oldComponent,
				"The component to be replaced cannot be null");
		Preconditions.checkNotNull(newConstraint,
				"The new constraint cannot be null");
		Preconditions
				.checkArgument(
						this.subProperties.contains(oldComponent),
						"The old component must be contained into the set of the components of the constraint");

		this.union(newConstraint);
		// coping the old INCOMING transition to the new ports
		for (Port oldIncomingPort : mapOldPropertyNewConstraintIncomingPorts
				.keySet()) {
			if (intersectionIncomingPortClaimPortMap
					.containsKey(oldIncomingPort)) {
				for (DefaultEdge e : this.portsGraph
						.incomingEdgesOf(intersectionIncomingPortClaimPortMap
								.get(oldIncomingPort))) {
					for (Port newIncomingPort : mapOldPropertyNewConstraintIncomingPorts
							.get(oldIncomingPort)) {
						this.portsGraph.addEdge(
								this.portsGraph.getEdgeSource(e),
								newIncomingPort);
					}
				}
			}
		}
		// coping the old OUTCOMING transitions to the new ports
		for (Port oldOutcomingPort : mapOldPropertyNewConstraintOutcomingPorts
				.keySet()) {
			if (intersectionOutcomingPortClaimPortMap
					.containsKey(oldOutcomingPort)) {
				for (DefaultEdge e : this.portsGraph
						.outgoingEdgesOf(intersectionOutcomingPortClaimPortMap
								.get(oldOutcomingPort))) {
					for (Port newOutcomingPort : mapOldPropertyNewConstraintOutcomingPorts
							.get(oldOutcomingPort)) {
						this.portsGraph.addEdge(newOutcomingPort,
								this.portsGraph.getEdgeTarget(e));
					}
				}
			}
		}

		this.removeComponent(oldComponent);
	}

	public void union(Constraint other) {
		Preconditions.checkNotNull(other, "The other component cannot be null");
		this.subProperties.addAll(other.getComponents());
		
		// generates the union of the two graphs
		Graphs.addGraph(this.portsGraph, other.portsGraph);
	}

	public void removeComponent(SubProperty component) {

		Preconditions.checkNotNull(component,
				"The other component cannot be null");
		this.subProperties.remove(component);

		Set<Port> incomingPorts = new HashSet<Port>();
		incomingPorts.addAll(component.getIncomingPorts());

		for (Port incomingPort : incomingPorts) {
			this.portsGraph.removeVertex(incomingPort);
		}

		Set<Port> outComingPorts = new HashSet<Port>();
		outComingPorts.addAll(component.getOutcomingPorts());

		for (Port outcomingPort : outComingPorts) {
			this.portsGraph.removeVertex(outcomingPort);

		}
		this.subProperties.remove(component);
	}
}
