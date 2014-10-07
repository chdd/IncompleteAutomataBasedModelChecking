package it.polimi.model.automata.ba.state;

import org.apache.commons.collections15.Factory;

@SuppressWarnings("unchecked")
public class StateFactory<S extends State> implements Factory<S> {

	private static int nodeCount=0;
	
	@Override
	public S create() {
		
		State s=new State("s"+StateFactory.nodeCount, StateFactory.nodeCount);
		StateFactory.nodeCount++;
		return (S) s;
	}
	
	public S create(String name) {
		
		State s=new State(name, StateFactory.nodeCount);
		StateFactory.nodeCount++;
		return (S) s;
	}

}
