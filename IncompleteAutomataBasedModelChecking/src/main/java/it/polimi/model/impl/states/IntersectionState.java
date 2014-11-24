package it.polimi.model.impl.states;

/**
 * @author claudiomenghi contains an {@link IntersectionState}. The
 *         {@link IntersectionState} is a {@link State} that results from the
 *         intersection of two automata {@link State}s. The {@link State} is
 *         identified by the {@link State}s of the two original automata and a
 *         number (0,1,2) that specifies if the {@link State} is accepting (2),
 *         or not accepting (0 ,1)
 */
public class IntersectionState<STATE extends State> extends State {

	/**
	 * is the {@link State} of the fist automaton
	 */
	private final STATE s1;

	/**
	 * is the {@link State} of the second automaton
	 */
	private final STATE s2;

	/**
	 * is the number (0,1,2) that identifies if the {@link State} is accepting
	 * (2) or not accepting (0,1)
	 */
	private final int number;

	/**
	 * creates a new {@link IntersectionState}
	 * 
	 * @param s1
	 *            is the {@link State} of the first automaton
	 * @param s2
	 *            is the {@link State} of the second automaton
	 * @param number
	 *            is the number of the {@link State}
	 * @param the
	 *            name of the {@link State} to be created
	 * @throws IllegalArgumentException
	 *             if the number is different from 0,1,2 - if the name of the
	 *             {@link State} is null
	 * @throws NullPointerException
	 *             is generated if the {@link State} s1 or the {@link State} s2
	 *             is null
	 */
	protected IntersectionState(STATE s1, STATE s2, String name, int number,
			int id) {
		super(name, id);
		if (s1 == null) {
			throw new NullPointerException("The state s1 cannot be null");
		}
		if (s2 == null) {
			throw new NullPointerException("The state s2 cannot be null");
		}
		if (!(number >= 0 && number <= 2)) {
			throw new IllegalArgumentException(
					"The number of the state must be equal to 0,1 or 2");
		}

		this.s1 = s1;
		this.s2 = s2;
		this.number = number;
	}

	/**
	 * @return the {@link State} s1 of the first automaton
	 */
	public STATE getS1() {
		return s1;
	}

	/**
	 * @return the {@link State} s2 of the second automaton
	 */
	public STATE getS2() {
		return s2;
	}

	/**
	 * @return the number of the {@link State}
	 */
	public int getNumber() {
		return number;
	}

	/*
	 * (non-Javadoc)
	 * 
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

	/*
	 * (non-Javadoc)
	 * 
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
		IntersectionState<STATE> other = (IntersectionState<STATE>) obj;
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
	 * @return the {@link String} representation of the
	 *         {@link IntersectionState}
	 */
	public String toString() {
		return "<HTML>Id: {" + this.id + "}<BR>" + "{" + s1.getId() + "-"
				+ s2.getId() + "}" + "<BR>" + this.getName() + "</HTML>";
	}
}
