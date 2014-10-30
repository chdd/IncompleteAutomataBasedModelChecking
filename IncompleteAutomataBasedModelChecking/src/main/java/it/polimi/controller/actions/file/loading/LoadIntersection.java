package it.polimi.controller.actions.file.loading;

import it.polimi.model.ModelInterface;

@SuppressWarnings("serial")
public class LoadIntersection extends LoadingAction {

	public LoadIntersection(Object source, int id, String command) {
		super(source, id, command);
	}

	@Override
	public void perform(ModelInterface model) throws Exception {
		String file=this.getFile();
		if(file!=null){
			model.loadIntersection(file);
		}
	}
}

