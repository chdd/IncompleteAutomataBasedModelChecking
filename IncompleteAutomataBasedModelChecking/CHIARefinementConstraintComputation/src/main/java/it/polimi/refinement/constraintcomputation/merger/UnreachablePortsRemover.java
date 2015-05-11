package it.polimi.refinement.constraintcomputation.merger;

import it.polimi.constraints.Constraint;
import it.polimi.constraints.ColoredPort;
import it.polimi.constraints.SubProperty;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.jgrapht.Graphs;

import com.google.common.base.Preconditions;

/**
 * given a set of incoming ports removes the incoming ports and all of its
 * successors which are not any more reachable given a set of out-coming ports
 * removes the out-coming ports and all of its predecessors which are not any
 * more reachable. <br>
 * 
 * The UnreachablePortsRemover explores the port graphs, starting from the
 * incoming ports (in the method backRemover) or outcoming ports (in the method
 * forwardRemover) and counts how many times the nodes of the graph (the ports)
 * are hit. If the number corresponds to the number of outcoming transitions
 * 
 * @author claudiomenghi
 *
 */
public class UnreachablePortsRemover {

	/**
	 * contains the set of incoming ports
	 */
	private final Set<ColoredPort> incomingPorts;

	/**
	 * contains the set of outcoming ports
	 */
	private final Set<ColoredPort> outcomingPorts;

	private final Map<ColoredPort, Integer> backMapCount;
	private final Map<ColoredPort, Integer> forwardMapCount;
	/**
	 * contains the constraint to be considered
	 */
	private final Constraint constraint;

	/**
	 * creates a new component which is in charge of removing the non reachable
	 * ports either incoming and outcoming. In the first case their predecessors
	 * which are not any more reachable are removed. In the second case, their
	 * successors which are not any more reachable are removed
	 * 
	 * @param incomingPorts
	 *            is the set of incomingPorts to be removed
	 * @param outcomingPorts
	 *            is the set of outcomingPorts to be removed
	 * @param constraint
	 *            is the constraint to be considered
	 * @throws NullPointerException
	 *             if one of the parameters is null
	 */
	public UnreachablePortsRemover(Set<ColoredPort> incomingPorts,
			Set<ColoredPort> outcomingPorts, Constraint constraint) {
		Preconditions.checkNotNull(incomingPorts);
		Preconditions.checkNotNull(outcomingPorts);
		Preconditions.checkNotNull(constraint);
		this.incomingPorts = incomingPorts;
		this.outcomingPorts = outcomingPorts;
		this.constraint = constraint;
		this.backMapCount = new HashMap<ColoredPort, Integer>();
		for (ColoredPort p : constraint.getPortsGraph().vertexSet()) {
			this.backMapCount.put(p, 0);
		}
		this.forwardMapCount = new HashMap<ColoredPort, Integer>();
		for (ColoredPort p : constraint.getPortsGraph().vertexSet()) {
			this.forwardMapCount.put(p, 0);
		}
	}

	public void remove() {
		Set<ColoredPort> toBeRemoved = new HashSet<ColoredPort>();
		toBeRemoved.addAll(this.backRemover());
		toBeRemoved.addAll(this.forwardRemover());
		for (SubProperty subproperty : this.constraint.getSubProperties()) {
			for (ColoredPort incomingPort : subproperty.getIncomingPorts()) {
				if (toBeRemoved.contains(incomingPort)) {
					subproperty.removePort(incomingPort);
					this.constraint.getPortsGraph().removeVertex(incomingPort);
				}
			}
			for (ColoredPort outcomingPort : subproperty.getOutcomingPorts()) {
				if (toBeRemoved.contains(outcomingPort)) {
					subproperty.removePort(outcomingPort);
					this.constraint.getPortsGraph().removeVertex(outcomingPort);
				}
			}
		}
	}

	private Set<ColoredPort> backRemover() {
		Set<ColoredPort> hashedPorts = new HashSet<ColoredPort>();
		Set<ColoredPort> toBeAnalyzed = new HashSet<ColoredPort>();
		toBeAnalyzed.addAll(this.incomingPorts);

		while (!toBeAnalyzed.isEmpty()) {
			ColoredPort port = toBeAnalyzed.iterator().next();
			for (ColoredPort predecessor : Graphs.predecessorListOf(
					constraint.getPortsGraph(), port)) {
				this.backMapCount.put(predecessor, this.backMapCount.get(predecessor) + 1);
				
				if (!hashedPorts.contains(predecessor)
						&& Graphs.successorListOf(constraint.getPortsGraph(),
								predecessor).size() == this.backMapCount
								.get(predecessor)) {
					toBeAnalyzed.add(predecessor);
				}
			}
			hashedPorts.add(port);
			toBeAnalyzed.remove(port);
		}
		
		return hashedPorts;
	}

	private Set<ColoredPort> forwardRemover() {
		Set<ColoredPort> hashedPorts = new HashSet<ColoredPort>();
		Set<ColoredPort> toBeAnalyzed = new HashSet<ColoredPort>();
		toBeAnalyzed.addAll(this.outcomingPorts);

		while (!toBeAnalyzed.isEmpty()) {
			ColoredPort port = toBeAnalyzed.iterator().next();
			for (ColoredPort successor : Graphs.successorListOf(
					constraint.getPortsGraph(), port)) {
				this.forwardMapCount.put(successor, this.forwardMapCount.get(successor) + 1);
				
				if (!hashedPorts.contains(successor)&& Graphs.predecessorListOf(constraint.getPortsGraph(),
						successor).size() == this.forwardMapCount
						.get(successor)) {
					toBeAnalyzed.add(successor);
				}
			}
			hashedPorts.add(port);
			toBeAnalyzed.remove(port);
		}
		
		return hashedPorts;
	}
}
