package it.polimi.model.automata.intersection;

import it.polimi.model.automata.ba.state.State;
import it.polimi.model.automata.ba.state.StateFactory;

import org.apache.commons.collections15.Factory;

public class IntersectionStateFactory<S extends State, IntS extends IntersectionState<S>> implements Factory<IntS> {

	private static int nodeCount=0;
	@Override
	public IntS create() {
		
		IntersectionState<S> s=new IntersectionState<S>(new StateFactory<S>().create(), new StateFactory<S>().create(), 0, IntersectionStateFactory.nodeCount);
		IntersectionStateFactory.nodeCount++;
		return (IntS) s;
	}
	
	public IntS create(S s1, S s2, int num) {
		
		IntersectionState<S> s=new IntersectionState<S>(s1, s2, num, IntersectionStateFactory.nodeCount);
		IntersectionStateFactory.nodeCount++;
		return (IntS) s;
	}

}
