package it.polimi.controller.actions.file.saving;

import it.polimi.controller.actions.file.FileAction;

import javax.swing.JFileChooser;

@SuppressWarnings("serial")
public abstract class SaveAction extends FileAction {

	public SaveAction(Object source, int id, String command) {
		super(source, id, command);
	}

	protected String getFile(){
		JFileChooser saveFile = new JFileChooser();
		saveFile.setDialogTitle("Specify a file to save");
		saveFile.showSaveDialog(null);
		return saveFile.getSelectedFile().getAbsolutePath();
	}
}
