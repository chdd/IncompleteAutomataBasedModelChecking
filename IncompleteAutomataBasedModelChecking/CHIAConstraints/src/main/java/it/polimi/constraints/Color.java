package it.polimi.constraints;

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
		 * indicates a port from which an initial/final state can be reached by passing some transparent state
		 */
		YELLOW, 	
		/**
		 * indicates a port from which an initial/final state can be reached by passing some transparent state
		 */
		BLACK, 
}
