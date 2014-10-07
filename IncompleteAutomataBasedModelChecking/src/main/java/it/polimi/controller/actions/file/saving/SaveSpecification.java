package it.polimi.controller.actions.file.saving;

import it.polimi.model.ModelInterface;

@SuppressWarnings("serial")
public class SaveSpecification extends SaveAction {

	

	public SaveSpecification(Object source, int id, String command) {
		super(source, id, command);
	}

	@Override
	public void perform(ModelInterface model) throws Exception {
		String filePath=this.getFile();
		model.saveSpecification(filePath);
	}


}
