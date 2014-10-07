package it.polimi.controller.actions.file.loading;

import it.polimi.controller.actions.file.FileAction;

import java.io.File;

import javax.swing.JFileChooser;

/**
 * contains a loading action. The loading action loads the automaton from a specific file which is
 * obtained through the method getFile()
 * @author claudiomenghi
 */
public abstract class LoadingAction extends FileAction {

	public LoadingAction(Object source, int id, String command) {
		super(source, id, command);
	}
	
	/**
	 * returns the file from which loading the automaton
	 * @return the file from which loading the automaton
	 */
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

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
}