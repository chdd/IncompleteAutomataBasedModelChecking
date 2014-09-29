package it.polimi.controller.actions.file.loading;

import it.polimi.model.ModelInterface;

@SuppressWarnings("serial")
public class LoadModel extends LoadingAction{

	
	public LoadModel(Object source, int id, String command) {
		super(source, id, command);
	}

	@Override
	public void perform(ModelInterface model) throws Exception {
		String file=this.getFile();
		model.changeModel(file);
	}
}
