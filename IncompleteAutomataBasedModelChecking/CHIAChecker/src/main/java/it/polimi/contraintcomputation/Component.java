/**
 * 
 */
package it.polimi.contraintcomputation;

import it.polimi.automata.state.State;

import java.util.HashSet;
import java.util.Set;

/**
 * @author claudiomenghi
 * 
 */
public class Component<S extends State> {

	/**
	 * contains the set of the states to be included in the component
	 */
	private Set<S> states;

	/**
	 * creates a new component with a null set of states
	 */
	public Component() {
		states = new HashSet<S>();
	}

	/**
	 * creates a new component with the specified set of states
	 * 
	 * @param states
	 *            contains the states to be added in the component
	 * @throws NullPointerException
	 *             if the set of the states is null
	 */
	public Component(Set<S> states) {
		if (states == null) {
			throw new NullPointerException(
					"The set of the states cannot be null");
		}
		this.states = states;
	}

	/**
	 * adds the state s to the set of the states included in the component
	 * 
	 * @param s
	 *            the state to be added into the set of the states
	 * @throws NullPointerException
	 *             if the state s is null
	 */
	public void add(S s) {
		if (s == null) {
			throw new NullPointerException("The state s cannot be null");
		}
		this.states.add(s);
	}

	/**
	 * adds the set of the states to the set of the states of the component
	 * 
	 * @param states
	 *            the set of the states to be added in the component
	 * @throws NullPointerException
	 *             if the set of the states to be added is null
	 */
	public void addAll(Set<S> states) {
		if (states == null) {
			throw new NullPointerException(
					"The set of the states to be added into the component cannot be null");
		}
		this.states.addAll(states);
	}

	/**
	 * returns the set of the states of the component
	 * 
	 * @return the set of the states of the component
	 */
	public Set<S> getStates() {
		return states;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((states == null) ? 0 : states.hashCode());
		return result;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		@SuppressWarnings("unchecked")
		Component<S> other = (Component<S>) obj;
		if (!states.equals(other.states))
			return false;
		return true;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		String ret = "<";

		for (S s : this.states) {
			ret = ret + s + ", ";
		}
		if (ret.endsWith(", ")) {
			ret = ret.substring(0, ret.length() - ", ".length());
		}
		ret = ret + ">";
		return ret;
	}
}
