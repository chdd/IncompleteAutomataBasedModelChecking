package it.polimi.model.impl.states;

import it.polimi.model.impl.automata.IntBAImpl;

import org.apache.commons.collections15.Factory;

/**
 * @author claudiomenghi
 * contains the factory method (see Factory method pattern) which is in charge of instantiating the {@link IntersectionState} of the {@link IntBAImpl}
 * @param <STATE> is the type of the {@link State} of the original automata
 * @param <INTERSECTIONSTATE> is the type of the {@link IntersectionState} of the intersection automaton
 */
public class IntersectionStateFactory<STATE extends State, INTERSECTIONSTATE extends IntersectionState<STATE>> 
	extends StateFactory<INTERSECTIONSTATE> implements Factory<INTERSECTIONSTATE> {
	
	/**
	 * contains the counter which whose value will be associated to the next state of the automaton
	 */
	private static int nodeCount=0;
	
	private StateFactory<STATE> stateFactory;
	
	public IntersectionStateFactory(){
		stateFactory=new StateFactory<STATE>();
	}
	
	/**
	 * crates a new {@link IntersectionState} with an empty name and two new {@link State}s for the original automata 
	 * @return a new {@link IntersectionState} with an empty name and two new {@link State}s for the original automata
	 */
	@SuppressWarnings("unchecked")
	@Override
	public INTERSECTIONSTATE create() {
		
		IntersectionState<STATE> s=new IntersectionState<STATE>(new StateFactory<STATE>().create(), new StateFactory<STATE>().create(), "", 0, IntersectionStateFactory.nodeCount);
		IntersectionStateFactory.nodeCount++;
		return (INTERSECTIONSTATE) s;
	}
	
	/**
	 * creates a new {@link IntersectionState} starting from the two {@link State}s s1 and s2 and with number num
	 * @param s1 is the first {@link State} of the automaton
	 * @param s2 is the second {@link State} of the automaton
	 * @param num is the number which is associated to the state
	 * @return a new {@link IntBAImpl} for the {@link IntBAImpl}
	 */
	@SuppressWarnings("unchecked")
	public INTERSECTIONSTATE create(STATE s1, STATE s2, int num) {
		
		IntersectionState<STATE> s=new IntersectionState<STATE>(s1, s2, s1.getName()+"-"+s2.getName()+"-"+num,  num, IntersectionStateFactory.nodeCount);
		IntersectionStateFactory.nodeCount++;
		return (INTERSECTIONSTATE) s;
	}
	
	/**
	 * creates a new {@link State} with the specified name and id
	 * @param name is the name of the {@link State} to be created
	 * @param id is the id of the state to be created
	 * @return a new {@link State} with the specified name and id
	 * @throws IllegalArgumentException if the id is less than 0
	 */
	public INTERSECTIONSTATE create(String name, int id) {
		if(id<0){
			throw new IllegalArgumentException("The id must be grater than or equal to 0");
		}
		@SuppressWarnings("unchecked")
		INTERSECTIONSTATE s=(INTERSECTIONSTATE) new IntersectionState<State>(this.stateFactory.create(), this.stateFactory.create(), name, 0, id);
		IntersectionStateFactory.nodeCount=Math.max(IntersectionStateFactory.nodeCount++, id+1);
		return s;
	}

}
