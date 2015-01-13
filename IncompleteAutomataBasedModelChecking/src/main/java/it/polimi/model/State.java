package it.polimi.model;

/**
 * contains a {@link State} of the automaton, 
 * which is represented by its id and is labeled with a name
 * @author claudiomenghi
 *
 */
public interface State {
	
	/**
	 * returns the id of the {@link State}
	 * 
	 * @return the id of the {@link State}
	 */
	public int getId();

	/**
	 * returns the name of the {@link State}
	 * 
	 * @return the name of the {@link state}
	 */
	public String getName();
}
