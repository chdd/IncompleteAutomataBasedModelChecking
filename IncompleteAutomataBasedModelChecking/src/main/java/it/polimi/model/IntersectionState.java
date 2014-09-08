package it.polimi.model;

/**
 * @author Claudio Menghi
 * contains an intersection state. The intersection state is a state that results from the intersection of two automata states.
 * The state is identified by the states of the two original automata and a number (0,1,2) that specifies if the state is accepting (2),
 * or not accepting (0 ,1)
 */
public class IntersectionState<S extends State> extends State{

	/**
	 * is the state of the fist automaton
	 */
	private S s1;
	/**
	 * is the state of the second automaton
	 */
	private S s2;
	/**
	 * is the number (0,1,2) that identifies if the state is accepting (2) or not accepting (0,1)
	 */
	private int number;
	
	/**
	 * creates a new intersection state
	 * @param s1 is the state of the first automaton
	 * @param s2 is the state of the second automaton
	 * @param number is the number of the state
	 * @throws IllegalArgumentException is generated if the state s1 or the state s2 is null or if the number is different from 0,1,2
	 */
	public IntersectionState(S s1, S s2, int number) {
		super("");
		if(s1==null){
			throw new IllegalArgumentException("The state s1 cannot be null");
		}
		if(s2==null){
			throw new IllegalArgumentException("The state s2 cannot be null");
		}
		if(!(number>=0 && number<=2)){
			throw new IllegalArgumentException("the number of the state must be equal to 0,1 or 2");
		}
		super.setName(s1.getName()+"-"+s2.getName()+"-"+number);
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
		int result = super.hashCode();
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
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		@SuppressWarnings("unchecked")
		IntersectionState<S> other = (IntersectionState<S>) obj;
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

	/**
	 * generates a new intersection state
	 * @param a1: is the first automata which contains the state s1
	 * @param a2: is the automaton that contains the state s2
	 * @param s1: is the state of the automaton a1 to be included in the intersection state
	 * @param s2: is the state of the automaton a2 to be included in the intersection state
	 * @param currentState: is the current state to be considered in the generation of the automaton state
	 * @return a new intersection state
	 */
	protected static<S extends State,  T extends Transition<S>, A extends BuchiAutomaton<S,T>> IntersectionState<S> generateIntersectionState( A a1, A a2, S s1, S s2, IntersectionState<S> currentState){
		int num=0;
		if(currentState!=null){
			num=currentState.getNumber();
		}
		if(num==0 && a1.isAccept(s1)){
			num=1;
		}
		else{
			if(num==1 && a2.isAccept(s2)){
				num=2;
			}
			else{
				if(num==2){
					num=0;
				}
			}
		}
		
		IntersectionState<S> p = new IntersectionState<S>(s1, s2, num);
		
		return p;
	}
	
	
}
