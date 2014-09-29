package it.polimi.controller.actions;

import it.polimi.model.ModelInterface;

import java.awt.event.ActionEvent;

public class BuchiAddCharacterAction extends ActionEvent implements ActionInterface{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected final String character;
	public BuchiAddCharacterAction(Object source, int id, String command, String character) {
		super(source, id, command);
		this.character=character;
	}
	
	@Override
	public void perform(ModelInterface model) {
		model.addCharacterToTheSpecification(this.character);
	}
}
