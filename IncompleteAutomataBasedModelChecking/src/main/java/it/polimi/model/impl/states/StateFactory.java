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
	
	/**
	 * creates a new {@link State} with the specified name and id
	 * @param name is the name of the {@link State} to be created
	 * @param id is the id of the state to be created
	 * @return a new {@link State} with the specified name and id
	 * @throws IllegalArgumentException if the id is less than 0
	 */
	public STATE create(String name, int id) {
		if(id<0){
			throw new IllegalArgumentException("The id must be grater than or equal to 0");
		}
		State s=new State(name, id);
		StateFactory.nodeCount=Math.max(StateFactory.nodeCount++, id+1);
		return (STATE) s;
	}
}
