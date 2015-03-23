package it.polimi.constraints;

/**
 * The Color class specifies whether from the specific port it is
 * possible to directly reach an accepting state (red), whether the port is
 * directly reachable from an initial state (green) or whether to reach an
 * accepting or an initial state it is necessary to enter other transparent
 * states (yellow).
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
