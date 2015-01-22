package it.polimi.controller.actions.file.saving;

import it.polimi.controller.actions.file.FileAction;
import it.polimi.model.impl.states.State;
import it.polimi.model.impl.transitions.Transition;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import edu.uci.ics.jung.algorithms.layout.AbstractLayout;

@SuppressWarnings("serial")
public abstract class SaveAction<
CONSTRAINEDELEMENT extends State, 
STATE extends State, 
TRANSITION extends Transition,
LAYOUT extends AbstractLayout<?, ?>>
		extends FileAction<CONSTRAINEDELEMENT, STATE,  TRANSITION> {

	protected LAYOUT layout;
	
	
	public SaveAction(Object source, int id, String command, LAYOUT layout) {
		super(source, id, command);
		this.layout=layout;
		
	}

	protected String getFile(FileNameExtensionFilter filter){
		if(filter==null){
			throw new NullPointerException("The file filter cannot be null");
		}
		
		JFileChooser saveFile = new JFileChooser();
		saveFile.setAcceptAllFileFilterUsed(false);
		saveFile.addChoosableFileFilter(filter);
		saveFile.setFileFilter(filter);
		saveFile.removeChoosableFileFilter(saveFile.getAcceptAllFileFilter());
		saveFile.setSelectedFile(new File("*."+filter.getExtensions()[0].toString()));
		saveFile.setDialogTitle("Specify a file to save");
		saveFile.showSaveDialog(null);
		if(saveFile.getSelectedFile()!=null){
			String selectedFile=saveFile.getSelectedFile().getAbsolutePath();
			if (selectedFile.contains(".") &&
					selectedFile.substring(selectedFile.lastIndexOf(".")).equals("."+filter.getExtensions()[0].toString())) {
					return saveFile.getSelectedFile().getAbsolutePath();
			} 
			else 
			{
				throw new IllegalArgumentException("The saved file must have the "+filter.getExtensions()[0].toString()+" extension");
			}
		}
		throw new NullPointerException("It is not possible to save a null file");
	}
	protected String getDirectory(String fileName){
		
		JFileChooser saveFile = new JFileChooser();
		
		saveFile.setDialogTitle("Select the folder where the model must be saved");
		saveFile.setAcceptAllFileFilterUsed(false);
		saveFile.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		saveFile.setAcceptAllFileFilterUsed(false);
		saveFile.showSaveDialog(null);
		saveFile.setSelectedFile(new File(fileName));
		
		if(saveFile.getCurrentDirectory()!=null){
			return saveFile.getCurrentDirectory().getAbsolutePath();
		}
		throw new NullPointerException("It is not possible to save a null folder");
	}
}
