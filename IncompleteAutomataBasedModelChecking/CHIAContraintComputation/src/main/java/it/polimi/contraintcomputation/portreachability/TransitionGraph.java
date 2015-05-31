package it.polimi.contraintcomputation.portreachability;

import it.polimi.automata.BA;
import it.polimi.automata.state.State;
import it.polimi.automata.transition.Transition;

import java.util.HashSet;
import java.util.Set;

import org.jgrapht.DirectedGraph;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;

import com.google.common.base.Preconditions;

/**
 * computes a graph which specify for each transition the set of the possible
 * transitions that cannot be fired after that transition. Two transitions are
 * connected if and only if there exists a state in the set of the
 * traversableStates that is a common vertex between the two transitions.
 * 
 * @author Claudio Menghi
 *
 */
public class TransitionGraph {

	/**
	 * contains the graph of the transitions. Transitions represent nodes of the
	 * graph which are connected if and only if there exists a state in the set
	 * of the traversableStates that is a common vertex between the transition.
	 */
	private final DirectedGraph<Transition, DefaultEdge> transitionGraph;

	/**
	 * the set of initial transitions
	 */
	private final Set<Transition> sourceTransitions;

	/**
	 * the set of the destination transitions
	 */
	private final Set<Transition> destinationTransitions;

	/**
	 * the set of the traversable states
	 */
	private final Set<State> traversableStates;

	/**
	 * the ba to be considered in the port reachability computation
	 */
	private final BA ba;
	
	private final Set<Transition> visitedTransitions;

	/**
	 * creates a new port graph
	 * 
	 * @param sourceTransitions
	 *            is the set of the sourceTransition of the transition graph are
	 *            the initial transitions for the port graph computation
	 * @param destinationTransitions
	 *            is the set of the destinationTransitions of the graph whenever
	 *            a destination transition is founded none of its successors are
	 *            added
	 * @param traversableStates
	 *            is the set of the traversable states. Contains the states that
	 *            are traversable in the graph exploration
	 * @param ba
	 *            is the ba to be considered
	 */
	public TransitionGraph(Set<Transition> sourceTransitions,
			Set<Transition> destinationTransitions,
			Set<State> traversableStates, BA ba) {

		Preconditions.checkNotNull(sourceTransitions,
				"The set of the sourceTransitions cannot be null");
		Preconditions.checkNotNull(destinationTransitions,
				"The set of the destinationTransitions cannot be null");
		Preconditions.checkNotNull(traversableStates,
				"The set of the traversable states cannot be null");
		Preconditions.checkNotNull(ba, "Is the ba to be considered");

		this.sourceTransitions = sourceTransitions;
		this.destinationTransitions = destinationTransitions;
		this.traversableStates = traversableStates;
		this.transitionGraph = new DefaultDirectedGraph<Transition, DefaultEdge>(
				DefaultEdge.class);
		this.ba = ba;
		this.visitedTransitions=new HashSet<Transition>();
	}

	
	public void populateGraph() {
		for(Transition transition: sourceTransitions){
			this.transitionGraph.addVertex(transition);
			this.analyzeTransition(transition);
		}
		
	}
	
	private void analyzeTransition(Transition transition){
		
		if(this.destinationTransitions.contains(transition) || this.visitedTransitions.contains(transition)){
			return; 
		}
		this.visitedTransitions.add(transition);
		State nextState=this.ba.getTransitionDestination(transition);
		if(this.traversableStates.contains(nextState)){
			for(Transition nextTransition: this.ba.getOutTransitions(nextState)){
				if(!this.transitionGraph.containsVertex(nextTransition)){
					this.transitionGraph.addVertex(nextTransition);
				}
				this.transitionGraph.addEdge(transition, nextTransition);
				this.analyzeTransition(nextTransition);
			}
		}
	}
	
	public DirectedGraph<Transition, DefaultEdge> getTransitionGraph(){
		this.populateGraph();
		return this.transitionGraph;
	}
}
