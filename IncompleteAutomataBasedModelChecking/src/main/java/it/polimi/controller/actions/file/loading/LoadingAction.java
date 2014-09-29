package it.polimi.controller.actions.file.loading;

import it.polimi.controller.actions.file.FileAction;

import java.io.File;

import javax.swing.JFileChooser;

@SuppressWarnings("serial")
public abstract class LoadingAction extends FileAction {

	public LoadingAction(Object source, int id, String command) {
		super(source, id, command);
	}
	
	protected String getFile() {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
		int result = fileChooser.showOpenDialog(null);
		if (result == JFileChooser.APPROVE_OPTION) {
		    File selectedFile = fileChooser.getSelectedFile();
		    return selectedFile.getAbsolutePath();
		}
		return null;
	}

}
