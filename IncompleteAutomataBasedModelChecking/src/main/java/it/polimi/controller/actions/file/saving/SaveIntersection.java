package it.polimi.controller.actions.file.saving;

import it.polimi.model.ModelInterface;

public class SaveIntersection extends SaveAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public SaveIntersection(Object source, int id, String command) {
		super(source, id, command);
	}

	@Override
	public void perform(ModelInterface model) throws Exception {
		String filePath=this.getFile();
		if(filePath!=null){
			model.saveIntersection(filePath);
		}
	}
}
