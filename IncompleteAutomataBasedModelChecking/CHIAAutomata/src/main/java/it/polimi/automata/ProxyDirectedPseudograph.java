package it.polimi.automata;

import java.util.Set;

import org.jgrapht.EdgeFactory;
import org.jgrapht.graph.AbstractGraph;

import com.google.common.base.Preconditions;

/**
 * is a proxy version of the DirectedPseudograph. It hides all the methods that allows to change the graph
 * @author Claudio1
 *
 */
public class ProxyDirectedPseudograph<S,T> extends AbstractGraph<S,T>{
	
	private AbstractGraph<S,T> graph;
	
	public ProxyDirectedPseudograph(AbstractGraph<S,T> graph){
		Preconditions.checkNotNull(graph);
		this.graph=graph;
	}

	@Override
	public Set<T> getAllEdges(S sourceVertex, S targetVertex) {
		return graph.getAllEdges(sourceVertex, targetVertex);
	}

	@Override
	public T getEdge(S sourceVertex, S targetVertex) {
		return graph.getEdge(sourceVertex, targetVertex);
	}

	@Override
	public EdgeFactory<S, T> getEdgeFactory() {
		return graph.getEdgeFactory();
	}

	@Override
	public T addEdge(S sourceVertex, S targetVertex) {
		throw new UnsupportedOperationException("You cannot change the graph underling a BA"); 
	}

	@Override
	public boolean addEdge(S sourceVertex, S targetVertex, T e) {
		throw new UnsupportedOperationException("You cannot change the graph underling a BA");
	}

	@Override
	public boolean addVertex(S v) {
		throw new UnsupportedOperationException("You cannot change the graph underling a BA");
	}

	@Override
	public boolean containsEdge(T e) {
		return graph.containsEdge(e);
	}

	@Override
	public boolean containsVertex(S v) {
		return this.graph.containsVertex(v);
	}

	@Override
	public Set<T> edgeSet() {
		return this.graph.edgeSet();
	}

	@Override
	public Set<T> edgesOf(S vertex) {
		return this.graph.edgesOf(vertex);
	}

	@Override
	public T removeEdge(S sourceVertex, S targetVertex) {
		throw new UnsupportedOperationException("You cannot change the graph underling a BA");
	}

	@Override
	public boolean removeEdge(T e) {
		throw new UnsupportedOperationException("You cannot change the graph underling a BA");
	}

	@Override
	public boolean removeVertex(S v) {
		throw new UnsupportedOperationException("You cannot change the graph underling a BA");
	}

	@Override
	public Set<S> vertexSet() {
		return graph.vertexSet();
	}

	@Override
	public S getEdgeSource(T e) {
		return graph.getEdgeSource(e);
	}

	@Override
	public S getEdgeTarget(T e) {
		return graph.getEdgeTarget(e);
	}

	@Override
	public double getEdgeWeight(T e) {
		return graph.getEdgeWeight(e);
	}
}
