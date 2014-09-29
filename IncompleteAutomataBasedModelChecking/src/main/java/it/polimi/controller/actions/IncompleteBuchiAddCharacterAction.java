package it.polimi.controller.actions;

import it.polimi.model.ModelInterface;

public class IncompleteBuchiAddCharacterAction extends BuchiAddCharacterAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public IncompleteBuchiAddCharacterAction(Object source, int id,
			String command, String character) {
		super(source, id, command, character);
	}

	@Override
	public void perform(ModelInterface model) {
		model.addCharacterToTheModed(this.character);
	}
}
