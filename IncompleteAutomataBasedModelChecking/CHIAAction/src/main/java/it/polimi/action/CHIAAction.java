package it.polimi.action;

import com.google.common.base.Preconditions;

/**
 * contains the abstract definition of a CHIAAction
 * 
 * @author Claudio Menghi
 *
 * @param <O>
 *            is the type of the output of the class
 */
public abstract class CHIAAction<O> {

	/**
	 * is a flag which specifies if the action has been performed or not
	 */
	private boolean performed;
	/**
	 * is the name of the CHIAAction
	 */
	private final String name;

	/**
	 * creates a new CHIAAction with the specified name
	 * 
	 * @param name
	 *            the name of the CHIAAction
	 * @throws NullPointerException
	 *             if the name of the action is null
	 */
	public CHIAAction(String name) {
		Preconditions.checkNotNull(name,
				"The name of the action cannot be null");
		performed = false;
		this.name = name;
	}

	/**
	 * returns a boolean which specifies whether the action has been performed
	 * or not
	 * 
	 * @return a boolean which specifies whether the action has been performed
	 *         or not
	 */
	public boolean isPerformed() {
		return performed;
	}

	/**
	 * performs the action
	 * 
	 * @return the output of the action
	 * @throws Exception
	 *             if an exception in the execution of the action is thrown
	 */
	public abstract O perform() throws Exception;

	/**
	 * specifies that the action has been performed
	 */
	public void performed() {
		this.performed = true;
	}

	/**
	 * returns the name of the action
	 * 
	 * @return the name of the action
	 */
	public String getName() {
		return this.name;
	}
}
