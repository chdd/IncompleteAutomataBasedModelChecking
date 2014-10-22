package it.polimi.model.impl.states;

import org.apache.commons.collections15.Factory;

/**
 * @author claudiomenghi
 * contains the factory method (see Factory method pattern) which is in charge of instantiating the states of the automaton
 * @param <STATE> is the type of the states to be instantiated
 */
@SuppressWarnings("unchecked")
public class StateFactory<STATE extends State> implements Factory<STATE> {

	/**
	 * contains the counter which whose value will be associated to the next state of the automaton
	 */
	private static int nodeCount=0;
	
	/**
	 * crates a new {@link State} with an empty name
	 * @return a new state with an empty name
	 */
	@Override
	public STATE create() {
		
		State s=new State("s"+StateFactory.nodeCount, StateFactory.nodeCount);
		StateFactory.nodeCount++;
		return (STATE) s;
	}
	
	/**
	 * creates a new {@link State} with the specified name
	 * @param name the name of the state
	 * @return a new {@link State} with the specified name
	 */
	public STATE create(String name) {
		
		State s=new State(name, StateFactory.nodeCount);
		StateFactory.nodeCount++;
		return (STATE) s;
	}
	
	public STATE create(String name, int id) {
		
		State s=new State(name, id);
		StateFactory.nodeCount=Math.max(StateFactory.nodeCount++, id+1);
		return (STATE) s;
	}
}
