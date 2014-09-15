package it.polimi.view.automaton;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.jgrapht.EdgeFactory;
import org.jgrapht.Graph;

import it.polimi.model.BuchiAutomaton;
import it.polimi.model.State;
import it.polimi.model.Transition;

public class BuchiAutomatonDecorated<S extends State, T extends Transition<S>> extends BuchiAutomaton<S, T> implements Graph<S, T>{

	@Override
	public Set<T> getAllEdges(S sourceVertex, S targetVertex) {
		
		Set<T> ret=new HashSet<T>();
		for(T t: this.getTransitionsWithSource(sourceVertex)){
			if(t.getDestination().equals(targetVertex)){
				ret.add(t);
			}
		}
		return ret;
	}

	@Override
	public T getEdge(S sourceVertex, S targetVertex) {
		for(T t: this.getTransitionsWithSource(sourceVertex)){
			if(t.getDestination().equals(targetVertex)){
				return t;
			}
		}
		return null;
	}

	@Override
	public EdgeFactory<S, T> getEdgeFactory() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public T addEdge(S sourceVertex, S targetVertex) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean addEdge(S sourceVertex, S targetVertex, T e) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean addVertex(S v) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean containsEdge(S sourceVertex, S targetVertex) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean containsEdge(T e) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean containsVertex(S v) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Set<T> edgeSet() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<T> edgesOf(S vertex) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean removeAllEdges(Collection<? extends T> edges) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Set<T> removeAllEdges(S sourceVertex, S targetVertex) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean removeAllVertices(Collection<? extends S> vertices) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public T removeEdge(S sourceVertex, S targetVertex) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean removeEdge(T e) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean removeVertex(S v) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Set<S> vertexSet() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public S getEdgeSource(T e) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public S getEdgeTarget(T e) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public double getEdgeWeight(T e) {
		// TODO Auto-generated method stub
		return 0;
	}


}
