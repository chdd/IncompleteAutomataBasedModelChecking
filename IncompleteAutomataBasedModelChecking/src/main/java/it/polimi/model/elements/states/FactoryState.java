package it.polimi.model.elements.states;

import org.apache.commons.collections15.Factory;

/**
 * @author claudiomenghi
 * contains the factory method (see Factory method pattern) which is in charge of instantiating the states of the automaton
 * @param <S> is the type of the states to be instantiated
 */
@SuppressWarnings("unchecked")
public class FactoryState<S extends State> implements Factory<S> {

	/**
	 * contains the counter which whose value will be associated to the next state of the automaton
	 */
	private static int nodeCount=0;
	
	/**
	 * crates a new {@link State} with an empty name
	 * @return a new state with an empty name
	 */
	@Override
	public S create() {
		
		State s=new State("s"+FactoryState.nodeCount, FactoryState.nodeCount);
		FactoryState.nodeCount++;
		return (S) s;
	}
	
	/**
	 * creates a new {@link State} with the specified name
	 * @param name the name of the state
	 * @return a new {@link State} with the specified name
	 */
	public S create(String name) {
		
		State s=new State(name, FactoryState.nodeCount);
		FactoryState.nodeCount++;
		return (S) s;
	}
}
