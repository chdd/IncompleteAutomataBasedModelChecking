package it.polimi.controller.actions;

import it.polimi.model.ModelInterface;

public class CheckAction implements ActionInterface {

	@Override
	public void perform(ModelInterface model) throws Exception {
		model.check();
	}

}
