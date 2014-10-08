package it.polimi.model.automata.intersection;

import it.polimi.model.automata.ba.state.State;

/**
 * @author claudiomenghi
 * contains an intersection state. The intersection state is a state that results from the intersection of two automata states.
 * The state is identified by the states of the two original automata and a number (0,1,2) that specifies if the state is accepting (2),
 * or not accepting (0 ,1)
 */
public class IntersectionState<S extends State> extends State{

	/**
	 * is the state of the fist automaton
	 */
	private final S s1;
	/**
	 * is the state of the second automaton
	 */
	private final S s2;
	/**
	 * is the number (0,1,2) that identifies if the state is accepting (2) or not accepting (0,1)
	 */
	private final int number;
	
	/**
	 * creates a new intersection state
	 * @param s1 is the state of the first automaton
	 * @param s2 is the state of the second automaton
	 * @param number is the number of the state
	 * @throws IllegalArgumentException is generated if the state s1 or the state s2 is null or if the number is different from 0,1,2
	 */
	protected IntersectionState(S s1, S s2, int number, int id) {
		super(id);
		if(s1==null){
				throw new IllegalArgumentException("The state s1 cannot be null");
		}
		if(s2==null){
			throw new IllegalArgumentException("The state s2 cannot be null");
		}
		if(!(number>=0 && number<=2)){
			throw new IllegalArgumentException("the number of the state must be equal to 0,1 or 2");
		}
		this.name=s1.getName()+"-"+s2.getName()+"-"+number;
		

	
		this.s1=s1;
		this.s2=s2;
		this.number=number;
		
	}

	/**
	 * @return the state s1 of the first automaton
	 */
	public S getS1() {
		return s1;
	}

	/**
	 * @return the state s2 of the second automaton
	 */
	public S getS2() {
		return s2;
	}

	/**
	 * @return the number of the state
	 */
	public int getNumber() {
		return number;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + number;
		result = prime * result + ((s1 == null) ? 0 : s1.hashCode());
		result = prime * result + ((s2 == null) ? 0 : s2.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (getClass() != obj.getClass())
			return false;
		IntersectionState other = (IntersectionState) obj;
		if (number != other.number)
			return false;
		if (s1 == null) {
			if (other.s1 != null)
				return false;
		} else if (!s1.equals(other.s1))
			return false;
		if (s2 == null) {
			if (other.s2 != null)
				return false;
		} else if (!s2.equals(other.s2))
			return false;
		return true;
	}

	
}
