package it.polimi.constraints.transitions;

/**
 * The Color enum type can assume three different values RED if an accepting
 * state is directly reachable from the port, GREEN if the port is directly
 * reachable from the initial state and YELLOW if it is necessary to enter an
 * initial state before reaching an accepting or a transparent state,
 * respectively.
 * 
 * @author claudiomenghi
 *
 */
public enum Color {
	/**
	 * indicates a port from which the accepting state can be reached
	 */
	RED,
	/**
	 * indicates a port that can be reached from an initial state
	 */
	GREEN,
	/**
	 * indicates a port from which an initial/final state can be reached by
	 * passing some transparent state
	 */
	YELLOW,
	/**
	 * indicates a port from which an initial/final state can be reached by
	 * passing some transparent state
	 */
	BLACK,
}
