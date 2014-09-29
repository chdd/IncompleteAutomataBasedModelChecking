package it.polimi.controller.actions.file.saving;

import it.polimi.model.ModelInterface;

import java.awt.Frame;

@SuppressWarnings("serial")
public class SaveModel extends SaveAction {

	public SaveModel(Object source, int id, String command,  Frame frameParent, String modelXML) {
		super(source, id, command, frameParent, modelXML);
	}

	@Override
	public void perform(ModelInterface model) throws Exception {
		String filePath=this.getFile();
		model.loadModelFromXML(this.automatonXML);
		model.saveModel(filePath);
	}

}
