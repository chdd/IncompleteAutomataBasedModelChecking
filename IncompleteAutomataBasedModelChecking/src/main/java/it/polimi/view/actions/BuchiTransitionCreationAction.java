package it.polimi.view.actions;

import java.awt.event.ActionEvent;

public class BuchiTransitionCreationAction extends ActionEvent{
	
	
	private final String sourceState;
	private final String destinationState;
	private final String character;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public BuchiTransitionCreationAction(Object source, int id, String command,  String sourceState, String destinationState, String character){
		super(source, id, command);
		this.sourceState=sourceState;
		this.destinationState=destinationState;
		this.character=character;
	}

	/**
	 * @return the sourceState
	 */
	public String getSourceState() {
		return sourceState;
	}

	/**
	 * @return the destinationState
	 */
	public String getDestinationState() {
		return destinationState;
	}

	/**
	 * @return the character
	 */
	public String getCharacter() {
		return character;
	}

	
}
