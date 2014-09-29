package it.polimi.controller.actions.file.saving;

import it.polimi.controller.actions.file.FileAction;

import java.awt.FileDialog;
import java.awt.Frame;

@SuppressWarnings("serial")
public abstract class SaveAction extends FileAction {

	private Frame frameParent;
	protected String automatonXML;
	public SaveAction(Object source, int id, String command, Frame frameParent, String automatonXML) {
		super(source, id, command);
		this.frameParent=frameParent;
		this.automatonXML=automatonXML;
	}

	protected String getFile(){
		 FileDialog fDialog = new FileDialog(this.frameParent, "Save", FileDialog.SAVE);
	     fDialog.setVisible(true);
	     return fDialog.getDirectory() + fDialog.getFile();
	}
}
