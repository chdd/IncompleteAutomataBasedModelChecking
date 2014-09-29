package it.polimi.controller.actions.file.loading;

import it.polimi.model.ModelInterface;

@SuppressWarnings("serial")
public class LoadSpecification extends LoadingAction{

	public LoadSpecification(Object source, int id, String command) {
		super(source, id, command);
	}

	@Override
	public void perform(ModelInterface model) throws Exception {
		String file=this.getFile();
		model.changeSpecification(file);
	}
}
