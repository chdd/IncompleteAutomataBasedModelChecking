package it.polimi.controller.actions.file;

import it.polimi.controller.actions.ActionInterface;

import java.awt.event.ActionEvent;

@SuppressWarnings("serial")
public abstract class FileAction extends ActionEvent implements ActionInterface{
	
	public FileAction(Object source, int id, String command) {
		super(source, id, command);
	}

}
