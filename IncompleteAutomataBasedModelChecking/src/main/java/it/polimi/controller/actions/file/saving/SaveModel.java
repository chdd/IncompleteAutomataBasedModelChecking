package it.polimi.controller.actions.file.saving;

import it.polimi.model.ModelInterface;

@SuppressWarnings("serial")
public class SaveModel extends SaveAction {

	public SaveModel(Object source, int id, String command) {
		super(source, id, command);
	}

	@Override
	public void perform(ModelInterface model) throws Exception {
		String filePath=this.getFile();
		model.saveModel(filePath);
	}

}
