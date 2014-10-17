package it.polimi.model.elements.states;

import it.polimi.model.impl.IntBAImpl;

import org.apache.commons.collections15.Factory;

/**
 * @author claudiomenghi
 * contains the factory method (see Factory method pattern) which is in charge of instantiating the {@link IntersectionState} of the {@link IntBAImpl}
 * @param <S> is the type of the {@link State} of the original automata
 * @param <IntS> is the type of the {@link IntersectionState} of the intersection automaton
 */
public class FactoryIntersectionState<S extends State, IntS extends IntersectionState<S>> implements Factory<IntS> {
	
	/**
	 * contains the counter which whose value will be associated to the next state of the automaton
	 */
	private static int nodeCount=0;
	
	/**
	 * crates a new {@link IntersectionState} with an empty name and two new {@link State}s for the original automata 
	 * @return a new {@link IntersectionState} with an empty name and two new {@link State}s for the original automata
	 */
	@SuppressWarnings("unchecked")
	@Override
	public IntS create() {
		
		IntersectionState<S> s=new IntersectionState<S>(new FactoryState<S>().create(), new FactoryState<S>().create(), 0, FactoryIntersectionState.nodeCount);
		FactoryIntersectionState.nodeCount++;
		return (IntS) s;
	}
	
	/**
	 * creates a new {@link IntersectionState} starting from the two {@link State}s s1 and s2 and with number num
	 * @param s1 is the first {@link State} of the automaton
	 * @param s2 is the second {@link State} of the automaton
	 * @param num is the number which is associated to the state
	 * @return a new {@link IntBAImpl} for the {@link IntBAImpl}
	 */
	@SuppressWarnings("unchecked")
	public IntS create(S s1, S s2, int num) {
		
		IntersectionState<S> s=new IntersectionState<S>(s1, s2, num, FactoryIntersectionState.nodeCount);
		FactoryIntersectionState.nodeCount++;
		return (IntS) s;
	}

}
