package it.polimi.view.actions;

import java.awt.event.ActionEvent;

@SuppressWarnings("serial")
public class BuchiActionStateCreation extends ActionEvent {
	
	private final String name;
	private final boolean initial;
	private final boolean accepting;

	public BuchiActionStateCreation(Object source, int id, String command, String  name, boolean initial, boolean accepting){
		super(source, id, command);
		this.name=name;
		this.initial=initial;
		this.accepting=accepting;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}


		/**
	 * @return the initial
	 */
	public boolean isInitial() {
		return initial;
	}

	
	/**
	 * @return the accepting
	 */
	public boolean isAccepting() {
		return accepting;
	}

	
}
