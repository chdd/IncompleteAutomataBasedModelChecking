package it.polimi.view.actions;

import java.awt.event.ActionEvent;

public class BuchiAddCharacterAction extends ActionEvent{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final String character;
	public BuchiAddCharacterAction(Object source, int id, String command, String character) {
		super(source, id, command);
		this.character=character;
	}
	/**
	 * @return the character
	 */
	public String getCharacter() {
		return character;
	}
	
	

}
