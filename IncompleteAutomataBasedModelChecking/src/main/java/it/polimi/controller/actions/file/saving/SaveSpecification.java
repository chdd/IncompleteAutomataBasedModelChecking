package it.polimi.controller.actions.file.saving;

import it.polimi.model.ModelInterface;

import java.awt.Frame;

@SuppressWarnings("serial")
public class SaveSpecification extends SaveAction {

	

	public SaveSpecification(Object source, int id, String command,
			Frame frameParent, String modelXML) {
		super(source, id, command, frameParent, modelXML);
	}

	@Override
	public void perform(ModelInterface model) throws Exception {
		String filePath=this.getFile();
		model.loadSpecificationFromXML(this.automatonXML);
		model.saveSpecification(filePath);
	}


}
